/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.frame.utils.sso;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * SSO页面操作工具类
 *
 * @author：feng
 * @since：2012-9-28 上午11:21:23
 * @version:
 */
public class SSOWebHelper {

	private static Logger logger = LoggerFactory.getLogger(SSOWebHelper.class);

	/**
	 * 重定向
	 *
	 * @param res
	 *            ：
	 * @param url
	 */
	public static void sendRedirect(HttpServletResponse res, String url) {
		try {
			res.sendRedirect(url);
		} catch (IOException e) {
		}
	}

	/**
	 * 根据FreeMarker模板和键值对的参数生成字符串，如果生成过程中，出现IOException或TemplateException异常，
	 * 则返回null
	 *
	 * @param args
	 * @param templateName
	 * @param reader
	 * @param ENCODING
	 * @return
	 */
	public static String getContentFromTemplate(String templateName,
			Map<String, Object> params) {
		Writer writer = null;
		Reader reader = null;
		InputStream in = null;
		try {
			in = SSOWebHelper.class.getClassLoader().getResourceAsStream(
					templateName);
			reader = new InputStreamReader(in, SSOCodeConst.DEFAULT_ENCODING);
			Template template = new Template(templateName, reader,
					new Configuration(), SSOCodeConst.DEFAULT_ENCODING);
			writer = new StringWriter();
			template.process(params, writer);
			writer.flush();
			return writer.toString();

		} catch (Exception e) {
			logger.error("load freemarker template for sso faile.", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("inputStream关闭失败", e);
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("reader关闭失败", e);
				}
			}

			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error("writer关闭失败", e);
				}
			}
		}
		return null;
	}

	/**
	 * 将请求转化为查询字符串
	 *
	 * @param req
	 *            ：http请求
	 * @param charSet
	 *            ：字符编码
	 * @param filterParam
	 *            ：过滤参数
	 * @return
	 */
	public static String toQueryString(HttpServletRequest req, String charSet,
			String... filterParam) {
		StringBuilder sb = new StringBuilder();
		Map<?, ?> parameters = req.getParameterMap();
		if (parameters != null && !parameters.isEmpty()) {
			Set<String> filterParamSet = new HashSet<String>();
			if (filterParam != null && filterParam.length > 0) {
				for (String param : filterParam) {
					filterParamSet.add(param);
				}
			}
			Enumeration<?> paramNames = req.getParameterNames();
			while (paramNames.hasMoreElements()) {
				String paramName = paramNames.nextElement().toString();
				if (filterParamSet.contains(paramName)) {
					continue; // 过滤跳过参数
				}
				String[] values = req.getParameterValues(paramName);
				if (values != null) {
					for (Object v : values) {
						try {
							sb.append(paramName)
									.append("=")
									.append(URLEncoder.encode(v == null ? ""
											: v.toString(), charSet))
									.append("&");
						} catch (UnsupportedEncodingException e) {
						}
					}
				}
			}
			if (sb.length() > 0) {
				sb = sb.deleteCharAt(sb.length() - 1);
			}
		}
		StringBuilder queryStringBuilder = new StringBuilder(req
				.getRequestURL().toString());
		if (sb.length() > 0) {
			queryStringBuilder.append("?");
			queryStringBuilder.append(sb.toString());
		}
		return queryStringBuilder.toString();

	}
}
