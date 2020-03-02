package com.briskhu.util.web.result;

/**
 * 基本的返回码<p/>
 *
 * @author Brisk Hu
 * created on 2019-11-26
 **/
public interface ReturnCode {

    /**
     * 基础的响应码
     */
    enum Basic implements ReplyMessage{
        SUCCESS(0, "请求成功"),
        FAIL(999999, "请求失败"),

        PARAM_ILLEGAL(100100, "参数不合法"),
        // Don't forget the semicolon。
        ;

        int code;
        String msg;

        Basic(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String getMsg() {
            return this.msg;
        }

        @Override
        public int getCode() {
            return this.code;
        }
    }



}