/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.logout;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.frame.security.SecurityManager;
import com.zbjdl.boss.admin.frame.utils.sso.SSOCookieHelper;
import com.zbjdl.boss.admin.frame.utils.sso.SSOSessionHelper;
import com.zbjdl.boss.admin.user.dto.UserDTO;

/**
 * 
 * 类名称：Logout <br>
 * 类描述： 登出Action <br>
 * 
 * @author：feng
 * @since：2011-8-1 上午10:12:10
 * @version:
 * 
 */
public class LogoutAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 8701605020677935564L;

	@Override
	public String execute() throws Exception {
		try {
			//  单点登录改造
			remoteService.getUserLoginFacade()
					.logoutCookieInfo(SSOCookieHelper.getCookie(ServletActionContext
							.getRequest()));
			SSOCookieHelper.removeCookie(ServletActionContext.getRequest(),
					ServletActionContext.getResponse());

			// 清除session
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			if (session != null) {
				UserDTO userDTO = (UserDTO) session
						.getAttribute(DataDictDTO.SESSION_USERINFO);
				if (userDTO != null) {
					SecurityManager.destroyUserSecurityCache(userDTO
							.getUserId());
				}
				SSOSessionHelper.clearSession(session);
				session.invalidate();
			}
		} catch (Exception e) {
			// 出现异常也不往外抛出，否则会到异常页面而不是登录页
			logger.error("退出系统时出现异常：", e);
		}
		return SUCCESS;
	}

}
