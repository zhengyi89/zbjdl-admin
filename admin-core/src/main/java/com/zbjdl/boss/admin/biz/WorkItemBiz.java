/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.biz;

import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.workitem.dto.WorkItemDTO;
import com.zbjdl.boss.admin.workitem.dto.WorkItemTemplateDTO;
import com.zbjdl.boss.admin.workitem.entity.WorkItemEntity;
import com.zbjdl.boss.admin.workitem.enums.WorkItemStatusEnum;
import com.zbjdl.boss.admin.workitem.exception.WorkItemException;

import java.util.List;

/**
 * 
 * 类名称：WorkItemBiz <br>
 * 类描述： <br>
 * 
 * @author feng
 * @since 2011-11-17 下午02:24:43
 * @author feng
 * @since 2013-06-08 下午16:44:36
 * @version 1.0.0
 * 
 */
public interface WorkItemBiz {

	/**
	 * 
	 * 描述： 创建申请记录
	 * 
	 * @param workItemDTO 审核记录
	 * @param user
	 *            当前操作人
	 */
	public void createWorkItem(WorkItemDTO workItemDTO, UserDTO user)
			throws WorkItemException;

	/**
	 * 
	 * 描述： 预审核
	 * 
	 * @param workItemId 审核记录
	 * @param user
	 *            当前操作人
	 */
	public void preAudit(Long workItemId, UserDTO user)
			throws WorkItemException;

	/**
	 * 
	 * 描述： 审核通过
	 * 
	 * @param workItemId 审核记录
	 * @param user
	 *            当前操作人
	 */
	public void checkSuccess(Long workItemId, UserDTO user)
			throws WorkItemException;

	/**
	 * 
	 * 描述： 审核拒绝
	 * 
	 * @param workItemId
	 *            记录ID
	 * @param user
	 *            当前操作人
	 * @param refuseReason
	 *            拒绝原因
	 */
	public void checkRefuse(Long workItemId, UserDTO user, String refuseReason)
			throws WorkItemException;

	/**
	 * 
	 * 描述： 审核撤销
	 * 
	 * @param workItemId 审核记录
	 * @param user
	 *            当前操作人
	 */
	public void checkCancel(Long workItemId, UserDTO user)
			throws WorkItemException;

	/**
	 * 
	 * 描述： 锁定审核记录
	 * 
	 * @param workItemId 审核记录
	 * @param user
	 *            当前操作人
	 */
	public void lockWorkItem(Long workItemId, UserDTO user)
			throws WorkItemException;

    /**
     * 解锁审核记录
     *
     * @param workItemId 审核记录
     */
    public void unLockWorkItem(Long workItemId, UserDTO user);

	/**
	 * 
	 * 描述： 根据ID查询记录
	 * 
	 * @param workItemId 审核记录
	 * @return
	 */
	public WorkItemDTO queryWorkItemById(Long workItemId);

	/**
	 * 
	 * 描述： 更新审核记录
	 * 
	 * @param workItemDTO
	 *            审核记录DTO
	 */
	public void updateWorkItem(WorkItemDTO workItemDTO);

	/**
	 * 描述：根据资源ID查询记录
	 * 
	 * @param resourceId 审核记录
	 * @return
	 */
	public List<WorkItemEntity> queryWorkItemByResourceId(String resourceId);

	/**
	 * Title：按条件查询实体对象
	 * 
	 * @param entity 审核记录
	 * @return added by：xuebo.yang
	 */
	public List<WorkItemEntity> getWorkItemByObject(WorkItemEntity entity);

	public WorkItemTemplateDTO queryTemplateByFunctionId(Long functionId);

	/**
	 * 
	 * 描述： 添加双权限审核模板
	 * 
	 * @param workItemTemplateDTO
	 *            模板对象
	 */
	public void addTemplate(WorkItemTemplateDTO workItemTemplateDTO);

	/**
	 * 
	 * 描述： 删除双权限审核模板
	 * 
	 * @param templateId
	 *            模板ID
	 */
	public void deleteTemplate(Long templateId);

	/**
	 * 
	 * 描述： 查询双权限审核模板
	 * 
	 * @param templateId
	 *            模板ID
	 */
	public WorkItemTemplateDTO queryTemplate(Long templateId);

	/**
	 * 
	 * 描述： 更新双权限审核模板
	 * 
	 * @param templateId
	 *            模板ID
	 * @param workItemTemplateDTO
	 *            模板对象
	 */
	public void updateTemplate(Long templateId,
			WorkItemTemplateDTO workItemTemplateDTO);

	/**
	 * 更新状态
	 * 
	 * @param status
	 *            目标状态
	 * @param workItemId
	 *            ID
	 */
	public void updateStatus(WorkItemStatusEnum status, Long workItemId);

}
