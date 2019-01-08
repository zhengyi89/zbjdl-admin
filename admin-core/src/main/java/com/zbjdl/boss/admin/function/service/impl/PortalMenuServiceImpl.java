/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.service.impl;

import com.zbjdl.boss.admin.function.convert.impl.PortalMenuConvert;
import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.dto.MenuKVDTO;
import com.zbjdl.boss.admin.function.entity.PortalMenu;
import com.zbjdl.boss.admin.function.service.PortalMenuService;
import com.zbjdl.boss.admin.repository.PortalMenuDao;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author feng, feng
 * @since 2013-5-31 上午10:35:37
 * @version 1.0.0
 */
public class PortalMenuServiceImpl implements PortalMenuService {

	@Autowired
	private PortalMenuDao portalMenuDao;

	@Autowired
	private PortalMenuConvert portalMenuConvert;

	@Override
	public Long addMenu(MenuDTO menuDTO) {
		PortalMenu portalMenu = portalMenuConvert.convert(menuDTO);
		portalMenuDao.save(portalMenu);
		return portalMenu.getMenuId();
	}

	@Override
	public void deleteMenu(Long menuID) {
        List<PortalMenu> childs = portalMenuDao.queryMenusByParentId(menuID);
        for (PortalMenu child : childs) {
            this.deleteMenu(child.getMenuId());
        }
		portalMenuDao.delete(menuID);
	}

	@Override
	public void updateMenu(MenuDTO menuDTO) {
		PortalMenu portalMenu = portalMenuConvert.convert(menuDTO);
        PortalMenu parentMenu = portalMenuDao.selectById(menuDTO.getParentId());
        if(null != parentMenu) {
            portalMenu.setLevelNum(parentMenu.getLevelNum() + 1);
        }
		portalMenuDao.update(portalMenu);
	}

	@Override
	public void batchUpdateMenu(List<MenuDTO> menuDTOs) {
		List<PortalMenu> entityList = new ArrayList<PortalMenu>();
		for (MenuDTO menuDTO : menuDTOs) {
			PortalMenu portalMenu = portalMenuConvert.convert(menuDTO);
			entityList.add(portalMenu);
		}
		portalMenuDao.batchUpdate(entityList);
	}

	@Override
	public MenuDTO findMenuByID(Long menuID) {
		PortalMenu portalMenu = portalMenuDao.selectById(menuID);
		return portalMenuConvert.convert(portalMenu);
	}

	@Override
	public List<MenuDTO> queryMenusByDepartment(Long departmentId) {
		List<PortalMenu> menus = portalMenuDao.queryMenusByDepartment(departmentId);
		return portalMenuConvert.convertToDtos(menus);
	}

    @Override
    public List<MenuKVDTO> queryMenusKVByDepartment(Long departmentId) {
        List<PortalMenu> menus = portalMenuDao.queryMenusKVByDepartment(departmentId);
        return portalMenuConvert.convertToKVs(menus);
    }

    @Override
	public List<MenuDTO> queryMenusByParentId(Long parentId) {
		List<PortalMenu> menus = portalMenuDao.queryMenusByParentId(parentId);
		return portalMenuConvert.convertToDtos(menus);
	}

	@Override
	public void batchAddMenu(List<MenuDTO> menus) {
		List<PortalMenu> portalMenus = portalMenuConvert.convertToEntities(menus);
		portalMenuDao.batchSave(portalMenus);
	}

	@Override
	public void batchDeleteMenu(List<Long> menuIds) {
		portalMenuDao.deleteMenus(menuIds);
	}

	@Override
	public List<MenuDTO> queryMenusByIdList(List<Long> menuIds) {
		List<PortalMenu> menus = portalMenuDao.queryMenusByIdList(menuIds);
		return portalMenuConvert.convertToDtos(menus);
	}

	@Override
	public void deleteDepartmentMenu(Long departmentId) {
		portalMenuDao.deleteDepartmentMenu(departmentId);
	}
}
