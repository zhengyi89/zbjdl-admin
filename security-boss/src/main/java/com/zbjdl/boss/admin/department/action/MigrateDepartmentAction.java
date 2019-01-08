/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.department.action;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;

/**
 * <p>Title: 部门迁移功能Action</p>
 * <p>Description: 用户整体迁移一个部门</p>
 * <p>Copyright: Copyright (c)2018</p>
 * <p>Company: 八戒财云</p>
 *
 * @author feng
 * @version 0.1, 13-6-27 上午9:45
 */
public class MigrateDepartmentAction extends EmployeeBossBaseAction {

	/**
	 * 待迁移部门 Id
	 */
	private Long departmentId;

	/**
	 * 拟迁往部门 Id
	 */
	private Long newDepartmentId;

	/**
	 * 是否迁移用户
	 */
	private boolean migrateUser;

	/**
	 * 是否迁移角色
	 */
	private boolean migrateRole;

	/**
	 * 是否迁移功能
	 */
	private boolean migrateFunction;

	public String execute() {
		try {
			remoteService.getSecurityConfigFacade().migrateDepartment(departmentId, newDepartmentId, migrateUser, migrateRole, migrateFunction);
		} catch (Exception e) {
			throw new IllegalArgumentException("迁移部门失败！");
		}
		return SUCCESS;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getNewDepartmentId() {
		return newDepartmentId;
	}

	public void setNewDepartmentId(Long newDepartmentId) {
		this.newDepartmentId = newDepartmentId;
	}

	public boolean isMigrateUser() {
		return migrateUser;
	}

	public void setMigrateUser(boolean migrateUser) {
		this.migrateUser = migrateUser;
	}

	public boolean isMigrateRole() {
		return migrateRole;
	}

	public void setMigrateRole(boolean migrateRole) {
		this.migrateRole = migrateRole;
	}

	public boolean isMigrateFunction() {
		return migrateFunction;
	}

	public void setMigrateFunction(boolean migrateFunction) {
		this.migrateFunction = migrateFunction;
	}
}
