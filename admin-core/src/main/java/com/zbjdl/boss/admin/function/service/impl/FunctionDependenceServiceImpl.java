/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.function.service.impl;

import java.util.List;

import com.zbjdl.boss.admin.function.entity.FunctionDependenceEntity;
import com.zbjdl.boss.admin.function.service.FunctionDependenceService;
import com.zbjdl.boss.admin.repository.FunctionDependenceDao;

/**
 * 
 * 功能依赖服务接口定义实现
 *     
 * @author：feng    
 * @since：2012-4-11 下午5:35:21 
 * @version:   
 */
public class FunctionDependenceServiceImpl implements FunctionDependenceService {
	
	private FunctionDependenceDao functionDependenceDao;

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.function.service.FunctionDependenceService#getAllDependenceList()    
	 */
	@Override
	public List<FunctionDependenceEntity> getAllDependenceList() {
		return functionDependenceDao.findAll();
	}

	/**    
	 * @param functionDependenceDao the functionDependenceDao to set    
	 */
	public void setFunctionDependenceDao(FunctionDependenceDao functionDependenceDao) {
		this.functionDependenceDao = functionDependenceDao;
	}
	
}
