/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.authority.service;

import java.util.List;

import com.zbjdl.boss.admin.authority.entity.RoleAndFunctionRelationEntity;

/**
 *
 * 类名称：RoleAndFunctionRelation <br>
 * 类描述：   角色和功能关系<br>
 * @author：feng
 * @since：2011-7-6 下午02:02:43
 * @version:
 *
 */
public interface RoleAndFunctionRelationService {

	/**
	 *
	 * 描述：创建角色和功能关系
	 * @param roleId 角色ID
	 * @param functionId 功能ID
	 * @return
	 */
	public void createRoleAndFunctionRelation(Long roleId,Long functionId);

	/**
	 *
	 * 描述：    根据角色Id查找角色功能关系
	 * @param roleId 角色ID
	 * @return 角色功能关系列表
	 */
	public List<RoleAndFunctionRelationEntity> queryByRoleId(Long roleId);

	/**
	 *
	 * 描述：    根据功能id查找角色功能关系
	 * @param functionId 功能id
	 * @return
	 */
	public List<RoleAndFunctionRelationEntity> queryByFunctionId(Long functionId);

	/**
	 *
	 * 描述：    根据角色id和功能id查询角色功能关系
	 * @param roleId
	 * @param functionId
	 * @return
	 */
	public RoleAndFunctionRelationEntity queryByRoleIdAndFunctionId(Long roleId,Long functionId);
	/**
	 *
	 * 描述：    根据角色id和功能id删除角色功能关系，id都不可为空
	 * @param roleId 角色ID
	 * @param functionId 功能ID
	 * @return
	 */
	public void deleteRoleAndFunctionRelation(Long roleId, Long functionId);

	/**
	 * 描述：保存角色功能关系
	 *
	 * @param roleAndFunctionRelationEntityList
	 */
	public void saveAll(List<RoleAndFunctionRelationEntity>roleAndFunctionRelationEntityList);

	/**
	 *
	 * 描述：删除角色功能关系
	 *
	 * @param roleAndFunctionRelationEntityList
	 */
	public void deleteAll(List<RoleAndFunctionRelationEntity>roleAndFunctionRelationEntityList);
	/**
	 * 描述：根据部门、功能、角色等条件，删除角色功能关系
	 * @param departmentId：部门ID
	 * @param functionId：功能ID
	 * @param roleId：角色ID
	 */
	public void deleteRoleAndFunctionByCondition(Long departmentId,Long functionId,Long roleId);
	/**
	 * 删除角色功能关系
	 * @param functionId:功能ID
	 */
	public void deleteRoleAndFunctionByFunctionId(Long functionId);
}
