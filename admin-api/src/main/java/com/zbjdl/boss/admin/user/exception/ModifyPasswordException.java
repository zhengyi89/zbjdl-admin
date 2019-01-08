/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.exception;

import com.zbjdl.common.exception.BaseException;

/**    
 * 密码修改异常
 * 类名称：PasswordException <br>    
 * 类描述：   <br>
 * @author：feng    
 * @since：2011-6-22 上午11:08:22 
 * @version:1.0     
 *     
 */
public class ModifyPasswordException extends BaseException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户不存在异常
	 */
	public static ModifyPasswordException USER_NOTEXIST_EXCEPTION = new ModifyPasswordException("001");

	/**
	 * 原始密码错误异常
	 */
	public static ModifyPasswordException PASSWORD_ERROR_EXCEPTION = new ModifyPasswordException("002");
	
	/**
	 * 原始密码与新密码相同异常
	 */
	public static ModifyPasswordException PASSWORD_SAME_EXCEPTION = new ModifyPasswordException("003");
	
	/**
	 * 新密码与登录名相同 异常
	 */
	public static ModifyPasswordException PASSWORD_NAME_SAME_EXCEPTION = new ModifyPasswordException("004");
	
	/**    
	 * 创建一个新的实例 ModifyPasswordException.    
	 *    
	 * @param defineCode    
	 */
	protected ModifyPasswordException(String defineCode) {
		super(defineCode);
	}
	
	public ModifyPasswordException newInstance(String message, Object... args) {
		ModifyPasswordException ex = new ModifyPasswordException(this.defineCode);
		ex.setMessage(message, args);
		return ex;
	}

}
