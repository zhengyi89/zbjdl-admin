/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.repository;

import org.springframework.stereotype.Repository;

import com.zbjdl.boss.admin.notice.entity.UpgradeNoticeHideEntity;
import com.zbjdl.common.respository.mybatis.GenericRepository;

/**
 * @author：feng
 * @since：2012-8-31 上午11:01:24
 * @version:
 */
@Repository
public interface UpgradeNoticeHideDao extends GenericRepository {
	public void deleteByUser(Long userId, Long noticeId);

	public UpgradeNoticeHideEntity checkExist(UpgradeNoticeHideEntity notice);
}
