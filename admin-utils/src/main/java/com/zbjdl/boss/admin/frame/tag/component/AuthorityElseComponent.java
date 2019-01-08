/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.frame.tag.component;

import java.io.Writer;
import java.util.Map;

import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

/**    
 *    
 * 类名称：AuthorityElseComponent <br>    
 * 类描述：权限标签对应的Component<br>
 * @author：feng    
 * @since：2011-9-13 下午02:57:43 
 * @version:     
 *     
 */
public class AuthorityElseComponent extends Component {
	
	public static final String AUTHORITY_KEY = "boss.authorityif.answer";

	/**    
	 * 创建一个新的实例 AuthorityComponent.    
	 *    
	 * @param stack    
	 */
	public AuthorityElseComponent(ValueStack stack) {
		super(stack);
	}
	
	/* (non-Javadoc)    
	 * @see org.apache.struts2.components.Component#start(java.io.Writer)    
	 */
	@Override
	public boolean start(Writer writer) {
		@SuppressWarnings("rawtypes")
		Map context = this.stack.getContext();
		Boolean ifResult = (Boolean)context.get(AUTHORITY_KEY);
		context.remove(AUTHORITY_KEY);
		return ((ifResult != null) && (!(ifResult.booleanValue())));
	}

}
