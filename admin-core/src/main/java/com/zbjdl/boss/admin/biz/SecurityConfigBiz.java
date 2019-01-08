/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.biz;

import java.util.List;
import java.util.Map;

import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.enums.MenuDirectionEnum;
import com.zbjdl.boss.admin.role.dto.RoleDTO;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.enums.UserTypeEnum;

/**
 * @author feng
 * @version 1.0.0
 * @since 2012-4-6 下午2:26:29
 */
public interface SecurityConfigBiz {

	/**
	 * 切换部门
	 * 
	 * @param userId
	 *            ：用户ID
	 * @param newDepartmnetId
	 *            ：新部门
	 */
	public void changeDepartment(Long userId, Long newDepartmnetId);

	/**
	 * 迁移部门
	 * 
	 * @param departmentId
	 *            待迁移部门 Id
	 * @param newDepartmentId
	 *            拟迁移到部门 Id
	 * @param migrateUser
	 *            是否迁移用户
	 * @param migrateRole
	 *            是否迁移角色
	 * @param migrateFunction
	 *            是否迁移功能
	 * @author feng
	 * @since 2013-06-27 15:50:26
	 */
	public void migrateDepartment(Long departmentId, Long newDepartmentId,
			boolean migrateUser, boolean migrateRole, boolean migrateFunction);

	/**
	 * 查询功能
	 * 
	 * @param functionDTO
	 * @return
	 */
	public List<FunctionDTO> queryFunction(FunctionDTO functionDTO);

	/**
	 * 获取角色信息
	 * 
	 * @param roleId
	 *            ：角色ID
	 * @return
	 */
	public RoleDTO getRoleInfo(Long roleId);

	/**
	 * 根据条件查询角色信息
	 * 
	 * @param roleDTO
	 *            ：角色信息
	 * @return
	 */
	public List<RoleDTO> queryRoles(RoleDTO roleDTO);

	/**
	 * 获取菜单信息
	 * 
	 * @param menuId
	 *            ：菜单ID
	 * @return
	 */
	public MenuDTO getMenuInfo(Long menuId);

	/**
	 * 根据条件查询菜单
	 * 
	 * @param menuDTO
	 *            ：菜单信息
	 * @return
	 */
	public List<MenuDTO> queryMenu(MenuDTO menuDTO);

	/**
	 * 添加菜单
	 * 
	 * @param menuDTO
	 *            ：菜单信息
	 */
	public void addMenu(MenuDTO menuDTO);

	/**
	 * 删除菜单
	 * 
	 * @param menuId
	 */
	public void deleteMenu(Long menuId);

	/**
	 * 描述：移动菜单
	 * 
	 * @param menuId
	 *            被移动的菜单Id
	 * @param direction
	 *            移动方向
	 */
	void moveMenu(Long menuId, MenuDirectionEnum direction);

	/**
	 * 更新菜单
	 * 
	 * @param menuDTO
	 */
	public void updateMenu(MenuDTO menuDTO);

	/**
	 * 获取功能信息
	 * 
	 * @param functionId
	 * @return
	 */
	public FunctionDTO getFunctionInfo(Long functionId);

	/**
	 * 根据功能Id查询所有部门名称
	 * 
	 * @param functionId
	 *            ：功能ID
	 * @return
	 */
	public List<DepartmentDTO> queryDepartmentsByFunctionId(Long functionId);

	/**
	 * 更新功能
	 * 
	 * @param functionDTO
	 *            ：功能信息
	 */
	public void updateFunction(FunctionDTO functionDTO);

	/**
	 * 新增功能
	 * 
	 * @param functionDTO
	 *            ：功能信息
	 */
	public void addFunction(FunctionDTO functionDTO);

	/**
	 * 添加功能和部门功能关系
	 * 
	 * @param functionDTO
	 *            ：功能信息
	 */
	public void addAuditFunction(FunctionDTO functionDTO, UserDTO userDTO);

