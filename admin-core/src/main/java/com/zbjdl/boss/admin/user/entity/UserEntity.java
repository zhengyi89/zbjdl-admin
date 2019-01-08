/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.entity;

import java.util.Date;
import java.util.List;

import com.zbjdl.boss.admin.user.enums.UserStatusEnum;
import com.zbjdl.common.utils.EqualUtils;
import com.zbjdl.common.persistence.Entity;

/**    
 * 类名称：UserEntity <br>
 * 类描述： 用户信息实体<br>
 * @author feng
 * @since 2011-6-17 下午03:02:58
 * @version 1.0
 */
public class UserEntity implements Entity<Long> {
	
	private static final long serialVersionUID = -67126404007125151L;

	private Long userId;//用户ID，主键
	
	private String loginName;//登录名
	
	private String userName;//用户名
	
	private String password;//密码
	
	private UserStatusEnum userStatus;//用户状态
	
	private String isSuperAdmin;//是否为超级管理员
	
	private String isAdmin;//是否有管理权限
	
	private Integer pwdErrorNums;//密码输入错误次数
	
	private Long primaryDepartmentId;//所属主部门ID
	
	private List<DepartmentEntity> departmentList;//所有的所属部门
	
	private Date createTime;//创建时间
	
	private String email;// 邮件
	private String mobile;// 手机
	
	private String migration;// 是否为二代迁移用户

	private String pwdShowNotice;

	private Date pwdLastModifiedTime;

	/**    
	 * 描述： 用户ID，主键
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
	 * 描述： 用户状态
	 * @param userStatus the userStatus to set    
	 */
	public void setUserStatus(UserStatusEnum userStatus) {
		this.userStatus = userStatus;
	}

	/**    
	 * 描述： 是否为超级管理员
	 * @param isSuperAdmin the isSuperAdmin to set    
	 */
	public void setIsSuperAdmin(String isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	/**    
	 * 描述： 是否有管理权限
	 * @param isAdmin the isAdmin to set    
	 */
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**    
	 * 描述：密码输入错误次数
	 * @param pwdErrorNums the pwdErrorNums to set    
	 */
	public void setPwdErrorNums(Integer pwdErrorNums) {
		this.pwdErrorNums = pwdErrorNums;
	}

	/**    
	 * 描述： 所属主部门ID
	 * @param primaryDepartment the primaryDepartment to set    
	 */
	public void setPrimaryDepartmentId(Long primaryDepartmentId) {
		this.primaryDepartmentId = primaryDepartmentId;
	}

	/**    
	 * 描述： 所有的所属部门
	 * @param departmentList the departmentList to set    
	 */
	public void setDepartmentList(List<DepartmentEntity> departmentList) {
		this.departmentList = departmentList;
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
	 * userStatus    
	 *    
	 * @return  the userStatus   
	 */
	
	public UserStatusEnum getUserStatus() {
		return userStatus;
	}

	/**    
	 * isSuperAdmin    
	 *    
	 * @return  the isSuperAdmin   
	 */
	
	public String getIsSuperAdmin() {
		return isSuperAdmin;
	}

	/**    
	 * isAdmin    
	 *    
	 * @return  the isAdmin   
	 */
	
	public String getIsAdmin() {
		return isAdmin;
	}

	/**    
	 * pwdErrorNums    
	 *    
	 * @return  the pwdErrorNums   
	 */
	
	public Integer getPwdErrorNums() {
		return pwdErrorNums;
	}

	/**    
	 * primaryDepartment    
	 *    
	 * @return  the primaryDepartment   
	 */
	
	public Long getPrimaryDepartmentId() {
		return primaryDepartmentId;
	}

	/**    
	 * departmentList    
	 *    
	 * @return  the departmentList   
	 */
	
	public List<DepartmentEntity> getDepartmentList() {
		return departmentList;
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

	/* (non-Javadoc)    
	 * @see com.zbjdl.common.persistence.Entity#getId()    
	 */
	@Override
	public Long getId() {
		return userId;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.common.persistence.Entity#setId(java.io.Serializable)    
	 */
	@Override
	public void setId(Long arg0) {
		this.userId = arg0;
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
	
	public String getMigration() {
		return migration;
	}

	/**    
	 * @param migration the migration to set    
	 */
	public void setMigration(String migration) {
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

	/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserEntity) {
			UserEntity entity = (UserEntity) obj;
			if (!EqualUtils.isEqual(entity.getIsAdmin(), this.getIsAdmin())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getIsSuperAdmin(), this.getIsSuperAdmin())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getLoginName(), this.getLoginName())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getPassword(), this.getPassword())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getPrimaryDepartmentId(), this.getPrimaryDepartmentId())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getPwdErrorNums(), this.getPwdErrorNums())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getUserId(), this.getUserId())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getUserStatus(), this.getUserStatus())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getUserName(), this.getUserName())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getCreateTime(), this.getCreateTime())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getMigration(), this.getMigration())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getPwdShowNotice(), this.getPwdShowNotice())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getPwdLastModifiedTime(), this.getPwdLastModifiedTime())) {
				return false;
			}
			return true;
		}
		return false;
	}

}
