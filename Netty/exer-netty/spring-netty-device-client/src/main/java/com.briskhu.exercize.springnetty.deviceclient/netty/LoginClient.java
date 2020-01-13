package com.briskhu.exercize.springnetty.deviceclient.netty;

import com.briskhu.exercize.springnetty.deviceclient.constant.BusinessConstant;
import com.briskhu.exercize.springnetty.deviceclient.util.PropertiesUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 登录客户端<p/>
 *
 * @author Brisk Hu
 * created on 2020-01-08
 **/
public class LoginClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginClient.class);

    /* ---------------------------------------- fileds ---------------------------------------- */
    private Integer readerIdleTimeSeconds = Integer.parseInt(PropertiesUtil.getFileProperties().getProperty("readerIdleTimeSeconds"));
    private Integer writerIdleTimeSeconds = Integer.parseInt(PropertiesUtil.getFileProperties().getProperty("writerIdleTimeSeconds"));
    private Integer allIdleTimeSeconds = Integer.parseInt(PropertiesUtil.getFileProperties().getProperty("allIdleTimeSeconds"));


    /* ---------------------------------------- methods ---------------------------------------- */

    /**
     * @param socketAddr ip:port形式的字符串
     * @return
     */
    public Channel connect(String socketAddr) {
        if (StringUtils.isNotBlank(socketAddr) && socketAddr.indexOf(":") != -1) {
            String[] ipPort = socketAddr.split(":");
            if (ipPort.length == 2) {
                return connect(ipPort[0], Integer.valueOf(ipPort[1]));
            }
        }

        return null;
    }

    /**
     * 连接到指定服务器
     *
     * @param ip
     * @param port
     * @return
     */
    public Channel connect(String ip, int port) {
        LOGGER.info("[connect] 连接到登录服务器：ip = {}, port = {}.", ip, port);

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap client = new Bootstrap();
        client.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //接收
                        pipeline.addLast(new LineBasedFrameDecoder(BusinessConstant.Netty.NETTY_MAX_FRAME_SIZE));
                        pipeline.addLast(new MessageDecoder());

                        // 发送
                        pipeline.addLast(new MessageEecoder());
                        pipeline.addLast("timeout", new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));
                        pipeline.addLast(new DeviceChannelInboundHandler());
                    }
                });

        Channel channel = null;
        try {
            channel = client.connect(ip, port).sync().channel();
            LOGGER.info("[connect] 连接到 {}:{} 成功.", ip, port);
            return channel;
        } catch (InterruptedException e) {
            LOGGER.info("[connect] 连接到 {}:{} 失败.", ip, port);
            e.printStackTrace();
        }

        return channel;
    }

}


