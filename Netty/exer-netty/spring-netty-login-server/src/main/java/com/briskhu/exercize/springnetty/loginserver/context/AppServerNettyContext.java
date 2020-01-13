package com.briskhu.exercize.springnetty.loginserver.context;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * App服务组件的netty连接上下文<p/>
 *
 * @author Brisk Hu
 * created on 2019-12-25
 **/
public class AppServerNettyContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppServerNettyContext.class);

    /* ---------------------------------------- fileds ---------------------------------------- */
    /**
     * 与登录组件连接的channel
     */
    private static volatile Channel connectChannel;


    /* ---------------------------------------- methods ---------------------------------------- */
    public static void addConnectChannel (Channel channel){
        AppServerNettyContext.connectChannel = channel;
    }

    public static Channel getConnectChannel(){
        if (connectChannel == null){
            return null;
        }
        if (connectChannel.isOpen()){
            return connectChannel;
        }
        removeConnectChannel();
        return null;
    }

    public static void removeConnectChannel(){
        connectChannel = null;
    }



}


