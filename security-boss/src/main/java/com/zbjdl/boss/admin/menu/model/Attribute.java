/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.menu.model;

/**
 * 
 * 类名称：Attribute <br>
 * 类描述：jsTree节点的服务器端模型 <br>
 * 
 * @author：feng
 * @since：2011-8-1 下午01:32:00
 * @version:
 * 
 */
public class Attribute {

	/**
	 * 节点Id
	 */
	protected String id;

	/**
	 * 节点类型
	 */
	protected String rel;

	/**
	 * id
	 * 
	 * @return the id
	 */

	public String getId() {
		return id;
	}

	/**
	 * 描述： 设定节点Id
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * rel
	 * 
	 * @return the rel
	 */

	public String getRel() {
		return rel;
	}

	/**
	 * 描述： 设定节点类型
	 * 
	 * @param rel
	 *            the rel to set
	 */
	public void setRel(String rel) {
		this.rel = rel;
	}

}
