/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.action;

import java.util.List;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.user.dto.UserDTO;

/**
 * 
 * 类名称：SuperAdminSignInAction <br>
 * 类描述： 系管员签到登录 <br>
 * 
 * @author：feng
 * @since：2011-7-26 下午10:45:33
 * @version:
 * 
 */
public class SuperAdminSignInAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 2042130602379330558L;

	private String superPassword;// Admin密码

	private String loginName;// 当前登陆用户名

	private String password;// 当前登陆密码

	private String code;// 验证码

	public String showSignIn() {
		return SUCCESS;
	}

	public String superAdminSignIn() {
		// 登录页面已合并
		return SUCCESS;

	}

	public boolean userIsExist() {
		UserDTO validateUser = new UserDTO();
		validateUser.setLoginName(loginName);
		List<UserDTO> list = remoteService.getUserFacade().queryUser(
				validateUser);
		if (list == null || list.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * superPassword
	 * 
	 * @return the superPassword
	 */

	public String getSuperPassword() {
		return superPassword;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param superPassword
	 *            the superPassword to set
	 */
	public void setSuperPassword(String superPassword) {
		this.superPassword = superPassword;
	}

	/**
	 * loginName
	 * 
	 * @return the loginName
	 */

	public String getLoginName() {
		return loginName;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param loginName
	 *            the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * password
	 * 
	 * @return the password
	 */

	public String getPassword() {
		return password;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * code
	 * 
	 * @return the code
	 */

	public String getCode() {
		return code;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

}
