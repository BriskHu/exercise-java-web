package com.briskhu.exercize.springnetty.deviceclient;

import com.briskhu.exercize.springnetty.deviceclient.dto.DeviceLoginInfoDto;
import com.briskhu.exercize.springnetty.deviceclient.dto.DeviceLoginReqDto;
import com.briskhu.exercize.springnetty.deviceclient.netty.LoginClient;
import com.briskhu.exercize.springnetty.deviceclient.util.PropertiesUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 设备客户端<p/>
 * 用来模拟设备与平台的通信
 *
 * @author Brisk Hu
 * created on 2020-01-08
 **/
public class DeviceClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceClient.class);

    /* ---------------------------------------- fileds ---------------------------------------- */
    private String loginHost = PropertiesUtil.getFileProperties().getProperty("loginHost");
    private String dn = PropertiesUtil.getFileProperties().getProperty("dn");
    private String pin = PropertiesUtil.getFileProperties().getProperty("pin");
    private String mac = PropertiesUtil.getFileProperties().getProperty("mac");


    /* ---------------------------------------- methods ---------------------------------------- */
    public static void main(String[] args){
        DeviceClient deviceClient = new DeviceClient();
        try {
            deviceClient.deviceLogin();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deviceLogin() throws InterruptedException {
        LoginClient loginClient = new LoginClient();
        Channel channel = loginClient.connect(loginHost);
        if (channel != null){
            DeviceLoginReqDto deviceLoginReqDto = initDeviceLoginReqDto();
            // TODO-Brisk 处理数据加密
            LOGGER.debug("[deviceLogin] 设备登录发送数据：deviceLoginReqDto = {}.", deviceLoginReqDto);
            channel.writeAndFlush(deviceLoginReqDto.toTransDto()).sync();
        }
    }

    private DeviceLoginReqDto initDeviceLoginReqDto(){
        DeviceLoginReqDto deviceLoginReqDto = new DeviceLoginReqDto();
        deviceLoginReqDto.setCode("1001");
        deviceLoginReqDto.setDn(dn);

        DeviceLoginInfoDto loginInfoDto = new DeviceLoginInfoDto();
        loginInfoDto.setDn(dn);
        loginInfoDto.setMac(mac);
        deviceLoginReqDto.setData(loginInfoDto.toString());

        return deviceLoginReqDto;
    }

}


