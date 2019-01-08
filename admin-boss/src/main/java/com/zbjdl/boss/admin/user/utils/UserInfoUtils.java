/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.utils;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;

/**
 * 
 * @author zhengy
 * @date 2016年7月6日 下午3:28:37
 */
public class UserInfoUtils {
	
	/**
	 * 
	 * 描述：    从session中读取用户信息DTO
	 * @param session
	 * @return
	 */
	public static UserDTO getUserFromSession(HttpSession session) {
		if (session == null) {
			session = ServletActionContext.getRequest().getSession(true);
		}
		Object obj = session.getAttribute(DataDictDTO.SESSION_USERINFO);
		if (obj == null) {
			return null;
		}
		UserDTO userDto = (UserDTO) obj;
		return userDto;
	}
	
	/**
	 * 
	 * 描述：    向session中写入用户信息DTO
	 * @param session
	 * @return
	 */
	public static void setUserToSession(HttpSession session, UserDTO userDTO) {
		if (session == null) {
			session = ServletActionContext.getRequest().getSession(true);
		}
		if (userDTO == null) {
			return;
		}
		session.setAttribute(DataDictDTO.SESSION_USERINFO, userDTO);
	}

}
