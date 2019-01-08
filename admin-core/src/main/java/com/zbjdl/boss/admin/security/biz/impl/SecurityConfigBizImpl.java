/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.security.biz.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.zbjdl.boss.admin.authority.entity.RoleAndFunctionRelationEntity;
import com.zbjdl.boss.admin.authority.entity.UserAndRoleRelationEntity;
import com.zbjdl.boss.admin.authority.service.RoleAndFunctionRelationService;
import com.zbjdl.boss.admin.authority.service.RoleService;
import com.zbjdl.boss.admin.authority.service.UserAndRoleRelationService;
import com.zbjdl.boss.admin.biz.SecurityConfigBiz;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.entity.DepartmentAndFunctionRelationEntity;
import com.zbjdl.boss.admin.function.entity.FunctionDependenceEntity;
import com.zbjdl.boss.admin.function.enums.FunctionStatusEnum;
import com.zbjdl.boss.admin.function.enums.FunctionTypeEnum;
import com.zbjdl.boss.admin.function.enums.MenuDirectionEnum;
import com.zbjdl.boss.admin.function.exception.FunctionException;
import com.zbjdl.boss.admin.function.exception.MenuException;
import com.zbjdl.boss.admin.function.service.DepartmentAndFunctionRelationService;
import com.zbjdl.boss.admin.function.service.FunctionDependenceService;
import com.zbjdl.boss.admin.function.service.FunctionService;
import com.zbjdl.boss.admin.function.service.MenuService;
import com.zbjdl.boss.admin.role.dto.RoleDTO;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.enums.UserTypeEnum;
import com.zbjdl.boss.admin.user.service.DepartmentService;
import com.zbjdl.boss.admin.user.service.UserService;
import com.zbjdl.common.utils.cache.remote.RemoteCacheUtils;
import com.zbjdl.common.utils.config.ConfigParam;
import com.zbjdl.common.utils.config.ConfigurationUtils;
import com.zbjdl.common.exception.BaseException;
import com.zbjdl.common.utils.CheckUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author feng
 * @version 1.0.0
 * @since 2012-4-6 下午2:28:11
 */
public class SecurityConfigBizImpl implements SecurityConfigBiz {

	private static final Logger logger = LoggerFactory
			.getLogger(SecurityConfigBizImpl.class);
	@Autowired
	private FunctionService functionService;

	private RoleService roleService;

	private DepartmentService departmentService;

	private UserAndRoleRelationService userAndRoleRelationService;

	private RoleAndFunctionRelationService roleAndFunctionRelationService;

	private DepartmentAndFunctionRelationService departmentAndFunctionRelationService;

	private FunctionDependenceService functionDependenceService;

	private UserService userService;

	private MenuService menuService;

