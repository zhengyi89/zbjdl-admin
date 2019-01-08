package com.zbjdl.boss.admin.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zbjdl.boss.admin.frame.service.RemoteService;
import com.zbjdl.boss.admin.frame.service.impl.RemoteServiceImpl;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.user.exception.UserAuthenticationException;
import com.zbjdl.common.json.JSONException;
import com.zbjdl.common.utils.BeanUtils;

/**
 * @author zhengy
 * @date 2016/07/14
 */
@RestController
@RequestMapping("/api")
public class UserApi {
	private static final Logger logger = LoggerFactory.getLogger(UserApi.class);

	private RemoteService remoteService = RemoteServiceImpl.getInstance();

	/**
	 * 登陆
	 * 
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value = "/loginByMobile", method = { RequestMethod.POST, RequestMethod.GET })
	public Object loginByMobile(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes)
			throws JSONException {
		Map<String, Object> resultMap = new HashMap<>();
		String mobile = request.getParameter("mobile");
		String password = request.getParameter("password");

		UserDTO userDTO = new UserDTO();
		try {
			userDTO = remoteService.getUserFacade().userLoginValidateByMobile(mobile, password);
		} catch (UserAuthenticationException e) {
			if ("004".equals(e.getDefineCode())) {
				resultMap.put("errorMessage", "提示：用户不存在。");
			} else if ("003".equals(e.getDefineCode())) {
				resultMap.put("errorMessage", "提示：用户已冻结。");
			} else {
				resultMap.put("errorMessage", "提示：密码错误。");
			}
			logger.info("调用api登录失败，原因：", resultMap.toString());
			return resultMap.toString();
		}
		if (userDTO.getUserId() == null || "".equals(userDTO.getUserId())) {
			resultMap.put("flag", "failed");
		} else {
			BeanUtils.copyProperties(userDTO, resultMap);
			resultMap.put("flag", "ok");
		}
		logger.info("调用api登录成功，返回数据:{}", resultMap.toString());
		return resultMap;
	}
	
	
	/**
	 * 登陆
	 * @return
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
			Map<String, Object> resultMap = new HashMap<>();
			String loginName = request.getParameter("loginName");
			String password = request.getParameter("password");

			UserDTO userDTO = new UserDTO();
			try {
				userDTO = remoteService.getUserFacade().userLoginValidate(loginName, password);
			} catch (UserAuthenticationException e) {
				if ("004".equals(e.getDefineCode())) {
					resultMap.put("errorMessage", "提示：用户不存在。");
				} else if ("003".equals(e.getDefineCode())) {
					resultMap.put("errorMessage", "提示：用户已冻结。");
				} else {
					resultMap.put("errorMessage", "提示：密码错误。");
				}
				logger.info("调用api登录失败，原因：", resultMap.toString());
				resultMap.put("flag", "failed");
				return resultMap;
			}
			if (userDTO.getUserId() == null || "".equals(userDTO.getUserId())) {
				resultMap.put("errorMessage", "提示：用户名或密码错误。");
				resultMap.put("flag", "failed");
			} else {
				BeanUtils.copyProperties(userDTO, resultMap);
				resultMap.put("flag", "ok");
			}
			logger.info("调用api登录成功，返回数据:{}", resultMap.toString());
			return resultMap;

	}
	

	/**
	 * 根据userid查找个人信息
	 *
	 * @return
	 */
	@RequestMapping(value = "/user/get/{userId}", method = RequestMethod.GET)
	public Object showUserInfoModify(@PathVariable long userId) {
		UserDTO userDto = remoteService.getUserFacade().queryUserById(userId);
		DepartmentDTO dptDto = remoteService.getUserFacade().queryDepartmentById(userDto.getPrimaryDepartmentId());
		userDto.setPassword(dptDto.getDepartmentName());
		return userDto;
	}
}
