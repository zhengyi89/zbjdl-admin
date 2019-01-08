/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.workitem.biz.impl;

import com.zbjdl.boss.admin.biz.WorkItemBiz;
import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.utils.TemplateUtil;
import com.zbjdl.boss.admin.workitem.dto.WorkItemDTO;
import com.zbjdl.boss.admin.workitem.dto.WorkItemTemplateDTO;
import com.zbjdl.boss.admin.workitem.entity.WorkItemEntity;
import com.zbjdl.boss.admin.workitem.enums.WorkItemResultEnum;
import com.zbjdl.boss.admin.workitem.enums.WorkItemStatusEnum;
import com.zbjdl.boss.admin.workitem.enums.WorkItemTypeEnum;
import com.zbjdl.boss.admin.workitem.exception.WorkItemException;
import com.zbjdl.boss.admin.workitem.service.WorkItemService;
import com.zbjdl.common.exception.BaseException;
import com.zbjdl.common.httpclient.SimpleHttpParam;
import com.zbjdl.common.httpclient.SimpleHttpResult;
import com.zbjdl.common.httpclient.SimpleHttpUtils;
import com.zbjdl.common.json.JSONObject;
import com.zbjdl.common.json.JSONUtils;
import com.zbjdl.common.utils.CheckUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 
 * 类名称：WorkItemBizImpl <br>
 * 类描述： <br>
 * 
 * @author feng
 * @since 2011-11-17 下午04:26:06
 * @author feng
 * @since 2013-06-08 下午16:42:17
 * @version 1.0.0
 * 
 */
public class WorkItemBizImpl implements WorkItemBiz {

	private final static Logger logger = LoggerFactory
			.getLogger(WorkItemBizImpl.class);

	/**
	 * 审核记录锁定超时：10分钟
	 */
	private static final long LOCK_TIMEOUT = 10 * 60 * 1000;

	/**
	 * 错误码和错误信息的分隔符
	 */
	private final static String separateForResult = "_";

	private WorkItemService workItemService;


	@Override
	public void createWorkItem(WorkItemDTO workItemDTO, UserDTO user) {
		if (workItemDTO == null) {
			throw WorkItemException.WORKITME_IS_NULL;
		}
		workItemDTO.setSubmittedBy(user.getUserId());
		workItemDTO.setSubmittedUserName(user.getUserName());
		workItemDTO.setSubmitTime(new Timestamp(System.currentTimeMillis()));
		if (workItemDTO.getWorkItemStatus() == null) {
			workItemDTO.setWorkItemStatus(WorkItemStatusEnum.WAITING);
		}
		if (workItemDTO.getWorkItemType() == null) {
			workItemDTO.setWorkItemType(WorkItemTypeEnum.NORMAL);
		}
		@SuppressWarnings("rawtypes")
		Map paramMap = JSONUtils.jsonToMap(workItemDTO.getContent(),
				String.class, null);
		// 根据功能ID查询日志模板
		WorkItemTemplateDTO template = workItemService
				.queryTemplateByFunctionId(workItemDTO.getFunctionId());
		if (template != null && StringUtils.isNotBlank(template.getContent())) {
			String fullContent = TemplateUtil.fillTemplate(
					template.getContent(), paramMap);
			workItemDTO.setFullContent(fullContent);
		}
		workItemService.createWorkItem(workItemDTO);
	}

