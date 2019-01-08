/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.frame.service;

import com.zbjdl.boss.admin.facade.OperationLogFacade;
import com.zbjdl.boss.admin.facade.SecurityConfigFacade;
import com.zbjdl.boss.admin.facade.SecurityFacade;
import com.zbjdl.boss.admin.facade.UserFacade;
import com.zbjdl.boss.admin.facade.UserLoginFacade;
import com.zbjdl.boss.admin.facade.WorkItemFacade;

/**    
 *    
 * 类名称：RemoteCallService <br>    
 * 类描述：远程调用Service接口<br>
 * @author：feng    
 * @since：2011-7-22 下午03:13:13 
 * @version:     
 *     
 */
public interface RemoteService {
	
	/**
	 * 
	 * 描述：    获取操作日志Facade对象
	 * @return
	 */
	public OperationLogFacade getOperationLogFacade();

	/**
	 * 
	 * 描述：    获取单点登录Facade对象
	 * @return
	 */
	public UserLoginFacade getUserLoginFacade();
	
	
	/**
	 * 描述：    获取双重复核Facade对象
	 * @return
	 */
	public WorkItemFacade getWorkItemFacade();
	
	/**
	 *  描述：获取功能Facade对象
	 * @return
	 */
	public UserFacade getUserFacade();
	
	/**
	 * 描述：获取安全接口
	 * @return
	 */
	public SecurityFacade getSecurityFacade();
	
	/**
	 * 描述：获取安全配置接口
	 * @return
	 */
	public SecurityConfigFacade getSecurityConfigFacade();

}
