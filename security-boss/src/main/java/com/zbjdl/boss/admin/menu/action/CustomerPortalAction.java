package com.zbjdl.boss.admin.menu.action;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.facade.PortalMenuFacade;
import com.zbjdl.boss.admin.frame.utils.Constants;
import com.zbjdl.boss.admin.function.dto.MenuModel;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.common.dubbo.proxy.SoaServiceRepository;
import com.zbjdl.common.json.JSONUtils;
import com.zbjdl.common.utils.config.ConfigParam;
import com.zbjdl.common.utils.config.ConfigurationUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author feng
 * @version 1.0.0
 * @since 2013-3-20 下午4:38:02
 */
public class CustomerPortalAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 344399200781184000L;

	private String employeePath;

	private String resourcePath;

	private UserDTO curUser;

	private String menuTree;

	private String portalName;

	private String topHeightJson;

	private String lastOpenedTab;

	private PortalMenuFacade portalMenuFacade = SoaServiceRepository.getService(PortalMenuFacade.class,Constants.DUBBO_DEFAULT_GROUP,Constants.DUBBO_DEFAULT_VERSION);

	public String execute() {
		employeePath = (String) this.getMVCFacade().getRequestAttribute(
				"employeePath");
		resourcePath = (String) this.getMVCFacade().getRequestAttribute(
				"resourcePath");
		curUser = super.getCurrentUser();
		portalName = remoteService.getUserFacade().queryDepartmentById(curUser.getPrimaryDepartmentId()).getDepartmentDesc();
		MenuModel menuModel = portalMenuFacade.findDepartmentMemuTree(curUser.getPrimaryDepartmentId());
		menuTree = viewLogicHelper.buildMenu(menuModel);
		Map<String, String> topHeightConfig = new HashMap<String, String>(0);
		try {
			@SuppressWarnings("unchecked")
			ConfigParam<Map<String, String>> config = ConfigurationUtils
					.getAppConfigParam(Constants.ADMIN_BOSS_PORTAL_TOPHEIGHT);
			if (config != null && config.getValue() != null) {
				topHeightConfig = config.getValue();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		topHeightJson = JSONUtils.toJsonString(topHeightConfig);

		try {
			@SuppressWarnings("unchecked")
			ConfigParam<String> config = ConfigurationUtils.getAppConfigParam(Constants.ADMIN_BOSS_PORTAL_TAB);
			if (config != null && config.getValue() != null && "1".equals(config.getValue())) {
				lastOpenedTab = JSONUtils.toJsonString(portalMenuFacade.getAllTab(curUser.getUserId()));
			}
		} catch (Exception e) {
			// Do nothing
		}
		return SUCCESS;
	}

	public String getEmployeePath() {
		return employeePath;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public UserDTO getCurUser() {
		return curUser;
	}

	public String getMenuTree() {
		return menuTree;
	}

	public String getPortalName() {
		return portalName;
	}

	public String getTopHeightJson() {
		return topHeightJson;
	}

	public String getLastOpenedTab() {
		return lastOpenedTab;
	}

}
