/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.action;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.enums.UserTypeEnum;
import com.zbjdl.common.exception.BaseException;
import com.zbjdl.utils.query.QueryResult;
import com.zbjdl.utils.query.QueryService;
import com.zbjdl.common.utils.easyquery.action.QueryForm;
/**
 * 
 * 类名称：OperatorInfoDetailAction <br>
 * 类描述： 操作员信息详情 <br>
 * 
 * @author：feng
 * @since：2011-7-27 下午07:20:11
 * @version:
 * 
 */
public class OperatorInfoDetailAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 4412030120745972607L;

	private Long userId;

	private UserDTO userDTO;

	private DepartmentDTO departmentDTO;

	private QueryForm queryForm;

	@Autowired
	private QueryService queryService;

	private int pageSize = 15;
	/** 人员类型 */
	private String userType;

	@Override
	public String execute() throws Exception {
		userDTO = remoteService.getUserFacade().queryUserById(userId);
		if (userDTO == null) {
			throw new BaseException("用户不存在，用户ID：" + userId);
		}
		departmentDTO = remoteService.getUserFacade().queryDepartmentById(
				userDTO.getPrimaryDepartmentId());
		userType = remoteService.getSecurityFacade().checkUserType(userId,
				UserTypeEnum.DEPARTMENT_MANAGER) ? "1" : "0";
		return SUCCESS;
	}

	// 查询某个操作员所具有的角色
	public String queryOperatorRoles() {

		queryForm = QueryForm.buildForm(ServletActionContext.getRequest(),
				"queryOperatorRoles");
		queryForm.getQueryParams().put("userId", userId);
		QueryResult result = queryService.query(queryForm.getStratIndex(),
				pageSize, "queryOperatorRoles", queryForm.getQueryParams(), "",
				queryForm.getAsc(), false);
		queryForm.setQueryResult(result);

		return SUCCESS;
	}

	/**
	 * userId
	 * 
	 * @return the userId
	 */

	public Long getUserId() {
		return userId;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * userDTO
	 * 
	 * @return the userDTO
	 */

	public UserDTO getUserDTO() {
		return userDTO;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param userDTO
	 *            the userDTO to set
	 */
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	/**
	 * departmentDTO
	 * 
	 * @return the departmentDTO
	 */

	public DepartmentDTO getDepartmentDTO() {
		return departmentDTO;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param departmentDTO
	 *            the departmentDTO to set
	 */
	public void setDepartmentDTO(DepartmentDTO departmentDTO) {
		this.departmentDTO = departmentDTO;
	}

	/**
	 * queryForm
	 * 
	 * @return the queryForm
	 */

	public QueryForm getQueryForm() {
		return queryForm;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param queryForm
	 *            the queryForm to set
	 */
	public void setQueryForm(QueryForm queryForm) {
		this.queryForm = queryForm;
	}

	/**
	 * queryService
	 * 
	 * @return the queryService
	 */

	public QueryService getQueryService() {
		return queryService;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param queryService
	 *            the queryService to set
	 */
	public void setQueryService(QueryService queryService) {
		this.queryService = queryService;
	}

	/**
	 * pageSize
	 * 
	 * @return the pageSize
	 */

	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * userType
	 * 
	 * @return the userType
	 */

	public String getUserType() {
		return userType;
	}

}
