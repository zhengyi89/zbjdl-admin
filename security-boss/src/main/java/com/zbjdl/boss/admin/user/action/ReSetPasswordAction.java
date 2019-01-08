/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.user.dto.AdminDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;

/**
 * 
 * 类名称：ReSetPasswordAction <br>
 * 类描述： 重置密码 <br>
 * 
 * @author：feng
 * @since：2011-7-28 上午10:10:39
 * @version:
 * 
 */
public class ReSetPasswordAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = -5694390727816120777L;

	private Long userId;

	public String execute() throws Exception {
		try {
			HttpSession session = getMVCFacade().getHttpSession();
			HttpServletRequest request = ServletActionContext.getRequest();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							request.getParameter("password"));
			AdminDTO adminDTO = new AdminDTO();
			adminDTO.setLoginName(user.getLoginName());
			adminDTO.setPassword(user.getPassword());
			adminDTO.setUserId(user.getUserId());
			if (!passwordRightOrNot) {
				this.errMsg = "密码错误!";
			} else {
				remoteService.getUserFacade().resetPassword(userId, "");
			}
			// result = "OK";
		} catch (Exception e) {
			handleException(e);
		}
		return SUCCESS;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
