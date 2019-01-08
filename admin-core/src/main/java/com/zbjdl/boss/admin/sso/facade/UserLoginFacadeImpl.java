/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.sso.facade;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.zbjdl.boss.admin.dto.sso.SSOLoginInfoDTO;
import com.zbjdl.boss.admin.facade.UserLoginFacade;
import com.zbjdl.boss.admin.sso.enums.SSOExceptionEnum;
import com.zbjdl.boss.admin.sso.exception.SSOException;
import com.zbjdl.boss.admin.utils.sso.CacheHelper;
import com.zbjdl.boss.admin.utils.sso.SecurityHelper;
import com.zbjdl.common.utils.config.ConfigurationUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 类名称：SSOFacadeImpl <br>
 * 类描述： <br>
 * 
 * @author：feng
 * @since：2011-7-23 下午11:42:25
 * @version:
 * 
 */
public class UserLoginFacadeImpl implements UserLoginFacade {

	private Logger logger = LoggerFactory.getLogger(UserLoginFacade.class);

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.facade.UserLoginFacade#checkSecurityInfo(java.lang.String)    
	 */
	@Override
	public String checkSecurityInfo(String cookieSecurityInfo)
			throws SSOException {
		logger.info("进入checkSecurityInfo方法，入参cookieSecurityInfo：{}", cookieSecurityInfo);
		String[] data = null;
		try {
			data = SecurityHelper.unsign(cookieSecurityInfo);// 解码字符串
			logger.info("解码字符串,data为:{}", JSON.toJSONString(data));
		} catch (Exception e) {
			logger.info("解码字符串失败,安全信息有问题1，data为:{}", data);
			logger.warn("unsign cookie info failed,cookied{}", cookieSecurityInfo);
			throw new SSOException(SSOExceptionEnum.UnsignFailed);// 安全信息有问题
		}
		if (null == data || data.length != 2) {
			logger.info("解码字符串失败,安全信息有问题2，data为:{}", data);
			throw new SSOException(SSOExceptionEnum.UnsignFailed);// 安全信息有问题
		}
		String userId = data[0]; // 登录名
		logger.info("userId:{}", userId);
		String loginId = data[1]; // 登录ID
		logger.info("loginId:{}", loginId);
		if (this.validate(userId, loginId)) {// 已登录，生成登录凭证
			logger.info("已登录，生成登录凭证");
			String token = SecurityHelper.generateUniqueRandomStr();
			logger.info("已登录，token:{}", token);
			SSOLoginInfoDTO ssoLoginInfo = CacheHelper.getLoginInfo(userId, loginId);
			CacheHelper.cachedToken(token, ssoLoginInfo);
			logger.info("create login token {} for user {}", token, userId);
			return token; // 返回登录凭证
		}
		// 登录过期，或已被他人踢出
		logger.info("登录过期，或已被他人踢出");
		throw new SSOException(SSOExceptionEnum.LoginExpire);
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.facade.UserLoginFacade#createTokenAndSecurityInfo(java.lang.String)    
	 */
	@Override
	public String[] createTokenAndSecurityInfo(String userId, String loginIp) {
		SSOLoginInfoDTO sSOLoginInfoDTO = newLoginInfoInstance(userId,loginIp);
		String token = SecurityHelper.generateUniqueRandomStr();
		CacheHelper.cachedToken(token, sSOLoginInfoDTO); // 缓存登录凭证
		if (!ignoreUniqueLoginCheck(userId)) {
			// 不在可忽略的清单里的人员，只有一个用户可登录
			CacheHelper.removeLoginInfo(userId, null);
		}
		CacheHelper.cachedLoginInfo(sSOLoginInfoDTO); // 缓存登录信息实例
		String cookieSecurityInfo = SecurityHelper.sign(userId,
				sSOLoginInfoDTO.getLoginid());
		return new String[] { token, cookieSecurityInfo };
	}
	/**
	 * 创建一个登录信息实例
	 * @param userId
	 * @return
	 */
	private SSOLoginInfoDTO newLoginInfoInstance(String userId,String loginIp){
		SSOLoginInfoDTO sSOLoginInfoDTO = new SSOLoginInfoDTO();
		sSOLoginInfoDTO.setUserid(userId);
		Date now = new Date();
		sSOLoginInfoDTO.setLastAccessTime(now);
		sSOLoginInfoDTO.setLoginTime(now);
		sSOLoginInfoDTO.setLoginid(SecurityHelper.generateUniqueRandomStr());
		sSOLoginInfoDTO.setLoginIp(loginIp);
		return sSOLoginInfoDTO;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.facade.UserLoginFacade#getUserIdByToken(java.lang.String)    
	 */
	@Override
	public SSOLoginInfoDTO getUserIdByToken(String token) throws SSOException {
		SSOLoginInfoDTO sSOLoginInfoDTO = CacheHelper.getLoginInfoByToken(token);
		if(null == sSOLoginInfoDTO){
			throw new SSOException(SSOExceptionEnum.LoginExpire);
		}
		return sSOLoginInfoDTO;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.facade.UserLoginFacade#validate(java.lang.String, java.lang.String)    
	 */
	@Override
	public boolean validate(String userId, String loginId) {
		SSOLoginInfoDTO sSOLoginInfoDTO = CacheHelper.getLoginInfo(userId, loginId);
		if (null == sSOLoginInfoDTO) {
			return false;
		}
		Date expireDate = new Date(sSOLoginInfoDTO.getLastAccessTime()
				.getTime() + (CacheHelper.getLoginInfoCachedTime() * 1000));// 过期时间
		Date now = new Date();
		if (now.compareTo(expireDate) > 0) {// 已过期
			CacheHelper.removeLoginInfo(userId,loginId);
			return false;
		}
		logger.info("valid user login info success,userId {}", userId);
		sSOLoginInfoDTO.setLastAccessTime(now);
		CacheHelper.cachedLoginInfo(sSOLoginInfoDTO);
		return true;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.facade.UserLoginFacade#logout(java.lang.String)    
	 */
	@Override
	public void logoutCookieInfo(String cookieInfoStr) {
		String[] data = null;
		try {
			data = SecurityHelper.unsign(cookieInfoStr);// 解码字符串
		} catch (Exception e) {
			logger.warn("unsign cookie info failed,cookied{}", cookieInfoStr);
			return ;
		}
		if (data.length != 2) {
			return;
		}
		String userId = data[0];
		String loginId =  data[1];
		// 退出登录
		logout(userId, loginId);
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.facade.UserLoginFacade#logout(java.lang.String, java.lang.String)    
	 */
	@Override
	public void logout(String userId, String loginId) {
		CacheHelper.removeLoginInfo(userId, loginId);
	}
	/**
	 * 该用户是否忽略唯一登录检查
	 * @param userId
	 * @return
	 */
	private boolean ignoreUniqueLoginCheck(String userId){
		@SuppressWarnings("unchecked")
		List<String> membersIgnore = (List<String>) ConfigurationUtils
				.getAppConfigParam("admin_boss_ignoreUniqueLoginCheck")
				.getValue();
		if (membersIgnore != null && !membersIgnore.isEmpty()) {
			for (String userName : membersIgnore) {
				if (userName != null && userId.equals(userName.trim())) {
					logger.debug("user ignore unique login check ,userid {}",userId);
					return true;
				}
			}
		}
		return false;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.facade.UserLoginFacade#logoutByUserIdAndLoginId(java.lang.String, java.lang.String)    
	 */
	@Override
	public void logoutByUserIdAndLoginId(String userId, String loginId) {
		logout(userId, loginId);
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.facade.UserLoginFacade#findLoginInfoByUserId(java.lang.String)    
	 */
	@Override
	public Collection<SSOLoginInfoDTO> findLoginInfoByUserId(String cookieInfo) {
		String[] data = null;
		try {
			data = SecurityHelper.unsign(cookieInfo);// 解码字符串
		} catch (Exception e) {
			logger.warn("unsign cookie info failed,cookied{}", cookieInfo);
			return null;
		}
		if (null == data || data.length != 2) {
			return null;
		}
		String userId = data[0]; // 登录名
		return CacheHelper.findLoginInfoByUserId(userId);
	}
	
}
