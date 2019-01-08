/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zbjdl.boss.admin.function.entity.FunctionEntity;
import com.zbjdl.common.respository.mybatis.GenericRepository;

/**
 *
 * 类名称：FunctionDao <br>
 * 类描述： 功能DAO接口<br>
 *
 * @author：feng
 * @since：2011-7-4 下午01:33:28
 * @version:
 *
 */
@Repository
public interface FunctionDao extends GenericRepository {

	/**
	 *
	 * 描述：    根据所复核的功能ID和功能类型删除功能
	 * @param map	存放参数的map对象
	 */
	public void deleteByCheckFunctionIdAndType(Map<String,?> map);

	public List<FunctionEntity> queryFunctionByUri(String uri);

	public Integer isPreFunction(Long functionID);

	public List queryFuntionIdByDepartmentId(Map<String, Long> map);

	public List<FunctionEntity> queryByFunction(FunctionEntity queryEntity);

	public List<FunctionEntity> queryFuntionByUserId(Map<String, Long> map);

	public List<FunctionEntity> queryFuntionByDepartmentIdAndFunctionId(Map<String, Long> map);

	public List<FunctionEntity> queryFuntionByDepartmentId(Map<String, Long> map);

	public List<FunctionEntity> queryFuntionByRoleId(Map<String, Long> map);

	public List<FunctionEntity> queryFuntionByCheckFunctionId(Map<String, Long> map);

	public void migrateFunction(Map<String, Long> map);

	public void insertWithNullFunctionId(FunctionEntity functionEntity);

	public void deleteByDepartmentId(Long departmentId);

	public void deleteRelationByDepartmentId(Long departmentId);

	public void deleteFuntionByCheckFunctionId(Long checkFunctionId);
}
