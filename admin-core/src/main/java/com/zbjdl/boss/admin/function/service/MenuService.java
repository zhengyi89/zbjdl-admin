/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.service;

import java.util.List;

import com.zbjdl.boss.admin.function.dto.MenuDTO;

/**
 * 类名称：MenuService <br>
 * 类描述：菜单服务接口定义 <br>
 * 
 * @author feng
 * @since 2011-7-5 下午04:34:37
 * @version 1.0.0
 */
public interface MenuService {

	/**
	 * 对应映射文件中ID为query的查询语句
	 */
	public static final String MAPPING_QUERY_MENU = "query";

	/**
	 * 对应映射文件中ID为hasChildrenMenus的查询语句
	 */
	public static final String MAPPING_HAS_CHILDREN_MENUS = "hasChildrenMenus";

	/**
	 * 对应映射文件中ID为queryMenusByFunctionIds的查询语句
	 */
	public static final String MAPPING_QUERY_MENUS_BY_FUNCTIONIDS = "queryMenusByFunctionIds";
	
	/**
	 * 对应映射文件中ID为queryAllMenus的查询语句
	 */
	public static final String MAPPING_QUERY_All_MENUS = "queryAllMenus";

	/**
	 * 对应映射文件中ID为queryMaxSequenceByParentId的查询语句
	 */
	public static final String MAPPING_QUERY_MAX_SEQUENCE_BY_PARENTID = "queryMaxSequenceByParentId";

	/**
	 * 默认的跟菜单的parentId值
	 */
	public static final Long DEFAULT_MENU_ROOT_PARENT_ID = 0L;

	/**
	 * 
	 * 描述： 创建菜单
	 * 
	 * @param menuDTO
	 *            菜单信息
	 * @return 菜单ID
	 */
	Long createMenu(MenuDTO menuDTO);

	/**
	 * 
	 * 描述： 删除菜单
	 * 
	 * @param menuID
	 *            菜单ID
	 */
	void deleteMenu(Long menuID);

	/**
	 * 
	 * 描述： 更新菜单
	 * 
	 * @param menuDTO
	 *            菜单更新信息
	 */
	void updateMenu(MenuDTO menuDTO);

	/**
	 * 
	 * 描述： 对菜单进行批量更新
	 * 
	 * @param menuDTOs
	 *            需要更新的菜单列表
	 */
	void batchUpdateMenu(List<MenuDTO> menuDTOs);

	/**
	 * 
	 * 描述： 根据菜单ID查询
	 * 
	 * @param menuID
	 *            菜单ID
	 * @return 菜单信息
	 */
	MenuDTO queryMenuByID(Long menuID);

	/**
	 * 
	 * 描述： 根据查询条件查询菜单
	 * 
	 * @param queryDTO
	 *            查询条件
	 * @return 菜单列表
	 */
	List<MenuDTO> query(MenuDTO queryDTO);

	/**
	 * 
	 * 描述： 当前菜单是否含有子菜单
	 * 
	 * @param menuID
	 *            菜单ID
	 * @return 是否含有子菜单
	 */
	boolean hasChildrenMenus(Long menuID);

	/**
	 * 
	 * 描述： 根据功能Id列表查询所拥有的菜单
	 * 
	 * @param functionIds
	 *            功能Id列表
	 * @return 菜单列表
	 */
	List<MenuDTO> queryMenusByFunctionIds(List<Long> functionIds);
	
	List<MenuDTO> queryAllMenus();

	/**
	 * 描述： 根据用户 Id 查询所拥有的菜单
	 *
	 * @param userId
	 *            用户 Id
	 * @return 菜单列表
	 */
	List<MenuDTO> queryMenusByUserId(Long userId);

	/**
	 * 
	 * 描述： 获得指定菜单下所有子菜单的最大sequence值
	 * 
	 * @param parentId
	 *            父菜单Id
	 * @return 指定菜单下所有子菜单的最大sequence值
	 */
	Integer queryMaxSequenceByParentId(Long parentId);

}
