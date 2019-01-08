/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.facade;

import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.dto.MenuKVDTO;
import com.zbjdl.boss.admin.function.dto.MenuModel;

import java.util.List;
import java.util.Map;

/**
 * 门户管理接口
 * 
 * @author：feng
 * @since：2013-5-30 下午03:00:26
 * @version:
 */
public interface PortalMenuFacade {

	/**
	 * 取得单个菜单信息
	 */
	public MenuDTO findMenu(Long menuId);

	/**
	 * 添加菜单
	 */
	public Long addMenu(MenuDTO menuDTO);

	/**
	 * 更新菜单
	 */
	public void updateMenu(MenuDTO menuDTO);

	/**
	 * 移动菜单
	 */
	public void moveMenus(List<Long> menuIds, Long parentId, Long departmentId);
	
	/**
	 * 非级联批量删除菜单
	 */
	public void deleteMenus(List<Long> menuIds);

    /**
     * 获取指定部门下的所有菜单
     */
    public List<MenuKVDTO> findMenuKVByDepartmentId(Long departmentId);

	/**
	 * 取得菜单树
	 */
	public MenuModel findDepartmentMemuTree(Long departmentId);
	
	/**
	 * 导入菜单
	 * @param menuDetail 菜单明细，多行字符串，单行格式为：菜单名,菜单url,父菜单名
	 * @param departmentId 部门ID
	 */
	public void importMenus(String menuDetail, Long departmentId);

	public void deleteDepartmentMenu(Long departmentId);

	// 缓存用户最后打开的页面

	/**
	 * 缓存Tab
	 *
	 * @param userId
	 * @param title
	 * @param url
	 */
	public void addTab(Long userId, String title, String url);

	/**
	 * 删除Tab
	 *
	 * @param userId
	 * @param title
	 */
	public void deleteTab(Long userId, String title);

	/**
	 * 清空Tab
	 *
	 * @param userId
	 */
	public void cleanTab(Long userId);

	/**
	 * 清空Tab
	 *
	 * @param userIds
	 */
	public void cleanAllTab(List<String> userIds);

	/**
	 * 获取所有Tab
	 *
	 * @param userId
	 * @return
	 */
	public Map<String, String> getAllTab(Long userId);

}
