/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.repository;

import com.zbjdl.boss.admin.authority.entity.RoleEntity;
import com.zbjdl.common.respository.mybatis.GenericRepository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**    
 *    
 * 类名称：RoleDao <br>    
 * 类描述：   角色DAO<br>
 * @author feng
 * @since 2011-7-4 下午02:42:15
 * @version 1.0.0
 *     
 */
@Repository
public interface RoleDao extends GenericRepository{

	/**
	 * 批量更新角色部门 Id
	 *
	 * @param map    原部门编号, 拟迁移部门编号
	 * @return 是否成功
	 * @author feng
	 * @since 2013-06-27 16:10:56
	 */
	public void migrateRole(Map<String, ?> map);

	public List<RoleEntity> queryByRole(RoleEntity roleEntity);

	public List<RoleEntity> queryRolesByUserId(Long userId);

	public void deleteRoleRelationByDepartmentId(Long departmentId);

	public void deleteRoleByDepartmentId(Long departmentId);

}