	/**
	 * 删除功能
	 * 
	 * @param functionId
	 *            ：功能ID
	 */
	public void deleteFunction(Long functionId);

	/**
	 * 查询所有功能
	 * 
	 * @return
	 */
	public List<FunctionDTO> queryAllFunction();

	/**
	 * 查询可展示的function：排除基本角色、系统管理员角色、部门管理员角色拥有的功能
	 * 
	 * @return
	 */
	public List<FunctionDTO> queryDisplayFunction();

	/**
	 * 获取用户拥有的功能
	 * 
	 * @param userId
	 *            ：用户ID
	 * @return
	 */
	public List<FunctionDTO> queryFunctionByUser(Long userId);

	/**
	 * 获取用户拥有以及满足Function的功能
	 * 
	 * @param departmentId
	 *            ：用户ID
	 * @param functionId
	 *            ：用户ID
	 * @return
	 */
	public List<FunctionDTO> queryFunctionByDepartmentAndFunctionId(
			Long departmentId, Long functionId);

	/**
	 * 获取角色拥有的功能
	 * 
	 * @param roleId
	 *            ：角色ID
	 * @return
	 */
	public List<FunctionDTO> queryFunctionByRole(Long roleId);

	/**
	 * 获取用户拥有的角色
	 * 
	 * @param userId
	 *            ：用户ID
	 * @return
	 */
	public List<RoleDTO> queryRolesByUser(Long userId);

	/**
	 * 获取部门拥有的角色
	 * 
	 * @param departmentId
	 *            ：部门ID
	 * @return
	 */
	public List<RoleDTO> queryRolesByDeparment(Long departmentId);
	
	/**
	 * 获取角色下的人
	 * @param roleId
	 * @return
	 */
	public List<UserDTO> queryUsersByRole(Long roleId);

	/**
	 * 获取部门拥有的功能
	 * 
	 * @param departmentId
	 *            ：部门ID
	 * @return
	 */
	public List<FunctionDTO> queryFunctionByDepartment(Long departmentId);

	/**
	 * 配置部门功能
	 * 
	 * @param departmentDTO
	 *            ：部门信息
	 * @param functionIds
	 *            ：功能ID集合
	 */
	public void configDepartmentFunction(DepartmentDTO departmentDTO,
			Long[] functionIds);

	/**
	 * 配置角色功能
	 * 
	 * @param roleDTO
	 *            ：角色信息
	 * @param functionIds
	 *            ：功能集合
	 */
	public void configRoleFunction(RoleDTO roleDTO, Long[] functionIds);

	/**
	 * 配置人员角色（用于跨部门分配权限）
	 * 
	 * @param departmentId
	 *            ：操作人员所在部门ID
	 * @param userId
	 *            ：人员ID
	 * @param roleIds
	 *            ：角色ID
	 */
	public void configUserRoles(Long departmentId, Long userId, Long[] roleIds);

	/**
	 * 为操作员添加新角色(如果已存在则不添加)
	 * 
	 * @param userId
	 *            ：人员ID
	 * @param roleId
	 *            ：角色ID
	 */
	public void configUserRoles(Long userId, Long roleId);

	/**
	 * 添加人员
	 * 
	 * @param userDTO
	 *            ：人员
	 * @param userType
	 *            ：人员类型
	 */
	public Long addUser(UserDTO userDTO, UserTypeEnum userType);

	/**
	 * 删除人员
	 * 
	 * @param userId
	 */
	public void deleteUser(Long userId);

	/**
	 * 冻结人员
	 * 
	 * @param userId
	 *            ：人员ID
	 * @param frozenReason
	 *            ：冻结原因
	 * @param operatorId
	 *            ：操作人员ID
	 */
	public void frozeUser(Long userId, String frozenReason, Long operatorId);

	/**
	 * 解冻人员
	 * 
	 * @param userId
	 *            ：人员ID
	 * @param activateReason
	 *            ：解冻原因
	 * @param operatorId
	 *            ：操作人员ID
	 */
	public void unFrozeUser(Long userId, String activateReason, Long operatorId);

