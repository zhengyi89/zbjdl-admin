/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.notice;

import com.zbjdl.boss.admin.facade.UpgradeNoticeFacade;
import com.zbjdl.boss.admin.notice.dto.UpgradeNoticeDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.Date;
import java.util.Random;

/**
 * @author：feng
 * @since：2012-8-31 上午11:48:10
 * @version:
 */
@ContextConfiguration(locations = {"classpath:/runtimecfg/datasource.xml",
		"classpath:/employee-core-spring/employee-dao-appContext.xml",
		"classpath:/employee-core-spring/employee-service-appContext.xml",
		"classpath:/employee-core-spring/employee-biz-appContext.xml",
		"classpath:/employee-core-spring/employee-facade-appContext.xml"})
public class UpgradeNoticeFacadeTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	private UpgradeNoticeFacade upgradeNoticeFacade;

	private static UserDTO user;

	private UserDTO getUser() {
		if (user == null) {
			user = new UserDTO();
			user.setUserId(309l);
			user.setLoginName("baow-2");
			user.setIsAdmin(true);
			user.setPrimaryDepartmentId(-9l);
		}
		return user;
	}

	private UpgradeNoticeDTO genUpgradeNotice(long functionId, String content) {
		UpgradeNoticeDTO upgradeNotice = new UpgradeNoticeDTO();
		upgradeNotice.setFunctionId(functionId);
		upgradeNotice.setContent(content);
		upgradeNotice.setOperator(getUser().getLoginName());
		upgradeNotice.setUpgradeDate(new Date());
		upgradeNotice.setOaOrderNo(String.valueOf((new Random()).nextInt()));
		return upgradeNotice;
	}

	/**
	 * 添加升级公告
	 */
	@Test
	public void addNotice() {

		upgradeNoticeFacade
				.addNotice(
						getUser(),
						genUpgradeNotice(-90013l,
								"修改扩展号码后通知集群只更新一次数据库，同步配置时不操作可用扩展号码表"));
		upgradeNoticeFacade.addNotice(getUser(),
				genUpgradeNotice(-90005l, "增加按单项条件统计支持，比如按单个通知规则、应用等按月或按日统计"));
		upgradeNoticeFacade.addNotice(getUser(),
				genUpgradeNotice(1l, "fortest"));
		upgradeNoticeFacade.addNotice(getUser(),
				genUpgradeNotice(-90005l, "查询SQL 追加 WITH UR以提高性能"));
	}

	/**
	 * 删除升级公告
	 */
	@Test
	public void deleteNotice() {
		upgradeNoticeFacade.deleteNotice(getUser(), 3l);
	}

	/**
	 * 查询功能对应的公告并根据用户设置做展示
	 *
	 * @param userId
	 * @param functionId
	 * @return
	 */
	@Test
	public void showNotice() {
		UpgradeNoticeDTO notice = upgradeNoticeFacade.showNotice(310l,
				"/employee-boss/log/logDetail.action");
		System.out.println(notice);
	}

	/**
	 * 用户设置不再显示此公告
	 *
	 * @param userId
	 * @param noticeId
	 */
	@Test
	public void hideNotice() {
		upgradeNoticeFacade.closeNotice(311l, -90013l);
	}

}
