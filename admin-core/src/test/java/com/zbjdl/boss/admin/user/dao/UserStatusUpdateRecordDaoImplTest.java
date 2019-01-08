/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.user.dao;

import com.zbjdl.boss.admin.BaseDaoTest;
import com.zbjdl.boss.admin.repository.UserStatusUpdateRecordDao;
import com.zbjdl.boss.admin.user.entity.UserStatusUpdateRecordEntity;
import com.zbjdl.boss.admin.user.enums.UserStatusEnum;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 类名称：UserStatusUpdateRecordDaoImplTest <br>
 * 类描述：   <br>
 *
 * @author：feng
 * @since：2011-6-27 上午11:37:47
 * @version:
 */
public class UserStatusUpdateRecordDaoImplTest extends BaseDaoTest {

	@Autowired
	private UserStatusUpdateRecordDao userStatusUpdateRecordDao;

	@Test
	public void testAdd() {
		List<UserStatusUpdateRecordEntity> list = userStatusUpdateRecordDao.findAll();
		UserStatusUpdateRecordEntity usur = new UserStatusUpdateRecordEntity();

		usur.setAdminUserId(null);
		usur.setPreStatus(UserStatusEnum.ACTIVE);
		usur.setCurrentStatus(UserStatusEnum.FROZEN);
		usur.setUpdateDate(new Date());
		usur.setUpdateReason("连续输入密码错误达到3次");
		usur.setUserId(0L);

//		statusUpdateRecordEntity.setCurrentStatus(UserStatusEnum.FROZEN);
//		statusUpdateRecordEntity.setPreStatus(UserStatusEnum.ACTIVE);
//		statusUpdateRecordEntity.setUserId(0L);
//		statusUpdateRecordEntity.setUpdateReason("连续输入密码错误达到3次");
//		statusUpdateRecordEntity.setUpdateDate(new Date());
		userStatusUpdateRecordDao.save(usur);
		list = userStatusUpdateRecordDao.findAll();
		Assert.assertNotNull(list);
	}

}
