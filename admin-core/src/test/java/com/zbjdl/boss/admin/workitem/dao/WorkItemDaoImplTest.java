package com.zbjdl.boss.admin.workitem.dao;

import com.zbjdl.boss.admin.BaseDaoTest;
import com.zbjdl.boss.admin.repository.WorkItemDao;
import com.zbjdl.boss.admin.workitem.entity.WorkItemEntity;
import com.zbjdl.boss.admin.workitem.enums.WorkItemResultEnum;
import com.zbjdl.boss.admin.workitem.enums.WorkItemStatusEnum;
import com.zbjdl.boss.admin.workitem.enums.WorkItemTypeEnum;

import junit.framework.Assert;

import org.junit.Test;

import javax.annotation.Resource;

import java.util.Date;

/**
 * Filename:    WorkItemDaoImplTest.java
 * Projectname: employee-core
 * Title:  workItem测试类
 * Description:
 * Copyright:   Copyright (c)2015
 */
public class WorkItemDaoImplTest extends BaseDaoTest {

	@Resource
	private WorkItemDao workItemDao;

	@Test
	public void testGet() {
		WorkItemEntity workItem = workItemDao.selectById(1L);
		Assert.assertNotNull(workItem);
	}

	@Test
	public void testAdd() {
		WorkItemEntity workItemEntity = getWorkItemEntity(null);
		workItemDao.save(workItemEntity);
	}

	@Test
	public void testUpdate() {
		WorkItemEntity workItemEntity = getWorkItemEntity(new Long(1));
		workItemDao.update(workItemEntity);
	}

	private WorkItemEntity getWorkItemEntity(Long id) {
		WorkItemEntity workItemEntity = new WorkItemEntity();
		workItemEntity.setApproveBy(new Long("1"));
		workItemEntity.setApproveTime(new Date());
		workItemEntity.setApproveUserName("杨学博");
		workItemEntity.setBelongSystem("REMIT");
		workItemEntity.setCancelBy(new Long("1"));
		workItemEntity.setCancelTime(new Date());
		workItemEntity.setCancelUserName("杨学博");
		workItemEntity.setContent("执行成功！");
		workItemEntity.setCreateTime(new Date());
		workItemEntity.setFunctionId(new Long("1"));
		workItemEntity.setInvocatResult(WorkItemResultEnum.SUCCESS);
		workItemEntity.setInvocatTime(new Date());
		workItemEntity.setLockedBy(new Long(1));
		workItemEntity.setLockedUserName("杨学博");
		workItemEntity.setLockTime(new Date());
		workItemEntity.setRejectCause("出现错误！");
		workItemEntity.setRejectedBy(new Long(1));
		workItemEntity.setRejectTime(new Date());
		workItemEntity.setRejectUserName("杨学博");
		workItemEntity.setResourceId("1121");
		workItemEntity.setResultComment("执行成功！");
		workItemEntity.setSubmittedBy(new Long(1));
		workItemEntity.setSubmittedUserName("杨学博");
		workItemEntity.setSubmitTime(new Date());
		workItemEntity.setSummary("摘要！");
		workItemEntity.setUpdateTime(new Date());
		workItemEntity.setWorkItemStatus(WorkItemStatusEnum.CHECKING);
		workItemEntity.setWorkItemType(WorkItemTypeEnum.NORMAL);
		if (id != null) {
			workItemEntity.setId(id);
		}
		return workItemEntity;
	}

}
