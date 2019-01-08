/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.menu.action;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.function.enums.MenuDirectionEnum;

/**
 * 
 * 类名称：MoveMenuAction <br>
 * 类描述： 菜单移动动作类<br>
 * 
 * @author：feng
 * @since：2011-7-20 上午11:29:32
 * @version:
 * 
 */
public class MoveMenuAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 选中的菜单Id
	 */
	private Long selectedMenuId;

	/**
	 * 菜单移动方向
	 */
	private String direction;

	/**
	 * 移动菜单
	 */
	public String execute() {
		if (MenuDirectionEnum.UP.name().equals(direction)) {
			remoteService.getSecurityConfigFacade().moveMenu(
					selectedMenuId, MenuDirectionEnum.UP);
		} else {
			remoteService.getSecurityConfigFacade().moveMenu(
					selectedMenuId, MenuDirectionEnum.DOWN);
		}
		return SUCCESS;
	}

	/**
	 * 描述： 选中的菜单Id
	 * 
	 * @param selectedMenuId
	 *            the selectedMenuId to set
	 */
	public void setSelectedMenuId(Long selectedMenuId) {
		this.selectedMenuId = selectedMenuId;
	}

	/**
	 * 描述： 菜单移动方向
	 * 
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

}
