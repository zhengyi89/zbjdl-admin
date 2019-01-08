/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.utils.sso;

import com.zbjdl.common.encrypt.AES;

/**
 * 加密工具类
 * 
 * @author：feng
 * @since：2012-9-25 下午2:50:02
 * @version:
 */
public class SecurityHelper {
	/**
	 * 数据分隔符
	 */
	private static final String DELIMITER = ",";
	

	/**
	 * 加密数据
	 * 
	 * @param datas
	 *            ：待加密数据
	 * @return
	 */
	public static String sign(String... datas) {
		return sign(ConfigHelper.PRIVATE_KEY, datas);
	}

	/**
	 * 解密数据
	 * 
	 * @param signData
	 *            ：待解密数据
	 * @return
	 */
	public static String[] unsign(String signData) {
		return unsign(signData, ConfigHelper.PRIVATE_KEY);
	}
	
	
	
	/**
	 * 产生全局唯一字符串
	 * @return
	 */
	public static  String generateUniqueRandomStr(){
		// 先暂时使用uuid产生全局唯一字符串
		return java.util.UUID.randomUUID().toString();
	}


	/**
	 * 加密数据
	 * 
	 * @param privateKey
	 *            ：私钥
	 * @param datas
	 *            ：待加密数据
	 * @return
	 */
	private static String sign(String privateKey, String... datas) {
		if (null == datas || datas.length < 1) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (String data : datas) {
			sb.append(data);
			sb.append(DELIMITER);
		}
		String unSignData = sb.deleteCharAt(sb.length() - 1).toString();
		return AES.encryptToBase64(unSignData, privateKey);

	}

	/**
	 * 解密数据
	 * 
	 * @param signData
	 *            ：待解密数据
	 * @param privateKey
	 *            ：私钥
	 * @return
	 */
	private static String[] unsign(String signData, String privateKey) {
		if (null == signData)
			return null;
		String data = AES.decryptFromBase64(signData, privateKey);
		return data.split(DELIMITER);
	}

}
