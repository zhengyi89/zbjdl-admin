package com.zbjdl.boss.admin.user.dto;

import com.zbjdl.boss.admin.dto.basic.BaseDTO;

/**
 * 登陆用户验证DTO
 * @author：feng    
 * @since：2011-6-21 上午09:36:13 
 * @version:1.0
 */

public class AdminDTO extends BaseDTO{

	private static final long serialVersionUID = -8050165185453381300L;
	
	/**
	 * 当前登陆用户的userId
	 */
	private Long userId;
	
	/**
	 * 当前登陆用户的登录名
	 */
	private String loginName;
	
	/**
	 * 当前登陆用户的登陆密码
	 */
	private String password;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
