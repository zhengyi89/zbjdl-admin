/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.frame.interceptor.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.frame.interceptor.helper.OperationLogInterceptorHelper;
import com.zbjdl.boss.admin.frame.security.SecurityManager;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;

/**
 * <p>
 * Title: 操作日志拦截器
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
public class OperationLogInterceptor extends BaseInterceptor {

	private Long startInvokeTime;

	/**
	 * 是否可跳过
	 */
	@Override
	protected boolean isSkipAble() {
		return false;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 是否跳过当前拦截器
		if (isSkipAble() && isAutoSkip(request)) {
			return true;
		}
		startInvokeTime = System.currentTimeMillis();

		LOGGER.debug("进入日志拦截器");
		FunctionDTO function = SecurityManager.getThreadFunction(request);
		// 是否需要记日志只在这个地方
		if (function != null && function.getLogNeeded() != null
				&& function.getLogNeeded()) {
			try {
				// 先取用户对象再继续执行，否则会导致
				// java.lang.IllegalStateException:
				// Cannot create a session after the response has been committed
				UserDTO userDTO = (UserDTO) request.getSession().getAttribute(
						DataDictDTO.SESSION_USERINFO);
				if (userDTO == null && function.getAuditLevel() > 0) {
					LOGGER.info("操作日志。functionId : " + function.getFunctionId());
				}
			} catch (Exception e) {
				LOGGER.error("", e);
			}
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		FunctionDTO function = SecurityManager.getThreadFunction(request);
		String result = "SUCCESS";
		// 是否需要记日志只在这个地方
		if (function != null && function.getLogNeeded() != null
				&& function.getLogNeeded()) {
			try {
				// 先取用户对象再继续执行，否则会导致
				// java.lang.IllegalStateException:
				// Cannot create a session after the response has been committed
				UserDTO userDTO = (UserDTO) request.getSession().getAttribute(
						DataDictDTO.SESSION_USERINFO);
				if (userDTO == null) {
					// 重新取一次用户信息
					userDTO = (UserDTO) request.getSession(true).getAttribute(
							DataDictDTO.SESSION_USERINFO);
					if (userDTO == null) {
						LOGGER.info("当前用户为空。functionId : "
								+ function.getFunctionId());
					}
				}

				OperationLogInterceptorHelper.getInstance().output(request,
						response, userDTO, function, result, startInvokeTime);
			} catch (Exception e) {
				LOGGER.error("", e);
			}
		}
	}
}
