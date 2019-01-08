/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.user.action;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.ModifyPasswordDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;
import com.zbjdl.common.utils.StringUtils;

import org.apache.struts2.json.annotations.JSON;

import javax.servlet.http.HttpSession;

import java.util.*;

/**
 * 类名称：ModifyPasswordAction <br>
 * 类描述： 修改密码<br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-7-26 下午09:48:31
 */
public class ModifyPasswordAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 1548453161522284285L;

	private static final long magicNum = 125004628000l;

	private String originalPassword;// 原密码
	private String newPassword;// 新密码
	private String confirmPassword;// 确认密码

	private String message;

	private boolean firstLogin;

	private boolean toPortal;

	private Map<String, Object> sessionAttr;

	@Override
	public String execute() throws Exception {
		HttpSession session = getMVCFacade().getHttpSession();
		if (StringUtils.isBlank(message)) {
			UserDTO userDTO = UserInfoUtils.getUserFromSession(session);
			firstLogin = magicNum == userDTO.getPwdLastModifiedTime().getTime();
			DepartmentDTO departmentDTO = remoteService.getUserFacade().queryDepartmentById(userDTO.getPrimaryDepartmentId());
			toPortal = (null != departmentDTO) && departmentDTO.isPortal();
			return SUCCESS;
		} else {
			String[] attrNames = session.getValueNames();
			sessionAttr = new HashMap();
			for (String attrName : attrNames) {
				sessionAttr.put(attrName, session.getAttribute(attrName));
			}
			return "json";
		}
	}

	public String modifyPwd() {
		ModifyPasswordDTO modifyPasswordDTO = new ModifyPasswordDTO();
		UserDTO loginUser = UserInfoUtils.getUserFromSession(null);
		modifyPasswordDTO.setLoginName(loginUser.getLoginName());
		modifyPasswordDTO.setOriginalPassword(originalPassword);
		modifyPasswordDTO.setNewPassword(newPassword);
		modifyPasswordDTO.setConfirmPassword(confirmPassword);

		// 新密码与确认密码不相同
		if (!newPassword.equals(confirmPassword)) {
			message = "newpassword_confirmpassword_notsame";
			return SUCCESS;
		}

		// 原始密码与新密码相同
		if (originalPassword.equals(newPassword)) {
			message = "password_same";
			return SUCCESS;
		}

		// 新密码与登录名相同
		if (newPassword.endsWith(loginUser.getLoginName())) {
			message = "password_name_same";
			return SUCCESS;
		}

		try {
			if(remoteService.getUserFacade().updatePassword(modifyPasswordDTO)) {
				message = "success";
			}
			else {
				message = "password_error";
			}
		}
		catch (Exception e) {
			LOG.warn("{} 修改密码时报错：{}", loginUser.getLoginName(), e.getMessage());
			message = e.getMessage();
		}
		return SUCCESS;
	}

	/**
	 * originalPassword
	 *
	 * @return the originalPassword
	 */
	@JSON(serialize = false)
	public String getOriginalPassword() {
		return originalPassword;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param originalPassword the originalPassword to set
	 */
	public void setOriginalPassword(String originalPassword) {
		this.originalPassword = originalPassword;
	}

	/**
	 * newPassword
	 *
	 * @return the newPassword
	 */
	@JSON(serialize = false)
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * confirmPassword
	 *
	 * @return the confirmPassword
	 */
	@JSON(serialize = false)
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * message
	 *
	 * @return the message
	 */
	@JSON
	public String getMessage() {
		return message;
	}

	public boolean isFirstLogin() {
		return firstLogin;
	}

	public boolean isToPortal() {
		return toPortal;
	}

	public Map<String, Object> getSessionAttr() {
		return sessionAttr;
	}

}
