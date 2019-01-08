/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.dto;

import com.zbjdl.boss.admin.dto.basic.BaseDTO;
import com.zbjdl.boss.admin.user.enums.DepartmentStatusEnum;

/**
 * 
 * 类名称：DepartmentDTO <br>
 * 类描述：部门信息DTO<br>
 * 
 * @author：feng
 * @since：2011-6-21 上午09:38:43
 * @version: 1.0
 * 
 */
public class DepartmentDTO extends BaseDTO {

	private static final long serialVersionUID = 5644296838070830509L;

	private Long departmentId;// 部门ID，主键

	private String departmentName;// 部门名称

	private String departmentCode;// 部门编码

	private String departmentDesc;// 部门描述

	private boolean isPortal;// 是否 Portal

	private DepartmentStatusEnum departmentStatus;

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
	 * 描述： 部门编码
	 * 
	 * @param departmentCode
	 *            the departmentCode to set
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
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
     * isPortal
     *
     * @return the isPortal
     */
    public boolean isPortal() {
        return isPortal;
    }

    /**
     * 描述： 是否 Portal
     *
     * @param portal
     *            the portal to set
     */
    public void setPortal(boolean portal) {
        isPortal = portal;
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
	 * departmentCode
	 * 
	 * @return the departmentCode
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * departmentDesc
	 * 
	 * @return the departmentDesc
	 */
	public String getDepartmentDesc() {
		return departmentDesc;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	/**
	 * 
	 * 描述： 部门ID
	 * 
	 * @param departmentId
	 */
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * departmentStatus
	 * 
	 * @return the departmentStatus
	 */
	public DepartmentStatusEnum getDepartmentStatus() {
		return departmentStatus;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param departmentStatus
	 *            the departmentStatus to set
	 */
	public void setDepartmentStatus(DepartmentStatusEnum departmentStatus) {
		this.departmentStatus = departmentStatus;
	}

}
