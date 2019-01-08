/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.function.entity;

import com.zbjdl.common.persistence.Entity;

/**
 * @author：feng
 * @since：2012-4-11 下午5:09:11
 * @version:
 */
public class FunctionDependenceEntity implements Entity<Long> {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 功能ID
	 */
	private Long functionId;
	/**
	 * 依赖功能ID
	 */
	private Long dependentFunctionId;



	/* (non-Javadoc)    
	 * @see com.zbjdl.common.persistence.Entity#getId()    
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.common.persistence.Entity#setId(java.io.Serializable)    
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
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
	 * @param functionId
	 *            the functionId to set
	 */
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	/**
	 * dependentFunctionId
	 * 
	 * @return the dependentFunctionId
	 */

	public Long getDependentFunctionId() {
		return dependentFunctionId;
	}

	/**
	 * @param dependentFunctionId
	 *            the dependentFunctionId to set
	 */
	public void setDependentFunctionId(Long dependentFunctionId) {
		this.dependentFunctionId = dependentFunctionId;
	}

}
