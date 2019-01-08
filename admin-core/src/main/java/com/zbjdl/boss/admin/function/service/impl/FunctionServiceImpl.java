/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.function.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.function.dto.ExclusiveFunctionDTO;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.function.entity.ExclusiveFunctionEntity;
import com.zbjdl.boss.admin.function.entity.FunctionEntity;
import com.zbjdl.boss.admin.function.service.FunctionService;
import com.zbjdl.boss.admin.repository.ExclusiveFunctionDao;
import com.zbjdl.boss.admin.repository.FunctionDao;
import com.zbjdl.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名称：FunctionServiceImpl <br>
 * 类描述：功能服务实现 <br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-7-5 下午04:36:16
 */
public class FunctionServiceImpl implements FunctionService {

	/**
	 * 对应映射文件中ID为query的查询语句
	 */
	public static final String MAPPING_QUERY_FUNCTION_BY_DEPT_ID = "query";

	/**
	 * 对应映射文件中ID为queryFuntionByCheckFunctionId的查询语句
	 */
	public static final String MAPPING_QUERY_FUNTION_BY_CHECKFUNCTIONID = "queryFuntionByCheckFunctionId";

	/**
	 * 对应映射文件中ID为deleteFuntionByCheckFunctionId的查询语句
	 */
	public static final String MAPPING_DELETE_FUNTION_BY_CHECKFUNCTIONID = "deleteFuntionByCheckFunctionId";

	/**
	 * 对应映射文件中ID为isPreFunction的查询语句
	 */
	public static final String MAPPING_IS_PRE_FUNCTION = "isPreFunction";

	/**
	 * 对应映射文件中ID为queryFuntionByUserId的查询语句
	 */
	public static final String MAPPING_QUERY_FUNTION_BY_USERID = "queryFuntionByUserId";

	/**
	 * 对应映射文件中ID为queryFuntionByUserId的查询语句
	 */
	public static final String MAPPING_QUERY_FUNTION_BY_DEPARTMENTID_AND_FunctionId = "queryFuntionByDepartmentIdAndFunctionId";

	/**
	 * 对应映射文件中ID为queryFunctionByUri的查询语句
	 */
	public static final String MAPPING_QUERY_FUNTION_BY_URI = "queryFunctionByUri";

	/**
	 * 对应映射文件中ID为queryFuntionByDepartmentId的查询语句
	 */
	public static final String MAPPING_QUERY_FUNTION_BY_DEPARTMENTID = "queryFuntionByDepartmentId";

	/**
	 * 对应映射文件中ID为queryFuntionByRoleId的查询语句
	 */
	public static final String MAPPING_QUERY_FUNTION_BY_ROLEID = "queryFuntionByRoleId";

	private FunctionDao functionDao;

	private ExclusiveFunctionDao exclusiveFunctionDao;

	private Convert<FunctionDTO, FunctionEntity> functionConvert;

