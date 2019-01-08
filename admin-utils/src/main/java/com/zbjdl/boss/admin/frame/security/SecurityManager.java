/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 */
package com.zbjdl.boss.admin.frame.security;

import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.facade.SecurityConfigFacade;
import com.zbjdl.boss.admin.facade.SecurityFacade;
import com.zbjdl.boss.admin.frame.service.impl.RemoteServiceImpl;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.function.dto.MenuModel;
import com.zbjdl.common.utils.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类名称：SecurityManager <br/>
 * 类描述：安全管理器<br/>
 *
 * @author feng feng yufan.sheng
 * @version 1.0.0
 * @since 2015-1-6 下午01:49:39
 */
public class SecurityManager {

	private final static Logger logger = LoggerFactory.getLogger(SecurityManager.class);
	// 为了平滑升级的让步
	private final static String SESSION_USER_FUNCTION_DYNA = "SESSION_USER_FUNCTION_DYNA";

	/**
	 * 安全接口
	 */
	private static SecurityFacade securityFacade = RemoteServiceImpl
			.getInstance().getSecurityFacade();

	private static SecurityConfigFacade securityConfigFacade = RemoteServiceImpl
			.getInstance().getSecurityConfigFacade();

	/**
	 * 静态URI与功能映射缓存
	 */
	private static Map<String, FunctionDTO> functionCache;
	/**
	 * 动态SpringMVC URI缓存
	 */
	private static Map<String, FunctionDTO> dynamicFunctionCache;

	private static ScheduledExecutorService executor;

	static {
		functionCache = new HashMap<String, FunctionDTO>();
		dynamicFunctionCache = new HashMap<String, FunctionDTO>();
		executor = Executors.newSingleThreadScheduledExecutor();
		// 2小时清空一次
		executor.scheduleWithFixedDelay(new Runnable() {
			public void run() {
				functionCache.clear();
				dynamicFunctionCache.clear();
			}
		}, 2, 2, TimeUnit.HOURS);
	}

	/**
	 * 获取本次线程的功能信息，依次去匹配静态、动态地址
	 *
	 * @param request
	 * @return FunctionDTO
	 */
	public static FunctionDTO getThreadFunction(HttpServletRequest request) {
		FunctionDTO function = getStaticUrlFunction(request);
		// 如果静态地址中不存在
		if (function == null) {
			function = getDynamicUrlFunction(request);
		}
		return function;
	}

	/**
	 * 获取本次线程的功能信息 静态地址版 先从缓存中读取 如果没有则远程调用查询URI并缓存
	 *
	 * @param request
	 * @return FunctionDTO
	 */
	private static FunctionDTO getStaticUrlFunction(HttpServletRequest request) {
		FunctionDTO function = functionCache.get(request.getRequestURI().toLowerCase());
		// 如果地址未被缓存
		if (null == function) {
			function = securityFacade.getFunctionByUri(request.getRequestURI());
			if (function != null) {
				functionCache.put(request.getRequestURI().toLowerCase(), function);
			}
		}
		return function;
	}

	/**
	 * 获取本次线程的功能信息 动态地址版 先从动态地址缓存中匹配 如果不存在 则从session中的用户功能列表中去匹配
	 *
	 * @param request
	 * @return FunctionDTO
	 */
	@SuppressWarnings("unchecked")
	private static FunctionDTO getDynamicUrlFunction(HttpServletRequest request) {
		String uri = request.getRequestURI().toLowerCase();
		// 首先遍历动态地址缓存
		for (Map.Entry<String, FunctionDTO> entry : dynamicFunctionCache
				.entrySet()) {
			if (isMatch(entry.getKey(), uri)) {
				return entry.getValue();
			}
		}
		Map<Pattern, Long> dynaFunctionMap = (Map<Pattern, Long>) request
				.getSession().getAttribute(SESSION_USER_FUNCTION_DYNA);
		if (dynaFunctionMap != null) {
			for (Entry<Pattern, Long> p : dynaFunctionMap.entrySet()) {
				Matcher m = p.getKey().matcher(uri);
				if (m.matches()) {
					return securityConfigFacade.getFunctionInfo(p.getValue());
				}
			}
		}
		return null;
	}

