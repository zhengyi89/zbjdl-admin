package com.zbjdl.boss.admin.frame.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.frame.utils.BossWebUtils;
import com.zbjdl.boss.admin.frame.utils.Constants;
import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.dto.MenuModel;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.common.utils.StringUtils;
import com.zbjdl.common.utils.config.ConfigurationUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author：feng
 * @since：2014年9月24日 下午5:40:26
 * @version:
 */
public class MenuBuilder {

	private static final Logger logger = LoggerFactory
			.getLogger(MenuBuilder.class);

	// 默认系统：运营后台
	private static final long DEFAULT_SYSTEM_ID = -1l;

	// 默认一级菜单：权限
	private static final long DEFAULT_FIRST_MENU_ID = -1001l;

	/**
	 * Map形式的菜单列表，方便存取
	 */
	private Map<Long, MenuModel> menuMap = new HashMap<Long, MenuModel>();

	private Long currentMenuId;

	private Long firstMenuId;

	private Long systemId;

	private String bossName;

	private List<String> frameBossList = new ArrayList<String>();

	private List<Long> frameSupportList = new ArrayList<Long>();

	private final static String SESSION_MENU_MAP = "menuMap";
	private final static String SESSION_BOSSNAME = "bossName";
	private final static String SESSION_PAGETYPE = "pageType";
	private final static String SESSION_FRAMESUPPORTLIST = "frameSupportList";

	public boolean build(HttpServletRequest request) throws Exception {
		// 判断是否为ajax请求
		boolean isAjax = BossWebUtils.isAjaxRequest(request);
		if (isAjax) {
			return true;
		}
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session
				.getAttribute(DataDictDTO.SESSION_USERINFO);

		// 菜单缓存到session中,若存在，直接返回true
		MenuModel menuTree = (MenuModel) session
				.getAttribute(DataDictDTO.SESSION_USER_MENU);
		if (menuTree == null) {
			menuTree = SecurityManager.getMenuTreeByUserId(request,
					user.getUserId());
			buildMenuMap(menuTree);
			session.setAttribute(SESSION_MENU_MAP, menuMap);
		}
		setCurrentMenu(request);
		setCurrentFirstMenu(request);
		setCurrentSystem(request);

		setFrameSupport(request, menuTree);

		// 兼容xxx-sitemesh包中的模板
		request.setAttribute(DataDictDTO.SESSION_USER_MENU, menuTree);
		request.setAttribute(SESSION_MENU_MAP, menuMap);
		request.setAttribute(SESSION_BOSSNAME,
				session.getAttribute(SESSION_BOSSNAME));
		request.setAttribute(SESSION_PAGETYPE,
				session.getAttribute(SESSION_PAGETYPE));
		request.setAttribute(SESSION_FRAMESUPPORTLIST,
				session.getAttribute(SESSION_FRAMESUPPORTLIST));

		return true;
	}

	/**
	 * 将树形menu变成Map形式
	 *
	 * @param menu
	 */
	private void buildMenuMap(MenuModel menu) {
		if (menu == null) {
			return;
		}

		if (menu.getMenuDTO() != null) {
			menuMap.put(menu.getMenuDTO().getMenuId(), menu);
			checkFrameSupport(menu.getMenuDTO());
		}

		List<MenuModel> subMenus = menu.getSubMenus();
		if (subMenus != null && !subMenus.isEmpty()) {
			for (MenuModel m : menu.getSubMenus()) {
				buildMenuMap(m);
			}
		}
	}

	/**
	 * Title：在session中放置当前的菜单ID
	 */
	private void setCurrentMenu(HttpServletRequest request) {
		HttpSession session = request.getSession();
		currentMenuId = getMenuId(request, MenuType.END_MENU);
		String reqMenuId = request
				.getParameter(ParamNameModel.END_MENU.paramName);
		String reqFirstMenuId = request
				.getParameter(ParamNameModel.FIRST_MENU.paramName);
		// 切换一级菜单的情况
		if (StringUtils.isBlank(reqMenuId)
				&& StringUtils.isNotBlank(reqFirstMenuId)) {
			String sessionFirstMenuId = String.valueOf(request.getSession()
					.getAttribute(ParamNameModel.FIRST_MENU.sessionIdKey));
			if (!StringUtils.equals(reqFirstMenuId, sessionFirstMenuId)) {
				currentMenuId = null;
				cleanMenuFromSession(request, MenuType.END_MENU);
			}
		}
		if (currentMenuId != null) {
			setMenuToSession(request, MenuType.END_MENU, currentMenuId);
			MenuModel menu = this.menuMap.get(currentMenuId);
			if (menu != null) {
				bossName = getBossName(menu.getFunctionUrl());
				session.setAttribute(SESSION_BOSSNAME, bossName);
			}
		}
	}

