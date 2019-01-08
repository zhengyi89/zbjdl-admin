/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.menu.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * 类名称：DeptVO <br>
 * 类描述： <br>
 * 
 * @author：feng
 * @since：2011-7-14 下午02:24:17
 * @version:
 * 
 */
public class Dept {

	private Long deptId;

	private String deptName;

	private List<Function> functions = new ArrayList<Function>();

	private boolean selected = false;

	/**
	 * deptId
	 * 
	 * @return the deptId
	 */

	public Long getDeptId() {
		return deptId;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param deptId
	 *            the deptId to set
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	/**
	 * deptName
	 * 
	 * @return the deptName
	 */

	public String getDeptName() {
		return deptName;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param deptName
	 *            the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * functions
	 * 
	 * @return the functions
	 */

	public List<Function> getFunctions() {
		return functions;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * 
	 * @param functions
	 *            the functions to set
	 */
	public void setFunctions(List<Function> functions) {
		this.functions = functions;
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
