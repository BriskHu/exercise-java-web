package com.briskhu.exercize.springnetty.common.exception;


import com.briskhu.exercize.springnetty.common.constant.IMsgCode;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2019-12-10
 **/
public class NettyException extends BaseException {

    /* ---------------------------------------- fileds ---------------------------------------- */
    

    /* ---------------------------------------- methods ---------------------------------------- */
    public NettyException(String msg) {
        super(msg);
    }

    public NettyException(IMsgCode replyCode) {
        super(replyCode);
    }

    public NettyException(IMsgCode replyCode, String msg) {
        super(replyCode, msg);
    }


    /**
     * Fills in the execution stack trace. This method records within this
     * {@code Throwable} object information about the current state of
     * the stack frames for the current thread.
     *
     * <p>If the stack trace of this {@code Throwable} {@linkplain
     * Throwable#Throwable(String, Throwable, boolean, boolean) is not
     * writable}, calling this method has no effect.
     *
     * @return a reference to this {@code Throwable} instance.
     * @see Throwable#printStackTrace()
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
//        return super.fillInStackTrace();
        return this;
    }
}


