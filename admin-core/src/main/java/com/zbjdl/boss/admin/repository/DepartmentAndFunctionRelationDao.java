/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zbjdl.boss.admin.function.entity.DepartmentAndFunctionRelationEntity;
import com.zbjdl.common.respository.mybatis.GenericRepository;

/**
 * 
 * 类名称：DepartmentAndFunctionRelationDao <br>
 * 类描述： <br>
 * 
 * @author：feng
 * @since：2011-7-15 下午01:13:15
 * @version:
 * 
 */
@Repository
public interface DepartmentAndFunctionRelationDao extends GenericRepository {

	List<DepartmentAndFunctionRelationEntity> queryFunctionByDepartmentId(Long departmentId);

	List<DepartmentAndFunctionRelationEntity> queryDepartmentByFunctionId(Long functionId);

	void deleteDepartmentAndFunctionRelation(DepartmentAndFunctionRelationEntity entity);

	void deleteDepartmentAndFunctionByDepartmentId(Long departmentId);

	void deleteDepartmentAndFunctionByFunctionId(Long functionId);

}
