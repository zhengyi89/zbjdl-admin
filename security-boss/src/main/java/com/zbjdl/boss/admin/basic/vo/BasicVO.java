/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.basic.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * 类名称：BasicVO <br>
 * 类描述： VO对象基础类<br>
 * 
 * @author：feng
 * @since：2011-7-25 上午10:27:50
 * @version:
 * 
 */
public class BasicVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String errMsg;

	/**
	 * 重写toString方法，显示VO对象里各个字段信息
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
