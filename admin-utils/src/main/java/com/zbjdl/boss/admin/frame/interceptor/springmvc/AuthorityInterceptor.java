/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.frame.interceptor.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zbjdl.boss.admin.frame.interceptor.helper.AuthorityInterceptorHelper;

/**
 * <p>
 * Title: Spring MVC 版权限拦截器
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * <p>
 * Copyright: Copyright (c)2018
 * </p>
 * <p>
 * Company: 八戒财云
 * </p>
 *
 * @author feng
 * @version 0.1, 14-5-21 19:08
 */
public class AuthorityInterceptor extends BaseInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if(isErrorController(request)){
			return true;
		}
		// 是否跳过当前拦截器
		if (isSkipAble() && isAutoSkip(request)) {
			return true;
		}
		return AuthorityInterceptorHelper.getInstance().before(request,
				response);
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
