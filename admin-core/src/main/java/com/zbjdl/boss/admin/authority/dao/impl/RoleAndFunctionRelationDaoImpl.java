package com.zbjdl.boss.admin.authority.dao.impl;
///**    
// * 
// * Copyright: Copyright (c)2018
// * Company: 八戒财云 
// *    
// */
//package com.zbjdl.boss.admin.authority.dao.impl;
//
//import com.zbjdl.common.persistence.mybatis.GenericDaoDefault;
//
//import java.util.List;
//
//import com.zbjdl.boss.admin.authority.entity.RoleAndFunctionRelationEntity;
//import com.zbjdl.boss.admin.repository.RoleAndFunctionRelationDao;
//
///**
// * 
// * 类名称：RoleAndFunctionRelationDaoImpl <br>
// * 类描述： <br>
// * 
// * @author：feng
// * @since：2011-7-5 上午11:20:41
// * @version:
// * 
// */
//public class RoleAndFunctionRelationDaoImpl extends
//		GenericDaoDefault<RoleAndFunctionRelationEntity> implements
//		RoleAndFunctionRelationDao {
//
//	/* (non-Javadoc)    
//	 * @see com.zbjdl.boss.admin.authority.dao.RoleAndFunctionRelationDao#deleteUserAndRoleRelation(java.lang.Long, java.lang.Long)    
//	 */
//	@Override
//	public void deleteUserAndRoleRelation(Long roleId, Long functionId) {
//		RoleAndFunctionRelationEntity roleAndFunctionRelationEntity = new RoleAndFunctionRelationEntity();
//		roleAndFunctionRelationEntity.setRoleId(roleId);
//		roleAndFunctionRelationEntity.setFunctionId(functionId);
//		if(roleId != null && functionId != null){
//			getSqlSession().delete(getStatementId("deleteRoleAndFunctionRelation"),roleAndFunctionRelationEntity);
//		}
//	}
//
//	@Override
//	public List<RoleAndFunctionRelationEntity> queryByRoleId(Long roleId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<RoleAndFunctionRelationEntity> queryByFunctionId(Long functionId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public RoleAndFunctionRelationEntity queryByRelation(RoleAndFunctionRelationEntity roleAndFunctionRelation) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
