/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.convert.impl;

import java.util.List;

import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.function.dto.ExclusiveFunctionDTO;
import com.zbjdl.boss.admin.function.entity.ExclusiveFunctionEntity;

/**
 * 
 * 类名称：FunctionConvert <br>
 * 类描述：FunctionDTO和FunctionEntity转换器 <br>
 * 
 * @author：feng
 * @since：2011-7-5 下午06:08:36
 * @version:
 * 
 */
public class ExclusiveFunctionConvert implements Convert<ExclusiveFunctionDTO, ExclusiveFunctionEntity> {

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.convert.Convert#convert(com.zbjdl.boss.admin.dto.basic.BaseDTO)    
	 */
	@Override
	public ExclusiveFunctionEntity convert(ExclusiveFunctionDTO dto) {
		if(dto == null){
			return null;
		}
		ExclusiveFunctionEntity entity = new ExclusiveFunctionEntity();
		org.springframework.beans.BeanUtils.copyProperties(dto, entity);
		return entity;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.convert.Convert#convert(com.zbjdl.common.persistence.Entity)    
	 */
	@Override
	public ExclusiveFunctionDTO convert(ExclusiveFunctionEntity entity) {
		if(entity == null){
			return null;
		}
		ExclusiveFunctionDTO dto = new ExclusiveFunctionDTO();
		org.springframework.beans.BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	@Override
	public List<ExclusiveFunctionDTO> convertToDtos(
			List<ExclusiveFunctionEntity> es) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExclusiveFunctionEntity> convertToEntities(
			List<ExclusiveFunctionDTO> ts) {
		// TODO Auto-generated method stub
		return null;
	}

}
