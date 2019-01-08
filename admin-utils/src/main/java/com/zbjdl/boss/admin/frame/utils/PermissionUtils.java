/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.frame.utils;

import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.frame.security.SecurityManager;
import com.zbjdl.boss.admin.frame.service.RemoteService;
import com.zbjdl.boss.admin.frame.service.impl.RemoteServiceImpl;
import com.zbjdl.boss.admin.user.dto.UserDTO;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

/**
 * 细粒度的权限控制器，可在jsp页面直接判断是否有权限访问某个action
 *
 * @author：feng
 * @since：2013-8-8 上午10:02:26
 * @version:
 */
public class PermissionUtils {

	private static RemoteService bossRemoteCallService = RemoteServiceImpl
			.getInstance();

	private HttpServletRequest request;

	/**
	 * 权限缓存，单个request生命周期内有效<br>
	 * 主要用于一个循环内每次都判断是否有某个action权限<br>
	 * 例如：查询组件一次查询出20条记录，这些记录都有一个共同的操作，如果用户有权限则在数据后面显示操作按钮
	 */
	private Map<String, Boolean> cachedPermit = new HashMap<String, Boolean>();

	public PermissionUtils() {
	}

	public PermissionUtils(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 取得当前用户
	 *
	 * @return
	 */
	private UserDTO getCurrentUser() {
		if (request == null) {
			request = ServletActionContext.getRequest();
		}
		return (UserDTO) request.getSession().getAttribute(
				DataDictDTO.SESSION_USERINFO);
	}

	/**
	 * 判断是否已登录
	 *
	 * @return
	 */
	public boolean isLogin() {
		return getCurrentUser() != null;
	}

	/**
	 * 判断是否有权限访问某个功能
	 *
	 * @param function
	 *            功能对应的URI
	 * @return
	 */
	public boolean hasPermit(String function) {
		UserDTO userDTO = getCurrentUser();
		// session存储的功能不再包含后缀 且均为小写 此处需要过滤
		return hasPermit(userDTO, function.toLowerCase().replaceAll("\\.[^\\.^/]+$", ""));
	}

	/**
	 * 判断是否有权限访问某个功能
	 *
	 * @param userDTO
	 *            SESSION中的用户对象
	 * @param action
	 *            功能对应的URI
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean hasPermit(UserDTO userDTO, String action) {
		if (request == null || userDTO == null || StringUtils.isBlank(action)) {
			return false;
		}

		if (cachedPermit.get(action) != null && cachedPermit.get(action)) {
			return true;
		}

		HttpSession session = request.getSession();
		Map<String, Long> functionMap = (Map<String, Long>) session
				.getAttribute(DataDictDTO.SESSION_USER_FUNCTION);
		if (functionMap == null) {
			// 使用 SecurityManager 中的方法来更新 Session 中的用户功能Map
			SecurityManager.checkCurrentThreadPermit(request, userDTO.getUserId());
			//functionMap = new HashMap<Long, String>();
			//List<FunctionDTO> functionList = bossRemoteCallService
			//		.getSecurityConfigFacade().queryFunctionByUser(
			//				userDTO.getUserId());
			//for (FunctionDTO function : functionList) {
			//	functionMap.put(function.getFunctionId(),
			//			function.getFunctionUrl());
			//}
			//session.setAttribute(DataDictDTO.SESSION_USER_FUNCTION, functionMap);
		}

		for (String url : functionMap.keySet()) {
			// 如果有权限，直接返回
			if (StringUtils.contains(url, action)) {
				cachedPermit.put(action, true);
				return true;
			}
		}
		cachedPermit.put(action, false);
		return false;
	}
}
