/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.exception;

import com.zbjdl.common.exception.BaseException;

/**
 * 
 * 类名称：MenuException <br>
 * 类描述： 菜单业务异常定义 <br>
 * 
 * @author：feng
 * @since：2011-7-6 下午05:48:44
 * @version:
 * 
 */
public class MenuException extends BaseException {

	private static final long serialVersionUID = 1L;

	/**
	 * 如果含有子菜单，则不能删除
	 */
	public static MenuException MENU_CAN_NOT_BE_DELETED_WHEN_SUB_MENUS_EXIST_EXCEPTION = new MenuException(
			"001");

	/**
	 * 没有权限创建菜单
	 */
	public static MenuException NO_AUTH_TO_ADD_MENU_EXCEPTION = new MenuException(
			"002");

	/**
	 * 没有权删除建菜单
	 */
	public static MenuException NO_AUTH_TO_DELETE_MENU_EXCEPTION = new MenuException(
			"003");

	/**
	 * 没有权更新建菜单
	 */
	public static MenuException NO_AUTH_TO_UPDATE_MENU_EXCEPTION = new MenuException(
			"004");

	/**
	 * 菜单不存在
	 */
	public static MenuException MENU_NOT_EXIST_EXCEPTION = new MenuException(
			"005");
	
	/**
	 * 菜单已经到顶端不能在向上移动
	 */
	public static MenuException MENU_CAN_NOT_BE_MOVED_UP_ON_TOP = new MenuException(
			"006");
	
	/**
	 * 菜单已经到底端不能在向下移动
	 */
	public static MenuException MENU_CAN_NOT_BE_MOVED_DOWN_ON_BOTTOM = new MenuException(
			"007");

	protected MenuException(String defineCode) {
		super(defineCode);
	}

}
