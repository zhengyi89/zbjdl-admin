package com.zbjdl.boss.admin.frame.interceptor.helper;

import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.frame.utils.BossWebUtils;
import com.zbjdl.common.json.JSONUtils;
import com.zbjdl.common.utils.StringUtils;
import com.zbjdl.common.utils.ThreadContextUtils;
import com.zbjdl.common.utils.config.ConfigParam;
import com.zbjdl.common.utils.config.ConfigurationUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数拦截器辅助类<br>
 * 可指定jquery版本，可指定是否启用XSS过滤
 *
 * @author：feng
 * @since：2014年11月10日 下午5:25:19
 * @version:
 */
public class ParametersInterceptorHelper extends BaseInterceptorHelper {
	public static final String CONF_KEY_JQUERY_VERSION = "portal.boss.jquery.version";

	public static final String CONF_KEY_XSS_FILTER_ENABLE = "portal.boss.xss.filter.enable";

	// 目前默认版本为 1.8.3
	public static final String JQUERY_DEFAULT_VERSION = "1.8.3";

	private static ParametersInterceptorHelper instance;

	private ParametersInterceptorHelper() {

	}

	public static ParametersInterceptorHelper getInstance() {
		if (instance == null) {
			synchronized (ParametersInterceptorHelper.class) {
				if (instance == null) {
					instance = new ParametersInterceptorHelper();
				}
			}
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean before(HttpServletRequest request,
			HttpServletResponse response, Object... extParams) throws Exception {
		String resourcePath = getConfigValue(DataDictDTO.CONF_KEY_STATIC_RESOURCE_PATH);
		//TODO
		if (resourcePath != null && resourcePath.endsWith("/")) {
			resourcePath = StringUtils.substring(resourcePath, 0,
					resourcePath.length() - 1);
		}
		request.setAttribute("resourcePath", resourcePath);

		String employeePath = getConfigValue(DataDictDTO.CONF_KEY_ADMIN_BOSS_PATH);
		if (employeePath != null && employeePath.endsWith("/")) {
			employeePath = StringUtils.substring(employeePath, 0,
					employeePath.length() - 1);
		}
		request.setAttribute("employeePath", employeePath);

		String decoration = request.getParameter("decoration");
        //纯页面
        String purepage = request.getParameter("purepage");

		if (StringUtils.isBlank(decoration)) {
			if (BossWebUtils.isAjaxRequest(request)) {
				decoration = "false";
				request.setAttribute("isAjax", "true");
			} else {
				decoration = "true";
			}
		}
        if (StringUtils.isNotBlank(purepage) && StringUtils.equals(purepage, "true")) {
            decoration = "false";
            purepage = "true";
        } else {
            purepage = "false";
        }
		request.setAttribute("decoration", decoration);
        request.setAttribute("purepage", purepage);

        String appName = this.getAppName(request);
		request.setAttribute("jqueryVersion", this.getJQueryVersion(appName));
		boolean killXss = this.isKillXSS(appName);
		// trim所有参数
		try {
			// 注意：原生request.getParameterMap()不可修改，BossHttpServletRequestWrapper可支持修改
			Map<String, Object> params = request.getParameterMap();
			if (extParams != null && extParams.length > 0
					&& extParams[0] instanceof Map) {
				params = (Map<String, Object>) extParams[0];
			}
			for (String key : params.keySet()) {
				Object value = params.get(key);
				if (value == null) {
					continue;
				}
				if (value instanceof String) {
					value = StringUtils.trim((String) value);
					if (killXss) {
						value = BossWebUtils.cleanXSS((String) value);
					}
					params.put(key, value);
				} else if (value instanceof String[]) {
					String[] values = (String[]) value;
					for (int i = 0; i < values.length; i++) {
						values[i] = StringUtils.trim(values[i]);
						if (killXss) {
							values[i] = BossWebUtils
									.cleanXSS((String) values[i]);
						}
					}
					params.put(key, values);
				} else if (value instanceof Object[]) {
					Object[] values = (Object[]) value;
					String[] strValues = new String[values.length];
					for (int i = 0; i < values.length; i++) {
						strValues[i] = StringUtils.trim((String) values[i]);
						if (killXss) {
							strValues[i] = BossWebUtils
									.cleanXSS((String) values[i]);
						}
					}
					params.put(key, strValues);
				}
			}

			// 调试日志，不输出密码信息
			if (logger.isDebugEnabled()) {
				Map<String, Object> newMap = new HashMap<String, Object>();
				newMap.putAll(params);
				for (String key : params.keySet()) {
					String newKey = StringUtils.lowerCase(key);
					if (StringUtils.contains(newKey, "password")
							|| StringUtils.contains(newKey, "passwd")
							|| StringUtils.contains(newKey, "pwd")) {
						newMap.remove(key);
					}
				}
				logger.debug("http params: {}" + JSONUtils.toJsonString(newMap));
			}
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
		return true;
	}

	private String getConfigValue(String key) {
		try {
			@SuppressWarnings("unchecked")
			ConfigParam<String> param = ConfigurationUtils
					.getAppConfigParam(key);
			return param.getValue();
		} catch (Exception e) {
			logger.error("获取产品应用参数失败！key: {}" + key);
			return null;
		}
	}

	private String getAppName(HttpServletRequest request) {
		String appName = StringUtils.replace(request.getContextPath(), "/", "");
		if (StringUtils.isBlank(appName)) {
			appName = ThreadContextUtils.getAppName();
		}
		return appName;
	}

	private String getJQueryVersion(String appName) {
		String version = null;
		try {
			@SuppressWarnings("unchecked")
			ConfigParam<Map<String, String>> param = ConfigurationUtils
					.getAppConfigParam(CONF_KEY_JQUERY_VERSION);
			if (param != null && param.getValue() != null
					&& param.getValue().containsKey(appName)) {
				version = param.getValue().get(appName);
			}
		} catch (Exception e) {
		}
		if (StringUtils.isBlank(version)) {
			version = JQUERY_DEFAULT_VERSION;
		}
		return version;
	}

	private boolean isKillXSS(String appName) {
		boolean enable = false;
		try {
			@SuppressWarnings("unchecked")
			ConfigParam<Map<String, Boolean>> param = ConfigurationUtils
					.getAppConfigParam(CONF_KEY_XSS_FILTER_ENABLE);
			if (param != null && param.getValue() != null
					&& param.getValue().containsKey(appName)) {
				enable = param.getValue().get(appName);
			}
		} catch (Exception e) {
		}
		return enable;
	}
}