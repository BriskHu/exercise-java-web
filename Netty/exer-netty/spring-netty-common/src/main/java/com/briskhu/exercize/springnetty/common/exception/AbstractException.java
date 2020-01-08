package com.briskhu.exercize.springnetty.common.exception;

import com.briskhu.exercize.springnetty.common.constant.IMsgCode;
import com.briskhu.exercize.springnetty.common.constant.MsgCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2019-12-10
 **/
public abstract class AbstractException extends RuntimeException{
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractException.class);

    /* ---------------------------------------- fileds ---------------------------------------- */
    protected IMsgCode replyCode;


    /* ---------------------------------------- methods ---------------------------------------- */
    public AbstractException(){ }

    protected AbstractException(String msg){
        super(msg);
        replyCode = MsgCode.Netty.UNKNOWN_ERR;
    }

    protected AbstractException(IMsgCode replyCode){
        super(replyCode.getMsg());
        this.replyCode = replyCode;
    }

    protected AbstractException(IMsgCode replyCode, String msg){
        super(msg);
        this.replyCode = replyCode;
    }

}
