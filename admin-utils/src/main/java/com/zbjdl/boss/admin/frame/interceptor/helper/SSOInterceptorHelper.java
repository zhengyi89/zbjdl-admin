/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.frame.interceptor.helper;

import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.facade.UserFacade;
import com.zbjdl.boss.admin.frame.security.CSPDecorate;
import com.zbjdl.boss.admin.frame.security.SecurityManager;
import com.zbjdl.boss.admin.frame.service.impl.RemoteServiceImpl;
import com.zbjdl.boss.admin.frame.utils.sso.SSOSessionHelper;
import com.zbjdl.boss.admin.frame.utils.sso.SSOValidateHelper;
import com.zbjdl.boss.admin.user.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类描述：单点登录拦截器辅助类
 *
 * @author：feng
 * @since：2014年11月10日 下午5:24:16
 */
public class SSOInterceptorHelper extends BaseInterceptorHelper {

	private UserFacade userFacade = RemoteServiceImpl.getInstance()
			.getUserFacade();

	private static SSOInterceptorHelper instance;

	private SSOInterceptorHelper() {

	}

	public static SSOInterceptorHelper getInstance() {
		if (instance == null) {
			synchronized (SSOInterceptorHelper.class) {
				if (instance == null) {
					instance = new SSOInterceptorHelper();
				}
			}
		}
		return instance;
	}

	@Override
	public boolean before(HttpServletRequest request,
			HttpServletResponse response, Object... extParams) throws Exception {
		SecurityManager.handleCache(request);
        //CSP HEADER
        CSPDecorate.addCSPHeader(request, response);

		if (SSOValidateHelper.ssoLoginValid(request, response)) {
			// 设置三代系统需要的session信息
			if (request.getSession().getAttribute(DataDictDTO.SESSION_USERINFO) == null) {
				UserDTO userDto = userFacade
						.queryUserByLoginName(SSOSessionHelper
								.getUserId(request.getSession()));
				request.getSession().setAttribute(DataDictDTO.SESSION_USERINFO,
						userDto);
			}
			return true;
		}
		return false;
	}
}
