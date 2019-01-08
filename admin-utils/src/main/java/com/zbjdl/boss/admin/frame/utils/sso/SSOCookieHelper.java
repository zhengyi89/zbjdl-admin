/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.frame.utils.sso;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zbjdl.boss.admin.frame.utils.BossWebUtils;
import com.zbjdl.common.utils.StringUtils;

/**
 * cookie操作类
 * 
 * @author：feng
 * @since：2012-9-26 下午1:58:07
 * @version:
 */
public class SSOCookieHelper {

	/**
	 * 从cookie获取单点登录信息
	 * 
	 * @param req
	 * @return
	 */
	public static String getCookie(HttpServletRequest req) {
		return getCookieByName(req, SSOCodeConst.SSO_COOKIE_NAME);
	}

	/**
	 * 设置单点登录cookie
	 * 
	 * @param res
	 * @param cookieSecurityInfo
	 * @param domain
	 */
	public static void setCookie(HttpServletResponse res,
			String cookieSecurityInfo, String domain) {
		addCookie(res, SSOCodeConst.SSO_COOKIE_NAME,
				SSOCodeConst.SSO_COOKIE_PATH, domain, cookieSecurityInfo,
				SSOCodeConst.SSO_COOKIE_ALIVE);
	}

	/**
	 * 删除单点登录cookie
	 * 
	 * @param req
	 * @param res
	 */
	public static void removeCookie(HttpServletRequest req,
			HttpServletResponse res) {
		String domain = BossWebUtils.getDomain(req);
		addCookie(res, SSOCodeConst.SSO_COOKIE_NAME,
				SSOCodeConst.SSO_COOKIE_PATH, domain, null, -1);
	}

	/**
	 * 获取cookie值
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	private static String getCookieByName(HttpServletRequest request,
			String name) {
		Cookie cookies[] = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie.getValue();
				}
			}

		}
		return null;
	}

	/**
	 * 添加cookie
	 * 
	 * @param response
	 * @param name
	 * @param path
	 * @param domainName
	 * @param value
	 * @param maxAge
	 */
	private static void addCookie(HttpServletResponse response, String name,
			String path, String domainName, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		if (StringUtils.isNotBlank(path))
			cookie.setPath(path);
		if (StringUtils.isNotBlank(domainName))
			cookie.setDomain(domainName);
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	/**
	 * 删除cookie
	 * 
	 * @param request
	 * @param response
	 * @param name
	 */
	@SuppressWarnings("unused")
	private static void deleteCookie(HttpServletRequest request,
			HttpServletResponse response, String name) {
		Cookie cookies[] = request.getCookies();
		String domain = BossWebUtils.getDomain(request);
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					cookie.setMaxAge(0);
					cookie.setDomain(domain);
					cookie.setPath(SSOCodeConst.SSO_COOKIE_PATH);
					response.addCookie(cookie);
					break;
				}
			}
		}
	}

}
