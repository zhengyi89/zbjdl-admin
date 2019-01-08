/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.service;

import java.util.List;

import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.dto.MenuKVDTO;

/**
 * 
 * @author：feng    
 * @since：2013-5-30 下午03:49:44 
 * @version:
 */
public interface PortalMenuService {

	/**
	 * 
	 * 描述： 创建菜单
	 * 
	 * @param menuDTO
	 *            菜单信息
	 * @return 菜单ID
	 */
	Long addMenu(MenuDTO menuDTO);

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

	void batchAddMenu(List<MenuDTO> importMenus);

	/**
	 * 
	 * 描述： 对菜单进行批量更新
	 * 
	 * @param menuDTOs
	 *            需要更新的菜单列表
	 */
	void batchUpdateMenu(List<MenuDTO> menuDTOs);

	void batchDeleteMenu(List<Long> menuIds);

	/**
	 * 
	 * 描述： 根据菜单ID查询
	 * 
	 * @param menuID
	 *            菜单ID
	 * @return 菜单信息
	 */
	MenuDTO findMenuByID(Long menuID);

	/**
	 * 
	 * 描述： 根据部门ID查询所拥有的菜单
	 */
	List<MenuDTO> queryMenusByDepartment(Long departmentId);

	List<MenuKVDTO> queryMenusKVByDepartment(Long departmentId);

	List<MenuDTO> queryMenusByIdList(List<Long> menuIds);
	
	List<MenuDTO> queryMenusByParentId(Long parentId);

	void deleteDepartmentMenu(Long departmentId);
}
