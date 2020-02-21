package com.briskhu.exercize.springnetty.loginserver.handler;

import com.briskhu.exercize.springnetty.common.constant.MsgCode;
import com.briskhu.exercize.springnetty.common.constant.RedisKey;
import com.briskhu.exercize.springnetty.common.dto.info.device.DeviceSessionInfo;
import com.briskhu.exercize.springnetty.common.dto.req.device.netty.DeviceLoginDataDto;
import com.briskhu.exercize.springnetty.common.dto.resp.server.DeviceLoginRespData;
import com.briskhu.exercize.springnetty.common.exception.ApiException;
import com.briskhu.exercize.springnetty.common.util.AesEncryptUtil;
import com.briskhu.exercize.springnetty.common.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2020-01-15
 **/
@Component
public class DeviceLoginHandler extends BaseDeviceLoginHanlder<DeviceLoginDataDto, DeviceLoginRespData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceLoginHandler.class);

    /* ---------------------------------------- fileds ---------------------------------------- */
    @Autowired
    private RedisTemplate redisTemplate;




    /* ---------------------------------------- methods ---------------------------------------- */

    /**
     * 获取请求参数中Data字段的类型
     *
     * @return
     */
    @Override
    public Class<DeviceLoginDataDto> getReqDataClass() {
        return DeviceLoginDataDto.class;
    }

    /**
     * 处理请求体中的data字段数据
     *
     * @param reqData
     * @param dn
     * @param cipher 16位字符串秘钥
     * @return
     */
    @Override
    public ListenableFuture<DeviceLoginRespData> handleReqData(DeviceLoginDataDto reqData, String dn, String cipher) {
        //参数验证
        ValidateUtil.notEmptyForFuture(reqData.getDn(), "[handleReqData] dn不能为空。");
        ValidateUtil.notEmptyForFuture(reqData.getPin(), "[handleReqData] pin不能为空。");
        ValidateUtil.notEmptyForFuture(reqData.getIp(), "[handleReqData] ip不能为空。");

        if (!reqData.getDn().equals(dn)){
            LOGGER.error("[handleReqData] 请求参数中的dn和data字段中的dn不一致。");
            return new SettableListenableFuture<DeviceLoginRespData>(){
                {
                    setException(new ApiException(MsgCode.Common.PARAM_VALUE_INVALID));
                }
            };
        }

        DeviceLoginRespData deviceLoginRespData = new DeviceLoginRespData();
        SettableListenableFuture<DeviceLoginRespData> deviceLoginFuture = new SettableListenableFuture<>();
        // 获取连接信息
        Map<String, String> sessionInfoMap = redisTemplate.opsForHash().entries(RedisKey.Device.D_SESSION + dn);
        LOGGER.debug("[handleReqData] 从redis中获取设备登录信息：key = {}, sessionInfoMap = {}.", RedisKey.Device.D_SESSION + dn, sessionInfoMap);
        if (sessionInfoMap != null){
            deviceLoginRespData.setResultCode(MsgCode.Common.SUCCESS.getReplyCode());
            deviceLoginRespData.setSessionKey(sessionInfoMap.get(RedisKey.Device.F_SESSION_KEY));
            deviceLoginRespData.setToken(sessionInfoMap.get(RedisKey.Device.F_TOKEN));
            deviceLoginRespData.setDn(dn);
            deviceLoginFuture.set(deviceLoginRespData);
        }
        else {
            Date now = new Date();
            String token = AesEncryptUtil.getSessionKey(reqData.getPin() + now);
            String sessionKey = AesEncryptUtil.getSessionKey(dn + now);
            DeviceSessionInfo sessionInfo = new DeviceSessionInfo(dn, sessionKey, token, cipher, reqData.getIp());
            saveDeviceSessionToRedis(sessionInfo);

            deviceLoginRespData.setDn(dn);
            deviceLoginRespData.setSessionKey(sessionKey);
            deviceLoginRespData.setToken(token);
            deviceLoginRespData.setResultCode(MsgCode.Netty.DEVICE_LOGIN_TODEVICE.getReplyCode());

            deviceLoginFuture.set(deviceLoginRespData);
        }

        return deviceLoginFuture;
    }

    /**
     * 获取返回消息码
     *
     * @return
     */
    @Override
    public String getResponseCode() {
        return MsgCode.Netty.DEVICE_LOGIN_TODEVICE.getCodeString();
    }

    /**
     * 获取当前待处理消息的消息码
     *
     * @return
     */
    @Override
    public String getMessageCode() {
        return MsgCode.Netty.DEVICE_LOGIN_TOSERVER.getCodeString();
    }

    private void saveDeviceSessionToRedis(DeviceSessionInfo sessionInfo){
        Map<String, String> map = new HashMap<>();
        map.put(RedisKey.Device.F_SESSION_KEY, sessionInfo.getSessionKey());
        map.put(RedisKey.Device.F_TOKEN, sessionInfo.getToken());
        map.put(RedisKey.Device.F_CIPHER, sessionInfo.getCipher());
        map.put(RedisKey.Device.F_IP, sessionInfo.getTcpHost());

        redisTemplate.opsForHash().putAll(RedisKey.Device.D_SESSION + sessionInfo.getDn(), map);
    }


}


