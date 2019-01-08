/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.department.vo;

import com.zbjdl.boss.admin.basic.vo.BasicVO;

/**
 * 
 * 类名称：DeparmentVO <br>
 * 类描述：部门VO对象 <br>
 * 
 * @author：feng
 * @since：2011-7-27 上午11:27:00
 * @version:
 * 
 */
public class DepartmentVO extends BasicVO {

	private static final long serialVersionUID = 1L;

	private Long departmentId;// 部门ID，主键

	private String departmentName;// 部门名称

	private String departmentCode;// 部门编码

	private String departmentDesc;// 部门描述

	private String type;// 编辑类型，添加或更新

	private boolean portal;// 是否为 Portal

	private boolean deptNameUnique;// 部门名称是否唯一

	private boolean deptCodeUnique;// 部门编码是否唯一

	private String password;// 管理员密码
	
	private String functionIds;
	

	/**
	 * departmentId
	 * 
	 * @return the departmentId
	 */

	public Long getDepartmentId() {
		return departmentId;
	}

	/**
	 * 描述： 部门ID
	 * 
	 * @param departmentId
	 *            the departmentId to set
	 */
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * departmentName
	 * 
	 * @return the departmentName
	 */

	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * 描述： 部门名称
	 * 
	 * @param departmentName
	 *            the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * departmentCode
	 * 
	 * @return the departmentCode
	 */

	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * 描述： 部门编码
	 * 
	 * @param departmentCode
	 *            the departmentCode to set
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * departmentDesc
	 * 
	 * @return the departmentDesc
	 */

	public String getDepartmentDesc() {
		return departmentDesc;
	}

	/**
	 * 描述： 部门描述
	 * 
	 * @param departmentDesc
	 *            the departmentDesc to set
	 */
	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
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
	 * 描述： 编辑类型
	 * 
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

    /**
     * 描述： 是否 Portal
     *
     * @return the type
     */
    public boolean isPortal() {
        return portal;
    }

    /**
     * 描述： 是否 Portal
     *
     * @param portal
     *            the type to set
     */
    public void setPortal(boolean portal) {
        this.portal = portal;
    }

    /**
	 * deptNameUnique
	 * 
	 * @return the deptNameUnique
	 */

	public boolean isDeptNameUnique() {
		return deptNameUnique;
	}

	/**
	 * 描述： 部门名称是否唯一
	 * 
	 * @param deptNameUnique
	 *            the deptNameUnique to set
	 */
	public void setDeptNameUnique(boolean deptNameUnique) {
		this.deptNameUnique = deptNameUnique;
	}

	/**
	 * deptCodeUnique
	 * 
	 * @return the deptCodeUnique
	 */

	public boolean isDeptCodeUnique() {
		return deptCodeUnique;
	}

	/**
	 * 描述： 部门编码是否唯一
	 * 
	 * @param deptCodeUnique
	 *            the deptCodeUnique to set
	 */
	public void setDeptCodeUnique(boolean deptCodeUnique) {
		this.deptCodeUnique = deptCodeUnique;
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
	 * 描述： 管理员密码
	 * 
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getFunctionIds() {
		return functionIds;
	}

	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

    
	
	

}
