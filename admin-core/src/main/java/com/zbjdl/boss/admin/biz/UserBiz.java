/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.biz;

import java.util.List;

import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.ModifyPasswordDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.exception.ModifyPasswordException;
import com.zbjdl.boss.admin.user.exception.UserAuthenticationException;
import com.zbjdl.common.utils.cache.config.Cache;
import com.zbjdl.common.utils.cache.config.CacheTypeEnum;
import com.zbjdl.common.utils.cache.config.RemoteCache;

/**
 * 类名称：UserBiz <br>
 * 类描述：内部用户业务Biz接口<br>
 * 
 * @author feng
 * @since 2011-6-22 上午11:32:18
 * @version 1.0.0
 */
public interface UserBiz {

	/**
	 * 用户登录验证
	 * 
	 * @param loginName
	 * @param password
	 * @return 所需要的用户信息
	 */
	public UserDTO userLoginValidate(String loginName, String password)
			throws UserAuthenticationException;

	/**
	 * 用户身份验证
	 * 
	 * @param userId
	 * @param password
	 * @return 是否是管理员
	 */
	public boolean userAuthentication(Long userId, String password)
			throws UserAuthenticationException;

	/**
	 * 
	 * 描述： 根据用户ID查询用户
	 * 
	 * @param userId
	 * @return 用户信息
	 */
	public UserDTO queryUserById(Long userId);

	/**
	 * 
	 * 描述： 根据用户信息查找用户，UserDTO为空则全查
	 * 
	 * @param userDTO
	 * @return 用户信息列表
	 */
	public List<UserDTO> queryUser(UserDTO userDTO);

	/**
	 * 描述： 根据登录名查找用户
	 * 
	 * @param loginName
	 *            登录名
	 * @return 用户信息列表
	 */
	public UserDTO queryUserByLoginName(String loginName);

	/**
	 * 修改密码
	 * 
	 * @param modifyPasswordDTO
	 * @return 修改结果
	 */
	public boolean updatePassword(ModifyPasswordDTO modifyPasswordDTO)
			throws ModifyPasswordException;

	/**
	 * 
	 * 描述： 根据部门ID查找部门
	 * 
	 * @param departmentId
	 * @return 部门信息
	 */
	public DepartmentDTO queryDepartmentById(Long departmentId);

	/**
	 * 
	 * 描述： 根据部门信息查找部门
	 * 
	 * @param departmentDTO
	 * @return 部门列表
	 */
	public List<DepartmentDTO> queryDepartments(DepartmentDTO departmentDTO);

	/**
	 * 
	 * 描述： 获取所有的部门信息
	 * 
	 * @return
	 */
	@Cache(name = "queryAllDepartments", type = CacheTypeEnum.REMOTE, remote = @RemoteCache(time = 1800))
	public List<DepartmentDTO> queryAllDepartments();

	/**
	 * 
	 * 描述： 查询某个部门下的所有用户
	 * 
	 * @param departmentId
	 * @return 用户列表
	 */
	public List<UserDTO> queryDepartmentUsers(Long departmentId);

	/**
	 * 
	 * 描述： 通过用户名称 验证用户状态，只有活动的用户才会返回true
	 * 
	 * @param userName
	 * @return
	 */
	public boolean validateUserStatus(String userName);

	/**
	 * 更新人员信息
	 * 
	 * @param userDTO
	 *            :人员信息
	 */
	public void updateUserInfo(UserDTO userDTO);

	/**
	 * 重置密码
	 * 
	 * @param userId
	 *            :人员ID
	 * @param password
	 *            :重置后的密码
	 * @param resetReason
	 *            :重置原因
	 */
	public void resetPassword(Long userId, String password, String resetReason);

	/**
	 * 用户登录验证
	 * 
	 * @param mobile
	 * @param password
	 * @return 所需要的用户信息
	 */
	public UserDTO userLoginValidateByMobile(String mobile, String password);
}
