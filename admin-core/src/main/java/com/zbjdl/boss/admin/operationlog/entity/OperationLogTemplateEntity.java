/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.operationlog.entity;

import com.zbjdl.common.persistence.Entity;

/**
 *
 * 类名称：OperationLogTemplate <br>
 * 类描述：操作日志模板类<br>
 * @author：feng
 * @since：2011-5-16 下午02:56:27
 * @version:
 *
 */
public class OperationLogTemplateEntity implements Entity<Long> {

	private static final long serialVersionUID = 2806359565080490004L;

	private Long templateId;//主键

	private Long functionId;//对应功能ID

	private String content;//模板内容

	private String keyWord;//关键字




	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
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
	 * 描述： 对应功能ID
	 * @param functionId the functionId to set
	 */
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	/**
	 * content
	 *
	 * @return  the content
	 */

	public String getContent() {
		return content;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * keyWord
	 *
	 * @return  the keyWord
	 */

	public String getKeyWord() {
		return keyWord;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 * @param keyWord the keyWord to set
	 */
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.common.persistence.Entity#getId()
	 */
	@Override
	public Long getId() {
		return templateId;
	}

	/* (non-Javadoc)
	 * @see com.zbjdl.common.persistence.Entity#setId(java.io.Serializable)
	 */
	@Override
	public void setId(Long arg0) {
		this.templateId = arg0;
	}

}
