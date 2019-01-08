/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.workitem.enums;

/**
 * 
 * 类名称：WorkItemStatusEnum <br>
 * 类描述： 审核状态枚举类<br>
 * 
 * @author：feng
 * @since：2011-11-16 下午04:34:27
 * @version:
 * 
 */
public enum WorkItemStatusEnum {
	/**
	 * 新建状态，等待风控审核
	 */
	RISK_WAITING,

	/**
	 * 风控锁定
	 */
	RISK_CHECKING,

	WAITING, // 待审核状态

	CHECKING, // 审核中状态

	SUCESS, // 审核通过状态

	REFUSE, // 审核拒绝状态

	FORBIDDEN, // 废弃状态

	ERROR
	// 审核异常
}
