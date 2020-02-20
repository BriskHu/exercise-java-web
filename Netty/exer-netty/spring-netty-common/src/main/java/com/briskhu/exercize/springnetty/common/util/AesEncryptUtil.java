package com.briskhu.exercize.springnetty.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AES加解密工具 <p/>
 *
 * @author Brisk Hu
 * created on 2020-02-05
 **/
public class AesEncryptUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AesEncryptUtil.class);

    /* ---------------------------------------- fileds ---------------------------------------- */


    /* ---------------------------------------- methods ---------------------------------------- */

    /**
     * 获取设备登录会话秘钥
     * @param text
     * @return
     */
    public static String getSessionKey(String text){
        return DigestUtils.md5Hex(text.getBytes()).substring(0, 16);
    }


}


