package com.zbjdl.boss.admin.user.exception;

import com.zbjdl.common.exception.BaseException;

public class DepartmentException extends BaseException{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 部门不存在异常
	 */
	public static DepartmentException DEPARTMENT_NOTEXIST_EXCEPTION = new DepartmentException("001");
	
	/**
	 * 部门已存在 异常
	 */
	public static DepartmentException DEPARTMENT_EXIST_EXCEPTION = new DepartmentException("002");

	/**
	 * 部门编号冲突异常
	 */
	public static DepartmentException DEPARTMENTCODE_CONFLICT_EXCEPTION = new DepartmentException("003");
	
	/**
	 * 没有权限删除部门
	 */
	public static DepartmentException NO_AUTH_TO_DELETE_DEPARTMENT = new DepartmentException("004");
	
	/**
	 * 当有用户时部门删除部门
	 */
	public static DepartmentException DEPARTMENT_CAN_NOT_BE_DELETE_HAVING_USER = new DepartmentException("005");
	
	protected DepartmentException(String defineCode) {
		super(defineCode);
	}
	
	public DepartmentException newInstance(String message, Object... args) {
		DepartmentException ex = new DepartmentException(this.defineCode);
		ex.setMessage(message, args);
		return ex;
	}

}
