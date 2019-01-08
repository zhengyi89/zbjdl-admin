/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.department.action;

import com.google.common.collect.Lists;
import com.zbjdl.common.utils.easyquery.action.QueryForm;
import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.utils.query.QueryService;

import java.util.List;

/**
 * 类名称：QueryDepartMentAction <br>
 * 类描述： 部门查询action <br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-7-20 上午11:41:57
 */
public class DepartMentManagerAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 5058729041109180780L;
	private QueryForm queryForm;
	private QueryService queryService;
	private int pageSize;
	private String departmentCode;
	private String departmentName;
	private String departmentDesc;
	private String departmentStatus;

	private List<DepartmentDTO> departList = Lists.newArrayList();// 所有部门列表

	public String execute() {
		departList = remoteService.getUserFacade().queryAllDepartments();
		return SUCCESS;
	}

	@Deprecated
	public String queryDepartMent() {
		return SUCCESS;
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
	 * @param queryForm the queryForm to set
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
	 * @param queryService the queryService to set
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
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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
	 * @param departmentCode the departmentCode to set
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * departmentName
	 *
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * departmentDesc
	 *
	 * @return the departmentDesc
	 */
	public String getDepartmentDesc() {
		return departmentDesc;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param departmentDesc the departmentDesc to set
	 */
	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

	/**
	 * departmentStatus
	 *
	 * @return the departmentStatus
	 */
	public String getDepartmentStatus() {
		return departmentStatus;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param departmentStatus the departmentStatus to set
	 */
	public void setDepartmentStatus(String departmentStatus) {
		this.departmentStatus = departmentStatus;
	}

	public List<DepartmentDTO> getDepartList() {
		return departList;
	}

	public void setDepartList(List<DepartmentDTO> departList) {
		this.departList = departList;
	}

}
