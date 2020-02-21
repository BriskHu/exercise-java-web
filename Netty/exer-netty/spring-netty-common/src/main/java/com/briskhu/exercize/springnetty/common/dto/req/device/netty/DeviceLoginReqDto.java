package com.briskhu.exercize.springnetty.common.dto.req.device.netty;

import lombok.Data;

/**
 * 设备登录请求Dto<p/>
 *
 * @author Brisk Hu
 * created on 2019-12-23
 **/
@Data
public class DeviceLoginReqDto extends BaseDeviceToLoginServerReqDto {
    private static final long serialVersionUID = -9499177398480109L;

    /**
     * 设备编码
     */
    private String dn;
}
