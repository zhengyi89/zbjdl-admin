/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.dto.basic;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**    
 *    
 * 类名称：BaseDTO <br>    
 * 类描述：DTO接口<br>
 * @version:     
 *     
 */
public abstract class BaseDTO implements Serializable {
	private static final long serialVersionUID = -1283211124138126903L;

	/**
	 * 重写toString使更容易查看对象的属性值
	 */
	public String toString() {
		try {
			return ToStringBuilder.reflectionToString(this,
					ToStringStyle.SHORT_PREFIX_STYLE);
		} catch (Exception e) {
			return super.toString();
		}
	}
}
