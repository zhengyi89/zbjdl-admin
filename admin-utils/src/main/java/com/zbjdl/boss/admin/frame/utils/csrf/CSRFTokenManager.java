package com.zbjdl.boss.admin.frame.utils.csrf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * title: CSRF token 管理器<br/>
 * description: A manager for the CSRF token for a given session. The {@link #getTokenForSession(javax.servlet.http.HttpSession)} should used to
 * obtain the token value for the current session (and this should be the only way to obtain the token value).<br/>
 * Copyright: Copyright (c)2018<br/>
 * Company: 八戒财云<br/>
 *
 * @author feng
 * @version 1.0.0
 * @since 2014年12月14日 下午5:34:19
 */
final class CSRFTokenManager {

    static final String CSRF_PARAM_NAME = "CSRFToken";

    private final static String CSRF_TOKEN_FOR_SESSION_ATTR_NAME = CSRFTokenManager.class.getName() + ".tokenval";

    static String getTokenForSession(HttpSession session) {
        String token = null;
        synchronized (session) {
            token = (String) session.getAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME);
            if (null == token) {
                // TODO 建立一个pool
                token = UUID.randomUUID().toString();
                session.setAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME, token);
            }
        }
        return token;
    }

    /**
     * 获取 token
     *
     * @param request
     * @return
     */
    static String getTokenFromRequest(HttpServletRequest request) {
        return request.getParameter(CSRF_PARAM_NAME);
    }

    private CSRFTokenManager() {
        // do nothing
    }

}
