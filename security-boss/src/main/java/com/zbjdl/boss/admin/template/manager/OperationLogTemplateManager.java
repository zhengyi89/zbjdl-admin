/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.template.manager;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zbjdl.boss.admin.dto.operationlog.OperationLogTemplateDTO;
import com.zbjdl.boss.admin.facade.OperationLogFacade;
import com.zbjdl.boss.admin.facade.SecurityConfigFacade;
import com.zbjdl.boss.admin.frame.utils.Constants;
import com.zbjdl.common.dubbo.proxy.SoaServiceRepository;

/**
 * 操作日志模板管理调用
 * 
 * @author：feng
 * @since：2012-6-14 下午03:23:46
 * @version:
 */
@Component
public class OperationLogTemplateManager {

	/**
	 * 操作日志服务接口
	 */
	private OperationLogFacade operationLogFacade = SoaServiceRepository
			.getService(OperationLogFacade.class,Constants.DUBBO_DEFAULT_GROUP,Constants.DUBBO_DEFAULT_VERSION);

	private SecurityConfigFacade securityConfigFacade = SoaServiceRepository
			.getService(SecurityConfigFacade.class,Constants.DUBBO_DEFAULT_GROUP,Constants.DUBBO_DEFAULT_VERSION);

	public void addTemplate(Long functionId, String keyWord, String content,
			Long departmentId) {
		checkTemplate(functionId, departmentId, -1);
		OperationLogTemplateDTO operationLogTemplateDTO = new OperationLogTemplateDTO();
		operationLogTemplateDTO.setFunctionId(functionId);
		operationLogTemplateDTO.setKeyWord(keyWord);
		operationLogTemplateDTO.setContent(content);
		operationLogFacade.addTemplate(operationLogTemplateDTO);
	}

	public void deleteLogTemplate(Long templateId) {
		operationLogFacade.deleteTemplate(templateId);
	}

	public void updateTemplate(Long templateId, Long functionId,
			String keyWord, String content, Long departmentId) {
		checkTemplate(functionId, departmentId, templateId);
		OperationLogTemplateDTO operationLogTemplateDTO = new OperationLogTemplateDTO();
		operationLogTemplateDTO.setFunctionId(functionId);
		operationLogTemplateDTO.setKeyWord(keyWord);
		operationLogTemplateDTO.setContent(content);
		operationLogFacade.updateTemplate(templateId, operationLogTemplateDTO);
	}

	private void checkTemplate(Long functionId, Long departmentId,
			long templateId) {
		OperationLogTemplateDTO operationLogTemplateDTO = operationLogFacade
				.queryTemplateByFunctionId(functionId);
		if (operationLogTemplateDTO != null
				&& operationLogTemplateDTO.getTemplateId() != templateId) {
			throw new IllegalArgumentException("功能ID重复");
		}
		List<Long> departmentIds = securityConfigFacade
				.queryDepartmentAndFunctionRelation(functionId);
		if (departmentId != null && departmentIds != null
				&& !departmentIds.contains(departmentId)) {
			throw new IllegalArgumentException("部门管理员只能添加本部门功能的模板");
		}
	}

	public OperationLogTemplateDTO queryTemplate(Long templateId) {
		return operationLogFacade.queryTemplate(templateId);
	}

	public OperationLogTemplateDTO queryTemplateByFunctionId(Long functionId) {
		return operationLogFacade.queryTemplateByFunctionId(functionId);
	}
}
