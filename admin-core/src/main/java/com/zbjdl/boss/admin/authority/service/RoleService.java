/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.authority.service;

import com.zbjdl.boss.admin.role.dto.RoleDTO;

import java.util.List;

/**    
 *    
 * 类名称：RoleService <br>    
 * 类描述：  角色管理 <br>
 * @author feng
 * @since 2011-7-5 上午11:30:40
 * @version 1.0.0
 *     
 */
public interface RoleService {
	
	/**
	 * 
	 * 描述：  创建角色  
	 * @param roleDTO 角色信息
	 * @return 角色id
	 */
	public Long createRole(RoleDTO roleDTO);
	
	/**
	 * 
	 * 描述：    删除角色
	 * @param roleId 角色id
	 * @return 删除结果
	 */
	public void deleteRole(Long roleId);
	
	/**
	 * 
	 * 描述：    修改角色
	 * @param roleDTO
	 * @return 修改结果
	 */
	public void updateRole(RoleDTO roleDTO);
	
	/**
	 * 
	 * 描述：    通过roleId查询角色
	 * @param roleId
	 * @return 角色信息
	 */
	public RoleDTO queryRoleById(Long roleId);
	
	/**
	 * 
	 * 描述：    通过角色信息查找角色
	 * @param roleDTO 角色信息
	 * @return 角色列表
	 */
	public List<RoleDTO> queryRole(RoleDTO roleDTO);
	
	/**
	 * 
	 * 描述： 解冻角色   
	 * @param roleId
	 */
	public void activateRole(Long roleId);
	
	/**
	 * 
	 * 描述：    冻结角色
	 * @param roleId
	 */
	public void frozenRole(Long roleId);
	
	/**
	 * 
	 * 描述：   废弃角色 
	 * @param roleId
	 */
	public void forbidRole(Long roleId);
	
	/**
	 * 查询用户角色
	 * @param userId ：用户ID
	 * @return
	 */
	public List<RoleDTO> queryRolesByUser(Long userId);
	
	/**
	 * 查询部门功能
	 * @param departmentId ：部门ID
	 * @return
	 */
	public List<RoleDTO> queryRolesByDeparment(Long departmentId);

	/**
	 * 迁移角色
	 *
	 * @param departmentId 待迁移部门ID
	 * @param newDepartmentId 拟迁移入的部门ID
	 * @return
	 */
	public boolean migrateRole(Long departmentId, Long newDepartmentId);

	/**
	 * 删除部门拥有的角色
	 * @param departmentId ：部门ID
	 */
	public void deleteRoleByDepartmentId(Long departmentId);

}
