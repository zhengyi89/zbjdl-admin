/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.workitem.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.repository.WorkItemDao;
import com.zbjdl.boss.admin.repository.WorkItemTemplateDao;
import com.zbjdl.boss.admin.workitem.dto.WorkItemDTO;
import com.zbjdl.boss.admin.workitem.dto.WorkItemTemplateDTO;
import com.zbjdl.boss.admin.workitem.entity.WorkItemEntity;
import com.zbjdl.boss.admin.workitem.entity.WorkItemTemplateEntity;
import com.zbjdl.boss.admin.workitem.enums.WorkItemStatusEnum;
import com.zbjdl.boss.admin.workitem.service.WorkItemService;

/**
 *
 * 类名称：WorkItemServiceImpl <br>
 * 类描述： <br>
 *
 * @author：feng
 * @since：2011-11-17 下午02:03:16
 * @version:
 *
 */
public class WorkItemServiceImpl implements WorkItemService {

	private WorkItemDao workItemDao;

	private WorkItemTemplateDao workItemTemplateDao;

	private Convert<WorkItemDTO, WorkItemEntity> workItemConvert;

	private Convert<WorkItemTemplateDTO, WorkItemTemplateEntity> workItemTemplateConvert;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.workitem.service.WorkItemService#createWorkItem
	 * (com.zbjdl.boss.admin.workitem.dto.WorkItemDTO)
	 */
	@Override
	public Long createWorkItem(WorkItemDTO workItemDTO) {
		WorkItemEntity workItemEntity = workItemConvert.convert(workItemDTO);
		workItemEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
		workItemDao.save(workItemEntity);
		return workItemEntity.getWorkItemId();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.workitem.service.WorkItemService#updateWorkItem
	 * (com.zbjdl.boss.admin.workitem.dto.WorkItemDTO)
	 */
	@Transactional
	@Override
	public void updateWorkItem(WorkItemDTO workItemDTO) {
		WorkItemEntity workItemEntity = workItemConvert.convert(workItemDTO);
		workItemEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		workItemDao.update(workItemEntity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.workitem.service.WorkItemService#queryById
	 * (java.lang.Long)
	 */
	@Override
	public WorkItemDTO queryById(Long workItemId) {
		WorkItemEntity workItemEntity = workItemDao.selectById(workItemId);
		return workItemConvert.convert(workItemEntity);
	}

	@Override
	public List<WorkItemEntity> queryWorkItemByResourceId(String resourceId) {
		@SuppressWarnings("unchecked")
		List<WorkItemEntity> workItemEntity = (List<WorkItemEntity>) workItemDao.queryByResourceId(resourceId);
		return workItemEntity;
	}

	@Override
	public WorkItemTemplateDTO queryTemplateByFunctionId(Long functionId) {
		WorkItemTemplateEntity workItemTemplateEntity = workItemTemplateDao
				.queryTemplateByFunctionId(functionId);
		return workItemTemplateConvert.convert(workItemTemplateEntity);
	}

	@Override
	public List<WorkItemEntity> getWorkItemByObject(WorkItemEntity entity) {
		@SuppressWarnings("unchecked")
		List<WorkItemEntity> workItemEntity = (List<WorkItemEntity>) workItemDao.findList(entity);
		return workItemEntity;
	}

	@Override
	public void addTemplate(WorkItemTemplateDTO workItemTemplateDTO) {
		workItemTemplateDao.save(workItemTemplateConvert.convert(workItemTemplateDTO));
	}


	@Override
	public void deleteTemplate(Long templateId) {
		workItemTemplateDao.deleteByTemplateId(templateId);
	}


	@Override
	public void updateTemplate(Long templateId,
			WorkItemTemplateDTO workItemTemplateDTO) {
		WorkItemTemplateEntity entity = workItemTemplateConvert.convert(workItemTemplateDTO);
		entity.setId(templateId);
		workItemTemplateDao.update(entity);
	}


	@Override
	public WorkItemTemplateDTO queryTemplate(Long templateId) {
		WorkItemTemplateEntity entity = workItemTemplateDao.selectById(templateId);
		return workItemTemplateConvert.convert(entity);
	}


	/**
	 * workItemDao
	 *
	 * @return the workItemDao
	 */

	public WorkItemDao getWorkItemDao() {
		return workItemDao;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param workItemDao
	 *            the workItemDao to set
	 */
	public void setWorkItemDao(WorkItemDao workItemDao) {
		this.workItemDao = workItemDao;
	}

	public WorkItemTemplateDao getWorkItemTemplateDao() {
		return workItemTemplateDao;
	}

	public void setWorkItemTemplateDao(WorkItemTemplateDao workItemTemplateDao) {
		this.workItemTemplateDao = workItemTemplateDao;
	}

	/**
	 * workItemConvert
	 *
	 * @return the workItemConvert
	 */

	public Convert<WorkItemDTO, WorkItemEntity> getWorkItemConvert() {
		return workItemConvert;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param workItemConvert
	 *            the workItemConvert to set
	 */
	public void setWorkItemConvert(
			Convert<WorkItemDTO, WorkItemEntity> workItemConvert) {
		this.workItemConvert = workItemConvert;
	}

	public Convert<WorkItemTemplateDTO, WorkItemTemplateEntity> getWorkItemTemplateConvert() {
		return workItemTemplateConvert;
	}

	public void setWorkItemTemplateConvert(
			Convert<WorkItemTemplateDTO, WorkItemTemplateEntity> workItemTemplateConvert) {
		this.workItemTemplateConvert = workItemTemplateConvert;
	}

	@Override
	public void updateStatus(WorkItemStatusEnum status, Long workItemId) {
		workItemDao.updateStatus(status, workItemId);
	}
}
