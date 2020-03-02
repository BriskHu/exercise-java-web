package com.briskhu.exercize.springnetty.device.manager.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.briskhu.exercize.springnetty.common.entity.DeviceInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 表  的Mapper <p/>
 *
 * @author Brisk Hu
 * created on 2020-02-22
 **/
@Mapper
public interface DeviceInfoMapper extends BaseMapper<DeviceInfo> {

    /**
     * 通过dn查找设备
     * @param dn
     * @return
     */
    default DeviceInfo getDeviceByDn(String dn){
        return this.selectOne(new QueryWrapper<DeviceInfo>().eq("dn", dn));
    }

}
