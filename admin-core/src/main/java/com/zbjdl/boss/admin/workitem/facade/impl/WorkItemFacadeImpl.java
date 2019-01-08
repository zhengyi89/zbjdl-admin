/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.workitem.facade.impl;

import com.zbjdl.boss.admin.biz.OperationLogBiz;
import com.zbjdl.boss.admin.biz.WorkItemBiz;
import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.facade.WorkItemFacade;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.utils.TemplateUtil;
import com.zbjdl.boss.admin.workitem.dto.WorkItemDTO;
import com.zbjdl.boss.admin.workitem.dto.WorkItemTemplateDTO;
import com.zbjdl.boss.admin.workitem.entity.WorkItemEntity;
import com.zbjdl.boss.admin.workitem.exception.WorkItemException;
import com.zbjdl.common.json.JSONUtils;
import com.zbjdl.common.utils.CheckUtils;
import com.zbjdl.common.utils.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 
 * 类名称：WorkItemFacadeImpl <br>
 * 类描述： <br>
 * 
 * @author feng
 * @since 2011-11-18 下午03:32:16
 * @author feng
 * @since 2013-06-08 下午16:42:17
 * @version 1.0.0
 * 
 */
public class WorkItemFacadeImpl implements WorkItemFacade {

	private WorkItemBiz workItemBiz;

	private OperationLogBiz operationLogBiz;

	private final static Logger logger = LoggerFactory
			.getLogger(WorkItemFacadeImpl.class);

	@Autowired
	private Convert<WorkItemDTO, WorkItemEntity> workItemConvert;


	@Override
	public void createWorkItem(WorkItemDTO workItemDTO, UserDTO user)
			throws WorkItemException {
		workItemBiz.createWorkItem(workItemDTO, user);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.facade.WorkItemFacade#checkSuccess(java
	 * .lang.Long, com.zbjdl.boss.admin.user.dto.UserDTO)
	 */
	@Override
	public void checkSuccess(Long workItemId, UserDTO user)
			throws WorkItemException {
		workItemBiz.checkSuccess(workItemId, user);
	}

    @Override
    public void checkSuccessBatch(List<Long> workItemIds, UserDTO user) throws WorkItemException {
	    lockWorkItemBatch(workItemIds, user);
	    StringBuffer sb = new StringBuffer();
	    for(Long itemId : workItemIds) {
		    try{
			    workItemBiz.checkSuccess(itemId, user);
		    }
		    catch (Exception e) {
			    logger.warn("checkSuccess {}. Detail: {}", itemId.toString(), e.getMessage());
			    sb.append("<br/>记录 " + itemId + " 操作失败：" + handleAuditException(e));
		    }
	    }
	    unLockWorkItemBatch(workItemIds, user);
	    if(!sb.toString().isEmpty()){
		    throw new RuntimeException(sb.toString());
	    }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.zbjdl.boss.admin.facade.WorkItemFacade#checkRefuse(java.
     * lang.Long, com.zbjdl.boss.admin.user.dto.UserDTO,
     * java.lang.String)
     */
	@Override
	public void checkRefuse(Long workItemId, UserDTO user, String refuseReason)
			throws WorkItemException {
		workItemBiz.checkRefuse(workItemId, user, refuseReason);
	}

    @Override
    public void checkRefuseBatch(List<Long> workItemIds, UserDTO user, String refuseReason) throws WorkItemException {
	    lockWorkItemBatch(workItemIds, user);
	    StringBuffer sb = new StringBuffer();
	    for(Long itemId : workItemIds) {
		    try{
			    workItemBiz.checkRefuse(itemId, user, refuseReason);
		    }
		    catch (Exception e) {
			    logger.warn("checkSRefuse {}. Detail: {}", itemId.toString(), e.getMessage());
			    sb.append("<br/>记录 " + itemId + " 操作失败：" + handleAuditException(e));
		    }
	    }
	    unLockWorkItemBatch(workItemIds, user);
	    if(!sb.toString().isEmpty()){
		    throw new RuntimeException(sb.toString());
	    }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.zbjdl.boss.admin.facade.WorkItemFacade#checkCancel(java.
     * lang.Long, com.zbjdl.boss.admin.user.dto.UserDTO)
     */
	@Override
	public void checkCancel(Long workItemId, UserDTO user)
			throws WorkItemException {
		workItemBiz.checkCancel(workItemId, user);
	}

