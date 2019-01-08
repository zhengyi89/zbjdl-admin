/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.frame.tag.component;

import java.io.Writer;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;
import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.frame.service.RemoteService;
import com.zbjdl.boss.admin.frame.service.impl.RemoteServiceImpl;
import com.zbjdl.boss.admin.user.dto.UserDTO;

/**    
 *    
 * 类名称：AuthorityComponent <br>    
 * 类描述：权限标签对应的Component<br>
 * @author：feng    
 * @since：2011-9-13 下午02:57:43 
 * @version:     
 *     
 */
public class AuthorityIfComponent extends Component {
	
	private RemoteService bossRemoteCallService = RemoteServiceImpl.getInstance();
	   
	/**    
	 * 创建一个新的实例 AuthorityComponent.    
	 *    
	 * @param stack    
	 */
	public AuthorityIfComponent(ValueStack stack) {
		super(stack);
	}
	
	/**
	 * 根据url属性判断当前登录用户是否有相应权限
	 */
	private String url;
	
	private Boolean answer = Boolean.FALSE;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/* (non-Javadoc)    
	 * @see org.apache.struts2.components.Component#start(java.io.Writer)    
	 */
	@Override
	public boolean start(Writer writer) {
		if (this.answer == null) {
			this.answer = Boolean.FALSE;
		}
		
		// 读取session，检查是否存在用户信息的值
		Object userDTO = ServletActionContext.getRequest().getSession().getAttribute(DataDictDTO.SESSION_USERINFO);
		if (userDTO == null) {
			this.answer = Boolean.FALSE;
		} else if (url == null) {
			this.answer = Boolean.FALSE;
		} else {
			Long userId = ((UserDTO) userDTO).getUserId();
			// 判断是否有url权限
			this.answer = bossRemoteCallService.getSecurityFacade().checkPermissionByUri(userId, url);
		}
		
		this.stack.getContext().put(AuthorityElseComponent.AUTHORITY_KEY, this.answer);
		return this.answer.booleanValue();
	}

}
