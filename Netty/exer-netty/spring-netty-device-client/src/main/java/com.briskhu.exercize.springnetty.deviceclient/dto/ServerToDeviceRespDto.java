package com.briskhu.exercize.springnetty.deviceclient.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * 服务器发给设备的Dto<p/>
 *
 * @author Brisk Hu
 * created on 2020-01-08
 **/
@Data
public class ServerToDeviceRespDto implements Serializable {
    private static final long serialVersionUID = -1139764282173904727L;

    private String code;

    private String data;

    public Object toTransDto(){
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}