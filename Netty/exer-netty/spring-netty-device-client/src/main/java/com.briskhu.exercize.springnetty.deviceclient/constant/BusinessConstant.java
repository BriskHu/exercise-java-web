package com.briskhu.exercize.springnetty.deviceclient.constant;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2020-01-08
 **/
public interface BusinessConstant {

    /**
     * netty常量
     */
    interface Netty {
        int NETTY_MAX_FRAME_SIZE = Integer.MAX_VALUE;
        /**
         * netty消息起始标志
         */
        String NETTY_MSG_HEADER = "EBDM";
        /**
         * netty消息结束标志
         */
        String NETTY_MSG_TAIL = "\r\n";    }
}
