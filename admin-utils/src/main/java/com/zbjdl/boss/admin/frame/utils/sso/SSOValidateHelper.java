/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.frame.utils.sso;

import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.dto.sso.SSOLoginInfoDTO;
import com.zbjdl.boss.admin.facade.UserLoginFacade;
import com.zbjdl.boss.admin.frame.service.impl.RemoteServiceImpl;
import com.zbjdl.boss.admin.frame.utils.BossWebUtils;
import com.zbjdl.boss.admin.frame.utils.Constants;
import com.zbjdl.common.utils.StringUtils;
import com.zbjdl.common.utils.config.ConfigParam;
import com.zbjdl.common.utils.config.ConfigurationUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * SSO验证工具类
 * 
 * @author feng
 * @version 1.0.0
 * @since 2012-9-26 下午2:47:34
 */
public class SSOValidateHelper {
	private static final Logger logger = LoggerFactory
			.getLogger(SSOValidateHelper.class);

	private static final String HTTPS_PREFIX = "https://";
	private static final String HTTP_PREFIX = "http://";

	private static UserLoginFacade ssoFacade = RemoteServiceImpl.getInstance()
			.getUserLoginFacade();

	/**
	 * 验证是否属于登录状态
	 * <p/>
	 * 验证失败，删除session信息时，没有使用invalid方式， 主要是考虑应用中可能在session中保留了其他信息，
	 * 这里通过设置appKey来删除用户信息
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public static boolean ssoLoginValid(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		String validUrl = getValidUrl();
		String appKey = DataDictDTO.SESSION_USERINFO;
		String loginAction = getLoginUrlByValidUrl(validUrl); // 登录地址
		if (StringUtils.isBlank(validUrl)) {
			printErrorInfo(req, res, "未配置单点登录校验地址，请联系管理员!", loginAction);
			return false;
		}
		// 获取登录信息
		String userId = SSOSessionHelper.getUserId(req.getSession());
		String loginId = SSOSessionHelper.getLoginId(req.getSession());
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(loginId)) {// 登录信息
			// 检查是否存在token
			String token = getToken(req);
			if (StringUtils.isBlank(token)) {
				logger.info(
						"no token info ,need to valid ,current validUrl :{}",
						validUrl);
				// 删除session中的用户信息
				SSOSessionHelper.removeAppSession(req.getSession(), appKey);
				// 去验证cookie的服务
				sendToValid(req, res, validUrl);
				return false;
			} else {
				// 用token获取登录信息
				try {
					SSOLoginInfoDTO loginInfo = ssoFacade
							.getUserIdByToken(token);
					SSOSessionHelper.setLoginInfo(req.getSession(),
							loginInfo.getUserid(), loginInfo.getLoginid());
					SSOSessionHelper.removeAppSession(req.getSession(), appKey);
					logger.info(
							"login by token success. current userid: {} token: {}.",
							loginInfo.getUserid(), loginInfo.getLoginid());
					return true;
				} catch (Exception e) {
					logger.error("login by token failed.", e);
					// 删除session中的用户信息
					SSOSessionHelper.removeAppSession(req.getSession(), appKey);
					SSOSessionHelper.removeLoginInfo(req.getSession()); // 删除登录信息
					printErrorInfo(req, res, "登录失败,token无效或已过期!", loginAction);
					return false;
				}
			}
		} else {
			if (!ssoFacade.validate(userId, loginId)) {
				logger.info("valide with userId and loginId fail, redirect to ssoValid");
				// 删除session中的用户信息
				SSOSessionHelper.removeAppSession(req.getSession(), appKey);
				SSOSessionHelper.removeLoginInfo(req.getSession());// 删除登录信息
				// 清除原来的session信息，去验证cookie的服务
				sendToValid(req, res, validUrl);
				return false;
			}
			// 如果session中登录成功，但是token可以成功登录，则有可能是重新登录过来的。需要更新session中的用户信息，如果是个无效token则不用管他，按原来的逻辑处理即可
			String token = getToken(req);
			if (StringUtils.isNotBlank(token)) {
				// 用token获取登录信息
				try {
					SSOLoginInfoDTO loginInfo = ssoFacade
							.getUserIdByToken(token);
					SSOSessionHelper.setLoginInfo(req.getSession(),
							loginInfo.getUserid(), loginInfo.getLoginid());
					SSOSessionHelper.removeAppSession(req.getSession(), appKey);
					String message = String
							.format("login by token success. current userid :%s token:%s",
									loginInfo.getUserid(),
									loginInfo.getLoginid());
					logger.info(message);
					// 增加销毁原登录信息的操作，以防没点击退出，随意切换到其他的子应用时，子应用的session没有消失
					ssoFacade.logoutByUserIdAndLoginId(userId, loginId);
					return true;
				} catch (Exception e) {
					logger.info("invalid token keep current session info!");
				}
			}
			logger.info("user login staus valid success.");
			return true;
		}
	}

	/**
	 * 验证是否属于登录状态
	 * <p/>
	 * 验证失败，删除session信息时，没有使用invalid方式， 主要是考虑应用中可能在session中保留了其他信息，
	 * 这里通过设置appKey来删除用户信息
	 * 
	 * @param req
	 *            request
	 * @param res
	 *            response
	 * @return
	 * @throws Exception
	 */
	public static boolean ssoLoginValid4EMVC(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		String validUrl = getValidUrl();
		String appKey = DataDictDTO.SESSION_USERINFO;
		String loginAction = getLoginUrlByValidUrl(validUrl); // 登录地址
		if (StringUtils.isBlank(validUrl)) {
			printErrorInfo4EMVC(req, res, "未配置单点登录校验地址，请联系管理员!", loginAction);
			return false;
		}

		// 获取登录信息
		String userId = SSOSessionHelper.getUserId(req.getSession());
		String loginId = SSOSessionHelper.getLoginId(req.getSession());
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(loginId)) {// 登录信息
			// 检查是否存在token
			String token = getToken(req);
			if (StringUtils.isBlank(token)) {
				String message = String
						.format("No token info, need to check cookie info, current validUrl: %s",
								validUrl);
				logger.info(message);
				// 删除session中的用户信息
				SSOSessionHelper.removeAppSession(req.getSession(), appKey);
				// 去验证cookie的服务
				sendToValid(req, res, validUrl);
				return false;
			} else {
				// 用token获取登录信息
				try {
					SSOLoginInfoDTO loginInfo = ssoFacade
							.getUserIdByToken(token);
					SSOSessionHelper.setLoginInfo(req.getSession(),
							loginInfo.getUserid(), loginInfo.getLoginid());
					SSOSessionHelper.removeAppSession(req.getSession(), appKey);
					String message = String
							.format("login by token success. current userid :%s token:%s",
									loginInfo.getUserid(),
									loginInfo.getLoginid());
					logger.info(message);
					return true;
				} catch (Exception e) {
					logger.error("login by token failed.", e);
					// 删除session中的用户信息
					SSOSessionHelper.removeAppSession(req.getSession(), appKey);
					SSOSessionHelper.removeLoginInfo(req.getSession()); // 删除登录信息
					printErrorInfo4EMVC(req, res, "登录失败,token无效或已过期!",
							loginAction);
					return false;
				}
			}
		} else {
			if (!ssoFacade.validate(userId, loginId)) {
				// printErrorInfo(req, res, "登录超时或已有他人使用该账户登录!", loginAction);
				// 删除session中的用户信息
				SSOSessionHelper.removeAppSession(req.getSession(), appKey);
				SSOSessionHelper.removeLoginInfo(req.getSession());// 删除登录信息
				// 清除原来的session信息，去验证cookie的服务
				sendToValid(req, res, validUrl);
				return false;
			}
			// 如果session中登录成功，但是token可以成功登录，则有可能是重新登录过来的。需要更新session中的用户信息，如果是个无效token则不用管他，按原来的逻辑处理即可
			String token = getToken(req);
			if (StringUtils.isNotBlank(token)) {
				// 用token获取登录信息
				try {
					SSOLoginInfoDTO loginInfo = ssoFacade
							.getUserIdByToken(token);
					SSOSessionHelper.setLoginInfo(req.getSession(),
							loginInfo.getUserid(), loginInfo.getLoginid());
					SSOSessionHelper.removeAppSession(req.getSession(), appKey);
					String message = String
							.format("login by token success. current userid: %s token: %s.",
									loginInfo.getUserid(),
									loginInfo.getLoginid());
					logger.info(message);
					// 增加销毁原登录信息的操作，以防没点击退出，随意切换到其他的子应用时，子应用的session没有消失
					ssoFacade.logoutByUserIdAndLoginId(userId, loginId);
					return true;
				} catch (Exception e) {
					logger.info("invalid token keep current session info!");
				}

			}
			logger.info("user login staus valid success.");
			return true;
		}
	}

	/**
	 * 根据验签地址获取登录地址
	 * 
	 * @param validUrl
	 *            验证地址
	 * @return
	 */
	private static String getLoginUrlByValidUrl(String validUrl) {
		if (StringUtils.isBlank(validUrl)) {
			return null;
		}
		int actionIndex = validUrl.lastIndexOf("/");
		if (-1 == actionIndex) {
			return null;
		}
		return validUrl.substring(0, actionIndex + 1) + "showLogin";
	}

	/**
	 * 输出错误信息
	 * 
	 * @param req
	 * @param res
	 * @param message
	 *            错误提示
	 * @param loginAction
	 *            登录地址
	 */
	private static void printErrorInfo(HttpServletRequest req,
			HttpServletResponse res, String message, String loginAction) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("resourcePath", req.getAttribute("resourcePath"));
		try {
			String returnUrl = SSOWebHelper.toQueryString(req,
					SSOCodeConst.DEFAULT_ENCODING,
					SSOCodeConst.TOKEN_PARAM_NAME);
			params.put("returnUrl",
					URLEncoder.encode(returnUrl, SSOCodeConst.DEFAULT_ENCODING));
		} catch (UnsupportedEncodingException e1) {
			// do nothing
		}

		// 当前请求地址
		params.put("loginAction", loginAction);
		params.put("tips", message);
		String result = SSOWebHelper.getContentFromTemplate(
				"freemarker/ssoMessageTip.ftl", params);
		if (StringUtils.isBlank(result)) {
			result = message;
		}
		try {
			res.getOutputStream().write(result.getBytes());
		} catch (IOException e) {
			logger.error("sso print tip message failed. {}", e);
		}
	}

	/**
	 * 输出错误信息
	 * 
	 * @param req
	 * @param res
	 * @param message
	 *            错误提示
	 * @param loginAction
	 *            登录地址
	 */
	private static void printErrorInfo4EMVC(HttpServletRequest req,
			HttpServletResponse res, String message, String loginAction) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("resourcePath", req.getAttribute("resourcePath"));
		try {
			String returnUrl = SSOWebHelper.toQueryString(req,
					SSOCodeConst.DEFAULT_ENCODING,
					SSOCodeConst.TOKEN_PARAM_NAME);
			params.put("returnUrl",
					URLEncoder.encode(returnUrl, SSOCodeConst.DEFAULT_ENCODING));
		} catch (UnsupportedEncodingException e1) {
			// do nothing
		}

		// 当前请求地址
		params.put("loginAction", loginAction);
		params.put("tips", message);
		String result = SSOWebHelper.getContentFromTemplate(
				"freemarker/ssoMessageTip.ftl", params);
		if (StringUtils.isBlank(result)) {
			result = message;
		}
		try {
			res.getWriter().write(result);
		} catch (IOException e) {
			logger.error("sso print tip message failed. {}", e);
		}
	}

	/**
	 * 得到token参数
	 * 
	 * @param token
	 * @param token
	 */
	public static String getTokenPair(String token) {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(SSOCodeConst.TOKEN_PARAM_NAME)
					.append("=")
					.append(URLEncoder.encode(token,
							SSOCodeConst.DEFAULT_ENCODING));
		} catch (UnsupportedEncodingException e) {
			// do nothing
		}
		return sb.toString();
	}

	/**
	 * 将token参数与url连接
	 * 
	 * @param url
	 * @param token
	 * @return
	 */
	public static String getTokenPairWithUrl(String url, String token) {
		String coatctChar = (url.contains("?")) ? "&" : "?";
		return url + coatctChar + getTokenPair(token);
	}

	/**
	 * 重定向至验证登陆状态
	 * 
	 * @param req
	 * @param res
	 * @param validUrl
	 *            ：验证登陆状态的url
	 */
	private static void sendToValid(HttpServletRequest req,
			HttpServletResponse res, String validUrl) {
		// 判断是否为ajax请求, 在js里面单独处理
		boolean isAjax = BossWebUtils.isAjaxRequest(req);
		if(isAjax){
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		// 清空现有session
		SSOSessionHelper.clearSession(req.getSession());

		// 组织验证地址
		StringBuilder sb_validUrl = new StringBuilder(validUrl);
		if (validUrl.contains("?")) {
			sb_validUrl.append("&");
		} else {
			sb_validUrl.append("?");
		}
		sb_validUrl.append("returnUrl=");

		try {
			// 获取请求地址
			String returnUrl = SSOWebHelper.toQueryString(req,
					SSOCodeConst.DEFAULT_ENCODING,
					SSOCodeConst.TOKEN_PARAM_NAME);
			// fix:req.getRequestURL()总返回http地址，此处处理单点登录校验后https请求自动变成http的问题
			if (validUrl.startsWith(HTTPS_PREFIX)
					&& returnUrl.startsWith(HTTP_PREFIX)) {
				returnUrl = returnUrl.replaceFirst(HTTP_PREFIX, HTTPS_PREFIX);
			}
			logger.info("buildReturnUrl, returnUrl:{}", returnUrl);
			sb_validUrl.append(URLEncoder.encode(returnUrl,
					SSOCodeConst.DEFAULT_ENCODING));
		} catch (UnsupportedEncodingException e) {
			// do nothing
		}
		logger.info("sendRedirect:" + sb_validUrl.toString());
		// 重定向至验证地址
		SSOWebHelper.sendRedirect(res, sb_validUrl.toString());
	}

	/**
	 * 获得token信息
	 * 
	 * @param req
	 * @return
	 */
	public static String getToken(HttpServletRequest req) {
		return req.getParameter(SSOCodeConst.TOKEN_PARAM_NAME);
	}

	/**
	 * 获取SSO校验地址
	 * 
	 * @return
	 */
	private static String getValidUrl() {
		@SuppressWarnings("unchecked")
		ConfigParam<String> param = ConfigurationUtils.getAppConfigParam(Constants.ADMIN_BOSS_SSOVALID);
		return param != null ? param.getValue() : null;
	}

}
