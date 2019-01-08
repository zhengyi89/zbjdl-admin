package com.zbjdl.boss.admin;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
/**
 * 扩展ServletContextInitializer ，用于作为war包部署在web容器中
 * @author zhengy
 * @date 2016年7月4日 下午4:06:12
 */
public class ServletInitializer extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AdminBossApplication.class);
	}

}
