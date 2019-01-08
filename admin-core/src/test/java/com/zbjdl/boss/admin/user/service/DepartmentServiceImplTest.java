/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.user.service;

import com.zbjdl.boss.admin.BaseServiceTest;
import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.repository.DepartmentDao;
import com.zbjdl.boss.admin.user.convert.impl.DepartmentConvert;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.entity.DepartmentEntity;
import com.zbjdl.boss.admin.user.enums.DepartmentStatusEnum;
import com.zbjdl.boss.admin.user.service.impl.DepartmentServiceImpl;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：DepartmentServiceImpl <br>
 * 类描述：   <br>
 *
 * @author：feng
 * @since：2011-6-28 下午01:43:08
 * @version:
 */
public class DepartmentServiceImplTest extends BaseServiceTest {

	private DepartmentServiceImpl service = new DepartmentServiceImpl();

	private DepartmentDao departmentDao = mockery.mock(DepartmentDao.class);

	private Convert<DepartmentDTO, DepartmentEntity> departmentConvert = mockery.mock(Convert.class);

	@Before
	public void before() {
		service.setDepartmentDao(departmentDao);
		service.setDepartmentConvert(departmentConvert);
	}

	@Test
	public void testcreateDepartment() {
		final DepartmentDTO departmentDTO = new DepartmentDTO();
		departmentDTO.setDepartmentName("测试部门");
		departmentDTO.setDepartmentDesc("测试一下");
		departmentDTO.setDepartmentCode("123qwe");

		final DepartmentEntity departmentEntity = (new DepartmentConvert()).convert(departmentDTO);
		departmentEntity.setDepartmentId(2L);
		mockery.checking(new Expectations() {
			{
				oneOf(departmentConvert).convert(departmentDTO);
				will(returnValue(departmentEntity));
				oneOf(departmentDao).save(departmentEntity);

			}
		});
		//创建部门并返回部门id
		Long id = service.createDepartment(departmentDTO);

		Assert.isTrue(id.equals(2L));
		mockery.assertIsSatisfied();
	}

	@Test
	public void testUpdateDepartmentInfo() {
		final DepartmentDTO departmentDTO = new DepartmentDTO();
		departmentDTO.setDepartmentName("");
		departmentDTO.setDepartmentDesc("更新部门信息测试");
		departmentDTO.setDepartmentCode("qweasd");

		final DepartmentEntity departmentEntity = (new DepartmentConvert()).convert(departmentDTO);
		mockery.checking(new Expectations() {
			{
				oneOf(departmentDao).update(with(any(DepartmentEntity.class)));

				oneOf(departmentConvert).convert(departmentDTO);
				will(returnValue(departmentEntity));
			}
		});

		boolean b = service.updateDepartmentInfo(departmentDTO);
		mockery.assertIsSatisfied();
		Assert.isTrue(b);
	}

	@Test
	public void testDeleteDepartment() {
		final Long departmentId = 23L;

		mockery.checking(new Expectations() {
			{
				oneOf(departmentDao).delete(departmentId);

			}
		});

		boolean b = service.deleteDepartment(departmentId);
		Assert.isTrue(b);
	}

	@Test
	public void testValidateDepartmentExistOrnot() {
		final DepartmentDTO departmentDTO = new DepartmentDTO();
		departmentDTO.setDepartmentName("英哲的部门");
		departmentDTO.setDepartmentDesc("测试一下");
		departmentDTO.setDepartmentCode("123qwe");
//		final DepartmentEntity departmentEntity = DepartmentConvert.convert(departmentDTO);
		final List<DepartmentEntity> list = new ArrayList<DepartmentEntity>();
		final DepartmentEntity de = this.createDepart();
		DepartmentEntity de1 = this.createDepart();
		list.add(de);
		list.add(de1);
		mockery.checking(new Expectations() {
			{
				oneOf(departmentDao).findList(with(any(DepartmentEntity.class)));
				will(returnValue(list));

				oneOf(departmentConvert).convert(departmentDTO);
				will(returnValue(de));
			}
		});

		boolean b = service.validateDepartmentExistOrnot(departmentDTO);
		Assert.isTrue(b == false);
	}


	@Test
	public void testQueryDepartmentById() {
		final Long departmentId = 100L;
		final DepartmentEntity departmentEntityTest = this.createDepart();
		departmentEntityTest.setDepartmentId(departmentId);
		mockery.checking(new Expectations() {
			{
				oneOf(departmentDao).selectById(departmentId);
				will(returnValue(departmentEntityTest));

			}
		});

		DepartmentDTO departmentEntity = service.queryDepartmentById(departmentId);
		Assert.isTrue(departmentEntityTest.equals(departmentEntity));
	}

	@Test
	public void testQueryDepartments() {
		final DepartmentDTO departmentDTO = new DepartmentDTO();
		departmentDTO.setDepartmentName("英哲的部门");
		departmentDTO.setDepartmentDesc("测试一下");
		departmentDTO.setDepartmentCode("123qwe");
//		final DepartmentEntity departmentEntity = DepartmentConvert.convert(departmentDTO);
		final List<DepartmentEntity> list = new ArrayList<DepartmentEntity>();
		final DepartmentEntity de = this.createDepart();
		DepartmentEntity de1 = this.createDepart();
		list.add(de);
		list.add(de1);
		mockery.checking(new Expectations() {
			{
				oneOf(departmentConvert).convert(departmentDTO);
				will(returnValue(de));

				oneOf(departmentDao).findList(with(any(DepartmentEntity.class)));
				will(returnValue(list));

			}
		});

		List<DepartmentDTO> list1 = service.queryDepartments(departmentDTO);
		Assert.notEmpty(list1);
		Assert.isTrue(list1.size() == list.size());
	}

	private DepartmentEntity createDepart() {
		DepartmentEntity departmentEntity = new DepartmentEntity();
		departmentEntity.setDepartmentCode("12314");
		departmentEntity.setDepartmentDesc("22222");
		departmentEntity.setDepartmentId(123L);
		departmentEntity.setDepartmentName("技术部");
		departmentEntity.setDepartmentStatus(DepartmentStatusEnum.ACTIVE);
		return departmentEntity;

	}

}
