package com.zbjdl.boss.admin.utils.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.operationlog.entity.OperationLogTemplateEntity;

/**
 * 类名称：FunctionsAndTemplatesCache <br>
 * 类描述：功能列表和日志模板单例类<br>
 * @author xuebo.yang
 * @since 2012-1-16 下午05:33:13
 * @version:
 * 
 */
public class FunctionsAndTemplatesCache {
	/**
	 * 判断是否含有功能列表
	 */
	private boolean hasSetFunctions;
	/**
	 * 判断是否含有日志模板
	 */
	private boolean hasSetTemplates;
	/**
	 * 功能列表
	 */
	private List<FunctionDTO> functionList;
	/**
	 * 操作日志模板
	 */
	private Map<Long,OperationLogTemplateEntity> templatesMap ;
	
	private static FunctionsAndTemplatesCache INSTANCE;
	
	/**
	 * 获取单例
	 * @return
	 */
	public static FunctionsAndTemplatesCache getInstance() {
		if (INSTANCE == null) {
			synchronized (FunctionsAndTemplatesCache.class) {
				if (INSTANCE == null) {
					INSTANCE = new FunctionsAndTemplatesCache();
				}
			}
		}
		return INSTANCE;
	}

	public boolean isHasSetFunctions() {
		return hasSetFunctions;
	}

	public boolean isHasSetTemplates() {
		return hasSetTemplates;
	}

	public List<FunctionDTO> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<FunctionDTO> functionList) {
		this.functionList = functionList;
		this.hasSetFunctions = true;
	}

	public Map<Long, OperationLogTemplateEntity> getTemplatesMap() {
		return templatesMap;
	}

	public void setTemplatesMap(List<OperationLogTemplateEntity> templatesList) {
		templatesMap = new HashMap<Long,OperationLogTemplateEntity>();
		for(OperationLogTemplateEntity entity:templatesList){
			this.templatesMap.put(entity.getFunctionId(), entity);
		}
		this.hasSetTemplates=true;
	}
	
	
}
