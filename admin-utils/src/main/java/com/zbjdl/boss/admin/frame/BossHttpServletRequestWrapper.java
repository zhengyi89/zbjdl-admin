package com.zbjdl.boss.admin.frame;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.google.common.collect.Maps;

/**
 * 重写getParameterMap，返回可修改的map以支持参数拦截器做控制
 *
 * @author：feng
 * @since：2014年11月13日 下午1:17:24
 * @version:
 */
public class BossHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private Map<String, String[]> parameterMap = null;

	public BossHttpServletRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String[]> getParameterMap() {
		if (parameterMap == null) {
			synchronized (BossHttpServletRequestWrapper.class) {
				if (parameterMap == null) {
					parameterMap = Maps.newHashMap(super.getParameterMap());
				}
			}
		}
		return parameterMap;
	}

	public String[] getParameterValues(String parameter) {
		if (parameterMap == null) {
			getParameterMap();
		}
		Object values = parameterMap.get(parameter);
		if (values instanceof String[]) {
			return (String[]) values;
		} else if (values instanceof Object[]) {
			Object[] paramValues = (Object[]) values;
			String[] strValues = new String[paramValues.length];
			int i = 0;
			for (Object v : paramValues) {
				strValues[i++] = (String) v;
			}
		} else if (values instanceof String) {
			return new String[] { (String) values };
		}
		return super.getParameterValues(parameter);
	}

	public String getParameter(String parameter) {
		if (parameterMap == null) {
			getParameterMap();
		}
		Object value = parameterMap.get(parameter);
		if (value instanceof String) {
			return (String) value;
		} else if (value instanceof String[]) {
			String[] paramValues = (String[]) value;
			if (paramValues.length > 0) {
				return (String) paramValues[0];
			}
		} else if (value instanceof Object[]) {
			Object[] paramValues = (Object[]) value;
			if (paramValues.length > 0) {
				return (String) paramValues[0];
			}
		}
		return super.getParameter(parameter);
	}
}
