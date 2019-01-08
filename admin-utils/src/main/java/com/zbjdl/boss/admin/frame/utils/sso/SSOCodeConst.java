/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.frame.utils.sso;

/**
 * @author：feng
 * @since：2012-9-26 下午2:40:36
 * @version:
 */
public class SSOCodeConst {

	/**
	 * 单点登录session中的loginId
	 */
	public static final String SSO_SESSION_LOGIN_ID = "sso_session_loginid";
	/**
	 * 单点登录中session中userId
	 */
	public static final String SSO_SESSION_USERID_ID = "sso_session_userid";

	/**
	 * 单点登录cookie名
	 */
	public static final String SSO_COOKIE_NAME = "boss_sso_id";
	/**
	 * 单点登录cookie路径
	 */
	public static final String SSO_COOKIE_PATH = "/";
	/**
	 * 单点登录cookie存活期，设置为-1，关闭浏览器删除
	 */
	public static final int SSO_COOKIE_ALIVE = -1;

	/**
	 * 单点登录url字符集
	 */
	public static final String DEFAULT_ENCODING = "UTF-8";
	/**
	 * 单点登录token
	 */
	public static final String TOKEN_PARAM_NAME = "boss_sso_token";

}
