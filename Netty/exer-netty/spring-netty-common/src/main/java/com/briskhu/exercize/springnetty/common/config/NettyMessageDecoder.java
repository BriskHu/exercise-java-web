package com.briskhu.exercize.springnetty.common.config;

import com.briskhu.exercize.springnetty.common.constant.NettyConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Netty消息解码<p/>
 *
 * @author Brisk Hu
 * created on 2019-12-08
 **/
@ChannelHandler.Sharable
public class NettyMessageDecoder extends MessageToMessageDecoder<ByteBuf>  {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyMessageDecoder.class);

    /* ---------------------------------------- fileds ---------------------------------------- */
    private Charset charset;

    public NettyMessageDecoder() {
        this.charset = Charset.defaultCharset();
    }

    public NettyMessageDecoder(Charset charset) {
        if (charset == null){
            throw new NullPointerException("charset is null");
        }
        this.charset = charset;
    }

    /* ---------------------------------------- methods ---------------------------------------- */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf msgByteBuf, List<Object> outList) throws Exception {
        String msgStr = msgByteBuf.toString(this.charset);
        if (msgStr != null && msgStr.startsWith(NettyConstant.MessageConstant.NETTY_MSG_HEADER)){
            msgStr = msgStr.substring(NettyConstant.MessageConstant.NETTY_MSG_HEADER.length());
            outList.add(msgStr);
        }
    }

}


