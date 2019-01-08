/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.workitem.exception;

import com.zbjdl.common.exception.BaseException;

/**
 * 
 * 类名称：WorkItemException <br>
 * 类描述： <br>
 * 
 * @author：feng
 * @since：2011-11-18 下午02:46:55
 * @version:
 * 
 */
public class WorkItemException extends BaseException {

	private static final long serialVersionUID = 8126126298827751037L;

	/**
	 * 审核记录不存在
	 */
	public static WorkItemException WORKITME_IS_NULL = new WorkItemException(
			"001");

	/**
	 * 审核记录已废弃
	 */
	public static WorkItemException WORKITME_IS_FORBIDDEN = new WorkItemException(
			"002");

	/**
	 * 审核记录已被锁定
	 */
	public static WorkItemException WORKITME_IS_LOCKED = new WorkItemException(
			"003");

	/**
	 * 只有提交人才能撤销自己的审核
	 */
	public static WorkItemException WORKITME_CANCEL_ERROR = new WorkItemException(
			"004");

	/**
	 * 记录已被处理，不可撤销
	 */
	public static WorkItemException WORKITME_HAS_DONE = new WorkItemException(
			"005");

	/**
	 * 提交人不可审核自己的申请
	 */
	public static WorkItemException WORKITME_OPERATOR_ERROR = new WorkItemException(
			"006");

	/**
	 * 没有双重复核权限
	 */
	public static WorkItemException OPERATOR_NO_RECHECK = new WorkItemException(
			"007");
	/**
	 * 业务执行失败
	 */
	public static WorkItemException BIZ_EXECUTE_FAIL = new WorkItemException(
			"008");

	/**
	 * 双权限审核状态错误
	 */
	public static WorkItemException WORKITME_STATUS_ERROR = new WorkItemException(
			"009");

	/**
	 * 创建一个新的实例 WorkItemException.
	 * 
	 * @param defineCode
	 */
	protected WorkItemException(String defineCode) {
		super(defineCode);
	}

}
