/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.facade;

import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.function.dto.MenuModel;
import com.zbjdl.boss.admin.user.enums.UserTypeEnum;

/**
 * 类名称：SecurityFacade <br>
 * 类描述：权限门面<br>
 *
 * @author feng
 * @since 2012-4-1 下午5:54:15
 * @version v1.0
 */
public interface SecurityFacade {

	/**
	 * 获取用户菜单的方法
	 *
	 * @param userId ：用户ID
	 * @return
	 */
	public MenuModel getMenuTreeByUserId(Long userId);

	/**
	 * 权限检查
	 *
	 * @param userId     ：用户ID
	 * @param functionId ：功能ID
	 * @return
	 */
	public boolean checkPermission(Long userId, Long functionId);

	/**
	 * 权限检查
	 *
	 * @param userId ：用户ID
	 * @param uri    ：请求路径
	 * @return
	 */
	public boolean checkPermissionByUri(Long userId, String uri);

	/**
	 * 批量权限检查
	 *
	 * @param userId：用户ID
	 * @param uris：请求路径
	 * @return
	 */
	public boolean[] batchCheckPermission(Long userId, String... uris);

	/**
	 * 权限检查并返回对应的功能信息，如果用户无此功能访问权限或uri无对应功能，均返回null
	 *
	 * @param userId ：用户ID
	 * @param uri    ：请求资源
	 * @return
	 */
	public FunctionDTO checkPermissionAndReturnFunction(Long userId, String uri);

	/**
	 * 根据uri返回对应功能
	 *
	 * @param uri：请求资源
	 * @return
	 */
	public FunctionDTO getFunctionByUri(String uri);

	/**
	 * 销毁权限缓存
	 *
	 * @param userId ：用户ID
	 */
	public void destroyUserSecurityCache(Long userId);

	/**
	 * 销毁所有用户的权限缓存
	 */
	public void destoryAllUserSecurityCache();

	/**
	 * 验证用户类型
	 *
	 * @param userId：用户ID
	 * @param userType：用户类型
	 * @return
	 */
	@Deprecated
	public boolean checkUserType(Long userId, UserTypeEnum userType);

	/**
	 * 销毁所有默认客户端相关缓存
	 */
	public void destoryDefaultCache();

}
