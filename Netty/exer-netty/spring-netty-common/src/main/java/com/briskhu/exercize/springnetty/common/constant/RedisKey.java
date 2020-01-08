package com.briskhu.exercize.springnetty.common.constant;

/**
 * Redis键信息<p/>
 *
 * @author Brisk Hu
 * created on 2019-12-23
 **/
public interface RedisKey {

    String COMMON_PREFIX = "EarthBeat:";

    String F_IP = "ip";
    String F_TOKEN = "token";


    /**
     * 设备相关的redis键
     */
    interface Device {
        /**
         * 设备鉴权信息-hash
         * key：$+{deviceSn}
         * field-value: ip  deviceIp(String)
         * field-value: token   deviceToken(String)
         *
         */
        String D_AUTH = COMMON_PREFIX + "d:auth:";

        /**
         * 在线设备集-set
         *
         */
        String D_ONLINE = COMMON_PREFIX + "d:online";
    }
}
