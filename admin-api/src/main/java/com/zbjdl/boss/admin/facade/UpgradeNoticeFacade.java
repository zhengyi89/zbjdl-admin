/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.facade;

import com.zbjdl.boss.admin.notice.dto.UpgradeNoticeDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;

/**
 * @author feng
 * @since 2012-8-31 上午10:34:17
 * @version 1.0.0
 */
public interface UpgradeNoticeFacade {

	/**
	 * 添加升级公告
	 */
	public void addNotice(UserDTO user, UpgradeNoticeDTO notice);

	/**
	 * 修改升级公告
	 */
	public void updateNotice(UserDTO user, UpgradeNoticeDTO notice);

	/**
	 * 删除升级公告
	 */
	public void deleteNotice(UserDTO user, Long id);

	/**
	 * 根据URI查询功能对应的公告并根据用户设置做展示
	 * 
	 * @param userId
	 * @param functionUri
	 * @return
	 */
	public UpgradeNoticeDTO showNotice(Long userId, String functionUri);

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
