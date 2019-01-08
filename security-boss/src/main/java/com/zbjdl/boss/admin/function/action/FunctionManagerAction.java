/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.google.common.collect.Lists;
import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.enums.UserTypeEnum;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;

/**
 * 
 * 类名称：FunctionManagerAction <br>
 * 类描述： 功能管理 <br>
 * 
 * @author feng
 * @since 2011-7-25 下午02:24:22
 * @version 1.0.0
 * 
 */
public class FunctionManagerAction extends EmployeeBossBaseAction {
	private static final long serialVersionUID = -67194711451505365L;

	private String functionName;

	private Long departmentId;

	private String functionType;

	private String functionStatus;

	private String functionUrl;

	private String riskLevel;

	private String departmentCode;

	private String functionTag;

	private List<DepartmentDTO> list = Lists.newArrayList();

	@Override
	public String execute() throws Exception {
		List<DepartmentDTO> departlist = remoteService.getUserFacade()
				.queryAllDepartments();
		DepartmentDTO allDepart = new DepartmentDTO();
		allDepart.setDepartmentName("全部");
		allDepart.setDepartmentCode("");
		list.add(allDepart);
		list.addAll(departlist);
		HttpSession session = getMVCFacade().getHttpSession();
		UserDTO user = UserInfoUtils.getUserFromSession(session);
		Boolean isDepartmentAdmin = remoteService.getSecurityFacade()
		.checkUserType(user.getUserId(), UserTypeEnum.DEPARTMENT_MANAGER);
		if(isDepartmentAdmin){
			departmentId = user.getPrimaryDepartmentId();
		}
		return SUCCESS;
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
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param functionName
	 *            the functionName to set
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	/**
	 * functionType
	 * 
	 * @return the functionType
	 */

	public String getFunctionType() {
		return functionType;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param functionType
	 *            the functionType to set
	 */
	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

	/**
	 * functionStatus
	 * 
	 * @return the functionStatus
	 */

	public String getFunctionStatus() {
		return functionStatus;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param functionStatus
	 *            the functionStatus to set
	 */
	public void setFunctionStatus(String functionStatus) {
		this.functionStatus = functionStatus;
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
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param functionUrl
	 *            the functionUrl to set
	 */
	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	/**
	 * riskLevel
	 * 
	 * @return the riskLevel
	 */

	public String getRiskLevel() {
		return riskLevel;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param riskLevel
	 *            the riskLevel to set
	 */
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	/**
	 * list
	 * 
	 * @return the list
	 */

	public List<DepartmentDTO> getList() {
		return list;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param list
	 *            the list to set
	 */
	public void setList(List<DepartmentDTO> list) {
		this.list = list;
	}

	/**
	 * departmentCode
	 * 
	 * @return the departmentCode
	 */

	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param departmentCode
	 *            the departmentCode to set
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getFunctionTag() {
		return functionTag;
	}

	public void setFunctionTag(String functionTag) {
		this.functionTag = functionTag;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	
}