    @Override
    public void checkCancelBatch(List<Long> workItemIds, UserDTO user) throws WorkItemException {
	    lockWorkItemBatch(workItemIds, user);
	    StringBuffer sb = new StringBuffer();
	    for(Long itemId : workItemIds) {
		    try{
			    workItemBiz.checkCancel(itemId, user);
		    }
		    catch (Exception e) {
			    logger.warn("checkCancles {}. Detail: {}", itemId.toString(), e.getMessage());
			    sb.append("<br/>记录 " + itemId + " 操作失败：" + handleAuditException(e));
		    }
	    }
	    unLockWorkItemBatch(workItemIds, user);
	    if(!sb.toString().isEmpty()){
		    throw new RuntimeException(sb.toString());
	    }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.zbjdl.boss.admin.facade.WorkItemFacade#lockWorkItem(java
     * .lang.Long, com.zbjdl.boss.admin.user.dto.UserDTO)
     */
	@Override
	public void lockWorkItem(Long workItemId, UserDTO user)
			throws WorkItemException {
		workItemBiz.lockWorkItem(workItemId, user);
	}

    @Override
    public void lockWorkItemBatch(List<Long> workItemIds, UserDTO user)
            throws WorkItemException {
	    for(Long itemId : workItemIds) {
		    try{
			    workItemBiz.lockWorkItem(itemId, user);
		    }
		    catch (Exception e) {
			    logger.warn("lockWorkItem {}. Detail: {}", itemId.toString(), e.getMessage());
		    }
	    }
    }

	@Override
	public void unLockWorkItem(Long workItemId, UserDTO user)
			throws WorkItemException {
		workItemBiz.unLockWorkItem(workItemId, user);
	}

    @Override
    public void unLockWorkItemBatch(List<Long> workItemIds, UserDTO user)
            throws WorkItemException {
	    for(Long itemId : workItemIds) {
		    try{
			    workItemBiz.unLockWorkItem(itemId, user);
		    }
		    catch (Exception e) {
			    logger.warn("unLockWorkItem {}. Detail: {}", itemId.toString(), e.getMessage());
		    }
	    }
    }

	public WorkItemDTO queryWorkItemById(Long workItemId) {
		return workItemBiz.queryWorkItemById(workItemId);
	}

