package com.briskhu.exercize.springnetty.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备信息表 t_device_info <p/>
 *
 * @author Brisk Hu
 * created on 2020-02-22
 **/
@Data
@TableName(value = "t_device_info")
public class DeviceInfo implements Serializable {
    private static final long serialVersionUID = -5397354271303004163L;

    /**
     * 设备别名
     */
    private String alias;

    /**
     * 设备编码
     */
    private String dn;

    /**
     * 设备pin码
     */
    private String pin;

    /**
     * 设备mac地址
     */
    private String mac;

    /**
     * 设备位置
     * 值的格式为：“(经度,纬度)”。注意是英文括号、逗号。
     */
    private String location;

}