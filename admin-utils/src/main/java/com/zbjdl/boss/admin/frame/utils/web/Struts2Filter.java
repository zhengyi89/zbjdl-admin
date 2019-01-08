package com.zbjdl.boss.admin.frame.utils.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

/**
 * <p>Title: Struts2配置Filter</p>
 * <p>Description: 封装StrutsPrepareAndExecuteFilter 实现排除过滤功能<br/>
 * 	  在web.xml中filter配置init-param
 */
public class Struts2Filter extends StrutsPrepareAndExecuteFilter {

	@Override
	protected void postInit(Dispatcher dispatcher, FilterConfig filterConfig) {
		super.postInit(dispatcher, filterConfig);
		String excludedPatternsString = filterConfig.getInitParameter("excludedPatterns");
		if(excludedPatternsString != null && !"".equals(excludedPatternsString)) {
			if(this.excludedPatterns == null) {
				this.excludedPatterns = new ArrayList<Pattern>();
			}
			String[] excludedPatterns = excludedPatternsString.split(",");
			for(String pattern: excludedPatterns) {
				this.excludedPatterns.add(Pattern.compile(pattern));
			}
		}
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		((HttpServletRequest) req).getSession();
		super.doFilter(req, res, chain);
	}
	
}
