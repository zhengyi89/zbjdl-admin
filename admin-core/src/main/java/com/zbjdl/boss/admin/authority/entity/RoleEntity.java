/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.authority.entity;

import com.zbjdl.boss.admin.role.enums.RoleStatusEnum;
import com.zbjdl.boss.admin.role.enums.RoleTypeEnum;
import com.zbjdl.common.persistence.Entity;

/**
 * 
 * 类名称：Role <br>
 * 类描述： 用户角色 <br>
 * 
 * @author：feng
 * @since：2011-6-30 下午07:09:19
 * @version:
 * 
 */
public class RoleEntity implements Entity<Long> {

	private static final long serialVersionUID = -2193339556944495676L;

	private Long roleId;
	/**
	 * 角色名
	 */
	private String roleName;
	/**
	 * 角色描述
	 */
	private String description;
	/**
	 * 角色状态
	 */
	private RoleStatusEnum roleStatus;

	/**
	 * 角色类型
	 */
	private RoleTypeEnum roleType;

	/**
	 * 角色所属部门Id
	 */
	private Long departmentId;

	/**
	 * 描述： 角色ID
	 * 
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * 描述： 角色名
	 * 
	 * @param roleName
	 *            the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 描述： 角色描述
	 * 
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * status
	 * 
	 * @return the status
	 */

	public RoleStatusEnum getRoleStatus() {
		return roleStatus;
	}

	/**
	 * 描述： 角色状态
	 * 
	 * @param status
	 *            the status to set
	 */
	public void setRoleStatus(RoleStatusEnum status) {
		this.roleStatus = status;
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
	 * roleName
	 * 
	 * @return the roleName
	 */

	public String getRoleName() {
		return roleName;
	}

	/**
	 * description
	 * 
	 * @return the description
	 */

	public String getDescription() {
		return description;
	}

	/**
	 * roleType
	 * 
	 * @return the roleType
	 */

	public RoleTypeEnum getRoleType() {
		return roleType;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param roleType
	 *            the roleType to set
	 */
	public void setRoleType(RoleTypeEnum roleType) {
		this.roleType = roleType;
	}

	/**
	 * departmentId
	 * 
	 * @return the departmentId
	 */

	public Long getDepartmentId() {
		return departmentId;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param departmentId
	 *            the departmentId to set
	 */
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zbjdl.common.persistence.Entity#getId()
	 */
	@Override
	public Long getId() {
		return roleId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zbjdl.common.persistence.Entity#setId(java.io.Serializable)
	 */
	@Override
	public void setId(Long arg0) {
		this.roleId = arg0;
	}

}
