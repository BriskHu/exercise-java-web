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

    private Float longitude;

    private Float latitude;

    /**
     * 获取坐标字符串
     * @return 返回字符串的格式为：“(经度,纬度)”。注意是英文括号。
     */
    public String getCoordinateString(){
        return "(" + this.latitude + "," + this.longitude + ")";
    }

}