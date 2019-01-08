/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.exception;

import com.zbjdl.common.exception.BaseException;

/**
 * 
 * 类名称：FunctionException <br>
 * 类描述：功能业务异常定义 <br>
 * 
 * @author：feng
 * @since：2011-7-6 下午03:49:22
 * @version:
 * 
 */
public class FunctionException extends BaseException {

	private static final long serialVersionUID = 1L;

	/**
	 * 前置功能不能删除
	 */
	public static FunctionException PRE_FUNCTION_CAN_NOT_BE_DELETED_EXCEPTION = new FunctionException(
			"001");

	/**
	 * 前置功能不能冻结
	 */
	public static FunctionException PRE_FUNCTION_CAN_NOT_BE_FREEZED_EXCEPTION = new FunctionException(
			"002");

	/**
	 * 前置功能不能停用
	 */
	public static FunctionException PRE_FUNCTION_CAN_NOT_BE_FORBIDED_EXCEPTION = new FunctionException(
			"003");

	/**
	 * 如果功能处于停用状态，则不能被激活
	 */
	public static FunctionException FUNCTION_CAN_NOT_BE_ACTIVATED_ON_FORBIDDEN_EXCEPTION = new FunctionException(
			"004");

	/**
	 * 如果功能处于停用状态，则不能被冻结
	 */
	public static FunctionException FUNCTION_CAN_NOT_BE_FREEZED_ON_FORBIDDEN_EXCEPTION = new FunctionException(
			"005");

	/**
	 * 功能的状态不属于激活、冻结、停用
	 */
	public static FunctionException INVALID_FUNCTION_STATUS_EXCEPTION = new FunctionException(
			"006");

	/**
	 * 功能已经被激活
	 */
	public static FunctionException FUNCTION_HAS_BEEN_ACTIVED_EXCEPTION = new FunctionException(
			"007");

	/**
	 * 功能已经被冻结
	 */
	public static FunctionException FUNCTION_HAS_BEEN_FREEZED_EXCEPTION = new FunctionException(
			"008");

	/**
	 * 功能已存在
	 */
	public static FunctionException FUNCTIION_HAS_EXISTED_EXCEPTION = new FunctionException(
			"009");

	/**
	 * 功能不存在
	 */
	public static FunctionException FUNCTION_NOT_EXIST_EXCEPTION = new FunctionException(
			"010");

	/**
	 * 功能被冻结时修改
	 */
	public static FunctionException FUNCTION_CAN_NOT_BE_UPDATE_ON_FROZEN_EXCEPTION = new FunctionException(
			"011");

	/**
	 * 功能被停用时修改
	 */
	public static FunctionException FUNCTION_CAN_NOT_BE_UPDATE_ON_FORBIDDEN_EXCEPTION = new FunctionException(
			"012");

	/**
	 * 没有权限添加功能
	 */
	public static FunctionException NO_AUTH_TO_ADD_FUNCTION_EXCEPTION = new FunctionException(
			"013");

	/**
	 * 没有权限删除功能
	 */
	public static FunctionException NO_AUTH_TO_DELETE_FUNCTION_EXCEPTION = new FunctionException(
			"014");

	/**
	 * 没有权更新除功能
	 */
	public static FunctionException NO_AUTH_TO_UPDATE_FUNCTION_EXCEPTION = new FunctionException(
			"015");

	/**
	 * 没有权限激活功能
	 */
	public static FunctionException NO_AUTH_TO_ACTIVATE_FUNCTION_EXCEPTION = new FunctionException(
			"016");

	/**
	 * 没有权限冻结功能
	 */
	public static FunctionException NO_AUTH_TO_FREEZE_FUNCTION_EXCEPTION = new FunctionException(
			"017");

	/**
	 * 没有权限停用功能
	 */
	public static FunctionException NO_AUTH_TO_FORBID_FUNCTION_EXCEPTION = new FunctionException(
			"018");

	protected FunctionException(String defineCode) {
		super(defineCode);
	}

}
