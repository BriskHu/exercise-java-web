package com.briskhu.exercize.springnetty.deviceclient.netty;

import com.briskhu.exercize.springnetty.deviceclient.constant.BusinessConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2020-01-08
 **/
public class MessageDecoder extends MessageToMessageDecoder<ByteBuf> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDecoder.class);

    private final Charset charset;

    public MessageDecoder() {
        this(Charset.forName("UTF-8"));
    }

    public MessageDecoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {
        String msg = byteBuf.toString(this.charset);

        if (msg != null && msg.startsWith(BusinessConstant.Netty.NETTY_MSG_HEADER)) {
            msg = msg.substring(BusinessConstant.Netty.NETTY_MSG_HEADER.length());
            out.add(msg);
        }
    }
}
