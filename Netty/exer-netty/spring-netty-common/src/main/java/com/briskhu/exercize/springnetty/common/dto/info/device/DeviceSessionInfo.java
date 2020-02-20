package com.briskhu.exercize.springnetty.common.dto.info.device;

import lombok.Data;

import java.io.Serializable;

/**
 * 设备登录 session <p/>
 *
 * @author Brisk Hu
 * created on 2020-02-02
 **/
@Data
public class DeviceSessionInfo implements Serializable {
    private static final long serialVersionUID = -1227327554957092247L;

    private String dn;

    /**
     * 会话加解密秘钥和偏移量
     * 16位字符。
     * 加解密秘钥和偏移量都使用这个值。
     */
    private String sessionKey;

    /**
     * 访问授权token
     */
    private String token;

    private String cipher;

    private String tcpHost;

    public DeviceSessionInfo(String dn, String sessionKey, String token, String cipher, String tcpHost) {
        this.dn = dn;
        this.sessionKey = sessionKey;
        this.token = token;
        this.cipher = cipher;
        this.tcpHost = tcpHost;
    }
}