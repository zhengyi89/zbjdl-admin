/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.function.service;

import java.util.List;

import com.zbjdl.boss.admin.function.entity.FunctionDependenceEntity;

/** 
 * 
 * 功能依赖服务接口定义
 *    
 * @author：feng    
 * @since：2012-4-11 下午5:34:08 
 * @version:   
 */
public interface FunctionDependenceService {
	/**
	 * 获取全部功能依赖
	 * @return
	 */
	public List<FunctionDependenceEntity>getAllDependenceList();
}
