/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.authority.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zbjdl.boss.admin.authority.entity.RoleAndFunctionRelationEntity;
import com.zbjdl.boss.admin.authority.service.RoleAndFunctionRelationService;
import com.zbjdl.boss.admin.repository.RoleAndFunctionRelationDao;

/**
 *
 * 类名称：RoleAndFunctionRelationServiceImpl <br>
 * 类描述：   <br>
 * @author：feng
 * @since：2011-7-6 下午02:49:14
 * @version:
 *
 */
public class RoleAndFunctionRelationServiceImpl implements
		RoleAndFunctionRelationService {

	private RoleAndFunctionRelationDao roleAndFunctionRelationDao;

	/**
	 * 对应映射文件中deleteRoleAndFunctionByDepartmentAndFunction为sql语句
	 */
	private final String DELETE_ROLEANDFUNCTION_BY_CONDITION ="deleteRoleAndFunctionByCondition";

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.authority.service.RoleAndFunctionRelationService#createRoleAndFunctionRelation(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void createRoleAndFunctionRelation(Long roleId, Long functionId) {
		RoleAndFunctionRelationEntity roleAndFunctionRelationEntity = new RoleAndFunctionRelationEntity();
		roleAndFunctionRelationEntity.setRoleId(roleId);
		roleAndFunctionRelationEntity.setFunctionId(functionId);
		roleAndFunctionRelationDao.save(roleAndFunctionRelationEntity);
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.authority.service.RoleAndFunctionRelationService#queryByRoleId(java.lang.Long)
	 */
	@Override
	public List<RoleAndFunctionRelationEntity> queryByRoleId(Long roleId) {
//		List<RoleAndFunctionRelationEntity> list = roleAndFunctionRelationDao.query("queryByRoleId", roleId);
		List<RoleAndFunctionRelationEntity> list = roleAndFunctionRelationDao.queryByRoleId(roleId);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.authority.service.RoleAndFunctionRelationService#queryByFunctionId(java.lang.Long)
	 */
	@Override
	public List<RoleAndFunctionRelationEntity> queryByFunctionId(Long functionId) {
//		List<RoleAndFunctionRelationEntity> list = roleAndFunctionRelationDao.query("queryByFunctionId", functionId);
		List<RoleAndFunctionRelationEntity> list = roleAndFunctionRelationDao.queryByFunctionId(functionId);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.authority.service.RoleAndFunctionRelationService#queryByRoleIdAndFunctionId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public RoleAndFunctionRelationEntity queryByRoleIdAndFunctionId(
			Long roleId, Long functionId) {
		RoleAndFunctionRelationEntity roleAndFunctionRelation = new RoleAndFunctionRelationEntity();
		roleAndFunctionRelation.setFunctionId(functionId);
		roleAndFunctionRelation.setRoleId(roleId);
		RoleAndFunctionRelationEntity roleAndFunctionRelationEntity = (RoleAndFunctionRelationEntity) roleAndFunctionRelationDao.queryByRelation( roleAndFunctionRelation);
		return roleAndFunctionRelationEntity;
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.authority.service.RoleAndFunctionRelationService#deleteRoleAndFunctionRelation(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void deleteRoleAndFunctionRelation(Long roleId, Long functionId) {
		RoleAndFunctionRelationEntity roleAndFunctionRelationEntity = new RoleAndFunctionRelationEntity();
		roleAndFunctionRelationEntity.setRoleId(roleId);
		roleAndFunctionRelationEntity.setFunctionId(functionId);
		roleAndFunctionRelationDao.deleteRoleAndFunctionRelation(roleAndFunctionRelationEntity);
	}



	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.authority.service.RoleAndFunctionRelationService#saveAll(java.util.List)
	 */
	@Override
	public void saveAll(
			List<RoleAndFunctionRelationEntity> roleAndFunctionRelationEntityList) {
//		roleAndFunctionRelationDao.batchInsert(roleAndFunctionRelationEntityList);
		// TODO BATCH
		if(roleAndFunctionRelationEntityList!=null && !roleAndFunctionRelationEntityList.isEmpty()){
			for(RoleAndFunctionRelationEntity roleAndFunctionRelationEntity:roleAndFunctionRelationEntityList){
				roleAndFunctionRelationDao.save(roleAndFunctionRelationEntity);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.authority.service.RoleAndFunctionRelationService#deleteAll(java.util.List)
	 */
	@Override
	public void deleteAll(
			List<RoleAndFunctionRelationEntity> roleAndFunctionRelationEntityList) {
//		roleAndFunctionRelationDao.batchDelete(roleAndFunctionRelationEntityList);
		// TODO BATCH
		if(roleAndFunctionRelationEntityList!=null && !roleAndFunctionRelationEntityList.isEmpty()){
			for(RoleAndFunctionRelationEntity roleAndFunctionRelationEntity:roleAndFunctionRelationEntityList){
				roleAndFunctionRelationDao.delete(roleAndFunctionRelationEntity.getId());
			}
		}
	}



	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.authority.service.RoleAndFunctionRelationService#deleteRoleAndFunctionByCondition(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public void deleteRoleAndFunctionByCondition(Long departmentId,
			Long functionId, Long roleId) {
		Map<String, Long> param = new HashMap<String, Long>();
		param.put("functionId", functionId);
		param.put("departmentId", departmentId);
		param.put("roleId", roleId);
		roleAndFunctionRelationDao.deleteRoleAndFunctionByCondition(param);
	}

	/**
	 * roleAndFunctionRelationDao
	 *
	 * @return  the roleAndFunctionRelationDao
	 */

	public RoleAndFunctionRelationDao getRoleAndFunctionRelationDao() {
		return roleAndFunctionRelationDao;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * @param roleAndFunctionRelationDao the roleAndFunctionRelationDao to set
	 */
	public void setRoleAndFunctionRelationDao(
			RoleAndFunctionRelationDao roleAndFunctionRelationDao) {
		this.roleAndFunctionRelationDao = roleAndFunctionRelationDao;
	}

	@Override
	public void deleteRoleAndFunctionByFunctionId(Long functionId) {
		roleAndFunctionRelationDao.deleteRoleAndFunctionByFunctionId(functionId);
	}

}
