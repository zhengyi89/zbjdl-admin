/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 类名称：MenuHelp <br>
 * 类描述：菜单辅助类 <br>
 * 
 * @author：feng
 * @since：2011-7-11 下午02:11:36
 * @version:
 * 
 */
public class MenuHelper {

	/**
	 * 父菜单
	 */
	private MenuDTO father;

	/**
	 * 子菜单列表
	 */
	private List<MenuDTO> children = new ArrayList<MenuDTO>();

	/**
	 * father
	 * 
	 * @return the father
	 */

	public MenuDTO getFather() {
		return father;
	}

	/**
	 * 描述： 父菜单
	 * 
	 * @param father
	 *            the father to set
	 */
	public void setFather(MenuDTO father) {
		this.father = father;
	}

	/**
	 * children
	 * 
	 * @return the children
	 */

	public List<MenuDTO> getChildren() {
		return children;
	}

	/**
	 * 描述： 子菜单列表
	 * 
	 * @param children
	 *            the children to set
	 */
	public void setChildren(List<MenuDTO> children) {
		this.children = children;
	}

}
