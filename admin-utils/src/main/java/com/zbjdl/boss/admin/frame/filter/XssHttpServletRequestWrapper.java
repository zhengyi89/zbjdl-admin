package com.zbjdl.boss.admin.frame.filter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zbjdl.boss.admin.frame.utils.BossWebUtils;

/**
 * <p>
 * Title: XSS HttpServletRequest 包装类
 * </p>
 * <p>
 * Description: 过滤post请求的每一个参数，替换成安全的
 * </p>
 * <p>
 * Copyright: Copyright (c)2018
 * </p>
 * <p>
 * Company: 八戒财云
 * </p>
 *
 * @author feng
 * @version 0.1, 14-5-1 21:45
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getParameterMap() {
		Map<String, Object> params = Maps.newHashMap(super.getParameterMap());
		for (String key : params.keySet()) {
			params.put(key, this.escapeParam(params.get(key)));
		}
		return Collections.unmodifiableMap(params);
	}

	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = BossWebUtils.cleanXSS(values[i]);
		}
		return encodedValues;
	}

	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}
		return BossWebUtils.cleanXSS(value);
	}

	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value == null) {
			return null;
		}
		return BossWebUtils.cleanXSS(value);
	}

	/**
	 * 请求参数防注入攻击
	 *
	 * @param value
	 * @return
	 */
	private Object escapeParam(Object value) {
		List<String> newValue = Lists.newArrayList();
		for (String item : (String[]) value) {
			newValue.add(BossWebUtils.cleanXSS(item));
		}
		return newValue.toArray();
	}
}
