/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.department.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zbjdl.boss.admin.facade.UserFacade;
import com.zbjdl.boss.admin.frame.utils.Constants;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.common.dubbo.proxy.SoaServiceRepository;

/**
 * @author：feng
 * @since：2012-6-28 上午10:38:19
 * @version:
 */
@Component
public class DepartmentManager {
	private final static Logger logger = LoggerFactory.getLogger(DepartmentManager.class);

	private UserFacade userFacade = SoaServiceRepository
			.getService(UserFacade.class,Constants.DUBBO_DEFAULT_GROUP,Constants.DUBBO_DEFAULT_VERSION);

	public List<DepartmentDTO> queryAllDepartment() {
		List<DepartmentDTO> departList = new ArrayList<DepartmentDTO>();
		try {
			departList = userFacade.queryAllDepartments();
		} catch (Exception e) {
			logger.error("", e);
		}
		if (departList == null) {
			departList = new ArrayList<DepartmentDTO>(0);
		}
		return departList;
	}
}
