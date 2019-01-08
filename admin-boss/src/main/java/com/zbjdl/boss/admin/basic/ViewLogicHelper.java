/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.basic;

import org.springframework.stereotype.Component;

import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.dto.MenuModel;
import com.zbjdl.common.utils.StringUtils;;

/**
 * @author：feng
 * @since：2012-6-8 下午02:03:56
 * @version:
 */
@Component
public class ViewLogicHelper {
	private static final String TPL_ROOT_START = "<dl>\n";
	private static final String TPL_ROOT_END = "</dl>\n";
	private static final String TPL_LEVEL_ONE_START = "<dd class=\"nav_default fw\"><span><a href=\"javascript:void(0);\" url=\"URL\">NAME</a></span><em></em>\n";
	private static final String TPL_LEVEL_ONE_END = "</dd>\n";
	private static final String TPL_LEVEL_TWO_START = "<ul style=\"display:none;\">\n";
	private static final String TPL_LEAF_START = "<li><a href=\"javascript:void(0);\" url=\"URL\">NAME <i class=\"icon-chevron-right\">></i></a>\n";
	private static final String TPL_LEAF_FULL = "<li><a href=\"javascript:void(0);\" url=\"URL\">NAME</a></li>\n";
	private static final String TAG_UL_START = "<ul>\n";
	private static final String TAG_UL_END = "</ul>\n";
	private static final String TAG_LI_END = "</li>\n";

	private StringUtils stringUtils = new StringUtils();

	public String[] split(String string, String splitor) {
		return StringUtils.split(string, splitor);
	}

	public StringUtils getStringUtils() {
		return stringUtils;
	}

	public String buildMenu(MenuModel menuModel) {
		return this.buildMenu(menuModel, 0);
	}
	
	public String buildMenu(MenuModel menuModel, int level) {
		StringBuilder sb = new StringBuilder();
		if (menuModel == null) {
			return sb.toString();
		}
		if (menuModel.getMenuDTO() == null && menuModel.getSubMenus() != null && !menuModel.getSubMenus().isEmpty()) {
			return buildMenu(menuModel.getSubMenus().get(0), 1);
		}
		if (level < 1) {
			level = menuModel.getMenuDTO().getLevelNum();
		}
		if (level < 2) {
			sb.append(TPL_ROOT_START);
			if (menuModel.getSubMenus() != null) {
				for (MenuModel sub : menuModel.getSubMenus()) {
					sb.append(buildMenu(sub, level + 1));
				}
			}
			sb.append(TPL_ROOT_END);
		} else if (level == 2) {
			sb.append(this.buildLeaf(menuModel.getMenuDTO(), TPL_LEVEL_ONE_START));
			sb.append(TPL_LEVEL_TWO_START);
			// 本身是叶子节点，无子菜单,什么都不做
			if (menuModel.getSubMenus() != null && !menuModel.getSubMenus().isEmpty()) {
				for (MenuModel sub : menuModel.getSubMenus()) {
					sb.append(buildMenu(sub, level + 1));
				}
			}
			sb.append(TAG_UL_END);
			sb.append(TPL_LEVEL_ONE_END); 
		} else {
			if (menuModel.getSubMenus() == null || menuModel.getSubMenus().isEmpty()) {
				// 本身是叶子节点，无子菜单
				sb.append(this.buildLeaf(menuModel.getMenuDTO(), TPL_LEAF_FULL));
			} else {
				// 有子菜单
				sb.append(this.buildLeaf(menuModel.getMenuDTO(), TPL_LEAF_START));
				sb.append(TAG_UL_START);
				for (MenuModel sub : menuModel.getSubMenus()) {
					sb.append(buildMenu(sub, level + 1));
				}
				sb.append(TAG_UL_END);
				sb.append(TAG_LI_END);
			}
		}

		return sb.toString();
	}

	private String buildLeaf(MenuDTO menuDTO, String template) {
		String url = menuDTO.getFunctionUrl();
		if (url == null) {
			url = "";
		}
		String leaf = template.replace("URL", url);
		leaf = leaf.replace("NAME", menuDTO.getMenuName());
		return leaf;
	}
}
