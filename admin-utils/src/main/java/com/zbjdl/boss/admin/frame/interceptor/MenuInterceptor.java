package com.zbjdl.boss.admin.frame.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.zbjdl.boss.admin.frame.interceptor.helper.MenuInterceptorHelper;

/**
 * 菜单拦截器
 *
 * @author xuebo.yang, feng
 * @version 1.0.0
 * @since 2012-4-6 下午04:32:15
 */
public class MenuInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean before(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		return MenuInterceptorHelper.getInstance().before(request, response);
	}
}
