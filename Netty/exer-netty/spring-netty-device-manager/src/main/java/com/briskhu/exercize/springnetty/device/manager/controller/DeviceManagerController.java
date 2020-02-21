package com.briskhu.exercize.springnetty.device.manager.controller;

import com.briskhu.exercize.springnetty.common.dto.req.device.manager.AddDeviceReqDto;
import com.briskhu.exercize.springnetty.device.manager.service.DeviceManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2020-02-20
 **/
@RestController
public class DeviceManagerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceManagerController.class);

    /* ---------------------------------------- fileds ---------------------------------------- */
    @Autowired
    private DeviceManagerService deviceManagerService;


    /* ---------------------------------------- methods ---------------------------------------- */

    /**
     * 添加一个设备
     *
     * @param addDeviceReqDto
     * @return
     */
    @PostMapping("/addDevice")
    public String addDevice(@RequestBody @Valid AddDeviceReqDto addDeviceReqDto) {
        LOGGER.info("[addDevice] 入参：addDeviceReqDto = {}。", addDeviceReqDto);
        return deviceManagerService.addDevice(addDeviceReqDto);
    }


}


