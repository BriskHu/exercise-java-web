package com.briskhu.exercize.springnetty.common.constant;

/**
 * 消息码<p/>
 * 目前的编码分类规则如下：
 *      0 处理成功
 *      1开头 平台通用基础消息码
 *      3开头 App服务组件请求应答消息码
 *      5开头 设备连接服务组件请求应答消息码
 *      7开头 设备与连接服务组件之间的通信消息码
 * @author Brisk Hu
 * created on 2019-12-10
 **/
public interface MsgCode {


    enum Netty implements IMsgCode{
        SUCCESS(0, "netty操作成功"),
        UNKNOWN_ERR(999999, "发生未知错误"),
        CONN_SUCCESS(100000, "netty连接成功"),
        CONN_FAIL(100001, "netty连接失败"),
        ;

        int code;
        String msg;

        Netty(int code, String msg){
            this.code = code;
            this.msg = msg;
        }

        /**
         * @return
         */
        @Override
        public String getMsg() {
            return this.msg;
        }

        /**
         * @return
         */
        @Override
        public int getReplyCode() {
            return this.code;
        }
    }

    enum Device implements IMsgCode{
        APP_SERVER_CONNECT(700100, "App服务组件连接"),
        ;

        int code;
        String msg;

        Device(int code, String msg){
            this.code = code;
            this.msg = msg;
        }

        /**
         * @return
         */
        @Override
        public String getMsg() {
            return null;
        }

        /**
         * @return
         */
        @Override
        public int getReplyCode() {
            return 0;
        }
    }

}
