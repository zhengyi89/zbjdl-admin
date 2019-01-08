/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.convert.impl;

import java.util.ArrayList;
import java.util.List;

import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.dto.MenuKVDTO;
import com.zbjdl.boss.admin.function.entity.PortalMenu;

/**
 * 
 * @author：feng
 * @since：2013-5-30 下午03:37:29
 * @version:
 */
public class PortalMenuConvert implements Convert<MenuDTO, PortalMenu> {
	public PortalMenu convert(MenuDTO menuDTO) {
		if (menuDTO == null) {
			return null;
		}
		PortalMenu menuEntity = new PortalMenu();
		menuEntity.setMenuId(menuDTO.getMenuId());
		menuEntity.setMenuName(menuDTO.getMenuName());
		menuEntity.setParentId(menuDTO.getParentId());
		menuEntity.setSequence(menuDTO.getSequence());
		menuEntity.setLevelNum(menuDTO.getLevelNum());
		menuEntity.setFunctionId(menuDTO.getFunctionId());
		menuEntity.setFunctionUrl(menuDTO.getFunctionUrl());
		menuEntity.setDepartmentId(menuDTO.getDepartmentId());
		menuEntity.setIconName(menuDTO.getIconName());
		return menuEntity;
	}

	public MenuDTO convert(PortalMenu menuEntity) {
		if (menuEntity == null) {
			return null;
		}
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setMenuId(menuEntity.getMenuId());
		menuDTO.setMenuName(menuEntity.getMenuName());
		menuDTO.setParentId(menuEntity.getParentId());
		menuDTO.setSequence(menuEntity.getSequence());
		menuDTO.setLevelNum(menuEntity.getLevelNum());
		menuDTO.setFunctionId(menuEntity.getFunctionId());
		menuDTO.setFunctionUrl(menuEntity.getFunctionUrl());
		menuDTO.setDepartmentId(menuEntity.getDepartmentId());
		menuDTO.setIconName(menuEntity.getIconName());
		return menuDTO;
	}

    public MenuKVDTO convertKV(PortalMenu menuEntity) {
        if (menuEntity == null) {
            return null;
        }
        MenuKVDTO menuDTO = new MenuKVDTO();
        menuDTO.setMenuId(menuEntity.getMenuId());
        menuDTO.setMenuName(menuEntity.getMenuName());
        return menuDTO;
    }

	@Override
	public List<MenuDTO> convertToDtos(List<PortalMenu> es) {
		List<MenuDTO> menus = new ArrayList<MenuDTO>();
		if (es != null) {
			for (PortalMenu menu : es) {
				menus.add(this.convert(menu));
			}
		}
		return menus;
	}

    public List<MenuKVDTO> convertToKVs(List<PortalMenu> es) {
        List<MenuKVDTO> menus = new ArrayList<MenuKVDTO>();
        if (es != null) {
            for (PortalMenu menu : es) {
                menus.add(this.convertKV(menu));
            }
        }
        return menus;
    }

	@Override
	public List<PortalMenu> convertToEntities(List<MenuDTO> ts) {
		List<PortalMenu> menus = new ArrayList<PortalMenu>();
		if (ts != null) {
			for (MenuDTO menu : ts) {
				menus.add(this.convert(menu));
			}
		}
		return menus;
	}
}
