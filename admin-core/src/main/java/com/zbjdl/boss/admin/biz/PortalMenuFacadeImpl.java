package com.zbjdl.boss.admin.biz;

import com.google.common.collect.Maps;
import com.zbjdl.boss.admin.LRUCache;
import com.zbjdl.boss.admin.facade.PortalMenuFacade;
import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.dto.MenuKVDTO;
import com.zbjdl.boss.admin.function.dto.MenuModel;
import com.zbjdl.boss.admin.function.service.PortalMenuService;
import com.zbjdl.boss.admin.utils.sso.ConfigHelper;
import com.zbjdl.common.utils.CheckUtils;
import com.zbjdl.common.utils.StringUtils;
import com.zbjdl.common.utils.cache.remote.RemoteCacheUtils;
import com.zbjdl.common.utils.config.ConfigParam;
import com.zbjdl.common.utils.config.ConfigurationUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author feng
 * @version 1.0.0
 * @since 2013-5-30 下午03:05:31
 */
public class PortalMenuFacadeImpl implements PortalMenuFacade {

	private Logger logger = LoggerFactory.getLogger(PortalMenuFacadeImpl.class);

	@Autowired
	private PortalMenuService portalMenuService;

	@Override
	public MenuDTO findMenu(Long menuId) {
		return portalMenuService.findMenuByID(menuId);
	}

	@Override
	public Long addMenu(MenuDTO menuDTO) {
		CheckUtils.notEmpty(menuDTO, "菜单信息");
		CheckUtils.notEmpty(menuDTO.getMenuName(), "菜单名");
		CheckUtils.notEmpty(menuDTO.getParentId(), "父菜单ID");
		CheckUtils.notEmpty(menuDTO.getLevelNum(), "菜单层级");
		return portalMenuService.addMenu(menuDTO);
	}

	@Override
	public void updateMenu(MenuDTO menuDTO) {
		CheckUtils.notEmpty(menuDTO, "菜单信息");
		CheckUtils.notEmpty(menuDTO.getMenuName(), "菜单名");
		CheckUtils.notEmpty(menuDTO.getParentId(), "父菜单ID");
		CheckUtils.notEmpty(menuDTO.getMenuId(), "菜单ID");
		portalMenuService.updateMenu(menuDTO);
	}

	@Override
	public void moveMenus(List<Long> menuIds, Long parentId, Long departmentId) {
		CheckUtils.notEmpty(menuIds, "子菜单列表");
		CheckUtils.notNull(parentId, "父菜单ID");
		if (menuIds.contains(parentId)) {
			throw new IllegalArgumentException("子菜单ID列表不能包含父菜单ID");
		}
		MenuDTO parent = portalMenuService.findMenuByID(parentId);
		if (parent == null) {
			throw new IllegalArgumentException("父菜单不存在，菜单ID：" + parentId);
		}
		List<MenuDTO> menus = portalMenuService.queryMenusByIdList(menuIds);
		for (MenuDTO menu : menus) {
			menu.setParentId(parent.getMenuId());
			menu.setDepartmentId(departmentId);
			menu.setLevelNum(parent.getLevelNum() + 1);
		}
		portalMenuService.batchUpdateMenu(menus);
	}

	@Override
	public void deleteMenus(List<Long> menuIds) {
		portalMenuService.batchDeleteMenu(menuIds);
	}

	@Override
	public List<MenuKVDTO> findMenuKVByDepartmentId(Long departmentId) {
		return portalMenuService.queryMenusKVByDepartment(departmentId);
	}

	@Override
	public MenuModel findDepartmentMemuTree(Long departmentId) {
		List<MenuDTO> menus = portalMenuService
				.queryMenusByDepartment(departmentId);
		return this.buildMenuModel(menus);
	}

