/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.exception;

import com.zbjdl.common.exception.BaseException;

/**    
 * 类名称：UserAuthenticationException <br>
 * 类描述：   <br>
 * @author feng
 * @since 2011-6-22 上午11:31:31
 * @version 1.0.0
 */
public class UserAuthenticationException extends BaseException{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 用户认证失败异常
	 */
	public static UserAuthenticationException USERAUTHEN_FAIL_EXCEPTION= new UserAuthenticationException("001");
	
	/**
	 * 用户没有权限异常
	 */
	public static UserAuthenticationException USERAUTHEN_NO_FUNCTION_EXCEPTION =new UserAuthenticationException("002");

	/**
	 * 用户已冻结异常
	 */
	public static UserAuthenticationException USER_FROZEN_EXCEPTION =new UserAuthenticationException("003");

	/**
	 * 用户不存在异常
	 */
	public static UserAuthenticationException USER_NOEXIST_EXCEPTION =new UserAuthenticationException("004");

	/**    
	 * 创建一个新的实例 UserAuthenticationException.    
	 *    
	 * @param defineCode    
	 */
	protected UserAuthenticationException(String defineCode) {
		super(defineCode);
	}

	public UserAuthenticationException newInstance(String message, Object... args) {
		UserAuthenticationException ex = new UserAuthenticationException(this.defineCode);
		ex.setMessage(message, args);
		return ex;
	}
}
