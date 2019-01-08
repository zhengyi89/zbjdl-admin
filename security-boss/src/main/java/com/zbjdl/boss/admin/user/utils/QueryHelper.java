/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.utils;

import java.util.ArrayList;
import java.util.List;

import com.zbjdl.boss.admin.frame.service.impl.RemoteServiceImpl;
import com.zbjdl.boss.admin.role.dto.RoleDTO;

/**
 * 
 * 类名称：QueryHelper <br>
 * 类描述： <br>
 * 
 * @author：feng
 * @since：2011-8-1 下午03:55:58
 * @version:
 * 
 */
public class QueryHelper {


	public boolean validateRole(Long userId, Long roleId) {
		List<RoleDTO> roleList = RemoteServiceImpl.getInstance().getSecurityConfigFacade()
				.queryRolesByUser(userId);
		if (roleList == null || roleList.size() == 0) {
			return false;
		}
		List<Long> roleIds = new ArrayList<Long>();
		for (RoleDTO role : roleList) {
			if (role == null) {
				continue;
			}
			roleIds.add(role.getRoleId());
		}
		return roleIds.contains(roleId);
	}



}
