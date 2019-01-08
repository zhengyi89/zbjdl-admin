/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.security.biz.impl;

import com.zbjdl.boss.admin.biz.SecurityConfigBiz;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * <p>Title: 功能</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2018</p>
 * <p>Company: 八戒财云</p>
 *
 * @author feng
 * @version 0.1, 13-6-27 下午5:52
 */
@ContextConfiguration(locations = {"classpath:/testContext.xml"})
public class SecurityConfigBizImplTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private SecurityConfigBiz securityConfigBiz;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Before
	public void beforeInit() {
		jdbcTemplate.execute("INSERT INTO TBL_USER (LOGINNAME, USERNAME, PASSWORD, USERSTATUS, EMAIL, ISSUPERADMIN, ISADMIN, PWDERRORNUMS, PRIMARYDEPARTMENTID, CREATETIME, MOBILE, MIGRATION, CREATOR_ID) VALUES ('laoban', '老板', '5d949647962b1b9bfeda6cd509b081e1', 'ACTIVE', '', '0', '1', 0, -1, '2013-06-27 00:00:00', '13098769876', '0', null)");
	}

	@Test
	public void testMigrateDepartment() throws Exception {
		securityConfigBiz.migrateDepartment(-1L, -47L, true, true, true);

	}
}
