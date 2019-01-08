/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.dto;

import com.zbjdl.boss.admin.dto.basic.BaseDTO;

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
public class ExclusiveFunctionDTO extends BaseDTO {

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
