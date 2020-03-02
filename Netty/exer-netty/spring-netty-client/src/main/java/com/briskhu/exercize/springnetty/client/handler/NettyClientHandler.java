package com.briskhu.exercize.springnetty.client.handler;

import com.briskhu.exercize.springnetty.common.util.ApplicationContextUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.server.ExportException;
import java.util.concurrent.Executor;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2019-12-26
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientHandler.class);

    /* ---------------------------------------- fileds ---------------------------------------- */


    /* ---------------------------------------- methods ---------------------------------------- */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        LOGGER.debug("[channelRegistered] channelHandlerContext = {}.", ctx);
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        LOGGER.debug("[channelUnregistered] channelHandlerContext = {}.", ctx);
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try{
            if (!(msg instanceof String)) {
                LOGGER.error("[channelRead] 消息体格式错误");
                return;
            }

            Executor executor = ApplicationContextUtil.getBean("nettyExecutor", Executor.class);
            LOGGER.debug("[channelRead] executor = {}.", executor);
            executor.execute(() -> {
                LOGGER.info("[channelRead] request data = {}.", msg);
            });
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.debug("[channelActive] channelHandlerContext = {}.", ctx);

//        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.debug("[channelInactive] channelHandlerContext = {}.", ctx);

        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        LOGGER.debug("[channelInactive] channelHandlerContext = {}, evt = {}.", ctx, evt);
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("[exceptionCaught] channelHandlerContext = {}, errMsg = {}.", ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }

}


