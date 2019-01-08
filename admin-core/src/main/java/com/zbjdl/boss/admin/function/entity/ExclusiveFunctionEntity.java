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
 * 类名称：ExclusiveFunctionEntity <br>
 * 类描述：互斥功能实体<br>
 * 
 * @author：feng
 * @since：2011-7-1 上午11:02:36
 * @version:
 * 
 */
public class ExclusiveFunctionEntity implements Entity<Long> {

	private static final long serialVersionUID = -845675134174747085L;
	
	private Long id;//主键

	private Long functionId;// 功能ID
	
	private Long exclusiveId;//冲突功能ID

	/**
	 * 描述： 功能ID
	 * 
	 * @param functionId
	 *            the functionId to set
	 */
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	/**
	 * 描述： 冲突功能ID
	 * 
	 * @param functionId
	 *            the functionId to set
	 */
	public void setExclusiveId(Long exclusiveId) {
		this.exclusiveId = exclusiveId;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public Long getExclusiveId() {
		return exclusiveId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zbjdl.common.persistence.Entity#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zbjdl.common.persistence.Entity#setId(java.io.Serializable)
	 */
	@Override
	public void setId(Long arg0) {
		this.id = arg0;
	}

}
