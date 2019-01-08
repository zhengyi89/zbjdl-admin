/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.service;

import java.util.Map;

import com.zbjdl.boss.admin.facade.OperationLogFacade;
import com.zbjdl.boss.admin.facade.SecurityConfigFacade;
import com.zbjdl.boss.admin.facade.SecurityFacade;
import com.zbjdl.boss.admin.facade.UserFacade;
import com.zbjdl.boss.admin.facade.UserLoginFacade;
import com.zbjdl.boss.admin.facade.WorkItemFacade;

/**
 * 
 * 类名称：RemoteCallService <br>
 * 类描述： 远程调用服务接口 <br>
 * 
 * @author：feng
 * @since：2011-7-21 下午04:04:26
 * @version:
 * 
 */
public interface RemoteCallService {

	/**
	 * 
	 * 描述： 获取用户Facade对象
	 * 
	 * @return
	 */
	UserFacade getUserFacade();

	/**
	 * 
	 * 描述： 获取菜单图标Map
	 * 
	 * @return
	 */
	Map<String, String> getMenuIcons();

	/**
	 * 
	 * 描述： 获取操作日志Facade对象
	 * 
	 * @return
	 */
	OperationLogFacade getOperationLogFacade();

	/**
	 * 
	 * 描述：    获取单点登录Facade对象
	 * @return
	 */
	UserLoginFacade getUserLoginFacade();
	
	
	/**
	 * 
	 * 描述：    获取审核记录Facade对象
	 * @return
	 */
	WorkItemFacade getWorkItemFacade();
	
	/**
	 * 
	 * 描述：    重构后 权限Facade对象
	 * @return
	 */
	SecurityConfigFacade getSecurityConfigFacade();
	
	
	
	SecurityFacade getSecurityFacade();
	
	
	
}
