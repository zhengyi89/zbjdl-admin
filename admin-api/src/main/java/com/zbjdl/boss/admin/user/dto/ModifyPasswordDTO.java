package com.zbjdl.boss.admin.user.dto;

import com.zbjdl.boss.admin.dto.basic.BaseDTO;

/**
 * 类名称：ModifyPasswordDTO <br>    
 * 类描述：重置密码Dto，用于重置密码接口的输入输出<br>
 * @author：feng    
 * @since：2011-6-20 下午06:31:09 
 * @version:1.0
 */

public class ModifyPasswordDTO extends BaseDTO {

	private static final long serialVersionUID = -2927094257582590621L;
	
	/**
	 * 登陆名
	 */
	private String loginName;
	/**
	 * 原密码
	 */
	private String originalPassword;
	
	/**
	 * 新密码
	 */
	private String newPassword;
	
	/**
	 * 确认新密码
	 */
	private String confirmPassword;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getOriginalPassword() {
		return originalPassword;
	}

	public void setOriginalPassword(String originalPassword) {
		this.originalPassword = originalPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
