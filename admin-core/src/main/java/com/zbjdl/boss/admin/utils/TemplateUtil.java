/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.utils;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import com.zbjdl.common.utils.CheckUtils;

/**
 * 
 * 类名称：FillOperationLogUtil <br>
 * 类描述：组装操作日志工具类<br>
 * 
 * @author：feng
 * @since：2011-7-27 下午02:18:08
 * @version:
 * 
 */
public class TemplateUtil {

	private static final String PLACESTART = "[#";

	private static final String PLACEEND = "#]";

	/**
	 * 
	 * 描述： 组装操作日志方法
	 * 
	 * @param template
	 *            模板内容
	 * @param paramMap
	 *            参数Map
	 * @return 操作日志内容
	 */
	public static String fillTemplate(String template, Map<?, ?> paramMap) {
		StringBuilder bd = new StringBuilder(template);
		for (int satrtIndex = bd.indexOf(PLACESTART), endIndex = bd
				.indexOf(PLACEEND); satrtIndex >= 0 && endIndex >= 0; satrtIndex = bd
				.indexOf(PLACESTART), endIndex = bd.indexOf(PLACEEND)) {
			String key = bd.substring(satrtIndex + 2, endIndex);
			// 从参数中获取的有可能是数组，调用String.valueOf打印了对象的地址，因此加以改进
			Object object = paramMap.get(key);
			String value = getStringValue(object);
			if (!CheckUtils.isEmpty(value)) {
				bd.replace(satrtIndex, endIndex + 2, value);
			} else {
				bd.delete(satrtIndex, endIndex + 2);
			}
		}
		return bd.toString();// .replaceAll("[", "").replaceAll("]", "");
	}

	/**
	 * Title：根据参数不同的类型，获取其中的String的字符串值
	 * 
	 * @param obj
	 * @return added by：xuebo.yang
	 */
	public static String getStringValue(Object obj) {
		String message = null;
		if (obj == null) {
			message = "未设置！";
		} else if (obj instanceof String && obj.toString().trim().length() != 0) {
			message = obj.toString();
		} else if (obj.getClass().isArray() && Array.getLength(obj) != 0) {
			String[] input = (String[]) obj;
			message = "[";
			for (int i = 0; i < input.length; i++) {
				message += input[i] + ",";
			}
			message = message.substring(0, message.length() - 1);
			message += "]";
		} else {
			message = obj.toString();
		}
		return message;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		String template = "测试模板，功能:[#functionName#]，说明:[#description#]。";
		Map paramMap = new HashMap();
		String[] obj = { "1", "2", "3", "4" };
		paramMap.put("functionName", "测试功能Name");
		paramMap.put("description", obj);
		System.out.println(fillTemplate(template, paramMap));

	}

}
