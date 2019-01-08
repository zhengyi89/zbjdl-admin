/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.function.service;

import com.zbjdl.boss.admin.BaseServiceTest;
import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.function.convert.impl.MenuConvert;
import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.entity.MenuEntity;
import com.zbjdl.boss.admin.function.service.impl.MenuServiceImpl;
import com.zbjdl.boss.admin.repository.MenuDao;

import junit.framework.Assert;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：MenuServiceImplTest <br>
 * 类描述： <br>
 *
 * @author：feng
 * @since：2011-7-7 下午06:07:55
 * @version:
 */
public class MenuServiceImplTest extends BaseServiceTest {

	private MenuServiceImpl service = new MenuServiceImpl();

	private MenuDao menuDao = mockery.mock(MenuDao.class);

	@SuppressWarnings("unchecked")
	private Convert<MenuDTO, MenuEntity> menuConvert = mockery
			.mock(Convert.class);

	@Before
	public void before() {
		service.setMenuDao(menuDao);
		service.setMenuConvert(menuConvert);
	}

	/**
	 * Test method for
	 * {@link com.zbjdl.boss.admin.function.service.impl.MenuServiceImpl#createMenu(com.zbjdl.boss.admin.function.dto.MenuDTO)}
	 * .
	 */
	@Test
	public void testCreateMenu() {
		final MenuDTO dto = new MenuDTO();
		dto.setMenuId(new Long(1));
		dto.setMenuName("菜单1");
		dto.setFunctionId(new Long(1));
		dto.setLevelNum(1);
		dto.setParentId(new Long(0));
		dto.setLevelNum(1);

		final MenuEntity entity = new MenuConvert().convert(dto);
		mockery.checking(new Expectations() {
			{
				oneOf(menuConvert).convert(dto);
				will(returnValue(entity));
				oneOf(menuDao).save(entity);
			}
		});

		Long id = service.createMenu(dto);
		Assert.assertEquals(new Long(1), id);
	}

	/**
	 * Test method for
	 * {@link com.zbjdl.boss.admin.function.service.impl.MenuServiceImpl#deleteMenu(java.lang.Long)}
	 * .
	 */
	@Test
	public void testDeleteMenu() {
		final long menuId = 0;
		mockery.checking(new Expectations() {
			{
				oneOf(menuDao).delete(menuId);
			}
		});
		service.deleteMenu(menuId);
		mockery.assertIsSatisfied();
	}

	/**
	 * Test method for
	 * {@link com.zbjdl.boss.admin.function.service.impl.MenuServiceImpl#updateMenu(com.zbjdl.boss.admin.function.dto.MenuDTO)}
	 * .
	 */
	@Test
	public void testUpdateMenu() {
		final MenuDTO dto = new MenuDTO();
		dto.setMenuId(new Long(1));
		dto.setMenuName("菜单1");
		dto.setFunctionId(new Long(1));
		dto.setLevelNum(1);
		dto.setParentId(new Long(0));
		dto.setLevelNum(1);

		final MenuEntity entity = new MenuConvert().convert(dto);
		mockery.checking(new Expectations() {
			{
				oneOf(menuConvert).convert(dto);
				will(returnValue(entity));
				oneOf(menuDao).update(entity);
			}
		});
		service.updateMenu(dto);
		mockery.assertIsSatisfied();
	}

	/**
	 * Test method for
	 * {@link com.zbjdl.boss.admin.function.service.impl.MenuServiceImpl#queryMenuByID(java.lang.Long)}
	 * .
	 */
	@Test
	public void testQueryMenuByID() {
		final long menuId = 0;
		final MenuDTO dto = new MenuDTO();
		final MenuEntity entity = new MenuEntity();
		mockery.checking(new Expectations() {
			{
				oneOf(menuDao).selectById(menuId);
				will(returnValue(entity));
				oneOf(menuConvert).convert(entity);
				will(returnValue(dto));
			}
		});
		MenuDTO returnValue = service.queryMenuByID(menuId);
		Assert.assertNotNull(returnValue);
	}

	/**
	 * Test method for
	 * {@link com.zbjdl.boss.admin.function.service.impl.MenuServiceImpl#query(com.zbjdl.boss.admin.function.dto.MenuDTO)}
	 * .
	 */
	@Test
	public void testQuery() {
		final MenuDTO dto = new MenuDTO();
		dto.setMenuName("测试功能0");

		final List<MenuEntity> entities = new ArrayList<MenuEntity>();

		final MenuEntity entity = new MenuConvert().convert(dto);
		mockery.checking(new Expectations() {
			{
				oneOf(menuConvert).convert(dto);
				will(returnValue(entity));
				oneOf(menuDao).findList(entity);
				will(returnValue(entities));
			}
		});

		List<MenuDTO> functions = service.query(dto);
		Assert.assertEquals(entities.size(), functions.size());
	}

	/**
	 * Test method for
	 * {@link com.zbjdl.boss.admin.function.service.impl.MenuServiceImpl#hasChildrenMenus(java.lang.Long)}
	 * .
	 */
	@Test
	public void testHasChildrenMenus() {
		final long menuId = 0;
		final int count = 0;
		mockery.checking(new Expectations() {
			{
				oneOf(menuDao).hasChildrenMenus(menuId);
				will(returnValue(count));
			}
		});
		boolean hasChildrenMenus = service.hasChildrenMenus(menuId);
		Assert.assertEquals(false, hasChildrenMenus);
	}

	@Test
	public void testQueryMenusByFunctionIds() {

		final List<Long> functionIds = new ArrayList<Long>();
		functionIds.add(new Long(0));

		final List<MenuEntity> entities = new ArrayList<MenuEntity>();

		mockery.checking(new Expectations() {
			{
				oneOf(menuDao).queryMenusByFunctionIds(functionIds);
				will(returnValue(entities));
			}
		});

		List<MenuDTO> functions = service.queryMenusByFunctionIds(functionIds);
		Assert.assertEquals(entities.size(), functions.size());
	}

}
