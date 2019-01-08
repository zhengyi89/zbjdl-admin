/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.menu.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * 类名称：FunctionVO <br>
 * 类描述： <br>
 * 
 * @author：feng
 * @since：2011-7-14 下午02:24:27
 * @version:
 * 
 */
public class Function {

	private Long functionId;

	private String functionName;

	private boolean selected = false;

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
	 * functionName
	 * 
	 * @return the functionName
	 */

	public String getFunctionName() {
		return functionName;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param functionName
	 *            the functionName to set
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	/**
	 * selected
	 * 
	 * @return the selected
	 */

	public boolean isSelected() {
		return selected;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
