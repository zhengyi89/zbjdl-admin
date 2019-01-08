package com.zbjdl.common.utils.web.struts2;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zbjdl.common.utils.CheckUtils;
import com.zbjdl.common.utils.easyquery.RequestParams;


public class MVCFacade {
	private ServletContext servletContext;
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	private RequestParams requestParams;
	
	private Map<String,Object> uploadFiles;
	
	public MVCFacade(HttpServletRequest request, HttpServletResponse response) {
		CheckUtils.notNull(request, "request");
		CheckUtils.notNull(response, "response");
		this.request = request;
		this.response = response;
		this.requestParams = RequestParams.buildRequestParams(request);
	}
	
	public MVCFacade(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) {
		this(request, response);
		this.servletContext = servletContext;
	}
	
	/**
	 * 获得当前HttpServlet Request
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getRequest() {
		return request;
	}
	
	public ServletContext getServletContext(){
		return servletContext;
	}

	/**
	 * 获得 HttpServlet Response
	 * @return HttpServletResponse
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * 获得当前 HttpSession
	 * @return HttpSession
	 */
	public HttpSession getHttpSession() {
		return getRequest().getSession();
	}

	/**
	 * 向request对象添加attribute
	 * @param key
	 * @param value
	 */
	public void setRequestAttribute(String key, Object value) {
		getRequest().setAttribute(key, value);
	}

	/**
	 * 获取request对象中attribute
	 * @param key
	 * @return object
	 */
	public Object getRequestAttribute(String key) {
		Object o = getRequest().getAttribute(key);
		return o;
	}
	/**
	 * 从request中获得String型参数
	 * @param paramName 参数名称
	 * @return 参数值
	 */
	public String getParameter(String paramName) {
		return getRequestParams().getStringParam(paramName);
	}
	
	
	/**
	 * 从request中获得多个相同名称的String型参数
	 * @param paramName 参数名称
	 * @return 参数值数组
	 */
	public String[] getParameters(String paramName){
		return getRequest().getParameterValues(paramName);
	}
	
	/**
	 * 向session中设置属性值
	 * @param name
	 * @param value
	 */
	public void setSessionAttribute(String name, Object value) {
		getHttpSession().setAttribute(name, value);
	}
	
	/**
	 * 从session中取得相应的值
	 * @param name
	 * @return
	 */
	public Object getSessionAttribute(String name) {
		return getHttpSession().getAttribute(name);
	}

	/**
	 * 设置 cookie
	 * @param name 名
	 * @param value 值
	 * @param path 路径
	 * @param expiry 失效时间
	 */
	public void setCookie(String name, String value, String path, int expiry) {
		Cookie cookie = new Cookie(name, value);
		cookie.setSecure(false);
		cookie.setPath(path);
		if (expiry > 0) {
			cookie.setMaxAge(expiry);
		} else {
			cookie.setMaxAge(3600 * 24 * 7); // 7 days
		}
		getResponse().addCookie(cookie);
	}

	/**
	 * 得到 Cookie
	 * @param name cookie 名
	 * @return Cookie || null
	 */
	public Cookie getCookie(String name) {
		HttpServletRequest request = getRequest();
		Cookie[] cookies = request.getCookies();
		Cookie returnCookie = null;
		if (cookies == null) {
			return returnCookie;
		}
		for (int i = 0; i < cookies.length; i++) {
			Cookie thisCookie = cookies[i];
			if (thisCookie.getName().equals(name)) {
				if (!"".equals(thisCookie.getValue())) {
					returnCookie = thisCookie;
					break;
				}
			}
		}
		return returnCookie;
	}

	/**
	 * 删除Cookie
	 * @param cookie
	 * @param path
	 */
	public void deleteCookie(Cookie cookie, String path) {
		HttpServletResponse response = getResponse();
		if (cookie != null) {
			cookie.setMaxAge(0);
			cookie.setPath(path);
			response.addCookie(cookie);
		}
	}
	
	public Map<String, Object> getUploadFiles() {
		return uploadFiles;
	}

	public void setUploadFiles(Map<String, Object> uploadFiles) {
		this.uploadFiles = uploadFiles;
	}

	/**
	 * 获取httprequest请求参数的封装对象
	 * @return
	 */
	public RequestParams getRequestParams() {
		return requestParams;
	}

	/**
	 * 将所有URL参数合并成一个URL字符串(page参数除外), 提供分页时显示.
	 * 
	 * @return 字符串, 如: para1=11&para2=bb
	 */
	public String mergeParamsAsURI() {
		Map<String, String[]> params = getRequest().getParameterMap();
		StringBuffer out = new StringBuffer();
		Set<String> keys = params.keySet();// 列出所有表单参数
		for (String key : keys) {
			if (!"page".equals(key)) {
				String[] values = params.get(key);// 尝试获取多个参数
				// 不管单个参数还是多个参数统一转成了数组
				if (values.length > 1) {
					values = getRequest().getParameterValues(key);
				} else {
					values = new String[] { getParameter(key) };
				}
				try {
					for (String value : values) {
						System.out.println("value=" + value);
						out.append(java.net.URLEncoder.encode(key, "UTF-8")).append("=");
						out.append(java.net.URLEncoder.encode(value, "UTF-8")).append("&");
						// key=value&
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		// 删除末尾多余的 & 字符
		if (out.toString().endsWith("&")) {
			out.deleteCharAt(out.length() - 1);
		}
		return out.toString();
	}
	
}
