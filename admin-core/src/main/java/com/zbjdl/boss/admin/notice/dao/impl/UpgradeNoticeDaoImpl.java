package com.zbjdl.boss.admin.notice.dao.impl;
//package com.zbjdl.boss.admin.notice.dao.impl;
//
//import com.google.common.collect.Maps;
//import com.zbjdl.boss.admin.notice.entity.UpgradeNoticeEntity;
//import com.zbjdl.boss.admin.repository.UpgradeNoticeDao;
//import com.zbjdl.common.persistence.mybatis.GenericDaoDefault;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @author feng
// * @version 1.0.0
// * @since 2012-8-31 上午11:09:13
// */
//public class UpgradeNoticeDaoImpl extends
//		GenericDaoDefault<UpgradeNoticeEntity> implements UpgradeNoticeDao {
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public UpgradeNoticeEntity queryNoticeForUser(Long userId, Long functionId) {
//		Map<String, Object> params = Maps.newHashMap();
//		params.put("userId", userId);
//		params.put("functionId", functionId);
//		List<UpgradeNoticeEntity> result = (List<UpgradeNoticeEntity>) this
//				.query("queryNoticeForUser", params);
//		if (result != null && !result.isEmpty()) {
//			return result.get(0);
//		}
//		return null;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public UpgradeNoticeEntity checkExist(UpgradeNoticeEntity notice) {
//		List<UpgradeNoticeEntity> result = (List<UpgradeNoticeEntity>) this
//				.query("checkExist", notice);
//		if (result != null && !result.isEmpty()) {
//			return result.get(0);
//		}
//		return null;
//	}
//
//}
