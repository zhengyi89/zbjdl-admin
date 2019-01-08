/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.dto.sso;

import java.util.Date;

/** 
 * 用户统一的登录信息
 *    
 */
public class SSOLoginInfoDTO implements java.io.Serializable{
	private static final long serialVersionUID = -4669293147140816348L;
	/**
	 * 用户ID
	 */
	private String userid;
	/**
	 * 登录时间
	 */
	private Date loginTime;
	/**
	 * 上次访问时间
	 */
	private Date lastAccessTime;
	
	/**
	 * 登录ID
	 */
	private String loginid;
	/**
	 * 登录Ip
	 */
	private String loginIp;
	/**    
	 * userid    
	 *    
	 * @return  the userid   
	 */
	
	public String getUserid() {
		return userid;
	}
	/**    
	 * @param userid the userid to set    
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**    
	 * loginTime    
	 *    
	 * @return  the loginTime   
	 */
	
	public Date getLoginTime() {
		return loginTime;
	}
	/**    
	 * @param loginTime the loginTime to set    
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	/**    
	 * lastAccessTime    
	 *    
	 * @return  the lastAccessTime   
	 */
	
	public Date getLastAccessTime() {
		return lastAccessTime;
	}
	/**    
	 * @param lastAccessTime the lastAccessTime to set    
	 */
	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	
	/**    
	 * loginid    
	 *    
	 * @return  the loginid   
	 */
	
	public String getLoginid() {
		return loginid;
	}
	/**    
	 * @param loginid the loginid to set    
	 */
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	/**    
	 * loginIp    
	 *    
	 * @return  the loginIp   
	 */
	
	public String getLoginIp() {
		return loginIp;
	}
	/**    
	 * @param loginIp the loginIp to set    
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	
}
