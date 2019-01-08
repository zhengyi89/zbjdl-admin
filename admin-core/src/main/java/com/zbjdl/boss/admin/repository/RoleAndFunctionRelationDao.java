/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.repository;

import com.zbjdl.boss.admin.authority.entity.RoleAndFunctionRelationEntity;
import com.zbjdl.common.respository.mybatis.GenericRepository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**    
 *    
 * 类名称：RoleAndFunctionRelationDao <br>    
 * 类描述：   角色和功能关系DAO<br>
 * @author：feng    
 * @since：2011-7-5 上午11:19:07 
 * @version:     
 *     
 */
@Repository
public interface RoleAndFunctionRelationDao extends GenericRepository{
	
	/**
	 * 
	 * 描述：    根据角色id和功能id删除功能关系，两个id不可为空
	 * @param roleId
	 * @param functionId
	 * @return
	 */
	public void deleteUserAndRoleRelation(Long roleId, Long functionId);

	public List<RoleAndFunctionRelationEntity> queryByRoleId(Long roleId);

	public List<RoleAndFunctionRelationEntity> queryByFunctionId(Long functionId);

	public RoleAndFunctionRelationEntity queryByRelation(RoleAndFunctionRelationEntity roleAndFunctionRelation);

	public void deleteRoleAndFunctionRelation(RoleAndFunctionRelationEntity roleAndFunctionRelationEntity);

	public void deleteRoleAndFunctionByCondition(Map<String, Long> param);

	public void deleteRoleAndFunctionByFunctionId(Long functionId);
}
