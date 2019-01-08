/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.security.facade.impl;

import com.zbjdl.boss.admin.biz.SecurityConfigBiz;
import com.zbjdl.boss.admin.facade.SecurityConfigFacade;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.enums.MenuDirectionEnum;
import com.zbjdl.boss.admin.role.dto.RoleDTO;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.enums.UserTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * @author feng
 * @since 2012-4-6 下午2:22:02
 * @version 1.0.0
 */
public class SecurityConfigFacadeImpl implements SecurityConfigFacade {

	private SecurityConfigBiz securityConfigBiz;

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.facade.SecurityConfigFacade#changeDepartment(java.lang.Long, java.lang.Long)    
	 */
	@Override
	public void changeDepartment(Long userId, Long newDepartmnetId) {
		securityConfigBiz.changeDepartment(userId, newDepartmnetId);
	}

	@Override
	public void migrateDepartment(Long departmentId, Long newDepartmentId, boolean migrateUser, boolean migrateRole, boolean migrateFunction) {
		securityConfigBiz.migrateDepartment(departmentId, newDepartmentId, migrateUser, migrateRole, migrateFunction);
	}

	public List<FunctionDTO> queryFunction(FunctionDTO functionDTO) {
		return securityConfigBiz.queryFunction(functionDTO);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#getRoleInfo
	 * (java.lang.Long)
	 */
	@Override
	public RoleDTO getRoleInfo(Long roleId) {
		return securityConfigBiz.getRoleInfo(roleId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#queryRoles(
	 * com.zbjdl.boss.admin.role.dto.RoleDTO)
	 */
	@Override
	public List<RoleDTO> queryRoles(RoleDTO roleDTO) {
		return securityConfigBiz.queryRoles(roleDTO);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#getMenuInfo
	 * (java.lang.Long)
	 */
	@Override
	public MenuDTO getMenuInfo(Long menuId) {
		return securityConfigBiz.getMenuInfo(menuId);
	}


	@Override
	public List<MenuDTO> queryMenu(MenuDTO menuDTO) {
		return securityConfigBiz.queryMenu(menuDTO);
	}

	
	@Override
	public void addMenu(MenuDTO menuDTO) {
		securityConfigBiz.addMenu(menuDTO);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#deleteMenu(
	 * java.lang.Long)
	 */
	@Override
	public void deleteMenu(Long menuId) {
		securityConfigBiz.deleteMenu(menuId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#moveMenu(java
	 * .lang.Long,
	 * com.zbjdl.boss.admin.function.enums.MenuDirectionEnum)
	 */
	@Override
	public void moveMenu(Long menuId, MenuDirectionEnum direction) {
		securityConfigBiz.moveMenu(menuId, direction);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#updateMenu(
	 * com.zbjdl.boss.admin.function.dto.MenuDTO)
	 */
	@Override
	public void updateMenu(MenuDTO menuDTO) {
		securityConfigBiz.updateMenu(menuDTO);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#getFunctionInfo
	 * (java.lang.Long)
	 */
	@Override
	public FunctionDTO getFunctionInfo(Long functionId) {
		return securityConfigBiz.getFunctionInfo(functionId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zbjdl.boss.admin.facade.SecurityConfigFacade#
	 * queryDepartmentsByFunctionId(java.lang.Long)
	 */
	@Override
	public List<DepartmentDTO> queryDepartmentsByFunctionId(Long functionId) {
		return securityConfigBiz.queryDepartmentsByFunctionId(functionId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#updateFunction
	 * (com.zbjdl.boss.admin.function.dto.FunctionDTO)
	 */
	@Override
	public void updateFunction(FunctionDTO functionDTO) {
		securityConfigBiz.updateFunction(functionDTO);
	}

	@Override
	public void addFunction(FunctionDTO functionDTO) {
		securityConfigBiz.addFunction(functionDTO);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#deleteFunction
	 * (java.lang.Long)
	 */
	@Override
	public void deleteFunction(Long functionId) {
		securityConfigBiz.deleteFunction(functionId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zbjdl.boss.admin.facade.SecurityConfigFacade#
	 * queryAllFunction()
	 */
	@Override
	public List<FunctionDTO> queryAllFunction() {
		return securityConfigBiz.queryAllFunction();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zbjdl.boss.admin.facade.SecurityConfigFacade#
	 * queryDisplayFunction()
	 */
	@Override
	public List<FunctionDTO> queryDisplayFunction() {
		return securityConfigBiz.queryDisplayFunction();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zbjdl.boss.admin.facade.SecurityConfigFacade#
	 * queryFunctionByUser(java.lang.Long)
	 */
	@Override
	public List<FunctionDTO> queryFunctionByUser(Long userId) {
		return securityConfigBiz.queryFunctionByUser(userId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zbjdl.boss.admin.facade.SecurityConfigFacade#
	 * queryFunctionByRole(java.lang.Long)
	 */
	@Override
	public List<FunctionDTO> queryFunctionByRole(Long roleId) {
		return securityConfigBiz.queryFunctionByRole(roleId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#queryRolesByUser
	 * (java.lang.Long)
	 */
	@Override
	public List<RoleDTO> queryRolesByUser(Long userId) {
		return securityConfigBiz.queryRolesByUser(userId);
	}

	@Override
	public List<FunctionDTO> queryFunctionByDepartmentAndFunctionId(
			Long departmentId, Long functionId) {
		return securityConfigBiz.queryFunctionByDepartmentAndFunctionId(
				departmentId, functionId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zbjdl.boss.admin.facade.SecurityConfigFacade#
	 * queryRolesByDeparment(java.lang.Long)
	 */
	@Override
	public List<RoleDTO> queryRolesByDeparment(Long departmentId) {
		return securityConfigBiz.queryRolesByDeparment(departmentId);
	}
	
	@Override
	public List<UserDTO> queryUsersByRole(Long departmentId) {
		return securityConfigBiz.queryUsersByRole(departmentId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zbjdl.boss.admin.facade.SecurityConfigFacade#
	 * queryFunctionByDepartment(java.lang.Long)
	 */
	@Override
	public List<FunctionDTO> queryFunctionByDepartment(Long departmentId) {
		return securityConfigBiz.queryFunctionByDepartment(departmentId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zbjdl.boss.admin.facade.SecurityConfigFacade#
	 * configDepartmentFunction
	 * (com.zbjdl.boss.admin.user.dto.DepartmentDTO, java.lang.Long[])
	 */
	@Override
	public void configDepartmentFunction(DepartmentDTO departmentDTO,
	                                     Long[] functionIds) {
		securityConfigBiz.configDepartmentFunction(departmentDTO, functionIds);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#configRoleFunction
	 * (com.zbjdl.boss.admin.role.dto.RoleDTO, java.lang.Long[])
	 */
	@Override
	public void configRoleFunction(RoleDTO roleDTO, Long[] functionIds) {
		securityConfigBiz.configRoleFunction(roleDTO, functionIds);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#configUserRoles
	 * (java.lang.Long, java.lang.Long[])
	 */
	@Override
	public void configUserRoles(Long departmentId, Long userId, Long[] roleIds) {
		securityConfigBiz.configUserRoles(departmentId, userId, roleIds);
	}

	@Override
	public void configRoleUsers(Long departmentId, List<Long> userIds, Long roleId) {
		List<RoleDTO> roleDTOList = securityConfigBiz.queryRolesByDeparment(departmentId);
		for(RoleDTO roleDTO : roleDTOList) {
			if(0 == roleDTO.getRoleId().compareTo(roleId)) {
				for(Long userId : userIds) {
					securityConfigBiz.configUserRoles(userId, roleId);
				}
				break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zbjdl.boss.admin.facade.SecurityConfigFacade#
	 * getDependenceRelation()
	 */
	@Override
	public Map<Long, Long[]>[] getDependenceRelation() {
		return securityConfigBiz.getDependenceRelation();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#deleteDepartment
	 * (java.lang.Long)
	 */
	@Override
	public void deleteDepartment(Long departmentId) {
		securityConfigBiz.deleteDepartment(departmentId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#deleteRole(
	 * java.lang.Long)
	 */
	@Override
	public void deleteRole(Long roleId) {
		securityConfigBiz.deleteRole(roleId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#frozeRole(java
	 * .lang.Long)
	 */
	@Override
	public void frozeRole(Long roleId) {
		securityConfigBiz.frozeRole(roleId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#unFrozeRole
	 * (java.lang.Long)
	 */
	@Override
	public void unFrozeRole(Long roleId) {
		securityConfigBiz.unFrozeRole(roleId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#frozeFunction
	 * (java.lang.Long)
	 */
	@Override
	public void frozeFunction(Long functionId) {
		securityConfigBiz.frozeFunction(functionId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#unFrozeFunction
	 * (java.lang.Long)
	 */
	@Override
	public void unFrozeFunction(Long functionId) {
		securityConfigBiz.unFrozeFunction(functionId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.facade.SecurityConfigFacade#changeUserType
	 * (java.lang.Long, com.zbjdl.boss.admin.user.enums.UserTypeEnum)
	 */
	@Override
	public void changeUserType(Long userId, UserTypeEnum userType) {
		securityConfigBiz.changeUserType(userId, userType);
	}

	/**
	 * @param securityConfigBiz the securityConfigBiz to set
	 */
	public void setSecurityConfigBiz(SecurityConfigBiz securityConfigBiz) {
		this.securityConfigBiz = securityConfigBiz;
	}

	@Override
	public void assignFunctionToDept(Long functionId, List<Long> departmentIds) {
		securityConfigBiz.assignFunctionToDept(functionId, departmentIds);
	}

	@Override
	public void assignFunctionToRole(Long functionId, List<Long> roleIds) {
		securityConfigBiz.assignFunctionToRole(functionId, roleIds);

	}

	@Override
	public void deleteDepartmentAndFunctionRelation(
			Long departmentId, Long functionId) {
		securityConfigBiz.deleteDepartmentAndFunctionRelation(departmentId,
				functionId);
	}

	@Override
	public void deleteRoleAndFunctionRelation(Long roleId, Long functionId) {
		securityConfigBiz.deleteRoleAndFunctionRelation(roleId, functionId);
	}

	@Override
	public List<Long> queryDepartmentAndFunctionRelation(Long functionId) {
		return securityConfigBiz.queryDepartmentAndFunctionRelation(functionId);
	}

	@Override
	public List<Long> queryByFunctionId(Long functionId) {
		return securityConfigBiz.queryByFunctionId(functionId);
	}

	@Override
	public void deleteDepartmentAndFunctionRelationByFunctionId(Long functionId) {
		securityConfigBiz.deleteDepartmentAndFunctionRelation(functionId);
	}

	@Override
	public void deleteRoleAndFunctionRelationByFunctionId(Long functionId) {
		securityConfigBiz.deleteRoleAndFunctionRelation(functionId);
	}

	@Override
	public List<FunctionDTO> queryByCheckFunctionId(Long checkFunctionId) {
		return securityConfigBiz.queryByCheckFunctionId(checkFunctionId);
	}

	@Override
	public void deleteByCheckFunctionId(Long checkFunctionId) {
		securityConfigBiz.deleteByCheckFunctionId(checkFunctionId);
	}

	@Override
	public void addAuditFunction(FunctionDTO functionDTO, UserDTO userDTO) {
		securityConfigBiz.addAuditFunction(functionDTO, userDTO);
	}

	@Override
	public void deleteByCheckFunctionIdAndType(Long checkFunctionId,
	                                           String functionType) {
		securityConfigBiz.deleteByCheckFunctionIdAndType(checkFunctionId,
				functionType);
	}

}
