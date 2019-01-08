/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.entity;

import java.sql.Timestamp;

import com.zbjdl.boss.admin.function.enums.FunctionRiskLevelEnum;
import com.zbjdl.boss.admin.function.enums.FunctionStatusEnum;
import com.zbjdl.boss.admin.function.enums.FunctionTypeEnum;
import com.zbjdl.common.persistence.Entity;

/**
 * 类名称：FunctionEntity <br>
 * 类描述：功能实体<br>
 * 
 * @author feng
 * @since 2011-7-1 上午11:02:36
 * @version 1.0.0
 */
public class FunctionEntity implements Entity<Long> {

	private static final long serialVersionUID = -845675134174747085L;

	private Long functionId;// 功能ID

	private String functionName;// 功能名称

	private String keyWord;//关键字

	private String tag;//标签

	private String functionUrl;// 功能url

	private Long preFunctionId;// 前置功能ID

	private String description;// 功能描述

	private FunctionRiskLevelEnum riskLevel;// 风险级别

	private FunctionTypeEnum functionType;// 功能类型

	private FunctionStatusEnum functionStatus;// 功能状态

	private Timestamp createTime;// 创建时间
	
	private String checkNeeded;//是否需要复核
	
	private String logNeeded;//是否需要记录日志
	
	/** 在权限配置中是否展示 */
	private String display;
	
	private Long checkFunctionId;//所复核的功能ID
	
	private Long systemId;

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

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getFunctionUrl() {
		return functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	public Long getPreFunctionId() {
		return preFunctionId;
	}

	public void setPreFunctionId(Long preFunctionId) {
		this.preFunctionId = preFunctionId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FunctionRiskLevelEnum getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(FunctionRiskLevelEnum riskLevel) {
		this.riskLevel = riskLevel;
	}

	public FunctionTypeEnum getFunctionType() {
		return functionType;
	}

	public void setFunctionType(FunctionTypeEnum functionType) {
		this.functionType = functionType;
	}

	public FunctionStatusEnum getFunctionStatus() {
		return functionStatus;
	}

	public void setFunctionStatus(FunctionStatusEnum functionStatus) {
		this.functionStatus = functionStatus;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCheckNeeded() {
		return checkNeeded;
	}

	public void setCheckNeeded(String checkNeeded) {
		this.checkNeeded = checkNeeded;
	}

	public String getLogNeeded() {
		return logNeeded;
	}

	public void setLogNeeded(String logNeeded) {
		this.logNeeded = logNeeded;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public Long getCheckFunctionId() {
		return checkFunctionId;
	}

	public void setCheckFunctionId(Long checkFunctionId) {
		this.checkFunctionId = checkFunctionId;
	}

	public Long getSystemId() {
		return systemId;
	}

	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}

	@Override
	public Long getId() {
		return functionId;
	}

	@Override
	public void setId(Long arg0) {
		this.functionId = arg0;
	}




}
