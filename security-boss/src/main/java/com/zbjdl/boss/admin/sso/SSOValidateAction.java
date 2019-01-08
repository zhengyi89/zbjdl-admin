/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.sso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;

import org.apache.struts2.ServletActionContext;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.dto.sso.SSOLoginInfoDTO;
import com.zbjdl.boss.admin.frame.utils.sso.SSOCookieHelper;
import com.zbjdl.boss.admin.frame.utils.sso.SSOValidateHelper;
import com.zbjdl.common.utils.StringUtils;;

/**
 * SSO验证服务
 * 
 * @author：feng
 * @since：2012-9-26 下午5:17:06
 * @version:
 */
public class SSOValidateAction extends EmployeeBossBaseAction {
	private static final long serialVersionUID = 7046708361505317135L;
	/**
	 * token参数
	 */
	private String tokenPair;
	/**
	 * 需要重定向的服务地址
	 */
	private String returnUrl;
	/**已登录信息*/
	private SSOLoginInfoDTO ssoLoginInfo;

	/**
	 * 验证方法
	 */
	@Override
	public String execute() {
		try {
			return goService(); // 尝试访问目标服务地址
		} catch (Exception e) {
			logger.error("sso valid failed ,ready to go login ");
			return goFailedTips();
		}
	}
	/**
	 * 转向错误提示
	 * @return
	 */
	private String goFailedTips() {
		String cookieInfo = SSOCookieHelper.getCookie(ServletActionContext
				.getRequest());
		Collection<SSOLoginInfoDTO> loginInfoList = remoteService
				.getUserLoginFacade().findLoginInfoByUserId(cookieInfo);
		if(loginInfoList != null && !loginInfoList.isEmpty()){
			ssoLoginInfo = loginInfoList.iterator().next();
		}
		encodeReturnUrl();
		return "failTips";
	}
	/**
	 * 加密返回地址
	 */
	private void encodeReturnUrl(){
		if (StringUtils.isNotBlank(returnUrl)) {
			try {
				this.returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
			}
		}
	}
	/**
	 * 去登录页面
	 * @return
	 */
	private String goLogin() {
		// 重定向至服务地址
		encodeReturnUrl();
		return "login";
	}
	/**
	 * 访问目标服务地址
	 * @return
	 */
	private String goService() {
		String cookieInfo = SSOCookieHelper.getCookie(ServletActionContext
				.getRequest());
		if(StringUtils.isNotBlank(cookieInfo)){
			String token = remoteService.getUserLoginFacade()
					.checkSecurityInfo(cookieInfo);
			// 重定向至服务地址
			if (StringUtils.isNotBlank(returnUrl)) {
				this.tokenPair = SSOValidateHelper.getTokenPairWithUrl(returnUrl,
						token);
				return "serviceUrl";
			}
			// 不需要重定向，只要
			this.tokenPair = SSOValidateHelper.getTokenPair(token);
			return SUCCESS;
		}
		return goLogin();
	}

	/**
	 * tokenPair
	 * 
	 * @return the tokenPair
	 */

	public String getTokenPair() {
		return tokenPair;
	}

	/**
	 * returnUrl
	 * 
	 * @return the returnUrl
	 */

	public String getReturnUrl() {
		return returnUrl;
	}

	/**    
	 * @param returnUrl the returnUrl to set    
	 */
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	/**    
	 * ssoLoginInfo    
	 *    
	 * @return  the ssoLoginInfo   
	 */
	
	public SSOLoginInfoDTO getSsoLoginInfo() {
		return ssoLoginInfo;
	}
	
	

}
