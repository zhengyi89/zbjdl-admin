/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.repository;

import org.springframework.stereotype.Repository;

import com.zbjdl.boss.admin.workitem.entity.WorkItemTemplateEntity;
import com.zbjdl.common.respository.mybatis.GenericRepository;

/**
 *
 * @author：feng
 * @since：2012-6-8 上午09:57:36
 * @version:
 */
@Repository
public interface WorkItemTemplateDao extends GenericRepository {


	public void deleteByTemplateId(Long id);

	public WorkItemTemplateEntity queryTemplateByFunctionId(Long functionId);
}
