/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.utils.sso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zbjdl.boss.admin.dto.sso.SSOLoginInfoDTO;
import com.zbjdl.common.utils.cache.remote.RemoteCacheUtils;
import com.zbjdl.common.utils.config.ConfigParam;
import com.zbjdl.common.utils.config.ConfigurationUtils;
import com.zbjdl.common.utils.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存工具类
 * 
 * @author：feng
 * @since：2012-9-25 下午4:04:16
 * @version:
 */
public class CacheHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(CacheHelper.class);
	/**
	 * 用户Key前缀
	 */
	private static final String USER_PREFIX = "UP_";

	/**
	 * token前缀
	 */
	private static final String TOKEN_PREFIX = "TP_";

	/**
	 * 一次性token存活期最长五分钟
	 */
	private static int TOKEN_LIVE = 60 * 5;
	/**
	 * 默认登录信息缓存时间半小时
	 */
	private static int DEFAULT_LOGIN_INFO_LIVE = 60 * 30;
	/**
	 * 登录信息缓存时常参数
	 */
	private static final String LOGIN_INFO_LIVE_PARAM = "loginInfo-cached-time";

	/**
	 * 使用一次性token获取用户登录信息
	 * 
	 * @param token
	 *            ：一次性使用token
	 * @return
	 */
	public static SSOLoginInfoDTO getLoginInfoByToken(String token) {
		String key = TOKEN_PREFIX + token;
		SSOLoginInfoDTO ssoLoginInfo = getData(SSOLoginInfoDTO.class, key);
		removeData(key);
		return ssoLoginInfo;
	}

	/**
	 * 缓存token
	 * 
	 * @param token
	 *            ：一次性使用token
	 * @param ssoLoginInfo
	 *            ：用户登录信息
	 */
	public static void cachedToken(String token, SSOLoginInfoDTO ssoLoginInfo) {
		String key = TOKEN_PREFIX + token;
		putData(key, ssoLoginInfo, TOKEN_LIVE);
	}

	/**
	 * 根据获取登录信息
	 * 
	 * @param userId
	 * @param loginId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static SSOLoginInfoDTO getLoginInfo(String userId, String loginId) {
		String key = USER_PREFIX + userId;
		logger.info("####getLoginInfo key:"+key+"loginId:"+loginId);

		Map<String, Map<String,Object>> data = getData(HashMap.class, key);
		logger.info("####data:"+data);

		if (data != null) {
			Map<String,Object> info =  (Map<String,Object>)data.get(loginId);
			if(info !=null ){
				SSOLoginInfoDTO loginInfoDTO = convertToLoginInfoDTO(info);
				return loginInfoDTO;
			}
//			else{
//				logger.info("#### info is null,removeData ,key:"+key);
//				removeData(key);
//			}
		}
		return null;
	}

	private static SSOLoginInfoDTO convertToLoginInfoDTO(Map<String, Object> info) {
		Long strlastAccessTime = (Long)info.get("lastAccessTime");
		Long strLoginTime = (Long)info.get("loginTime");
		SSOLoginInfoDTO loginInfoDTO = new SSOLoginInfoDTO();
		loginInfoDTO.setLoginid((String)info.get("loginid"));
		loginInfoDTO.setUserid((String)info.get("userid"));
		loginInfoDTO.setLastAccessTime(new Date(strlastAccessTime));
		loginInfoDTO.setLoginTime(new Date(strLoginTime));
		loginInfoDTO.setLoginIp((String)info.get("loginIp"));
		return loginInfoDTO;
	}

	/**
	 * 获取缓存时常
	 * 
	 * @return
	 */
	public static int getLoginInfoCachedTime() {
		@SuppressWarnings("unchecked")
		ConfigParam<String> param = ConfigurationUtils
				.getAppConfigParam(LOGIN_INFO_LIVE_PARAM);
		int liveTime = DEFAULT_LOGIN_INFO_LIVE;
		if (param != null && !StringUtils.isBlank(param.getValue())) {
			liveTime = Integer.parseInt(param.getValue().trim());
		}
		return liveTime;
	}

	/**
	 * 缓存登录信息
	 * 
	 * @param loginInfo
	 *            ：登录信息
	 */
	public static void cachedLoginInfo(SSOLoginInfoDTO loginInfo) {
		String key = USER_PREFIX + loginInfo.getUserid();
		@SuppressWarnings("unchecked")
		Map<String, SSOLoginInfoDTO> data = getData(HashMap.class, key);
		if (null == data) {
			data = new HashMap<String, SSOLoginInfoDTO>();
		}
		data.put(loginInfo.getLoginid(), loginInfo);
		putData(key, data, getLoginInfoCachedTime());
	}

	/**
	 * 删除登录信息
	 * 
	 * @param userId
	 * @param loginId
	 */
	public static void removeLoginInfo(String userId, String loginId) {
		String key = USER_PREFIX + userId;
		if (StringUtils.isBlank(loginId)) {
			removeData(key);
		} else {
			@SuppressWarnings("unchecked")
			Map<String, SSOLoginInfoDTO> data = getData(HashMap.class, key);
			if(data != null){
				data.remove(loginId);
				if (data.size() > 0) {
					putData(key, data, getLoginInfoCachedTime());
				} else {
					removeData(key);
				}
			}
		}
	}
	/**
	 * 查询一个用户下的登录ID
	 * 
	 * @param userId
	 *            ：登录信息
	 * @return
	 */
	public static Collection<SSOLoginInfoDTO> findLoginInfoByUserId(String userId) {
		String key = USER_PREFIX + userId;
		@SuppressWarnings("unchecked")
//		Map<String, SSOLoginInfoDTO> data = getData(HashMap.class, key);
//		if(data != null){
//			return data.values();
//		}
//		return null;

		
		Map<String, Map<String,Object>> data = getData(HashMap.class, key);
		logger.info("####data:"+data);
		
		if (data != null) {
			Collection<Map<String,Object>> collection = data.values();
			
			Map<String,Object> info =  collection.iterator().next();
			if(info !=null ){
				SSOLoginInfoDTO loginInfoDTO = convertToLoginInfoDTO(info);
				List<SSOLoginInfoDTO> list =  new ArrayList<SSOLoginInfoDTO>();
				list.add(loginInfoDTO);
				return list;
			}
		}		
		return null;
	}

	/**
	 * 缓存数据
	 * 
	 * @param key
	 * @param value
	 * @param timeToLive
	 */
	private static void putData(String key, Object value, int timeToLive) {
		RemoteCacheUtils.putClient(ConfigHelper.SSO_CACHE_CLIENT, key, value,
				timeToLive);
	}

	/**
	 * 获取缓存数据
	 * 
	 * @param clazz
	 * @param key
	 * @return
	 */
	private static <T> T getData(Class<T> clazz, String key) {
		try {
			return (T) RemoteCacheUtils.getClient(ConfigHelper.SSO_CACHE_CLIENT,
					clazz, key);
		} catch (Exception e) {
			logger.error("fail to get sso login info",e);
			removeData(key);
			return null;
		}
	}

	/**
	 * 删除缓存数据
	 * 
	 * @param key
	 */
	private static void removeData(String key) {
		RemoteCacheUtils.removeClient(ConfigHelper.SSO_CACHE_CLIENT, key);
	}

	/**
	 * 清空所有缓存数据
	 */
	public static void clearCache() {
		RemoteCacheUtils.clearClient(ConfigHelper.SSO_CACHE_CLIENT);
	}

}
