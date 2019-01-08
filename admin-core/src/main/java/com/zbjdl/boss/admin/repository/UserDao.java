/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.repository;

import com.zbjdl.boss.admin.user.entity.UserEntity;
import com.zbjdl.common.respository.mybatis.GenericRepository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 类名称：UserDao <br>
 * 类描述：   <br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-6-17 下午04:57:12
 */
@Repository
public interface UserDao extends GenericRepository {

	/**
	 * 更新用户主部门
	 *
	 * @param map 参数
	 */
	public void updateUserPrimaryDepartmentId(Map<String, ?> map);

	/**
	 * 批量更新用户主部门
	 *
	 * @param map 参数
	 */
	public void migrateUser(Map<String, ?> map);

	/**
	 * 批量指定部门的所有用户数据
	 *
	 * @param departmentId 部门编号
	 */
	public void batchDeleteUserByDepartmentId(Long departmentId);

	/**
	 * 批量指定部门的所有用户-角色关系
	 *
	 * @param departmentId 部门编号
	 */
	public void batchDeleteUserRelationByDepartmentId(Long departmentId);

	public UserEntity queryUserByLoginName(String loginName);

	public List<UserEntity> findList(UserEntity userEntity);

	public void updateUser(UserEntity userEntity);

	public UserEntity queryUserByMobile(String mobile);


}
