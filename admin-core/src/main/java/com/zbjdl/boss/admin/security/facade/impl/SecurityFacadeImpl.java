/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.security.facade.impl;

import com.zbjdl.boss.admin.biz.SecurityBiz;
import com.zbjdl.boss.admin.facade.SecurityFacade;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.function.dto.MenuModel;
import com.zbjdl.boss.admin.user.enums.UserTypeEnum;
import com.zbjdl.common.utils.cache.remote.RemoteCacheUtils;

/**
 * @author feng
 * @since 2012-4-5 下午2:08:25
 * @version 1.0.0
 */
public class SecurityFacadeImpl implements SecurityFacade {
	/**
	 * 权限业务接口
	 */
	private SecurityBiz securityBiz;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityFacade#getMenuTreeByUserId
	 * (java.lang.Long)
	 */
	@Override
	public MenuModel getMenuTreeByUserId(Long userId) {
		return securityBiz.getMenuTreeByUserId(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityFacade#checkPermission(java
	 * .lang.Long, java.lang.Long)
	 */
	@Override
	public boolean checkPermission(Long userId, Long functionId) {
		return securityBiz.checkPermission(userId, functionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityFacade#checkPermission(java
	 * .lang.Long, java.lang.String)
	 */
	@Override
	public boolean checkPermissionByUri(Long userId, String uri) {
		return securityBiz.checkPermission(userId, uri);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityFacade#batchCheckPermission
	 * (java.lang.Long, java.lang.String[])
	 */
	@Override
	public boolean[] batchCheckPermission(Long userId, String... requestUris) {
		return securityBiz.batchCheckPermission(userId, requestUris);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zbjdl.boss.admin.facade.SecurityFacade#
	 * checkPermissionAndReturnFunction(java.lang.Long, java.lang.String)
	 */
	@Override
	public FunctionDTO checkPermissionAndReturnFunction(Long userId, String uri) {
		return securityBiz.checkPermissionAndReturnFunction(userId, uri);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityFacade#destroyUserSecurityCache
	 * (java.lang.Long)
	 */
	@Override
	public void destroyUserSecurityCache(Long userId) {
		securityBiz.destroyUserSecurityCache(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zbjdl.boss.admin.facade.SecurityFacade#
	 * destoryAllUserSecurityCache()
	 */
	@Override
	public void destoryAllUserSecurityCache() {
		securityBiz.destoryAllUserSecurityCache();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityFacade#getFunctionByUri(
	 * java.lang.String)
	 */
	@Override
	public FunctionDTO getFunctionByUri(String uri) {
		return securityBiz.getFunctionByUri(uri);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityFacade#checkUserType(java
	 * .lang.Long, com.zbjdl.boss.admin.user.enums.UserTypeEnum)
	 */
	@Override
	public boolean checkUserType(Long userId, UserTypeEnum userType) {
		return securityBiz.checkUserType(userId, userType);
	}

	/**
	 * @param securityBiz
	 *            the securityBiz to set
	 */
	public void setSecurityBiz(SecurityBiz securityBiz) {
		this.securityBiz = securityBiz;
	}

	@Override
	public void destoryDefaultCache() {
		RemoteCacheUtils.remove(RemoteCacheUtils
				.generateCacheKey("queryAllDepartments"));
	}
}
