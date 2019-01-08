/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.entity.MenuEntity;
import com.zbjdl.boss.admin.function.service.MenuService;
import com.zbjdl.boss.admin.repository.MenuDao;

/**
 * 类名称：MenuServiceImpl <br>
 * 类描述：菜单服务实现 <br>
 * 
 * @author feng
 * @since 2011-7-5 下午04:35:42
 * @version 1.0.0
 */
public class MenuServiceImpl implements MenuService {

	private MenuDao menuDao;

	private Convert<MenuDTO, MenuEntity> menuConvert;


	public Long createMenu(MenuDTO menuDTO) {
		MenuEntity menuEntity = menuConvert.convert(menuDTO);
		menuDao.save(menuEntity);
		return menuEntity.getMenuId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.function.service.MenuService#deleteMenu(java
	 * .lang.Long)
	 */
	public void deleteMenu(Long menuID) {
		menuDao.delete(menuID);
	}

	public void updateMenu(MenuDTO menuDTO) {
		MenuEntity menuEntity = menuConvert.convert(menuDTO);
		menuDao.update(menuEntity);
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.function.service.MenuService#batchUpdateMenu(java.util.List)    
	 */
	public void batchUpdateMenu(List<MenuDTO> menuDTOs) {
		List<MenuEntity> entityList = new ArrayList<MenuEntity>();
		for(MenuDTO menuDTO : menuDTOs) {
			MenuEntity menuEntity = menuConvert.convert(menuDTO);
			entityList.add(menuEntity);
		}
		//TODO
//		menuDao.batchUpdate(entityList);
		for(MenuEntity menuEntity:entityList){
			menuDao.update(menuEntity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.function.service.MenuService#queryMenuByID
	 * (java.lang.Long)
	 */
	public MenuDTO queryMenuByID(Long menuID) {
		MenuEntity menuEntity = menuDao.selectById(menuID);
		MenuDTO menuDTO = menuConvert.convert(menuEntity);
		return menuDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zbjdl.boss.admin.function.service.MenuService#
	 * queryMenuByFunctionID(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<MenuDTO> query(MenuDTO queryDTO) {
		List<MenuDTO> menuDTOs = new ArrayList<MenuDTO>();
		MenuEntity queryEntity = menuConvert.convert(queryDTO);
		List<MenuEntity> menuEntities = menuDao.findList(queryEntity);
		for (MenuEntity menuEntity : menuEntities) {
			MenuDTO menuDTO = menuConvert.convert(menuEntity);
			menuDTOs.add(menuDTO);
		}
		return menuDTOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.function.service.MenuService#hasChildrenMenus
	 * (java.lang.Long)
	 */
	public boolean hasChildrenMenus(Long menuID) {
		int count = (Integer) menuDao.hasChildrenMenus(menuID);
		return count > 0;
	}

	@SuppressWarnings("unchecked")
	public List<MenuDTO> queryMenusByFunctionIds(List<Long> functionIds) {
		List<MenuDTO> menuDTOs = new ArrayList<MenuDTO>();
		if(functionIds.isEmpty()) {
			return menuDTOs;
		}
		List<MenuEntity> menus = menuDao.queryMenusByFunctionIds(functionIds);
		for (MenuEntity menuEntity : menus) {
			MenuDTO menuDTO = menuConvert.convert(menuEntity);
			menuDTOs.add(menuDTO);
		}
		return menuDTOs;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<MenuDTO> queryAllMenus() {
		List<MenuDTO> menuDTOs = Lists.newArrayList();
		List<MenuEntity> menus = menuDao.findAll();
		for (MenuEntity menuEntity : menus) {
			MenuDTO menuDTO = menuConvert.convert(menuEntity);
			menuDTOs.add(menuDTO);
		}
		return menuDTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuDTO> queryMenusByUserId(Long userId) {
		List<MenuDTO> menuDTOs = Lists.newArrayList();
		List<MenuEntity> menus = menuDao.queryMenusByUserId(userId);
		for (MenuEntity menuEntity : menus) {
			MenuDTO menuDTO = menuConvert.convert(menuEntity);
			menuDTOs.add(menuDTO);
		}
		return menuDTOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zbjdl.boss.admin.function.service.MenuService#
	 * queryMaxSequenceByLevelNum(java.lang.Integer)
	 */
	public Integer queryMaxSequenceByParentId(Long parentId) {
		Integer max = (Integer) menuDao.queryMaxSequenceByParentId(parentId);
		return max == null ? 0 : max;
	}

	/**
	 * menuDao
	 * 
	 * @return the menuDao
	 */
	public MenuDao getMenuDao() {
		return menuDao;
	}

	/**
	 * 描述： menuDao
	 * 
	 * @param menuDao
	 *            the menuDao to set
	 */
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	/**
	 * menuConvert
	 * 
	 * @return the menuConvert
	 */
	public Convert<MenuDTO, MenuEntity> getMenuConvert() {
		return menuConvert;
	}

	/**
	 * 描述：注入menuConvert
	 * 
	 * @param menuConvert
	 *            the menuConvert to set
	 */
	public void setMenuConvert(Convert<MenuDTO, MenuEntity> menuConvert) {
		this.menuConvert = menuConvert;
	}

}
