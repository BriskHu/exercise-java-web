package com.briskhu.exercize.springnetty.common.dto.resp.server;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * 服务器发给设备的Dto<p/>
 *
 * @author Brisk Hu
 * created on 2020-01-08
 **/
@Data
public class ServerToDeviceRespDto implements Serializable {
    private static final long serialVersionUID = -6743378200066935489L;

    /**
     * 通信数据类型编码
     */
    private String code;

    private String dn;

    /**
     * 通信数据
     */
    private String data;

    public ServerToDeviceRespDto() {
    }

    public ServerToDeviceRespDto(String code, String dn) {
        this.code = code;
        this.dn = dn;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public Object toTransDto() {
        return this;
    }
}
