/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.user.service.impl;

import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.repository.DepartmentDao;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.entity.DepartmentEntity;
import com.zbjdl.boss.admin.user.service.DepartmentService;

import java.util.List;

/**
 * 类名称：DepartmentServiceImpl <br>
 * 类描述：   <br>
 *
 * @author：feng
 * @since：2011-6-22 下午03:02:09
 * @version:
 */
public class DepartmentServiceImpl implements DepartmentService {

	private DepartmentDao departmentDao;

	private Convert<DepartmentDTO, DepartmentEntity> departmentConvert;
	/**
	 * 对应映射文件中ID为queryDeparmentByFunctionId的查询语句
	 */
	private final String QUERY_DEPARMENT_BY_FUNCTIONID = "queryDeparmentByFunctionId";

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.DepartmentService#createDepartment(com.zbjdl.boss.admin.user.dto.DepartmentDTO)    
	 */
	@Override
	public Long createDepartment(DepartmentDTO departmentDTO) {
		DepartmentEntity departmentEntity = new DepartmentEntity();
		departmentEntity = departmentConvert.convert(departmentDTO);
		departmentDao.save(departmentEntity);
		return departmentEntity.getId();
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.DepartmentService#updateDepartmentInfo(java.lang.Long, java.lang.String, java.lang.String)    
	 */
	@Override
	public boolean updateDepartmentInfo(DepartmentDTO departmentDTO) {
		DepartmentEntity departmentEntity = new DepartmentEntity();
		departmentEntity = departmentConvert.convert(departmentDTO);
		departmentDao.update(departmentEntity);
		return true;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.DepartmentService#deleteDepartment(java.lang.Long)    
	 */
	@Override
	public boolean deleteDepartment(Long departmentId) {
		departmentDao.delete(departmentId);
		return true;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.DepartmentService#validateDepartmentExistOrnot(com.zbjdl.boss.admin.user.dto.DepartmentDTO)    
	 */
	@Override
	public boolean validateDepartmentExistOrnot(DepartmentDTO departmentDTO) {
		DepartmentEntity departmentEntity = new DepartmentEntity();
		departmentEntity = departmentConvert.convert(departmentDTO);
		List<DepartmentEntity> list = departmentDao.findList(departmentEntity);
		if (list == null || list.size() == 0) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.DepartmentService#queryDepartmentById(java.lang.Long)    
	 */
	@Override
	public DepartmentDTO queryDepartmentById(Long departmentId) {
		return departmentConvert.convert((DepartmentEntity)departmentDao.selectById(departmentId));
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.DepartmentService#queryDepartments(com.zbjdl.boss.admin.user.dto.DepartmentDTO)    
	 */
	@Override
	public List<DepartmentDTO> queryDepartments(DepartmentDTO departmentDTO) {
		DepartmentEntity departmentEntity = departmentConvert.convert(departmentDTO);
		List<DepartmentEntity> list = departmentDao.findList(departmentEntity);
		return departmentConvert.convertToDtos(list);
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.user.service.DepartmentService#queryAllDepartments()    
	 */
	@Override
	public List<DepartmentDTO> queryAllDepartments() {
		List<DepartmentEntity> list = departmentDao.findAll();
		return departmentConvert.convertToDtos(list);
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.user.service.DepartmentService#queryDeparmentByFunctionId(java.lang.Long)    
	 */
	@Override
	public List<DepartmentDTO> queryDeparmentByFunctionId(Long functionId) {
		List<DepartmentEntity> list = departmentDao.queryDeparmentByFunctionId(functionId);
		return departmentConvert.convertToDtos(list);
	}

	/**
	 * 描述： 注入部门DAO
	 *
	 * @param departmentDao the departmentDao to set
	 */
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	/**
	 * 描述： 注入部门信息转换器
	 *
	 * @param departmentConvert the departmentConvert to set
	 */
	public void setDepartmentConvert(
			Convert<DepartmentDTO, DepartmentEntity> departmentConvert) {
		this.departmentConvert = departmentConvert;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.user.service.DepartmentService#queryUserByDeparmentId(java.lang.Long)    
	 */
	@SuppressWarnings({"unchecked"})
	public List<Long> queryUserByDeparmentId(Long departmentId) {
		List<Long> userIds = departmentDao.queryUserByDepartmentId(departmentId);
		return userIds;
	}

}
