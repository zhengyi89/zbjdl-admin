/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.frame.interceptor.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.zbjdl.boss.admin.frame.interceptor.helper.ExceptionInterceptorHelper;
import com.zbjdl.boss.admin.frame.interceptor.helper.SSOInterceptorHelper;

/**
 * <p>
 * Title: Spring MVC 版Exception 拦截器
 * </p>
 * <p>
 * Description: 描述 ，判断如果是/error controller，则返回错误信息
 * </p>
 * <p>
 * Copyright: Copyright (c)2015
 * </p>
 * <p>
 * Company: 云宝金服
 * </p>
 *
 * @author feng
 * @version 0.1, 14-5-21 19:21
 */
public class ExceptionInterceptor extends BaseInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 是否跳过当前拦截器
		if (!isErrorController(request)) {
			return true;
		}
		ExceptionInterceptorHelper.getInstance().output(request, response);
		return false;
	}
	
	//判断是否为error controller
	protected boolean isErrorController(HttpServletRequest request) {
		if(request.getRequestURI().toLowerCase().equals(request.getContextPath()+"/error")){
			return true;

		}else{
			return false;
		}
	}	
}
