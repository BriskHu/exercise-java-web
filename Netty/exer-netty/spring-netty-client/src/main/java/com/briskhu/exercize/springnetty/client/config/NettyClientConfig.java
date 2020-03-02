package com.briskhu.exercize.springnetty.client.config;

import com.briskhu.exercize.springnetty.client.handler.NettyClientHandler;
import com.briskhu.exercize.springnetty.common.config.NettyMessageDecoder;
import com.briskhu.exercize.springnetty.common.constant.BusinessConstant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2019-12-26
 **/
public class NettyClientConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientConfig.class);

    /* ---------------------------------------- fileds ---------------------------------------- */
    @Value("${nettyClient.bossCount}")
    private int bossCount;

    @Value("${nettyClient.workerCount}")
    private int workerCount;

    @Value("${reader.idle.time.seconds}")
    private int readerIdleSeconds;

    @Value("${writer.idle.time.seconds}")
    private int writerIdleSeconds;

    @Value("${all.idle.time.seconds}")
    private int allIdleSeconds;


    /* ---------------------------------------- methods ---------------------------------------- */

    /**
     * Netty 客户端配置
     * @return
     */
    @Bean("clientBootstrap")
    public Bootstrap clientBootstrap(){
        Bootstrap clientBootstrap = new Bootstrap();
        clientBootstrap.group(clientGroup())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new LineBasedFrameDecoder(BusinessConstant.Netty.NETTY_MAX_FRAME_SIZE));
                        pipeline.addLast("decoder", new NettyMessageDecoder());
                        pipeline.addLast("encoder", new StringEncoder());
                        pipeline.addLast(new IdleStateHandler(50, 20, 0, TimeUnit.SECONDS));
                        pipeline.addLast(new NettyClientHandler());
                    }
                });

        return clientBootstrap;
    }

    @Bean(name = "clientGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup clientGroup(){
        return new NioEventLoopGroup(workerCount);
    }



}


