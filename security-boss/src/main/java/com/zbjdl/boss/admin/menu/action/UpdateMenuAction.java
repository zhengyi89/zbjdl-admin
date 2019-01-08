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
import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.dto.MenuModel;
import com.zbjdl.boss.admin.menu.vo.MenuVO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;
import com.zbjdl.common.json.JSONUtils;

/**
 *
 * 类名称：UpdateMenuAction <br>
 * 类描述：更新菜单动作类 <br>
 *
 * @author：feng
 * @since：2011-7-13 下午06:40:53
 * @version:
 *
 */
public class UpdateMenuAction extends EmployeeBossBaseAction implements
		ModelDriven<MenuVO> {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单VO对象
	 */
	private MenuVO menuVO;

	private String type;

	private String menuAutoCompleteJson;

	private Map<String, Long> menuMap = new HashMap<String, Long>();

	/**
	 * 更新菜单
	 */
	public String execute() {
		try {
			logger.debug(menuVO.toString());
			MenuDTO menuDTO = new MenuDTO();
			menuDTO.setMenuId(menuVO.getMenuId());
			List<MenuDTO> menuList = remoteService
					.getSecurityConfigFacade().queryMenu(menuDTO);

			if (!menuList.isEmpty()) {
				menuDTO = menuList.get(0);
				// 能够更新的内容
				menuDTO.setMenuName(menuVO.getMenuName());
				menuDTO.setIconName(menuVO.getIconName());
				menuDTO.setFunctionId(menuVO.getFunctionId());
				menuDTO.setLevelNum(menuVO.getLevelNum());
				menuDTO.setParentId(menuVO.getParentId());
				menuDTO.setSequence(menuVO.getSequence());
				HttpSession session = getMVCFacade().getHttpSession();
				UserDTO user = UserInfoUtils.getUserFromSession(session);
				Boolean passwordRightOrNot = remoteService.getUserFacade()
						.userAuthentication(user.getUserId(),
								menuVO.getPassword());
				if (!passwordRightOrNot) {
					menuVO.setErrMsg("密码错误!");
					return SUCCESS;
				} else {
					remoteService.getSecurityConfigFacade().updateMenu(
							menuDTO);
				}
			}
		} catch (Exception e) {
			handleException(e, menuVO);
		}
		return SUCCESS;
	}

	public String initUpdate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String menu = request.getParameter("menuId");
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setMenuId(Long.parseLong(menu));
		List<MenuDTO> menuList = remoteService.getSecurityConfigFacade()
				.queryMenu(menuDTO);
		if (!menuList.isEmpty()) {
			menuDTO = menuList.get(0);
			menuVO.setFunctionId(menuDTO.getFunctionId());
			menuVO.setIconName(menuDTO.getIconName());
			menuVO.setLevelNum(menuDTO.getLevelNum());
			menuVO.setMenuName(menuDTO.getMenuName());
			menuVO.setParentId(menuDTO.getParentId());
			menuVO.setSequence(menuDTO.getSequence());
			menuVO.setMenuId(menuDTO.getMenuId());
		}

		HttpSession session = getMVCFacade().getHttpSession();
		UserDTO user = UserInfoUtils.getUserFromSession(session);
		MenuModel menuModel = remoteService.getSecurityFacade()
				.getMenuTreeByUserId(user.getUserId());
		List<Map<String, String>> mapList = getMenuMap(menuModel);
		menuAutoCompleteJson = JSONUtils.toJsonString(mapList);

		type = "update";

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
	/* @JSON(serialize = false) */

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

	public MenuVO getMenuVO() {
		return menuVO;
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

	public Map<String, Long> getMenuMap() {
		return menuMap;
	}

	public void setMenuMap(Map<String, Long> menuMap) {
		this.menuMap = menuMap;
	}

}
