/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.operationlog.convert.impl;

import java.util.List;

import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.dto.operationlog.OperationLogTemplateDTO;
import com.zbjdl.boss.admin.operationlog.entity.OperationLogTemplateEntity;

/**
 *
 * 类名称：OperationLogConvert <br>
 * 类描述：   <br>
 * @author：feng
 * @since：2011-7-26 下午04:06:57
 * @version:
 *
 */
public class OperationLogTemplateConvert implements Convert<OperationLogTemplateDTO, OperationLogTemplateEntity> {

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.convert.Convert#convert(com.zbjdl.boss.admin.dto.basic.BaseDTO)
	 */
	@Override
	public OperationLogTemplateEntity convert(OperationLogTemplateDTO dto) {
		if (dto == null) {
			return null;
		}
		OperationLogTemplateEntity entity = new OperationLogTemplateEntity();
		entity.setContent(dto.getContent());
		entity.setFunctionId(dto.getFunctionId());
		entity.setTemplateId(dto.getTemplateId());
		entity.setKeyWord(dto.getKeyWord());
		return entity;
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.convert.Convert#convert(com.zbjdl.common.persistence.Entity)
	 */
	@Override
	public OperationLogTemplateDTO convert(OperationLogTemplateEntity entity) {
		if (entity == null) {
			return null;
		}
		OperationLogTemplateDTO dto = new OperationLogTemplateDTO();
		dto.setContent(entity.getContent());
		dto.setFunctionId(entity.getFunctionId());
		dto.setTemplateId(entity.getTemplateId());
		dto.setKeyWord(entity.getKeyWord());
		return dto;
	}

	@Override
	public List<OperationLogTemplateDTO> convertToDtos(
			List<OperationLogTemplateEntity> es) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OperationLogTemplateEntity> convertToEntities(
			List<OperationLogTemplateDTO> ts) {
		// TODO Auto-generated method stub
		return null;
	}


}
