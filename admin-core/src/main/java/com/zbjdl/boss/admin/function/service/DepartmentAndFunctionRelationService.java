/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.function.service;

import java.util.List;

import com.zbjdl.boss.admin.function.entity.DepartmentAndFunctionRelationEntity;

/**
 *
 * 类名称：DepartmentAndFunctionRelationService <br>
 * 类描述： <br>
 *
 * @author：feng
 * @since：2011-7-15 下午01:21:05
 * @version:
 *
 */
public interface DepartmentAndFunctionRelationService {

	/**
	 *
	 * 描述： 创建部门功能关系
	 *
	 * @param departmentId
	 * @param functionId
	 */

	public void createDepartmentAndFunctionRelation(Long departmentId,
			Long functionId);

	/**
	 *
	 * 描述： 根据部门id查询部门与功能关系
	 *
	 * @param departmentId
	 * @return 部门功能关系列表
	 */

	public List<DepartmentAndFunctionRelationEntity> queryByDeptId(
			Long departmentId);

	/**
	 *
	 * 描述： 根据功能id查询部门功能关系
	 *
	 * @param functionId
	 * @return 关系列表
	 */

	public List<DepartmentAndFunctionRelationEntity> queryByFunctionId(
			Long functionId);

	/**
	 *
	 * 描述： 根据部门id和功能id 删除部门功能关系，id不为空
	 *
	 * @param departmentId
	 * @param functionId
	 */

	public void deleteDepartmentAndFunctionRelation(Long departmentId,
			Long functionId);

	/**
	 *
	 * 描述： 根据主键删除部门功能关系
	 *
	 * @param relationId
	 *            主键
	 */
	public void deleteDepartmentAndFunctionRelation(Long relationId);

	/**
	 * 描述：保存部门功能关系
	 *
	 * @param departmentAndFunctionRelationEntityList
	 */
	public void saveAll(List<DepartmentAndFunctionRelationEntity>departmentAndFunctionRelationEntityList);

	/**
	 *
	 * 描述：删除部门功能关系
	 *
	 * @param roleAndFunctionRelationEntityList
	 */
	public void deleteAll(List<DepartmentAndFunctionRelationEntity>departmentAndFunctionRelationEntityList);
	/**
	 * 根据部门拥有的全部功能
	 * @param departmentId：部门ID
	 */
	public void deleteDepartmentAndFunctionByDepartmentId(Long departmentId);
	/**
	 * 删除部门功能关系
	 * @param functionId:功能ID
	 */
	public void deleteDepartmentAndFunctionByFunctionId(Long functionId);

}
