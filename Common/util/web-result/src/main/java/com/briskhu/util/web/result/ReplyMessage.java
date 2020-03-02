package com.briskhu.util.web.result;

import com.briskhu.util.web.constant.CommonConstant;

/**
 * 基础响应码<p/>
 * 通过继承该类实现定制化的返回码。
 *
 * @author Brisk Hu
 * created on 2019-11-26
 **/
public interface ReplyMessage {
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
    int getCode();

    /**
     * 获取字符串形式的 消息码
     *
     * @return
     */
    default String getCodeString() {
        return getCode() + CommonConstant.Str.EMPTY;
    }
}
