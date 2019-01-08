package com.zbjdl.boss.admin.workitem.dao.impl;
///**    
// * 
// * Copyright: Copyright (c)2018
// * Company: 八戒财云 
// *    
// */
//package com.zbjdl.boss.admin.workitem.dao.impl;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.zbjdl.boss.admin.repository.WorkItemDao;
//import com.zbjdl.boss.admin.workitem.entity.WorkItemEntity;
//import com.zbjdl.boss.admin.workitem.enums.WorkItemStatusEnum;
//import com.zbjdl.common.persistence.mybatis.GenericDaoDefault;
//
///**
// * 
// * 类名称：WorkItemDaoImpl <br>
// * 类描述： <br>
// * 
// * @author：feng
// * @since：2011-11-17 下午01:40:23
// * @version:
// * 
// */
//public class WorkItemDaoImpl extends GenericDaoDefault<WorkItemEntity>
//		implements WorkItemDao {
//
//	@Override
//	public void updateStatus(WorkItemStatusEnum status, Long workItemId) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("workItemStatus", status.toString());
//		params.put("workItemId", workItemId);
//		this.update("updateStatus", params);
//	}
//
//	@Override
//	public List<WorkItemEntity> queryByResourceId(String resourceId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<WorkItemEntity> findList(WorkItemEntity entity) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
