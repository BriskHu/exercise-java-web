package com.briskhu.exercize.springnetty.loginserver.handler;

import com.briskhu.exercize.springnetty.common.dto.req.device.netty.DeviceLoginReqDto;
import com.briskhu.exercize.springnetty.common.dto.resp.server.ServerToDeviceRespDto;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * 设备登录处理器<p/>
 *
 * @author Brisk Hu
 * created on 2020-01-08
 **/
public interface LoginHandler {

    /**
     * 异步处理接收到消息
     * @param deviceLoginReqDto
     * @return
     */
    ListenableFuture<ServerToDeviceRespDto> asynHandleMessage(DeviceLoginReqDto deviceLoginReqDto);

    /**
     * 获取当前待处理消息的消息码
     * @return
     */
    String getMessageCode();
}
