
package com.zbjdl.boss.admin.frame.utils.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zbjdl.common.exception.BaseException;


public class ExceptionFilter implements Filter{
	protected static final Logger logger = LoggerFactory.getLogger(ExceptionFilter.class);
	public static final String EXCEPTION_MESSAGE_ENCODING = "utf-8";
	
	/* (non-Javadoc)    
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)    
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	/* (non-Javadoc)    
	 * @see javax.servlet.Filter#destroy()    
	 */
	@Override
	public void destroy() {
	}

	/* (non-Javadoc)    
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)    
	 */
	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		try{
			chain.doFilter(servletRequest, servletResponse);
			Exception ex = (Exception)servletRequest.getAttribute("javax.servlet.error.exception");
			if(ex!=null){
				handelException(ex, servletRequest, response);
			}
		}catch(Throwable e){
			handelException(e, servletRequest, response);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	public void handelException(Throwable e, ServletRequest servletRequest, HttpServletResponse response){
		ExceptionInfo einfo = new ExceptionInfo();
		einfo.setException(e);
		einfo.setExceptionMessage(e.getMessage()== null ? "":e.getMessage());
		if(e instanceof BaseException){
			BaseException ye = (BaseException)e;
        	logger.error("catch BaseException, {}", e.getClass().getName());
        	logger.error("realClass:{}, exceptionId:{}",ye.getRealClassName(),ye.getId());
        	logger.error("exception message : {}",e.getMessage());
        	logger.error(ye.getCoreStackTraceStr());
        	
        	einfo.setExceptionId(ye.getId());
        	einfo.setExceptionClassInfo(ye.getClass().getName());
        }
        else{
        	BaseException ye = new BaseException(e.getMessage());
        	ye.setStackTrace(e.getStackTrace());
        	ye.setRealClassName(e.getClass().getName());
        	logger.error("catch UnknowException, {}" , e.getClass().getName());
        	logger.error("exceptionId:{}",ye.getId());
        	logger.error(e.getMessage(), e);
        	e = ye;
        	
        	einfo.setExceptionId(ye.getId());
        	einfo.setExceptionClassInfo(ye.getClass().getName());
        }
		servletRequest.setAttribute("_exception_info", einfo);
		
		//在response里面放入错误码和错误信息，以便AJAX客户端能够识别异常
		response.setHeader("r_error_type", "exception");
		try {
			response.setHeader("r_exception_msg", URLEncoder.encode(einfo.getExceptionMessage(), EXCEPTION_MESSAGE_ENCODING));
		} catch (UnsupportedEncodingException e1) {
		}
		response.setHeader("r_exception_id", einfo.getExceptionId());
		response.setHeader("r_exception_code", einfo.getExceptionCode());
	}
}
