/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.function.entity;

/**
 * @author：feng
 * @since：2013-5-30 下午03:09:48
 * @version:
 */
public class PortalMenu extends MenuEntity {
	private static final long serialVersionUID = -1024705052178238921L;
	/**
	 * 菜单所属功能对应的部门ID
	 */
	private Long departmentId;
	
	/**
	 * 菜单目标地址
	 */
	private String functionUrl;

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getFunctionUrl() {
		return functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}
}
