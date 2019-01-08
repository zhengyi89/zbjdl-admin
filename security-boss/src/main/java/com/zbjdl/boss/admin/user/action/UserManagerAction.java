/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *
 */
package com.zbjdl.boss.admin.user.action;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.enums.UserTypeEnum;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;
import com.zbjdl.common.exception.BaseException;
import com.zbjdl.common.utils.StringUtils;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：UserManagerAction <br>
 * 类描述： 用户管理Action <br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-7-21 上午10:47:12
 */
public class UserManagerAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 6605192850749890236L;

	private List<DepartmentDTO> departList = new ArrayList<DepartmentDTO>();// 所有部门列表

	private String departmentName;

	private String userStatus;

	private String loginName;

	private String isAdmin;

	private Long userId;

	private String userName;

	private String departmentCode;

	private String passwordIsRight;

	private UserDTO userDto;

	public String queryUser() {

		UserDTO userDTO = UserInfoUtils.getUserFromSession(null);

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("userId", userDTO.getUserId());

		List<DepartmentDTO> list = remoteService.getUserFacade()
				.queryAllDepartments();
		DepartmentDTO allDepart = new DepartmentDTO();
		allDepart.setDepartmentName("全部");
		allDepart.setDepartmentCode("");
		departList.add(allDepart);
		departList.addAll(list);
		return SUCCESS;
	}

	// 设为管理员
	public String setAdmin() {

		HttpSession session = getMVCFacade().getHttpSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		UserDTO user = UserInfoUtils.getUserFromSession(session);
		Boolean passwordRightOrNot = remoteService.getUserFacade()
				.userAuthentication(user.getUserId(),
						request.getParameter("password"));
		if (!passwordRightOrNot) {
			throw new BaseException("密码错误!");
		}

		// 获取登录的系管员信息
		remoteService.getSecurityConfigFacade().changeUserType(userId,
				UserTypeEnum.SYSTEM_MANAGER);
		return SUCCESS;
	}

	// 取消管理员
	public String cancelAdmin() {

		HttpSession session = getMVCFacade().getHttpSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		UserDTO user = UserInfoUtils.getUserFromSession(session);
		Boolean passwordRightOrNot = remoteService.getUserFacade()
				.userAuthentication(user.getUserId(),
						request.getParameter("password"));
		if (!passwordRightOrNot) {
			throw new BaseException("密码错误!");
		}

		remoteService.getSecurityConfigFacade().changeUserType(userId,
				UserTypeEnum.DEPARTMENT_MANAGER);
		return SUCCESS;
	}

	// 激活用户
	public String activeUser() {
		try {
			Boolean isDepartmentAdmin = remoteService.getSecurityFacade()
					.checkUserType(userId, UserTypeEnum.DEPARTMENT_MANAGER);
			UserDTO userDTO = UserInfoUtils.getUserFromSession(null);

			HttpSession session = getMVCFacade().getHttpSession();
			HttpServletRequest request = ServletActionContext.getRequest();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							request.getParameter("password"));
			if (!passwordRightOrNot) {
				this.errMsg = "密码错误!";
			} else if (!isDepartmentAdmin) {
				this.errMsg = "激活用户不是部门管理员!";
			} else {
				remoteService.getUserFacade().unFrozeUser(userId, "",
						userDTO.getUserId());
			}
		} catch (Exception e) {
			handleException(e);
		}
		return SUCCESS;
	}

	// 冻结用户
	public String frozenUser() {
		try {
			Boolean isDepartmentAdmin = remoteService.getSecurityFacade()
					.checkUserType(userId, UserTypeEnum.DEPARTMENT_MANAGER);
			UserDTO userDTO = UserInfoUtils.getUserFromSession(null);
			// remoteService.getSecurityConfigFacade().
			HttpSession session = getMVCFacade().getHttpSession();
			HttpServletRequest request = ServletActionContext.getRequest();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							request.getParameter("password"));
			if (!passwordRightOrNot) {
				this.errMsg = "密码错误!";
			} else if (!isDepartmentAdmin) {
				this.errMsg = "冻结用户不是部门管理员!";
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
	public String deleteUser() {
		try {
			HttpSession session = getMVCFacade().getHttpSession();
			HttpServletRequest request = ServletActionContext.getRequest();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(),
							request.getParameter("password"));
			Boolean isDepartmentAdmin = remoteService.getSecurityFacade()
					.checkUserType(userId, UserTypeEnum.DEPARTMENT_MANAGER);
			if (!passwordRightOrNot) {
				this.errMsg = "密码错误!";
			} else if (!isDepartmentAdmin) {
				/* throw UserException.USER_ISNOTADMIN_EXCEPTION; */
				this.errMsg = "删除用户不是部门管理员!";
			} else {
				remoteService.getUserFacade().deleteUser(userId);
			}
		} catch (Exception e) {
			handleException(e);
		}
		return SUCCESS;
	}

	/**
	 * 进入个人信息修改页面
	 *
	 * @return
	 */
	public String showUserInfoModify() {
		HttpSession session = getMVCFacade().getHttpSession();
		userDto = UserInfoUtils.getUserFromSession(session);
		return SUCCESS;
	}

	/**
	 * 修改个人信息
	 *
	 * @return
	 */
	public String modifyUserInfo() {
		if (checkModifyInfoParam()) {
			HttpSession session = getMVCFacade().getHttpSession();
			UserDTO user = remoteService.getUserFacade().queryUserById(
					UserInfoUtils.getUserFromSession(session).getUserId());

			if ("2".equals(userDto.getPwdShowNotice())) {
				user.setPwdShowNotice("0");
			} else {
				user.setUserName(userDto.getUserName());
				user.setEmail(userDto.getEmail());
				user.setMobile(userDto.getMobile());

				// 提示定期修改密码
				if (null == userDto.getPwdShowNotice()) {
					user.setPwdShowNotice("0");
				} else {
					user.setPwdShowNotice(userDto.getPwdShowNotice());
				}
			}
			try {
				remoteService.getUserFacade().updateUserInfo(user);
				UserInfoUtils.setUserToSession(session, user); // 更新session信息状态
			} catch (Exception e) {
				handleException(e);
			}
		}
		return SUCCESS;
	}

	/**
	 * 检查修改信息
	 *
	 * @return
	 */
	private boolean checkModifyInfoParam() {
		if (null == userDto) {
			this.errMsg = "修改信息不能为空!";
			return false;
		}
		if ("2".equals(userDto.getPwdShowNotice())) {
			return true;
		}
		if (StringUtils.isBlank(userDto.getUserName())) {
			this.errMsg = "用户姓名不能为空!";
			return false;
		} else if (StringUtils.isBlank(userDto.getEmail())) {
			this.errMsg = "邮箱不能为空!";
			return false;
		}
		return true;
	}

	/**
	 * departList
	 *
	 * @return the departList
	 */

	public List<DepartmentDTO> getDepartList() {
		return departList;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param departList the departList to set
	 */
	public void setDepartList(List<DepartmentDTO> departList) {
		this.departList = departList;
	}

	/**
	 * departmentName
	 *
	 * @return the departmentName
	 */

	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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
	 * @param userStatus the userStatus to set
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
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * isAdmin
	 *
	 * @return the isAdmin
	 */

	public String getIsAdmin() {
		return isAdmin;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param isAdmin the isAdmin to set
	 */
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
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
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
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
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * departmentCode
	 *
	 * @return the departmentCode
	 */

	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param departmentCode the departmentCode to set
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getPasswordIsRight() {
		return passwordIsRight;
	}

	public void setPasswordIsRight(String passwordIsRight) {
		this.passwordIsRight = passwordIsRight;
	}

	/**
	 * userDto
	 *
	 * @return the userDto
	 */

	public UserDTO getUserDto() {
		return userDto;
	}

	/**
	 * @param userDto the userDto to set
	 */
	public void setUserDto(UserDTO userDto) {
		this.userDto = userDto;
	}

}
