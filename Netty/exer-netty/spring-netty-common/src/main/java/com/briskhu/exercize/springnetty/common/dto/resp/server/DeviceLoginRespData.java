package com.briskhu.exercize.springnetty.common.dto.resp.server;

import lombok.Data;


/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2020-01-27
 **/
@Data
public class DeviceLoginRespData extends ServerToDeviceDataDto {
    private static final long serialVersionUID = 580754366726342094L;

    private String sessionKey;

    private String token;

    public Object toTransDto(){
        return this;
    }

}