	private Convert<ExclusiveFunctionDTO, ExclusiveFunctionEntity> exclusiveFunctionConvert;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.function.service.FunctionService#createFunction
	 * (com.zbjdl.boss.admin.function.dto.FunctionDTO)
	 */
	public Long createFunction(FunctionDTO functionDTO) {
		FunctionEntity functionEntity = functionConvert.convert(functionDTO);
		functionDao.save(functionEntity);
		return functionEntity.getFunctionId();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.function.service.FunctionService#deleteFunction
	 * (java.lang.Long)
	 */
	public void deleteFunction(Long functionID) {
		functionDao.delete(functionID);
	}

	public void updateFunction(FunctionDTO functionDTO) {
		FunctionEntity functionEntity = functionConvert.convert(functionDTO);
		functionDao.update(functionEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void migrateFunction(Long departmentId, Long newDepartmentId) {
		Map<String, Long> map = Maps.newHashMap();
		map.put("departmentId", departmentId);
		map.put("newDepartmentId", newDepartmentId);
		List functionIdList = functionDao.queryFuntionIdByDepartmentId(map);
		for (Object functionId : functionIdList) {
			map.put("functionId", (Long) functionId);
			functionDao.migrateFunction(map);
		}
		functionDao.batchDeleteById(functionIdList);
	}

	@Override
	public void addFunction(FunctionDTO functionDTO) {
		FunctionEntity functionEntity = functionConvert.convert(functionDTO);
		if (functionEntity.getFunctionId() != null) {
			functionDao.save(functionEntity);
		} else {
			functionDao.insertWithNullFunctionId(functionEntity);
			if (functionDTO.getFunctionId() == null) {
				functionDTO.setFunctionId(functionEntity.getFunctionId());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zbjdl.boss.admin.function.service.FunctionService#
	 * queryFunctionByID(java.lang.Long)
	 */
	public FunctionDTO queryFunctionByID(Long functionID) {
		FunctionEntity functionEntity = functionDao.selectById(functionID);
		return functionConvert.convert(functionEntity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.zbjdl.boss.admin.function.service.FunctionService#
	 * queryFunctionByDeptID(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FunctionDTO> query(FunctionDTO queryDTO) {
		List<FunctionDTO> functionDTOs = Lists.newArrayList();
		FunctionEntity queryEntity = functionConvert.convert(queryDTO);
		List<FunctionEntity> functionEntities = functionDao.queryByFunction(queryEntity);
		for (FunctionEntity functionEntity : functionEntities) {
			FunctionDTO functionDTO = functionConvert.convert(functionEntity);
			functionDTOs.add(functionDTO);
		}
		return functionDTOs;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.zbjdl.boss.admin.function.service.FunctionService#isPreFunction
	 * (java.lang.Long)
	 */
	public boolean isPreFunction(Long functionID) {
		int count = (Integer) functionDao.isPreFunction(functionID);
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.function.service.FunctionService#getAllExclusiveFunction()
	 */
	@Override
	public List<ExclusiveFunctionDTO> getAllExclusiveFunction() {
		List<ExclusiveFunctionDTO> dtos = Lists.newArrayList();
		List<ExclusiveFunctionEntity> exclusiveFunctionEntities = exclusiveFunctionDao.findAll();
		for (ExclusiveFunctionEntity entity : exclusiveFunctionEntities) {
			ExclusiveFunctionDTO dto = exclusiveFunctionConvert.convert(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.function.service.FunctionService#queryFuntionByUserId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FunctionDTO> queryFuntionByUserId(Long userId) {
		Map<String, Long> map = Maps.newHashMap();
		map.put("userId", userId);
		List<FunctionEntity> functionEntities = functionDao.queryFuntionByUserId(map);
		List<FunctionDTO> functionDTOs = Lists.newArrayList();
		if (functionEntities != null) {
			for (FunctionEntity functionEntity : functionEntities) {
				FunctionDTO functionDTO = functionConvert
						.convert(functionEntity);
				functionDTOs.add(functionDTO);
			}
		}
		return functionDTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FunctionDTO> queryFunctionByDepartmentAndFunctionId(Long departmentId,
	                                                                Long functionId) {
		Map<String, Long> map = Maps.newHashMap();
		map.put("departmentId", departmentId);
		map.put("functionId", functionId);
		List<FunctionEntity> functionEntities = functionDao.queryFuntionByDepartmentIdAndFunctionId(map);
		List<FunctionDTO> functionDTOs = Lists.newArrayList();
		if (functionEntities != null) {
			for (FunctionEntity functionEntity : functionEntities) {
				FunctionDTO selectFunctionDTO = functionConvert
						.convert(functionEntity);
				functionDTOs.add(selectFunctionDTO);
			}
		}
		return functionDTOs;
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.function.service.FunctionService#queryFunctionByUri(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public FunctionDTO queryFunctionByUri(String uri) {
		FunctionDTO functionDTO = null;
		if (uri != null) {
			// 处理uri中包含.action ，而数据库function的url没配置后缀名的问题
			uri = uri.replaceAll("\\.[^\\.^/]+$", "");
		}
//		List<FunctionEntity> functionEntities = functionDao.query(MAPPING_QUERY_FUNTION_BY_URI, "%" + uri + "%");
		List<FunctionEntity> functionEntities = functionDao.queryFunctionByUri("%" + uri + "%");
		if (functionEntities != null && !functionEntities.isEmpty()) {
			// 寻找最匹配的，如果结尾是空白，或者结尾以  “.|?” 开始，则说明uri匹配正确了
			for (FunctionEntity function : functionEntities) {
				String end = StringUtils.substringAfterLast(function.getFunctionUrl(), uri);
				if (StringUtils.isBlank(end) || end.startsWith(".") || end.startsWith("?")) {
					functionDTO = functionConvert.convert(function);
					break;
				}
			}

		}
		return functionDTO;
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.function.service.FunctionService#queryFuntionByDepartmentId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FunctionDTO> queryFuntionByDepartmentId(Long departmentId) {
		Map<String, Long> map = Maps.newHashMap();
		map.put("departmentId", departmentId);
		List<FunctionEntity> functionEntities = functionDao.queryFuntionByDepartmentId(map);
		List<FunctionDTO> functionDTOs = Lists.newArrayList();
		if (functionEntities != null) {
			for (FunctionEntity functionEntity : functionEntities) {
				FunctionDTO functionDTO = functionConvert
						.convert(functionEntity);
				functionDTOs.add(functionDTO);
			}
		}
		return functionDTOs;
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.function.service.FunctionService#queryFuntionByRoleId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FunctionDTO> queryFuntionByRoleId(Long roleId) {
		Map<String, Long> map = Maps.newHashMap();
		map.put("roleId", roleId);
		List<FunctionEntity> functionEntities = functionDao.queryFuntionByRoleId(map);
		List<FunctionDTO> functionDTOs = Lists.newArrayList();
		if (functionEntities != null) {
			for (FunctionEntity functionEntity : functionEntities) {
				FunctionDTO functionDTO = functionConvert
						.convert(functionEntity);
				functionDTOs.add(functionDTO);
			}
		}
		return functionDTOs;
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.boss.admin.function.service.FunctionService#getAllFunction()
	 */
	@Override
	public List<FunctionDTO> getAllFunction() {
		List<FunctionEntity> functionEntities = functionDao.findAll();
		List<FunctionDTO> functionDTOs = Lists.newArrayList();
		if (functionEntities != null) {
			for (FunctionEntity functionEntity : functionEntities) {
				FunctionDTO functionDTO = functionConvert
						.convert(functionEntity);
				functionDTOs.add(functionDTO);
			}
		}
		return functionDTOs;
	}

	@Override
	public List<FunctionDTO> queryByCheckFunctionId(Long checkFunctionId) {
		Map<String, Long> map = Maps.newHashMap();
		map.put("checkFunctionId", checkFunctionId);
		@SuppressWarnings("unchecked")
		List<FunctionEntity> functionEntities = functionDao.queryFuntionByCheckFunctionId(map);
		List<FunctionDTO> functionDTOs = Lists.newArrayList();
		if (functionEntities != null) {
			for (FunctionEntity functionEntity : functionEntities) {
				FunctionDTO functionDTO = functionConvert
						.convert(functionEntity);
				functionDTOs.add(functionDTO);
			}
		}
		return functionDTOs;
	}

	@Override
	public void deleteByDepartmentId(Long departmentId) {
		functionDao.deleteByDepartmentId(departmentId);
		functionDao.deleteRelationByDepartmentId(departmentId);
	}

	@Override
	public void deleteByCheckFunctionId(Long checkFunctionId) {
		functionDao.deleteFuntionByCheckFunctionId(checkFunctionId);
	}

	@Override
	public void deleteByCheckFunctionIdAndType(Long checkFunctionId,
	                                           String functionType) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("checkFunctionId", checkFunctionId);
		map.put("functionType", functionType);
		functionDao.deleteByCheckFunctionIdAndType(map);
	}

	/**
	 * functionDao
	 *
	 * @return the functionDao
	 */
	public FunctionDao getFunctionDao() {
		return functionDao;
	}

	/**
	 * 描述： 注入functionDao
	 *
	 * @param functionDao the functionDao to set
	 */
	public void setFunctionDao(FunctionDao functionDao) {
		this.functionDao = functionDao;
	}

	/**
	 * functionConvert
	 *
	 * @return the functionConvert
	 */
	public Convert<FunctionDTO, FunctionEntity> getFunctionConvert() {
		return functionConvert;
	}

	/**
	 * 描述： 注入functionConvert
	 *
	 * @param functionConvert the functionConvert to set
	 */
	public void setFunctionConvert(
			Convert<FunctionDTO, FunctionEntity> functionConvert) {
		this.functionConvert = functionConvert;
	}

	public void setExclusiveFunctionDao(ExclusiveFunctionDao exclusiveFunctionDao) {
		this.exclusiveFunctionDao = exclusiveFunctionDao;
	}

	public void setExclusiveFunctionConvert(
			Convert<ExclusiveFunctionDTO, ExclusiveFunctionEntity> exclusiveFunctionConvert) {
		this.exclusiveFunctionConvert = exclusiveFunctionConvert;
	}

}
