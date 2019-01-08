package com.zbjdl.boss.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zbjdl.common.utils.springboot.autoconfigure.dubbo.DubboServicePackageScan;
import com.zbjdl.common.utils.springboot.autoconfigure.dubbo.EnableDubboConfiguration;

//import com.zbjdl.common.utils.springboot.autoconfigure.dubbo.DubboAnnotationConfiguration;
//import com.zbjdl.common.utils.springboot.autoconfigure.dubbo.EnableDubboConfiguration;

//import com.zbjdl.common.utils.springboot.autoconfigure.dubbo.EnableDubboConfiguration;



/**
 * 
 * @author yejiyong
 *
 */
@Controller
@SpringBootApplication

@DubboServicePackageScan(basePackages="com.zbjdl.boss.admin.facade")
@ImportResource("classpath*:/employee-core-spring/employee-appContext.xml")

public class AdminWebApplication {
	private static final Logger logger = LoggerFactory
			.getLogger(AdminWebApplication.class);

    @RequestMapping("/")
    @ResponseBody
	public String home(){
		
        return "Welcome!";
	}
    
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(AdminWebApplication.class);
        springApplication.run(args);
        logger.info("Spring boot loaded");
	}
    @Bean
    public StartupRunner schedulerRunner() {
        return new StartupRunner();
    }	
	@RequestMapping(value = "/test")
	@ResponseBody
	public String test(){
		return "";
	}


}