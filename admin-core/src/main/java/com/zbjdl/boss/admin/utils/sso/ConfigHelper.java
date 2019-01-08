/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.utils.sso;

import java.util.ResourceBundle;

/**
 * 配置工具类    
 * @author：feng    
 * @since：2012-9-25 下午3:54:51 
 * @version:   
 */
public class ConfigHelper {
	/**
	 * 私钥配置文件
	 */
	private static final String RESOURCE_FILE = "ssoConfig";
	
	/**
	 * 私钥
	 */
	public  static String PRIVATE_KEY = null;
	
	/**
	 * 单点登录缓存client名
	 */
	public static String SSO_CACHE_CLIENT = null;

	static {
		init();
	}

	/**
	 * 初始化方法
	 */
	private static void init() {
		ResourceBundle rb = ResourceBundle.getBundle(RESOURCE_FILE);
		PRIVATE_KEY = rb.getString("privateKey");
		SSO_CACHE_CLIENT = rb.getString("ssoClient");
	}
	
	
}
