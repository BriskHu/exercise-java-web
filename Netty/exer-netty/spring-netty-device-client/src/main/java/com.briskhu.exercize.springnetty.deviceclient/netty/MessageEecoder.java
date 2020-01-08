package com.briskhu.exercize.springnetty.deviceclient.netty;

import com.alibaba.fastjson.JSON;
import com.briskhu.exercize.springnetty.deviceclient.constant.BusinessConstant;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.List;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2020-01-08
 **/
public class MessageEecoder extends MessageToMessageEncoder<Serializable> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageEecoder.class);

    private final Charset charset;

    public MessageEecoder() {
        this(Charset.forName("UTF-8"));
    }

    public MessageEecoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Serializable msg, List<Object> out) throws Exception {
        if (msg != null) {
            String msgStr = JSON.toJSONString(msg);
            msgStr = BusinessConstant.Netty.NETTY_MSG_HEADER + msgStr + BusinessConstant.Netty.NETTY_MSG_TAIL;
            out.add(Unpooled.copiedBuffer(msgStr, this.charset));
        }
    }
}