	/**
	 * Title：在session中放置当前的一级菜单ID
	 */
	private void setCurrentFirstMenu(HttpServletRequest request) {
		HttpSession session = request.getSession();
		firstMenuId = getMenuId(request, MenuType.FIRST_MENU);

		// 如果只有叶子节点菜单，也能找到对应的一级菜单
		if (firstMenuId == null && currentMenuId != null) {
			MenuModel currentMenu = menuMap.get(currentMenuId);
			firstMenuId = getMenuByEndMenuAndLevel(currentMenu, 2);
		}
		if (firstMenuId == null || firstMenuId == 0) {
			firstMenuId = DEFAULT_FIRST_MENU_ID;
		}
		setMenuToSession(request, MenuType.FIRST_MENU, firstMenuId);
		boolean isFrame = this.isFrameSupport(firstMenuId);
		session.setAttribute(SESSION_PAGETYPE, isFrame ? "frame" : "");
	}

	/**
	 * Title：在session中放置当前systemId
	 */
	private void setCurrentSystem(HttpServletRequest request) {
		systemId = getMenuId(request, MenuType.SYSTEM);

		// 如果只有叶子节点菜单，也能找到对应的系统菜单
		if (systemId == null && currentMenuId != null) {
			MenuModel currentMenu = menuMap.get(currentMenuId);
			systemId = getMenuByEndMenuAndLevel(currentMenu, 1);
		}
		if (systemId == null || systemId == 0) {
			systemId = DEFAULT_SYSTEM_ID;
		}
		setMenuToSession(request, MenuType.SYSTEM, systemId);
	}

	/**
	 * 根据叶子节点菜单找到一级菜单
	 *
	 * @param currentMenu
	 * @return
	 */
	private Long getMenuByEndMenuAndLevel(MenuModel currentMenu, int level) {
		if (currentMenu == null || currentMenu.getMenuDTO() == null) {
			return null;
		}
		if (currentMenu.getMenuDTO().getLevelNum() == level) {
			return currentMenu.getMenuDTO().getParentId();
		}
		return getMenuByEndMenuAndLevel(
				menuMap.get(currentMenu.getMenuDTO().getParentId()), level);
	}

	/**
	 * 取得一个叶子节点菜单的url
	 *
	 * @param menu
	 * @return
	 */
	private String getOneEndMenuUrl(MenuModel menu) {
		if (menu == null || menu.getMenuDTO() == null) {
			return null;
		}
		if (StringUtils.isNotBlank(menu.getFunctionUrl())) {
			return menu.getFunctionUrl();
		}
		for (MenuModel m : menu.getSubMenus()) {
			String url = getOneEndMenuUrl(m);
			if (StringUtils.isNotBlank(url)) {
				return url;
			}
		}
		return null;
	}

	/**
	 * 回写菜单到session
	 *
	 * @param menuType
	 */
	private ParamNameModel getParamNameModel(MenuType menuType) {
		switch (menuType) {
		case SYSTEM:
			return ParamNameModel.SYSTEM_MENU;
		case FIRST_MENU:
			return ParamNameModel.FIRST_MENU;
		default:
			return ParamNameModel.END_MENU;
		}
	}

	/**
	 * 回写菜单到session
	 *
	 * @param request
	 */
	private Long getMenuId(HttpServletRequest request, MenuType menuType) {
		ParamNameModel param = getParamNameModel(menuType);
		HttpSession session = request.getSession();
		String menuIdStr = request.getParameter(param.paramName);
		// 如果当前菜单ID为空，默认为上次所选的菜单ID
		if (StringUtils.isBlank(menuIdStr)) {
			Object sessionMenuId = session.getAttribute(param.sessionIdKey);
			if (sessionMenuId != null) {
				menuIdStr = String.valueOf(sessionMenuId);
			}
		}
		menuIdStr = StringUtils.trim(menuIdStr);
		if (isNumber(menuIdStr)) {
			return Long.valueOf(menuIdStr);
		} else {
			return null;
		}
	}

	/**
	 * 设置菜单到session
	 *
	 * @param request
	 * @param menuType
	 * @param menuId
	 */
	private void setMenuToSession(HttpServletRequest request,
			MenuType menuType, Long menuId) {
		if (menuId != null) {
			ParamNameModel param = getParamNameModel(menuType);
			HttpSession session = request.getSession();
			session.setAttribute(param.sessionIdKey, menuId);
			if (menuMap.get(menuId) != null) {
				session.setAttribute(param.sessionObjKey, menuMap.get(menuId));
			}
		}
	}

	/**
	 * 从session清除菜单
	 *
	 * @param request
	 * @param menuType
	 */
	private void cleanMenuFromSession(HttpServletRequest request,
			MenuType menuType) {
		ParamNameModel param = getParamNameModel(menuType);
		HttpSession session = request.getSession();
		session.removeAttribute(param.sessionIdKey);
		session.removeAttribute(param.sessionObjKey);
	}

