/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.user.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.enums.DepartmentStatusEnum;
import com.zbjdl.boss.admin.user.enums.UserStatusEnum;
import com.zbjdl.boss.admin.user.enums.UserTypeEnum;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;

/**
 *
 * 类名称：AddDepartmentAdmin <br>
 * 类描述： 添加部门管理员<br>
 *
 * @author：feng
 * @since：2011-7-26 下午09:21:46
 * @version:
 *
 */
public class AddDepartmentAdminAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = -4518064655150330042L;

	private String loginName;

	private String userName;
	
	private String email;
	
	private String mobile;

	private Long primaryDepartmentId;// 添加管理员的主部门id

	private List<DepartmentDTO> departList;// 所有部门列表

	private String password;

	@Override
	public String execute() throws Exception {
		DepartmentDTO department = new DepartmentDTO();
		department.setDepartmentStatus(DepartmentStatusEnum.ACTIVE);
		departList = remoteService.getUserFacade().queryDepartments(
				department);
		return SUCCESS;
	}

	public String addDepartmentAdmin() {
		try {
			HttpSession session = getMVCFacade().getHttpSession();
			UserDTO user = UserInfoUtils.getUserFromSession(session);
			Boolean passwordRightOrNot = remoteService.getUserFacade()
					.userAuthentication(user.getUserId(), password);
			UserDTO userDTO = new UserDTO();
			userDTO.setUserName(userName);
			userDTO.setLoginName(loginName);
			userDTO.setPassword(loginName);
			userDTO.setIsAdmin(true);
			userDTO.setIsSuperAdmin(false);
			userDTO.setPrimaryDepartmentId(primaryDepartmentId);
			userDTO.setUserstatus(UserStatusEnum.ACTIVE);
			userDTO.setEmail(email); // 新增邮件属性
			userDTO.setMobile(mobile); // 新增手机属性
			if (!passwordRightOrNot) {
				this.errMsg = "密码错误!";
			} else {
				remoteService.getUserFacade().addUser(userDTO,
						UserTypeEnum.DEPARTMENT_MANAGER);
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
	 * @param departList
	 *            the departList to set
	 */
	public void setDepartList(List<DepartmentDTO> departList) {
		this.departList = departList;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**    
	 * email    
	 *    
	 * @return  the email   
	 */
	
	public String getEmail() {
		return email;
	}

	/**    
	 * @param email the email to set    
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**    
	 * mobile    
	 *    
	 * @return  the mobile   
	 */
	
	public String getMobile() {
		return mobile;
	}

	/**    
	 * @param mobile the mobile to set    
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	

}
