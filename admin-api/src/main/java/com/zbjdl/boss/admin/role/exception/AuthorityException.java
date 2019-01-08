/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.role.exception;

import com.zbjdl.common.exception.BaseException;

/**
 * 
 * 类名称：RoleException <br>
 * 类描述： 权限异常 <br>
 * 
 * @author：feng
 * @since：2011-7-5 下午03:37:48
 * @version:
 * 
 */
public class AuthorityException extends BaseException {

	private static final long serialVersionUID = 9188478432183607298L;

	/**
	 * 角色不存在异常
	 */
	public static AuthorityException ROLE_NOTEXIST_EXCEPTION = new AuthorityException(
			"001");

	/**
	 * 角色已存在异常
	 */
	public static AuthorityException ROLE_EXIST_EXCEPTION = new AuthorityException(
			"002");

	/**
	 * 用户和角色关系已存在异常
	 */
	public static AuthorityException USERANDROLERELATION_EXIST_EXCEPTION = new AuthorityException(
			"003");

	/**
	 * 角色正在使用中，不可删除异常
	 */
	public static AuthorityException ROLE_INUSE_EXCEPTION = new AuthorityException(
			"004");

	/**
	 * 用户和角色关系不存在异常
	 */
	public static AuthorityException USERANDROLERELATION_NOTEXIST_EXCEPTION = new AuthorityException(
			"004");

	/**
	 * 角色和功能关系已存在异常
	 */
	public static AuthorityException ROLEANDFUNCTIONRELATION_EXIST_EXCEPTION = new AuthorityException(
			"006");

	/**
	 * 角色和功能关系不存在异常
	 */
	public static AuthorityException ROLEANDFUNCTIONRELATION_NOTEXIST_EXCEPTION = new AuthorityException(
			"007");

	/**
	 * 角色中不能含有互斥功能
	 */
	public static AuthorityException ROLE_EXCLUSIVE_EXCEPTION = new AuthorityException(
			"008");

	/**
	 * 创建一个新的实例 RoleException.
	 * 
	 * @param defineCode
	 */
	public AuthorityException(String defineCode) {
		super(defineCode);
	}

	public AuthorityException newInstance(String message, Object... args) {
		AuthorityException ex = new AuthorityException(this.defineCode);
		ex.setMessage(message, args);
		return ex;

	}

}
