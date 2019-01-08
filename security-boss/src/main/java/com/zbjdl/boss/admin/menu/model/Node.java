/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.menu.model;

/**
 * 
 * 类名称：Node <br>
 * 类描述： jsTree的服务器端模型 <br>
 * 
 * @author：feng
 * @since：2011-8-1 下午01:30:56
 * @version:
 * 
 */
public class Node {

	/**
	 * 树节点的默认状态
	 */
	public static final String DEFAULT_STATE = "closed";

	/**
	 * 节点属性
	 */
	protected Attribute attr;

	/**
	 * 节点名称
	 */
	protected String data;

	/**
	 * 节点状态
	 */
	protected String state = DEFAULT_STATE;

	/**
	 * attr
	 * 
	 * @return the attr
	 */

	public Attribute getAttr() {
		return attr;
	}

	/**
	 * 描述：设定节点属性
	 * 
	 * @param attr
	 *            the attr to set
	 */
	public void setAttr(Attribute attr) {
		this.attr = attr;
	}

	/**
	 * data
	 * 
	 * @return the data
	 */

	public String getData() {
		return data;
	}

	/**
	 * 描述： 设定节点名称
	 * 
	 * @param data
	 *            the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * state
	 * 
	 * @return the state
	 */

	public String getState() {
		return state;
	}

	/**
	 * 描述： 设定节点状态
	 * 
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

}