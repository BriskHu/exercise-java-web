package com.briskhu.exercize.springnetty.device.manager.service;

import com.briskhu.exercize.springnetty.common.dto.req.device.manager.AddDeviceReqDto;
import com.briskhu.exercize.springnetty.device.manager.mapper.DeviceInfoMapper;
import com.briskhu.util.web.result.BasicResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2020-02-20
 **/
@Service
public class DeviceManagerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceManagerService.class);

    /* ---------------------------------------- fileds ---------------------------------------- */
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;


    /* ---------------------------------------- methods ---------------------------------------- */
    /**
     * 添加一个设备
     *
     * @param addDeviceReqDto
     * @return
     */
    public BasicResult addDevice(AddDeviceReqDto addDeviceReqDto) {
        LOGGER.info("[addDevice] start...");
        String dn = addDeviceReqDto.getDn();
        if (deviceInfoMapper.getDeviceByDn(dn) != null){
            LOGGER.error("[addDevice] 设备已经存在.\n");
            return BasicResult.fail("设备已经存在");
        }





        return null;
    }


}


