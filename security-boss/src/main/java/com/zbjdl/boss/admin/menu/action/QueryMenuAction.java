/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.menu.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.BeanUtils;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.menu.model.Attribute;
import com.zbjdl.boss.admin.menu.model.Node;
import com.zbjdl.boss.admin.menu.vo.Dept;
import com.zbjdl.boss.admin.menu.vo.Function;
import com.zbjdl.boss.admin.menu.vo.MenuVO;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.enums.UserTypeEnum;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;
import com.zbjdl.common.utils.config.ConfigParam;
import com.zbjdl.common.utils.config.ConfigurationUtils;

/**
 *
 * 类名称：QueryMenuAction <br>
 * 类描述： 菜单查询动作类<br>
 *
 * @author：feng
 * @since：2011-7-13 下午06:40:17
 * @version:
 *
 */
public class QueryMenuAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 操作类型：更新
	 */
	private static final String OPERATION_UPDATE = "update";

	/**
	 * 操作类型：添加同级菜单
	 */
	private static final String OPERATION_ADD_SIBLING_MENU = "addSiblingMenu";

	/**
	 * 操作类型：添加子菜单
	 */
	private static final String OPERATION_ADD_SUB_MENU = "addSubMenu";

	/**
	 * 默认父菜单Id
	 */
	private long id = 0L;

	/**
	 * 部门Id
	 */
	private long deptId;

	/**
	 * 操作类型
	 */
	private String operation;

	/**
	 * 菜单VO对象
	 */
	private MenuVO menuVO = new MenuVO();

	/**
	 * 菜单树节点List
	 */
	private List<Node> nodes = new ArrayList<Node>();

	/**
	 * 功能List
	 */
	private List<Function> functionList = new ArrayList<Function>();

	private Long userId;

	private Long departmentId;

	/*
	 * public String menuSetting(){ return SUCCESS; }
	 */

	public String menuSetting() {
		HttpSession session = getMVCFacade().getHttpSession();
		UserDTO user = UserInfoUtils.getUserFromSession(session);
		Boolean isDepartment = remoteService.getSecurityFacade()
				.checkUserType(user.getUserId(),
						UserTypeEnum.DEPARTMENT_MANAGER);
		if (isDepartment) {
			departmentId = user.getPrimaryDepartmentId();
		}
		return SUCCESS;
	}

	/**
	 * 菜单树显示
	 */
	public String execute() {
		MenuDTO query = new MenuDTO();
		query.setParentId(id);
		List<MenuDTO> menus = remoteService.getSecurityConfigFacade()
				.queryMenu(query);
		for (MenuDTO menu : menus) {
			Attribute attr = new Attribute();
			attr.setId(String.valueOf(menu.getMenuId()));
			Node node = new Node();
			node.setAttr(attr);
			node.setData(menu.getMenuName());
			nodes.add(node);
		}
		return SUCCESS;
	}

	/**
	 *
	 * 描述： 显示选中菜单详细信息
	 *
	 * @return
	 */
	public String showMenuDetail() {
		logger.debug("id : " + id);

		MenuDTO selectedMenu = remoteService.getSecurityConfigFacade()
				.getMenuInfo(id);

		BeanUtils.copyProperties(selectedMenu, menuVO);

		Map<String, String> menuIcons = getMenuIcons();
		menuVO.setMenuIcons(menuIcons);

		if (OPERATION_ADD_SIBLING_MENU.equals(operation)
				|| OPERATION_UPDATE.equals(operation)) {
			MenuDTO parentMenu = remoteService.getSecurityConfigFacade()
					.getMenuInfo(selectedMenu.getParentId());
			if (parentMenu != null) {
				menuVO.setParentName(parentMenu.getMenuName());
			}
		} else if (OPERATION_ADD_SUB_MENU.equals(operation)) {
			menuVO.setParentName(selectedMenu.getMenuName());
		}

		List<DepartmentDTO> depts = remoteService.getUserFacade()
				.queryDepartments(new DepartmentDTO());
		for (DepartmentDTO dept : depts) {
			Long deptId = dept.getDepartmentId();
			Dept deptInfo = new Dept();
			deptInfo.setDeptId(deptId);
			deptInfo.setDeptName(dept.getDepartmentName());
			List<FunctionDTO> functions = remoteService
					.getSecurityConfigFacade()
					.queryFunctionByDepartment(deptId);
			for (FunctionDTO function : functions) {
				Function functionInfo = new Function();
				functionInfo.setFunctionId(function.getFunctionId());
				functionInfo.setFunctionName(function.getFunctionName());
				if (OPERATION_UPDATE.equals(operation)) {
					if (selectedMenu.getFunctionId() == function
							.getFunctionId()) {
						functionInfo.setSelected(true);
						deptInfo.setSelected(true);
					}
				}
				deptInfo.getFunctions().add(functionInfo);
			}
			menuVO.getDepts().add(deptInfo);
		}
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getMenuIcons() {
		ConfigParam<Map<String, String>> param = ConfigurationUtils
				.getAppConfigParam("admin.menu.icons");
		return param.getValue();
	}


	/**
	 *
	 * 描述： 根据部门Id查询所用的功能
	 *
	 * @return
	 */
	public String queryFunctionsByDeptId() {
		logger.debug("deptId : " + deptId);

		List<FunctionDTO> functions = remoteService
				.getSecurityConfigFacade().queryFunctionByDepartment(deptId);
		for (FunctionDTO function : functions) {
			Function functionInfo = new Function();
			functionInfo.setFunctionId(function.getFunctionId());
			functionInfo.setFunctionName(function.getFunctionName());
			functionList.add(functionInfo);
		}
		return SUCCESS;
	}

	@JSON
	public List<Node> getNodes() {
		return nodes;
	}

	/**
	 * menuVO
	 *
	 * @return the menuVO
	 */
	@JSON(name = "menuVO")
	public MenuVO getMenuVO() {
		return menuVO;
	}

	/**
	 * 描述： 菜单Id
	 *
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 描述： 部门Id
	 *
	 * @param deptId
	 *            the deptId to set
	 */
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	/**
	 * 描述： 操作类型
	 *
	 * @param operation
	 *            the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * functionList
	 *
	 * @return the functionList
	 */
	@JSON(name = "functionList")
	public List<Function> getFunctionList() {
		return functionList;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

}
