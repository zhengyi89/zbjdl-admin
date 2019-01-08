/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.entity;

import java.util.List;

import com.zbjdl.boss.admin.user.enums.DepartmentStatusEnum;
import com.zbjdl.common.persistence.Entity;
import com.zbjdl.common.utils.EqualUtils;

/**    
 *    
 * 类名称：DepartmentEntity <br>    
 * 类描述：部门实体<br>
 * @author：feng    
 * @since：2011-6-17 下午03:03:12 
 * @version: 1.0
 *     
 */
public class DepartmentEntity implements Entity<Long> {
	
	private static final long serialVersionUID = -8705521478451079876L;

	private Long departmentId;//部门ID，主键
	
	private String departmentCode;//部门编码，唯一索引
	
	private String departmentName;//部门名称
	
	private String departmentDesc;//部门描述
	
	private DepartmentStatusEnum departmentStatus;//部门状态

	private boolean isPortal;//是否 Portal

	private List<UserEntity> userList;//部门所有用户列表

	/**    
	 * 描述： 部门ID，主键
	 * @param departmentId the departmentId to set    
	 */
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	/**    
	 * 描述： 在这里描述属性的含义
	 * @param departmentCode the departmentCode to set    
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**    
	 * 描述： 部门名称
	 * @param departmentName the departmentName to set    
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**    
	 * 描述： 部门描述
	 * @param departmentDesc the departmentDesc to set    
	 */
	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

	/**    
	 * 描述： 部门状态
	 * @param departmentStatus the departmentStatus to set    
	 */
	public void setDepartmentStatus(DepartmentStatusEnum departmentStatus) {
		this.departmentStatus = departmentStatus;
	}

	/**    
	 * 描述： 部门所有用户列表
	 * @param userList the userList to set    
	 */
	public void setUserList(List<UserEntity> userList) {
		this.userList = userList;
	}

	/**    
	 * departmentId    
	 *    
	 * @return  the departmentId   
	 */
	
	public Long getDepartmentId() {
		return departmentId;
	}

	/**    
	 * departmentCode    
	 *    
	 * @return  the departmentCode   
	 */
	
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**    
	 * departmentName    
	 *    
	 * @return  the departmentName   
	 */
	
	public String getDepartmentName() {
		return departmentName;
	}

	/**    
	 * departmentDesc    
	 *    
	 * @return  the departmentDesc   
	 */
	
	public String getDepartmentDesc() {
		return departmentDesc;
	}

	/**    
	 * departmentStatus    
	 *    
	 * @return  the departmentStatus   
	 */
	
	public DepartmentStatusEnum getDepartmentStatus() {
		return departmentStatus;
	}

	/**    
	 * userList    
	 *    
	 * @return  the userList   
	 */
	
	public List<UserEntity> getUserList() {
		return userList;
	}

    public boolean isPortal() {
        return isPortal;
    }

    public void setPortal(boolean portal) {
        isPortal = portal;
    }

    /* (non-Javadoc)
         * @see com.zbjdl.common.persistence.Entity#getId()
         */
	@Override
	public Long getId() {
		return departmentId;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.common.persistence.Entity#setId(java.io.Serializable)    
	 */
	@Override
	public void setId(Long arg0) {
		this.departmentId = arg0;
	}

	/* (non-Javadoc)    
	 * @see java.lang.Object#equals(java.lang.Object)    
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DepartmentEntity) {
			DepartmentEntity entity = (DepartmentEntity) obj;
			if (!EqualUtils.isEqual(entity.getDepartmentCode(), this.getDepartmentCode())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getDepartmentDesc(), this.getDepartmentDesc())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getDepartmentId(), this.getDepartmentId())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getDepartmentName(), this.getDepartmentName())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getDepartmentStatus(), this.getDepartmentStatus())) {
				return false;
			}
			return true;
		}
		return false;
	}

}
