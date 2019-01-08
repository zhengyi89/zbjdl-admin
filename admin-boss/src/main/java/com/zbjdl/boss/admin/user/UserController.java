/**
 * 
 */
package com.zbjdl.boss.admin.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zbjdl.boss.admin.frame.service.RemoteService;
import com.zbjdl.boss.admin.frame.service.impl.RemoteServiceImpl;
import com.zbjdl.boss.admin.login.LoginController;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.ModifyPasswordDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.utils.UserInfoUtils;
import com.zbjdl.common.json.JSONUtils;
import com.zbjdl.common.utils.StringUtils;

/**
 * @author zhengy
 * @date 2016年7月6日 下午5:33:18
 */

@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	private static final long magicNum = 125004628000l;
	
	private RemoteService remoteService = RemoteServiceImpl.getInstance();

	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/showModifyPwd", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView showModifyPwd(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView("user/modifypwd");
		HttpSession session = request.getSession();
		UserDTO userDTO = UserInfoUtils.getUserFromSession(session);
		boolean firstLogin = magicNum == userDTO.getPwdLastModifiedTime().getTime();
		DepartmentDTO departmentDTO = remoteService.getUserFacade().queryDepartmentById(userDTO.getPrimaryDepartmentId());
		boolean toPortal = (null != departmentDTO) && departmentDTO.isPortal();
		boolean isGreater3Month = new Date().getTime() - userDTO.getPwdLastModifiedTime().getTime() > 13305600000l;
		
		mav.addObject("firstLogin", firstLogin);
		mav.addObject("toPortal", toPortal);
		mav.addObject("isGreater3Month", isGreater3Month);
		return mav;
	}
	
	/**
	 * 保存密码
	 * @param originalPassword
	 * @param newPassword
	 * @param confirmPassword
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyPwd", method = { RequestMethod.POST })
	public @ResponseBody String modifyPwd(@RequestParam(required = true, value = "originalPassword") String originalPassword,
			@RequestParam(required = true, value = "newPassword") String newPassword,
			@RequestParam(required = true, value = "confirmPassword") String confirmPassword,
			HttpServletRequest request) {
		ModifyPasswordDTO modifyPasswordDTO = new ModifyPasswordDTO();
		UserDTO loginUser = UserInfoUtils.getUserFromSession(request.getSession());
		modifyPasswordDTO.setLoginName(loginUser.getLoginName());
		modifyPasswordDTO.setOriginalPassword(originalPassword);
		modifyPasswordDTO.setNewPassword(newPassword);
		modifyPasswordDTO.setConfirmPassword(confirmPassword);
		
		Map<String, String> returnMap = new HashMap<String, String>();
		// 新密码与确认密码不相同
		if (!newPassword.equals(confirmPassword)) {
			returnMap.put("message", "newpassword_confirmpassword_notsame");
			returnMap.put("status", "success");
			return JSONUtils.toJsonString(returnMap);
		}

		// 原始密码与新密码相同
		if (originalPassword.equals(newPassword)) {
			returnMap.put("message", "password_same");
			returnMap.put("status", "success");
			return JSONUtils.toJsonString(returnMap);
		}

		// 新密码与登录名相同
		if (newPassword.endsWith(loginUser.getLoginName())) {
			returnMap.put("message", "password_name_same");
			returnMap.put("status", "success");
			return JSONUtils.toJsonString(returnMap);
		}

		try {
			if (remoteService.getUserFacade().updatePassword(modifyPasswordDTO)) {
				returnMap.put("message", "success");
			} else {
				returnMap.put("message", "password_error");
			}
		} catch (Exception e) {
			logger.warn("{} 修改密码时报错：{}", loginUser.getLoginName(), e.getMessage());
			returnMap.put("message", e.getMessage());
		}
		returnMap.put("status", "success");
		return JSONUtils.toJsonString(returnMap);
	}
	
	/**
	 * 进入个人信息修改页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/showUserInfoModify", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView showUserInfoModify(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("user/userInfoModify");
		HttpSession session = request.getSession();
		UserDTO userDto = UserInfoUtils.getUserFromSession(session);
		mav.addObject("userDto", userDto);
		return mav;
	}
	
	/**
	 * 修改个人信息
	 *
	 * @return
	 */
	@RequestMapping(value = "/modifyUserInfo", method = { RequestMethod.POST })
	public @ResponseBody String modifyUserInfo(HttpServletRequest request, @ModelAttribute UserDTO userDto) {
		if (null == userDto) {
			return "修改信息不能为空!";
		}
		if (StringUtils.isBlank(userDto.getUserName())) {
			return "姓名不能为空!";
		} else if (StringUtils.isBlank(userDto.getEmail())) {
			return "邮箱不能为空!";
		}
		
		HttpSession session = request.getSession();
		UserDTO user = remoteService.getUserFacade().queryUserById(
				UserInfoUtils.getUserFromSession(session).getUserId());

		if ("2".equals(userDto.getPwdShowNotice())) {
			user.setPwdShowNotice("0");
		} else {
			user.setUserName(userDto.getUserName());
			user.setEmail(userDto.getEmail());
			user.setMobile(userDto.getMobile());

			// 提示定期修改密码
			if (null == userDto.getPwdShowNotice()) {
				user.setPwdShowNotice("0");
			} else {
				user.setPwdShowNotice(userDto.getPwdShowNotice());
			}
		}
		try {
			remoteService.getUserFacade().updateUserInfo(user);
			UserInfoUtils.setUserToSession(session, user); // 更新session信息状态
		} catch (Exception e) {
			return "系统异常，请稍后重试！";
		}
		
		return "success";
	}
}
