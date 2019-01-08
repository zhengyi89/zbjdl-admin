/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.frame.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;
import com.zbjdl.boss.admin.frame.tag.component.AuthorityElseComponent;

/**    
 *    
 * 类名称：AuthorityTag <br>    
 * 类描述：运营后台权限标签<br>
 * @author：feng    
 * @since：2011-9-13 下午02:52:38 
 * @version:     
 *     
 */
public class AuthorityElseTag extends ComponentTagSupport {
	
	private static final long serialVersionUID = 6855757782050866813L;
	
	/* (non-Javadoc)    
	 * @see org.apache.struts2.views.jsp.ComponentTagSupport#getBean(com.opensymphony.xwork2.util.ValueStack, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)    
	 */
	@Override
	public Component getBean(ValueStack arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
		return new AuthorityElseComponent(arg0);
	}

}
