/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.security.biz.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Maps;
import com.zbjdl.boss.admin.authority.entity.UserAndRoleRelationEntity;
import com.zbjdl.boss.admin.authority.service.UserAndRoleRelationService;
import com.zbjdl.boss.admin.biz.SecurityBiz;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.dto.MenuModel;
import com.zbjdl.boss.admin.function.service.FunctionService;
import com.zbjdl.boss.admin.function.service.MenuService;
import com.zbjdl.boss.admin.user.enums.UserTypeEnum;
import com.zbjdl.common.utils.StringUtils;
import com.zbjdl.common.utils.cache.remote.RemoteCacheUtils;

/**
 * 权限业务逻辑实现类
 *
 * @author feng
 * @version 1.0.0
 * @since 2012-4-5 下午2:16:12
 */
public class SecurityBizImpl implements SecurityBiz {

	/**
	 * 权限缓存客户端名称
	 */
	private static final String CACHE_CLIENT_NAME = "securityClient";

	/**
	 * 权限客户端
	 */
	private static final String AUTHORITIES_CLIENT = "AUTHORITIES_CLIENT_";

	/**
	 * 菜单客户端
	 */
	private static final String MENUS_CLIENT = "MENUS_CLIENT_";

	/**
	 * 缓存有效期 ，2个小时
	 */
	private static final int EXPIREDATE = 60 * 60 * 2;

	/**
	 * 日志
	 */
	private Log logger = LogFactory.getLog(getClass());

	/**
	 * 菜单服务
	 */
	private MenuService menuService;

	/**
	 * 功能服务
	 */
	private FunctionService functionService;

