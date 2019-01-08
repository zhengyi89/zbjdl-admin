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

/**
 * 
 * 类名称：UserInfoDetailAction <br>
 * 类描述： 用户详细信息<br>
 * 
 * @author：feng
 * @since：2011-7-26 下午02:00:54
 * @version:
 * 
 */
public class UserInfoDetailAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 6107059383647353047L;

	private Long userId;

	private UserDTO userDTO;

	private DepartmentDTO departmentDTO;

	@Override
	public String execute() throws Exception {
		userDTO = remoteService.getUserFacade().queryUserById(userId);
		departmentDTO = remoteService.getUserFacade().queryDepartmentById(
				userDTO.getPrimaryDepartmentId());
		return SUCCESS;
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
	 * userDTO
	 * 
	 * @return the userDTO
	 */

	public UserDTO getUserDTO() {
		return userDTO;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param userDTO
	 *            the userDTO to set
	 */
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	/**
	 * departmentDTO
	 * 
	 * @return the departmentDTO
	 */

	public DepartmentDTO getDepartmentDTO() {
		return departmentDTO;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param departmentDTO
	 *            the departmentDTO to set
	 */
	public void setDepartmentDTO(DepartmentDTO departmentDTO) {
		this.departmentDTO = departmentDTO;
	}

}
