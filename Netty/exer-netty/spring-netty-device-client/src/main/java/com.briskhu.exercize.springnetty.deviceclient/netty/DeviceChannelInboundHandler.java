package com.briskhu.exercize.springnetty.deviceclient.netty;

import com.alibaba.fastjson.JSON;
import com.briskhu.exercize.springnetty.deviceclient.dto.ServerToDeviceRespDto;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理设备接收到信息<p/>
 *
 * @author Brisk Hu
 * created on 2020-01-08
 **/
@ChannelHandler.Sharable
public class DeviceChannelInboundHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceChannelInboundHandler.class);



    /* ---------------------------------------- fileds ---------------------------------------- */


    /* ---------------------------------------- methods ---------------------------------------- */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        LOGGER.info("[channelRead0] 设备收到服务器消息：response = {}，context = {}。", msg, channelHandlerContext);

        // 处理String类型的返回消息
        if (msg instanceof String ){
            ServerToDeviceRespDto response = JSON.parseObject((String)msg, ServerToDeviceRespDto.class);

            // 返回值为服务器返回Dto，则进一步处理具体业务
            if (response != null){
                String code = response.getCode();

                if (code.equals("700102")){
                    String respData = response.getData();

                }
            }
        }
        // 非String类型消息不处理
        else {
            LOGGER.warn("[channelRead0] 未知的消息类型。");
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("[exceptionCaught] {} exceptionCaught:", ctx.channel(), cause);
        ctx.channel().close();
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        LOGGER.info("[channelUnregistered] {} channelUnregistered", ctx.channel());
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        LOGGER.info("[channelRegistered] {} channelRegistered", ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        LOGGER.info("[userEventTriggered] context = {}, event = {}.", ctx, evt);

        // 心跳处理
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                LOGGER.info("{} READER_IDLE 读超时", ctx.channel());
            } else if (event.state() == IdleState.WRITER_IDLE) {
                LOGGER.info("{} WRITER_IDLE 写超时", ctx.channel());
            } else if (event.state() == IdleState.ALL_IDLE) {
                LOGGER.info("{} ALL_IDLE 总超时", ctx.channel());
                ctx.disconnect();
            }
        } else {
            LOGGER.warn("[userEventTriggered] 其他事件，交给父类处理。");
            super.userEventTriggered(ctx, evt);
        }
    }

}


