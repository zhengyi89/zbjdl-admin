/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.zbjdl.boss.admin.dto.basic.BaseDTO;
import com.zbjdl.boss.admin.function.enums.FunctionRiskLevelEnum;
import com.zbjdl.boss.admin.function.enums.FunctionStatusEnum;
import com.zbjdl.boss.admin.function.enums.FunctionTypeEnum;
import com.zbjdl.common.utils.StringUtils;

/**
 * 
 * 类名称：FunctionDTO <br>
 * 类描述：功能DTO<br>
 * 
 * @author feng
 * @since 2011-7-4 下午03:50:53
 * @version 1.0.0
 * 
 */
public class FunctionDTO extends BaseDTO {

	private static final long serialVersionUID = 1349630025032305786L;

	private Long systemId;
	private Long functionId;// 功能ID

	private String functionName;// 功能名称

	private String keyWord;// 关键字

	private String tag;// 标签

	private String functionUrl;// 功能url

	private Long preFunctionId;// 前置功能ID

	private String description;// 功能描述

	private FunctionRiskLevelEnum riskLevel;// 风险级别

	private FunctionTypeEnum functionType;// 功能类型

	private FunctionStatusEnum functionStatus;// 功能状态

	private Timestamp createTime = new Timestamp(System.currentTimeMillis());// 创建时间

	private String checkNeeded;// 是否需要复核

	private Boolean logNeeded;// 是否需要记录日志

	/**
	 * 是否在配置权限页面展示
	 */
	private Boolean display;

	private Long checkFunctionId;// 所复核的功能ID

	private List<Long> deptIds = new ArrayList<Long>();// 所属部门Id

	/**
	 * 描述： 功能ID
	 * 
	 * @param functionId
	 *            the functionId to set
	 */
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	/**
	 * 描述： 功能名称
	 * 
	 * @param functionName
	 *            the functionName to set
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	/**
	 * 描述： 关键字
	 * 
	 * @param keyWord
	 *            the keyWord to set
	 */

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * 描述： 功能url
	 * 
	 * @param functionUrl
	 *            the functionUrl to set
	 */
	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	/**
	 * 描述： 前置功能ID
	 * 
	 * @param preFunctionId
	 *            the preFunctionId to set
	 */
	public void setPreFunctionId(Long preFunctionId) {
		this.preFunctionId = preFunctionId;
	}

	/**
	 * functionId
	 * 
	 * @return the functionId
	 */
	public Long getFunctionId() {
		return functionId;
	}

	/**
	 * functionName
	 * 
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * keyWord
	 * 
	 * @return the keyWord
	 */
	public String getKeyWord() {
		return keyWord;
	}

	/**
	 * functionUrl
	 * 
	 * @return the functionUrl
	 */
	public String getFunctionUrl() {
		return functionUrl;
	}

	/**
	 * preFunctionId
	 * 
	 * @return the preFunctionId
	 */
	public Long getPreFunctionId() {
		return preFunctionId;
	}

	/**
	 * functionStatus
	 * 
	 * @return the functionStatus
	 */

	public FunctionStatusEnum getFunctionStatus() {
		return functionStatus;
	}

	/**
	 * 描述： 设定功能状态
	 * 
	 * @param functionStatus
	 *            the functionStatus to set
	 */
	public void setFunctionStatus(FunctionStatusEnum functionStatus) {
		this.functionStatus = functionStatus;
	}

	/**
	 * description
	 * 
	 * @return the description
	 */

	public String getDescription() {
		return description;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * riskLevel
	 * 
	 * @return the riskLevel
	 */

	public FunctionRiskLevelEnum getRiskLevel() {
		return riskLevel;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param riskLevel
	 *            the riskLevel to set
	 */
	public void setRiskLevel(FunctionRiskLevelEnum riskLevel) {
		this.riskLevel = riskLevel;
	}

	/**
	 * functionType
	 * 
	 * @return the functionType
	 */

	public FunctionTypeEnum getFunctionType() {
		return functionType;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param functionType
	 *            the functionType to set
	 */
	public void setFunctionType(FunctionTypeEnum functionType) {
		this.functionType = functionType;
	}

	/**
	 * createTime
	 * 
	 * @return the createTime
	 */

	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * deptIds
	 * 
	 * @return the deptIds
	 */

	public List<Long> getDeptIds() {
		return deptIds;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param deptIds
	 *            the deptIds to set
	 */
	public void setDeptIds(List<Long> deptIds) {
		this.deptIds = deptIds;
	}

	public String getCheckNeeded() {
		return checkNeeded;
	}

	public void setCheckNeeded(String checkNeeded) {
		this.checkNeeded = checkNeeded;
	}

	public Boolean getLogNeeded() {
		return logNeeded;
	}

	public void setLogNeeded(Boolean logNeeded) {
		this.logNeeded = logNeeded;
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

	/**
	 * display
	 * 
	 * @return the display
	 */

	public Boolean getDisplay() {
		return display;
	}

	/**
	 * @param display
	 *            the display to set
	 */
	public void setDisplay(Boolean display) {
		this.display = display;
	}

	// -------------------HELP METHOD-------------------//
	/**
	 * 取得审核级别
	 * 
	 * @return
	 */
	public int getAuditLevel() {
		int auditLevel = 0;
		// 判断功能是否需要复合
		// 是否需要审核对象0和null不需要
		if (StringUtils.isNotBlank(this.checkNeeded)
				&& this.checkNeeded.matches("[0-9]+")) {
			auditLevel = Integer.parseInt(this.checkNeeded);
		}
		return auditLevel;
	}
}
