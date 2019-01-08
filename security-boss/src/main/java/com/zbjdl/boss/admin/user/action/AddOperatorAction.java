/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.google.common.collect.Lists;
import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.enums.UserStatusEnum;
import com.zbjdl.boss.admin.user.enums.UserTypeEnum;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;

/**
 * 
 * 类名称：AddOperatorAction <br>
 * 类描述： 添加操作员 <br>
 * 
 * @author：feng
 * @since：2011-7-27 下午06:24:59
 * @version:
 * 
 */
public class AddOperatorAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 5404306863839542897L;

	private String loginName;

	private String userName;

	private String adminPassword;// 管理员密码

	private String password;

	private String email;

	private String mobile;

	private Long primaryDepartmentId;

	private Long userId;

	private List<DepartmentDTO> departList = Lists.newArrayList();

	@Override
	public String execute() throws Exception {
		departList = remoteService.getUserFacade().queryAllDepartments();
		return SUCCESS;
	}

	public String addOperator() {
		try {
			HttpSession session = getMVCFacade().getHttpSession();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(), password);
			UserDTO loginUser = UserInfoUtils.getUserFromSession(null);
			UserDTO userDTO = new UserDTO();
			userDTO.setUserName(userName);
			userDTO.setLoginName(loginName);
			userDTO.setPassword(loginName);
			if (loginUser.getIsSuperAdmin() != null
					&& loginUser.getIsSuperAdmin()) {
				userDTO.setPrimaryDepartmentId(primaryDepartmentId);
			} else {
				userDTO.setPrimaryDepartmentId(loginUser
						.getPrimaryDepartmentId());
			}
			// userDTO.setIsAdmin(false);
			// userDTO.setIsSuperAdmin(false);
			userDTO.setEmail(email); // 新增邮件属性
			userDTO.setMobile(mobile); // 新增手机属性
			userDTO.setUserstatus(UserStatusEnum.ACTIVE);
			if (!passwordRightOrNot) {
				this.errMsg = "密码错误!";
			} else {
				remoteService.getUserFacade().addUser(
						userDTO, UserTypeEnum.OPERATOR);
			}
		} catch (Exception e) {
			handleException(e);
		}
		return SUCCESS;
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
	 * adminPassword
	 * 
	 * @return the adminPassword
	 */

	public String getAdminPassword() {
		return adminPassword;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param adminPassword
	 *            the adminPassword to set
	 */
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * email
	 * 
	 * @return the email
	 */

	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * mobile
	 * 
	 * @return the mobile
	 */

	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getPrimaryDepartmentId() {
		return primaryDepartmentId;
	}

	public void setPrimaryDepartmentId(Long primaryDepartmentId) {
		this.primaryDepartmentId = primaryDepartmentId;
	}

	public List<DepartmentDTO> getDepartList() {
		return departList;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
