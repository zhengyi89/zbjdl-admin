package com.zbjdl.boss.admin.frame.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class BossFreemarkerUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(BossFreemarkerUtils.class);
	private static final String ENCODING = "utf-8";

	/**
	 * 根据FreeMarker模板和键值对的参数生成字符串，如果生成过程中，出现IOException或TemplateException异常，
	 * 则返回null
	 * 
	 * @param templateName
	 * @param params
	 * @return
	 */
	public static String getContentFromTemplate(String templateName,
			Map<String, Object> params) {
		Writer writer = null;
		Reader reader = null;
		InputStream in = null;
		try {
			in = BossFreemarkerUtils.class.getClassLoader()
					.getResourceAsStream(templateName);
			reader = new InputStreamReader(in, ENCODING);
			Template template = new Template(templateName, reader,
					new Configuration(), ENCODING);
			writer = new StringWriter();
			template.process(params, writer);
			writer.flush();
			return writer.toString();
		} catch (IOException e) {
			logger.error("{}", e);
		} catch (TemplateException e) {
			logger.error("{}", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("inputStream关闭失败, {}", e);
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("reader关闭失败, {}", e);
				}
			}

			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error("writer关闭失败, {}", e);
				}
			}
		}
		return null;
	}

}
