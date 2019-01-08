/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.frame.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.frame.interceptor.helper.OperationLogInterceptorHelper;
import com.zbjdl.boss.admin.frame.security.SecurityManager;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;

/**
 * 类名称：OperationLogInterceptor <br>
 * 类描述：操作日志拦截器<br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-7-22 下午02:46:53
 */
public class OperationLogInterceptor extends BaseInterceptor {
	private static final long serialVersionUID = -1846568738635842514L;

	/**
	 * 是否可跳过
	 */
	@Override
	protected boolean isSkipAble() {
		return false;
	}

	@Override
	public String doIntercept(ActionInvocation invocation) throws Exception {
		FunctionDTO function = SecurityManager
				.getThreadFunction(ServletActionContext.getRequest());
		String result = null;
		// 是否需要记日志只在这个地方
		if (function != null && function.getLogNeeded() != null
				&& function.getLogNeeded()) {
			long startInvokeTime = System.currentTimeMillis();// 开始调用时间
			try {
				HttpServletRequest request = ServletActionContext.getRequest();
				// 先取用户对象再继续执行，否则会导致
				// java.lang.IllegalStateException:
				// Cannot create a session after the response has been committed
				UserDTO userDTO = (UserDTO) request.getSession().getAttribute(
						DataDictDTO.SESSION_USERINFO);
				if (userDTO == null && function.getAuditLevel() > 0) {
					logger.info("操作日志。functionId : " + function.getFunctionId());
				}
				result = invocation.invoke();
				if (userDTO == null) {
					// 重新取一次用户信息
					userDTO = (UserDTO) request.getSession(true).getAttribute(
							DataDictDTO.SESSION_USERINFO);
					if (userDTO == null) {
						logger.info("当前用户为空，不记录操作日志。functionId : "
								+ function.getFunctionId());
						return result;
					}
				}

				HttpServletResponse response = ServletActionContext
						.getResponse();
				OperationLogInterceptorHelper.getInstance().output(request,
						response, userDTO, function, result, startInvokeTime);
			} catch (Exception e) {
				logger.error("", e);
			}
			return result;
		} else {
			return invocation.invoke();
		}
	}
}
