package com.briskhu.exercize.springnetty.common.util;

import com.briskhu.exercize.springnetty.common.constant.MsgCode;
import com.briskhu.exercize.springnetty.common.dto.resp.server.DeviceLoginRespData;
import com.briskhu.exercize.springnetty.common.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.SettableListenableFuture;

/**
 * 校验工具类<p/>
 *
 * @author Brisk Hu
 * created on 2020-02-06
 **/
public class ValidateUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateUtil.class);

    /* ---------------------------------------- fileds ---------------------------------------- */


    /* ---------------------------------------- methods ---------------------------------------- */

    /**
     * 检查对象是否为空
     *
     * @param obj
     * @param msg 对象为空时，提示信息。
     */
    public static void notEmptyForFuture(Object obj, String msg){
        if (StringUtils.isEmpty(obj)){
            LOGGER.error(msg);
            throw new ApiException(MsgCode.Common.PARAM_NULL);
        }
    }


}


