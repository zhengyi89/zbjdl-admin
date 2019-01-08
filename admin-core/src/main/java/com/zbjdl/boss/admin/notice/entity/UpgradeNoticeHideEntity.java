/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.notice.entity;

import com.zbjdl.common.persistence.Entity;

/**
 * @author：feng
 * @since：2012-8-31 上午10:48:53
 * @version:
 */
public class UpgradeNoticeHideEntity implements Entity<Long> {
	private static final long serialVersionUID = 6781862639900105888L;

	private Long id;

	private Long userId;

	private Long noticeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}
}
