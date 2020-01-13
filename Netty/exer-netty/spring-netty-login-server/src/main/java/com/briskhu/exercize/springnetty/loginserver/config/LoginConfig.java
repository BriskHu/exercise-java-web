package com.briskhu.exercize.springnetty.loginserver.config;

import com.briskhu.exercize.springnetty.common.config.NettyMessageDecoder;
import com.briskhu.exercize.springnetty.common.config.NettyMessageEncoder;
import com.briskhu.exercize.springnetty.common.constant.BusinessConstant;
import com.briskhu.exercize.springnetty.loginserver.handler.LoginServerInboundHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 登录服务器配置<p/>
 *
 * @author Brisk Hu
 * created on 2019-12-08
 **/
@Configuration
public class LoginConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginConfig.class);

    /* ---------------------------------------- fileds ---------------------------------------- */
    @Value("${loginServer.bossCount}")
    private int bossCount;

    @Value("${loginServer.workerCount}")
    private int workerCount;

    @Value("${reader.idle.time.seconds}")
    private int readerIdleSeconds;

    @Value("${writer.idle.time.seconds}")
    private int writerIdleSeconds;

    @Value("${all.idle.time.seconds}")
    private int allIdleSeconds;

    @Autowired
    private LoginServerInboundHandler loginServerInboundHandler;


    /* ---------------------------------------- methods ---------------------------------------- */

    /**
     * Netty服务端配置
     *
     * @return
     */
    @Bean("serverBootstrap")
    public ServerBootstrap serverBootstrap() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
                .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel socketChannel) {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("logging", new LoggingHandler(LogLevel.DEBUG));
                        // 接收消息
                        pipeline.addLast("lineBasedFrameDecoder", new LineBasedFrameDecoder(BusinessConstant.Netty.NETTY_MAX_FRAME_SIZE));
                        pipeline.addLast("decoder", new NettyMessageDecoder());
                        // 发送消息
                        pipeline.addLast("encoder", new NettyMessageEncoder());
                        // server端检测到超时，会调用ConnectorChannelInboundHandler的userEventTriggered方法
                        pipeline.addLast("timeout", new IdleStateHandler(readerIdleSeconds, writerIdleSeconds, allIdleSeconds));
                        pipeline.addLast(loginServerInboundHandler);
                    }
                })

                .childOption(ChannelOption.SO_KEEPALIVE, true);

        return serverBootstrap;
    }


    /**
     * 客户端bossGroup和workerGroup共用同一个事件组。
     *
     * @return
     */
    @Bean(name = "clientGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup clientGroup() {
        return new NioEventLoopGroup(workerCount);
    }

    @Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(bossCount);
    }

    @Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(workerCount);
    }


}


