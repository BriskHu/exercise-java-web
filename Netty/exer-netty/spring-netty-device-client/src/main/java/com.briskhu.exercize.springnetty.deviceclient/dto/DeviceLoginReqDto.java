package com.briskhu.exercize.springnetty.deviceclient.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备登录请求体<p/>
 *
 * @author Brisk Hu
 * created on 2020-01-08
 **/
@Data
public class DeviceLoginReqDto implements Serializable {
    private static final long serialVersionUID = 2366020697643723726L;

    private String code;

    /**
     * 访问令牌
     */
    private String token;

    private String data;

    private String dn;

    public Object toTransDto(){
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
