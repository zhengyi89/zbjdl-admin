/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.role.dto;

import java.util.ArrayList;
import java.util.List;

import com.zbjdl.boss.admin.dto.basic.BaseDTO;
import com.zbjdl.boss.admin.role.enums.RoleStatusEnum;
import com.zbjdl.boss.admin.role.enums.RoleTypeEnum;

/**
 * 
 * 类名称：RoleDTO <br>
 * 类描述： <br>
 * 
 * @author：feng
 * @since：2011-7-5 下午01:35:51
 * @version:
 * 
 */
public class RoleDTO extends BaseDTO {

	private static final long serialVersionUID = -7519240649920068687L;

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
	 * 分配的功能列表
	 */
	private List<Long> functionIds = new ArrayList<Long>();

	private Long departmentId;

	/**
	 * roleId
	 * 
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * 描述： 角色id
	 * 
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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
	 * 描述： 角色名
	 * 
	 * @param roleName
	 *            the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	 * 描述： 角色描述
	 * 
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * roleStatus
	 * 
	 * @return the roleStatus
	 */
	public RoleStatusEnum getRoleStatus() {
		return roleStatus;
	}

	/**
	 * 描述： 角色状态
	 * 
	 * @param roleStatus
	 *            the roleStatus to set
	 */
	public void setRoleStatus(RoleStatusEnum roleStatus) {
		this.roleStatus = roleStatus;
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
	 * functionIds
	 * 
	 * @return the functionIds
	 */

	public List<Long> getFunctionIds() {
		return functionIds;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param functionIds
	 *            the functionIds to set
	 */
	public void setFunctionIds(List<Long> functionIds) {
		this.functionIds = functionIds;
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
	
	
	
	
	

}
