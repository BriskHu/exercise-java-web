package com.briskhu.exercize.springnetty.common.exception;

import com.briskhu.exercize.springnetty.common.constant.IMsgCode;

/**
 * 业务异常<p/>
 *
 * @author Brisk Hu
 * created on 2020-01-15
 **/
public class ApiException extends BaseException {

    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(IMsgCode replyCode) {
        super(replyCode);
    }

    public ApiException(IMsgCode replyCode, String msg) {
        super(replyCode, msg);
    }



}
