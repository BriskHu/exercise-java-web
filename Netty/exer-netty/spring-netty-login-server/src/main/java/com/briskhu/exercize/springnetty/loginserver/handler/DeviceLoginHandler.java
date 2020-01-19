package com.briskhu.exercize.springnetty.loginserver.handler;

import com.briskhu.exercize.springnetty.common.constant.MsgCode;
import com.briskhu.exercize.springnetty.common.dto.req.device.DeviceLoginDataDto;
import com.briskhu.exercize.springnetty.common.dto.resp.server.ServerToDeviceDataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2020-01-15
 **/
@Component
public class DeviceLoginHandler extends BaseDeviceLoginHanlder<DeviceLoginDataDto, ServerToDeviceDataDto> {
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
     * @param cipher
     * @return
     */
    @Override
    public ListenableFuture<ServerToDeviceDataDto> handleReqData(DeviceLoginDataDto reqData, String dn, String cipher) {
        return null;
    }

    /**
     * 获取返回消息码
     *
     * @return
     */
    @Override
    public String getResponseCode() {
        return MsgCode.Netty.DEVICE_LOGIN_TOSERVER.getCodeString();
    }

    /**
     * 获取当前待处理消息的消息码
     *
     * @return
     */
    @Override
    public String getMessageCode() {
        return MsgCode.Netty.DEVICE_LOGIN_TODEVICE.getCodeString();
    }


}


