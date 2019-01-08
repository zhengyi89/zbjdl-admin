/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.frame.interceptor.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zbjdl.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * <p>
 * Copyright: Copyright (c)2018
 * </p>
 * <p>
 * Company: 八戒财云
 * </p>
 *
 * @author feng
 * @version 0.1, 14-5-21 19:03
 */
public abstract class BaseInterceptor implements HandlerInterceptor {

	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	/**
	 * 如果设置admin-frame-passFlag为以下值则直接进入下一个拦截器
	 */
	private static final String[] PASSFLAG = { "ok", "true" };

	/**
	 * 是否可跳过
	 *
	 * @return
	 */
	protected boolean isSkipAble() {
		return true;
	}

	/**
	 * 判断是否自动跳过
	 *
	 * @return
	 */
	protected boolean isAutoSkip(HttpServletRequest request) {
		String passFlag = request.getParameter("admin-frame-passFlag");
		LOGGER.debug("passFlag: {}", passFlag);
		if (StringUtils.isNotBlank(passFlag)) {
			for (String flag : PASSFLAG) {
				if (StringUtils.equalsIgnoreCase(flag, passFlag)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		LOGGER.debug("离开权限拦截器");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
