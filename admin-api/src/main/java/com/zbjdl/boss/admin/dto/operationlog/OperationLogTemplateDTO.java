/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.dto.operationlog;

import com.zbjdl.boss.admin.dto.basic.BaseDTO;

/**
 *
 * 类名称：OperationLogTemplateDTO <br>
 * 类描述：操作日志模板DTO<br>
 *
 */
public class OperationLogTemplateDTO extends BaseDTO {

	private static final long serialVersionUID = 3344101259048790400L;

	private Long templateId;//模板ID

	private Long functionId;//对应功能ID

	private String content;//模板内容

	private String keyWord;//日志关键字



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

}
