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
 * @author：feng
 * @since：2011-7-4 下午04:04:09
 * @version:
 * 
 */
public class MenuKVDTO extends BaseDTO {

	private static final long serialVersionUID = 3728224653422977213L;

	private Long menuId;// 菜单Id

	private String menuName;// 菜单名

    public MenuKVDTO() {
        // Nothing
    }

    public MenuKVDTO(Long menuId, String menuName) {
        this.menuId = menuId;
        this.menuName = menuName;
    }

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

	public String toString() {
		try {
			return ToStringBuilder.reflectionToString(this,
					ToStringStyle.SHORT_PREFIX_STYLE);
		} catch (Exception e) {
			return super.toString();
		}
	}
}
