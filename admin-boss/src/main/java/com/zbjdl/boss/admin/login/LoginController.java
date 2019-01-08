/**
 * 
 */
package com.zbjdl.boss.admin.login;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.dto.sso.SSOLoginInfoDTO;
import com.zbjdl.boss.admin.frame.security.SecurityManager;
import com.zbjdl.boss.admin.frame.service.RemoteService;
import com.zbjdl.boss.admin.frame.service.impl.RemoteServiceImpl;
import com.zbjdl.boss.admin.frame.utils.BossWebUtils;
import com.zbjdl.boss.admin.frame.utils.sso.SSOCookieHelper;
import com.zbjdl.boss.admin.frame.utils.sso.SSOSessionHelper;
import com.zbjdl.boss.admin.frame.utils.sso.SSOValidateHelper;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.exception.UserAuthenticationException;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;
import com.zbjdl.common.utils.IpUtils;
import com.zbjdl.common.utils.StringUtils;

/**
 * @author zhengy
 * @date 2016年7月4日 下午4:41:47
 */
@Controller
@RequestMapping("/loginout")
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	private RemoteService remoteService = RemoteServiceImpl.getInstance();
	
	/**
	 * 登陆页面
	 */
	@RequestMapping(value = "/showLogin")
	public ModelAndView execute(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("login/login");
		String returnUrl = request.getParameter("returnUrl");
		logger.info("returnUrl:{}", returnUrl);
		mav.addObject("returnUrl", returnUrl);
		mav.addObject("isShowCode", "false");
		return mav;
	}
	
	/**
	 * 登陆
	 * @return
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		String code1 = (String) session.getAttribute("SecurityCode");
		String isShowCode = request.getParameter("isShowCode");
		String code = request.getParameter("code");
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		
		if ("true".equals(isShowCode) && (code == null || code1 == null || !code1.equalsIgnoreCase(code))) {
			logger.info("验证码错误: 系统生产验证码为[" + code1 + "], 输入验证码为: [" + code + "]");
			session.invalidate();
			mav.addObject("errorMessage", "提示：验证码错误。");
			mav.addObject("isShowCode", "true");
			mav.setViewName("login/login");
			mav.addObject("loginName", loginName);
			logger.info("跳转页面1------->>>>>");
			return mav;
		}

		
		UserDTO userDTO;
		try {
			userDTO = remoteService.getUserFacade().userLoginValidate(loginName, password);
			logger.info("userDTO:{}", JSON.toJSONString(userDTO));
		} catch (UserAuthenticationException e) {
			if ("004".equals(e.getDefineCode())) {
				mav.addObject("errorMessage", "提示：用户不存在。");
			} else if ("003".equals(e.getDefineCode())) {
				mav.addObject("errorMessage", "提示：用户已冻结。");
			} else {
				mav.addObject("errorMessage", "提示：密码错误。");
			}
			mav.addObject("isShowCode", "true");
			mav.addObject("loginName", loginName);
			mav.setViewName("login/login");
			logger.info("跳转页面2------->>>>>");
			return mav;
		}

		userDTO.setPassword(password);
		// 设置 session 为了记录操作日志
		UserInfoUtils.setUserToSession(session, userDTO);

		// 未点击退出直接切换用户时，根据cookie清空之前的登录缓存
		String cookieLoingInfo = SSOCookieHelper.getCookie(request);
		logger.info("cookieLoingInfo:{}", cookieLoingInfo);
		if (StringUtils.isNotBlank(cookieLoingInfo)) {
			remoteService.getUserLoginFacade().logoutCookieInfo(cookieLoingInfo);
		}

		// 单点登录改造，seesion 里不再存储内容
		String[] data = remoteService.getUserLoginFacade()
				.createTokenAndSecurityInfo(userDTO.getLoginName(),IpUtils.getIpAddr(request));
		logger.info("data:{}", JSON.toJSONString(data));
		String token = data[0]; // 一次性token
		String cookieInfo = data[1]; // cookie信息
		SSOCookieHelper.setCookie(response, cookieInfo,BossWebUtils.getDomain(request));
		// 销毁之前的缓存，避免忘记点退出，权限变化无法
		com.zbjdl.boss.admin.frame.security.SecurityManager.destroyUserSecurityCache(userDTO.getUserId());

		String tokenPair = SSOValidateHelper.getTokenPair(token);
		logger.info("tokenPair:{}", tokenPair);
		if ("1".equals(userDTO.getPwdShowNotice()) && userDTO.getPwdLastModifiedTime().getTime() <= new Date().getTime() - 7776000000L) {
			//return "pwdModify";
			mav.setViewName("redirect:/user/showModifyPwd?decoration=false&"+tokenPair);
			logger.info("跳转页面3------->>>>>");
			return mav;
		}
		
		String returnUrl = request.getParameter("returnUrl");
		logger.info("returnUrl:{}", returnUrl);
		boolean toPortal = this.isPortalUser(userDTO);
		logger.info("toPortal:{}", toPortal);
		if (!toPortal && StringUtils.isNotBlank(returnUrl)) {
			logger.info("redirect to service url: {}.", returnUrl);
			tokenPair = SSOValidateHelper.getTokenPairWithUrl(returnUrl, token);
			//return "serviceUrl";
			mav.setViewName("redirect:"+tokenPair);
			logger.info("跳转页面4,tokenPair:{}", tokenPair);
			logger.info("跳转页面4------->>>>>");
			return mav;
		}
		if (toPortal) {
			//return "portal";
			mav.setViewName("redirect:/menu/customerPortal?decoration=false&"+tokenPair);
			logger.info("跳转页面5------->>>>>");
			return mav;
		}
		//return "success";
		mav.setViewName("redirect:/menu/showMenu?"+tokenPair);
		logger.info("跳转页面6------->>>>>");
		return mav;
	}
	
	@RequestMapping(value = "/logout", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView("login/login");
		try {
			//  单点登录改造
			remoteService.getUserLoginFacade()
					.logoutCookieInfo(SSOCookieHelper.getCookie(request));
			SSOCookieHelper.removeCookie(request,response);

			// 清除session
			HttpSession session = request.getSession();
			if (session != null) {
				UserDTO userDTO = (UserDTO) session
						.getAttribute(DataDictDTO.SESSION_USERINFO);
				if (userDTO != null) {
					SecurityManager.destroyUserSecurityCache(userDTO
							.getUserId());
				}
				SSOSessionHelper.clearSession(session);
				session.invalidate();
			}
		} catch (Exception e) {
			// 出现异常也不往外抛出，否则会到异常页面而不是登录页
			logger.error("退出系统时出现异常：", e);
		}
		return mav;
	}

	/**
	 * 是否门户模式，默认为false
	 *
	 * @return true/false
	 */
	private boolean isPortalUser(UserDTO userDTO) {
		try {
			// 所在部门是否为 Portal
			DepartmentDTO departmentDTO = remoteService.getUserFacade().queryDepartmentById(userDTO.getPrimaryDepartmentId());
			return (null != departmentDTO) && departmentDTO.isPortal();
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 验证方法
	 */
	@RequestMapping(value = "/ssoValid", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView ssoValid(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		try {
			return goService(mav, request); // 尝试访问目标服务地址
		} catch (Exception e) {
			logger.error("sso valid failed ,ready to go login ", e);
			return goFailedTips(mav, request);
		}
	}
	
	/**
	 * 访问目标服务地址
	 * @return
	 */
	private ModelAndView goService(ModelAndView mav, HttpServletRequest request) {
		String cookieInfo = SSOCookieHelper.getCookie(request);
		logger.info("cookieInfo：{}", cookieInfo);
		String returnUrl = request.getParameter("returnUrl");
		logger.info("returnUrl：{}", returnUrl);
		String tokenPair = null;
		if(StringUtils.isNotBlank(cookieInfo)){
			logger.info("cookieInfo不为null");
			String token = remoteService.getUserLoginFacade()
					.checkSecurityInfo(cookieInfo);
			logger.info("token：{}", token);
			// 重定向至服务地址
			if (StringUtils.isNotBlank(returnUrl)) {
				logger.info("returnUrl不为null");
				tokenPair = SSOValidateHelper.getTokenPairWithUrl(returnUrl,
						token);
				logger.info("tokenPair：{}", tokenPair);
				//return "serviceUrl";
				//mav.setViewName("redirect:/"+tokenPair);
				mav.setViewName("redirect:"+tokenPair);
				logger.info("goService方法，跳转1");
				return mav;
			}
			// 不需要重定向，只要
			tokenPair = SSOValidateHelper.getTokenPair(token);
			logger.info("returnUrl是null");
			logger.info("tokenPair：{}", tokenPair);
			//return SUCCESS;
			//mav.setViewName("redirect:/menu/showMenu?"+tokenPair);
			mav.setViewName("redirect:/menu/showMenu?"+tokenPair);
			logger.info("goService方法，跳转2");
			return mav;
		}
		
		logger.info("cookieInfo是null");
		if (StringUtils.isNotBlank(returnUrl)) {
			try {
				returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
			}
			logger.info("returnUrl不是null，新的returnUrl：{}", returnUrl);
		}
		//mav.setViewName("redirect:/loginout/showLogin?returnUrl="+returnUrl);
		mav.setViewName("redirect:/loginout/showLogin?returnUrl="+returnUrl);
		logger.info("goService方法，跳转3");
		return mav;
	}
	
	/**
	 * 转向错误提示
	 * @return
	 */
	private ModelAndView goFailedTips(ModelAndView mav, HttpServletRequest request) {
		String cookieInfo = SSOCookieHelper.getCookie(request);
		String returnUrl = request.getParameter("returnUrl");
		Collection<SSOLoginInfoDTO> loginInfoList = remoteService
				.getUserLoginFacade().findLoginInfoByUserId(cookieInfo);
		if(loginInfoList != null && !loginInfoList.isEmpty()){
			SSOLoginInfoDTO ssoLoginInfo = loginInfoList.iterator().next();
			mav.addObject("ssoLoginInfo", ssoLoginInfo);
		}
		if (StringUtils.isNotBlank(returnUrl)) {
			try {
				returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
				mav.addObject("returnUrl", returnUrl);
			} catch (UnsupportedEncodingException e1) {
			}
		}
		//return "failTips";
		//mav.setViewName("login/failTips");
		mav.setViewName("login/failTips");
		return mav;
	}
	
}
