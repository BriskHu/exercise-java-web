package com.briskhu.exercize.springnetty.common.dto.resp.server;

import com.briskhu.exercize.springnetty.common.constant.IMsgCode;
import com.briskhu.exercize.springnetty.common.constant.MsgCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 服务器发送给设备的返回体中的data字段的Dto<p/>
 *
 * @author Brisk Hu
 * created on 2020-01-15
 **/
@Data
public class ServerToDeviceDataDto implements Serializable {
    private static final long serialVersionUID = -5302223687313804460L;

    private int resultCode;

    private String dn;

    public ServerToDeviceDataDto() {
        this.resultCode = MsgCode.Common.SUCCESS.getReplyCode();
    }

    public ServerToDeviceDataDto(int resultCode, String dn) {
        this.resultCode = resultCode;
        this.dn = dn;
    }

    public ServerToDeviceDataDto(IMsgCode msgCode, String dn) {
        this.resultCode = msgCode.getReplyCode();
        this.dn = dn;
    }


}