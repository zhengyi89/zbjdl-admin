/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.repository;

import org.springframework.stereotype.Repository;

import com.zbjdl.boss.admin.notice.entity.UpgradeNoticeEntity;
import com.zbjdl.common.respository.mybatis.GenericRepository;

/**
 * @author feng
 * @since 2012-8-31 上午11:01:24
 * @version 1.0.0
 */
@Repository
public interface UpgradeNoticeDao extends GenericRepository {

	public UpgradeNoticeEntity queryNoticeForUser(Long userId, Long functionId);

	public UpgradeNoticeEntity checkExist(UpgradeNoticeEntity notice);

}
