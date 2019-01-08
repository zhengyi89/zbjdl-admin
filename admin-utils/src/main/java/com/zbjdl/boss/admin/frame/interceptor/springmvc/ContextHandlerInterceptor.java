package com.zbjdl.boss.admin.frame.interceptor.springmvc;

import com.zbjdl.boss.admin.frame.ControllerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: 上下文拦截器<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2018<br/>
 * Company: 八戒财云<br/>
 * <br/>
 *
 * @author feng
 * @version 0.1, 14-8-26 16:52
 */
public class ContextHandlerInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ContextHandlerInterceptor.class);

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(request.getRequestURI());
		}
		// 注入Request/Response对象至SpringMVC执行上下文对象中
		ControllerContext.getContext().setRequest(request);
		ControllerContext.getContext().setResponse(response);

		return super.preHandle(request, response, handler);
	}

}
