/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.authority.service.impl;

import com.zbjdl.boss.admin.authority.entity.RoleEntity;
import com.zbjdl.boss.admin.authority.service.RoleService;
import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.repository.RoleDao;
import com.zbjdl.boss.admin.role.dto.RoleDTO;
import com.zbjdl.boss.admin.role.enums.RoleStatusEnum;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名称：RoleServiceImpl <br>
 * 类描述：   角色管理<br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-7-5 下午02:11:41
 */
public class RoleServiceImpl implements RoleService {

	private static final Log logger = LogFactory.getLog(RoleServiceImpl.class.getName());

	private Convert<RoleDTO, RoleEntity> roleConvert;

	private RoleDao roleDao;

	@Override
	public Long createRole(RoleDTO roleDTO) {
		RoleEntity roleEntity = roleConvert.convert(roleDTO);
		roleDao.save(roleEntity);
		return roleEntity.getRoleId();
	}

	@Override
	public void deleteRole(Long roleId) {
		roleDao.delete(roleId);
	}

	@Override
	public void updateRole(RoleDTO roleDTO) {
		RoleEntity roleEntity = roleConvert.convert(roleDTO);
		roleDao.update(roleEntity);
	}

	@Override
	public RoleDTO queryRoleById(Long roleId) {
		return roleConvert.convert((RoleEntity)roleDao.selectById(roleId));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleDTO> queryRole(RoleDTO roleDTO) {
		RoleEntity roleEntity = roleConvert.convert(roleDTO);
		List<RoleEntity> list = roleDao.queryByRole(roleEntity);
		return roleConvert.convertToDtos(list);
	}

	@Override
	public void activateRole(Long roleId) {
		RoleEntity role = roleDao.selectById(roleId);
		role.setRoleStatus(RoleStatusEnum.ACTIVE);
		roleDao.update(role);
	}

	@Override
	public void frozenRole(Long roleId) {
		RoleEntity role = roleDao.selectById(roleId);
		role.setRoleStatus(RoleStatusEnum.FROZEN);
		roleDao.update(role);
	}

	@Override
	public void forbidRole(Long roleId) {
		RoleEntity role = roleDao.selectById(roleId);
		role.setRoleStatus(RoleStatusEnum.FORBID);
		roleDao.update(role);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleDTO> queryRolesByUser(Long userId) {
		List<RoleEntity> roleEntityList = roleDao.queryRolesByUserId(userId);
		return roleConvert.convertToDtos(roleEntityList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleDTO> queryRolesByDeparment(Long departmentId) {
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setDepartmentId(departmentId);
		List<RoleEntity> roleEntityList = roleDao.queryByRole(roleEntity);
		return roleConvert.convertToDtos(roleEntityList);
	}

	@Override
	public boolean migrateRole(Long departmentId, Long newDepartmentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("departmentId", departmentId);
		map.put("newDepartmentId", newDepartmentId);
		try {
			roleDao.migrateRole(map);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public void deleteRoleByDepartmentId(Long departmentId) {
		roleDao.deleteRoleRelationByDepartmentId( departmentId);
		roleDao.deleteRoleByDepartmentId( departmentId);
	}

	/**
	 * roleConvert
	 *
	 * @return the roleConvert
	 */
	public Convert<RoleDTO, RoleEntity> getRoleConvert() {
		return roleConvert;
	}

	/**
	 * 描述： 注入roleConvert
	 *
	 * @param roleConvert the roleConvert to set
	 */
	public void setRoleConvert(Convert<RoleDTO, RoleEntity> roleConvert) {
		this.roleConvert = roleConvert;
	}

	/**
	 * roleDao
	 *
	 * @return the roleDao
	 */
	public RoleDao getRoleDao() {
		return roleDao;
	}

	/**
	 * 描述： RoleDao
	 *
	 * @param roleDao the roleDao to set
	 */
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

}
