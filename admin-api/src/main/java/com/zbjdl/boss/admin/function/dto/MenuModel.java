/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * 类名称：MenuModel <br>
 * 类描述：对所有菜单树形结构的组织定义 <br>
 * 
 * @author：feng
 * @since：2011-7-11 上午11:26:19
 * @version:
 * 
 */
public class MenuModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private MenuDTO menuDTO;
	
	private List<MenuModel> subMenus = new ArrayList<MenuModel>();
	
	private String functionUrl;

	/**    
	 * menuDTO    
	 *    
	 * @return  the menuDTO   
	 */
	
	public MenuDTO getMenuDTO() {
		return menuDTO;
	}

	/**    
	 * 描述： 菜单信息
	 * @param menuDTO the menuDTO to set    
	 */
	public void setMenuDTO(MenuDTO menuDTO) {
		this.menuDTO = menuDTO;
	}

	/**    
	 * subMenus    
	 *    
	 * @return  the subMenus   
	 */
	
	public List<MenuModel> getSubMenus() {
		return subMenus;
	}

	/**    
	 * 描述： 递归子菜单信息
	 * @param subMenus the subMenus to set    
	 */
	public void setSubMenus(List<MenuModel> subMenus) {
		this.subMenus = subMenus;
	}

	/**    
	 * funtionUrl    
	 *    
	 * @return  the funtionUrl   
	 */
	
	public String getFunctionUrl() {
		return functionUrl;
	}

	/**    
	 * 描述： 在这里描述属性的含义
	 * @param funtionUrl the funtionUrl to set    
	 */
	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}
	
	public String toString() {
		try {
			return ToStringBuilder.reflectionToString(this,
					ToStringStyle.MULTI_LINE_STYLE);
		} catch (Exception e) {
			return super.toString();
		}
	}
}
