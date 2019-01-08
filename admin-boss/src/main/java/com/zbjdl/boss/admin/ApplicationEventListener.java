package com.zbjdl.boss.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Spring Boot EventListener
 * @author zhengy
 * @date 2016年7月4日 下午4:08:49
 */
public class ApplicationEventListener implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger logger = LoggerFactory
			.getLogger(ApplicationEventListener.class);
	@Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info(event.toString());
	}
}