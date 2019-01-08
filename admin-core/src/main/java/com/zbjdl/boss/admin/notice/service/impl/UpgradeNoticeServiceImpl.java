/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.zbjdl.boss.admin.notice.dto.UpgradeNoticeDTO;
import com.zbjdl.boss.admin.notice.entity.UpgradeNoticeEntity;
import com.zbjdl.boss.admin.notice.entity.UpgradeNoticeHideEntity;
import com.zbjdl.boss.admin.notice.service.UpgradeNoticeService;
import com.zbjdl.boss.admin.repository.UpgradeNoticeDao;
import com.zbjdl.boss.admin.repository.UpgradeNoticeHideDao;
import com.zbjdl.common.utils.BeanUtils;

/**
 * @author：feng
 * @since：2012-8-31 上午10:45:37
 * @version:
 */
public class UpgradeNoticeServiceImpl implements UpgradeNoticeService {

	@Autowired
	private UpgradeNoticeDao upgradeNoticeDao;

	@Autowired
	private UpgradeNoticeHideDao upgradeNoticeHideDao;

	@Override
	public void addNotice(UpgradeNoticeDTO noticeDTO) {
		UpgradeNoticeEntity notice = this.toUpgradeNoticeEntity(noticeDTO);
		UpgradeNoticeEntity exist = upgradeNoticeDao.checkExist(notice);
		if (exist == null) {
			upgradeNoticeDao.save(notice);
		}
	}

	@Override
	public void updateNotice(UpgradeNoticeDTO noticeDTO) {
		UpgradeNoticeEntity upgradeNotice = this
				.toUpgradeNoticeEntity(noticeDTO);
		upgradeNoticeDao.update(upgradeNotice);
	}

	@Override
	public void deleteNotice(Long id) {
		upgradeNoticeDao.delete(id);
	}

	@Override
	public UpgradeNoticeDTO showNotice(Long userId, Long functionId) {
		UpgradeNoticeEntity notice = upgradeNoticeDao.queryNoticeForUser(
				userId, functionId);
		return this.toUpgradeNoticeDTO(notice);
	}

	@Override
	public void closeNotice(Long userId, Long noticeId) {
		UpgradeNoticeHideEntity notice = new UpgradeNoticeHideEntity();
		notice.setUserId(userId);
		notice.setNoticeId(noticeId);
		UpgradeNoticeHideEntity exist = upgradeNoticeHideDao.checkExist(notice);
		if (exist == null) {
			upgradeNoticeHideDao.save(notice);
		}
	}

	@Override
	public UpgradeNoticeDTO queryNotice(Long noticeId) {
		UpgradeNoticeEntity upgradeNotice = upgradeNoticeDao.selectById(noticeId);
		return this.toUpgradeNoticeDTO(upgradeNotice);
	}

	private UpgradeNoticeEntity toUpgradeNoticeEntity(
			UpgradeNoticeDTO upgradeNotice) {
		if (upgradeNotice == null) {
			return null;
		}
		UpgradeNoticeEntity upgradeNoticeEntity = new UpgradeNoticeEntity();
		BeanUtils.copyProperties(upgradeNotice, upgradeNoticeEntity);
		return upgradeNoticeEntity;
	}

	private UpgradeNoticeDTO toUpgradeNoticeDTO(
			UpgradeNoticeEntity upgradeNotice) {
		if (upgradeNotice == null) {
			return null;
		}
		UpgradeNoticeDTO upgradeNoticeDTO = new UpgradeNoticeDTO();
		BeanUtils.copyProperties(upgradeNotice, upgradeNoticeDTO);
		return upgradeNoticeDTO;
	}
}
