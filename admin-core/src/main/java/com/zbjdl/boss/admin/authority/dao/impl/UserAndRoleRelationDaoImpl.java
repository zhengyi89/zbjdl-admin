package com.zbjdl.boss.admin.authority.dao.impl;
///**    
// * 
// * Copyright: Copyright (c)2018
// * Company: 八戒财云 
// *    
// */
//package com.zbjdl.boss.admin.authority.dao.impl;
//
//import com.zbjdl.boss.admin.authority.entity.UserAndRoleRelationEntity;
//import com.zbjdl.boss.admin.repository.UserAndRoleRelationDao;
//import com.zbjdl.common.persistence.mybatis.GenericDaoDefault;
//
///**
// * 
// * 类名称：UserAndRoleRelationDaoImpl <br>
// * 类描述： <br>
// * 
// * @author：feng
// * @since：2011-7-5 上午11:23:41
// * @version:
// * 
// */
//public abstract class UserAndRoleRelationDaoImpl extends
//		GenericDaoDefault<UserAndRoleRelationEntity> implements
//		UserAndRoleRelationDao {
//	
//	public void deleteUserAndRoleRelation(UserAndRoleRelationEntity userAndRoleRelationEntity){
//		
//		if(userAndRoleRelationEntity.getRoleId() != null && userAndRoleRelationEntity.getUserId() != null){
//			getSqlSession().delete(getStatementId("deleteUserAndRoleRelation"),userAndRoleRelationEntity);
//		}
//	}
//
//	@Override
//	public UserAndRoleRelationEntity queryByRelation(UserAndRoleRelationEntity userAndRoleRelationEntity) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
