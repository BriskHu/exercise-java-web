package com.briskhu.util.web.result;

import com.alibaba.fastjson.JSON;
import lombok.Data;


/**
 * 通用Web响应结果<p/>
 *
 * @author Brisk Hu
 * created on 2019-11-26
 **/
@Data
public class BasicResult<T> {

    /* ---------------------------------------- fileds ---------------------------------------- */
    private int code;
    private String msg;
    private T data;
    private boolean success;


    /* ---------------------------------------- methods ---------------------------------------- */
    public BasicResult() {
    }

    public BasicResult(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public BasicResult(ReplyMessage replyMessage) {
        this.code = replyMessage.getCode();
        this.msg = replyMessage.getMsg();
    }


    public static <T> BasicResult<T> ok() {
        BasicResult<T> result = new BasicResult<>(ReturnCode.Basic.SUCCESS);
        result.setSuccess(true);
        return result;
    }

    public static <T> BasicResult<T> ok(String message) {
        BasicResult<T> result = new BasicResult<T>(ReturnCode.Basic.SUCCESS.getCode(), message);
        result.setSuccess(true);
        return result;
    }

    public static <T> BasicResult<T> ok(ReplyMessage replyMessage) {
        BasicResult<T> result = new BasicResult<>(replyMessage);
        result.setSuccess(true);
        return result;
    }

    public static <T> BasicResult<T> fail() {
        BasicResult<T> result = new BasicResult<>(ReturnCode.Basic.FAIL);
        result.setSuccess(false);
        return result;
    }

    public static <T> BasicResult<T> fail(String message) {
        BasicResult<T> result = new BasicResult<T>(ReturnCode.Basic.FAIL.getCode(), message);
        result.setSuccess(false);
        return result;
    }

    public static <T> BasicResult<T> fail(ReplyMessage replyMessage) {
        BasicResult<T> result = new BasicResult<>(replyMessage);
        result.setSuccess(false);
        return result;
    }

    public static <T> BasicResult<T> failAsRequestArgs(String message) {
        BasicResult<T> result = new BasicResult<T>(ReturnCode.Basic.PARAM_ILLEGAL.getCode(), message);
        result.setSuccess(false);
        return result;
    }


    public BasicResult<T> addData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}


