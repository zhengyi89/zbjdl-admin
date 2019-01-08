/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.authority.service;

import com.zbjdl.boss.admin.authority.entity.UserAndRoleRelationEntity;

import java.util.List;

/**    
 *    
 * 类名称：UserAndRoleRelationService <br>    
 * 类描述：   <br>
 * @author：feng    
 * @since：2011-7-6 上午10:36:05 
 * @version:     
 *     
 */
public interface UserAndRoleRelationService {
	
	/**
	 * 
	 * 描述：    创建用户与角色关系
	 * @param userId 用户ID
	 * @param roleId 角色ID
	 * @return 创建结果
	 */
	public void createUserAndRoleRelation(Long userId, Long roleId);
	
	/**
	 * 
	 * 描述：    根据用户ID查找用户角色关系
	 * @param userId 用户ID
	 * @return 用户角色关系
	 */
	public List<UserAndRoleRelationEntity> queryByUserId(Long userId);
	
	/**
	 * 
	 * 描述：    根据角色ID查找用户角色关系
	 * @param roleId 角色ID
	 * @return 用户角色关系
	 */
	public List<UserAndRoleRelationEntity> queryByRoleId(Long roleId);
	
	/**
	 * 
	 * 描述：    根据用户id和角色id查询用户角色关系
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public UserAndRoleRelationEntity queryByUserIdAndRoleId(Long userId,Long roleId);
	/**
	 * 
	 * 描述：    删除用户角色关系
	 * @param userId
	 * @param roleId
	 * @return 删除结果
	 */
	public void deleteUserAndRoleRelation(Long userId, Long roleId);
	
	/**
	 * 描述：保存人员角色关系
	 * @param userAndRoleRelationEntityList
	 */
	public void saveAll(List<UserAndRoleRelationEntity>userAndRoleRelationEntityList);
	/**
	 * 描述：删除人员角色关系
	 * @param userAndRoleRelationEntityList
	 */
	public void deleteAll(List<UserAndRoleRelationEntity>userAndRoleRelationEntityList);
	
}
