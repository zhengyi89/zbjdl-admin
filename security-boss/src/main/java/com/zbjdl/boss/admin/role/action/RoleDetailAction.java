/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.role.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.zbjdl.common.utils.easyquery.action.QueryForm;
import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.role.dto.RoleDTO;
import com.zbjdl.utils.query.QueryService;

/**
 * 
 * 类名称：RoleDetailAction <br>
 * 类描述： <br>
 * 
 * @author：feng
 * @since：2011-7-30 下午02:45:47
 * @version:
 * 
 */
public class RoleDetailAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = -8970800294088096047L;

	private Long roleId;

	private RoleDTO roleDTO;

	private QueryForm queryForm;

	@Autowired
	private QueryService queryService;

	private int pageSize = 15;

	@Override
	public String execute() throws Exception {
		roleDTO = remoteService.getSecurityConfigFacade().getRoleInfo(
				roleId);
		return SUCCESS;
	}

	/**
	 * roleId
	 * 
	 * @return the roleId
	 */

	public Long getRoleId() {
		return roleId;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * roleDTO
	 * 
	 * @return the roleDTO
	 */

	public RoleDTO getRoleDTO() {
		return roleDTO;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param roleDTO
	 *            the roleDTO to set
	 */
	public void setRoleDTO(RoleDTO roleDTO) {
		this.roleDTO = roleDTO;
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

}
