/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.convert.impl;

import java.util.List;

import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.entity.MenuEntity;

/**
 * 
 * 类名称：MenuConvert <br>
 * 类描述：MenuDTO和MenuEntity转换器 <br>
 * 
 * @author：feng
 * @since：2011-7-5 下午06:20:27
 * @version:
 * 
 */
public class MenuConvert implements Convert<MenuDTO, MenuEntity> {


	public MenuEntity convert(MenuDTO menuDTO) {
		if (menuDTO == null) {
			return null;
		}
		MenuEntity menuEntity = new MenuEntity();
		menuEntity.setMenuId(menuDTO.getMenuId());
		menuEntity.setMenuName(menuDTO.getMenuName());
		menuEntity.setParentId(menuDTO.getParentId());
		menuEntity.setSequence(menuDTO.getSequence());
		menuEntity.setLevelNum(menuDTO.getLevelNum());
		menuEntity.setFunctionId(menuDTO.getFunctionId());
		menuEntity.setIconName(menuDTO.getIconName());
		return menuEntity;
	}


	public MenuDTO convert(MenuEntity menuEntity) {
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
		menuDTO.setIconName(menuEntity.getIconName());
		return menuDTO;
	}

	@Override
	public List<MenuDTO> convertToDtos(List<MenuEntity> es) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MenuEntity> convertToEntities(List<MenuDTO> ts) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
