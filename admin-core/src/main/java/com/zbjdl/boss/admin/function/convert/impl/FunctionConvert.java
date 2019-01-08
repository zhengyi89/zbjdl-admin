/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.convert.impl;

import java.util.List;

import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.function.entity.FunctionEntity;
import com.zbjdl.boss.admin.utils.StringBooleanUtil;

/**
 * 
 * 类名称：FunctionConvert <br>
 * 类描述：FunctionDTO和FunctionEntity转换器 <br>
 * 
 * @author feng
 * @since 2011-7-5 下午06:08:36
 * @version 1.0.0
 * 
 */
public class FunctionConvert implements Convert<FunctionDTO, FunctionEntity> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.convert.Convert#convert(com.zbjdl.boss.admin.dto.basic.BaseDTO)
	 */
	@Override
	public FunctionEntity convert(FunctionDTO functionDTO) {
		if (functionDTO == null) {
			return null;
		}
		FunctionEntity functionEntity = new FunctionEntity();
		functionEntity.setFunctionId(functionDTO.getFunctionId());
		functionEntity.setFunctionName(functionDTO.getFunctionName());
		functionEntity.setKeyWord(functionDTO.getKeyWord());
		functionEntity.setTag(functionDTO.getTag());
		functionEntity.setFunctionUrl(functionDTO.getFunctionUrl());
		functionEntity.setPreFunctionId(functionDTO.getPreFunctionId());
		functionEntity.setFunctionStatus(functionDTO.getFunctionStatus());
		functionEntity.setCheckFunctionId(functionDTO.getCheckFunctionId());
		functionEntity.setCheckNeeded(functionDTO.getCheckNeeded());
		functionEntity.setLogNeeded(StringBooleanUtil.booleanToString(functionDTO.getLogNeeded()));
		functionEntity.setDisplay(StringBooleanUtil.booleanToString(functionDTO.getDisplay()));
		functionEntity.setDescription(functionDTO.getDescription());
		functionEntity.setRiskLevel(functionDTO.getRiskLevel());
		functionEntity.setFunctionType(functionDTO.getFunctionType());
		functionEntity.setCreateTime(functionDTO.getCreateTime());
		// BeanUtils.copyProperties(functionDTO, functionEntity);

		return functionEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public FunctionDTO convert(FunctionEntity functionEntity) {
		if (functionEntity == null) {
			return null;
		}
		FunctionDTO functionDTO = new FunctionDTO();
		functionDTO.setFunctionId(functionEntity.getFunctionId());
		functionDTO.setFunctionName(functionEntity.getFunctionName());
		functionDTO.setFunctionUrl(functionEntity.getFunctionUrl());
		functionDTO.setKeyWord(functionEntity.getKeyWord());
		functionDTO.setTag(functionEntity.getTag());
		functionDTO.setPreFunctionId(functionEntity.getPreFunctionId());
		functionDTO.setFunctionStatus(functionEntity.getFunctionStatus());
		functionDTO.setCheckFunctionId(functionEntity.getCheckFunctionId());
		functionDTO.setCheckNeeded(functionEntity.getCheckNeeded());
		functionDTO.setLogNeeded(StringBooleanUtil.stringToBoolean(functionEntity.getLogNeeded()));
		functionDTO.setDisplay(StringBooleanUtil.stringToBoolean(functionEntity.getDisplay()));
		functionDTO.setDescription(functionEntity.getDescription());
		functionDTO.setRiskLevel(functionEntity.getRiskLevel());
		functionDTO.setFunctionType(functionEntity.getFunctionType());
		functionDTO.setCreateTime(functionEntity.getCreateTime());
		functionDTO.setSystemId(functionEntity.getSystemId());
		// BeanUtils.copyProperties(functionEntity, functionDTO);
		return functionDTO;
	}

	@Override
	public List<FunctionDTO> convertToDtos(List<FunctionEntity> es) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FunctionEntity> convertToEntities(List<FunctionDTO> ts) {
		// TODO Auto-generated method stub
		return null;
	}

}