	@Override
	public void deleteDepartmentMenu(Long departmentId) {
		CheckUtils.notNull(departmentId, "部门ID");
		portalMenuService.deleteDepartmentMenu(departmentId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addTab(Long userId, String title, String url) {
		try {
			ConfigParam<List<String>> config = ConfigurationUtils.getAppConfigParam("employee-boss-portal-tab-disabled");
			if (null != config && null != config.getValue()) {
				List<String> values = config.getValue();
				for (String item : values) {
					if (Pattern.compile(item).matcher(url).find()) {
						return;
					}
				}
			}
		} catch (Exception e) {
			// Do nothing
		}

		LRUCache<String, String> lastOpenTab = (LRUCache<String, String>) RemoteCacheUtils.getClient(ConfigHelper.SSO_CACHE_CLIENT, "lastOpenTab-" + userId);
		if (null == lastOpenTab) {
			lastOpenTab = new LRUCache<String, String>(10);
		}
		lastOpenTab.put(title, url.replace("boss_sso_token", "hehe"));
		RemoteCacheUtils.putClient(ConfigHelper.SSO_CACHE_CLIENT, "lastOpenTab-" + userId, lastOpenTab, 259200);
		logger.debug("保存最后访问地址： 用户：{} 访问：{} 地址：{}", userId, title, url);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteTab(Long userId, String title) {
		LRUCache<String, String> lastOpenTab = (LRUCache<String, String>) RemoteCacheUtils.getClient(ConfigHelper.SSO_CACHE_CLIENT, "lastOpenTab-" + userId);
		if (null == lastOpenTab) {
			lastOpenTab = new LRUCache<String, String>(10);
		} else {
			lastOpenTab.remove(title);
		}
		RemoteCacheUtils.putClient(ConfigHelper.SSO_CACHE_CLIENT, "lastOpenTab-" + userId, lastOpenTab);
		logger.debug("删除最后访问地址： 用户：{} 访问：{}", userId, title);
	}

	@Override
	public void cleanTab(Long userId) {
		LRUCache<String, String> lastOpenTab = new LRUCache<String, String>(10);
		RemoteCacheUtils.putClient(ConfigHelper.SSO_CACHE_CLIENT, "lastOpenTab-" + userId, lastOpenTab, 1);
	}

	@Override
	public void cleanAllTab(List<String> userIds) {
		for (String userId : userIds) {
			cleanTab(Long.parseLong(userId));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getAllTab(Long userId) {
		return (Map<String, String>) RemoteCacheUtils.getClient(ConfigHelper.SSO_CACHE_CLIENT, "lastOpenTab-" + userId);
	}

	@Override
	public void importMenus(String menuDetail, Long departmentId) {
		CheckUtils.notEmpty(menuDetail, "菜单信息");
		CheckUtils.notNull(departmentId, "部门ID");
		String[] infoList = menuDetail.split("[\r\n]+");
		Map<String, Long> menuIds = Maps.newHashMap();
		menuIds.put("", -1L);// 父级菜单找不到的情况下，认为是顶级菜单

		// 处理已有菜单
		List<MenuKVDTO> menuKVDTOList = portalMenuService.queryMenusKVByDepartment(departmentId);
		for (MenuKVDTO menuKV : menuKVDTOList) {
			menuIds.put(menuKV.getMenuName(), menuKV.getMenuId());
		}

		// 处理新导入的菜单
		for (String line : infoList) {
			if (StringUtils.isBlank(line)) {
				continue;
			}
			String[] info = line.split("[,，]");
			if (StringUtils.isBlank(info[0])) {
				logger.error("导入菜单信息不正确，导入行：" + line);
				continue;
			}
			String menuName = info[0].trim();
			String functionUrl = null;
			String parentName = "";
			if (info.length > 1) {
				functionUrl = info[1].trim();
			}
			if (info.length > 2) {
				parentName = info[2].trim();
			}

			// 封装 MenuDTO
			MenuDTO menuDTO = new MenuDTO();
			menuDTO.setMenuName(menuName);
			menuDTO.setFunctionUrl(functionUrl);
			menuDTO.setParentId(menuIds.get(parentName));
			menuDTO.setDepartmentId(departmentId);

			try {
				Long menuId = portalMenuService.addMenu(menuDTO);
				menuIds.put(menuName, menuId);
			} catch (Exception e) {
				// 出错也继续
				logger.error("{}", e);
			}
		}

		List<MenuDTO> allMenus = this.portalMenuService
				.queryMenusByDepartment(departmentId);

		// 构建菜单树
		MenuModel menuModel = this.buildMenuModel(allMenus);

		// 重置层级
		this.resetSeqAndLevel(menuModel, 0);

		// 批量更新
		if (!allMenus.isEmpty()) {
			portalMenuService.batchUpdateMenu(allMenus);
		}
	}

	/**
	 * 重置菜单顺序和层级
	 */
	private void resetSeqAndLevel(MenuModel parent, int levelNum) {
		if (parent == null) {
			return;
		}
		if (parent.getMenuDTO() != null) {
			parent.getMenuDTO().setLevelNum(levelNum);
		}
		if (parent.getSubMenus() != null) {
			int preSeq = 0;
			for (MenuModel model : parent.getSubMenus()) {
				if (model.getMenuDTO() != null) {
					if (model.getMenuDTO().getSequence() == null) {
						model.getMenuDTO().setSequence(preSeq + 1);
					}
					preSeq = model.getMenuDTO().getSequence();
				}
				this.resetSeqAndLevel(model, levelNum + 1);
			}
		}
	}

	/**
	 * 构建菜单树模型
	 */
	private MenuModel buildMenuModel(List<MenuDTO> menuDTOList) {
		MenuModel menuModel = new MenuModel();

		// 遍历所有有权限的菜单
		Map<Long, MenuModel> menuIdMap = Maps.newHashMap();
		for (MenuDTO menuDTO : menuDTOList) {
			MenuModel subMenu = new MenuModel();
			subMenu.setFunctionUrl(menuDTO.getFunctionUrl());
			subMenu.setMenuDTO(menuDTO);
			if (menuDTO.getParentId() != null && 0 == menuDTO.getParentId()) {
				menuModel.getSubMenus().add(subMenu);
			}
			menuIdMap.put(menuDTO.getMenuId(), subMenu);
		}

		// 设置上下级关系
		for (MenuDTO menuDTO : menuDTOList) {
			MenuModel node = menuIdMap.get(menuDTO.getMenuId());
			if (null == node) {
				continue;
			}
			MenuModel parentNode = menuIdMap.get(menuDTO.getParentId());
			if (null == parentNode) {
				continue;
			}
			parentNode.getSubMenus().add(node);
		}

		return menuModel;
	}

}
