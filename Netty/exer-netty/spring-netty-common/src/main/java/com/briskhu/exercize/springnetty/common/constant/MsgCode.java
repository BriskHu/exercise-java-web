package com.briskhu.exercize.springnetty.common.constant;

/**
 * 消息码<p/>
 * 目前的编码分类规则如下：<br/>
 * <pre>
 *  1. 除了成功的消息码是一位数字“0”外，其他的消息码均是6位的数字码；
 *  2. 消息码“0”表示操作处理成功；
 *  3. 消息码第一位代表消息码的大类，取值含义如下：
 *      1开头 平台通用基础消息码；
 *      3开头 App服务组件请求应答消息码；
 *      5开头 设备连接服务组件请求应答消息码；
 *      7开头 设备与连接服务组件之间的通信消息码；
 *  4. 消息码的其他各位代表的含义如下：
 | 第一位 | 第二、三位 | 第四位 | 第五、六位 | 用途                 |
 | ------ | --------- | ------ | --------- | -------------------- |
 | 1      | 00        | 1      | xx        | 参数校验不通过        |
 | 1      | 00        | 2      | xx        | 设备基础信息消息码        |
 | 3      | 00        | 1      | xx        | 参数校验不通过        |
 | 5      | 00        | 1      | xx        | 参数校验不通过        |
 | 7      | 00        | 1      | xx        | 设备与连接组件之间的通信消息码    |
 | 7      | 00        | 2      | xx        | App服务组件与连接组件之间的通信消息码    |
 * </pre>
 *
 * @author Brisk Hu
 * created on 2019-12-10
 **/
public interface MsgCode {

    /**
     * 平台通用基础消息码的第一位
     */
    int PLATFORM_BASE = 1;
    /**
     * App服务组件请求应答消息码的第一位
     */
    int APP_SERVER = 3;
    /**
     * 设备连接服务组件请求应答消息码的第一位
     */
    int CONNECT_SERVER = 5;
    /**
     * 设备与连接服务组件之间的通信消息码的第一位
     */
    int DEVICE_CONNECTOR = 7;


    enum Common implements IMsgCode {
        // TODO-note 目前整个平台成功的消息码共用 Common.SUCCESS。
        // 后期若有模块需要新的操作成功的消息码，则在该模块覆盖掉公共的消息即可。
        SUCCESS(0, "操作成功"),
        PARAM_VALUE_INVALID(PLATFORM_BASE + 00100, "参数值非法"),
        PARAM_FORM_INVALID(PLATFORM_BASE + 00101, "参数格式非法"),
        PARAM_NULL(PLATFORM_BASE + 00101, "必传参数为空"),


        UNKNOWN_ERR(999999, "发生未知错误"),

        // “;”不可少
        ;


        int code;
        String msg;

        Common(int code, String msg) {
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

    /**
     * 设备相关的消息码
     */
    enum Device implements IMsgCode {
        // 设备基础信息消息码
        DN_ERR(PLATFORM_BASE + 00200, "设备dn值错误"),
        PIN_ERR(PLATFORM_BASE + 00201, "设备pin值错误"),
        MAC_ERR(PLATFORM_BASE + 00202, "设备mac值错误"),

        // “;”不可少
        ;

        int code;
        String msg;

        Device(int code, String msg) {
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



    /**
     * netty通信相关的消息码
     */
    enum Netty implements IMsgCode {
        // 设备与连接组件之间的通信消息码
        DEVICE_LOGIN_TOSERVER(DEVICE_CONNECTOR + 00101, "设备登录时发送的消息码"),
        DEVICE_LOGIN_TODEVICE(DEVICE_CONNECTOR + 00102, "设备登录后服务器的返回码"),

        // App服务组件与连接组件之间的通信消息码
        APP_SERVER_CONNECT(DEVICE_CONNECTOR + 00200, "App服务组件连接"),


        CONN_SUCCESS(100000, "netty连接成功"),
        CONN_FAIL(100001, "netty连接失败"),

        // “;”不可少
        ;

        int code;
        String msg;

        Netty(int code, String msg) {
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


}
