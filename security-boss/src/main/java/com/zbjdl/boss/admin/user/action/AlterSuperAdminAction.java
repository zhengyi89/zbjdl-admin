/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.action;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;

/**
 * 
 * 类名称：AlterSuperAdmin <br>
 * 类描述： 管理员签到变更<br>
 * 
 * @author：feng
 * @since：2011-7-27 下午04:48:46
 * @version:
 * 
 */
public class AlterSuperAdminAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = -6072162436368101752L;

	private String loginName;

	private String superPassword;

	@Override
	public String execute() throws Exception {

		return SUCCESS;
	}

	public String alterSuperAdmin() {
		// remoteService.getUserFacade().alterSuperAdmin(loginName,
		// superPassword);
		return SUCCESS;
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

}
