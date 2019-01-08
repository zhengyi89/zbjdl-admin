/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.entity;

import com.zbjdl.common.persistence.Entity;

/**
 * 
 * 类名称：DepartmentAndFunctionRelationEntity <br>
 * 类描述： <br>
 * 
 * @author：feng
 * @since：2011-7-15 下午01:08:08
 * @version:
 * 
 */
public class DepartmentAndFunctionRelationEntity implements Entity<Long> {

	private static final long serialVersionUID = 1L;

	private Long relationId;

	private Long departmentId;

	private Long functionId;

	/**
	 * relationId
	 * 
	 * @return the relationId
	 */

	public Long getRelationId() {
		return relationId;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param relationId
	 *            the relationId to set
	 */
	public void setRelationId(Long relationId) {
		this.relationId = relationId;
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
	 * functionId
	 * 
	 * @return the functionId
	 */

	public Long getFunctionId() {
		return functionId;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param functionId
	 *            the functionId to set
	 */
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zbjdl.common.persistence.Entity#getId()
	 */
	public Long getId() {
		return relationId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zbjdl.common.persistence.Entity#setId(java.io.Serializable)
	 */
	@Override
	public void setId(Long id) {
		this.relationId = id;
	}

}
