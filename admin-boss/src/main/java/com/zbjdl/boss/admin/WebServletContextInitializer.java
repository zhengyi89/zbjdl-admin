package com.zbjdl.boss.admin;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
/**
 * 容器在初始化阶段调用 ServletContextInitializer的onStartup 
 * 可以在此方法中去做一些程序设置
 * 
 * @author zhengy
 * @date 2016年7月4日 下午5:09:32
 */
@Configuration
public class WebServletContextInitializer  implements ServletContextInitializer{
	private static Logger logger = LoggerFactory.getLogger(WebServletContextInitializer.class);
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
	}
	
}
