/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.function.service.impl;

import java.util.List;

import com.zbjdl.boss.admin.function.entity.DepartmentAndFunctionRelationEntity;
import com.zbjdl.boss.admin.function.service.DepartmentAndFunctionRelationService;
import com.zbjdl.boss.admin.repository.DepartmentAndFunctionRelationDao;

/**
 *
 * 类名称：DepartmentAndFunctionRelationServiceImpl <br>
 * 类描述：   <br>
 * @author：feng
 * @since：2011-7-15 下午01:33:23
 * @version:
 *
 */
public class DepartmentAndFunctionRelationServiceImpl implements
		DepartmentAndFunctionRelationService {

	/** 对应映射文件中ID为deleteDepartmentAndFunctionByDepartmentId的Sql语句 */
	private final String DELETE_DEPARTMENTANDFUNCTION_BY_DEPARTMENTID="deleteDepartmentAndFunctionByDepartmentId";

	private DepartmentAndFunctionRelationDao departmentAndFunctionRelationDao;

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.function.service.DepartmentAndFunctionRelationService#createDepartmentAndFunctionRelation(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void createDepartmentAndFunctionRelation(Long departmentId,
			Long functionId) {
		DepartmentAndFunctionRelationEntity entity = new DepartmentAndFunctionRelationEntity();
		entity.setDepartmentId(departmentId);
		entity.setFunctionId(functionId);
		departmentAndFunctionRelationDao.save(entity);

	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.function.service.DepartmentAndFunctionRelationService#queryByDeptId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DepartmentAndFunctionRelationEntity> queryByDeptId(
			Long departmentId) {
		List<DepartmentAndFunctionRelationEntity> list = departmentAndFunctionRelationDao.queryFunctionByDepartmentId(departmentId);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.function.service.DepartmentAndFunctionRelationService#queryByFunctionId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DepartmentAndFunctionRelationEntity> queryByFunctionId(
			Long functionId) {
		List<DepartmentAndFunctionRelationEntity> list = departmentAndFunctionRelationDao.queryDepartmentByFunctionId(functionId);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.function.service.DepartmentAndFunctionRelationService#deleteDepartmentAndFunctionRelation(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void deleteDepartmentAndFunctionRelation(Long departmentId,
			Long functionId) {
		DepartmentAndFunctionRelationEntity entity = new DepartmentAndFunctionRelationEntity();
		entity.setDepartmentId(departmentId);
		entity.setFunctionId(functionId);
		departmentAndFunctionRelationDao.deleteDepartmentAndFunctionRelation(entity);
	}



	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.function.service.DepartmentAndFunctionRelationService#saveAll(java.util.List)
	 */
	@Override
	public void saveAll(
			List<DepartmentAndFunctionRelationEntity> departmentAndFunctionRelationEntityList) {
//		departmentAndFunctionRelationDao.batchInsert(departmentAndFunctionRelationEntityList);
		// TODO BATCH
		if(departmentAndFunctionRelationEntityList!=null && !departmentAndFunctionRelationEntityList.isEmpty()){
			for(DepartmentAndFunctionRelationEntity departmentAndFunctionRelationEntity:departmentAndFunctionRelationEntityList){
				departmentAndFunctionRelationDao.save(departmentAndFunctionRelationEntity);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.function.service.DepartmentAndFunctionRelationService#deleteAll(java.util.List)
	 */
	@Override
	public void deleteAll(
			List<DepartmentAndFunctionRelationEntity> departmentAndFunctionRelationEntityList) {
//		departmentAndFunctionRelationDao.batchDelete(departmentAndFunctionRelationEntityList);
		// TODO BATCH
		if(departmentAndFunctionRelationEntityList!=null && !departmentAndFunctionRelationEntityList.isEmpty()){
			for(DepartmentAndFunctionRelationEntity departmentAndFunctionRelationEntity:departmentAndFunctionRelationEntityList){
				departmentAndFunctionRelationDao.delete(departmentAndFunctionRelationEntity.getId());
			}
		}
	}


	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.function.service.DepartmentAndFunctionRelationService#deleteDepartmentAndFunctionByDepartmentId(java.lang.Long)
	 */
	@Override
	public void deleteDepartmentAndFunctionByDepartmentId(Long departmentId) {
		departmentAndFunctionRelationDao.deleteDepartmentAndFunctionByDepartmentId(departmentId);
	}

	public void deleteDepartmentAndFunctionRelation(Long relationId) {
		departmentAndFunctionRelationDao.delete(relationId);
	}

	/**
	 * departmentAndFunctionRelationDao
	 *
	 * @return  the departmentAndFunctionRelationDao
	 */

	public DepartmentAndFunctionRelationDao getDepartmentAndFunctionRelationDao() {
		return departmentAndFunctionRelationDao;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * @param departmentAndFunctionRelationDao the departmentAndFunctionRelationDao to set
	 */
	public void setDepartmentAndFunctionRelationDao(
			DepartmentAndFunctionRelationDao departmentAndFunctionRelationDao) {
		this.departmentAndFunctionRelationDao = departmentAndFunctionRelationDao;
	}

	@Override
	public void deleteDepartmentAndFunctionByFunctionId(Long functionId) {
		departmentAndFunctionRelationDao.deleteDepartmentAndFunctionByFunctionId(functionId);
	}



}
