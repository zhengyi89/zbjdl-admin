package com.zbjdl.boss.admin.frame.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.zbjdl.boss.admin.frame.interceptor.helper.ParametersInterceptorHelper;

public class ParametersInterceptor extends BaseInterceptor {

	private static final long serialVersionUID = 1443376017492522285L;

	/**
	 * 是否可跳过
	 */
	@Override
	protected boolean isSkipAble() {
		return false;
	}

	@Override
	public boolean before(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		return ParametersInterceptorHelper.getInstance().before(request,
				response, invocation.getInvocationContext().getParameters());
	}
}