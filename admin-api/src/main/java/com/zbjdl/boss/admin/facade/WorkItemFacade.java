/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.facade;

import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.workitem.dto.WorkItemDTO;
import com.zbjdl.boss.admin.workitem.dto.WorkItemTemplateDTO;
import com.zbjdl.boss.admin.workitem.exception.WorkItemException;

import java.util.List;

/**
 * 
 * 类名称：WorkItemFacade <br>
 * 类描述： 审核记录facade门面 <br>
 * 
 * @author feng
 * @since 2011-11-17 下午01:22:04
 * @author feng
 * @since 2013-06-08 16:37:38
 * @version 1.0.0
 * 
 */
public interface WorkItemFacade {

	/**
	 * 
	 * 描述： 创建申请记录
	 * 
	 * @param workItemDTO
     *            审核记录
	 * @param user
	 *            当前操作人
	 */
	public void createWorkItem(WorkItemDTO workItemDTO, UserDTO user)
			throws WorkItemException;

	/**
	 * 
	 * 描述： 审核通过
	 * 
	 * @param workItemId
     *            审核记录
	 * @param user
	 *            当前操作人
	 */
	public void checkSuccess(Long workItemId, UserDTO user)
			throws WorkItemException;

    /**
     *
     * 描述： 批量审核通过
     *
     * @param workItemIds
     *            审核记录
     * @param user
     *            当前操作人
     */
    public void checkSuccessBatch(List<Long> workItemIds, UserDTO user)
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
     * 描述： 批量审核拒绝
     *
     * @param workItemIds
     *            记录ID
     * @param user
     *            当前操作人
     * @param refuseReason
     *            拒绝原因
     */
    public void checkRefuseBatch(List<Long> workItemIds, UserDTO user, String refuseReason)
            throws WorkItemException;

	/**
	 * 
	 * 描述： 审核撤销
	 * 
	 * @param workItemId
     *            审核记录
	 * @param user
	 *            当前操作人
	 */
	public void checkCancel(Long workItemId, UserDTO user)
			throws WorkItemException;

    /**
     *
     * 描述： 批量审核撤销
     *
     * @param workItemIds
     *            审核记录
     * @param user
     *            当前操作人
     */
    public void checkCancelBatch(List<Long> workItemIds, UserDTO user)
            throws WorkItemException;

	/**
	 * 
	 * 描述： 锁定审核记录
	 * 
	 * @param workItemId
     *            审核记录
	 * @param user
	 *            当前操作人
	 */
	public void lockWorkItem(Long workItemId, UserDTO user)
			throws WorkItemException;

    /**
     *
     * 描述： 批量锁定审核记录
     *
     * @param workItemIds
     *            审核记录
     * @param user
     *            当前操作人
     */
    public void lockWorkItemBatch(List<Long> workItemIds, UserDTO user)
            throws WorkItemException;

    /**
     * 解锁审核记录
     *
     * @param workItemId 审核记录
     * @throws WorkItemException
     */
    public void unLockWorkItem(Long workItemId, UserDTO user)
            throws WorkItemException;

    /**
     * 批量解锁审核记录
     *
     * @param workItemIds 审核记录
     * @throws WorkItemException
     */
    public void unLockWorkItemBatch(List<Long> workItemIds, UserDTO user)
            throws WorkItemException;

	/**
	 * 
	 * 风控预先审核
	 * 
	 * @param workItemId
     *            审核记录
	 * @param user
	 *            当前操作人
	 */
	public void preAudit(Long workItemId, UserDTO user);

    /**
     *
     * 风控批量预先审核
     *
     * @param workItemIds
     *            审核记录
     * @param user
     *            当前操作人
     */
    public void preAuditBatch(List<Long> workItemIds, UserDTO user);

	/**
	 * 
	 * 描述： 根据ID查询记录
	 * 
	 * @param workItemId
     *            审核记录
	 * @return    审核记录
	 */
	public WorkItemDTO queryWorkItemById(Long workItemId);

	/**
	 * Title：查询符合条件的workItem对象
	 * 
	 * @param dto  审核记录
	 * @return added by：xuebo.yang
	 */
	public List<WorkItemDTO> getWorkItemByObject(WorkItemDTO dto);

	/**
	 * 
	 * 描述： 查询复核记录详情
	 * 
	 * @param workItemId  审核记录
	 * @return  审核记录
	 */
	public WorkItemDTO showWorkItemDetail(Long workItemId);

	/**
	 * 描述：根据资源ID查询记录
	 * 
	 * @param resourceId 审核记录
	 * @author feng
	 */
	public List<WorkItemDTO> queryWorkItemByResourceId(String resourceId);

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
	 * 描述： 根据功能ID查询双权限审核模板
	 * 
	 * @param functionId
	 *            功能ID
	 */
	public WorkItemTemplateDTO queryTemplateByFunctionId(Long functionId);

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

}
