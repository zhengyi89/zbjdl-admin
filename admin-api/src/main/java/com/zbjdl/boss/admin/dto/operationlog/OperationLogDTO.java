/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.dto.operationlog;

import java.util.Date;
import java.util.Map;

import com.zbjdl.boss.admin.dto.basic.BaseDTO;
import com.zbjdl.common.annotation.LogColumn;
import com.zbjdl.common.annotation.LogTable;

/**
 * 
 * 类名称：OperationLogDTO <br/>
 * 类描述：操作日志信息<br/>
 * 
 * @version:
 */
@LogTable(tableName = "ADMIN_OPERATION_LOG")
public class OperationLogDTO extends BaseDTO {
	private static final long serialVersionUID = -413698099765231201L;

	/**
	 * 主键
	 */
	@LogColumn(name = "ID")
	private Long id;

	/**
	 * 操作员ID
	 */
	@LogColumn(name = "OPERATOR_ID")
	private Long operatorId;

	/**
	 * 操作员登录名
	 */
	@LogColumn(name = "OPERATOR_LOGIN_NAME")
	private String operatorLoginName;

	/**
	 * 操作结束时间
	 */
	@LogColumn(name = "OPERATE_END_TIME")
	private Date operateEndTime;

	/**
	 * 功能ID
	 */
	@LogColumn(name = "FUNCTION_ID")
	private Long functionId;

	/**
	 * 功能名
	 */
	@LogColumn(name = "FUNCTION_NAME")
	private String functionName;

	/**
	 * 功能URL
	 */
	@LogColumn(name = "FUNCTION_URL")
	private String functionUrl;

	/**
	 * 操作参数
	 */
	@LogColumn(name = "OPERATE_ARGS")
	private String operateArgs;

	/**
	 * 日志内容
	 */
	@LogColumn(name = "OPERATE_CONTENT")
	private String operateContent;

	/**
	 * 操作结果
	 */
	@LogColumn(name = "OPERATE_RESULT")
	private String operateResult;

	/**
	 * 操作时长
	 */
	@LogColumn(name = "DURING_TIME")
	private Long duringTime;

	/**
	 * 关键字
	 */
	@LogColumn(name = "KEYWORD")
	private String keyword;

	/**
	 * http参数
	 */
	@SuppressWarnings("rawtypes")
	private Map paramMap;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorLoginName() {
		return operatorLoginName;
	}

	public void setOperatorLoginName(String operatorLoginName) {
		this.operatorLoginName = operatorLoginName;
	}

	public Date getOperateEndTime() {
		return operateEndTime;
	}

	public void setOperateEndTime(Date operateEndTime) {
		this.operateEndTime = operateEndTime;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionUrl() {
		return functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	public String getOperateArgs() {
		return operateArgs;
	}

	public void setOperateArgs(String operateArgs) {
		this.operateArgs = operateArgs;
	}

	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	public String getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}

	public Long getDuringTime() {
		return duringTime;
	}

	public void setDuringTime(Long duringTime) {
		this.duringTime = duringTime;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@SuppressWarnings("rawtypes")
	public Map getParamMap() {
		return paramMap;
	}

	@SuppressWarnings("rawtypes")
	public void setParamMap(Map paramMap) {
		this.paramMap = paramMap;
	}
}
