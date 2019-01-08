package com.zbjdl.boss.admin.frame.interceptor.springmvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Configuration;
/**
 * 重复提交令牌
 * 
 * @author ZhuTao
 *
 * 下午4:32:42
 */
@Configuration
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public  @interface Token {

	    /*
	     * 既需要检验令牌是否重复，又需要获取更新令牌
	     */
	    boolean validate() default false;
}