	/**
	 * 获取依赖关系
	 * 
	 * @return obj[0]：节点及依赖节点映射 ，obj[1]：节点及依赖此节点的映射
	 */
	public Map<Long, Long[]>[] getDependenceRelation();

	/**
	 * 删除部门
	 * 
	 * @param departmentId
	 *            ：部门ID
	 */
	public void deleteDepartment(Long departmentId);

	/**
	 * 删除角色
	 * 
	 * @param roleId
	 *            ：角色ID
	 */
	public void deleteRole(Long roleId);

	/**
	 * 冻结角色
	 * 
	 * @param roleId
	 *            ：角色ID
	 */
	public void frozeRole(Long roleId);

	/**
	 * 解冻角色
	 * 
	 * @param roleId
	 *            :：角色ID
	 */
	public void unFrozeRole(Long roleId);

	/**
	 * 冻结功能
	 * 
	 * @param functionId
	 *            ：功能ID
	 */
	public void frozeFunction(Long functionId);

	/**
	 * 解冻功能
	 * 
	 * @param functionId
	 *            ：功能ID
	 */
	public void unFrozeFunction(Long functionId);

	/**
	 * 设置人员身份
	 * 
	 * @param userId
	 *            :人员ID
	 * @param userType
	 *            :人员类型
	 */
	public void changeUserType(Long userId, UserTypeEnum userType);

	/**
	 * 添加部门功能关系
	 * 
	 * @param functionId
	 *            :功能ID
	 * @param departmentIds
	 *            :部门ID
	 */
	public void assignFunctionToDept(Long functionId, List<Long> departmentIds);

	/**
	 * 添加角色功能关系
	 * 
	 * @param functionId
	 *            :功能ID
	 * @param roleIds
	 *            :角色ID
	 */
	public void assignFunctionToRole(Long functionId, List<Long> roleIds);

	/**
	 * 删除部门功能关系
	 * 
	 * @param functionId
	 *            :功能ID
	 * @param departmentId
	 *            :部门ID
	 */
	public void deleteDepartmentAndFunctionRelation(Long departmentId,
			Long functionId);

	/**
	 * 删除角色功能关系
	 * 
	 * @param functionId
	 *            :功能ID
	 * @param roleId
	 *            :角色ID
	 */
	public void deleteRoleAndFunctionRelation(Long roleId, Long functionId);

	/**
	 * 查询部门功能关系
	 * 
	 * @param functionId
	 *            :功能ID
	 */
	public List<Long> queryDepartmentAndFunctionRelation(Long functionId);

	/**
	 * 查询角色功能关系
	 * 
	 * @param functionId
	 *            :功能ID
	 */
	public List<Long> queryByFunctionId(Long functionId);

	/**
	 * 删除部门功能关系
	 * 
	 * @param functionId
	 *            :功能ID
	 */
	public void deleteDepartmentAndFunctionRelation(Long functionId);

	/**
	 * 删除角色功能关系
	 * 
	 * @param functionId
	 *            :功能ID
	 */
	public void deleteRoleAndFunctionRelation(Long functionId);

	/**
	 * 根据所复核的功能ID查询功能
	 * 
	 * @param checkFunctionId
	 *            :所复核的功能ID
	 */
	public List<FunctionDTO> queryByCheckFunctionId(Long checkFunctionId);

	/**
	 * 根据所复核的功能ID删除功能
	 * 
	 * @param checkFunctionId
	 *            :所复核的功能ID
	 */
	public void deleteByCheckFunctionId(Long checkFunctionId);

	/**
	 * 根据所复核的功能ID和功能类型删除功能
	 * 
	 * @param checkFunctionId
	 *            :所复核的功能ID
	 * @param functionType
	 *            ：功能类型
	 */
	public void deleteByCheckFunctionIdAndType(Long checkFunctionId,
			String functionType);

}
