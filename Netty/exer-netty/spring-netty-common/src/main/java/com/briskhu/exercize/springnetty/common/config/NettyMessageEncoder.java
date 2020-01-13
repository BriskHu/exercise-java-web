package com.briskhu.exercize.springnetty.common.config;

import com.alibaba.fastjson.JSON;
import com.briskhu.exercize.springnetty.common.constant.BusinessConstant;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Netty消息编码<p/>
 *
 * @author Brisk Hu
 * created on 2019-12-08
 **/
@ChannelHandler.Sharable
public class NettyMessageEncoder extends MessageToMessageEncoder<Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyMessageEncoder.class);

    /* ---------------------------------------- fileds ---------------------------------------- */
    private Charset charset;

    public NettyMessageEncoder() {
        this.charset = Charset.forName("UTF-8");
    }

    public NettyMessageEncoder(Charset charset) {
        if (charset == null){
            throw new NullPointerException("charset is null");
        }
        this.charset = charset;
    }

    /* ---------------------------------------- methods ---------------------------------------- */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, List<Object> outList) throws Exception {
        if (msg != null) {
            String msgStr = JSON.toJSONString(msg);
            msgStr += BusinessConstant.MessageConstant.NETTY_MSG_TAIL;
            outList.add(Unpooled.copiedBuffer(msgStr, this.charset));
        }

    }

}


