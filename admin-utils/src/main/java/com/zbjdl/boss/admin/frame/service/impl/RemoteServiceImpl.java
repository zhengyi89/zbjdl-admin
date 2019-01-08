/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.frame.service.impl;

import com.zbjdl.boss.admin.facade.OperationLogFacade;
import com.zbjdl.boss.admin.facade.SecurityConfigFacade;
import com.zbjdl.boss.admin.facade.SecurityFacade;
import com.zbjdl.boss.admin.facade.UserFacade;
import com.zbjdl.boss.admin.facade.UserLoginFacade;
import com.zbjdl.boss.admin.facade.WorkItemFacade;
import com.zbjdl.boss.admin.frame.service.RemoteService;
import com.zbjdl.boss.admin.frame.utils.Constants;
import com.zbjdl.common.dubbo.proxy.SoaServiceRepository;

/**    
 *    
 * 类名称：RemoteCallServiceImpl <br>    
 * 类描述： 远程调用servcie实现类<br>
 * @author：feng    
 * @since：2011-7-22 下午03:43:16 
 * @version:     
 *     
 */
public class RemoteServiceImpl implements RemoteService {
	
	private RemoteServiceImpl() {}
	
	private static RemoteServiceImpl INSTANCE;
	
	public static RemoteServiceImpl getInstance() {
		if (INSTANCE == null) {
			synchronized (RemoteServiceImpl.class) {
				if (INSTANCE == null) {
					INSTANCE = new RemoteServiceImpl();
				}
			}
		}
		return INSTANCE;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.frame.service.RemoteCallService#getOperationLogFacade()    
	 */
	@Override
	public OperationLogFacade getOperationLogFacade() {
		return SoaServiceRepository.getService(OperationLogFacade.class,Constants.DUBBO_DEFAULT_GROUP,Constants.DUBBO_DEFAULT_VERSION);
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.frame.service.RemoteCallService#getSSOFacade()    
	 */
	@Override
	public UserLoginFacade getUserLoginFacade() {
		return SoaServiceRepository.getService(UserLoginFacade.class,Constants.DUBBO_DEFAULT_GROUP,Constants.DUBBO_DEFAULT_VERSION);
	}


	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.frame.service.RemoteService#getWorkItemFacade()    
	 */
	@Override
	public WorkItemFacade getWorkItemFacade() {
		return SoaServiceRepository.getService(WorkItemFacade.class,Constants.DUBBO_DEFAULT_GROUP,Constants.DUBBO_DEFAULT_VERSION);
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.frame.service.RemoteService#getUserFacade()    
	 */
	@Override
	public UserFacade getUserFacade() {
		return SoaServiceRepository.getService(UserFacade.class,Constants.DUBBO_DEFAULT_GROUP,Constants.DUBBO_DEFAULT_VERSION);
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.frame.service.RemoteService#getSecurityFacade()    
	 */
	@Override
	public SecurityFacade getSecurityFacade() {
		return SoaServiceRepository.getService(SecurityFacade.class,Constants.DUBBO_DEFAULT_GROUP,Constants.DUBBO_DEFAULT_VERSION);
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.frame.service.RemoteService#getSecurityConfigFacade()    
	 */
	@Override
	public SecurityConfigFacade getSecurityConfigFacade() {
		return SoaServiceRepository.getService(SecurityConfigFacade.class,Constants.DUBBO_DEFAULT_GROUP,Constants.DUBBO_DEFAULT_VERSION);
	}

}
