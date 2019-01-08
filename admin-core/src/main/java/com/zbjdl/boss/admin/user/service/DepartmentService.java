/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.user.service;

import com.zbjdl.boss.admin.user.dto.DepartmentDTO;

import java.util.List;

/**
 * 类名称：DepartmentService <br>
 * 类描述：部门管理Service<br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-6-17 下午03:01:15
 */
public interface DepartmentService {

	/**
	 * 描述： 创建部门
	 *
	 * @param departmentDTO 部门信息
	 * @return 部门ID
	 */
	public Long createDepartment(DepartmentDTO departmentDTO);

	/**
	 * 描述：修改部门信息
	 *
	 * @param departmentDTO
	 * @return 修改结果
	 */
	public boolean updateDepartmentInfo(DepartmentDTO departmentDTO);

	/**
	 * 描述： 删除部门
	 *
	 * @param departmentId
	 * @return 删除结果
	 */
	public boolean deleteDepartment(Long departmentId);

	/**
	 * 描述： 验证部门是否已存在
	 *
	 * @param departmentDTO
	 * @return 验证结果
	 */
	public boolean validateDepartmentExistOrnot(DepartmentDTO departmentDTO);

	/**
	 * 描述： 根据部门id查询部门
	 *
	 * @param departmentId
	 * @return 部门信息
	 */
	public DepartmentDTO queryDepartmentById(Long departmentId);

	/**
	 * 描述： 根据部门信息查询部门
	 *
	 * @param departmentDTO
	 * @return 相关信息的部门列表
	 */
	public List<DepartmentDTO> queryDepartments(DepartmentDTO departmentDTO);

	/**
	 * 描述：获取所有部门
	 *
	 * @return
	 */
	public List<DepartmentDTO> queryAllDepartments();

	/**
	 * 描述：获取已配置目标功能的部门
	 * functionId:功能ID
	 *
	 * @return
	 */
	public List<DepartmentDTO> queryDeparmentByFunctionId(Long functionId);

	/**
	 * 描述： 根据部门Id查询该部门下所有的用户Id
	 *
	 * @param departmentId 部门Id
	 * @return 用户Id列表
	 */
	public List<Long> queryUserByDeparmentId(Long departmentId);

}
