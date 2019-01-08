/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.frame.interceptor;

import com.zbjdl.common.utils.StringUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * 类名称：BaseInterceptor <br>
 * 类描述： 拦截器抽象基类<br>
 *
 * @author feng
 * @version 1.0.0
 * @since 2011-5-16 上午11:18:38
 */
public abstract class BaseInterceptor implements Interceptor {

	private static final long serialVersionUID = 4603338299805938124L;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 如果设置admin-frame-passFlag为以下值则直接进入下一个拦截器
	 */
	private static final String[] PASSFLAG = { "ok", "true" };

	/*
	 * (non-Javadoc)
	 *
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#destroy()
	 */
	@Override
	public void destroy() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#init()
	 */
	@Override
	public void init() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony
	 * .xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = null;
		long startTime = System.currentTimeMillis();
		long otherTime = 0;
		try {
			// 直接通过的检查
			if (isSkipAble() && isAutoSkip()) {
				long nextStartTime = System.currentTimeMillis();
				result = doIntercept(invocation);
				otherTime = System.currentTimeMillis() - nextStartTime;
			} else {
				if (before(invocation)) {
					long nextStartTime = System.currentTimeMillis();
					// 调用下一个拦截器，如果拦截器不存在，则执行Action
					result = doIntercept(invocation);
					otherTime = System.currentTimeMillis() - nextStartTime;
				}
				after(invocation, result);
			}
			return result;
		} finally {
			long totalTime = System.currentTimeMillis() - startTime;
			logger.info(ServletActionContext.getRequest().getRequestURI()
					+ " | method : " + invocation.getProxy().getMethod()
					+ " | result : " + result + " | timeelapse : totalTime="
					+ totalTime + ",interceptTime=" + (totalTime - otherTime));
		}
	}

	/**
	 * 拦截器前处理，处理成功才继续<br>
	 * 如果设置了admin-frame-passFlag，则不执行
	 *
	 * @param invocation
	 * @return
	 * @throws Exception
	 */
	protected boolean before(ActionInvocation invocation) throws Exception {
		return true;
	}

	/**
	 * 进入拦截器必须执行的处理<br>
	 * 不管设置没设置admin-frame-passFlag<br>
	 * 必须手动控制进入下一个拦截器(invocation.invoke())
	 *
	 * @param invocation
	 * @return
	 * @throws Exception
	 */
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		return invocation.invoke();
	}

	/**
	 * 拦截器后处理 <br>
	 * 如果设置了admin-frame-passFlag，则不执行
	 *
	 * @param invocation
	 * @param resultCode
	 * @throws Exception
	 */
	protected void after(ActionInvocation invocation, String resultCode)
			throws Exception {
	}

	/**
	 * 是否可跳过
	 *
	 * @return
	 */
	protected boolean isSkipAble() {
		return true;
	}

	/**
	 * 判断是否自动跳过
	 *
	 * @return
	 */
	private boolean isAutoSkip() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String passFlag = request.getParameter("admin-frame-passFlag");
		logger.debug("passFlag: {}", passFlag);
		if (StringUtils.isNotBlank(passFlag)) {
			for (String flag : PASSFLAG) {
				if (StringUtils.equalsIgnoreCase(flag, passFlag)) {
					return true;
				}
			}
		}
		return false;
	}
}
