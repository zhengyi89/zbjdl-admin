package com.zbjdl.boss.admin.user.exception;

import com.zbjdl.common.exception.BaseException;

/**
 * 创建用户异常
 * @author：feng    
 * @since：2011-6-20 下午08:30:41 
 * @version:1.0
 */
public class UserException extends BaseException{
	
	/**    
	 *
	 */    
	private static final long serialVersionUID = 1L;

	/**
	 * 用户名已存在异常
	 */
	public static UserException USERNAME_EXIST_EXCEPTION = new UserException("001");
	
	/**
	 * 用户所属的部门不存在
	 */
	public static UserException DEPARTMENT_NOTEXIST_EXCEPTION = new UserException("002");
	
	/**
	 * 用户不存在异常
	 */
	public static UserException USER_NOTEXIST_EXCEPTION = new UserException("003");

	/**
	 * 用户不可删除异常
	 */
	public static UserException USER_NODELETE_EXCEPTION = new UserException("004");
	
	/**
	 * 用户信息不合法异常 
	 */
	public static UserException USERINFO_ERROR_EXCEPTION = new UserException("005");
	
	/**
	 * 用户不可冻结
	 */
	public static UserException USER_NONEFROZEN_EXCEPTION = new UserException("006");
	/**
	 * 用户不可激活
	 */
	public static UserException USER_NONEACTIVITE_EXCEPTION = new UserException("007");
	/**
	 * 用户不可停用
	 */
	public static UserException USER_NONEFORBIDDEN_EXCEPTION = new UserException("008");
	/**
	 * 不是管理员，没有权限异常
	 */
	public static UserException USER_ISNOTADMIN_EXCEPTION = new UserException("009");
	/**
	 * 管理员和用户不属于同一部门异常
	 */
	public static UserException USERANDADMIN_DEPARTID_EXCEPTION = new UserException("010");

	/**
	 * 用户主部门不存在
	 */
	public static UserException USERPRIMARYID_NOEXIST_EXCEPTION = new UserException("011");
	
	/**
	 * 不可新建超级管理员异常
	 */
	public static UserException SUPERADMINUSER_NOCREATING_EXCEPTION = new UserException("012");
	
	/**
	 * 不是超级管理员不可创建部门管理员异常
	 */
	public static UserException SUPERADMINUSER_ISNOT_EXCEPTION = new UserException("013");
	
	/**
	 * 系统管理员只能创建管理员，不可以创建操作员
	 */
	public static UserException SUPERADMIN_ONLYCREATEADMIN_EXCEPTION = new UserException("014");
	
	/**
	 * 没有权限进行操作
	 */
	public static UserException NO_AUTH_TO_OPERATE = new UserException("015");
	
	/**
	 * 一个用户不能同时拥有两个互斥功能
	 */
	public static UserException USRE_FUNCTION_EXCLUSIVE_EXCEPTION = new UserException("016");
	
	protected UserException(String defineCode){
		super(defineCode);
	}
	
	public UserException newInstance(String message, Object... args) {
		UserException ex = new UserException(this.defineCode);
		ex.setMessage(message, args);
		return ex;
	}
}
