package com.zbjdl.boss.admin.frame;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: Spring MVC 上下文<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2018<br/>
 * Company: 八戒财云<br/>
 * <br/>
 *
 * @author feng
 * @version 0.1, 14-8-26 16:54
 */
public class ControllerContext implements Serializable {
	private static final long serialVersionUID = 3509504476165738552L;

	private static ThreadLocal<ControllerContext> controllerContext = new ThreadLocal<ControllerContext>();
	private Map<Object, Object> context;

	private static final String REQUEST = "javax.servlet.http.HttpServletRequest";
	private static final String RESPONSE = "javax.servlet.http.HttpServletResponse";

	public ControllerContext(Map<Object, Object> context) {
		this.context = context;
	}

	public static void setContext(ControllerContext context) {
		controllerContext.set(context);
	}

	public static ControllerContext getContext() {
		ControllerContext context = (ControllerContext) controllerContext.get();
		if (null == context) {
			context = new ControllerContext(new HashMap<Object, Object>());
			setContext(context);
		}
		return context;
	}

	public Map<Object, Object> getContextMap() {
		return this.context;
	}

	public void setContextMap(Map<Object, Object> contextMap) {
		getContext().context = contextMap;
	}

	public Object get(Object key) {
		return context.get(key);
	}

	public void put(Object key, Object value) {
		context.put(key, value);
	}

	public void setRequest(HttpServletRequest request) {
		put(REQUEST, request);
	}

	public HttpServletRequest getRequest() {
		return (HttpServletRequest) get(REQUEST);
	}

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) get(RESPONSE);
	}

	public void setResponse(HttpServletResponse response) {
		put(RESPONSE, response);
	}

	public HttpSession getSession() {
		return getRequest().getSession();
	}

	public ServletContext getApplication() {
		return getSession().getServletContext();
	}
}
