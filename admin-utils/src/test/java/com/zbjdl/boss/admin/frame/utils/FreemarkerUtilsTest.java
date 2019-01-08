package com.zbjdl.boss.admin.frame.utils;

import java.util.HashMap;
import java.util.Map;

import com.zbjdl.boss.admin.frame.utils.BossFreemarkerUtils;

public class FreemarkerUtilsTest {
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getContextClassLoader()
				.getResource("freemarker/audittemplate.ftl").getPath());

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("message", "this is my message");
		params.put("resourcePath", "http://172.17.102.4");
		String content = BossFreemarkerUtils.getContentFromTemplate(
				"freemarker/audittemplate.ftl", params);
		System.out.println(content);
	}
}
