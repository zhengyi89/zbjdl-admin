/**
 * 
 */
package com.zbjdl.boss.admin.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;

/**
 * @author zhengy
 * @date 2016年8月3日 下午3:32:39
 */
@Controller
public class PortalController {
	
	@RequestMapping(value = "/")
	public ModelAndView execute(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session
				.getAttribute(DataDictDTO.SESSION_USERINFO);
		if(user == null){
			mav.setViewName("redirect:/loginout/showLogin");
		}else{
			mav.setViewName("redirect:/menu/showMenu");
		}
		
		return mav;
	}
}
