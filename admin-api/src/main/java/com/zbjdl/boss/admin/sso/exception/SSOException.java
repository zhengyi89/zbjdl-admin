/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.sso.exception;

import com.zbjdl.boss.admin.sso.enums.SSOExceptionEnum;
import com.zbjdl.common.exception.BaseException;

/**
 * 单点登录异常
 * 
 * @author：feng
 * @since：2012-9-25 下午6:03:26
 * @version:
 */
public class SSOException extends BaseException {
	private static final long serialVersionUID = 2162316153971500566L;

	/**
	 * 
	 * 创建一个新的实例 SSOException.
	 * 
	 * @param defineCode
	 */
	protected SSOException(String defineCode) {
		super(defineCode);
	}

	/**
	 * 
	 * 创建一个新的实例 SSOException.
	 * 
	 * @param ssoExceptionEnum
	 */
	public SSOException(SSOExceptionEnum ssoExceptionEnum) {
		super(ssoExceptionEnum.toString());
	}

	/**
	 * 
	 * 创建一个新的实例 SSOException.
	 * 
	 * @param ssoExceptionEnum
	 * @param message
	 * @param args
	 */
	public SSOException(SSOExceptionEnum ssoExceptionEnum, String message,
			Object... args) {
		super(ssoExceptionEnum.toString());
		super.setMessage(message, args);
	}

}
