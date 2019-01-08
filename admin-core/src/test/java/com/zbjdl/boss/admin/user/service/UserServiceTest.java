/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.user.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.zbjdl.boss.admin.BaseServiceTest;
import com.zbjdl.boss.admin.user.service.UserService;

/**
 * 类名称：UserServiceTest <br>
 * 类描述：   <br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2013-06-28 10:35:28
 */
@ContextConfiguration(locations = {"classpath:/testContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class UserServiceTest extends BaseServiceTest {

	@Autowired
	private UserService userService;

	@Test
	public void testBatchUpdateUserPrimaryDepartmentId() {
		Long departmentId = 123L;
		Long newDepartmnetId = 456L;
		boolean b = userService.migrateUser(departmentId, newDepartmnetId);
		Assert.assertTrue(b);
	}

}
