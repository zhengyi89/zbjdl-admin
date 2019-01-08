/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zbjdl.boss.admin.workitem.entity.WorkItemEntity;
import com.zbjdl.boss.admin.workitem.enums.WorkItemStatusEnum;
import com.zbjdl.common.respository.mybatis.GenericRepository;

/**    
 *    
 * 类名称：WorkItemDao <br>    
 * 类描述：   审核记录Dao接口<br>
 * @author：feng    
 * @since：2011-11-17 下午01:38:16 
 * @version:     
 *     
 */
@Repository
public interface WorkItemDao extends GenericRepository{

	/**
	 * 更新状态
	 * @param status 目标状态
	 * @param workItemId ID
	 */
	public void updateStatus(WorkItemStatusEnum status, Long workItemId);

	public List<WorkItemEntity> queryByResourceId(String resourceId);

	public List<WorkItemEntity> findList(WorkItemEntity entity);
}
