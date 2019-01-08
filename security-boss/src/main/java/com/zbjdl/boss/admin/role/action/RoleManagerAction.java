/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.role.action;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.department.manager.DepartmentManager;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 类名称：RoleManagerAction <br>
 * 类描述： 角色管理<br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-7-21 下午06:26:30
 */
public class RoleManagerAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = -654419857661216038L;

	private String roleName;

	private String functionName;

	private String roleStatus;

	private Long departmentId;

	private Boolean isSuperAdmin = false;

	private List<DepartmentDTO> departList;

	private Boolean multiDept;

	@Autowired
	private DepartmentManager departmentManager;

	@Override
	public String execute() throws Exception {
		// 改用角色判断
		UserDTO loginUserDTO = UserInfoUtils.getUserFromSession(null);
		isSuperAdmin = loginUserDTO.getIsSuperAdmin() != null
				&& loginUserDTO.getIsSuperAdmin();

		departList = departmentManager.queryAllDepartment();
		if (!isSuperAdmin) {
			// 部门管理员只能查看本部门角色
			departmentId = loginUserDTO.getPrimaryDepartmentId();
		}
		return SUCCESS;
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
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * functionName
	 *
	 * @return the functionName
	 */

	public String getFunctionName() {
		return functionName;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param functionName the functionName to set
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
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
	 * @param roleStatus the roleStatus to set
	 */
	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
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
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Boolean getMultiDept() {
		return multiDept;
	}

	public void setMultiDept(Boolean multiDept) {
		this.multiDept = multiDept;
	}

	public Boolean getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public List<DepartmentDTO> getDepartList() {
		return departList;
	}
}
