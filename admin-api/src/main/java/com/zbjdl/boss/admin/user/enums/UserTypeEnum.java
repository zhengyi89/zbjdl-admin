/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.user.enums;

/**
 * @author：feng
 * @since：2012-4-5 上午10:36:48
 * @version:
 */
public enum UserTypeEnum {
	/**
	 * 系统管理员
	 */
	SYSTEM_MANAGER(-1001L, "系统管理员角色"),
	/**
	 * 部门管理员
	 */
	DEPARTMENT_MANAGER(-1000L, "部门管理员角色"),
	/**
	 * 操作员
	 */
	OPERATOR(-999L, "基本角色");

	private UserTypeEnum(Long roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
	}

	/** 角色ID */
	private Long roleId;

	/** 角色名称 */
	private String roleName;

	/**    
	 * roleId    
	 *    
	 * @return  the roleId   
	 */
	
	public Long getRoleId() {
		return roleId;
	}

	/**    
	 * roleName    
	 *    
	 * @return  the roleName   
	 */
	
	public String getRoleName() {
		return roleName;
	}


}
