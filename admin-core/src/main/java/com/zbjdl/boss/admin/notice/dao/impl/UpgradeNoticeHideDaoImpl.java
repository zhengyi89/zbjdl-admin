package com.zbjdl.boss.admin.notice.dao.impl;
//package com.zbjdl.boss.admin.notice.dao.impl;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.zbjdl.boss.admin.notice.entity.UpgradeNoticeHideEntity;
//import com.zbjdl.boss.admin.repository.UpgradeNoticeHideDao;
//import com.zbjdl.common.persistence.mybatis.GenericDaoDefault;
//
///**
// * @author：feng
// * @since：2012-8-31 上午11:09:45
// * @version:
// */
//public class UpgradeNoticeHideDaoImpl extends
//		GenericDaoDefault<UpgradeNoticeHideEntity> implements
//		UpgradeNoticeHideDao {
//
//	@Override
//	public void deleteByUser(Long userId, Long noticeId) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("userId", userId);
//		params.put("noticeId", noticeId);
//		this.delete("deleteByUser", params);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public UpgradeNoticeHideEntity checkExist(UpgradeNoticeHideEntity notice) {
//		List<UpgradeNoticeHideEntity> result = (List<UpgradeNoticeHideEntity>) this
//				.query("checkExist", notice);
//		if (result != null && !result.isEmpty()) {
//			return result.get(0);
//		}
//		return null;
//	}
//
//}
