/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.frame.utils.config;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: 基于柏涛封装的统一配置信息</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2018</p>
 * <p>Company: 八戒财云</p>
 *
 * @author yufan.sheng
 * @version 1.0
 */
public enum ConfigEnum {

    /**
     * 通知系统配置信息
     */
    BOOS_CSP_CONFIG("boss.security.csp", new HashMap<String, String>() {{
        put("X-XSS-Protection", "1; mode=block");
        put("Strict-Transport-Security", "max-age=63072000");
        put("X-Frame-Options", "SAMEORIGIN");
        put("X-Content-Type-Options", "nosniff");
        put("Content-Security-Policy", "default-src 'self' *.yunpal.net; frame-src *yunpal.net'self'; script-src *.yunpal.net 'unsafe-inline' 'unsafe-eval'; style-src *.yunpal.net  'unsafe-inline';");
    }});

    private static Map<String, ConfigEnum> valueMap = Maps.newHashMap();

    private String configKey;
    private Object defaultValue;

    static {
        for (ConfigEnum item : ConfigEnum.values()) {
            valueMap.put(item.configKey, item);
        }
    }

    ConfigEnum(String configKey, Object defaultValue) {
        this.configKey = configKey;
        this.defaultValue = defaultValue;
    }

    public String getConfigKey() {
        return configKey;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

}
