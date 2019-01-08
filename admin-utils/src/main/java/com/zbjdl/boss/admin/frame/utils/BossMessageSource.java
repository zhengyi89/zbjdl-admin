package com.zbjdl.boss.admin.frame.utils;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.MessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import com.zbjdl.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 资源文件工具类(数据字典)
 *  所有的message以LinkedHashMap方式定义在spring配置文件中
 * 配置文件位于classpath路径下的，不能使用reload方法来重新载入
 * 配置文件位于文件系统路径下的，可以reload，每次调用reload将会重新读取文件
 * 以下几种方式获取message:
 * 1.public Map get(String beanName)直接返回Map,如果map不存在则返回NULL
 * 2.public Map get(String beanName,String defaultKey, String defaultValue)方法
 * 会在返回的Map前加入一个默认的key-value-entry，在select标签需要有默认值的情况下使用
 * 3.public String getMessage(String beanName, String key)直接返回MAP中的某个VALUE，如果MAP不存在或key不存在则返回NULL
 * 4.public String getMessage(String beanName, String keyString defaultMessage, Object...args)可以使用defaultMessage代替返回NULL的情况
 * 并且会使用java.text.MessageFormat对信息进行格式化，args为格式化参数
 * 5.public String getFileMessage(String beanName, String key, Object...
 * args)读取文件转换为String，进行格式化后返回 该方法对应的map中value为文件路径
 * @author feng
 *
 */
public class BossMessageSource implements InitializingBean{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(MessageSource.class);

	private DefaultListableBeanFactory factory;
	public static final String FORMAT_BEAN = "format";
	private String[] config;
	ResourceLoader resourceLoader = new DefaultResourceLoader();
	public static final String ADVICE_MESSAGES = "adviceMessages";
	public static final String ADVICES = "advices";
	public static final String PROMPT_MESSAGES= "promptMessages";
	public static final String ERROR_MESSAGES= "errorMessages";

	public String getPromptMessage(String key, Object...obj){
		return getMessage(PROMPT_MESSAGES, key, "",obj);
	}

	public String getErrorMessage(String key, Object...obj){
		return getMessage(ERROR_MESSAGES, key, "",obj);
	}

	public String getAdviceMessage(String type, String key,Object... reqs){
		String name = getAdviceName(key);
		Object[] objs = null;
		if(reqs==null || reqs.length==0){
			objs = new Object[]{name};
		}else{
			objs = new Object[reqs.length+1];
			objs[0] = name;
			System.arraycopy(reqs, 0, objs, 1, reqs.length);
		}
		return getMessage(ADVICE_MESSAGES, type, "",objs);
	}

	public String getAdviceName(String key){
		return getMessage(ADVICES, key, "");
	}

	public void setConfig(String[] config) {
		this.config = config;
	}

	public Object getBean(String beanName){
		return factory.getBean(beanName);
	}

	@SuppressWarnings("rawtypes")
	public Map get(String beanName) {
		Object bean = factory.getBean(beanName);
		if (bean != null && bean instanceof Map) {
			return (Map) bean;
		}
		logger.info("no bean named["+beanName+"]");
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map get(String beanName, String defaultKey, String defaultValue) {
		Map map = get(beanName);
		if (map != null) {
			LinkedHashMap result = new LinkedHashMap();
			result.put(defaultKey, defaultValue);
			result.putAll(map);
			return result;
		}
		return null;
	}

	public String getMessage(String beanName, String key, String defaultMessage){
		String result = getMessage(beanName, key);
		if(result == null){
			return defaultMessage;
		}
		return result;
	}

	public String getMessage(String beanName, String key,
			String defaultMessage, Object... args) {
		String message = getMessage(beanName, key);
		message = format(message, args);
		if (message == null) {
			return defaultMessage;
		}
		return message;
	}
	@SuppressWarnings("rawtypes")
	public String getMessage(String beanName, String key) {
		Map map = get(beanName);
		if (map != null) {
			Object value = map.get(key);
			if (value != null) {
				return value.toString();
			}
			logger.debug("no value for key["+key+"]");
		}
		return null;
	}

	private String format(String message, Object... args) {
		if (message == null) {
			return null;
		}
		if (args == null || args.length == 0) {
			return message;
		}
		return MessageFormat.format(message, args);
	}

	public String formatMessage(String key, Object... args) {
		if(args == null || args.length==0){
			return "";
		}
		boolean flag = true;
		for(Object o : args){
			if(o!=null){
				flag = false;
				break;
			}
		}
		if(flag){
			return "";
		}
		String message = getMessage(FORMAT_BEAN, key);
		return format(message, args);
	}

	public boolean reload(){
		Exception ex = null;
		try {
			if (factory != null) {
				((ConfigurableBeanFactory) factory).destroySingletons();
				factory = null;
			}
			factory = new DefaultListableBeanFactory();
			XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(
					factory);
			reader.setResourceLoader(resourceLoader);
			reader.loadBeanDefinitions(config);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ex = e;
		}

		if(ex!=null){
			return false;
		}
		return true;
	}

	public void afterPropertiesSet() throws Exception {
		try{
			reload();
		}catch(Exception e){
			logger.error("init messageSource fail!!!!!!!!!!!!!!!!!!!!!!!!");
			logger.error(e.getMessage(), e);
		}
	}

	public String getSeperatedMessages(String group, String key,String defaultMessage){
		return getSeperatedMessages(group, key, ",", " ",defaultMessage, -1);
	}

	public String getSeperatedMessages(String group, String key,String defaultMessage, int maxLength){
		return getSeperatedMessages(group, key, ",", " ",defaultMessage, maxLength);
	}
	@SuppressWarnings("rawtypes")
	public String getSeperatedMessages(String group, String key, String seperator, String outputseperator, String defaultMessage, int maxLength, Object... args){
		Map map = get(group);
		if (map == null || StringUtils.isBlank(key)) {
			return defaultMessage;
		}
		String result = "";
		String[] keys = key.split(seperator);
		for(String k:keys){
			result = result + getMessage(group, k,"",args) +outputseperator;
		}
		if(StringUtils.isBlank(result)){
			return defaultMessage;
		}else{
			result = result.substring(0, result.length()-outputseperator.length());
		}
		if(maxLength>0 && result.length()>maxLength){
			result = result.substring(0,maxLength)+"...";
		}
		return result;
	}

	public static void main(String[] args){
		String[] reqs = new String[]{"a","b"};
		String[] objs = new String[reqs.length+1];
		objs[0] = "ddd";
		System.arraycopy(reqs, 0, objs, 1, reqs.length);
		for(String s:objs){
			System.out.println(s);
		}
	}
}