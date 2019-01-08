/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.menu.action;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.facade.PortalMenuFacade;
import com.zbjdl.boss.admin.frame.utils.Constants;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;
import com.zbjdl.common.dubbo.proxy.SoaServiceRepository;
import com.zbjdl.common.json.JSONUtils;
import com.zbjdl.common.utils.config.ConfigParam;
import com.zbjdl.common.utils.config.ConfigurationUtils;

import java.util.Arrays;
import java.util.Map;

/**
 * <p>Title: 结算部门更新最后访问过的URL缓存</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2018</p>
 * <p>Company: 八戒财云</p>
 *
 * @author feng
 * @version 0.1, 13-10-24 上午9:37
 */
public class PortalTabAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 344399200781184010L;

	private String lastOpenedTab;

	private String title;

	private String url;

	private PortalMenuFacade portalMenuFacade = SoaServiceRepository.getService(PortalMenuFacade.class,Constants.DUBBO_DEFAULT_GROUP,Constants.DUBBO_DEFAULT_VERSION);

	public String get() {
		UserDTO loginUser = UserInfoUtils.getUserFromSession(null);
		boolean isOpen = false;
		try {
			@SuppressWarnings("unchecked")
			ConfigParam<String> config = ConfigurationUtils.getAppConfigParam(Constants.ADMIN_BOSS_PORTAL_TAB);
			if (config != null && config.getValue() != null) {
				isOpen = "1".equals(config.getValue());
			}
		}
		catch (Exception e) {
			// Do nothing
		}
		if (isOpen && null != loginUser && loginUser.getPrimaryDepartmentId() == -4) {
			lastOpenedTab = JSONUtils.toJsonString(portalMenuFacade.getAllTab(loginUser.getUserId()).keySet());
		}
		return SUCCESS;
	}

	public String active() {
		UserDTO loginUser = UserInfoUtils.getUserFromSession(null);
		if (null != loginUser && loginUser.getPrimaryDepartmentId() == -4 && null != title && !"".equals(title) && null != url && !"".equals(url)) {
			portalMenuFacade.addTab(loginUser.getUserId(), title, url);
		}
		return SUCCESS;
	}

	public String delete() {
		UserDTO loginUser = UserInfoUtils.getUserFromSession(null);
		if (null != loginUser && loginUser.getPrimaryDepartmentId() == -4 && null != title && !"".equals(title)) {
			portalMenuFacade.deleteTab(loginUser.getUserId(), title);
		}
		return SUCCESS;
	}

	public String clean() {
		UserDTO loginUser = UserInfoUtils.getUserFromSession(null);
		if (null != loginUser && loginUser.getPrimaryDepartmentId() == -4) {
			portalMenuFacade.cleanTab(loginUser.getUserId());
		}
		else if (null != title && !"".equals(title)) {
			portalMenuFacade.cleanAllTab(Arrays.asList(title.split(",")));
		}
		return SUCCESS;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLastOpenedTab() {
		return lastOpenedTab;
	}

}
