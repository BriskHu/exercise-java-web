package com.briskhu.exercize.springnetty.common.Dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2019-12-14
 **/
@Data
public class BaseDeviceToLoginServerDto implements Serializable {
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