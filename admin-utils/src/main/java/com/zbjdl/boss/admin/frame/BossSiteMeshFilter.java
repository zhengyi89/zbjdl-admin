/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.frame;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

import com.zbjdl.boss.admin.frame.utils.BossFreemarkerUtils;
import com.zbjdl.boss.admin.frame.utils.BossWebUtils;

public class BossSiteMeshFilter extends ConfigurableSiteMeshFilter {
	private static final Pattern MONITOR_CHECK_STATUS = Pattern
			.compile("/[^/]+/check-status.jsp");

	private static final Pattern MONITOR_SHOW_HOST = Pattern
			.compile("/[^/]+/showhost.jsp");

	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		builder.addDecoratorPath("/*", "/freemarker/bossportal.ftl");
		builder.addExcludedPath("/loginout/*");
		builder.addExcludedPath("*/no-decoration/*");
		builder.addExcludedPath("*/js/*");
		builder.addExcludedPath("*/css/*");
		builder.addExcludedPath("*/img/*");
		builder.addExcludedPath("*/images/*");
		builder.addExcludedPath("*/mCustomScrollbar/*");
		builder.addExcludedPath("*/fonts/*");
		builder.addExcludedPath("*/DatePicker/*");
		builder.addExcludedPath("*/datetimepick/*");
		builder.addExcludedPath("*/bootstrap/*");
		builder.addExcludedPath("*/jquery*/*");
		builder.addExcludedPath("*/druid/*");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String uri = ((HttpServletRequest) request).getRequestURI()
				.toLowerCase();
		if (MONITOR_CHECK_STATUS.matcher(uri).matches()) {
			this.toCheckStatus(request, response);
			return;
		}
		if (MONITOR_SHOW_HOST.matcher(uri).matches()) {
			this.toShowHost(request, response);
			return;
		}
		super.doFilter(new BossHttpServletRequestWrapper(
				(HttpServletRequest) request), response, chain);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void toCheckStatus(ServletRequest request, ServletResponse response) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			StringBuilder statusInfo = new StringBuilder();
			Enumeration names = ((HttpServletRequest) request).getHeaderNames();
			while (names.hasMoreElements()) {
				String name = (String) names.nextElement();
				statusInfo.append(name + ": "
						+ ((HttpServletRequest) request).getHeader(name)
						+ "<br>");
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.putAll(request.getParameterMap());
			params.put("resourcePath", request.getAttribute("resourcePath"));
			params.put("statusInfo", statusInfo.toString());
			String result = BossFreemarkerUtils.getContentFromTemplate(
					"monitor/check-status.ftl", params);
			response.getWriter().println(result);
		} catch (Exception e) {
		}
	}

	@SuppressWarnings({ "unchecked" })
	private void toShowHost(ServletRequest request, ServletResponse response) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> params = new HashMap<String, Object>();
			params.putAll(request.getParameterMap());
			params.put("resourcePath", request.getAttribute("resourcePath"));
			InetAddress address = BossWebUtils.getLocalAddress();
			params.put("hostName", address.getHostName());
			params.put("hostIp", address.getHostAddress());
			String result = BossFreemarkerUtils.getContentFromTemplate(
					"monitor/showhost.ftl", params);
			response.getWriter().println(result);
		} catch (Exception e) {
		}
	}

}
