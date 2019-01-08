package com.zbjdl.boss.admin.user.action;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.enums.UserStatusEnum;
import com.zbjdl.boss.admin.user.enums.UserTypeEnum;


public class AddSysAdminAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 6275231906913861591L;

	private String loginName;

	private String userName;
	
	private String email;
	
	private String mobile;

	public String execute() {
		return SUCCESS;
	}

	public String addSysAdmin() throws Exception {

		// 获取登录的系管员信息
		// UserDTO superAdmin = UserInfoUtils.getUserFromSession(null);

		UserDTO userDTO = new UserDTO();
		userDTO.setUserName(userName);
		userDTO.setLoginName(loginName);
		userDTO.setPassword(loginName);
		userDTO.setEmail(email); // 新增邮件属性
		userDTO.setMobile(mobile); // 新增手机属性
		userDTO.setUserstatus(UserStatusEnum.ACTIVE);

		remoteService.getUserFacade().addUser(userDTO,
				UserTypeEnum.SYSTEM_MANAGER);

		return SUCCESS;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
