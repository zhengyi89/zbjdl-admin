/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.login;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.frame.security.SecurityManager;
import com.zbjdl.boss.admin.frame.utils.BossWebUtils;
import com.zbjdl.boss.admin.frame.utils.sso.SSOCookieHelper;
import com.zbjdl.boss.admin.frame.utils.sso.SSOSessionHelper;
import com.zbjdl.boss.admin.frame.utils.sso.SSOValidateHelper;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.exception.UserAuthenticationException;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;
import com.zbjdl.common.utils.IpUtils;
import com.zbjdl.common.utils.StringUtils;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpSession;

import java.util.Date;

/**
 * 类名称：LoginAction <br>
 * 类描述： <br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-7-15 下午01:56:52
 */
public class LoginAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 1L;

	private String loginName;

	private String password;

	private String code;// 验证码

	private String returnUrl;

	private String isShowCode; // 是否需要显示验证码

	/**
	 * token参数
	 */
	private String tokenPair;

	@Override
	public String execute() throws Exception {
		isShowCode = "false";
		return SUCCESS;
	}

	public String login() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		String code1 = (String) session.getAttribute(DataDictDTO.SECURITY_CODE);
		if ("true".equals(isShowCode)
				&& (code == null || code1 == null || !code1
				.equalsIgnoreCase(code))) {
			this.addActionMessage("提示：验证码错误。");
			logger.error("验证码错误: 系统生产验证码为[" + code1 + "], 输入验证码为: [" + code
					+ "]");
			session.invalidate();
			isShowCode = "true";
			return "loginJsp";
		}

		UserDTO userDTO;
		try {
			userDTO = remoteService.getUserFacade().userLoginValidate(loginName, password);
		} catch (UserAuthenticationException e) {
			if ("004".equals(e.getDefineCode())) {
				this.addActionMessage("提示：用户不存在。");
			} else if ("003".equals(e.getDefineCode())) {
				this.addActionMessage("提示：用户已冻结。");
			} else {
				this.addActionMessage("提示：密码错误。");
			}
			isShowCode = "true";
			return "loginJsp";
		}

		userDTO.setPassword(password);
		session.invalidate();
		SSOSessionHelper.clearSession(session);
		// 设置 session 为了记录操作日志
		UserInfoUtils.setUserToSession(null, userDTO);

		// 未点击退出直接切换用户时，根据cookie清空之前的登录缓存
		String cookieLoingInfo = SSOCookieHelper.getCookie(ServletActionContext
				.getRequest());
		if (StringUtils.isNotBlank(cookieLoingInfo)) {
			remoteService.getUserLoginFacade().logoutCookieInfo(cookieLoingInfo);
		}

		// 单点登录改造，seesion 里不再存储内容
		String[] data = remoteService.getUserLoginFacade()
				.createTokenAndSecurityInfo(userDTO.getLoginName(),IpUtils.getIpAddr(ServletActionContext.getRequest()));
		String token = data[0]; // 一次性token
		String cookieInfo = data[1]; // cookie信息
		SSOCookieHelper
				.setCookie(ServletActionContext.getResponse(), cookieInfo,
						BossWebUtils.getDomain(ServletActionContext
								.getRequest()));
		// 销毁之前的缓存，避免忘记点退出，权限变化无法
		SecurityManager.destroyUserSecurityCache(userDTO.getUserId());

		this.tokenPair = SSOValidateHelper.getTokenPair(token);
		if ("1".equals(userDTO.getPwdShowNotice()) && userDTO.getPwdLastModifiedTime().getTime() <= new Date().getTime() - 7776000000L) {
			return "pwdModify";
		}

		boolean toPortal = this.isPortalUser(userDTO);
		if (!toPortal && StringUtils.isNotBlank(returnUrl)) {
			logger.debug("redirect to service url: {}.", returnUrl);
			this.tokenPair = SSOValidateHelper.getTokenPairWithUrl(returnUrl, token);
			return "serviceUrl";
		}
		if (toPortal) {
			return "portal";
		}
		return SUCCESS;
	}

	/**
	 * loginName
	 *
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * password
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * code
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * tokenPair
	 *
	 * @return the tokenPair
	 */
	public String getTokenPair() {
		return tokenPair;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getIsShowCode() {
		return isShowCode;
	}

	public void setIsShowCode(String isShowCode) {
		this.isShowCode = isShowCode;
	}

	/**
	 * 是否门户模式，默认为false
	 *
	 * @return true/false
	 */
	private boolean isPortalUser(UserDTO userDTO) {
		try {
			// 所在部门是否为 Portal
			DepartmentDTO departmentDTO = remoteService.getUserFacade().queryDepartmentById(userDTO.getPrimaryDepartmentId());
			return (null != departmentDTO) && departmentDTO.isPortal();
		} catch (Exception e) {
			return false;
		}
	}
}
