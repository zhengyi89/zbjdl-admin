/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zbjdl.boss.admin.user.entity.DepartmentEntity;
import com.zbjdl.common.respository.mybatis.GenericRepository;

/**
 * 类名称：DepartmentDao <br>
 * 类描述：   <br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-6-17 下午03:01:35
 */
@Repository
public interface DepartmentDao extends GenericRepository {

	List<DepartmentEntity> findList(DepartmentEntity departmentEntity);

	List<DepartmentEntity> queryDeparmentByFunctionId(Long functionId);

	List<Long> queryUserByDepartmentId(Long departmentId);

}