/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.workitem.service;

import java.util.List;

import com.zbjdl.boss.admin.workitem.dto.WorkItemDTO;
import com.zbjdl.boss.admin.workitem.dto.WorkItemTemplateDTO;
import com.zbjdl.boss.admin.workitem.entity.WorkItemEntity;
import com.zbjdl.boss.admin.workitem.enums.WorkItemStatusEnum;

/**
 *
 * 类名称：WorkItemService <br>
 * 类描述：   <br>
 * @author：feng
 * @since：2011-11-17 下午01:44:06
 * @version:
 *
 */
public interface WorkItemService {

	/**
	 *
	 * 描述：新建审核记录
	 * @param workItemDTO 审核记录DTO
	 * @return 审核记录ID
	 */
	public Long createWorkItem(WorkItemDTO workItemDTO);

	/**
	 *
	 * 描述：   更新审核记录
	 * @param workItemDTO 审核记录DTO
	 */
	public void updateWorkItem(WorkItemDTO workItemDTO);

	/**
	 *
	 * 描述：    根据Id获取记录
	 * @param workItemId
	 * @return
	 */
	public WorkItemDTO queryById(Long workItemId);

	/**
	 * 描述：根据资源ID查询记录
	 * @param resourceId
	 * @return
	 */
	public List<WorkItemEntity> queryWorkItemByResourceId(String resourceId);

	/**
	 * Title：按条件查询实体对象
	 * @param entity
	 * @return
	 * added by：xuebo.yang
	 */
	public List<WorkItemEntity> getWorkItemByObject(WorkItemEntity entity);

	public WorkItemTemplateDTO queryTemplateByFunctionId(Long functionId);

	/**
	 *
	 * 描述：    添加双权限审核模板
	 * @param workItemTemplateDTO	模板对象
	 */
	public void addTemplate(WorkItemTemplateDTO workItemTemplateDTO);

	/**
	 *
	 * 描述：    删除双权限审核模板
	 * @param templateId	模板ID
	 */
	public void deleteTemplate(Long templateId);

	/**
	 *
	 * 描述：    查询双权限审核模板
	 * @param templateId	模板ID
	 */
	public WorkItemTemplateDTO queryTemplate(Long templateId);

	/**
	 *
	 * 描述：    更新双权限审核模板
	 * @param templateId	模板ID
	 * @param workItemTemplateDTO	模板对象
	 */
	public void updateTemplate(Long templateId, WorkItemTemplateDTO workItemTemplateDTO);

	/**
	 * 更新状态
	 * @param status 目标状态
	 * @param workItemId ID
	 */
	public void updateStatus(WorkItemStatusEnum status, Long workItemId);
}
