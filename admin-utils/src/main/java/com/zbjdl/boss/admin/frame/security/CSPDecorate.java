/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.frame.security;

import com.zbjdl.boss.admin.frame.utils.config.ConfigEnum;
import com.zbjdl.boss.admin.frame.utils.config.ConfigUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

/**
 * 类名称: CSPDecorate <br>
 * 类描述: CSP安全头装饰，设置HTTP头<br>
 * Adds the 'Content-Security-Policy' Header to the response.
 * Also see: http://content-security-policy.com/ & http://www.w3.org/TR/CSP/#directives
 *
 * @author: yufan.sheng
 * @since: 15/5/4 下午1:27
 * @version: 1.0.0
 */
public class CSPDecorate {

    private static final Logger logger = LoggerFactory.getLogger(CSPDecorate.class);

    private CSPDecorate() {
        // 组件类不提供任何构造方法
    }

    public static void addCSPHeader(HttpServletRequest request, HttpServletResponse response) {
        if (response == null) {
            logger.debug("No respond, don't set CSP Header.");
            return;
        }
        if (request.getProtocol().equalsIgnoreCase("https")) {
            try {
                setContentSecurityPolicy(response, ConfigUtils.getResourcesConfigMap(ConfigEnum.BOOS_CSP_CONFIG));
            } catch (ServletException e) {
                logger.warn("CSP Header setting error {}.", e);
            }
        }
    }

    private static void setContentSecurityPolicy(HttpServletResponse response, Map<String, String> params) throws ServletException {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            logger.debug("Adding CSP Header {} = {}", entry.getKey(), entry.getValue());
            response.addHeader(entry.getKey(), entry.getValue());
        }
    }
}