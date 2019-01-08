package com.zbjdl.boss.admin.frame.interceptor.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.zbjdl.boss.admin.frame.interceptor.helper.AuditInterceptorHelper;

/**
 * Title: Spring MVC 版双重复核拦截器<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2018<br/>
 * Company: 八戒财云<br/>
 * <br/>
 *
 * @author feng
 * @version 0.1, 14-8-19 17:10
 */
public class AuditInterceptor extends BaseInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 是否跳过当前拦截器
		if (isSkipAble() && isAutoSkip(request)) {
			return true;
		}
		return AuditInterceptorHelper.getInstance().before(request, response);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		AuditInterceptorHelper.getInstance().doIntercept(request, response);
	}
}
