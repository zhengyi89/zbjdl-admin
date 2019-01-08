/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.json.annotations.JSON;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.vo.FunctionVO;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;

/**
 * 
 * 类名称：ShowFunctionAction <br>
 * 类描述：功能详情显示Action <br>
 * 
 * @author：feng
 * @since：2011-7-25 上午10:25:37
 * @version:
 * 
 */
public class ShowFunctionAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 功能Id
	 */
	private Long functionId;

	/**
	 * 功能VO对象
	 */
	private FunctionVO functionVO = new FunctionVO();

	/**
	 * 功能VO列表
	 */
	private List<FunctionVO> functionVOList = new ArrayList<FunctionVO>();

	/**
	 * 角色Id
	 */
	private Long roleId;

	/**
	 * 显示功能详情
	 */
	public String execute() {
		FunctionDTO functionDTO = remoteService.getSecurityConfigFacade()
				.getFunctionInfo(functionId);
		if (functionDTO == null) {
			return SUCCESS;
		}
		functionVO.setFunctionId(functionId);
		functionVO.setFunctionName(functionDTO.getFunctionName());
		functionVO.setDescription(functionDTO.getDescription());
		functionVO.setCreateTime(functionDTO.getCreateTime());
		functionVO.setFunctionStatus(functionDTO.getFunctionStatus().name());
		functionVO.setRiskLevel(functionDTO.getRiskLevel().name());

		String[] deptNames = queryDeptNamesByFunctionId(functionId);
		functionVO.setDeptNames(deptNames);

		String menuPath = queryMenuPathByFunctionId(functionId);
		functionVO.setMenuPath(menuPath);
		functionVO.setDisplay(functionDTO.getDisplay());

		return SUCCESS;
	}

	/**
	 * 
	 * 描述： 查询所有激活状态的功能
	 * 
	 * @return 跳转页面
	 */
	/*
	 * public String queryAllActiveFunctions() { UserDTO userDto =
	 * UserInfoUtils.getUserFromSession(null); if (userDto == null) { return
	 * SUCCESS; } List<FunctionDTO> functions = null; if
	 * (userDto.getPrimaryDepartmentId() != null) { //查询本部门私有 functions =
	 * remoteService
	 * .getFunctionFacade().queryFunctionsByDeptId(userDto.getPrimaryDepartmentId
	 * ()); for (FunctionDTO function : functions) { if
	 * (!function.getFunctionStatus().equals(FunctionStatusEnum.ACTIVE)) {
	 * continue; } FunctionVO vo = new FunctionVO();
	 * vo.setFunctionId(function.getFunctionId());
	 * vo.setFunctionName(function.getFunctionName());
	 * vo.setDescription(function.getDescription());
	 * vo.setRiskLevel(function.getRiskLevel().name());
	 * vo.setDeptNames(queryDeptNamesByFunctionId(function.getFunctionId()));
	 * vo.setMenuPath(queryMenuPathByFunctionId(function.getFunctionId()));
	 * functionVOList.add(vo); } } //查询部门公开 FunctionDTO query = new
	 * FunctionDTO(); query.setFunctionStatus(FunctionStatusEnum.ACTIVE);
	 * query.setFunctionType(FunctionTypeEnum.DEPARTMENT_PUBLIC); functions =
	 * null; functions = remoteService.getFunctionFacade()
	 * .queryFunction(query); for (FunctionDTO function : functions) {
	 * FunctionVO vo = new FunctionVO();
	 * vo.setFunctionId(function.getFunctionId());
	 * vo.setFunctionName(function.getFunctionName());
	 * vo.setDescription(function.getDescription());
	 * vo.setRiskLevel(function.getRiskLevel().name());
	 * vo.setDeptNames(queryDeptNamesByFunctionId(function.getFunctionId()));
	 * vo.setMenuPath(queryMenuPathByFunctionId(function.getFunctionId()));
	 * functionVOList.add(vo); } return SUCCESS; }
	 */

	/**
	 * 
	 * 描述： 查询所有功能
	 * 
	 * @return 跳转页面
	 */
	public String queryAllFunctions() {
		return SUCCESS;
	}

	/**
	 * 
	 * 描述： 根据功能Id查询所有部门名称
	 * 
	 * @param functionId
	 *            跳转页面
	 * @return
	 */
	private String[] queryDeptNamesByFunctionId(Long functionId) {
		List<DepartmentDTO> depts = remoteService.getSecurityConfigFacade()
				.queryDepartmentsByFunctionId(functionId);
		List<String> nameList = new ArrayList<String>();
		for (DepartmentDTO dept : depts) {
			nameList.add(dept.getDepartmentName());
		}
		String[] deptNames = new String[] {};
		deptNames = nameList.toArray(deptNames);
		return deptNames;
	}

	/**
	 * 
	 * 描述：根据功能Id查询该功能下所有的菜单路径
	 * 
	 * @param functionId
	 * @return
	 */
	private String queryMenuPathByFunctionId(Long functionId) {
		MenuDTO query = new MenuDTO();
		query.setFunctionId(functionId);
		List<MenuDTO> menus = remoteService.getSecurityConfigFacade()
				.queryMenu(query);
		StringBuffer menuNames = new StringBuffer();
		for (MenuDTO menu : menus) {
			List<String> currentMenus = new ArrayList<String>();
			currentMenus.add(menu.getMenuName());
			generateMenuPath(menu.getParentId(), currentMenus);
			menuNames.append(assemblePath(currentMenus)).append("，");
		}
		if (menuNames.length() > 0) {
			return menuNames.substring(0, menuNames.length() - 1);
		}
		return null;
	}

	/**
	 * 
	 * 描述： 生成菜单路径
	 * 
	 * @param menuId
	 *            菜单Id
	 * @param menuNames
	 *            菜单名称List
	 */
	private void generateMenuPath(Long menuId, List<String> menuNames) {
		if (menuId != null && menuId != 0L) {
			MenuDTO menu = remoteService.getSecurityConfigFacade()
					.getMenuInfo(menuId);
			if (menu != null) {
				String menuName = menu.getMenuName();
				if (!StringUtils.isEmpty(menuName)) {
					menuNames.add(menuName);
				}
				generateMenuPath(menu.getParentId(), menuNames);
			}
		} else {
			return;
		}
	}

	/**
	 * 
	 * 描述： 根据菜单名称List拼装菜单路径
	 * 
	 * @param menuNames
	 *            菜单名称List
	 * @return 菜单路径
	 */
	private String assemblePath(List<String> menuNames) {
		StringBuffer path = new StringBuffer();
		if (!menuNames.isEmpty()) {
			for (int i = menuNames.size() - 1; i >= 0; i--) {
				String name = menuNames.get(i);
				path.append(name).append("/");
			}
		}
		if (path.length() > 0) {
			return path.substring(0, path.length() - 1);
		} else {
			return null;
		}
	}

	/**
	 * 描述： 功能Id
	 * 
	 * @param functionId
	 *            the functionId to set
	 */
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	/**
	 * functionVO
	 * 
	 * @return the functionVO
	 */
	@JSON(serialize = false)
	public FunctionVO getFunctionVO() {
		return functionVO;
	}

	/**
	 * 描述： 功能VO
	 * 
	 * @param functionVO
	 *            the functionVO to set
	 */
	public void setFunctionVO(FunctionVO functionVO) {
		this.functionVO = functionVO;
	}

	/**
	 * functionVOList
	 * 
	 * @return the functionVOList
	 */
	@JSON(name = "functionVOList")
	public List<FunctionVO> getFunctionVOList() {
		return functionVOList;
	}

	public Long getRoleId() {
		return roleId;
	}

	/**
	 * 描述： 角色Id
	 * 
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
