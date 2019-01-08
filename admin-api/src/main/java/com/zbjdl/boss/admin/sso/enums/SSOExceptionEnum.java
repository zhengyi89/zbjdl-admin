/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.sso.enums;

/**    
 * @author：feng    
 * @since：2012-9-25 下午6:14:09 
 * @version:   
 */
public enum SSOExceptionEnum {
	/**
	 * 过期访问
	 */
	LoginExpire,
	/**
	 * 加密失败
	 */
	SignFailed,
	/**
	 * 解密失败
	 */
	UnsignFailed,
	/**
	 * 无效token
	 */
	InvalidToken,
	/**
	 * 非法访问
	 */
	IllegalAccess
}
