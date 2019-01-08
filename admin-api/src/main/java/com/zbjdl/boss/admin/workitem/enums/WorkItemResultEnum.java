/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.workitem.enums;

/**    
 *    
 * 类名称：WorkItemResultEnum <br>    
 * 类描述：   审核结果枚举类<br>
 * @author：feng    
 * @since：2011-11-16 下午04:34:27 
 * @version:     
 *     
 */
public enum WorkItemResultEnum {
	
	SYSEXCEPTION("000"),//系统异常
	
	UNDEFINED("999"),//未定义
	
	SUCCESS("001"),//执行成功
	
	FAIL("002"),//执行失败
	
	ACTIONEXCEPTION("003");//执行action异常
	

	/**
	 * 错误码和错误信息的分隔符
	 */
	private final static String separateForResult = "_";
	
	private WorkItemResultEnum(String result) {
		//获取第一个下划线的位置
		int separeteIndex =  result.indexOf(separateForResult);
		if(separeteIndex>=0){
			this.resultCode = result.substring(0,separeteIndex);
		}else{
			this.resultCode = result;
		}
	}
	
	private String resultCode;
	
	public String getResultCode() {
		return resultCode;
	}

	public static WorkItemResultEnum getResultByCode(String resultCode) {
		if (resultCode == null) {
			return UNDEFINED;
		}
		
		for (WorkItemResultEnum result : WorkItemResultEnum.values()) {
			if (result.getResultCode().equals(resultCode)) {
				return result;
			}
		}
		return UNDEFINED;
	}
	
}
