/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.notice.service;

import com.zbjdl.boss.admin.notice.dto.UpgradeNoticeDTO;

/**
 * @author feng
 * @since 2012-8-31 上午10:44:52
 * @version 1.0.0
 */
public interface UpgradeNoticeService {

	/**
	 * 添加升级公告
	 */
	public void addNotice(UpgradeNoticeDTO notice);

	/**
	 * 修改升级公告
	 */
	public void updateNotice(UpgradeNoticeDTO notice);

	/**
	 * 删除升级公告
	 */
	public void deleteNotice(Long id);

	/**
	 * 查询功能对应的公告并根据用户设置做展示
	 * 
	 * @param userId
	 * @param functionId
	 * @return
	 */
	public UpgradeNoticeDTO showNotice(Long userId, Long functionId);

	/**
	 * 用户设置不再显示此公告
	 * 
	 * @param userId
	 * @param noticeId
	 */
	public void closeNotice(Long userId, Long noticeId);

	/**
	 * 通过ID查询公告
	 * 
	 * @param noticeId
	 * @return
	 */
	public UpgradeNoticeDTO queryNotice(Long noticeId);
}
