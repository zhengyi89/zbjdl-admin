package com.zbjdl.boss.admin.user.dao.impl;
///**    
// * 
// * Copyright: Copyright (c)2018
// * Company: 八戒财云 
// *    
// */
//package com.zbjdl.boss.admin.user.dao.impl;
//
//import com.zbjdl.boss.admin.repository.UserDao;
//import com.zbjdl.boss.admin.user.entity.UserEntity;
//import com.zbjdl.common.persistence.mybatis.GenericDaoDefault;
//
//import java.util.List;
//import java.util.Map;
//
///**    
// *    
// * 类名称：UserDaoImpl <br>    
// * 类描述：   <br>
// * @author feng
// * @since 2011-6-22 下午02:45:58
// * @version 1.0.0
// *     
// */
//public class UserDaoImpl extends GenericDaoDefault<UserEntity> implements
//		UserDao {
//
//	@Override
//	public void updateUserPrimaryDepartmentId(Map<String, ?> map) {
//		getSqlSession().update(getStatementId("updateUserPrimaryDepartmentId"), map);
//	}
//
//	@Override
//	public void migrateUser(Map<String, ?> map) {
//		getSqlSession().update(getStatementId("migrateUser"), map);
//	}
//
//	@Override
//	public void batchDeleteUserByDepartmentId(Long departmentId) {
//		getSqlSession().delete(getStatementId("batchDeleteUserByDepartmentId"), departmentId);
//	}
//
//	@Override
//	public void batchDeleteUserRelationByDepartmentId(Long departmentId) {
//		getSqlSession().delete(getStatementId("batchDeleteUserRelationByDepartmentId"), departmentId);
//	}
//
//	@Override
//	public UserEntity queryUserByLoginName(String loginName) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<UserEntity> findList(UserEntity userEntity) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//}
