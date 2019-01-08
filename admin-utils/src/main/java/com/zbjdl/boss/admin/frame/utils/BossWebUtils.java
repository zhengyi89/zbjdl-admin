/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.frame.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * 类名称：WebUtils <br>
 * 类描述： <br>
 *
 * @author feng
 * @since 2011-4-27 上午10:11:49
 * @version 1.0.0
 *
 */
public class BossWebUtils {
	public static final String LOCALHOST = "127.0.0.1";

	public static final String ANYHOST = "0.0.0.0";

	private static final Pattern IP_PATTERN = Pattern
			.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

	/**
	 *
	 * 描述： 从request中获取actionName
	 *
	 * @param request
	 * @return
	 */
	public static String getActionName(HttpServletRequest request) {
		String actionName;
		String uri = request.getRequestURI();
		// '/'到'?'之间即为actionName
		int beginIndex = uri.indexOf("/");
		int endIndex = uri.indexOf("?");
		actionName = uri.substring(beginIndex, endIndex);
		return actionName;
	}

	private static String COM = "com", CN = "cn", ORG = "org", GOV = "gov";

	public static boolean isAccessThoughIp(HttpServletRequest request) {
		String accessWay = request.getServerName().toLowerCase();

		return !(accessWay.contains(COM) || accessWay.contains(CN)
				|| accessWay.contains(ORG) || accessWay.contains(GOV));
	}

	public static String getDomain(HttpServletRequest request) {
		if (isAccessThoughIp(request)) {
			return null;
		} else {
			String serverName = request.getServerName();
			return getDomain(serverName);
		}
	}

	public static String getDomain(String serverName) {
		String[] parts = serverName.split("\\.");
		int length = parts.length;

		return "." + parts[length - 2] + "." + parts[length - 1];
	}

	/**
	 * Title：判断是否是ajax请求，
	 *
	 * @return
	 */
	@Deprecated
	public static boolean isAjaxRequest() {
		HttpServletRequest req = ServletActionContext.getRequest();
		return isAjaxRequest(req);
	}

	public static boolean isAjaxRequest(HttpServletRequest reqest) {
		// 请求头
		String reqHeader = reqest.getHeader("X-Requested-With");
		// ajax请求,返回true
		return reqHeader != null
				&& reqHeader.equalsIgnoreCase("XMLHttpRequest");
	}

	public static String cleanXSS(String value) {
		if (StringUtils.isBlank(value)) {
			return value;
		}
		// You'll need to remove the spaces from the html entities below
		value = value.trim().replaceAll("<", "&lt;").replaceAll(">", "&gt;");

		// 定时组件，当参数的方法名
		// value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");

		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
				"\"\"");
		return value;
	}

	public static InetAddress getLocalAddress() {
		InetAddress localAddress = null;
		try {
			localAddress = InetAddress.getLocalHost();
			if (isValidAddress(localAddress)) {
				return localAddress;
			}
		} catch (Throwable e) {
		}
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface
					.getNetworkInterfaces();
			if (interfaces != null) {
				while (interfaces.hasMoreElements()) {
					try {
						NetworkInterface network = interfaces.nextElement();
						Enumeration<InetAddress> addresses = network
								.getInetAddresses();
						if (addresses != null) {
							while (addresses.hasMoreElements()) {
								try {
									InetAddress address = addresses
											.nextElement();
									if (isValidAddress(address)) {
										return address;
									}
								} catch (Throwable e) {
								}
							}
						}
					} catch (Throwable e) {
					}
				}
			}
		} catch (Throwable e) {
		}
		return localAddress;
	}

	private static boolean isValidAddress(InetAddress address) {
		if (address == null || address.isLoopbackAddress())
			return false;
		String name = address.getHostAddress();
		return (name != null && !ANYHOST.equals(name)
				&& !LOCALHOST.equals(name) && IP_PATTERN.matcher(name)
				.matches());
	}
}
