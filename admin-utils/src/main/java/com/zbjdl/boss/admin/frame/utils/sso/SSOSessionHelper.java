/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.frame.utils.sso;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author：feng
 * @since：2012-9-26 下午2:36:01
 * @version:
 */
public class SSOSessionHelper {
	private static final Logger logger = LoggerFactory.getLogger(SSOSessionHelper.class);

	/**
	 * 从session中获取userId
	 *
	 * @param session
	 * @return
	 */
	public static String getUserId(HttpSession session) {
		return (String) session
				.getAttribute(SSOCodeConst.SSO_SESSION_USERID_ID);
	}

	/**
	 * 从session中获取loginId
	 *
	 * @param session
	 * @return
	 */
	public static String getLoginId(HttpSession session) {
		return (String) session.getAttribute(SSOCodeConst.SSO_SESSION_LOGIN_ID);
	}

	/**
	 * 设置登录信息
	 *
	 * @param session
	 * @param userId
	 * @param loginId
	 */
	public static void setLoginInfo(HttpSession session, String userId,
			String loginId) {
		session.setAttribute(SSOCodeConst.SSO_SESSION_USERID_ID, userId);
		session.setAttribute(SSOCodeConst.SSO_SESSION_LOGIN_ID, loginId);
	}

	/**
	 * 清空登录信息
	 *
	 * @param session
	 */
	public static void removeLoginInfo(HttpSession session) {
		session.removeAttribute(SSOCodeConst.SSO_SESSION_USERID_ID);
		session.removeAttribute(SSOCodeConst.SSO_SESSION_LOGIN_ID);
	}

	/**
	 * 删除应用系统所需的session信息
	 *
	 * @param session
	 * @param removeKey
	 */
	public static void removeAppSession(HttpSession session, String... appKey) {
		if (appKey != null) {
			for (String key : appKey) {
				session.removeAttribute(key);
			}
		}
	}

	/**
	 * 清空session
	 *
	 * @param session
	 */
	public static void clearSession(HttpSession session) {
		try {
			@SuppressWarnings("rawtypes")
			Enumeration attrs = session.getAttributeNames();
			List<String> attrNames = new ArrayList<String>();
			while (attrs.hasMoreElements()) {
				attrNames.add(attrs.nextElement().toString());
			}
			for (String attrName : attrNames) {
				session.removeAttribute(attrName);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
