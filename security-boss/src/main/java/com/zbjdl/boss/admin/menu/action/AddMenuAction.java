/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.menu.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.dto.MenuModel;
import com.zbjdl.boss.admin.menu.vo.MenuVO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;
import com.zbjdl.common.json.JSONUtils;

/**
 *
 * 类名称：AddMenuAction <br>
 * 类描述：添加菜单动作类 <br>
 *
 * @author：feng
 * @since：2011-7-13 下午06:41:13
 * @version:
 *
 */
public class AddMenuAction extends EmployeeBossBaseAction implements
		ModelDriven<MenuVO> {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单VO对象
	 */
	private MenuVO menuVO;

	private String type;

	private String menuAutoCompleteJson;

	private List<FunctionDTO> functionList;

	private Long userId;

	private Long departmentId;

	private String functionName;

	/**
	 * 添加菜单
	 */
	public String execute() {
		try {
			logger.debug(menuVO.toString());
			MenuDTO menuDTO = new MenuDTO();
			menuDTO.setMenuName(menuVO.getMenuName());
			menuDTO.setIconName(menuVO.getIconName());
			menuDTO.setFunctionId(menuVO.getFunctionId());
			menuDTO.setLevelNum(menuVO.getLevelNum());
			menuDTO.setParentId(menuVO.getParentId());
			menuDTO.setSequence(menuVO.getSequence());

			HttpSession session = getMVCFacade().getHttpSession();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(), menuVO.getPassword());
			if (!passwordRightOrNot) {
				menuVO.setErrMsg("密码错误!");
				return SUCCESS;
			} else {
				remoteService.getSecurityConfigFacade().addMenu(menuDTO);
				/* menuVO = null; */
			}
		} catch (Exception e) {
			handleException(e, menuVO);
		}

		return SUCCESS;
	}

	public String initAdd() {
		type = "add";
		HttpSession session = getMVCFacade().getHttpSession();
		UserDTO user = UserInfoUtils.getUserFromSession(session);
		MenuModel menuModel = remoteService.getSecurityFacade()
				.getMenuTreeByUserId(user.getUserId());
		List<Map<String, String>> mapList = getMenuMap(menuModel);
		menuAutoCompleteJson = JSONUtils.toJsonString(mapList);
		return SUCCESS;
	}

	public String searchUserFunction() {
		return SUCCESS;
	}

	public String validateMenuNameUnique() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menuName = request.getParameter("menuName");
		String menuId = request.getParameter("menuId");
		MenuDTO menu = new MenuDTO();
		menu.setMenuName(menuName);
		List<MenuDTO> menuList = remoteService.getSecurityConfigFacade()
				.queryMenu(menu);
		if (!menuList.isEmpty()) {
			if (menuId != null && !"".equals(menuId)) {
				if (!menuList.get(0).getMenuId().equals(Long.parseLong(menuId))
						&& menuList.get(0).getMenuName().equals(menuName)) {
					menuVO.setErrMsg("菜单名称重复");
					return SUCCESS;
				}
			} else {
				menuVO.setErrMsg("菜单名称重复");
				return SUCCESS;
			}

		}
		return SUCCESS;
	}

	/**
	 * 只取2级菜单不做递归
	 *
	 * @param menuModel
	 * @return
	 */
	private List<Map<String, String>> getMenuMap(MenuModel menuModel) {
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		if (menuModel == null) {
			return mapList;
		}
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setLevelNum(2);
		List<MenuDTO> menuList = remoteService.getSecurityConfigFacade()
				.queryMenu(menuDTO);
		for (MenuDTO menu : menuList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", menu.getMenuId() + "");
			map.put("name", menu.getMenuName());
			mapList.add(map);
		}
		return mapList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public MenuVO getModel() {
		menuVO = new MenuVO();
		return menuVO;
	}

	/**
	 * 描述： 菜单VO对象
	 *
	 * @param menuVO
	 *            the menuVO to set
	 */
	public void setMenuVO(MenuVO menuVO) {
		this.menuVO = menuVO;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MenuVO getMenuVO() {
		return menuVO;
	}

	public String getMenuAutoCompleteJson() {
		return menuAutoCompleteJson;
	}

	public List<FunctionDTO> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<FunctionDTO> functionList) {
		this.functionList = functionList;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
