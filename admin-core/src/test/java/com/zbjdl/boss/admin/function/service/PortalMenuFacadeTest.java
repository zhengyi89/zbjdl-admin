package com.zbjdl.boss.admin.function.service;

import com.zbjdl.boss.admin.facade.PortalMenuFacade;
import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.dto.MenuModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author feng
 * @version 1.0.0
 * @since 2013-5-31 上午11:27:24
 */
@ContextConfiguration(locations = {"classpath:/testContext.xml"})
public class PortalMenuFacadeTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private PortalMenuFacade portalMenuFacade;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Before
	public void beforeInit() {
		jdbcTemplate.execute("DELETE FROM EMP.TBL_PORTAL_MENU");
		jdbcTemplate.execute("INSERT INTO EMP.TBL_PORTAL_MENU (MENUID, DEPARTMENTID, MENUNAME, FUNCTIONID, FUNCTIONURL, PARENTID, LEVELNUM) VALUES (-1, -1, '运营系统',-1,'#', 0, 1)");
		jdbcTemplate.execute("INSERT INTO EMP.TBL_PORTAL_MENU (MENUID, DEPARTMENTID, MENUNAME, FUNCTIONID, FUNCTIONURL, PARENTID, LEVELNUM) VALUES (-101, -3, '一级菜单1',-1,'http://www.test.com/#1', -1, 2)");
		jdbcTemplate.execute("INSERT INTO EMP.TBL_PORTAL_MENU (MENUID, DEPARTMENTID, MENUNAME, FUNCTIONID, FUNCTIONURL, PARENTID, LEVELNUM) VALUES (-102, -3, '一级菜单2',-1,'http://www.test.com/#2', -1, 2)");
		jdbcTemplate.execute("INSERT INTO EMP.TBL_PORTAL_MENU (MENUID, DEPARTMENTID, MENUNAME, FUNCTIONID, FUNCTIONURL, PARENTID, LEVELNUM) VALUES (-103, -3, '一级菜单3',-1,'http://www.test.com/#3', -1, 2)");
		jdbcTemplate.execute("INSERT INTO EMP.TBL_PORTAL_MENU (MENUID, DEPARTMENTID, MENUNAME, FUNCTIONID, FUNCTIONURL, PARENTID, LEVELNUM) VALUES (-1011, -3, '二级菜单1',-1,'http://www.test.com/#1/#1', -101, 3)");
	}

	/**
	 * 取得单个菜单信息
	 */
	@Test
	public void findMenu() {
		MenuDTO menu = portalMenuFacade.findMenu(-101l);
		Assert.assertNotNull(menu);
	}

	/**
	 * 添加菜单
	 */
	public void addMenu(MenuDTO menuDTO) {
		;
	}

	/**
	 * 更新菜单
	 */
	public void updateMenu(MenuDTO menuDTO) {
		;
	}

	/**
	 * 移动菜单
	 */
	public void moveMenus(List<Long> menuIds, Long parentId) {
		;
	}

	/**
	 * 非级联批量删除菜单
	 */
	public void deleteMenus(List<Long> menuIds) {
		;
	}

	/**
	 * 取得菜单树
	 */
	public void findDepartmentMemuTree(Long departmentId) {
		;
	}

	@Test
	public void importMenus() {
		String menuDetail = "一级菜单,,运营系统\r\n二级菜单1,,一级菜单\r\n二级菜单1,,二级菜单1";
		Long departmentId = -3L;
		portalMenuFacade.importMenus(menuDetail, departmentId);
		MenuModel menuModel = portalMenuFacade.findDepartmentMemuTree(departmentId);
		assertEquals(menuModel.getMenuDTO().getDepartmentId(), departmentId);
	}
}
