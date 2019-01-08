/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.facade;

import java.util.Map;

import com.zbjdl.boss.admin.dto.operationlog.OperationLogDTO;
import com.zbjdl.boss.admin.dto.operationlog.OperationLogTemplateDTO;

/**
 *
 * 类名称：OperationLogFacade <br>
 * 类描述：操作日志门面接口<br>
 * @author：feng
 * @version:
 *
 */
public interface OperationLogFacade {

	/**
	 * 描述：写操作日志
	 * @param paramMap ：http参数
	 * @param userId ：操作员Id
	 * @param functionUrl ：请求路径
	 */
	public void writeLogInfo(@SuppressWarnings("rawtypes") Map paramMap, Long userId, String functionUrl);

	/**
	 *
	 * 描述：写操作日志
	 * @param operationLogDTO	操作日志信息
	 */
	public void writeLog(OperationLogDTO operationLogDTO);

	/**
	 *
	 * 描述：    添加操作日志模板
	 * @param operationLogTemplate	模板对象
	 */
	public void addTemplate(OperationLogTemplateDTO operationLogTemplate);

	/**
	 *
	 * 描述：    删除操作日志模板
	 * @param templateId	模板ID
	 */
	public void deleteTemplate(Long templateId);

	/**
	 *
	 * 描述：    查询操作日志模板
	 * @param templateId	模板ID
	 */
	public OperationLogTemplateDTO queryTemplate(Long templateId);

	/**
	 *
	 * 描述：    根据功能ID查询操作日志模板
	 * @param functionId	功能ID
	 */
	public OperationLogTemplateDTO queryTemplateByFunctionId(Long functionId);

	/**
	 *
	 * 描述：    更新操作日志模板
	 * @param templateId	模板ID
	 * @param operationLogTemplate	模板对象
	 */
	public void updateTemplate(Long templateId, OperationLogTemplateDTO operationLogTemplate);

}
