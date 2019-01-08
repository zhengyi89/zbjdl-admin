/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.repository;

import org.springframework.stereotype.Repository;

import com.zbjdl.boss.admin.operationlog.entity.OperationLogTemplateEntity;
import com.zbjdl.common.respository.mybatis.GenericRepository;

/**    
 *    
 * 类名称：OperationLogTemplateDao <br>    
 * 类描述： 操作日志模板dao接口<br>
 * @author：feng    
 * @since：2011-5-16 下午03:24:44 
 * @version:     
 *     
 */
@Repository
public interface OperationLogTemplateDao extends GenericRepository {

	OperationLogTemplateEntity queryByFunctionId(Long functionId);
}
