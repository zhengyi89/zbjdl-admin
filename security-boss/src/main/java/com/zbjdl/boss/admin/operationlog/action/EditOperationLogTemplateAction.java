/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.operationlog.action;

import com.opensymphony.xwork2.ModelDriven;
import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.dto.operationlog.OperationLogTemplateDTO;
import com.zbjdl.boss.admin.operationlog.vo.OperationLogTemplateVO;

/**
 *
 * 类名称：EditOperationLogTemplateAction <br>
 * 类描述： 编辑操作日志模板Action<br>
 *
 * @author：feng
 * @since：2011-7-28 下午01:21:36
 * @version:
 *
 */
public class EditOperationLogTemplateAction extends EmployeeBossBaseAction
		implements ModelDriven<OperationLogTemplateVO> {

	private static final long serialVersionUID = 1L;

	private OperationLogTemplateVO templateVO;

	public String init() {

		if ("edit".equals(templateVO.getType())) {
			OperationLogTemplateDTO dto = remoteService
					.getOperationLogFacade().queryTemplate(
							templateVO.getTemplateID());

			templateVO.setContent(dto.getContent());
			templateVO.setFunctionId(dto.getFunctionId());
			templateVO.setKeyWord(dto.getKeyWord());
			templateVO.setTemplateID(dto.getTemplateId());
		}
		return SUCCESS;
	}

	public String execute() {
		OperationLogTemplateDTO dto = remoteService.getOperationLogFacade()
				.queryTemplate(templateVO.getTemplateID());

		dto.setContent(templateVO.getContent());
		dto.setKeyWord(templateVO.getKeyWord());
		dto.setTemplateId(templateVO.getTemplateID());

		remoteService.getOperationLogFacade().updateTemplate(
				templateVO.getTemplateID(), dto);
		return SUCCESS;
	}

	public String add() {
		OperationLogTemplateDTO dto = new OperationLogTemplateDTO();

		dto.setContent(templateVO.getContent());
		dto.setKeyWord(templateVO.getKeyWord());
		dto.setTemplateId(templateVO.getTemplateID());
		dto.setFunctionId(templateVO.getFunctionId());

		remoteService.getOperationLogFacade().addTemplate(dto);

		return SUCCESS;
	}

	public String delete() {
		remoteService.getOperationLogFacade().deleteTemplate(
				templateVO.getTemplateID());
		return SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public OperationLogTemplateVO getModel() {
		templateVO = new OperationLogTemplateVO();
		return templateVO;
	}

	/**
	 * operationLogTemplateVO
	 *
	 * @return the operationLogTemplateVO
	 */

	public OperationLogTemplateVO getTemplateVO() {
		return templateVO;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param operationLogTemplateVO
	 *            the operationLogTemplateVO to set
	 */
	public void setTemplateVO(OperationLogTemplateVO operationLogTemplateVO) {
		this.templateVO = operationLogTemplateVO;
	}

}
