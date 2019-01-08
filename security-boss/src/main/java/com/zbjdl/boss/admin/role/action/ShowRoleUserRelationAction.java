/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.role.action;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;

/**
 * 
 * 类名称：ShowRoleUserRelation <br>
 * 类描述： 查询某个角色关联的所有用户信息 <br>
 * 
 * @author：feng
 * @since：2011-9-16 下午03:43:29
 * @version:
 * 
 */
public class ShowRoleUserRelationAction extends EmployeeBossBaseAction {
	private static final long serialVersionUID = 3425696554785187819L;

	private Long roleId;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() {
		return SUCCESS;
	}

	/**
	 * roleId
	 * 
	 * @return the roleId
	 */

	public Long getRoleId() {
		return roleId;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
