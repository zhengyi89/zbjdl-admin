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
import com.zbjdl.boss.admin.workitem.dto.WorkItemDTO;
import com.zbjdl.boss.admin.workitem.entity.WorkItemEntity;
import com.zbjdl.common.utils.CheckUtils;

/**
 * 
 * 类名称：WorkItemConvert <br>
 * 类描述： <br>
 * 
 * @author：feng
 * @since：2011-11-17 下午02:06:38
 * @version:
 * 
 */
public class WorkItemConvert implements Convert<WorkItemDTO, WorkItemEntity> {

	@Override
	public WorkItemEntity convert(WorkItemDTO dto) {
		if (dto == null) {
			return null;
		}
		WorkItemEntity workItemEntity = new WorkItemEntity();
		BeanUtils.copyProperties(dto, workItemEntity);
		return workItemEntity;
	}

	
	@Override
	public WorkItemDTO convert(WorkItemEntity entity) {
		if (entity == null) {
			return null;
		}

		WorkItemDTO workItemDTO = new WorkItemDTO();
		BeanUtils.copyProperties(entity, workItemDTO);
		return workItemDTO;
	}

	@Override
	public List<WorkItemDTO> convertToDtos(List<WorkItemEntity> workItemEntities) {
		if (CheckUtils.isEmpty(workItemEntities)) {
			return null;
		} else {
			List<WorkItemDTO> workItemDtos = new ArrayList<WorkItemDTO>();
			for (WorkItemEntity workItemEntity : workItemEntities) {
				workItemDtos.add(convert(workItemEntity));
			}
			return workItemDtos;
		}
	}

	@Override
	public List<WorkItemEntity> convertToEntities(List<WorkItemDTO> workItemDtos) {
		if (CheckUtils.isEmpty(workItemDtos)) {
			return null;
		} else {
			List<WorkItemEntity> workItemEntitys = new ArrayList<WorkItemEntity>();
			for (WorkItemDTO workItemDTO : workItemDtos) {
				workItemEntitys.add(convert(workItemDTO));
			}
			return workItemEntitys;
		}
	}
}
