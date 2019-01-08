/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.function.service;

import com.zbjdl.boss.admin.function.dto.ExclusiveFunctionDTO;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;

import java.util.List;

/**
 * 类名称：FunctionService <br>
 * 类描述：功能服务接口定义 <br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-7-5 下午04:35:01
 */
public interface FunctionService {

	/**
	 * 描述： 创建功能
	 *
	 * @param functionDTO 功能信息
	 * @return 功能ID
	 */
	Long createFunction(FunctionDTO functionDTO);

	/**
	 * 描述： 删除功能
	 *
	 * @param functionID 功能ID
	 */
	void deleteFunction(Long functionID);

	/**
	 * 描述： 更新功能
	 *
	 * @param functionDTO 需要更新的功能信息
	 */
	void updateFunction(FunctionDTO functionDTO);

	/**
	 * 描述： 更新功能
	 *
	 * @param departmentId    待迁移部门ID
	 * @param newDepartmentId 拟迁入部门ID
	 */
	void migrateFunction(Long departmentId, Long newDepartmentId);

	/**
	 * 描述： 新增功能
	 *
	 * @param functionDTO 需要更新的功能信息
	 */
	void addFunction(FunctionDTO functionDTO);

	/**
	 * 描述： 根据ID查询功能信息
	 *
	 * @param functionID 功能ID
	 * @return 功能信息
	 */
	FunctionDTO queryFunctionByID(Long functionID);

	/**
	 * 描述： 根据查询条件查询
	 *
	 * @param functionDTO 功能信息
	 *                    查询条件
	 * @return 功能列表
	 */
	List<FunctionDTO> query(FunctionDTO functionDTO);

	/**
	 * 描述： 根据指定的功能ID检查是否为其它功能的前置功能
	 *
	 * @param functionID 功能ID
	 * @return 检查结果
	 */
	boolean isPreFunction(Long functionID);

	/**
	 * 描述：获取全部互斥功能对
	 *
	 * @return
	 */
	List<ExclusiveFunctionDTO> getAllExclusiveFunction();

	/**
	 * 查询用户拥有的功能列表
	 *
	 * @param userId：用户ID
	 * @return
	 */
	public List<FunctionDTO> queryFuntionByUserId(Long userId);

	/**
	 * 查询用户拥有以及满足Function的功能列表
	 *
	 * @param department：部门ID
	 * @param functionId：功能ID
	 * @return
	 */
	public List<FunctionDTO> queryFunctionByDepartmentAndFunctionId(Long department,
	                                                                Long functionId);

	/**
	 * 获取uri请求对应的功能
	 *
	 * @param uri：uri请求
	 * @return
	 */
	public FunctionDTO queryFunctionByUri(String uri);

	/**
	 * 获取所有function列表
	 *
	 * @param departmentId：部门ID
	 * @return
	 */
	public List<FunctionDTO> queryFuntionByDepartmentId(Long departmentId);

	/**
	 * 获取所有function列表
	 *
	 * @param roleId：角色ID
	 * @return
	 */
	public List<FunctionDTO> queryFuntionByRoleId(Long roleId);

	/**
	 * 获取所有function列表
	 *
	 * @return
	 */
	public List<FunctionDTO> getAllFunction();

	/**
	 * 根据所复核的功能ID查询功能
	 *
	 * @param checkFunctionId:所复核的功能ID
	 */
	public List<FunctionDTO> queryByCheckFunctionId(Long checkFunctionId);

	/**
	 * 根据所复核的功能ID删除功能
	 *
	 * @param departmentId 部门ID
	 */
	public void deleteByDepartmentId(Long departmentId);

	/**
	 * 根据所复核的功能ID删除功能
	 *
	 * @param checkFunctionId:所复核的功能ID
	 */
	public void deleteByCheckFunctionId(Long checkFunctionId);

	/**
	 * 根据所复核的功能ID和功能类型删除功能
	 *
	 * @param checkFunctionId:所复核的功能ID
	 * @param functionType：功能类型
	 */
	public void deleteByCheckFunctionIdAndType(Long checkFunctionId, String functionType);

}
