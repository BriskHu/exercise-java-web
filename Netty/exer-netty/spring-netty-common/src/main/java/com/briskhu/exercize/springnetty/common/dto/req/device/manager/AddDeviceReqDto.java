package com.briskhu.exercize.springnetty.common.dto.req.device.manager;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2020-02-20
 **/
@Data
public class AddDeviceReqDto implements Serializable {
    private static final long serialVersionUID = -5124725219609883638L;

    private String alias;

    @NotBlank(message = "设备编码不能为空")
    @Pattern(regexp = "\\d{20,20}", message = "设备编码长度非法")
    private String dn;

    @NotBlank
    @Pattern(regexp = "\\s{32,32}", message = "设备pin码长度不合法")
    private String pin;

    @Pattern(regexp = "[0-9a-fA-F]{12,12}")
    private String mac;

    private Coordinate location;



}