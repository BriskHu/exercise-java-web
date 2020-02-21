package com.briskhu.exercize.springnetty.common.dto.req.device.manager;

import lombok.Data;

import java.io.Serializable;

/**
 * 经纬度<p/>
 *
 * @author Brisk Hu
 * created on 2020-02-21
 **/
@Data
public class Coordinate implements Serializable {
    private static final long serialVersionUID = 5987178205937884540L;

    private Long longitude;

    private Long latitude;

}