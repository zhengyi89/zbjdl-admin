package com.zbjdl.boss.admin.workitem.dao.impl;
///**
// *
// * Copyright: Copyright (c)2018
// * Company: 八戒财云
// *
// */
//package com.zbjdl.boss.admin.workitem.dao.impl;
//
//import com.zbjdl.boss.admin.repository.WorkItemTemplateDao;
//import com.zbjdl.boss.admin.workitem.entity.WorkItemTemplateEntity;
//import com.zbjdl.common.persistence.mybatis.GenericDaoDefault;
//
///**
// *
// * @author：feng
// * @since：2012-6-8 上午09:57:59
// * @version:
// */
//public class WorkItemTemplateDaoImpl extends
//		GenericDaoDefault<WorkItemTemplateEntity> implements
//		WorkItemTemplateDao {
//
//	@Override
//	public WorkItemTemplateEntity queryTemplateByFunctionId(Long functionId) {
//		return (WorkItemTemplateEntity) this.queryOne("queryByFunctionId",
//				functionId);
//	}
//
//	@Override
//	public void deleteByTemplateId(Long id) {
//		super.delete("deleteByTemplateId", id);
//	}
//
//}
