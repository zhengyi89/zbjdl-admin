/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.operationlog.biz.impl;

import java.util.Date;
import java.util.Map;

import com.zbjdl.boss.admin.biz.OperationLogBiz;
import com.zbjdl.boss.admin.biz.SecurityBiz;
import com.zbjdl.boss.admin.dto.operationlog.OperationLogDTO;
import com.zbjdl.boss.admin.dto.operationlog.OperationLogTemplateDTO;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.operationlog.convert.impl.OperationLogTemplateConvert;
import com.zbjdl.boss.admin.operationlog.entity.OperationLogEntity;
import com.zbjdl.boss.admin.operationlog.entity.OperationLogTemplateEntity;
import com.zbjdl.boss.admin.operationlog.enums.HttpParamFilter;
import com.zbjdl.boss.admin.repository.OperationLogDao;
import com.zbjdl.boss.admin.repository.OperationLogTemplateDao;
import com.zbjdl.boss.admin.utils.TemplateUtil;
import com.zbjdl.common.json.JSONUtils;
import com.zbjdl.common.log.SoaLogUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 类名称：OperationLogBizImpl <br>
 * 类描述： <br>
 * 
 * @author：feng
 * @since：2011-7-26 上午11:22:04
 * @version:
 * 
 */
public class OperationLogBizImpl implements OperationLogBiz {

	private OperationLogTemplateDao operationLogTemplateDao;

	private OperationLogTemplateConvert operationLogTemplateConvert;

	private static Logger logger = LoggerFactory
			.getLogger(OperationLogBizImpl.class);

	private OperationLogDao operationLogDao;

	private SecurityBiz securityBiz;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void writeLog(OperationLogDTO operationLogDTO) {
		try {
			logger.debug("记录日志开始！");
			Map paramMap = operationLogDTO.getParamMap();
			if (paramMap == null) {
				return;
			}
			OperationLogTemplateDTO template = this
					.queryTemplateByFunctionId(operationLogDTO.getFunctionId());
			paramMap.put("user_id", operationLogDTO.getOperatorId());
			for (HttpParamFilter httpParamFilter : HttpParamFilter.values()) {
				paramMap.remove(httpParamFilter.name());
			}
			operationLogDTO.setOperateArgs(JSONUtils.toJsonString(paramMap));
			String logContent = null;
			if (template == null) {
				logger.info("没有配置日志模板. functionId : "
						+ operationLogDTO.getFunctionId());
				logContent = operationLogDTO.getOperateArgs();
			} else {
				logContent = TemplateUtil.fillTemplate(template.getContent(),
						paramMap);
			}
			operationLogDTO.setOperateContent(logContent);
			// 掉用dubbo服务，saveToDB
//			logger.saveToDB(operationLogDTO);
			SoaLogUtils.save(OperationLogBizImpl.class.getName(),operationLogDTO);
			logger.debug("***记录日志end***");
		} catch (Exception e) {
			logger.error("操作日志记录失败，日志内容：" + operationLogDTO.toString(), e);
		}
	}

	@Deprecated
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void writeLog(Map paramMap, Long userId, String functionUrl) {
		logger.debug("记录日志开始！");
		logger.debug("请求路径:" + functionUrl);

		if (paramMap == null) {
			return;
		}
		// 单例模式查询数据库，设置功能列表
		FunctionDTO function = securityBiz.getFunctionByUri(functionUrl);
		if (function != null && function.getLogNeeded()) {
			logger.debug("符合条件，开始记录日志！");
			OperationLogTemplateDTO template = this
					.queryTemplateByFunctionId(function.getFunctionId());
			if (template == null) {
				logger.debug("未能获取到模板信息。");
				return;
			}
			paramMap.put("user_id", userId);
			// 把模板中的关键字替换，组成操作日志
			String logContent = TemplateUtil.fillTemplate(
					template.getContent(), paramMap);
			OperationLogEntity entity = new OperationLogEntity();
			entity.setLogTime(new Date());
			entity.setLogContent(logContent);
			entity.setKeyWord(template.getKeyWord());
			// 把操作日志入库
			operationLogDao.save(entity);
		}
		logger.debug("***记录日志end***");
	}

	@Override
	public void addTemplate(OperationLogTemplateDTO operationLogTemplate) {
		operationLogTemplateDao.save(operationLogTemplateConvert
				.convert(operationLogTemplate));
	}

	@Override
	public void deleteTemplate(Long templateId) {
		operationLogTemplateDao.delete(templateId);
	}

	@Override
	public void updateTemplate(Long templateId,
			OperationLogTemplateDTO operationLogTemplate) {
		OperationLogTemplateEntity entity = operationLogTemplateConvert
				.convert(operationLogTemplate);
		entity.setId(templateId);
		operationLogTemplateDao.update(entity);
	}

	@Override
	public OperationLogTemplateDTO queryTemplate(Long templateId) {
		OperationLogTemplateEntity entity = operationLogTemplateDao
				.selectById(templateId);
		return operationLogTemplateConvert.convert(entity);
	}

	/**
	 * 描述： 注入操作日志模板dao
	 * 
	 * @param operationLogTemplateDao
	 *            the operationLogTemplateDao to set
	 */
	public void setOperationLogTemplateDao(
			OperationLogTemplateDao operationLogTemplateDao) {
		this.operationLogTemplateDao = operationLogTemplateDao;
	}

	/**
	 * 描述： 注入操作日志模板转换器
	 * 
	 * @param operationLogTemplateConvert
	 *            the operationLogTemplateConvert to set
	 */
	public void setOperationLogTemplateConvert(
			OperationLogTemplateConvert operationLogTemplateConvert) {
		this.operationLogTemplateConvert = operationLogTemplateConvert;
	}

	@Override
	public OperationLogTemplateDTO queryTemplateByFunctionId(Long functionId) {
		//TODO 
		OperationLogTemplateEntity template = (OperationLogTemplateEntity) operationLogTemplateDao.queryByFunctionId(functionId);
		return operationLogTemplateConvert.convert(template);
	}

	/**
	 * @param operationLogDao
	 *            the operationLogDao to set
	 */
	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	/**
	 * @param securityBiz
	 *            the securityBiz to set
	 */
	public void setSecurityBiz(SecurityBiz securityBiz) {
		this.securityBiz = securityBiz;
	}

}
