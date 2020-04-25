package com.briskhu.exercize.springnetty.common.constant;

/**
 * Redis键信息<p/>
 *
 * @author Brisk Hu
 * created on 2019-12-23
 **/
public interface RedisKey {

    String COMMON_PREFIX = "EarthBeat:";

    /**
     * hash结构的域字段
     */
    String F_IP = "ip";
    String F_TOKEN = "token";


    /**
     * 设备相关的redis键
     */
    interface Device {
        /**
         * 设备相关的域字段
         */
        String F_DN = "dn";
        String F_PIN = "pin";
        String F_MAC = "mac";
        String F_LOCATION = "location";
        String F_SESSION_KEY = "sessionKey";
        String F_TOKEN = RedisKey.F_TOKEN;
        String F_CIPHER = "cipher";
        String F_IP = RedisKey.F_IP;


        /**
         * 设备鉴权信息 —— hash
         * key：$+{dn}
         * field-value: ip  deviceIp(String)
         * field-value: token   deviceToken(String)
         *
         */
        String D_AUTH = COMMON_PREFIX + "d:auth:";

        /**
         * 在线设备集 —— set
         *
         */
        String D_ONLINE = COMMON_PREFIX + "d:online";

        /**
         * 设备基础信息 —— hash
         * key：$+{dn}
         * field-value: pin  {pin}(String)
         * field-value: mac  {mac}(String)
         * field-value: location  {location}(String)
         *
         */
        String D_BASE = COMMON_PREFIX + "d:base:";

        /**
         * 设备会话信息 —— hash
         * key：$+{dn}
         * field-value: token  {deviceConnectToken}(String)
         * field-value: sessionKey  {sessionKey}(String)
         *
         */
        String D_SESSION = COMMON_PREFIX + "d:session:";

    }
}
