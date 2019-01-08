/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.function.enums;

/**
 *
 * 类名称：FunctionTypeEnum <br>
 * 类描述： 功能类型枚举类<br>
 *
 * @author：feng
 * @since：2011-7-22 下午04:58:46
 * @version:
 *
 */
public enum FunctionTypeEnum {
	/**
	 * 普通双权限审核
	 */
	WORKITEM_COMM_AUDIT,

	/**
	 * 双权限风控审核
	 */
	WORKITEM_RISK_AUDIT,

	/**
	 * 部门公开
	 */
	DEPARTMENT_PUBLIC,

	/**
	 * 部门私有
	 */
	DEPARTMENT_PRIVATE,

	/**
	 * 用户公开
	 */
	USER_PUBLIC,

	/**
	 * 系管员私有
	 */
	SYSADMIN_PRIVATE;
}
