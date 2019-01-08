/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.template.action;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.dto.operationlog.OperationLogTemplateDTO;
import com.zbjdl.boss.admin.template.manager.OperationLogTemplateManager;
import com.zbjdl.boss.admin.template.manager.WorkItemTemplateManager;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;
import com.zbjdl.boss.admin.workitem.dto.WorkItemTemplateDTO;
import com.zbjdl.common.utils.CheckUtils;

/**
 * @author：feng
 * @since：2012-6-13 下午02:49:32
 * @version:
 */
public class TemplateManageAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 2492197541152028600L;

	@Autowired
	private OperationLogTemplateManager operationLogTemplateManager;

	@Autowired
	private WorkItemTemplateManager workItemTemplateManager;

	/**
	 * 模板标识
	 */
	private String templateTag;

	/**
	 * 模板ID
	 */
	private Long templateId;

	/**
	 * 功能ID
	 */
	private Long functionId;

	/**
	 * 模板关键字
	 */
	private String keyWord;

	/**
	 * 模板内容
	 */
	private String content;

	/**
	 * 审核模板DTO
	 */
	private WorkItemTemplateDTO workItemTemplateDTO;

	/**
	 * 日志模板DTO
	 */
	private OperationLogTemplateDTO operationLogTemplateDTO;

	/**
	 * 添加模板
	 *
	 */
	public String addTemplate() {
		try {
			CheckUtils.valueIsEmpty(templateTag, "templateTag");
			CheckUtils.valueIsEmpty(functionId.toString(), "functionId");
			CheckUtils.valueIsEmpty(content, "content");
			HttpSession session = getMVCFacade().getHttpSession();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			if (templateTag.equals("log")) {
				operationLogTemplateManager.addTemplate(functionId, keyWord,
						content, user.getPrimaryDepartmentId());
			} else {
				workItemTemplateManager.addTemplate(functionId, keyWord,
						content, user.getPrimaryDepartmentId());
			}
		} catch (Exception e) {
			this.handleException(e);
		}
		return SUCCESS;
	}

	/**
	 * 跳转到模板修改页面
	 *
	 */
	public String showUpdateTemplate() {
		try {
			CheckUtils.valueIsEmpty(templateTag, "templateTag");
			if (templateTag.equals("log")) {
				if (templateId != null && functionId == null) {
					operationLogTemplateDTO = operationLogTemplateManager
							.queryTemplate(templateId);
				} else {
					operationLogTemplateDTO = operationLogTemplateManager
							.queryTemplateByFunctionId(functionId);
					if (operationLogTemplateDTO != null) {
						templateId = operationLogTemplateDTO.getTemplateId();
					}
				}
			} else {
				if (templateId != null && functionId == null) {
					workItemTemplateDTO = workItemTemplateManager
							.queryTemplate(templateId);
				} else {
					workItemTemplateDTO = workItemTemplateManager
							.queryTemplateByFunctionId(functionId);
					if (workItemTemplateDTO != null) {
						templateId = workItemTemplateDTO.getTemplateId();
					}
				}
			}
			if (templateId == null) {
				return "add";
			}
		} catch (Exception e) {
			this.handleException(e);
		}
		return "update";
	}

	/**
	 * 修改模板
	 *
	 */
	public String updateTemplate() {
		try {
			CheckUtils.valueIsEmpty(templateId.toString(), "templateId");
			CheckUtils.valueIsEmpty(templateTag, "templateTag");
			CheckUtils.valueIsEmpty(functionId.toString(), "functionId");
			CheckUtils.valueIsEmpty(content, "content");
			HttpSession session = getMVCFacade().getHttpSession();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			if (templateTag.equals("log")) {
				operationLogTemplateManager.updateTemplate(templateId,
						functionId, keyWord, content,
						user.getPrimaryDepartmentId());
			} else {
				workItemTemplateManager.updateTemplate(templateId, functionId,
						keyWord, content, user.getPrimaryDepartmentId());
			}
		} catch (Exception e) {
			this.handleException(e);
		}
		return SUCCESS;
	}

	/**
	 * 删除日志模板
	 */
	public String deleteLogTemplate() {
		try {
			CheckUtils.valueIsNull(templateId.toString(), "templateId");
			operationLogTemplateManager.deleteLogTemplate(templateId);
		} catch (Exception e) {
			this.handleException(e);
		}
		return SUCCESS;
	}

	/**
	 * 删除双权限审核模板
	 */
	public String deleteWorkItemTemplate() {
		try {
			CheckUtils.valueIsNull(templateId.toString(), "templateId");
			workItemTemplateManager.deleteWorkItemTemplate(templateId);
		} catch (Exception e) {
			this.handleException(e);
		}
		return SUCCESS;
	}

	public String getTemplateTag() {
		return templateTag;
	}

	public void setTemplateTag(String templateTag) {
		this.templateTag = templateTag;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public WorkItemTemplateDTO getWorkItemTemplateDTO() {
		return workItemTemplateDTO;
	}

	public void setWorkItemTemplateDTO(WorkItemTemplateDTO workItemTemplateDTO) {
		this.workItemTemplateDTO = workItemTemplateDTO;
	}

	public OperationLogTemplateDTO getOperationLogTemplateDTO() {
		return operationLogTemplateDTO;
	}

	public void setOperationLogTemplateDTO(
			OperationLogTemplateDTO operationLogTemplateDTO) {
		this.operationLogTemplateDTO = operationLogTemplateDTO;
	}

}
