package com.zbjdl.common.utils.web.struts2;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zbjdl.common.utils.CheckUtils;
import com.zbjdl.common.utils.MessageFormater;
import com.zbjdl.common.utils.config.TextResource;
import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>Title: Struts2 Action基类</p>
 * <p>Description: 所有自定义Struts2 Action 实现该类</p>
 */
public class BaseAction extends ActionSupport {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final long serialVersionUID = -3414974220756659164L;
	
	private static TextResource textResource = new TextResource();
	private static MessageFormater messageFormater = new MessageFormater();

	/**
	 * action中catch到的异常码
	 */
	private List<String> exceptionCodes;
	
	/**
	 * HttpServlet辅助工具类
	 */
	protected MVCFacade mvcFacade;
	
	/**
	 * 
	 * 描述：    由于在构造器中ServletActionContext还没有初始化
	 * 		      将原来在构造器中执行的代码放到get方法中
	 * @return
	 */
	protected MVCFacade getMVCFacade() {
		if (mvcFacade == null)
			mvcFacade = new MVCFacade(ServletActionContext.getRequest(), ServletActionContext.getResponse());
		return mvcFacade;
	}

	public TextResource getTextResource() {
		return textResource;
	}

	public MessageFormater getMessageFormater() {
		return messageFormater;
	}
	
	public List<String> getExceptionCodes() {
		return exceptionCodes;
	}

	public void addExceptionCode(String code){
		if(CheckUtils.isEmpty(code)){
			return;
		}
		if(exceptionCodes==null){
			exceptionCodes = new ArrayList<String>();
		}
		exceptionCodes.add(code);
	}
}