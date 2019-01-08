package com.zbjdl.boss.admin.frame.interceptor.springmvc;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 防止表单重复提交拦截器
 * 
 * @author ZhuTao
 *
 * 上午9:24:40
 */
public class AvoidDuplicateSubmissionInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null) {
                boolean needNewSession = annotation.validate();
                if(needNewSession){
                	if((String) request.getParameter("token")!=null){
                		   if (isRepeatSubmit(request)) {
                			   response.sendRedirect("error");
                			   return false;
                           }
                           request.getSession(false).removeAttribute("token");
                	}
                    request.getSession(false).setAttribute("token", UUID.randomUUID().toString());
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }
    
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object arg2, ModelAndView arg3) throws Exception {
//    	if(arg3!=null){
//    		arg3.addObject("message", "again submit");
//    	}
    }

    private boolean isRepeatSubmit(HttpServletRequest request) throws Exception {
        String serverToken = (String) request.getSession(false).getAttribute("token");
        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter("token");
        if (clinetToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
   
        return false;
    }
}