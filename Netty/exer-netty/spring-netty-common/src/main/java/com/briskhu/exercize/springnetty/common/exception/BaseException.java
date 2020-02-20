package com.briskhu.exercize.springnetty.common.exception;

import com.briskhu.exercize.springnetty.common.constant.IMsgCode;
import com.briskhu.exercize.springnetty.common.constant.MsgCode;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2019-12-10
 **/
@Data
public abstract class BaseException extends RuntimeException{
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseException.class);

    /* ---------------------------------------- fileds ---------------------------------------- */
    public IMsgCode msgCode;


    /* ---------------------------------------- methods ---------------------------------------- */
    public BaseException(){ }

    protected BaseException(String msg){
        super(msg);
        msgCode = MsgCode.Common.UNKNOWN_ERR;
    }

    protected BaseException(IMsgCode msgCode){
        super(msgCode.getMsg());
        this.msgCode = msgCode;
    }

    protected BaseException(IMsgCode msgCode, String msg){
        super(msg);
        this.msgCode = msgCode;
    }


    @Override
    public String getMessage() {
        return StringUtils.isEmpty(super.getMessage()) ? this.msgCode.getMsg() : super.getMessage();
    }
}