	@Override
	public List<WorkItemDTO> queryWorkItemByResourceId(String resourceId) {
		List<WorkItemEntity> workItemEntities = workItemBiz
				.queryWorkItemByResourceId(resourceId);
		return workItemConvert.convertToDtos(workItemEntities);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.facade.WorkItemFacade#showWorkItemDetail
	 * (java.lang.Long)
	 */
	@Override
	public WorkItemDTO showWorkItemDetail(Long workItemId) {
		WorkItemDTO workItemDTO = workItemBiz.queryWorkItemById(workItemId);
		if (StringUtils.isBlank(workItemDTO.getFullContent())) {
			@SuppressWarnings("rawtypes")
			Map paramMap = JSONUtils.jsonToMap(workItemDTO.getContent(),
					String.class, null);
			// 根据功能ID查询日志模板
			WorkItemTemplateDTO template = workItemBiz
					.queryTemplateByFunctionId(workItemDTO.getFunctionId());
			if (template != null
					&& StringUtils.isNotBlank(template.getContent())) {
				String fullContent = TemplateUtil.fillTemplate(
						template.getContent(), paramMap);
				workItemDTO.setFullContent(fullContent);
				workItemBiz.updateWorkItem(workItemDTO);
			}
		}
		return workItemDTO;
	}

	@Override
	public List<WorkItemDTO> getWorkItemByObject(WorkItemDTO dto) {
		WorkItemEntity workItem = workItemConvert.convert(dto);
		List<WorkItemEntity> workItemEntities = workItemBiz
				.getWorkItemByObject(workItem);
		return workItemConvert.convertToDtos(workItemEntities);
	}

	@Override
	public void addTemplate(WorkItemTemplateDTO workItemTemplateDTO) {
		CheckUtils.valueIsEmpty(workItemTemplateDTO.getFunctionId().toString(),
				"functionId");
		CheckUtils.valueIsEmpty(workItemTemplateDTO.getContent(), "content");
		workItemBiz.addTemplate(workItemTemplateDTO);
	}

	@Override
	public void deleteTemplate(Long templateId) {
		CheckUtils.valueIsNull(templateId.toString(), "templateId");
		workItemBiz.deleteTemplate(templateId);
	}

	@Override
	public WorkItemTemplateDTO queryTemplate(Long templateId) {
		CheckUtils.valueIsNull(templateId.toString(), "templateId");

		return workItemBiz.queryTemplate(templateId);
	}

	@Override
	public void updateTemplate(Long templateId,
			WorkItemTemplateDTO workItemTemplateDTO) {
		CheckUtils.valueIsNull(templateId.toString(), "templateId");
		CheckUtils.valueIsEmpty(workItemTemplateDTO.getFunctionId().toString(),
				"functionId");
		CheckUtils.valueIsEmpty(workItemTemplateDTO.getContent(), "content");

		workItemBiz.updateTemplate(templateId, workItemTemplateDTO);
	}

	/**
	 * workItemBiz
	 * 
	 * @return the workItemBiz
	 */
	public WorkItemBiz getWorkItemBiz() {
		return workItemBiz;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param workItemBiz
	 *            the workItemBiz to set
	 */
	public void setWorkItemBiz(WorkItemBiz workItemBiz) {
		this.workItemBiz = workItemBiz;
	}

	/**
	 * operationLogBiz
	 * 
	 * @return the operationLogBiz
	 */

	public OperationLogBiz getOperationLogBiz() {
		return operationLogBiz;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param operationLogBiz
	 *            the operationLogBiz to set
	 */
	public void setOperationLogBiz(OperationLogBiz operationLogBiz) {
		this.operationLogBiz = operationLogBiz;
	}

	public Convert<WorkItemDTO, WorkItemEntity> getWorkItemConvert() {
		return workItemConvert;
	}

	public void setWorkItemConvert(
			Convert<WorkItemDTO, WorkItemEntity> workItemConvert) {
		this.workItemConvert = workItemConvert;
	}

	@Override
	public WorkItemTemplateDTO queryTemplateByFunctionId(Long functionId) {
		return workItemBiz.queryTemplateByFunctionId(functionId);
	}

	public void preAudit(Long workItemId, UserDTO user) {
		workItemBiz.preAudit(workItemId, user);
	}

    public void preAuditBatch(List<Long> workItemIds, UserDTO user) {
	    lockWorkItemBatch(workItemIds, user);
	    StringBuffer sb = new StringBuffer();
	    for(Long itemId : workItemIds) {
		    try{
			    workItemBiz.preAudit(itemId, user);
		    }
		    catch (Exception e) {
			    logger.warn("preAudit {}. Detail: {}", itemId.toString(), e.getMessage());
			    sb.append("<br/>记录 " + itemId + " 操作失败：" + handleAuditException(e));
		    }
	    }
	    unLockWorkItemBatch(workItemIds, user);
	    if(!sb.toString().isEmpty()){
		    throw new RuntimeException(sb.toString());
	    }
    }

	private String handleAuditException(Exception e) {
		if (e instanceof WorkItemException) {
			logger.error("{}", e);
			WorkItemException we = (WorkItemException) e;
			if (StringUtils.equals(we.getDefineCode(), "001")) {
				return "该审核记录不存在！";
			} else if (StringUtils.equals(we.getDefineCode(), "002")) {
				// 该审核记录已废弃异常
				return "该审核记录已废弃！";
			} else if (StringUtils.equals(we.getDefineCode(), "003")) {
				// 该记录正在被其他人锁定
				return "该审核记录已被其他人锁定！";
			} else if (StringUtils.equals(we.getDefineCode(), "004")) {
				return "只有提交人才能撤销自己的审核";
			} else if (StringUtils.equals(we.getDefineCode(), "005")) {
				return "记录已被处理，不可撤销";
			} else if (StringUtils.equals(we.getDefineCode(), "006")) {
				return "提交人不可审核自己的申请";
			} else if (StringUtils.equals(we.getDefineCode(), "007")) {
				return "没有双重复核权限";
			} else if (StringUtils.equals(we.getDefineCode(), "009")) {
				return "审核记录状态错误,不支持当前操作";
			} else {
				return "defineCode:" + we.getDefineCode() + " errorMessage:"
						+ e.getMessage();
			}
		} else {
			return "未知错误";
		}
	}

}
