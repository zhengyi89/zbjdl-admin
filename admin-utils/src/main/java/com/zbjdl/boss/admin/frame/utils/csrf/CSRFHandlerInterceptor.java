package com.zbjdl.boss.admin.frame.utils.csrf;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * title: CSRF 处理拦截器<br/>
 * description: A Spring MVC <code>HandlerInterceptor</code> which is responsible to enforce CSRF token validity on incoming posts requests. The interceptor
 * should be registered with Spring MVC servlet using the following syntax:
 * <pre>
 *   &lt;mvc:interceptors&gt;
 *        &lt;bean class="com.zbjdl.boss.admin.frame.utils.csrf.CSRFHandlerInterceptor"/&gt;
 *   &lt;/mvc:interceptors&gt;
 *   </pre>
 *
 * @author feng
 * @version 1.0.0
 * @see CSRFRequestDataValueProcessor
 * Copyright: Copyright (c)2018<br/>
 * Company: 八戒财云<br/>
 * @since 2014年12月14日 下午5:34:19
 */
public class CSRFHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 仅对 POST 请求，且URL不以 .ajax 结尾或者不包含 /file/upload 的请求检查 token
        if (!request.getMethod().equalsIgnoreCase("POST")
                || request.getRequestURI().endsWith(".ajax")
                || request.getRequestURI().contains("/file/upload")) {
            return true;
        } else {
            String sessionToken = CSRFTokenManager.getTokenForSession(request.getSession());
            String requestToken = CSRFTokenManager.getTokenFromRequest(request);
            if (sessionToken.equals(requestToken)) {
                return true;
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bad or missing CSRF value");
                return false;
            }
        }
    }

}
