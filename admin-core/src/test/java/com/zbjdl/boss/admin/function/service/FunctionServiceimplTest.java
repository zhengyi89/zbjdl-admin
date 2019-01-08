/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.function.service;

import junit.framework.Assert;

import com.zbjdl.boss.admin.BaseServiceTest;
import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.function.convert.impl.FunctionConvert;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.function.entity.FunctionEntity;
import com.zbjdl.boss.admin.function.enums.FunctionStatusEnum;
import com.zbjdl.boss.admin.function.service.impl.FunctionServiceImpl;
import com.zbjdl.boss.admin.repository.FunctionDao;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：FunctionServiceimplTest <br>
 * 类描述： <br>
 *
 * @author：feng
 * @since：2011-7-7 下午06:13:09
 * @version:
 */
public class FunctionServiceimplTest extends BaseServiceTest {

	private FunctionServiceImpl service = new FunctionServiceImpl();

	private FunctionDao functionDao = mockery.mock(FunctionDao.class);

	@SuppressWarnings("unchecked")
	private Convert<FunctionDTO, FunctionEntity> functionConvert = mockery
			.mock(Convert.class);

	@Before
	public void before() {
		service.setFunctionDao(functionDao);
		service.setFunctionConvert(functionConvert);
	}

	/**
	 * Test method for
	 * {@link com.zbjdl.boss.admin.function.service.impl.FunctionServiceImpl#createFunction(com.zbjdl.boss.admin.function.dto.FunctionDTO)}
	 * .
	 */
	@Test
	public void testCreateFunction() {
		final FunctionDTO dto = new FunctionDTO();
		dto.setFunctionName("功能1");
		dto.setPreFunctionId(new Long(1));
		dto.setFunctionUrl("http://www.test.com");
		dto.setFunctionStatus(FunctionStatusEnum.ACTIVE);
//		dto.getDeptIds().add("1");
//		dto.getDeptIds().add("2");
//		dto.getDeptIds().add("3");
		dto.setFunctionId(new Long(1));

		final FunctionEntity entity = new FunctionConvert().convert(dto);
		mockery.checking(new Expectations() {
			{
				oneOf(functionConvert).convert(dto);
				will(returnValue(entity));
				oneOf(functionDao).save(entity);
			}
		});

		Long id = service.createFunction(dto);
		Assert.assertEquals(new Long(1), id);
	}

	/**
	 * Test method for
	 * {@link com.zbjdl.boss.admin.function.service.impl.FunctionServiceImpl#deleteFunction(java.lang.Long)}
	 * .
	 */
	@Test
	public void testDeleteFunction() {
		final long functionId = 0;
		mockery.checking(new Expectations() {
			{
				oneOf(functionDao).delete(functionId);
			}
		});
		service.deleteFunction(functionId);
		mockery.assertIsSatisfied();
	}

	/**
	 * Test method for
	 * {@link com.zbjdl.boss.admin.function.service.impl.FunctionServiceImpl#updateFunction(com.zbjdl.boss.admin.function.dto.FunctionDTO)}
	 * .
	 */
	@Test
	public void testUpdateFunction() {
		final FunctionDTO dto = new FunctionDTO();
		dto.setFunctionName("功能1");
		dto.setPreFunctionId(new Long(1));
		dto.setFunctionUrl("http://www.test.com");
		dto.setFunctionStatus(FunctionStatusEnum.ACTIVE);
//		dto.getDeptIds().add("1");
//		dto.getDeptIds().add("2");
//		dto.getDeptIds().add("3");
		dto.setFunctionId(new Long(0));

		final FunctionEntity entity = new FunctionConvert().convert(dto);
		mockery.checking(new Expectations() {
			{
				oneOf(functionConvert).convert(dto);
				will(returnValue(entity));
				oneOf(functionDao).update(entity);
			}
		});
		service.updateFunction(dto);
		mockery.assertIsSatisfied();
	}

	/**
	 * Test method for
	 * {@link com.zbjdl.boss.admin.function.service.impl.FunctionServiceImpl#queryFunctionByID(java.lang.Long)}
	 * .
	 */
	@Test
	public void testQueryFunctionByID() {
		final long functionId = 0;
		final FunctionDTO dto = new FunctionDTO();
		final FunctionEntity entity = new FunctionEntity();
		mockery.checking(new Expectations() {
			{
				oneOf(functionDao).selectById(functionId);
				will(returnValue(entity));
				oneOf(functionConvert).convert(entity);
				will(returnValue(dto));
			}
		});
		FunctionDTO returnValue = service.queryFunctionByID(functionId);
		Assert.assertNotNull(returnValue);
	}

	/**
	 * Test method for
	 * {@link com.zbjdl.boss.admin.function.service.impl.FunctionServiceImpl#query(com.zbjdl.boss.admin.function.dto.FunctionDTO)}
	 * .
	 */
	@Test
	public void testQuery() {
		final FunctionDTO functionDTO = new FunctionDTO();
		functionDTO.setFunctionName("测试功能0");

		final List<FunctionEntity> entities = new ArrayList<FunctionEntity>();

		final FunctionEntity entity = new FunctionConvert()
				.convert(functionDTO);
		mockery.checking(new Expectations() {
			{
				oneOf(functionConvert).convert(functionDTO);
				will(returnValue(entity));
				oneOf(functionDao).queryByFunction(entity);
				will(returnValue(entities));
			}
		});

		List<FunctionDTO> functions = service.query(functionDTO);
		Assert.assertEquals(entities.size(), functions.size());
	}

	/**
	 * Test method for
	 * {@link com.zbjdl.boss.admin.function.service.impl.FunctionServiceImpl#isPreFunction(java.lang.Long)}
	 * .
	 */
	@Test
	public void testIsPreFunction() {
		final long functionId = 0;
		final int count = 0;
		mockery.checking(new Expectations() {
			{
				oneOf(functionDao).isPreFunction(functionId);
				will(returnValue(count));
			}
		});
		boolean isPreFunction = service.isPreFunction(functionId);
		Assert.assertEquals(false, isPreFunction);
	}

}