	/**
	 * 验证本次线程是否有方法请求地址的权限，同时将本次请求对应的function信息存储至当前线程
	 *
	 * @param userId ：用户ID
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public static boolean checkCurrentThreadPermit(HttpServletRequest request, Long userId) {
		HttpSession session = request.getSession();
		Map<String, Long> functionMap = (Map<String, Long>) session
				.getAttribute(DataDictDTO.SESSION_USER_FUNCTION);
		Map<Pattern, Long> dynaFunctionMap = (Map<Pattern, Long>) session
				.getAttribute(SESSION_USER_FUNCTION_DYNA);

		// 用户第一次登陆Session中无功能缓存，或者请求参数里面需要刷新缓存 构建缓存
		if (functionMap == null || isRefreshCache(request)) {
			session.removeAttribute(DataDictDTO.SESSION_USER_FUNCTION);
			session.removeAttribute(SESSION_USER_FUNCTION_DYNA);
			functionMap = new HashMap<String, Long>();
			dynaFunctionMap = new HashMap<Pattern, Long>();

			List<FunctionDTO> functionList = securityConfigFacade.queryFunctionByUser(userId);
			for (FunctionDTO function : functionList) {
				if (StringUtils.isEmpty(function.getFunctionUrl())) {
					continue;
				}
				String[] urls = function.getFunctionUrl().split(";");
				for (String url : urls) {
					// 处理EMVC和Struts的后缀 URL请求参数 http或者https
					url = url.toLowerCase().trim().replaceAll("http[s]?://[^/]+", "").replaceAll("\\?(.*)$", "").replaceAll("\\.action$", "");
					if (StringUtils.contains(url, "{")) {
						url = url.replaceAll("\\{[^}]+\\}", "[^/]+");
						dynaFunctionMap.put(Pattern.compile(url), function.getFunctionId());
						dynamicFunctionCache.put(url, function);
					} else {
						functionMap.put(url, function.getFunctionId());
						functionCache.put(url, function);
					}
				}
			}
			session.setAttribute(DataDictDTO.SESSION_USER_FUNCTION, functionMap);
			session.setAttribute(SESSION_USER_FUNCTION_DYNA, dynaFunctionMap);
		}
		//某些请求还是会带后缀，匹配的时候需要干掉
		String uri = request.getRequestURI().toLowerCase().replaceAll("\\.action$", "");
		boolean hasPermit = functionMap.containsKey(uri);
		if (!hasPermit && dynaFunctionMap != null) {
			for (Pattern p : dynaFunctionMap.keySet()) {
				Matcher m = p.matcher(uri);
				if (m.matches()) {
					return true;
				}
			}
		}
		return hasPermit;
	}

	/**
	 * 获取用户菜单的方法
	 *
	 * @param userId ：用户ID
	 * @return
	 */
	public static MenuModel getMenuTreeByUserId(HttpServletRequest request,
												Long userId) {
		// 菜单缓存到session中
		HttpSession session = request.getSession();
		MenuModel menuModel = (MenuModel) session
				.getAttribute(DataDictDTO.SESSION_USER_MENU);
		if (menuModel == null || isRefreshCache(request)) {
			menuModel = securityFacade.getMenuTreeByUserId(userId);
			session.setAttribute(DataDictDTO.SESSION_USER_MENU, menuModel);
		}
		return menuModel;
	}

	/**
	 * 待认证的权限的状态为全部赋权
	 *
	 * @param userId           ：用户ID
	 * @param checkAuthorities ：待认证的权限
	 * @return
	 */
	public static boolean checkIfAllGranted(Long userId,
											List<String> checkAuthorities) {
		// 不需要任何验证的，直接返回true
		if (null == checkAuthorities || checkAuthorities.isEmpty()) {
			return true;
		}

		if (checkAuthorities.size() == 1) {
			return securityFacade.checkPermissionByUri(userId,
					checkAuthorities.get(0));
		} else {
			String[] uris = new String[checkAuthorities.size()];
			checkAuthorities.toArray(uris);
			boolean[] resutArray = securityFacade.batchCheckPermission(userId,
					uris);
			boolean ret = true;
			for (boolean resut : resutArray) {
				if (!resut) {
					return false;
				}
			}
			return ret;
		}
	}

	/**
	 * 销毁userId对应的用户认证信息
	 *
	 * @param userId
	 */
	public static void destroyUserSecurityCache(Long userId) {
		securityFacade.destroyUserSecurityCache(userId);
	}

	/**
	 * 是否刷新缓存
	 *
	 * @param request
	 */
	public static boolean isRefreshCache(HttpServletRequest request) {
		return StringUtils.equalsIgnoreCase(request.getParameter("_ccache"),
				"true");
	}

	/**
	 * 根据参数处理缓存
	 *
	 * @param request
	 */
	public static void handleCache(HttpServletRequest request) {
		try {
			if (isRefreshCache(request)) {
				securityFacade.destoryDefaultCache();
				functionCache.clear();
				dynamicFunctionCache.clear();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 动态地址正则匹配
	 *
	 * @param rexp 数据库中存储的正则表达式地址
	 * @param s    请求访问的地址
	 * @return
	 */
	private static boolean isMatch(String rexp, String s) {
		Pattern p = Pattern.compile(rexp);
		Matcher m = p.matcher(s);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}
}