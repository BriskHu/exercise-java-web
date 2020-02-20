package com.briskhu.exercize.springnetty.common.constant;

/**
 * 业务常量<p/>
 *
 * @author Brisk Hu
 * created on 2019-12-18
 **/
public interface BusinessConstant {

    interface Netty{
        int NETTY_MAX_FRAME_SIZE = Integer.MAX_VALUE;
        int NETTY_TIMEOUT_MILLIS = 2000;  //ms
        int SUCCESS_RESULT_CODE = 0;
    }



    /**
     * netty消息常量<p/>
     *
     * @author Brisk Hu
     * created on 2019-12-08
     **/
    interface MessageConstant {
        /**
         * netty消息起始标志
         */
        String NETTY_MSG_HEADER = "EBDM";
        /**
         * netty消息结束标志
         */
        String NETTY_MSG_TAIL = "\r\n";

        byte[] NETTY_MSG_HEADER_BYTES = {'E', 'B', 'D', 'M'};
        byte[] NETTY_MSG_TAIL_BYTES = {'\r', '\n'};

        /**
         * netty消息包的最小长度s
         */
        int NETTY_MSG_MIN_SIZE = NETTY_MSG_HEADER_BYTES.length + NETTY_MSG_TAIL_BYTES.length;
    }

}
