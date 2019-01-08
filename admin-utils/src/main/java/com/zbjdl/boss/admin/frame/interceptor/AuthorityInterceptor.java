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
import com.zbjdl.boss.admin.frame.interceptor.helper.AuthorityInterceptorHelper;

/**
 * 类名称：AuthorityInterceptor <br>
 * 类描述：权限鉴权拦截器<br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-7-22 下午02:46:53
 */
public class AuthorityInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = -1846568738635842514L;

	@Override
	public boolean before(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		return AuthorityInterceptorHelper.getInstance().before(request,
				response);
	}
}