	/**
	 * 人员角色关系服务
	 */
	private UserAndRoleRelationService userAndRoleRelationService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityBiz#getMenuTreeByUserId(java.
	 * lang.Long)
	 */
	@Override
	public MenuModel getMenuTreeByUserId(Long userId) {
		String key = MENUS_CLIENT + userId;
		MenuModel menuModel = RemoteCacheUtils.getClient(CACHE_CLIENT_NAME, MenuModel.class, key);
		if (menuModel != null) {
			return menuModel;
		}
		// 调用存储方法
		return (MenuModel) storeUserAuthoritiesAndMenu(userId)[1];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityBiz#checkPermission(java.lang
	 * .Long, java.lang.Long)
	 */
	@Override
	public boolean checkPermission(Long userId, Long functionId) {
		Set<Long> authorities = getAuthoritiesByUserId(userId);
		return authorities.contains(functionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityBiz#checkPermission(java.lang
	 * .Long, java.lang.String)
	 */
	@Override
	public boolean checkPermission(Long userId, String uri) {
		FunctionDTO function = getFunctionByUri(uri);
		if (null == function) {
			return false;
		}
		return checkPermission(userId, function.getFunctionId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityBiz#batchCheckPermission(java
	 * .lang.Long, java.lang.String[])
	 */
	@Override
	public boolean[] batchCheckPermission(Long userId, String... uris) {
		if (uris != null && uris.length > 0) {
			boolean[] resultArry = new boolean[uris.length];
			// 所有功能
			List<FunctionDTO> fuctionList = functionService
					.queryFuntionByUserId(userId);
			// 用户拥有的功能ID
			Set<Long> authorities = getAuthoritiesByUserId(userId);
			for (int i = 0; i < uris.length; i++) {
				boolean result = false;
				for (FunctionDTO functionDTO : fuctionList) {
					// 只有功能包含此uri，同时用户有此uri对应功能访问权限时返回true
					if (StringUtils.isNotBlank(functionDTO.getFunctionUrl())
							&& functionDTO.getFunctionUrl().contains(uris[i])
							&& authorities
							.contains(functionDTO.getFunctionId())) {
						result = true;
						break;
					}
				}
				resultArry[i] = result;
			}
			return resultArry;
		} else {
			return new boolean[]{};
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityBiz#checkPermissionAndReturnFunction
	 * (java.lang.Long, java.lang.String)
	 */
	@Override
	public FunctionDTO checkPermissionAndReturnFunction(Long userId, String uri) {
		FunctionDTO function = getFunctionByUri(uri);
		if (null == function) {
			return null;
		}
		return checkPermission(userId, function.getFunctionId()) ? function
				: null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityBiz#getFunctionByUri(java.lang
	 * .String)
	 */
	@Override
	public FunctionDTO getFunctionByUri(String uri) {
		return functionService.queryFunctionByUri(uri);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityBiz#destroyUserSecurityCache(
	 * java.lang.Long)
	 */
	@Override
	public void destroyUserSecurityCache(Long userId) {
		String auKey = AUTHORITIES_CLIENT + userId;
		String menuKey = MENUS_CLIENT + userId;
		logger.info("destory user authorities by userId: {}." + userId);
		RemoteCacheUtils.removeClient(CACHE_CLIENT_NAME, auKey);
		logger.info("destory user menu info by userId: {}." + userId);
		RemoteCacheUtils.removeClient(CACHE_CLIENT_NAME, menuKey);
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.biz.SecurityBiz#destoryAllUserSecurityCache()    
	 */
	@Override
	public void destoryAllUserSecurityCache() {
		RemoteCacheUtils.clearClient(CACHE_CLIENT_NAME);
	}

	@SuppressWarnings("unchecked")
	private Set<Long> getAuthoritiesByUserId(Long userId) {
		String key = AUTHORITIES_CLIENT + userId;
		Set<Long> authorities = RemoteCacheUtils.getClient(CACHE_CLIENT_NAME, HashSet.class, key);
		if (authorities != null)
			return authorities;
		// 调用存储方法
		return (Set<Long>) storeUserAuthoritiesAndMenu(userId)[0];
	}

	/**
	 * 存储用户缓存信息
	 *
	 * @param userId 用户ID
	 * @return 用户权限及菜单
	 */
	private Object[] storeUserAuthoritiesAndMenu(Long userId) {
		// 加载权限
		String auKey = AUTHORITIES_CLIENT + userId;
		String menuKey = MENUS_CLIENT + userId;
		Map<Long, String> idUrlMap = Maps.newHashMap();// 记录用户拥有的functionId及对应的Url
		Set<Long> authorities = assemUserAuthorities(userId, idUrlMap);
		logger.info("store user authorities for user_id: {}." + userId);
		RemoteCacheUtils.putClient(CACHE_CLIENT_NAME, auKey, authorities, EXPIREDATE);

		// 加载菜单
		MenuModel root = assemMenuTreeByUserId(userId, idUrlMap);
		logger.info("store user menu info for user_id: {}." + userId);
		RemoteCacheUtils.putClient(CACHE_CLIENT_NAME, menuKey, root, EXPIREDATE);
		return new Object[]{authorities, root};
	}

	/**
	 * 根据用户id组织授权集合
	 *
	 * @param userId   ：用户ID
	 * @param idUrlMap ：记录用户拥有的functionId及对应的Url
	 * @return
	 */
	private Set<Long> assemUserAuthorities(Long userId, Map<Long, String> idUrlMap) {
		List<FunctionDTO> fuctionList = functionService.queryFuntionByUserId(userId);
		Set<Long> authorities = new HashSet<Long>(fuctionList.size());
		if (!fuctionList.isEmpty()) {
			for (FunctionDTO functionDTO : fuctionList) {
				authorities.add(functionDTO.getFunctionId());
				// 记录用户拥有的functionId及对应的Url
				idUrlMap.put(functionDTO.getFunctionId(),
						buildFunctionUrl(functionDTO));
			}
		}
		return authorities;
	}

	private String buildFunctionUrl(FunctionDTO functionDTO) {
		String functionUrl = functionDTO.getFunctionUrl();
		if (functionDTO.getPreFunctionId() != null) {
			if (StringUtils.contains(functionUrl, "?")) {
				functionUrl += "&belongSystem="
						+ functionDTO.getPreFunctionId().toString();
			} else {
				functionUrl += "?belongSystem="
						+ functionDTO.getPreFunctionId().toString();
			}
		}
		return functionUrl;
	}

	/**
	 * 根据用户id组织菜单树
	 *
	 * @param userId   ：用户ID
	 * @param idUrlMap ：用户拥有的functionId及对应的Url
	 * @return
	 */
	private MenuModel assemMenuTreeByUserId(Long userId,
	                                        Map<Long, String> idUrlMap) {
		MenuModel menuModel = new MenuModel();
		if (idUrlMap.isEmpty()) {
			return menuModel;
		}

		// 查询所有有权限的菜单
		List<MenuDTO> menuDTOList = menuService.queryMenusByUserId(userId);
		if (null == menuDTOList || menuDTOList.isEmpty()) {
			return menuModel;
		}

		// 遍历所有有权限的菜单
		Map<Long, MenuModel> menuIdMap = Maps.newHashMap();
		for (MenuDTO menuDTO : menuDTOList) {
			MenuModel subMenu = new MenuModel();
			subMenu.setFunctionUrl(idUrlMap.get(menuDTO.getFunctionId()));
			subMenu.setMenuDTO(menuDTO);
			if (0 == menuDTO.getParentId()) {
				menuModel.getSubMenus().add(subMenu);
			}
			menuIdMap.put(menuDTO.getMenuId(), subMenu);

			// 设置上下级关系
			MenuModel node = menuIdMap.get(menuDTO.getMenuId());
			MenuModel parentNode = menuIdMap.get(menuDTO.getParentId());
			if (null == node || null == parentNode)
				continue;
			parentNode.getSubMenus().add(node);
		}
		return menuModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityBiz#checkUserType(java.lang.Long,
	 * com.zbjdl.boss.admin.user.enums.UserTypeEnum)
	 */
	@Override
	public boolean checkUserType(Long userId, UserTypeEnum userType) {
		boolean ret = false;
		if (userType.equals(UserTypeEnum.SYSTEM_MANAGER)
				|| userType.equals(UserTypeEnum.DEPARTMENT_MANAGER)) {
			UserAndRoleRelationEntity userAndRoleRelationEntity = userAndRoleRelationService
					.queryByUserIdAndRoleId(userId, userType.getRoleId());
			if (userAndRoleRelationEntity != null) {
				ret = true;
			}
		} else {
			ret = true;
			List<UserAndRoleRelationEntity> userAndRoleRelationEntityList = userAndRoleRelationService
					.queryByUserId(userId);
			if (userAndRoleRelationEntityList != null
					&& !userAndRoleRelationEntityList.isEmpty()) {
				for (UserAndRoleRelationEntity UserAndRoleRelationEntity : userAndRoleRelationEntityList) {
					if (UserAndRoleRelationEntity.getRoleId().equals(
							UserTypeEnum.SYSTEM_MANAGER.getRoleId())
							|| UserAndRoleRelationEntity.getRoleId()
							.equals(UserTypeEnum.DEPARTMENT_MANAGER
									.getRoleId())) {
						ret = false;
						break;
					}
				}
			}
		}
		return ret;
	}

	/**
	 * @param menuService the menuService to set
	 */
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	/**
	 * @param functionService the functionService to set
	 */
	public void setFunctionService(FunctionService functionService) {
		this.functionService = functionService;
	}

	/**
	 * @param userAndRoleRelationService the userAndRoleRelationService to set
	 */
	public void setUserAndRoleRelationService(
			UserAndRoleRelationService userAndRoleRelationService) {
		this.userAndRoleRelationService = userAndRoleRelationService;
	}

}
