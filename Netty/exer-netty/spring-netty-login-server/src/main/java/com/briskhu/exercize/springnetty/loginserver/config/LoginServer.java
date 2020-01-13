package com.briskhu.exercize.springnetty.loginserver.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 登录服务器<p/>
 *
 * @author Brisk Hu
 * created on 2019-12-08
 **/
@Component
public class LoginServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServer.class);

    /* ---------------------------------------- fileds ---------------------------------------- */
    @Value("${loginServer.port}")
    private int loginServerPort;

    @Autowired
    @Qualifier("serverBootstrap")
    private ServerBootstrap serverBootstrap;

    private ChannelFuture loginServerChannelFuture;

    /* ---------------------------------------- methods ---------------------------------------- */
    @PostConstruct
    public void start() throws InterruptedException {
        LOGGER.info("[start] 启动LoginServer: port = {}.", loginServerPort);
        loginServerChannelFuture = serverBootstrap.bind(loginServerPort).sync();
    }

    @PreDestroy
    public void stop() throws InterruptedException {
        LOGGER.info("[stop] 停止LoginServer.");
        loginServerChannelFuture.channel().closeFuture().sync();
    }
}


