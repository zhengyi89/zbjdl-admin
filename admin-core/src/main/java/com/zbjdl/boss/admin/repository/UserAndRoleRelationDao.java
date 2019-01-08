/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zbjdl.boss.admin.authority.entity.UserAndRoleRelationEntity;
import com.zbjdl.common.respository.mybatis.GenericRepository;

/**    
 *    
 * 类名称：UserAndRoleRelationDao <br>    
 * 类描述：   用户和角色关系DAO<br>
 * @author：feng    
 * @since：2011-7-5 上午11:22:27 
 * @version:     
 *     
 */
@Repository
public interface UserAndRoleRelationDao extends GenericRepository{
	
	/**
	 * 
	 * 描述：    根据userId和roleId删除用户
	 * @param userAndRoleRelationEntity
	 */
	public void deleteUserAndRoleRelation(UserAndRoleRelationEntity userAndRoleRelationEntity);

	public UserAndRoleRelationEntity queryByRelation(UserAndRoleRelationEntity userAndRoleRelationEntity);

	public List<UserAndRoleRelationEntity> queryByUserId(Long userId);

	public List<UserAndRoleRelationEntity> queryByRoleId(Long roleId);
}
