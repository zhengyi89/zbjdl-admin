/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.authority.convert.impl;

import java.util.ArrayList;
import java.util.List;

import com.zbjdl.boss.admin.authority.entity.RoleEntity;
import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.role.dto.RoleDTO;

/**    
 *    
 * 类名称：RoleConvert <br>    
 * 类描述：   RoleDTO和RoleEntity转换器<br>
 * @author：feng    
 * @since：2011-7-5 下午02:24:41 
 * @version:     
 *     
 */
public class RoleConvert implements Convert<RoleDTO, RoleEntity>{

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.convert.Convert#convert(com.zbjdl.boss.admin.dto.basic.BaseDTO)    
	 */
	@Override
	public RoleEntity convert(RoleDTO roleDTO) {
		if(roleDTO == null){
			return null;
		}
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setRoleId(roleDTO.getRoleId());
		roleEntity.setRoleName(roleDTO.getRoleName());
		roleEntity.setRoleStatus(roleDTO.getRoleStatus());
		roleEntity.setDescription(roleDTO.getDescription());
		roleEntity.setRoleType(roleDTO.getRoleType());
		roleEntity.setDepartmentId(roleDTO.getDepartmentId());
		return roleEntity;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.convert.Convert#convert(com.zbjdl.common.persistence.Entity)    
	 */
	@Override
	public RoleDTO convert(RoleEntity roleEntity) {
		if(roleEntity == null){
			return null;
		}
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setRoleId(roleEntity.getRoleId());
		roleDTO.setRoleName(roleEntity.getRoleName());
		roleDTO.setRoleStatus(roleEntity.getRoleStatus());
		roleDTO.setDescription(roleEntity.getDescription());
		roleDTO.setRoleType(roleEntity.getRoleType());
		roleDTO.setDepartmentId(roleEntity.getDepartmentId());
		return roleDTO;
	}

	@Override
	public List<RoleDTO> convertToDtos(List<RoleEntity> es) {
		List<RoleDTO> roleDTOList = new ArrayList<RoleDTO>();
		if (es != null && !es.isEmpty()) {
			for (RoleEntity roleEntity : es) {
				roleDTOList.add(convert(roleEntity));
			}
		}
		return roleDTOList;
	}

	@Override
	public List<RoleEntity> convertToEntities(List<RoleDTO> ts) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
