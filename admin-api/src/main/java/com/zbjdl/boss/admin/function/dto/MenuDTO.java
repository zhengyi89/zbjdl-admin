/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.dto;

import com.zbjdl.boss.admin.dto.basic.BaseDTO;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * 类名称：MenuDTO <br>
 * 类描述：菜单信息DTO<br>
 * 
 * @author feng
 * @since 2011-7-4 下午04:04:09
 * @version 1.0.0
 * 
 */
public class MenuDTO extends BaseDTO {

	private static final long serialVersionUID = 3728224653422977213L;

	private Long menuId;// 菜单Id

	private String menuName;// 菜单名

	private Long functionId;// 对应的功能ID

	private Integer sequence;// 菜单排序号

	private Long parentId;// 父菜单项ID

	private Integer levelNum;// 层级，即第几级菜单

	private Long departmentId;// 菜单所属功能对应的部门ID

	private String iconName;

	/**
	 * 菜单目标地址
	 */
	private String functionUrl;
	
	/**
	 * menuId
	 * 
	 * @return the menuId
	 */
	public Long getMenuId() {
		return menuId;
	}

	/**
	 * 描述： 在这里描述属性的含义
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
	 * 描述： 在这里描述属性的含义
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
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param functionId
	 *            the functionId to set
	 */
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	/**
	 * sequence
	 * 
	 * @return the sequence
	 */
	public Integer getSequence() {
		return sequence;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param sequence
	 *            the sequence to set
	 */
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
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
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * levelNum
	 * 
	 * @return the levelNum
	 */
	public Integer getLevelNum() {
		return levelNum;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param levelNum
	 *            the levelNum to set
	 */
	public void setLevelNum(Integer levelNum) {
		this.levelNum = levelNum;
	}

	/**
	 * departmentId
	 * 
	 * @return the departmentId
	 */
	public Long getDepartmentId() {
		return departmentId;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param departmentId
	 *            the departmentId to set
	 */
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
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
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param iconName
	 *            the iconName to set
	 */
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getFunctionUrl() {
		return functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	public String toString() {
		try {
			return ToStringBuilder.reflectionToString(this,
					ToStringStyle.SHORT_PREFIX_STYLE);
		} catch (Exception e) {
			return super.toString();
		}
	}
}
