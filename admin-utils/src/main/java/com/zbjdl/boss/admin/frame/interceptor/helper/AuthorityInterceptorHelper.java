/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.frame.interceptor.helper;

import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.frame.security.SecurityManager;
import com.zbjdl.boss.admin.frame.utils.BossFreemarkerUtils;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.common.utils.StringUtils;
import com.zbjdl.common.utils.config.ConfigParam;
import com.zbjdl.common.utils.config.ConfigurationUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 权限鉴权拦截器辅助类
 *
 * @author：feng
 * @since：2014年11月10日 下午5:24:16
 * @version:
 */
public class AuthorityInterceptorHelper extends BaseInterceptorHelper {

	private static AuthorityInterceptorHelper instance;

	private AuthorityInterceptorHelper() {

	}

	public static AuthorityInterceptorHelper getInstance() {
		if (instance == null) {
			synchronized (AuthorityInterceptorHelper.class) {
				if (instance == null) {
					instance = new AuthorityInterceptorHelper();
				}
			}
		}
		return instance;
	}

	@Override
	public boolean before(HttpServletRequest request,
			HttpServletResponse response, Object... extParams) throws Exception {
		try {
			// 判断访问的是否是根目录
			response.setCharacterEncoding("UTF-8");
			String toAsk = request.getRequestURI();
			String webRoot = request.getContextPath() + "/";
			@SuppressWarnings("unchecked")
			ConfigParam<String> param = ConfigurationUtils
					.getAppConfigParam("employee_boss_url");
			String cssUrl = param.getValue();
			// 禁止访问子系统跟路径，默认为没有权限页面。
			if (webRoot.equals(toAsk)) {
				output(request, response, cssUrl);
				return false;
			}

			// 读取session，检查是否存在用户信息的值
			UserDTO userDTO = (UserDTO) request.getSession().getAttribute(
					DataDictDTO.SESSION_USERINFO);

			String ctxPath = request.getContextPath();

			if (userDTO == null) {
				output(request, response, ctxPath);
				return false;
			} else {
				// 如果不是登录并且没有权限
				if (!request.getRequestURI().toLowerCase().contains("login")
						&& !SecurityManager.checkCurrentThreadPermit(request, userDTO.getUserId())) {
					response.setStatus(403);
					// 检查是否为ajax请求
					// 跳过action方法调用
					output(request, response, ctxPath);
					return false;
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return true;
	}

	/**
	 * 描述： 生成业务异常画面
	 */
	@Override
	public void output(HttpServletRequest request,
			HttpServletResponse response, Object... messages) throws Exception {
		PrintWriter out = response.getWriter();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("resourcePath", request.getAttribute("resourcePath"));
		String result = BossFreemarkerUtils.getContentFromTemplate(
				"freemarker/authoritytemplate.ftl", params);
		if (StringUtils.isBlank(result)) {
			result = "对不起，您没有权限访问该页面。";
		}
		out.println(result);
	}
}