	/**
	 * 
	 * 描述： 预审核
	 * 
	 * @param workItemId
	 *            编号
	 * @param user
	 *            当前操作人
	 */
	public void preAudit(Long workItemId, UserDTO user)
			throws WorkItemException {
		CheckUtils.notNull(workItemId, "WorkItemId");
		CheckUtils.notNull(user, "user");
		WorkItemDTO workItemDTO = workItemService.queryById(workItemId);
		if (workItemDTO == null) {
			throw WorkItemException.WORKITME_IS_NULL;
		}
		else if (workItemDTO.getSubmittedBy().equals(user.getUserId())) {// 自己不能审核自己提交的申请
			throw WorkItemException.WORKITME_OPERATOR_ERROR;
		}
		else if (WorkItemStatusEnum.RISK_CHECKING != workItemDTO.getWorkItemStatus()) {// 审核操作必须先锁定
			throw WorkItemException.WORKITME_STATUS_ERROR;
		}
		else if (!workItemDTO.getLockedBy().equals(user.getUserId())) {// 已被其他用户锁定
			throw WorkItemException.WORKITME_IS_LOCKED;
		}

		// 重置锁定信息
		workItemDTO.setLockedBy(null);
		workItemDTO.setLockedUserName(null);
		workItemDTO.setLockTime(null);

		// 更新审核人，审核时间
		workItemDTO.setApproveBy(user.getUserId());
		workItemDTO.setApproveUserName(user.getUserName());
		workItemDTO.setApproveTime(new Timestamp(System.currentTimeMillis()));
		workItemDTO.setWorkItemStatus(WorkItemStatusEnum.WAITING);
		workItemService.updateWorkItem(workItemDTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.WorkItemBiz#checkSuccess(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void checkSuccess(Long workItemId, UserDTO user) {
		WorkItemDTO workItemDTO = workItemService.queryById(workItemId);
		if (workItemDTO == null) {
			throw WorkItemException.WORKITME_IS_NULL;
		}
		WorkItemStatusEnum status = workItemDTO.getWorkItemStatus();
		if (WorkItemStatusEnum.FORBIDDEN == status) {// 该审核记录已废弃异常
			throw WorkItemException.WORKITME_IS_FORBIDDEN;
		}
		else if (WorkItemStatusEnum.CHECKING == status) {// 判断是否已被锁定
			if (user.getUserId().equals(workItemDTO.getLockedBy())) {
				// 更新审核人，审核时间
				workItemDTO.setApproveBy(user.getUserId());
				workItemDTO.setApproveUserName(user.getUserName());
				workItemDTO.setApproveTime(new Timestamp(System
						.currentTimeMillis()));
			} else {
				// 该记录正在被其他人审核中
				throw WorkItemException.WORKITME_IS_LOCKED;
			}
        }
		else {
            throw WorkItemException.WORKITME_STATUS_ERROR;
        }

		// 调用HttpClient提交请求
		String content = workItemDTO.getContent();
		@SuppressWarnings("rawtypes")
		Map requestMap = JSONUtils.jsonToMap(content, String.class, null);
		// 审核人
		requestMap.put(DataDictDTO.SESSION_AUDIT_USERID,
				String.valueOf(user.getUserId()));
		// TRIM空格
		for (Object key : requestMap.keySet()) {
			Object value = requestMap.get(key);
			if (value instanceof String) {
				requestMap.put(key, StringUtils.trim((String) value));
			} else if (value instanceof String[]) {
				String[] array = (String[]) value;
				for (int i = 0; i < array.length; i++) {
					array[i] = StringUtils.trim(array[i]);
				}
				requestMap.put(key, array);
			} else {
				requestMap.put(key, value);
			}
		}
		Object formUrl = requestMap.get(DataDictDTO.FORM_KEY);
		requestMap.put("admin-frame-passFlag", "true");
		if (formUrl == null) {
			logger.error("双权限审核formUrl is null.");
			workItemDTO.setWorkItemStatus(WorkItemStatusEnum.ERROR);
			workItemDTO.setInvocatResult(WorkItemResultEnum.SYSEXCEPTION);
			workItemDTO.setResultComment("formUrl is null");
			workItemService.updateWorkItem(workItemDTO);
		} else {
			String result = null;
			try {
				logger.info("workItem request formUrl : " + formUrl);
				logger.info("workItem request parmas : " + requestMap);
				SimpleHttpParam param = new SimpleHttpParam(formUrl.toString());
				param.setParameters(requestMap);
				param.setCharSet("UTF-8");
				param.setMethod(SimpleHttpUtils.HTTP_METHOD_POST);
				param.setIgnoreContentIfUnsuccess(false);
				SimpleHttpResult httpResult = SimpleHttpUtils
						.httpRequest(param);
				result = this.handleHttpResult(httpResult);
			} catch (Exception e) {
				logger.error("workItem request error, URL : " + formUrl, e);
				String message = getErrorMessage(e);
				result = actionException(WorkItemResultEnum.SYSEXCEPTION,
						message);
			}
			result = StringUtils.trim(result);
			logger.info("workItem request result ：" + result);
			if (StringUtils.isBlank(result)) {
				result = this.actionException(
						WorkItemResultEnum.ACTIONEXCEPTION, "双权限审核未返回任何信息");
			}
			// 获取第一个下划线的位置
			int separeteIndex = result.indexOf(separateForResult);
			if (separeteIndex >= 0) {
				String resultCode = result.substring(0, separeteIndex);
				String resultComment = result.substring(separeteIndex + 1,
						result.length());
				workItemDTO.setResultComment(resultComment);
				workItemDTO.setInvocatResult(WorkItemResultEnum
						.getResultByCode(resultCode));
			} else {
				workItemDTO.setResultComment(result);
				workItemDTO.setInvocatResult(WorkItemResultEnum
						.getResultByCode(result));
			}
			if (workItemDTO.getInvocatResult() == WorkItemResultEnum.SUCCESS) {
				workItemDTO.setWorkItemStatus(WorkItemStatusEnum.SUCESS);
			} else {
				workItemDTO.setWorkItemStatus(WorkItemStatusEnum.ERROR);
			}
			workItemService.updateWorkItem(workItemDTO);

			if (workItemDTO.getInvocatResult() != WorkItemResultEnum.SUCCESS) {
				WorkItemException e = WorkItemException.BIZ_EXECUTE_FAIL;
				e.setMessage(workItemDTO.getResultComment());
				throw e;
			}
		}

	}

    /*
     * (non-Javadoc) WorkItemworkItemService.queryById(workItemId);
     *
     * @see
     * com.zbjdl.boss.admin.biz.WorkItemBiz#checkRefuse(java.lang.Long)
     */
	@Override
	public void checkRefuse(Long workItemId, UserDTO user, String refuseReason) {
		WorkItemDTO workItemDTO = workItemService.queryById(workItemId);
		if (workItemDTO == null) {
			throw WorkItemException.WORKITME_IS_NULL;
		}
		WorkItemStatusEnum status = workItemDTO.getWorkItemStatus();

		if (WorkItemStatusEnum.CHECKING == status
				|| WorkItemStatusEnum.RISK_CHECKING == status) {
			if (user.getUserId().equals(workItemDTO.getLockedBy())) {
				workItemDTO.setWorkItemStatus(WorkItemStatusEnum.REFUSE);
				// 更新拒绝人，拒绝时间
				workItemDTO.setRejectedBy(user.getUserId());
				workItemDTO.setRejectUserName(user.getUserName());
				workItemDTO.setRejectTime(new Timestamp(System
						.currentTimeMillis()));
				workItemDTO.setRejectCause(refuseReason);
				workItemService.updateWorkItem(workItemDTO);
			} else {
				// 该记录被锁定
				throw WorkItemException.WORKITME_IS_LOCKED;
			}
		} else if (WorkItemStatusEnum.FORBIDDEN == status) {
			// 该审核记录已废弃异常
			throw WorkItemException.WORKITME_IS_FORBIDDEN;
		} else {
			throw WorkItemException.WORKITME_STATUS_ERROR;
		}
	}

    /*
     * (non-Javadoc)
     *
     * @see
     * com.zbjdl.boss.admin.biz.WorkItemBiz#checkCancel(java.lang.Long)
     */
	@Override
	public void checkCancel(Long workItemId, UserDTO user) {
		WorkItemDTO workItemDTO = workItemService.queryById(workItemId);
		if (workItemDTO == null) {
			throw WorkItemException.WORKITME_IS_NULL;
		}
		else if (!workItemDTO.getSubmittedBy().equals(user.getUserId())) {
			// 非提交人不可撤销非自己的审核记录异常
			throw WorkItemException.WORKITME_CANCEL_ERROR;
		}

		WorkItemStatusEnum status = workItemDTO.getWorkItemStatus();
		// 待审核、风控待审核状态都可以撤销
		if (WorkItemStatusEnum.WAITING == status
				|| WorkItemStatusEnum.RISK_WAITING == status) {
			workItemDTO.setWorkItemStatus(WorkItemStatusEnum.FORBIDDEN);
			workItemDTO.setCancelBy(user.getUserId());
			workItemDTO.setCancelUserName(user.getUserName());
			workItemDTO
					.setCancelTime(new Timestamp(System.currentTimeMillis()));
			workItemService.updateWorkItem(workItemDTO);
		} else if (WorkItemStatusEnum.FORBIDDEN != status) {
			// 该审核记录已撤销则直接通过，否则异常
			throw WorkItemException.WORKITME_STATUS_ERROR;
		}
	}

    @Override
	public List<WorkItemEntity> getWorkItemByObject(WorkItemEntity entity) {
		List<WorkItemEntity> workItemEntities = workItemService
				.getWorkItemByObject(entity);
		return workItemEntities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.WorkItemBiz#lockWorkItem(java.lang.Long)
	 */
	@Override
	public void lockWorkItem(Long workItemId, UserDTO user) {
		CheckUtils.notEmpty(workItemId, "workItemId");
		CheckUtils.notEmpty(user, "user");
		WorkItemDTO workItemDTO = workItemService.queryById(workItemId);
		if (workItemDTO == null) {
			throw WorkItemException.WORKITME_IS_NULL;
		}
		WorkItemStatusEnum status = workItemDTO.getWorkItemStatus();
		if (workItemDTO.getSubmittedBy().equals(user.getUserId())) {
			// 提交人不可审核自己的审核记录异常
			throw WorkItemException.WORKITME_OPERATOR_ERROR;
		} else if (WorkItemStatusEnum.WAITING == status
				|| WorkItemStatusEnum.RISK_WAITING == status) {
			lockWorkItem(workItemDTO, user);
		} else if (WorkItemStatusEnum.CHECKING == status
				|| WorkItemStatusEnum.RISK_CHECKING == status) {
			// 判断是否锁定超时
			if (System.currentTimeMillis()
					- workItemDTO.getLockTime().getTime() > LOCK_TIMEOUT) {
				lockWorkItem(workItemDTO, user);
			} else if (!workItemDTO.getLockedBy().equals(user.getUserId())) {
				// 已被其他人锁定
				throw WorkItemException.WORKITME_IS_LOCKED;
			}
		} else {
			// 其他状态则错误
			throw WorkItemException.WORKITME_STATUS_ERROR;
		}
	}

	/**
	 * 描述： 锁定记录
	 * 
	 * @param workItemDTO
	 * @param user
	 */
	private void lockWorkItem(WorkItemDTO workItemDTO, UserDTO user) {
		WorkItemStatusEnum status = workItemDTO.getWorkItemStatus();
		if (status == WorkItemStatusEnum.RISK_WAITING) {
			workItemDTO.setWorkItemStatus(WorkItemStatusEnum.RISK_CHECKING);
		} else if (status == WorkItemStatusEnum.WAITING) {
			workItemDTO.setWorkItemStatus(WorkItemStatusEnum.CHECKING);
		}

		// 更新锁定者信息
		workItemDTO.setLockedBy(user.getUserId());
		workItemDTO.setLockedUserName(user.getUserName());
		workItemDTO.setLockTime(new Timestamp(System.currentTimeMillis()));
		workItemService.updateWorkItem(workItemDTO);
	}

    @Override
    public void unLockWorkItem(Long workItemId, UserDTO user) {
        CheckUtils.notEmpty(workItemId, "workItemId");
        WorkItemDTO workItemDTO = workItemService.queryById(workItemId);
        if (workItemDTO == null) {
            throw WorkItemException.WORKITME_IS_NULL;
        }

        if (!workItemDTO.getLockedBy().equals(user.getUserId())) {
            throw WorkItemException.WORKITME_IS_LOCKED;
        }

        if (workItemDTO.getWorkItemStatus() == WorkItemStatusEnum.CHECKING) {
            workItemDTO.setWorkItemStatus(WorkItemStatusEnum.WAITING);
        } else if (workItemDTO.getWorkItemStatus() == WorkItemStatusEnum.RISK_CHECKING) {
            workItemDTO.setWorkItemStatus(WorkItemStatusEnum.RISK_WAITING);
        } else {
            // 状态错误
            throw WorkItemException.WORKITME_STATUS_ERROR;
        }

	    // 更新锁定者信息
        workItemDTO.setLockedBy(null);
        workItemDTO.setLockedUserName(null);
        workItemDTO.setLockTime(null);
        workItemService.updateWorkItem(workItemDTO);
    }

	@Override
	public void updateStatus(WorkItemStatusEnum status, Long workItemId) {
		workItemService.updateStatus(status, workItemId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.WorkItemBiz#queryWorkItemById(java.lang
	 * .Long)
	 */
	@Override
	public WorkItemDTO queryWorkItemById(Long workItemId) {
		return workItemService.queryById(workItemId);
	}

	@Override
	public List<WorkItemEntity> queryWorkItemByResourceId(String resourceId) {
		return workItemService.queryWorkItemByResourceId(resourceId);
	}

	@Override
	public WorkItemTemplateDTO queryTemplateByFunctionId(Long functionId) {
		return workItemService.queryTemplateByFunctionId(functionId);
	}

	@Override
	public void addTemplate(WorkItemTemplateDTO workItemTemplateDTO) {
		workItemService.addTemplate(workItemTemplateDTO);
	}

	@Override
	public void deleteTemplate(Long templateId) {
		workItemService.deleteTemplate(templateId);
	}

	@Override
	public void updateTemplate(Long templateId,
			WorkItemTemplateDTO workItemTemplateDTO) {
		workItemService.updateTemplate(templateId, workItemTemplateDTO);
	}

	@Override
	public WorkItemTemplateDTO queryTemplate(Long templateId) {
		return workItemService.queryTemplate(templateId);
	}

	/**
	 * workItemService
	 * 
	 * @return the workItemService
	 */

	public WorkItemService getWorkItemService() {
		return workItemService;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param workItemService
	 *            the workItemService to set
	 */
	public void setWorkItemService(WorkItemService workItemService) {
		this.workItemService = workItemService;
	}

	/**
	 * 解析http请返回内容
	 * 
	 * @param httpResult
	 * @return
	 */
	private String handleHttpResult(SimpleHttpResult httpResult) {
		String result = null;
		if (httpResult != null && httpResult.isSuccess()) {
			String content = httpResult.getContent();
			if (StringUtils.isBlank(content)) {
				return null;
			} else {
				content = StringUtils.trim(content);
				boolean isJson = false;
				String contentType = httpResult.getContentType();
				if (StringUtils.containsIgnoreCase(contentType, "json")) {
					isJson = true;
					// 服务器端可能以JSON类型返回，但不包含任何有效数据，认为是成功
					if (StringUtils.isBlank(content)) {
						content = "{}";
					}
				} else if (StringUtils.startsWith(content, "{")
						&& StringUtils.endsWith(content, "}")) {
					isJson = true;
				}
				// 返回JSON数据
				if (isJson) {
					try {
						content = StringUtils
								.replace(content, ":null", ":\"\"");
						JSONObject json = new JSONObject(content);
						String code = json.optString("code", null);
						if (code == null) {
							// 强制成功标识
							code = json.optString("success", null);
						}
						// 错误信息标识
						String errMsg = json.optString("message", null);
						if (errMsg == null) {
							errMsg = json.optString("errMsg", null);
						}

						boolean isSuccess = checkSuccess(code);

						if (isSuccess || StringUtils.isBlank(errMsg)) {
							result = actionException(
									WorkItemResultEnum.SUCCESS,
									StringUtils.isBlank(errMsg) ? "执行成功"
											: errMsg);
						} else {
							WorkItemResultEnum status = null;
							if (StringUtils.isNotBlank(code)) {
								status = WorkItemResultEnum
										.getResultByCode(code);
							}
							if (status == WorkItemResultEnum.UNDEFINED) {
								status = WorkItemResultEnum.ACTIONEXCEPTION;
							}
							result = actionException(status,
									StringUtils.isBlank(errMsg) ? "执行失败"
											: errMsg);
						}
					} catch (Exception e) {
						result = content;
					}
				} else if (StringUtils.startsWith(content, "<!DOCTYPE")) {
					// 返回错误页面
					String message = getErrorMessage(content);
					if (StringUtils.isBlank(message)) {
						result = actionException(WorkItemResultEnum.SUCCESS,
								"执行成功");
					} else {
						result = actionException(
								WorkItemResultEnum.ACTIONEXCEPTION, message);
					}
				} else {
					// 字符串返回
					result = content;
				}
			}
		} else if (httpResult != null) {
			Exception e = httpResult.getException();
			if (e != null) {
				logger.error("workItem request error : ", e);
				String message = getErrorMessage(e);
				result = actionException(WorkItemResultEnum.SYSEXCEPTION,
						message);
			} else {
				result = actionException(WorkItemResultEnum.SYSEXCEPTION,
						"StatusCode_" + httpResult.getStatusCode());
			}
		}
		return result;
	}

	private boolean checkSuccess(String str) {
		return StringUtils.equalsIgnoreCase("true", str)
				|| StringUtils.equalsIgnoreCase("success", str)
				|| StringUtils.equals(
						WorkItemResultEnum.SUCCESS.getResultCode(), str);
	}

	/**
	 * 取得异常信息
	 * 
	 * @param e
	 * @return
	 */
	private String getErrorMessage(Exception e) {
		StringBuilder sb = new StringBuilder();
		sb.append(e.getClass().getSimpleName());
		if (e instanceof BaseException) {
			BaseException ybe = (BaseException) e;
			sb.append("_");
			sb.append(ybe.getDefineCode());
		}
		sb.append("_");
		sb.append(e.getMessage());
		return sb.toString();
	}

	private String getErrorMessage(String document) {
		// TODO:错误页面改版后得对应修改才能取得错误信息
		String message = StringUtils.substringBetween(document, "<h3>",
				"<p>系统将在");
		if (StringUtils.isNotBlank(message)) {
			message = message.replaceAll("</?[^>]+>", "");
			message = message.replaceAll("[ \n\t]+", " ");
		} else {
			String title = StringUtils.substringBetween(document, "<title>",
					"</title>");
			if (StringUtils.contains(title, "错误")
					|| StringUtils.contains(title, "异常")) {
				message = title;
			}
		}
		return message;
	}

	private String actionException(WorkItemResultEnum invokeResult,
			String message) {
		if (message != null && message.length() > 600) {
			message = StringUtils.substring(message, 0, 600);
		}
		return invokeResult.getResultCode() + "_" + message;
	}

	@Override
	public void updateWorkItem(WorkItemDTO workItemDTO) {
		workItemService.updateWorkItem(workItemDTO);
	}

}
