/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.role.vo;

import java.util.ArrayList;
import java.util.List;

import com.zbjdl.boss.admin.basic.vo.BasicVO;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;

/**
 * 
 * 类名称：RoleVO <br>
 * 类描述： <br>
 * 
 * @author：feng
 * @since：2011-7-28 下午01:22:22
 * @version:
 * 
 */
public class RoleVO extends BasicVO {

	private static final long serialVersionUID = 1L;

	private Long roleId;

	private String roleName;

	private String description;

	private String roleStatus;

	private String password;

	private String type;

	private String roleType;

	private List<String> roleTypes = new ArrayList<String>();

	private String functionIds;

	private boolean roleNameUnique;

	private Long departmentId;

	private List<DepartmentDTO> depts = new ArrayList<DepartmentDTO>();

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

	/**
	 * roleName
	 * 
	 * @return the roleName
	 */

	public String getRoleName() {
		return roleName;
	}

	/**
	 * 描述： 在这里描述属性的含义
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
	 * 描述： 在这里描述属性的含义
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

	public String getRoleStatus() {
		return roleStatus;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param roleStatus
	 *            the roleStatus to set
	 */
	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
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
	 * type
	 * 
	 * @return the type
	 */

	public String getType() {
		return type;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * roleType
	 * 
	 * @return the roleType
	 */

	public String getRoleType() {
		return roleType;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param roleType
	 *            the roleType to set
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	/**
	 * roleTypes
	 * 
	 * @return the roleTypes
	 */

	public List<String> getRoleTypes() {
		return roleTypes;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param roleTypes
	 *            the roleTypes to set
	 */
	public void setRoleTypes(List<String> roleTypes) {
		this.roleTypes = roleTypes;
	}

	/**
	 * functionIds
	 * 
	 * @return the functionIds
	 */

	public String getFunctionIds() {
		return functionIds;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param functionIds
	 *            the functionIds to set
	 */
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

	/**
	 * roleNameUnique
	 * 
	 * @return the roleNameUnique
	 */

	public boolean isRoleNameUnique() {
		return roleNameUnique;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param roleNameUnique
	 *            the roleNameUnique to set
	 */
	public void setRoleNameUnique(boolean roleNameUnique) {
		this.roleNameUnique = roleNameUnique;
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

	/**
	 * depts
	 * 
	 * @return the depts
	 */

	public List<DepartmentDTO> getDepts() {
		return depts;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param depts
	 *            the depts to set
	 */
	public void setDepts(List<DepartmentDTO> depts) {
		this.depts = depts;
	}

}
