/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.function.dao;

import com.zbjdl.boss.admin.BaseDaoTest;
import com.zbjdl.boss.admin.function.entity.MenuEntity;
import com.zbjdl.boss.admin.repository.MenuDao;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：MenuDaoImplTest <br>
 * 类描述： <br>
 *
 * @author：feng
 * @since：2011-7-7 下午05:38:47
 * @version:
 */
public class MenuDaoImplTest extends BaseDaoTest {

	@Autowired
	private MenuDao menuDao;

	@Test
	public void testAdd() {
		MenuEntity menu = new MenuEntity();
		menu.setMenuName("功能123");
		menu.setFunctionId(new Long(100));
		menu.setLevelNum(1);
		menu.setParentId(new Long(0));
		menuDao.save(menu);

		System.out.println(menu.getMenuId());

		MenuEntity entity = menuDao.selectById(menu.getMenuId());

		System.out.println(entity.getMenuId());

		Assert.assertEquals(menu.getMenuId(), entity.getMenuId());
	}

	@Test
	public void testDelete() {
		long menuId = 0;
		menuDao.delete(menuId);
		MenuEntity nowMenu = menuDao.selectById(menuId);
		System.out.println(nowMenu == null);
		Assert.assertNull(nowMenu);
	}

	@Test
	public void testUpdate() {
		long menuId = 1;
		MenuEntity menu = menuDao.selectById(menuId);
		String changedName = "测试菜单2";
		menu.setMenuName(changedName);
		menuDao.update(menu);
		MenuEntity nowMenu = menuDao.selectById(menuId);
		System.out.println(nowMenu.getMenuName());
		Assert.assertEquals(changedName, nowMenu.getMenuName());
	}

	@Test
	public void testGet() {
		long menuId = 1;
		MenuEntity menu = menuDao.selectById(menuId);
		System.out.println(menu.getMenuId());
		Assert.assertNotNull(menu);
	}

	@Test
	public void testGetAll() {
		List<MenuEntity> menus = menuDao.findAll();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testQuery() {
		MenuEntity entity = new MenuEntity();
		entity.setFunctionId(new Long(0));
		List<MenuEntity> menus = menuDao.findList(entity);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testQueryMenusByFunctionId() {
		List<Long> functionIds = new ArrayList<Long>();
		functionIds.add(new Long(0));
		functionIds.add(new Long(1));
		List<MenuEntity> menus = menuDao.queryMenusByFunctionIds(functionIds);
	}

}
