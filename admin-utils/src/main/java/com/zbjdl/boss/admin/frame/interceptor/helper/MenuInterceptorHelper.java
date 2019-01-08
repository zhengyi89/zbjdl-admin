package com.zbjdl.boss.admin.frame.interceptor.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zbjdl.boss.admin.frame.security.MenuBuilder;

/**
 * 菜单拦截器辅助类
 *
 * @author：feng
 * @since：2014年11月10日 下午5:24:16
 */
public class MenuInterceptorHelper extends BaseInterceptorHelper {

	private static MenuInterceptorHelper instance;

	private MenuInterceptorHelper() {

	}

	public static MenuInterceptorHelper getInstance() {
		if (instance == null) {
			synchronized (MenuInterceptorHelper.class) {
				if (instance == null) {
					instance = new MenuInterceptorHelper();
				}
			}
		}
		return instance;
	}

	@Override
	public boolean before(HttpServletRequest request,
			HttpServletResponse response, Object... extParams) throws Exception {
		return new MenuBuilder().build(request);
	}
}
