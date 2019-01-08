package com.zbjdl.boss.admin.menu.action;

import java.util.ArrayList;
import java.util.List;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.facade.PortalMenuFacade;
import com.zbjdl.boss.admin.frame.utils.Constants;
import com.zbjdl.boss.admin.function.dto.MenuDTO;
import com.zbjdl.boss.admin.function.dto.MenuKVDTO;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.common.dubbo.proxy.SoaServiceRepository;
import com.zbjdl.common.utils.CheckUtils;

/**
 * 门户菜单维护
 * 
 * @author feng
 * @since 2013-6-3 下午4:38:02
 * @version 1.0.0
 */
public class PortalMenuAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 344399200781184000L;

	private Long departmentId;

	private List<Long> menuIds;

	private List<MenuKVDTO> menus;

	private Long parentId;

	private String menuDetail;

	private Boolean resetAll;

	private MenuDTO menuDTO;

	private UserDTO curUser;

	private List<DepartmentDTO> departList = new ArrayList<DepartmentDTO>();// 所有部门列表

	private PortalMenuFacade portalMenuFacade = SoaServiceRepository
			.getService(PortalMenuFacade.class,Constants.DUBBO_DEFAULT_GROUP,Constants.DUBBO_DEFAULT_VERSION);

	public String execute() {
		curUser = super.getCurrentUser();
		if (curUser.getIsSuperAdmin()) {
			departList = remoteService.getUserFacade()
					.queryAllDepartments();
			menus = portalMenuFacade.findMenuKVByDepartmentId(0L);
		} else if (curUser.getIsAdmin()) {
			departmentId = this.getCurrentDepartment();
			menus = portalMenuFacade.findMenuKVByDepartmentId(departmentId);
		}

		return SUCCESS;
	}

	public String addPortalMenu() {
		try {
			CheckUtils.notEmpty(menuDTO, "菜单信息");
			CheckUtils.notEmpty(menuDTO.getMenuName(), "菜单名");
			if (this.isDeptAdmin()) {
				departmentId = this.getCurrentDepartment();
			}
			CheckUtils.notNull(departmentId, "部门ID");
			menuDTO.setDepartmentId(departmentId);
			portalMenuFacade.addMenu(menuDTO);
		} catch (Exception e) {
			this.handleException(e);
		}
		return SUCCESS;
	}

	public String editPortalMenu() {
		try {
			CheckUtils.notEmpty(menuDTO, "菜单信息");
			CheckUtils.notEmpty(menuDTO.getMenuId(), "菜单ID");
            if (this.isDeptAdmin()) {
                departmentId = this.getCurrentDepartment();
            }
            CheckUtils.notNull(departmentId, "部门ID");
            menuDTO.setDepartmentId(departmentId);
			portalMenuFacade.updateMenu(menuDTO);
		} catch (Exception e) {
			this.handleException(e);
		}
		return SUCCESS;
	}

	public String delPortalMenu() {
		try {
			portalMenuFacade.deleteMenus(menuIds);
		} catch (Exception e) {
			this.handleException(e);
		}
		return SUCCESS;
	}

	public String importPortalMenu() {
		try {
			if (this.isDeptAdmin()) {
				departmentId = this.getCurrentDepartment();
			}
			CheckUtils.notEmpty(menuDetail, "菜单内容");
			CheckUtils.notNull(departmentId, "部门ID");
			if (resetAll != null && resetAll) {
				portalMenuFacade.deleteDepartmentMenu(departmentId);
			}
			portalMenuFacade.importMenus(menuDetail, departmentId);
		} catch (Exception e) {
			this.handleException(e);
		}
		return SUCCESS;
	}

	public String movePortalMenu() {
		try {
			CheckUtils.notEmpty(menuIds, "待移动菜单");
			CheckUtils.notEmpty(parentId, "父级菜单");
			portalMenuFacade.moveMenus(menuIds, parentId, departmentId);
		} catch (Exception e) {
			this.handleException(e);
		}
		return SUCCESS;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public List<Long> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<Long> menuIds) {
		this.menuIds = menuIds;
	}

	public List<MenuKVDTO> getMenus() {
		return menus;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getMenuDetail() {
		return menuDetail;
	}

	public void setMenuDetail(String menuDetail) {
		this.menuDetail = menuDetail;
	}

	public Boolean getResetAll() {
		return resetAll;
	}

	public void setResetAll(Boolean resetAll) {
		this.resetAll = resetAll;
	}

	public MenuDTO getMenuDTO() {
		return menuDTO;
	}

	public void setMenuDTO(MenuDTO menuDTO) {
		this.menuDTO = menuDTO;
	}

	public List<DepartmentDTO> getDepartList() {
		return departList;
	}

	public UserDTO getCurUser() {
		return curUser;
	}
}
