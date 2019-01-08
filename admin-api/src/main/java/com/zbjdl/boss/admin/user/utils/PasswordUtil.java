/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.utils;

import com.zbjdl.common.encrypt.Digest;

/**    
 *    
 * 类名称：PasswordUtil <br>    
 * 类描述：   密码验证相关工具类<br>
 * @author：feng    
 * @since：2011-6-27 上午10:26:51 
 * @version: 1.0
 *     
 */
public class PasswordUtil {
	
	/**
	 * 
	 * 描述：    验证密码是否正确
	 * @param dbPassword 用户数据库所存密码
	 * @param currentPassword 当前用户登录密码
	 * @param migration 是否是迁移后密码
	 * @return 验证结果
	 */
	public static boolean validatePassword(String dbPassword,
			String currentPassword, boolean migration) {
		if (dbPassword == null || currentPassword == null) {
			return false;
		}
		String passwordEncode = migration ? Digest.shaDigest(currentPassword)
				: Digest.md5Digest(currentPassword);
		if (dbPassword.equals(passwordEncode)) {
			return true;
		}
		return false;
	}
	/**
	 * 描述：密码加密方式
	 * @param currentPassword：待加密的密码
	 * @param migration：是否是迁移后的密码
	 * @return
	 */
	public static String encodePassword(String currentPassword,
			boolean migration) {
		String passwordEncode = migration ? Digest.shaDigest(currentPassword)
				: Digest.md5Digest(currentPassword);
		return passwordEncode;
	}
	
	
	public static void main(String[] args){
		System.out.println(Digest.shaDigest("123qwe!!"));
	}
	
}
