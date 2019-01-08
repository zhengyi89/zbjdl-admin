/**
 *
\ * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.menu.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;

/**
 *
 * 类名称：DeleteMenuAction <br>
 * 类描述： 删除菜单动作类<br>
 *
 * @author：feng
 * @since：2011-7-13 下午06:40:38
 * @version:
 *
 */
public class DeleteMenuAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 删除菜单
	 */
	public String execute() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			logger.debug(request.getParameter("menuId"));
			HttpSession session = getMVCFacade().getHttpSession();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							request.getParameter("password"));
			if (!passwordRightOrNot) {
				this.errMsg = "密码错误!";
				return SUCCESS;
			} else {
				remoteService.getSecurityConfigFacade().deleteMenu(
						Long.parseLong(request.getParameter("menuId")));
			}
		} catch (Exception e) {
			handleException(e);
		}

		return SUCCESS;
	}

}
