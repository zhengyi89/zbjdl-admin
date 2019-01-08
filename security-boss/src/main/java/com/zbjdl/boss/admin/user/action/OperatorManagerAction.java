/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.user.action;

import com.google.common.collect.Lists;
import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.enums.UserTypeEnum;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;
import com.zbjdl.common.exception.BaseException;
import com.zbjdl.common.json.JSONUtils;
import com.zbjdl.utils.query.QueryService;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

/**
 * 类名称：OperatorManagerAction <br>
 * 类描述： 操作员管理 <br>
 * 
 * @author feng
 * @version 1.0.0
 * @since 2011-7-26 上午10:09:09
 */
public class OperatorManagerAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = -6814909865472234009L;

	private String userStatus;// 状态

	private String loginName;// 登录名

	private String userName;// 姓名

	private Long userId;

	@Autowired
	private QueryService queryService;

	private Long primaryDepartmentId;

	private List<DepartmentDTO> departList = Lists.newArrayList();// 所有部门列表

	private List<UserDTO> operatorList = Lists.newArrayList();

	private Long departmentId;

	private String departmentCode;

	private Long departmentAdminId;

	/**
	 * 新部门ID
	 */
	private Long newPrimaryDepartmentId;

	/**
	 * 跨部门操作员管理
	 * 
	 * @return
	 */
	public String operatorSuperManager() {
		departList = remoteService.getUserFacade().queryAllDepartments();
		return SUCCESS;
	}

	/**
	 * 切换部门
	 * 
	 * @return
	 */
	public String changeDepartment() {
		try {
			UserDTO user = remoteService.getUserFacade().queryUserById(
					userId);
			if (null == user) {
				throw new BaseException("用户不存在！");
			}
			if (user.getPrimaryDepartmentId() != null
					&& user.getPrimaryDepartmentId().equals(
							newPrimaryDepartmentId)) {
				throw new BaseException("新切换部门与原部门相同！");
			}
			UserDTO manager = UserInfoUtils.getUserFromSession(null);
			if (!remoteService.getUserFacade().userAuthentication(
					manager.getUserId(),
					ServletActionContext.getRequest().getParameter("password"))) {
				throw new BaseException("管理员密码错误！");
			}
			remoteService.getSecurityConfigFacade().changeDepartment(
					userId, newPrimaryDepartmentId);
		} catch (Exception e) {
			handleException(e);
		}
		return SUCCESS;
	}

	/**
	 * 根据部门编号查找操作员
	 * 
	 * @return
	 */
	public String loadOperator() {
		try {
			operatorList = remoteService.getUserFacade()
					.queryDepartmentUsers(departmentId);
			errMsg = JSONUtils.toJsonString(operatorList);
		} catch (Exception e) {
			handleException(e);
		}
		return SUCCESS;
	}

	@Override
	public String execute() {
		UserDTO userDTO = UserInfoUtils.getUserFromSession(null);
		departmentAdminId = userDTO.getUserId();
		if (userDTO.getPrimaryDepartmentId() != null) {
			// 部门管理员只能查本部门的操作员
			primaryDepartmentId = userDTO.getPrimaryDepartmentId();
			departmentCode = userDTO.getPrimaryDepartmentId().toString();
		}
		primaryDepartmentId = userDTO.getPrimaryDepartmentId();
		System.out.println(primaryDepartmentId+"|||||||||||999999999999999999999");
		System.out.println(userDTO.getPrimaryDepartmentId()+"|||||||||||999999999999999999999");
		departList = remoteService.getUserFacade().queryAllDepartments();
		return SUCCESS;
	}

	// 激活用户
	public String activeOperator() {
		try {
			HttpSession session = getMVCFacade().getHttpSession();
			HttpServletRequest request = ServletActionContext.getRequest();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							request.getParameter("password"));
			Boolean isOperator = remoteService.getSecurityFacade()
					.checkUserType(userId, UserTypeEnum.OPERATOR);
			if (!passwordRightOrNot) {
				this.errMsg = "密码错误!";
			} else if (!isOperator) {
				this.errMsg = "激活用户不是操作员!";
			} else {
				remoteService.getUserFacade().unFrozeUser(userId, "",
						user.getUserId());
			}
		} catch (Exception e) {
			handleException(e);
		}
		return SUCCESS;
	}

	// 冻结用户
	public String frozenOperator() {
		try {
			HttpSession session = getMVCFacade().getHttpSession();
			HttpServletRequest request = ServletActionContext.getRequest();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							request.getParameter("password"));
			Boolean isOperator = remoteService.getSecurityFacade()
					.checkUserType(userId, UserTypeEnum.OPERATOR);
			UserDTO userDTO = UserInfoUtils.getUserFromSession(null);
			if (!passwordRightOrNot) {
				throw new BaseException("密码错误!");
			}
			if (!passwordRightOrNot) {
				this.errMsg = "密码错误!";
			} else if (!isOperator) {
				this.errMsg = "冻结用户不是操作员!";
			} else {
				remoteService.getUserFacade().frozeUser(userId, "",
						userDTO.getUserId());
			}
		} catch (Exception e) {
			handleException(e);
		}
		return SUCCESS;
	}

	// 删除用户
	public String deleteOperator() {
		try {
			HttpSession session = getMVCFacade().getHttpSession();
			HttpServletRequest request = ServletActionContext.getRequest();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							request.getParameter("password"));
			Boolean isOperator = remoteService.getSecurityFacade()
					.checkUserType(userId, UserTypeEnum.OPERATOR);
			if (!passwordRightOrNot) {
				this.errMsg = "密码错误!";
			} else if (!isOperator) {
				this.errMsg = "删除用户不是操作员!";
			} else {
				remoteService.getUserFacade().deleteUser(userId);
			}
		} catch (Exception e) {
			handleException(e);
		}
		return SUCCESS;
	}

	/**
	 * userStatus
	 * 
	 * @return the userStatus
	 */

	public String getUserStatus() {
		return userStatus;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param userStatus
	 *            the userStatus to set
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * loginName
	 * 
	 * @return the loginName
	 */

	public String getLoginName() {
		return loginName;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param loginName
	 *            the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * userName
	 * 
	 * @return the userName
	 */

	public String getUserName() {
		return userName;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * userId
	 * 
	 * @return the userId
	 */

	public Long getUserId() {
		return userId;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * queryService
	 * 
	 * @return the queryService
	 */

	public QueryService getQueryService() {
		return queryService;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param queryService
	 *            the queryService to set
	 */
	public void setQueryService(QueryService queryService) {
		this.queryService = queryService;
	}

	/**
	 * primaryDepartmentId
	 * 
	 * @return the primaryDepartmentId
	 */

	public Long getPrimaryDepartmentId() {
		return primaryDepartmentId;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param primaryDepartmentId
	 *            the primaryDepartmentId to set
	 */
	public void setPrimaryDepartmentId(Long primaryDepartmentId) {
		this.primaryDepartmentId = primaryDepartmentId;
	}

	public List<DepartmentDTO> getDepartList() {
		return departList;
	}

	public void setDepartList(List<DepartmentDTO> departList) {
		this.departList = departList;
	}

	public List<UserDTO> getOperatorList() {
		return operatorList;
	}

	public void setOperatorList(List<UserDTO> operatorList) {
		this.operatorList = operatorList;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public Long getDepartmentAdminId() {
		return departmentAdminId;
	}

	public void setDepartmentAdminId(Long departmentAdminId) {
		this.departmentAdminId = departmentAdminId;
	}

	/**
	 * @param newPrimaryDepartmentId
	 *            the newPrimaryDepartmentId to set
	 */
	public void setNewPrimaryDepartmentId(Long newPrimaryDepartmentId) {
		this.newPrimaryDepartmentId = newPrimaryDepartmentId;
	}

}
