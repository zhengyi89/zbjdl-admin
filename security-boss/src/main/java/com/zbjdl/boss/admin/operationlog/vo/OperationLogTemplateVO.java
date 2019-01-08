/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.operationlog.vo;

import com.zbjdl.boss.admin.basic.vo.BasicVO;

/**    
 *    
 * 类名称：OperationLogTemplateVO <br>    
 * 类描述：操作日志模板VO<br>
 * @author：feng    
 * @since：2011-7-22 上午11:31:02 
 * @version:     
 *     
 */
public class OperationLogTemplateVO extends BasicVO {

	private static final long serialVersionUID = 3344101259048790400L;
	
	private Long templateID;//模板ID
	
	private Long functionId;//对应功能ID
	
	private String content;//模板内容
	
	private String keyWord;//日志关键字
	
	private String type;

	/**    
	 * templateID    
	 *    
	 * @return  the templateID   
	 */
	
	public Long getTemplateID() {
		return templateID;
	}

	/**    
	 * 描述： 模板ID
	 * @param templateID the templateID to set    
	 */
	public void setTemplateID(Long templateID) {
		this.templateID = templateID;
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

	/**    
	 * type    
	 *    
	 * @return  the type   
	 */
	
	public String getType() {
		return type;
	}

	/**    
	 * 描述： 在这里描述属性的含义
	 * @param type the type to set    
	 */
	public void setType(String type) {
		this.type = type;
	}

}
