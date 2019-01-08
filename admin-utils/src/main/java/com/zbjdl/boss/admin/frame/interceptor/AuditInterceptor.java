/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.frame.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.zbjdl.boss.admin.frame.interceptor.helper.AuditInterceptorHelper;

/**
 * 类名称：AuditInterceptor <br>
 * 类描述：双重复核拦截器<br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-7-22 下午02:46:53
 */
public class AuditInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = -1846568738635842514L;

	@Override
	public boolean before(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		return AuditInterceptorHelper.getInstance().before(request, response);
	}

	@Override
	public String doIntercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		if (AuditInterceptorHelper.getInstance().doIntercept(request, response)) {
			return invocation.invoke();
		}
		return null;
	}
}
