/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.frame.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.zbjdl.boss.admin.frame.interceptor.helper.ExceptionInterceptorHelper;

/**
 * 类名称：ExceptionHandler <br>
 * 类描述： 业务异常统一处理类<br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-7-27 下午06:29:10
 */
public class ExceptionInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = 1L;

	/**
	 * 是否可跳过
	 */
	@Override
	protected boolean isSkipAble() {
		return false;
	}

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		try {
			return invocation.invoke();
		} catch (Throwable e) {
			logger.error("", e);
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			ExceptionInterceptorHelper.getInstance().output(request, response,
					e);
		}
		return null;
	}
}
