/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.service.impl;

import java.util.Map;

import com.zbjdl.boss.admin.facade.OperationLogFacade;
import com.zbjdl.boss.admin.facade.SecurityConfigFacade;
import com.zbjdl.boss.admin.facade.SecurityFacade;
import com.zbjdl.boss.admin.facade.UserFacade;
import com.zbjdl.boss.admin.facade.UserLoginFacade;
import com.zbjdl.boss.admin.facade.WorkItemFacade;
import com.zbjdl.boss.admin.service.RemoteCallService;
import com.zbjdl.common.dubbo.proxy.SoaServiceRepository;
import com.zbjdl.common.utils.config.ConfigParam;
import com.zbjdl.common.utils.config.ConfigurationUtils;

/**
 * 
 * 类名称：RemoteCallServiceImpl <br>
 * 类描述： <br>
 * 
 * @author：feng
 * @since：2011-7-21 下午04:06:14
 * @version:
 * 
 */
public class RemoteCallServiceImpl implements RemoteCallService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zbjdl.boss.admin.service.RemoteCallService#getUserFacade()
	 */
	public UserFacade getUserFacade() {
		UserFacade userFacade = SoaServiceRepository
				.getService(UserFacade.class);
		return userFacade;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zbjdl.boss.admin.service.RemoteCallService#getMenuIcons()
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getMenuIcons() {
		ConfigParam<Map<String, String>> param = ConfigurationUtils
				.getAppConfigParam("admin.menu.icons");
		return param.getValue();
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.service.RemoteCallService#getOperationLogFacade()    
	 */
	@Override
	public OperationLogFacade getOperationLogFacade() {
		return SoaServiceRepository.getService(OperationLogFacade.class);
	}
	

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.service.RemoteCallService#getUserLoginFacade()    
	 */
	@Override
	public UserLoginFacade getUserLoginFacade() {
		return SoaServiceRepository.getService(UserLoginFacade.class);
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.service.RemoteCallService#getWorkItemFacade()    
	 */
	@Override
	public WorkItemFacade getWorkItemFacade() {
		return SoaServiceRepository.getService(WorkItemFacade.class);
	}

	@Override
	public SecurityConfigFacade getSecurityConfigFacade() {
		return SoaServiceRepository.getService(SecurityConfigFacade.class);
	}

	@Override
	public SecurityFacade getSecurityFacade() {
		return SoaServiceRepository.getService(SecurityFacade.class);
	}


}
