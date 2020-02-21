package com.briskhu.exercize.springnetty.loginserver.netty;

import com.alibaba.fastjson.JSON;
import com.briskhu.exercize.springnetty.common.dto.req.device.netty.DeviceLoginReqDto;
import com.briskhu.exercize.springnetty.common.constant.CommonConstant;
import com.briskhu.exercize.springnetty.common.constant.RedisKey;
import com.briskhu.exercize.springnetty.common.constant.MsgCode;
import com.briskhu.exercize.springnetty.loginserver.context.AppServerNettyContext;
import com.briskhu.exercize.springnetty.loginserver.handler.DeviceLoginHandler;
import com.briskhu.exercize.springnetty.loginserver.handler.LoginHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.InetSocketAddress;

/**
 * 登录服务器入站事件处理类<p/>
 *
 * @author Brisk Hu
 * created on 2019-12-08
 **/
@Component
@ChannelHandler.Sharable
public class LoginServerInboundHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServerInboundHandler.class);


    /* ---------------------------------------- fileds ---------------------------------------- */
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LoginHandler loginHandler;
    @Autowired
    private DeviceLoginHandler deviceLoginHandler;

    /* ---------------------------------------- methods ---------------------------------------- */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        LOGGER.info("[channelRead0] context = {}, msg = {}.", channelHandlerContext, msg);

        try {
            if (msg instanceof String) {
                DeviceLoginReqDto deviceLoginReqDto = JSON.parseObject((String) msg, DeviceLoginReqDto.class);

                // 请求消息不为空
                if (deviceLoginReqDto != null) {
                    String deviceSn = deviceLoginReqDto.getDn();

                    // 添加设登录信息
                    if (deviceSn != null) {
                        // 获取设备的公网ip
                        InetSocketAddress deviceSockerAddr = (InetSocketAddress) channelHandlerContext.channel().remoteAddress();
                        String clientIp = deviceSockerAddr.getAddress().getHostAddress();
                        LOGGER.info("[channelRead0] 当前登录的设备为：deviceSn = {}, ip = {}.", deviceSn, clientIp);

                        String ip = (String) redisTemplate.opsForHash().get(RedisKey.Device.D_AUTH + deviceSn, RedisKey.Device.F_IP);
                        if (StringUtils.isEmpty(ip) || !clientIp.equals(ip)) {
                            LOGGER.debug("[channelRead0] 更新设备的登录信息：deviceSn = {}, key = {}，ip = {}.", deviceSn, RedisKey.Device.D_AUTH + deviceSn, clientIp);
                            redisTemplate.opsForHash().put(RedisKey.Device.D_AUTH + deviceSn, RedisKey.Device.F_IP, clientIp);
                            redisTemplate.opsForSet().add(RedisKey.Device.D_ONLINE, deviceSn);
                        }

//                        channelHandlerContext.channel().writeAndFlush("Server has received your message.").sync();
//                        LOGGER.debug("[channelRead0] 向连接者发送回执。");
                    }

                    if (deviceLoginReqDto.getCode().equals(MsgCode.Netty.APP_SERVER_CONNECT.getReplyCode() + CommonConstant.Str.EMPTY)) {
                        AppServerNettyContext.addConnectChannel(channelHandlerContext.channel());
                        return;
                    }

                    loginHandler = deviceLoginHandler;
                    if (loginHandler == null){
                        LOGGER.error("[channelRead0] 未获取到设备登录处理对象。");
                        channelHandlerContext.channel().close();
                        return;
                    }

                    loginHandler.asynHandleMessage(deviceLoginReqDto).addCallback(response -> {
                        LOGGER.debug("[addCallback] response = {}", response);
                        if (response != null){
                            channelHandlerContext.channel().writeAndFlush(response.toTransDto()).addListener(new GenericFutureListener<ChannelFuture>() {
                                @Override
                                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                    LOGGER.info("[operationComplete] 向连接的设备发送响应：result = {}, response = {}",
                                            channelFuture.isSuccess(), response);
                                    channelHandlerContext.channel().close();
                                }
                            });
                        }
                    }, exception -> {
                        LOGGER.error("[channelRead0] 服务器捕获到异常：errMsg = {}。", exception.getMessage());
                        channelHandlerContext.channel().close();
                    });
                }
            } else {
                LOGGER.error("[channelRead0] 消息体格式错误");
                channelHandlerContext.channel().close();
            }
        } catch (Exception e) {
            LOGGER.error("[channelRead0] 设备登录服务数据接收失败： errMsg = {}.", e);
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        LOGGER.error("[exceptionCaught] 设备登录服务捕获到异常：channel = {}, errMsg = {}.", ctx.channel(), cause);
        ctx.channel().close();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        LOGGER.debug("[channelRegistered] 设备登录服务连接注册：channel = {}.", ctx.channel());
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        LOGGER.debug("[channelUnregistered] 设备登录服务连接注销：channel = {}.", ctx.channel());
        super.channelUnregistered(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    LOGGER.error("[userEventTriggered] {} read timeout.", ctx.channel());
                    break;
                case WRITER_IDLE:
                    LOGGER.error("[userEventTriggered] {} write timeout.", ctx.channel());
                    break;
                case ALL_IDLE:
                    LOGGER.error("[userEventTriggered] {} 总超时.", ctx.channel());
                    break;
            }
            ctx.channel().close();
        } else {
            LOGGER.debug("[userEventTriggered] {} trigger event: {} .", ctx.channel(), evt);
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.debug("[channelActive] 设备登录服务连接通道激活：channel = {}.", ctx.channel());
        super.channelActive(ctx);
    }
}


