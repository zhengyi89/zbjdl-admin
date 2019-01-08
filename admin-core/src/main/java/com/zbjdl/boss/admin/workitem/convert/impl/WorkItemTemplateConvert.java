/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.workitem.convert.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.workitem.dto.WorkItemTemplateDTO;
import com.zbjdl.boss.admin.workitem.entity.WorkItemTemplateEntity;
import com.zbjdl.common.utils.CheckUtils;

/**
 * 
 * @author：feng
 * @since：2012-6-8 上午10:11:14
 * @version:
 */
public class WorkItemTemplateConvert implements
		Convert<WorkItemTemplateDTO, WorkItemTemplateEntity> {

	
	@Override
	public WorkItemTemplateEntity convert(WorkItemTemplateDTO dto) {
		if (dto == null) {
			return null;
		}
		WorkItemTemplateEntity workItemTemplateEntity = new WorkItemTemplateEntity();
		BeanUtils.copyProperties(dto, workItemTemplateEntity);
		return workItemTemplateEntity;
	}


	@Override
	public WorkItemTemplateDTO convert(WorkItemTemplateEntity entity) {
		if (entity == null) {
			return null;
		}

		WorkItemTemplateDTO workItemTemplateDTO = new WorkItemTemplateDTO();
		BeanUtils.copyProperties(entity, workItemTemplateDTO);
		return workItemTemplateDTO;
	}

	@Override
	public List<WorkItemTemplateDTO> convertToDtos(
			List<WorkItemTemplateEntity> workItemTemplateEntities) {
		if (CheckUtils.isEmpty(workItemTemplateEntities)) {
			return null;
		} else {
			List<WorkItemTemplateDTO> workItemTemplateDtos = new ArrayList<WorkItemTemplateDTO>();
			for (WorkItemTemplateEntity workItemTemplateEntity : workItemTemplateEntities) {
				workItemTemplateDtos.add(convert(workItemTemplateEntity));
			}
			return workItemTemplateDtos;
		}
	}

	@Override
	public List<WorkItemTemplateEntity> convertToEntities(
			List<WorkItemTemplateDTO> workItemTemplateDtos) {
		if (CheckUtils.isEmpty(workItemTemplateDtos)) {
			return null;
		} else {
			List<WorkItemTemplateEntity> workItemTemplateEntitys = new ArrayList<WorkItemTemplateEntity>();
			for (WorkItemTemplateDTO workItemDTO : workItemTemplateDtos) {
				workItemTemplateEntitys.add(convert(workItemDTO));
			}
			return workItemTemplateEntitys;
		}
	}
}
