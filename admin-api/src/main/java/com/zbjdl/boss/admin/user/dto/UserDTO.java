/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.dto;

import java.util.Date;

import com.zbjdl.boss.admin.dto.basic.BaseDTO;
import com.zbjdl.boss.admin.user.enums.UserStatusEnum;

/**    
 *    
 * 类名称：UserDTO <br>    
 * 类描述： 用户DTO，用于存放用户信息<br>
 * @author：feng    
 * @since：2011-6-21 上午09:44:23 
 * @version: 1.0
 *     
 */
public class UserDTO extends BaseDTO {
	
	private static final long serialVersionUID = 2169270938402114659L;
	
	private Long userId;//用户ID

	private String loginName;//登录名
	
	private String userName;//姓名
	
	private String password;//密码
	
	private Boolean isSuperAdmin;//是否为超级管理员
	
	private Boolean isAdmin;//是否有管理权限
	
	private Long primaryDepartmentId;//所属主部门ID
	
	private UserStatusEnum userstatus;//用户状态
	
	private Date createTime;//创建时间
	
	private String email;// 邮件
	private String mobile;// 手机
	private boolean migration;// 是否为二代迁移过来的用户

	private String pwdShowNotice;

	private Date pwdLastModifiedTime;

	/**    
	 * 描述： 用户ID
	 * @param userId the userId to set    
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**    
	 * 描述： 登录名
	 * @param loginName the loginName to set    
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**    
	 * 描述： 密码
	 * @param password the password to set    
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**    
	 * 描述： 是否为超级管理员
	 * @param isSuperAdmin the isSuperAdmin to set    
	 */
	public void setIsSuperAdmin(Boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	/**    
	 * 描述： 是否有管理权限
	 * @param isAdmin the isAdmin to set    
	 */
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**    
	 * userId    
	 *    
	 * @return  the userId   
	 */
	
	public Long getUserId() {
		return userId;
	}

	/**    
	 * loginName    
	 *    
	 * @return  the loginName   
	 */
	
	public String getLoginName() {
		return loginName;
	}

	/**    
	 * password    
	 *    
	 * @return  the password   
	 */
	
	public String getPassword() {
		return password;
	}

	/**    
	 * isSuperAdmin    
	 *    
	 * @return  the isSuperAdmin   
	 */
	
	public Boolean getIsSuperAdmin() {
		return isSuperAdmin;
	}

	/**    
	 * isAdmin    
	 *    
	 * @return  the isAdmin   
	 */
	
	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public Long getPrimaryDepartmentId() {
		return primaryDepartmentId;
	}

	/**
	 * 
	 * 描述：    用户主部门ID
	 * @param primaryDepartmentId
	 */
	public void setPrimaryDepartmentId(Long primaryDepartmentId) {
		this.primaryDepartmentId = primaryDepartmentId;
	}

	/**    
	 * userName    
	 *    
	 * @return  the userName   
	 */
	
	public String getUserName() {
		return userName;
	}

	/**    
	 * 描述： 在这里描述属性的含义
	 * @param userName the userName to set    
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**    
	 * userstatus    
	 *    
	 * @return  the userstatus   
	 */
	
	public UserStatusEnum getUserstatus() {
		return userstatus;
	}

	/**    
	 * 描述： 在这里描述属性的含义
	 * @param userstatus the userstatus to set    
	 */
	public void setUserstatus(UserStatusEnum userstatus) {
		this.userstatus = userstatus;
	}

	/**    
	 * createTime    
	 *    
	 * @return  the createTime   
	 */
	
	public Date getCreateTime() {
		return createTime;
	}

	/**    
	 * 描述： 在这里描述属性的含义
	 * @param createTime the createTime to set    
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	/**    
	 * migration    
	 *    
	 * @return  the migration   
	 */
	
	public boolean isMigration() {
		return migration;
	}

	/**    
	 * @param migration the migration to set    
	 */
	public void setMigration(boolean migration) {
		this.migration = migration;
	}

	public String getPwdShowNotice() {
		return pwdShowNotice;
	}

	public void setPwdShowNotice(String pwdShowNotice) {
		this.pwdShowNotice = pwdShowNotice;
	}

	public Date getPwdLastModifiedTime() {
		return pwdLastModifiedTime;
	}

	public void setPwdLastModifiedTime(Date pwdLastModifiedTime) {
		this.pwdLastModifiedTime = pwdLastModifiedTime;
	}
}
