/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.operationlog.facade;

import java.util.Map;

import com.zbjdl.boss.admin.biz.OperationLogBiz;
import com.zbjdl.boss.admin.dto.operationlog.OperationLogDTO;
import com.zbjdl.boss.admin.dto.operationlog.OperationLogTemplateDTO;
import com.zbjdl.boss.admin.facade.OperationLogFacade;

/**
 *
 * 类名称：OperationLogFacadeImpl <br>
 * 类描述：操作日志门面实现类<br>
 * @author：feng
 * @since：2011-7-22 下午05:50:28
 * @version:
 *
 */
public class OperationLogFacadeImpl implements OperationLogFacade {

	private OperationLogBiz operationLogBiz;

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.facade.OperationLogFacade#writeLog(java.util.Map, java.lang.Long, java.lang.String)
	 */
	@Override
	public void writeLogInfo(@SuppressWarnings("rawtypes") Map paramMap, Long userId, String functionUrl) {
		operationLogBiz.writeLog(paramMap, userId, functionUrl)	;
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.facade.OperationLogFacade#writeLog(com.zbjdl.boss.admin.facade.operationlog.dto.OperationLogDTO)
	 */
	@Override
	public void writeLog(OperationLogDTO operationLogDTO) {
		operationLogBiz.writeLog(operationLogDTO);
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.facade.OperationLogFacade#addTemplate(com.zbjdl.boss.admin.dto.operationlog.OperationLogTemplateDTO)
	 */
	@Override
	public void addTemplate(OperationLogTemplateDTO operationLogTemplate) {
		operationLogBiz.addTemplate(operationLogTemplate);
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.facade.OperationLogFacade#deleteTemplate(java.lang.Long)
	 */
	@Override
	public void deleteTemplate(Long templateId) {
		operationLogBiz.deleteTemplate(templateId);
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.facade.OperationLogFacade#updateTemplate(java.lang.Long, com.zbjdl.boss.admin.dto.operationlog.OperationLogTemplateDTO)
	 */
	@Override
	public void updateTemplate(Long templateId,
			OperationLogTemplateDTO operationLogTemplate) {
		operationLogBiz.updateTemplate(templateId, operationLogTemplate);
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.facade.OperationLogFacade#queryTemplate(java.lang.Long)
	 */
	@Override
	public OperationLogTemplateDTO queryTemplate(Long templateId) {
		return operationLogBiz.queryTemplate(templateId);
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * @param operationLogBiz the operationLogBiz to set
	 */
	public void setOperationLogBiz(OperationLogBiz operationLogBiz) {
		this.operationLogBiz = operationLogBiz;
	}

	@Override
	public OperationLogTemplateDTO queryTemplateByFunctionId(Long functionId) {
		return operationLogBiz.queryTemplateByFunctionId(functionId);
	}

}
