/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.template.manager;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zbjdl.boss.admin.facade.SecurityConfigFacade;
import com.zbjdl.boss.admin.facade.WorkItemFacade;
import com.zbjdl.boss.admin.frame.utils.Constants;
import com.zbjdl.boss.admin.workitem.dto.WorkItemTemplateDTO;
import com.zbjdl.common.dubbo.proxy.SoaServiceRepository;

/**
 * 审核模板管理调用
 * 
 * @author：feng
 * @since：2012-6-14 下午03:22:13
 * @version:
 */
@Component
public class WorkItemTemplateManager {

	/**
	 * 审核服务接口
	 */
	private WorkItemFacade workItemFacade = SoaServiceRepository
			.getService(WorkItemFacade.class,Constants.DUBBO_DEFAULT_GROUP,Constants.DUBBO_DEFAULT_VERSION);

	private SecurityConfigFacade securityConfigFacade = SoaServiceRepository
			.getService(SecurityConfigFacade.class,Constants.DUBBO_DEFAULT_GROUP,Constants.DUBBO_DEFAULT_VERSION);

	public void addTemplate(Long functionId, String keyWord, String content,
			Long departmentId) {
		checkTemplate(functionId, departmentId, -1L);
		WorkItemTemplateDTO workItemTemplateDTO = new WorkItemTemplateDTO();
		workItemTemplateDTO.setFunctionId(functionId);
		workItemTemplateDTO.setKeyWord(keyWord);
		workItemTemplateDTO.setContent(content);
		workItemFacade.addTemplate(workItemTemplateDTO);
	}

	public void deleteWorkItemTemplate(Long templateId) {
		workItemFacade.deleteTemplate(templateId);
	}

	public void updateTemplate(Long templateId, Long functionId,
			String keyWord, String content, Long departmentId) {
		checkTemplate(functionId, departmentId, templateId);
		WorkItemTemplateDTO workItemTemplateDTO = new WorkItemTemplateDTO();
		workItemTemplateDTO.setFunctionId(functionId);
		workItemTemplateDTO.setKeyWord(keyWord);
		workItemTemplateDTO.setContent(content);
		workItemFacade.updateTemplate(templateId, workItemTemplateDTO);
	}

	private void checkTemplate(Long functionId, Long departmentId,
			long templateId) {
		WorkItemTemplateDTO workItemTemplateDTO = workItemFacade
				.queryTemplateByFunctionId(functionId);
		if (workItemTemplateDTO != null
				&& workItemTemplateDTO.getTemplateId() != templateId) {
			throw new IllegalArgumentException("功能ID重复");
		}
		List<Long> departmentIds = securityConfigFacade
				.queryDepartmentAndFunctionRelation(functionId);
		if (departmentId != null && departmentIds != null
				&& !departmentIds.contains(departmentId)) {
			throw new IllegalArgumentException("部门管理员只能添加本部门功能的模板");
		}
	}

	public WorkItemTemplateDTO queryTemplate(Long templateId) {
		return workItemFacade.queryTemplate(templateId);
	}

	public WorkItemTemplateDTO queryTemplateByFunctionId(Long functionId) {
		return workItemFacade.queryTemplateByFunctionId(functionId);
	}
}
