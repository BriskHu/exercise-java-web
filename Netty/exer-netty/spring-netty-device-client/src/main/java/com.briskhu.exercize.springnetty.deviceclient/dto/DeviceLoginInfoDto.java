package com.briskhu.exercize.springnetty.deviceclient.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2020-01-08
 **/
@Data
public class DeviceLoginInfoDto implements Serializable {
    private static final long serialVersionUID = -1502651118843534491L;

    private String dn;

    private String mac;

    public Object toTransDto(){
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}