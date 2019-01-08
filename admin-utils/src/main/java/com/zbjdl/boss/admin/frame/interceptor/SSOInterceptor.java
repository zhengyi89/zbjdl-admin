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
import com.zbjdl.boss.admin.frame.interceptor.helper.SSOInterceptorHelper;

/**
 * 类名称：SSOInterceptor <br>
 * 类描述：单点登录拦截器<br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-7-22 下午02:35:22
 */
public class SSOInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = -7719598422283046506L;

	@Override
	public boolean before(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		return SSOInterceptorHelper.getInstance().before(request, response);
	}
}
