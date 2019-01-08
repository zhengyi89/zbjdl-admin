package com.zbjdl.boss.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author zhengy
 * @date 2016年7月4日 下午3:50:30
 */
@Controller
@SpringBootApplication
@ImportResource({"classpath:boss-spring/applicationContext-consumer.xml"})
public class AdminBossApplication  {
	private static final Logger logger = LoggerFactory
			.getLogger(AdminBossApplication.class);
    
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(AdminBossApplication.class);
        springApplication.addListeners(new ApplicationEventListener());
        springApplication.run(args);
        logger.info("Spring boot loaded");
	}
	
	@RequestMapping(value = "/test")
	@ResponseBody
	public String test(){
		return "test";
	}

}
