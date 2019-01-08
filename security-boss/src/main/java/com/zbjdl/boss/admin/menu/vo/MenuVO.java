/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.menu.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zbjdl.boss.admin.basic.vo.BasicVO;

/**
 * 
 * 类名称：MenuVO <br>
 * 类描述：菜单VO对象 <br>
 * 
 * @author：feng
 * @since：2011-7-14 下午02:05:49
 * @version:
 * 
 */
public class MenuVO extends BasicVO {

	private static final long serialVersionUID = 1L;

	private Long menuId;// 菜单Id

	private String menuName;// 菜单名

	private Long functionId;// 对应的功能ID

	private Integer sequence;// 菜单排序号

	private Long parentId;// 父菜单项ID

	private Integer levelNum;// 层级，即第几级菜单

	private String iconName;// 菜单图标名称

	private Long deptId;// 部门Id

	private String parentName;// 父菜单名称

	private Long selectedMenuId;// 选中菜单Id

	private String addType;// 添加类型

	private List<Dept> depts = new ArrayList<Dept>();// 部门List

	private Map<String, String> menuIcons;// 菜单图标Map
	
	private String password;

	/**
	 * menuId
	 * 
	 * @return the menuId
	 */

	public Long getMenuId() {
		return menuId;
	}

	/**
	 * 描述： 设定菜单Id
	 * 
	 * @param menuId
	 *            the menuId to set
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	/**
	 * menuName
	 * 
	 * @return the menuName
	 */

	public String getMenuName() {
		return menuName;
	}

	/**
	 * 描述： 设定菜单名称
	 * 
	 * @param menuName
	 *            the menuName to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * functionId
	 * 
	 * @return the functionId
	 */

	public Long getFunctionId() {
		return functionId;
	}

	/**
	 * 描述： 设定功能Id
	 * 
	 * @param functionId
	 *            the functionId to set
	 */
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	

	/**
	 * parentId
	 * 
	 * @return the parentId
	 */

	public Long getParentId() {
		return parentId;
	}

	/**
	 * 描述： 设定菜单父菜单Id
	 * 
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(Integer levelNum) {
		this.levelNum = levelNum;
	}

	/**
	 * deptId
	 * 
	 * @return the deptId
	 */

	public Long getDeptId() {
		return deptId;
	}

	/**
	 * 描述： 设定部门Id
	 * 
	 * @param deptId
	 *            the deptId to set
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	/**
	 * parentName
	 * 
	 * @return the parentName
	 */

	public String getParentName() {
		return parentName;
	}

	/**
	 * 描述： 设定父菜单名称
	 * 
	 * @param parentName
	 *            the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * depts
	 * 
	 * @return the depts
	 */

	public List<Dept> getDepts() {
		return depts;
	}

	/**
	 * 描述： 设定部门List
	 * 
	 * @param depts
	 *            the depts to set
	 */
	public void setDepts(List<Dept> depts) {
		this.depts = depts;
	}

	/**
	 * selectedMenuId
	 * 
	 * @return the selectedMenuId
	 */

	public Long getSelectedMenuId() {
		return selectedMenuId;
	}

	/**
	 * 描述： 设定选中菜单Id
	 * 
	 * @param selectedMenuId
	 *            the selectedMenuId to set
	 */
	public void setSelectedMenuId(Long selectedMenuId) {
		this.selectedMenuId = selectedMenuId;
	}

	/**
	 * addType
	 * 
	 * @return the addType
	 */

	public String getAddType() {
		return addType;
	}

	/**
	 * 描述： 设定添加类型
	 * 
	 * @param addType
	 *            the addType to set
	 */
	public void setAddType(String addType) {
		this.addType = addType;
	}

	/**
	 * menuIcons
	 * 
	 * @return the menuIcons
	 */

	public Map<String, String> getMenuIcons() {
		return menuIcons;
	}

	/**
	 * 描述： 设定菜单图标Map
	 * 
	 * @param menuIcons
	 *            the menuIcons to set
	 */
	public void setMenuIcons(Map<String, String> menuIcons) {
		this.menuIcons = menuIcons;
	}

	/**
	 * iconName
	 * 
	 * @return the iconName
	 */

	public String getIconName() {
		return iconName;
	}

	/**
	 * 描述： 在设定菜单图标名称
	 * 
	 * @param iconName
	 *            the iconName to set
	 */
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
