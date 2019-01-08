package com.zbjdl.boss.admin.frame.utils.csrf;

import org.springframework.web.servlet.support.RequestDataValueProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * title: CSRF 请求数据处理器<br/>
 * description: A <code>RequestDataValueProcessor</code> that pushes a hidden field with a CSRF token into forms.
 * This process implements the {@link #getExtraHiddenFields(javax.servlet.http.HttpServletRequest)} method to push the
 * CSRF token obtained from {@link CSRFTokenManager}. To register this processor to automatically process all
 * Spring based forms register it as a Spring bean named 'requestDataValueProcessor' as shown below:
 * <pre>
 *  &lt;bean name="requestDataValueProcessor" class="cn.im47.cloudprint.utils.csrf.CSRFRequestDataValueProcessor"/&gt;
 * </pre>
 * Copyright: Copyright (c)2018<br/>
 * Company: 八戒财云<br/>
 *
 * @author feng
 * @version 1.0.0
 * @since 2014年12月14日 下午5:34:19
 */
public class CSRFRequestDataValueProcessor implements RequestDataValueProcessor {

    @Override
    public String processFormFieldValue(HttpServletRequest request, String name, String value, String type) {
        return value;
    }

    @Override
    public Map<String, String> getExtraHiddenFields(HttpServletRequest request) {
        Map<String, String> hiddenFields = new HashMap<String, String>();
        hiddenFields.put(CSRFTokenManager.CSRF_PARAM_NAME, CSRFTokenManager.getTokenForSession(request.getSession()));
        return hiddenFields;
    }

    @Override
    public String processUrl(HttpServletRequest request, String url) {
        return url;
    }

	@Override
	public String processAction(HttpServletRequest request, String action, String httpMethod) {
		return action;
	}

}
