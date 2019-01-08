/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.menu.action;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;

/**
 * 类名称：ShowMenuAction <br>
 * 类描述： 用户菜单展示 <br>
 * 
 * @author feng
 * @since 2011-7-18 下午03:27:57
 * @version 1.0.0
 */
public class ShowMenuAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 1150128812214657010L;

	private Long departmentId;

	/**
	 * 当前登录用户的角色列表
	 */
	private String roleIds;

	public String execute() {
		/* 2013-07-10 停用升级公告功能，注释掉参数准备相关逻辑
		UserDTO userDTO = this.getCurrentUser();
		if (userDTO != null) {
			departmentId = userDTO.getPrimaryDepartmentId();
			if (departmentId != null) {
				List<RoleDTO> roleList = remoteService
						.getSecurityConfigFacade().queryRolesByUser(
								userDTO.getUserId());
				StringBuilder sb = new StringBuilder();
				for (RoleDTO role : roleList) {
					sb.append(role.getRoleId()).append(",");
				}
				if (sb.length() > 0) {
					sb.deleteCharAt(sb.length() - 1);
					roleIds = sb.toString();
				}
			}
		}
		*/
		return SUCCESS;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getRoleIds() {
		return roleIds;
	}
}