	public List<FunctionDTO> queryFunction(FunctionDTO functionDTO) {
		return functionService.query(functionDTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#getRoleInfo(java.lang
	 * .Long)
	 */
	@Override
	public RoleDTO getRoleInfo(Long roleId) {
		return roleService.queryRoleById(roleId);
	}

	
	@Override
	public List<RoleDTO> queryRoles(RoleDTO roleDTO) {
		return roleService.queryRole(roleDTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#getMenuInfo(java.lang
	 * .Long)
	 */
	@Override
	public MenuDTO getMenuInfo(Long menuId) {
		return menuService.queryMenuByID(menuId);
	}

	@Override
	public List<MenuDTO> queryMenu(MenuDTO menuDTO) {
		return menuService.query(menuDTO);
	}

	
	@Override
	@Transactional
	public void addMenu(MenuDTO menuDTO) {
		// 设定菜单的位置，添加该层的在最后
		Integer maxSequence = menuService.queryMaxSequenceByParentId(menuDTO
				.getParentId());
		menuDTO.setSequence(maxSequence + 1);

		menuService.createMenu(menuDTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#deleteMenu(java.lang
	 * .Long)
	 */
	@Override
	@Transactional
	public void deleteMenu(Long menuId) {
		if (menuService.hasChildrenMenus(menuId)) {
			throw MenuException.MENU_CAN_NOT_BE_DELETED_WHEN_SUB_MENUS_EXIST_EXCEPTION;
		}
		MenuDTO menu = menuService.queryMenuByID(menuId);

		menuService.deleteMenu(menuId);

		// 删除后对各个菜单的Sequence进行重置
		MenuDTO query = new MenuDTO();
		query.setParentId(menu.getParentId());
		List<MenuDTO> menuList = menuService.query(query);
		for (int i = 0; i < menuList.size(); i++) {
			MenuDTO dto = menuList.get(i);
			dto.setSequence(i + 1);
		}
		menuService.batchUpdateMenu(menuList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#moveMenu(java.lang.
	 * Long, com.zbjdl.boss.admin.function.enums.MenuDirectionEnum)
	 */
	@Override
	@Transactional
	public void moveMenu(Long menuId, MenuDirectionEnum direction) {

		MenuDTO movingMenu = menuService.queryMenuByID(menuId);
		if (movingMenu == null) {
			throw MenuException.MENU_NOT_EXIST_EXCEPTION;
		}

		MenuDTO query = new MenuDTO();
		query.setParentId(movingMenu.getParentId());
		int currentSequence = movingMenu.getSequence();
		if (direction == MenuDirectionEnum.UP) {
			if (currentSequence == 1) {
				throw MenuException.MENU_CAN_NOT_BE_MOVED_UP_ON_TOP;
			} else {
				query.setSequence(currentSequence - 1);
			}
		} else {
			int maxSequence = menuService.queryMaxSequenceByParentId(movingMenu
					.getParentId());
			if (currentSequence >= maxSequence) {
				throw MenuException.MENU_CAN_NOT_BE_MOVED_DOWN_ON_BOTTOM;
			} else {
				query.setSequence(currentSequence + 1);
			}
		}
		List<MenuDTO> result = menuService.query(query);
		if (result.size() != 1) {
			throw MenuException.MENU_NOT_EXIST_EXCEPTION;
		}
		MenuDTO movedMenu = result.get(0);

		movingMenu.setSequence(movedMenu.getSequence());
		movedMenu.setSequence(currentSequence);

		List<MenuDTO> updateMenus = new ArrayList<MenuDTO>();
		updateMenus.add(movedMenu);
		updateMenus.add(movingMenu);
		menuService.batchUpdateMenu(updateMenus);
	}

	
	@Override
	@Transactional
	public void updateMenu(MenuDTO menuDTO) {
		Long menuId = menuDTO.getMenuId();
		if (menuId == null) {
			throw MenuException.MENU_NOT_EXIST_EXCEPTION;
		}

		// 待更新信息
		String currentMenuName = menuDTO.getMenuName();
		Long currentFunctionId = menuDTO.getFunctionId();
		String currentIconName = menuDTO.getIconName();
		Integer levelNum = menuDTO.getLevelNum();
		Long parentId = menuDTO.getParentId();
		Integer sequence = menuDTO.getSequence();

		MenuDTO menu = menuService.queryMenuByID(menuId);

		// 检查菜单是否需要更新
		boolean update = false;
		if (!menu.getMenuName().equals(currentMenuName)) {
			menu.setMenuName(currentMenuName);
			update = true;
		}

		if (menu.getSequence() != null && menu.getSequence() != sequence) {
			menu.setSequence(sequence);
			update = true;
		}

		if (menu.getParentId() != null && !menu.getParentId().equals(parentId)) {
			menu.setParentId(parentId);
			update = true;
		}

		if (menu.getLevelNum() != null && menu.getLevelNum() != levelNum) {
			menu.setLevelNum(levelNum);
			update = true;
		}

		if (menu.getLevelNum() == 1) {
			if (StringUtils.isEmpty(menu.getIconName())
					&& !StringUtils.isEmpty(currentIconName)) {
				menu.setIconName(currentIconName);
				update = true;
			} else {
				if (!menu.getIconName().equals(currentIconName)) {
					menu.setIconName(currentIconName);
					update = true;
				}
			}
		}

		if (menu.getFunctionId() != currentFunctionId) {
			menu.setFunctionId(currentFunctionId);
			update = true;
		}
		if (update) {
			menuService.updateMenu(menuDTO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#getFunctionInfo(java
	 * .lang.Long)
	 */
	@Override
	public FunctionDTO getFunctionInfo(Long functionId) {
		return functionService.queryFunctionByID(functionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zbjdl.boss.admin.biz.SecurityConfigBiz#
	 * queryDepartmentsByFunctionId(java.lang.Long)
	 */
	@Override
	public List<DepartmentDTO> queryDepartmentsByFunctionId(Long functionId) {
		return departmentService.queryDeparmentByFunctionId(functionId);
	}

	@Override
	@Transactional
	public void updateFunction(FunctionDTO functionDTO) {
		FunctionDTO liveFunctionDTO = functionService
				.queryFunctionByID(functionDTO.getFunctionId());

		if (liveFunctionDTO == null) {
			throw FunctionException.FUNCTION_NOT_EXIST_EXCEPTION;
		}
		functionService.updateFunction(functionDTO);
	}

	@Override
	public void addFunction(FunctionDTO functionDTO) {
		functionService.addFunction(functionDTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#deleteFunction(java
	 * .lang.Long)
	 */
	@Override
	@Transactional
	public void deleteFunction(Long functionId) {
		// 验证是否为其它功能的前置功能，如果是则不能删除
		boolean isPreFunction = functionService.isPreFunction(functionId);
		if (isPreFunction) {
			throw FunctionException.PRE_FUNCTION_CAN_NOT_BE_DELETED_EXCEPTION;
		} else {
			functionService.deleteFunction(functionId);
		}
		// TODO 删除角色功能\部门功能
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zbjdl.boss.admin.biz.SecurityConfigBiz#queryAllFunction()
	 */
	@Override
	public List<FunctionDTO> queryAllFunction() {
		return functionService.getAllFunction();
	}

	/**
	 * 根据所选功能，获取依赖功能及所选功能的集合
	 *
	 * @param selFunctionIds ：所选功能
	 * @return
	 */
	private Long[] getAllSelFuctionWithRef(Long[] selFunctionIds) {
		Set<Long> functionIds = new HashSet<Long>();
		if (selFunctionIds != null && selFunctionIds.length > 0) {
			Map<Long, Long[]> dependenceMap = getDependenceRelation()[0];

			for (Long functionId : selFunctionIds) {
				functionIds.addAll(getAllDependenceForFunction(functionId,
						dependenceMap));
			}
		}
		Long[] ret = new Long[functionIds.size()];
		return functionIds.toArray(ret);
	}

	/**
	 * 获取一个功能的所有依赖，包含级联依赖
	 *
	 * @param functionId    ：功能ID
	 * @param dependenceMap ：一级功能依赖映射
	 * @return
	 */
	private Set<Long> getAllDependenceForFunction(Long functionId,
	                                              Map<Long, Long[]> dependenceMap) {
		Set<Long> refIds = new HashSet<Long>();
		refIds.add(functionId);
		Long[] functionIds = dependenceMap.get(functionId);
		if (functionIds != null) {
			for (Long id : functionIds) {
				refIds.addAll(getAllDependenceForFunction(id, dependenceMap));
			}
		}
		return refIds;
	}

	/**
	 * 获取先天存在的角色包含的功能
	 *
	 * @return
	 */
	private Set<Long> getInnateRoleFunctions() {
		Set<Long> exceptFunctions = new HashSet<Long>();
		List<FunctionDTO> systemManagerFunction = queryFunctionByRole(UserTypeEnum.SYSTEM_MANAGER
				.getRoleId());
		List<FunctionDTO> dpartmentManagerFunction = queryFunctionByRole(UserTypeEnum.DEPARTMENT_MANAGER
				.getRoleId());
		List<FunctionDTO> opertorFunction = queryFunctionByRole(UserTypeEnum.OPERATOR
				.getRoleId());
		for (FunctionDTO function : systemManagerFunction) {
			exceptFunctions.add(function.getFunctionId());
		}
		for (FunctionDTO function : dpartmentManagerFunction) {
			exceptFunctions.add(function.getFunctionId());
		}
		for (FunctionDTO function : opertorFunction) {
			exceptFunctions.add(function.getFunctionId());
		}
		return exceptFunctions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#queryDisplayFunction()
	 */
	@Override
	public List<FunctionDTO> queryDisplayFunction() {
		List<FunctionDTO> allFunctions = queryAllFunction();
		List<FunctionDTO> displayList = new ArrayList<FunctionDTO>();
		Set<Long> exceptFunctions = getInnateRoleFunctions();

		for (FunctionDTO functionDTO : allFunctions) {
			// 排除非活动状态的
			if (!FunctionStatusEnum.ACTIVE.equals(functionDTO
					.getFunctionStatus())) {
				continue;
			}
			// 排除先天角色功能
			if (exceptFunctions.contains(functionDTO.getFunctionId())) {
				continue;
			}
			// 排除不展示的功能
			if (!functionDTO.getDisplay()) {
				continue;
			}
			displayList.add(functionDTO);
		}
		return displayList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#queryFunctionByUser
	 * (java.lang.Long)
	 */
	@Override
	public List<FunctionDTO> queryFunctionByUser(Long userId) {
		return functionService.queryFuntionByUserId(userId);
	}

	@Override
	public List<FunctionDTO> queryFunctionByDepartmentAndFunctionId(
			Long departmentId, Long functionId) {
		return functionService.queryFunctionByDepartmentAndFunctionId(
				departmentId, functionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#queryFunctionByRole
	 * (java.lang.Long)
	 */
	@Override
	public List<FunctionDTO> queryFunctionByRole(Long roleId) {
		return functionService.queryFuntionByRoleId(roleId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#queryRolesByUser(java
	 * .lang.Long)
	 */
	@Override
	public List<RoleDTO> queryRolesByUser(Long userId) {
		List<RoleDTO> allUserRole = roleService.queryRolesByUser(userId);
		List<RoleDTO> retRoleDTO = new ArrayList<RoleDTO>();
		if (allUserRole != null) {
			for (RoleDTO role : allUserRole) {
				if (role.getRoleId().equals(
						UserTypeEnum.SYSTEM_MANAGER.getRoleId())
						|| role.getRoleId().equals(
						UserTypeEnum.DEPARTMENT_MANAGER.getRoleId())
						|| role.getRoleId().equals(
						UserTypeEnum.OPERATOR.getRoleId())) {
					continue;
				}
				retRoleDTO.add(role);
			}
		}
		return retRoleDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#queryRolesByDeparment
	 * (java.lang.Long)
	 */
	@Override
	public List<RoleDTO> queryRolesByDeparment(Long departmentId) {
		return roleService.queryRolesByDeparment(departmentId);
	}
	
	public List<UserDTO> queryUsersByRole(Long roleId){
		List<UserAndRoleRelationEntity> userRoleList = userAndRoleRelationService.queryByRoleId(roleId);
		List<UserDTO> userList = new ArrayList<>();
		if(userRoleList!=null && !userRoleList.isEmpty()){
			for(int i = 0;i<userRoleList.size();i++){
				UserAndRoleRelationEntity userRole = userRoleList.get(i);
				UserDTO user = userService.queryUserById(userRole.getUserId());
				userList.add(user);
			}
			
		}
		return userList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zbjdl.boss.admin.biz.SecurityConfigBiz#
	 * queryFunctionByDepartment(java.lang.Long)
	 */
	@Override
	public List<FunctionDTO> queryFunctionByDepartment(Long departmentId) {
		List<FunctionDTO> functionList = functionService
				.queryFuntionByDepartmentId(departmentId);
		List<FunctionDTO> retList = Lists.newArrayList();
		// 过滤不展示的功能
		if (functionList != null && !functionList.isEmpty()) {
			for (FunctionDTO funtion : functionList) {
				if (funtion.getDisplay()) {
					retList.add(funtion);
				}
			}
		}
		return retList;
	}

	/**
	 * 更新部门和部门功能关联
	 *
	 * @param departmentDTO ：部门信息
	 * @param functionIds   ：功能ID集合
	 */
	private void updateDepartmentAndFunctionRelation(
			DepartmentDTO departmentDTO, Long[] functionIds) {
		departmentService.updateDepartmentInfo(departmentDTO);
		List<DepartmentAndFunctionRelationEntity> entryList = departmentAndFunctionRelationService
				.queryByDeptId(departmentDTO.getDepartmentId());

		// 没有其他操作只是删除
		if (null == functionIds || functionIds.length < 1) {
			if (entryList != null && !entryList.isEmpty()) {
				departmentAndFunctionRelationService.deleteAll(entryList);
				// 删除部门下的角色拥有的对应功能
				for (DepartmentAndFunctionRelationEntity entry : entryList) {
					roleAndFunctionRelationService
							.deleteRoleAndFunctionByCondition(
									entry.getDepartmentId(),
									entry.getFunctionId(), null);
				}
			}
			return;
		}

		Set<Long> functionIdSet = new HashSet<Long>();
		for (Long functionId : functionIds) {
			functionIdSet.add(functionId);
		}
		// 比较获得删除内容
		List<DepartmentAndFunctionRelationEntity> delList = new ArrayList<DepartmentAndFunctionRelationEntity>();
		Map<Long, DepartmentAndFunctionRelationEntity> roleMap = new HashMap<Long, DepartmentAndFunctionRelationEntity>();
		for (DepartmentAndFunctionRelationEntity entry : entryList) {
			roleMap.put(entry.getFunctionId(), entry);
			if (!functionIdSet.contains(entry.getFunctionId())) {
				delList.add(entry);
			}
		}
		if (!delList.isEmpty()) {
			departmentAndFunctionRelationService.deleteAll(delList);
			// 删除部门下的角色拥有的对应功能
			for (DepartmentAndFunctionRelationEntity entry : delList) {
				roleAndFunctionRelationService
						.deleteRoleAndFunctionByCondition(
								entry.getDepartmentId(), entry.getFunctionId(),
								null);
			}
		}

		// 比较增加内容
		List<DepartmentAndFunctionRelationEntity> addList = new ArrayList<DepartmentAndFunctionRelationEntity>();
		for (Long functionId : functionIds) {
			if (null == roleMap.get(functionId)) {
				DepartmentAndFunctionRelationEntity entry = new DepartmentAndFunctionRelationEntity();
				entry.setDepartmentId(departmentDTO.getDepartmentId());
				entry.setFunctionId(functionId);
				addList.add(entry);
			}
		}
		if (!addList.isEmpty()) {
			departmentAndFunctionRelationService.saveAll(addList);
		}

	}

	/**
	 * 添加部门和部门功能关联
	 *
	 * @param departmentDTO ：部门信息
	 * @param functionIds   ：功能ID集合
	 */
	private void assignFunctionToDept(DepartmentDTO departmentDTO,
	                                  Long[] functionIds) {
		Long departmentId = departmentService.createDepartment(departmentDTO);
		// 创建部门及功能关联
		if (functionIds != null && functionIds.length > 0) {
			List<DepartmentAndFunctionRelationEntity> entryList = new ArrayList<DepartmentAndFunctionRelationEntity>();
			for (Long functionId : functionIds) {
				DepartmentAndFunctionRelationEntity entry = new DepartmentAndFunctionRelationEntity();
				entry.setDepartmentId(departmentId);
				entry.setFunctionId(functionId);
				entryList.add(entry);
			}
			departmentAndFunctionRelationService.saveAll(entryList);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#configDepartmentFunction
	 * (com.zbjdl.boss.admin.user.dto.DepartmentDTO, java.lang.Long[])
	 */
	@Override
	@Transactional
	public void configDepartmentFunction(DepartmentDTO departmentDTO,
	                                     Long[] functionIds) {
		CheckUtils.notNull(departmentDTO, "部门信息");
		CheckUtils.notEmpty(departmentDTO.getDepartmentName(), "部门名称");
		CheckUtils.notEmpty(departmentDTO.getDepartmentCode(), "部门编码");
		functionIds = getAllSelFuctionWithRef(functionIds);

		if (departmentDTO.getDepartmentId() != null) {
			// 验证部门是否存在
			if (null == departmentService.queryDepartmentById(departmentDTO
					.getDepartmentId())) {
				throw new BaseException("无法找到对应部门，部门ID:"
						+ departmentDTO.getDepartmentId());
			}
			// 更新部门和部门功能关联
			updateDepartmentAndFunctionRelation(departmentDTO, functionIds);
		} else {
			// 验证部门名称、部门编码的唯一性
			DepartmentDTO queryDto = new DepartmentDTO();
			queryDto.setDepartmentCode(departmentDTO.getDepartmentCode());

			if (!CheckUtils.isEmpty(departmentService
					.queryDepartments(queryDto))) {
				throw new BaseException("部门编码重复！");
			}
			queryDto.setDepartmentCode(null);
			queryDto.setDepartmentName(departmentDTO.getDepartmentName());
			if (!CheckUtils.isEmpty(departmentService
					.queryDepartments(queryDto))) {
				throw new BaseException("部门名称重复！");
			}
			// 添加部门和部门功能关联
			assignFunctionToDept(departmentDTO, functionIds);
		}

	}

	/**
	 * 添加角色及角色功能关联
	 *
	 * @param roleDTO     ：角色信息
	 * @param functionIds ：功能ID集合
	 */
	private void assignFunctionToRole(RoleDTO roleDTO, Long[] functionIds) {
		Long roleId = roleService.createRole(roleDTO);
		// 创建角色及功能关系
		if (functionIds != null && functionIds.length > 0) {
			List<RoleAndFunctionRelationEntity> entryList = new ArrayList<RoleAndFunctionRelationEntity>();
			for (Long functionId : functionIds) {
				RoleAndFunctionRelationEntity entry = new RoleAndFunctionRelationEntity();
				entry.setRoleId(roleId);
				entry.setFunctionId(functionId);
				entryList.add(entry);
			}
			roleAndFunctionRelationService.saveAll(entryList);
		}
	}

	/**
	 * 更新角色及角色功能关联
	 *
	 * @param roleDTO     ：角色信息
	 * @param functionIds ：功能ID集合
	 */
	private void updateRoleAndFunctionRelation(RoleDTO roleDTO,
	                                           Long[] functionIds) {
		roleService.updateRole(roleDTO);
		List<RoleAndFunctionRelationEntity> entryList = roleAndFunctionRelationService
				.queryByRoleId(roleDTO.getRoleId());
		// 没有其他操作只是删除
		if (null == functionIds || functionIds.length < 1) {
			if (entryList != null && !entryList.isEmpty()) {
				roleAndFunctionRelationService.deleteAll(entryList);
			}
			return;
		}

		Set<Long> functionIdSet = new HashSet<Long>();
		for (Long functionId : functionIds) {
			functionIdSet.add(functionId);
		}
		// 比较获得删除内容
		List<RoleAndFunctionRelationEntity> delList = new ArrayList<RoleAndFunctionRelationEntity>();
		Map<Long, RoleAndFunctionRelationEntity> roleMap = new HashMap<Long, RoleAndFunctionRelationEntity>();
		for (RoleAndFunctionRelationEntity entry : entryList) {
			roleMap.put(entry.getFunctionId(), entry);
			if (!functionIdSet.contains(entry.getFunctionId())) {
				delList.add(entry);
			}
		}
		if (!delList.isEmpty()) {
			roleAndFunctionRelationService.deleteAll(delList);
		}

		// 比较增加内容
		List<RoleAndFunctionRelationEntity> addList = new ArrayList<RoleAndFunctionRelationEntity>();
		for (Long functionId : functionIds) {
			if (null == roleMap.get(functionId)) {
				RoleAndFunctionRelationEntity entry = new RoleAndFunctionRelationEntity();
				entry.setRoleId(roleDTO.getRoleId());
				entry.setFunctionId(functionId);
				addList.add(entry);
			}
		}
		if (!addList.isEmpty()) {
			roleAndFunctionRelationService.saveAll(addList);
		}
	}

	@Override
	@Transactional
	public void configRoleFunction(RoleDTO roleDTO, Long[] functionIds) {
		CheckUtils.notNull(roleDTO, "角色信息");
		CheckUtils.notEmpty(roleDTO.getRoleName(), "角色名称");

		// 增加依赖功能
		functionIds = getAllSelFuctionWithRef(functionIds);
		if (roleDTO.getRoleId() != null) {
			// 验证角色是否存在
			if (null == roleService.queryRoleById(roleDTO.getRoleId())) {
				throw new BaseException("无法找到对应角色，角色ID:"
						+ roleDTO.getRoleId());
			}
			// 更新角色及角色功能关联
			if (roleDTO.getRoleId().equals(
					UserTypeEnum.SYSTEM_MANAGER.getRoleId())
					|| roleDTO.getRoleId().equals(
					UserTypeEnum.DEPARTMENT_MANAGER.getRoleId())
					|| roleDTO.getRoleId().equals(
					UserTypeEnum.OPERATOR.getRoleId())) {
				throw new BaseException("系统隐含角色不允许修改！");
			}
			updateRoleAndFunctionRelation(roleDTO, functionIds);
		} else {
			// 验证角色名称是否重复
			RoleDTO queryDto = new RoleDTO();
			queryDto.setRoleName(roleDTO.getRoleName());
			if (!CheckUtils.isEmpty(roleService.queryRole(queryDto))) {
				throw new BaseException("角色名称重复");
			}
			// 添加角色及角色功能关联
			assignFunctionToRole(roleDTO, functionIds);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#configUserRoles(java
	 * .lang.Long, java.lang.Long[])
	 */
	@Override
	@Transactional
	public void configUserRoles(Long departmentId, Long userId, Long[] roleIds) {
		CheckUtils.notNull(userId, "用户ID");

		// 新权限列表
		Set<Long> roleIdSet = new HashSet<Long>();
		if (roleIds != null) {
			for (Long roleId : roleIds) {
				roleIdSet.add(roleId);
			}
		}

		// 当前用户角色
		List<UserAndRoleRelationEntity> userRoleRelations = userAndRoleRelationService
				.queryByUserId(userId);

		// 当前部门管理员可以控制的权限
		List<RoleDTO> roleDTOList = roleService
				.queryRolesByDeparment(departmentId);
		List<Long> roleIdList = Lists.newLinkedList();
		for (RoleDTO roleDTO : roleDTOList) {
			roleIdList.add(roleDTO.getRoleId());
		}
		List<UserAndRoleRelationEntity> removeEntity = Lists.newArrayList();
		for (UserAndRoleRelationEntity userRoleRelation : userRoleRelations) {
			if (!roleIdList.contains(userRoleRelation.getRoleId())) {
				removeEntity.add(userRoleRelation);
			}
		}
		userRoleRelations.removeAll(removeEntity);

		// 比较获得删除内容
		List<UserAndRoleRelationEntity> delList = new ArrayList<UserAndRoleRelationEntity>();
		Map<Long, UserAndRoleRelationEntity> roleMap = new HashMap<Long, UserAndRoleRelationEntity>();
		for (UserAndRoleRelationEntity userAndRoleRelationEntity : userRoleRelations) {
			roleMap.put(userAndRoleRelationEntity.getRoleId(),
					userAndRoleRelationEntity);
			if (userAndRoleRelationEntity.getRoleId().equals(
					UserTypeEnum.SYSTEM_MANAGER.getRoleId())
					|| userAndRoleRelationEntity.getRoleId().equals(
					UserTypeEnum.DEPARTMENT_MANAGER.getRoleId())
					|| userAndRoleRelationEntity.getRoleId().equals(
					UserTypeEnum.OPERATOR.getRoleId())) {
				// 过滤先天角色，不进行删除
				continue;
			}
			if (!roleIdSet.contains(userAndRoleRelationEntity.getRoleId())) {
				delList.add(userAndRoleRelationEntity);
			}
		}
		if (!delList.isEmpty()) {
			userAndRoleRelationService.deleteAll(delList);
		}

		// 比较增加内容
		List<UserAndRoleRelationEntity> addList = new ArrayList<UserAndRoleRelationEntity>();
		for (Long roleId : roleIdSet) {
			if (null == roleMap.get(roleId)) {
				UserAndRoleRelationEntity userAndRoleRelationEntity = new UserAndRoleRelationEntity();
				userAndRoleRelationEntity.setRoleId(roleId);
				userAndRoleRelationEntity.setUserId(userId);
				addList.add(userAndRoleRelationEntity);
			}
		}
		if (!addList.isEmpty()) {
			userAndRoleRelationService.saveAll(addList);
		}
	}

	@Override
	public void configUserRoles(Long userId, Long roleId) {
		try {
			userAndRoleRelationService.createUserAndRoleRelation(userId, roleId);
		} catch (Exception e) {
			// 目标用户可能存在该角色
		}
	}


	@Override
	@Transactional
	public Long addUser(UserDTO userDTO, UserTypeEnum userType) {
		CheckUtils.notNull(userDTO, "用户信息");
		if (userDTO.getUserId() != null) {
			throw new BaseException("用户ID必须为空");
		}
		// 验证名称
		CheckUtils.notEmpty(userDTO.getLoginName(), "用户名称");
		if (userService.queryUserByLoginName(userDTO.getLoginName()) != null) {
			throw new BaseException("用户名称重复");
		}
		// 创建用户
		Long userId = userService.createUser(userDTO);

		// 配置基本操作员角色
		List<UserAndRoleRelationEntity> addList = new ArrayList<UserAndRoleRelationEntity>();
		UserAndRoleRelationEntity userAndRoleRelationEntity = new UserAndRoleRelationEntity();
		userAndRoleRelationEntity.setRoleId(UserTypeEnum.OPERATOR.getRoleId());
		userAndRoleRelationEntity.setUserId(userId);
		addList.add(userAndRoleRelationEntity);
		// 配置特殊职位角色
		if (!UserTypeEnum.OPERATOR.equals(userType)) {
			UserAndRoleRelationEntity userAndRoleRelationEntity1 = new UserAndRoleRelationEntity();
			userAndRoleRelationEntity1.setRoleId(userType.getRoleId());
			userAndRoleRelationEntity1.setUserId(userId);
			addList.add(userAndRoleRelationEntity1);
		}

		userAndRoleRelationService.saveAll(addList);
		return userId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#deleteUser(java.lang
	 * .Long)
	 */
	@Override
	@Transactional
	public void deleteUser(Long userId) {
		CheckUtils.notNull(userId, "用户ID");
		UserDTO userDTO = userService.queryUserById(userId);
		if (null == userDTO) {
			throw new BaseException("无法找到对应用户，用户ID:" + userId);
		}
		// 删除用户拥有的角色
		List<UserAndRoleRelationEntity> userRoleRelations = userAndRoleRelationService
				.queryByUserId(userId);
		userAndRoleRelationService.deleteAll(userRoleRelations);

		// 删除用户
		userService.deleteUser(userId);

//		if (isSynchronizationOpen()) {
//			// 同步用户信息至二代用户
//			UserSynchronizationFacade userSynchronizationFacade = RemoteServiceFactory
//					.getService(RemotingProtocol.HTTPINVOKER,
//							UserSynchronizationFacade.class);
//			try {
//				if (userSynchronizationFacade.getUserByUserName(userDTO
//						.getLoginName()) != null) {
//					userSynchronizationFacade.dropUser(userDTO.getLoginName());
//				}
//			} catch (UserSynchronizationException e) {
//				logger.error("drop user failed!", e);
//				throw new BaseException(e);
//			}
//		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#frozeUser(java.lang
	 * .Long, java.lang.String, java.lang.Long)
	 */
	@Override
	@Transactional
	public void frozeUser(Long userId, String frozenReason, Long operatorId) {
		CheckUtils.notNull(userId, "用户ID");
		CheckUtils.notNull(operatorId, "操作员ID");
		userService.frozenUser(userId, frozenReason, operatorId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#unFrozeUser(java.lang
	 * .Long, java.lang.String, java.lang.Long)
	 */
	@Override
	@Transactional
	public void unFrozeUser(Long userId, String activateReason, Long operatorId) {
		CheckUtils.notNull(userId, "用户ID");
		CheckUtils.notNull(operatorId, "操作员ID");
		userService.activateUser(userId, activateReason, operatorId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#getDependenceRelation()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, Long[]>[] getDependenceRelation() {
		List<FunctionDependenceEntity> dependencyList = functionDependenceService
				.getAllDependenceList();
		Map<Long, Set<Long>> depentMap = new HashMap<Long, Set<Long>>();
		Map<Long, Set<Long>> refMap = new HashMap<Long, Set<Long>>();
		if (dependencyList != null && !dependencyList.isEmpty()) {
			for (FunctionDependenceEntity entry : dependencyList) {
				Set<Long> depentArr = depentMap.get(entry.getFunctionId());
				Set<Long> refArr = refMap.get(entry.getDependentFunctionId());
				if (depentArr != null) {
					depentArr.add(entry.getDependentFunctionId());
				} else {
					depentArr = new HashSet<Long>();
					depentArr.add(entry.getDependentFunctionId());
					depentMap.put(entry.getFunctionId(), depentArr);
				}
				if (refArr != null) {
					refArr.add(entry.getFunctionId());
				} else {
					refArr = new HashSet<Long>();
					refArr.add(entry.getFunctionId());
					refMap.put(entry.getDependentFunctionId(), refArr);
				}
			}
		}
		Map<Long, Long[]> depentRest = new HashMap<Long, Long[]>();
		Map<Long, Long[]> refRest = new HashMap<Long, Long[]>();
		for (java.util.Iterator<Map.Entry<Long, Set<Long>>> it = depentMap
				.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<Long, Set<Long>> entry = it.next();
			Long[] arr = new Long[entry.getValue().size()];
			entry.getValue().toArray(arr);
			depentRest.put(entry.getKey(), arr);
		}
		for (java.util.Iterator<Map.Entry<Long, Set<Long>>> it = refMap
				.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<Long, Set<Long>> entry = it.next();
			Long[] arr = new Long[entry.getValue().size()];
			entry.getValue().toArray(arr);
			refRest.put(entry.getKey(), arr);
		}
		return new Map[]{depentRest, refRest};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#deleteDepartment(java
	 * .lang.Long)
	 */
	@Override
	@Transactional
	public void deleteDepartment(Long departmentId) {
		CheckUtils.notNull(departmentId, "部门ID");
		if (!CheckUtils.isEmpty(departmentService
				.queryUserByDeparmentId(departmentId))) {
			throw new BaseException("当前部门存在用户，不能删除！");
		}
		// 删除部门拥有的角色及功能
		roleAndFunctionRelationService.deleteRoleAndFunctionByCondition(
				departmentId, null, null);
		roleService.deleteRoleByDepartmentId(departmentId);
		departmentAndFunctionRelationService
				.deleteDepartmentAndFunctionByDepartmentId(departmentId);
		// 删除部门
		departmentService.deleteDepartment(departmentId);

		RemoteCacheUtils.remove(RemoteCacheUtils
				.generateCacheKey("queryAllDepartments"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#deleteRole(java.lang
	 * .Long)
	 */
	@Override
	@Transactional
	public void deleteRole(Long roleId) {
		CheckUtils.notNull(roleId, "角色ID");
		roleAndFunctionRelationService.deleteRoleAndFunctionByCondition(null,
				null, roleId);
		roleService.deleteRole(roleId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#frozeRole(java.lang
	 * .Long)
	 */
	@Override
	@Transactional
	public void frozeRole(Long roleId) {
		CheckUtils.notNull(roleId, "角色ID");
		roleService.frozenRole(roleId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#unFrozeRole(java.lang
	 * .Long)
	 */
	@Override
	@Transactional
	public void unFrozeRole(Long roleId) {
		CheckUtils.notNull(roleId, "角色ID");
		roleService.activateRole(roleId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#frozeFunction(java.
	 * lang.Long)
	 */
	@Override
	@Transactional
	public void frozeFunction(Long functionId) {
		CheckUtils.notNull(functionId, "功能ID");
		FunctionDTO functionDTO = functionService.queryFunctionByID(functionId);
		if (null == functionDTO) {
			throw new BaseException("未取到对应的功能信息，功能ID：" + functionId);
		}
		functionDTO.setFunctionStatus(FunctionStatusEnum.FROZEN);
		functionService.updateFunction(functionDTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#unFrozeFunction(java
	 * .lang.Long)
	 */
	@Override
	@Transactional
	public void unFrozeFunction(Long functionId) {
		CheckUtils.notNull(functionId, "功能ID");
		FunctionDTO functionDTO = functionService.queryFunctionByID(functionId);
		CheckUtils.notNull(functionDTO, "未取到对应的功能信息，功能ID：" + functionId);
		functionDTO.setFunctionStatus(FunctionStatusEnum.ACTIVE);
		functionService.updateFunction(functionDTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#changeUserType(java
	 * .lang.Long, com.zbjdl.boss.admin.user.enums.UserTypeEnum)
	 */
	@Override
	@Transactional
	public void changeUserType(Long userId, UserTypeEnum userType) {
		CheckUtils.notNull(userId, "用户ID");
		UserDTO user = userService.queryUserById(userId);
		if (null == user) {
			throw new BaseException("无法找到对应用户，用户ID:" + userId);
		}
		if (UserTypeEnum.SYSTEM_MANAGER.equals(userType)) {
			// 变为系统管理员,删除部门管理员及其他角色,增加系统管理员角色、和操作员角色
			userAndRoleRelationService.deleteAll(userAndRoleRelationService
					.queryByUserId(userId));
			userAndRoleRelationService.createUserAndRoleRelation(userId,
					UserTypeEnum.OPERATOR.getRoleId());
			userAndRoleRelationService.createUserAndRoleRelation(userId,
					UserTypeEnum.SYSTEM_MANAGER.getRoleId());

		} else if (UserTypeEnum.DEPARTMENT_MANAGER.equals(userType)) {
			// 变为系统管理员，删除系统管理员角色及其他角色,增加部门管理员角色\和操作员角色
			userAndRoleRelationService.deleteAll(userAndRoleRelationService
					.queryByUserId(userId));
			userAndRoleRelationService.createUserAndRoleRelation(userId,
					UserTypeEnum.OPERATOR.getRoleId());
			userAndRoleRelationService.createUserAndRoleRelation(userId,
					UserTypeEnum.DEPARTMENT_MANAGER.getRoleId());
		} else {
			// 变为操作员，删除系统管理员角色，删除部门管理员角色
			userAndRoleRelationService.deleteUserAndRoleRelation(userId,
					UserTypeEnum.SYSTEM_MANAGER.getRoleId());
			userAndRoleRelationService.deleteUserAndRoleRelation(userId,
					UserTypeEnum.DEPARTMENT_MANAGER.getRoleId());
		}
	}

	@Override
	public void assignFunctionToDept(Long functionId, List<Long> departmentIds) {
		if (functionId != null && departmentIds != null
				&& !departmentIds.isEmpty()) {
			for (Long departmentId : departmentIds) {
				departmentAndFunctionRelationService
						.createDepartmentAndFunctionRelation(departmentId,
								functionId);
			}
		}
	}

	@Override
	public void assignFunctionToRole(Long functionId, List<Long> roleIds) {
		if (functionId != null && roleIds != null && !roleIds.isEmpty()) {
			for (Long roleId : roleIds) {
				roleAndFunctionRelationService.createRoleAndFunctionRelation(
						roleId, functionId);
			}
		}
	}

	/**
	 * @param roleService the roleService to set
	 */
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
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

	/**
	 * @param departmentService the departmentService to set
	 */
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	/**
	 * @param roleAndFunctionRelationService the roleAndFunctionRelationService to set
	 */
	public void setRoleAndFunctionRelationService(
			RoleAndFunctionRelationService roleAndFunctionRelationService) {
		this.roleAndFunctionRelationService = roleAndFunctionRelationService;
	}

	/**
	 * @param departmentAndFunctionRelationService
	 *         the departmentAndFunctionRelationService to set
	 */
	public void setDepartmentAndFunctionRelationService(
			DepartmentAndFunctionRelationService departmentAndFunctionRelationService) {
		this.departmentAndFunctionRelationService = departmentAndFunctionRelationService;
	}

	/**
	 * @param functionDependenceService the functionDependenceService to set
	 */
	public void setFunctionDependenceService(
			FunctionDependenceService functionDependenceService) {
		this.functionDependenceService = functionDependenceService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @param menuService the menuService to set
	 */
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	@Override
	public void deleteDepartmentAndFunctionRelation(Long departmentId,
	                                                Long functionId) {
		departmentAndFunctionRelationService
				.deleteDepartmentAndFunctionRelation(departmentId, functionId);
	}

	@Override
	public void deleteRoleAndFunctionRelation(Long roleId, Long functionId) {
		roleAndFunctionRelationService.deleteRoleAndFunctionRelation(roleId,
				functionId);
	}

	@Override
	public List<Long> queryDepartmentAndFunctionRelation(Long functionId) {
		List<DepartmentAndFunctionRelationEntity> relationList = departmentAndFunctionRelationService
				.queryByFunctionId(functionId);
		List<Long> departmentIdList = new ArrayList<Long>();
		for (DepartmentAndFunctionRelationEntity entity : relationList) {
			departmentIdList.add(entity.getDepartmentId());
		}
		return departmentIdList;
	}

	@Override
	public List<Long> queryByFunctionId(Long functionId) {
		List<RoleAndFunctionRelationEntity> relationList = roleAndFunctionRelationService
				.queryByFunctionId(functionId);
		List<Long> roleIdList = new ArrayList<Long>();
		for (RoleAndFunctionRelationEntity entity : relationList) {
			roleIdList.add(entity.getRoleId());
		}
		return roleIdList;
	}

	@Override
	public void deleteDepartmentAndFunctionRelation(Long functionId) {
		departmentAndFunctionRelationService
				.deleteDepartmentAndFunctionByFunctionId(functionId);
	}

	@Override
	public void deleteRoleAndFunctionRelation(Long functionId) {
		roleAndFunctionRelationService
				.deleteRoleAndFunctionByFunctionId(functionId);
	}

	@Override
	public List<FunctionDTO> queryByCheckFunctionId(Long checkFunctionId) {
		return functionService.queryByCheckFunctionId(checkFunctionId);
	}

	@Override
	public void deleteByCheckFunctionId(Long checkFunctionId) {
		functionService.deleteByCheckFunctionId(checkFunctionId);
	}

	@Override
	public void addAuditFunction(FunctionDTO functionDTO, UserDTO userDTO) {
		int auditLevel = functionDTO.getAuditLevel();
		if (auditLevel < 1) {
			return;
		}

		// 查询是否存在双审核功能、预审核功能
		List<FunctionDTO> functionList = this
				.queryByCheckFunctionId(functionDTO.getFunctionId());

		if (auditLevel < 3) {
			// 普通双审核
			this.checkAndAddAuditFuction(functionList, functionDTO, userDTO,
					FunctionTypeEnum.WORKITEM_COMM_AUDIT);
		} else {
			// 预审核需同时添加普通审核功能
			// 普通双审核
			this.checkAndAddAuditFuction(functionList, functionDTO, userDTO,
					FunctionTypeEnum.WORKITEM_COMM_AUDIT);
			// 风控预审核
			this.checkAndAddAuditFuction(functionList, functionDTO, userDTO,
					FunctionTypeEnum.WORKITEM_RISK_AUDIT);
		}
	}

	/**
	 * 自动添加双权限审核相关功能
	 *
	 * @param functionList
	 * @param functionDTO
	 * @param userDTO
	 * @param functionType
	 */
	private void checkAndAddAuditFuction(List<FunctionDTO> functionList,
	                                     FunctionDTO functionDTO, UserDTO userDTO,
	                                     FunctionTypeEnum functionType) {
		String prefix = "";
		boolean exist = false;
		if (functionType == FunctionTypeEnum.WORKITEM_COMM_AUDIT) {
			prefix = "双审核_";
		} else if (functionType == FunctionTypeEnum.WORKITEM_RISK_AUDIT) {
			prefix = "预审核_";
		} else {
			// 扩展用
		}
		if (functionList != null) {
			for (FunctionDTO fun : functionList) {
				if (functionType == fun.getFunctionType()) {
					exist = true;
				}
			}
		}
		if (!exist) {
			FunctionDTO newFunctionDTO = new FunctionDTO();
			newFunctionDTO.setCreateTime(new Timestamp(System
					.currentTimeMillis()));
			newFunctionDTO
					.setDescription(prefix + functionDTO.getDescription());
			newFunctionDTO.setDisplay(true);
			newFunctionDTO.setFunctionName(prefix
					+ functionDTO.getFunctionName());
			newFunctionDTO.setFunctionStatus(FunctionStatusEnum.ACTIVE);
			newFunctionDTO.setCheckFunctionId(functionDTO.getFunctionId());
			newFunctionDTO.setFunctionType(functionType);
			newFunctionDTO.setRiskLevel(functionDTO.getRiskLevel());
			newFunctionDTO.setPreFunctionId(functionDTO.getPreFunctionId());

			functionService.addFunction(newFunctionDTO);
			// 如果是部门管理员，则添加部门功能关系
			if (userDTO.getIsAdmin()) {
				departmentAndFunctionRelationService
						.createDepartmentAndFunctionRelation(
								userDTO.getPrimaryDepartmentId(),
								newFunctionDTO.getFunctionId());
			}
		}
	}

	@Override
	public void deleteByCheckFunctionIdAndType(Long checkFunctionId,
	                                           String functionType) {
		functionService.deleteByCheckFunctionIdAndType(checkFunctionId,
				functionType);
	}

	/**
	 * 是否打开用户信息同步开关
	 *
	 * @return
	 */
	private boolean isSynchronizationOpen() {
		@SuppressWarnings("unchecked")
		ConfigParam<String> param = ConfigurationUtils
				.getAppConfigParam("employee-boss-synchronization");
		if (param != null && param.getValue() != null
				&& "1".equals(param.getValue().trim())) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zbjdl.boss.admin.biz.SecurityConfigBiz#changeDepartment(java
	 * .lang.Long, java.lang.Long)
	 */
	@Transactional
	@Override
	public void changeDepartment(Long userId, Long newDepartmnetId) {
		CheckUtils.notEmpty(userId, "操作员ID");
		CheckUtils.notEmpty(newDepartmnetId, "新切换部门ID");

		// 切换部门
		userService.updateUserPrimaryDepartmentId(userId, newDepartmnetId);

		// 修改权限
		userAndRoleRelationService.queryByUserId(userId);
		List<UserAndRoleRelationEntity> userAndRoleRelationEntityList = userAndRoleRelationService
				.queryByRoleId(userId);
		List<UserAndRoleRelationEntity> delList = new ArrayList<UserAndRoleRelationEntity>();
		for (UserAndRoleRelationEntity entity : userAndRoleRelationEntityList) {
			if (entity.getRoleId().equals(UserTypeEnum.OPERATOR.getRoleId())) {
				delList.add(entity);
			}
		}
		if (!delList.isEmpty()) {
			userAndRoleRelationService.deleteAll(userAndRoleRelationEntityList);
		}
	}

	@Override
	public void migrateDepartment(Long departmentId, Long newDepartmentId,
	                              boolean migrateUser, boolean migrateRole, boolean migrateFunction) {
		CheckUtils.notEmpty(departmentId, "待迁移部门ID");
		CheckUtils.notEmpty(newDepartmentId, "拟迁移到部门ID");
		if (departmentId == newDepartmentId) {
			throw new IllegalArgumentException("不允许将部门迁移到自身.");
		}

		// 迁移用户
		if (migrateUser) {
			userService.migrateUser(departmentId, newDepartmentId);
		} else {
			userService.batchDeleteUserByDepartmentId(departmentId);
		}

		// 迁移角色
		if (migrateRole) {
			roleService.migrateRole(departmentId, newDepartmentId);
		} else {
			roleService.deleteRoleByDepartmentId(departmentId);
		}

		// 迁移功能
		if (migrateFunction) {
			functionService.migrateFunction(departmentId, newDepartmentId);
		} else {
			functionService.deleteByDepartmentId(departmentId);
		}

		// 删除原部门
		departmentService.deleteDepartment(departmentId);

		RemoteCacheUtils.remove(RemoteCacheUtils
				.generateCacheKey("queryAllDepartments"));
	}

}