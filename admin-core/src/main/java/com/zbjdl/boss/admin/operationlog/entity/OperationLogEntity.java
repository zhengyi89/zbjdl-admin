/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.operationlog.entity;

import java.util.Date;

import com.zbjdl.common.persistence.Entity;

/**    
 *    
 * 类名称：OperationLog <br>    
 * 类描述：操作日志实体<br>
 * @author：feng    
 * @since：2011-5-16 下午02:08:03 
 * @version:     
 *     
 */
public class OperationLogEntity implements Entity<Long> {

	private static final long serialVersionUID = 5135800023202894512L;
	
	private Long operationLogID;//ID
	
	private String logContent;//日志内容
	
	private String keyWord;//日志关键字
	
	private Date logTime;//记录日志的时间

	   
	/**    
	 * 创建一个新的实例 OperationLog.    
	 *    
	 * @param logContent
	 * @param keyWord
	 * @param logTime    
	 */
	public OperationLogEntity(String logContent, String keyWord, Date logTime) {
		this.logContent = logContent;
		this.keyWord = keyWord;
		this.logTime = logTime;
	}
	
	/**    
	 * 创建一个新的实例 OperationLog.    
	 */
	public OperationLogEntity() {}

	/**    
	 * operationLogID    
	 *    
	 * @return  the operationLogID   
	 */
	
	public Long getOperationLogID() {
		return operationLogID;
	}

	/**    
	 * 描述： 日志ID
	 * @param operationLogID the operationLogID to set    
	 */
	public void setOperationLogID(Long operationLogID) {
		this.operationLogID = operationLogID;
	}

	/**    
	 * logContent    
	 *    
	 * @return  the logContent   
	 */
	
	public String getLogContent() {
		return logContent;
	}

	/**    
	 * 描述： 日志内容
	 * @param logContent the logContent to set    
	 */
	public void setLogContent(String logContent) {
		this.logContent = logContent;
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
	 * 描述： 关键字
	 * @param keyWord the keyWord to set    
	 */
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	/**    
	 * logTime    
	 *    
	 * @return  the logTime   
	 */
	
	public Date getLogTime() {
		return logTime;
	}

	/**    
	 * 描述： 记日志时间
	 * @param logTime the logTime to set    
	 */
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.common.persistence.Entity#getId()    
	 */
	@Override
	public Long getId() {
		return operationLogID;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.common.persistence.Entity#setId(java.io.Serializable)    
	 */
	@Override
	public void setId(Long arg0) {
		this.operationLogID = arg0;
	}

}
