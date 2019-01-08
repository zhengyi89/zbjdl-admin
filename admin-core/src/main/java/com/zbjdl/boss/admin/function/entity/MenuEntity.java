/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.zbjdl.common.persistence.Entity;

/**    
 *    
 * 类名称：MenuEntity <br>    
 * 类描述：菜单项实体<br>
 * @author：feng    
 * @since：2011-7-1 下午07:19:46 
 * @version:     
 *     
 */
public class MenuEntity implements Entity<Long> {
	
	private static final long serialVersionUID = 1500221709678646642L;

	private Long menuId;//主键
	
	private String menuName;//菜单名
	
	private Long functionId;//对应的功能ID
	
	private Integer sequence;//菜单排序号
	
	private Long parentId;//父菜单项Id
	
	private Integer levelNum;//层级，即第几级菜单
	
	private String iconName;//菜单图标名称

	/**    
	 * menuId    
	 *    
	 * @return  the menuId   
	 */
	
	public Long getMenuId() {
		return menuId;
	}

	/**    
	 * 描述： 在这里描述属性的含义
	 * @param menuId the menuId to set    
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	/**    
	 * menuName    
	 *    
	 * @return  the menuName   
	 */
	
	public String getMenuName() {
		return menuName;
	}

	/**    
	 * 描述： 在这里描述属性的含义
	 * @param menuName the menuName to set    
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**    
	 * functionId    
	 *    
	 * @return  the functionId   
	 */
	
	public Long getFunctionId() {
		return functionId;
	}

	/**    
	 * 描述： 在这里描述属性的含义
	 * @param functionId the functionId to set    
	 */
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	/**    
	 * sequence    
	 *    
	 * @return  the sequence   
	 */
	
	public Integer getSequence() {
		return sequence;
	}

	/**    
	 * 描述： 在这里描述属性的含义
	 * @param sequence the sequence to set    
	 */
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	/**    
	 * parentId    
	 *    
	 * @return  the parentId   
	 */
	
	public Long getParentId() {
		return parentId;
	}

	/**    
	 * 描述： 在这里描述属性的含义
	 * @param parentId the parentId to set    
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**    
	 * levelNum    
	 *    
	 * @return  the levelNum   
	 */
	
	public Integer getLevelNum() {
		return levelNum;
	}

	/**    
	 * 描述： 在这里描述属性的含义
	 * @param levelNum the levelNum to set    
	 */
	public void setLevelNum(Integer levelNum) {
		this.levelNum = levelNum;
	}

	/**    
	 * iconName    
	 *    
	 * @return  the iconName   
	 */
	
	public String getIconName() {
		return iconName;
	}

	/**    
	 * 描述： 在这里描述属性的含义
	 * @param iconName the iconName to set    
	 */
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.common.persistence.Entity#getId()    
	 */
	public Long getId() {
		return menuId;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.common.persistence.Entity#setId(java.io.Serializable)    
	 */
	public void setId(Long arg0) {
		this.menuId = arg0;
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
