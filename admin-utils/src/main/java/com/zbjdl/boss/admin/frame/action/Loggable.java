/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.frame.action;

import java.util.Map;


/**    
 *    
 * 类名称：Loggable <br>    
 * 类描述：记录操作日志接口<br>
 * @author：feng    
 * @since：2011-4-26 上午09:37:12 
 * @version:     
 *     
 */
public interface Loggable {
	
	/**
	 * 
	 * 描述：    获取日志关键字
	 * @return
	 */
	public String getKeyWord();
	
	public Map getLogParams();
	
}
