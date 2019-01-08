/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.utils;

/**    
 *    
 * 类名称：StringToBooleanUtil <br>    
 * 类描述：字符串转为布尔值的工具类<br>
 * @author：feng    
 * @since：2011-6-22 下午04:39:03 
 * @version:     
 *     
 */
public class StringBooleanUtil {
	
	private static final String TRUE_VALUE = "1";
	
	private static final String FALSE_VALUE = "0";
	
	/**
	 * 
	 * 描述：    把字符串转为布尔值
	 * @param value
	 * @return
	 */
	public static boolean stringToBoolean(String value) {
		if (value == null){
			return false;
		}
		if (TRUE_VALUE.equals(value)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * 描述：    把布尔值转为字符串
	 * @param value
	 * @return
	 */
	public static String booleanToString(Boolean value) {
		if (value == null){
			return null;
		}
		if (value) {
			return TRUE_VALUE;
		}
		else {
			return FALSE_VALUE;
		}
	}

}