	/**
	 * 兼容性处理，保证未用sitemesh装饰的后台能使用frame
	 *
	 * @param request
	 * @param menuTree
	 */
	private void setFrameSupport(HttpServletRequest request, MenuModel menuTree) {
		HttpSession session = request.getSession();
		session.setAttribute(SESSION_FRAMESUPPORTLIST, frameSupportList);
	}

	@SuppressWarnings("unchecked")
	private void checkFrameSupport(MenuDTO menu) {
		try {
			if (menu == null || StringUtils.isBlank(menu.getFunctionUrl())) {
				return;
			}
			if (frameBossList == null) {
				frameBossList = (List<String>) ConfigurationUtils
						.getAppConfigParam(Constants.ADMIN_BOSS_WITH_FRAME)
						.getValue();
			}
			if (frameBossList != null && !frameBossList.isEmpty()) {
				for (String boss : frameBossList) {
					if (StringUtils.contains(menu.getFunctionUrl(), boss)) {
						this.frameSupportList.add(menu.getMenuId());
					}
				}
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	/**
	 * 根据某一级别的所有菜单
	 *
	 * @param root
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<MenuModel> getMenuByLevel(MenuModel root, int level) {
		if (root == null) {
			return null;
		}
		if (root.getMenuDTO() != null
				&& root.getMenuDTO().getLevelNum() == level - 1) {
			return root.getSubMenus();
		}
		if (root.getSubMenus() != null
				&& (root.getMenuDTO() == null || root.getMenuDTO()
						.getLevelNum() < level)) {
			List<MenuModel> menus = new ArrayList<MenuModel>();
			for (MenuModel menu : root.getSubMenus()) {
				List<MenuModel> subMenus = getMenuByLevel(menu, level);
				if (subMenus != null && !subMenus.isEmpty()) {
					menus.addAll(subMenus);
				}
			}
			return menus;
		}
		return null;
	}

	private boolean isFrameSupport(long firstMenuId) {
		boolean isFrame = false;
		try {
			if (frameBossList != null && !frameBossList.isEmpty()) {
				// 取得根节点菜单项
				MenuModel menu = menuMap.get(firstMenuId);
				// 取得一个叶子节点菜单项url
				String url = getOneEndMenuUrl(menu);
				if (StringUtils.isNotBlank(url)) {
					for (String boss : frameBossList) {
						if (StringUtils.contains(url, boss)) {
							isFrame = true;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.info("get boss list use frame exception : " + e.getMessage());
		}
		return isFrame;
	}

	/**
	 * 检查是否为数值
	 *
	 * @param menuId
	 * @return
	 */
	private boolean isNumber(String menuId) {
		return StringUtils.isNotBlank(menuId) && menuId.matches("[-]?[\\d]+");
	}

	/**
	 * 从functionUrl中取得boss名称
	 *
	 * @param url
	 * @return
	 */
	private String getBossName(String url) {
		if (StringUtils.isNotBlank(url)) {
			String rule = "^.+/([a-zA-Z]+-[a-zA-Z]+).*$";
			String pattern = "$1";
			try {
				Pattern prule = Pattern.compile(rule);
				Matcher matcher = prule.matcher(url);
				return matcher.replaceAll(pattern);
			} catch (Exception e) {
				logger.error("{}.", e);
			}
		}
		return null;
	}

	private enum MenuType {
		/**
		 * 所属系统
		 */
		SYSTEM,

		/**
		 * 一级菜单
		 */
		FIRST_MENU,

		/**
		 * 叶子节点菜单
		 */
		END_MENU
	}

	/**
	 * 参数模型
	 *
	 * @author feng
	 * @version 1.0.0
	 * @since 2012-4-16 上午11:18:38
	 */
	private static class ParamNameModel {
		// request取得菜单ID的变量名
		public String paramName;
		// session中存储菜单ID的变量名
		public String sessionIdKey;
		// session中存储菜单对象的变量名
		public String sessionObjKey;

		public static ParamNameModel FIRST_MENU = new ParamNameModel(
				"_firstMenuId", "firstMenuId", "firstMenu");
		public static ParamNameModel SYSTEM_MENU = new ParamNameModel(
				"_systemId", "currentSystemId", "currentSystem");
		public static ParamNameModel END_MENU = new ParamNameModel("_menuId",
				"currentMenuId", "currentMenu");

		public ParamNameModel(String paramName, String sessionIdKey,
				String sessionObjKey) {
			this.paramName = paramName;
			this.sessionIdKey = sessionIdKey;
			this.sessionObjKey = sessionObjKey;
		}
	}
}
