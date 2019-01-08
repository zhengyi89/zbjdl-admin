/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.frame.interceptor.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zbjdl.boss.admin.frame.interceptor.helper.MenuInterceptorHelper;

/**
 * <p>
 * Title: Spring MVC 版菜单拦截器
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
 * @version 0.1, 14-5-21 18:20
 */
public class MenuInterceptor extends BaseInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 是否跳过当前拦截器
		if (isSkipAble() && isAutoSkip(request)) {
			return true;
		}
		return MenuInterceptorHelper.getInstance().before(request, response);
	}
}
