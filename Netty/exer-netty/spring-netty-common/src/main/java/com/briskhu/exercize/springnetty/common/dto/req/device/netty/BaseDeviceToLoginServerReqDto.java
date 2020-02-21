package com.briskhu.exercize.springnetty.common.dto.req.device.netty;

import lombok.Data;

import java.io.Serializable;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2019-12-18
 **/
@Data
public class BaseDeviceToLoginServerReqDto implements Serializable {
    private static final long serialVersionUID = -8444890255844232366L;
    /**
     * 通信数据类型编码
     */
    private String code;

    private String token;

    /**
     * 通信数据
     */
    private String data;
}