/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.role.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.role.dto.RoleDTO;
import com.zbjdl.boss.admin.role.enums.RoleStatusEnum;
import com.zbjdl.boss.admin.role.vo.RoleVO;
import com.zbjdl.boss.admin.user.dto.AdminDTO;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;
import com.zbjdl.boss.admin.ztree.utils.ZtreeUtils;
import com.zbjdl.common.json.JSONUtils;

/**
 * 类名称：EditRoleAction <br>
 * 类描述： <br>
 * 
 * @author feng
 * @since 2011-7-28 下午01:21:36
 * @version 1.0.0
 */
public class EditRoleAction extends EmployeeBossBaseAction implements
		ModelDriven<RoleVO> {

	private static final long serialVersionUID = 1L;

	private RoleVO roleVO;

	public String init() {
		HttpSession session = getMVCFacade().getHttpSession();
		UserDTO user = UserInfoUtils.getUserFromSession(session);
		HttpServletRequest request = ServletActionContext.getRequest();
		// 角色属于部门调整
		List<DepartmentDTO> departmentList = remoteService.getUserFacade()
				.queryAllDepartments();
		roleVO.setDepts(departmentList);
		roleVO.setType(request.getParameter("type"));

		if ("edit".equalsIgnoreCase(roleVO.getType())) {
			RoleDTO roleDTO = remoteService.getSecurityConfigFacade()
					.getRoleInfo(roleVO.getRoleId());
			roleVO.setDescription(roleDTO.getDescription());
			roleVO.setRoleName(roleDTO.getRoleName());
			// 角色属于部门调整
			roleVO.setDepartmentId(roleDTO.getDepartmentId());
		}

		// 查询本部门,除去私有和冻结
		List<FunctionDTO> functionList = null;
		if (user.getIsSuperAdmin() != null && user.getIsSuperAdmin()) {
			functionList = remoteService.getSecurityConfigFacade()
					.queryAllFunction();
		} else {
			functionList = remoteService.getSecurityConfigFacade()
					.queryFunctionByDepartment(user.getPrimaryDepartmentId());
		}
		List<FunctionDTO> roleFunctions = new ArrayList<FunctionDTO>();
		if (roleVO != null && roleVO.getRoleId() != null) {
			roleFunctions = remoteService.getSecurityConfigFacade()
					.queryFunctionByRole(roleVO.getRoleId());
		}

		Map<Long, Long[]>[] dependenceAndRefMap = remoteService
				.getSecurityConfigFacade().getDependenceRelation();

		List<Map<String, Object>> zTreeJson = ZtreeUtils.buildJson(
				functionList, roleFunctions, false, dependenceAndRefMap);
		request.setAttribute("zTreeJson", JSONUtils.toJsonString(zTreeJson));

		return SUCCESS;
	}

	public String execute() {
		try {
			HttpSession session = getMVCFacade().getHttpSession();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(), roleVO.getPassword());
			if (!passwordRightOrNot) {
				roleVO.setErrMsg("密码错误!");
			} else {
				RoleDTO role = new RoleDTO();
				role.setRoleId(roleVO.getRoleId());
				role.setRoleName(roleVO.getRoleName());
				role.setDescription(roleVO.getDescription());
				Long[] node = null;
				if (roleVO.getFunctionIds() != null
						&& !"".equals(roleVO.getFunctionIds())) {
					node = ZtreeUtils.getLongArrayByStringNum(roleVO
							.getFunctionIds());
				}
				remoteService.getSecurityConfigFacade().configRoleFunction(
						role, node);
			}
		} catch (Exception e) {
			handleException(e, roleVO);
		}
		return SUCCESS;
	}

	public String validateRoleNameUnique() {
		String roleName = roleVO.getRoleName();

		HttpSession session = getMVCFacade().getHttpSession();
		UserDTO user = UserInfoUtils.getUserFromSession(session);
		AdminDTO adminDTO = new AdminDTO();
		if (user != null) {
			adminDTO.setUserId(user.getUserId());
			adminDTO.setLoginName(user.getLoginName());
			adminDTO.setPassword(user.getPassword());
		}

		RoleDTO query = new RoleDTO();
		query.setRoleName(roleName);
		List<RoleDTO> result = remoteService.getSecurityConfigFacade()
				.queryRoles(query);
		for (int i = 0; i < result.size(); i++) {
			RoleDTO roleDTO = result.get(i);
			if (roleDTO.getRoleStatus() == RoleStatusEnum.FORBID) {
				result.remove(i);
				i--;
			}
		}
		roleVO.setRoleNameUnique(result.isEmpty());

		return SUCCESS;
	}

	public String add() {
		try {
			HttpSession session = getMVCFacade().getHttpSession();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(), roleVO.getPassword());
			RoleDTO role = new RoleDTO();
			role.setRoleStatus(RoleStatusEnum.ACTIVE);
			role.setRoleName(roleVO.getRoleName());
			// 角色属于部门调整
			// role.setRoleType(RoleTypeEnum.valueOf(roleVO.getRoleType()));
			role.setDescription(roleVO.getDescription());
			role.setDepartmentId(user.getPrimaryDepartmentId());
			Long[] node = null;
			if (roleVO.getFunctionIds() != null
					&& !"".equals(roleVO.getFunctionIds())) {
				node = ZtreeUtils.getLongArrayByStringNum(roleVO
						.getFunctionIds());
			}
			if (!passwordRightOrNot) {
				roleVO.setErrMsg("密码错误!");
			} else {
				remoteService.getSecurityConfigFacade().configRoleFunction(
						role, node);
			}
		} catch (Exception e) {
			handleException(e, roleVO);
		}
		return SUCCESS;
	}

	public String delete() {
		try {
			HttpSession session = getMVCFacade().getHttpSession();
			HttpServletRequest request = ServletActionContext.getRequest();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							request.getParameter("password"));
			Long roleId = roleVO.getRoleId();
			if (!passwordRightOrNot) {
				roleVO.setErrMsg("密码错误!");
			} else {
				remoteService.getSecurityConfigFacade().deleteRole(roleId);
			}
		} catch (Exception e) {
			handleException(e, roleVO);
		}
		return SUCCESS;
	}

	public String activate() {
		try {
			HttpSession session = getMVCFacade().getHttpSession();
			HttpServletRequest request = ServletActionContext.getRequest();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							request.getParameter("password"));
			Long roleId = roleVO.getRoleId();
			AdminDTO adminDTO = new AdminDTO();
			if (user != null) {
				adminDTO.setUserId(user.getUserId());
				adminDTO.setLoginName(user.getLoginName());
				adminDTO.setPassword(roleVO.getPassword());
			}
			if (!passwordRightOrNot) {
				roleVO.setErrMsg("密码错误!");
			} else {
				remoteService.getSecurityConfigFacade().unFrozeRole(roleId);
			}
		} catch (Exception e) {
			handleException(e, roleVO);
		}
		return SUCCESS;

	}

	public String freeze() {
		try {
			HttpSession session = getMVCFacade().getHttpSession();
			HttpServletRequest request = ServletActionContext.getRequest();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							request.getParameter("password"));
			Long roleId = roleVO.getRoleId();
			AdminDTO adminDTO = new AdminDTO();
			if (user != null) {
				adminDTO.setUserId(user.getUserId());
				adminDTO.setLoginName(user.getLoginName());
				adminDTO.setPassword(roleVO.getPassword());
			}
			if (!passwordRightOrNot) {
				roleVO.setErrMsg("密码错误!");
			} else {
				remoteService.getSecurityConfigFacade().frozeRole(roleId);
			}
		} catch (Exception e) {
			handleException(e, roleVO);
		}
		return SUCCESS;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public RoleVO getModel() {
		roleVO = new RoleVO();
		return roleVO;
	}

	/**
	 * roleVO
	 * 
	 * @return the roleVO
	 */

	public RoleVO getRoleVO() {
		return roleVO;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param roleVO
	 *            the roleVO to set
	 */
	public void setRoleVO(RoleVO roleVO) {
		this.roleVO = roleVO;
	}

}
