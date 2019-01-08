package com.zbjdl.boss.admin.authority.dao.impl;
///**
// *
// * Copyright: Copyright (c)2018
// * Company: 八戒财云 
// *
// */
//package com.zbjdl.boss.admin.authority.dao.impl;
//
//import com.zbjdl.boss.admin.authority.entity.RoleEntity;
//import com.zbjdl.boss.admin.repository.RoleDao;
//import com.zbjdl.common.persistence.mybatis.GenericDaoDefault;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * 类名称：RoleDaoImpl <br>
// * 类描述：   <br>
// *
// * @author feng
// * @version 1.0.0
// * @since 2011-7-4 下午06:08:06
// */
//public class RoleDaoImpl extends GenericDaoDefault<RoleEntity> implements RoleDao {
//
//	@Override
//	public void migrateRole(Map<String, ?> map) {
//		getSqlSession().update(getStatementId("migrateRole"), map);
//	}
//
//	@Override
//	public List<RoleEntity> queryByRole(RoleEntity roleEntity) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<RoleEntity> queryRolesByUserId(Long userId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
