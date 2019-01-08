/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.workitem.entity;

import java.util.Date;

import com.zbjdl.boss.admin.workitem.enums.WorkItemResultEnum;
import com.zbjdl.boss.admin.workitem.enums.WorkItemStatusEnum;
import com.zbjdl.boss.admin.workitem.enums.WorkItemTypeEnum;
import com.zbjdl.common.persistence.Entity;

/**
 * 
 * 类名称：WorkItemEntity <br>
 * 类描述： 审核记录表<br>
 * 
 * @author：feng
 * @since：2011-11-16 下午04:28:53
 * @version:
 * 
 */
public class WorkItemEntity implements Entity<Long> {

	private static final long serialVersionUID = -7889882542606264613L;

	private Long workItemId;

	/**
	 * 关联功能ID
	 */
	private Long functionId;

	/**
	 * 资源ID，同一个功能的资源ID必须是唯一的
	 */
	private String resourceId;

	/**
	 * 审核内容
	 */
	private String content;

	private String fullContent;

	/**
	 * 审核状态
	 */
	private WorkItemStatusEnum workItemStatus;

	/**
	 * 拒绝原因
	 */
	private String rejectCause;

	/**
	 * 审核类型
	 */
	private WorkItemTypeEnum workItemType;

	/**
	 * 审核取消人ID
	 */
	private Long cancelBy;

	/**
	 * 审核取消人
	 */
	private String cancelUserName;

	/**
	 * 审核提交人ID
	 */
	private Long submittedBy;

	/**
	 * 审核提交人
	 */
	private String submittedUserName;

	/**
	 * 审核记录加锁人ID
	 */
	private Long lockedBy;

	/**
	 * 审核记录锁定人
	 */
	private String lockedUserName;

	/**
	 * 审核拒绝人ID
	 */
	private Long rejectedBy;

	/**
	 * 审核拒绝人
	 */
	private String rejectUserName;

	/**
	 * 最终审核通过人
	 */
	private Long approveBy;

	/**
	 * 最终审核通过人
	 */
	private String approveUserName;

	/**
	 * 取消时间
	 */
	private Date cancelTime;

	/**
	 * 提交时间
	 */
	private Date submitTime;

	/**
	 * 记录加锁时间
	 */
	private Date lockTime;

	/**
	 * 拒绝时间
	 */
	private Date rejectTime;

	/**
	 * 最终审核通过时间
	 */
	private Date approveTime;

	/**
	 * 执行时间
	 */
	private Date invocatTime;

	/**
	 * 审核记录扩展或备注
	 */
	private String summary;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 执行结果
	 */
	private WorkItemResultEnum invocatResult;

	/**
	 * 执行结果
	 */
	private String resultComment;

	/**
	 * 审核单据所属子系统
	 */
	private String belongSystem;

	/**
	 * workItemId
	 * 
	 * @return the workItemId
	 */

