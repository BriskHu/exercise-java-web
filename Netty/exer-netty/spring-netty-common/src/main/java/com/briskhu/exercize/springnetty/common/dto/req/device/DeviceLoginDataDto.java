package com.briskhu.exercize.springnetty.common.dto.req.device;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备登录请求体中data字段的Dto<p/>
 *
 * @author Brisk Hu
 * created on 2020-01-15
 **/
@Data
public class DeviceLoginDataDto implements Serializable {
    private static final long serialVersionUID = 6485950460179302579L;

    private String dn;

    private String pin;

    private String mac;

    private String ip;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
