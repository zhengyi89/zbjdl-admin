package com.zbjdl.boss.admin.workitem.service;

import com.zbjdl.boss.admin.BaseDaoTest;
import com.zbjdl.boss.admin.workitem.entity.WorkItemEntity;
import com.zbjdl.boss.admin.workitem.enums.WorkItemResultEnum;
import com.zbjdl.boss.admin.workitem.enums.WorkItemStatusEnum;
import com.zbjdl.boss.admin.workitem.enums.WorkItemTypeEnum;
import com.zbjdl.boss.admin.workitem.service.WorkItemService;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Filename: WorkItemDaoImplTest.java Projectname: employee-core Title:
 */
public class WorkItemServiceImplTest extends BaseDaoTest {

	@Autowired
	private WorkItemService workItemService;

	@Test
	public void testGet() {
		// WorkItemEntity entity = this.getWorkItemEntity(null);
		WorkItemEntity entity = this.getEntity();
		List<WorkItemEntity> workItemEntities = workItemService
				.getWorkItemByObject(entity);
		Assert.assertNotNull(workItemEntities);
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

	private WorkItemEntity getEntity() {
		WorkItemEntity workItemEntity = new WorkItemEntity();
		workItemEntity.setBelongSystem("REMIT");
		workItemEntity
				.setResourceId("remit_return_619dda74-8b39-4177-b8af-8c2f336a");
		return workItemEntity;
	}

	public WorkItemService getWorkItemService() {
		return workItemService;
	}

	public void setWorkItemService(WorkItemService workItemService) {
		this.workItemService = workItemService;
	}
}
