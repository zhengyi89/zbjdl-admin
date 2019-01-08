/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.user.action;

import com.google.common.collect.Lists;
import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.role.dto.RoleDTO;
import com.zbjdl.boss.admin.role.enums.RoleStatusEnum;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.utils.QueryHelper;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;
import com.zbjdl.boss.admin.ztree.utils.ZtreeUtils;
import com.zbjdl.boss.admin.ztree.vo.ZtreeVO;
import com.zbjdl.common.json.JSONUtils;
import com.zbjdl.utils.query.QueryResult;
import com.zbjdl.utils.query.QueryService;
import com.zbjdl.common.utils.easyquery.action.QueryForm;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.*;

/**
 * 类名称：AuthorityAction <br>
 * 类描述： 权限分配 <br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-7-29 下午04:57:39
 */
public class AuthorityAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 3910409746647002862L;

	private QueryForm queryForm;

	@Autowired
	private QueryService queryService;

	private int pageSize = 15;

	private Long userId;

	private Long[] roleIds;// 所赋予的权限

	private String password;

	@Autowired
	private QueryHelper queryHelper;

	private List<Map<String, Object>> roleJsonList;

	private List<ZtreeVO> ztreeVOList;

	private String selectNodes;

	private Long roleId;

	private List<Long> userIds;

	private List<Long> checkBoxDisable;

	@Override
	public String execute() throws Exception {
		UserDTO userDTO = UserInfoUtils.getUserFromSession(null);
		/**
		 * 部门所拥有的角色
		 */
		List<RoleDTO> hasRoleList = remoteService.getSecurityConfigFacade()
				.queryRolesByUser(userId);
		List<FunctionDTO> functionUnitListQuery_Single = new ArrayList<FunctionDTO>();
		String hasRole = "";
		for (RoleDTO role : hasRoleList) {
			hasRole += role.getRoleId() + ",";
			List<FunctionDTO> list = remoteService
					.getSecurityConfigFacade().queryFunctionByRole(
							role.getRoleId());
			functionUnitListQuery_Single.addAll(list);
		}
		/**
		 * 列出全部的功能单元，要剔除 隐藏角色对应的功能单元 这里还没做
		 */
		List<FunctionDTO> functionUnitList = remoteService
				.getSecurityConfigFacade().queryDisplayFunction();
		Map<Long, Long[]>[] dependenceAndRefMap = remoteService
				.getSecurityConfigFacade().getDependenceRelation();
		roleJsonList = ZtreeUtils.buildJson(functionUnitList,
				functionUnitListQuery_Single, true, dependenceAndRefMap);
		// 查出部门下面的所有角色，还没做修改
		List<RoleDTO> roleDTOList = remoteService.getSecurityConfigFacade()
				.queryRolesByDeparment(userDTO.getPrimaryDepartmentId());
		queryForm = QueryForm.buildForm(ServletActionContext.getRequest(),
				"queryOperatorRole");
		queryForm.getQueryParams().put("departmentId",
				userDTO.getPrimaryDepartmentId());
		QueryResult result = queryService.query(queryForm.getStratIndex(),
				pageSize, "queryOperatorRole", queryForm.getQueryParams(), "",
				queryForm.getAsc(), false);
		queryForm.setQueryResult(result);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("hasRole", hasRole);
		request.setAttribute("roleDTOList", roleDTOList);
		request.setAttribute("userId", userId);
		// request.setAttribute("userName", userDTO.getUserName());
		request.setAttribute("jsonFunctionUnit",
				JSONUtils.toJsonString(roleJsonList));
		return SUCCESS;
	}

	// 分配权限
	public String distributeAuthority() {
		try {
			HttpSession session = getMVCFacade().getHttpSession();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(), password);
			if (!passwordRightOrNot) {
				this.errMsg = "密码错误!";
			} else {
				if (null == roleId) {
					// 单一授限
					Long[] node = null;
					if (selectNodes != null && !"".equals(selectNodes)) {
						node = ZtreeUtils.getLongArrayByStringNum(selectNodes);
					}
					remoteService.getSecurityConfigFacade().configUserRoles(
							user.getPrimaryDepartmentId(), userId, node);
				} else {
					// 批量授限
					remoteService.getSecurityConfigFacade().configRoleUsers(
							user.getPrimaryDepartmentId(), userIds, roleId
					);
				}
			}
		} catch (Exception e) {
			handleException(e);
		}
		return SUCCESS;
	}

	public String ajaxQurryRole() {

		HttpServletRequest request = ServletActionContext.getRequest();

		String roleIdVar = request.getParameter("roleId");

		roleId = Long.parseLong(roleIdVar);

		List<FunctionDTO> functionList = remoteService
				.getSecurityConfigFacade().queryFunctionByRole(roleId);

		Map<Long, Long[]>[] dependenceAndRefMap = null;

		roleJsonList = ZtreeUtils.buildJson(functionList, null, false,
				dependenceAndRefMap);

		request.setAttribute("jsonFunction",
				JSONUtils.toJsonString(roleJsonList));

		// request.setAttribute("roleId",roleId);
		// if(functionList == null || functionList.isEmpty()){
		// request.setAttribute("allNodes",0);
		// request.setAttribute("parentsNodes",0);
		// return SUCCESS;
		// }
		// int i = 0;
		// for(FunctionDTO dto :functionList){
		// if(dto == null || dto.getPreFunctionId() == null){
		// continue;
		// }
		// else if(dto.getPreFunctionId() == 0){
		// i++;
		// }
		// }
		//
		// request.setAttribute("allNodes",functionList.size());
		// request.setAttribute("parentsNodes",i);

		return SUCCESS;
	}

	public String quickChoiseRoleAjax() {

		UserDTO userDTO = UserInfoUtils.getUserFromSession(null);

		HttpServletRequest request = ServletActionContext.getRequest();

		String roleMessage = request.getParameter("selectNodes");

		String[] roles = roleMessage.split(",");

		String userId = request.getParameter("userId");

		List<FunctionDTO> functionUnitList = Lists.newArrayList();

		if (!"".equals(userId)) {
			functionUnitList = remoteService.getSecurityConfigFacade()
					.queryDisplayFunction();
		}

		Set<FunctionDTO> functionListQuery = new HashSet<FunctionDTO>();

		for (String roleId : roles) {
			if (roleId == null || "".equals(roleId)) {
				break;
			}
			List<FunctionDTO> functionListQuery_Single = remoteService
					.getSecurityConfigFacade().queryFunctionByRole(
							Long.parseLong(roleId));
			if (functionListQuery_Single != null
					&& !functionListQuery_Single.isEmpty()) {
				functionListQuery.addAll(functionListQuery_Single);
			}
		}

		List<FunctionDTO> list = Lists.newArrayList();
		if (!functionListQuery.isEmpty()) {
			list.addAll(functionListQuery);
		}

		roleJsonList = ZtreeUtils.buildJson(functionUnitList, list, true, null);

		ztreeVOList = ZtreeUtils.jsonMapToZtreeVOArray(roleJsonList);

		// 查出部门下面的所有角色，还没做修改
		List<RoleDTO> roleDTOList = remoteService.getSecurityConfigFacade()
				.queryRolesByDeparment(userDTO.getPrimaryDepartmentId());

		checkBoxDisable = new ArrayList<Long>();
		for (RoleDTO roleDTO : roleDTOList) {
			if (roleDTO.getRoleStatus() != null
					&& roleDTO.getRoleStatus().equals(RoleStatusEnum.FROZEN)) {
				checkBoxDisable.add(roleDTO.getRoleId());
			}
		}

		// request.setAttribute("roleJsonList", ztreeVOList);

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
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * roleIds
	 *
	 * @return the roleIds
	 */

	public Long[] getRoleIds() {
		return roleIds;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param roleIds the roleIds to set
	 */
	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * queryHelper
	 *
	 * @return the queryHelper
	 */

	public QueryHelper getQueryHelper() {
		return queryHelper;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param queryHelper the queryHelper to set
	 */
	public void setQueryHelper(QueryHelper queryHelper) {
		this.queryHelper = queryHelper;
	}

	public List<Map<String, Object>> getRoleJsonList() {
		return roleJsonList;
	}

	public void setRoleJsonList(List<Map<String, Object>> roleJsonList) {
		this.roleJsonList = roleJsonList;
	}

	public String getSelectNodes() {
		return selectNodes;
	}

	public void setSelectNodes(String selectNodes) {
		this.selectNodes = selectNodes;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

	public List<ZtreeVO> getZtreeVOList() {
		return ztreeVOList;
	}

	public void setZtreeVOList(List<ZtreeVO> ztreeVOList) {
		this.ztreeVOList = ztreeVOList;
	}

	public List<Long> getCheckBoxDisable() {
		return checkBoxDisable;
	}

	public void setCheckBoxDisable(List<Long> checkBoxDisable) {
		this.checkBoxDisable = checkBoxDisable;
	}

}
