package com.briskhu.exercize.springnetty.common.constant;

/**
 * 消息码<p/>
 *
 * @author Brisk Hu
 * created on 2019-12-10
 **/
public interface IMsgCode {
    /**
     * 获取应答消息
     *
     * @return
     */
    String getMsg ();

    /**
     * 获取应答码
     *
     * @return
     */
    int getReplyCode();

    /**
     * 获取字符串形式的 消息码
     *
     * @return
     */
    default String getCodeString() {
        return getReplyCode() + CommonConstant.Str.EMPTY;
    }
}
