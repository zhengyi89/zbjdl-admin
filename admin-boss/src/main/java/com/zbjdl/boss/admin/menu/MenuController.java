/**
 * 
 */
package com.zbjdl.boss.admin.menu;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zbjdl.boss.admin.basic.ViewLogicHelper;
import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.facade.PortalMenuFacade;
import com.zbjdl.boss.admin.frame.service.RemoteService;
import com.zbjdl.boss.admin.frame.service.impl.RemoteServiceImpl;
import com.zbjdl.boss.admin.frame.utils.Constants;
import com.zbjdl.boss.admin.function.dto.MenuModel;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.common.json.JSONUtils;
import com.zbjdl.common.utils.config.ConfigParam;
import com.zbjdl.common.utils.config.ConfigurationUtils;

/**
 * @author zhengy
 * @date 2016年7月6日 下午3:41:53
 */

@Controller
@RequestMapping("/menu")
public class MenuController {
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	@Autowired
	private PortalMenuFacade portalMenuFacade;

	private RemoteService remoteService = RemoteServiceImpl.getInstance();
	
	@Autowired
	private ViewLogicHelper viewLogicHelper;
	
	/**
	 * 显示主页面菜单
	 */
	@RequestMapping(value = "/showMenu")
	public ModelAndView showMenu(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("menu/showMenu");
		return mav;
	}
	
	@RequestMapping(value = "/customerPortal")
	public ModelAndView customerPortal(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("menu/customerPortal");
		UserDTO curUser = (UserDTO) request.getSession().getAttribute(DataDictDTO.SESSION_USERINFO);
		String portalName = remoteService.getUserFacade().queryDepartmentById(curUser.getPrimaryDepartmentId()).getDepartmentDesc();
		mav.addObject("portalName", portalName);
		MenuModel menuModel = portalMenuFacade.findDepartmentMemuTree(curUser.getPrimaryDepartmentId());
		String menuTree = viewLogicHelper.buildMenu(menuModel);
		mav.addObject("menuTree", menuTree);
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
		String topHeightJson = JSONUtils.toJsonString(topHeightConfig);
		mav.addObject("topHeightJson", topHeightJson);
		try {
			@SuppressWarnings("unchecked")
			ConfigParam<String> config = ConfigurationUtils.getAppConfigParam(Constants.ADMIN_BOSS_PORTAL_TAB);
			if (config != null && config.getValue() != null && "1".equals(config.getValue())) {
				String lastOpenedTab = JSONUtils.toJsonString(portalMenuFacade.getAllTab(curUser.getUserId()));
				mav.addObject("lastOpenedTab", lastOpenedTab);
			}
		} catch (Exception e) {
			// Do nothing
		}
		
		return mav;
	}
}
