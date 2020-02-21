package com.briskhu.exercize.springnetty.loginserver.handler;

import com.alibaba.fastjson.JSON;
import com.briskhu.exercize.springnetty.common.constant.CommonConstant;
import com.briskhu.exercize.springnetty.common.constant.MsgCode;
import com.briskhu.exercize.springnetty.common.constant.RedisKey;
import com.briskhu.exercize.springnetty.common.dto.req.device.netty.DeviceLoginDataDto;
import com.briskhu.exercize.springnetty.common.dto.req.device.netty.DeviceLoginReqDto;
import com.briskhu.exercize.springnetty.common.dto.resp.server.ServerToDeviceDataDto;
import com.briskhu.exercize.springnetty.common.dto.resp.server.ServerToDeviceRespDto;
import com.briskhu.exercize.springnetty.common.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.Map;

/**
 * 处理设备登录的基类<p/>
 *
 * @author Brisk Hu
 * created on 2020-01-15
 **/
public abstract class BaseDeviceLoginHanlder<Req extends DeviceLoginDataDto, Resp extends ServerToDeviceDataDto> implements LoginHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDeviceLoginHanlder.class);


    /* ---------------------------------------- fileds ---------------------------------------- */
    @Autowired
    private RedisTemplate redisTemplate;


    /* ---------------------------------------- methods ---------------------------------------- */

    /**
     * 异步处理接收到消息
     *
     * @param deviceLoginReqDto
     * @return
     */
    @Override
    public ListenableFuture<ServerToDeviceRespDto> asynHandleMessage(DeviceLoginReqDto deviceLoginReqDto) {
        LOGGER.info("[asynHandleMessage] start: deviceLoginReqDto = {}.", deviceLoginReqDto);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String secretKey = null;
        String secretCipher = null;

        String dn = deviceLoginReqDto.getDn();
        if (dn == null || dn.equals("")) {
            LOGGER.error("[asynHandleMessage] dn is null.");
            return null;
        }

        Map<String, String> dnBase = redisTemplate.opsForHash().entries(RedisKey.Device.D_BASE + dn);
        String pin = dnBase.get(RedisKey.Device.F_PIN);
        String mac = dnBase.get(RedisKey.Device.F_MAC);
        ServerToDeviceDataDto serverToDeviceDataDto = null;

        try {
            // 获取解密秘钥
            secretKey = getSecretKey(pin);
            secretCipher = getSecretCipher(pin);
            if (StringUtils.isEmpty(secretKey) || StringUtils.isEmpty(secretCipher)) {
                LOGGER.error("[asynHandleMessage] 解密秘钥为空.");
                return null;
            }

            // 处理本次请求
            String data = deviceLoginReqDto.getData();
            if (StringUtils.isEmpty(data)) {
                serverToDeviceDataDto = new ServerToDeviceDataDto(MsgCode.Common.PARAM_NULL, dn);
                return new AsyncResult<>(initServerRespDto(serverToDeviceDataDto, secretKey, secretCipher));
            }

            //TODO-Brisk 处理数据解密

            Req reqDataDto = JSON.parseObject(data, this.getReqDataClass());
            if (reqDataDto != null) {
                final String finalKey = secretKey;
                final String finalCipher = secretCipher;

                SettableListenableFuture<ServerToDeviceRespDto> respFuture = new SettableListenableFuture<>();

                // 调用子类实现的具体 handleReqData 方法处理子类的业务逻辑
                this.handleReqData(reqDataDto, dn, secretCipher).addCallback(
                        respData -> {
                            ServerToDeviceRespDto serverToDeviceRespDto = this.initServerRespDto(respData, finalKey, finalCipher);
                            if (serverToDeviceRespDto != null){
                                respFuture.set(serverToDeviceRespDto);
                            } else {
                                respFuture.set(null);
                            }
                        },
                        exception -> {
                            if (exception instanceof ApiException){
                                ApiException ae = (ApiException) exception;
                                LOGGER.error("[asynHandleMessage] 消息处理发生异常：code = {}，msg = {}.",
                                        ae.getMsgCode().getReplyCode(), ae.getMsgCode().getMsg());
                                ServerToDeviceDataDto respData = new ServerToDeviceDataDto(ae.getMsgCode(), dn);
                                respFuture.set(this.initServerRespDto(respData, finalKey, finalCipher));
                            }
                        }
                );
                LOGGER.info("[asynHandleMessage] end.");
                return respFuture;
            }

            serverToDeviceDataDto = new ServerToDeviceDataDto(MsgCode.Common.PARAM_VALUE_INVALID, dn);
            return new AsyncResult<>(initServerRespDto(serverToDeviceDataDto, secretKey, secretCipher));
        }
        // 处理业务异常
        catch (ApiException ae) {
            LOGGER.error("[asynHandleMessage] 消息处理发生异常：code = {}，msg = {}.", ae.getMsgCode().getReplyCode(), ae.getMsgCode().getMsg());
            ae.printStackTrace();
            serverToDeviceDataDto = new ServerToDeviceDataDto(ae.getMsgCode(), dn);
            return new AsyncResult<>(initServerRespDto(serverToDeviceDataDto, secretKey, secretCipher));
        }
        // 处理其他未知异常
        catch (Exception e){
            LOGGER.error("[asynHandleMessage] 消息处理发生异常：cause = {}，msg = {}.", e.getCause(), e.getMessage());
            e.printStackTrace();
            serverToDeviceDataDto = new ServerToDeviceDataDto(MsgCode.Common.UNKNOWN_ERR, dn);
            return new AsyncResult<>(initServerRespDto(serverToDeviceDataDto, secretKey, secretCipher));
        }
        // 关闭try
        finally {
            stopWatch.stop();
            LOGGER.info("[asynHandleMessage] 本次处理耗时：code = {}, dn = {}, time = {}ms.",
                    deviceLoginReqDto.getCode(), dn, stopWatch.getLastTaskTimeMillis());
        }
    }

    /**
     * 加密秘钥
     * 默认取的pin码的前16位。
     * @param pin
     * @return
     */
    private String getSecretKey(String pin) {
        try {
            return pin.substring(0, 16);
        } catch (Exception e) {
            throw new ApiException(MsgCode.Device.PIN_ERR);
        }
    }

    /**
     * 加密秘钥
     * 默认去pin码的后16位作为cipher。
     * @param pin
     * @return
     */
    private String getSecretCipher(String pin) {
        try {
            return pin.substring(16, 32);
        } catch (Exception e) {
            throw new ApiException(MsgCode.Device.PIN_ERR);
        }
    }


    /**
     * 初始化服务器向设备的返回体
     *
     * @param respDataDto
     * @param key
     * @param cipher
     * @return
     */
    private ServerToDeviceRespDto initServerRespDto(ServerToDeviceDataDto respDataDto, String key, String cipher) {
        ServerToDeviceRespDto result = new ServerToDeviceRespDto(respDataDto.getResultCode()
                + CommonConstant.Str.EMPTY, respDataDto.getDn());
        result.setCode(this.getResponseCode());
        String respDataStr = JSON.toJSONString(respDataDto);
        LOGGER.debug("[initServerRespDto] respDataStr = {}.", respDataStr);

        // TODO-Brisk 处理数据加密

        result.setData(respDataStr);

        return result;
    }

    /**
     * 获取请求参数中Data字段的类型
     *
     * @return
     */
    abstract public Class<Req> getReqDataClass();

    /**
     * 处理请求体中的data字段数据
     *
     * @param reqData
     * @param dn
     * @param cipher
     * @return
     */
    abstract public ListenableFuture<Resp> handleReqData(Req reqData, String dn, String cipher);

    /**
     * 获取返回消息码
     * @return
     */
    abstract public String getResponseCode();

}


