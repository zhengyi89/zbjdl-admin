/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.frame.interceptor.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类描述： 拦截器辅助类基类
 *
 * @author：feng
 * @since：2014年11月10日 下午5:24:16
 * @version:
 */
public abstract class BaseInterceptorHelper {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 拦截器前处理，处理成功才继续
	 */
	public boolean before(HttpServletRequest request,
			HttpServletResponse response, Object... extParams) throws Exception {
		return true;
	}

	/**
	 * 进入拦截器必须执行的处理
	 */
	public boolean doIntercept(HttpServletRequest request,
			HttpServletResponse response, Object... extParams) throws Exception {
		return true;
	}

	/**
	 * 拦截后输出提示页面
	 */
	public void output(HttpServletRequest request,
			HttpServletResponse response, Object... messages) throws Exception {
	}
}
