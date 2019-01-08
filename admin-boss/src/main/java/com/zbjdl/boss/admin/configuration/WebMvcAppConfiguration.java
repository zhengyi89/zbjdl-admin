package com.zbjdl.boss.admin.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.zbjdl.boss.admin.frame.interceptor.springmvc.AuditInterceptor;
import com.zbjdl.boss.admin.frame.interceptor.springmvc.AuthorityInterceptor;
import com.zbjdl.boss.admin.frame.interceptor.springmvc.MenuInterceptor;
import com.zbjdl.boss.admin.frame.interceptor.springmvc.OperationLogInterceptor;
import com.zbjdl.boss.admin.frame.interceptor.springmvc.ParametersInterceptor;
/**
 * Spring MVC Java Config
 * @author yejiyong
 * do not annotate this with @EnableWebMvc, if you want to keep Spring Boots auto configuration for mvc.
 */
@Configuration
public class WebMvcAppConfiguration extends WebMvcConfigurerAdapter{

    /** 
     * 配置拦截器 
     * @author lance 
     * @param registry 
     */  
    public void addInterceptors(InterceptorRegistry registry) {  
    	// If we do not add path pattern for an interceptor, that will be available for every request.
    	
    	registry.addInterceptor(new ParametersInterceptor());  
    	//registry.addInterceptor(new SSOInterceptor()).excludePathPatterns("/loginout/**").excludePathPatterns("/user/**").excludePathPatterns("/captcha/**"); ;  
    	registry.addInterceptor(new MenuInterceptor()).excludePathPatterns("/*").excludePathPatterns("/loginout/**").excludePathPatterns("/user/**").excludePathPatterns("/captcha/**").excludePathPatterns("/api/**"); 
    	registry.addInterceptor(new AuditInterceptor()).excludePathPatterns("/*").excludePathPatterns("/loginout/**").excludePathPatterns("/user/**").excludePathPatterns("/captcha/**").excludePathPatterns("/api/**"); 
    	registry.addInterceptor(new AuthorityInterceptor()).excludePathPatterns("/*").excludePathPatterns("/loginout/**").excludePathPatterns("/user/**").excludePathPatterns("/captcha/**").excludePathPatterns("/api/**");  
        //registry.addInterceptor(new DataTablesHandlerInterceptor());  
        registry.addInterceptor(new OperationLogInterceptor());  
        
    } 
}
