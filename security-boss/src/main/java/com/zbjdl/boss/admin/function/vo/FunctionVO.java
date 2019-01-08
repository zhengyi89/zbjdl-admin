/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.vo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.zbjdl.boss.admin.basic.vo.BasicVO;

/**
 * 
 * 类名称：FunctionVO <br>
 * 类描述：功能VO对象 <br>
 * 
 * @author feng
 * @since 2011-7-25 上午10:26:46
 * @version:
 * 
 */
public class FunctionVO extends BasicVO {

	private static final long serialVersionUID = 1L;

	private Long functionId;// 功能ID

	private String functionName;// 功能名称

	private String functionUrl;// 功能url

	private Long preFunctionId;// 前置功能ID

	private String description;// 功能描述

	private String riskLevel;// 风险级别

	private String functionType;// 功能类型

	private String functionStatus;// 功能状态

	private String[] deptNames;// 所属部门名称

	private String menuPath;// 菜单路径

	private Timestamp createTime;// 创建时间

	private String password;// 管理员密码

	private boolean selected;// 下拉列表中该功能是否处于选择状态

	private Long roleId; // 角色Id
	
	private Boolean display;//是否展示
	
	private String checkNeeded;//是否需要复核

	private Long checkFunctionId;//所复核的功能ID
	
	private Boolean logNeeded;
	
	private String keyWord;

	private String tag;

	/**
	 * functionId
	 * 
	 * @return the functionId
	 */

	public Long getFunctionId() {
		return functionId;
	}

	/**
	 * 描述： 功能Id
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
	 * 描述： 功能名称
	 * 
	 * @param functionName
	 *            the functionName to set
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	/**
	 * functionUrl
	 * 
	 * @return the functionUrl
	 */

	public String getFunctionUrl() {
		return functionUrl;
	}

	/**
	 * 描述： 功能URL
	 * 
	 * @param functionUrl
	 *            the functionUrl to set
	 */
	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	/**
	 * preFunctionId
	 * 
	 * @return the preFunctionId
	 */

	public Long getPreFunctionId() {
		return preFunctionId;
	}

	/**
	 * 描述： 前置功能Id
	 * 
	 * @param preFunctionId
	 *            the preFunctionId to set
	 */
	public void setPreFunctionId(Long preFunctionId) {
		this.preFunctionId = preFunctionId;
	}

	/**
	 * description
	 * 
	 * @return the description
	 */

	public String getDescription() {
		return description;
	}

	/**
	 * 描述： 功能描述
	 * 
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * riskLevel
	 * 
	 * @return the riskLevel
	 */

	public String getRiskLevel() {
		return riskLevel;
	}

	/**
	 * 描述： 功能风险级别
	 * 
	 * @param riskLevel
	 *            the riskLevel to set
	 */
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	/**
	 * functionType
	 * 
	 * @return the functionType
	 */

	public String getFunctionType() {
		return functionType;
	}

	/**
	 * 描述： 功能类型
	 * 
	 * @param functionType
	 *            the functionType to set
	 */
	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

	/**
	 * functionStatus
	 * 
	 * @return the functionStatus
	 */

	public String getFunctionStatus() {
		return functionStatus;
	}

	/**
	 * 描述： 功能状态
	 * 
	 * @param functionStatus
	 *            the functionStatus to set
	 */
	public void setFunctionStatus(String functionStatus) {
		this.functionStatus = functionStatus;
	}

	/**
	 * deptNames
	 * 
	 * @return the deptNames
	 */

	public String[] getDeptNames() {
		return deptNames;
	}

	/**
	 * 描述： 部门名字数组
	 * 
	 * @param deptNames
	 *            the deptNames to set
	 */
	public void setDeptNames(String[] deptNames) {
		this.deptNames = deptNames;
	}

	/**
	 * menuPath
	 * 
	 * @return the menuPath
	 */

	public String getMenuPath() {
		return menuPath;
	}

	/**
	 * 描述： 菜单路径
	 * 
	 * @param menuPath
	 *            the menuPath to set
	 */
	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	/**
	 * createTime
	 * 
	 * @return the createTime
	 */

	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * 
	 * 描述： 格式化的创建时间
	 * 
	 * @return
	 */
	public String getFormatCreateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:ss:mm");
		if (createTime != null) {
			return sdf.format(createTime);
		}
		return null;
	}

	/**
	 * 描述： 部门创建时间
	 * 
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * password
	 * 
	 * @return the password
	 */

	public String getPassword() {
		return password;
	}

	/**
	 * 描述： 管理员密码
	 * 
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * 描述： 是否被选中
	 * 
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * departmentName
	 * 
	 * @return the departmentName
	 */

	public String getDepartmentName() {
		if (deptNames != null && deptNames.length > 0) {
			StringBuffer sb = new StringBuffer();
			for (String name : deptNames) {
				sb.append(name).append("，");
			}
			return sb.substring(0, sb.length() - 1);
		}
		return null;
	}

	/**
	 * roleId
	 * 
	 * @return the roleId
	 */

	public Long getRoleId() {
		return roleId;
	}

	/**
	 * 描述： 角色Id
	 * 
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/* (non-Javadoc)    
	 * @see java.lang.Object#equals(java.lang.Object)    
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof FunctionVO) {
			FunctionVO tmp = (FunctionVO) obj;
			if (this.functionId != null && tmp.getFunctionId() != null && this.functionId.equals(tmp.getFunctionId())) {
				return true;
			}
		} else {
			return false;
		}
		return super.equals(obj);
	}

	/**    
	 * display    
	 *    
	 * @return  the display   
	 */
	
	public Boolean getDisplay() {
		return display;
	}

	/**    
	 * @param display the display to set    
	 */
	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public String getCheckNeeded() {
		return checkNeeded;
	}

	public void setCheckNeeded(String checkNeeded) {
		this.checkNeeded = checkNeeded;
	}

	public Long getCheckFunctionId() {
		return checkFunctionId;
	}

	public void setCheckFunctionId(Long checkFunctionId) {
		this.checkFunctionId = checkFunctionId;
	}

	public Boolean getLogNeeded() {
		return logNeeded;
	}

	public void setLogNeeded(Boolean logNeeded) {
		this.logNeeded = logNeeded;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
