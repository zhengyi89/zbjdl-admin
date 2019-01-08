package com.zbjdl.boss.admin.frame.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * <p>Title: XSS Filter</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2018</p>
 * <p>Company: 八戒财云</p>
 *
 * @author feng
 * @version 0.1, 14-5-1 21:43
 */
public class XssFilter implements Filter {

	private final static Logger LOGGER = LoggerFactory.getLogger(XssFilter.class);

	private FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void destroy() {
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {
		chain.doFilter(new XssHttpServletRequestWrapper(
				(HttpServletRequest) request), response);
	}

}
