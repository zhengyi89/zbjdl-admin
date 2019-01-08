package com.zbjdl.boss.admin.workitem.action;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.function.enums.FunctionTypeEnum;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;
import com.zbjdl.boss.admin.workitem.dto.WorkItemDTO;
import com.zbjdl.boss.admin.workitem.exception.WorkItemException;
import com.zbjdl.common.utils.StringUtils;

import org.apache.struts2.ServletActionContext;

import java.io.IOException;
import java.util.List;

/**
 * 类名称：QueryWorkItemAction <br>
 * 类描述： <br>
 *
 * @author feng
 * @since 2011-11-17 下午04:41:33
 * @author feng
 * @since 2013-06-21 下午03:40:53
 * @version 1.0.0
 */
public class WorkItemManagerAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = -4110408161645448501L;

	private Long workItemId;

	private List<Long> workItemIds;

	private String refuseReason; // 审核拒绝原因

	private WorkItemDTO workItemDTO;// 详情

	private String submittedUserName;// 提交人

	private String approveUserName;// 审核人

	private String workItemStatus;// 审核状态

	private String submitStartTime;// 提交开始时间

	private String submitEndTime;// 提交结束时间

	private String approveStartTime;// 审核开始时间

	private String approveEndTime;// 审核结束时间

	private String functionIds;// 当前登陆人的审核权限功能ID

	private String bizFor;

	/**
	 *
	 * 描述： 审核人查询审核记录
	 *
	 * @return
	 */
	public String queryWorkItem() {
		UserDTO loginUserDTO = UserInfoUtils.getUserFromSession(null);
		List<FunctionDTO> functionList = remoteService
				.getSecurityConfigFacade().queryFunctionByUser(
						loginUserDTO.getUserId());
		StringBuilder sb = new StringBuilder();
		for (FunctionDTO funtion : functionList) {
			if (funtion.getCheckFunctionId() != null
					&& FunctionTypeEnum.WORKITEM_RISK_AUDIT != funtion
							.getFunctionType()) {
				sb.append(funtion.getCheckFunctionId()).append(",");
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		if (sb.length() > 0) {
			functionIds = sb.toString();
		} else {
			// 没有任何功能的双重复核权限
			functionIds = "null";
		}
		return SUCCESS;
	}

	/**
	 *
	 * 描述： 查询预审核记录
	 *
	 * @return
	 */
	public String queryForPreAudit() {
		UserDTO loginUserDTO = UserInfoUtils.getUserFromSession(null);
		List<FunctionDTO> functionList = remoteService
				.getSecurityConfigFacade().queryFunctionByUser(
						loginUserDTO.getUserId());
		StringBuilder sb = new StringBuilder();
		for (FunctionDTO funtion : functionList) {
			if (funtion.getCheckFunctionId() != null
					&& FunctionTypeEnum.WORKITEM_RISK_AUDIT == funtion
							.getFunctionType()) {
				sb.append(funtion.getCheckFunctionId()).append(",");
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		if (sb.length() > 0) {
			functionIds = sb.toString();
		} else {
			// 没有任何功能的双重复核权限
			functionIds = "null";
		}
		return SUCCESS;
	}

	/**
	 *
	 * 描述： 申请人查询审核记录
	 *
	 * @return
	 */
	public String queryWorkItemForSubmit() {
		return SUCCESS;
	}

	/**
	 *
	 * 描述： 风控预先审核
	 *
	 * @return
	 */
	public String preAudit() {
		try {
			UserDTO loginUserDTO = UserInfoUtils.getUserFromSession(null);
            if(null == workItemIds) {
			    remoteService.getWorkItemFacade().preAudit(workItemId,
					loginUserDTO);
            }
            else{
                remoteService.getWorkItemFacade().preAuditBatch(workItemIds,
                        loginUserDTO);
            }
		} catch (Exception e) {
			this.handleAuditException(e);
		}
		return SUCCESS;
	}

	/**
	 *
	 * 描述： 审核
	 *
	 * @return
	 */
	public String audit() {
		errMsg = null;
		try {
			UserDTO loginUserDTO = UserInfoUtils.getUserFromSession(null);
            if(null == workItemIds) {
                remoteService.getWorkItemFacade().checkSuccess(workItemId,
                        loginUserDTO);
            }
            else {
                remoteService.getWorkItemFacade().checkSuccessBatch(workItemIds,
                        loginUserDTO);
            }

		} catch (Exception e) {
			handleAuditException(e);
		}
		return SUCCESS;
	}

	/**
	 *
	 * 描述： 由审核提交人操作，撤销已提交的审核
	 *
	 * @return
	 */
	public String cancel() {
		errMsg = null;
		try {
			UserDTO loginUserDTO = UserInfoUtils.getUserFromSession(null);
			remoteService.getWorkItemFacade().checkCancel(workItemId,
					loginUserDTO);
		} catch (Exception e) {
			handleAuditException(e);
		}
		return SUCCESS;
	}

	/**
	 *
	 * 描述： 审核人拒绝审核操作
	 *
	 * @return
	 */
	public String refuse() {
		errMsg = null;
		try {
			UserDTO loginUserDTO = UserInfoUtils.getUserFromSession(null);
            if(null == workItemIds) {
			    remoteService.getWorkItemFacade().checkRefuse(workItemId,
					loginUserDTO, refuseReason);
            }
            else{
                remoteService.getWorkItemFacade().checkRefuseBatch(workItemIds,
                        loginUserDTO, refuseReason);
            }
		} catch (Exception e) {
			handleAuditException(e);
		}
		return SUCCESS;
	}

	/**
	 *
	 * 描述： 申请人查看审核记录详情
	 *
	 * @return
	 */
	public String showItemDetail() {
		errMsg = null;
		workItemDTO = remoteService.getWorkItemFacade().showWorkItemDetail(
				workItemId);
		return SUCCESS;
	}

	/**
	 *
	 * 描述： 点击审核按钮的操作，锁定记录进入详情审核
	 *
	 * @return
	 */
	public String lockWorkItem() {
		errMsg = null;
		try {
			UserDTO loginUserDTO = UserInfoUtils.getUserFromSession(null);
            if(null == workItemIds) {
			    remoteService.getWorkItemFacade().lockWorkItem(workItemId,
					loginUserDTO);
                workItemDTO = remoteService.getWorkItemFacade().showWorkItemDetail(
                        workItemId);
            }
            else{
                remoteService.getWorkItemFacade().lockWorkItemBatch(workItemIds,
                        loginUserDTO);
            }
		} catch (Exception e) {
			this.handleAuditException(e);
		}
		return SUCCESS;
	}

	/**
	 *
	 * 描述： 点击审核按钮的操作，锁定记录进入详情审核
	 *
	 * @return
	 */
	public String unLockWorkItem() {
		errMsg = null;
		try {
			UserDTO loginUserDTO = UserInfoUtils.getUserFromSession(null);
            if(null == workItemIds) {
			    remoteService.getWorkItemFacade().unLockWorkItem(workItemId,
					loginUserDTO);
            }
            else{
                remoteService.getWorkItemFacade().unLockWorkItemBatch(workItemIds,
                        loginUserDTO);
            }
		} catch (Exception e) {
			this.handleAuditException(e);
		}
		return SUCCESS;
	}

	/**
	 * 双权限审核异常处理
	 *
	 * @param e
	 */
	private void handleAuditException(Exception e) {
		if (e instanceof WorkItemException) {
			logger.error("", e);
			WorkItemException we = (WorkItemException) e;
			if (StringUtils.equals(we.getDefineCode(), "001")) {
				errMsg = "该审核记录不存在！";
			} else if (StringUtils.equals(we.getDefineCode(), "002")) {
				// 该审核记录已废弃异常
				errMsg = "该审核记录已废弃！";
			} else if (StringUtils.equals(we.getDefineCode(), "003")) {
				// 该记录正在被其他人锁定
				errMsg = "该审核记录已被其他人锁定！";
			} else if (StringUtils.equals(we.getDefineCode(), "004")) {
				errMsg = "只有提交人才能撤销自己的审核";
			} else if (StringUtils.equals(we.getDefineCode(), "005")) {
				errMsg = "记录已被处理，不可撤销";
			} else if (StringUtils.equals(we.getDefineCode(), "006")) {
				errMsg = "提交人不可审核自己的申请";
			} else if (StringUtils.equals(we.getDefineCode(), "007")) {
				errMsg = "没有双重复核权限";
			} else if (StringUtils.equals(we.getDefineCode(), "009")) {
				errMsg = "审核记录状态错误,不支持当前操作";
			} else {
				errMsg = "defineCode:" + we.getDefineCode() + " errorMessage:"
						+ e.getMessage();
			}
		} else if (e instanceof RuntimeException) {
			errMsg = e.getMessage();
			errMsg = errMsg.substring(errMsg.indexOf("orgExceptionInfo:") + 17 ,errMsg.length());
		} else {
			this.handleException(e);
		}
	}

	@SuppressWarnings("unused")
	private void callBack(String info) {
		try {
			ServletActionContext.getResponse().setContentType(
					"text/html;charset=UTF-8");
			ServletActionContext.getResponse().getWriter().write(info);
		} catch (IOException e) {
			logger.error("", e);
		}
	}

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

    public List<Long> getWorkItemIds() {
        return workItemIds;
    }

    public void setWorkItemIds(List<Long> workItemIds) {
        this.workItemIds = workItemIds;
    }

    /**
	 * refuseReason
	 *
	 * @return the refuseReason
	 */
	public String getRefuseReason() {
		return refuseReason;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param refuseReason
	 *            the refuseReason to set
	 */
	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	/**
	 * workItemDTO
	 *
	 * @return the workItemDTO
	 */
	public WorkItemDTO getWorkItemDTO() {
		return workItemDTO;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param workItemDTO
	 *            the workItemDTO to set
	 */
	public void setWorkItemDTO(WorkItemDTO workItemDTO) {
		this.workItemDTO = workItemDTO;
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

	/**
	 * workItemStatus
	 *
	 * @return the workItemStatus
	 */
	public String getWorkItemStatus() {
		return workItemStatus;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param workItemStatus
	 *            the workItemStatus to set
	 */
	public void setWorkItemStatus(String workItemStatus) {
		this.workItemStatus = workItemStatus;
	}

	/**
	 * submitStartTime
	 *
	 * @return the submitStartTime
	 */
	public String getSubmitStartTime() {
		return submitStartTime;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param submitStartTime
	 *            the submitStartTime to set
	 */
	public void setSubmitStartTime(String submitStartTime) {
		this.submitStartTime = submitStartTime;
	}

	/**
	 * submitEndTime
	 *
	 * @return the submitEndTime
	 */
	public String getSubmitEndTime() {
		return submitEndTime;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param submitEndTime
	 *            the submitEndTime to set
	 */
	public void setSubmitEndTime(String submitEndTime) {
		this.submitEndTime = submitEndTime;
	}

	/**
	 * approveStartTime
	 *
	 * @return the approveStartTime
	 */
	public String getApproveStartTime() {
		return approveStartTime;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param approveStartTime
	 *            the approveStartTime to set
	 */
	public void setApproveStartTime(String approveStartTime) {
		this.approveStartTime = approveStartTime;
	}

	/**
	 * approveEndTime
	 *
	 * @return the approveEndTime
	 */
	public String getApproveEndTime() {
		return approveEndTime;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param approveEndTime
	 *            the approveEndTime to set
	 */
	public void setApproveEndTime(String approveEndTime) {
		this.approveEndTime = approveEndTime;
	}

	/**
	 * functionIds
	 *
	 * @return the functionIds
	 */
	public String getFunctionIds() {
		return functionIds;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param functionIds
	 *            the functionIds to set
	 */
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

	public String getBizFor() {
		return bizFor;
	}

	public void setBizFor(String bizFor) {
		this.bizFor = bizFor;
	}
}