	public Long getWorkItemId() {
		return workItemId;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param workItemId
	 *            the workItemId to set
	 */
	public void setWorkItemId(Long workItemId) {
		this.workItemId = workItemId;
	}

	/**
	 * functionId
	 * 
	 * @return the functionId
	 */

	public Long getFunctionId() {
		return functionId;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param functionId
	 *            the functionId to set
	 */
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	/**
	 * content
	 * 
	 * @return the content
	 */

	public String getContent() {
		return content;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public String getFullContent() {
		return fullContent;
	}

	public void setFullContent(String fullContent) {
		this.fullContent = fullContent;
	}

	/**
	 * workItemStatus
	 * 
	 * @return the workItemStatus
	 */

	public WorkItemStatusEnum getWorkItemStatus() {
		return workItemStatus;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param workItemStatus
	 *            the workItemStatus to set
	 */
	public void setWorkItemStatus(WorkItemStatusEnum workItemStatus) {
		this.workItemStatus = workItemStatus;
	}

	/**
	 * rejectCause
	 * 
	 * @return the rejectCause
	 */

	public String getRejectCause() {
		return rejectCause;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param rejectCause
	 *            the rejectCause to set
	 */
	public void setRejectCause(String rejectCause) {
		this.rejectCause = rejectCause;
	}

	/**
	 * workItemType
	 * 
	 * @return the workItemType
	 */

	public WorkItemTypeEnum getWorkItemType() {
		return workItemType;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param workItemType
	 *            the workItemType to set
	 */
	public void setWorkItemType(WorkItemTypeEnum workItemType) {
		this.workItemType = workItemType;
	}

	/**
	 * cancelBy
	 * 
	 * @return the cancelBy
	 */

	public Long getCancelBy() {
		return cancelBy;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param cancelBy
	 *            the cancelBy to set
	 */
	public void setCancelBy(Long cancelBy) {
		this.cancelBy = cancelBy;
	}

	/**
	 * submittedBy
	 * 
	 * @return the submittedBy
	 */

	public Long getSubmittedBy() {
		return submittedBy;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param submittedBy
	 *            the submittedBy to set
	 */
	public void setSubmittedBy(Long submittedBy) {
		this.submittedBy = submittedBy;
	}

	/**
	 * lockedBy
	 * 
	 * @return the lockedBy
	 */

	public Long getLockedBy() {
		return lockedBy;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param lockedBy
	 *            the lockedBy to set
	 */
	public void setLockedBy(Long lockedBy) {
		this.lockedBy = lockedBy;
	}

	/**
	 * rejectedBy
	 * 
	 * @return the rejectedBy
	 */

	public Long getRejectedBy() {
		return rejectedBy;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param rejectedBy
	 *            the rejectedBy to set
	 */
	public void setRejectedBy(Long rejectedBy) {
		this.rejectedBy = rejectedBy;
	}

	/**
	 * approveBy
	 * 
	 * @return the approveBy
	 */

	public Long getApproveBy() {
		return approveBy;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param approveBy
	 *            the approveBy to set
	 */
	public void setApproveBy(Long approveBy) {
		this.approveBy = approveBy;
	}

	/**
	 * summary
	 * 
	 * @return the summary
	 */

	public String getSummary() {
		return summary;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * invocatResult
	 * 
	 * @return the invocatResult
	 */

	public WorkItemResultEnum getInvocatResult() {
		return invocatResult;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param invocatResult
	 *            the invocatResult to set
	 */
	public void setInvocatResult(WorkItemResultEnum invocatResult) {
		this.invocatResult = invocatResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zbjdl.common.persistence.Entity#getId()
	 */
	@Override
	public Long getId() {
		return workItemId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zbjdl.common.persistence.Entity#setId(java.io.Serializable)
	 */
	@Override
	public void setId(Long id) {
		this.workItemId = id;
	}

	/**
	 * cancelUserName
	 * 
	 * @return the cancelUserName
	 */

	public String getCancelUserName() {
		return cancelUserName;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param cancelUserName
	 *            the cancelUserName to set
	 */
	public void setCancelUserName(String cancelUserName) {
		this.cancelUserName = cancelUserName;
	}

	/**
	 * submittedUserName
	 * 
	 * @return the submittedUserName
	 */

	public String getSubmittedUserName() {
		return submittedUserName;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param submittedUserName
	 *            the submittedUserName to set
	 */
	public void setSubmittedUserName(String submittedUserName) {
		this.submittedUserName = submittedUserName;
	}

	/**
	 * lockedUserName
	 * 
	 * @return the lockedUserName
	 */

	public String getLockedUserName() {
		return lockedUserName;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param lockedUserName
	 *            the lockedUserName to set
	 */
	public void setLockedUserName(String lockedUserName) {
		this.lockedUserName = lockedUserName;
	}

	/**
	 * rejectUserName
	 * 
	 * @return the rejectUserName
	 */

	public String getRejectUserName() {
		return rejectUserName;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param rejectUserName
	 *            the rejectUserName to set
	 */
	public void setRejectUserName(String rejectUserName) {
		this.rejectUserName = rejectUserName;
	}

	/**
	 * approveUserName
	 * 
	 * @return the approveUserName
	 */

	public String getApproveUserName() {
		return approveUserName;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param approveUserName
	 *            the approveUserName to set
	 */
	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * cancelTime
	 * 
	 * @return the cancelTime
	 */

	public Date getCancelTime() {
		return cancelTime;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param cancelTime
	 *            the cancelTime to set
	 */
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	/**
	 * submitTime
	 * 
	 * @return the submitTime
	 */

	public Date getSubmitTime() {
		return submitTime;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param submitTime
	 *            the submitTime to set
	 */
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	/**
	 * lockTime
	 * 
	 * @return the lockTime
	 */

	public Date getLockTime() {
		return lockTime;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param lockTime
	 *            the lockTime to set
	 */
	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	/**
	 * rejectTime
	 * 
	 * @return the rejectTime
	 */

	public Date getRejectTime() {
		return rejectTime;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param rejectTime
	 *            the rejectTime to set
	 */
	public void setRejectTime(Date rejectTime) {
		this.rejectTime = rejectTime;
	}

	/**
	 * approveTime
	 * 
	 * @return the approveTime
	 */

	public Date getApproveTime() {
		return approveTime;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param approveTime
	 *            the approveTime to set
	 */
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	/**
	 * invocatTime
	 * 
	 * @return the invocatTime
	 */

	public Date getInvocatTime() {
		return invocatTime;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param invocatTime
	 *            the invocatTime to set
	 */
	public void setInvocatTime(Date invocatTime) {
		this.invocatTime = invocatTime;
	}

	/**
	 * createTime
	 * 
	 * @return the createTime
	 */

	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * updateTime
	 * 
	 * @return the updateTime
	 */

	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getResultComment() {
		return resultComment;
	}

	public void setResultComment(String resultComment) {
		this.resultComment = resultComment;
	}

	public String getBelongSystem() {
		return belongSystem;
	}

	public void setBelongSystem(String belongSystem) {
		this.belongSystem = belongSystem;
	}

}
