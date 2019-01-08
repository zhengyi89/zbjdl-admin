/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.convert.impl;

import java.util.ArrayList;
import java.util.List;

import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.entity.UserEntity;
import com.zbjdl.boss.admin.utils.StringBooleanUtil;

/**    
 *    
 * 类名称：UserConvert <br>    
 * 类描述：用户信息DTO转换器<br>
 * @author：feng    
 * @since：2011-6-22 下午04:28:58 
 * @version:     
 *     
 */
public class UserConvert implements Convert<UserDTO, UserEntity> {
	
	/**
	 * 
	 * 描述：    用户Entity转为用户DTO
	 * @param userEntity
	 * @return
	 */
	public UserDTO convert(UserEntity userEntity) {
		if (userEntity == null) {
			return null;
		}
		UserDTO userDTO = new UserDTO();
		//TODO 如何转部门信息？
		userDTO.setUserName(userEntity.getUserName());
		userDTO.setIsAdmin(StringBooleanUtil.stringToBoolean(userEntity.getIsAdmin()));
		userDTO.setIsSuperAdmin(StringBooleanUtil.stringToBoolean(userEntity.getIsSuperAdmin()));
		userDTO.setLoginName(userEntity.getLoginName());
		userDTO.setPassword(userEntity.getPassword());
		userDTO.setUserId(userEntity.getUserId());
		userDTO.setUserstatus(userEntity.getUserStatus());
		userDTO.setPrimaryDepartmentId(userEntity.getPrimaryDepartmentId());
		userDTO.setCreateTime(userEntity.getCreateTime());
		userDTO.setEmail(userEntity.getEmail());
		userDTO.setMobile(userEntity.getMobile());
		userDTO.setMigration(StringBooleanUtil.stringToBoolean(userEntity.getMigration()));
		userDTO.setPwdShowNotice(userEntity.getPwdShowNotice());
		userDTO.setPwdLastModifiedTime(userEntity.getPwdLastModifiedTime());
		return userDTO;
	}
	
	/**
	 * 
	 * 描述：    用户DTO转为用户Entity
	 * @param userDTO
	 * @return
	 */
	public UserEntity convert(UserDTO userDTO) {
		if (userDTO == null) {
			return null;
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setUserName(userDTO.getUserName());
		userEntity.setIsAdmin(StringBooleanUtil.booleanToString(userDTO.getIsAdmin()));
		userEntity.setIsSuperAdmin(StringBooleanUtil.booleanToString(userDTO.getIsSuperAdmin()));
		userEntity.setLoginName(userDTO.getLoginName());
		userEntity.setPassword(userDTO.getPassword());
		userEntity.setUserId(userDTO.getUserId());
		userEntity.setUserStatus(userDTO.getUserstatus());
		userEntity.setPrimaryDepartmentId(userDTO.getPrimaryDepartmentId());
		userEntity.setCreateTime(userDTO.getCreateTime());
		userEntity.setEmail(userDTO.getEmail());
		userEntity.setMobile(userDTO.getMobile());
		userEntity.setMigration(StringBooleanUtil.booleanToString(userDTO.isMigration()));
		userEntity.setPwdShowNotice(userDTO.getPwdShowNotice());
		userEntity.setPwdLastModifiedTime(userDTO.getPwdLastModifiedTime());
		return userEntity;
	}

	@Override
	public List<UserDTO> convertToDtos(List<UserEntity> es) {
		List<UserDTO> list = new ArrayList<UserDTO>();
		if(es != null && !es.isEmpty()){
			for (UserEntity userEntity : es) {
				UserDTO userDto = convert(userEntity);
				list.add(userDto);
			}
		}
		return list;
	}

	@Override
	public List<UserEntity> convertToEntities(List<UserDTO> ts) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
