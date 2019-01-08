/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.function.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.function.enums.FunctionRiskLevelEnum;
import com.zbjdl.boss.admin.function.enums.FunctionStatusEnum;
import com.zbjdl.boss.admin.function.enums.FunctionTypeEnum;
import com.zbjdl.boss.admin.function.vo.FunctionVO;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;
import com.zbjdl.common.json.JSONUtils;
import com.zbjdl.common.utils.DateUtils;

/**
 * 
 * 类名称：DeleteFunctionAction <br>
 * 类描述： <br>
 * 
 * @author：feng
 * @since：2011-7-22 下午04:38:32
 * @version:
 * 
 */
public class UpdateFunctionAction extends EmployeeBossBaseAction implements
		ModelDriven<FunctionVO> {

	private static final long serialVersionUID = 1L;

	private static final Long PREFUNCTIONID = -99910000l;

	/**
	 * 所有部门名称
	 */
	private List<String> allDeptNames = new ArrayList<String>();
	/**
	 * 所选名称
	 */
	private List<String> selectedNames = new ArrayList<String>();
	/**
	 * 部门ID列表
	 */
	private List<Long> departmentIds;
	/**
	 * 角色ID列表
	 */
	private List<Long> roleIds;
	/**
	 * 所有部门
	 */
	private List<DepartmentDTO> depts;
	/**
	 * 功能模型
	 */
	private FunctionVO functionVO;
	/**
	 * 角色选择
	 */
	private Long[] roleBox;
	/**
	 * 部门选择
	 */
	private Long[] deptBox;
	/**
	 *
	 */
	private String from;
	/**
	 * 方式
	 */
	private String type;

	private String menuAutoCompleteJson;

	private Map<Long, String> preFunctionMapList;

	// 跳转到更新功能页面
	public String initUpdate() {
		FunctionDTO functionDTO = remoteService.getSecurityConfigFacade()
				.getFunctionInfo(functionVO.getFunctionId());
		functionVO.setFunctionName(functionDTO.getFunctionName());
		functionVO.setDescription(functionDTO.getDescription());
		functionVO.setRiskLevel(functionDTO.getRiskLevel().name());
		functionVO.setCheckNeeded(StringUtils.isBlank(functionDTO
				.getCheckNeeded()) ? "0" : functionDTO.getCheckNeeded());
		functionVO.setFunctionUrl(functionDTO.getFunctionUrl());
		if (functionDTO.getFunctionType() != null) {
			functionVO
					.setFunctionType(functionDTO.getFunctionType().toString());
		}
		functionVO.setPreFunctionId(functionDTO.getPreFunctionId());
		functionVO.setCheckFunctionId(functionDTO.getCheckFunctionId());
		functionVO.setLogNeeded(functionDTO.getLogNeeded());
		functionVO.setKeyWord(functionDTO.getKeyWord());
		functionVO.setTag(functionDTO.getTag());
		functionVO.setDisplay(functionDTO.getDisplay());

		DepartmentDTO query = new DepartmentDTO();
		depts = remoteService.getUserFacade().queryDepartments(query);
		for (DepartmentDTO dept : depts) {
			allDeptNames.add(dept.getDepartmentName());
		}

		List<DepartmentDTO> selectedDepts = remoteService
				.getSecurityConfigFacade().queryDepartmentsByFunctionId(
						functionVO.getFunctionId());
		for (DepartmentDTO dept : selectedDepts) {
			selectedNames.add(dept.getDepartmentName());
		}
		// 查询此功能在部门功能关系表中的所有部门ID
		departmentIds = remoteService.getSecurityConfigFacade()
				.queryDepartmentAndFunctionRelation(functionVO.getFunctionId());
		// 查询此功能在角色功能关系表中所有关系，将和隐藏角色关联的角色ID放入roleIds
		List<Long> roleIdList = remoteService.getSecurityConfigFacade()
				.queryByFunctionId(functionVO.getFunctionId());
		roleIds = new ArrayList<Long>();
		for (Long id : roleIdList) {
			if (id == -1001 || id == -1000 || id == -999) {
				roleIds.add(id);
			}
		}

		type = "update";

		List<FunctionDTO> functionListToMap = remoteService
				.getSecurityConfigFacade().queryAllFunction();
		List<Map<String, String>> mapList = functionListToMap(functionListToMap);
		menuAutoCompleteJson = JSONUtils.toJsonString(mapList);

		HttpSession session = getMVCFacade().getHttpSession();
		UserDTO user = UserInfoUtils.getUserFromSession(session);
		HttpServletRequest request = ServletActionContext.getRequest();
		boolean isDepartmentAdmin = user.getIsAdmin() != null
				&& user.getIsAdmin();
		boolean isSysAdmin = user.getIsSuperAdmin() != null
				&& user.getIsSuperAdmin();
		List<FunctionDTO> functionList = new ArrayList<FunctionDTO>();
		if (isDepartmentAdmin) {
			functionList = remoteService.getSecurityConfigFacade()
					.queryFunctionByDepartmentAndFunctionId(
							user.getPrimaryDepartmentId(), PREFUNCTIONID);
		} else if (isSysAdmin) {
			functionList = remoteService
					.getSecurityConfigFacade()
					.queryFunctionByDepartmentAndFunctionId(null, PREFUNCTIONID);
		}
		preFunctionMapList = getPreFunctionMap(functionList);
		request.setAttribute("preFunctionMapList", preFunctionMapList);

		return SUCCESS;
	}

	// 更新功能
	public String updateFunction() {
		try {
			logger.debug(functionVO.toString());
			checkFunctionAndDept();
			checkUpdateFunction();
			HttpSession session = getMVCFacade().getHttpSession();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			boolean isSysAdmin = user.getIsSuperAdmin() != null
					&& user.getIsSuperAdmin();
			// 是系统管理员
			if (isSysAdmin) {
				// 删除部门功能关系
				depts = remoteService.getUserFacade().queryDepartments(
						new DepartmentDTO());
				for (DepartmentDTO dept : depts) {
					remoteService.getSecurityConfigFacade()
							.deleteDepartmentAndFunctionRelation(
									dept.getDepartmentId(),
									functionVO.getFunctionId());
				}
				// 删除系统管理员、部门管理员、操作员隐藏角色和功能关系
				remoteService.getSecurityConfigFacade()
						.deleteRoleAndFunctionRelation(-1001L,
								functionVO.getFunctionId());
				remoteService.getSecurityConfigFacade()
						.deleteRoleAndFunctionRelation(-1000L,
								functionVO.getFunctionId());
				remoteService.getSecurityConfigFacade()
						.deleteRoleAndFunctionRelation(-999L,
								functionVO.getFunctionId());
			}

			FunctionDTO functionDTO = remoteService
					.getSecurityConfigFacade().getFunctionInfo(
							functionVO.getFunctionId());
			functionDTO.setFunctionName(functionVO.getFunctionName());
			functionDTO.setDescription(functionVO.getDescription());
			String riskLevel = functionVO.getRiskLevel();
			if (FunctionRiskLevelEnum.LOW.name().equals(riskLevel)) {
				functionDTO.setRiskLevel(FunctionRiskLevelEnum.LOW);
			} else if (FunctionRiskLevelEnum.MIDDLE.name().equals(riskLevel)) {
				functionDTO.setRiskLevel(FunctionRiskLevelEnum.MIDDLE);
			} else if (FunctionRiskLevelEnum.HIGH.name().equals(riskLevel)) {
				functionDTO.setRiskLevel(FunctionRiskLevelEnum.HIGH);
			}
			functionDTO.setCheckNeeded(functionVO.getCheckNeeded());
			if (StringUtils.isNotBlank(functionVO.getFunctionUrl())
					&& !StringUtils.endsWithIgnoreCase(
							functionVO.getFunctionUrl(), ".action")
					&& !StringUtils.contains(functionVO.getFunctionUrl(), "?")) {
				functionDTO.setFunctionUrl(functionVO.getFunctionUrl()
						+ ".action");
			} else {
				functionDTO.setFunctionUrl(functionVO.getFunctionUrl());
			}
			functionDTO.setPreFunctionId(functionVO.getPreFunctionId());
			functionDTO.setCheckFunctionId(functionVO.getCheckFunctionId());
			functionDTO.setLogNeeded(functionVO.getLogNeeded());
			functionDTO.setDisplay(functionVO.getDisplay());
			functionDTO.setKeyWord(functionVO.getKeyWord());
			functionDTO.setTag(functionVO.getTag());
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							functionVO.getPassword());
			if (!passwordRightOrNot) {
				functionVO.setErrMsg("密码错误!");
				return SUCCESS;
			} else {
				remoteService.getSecurityConfigFacade().updateFunction(
						functionDTO);

				if (isSysAdmin) { // 添加角色功能关系
					if (roleBox != null && roleBox.length > 0) {
						remoteService.getSecurityConfigFacade()
								.assignFunctionToRole(
										functionVO.getFunctionId(),
										Arrays.asList(roleBox));
					}
					// 添加部门功能关系
					if (deptBox != null && deptBox.length > 0) {
						remoteService.getSecurityConfigFacade()
								.assignFunctionToDept(
										functionVO.getFunctionId(),
										Arrays.asList(deptBox));
					}
				}
				// 添加功能
				remoteService.getSecurityConfigFacade().addAuditFunction(
						functionDTO, user);
			}
		} catch (Exception e) {
			handleException(e, functionVO);
		}
		return SUCCESS;
	}

	// 跳转到添加功能页面
	public String initAdd() {
		type = "add";
		DepartmentDTO query = new DepartmentDTO();
		depts = remoteService.getUserFacade().queryDepartments(query);
		HttpSession session = getMVCFacade().getHttpSession();
		UserDTO user = UserInfoUtils.getUserFromSession(session);
		HttpServletRequest request = ServletActionContext.getRequest();
		boolean isDepartmentAdmin = user.getIsAdmin() != null
				&& user.getIsAdmin();
		boolean isSysAdmin = user.getIsSuperAdmin() != null
				&& user.getIsSuperAdmin();
		List<FunctionDTO> functionList = new ArrayList<FunctionDTO>();
		if (isDepartmentAdmin) {
			functionList = remoteService.getSecurityConfigFacade()
					.queryFunctionByDepartmentAndFunctionId(
							user.getPrimaryDepartmentId(), PREFUNCTIONID);
		} else if (isSysAdmin) {
			functionList = remoteService
					.getSecurityConfigFacade()
					.queryFunctionByDepartmentAndFunctionId(null, PREFUNCTIONID);
		}
		preFunctionMapList = getPreFunctionMap(functionList);
		request.setAttribute("preFunctionMapList", preFunctionMapList);
		return SUCCESS;
	}

	// 添加功能
	public String add() {
		try {
			checkFunctionAndDept();
			checkAddFunction();
			FunctionDTO functionDTO = new FunctionDTO();
			functionDTO.setFunctionType(FunctionTypeEnum.DEPARTMENT_PRIVATE);
			functionDTO.setFunctionId(functionVO.getFunctionId());
			functionDTO.setFunctionName(functionVO.getFunctionName());
			functionDTO.setDescription(functionVO.getDescription());
			String riskLevel = functionVO.getRiskLevel();
			if (FunctionRiskLevelEnum.LOW.name().equals(riskLevel)) {
				functionDTO.setRiskLevel(FunctionRiskLevelEnum.LOW);
			} else if (FunctionRiskLevelEnum.MIDDLE.name().equals(riskLevel)) {
				functionDTO.setRiskLevel(FunctionRiskLevelEnum.MIDDLE);
			} else if (FunctionRiskLevelEnum.HIGH.name().equals(riskLevel)) {
				functionDTO.setRiskLevel(FunctionRiskLevelEnum.HIGH);
			}
			functionDTO.setCheckNeeded(functionVO.getCheckNeeded());
			if (StringUtils.isNotBlank(functionVO.getFunctionUrl())
					&& !StringUtils.endsWithIgnoreCase(
							functionVO.getFunctionUrl(), ".action")
					&& !StringUtils.contains(functionVO.getFunctionUrl(), "?")) {
				functionDTO.setFunctionUrl(functionVO.getFunctionUrl()
						+ ".action");
			} else {
				functionDTO.setFunctionUrl(functionVO.getFunctionUrl());
			}
			functionDTO.setPreFunctionId(functionVO.getPreFunctionId());
			functionDTO.setCheckFunctionId(functionVO.getCheckFunctionId());
			functionDTO.setLogNeeded(functionVO.getLogNeeded());
			functionDTO.setDisplay(functionVO.getDisplay());
			functionDTO.setFunctionStatus(FunctionStatusEnum.ACTIVE);
			functionDTO.setCreateTime(DateUtils.getCurTimestamp());
			functionDTO.setKeyWord(functionVO.getKeyWord());

			HttpSession session = getMVCFacade().getHttpSession();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							functionVO.getPassword());
			if (!passwordRightOrNot) {
				functionVO.setErrMsg("密码错误!");
				return SUCCESS;
			} else {
				remoteService.getSecurityConfigFacade().addFunction(
						functionDTO);

				boolean isSysAdmin = user.getIsSuperAdmin() != null
						&& user.getIsSuperAdmin();
				if (isSysAdmin) {
					// 添加角色功能关系
					if (roleBox != null && roleBox.length > 0) {
						remoteService.getSecurityConfigFacade()
								.assignFunctionToRole(
										functionVO.getFunctionId(),
										Arrays.asList(roleBox));
					}
					// 添加部门功能关系
					if (deptBox != null && deptBox.length > 0) {
						remoteService.getSecurityConfigFacade()
								.assignFunctionToDept(
										functionVO.getFunctionId(),
										Arrays.asList(deptBox));
					}
				}

				boolean isDepartmentAdmin = user.getIsAdmin() != null
						&& user.getIsAdmin();
				if (isDepartmentAdmin) {
					deptBox = new Long[1];
					deptBox[0] = user.getPrimaryDepartmentId();
					remoteService.getSecurityConfigFacade()
							.assignFunctionToDept(functionVO.getFunctionId(),
									Arrays.asList(deptBox));
				}
				// 添加双审核功能
				remoteService.getSecurityConfigFacade().addAuditFunction(
						functionDTO, user);
			}
		} catch (Exception e) {
			handleException(e, functionVO);
		}
		return SUCCESS;
	}

	private String getFunctionUrl(String url) {
		if (StringUtils.endsWith(url, "/")) {
			throw new IllegalArgumentException("URL不能以'/'结尾!");
		}
		// 内部URL
		Pattern pattern = Pattern.compile("/[a-zA-Z]+-boss/([^/]*/)?[^?]+");
		Matcher matcher = pattern.matcher(url);
		if (matcher.find()) {
			return matcher.group(0);
		}
		// 外部URL
		return url;
	}

	// 删除功能
	public String delete() {
		try {
			Long functionId = functionVO.getFunctionId();
			HttpSession session = getMVCFacade().getHttpSession();
			HttpServletRequest request = ServletActionContext.getRequest();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							request.getParameter("password"));
			if (!passwordRightOrNot) {
				functionVO.setErrMsg("密码错误!");
				return SUCCESS;
			} else {
				remoteService.getSecurityConfigFacade().deleteFunction(
						functionId);
				remoteService.getSecurityConfigFacade()
						.deleteDepartmentAndFunctionRelationByFunctionId(functionId);
				remoteService.getSecurityConfigFacade()
						.deleteRoleAndFunctionRelationByFunctionId(functionId);
			}

			if ("detail".equals(from)) {
				return "detail";
			}
		} catch (Exception e) {
			handleException(e, functionVO);
		}
		return SUCCESS;
	}

	// 冻结功能
	public String freeze() {
		try {
			logger.debug(from);
			Long functionId = functionVO.getFunctionId();

			HttpSession session = getMVCFacade().getHttpSession();
			HttpServletRequest request = ServletActionContext.getRequest();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							request.getParameter("password"));
			if (!passwordRightOrNot) {
				functionVO.setErrMsg("密码错误!");
				return SUCCESS;
			} else {
				remoteService.getSecurityConfigFacade().frozeFunction(
						functionId);
			}

			if ("detail".equals(from)) {
				return "detail";
			}
		} catch (Exception e) {
			handleException(e, functionVO);
		}
		return SUCCESS;
	}

	public String activate() {
		try {
			Long functionId = functionVO.getFunctionId();
			HttpSession session = getMVCFacade().getHttpSession();
			HttpServletRequest request = ServletActionContext.getRequest();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							request.getParameter("password"));
			if (!passwordRightOrNot) {
				functionVO.setErrMsg("密码错误!");
				return SUCCESS;
			} else {
				remoteService.getSecurityConfigFacade().unFrozeFunction(
						functionId);
			}
			if ("detail".equals(from)) {
				return "detail";
			}
		} catch (Exception e) {
			handleException(e, functionVO);
		}
		return SUCCESS;
	}

	private void checkFunctionAndDept() {
		if (roleBox != null && deptBox != null && roleBox.length > 0
				&& deptBox.length > 0) {
			throw new IllegalArgumentException("分配给默认角色的功能不能再分配给部门");
		}
	}

	private List<Map<String, String>> functionListToMap(
			List<FunctionDTO> menuList) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (FunctionDTO function : menuList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", function.getFunctionName());
			map.put("parentId", function.getFunctionId() == null ? ""
					: function.getFunctionId() + "");
			list.add(map);
		}
		return list;
	}

	private Map<Long, String> getPreFunctionMap(List<FunctionDTO> functionList) {
		Map<Long, String> mapList = new HashMap<Long, String>();
		if (functionList == null || functionList.isEmpty()) {
			return mapList;
		}
		for (FunctionDTO function : functionList) {
			if (function.getFunctionName() != null) {
				mapList.put(function.getFunctionId(),
						function.getFunctionName());
			}
		}
		return mapList;
	}

	private void checkAddFunction() {
		FunctionDTO functionDTOCheck = new FunctionDTO();
		functionDTOCheck.setFunctionId(functionVO.getFunctionId());
		List<FunctionDTO> functionList = remoteService
				.getSecurityConfigFacade().queryFunction(functionDTOCheck);
		if (!functionList.isEmpty()) {
			throw new IllegalArgumentException("功能ID已经存在!");
		}
		functionDTOCheck = new FunctionDTO();
		functionDTOCheck.setFunctionName(functionVO.getFunctionName());
		List<FunctionDTO> functionListName = remoteService
				.getSecurityConfigFacade().queryFunction(functionDTOCheck);
		if (!functionListName.isEmpty()) {
			throw new IllegalArgumentException("功能名称已经存在!");
		}

		// functionUrl 可以为空
		if (StringUtils.isNotBlank(functionVO.getFunctionUrl())) {
			String url = getFunctionUrl(functionVO.getFunctionUrl());
			FunctionDTO functionByUrl = remoteService.getSecurityFacade()
					.getFunctionByUri(url);
			if (functionByUrl != null) {
				throw new IllegalArgumentException("URL已经存在!");
			}
		}
	}

	private void checkUpdateFunction() {
		FunctionDTO functionDTO = new FunctionDTO();
		functionDTO.setFunctionName(functionVO.getFunctionName());
		List<FunctionDTO> functionList = remoteService
				.getSecurityConfigFacade().queryFunction(functionDTO);
		if (!functionList.isEmpty()
				&& !functionList.get(0).getFunctionId()
						.equals(functionVO.getFunctionId())) {
			throw new IllegalArgumentException("功能名称已经存在!");
		}

		// functionUrl 可以为空
		if (StringUtils.isNotBlank(functionVO.getFunctionUrl())) {
			String url = getFunctionUrl(functionVO.getFunctionUrl());
			FunctionDTO functionByUrl = remoteService.getSecurityFacade()
					.getFunctionByUri(url);
			if (functionByUrl != null
					&& !functionByUrl.getFunctionId().equals(
							functionVO.getFunctionId())) {
				throw new IllegalArgumentException("URL已经存在!");
			}
		}
	}

	/**
	 * functionVO
	 * 
	 * @return the functionVO
	 */

	public FunctionVO getFunctionVO() {
		return functionVO;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param functionVO
	 *            the functionVO to set
	 */
	public void setFunctionVO(FunctionVO functionVO) {
		this.functionVO = functionVO;
	}

	/**
	 * deptNames
	 * 
	 * @return the deptNames
	 */

	public List<String> getAllDeptNames() {
		return allDeptNames;
	}

	/**
	 * selectedNames
	 * 
	 * @return the selectedNames
	 */

	public List<String> getSelectedNames() {
		return selectedNames;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param from
	 *            the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public FunctionVO getModel() {
		functionVO = new FunctionVO();
		return functionVO;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMenuAutoCompleteJson() {
		return menuAutoCompleteJson;
	}

	public void setMenuAutoCompleteJson(String menuAutoCompleteJson) {
		this.menuAutoCompleteJson = menuAutoCompleteJson;
	}

	public List<DepartmentDTO> getDepts() {
		return depts;
	}

	public void setDepts(List<DepartmentDTO> depts) {
		this.depts = depts;
	}

	public Long[] getRoleBox() {
		return roleBox;
	}

	public void setRoleBox(Long[] roleBox) {
		this.roleBox = roleBox;
	}

	public Long[] getDeptBox() {
		return deptBox;
	}

	public void setDeptBox(Long[] deptBox) {
		this.deptBox = deptBox;
	}

	public Map<Long, String> getPreFunctionMapList() {
		return preFunctionMapList;
	}

	public void setPreFunctionMapList(Map<Long, String> preFunctionMapList) {
		this.preFunctionMapList = preFunctionMapList;
	}

	public List<Long> getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(List<Long> departmentIds) {
		this.departmentIds = departmentIds;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

}
