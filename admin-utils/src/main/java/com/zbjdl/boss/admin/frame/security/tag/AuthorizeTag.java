/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.frame.security.tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.frame.security.SecurityManager;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.common.utils.StringUtils;

/**
 * 
 * 类名称：AuthorizeTag <br/>
 * 类描述：权限认证标签<br/>
 * 
 * @author：feng
 * @since：2012-2-20 上午11:04:23
 * @version:
 */
@SuppressWarnings("serial")
public class AuthorizeTag extends TagSupport {
	/** 应用名，如 ：employee-boss */
	private String appName;
	/** 命名空间，如 ：trade */
	private String actionNamespace;
	/** Action名称，如：limitsList */
	private String actionName;
	/** action的后缀名 */
	private String actionSuffix = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		if (StringUtils.isBlank(actionName)) {
			// 无需权限控制
			return EVAL_BODY_INCLUDE;
		}
		// session为null，无权限
		if (null == pageContext.getSession()) {
			return SKIP_BODY;
		}

		UserDTO userDTO = (UserDTO) pageContext.getSession().getAttribute(
				DataDictDTO.SESSION_USERINFO);
		if (null == userDTO) {
			//未登陆用户
			return SKIP_BODY;
		}

		// 权限验证
		if (SecurityManager.checkIfAllGranted(userDTO.getUserId(),
				assemAuthorities())) {
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;

	}

	/**
	 * 组织权限认证字符串
	 * 
	 * @return
	 */
	public List<String> assemAuthorities() {
		StringBuilder authoriseBuilder = new StringBuilder();
		// 组织应用名
		if (StringUtils.isNotBlank(appName)) {
			authoriseBuilder.append(appName.trim());
		} else {
			HttpServletRequest httpServletRequest = (HttpServletRequest) pageContext
					.getRequest();
			authoriseBuilder.append(httpServletRequest.getContextPath()
					.replace("/", ""));
		}

		// 组织命名空间
		if (StringUtils.isNotBlank(actionNamespace)) {
			authoriseBuilder.append("/");
			authoriseBuilder.append(actionNamespace.trim());
		}
		// 组织Action名称
		if (StringUtils.isNotBlank(actionName)) {
			authoriseBuilder.append("/");
			authoriseBuilder.append(actionName.trim());
		}
		// 组织Action后缀
		if (StringUtils.isNotBlank(actionSuffix)) {
			authoriseBuilder.append(".");
			authoriseBuilder.append(actionSuffix.trim());
		}
		return java.util.Arrays.asList(authoriseBuilder.toString());
	}

	/**
	 * appName
	 * 
	 * @return the appName
	 */

	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * actionNamespace
	 * 
	 * @return the actionNamespace
	 */

	public String getActionNamespace() {
		return actionNamespace;
	}

	/**
	 * @param actionNamespace
	 *            the actionNamespace to set
	 */
	public void setActionNamespace(String actionNamespace) {
		this.actionNamespace = actionNamespace;
	}

	/**
	 * actionName
	 * 
	 * @return the actionName
	 */

	public String getActionName() {
		return actionName;
	}

	/**
	 * @param actionName
	 *            the actionName to set
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * actionSuffix
	 * 
	 * @return the actionSuffix
	 */

	public String getActionSuffix() {
		return actionSuffix;
	}

	/**
	 * @param actionSuffix
	 *            the actionSuffix to set
	 */
	public void setActionSuffix(String actionSuffix) {
		this.actionSuffix = actionSuffix;
	}

}
