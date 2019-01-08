/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.frame.interceptor.helper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zbjdl.boss.admin.frame.ExceptionInfo;
import com.zbjdl.boss.admin.frame.utils.BossFreemarkerUtils;
import com.zbjdl.boss.admin.frame.utils.BossWebUtils;
import com.zbjdl.common.exception.BaseException;
import com.zbjdl.common.json.JSONUtils;
import com.zbjdl.common.utils.StringUtils;
import com.zbjdl.common.utils.config.ConfigParam;
import com.zbjdl.common.utils.config.ConfigurationUtils;
import com.zbjdl.common.utils.config.TextResourceUtils;

/**
 * 类描述： 业务异常统一处理辅助类
 *
 * @author：feng
 * @since：2014年11月10日 下午5:24:16
 */
public class ExceptionInterceptorHelper extends BaseInterceptorHelper {
	private static ExceptionInterceptorHelper instance;

	private ExceptionInterceptorHelper() {

	}

	public static ExceptionInterceptorHelper getInstance() {
		if (instance == null) {
			synchronized (ExceptionInterceptorHelper.class) {
				if (instance == null) {
					instance = new ExceptionInterceptorHelper();
				}
			}
		}
		return instance;
	}

	/**
	 * 描述： 生成非业务异常画面
	 *
	 * @param e
	 *            非业务异常
	 */
	@Override
	public void output(HttpServletRequest request,
			HttpServletResponse response, Object... messages) throws Exception {
		Throwable e = (Throwable) messages[0];
		response.setCharacterEncoding("UTF-8");
		String result;
		if (BossWebUtils.isAjaxRequest(request)) {
			response.setContentType("application/json;charset=UTF-8");
			result = this.buildJsonInfo(e);
		} else {
			response.setContentType("text/html;charset=UTF-8");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("resourcePath", request.getAttribute("resourcePath"));
			ExceptionInfo einfo = this.handelException(e);
			if (einfo != null) {
				params.put("einfo", einfo);
			} else {
				String message = this.getMessage(e);
				params.put("message", message);
			}
			result = BossFreemarkerUtils.getContentFromTemplate(
					"freemarker/exceptiontemplate.ftl", params);
		}

		if (StringUtils.isBlank(result)) {
			result = e.getMessage();
		}
		response.getWriter().println(result);
	}

	private String buildJsonInfo(Throwable e) {
		Map<String, String> info = new HashMap<String, String>();
		String errMsg = this.getMessage(e);
		if (e instanceof BaseException) {
			BaseException ybe = (BaseException) e;
			info.put("name", ybe.getClass().getName());
			info.put("defineCode", ybe.getDefineCode());
			info.put("errMsg", errMsg);
		} else {
			info.put("name", e.getClass().getName());
			info.put("errMsg", errMsg);
		}

		String json;
		try {
			json = JSONUtils.toJsonString(info);
		} catch (Exception e1) {
			logger.error("{}", e1);
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			for (String key : info.keySet()) {
				if (sb.length() > 1) {
					sb.append(",");
				}
				sb.append(key);
				sb.append(":\"");
				sb.append(info.get(key));
				sb.append("\"");
			}
			sb.append("}");
			json = sb.toString();
		}
		return json;
	}

	private String getMessage(Throwable e) {
		String exceptionMsg = null;
		if (e instanceof BaseException) {
			BaseException cbe = (BaseException) e;
			@SuppressWarnings("unchecked")
			ConfigParam<Map<String, String>> param = ConfigurationUtils
					.getConfigParam("config_type_exception_messages", cbe
							.getClass().getName());
			if (param != null) {
				Map<String, String> exceptionMap = param.getValue();
				if (exceptionMap != null) {
					exceptionMsg = exceptionMap.get(cbe.getDefineCode());
				}
			}
			if (StringUtils.isBlank(exceptionMsg)) {
				exceptionMsg = cbe.getRealClassName() + " defineCode : "
						+ cbe.getDefineCode() + " message : "
						+ cbe.getMessage();
			}
		} else {
			exceptionMsg = e.getMessage();
			if (StringUtils.isBlank(exceptionMsg)) {
				Throwable t = e.getCause();
				if (t != null) {
					exceptionMsg = t.getMessage();
				}
			}
		}
		if (StringUtils.isBlank(exceptionMsg)) {
			exceptionMsg = "系统异常:" + e.getClass().getSimpleName();
		}

		return exceptionMsg;
	}

	private ExceptionInfo handelException(Throwable e) {
		ExceptionInfo einfo = new ExceptionInfo();
		einfo.setException(e);
		einfo.setExceptionMessage(e.getMessage());
		if (e instanceof BaseException) {
			BaseException ye = (BaseException) e;
			logger.warn("catch BaseException, {} ", e.getClass().getName());
			logger.warn("realClass:{}, defineCode: {}, exceptionId: {}",
					ye.getRealClassName(), ye.getDefineCode(), ye.getId());
			logger.warn("exception message: {}", e.getMessage());
			logger.warn(ye.getCoreStackTraceStr());

			try {
				String exceptionCode = TextResourceUtils.getExceptionCode(
						ye.getRealClassName(), ye.getDefineCode());
				String exceptionText = TextResourceUtils.getExceptionMessage(
						ye.getRealClassName(), ye.getDefineCode());
				einfo.setExceptionCode(exceptionCode);
				einfo.setExceptionText(exceptionText);
			} catch (Throwable t) {
				logger.error("build ExceptionInfo fail!");
				logger.error(t.getMessage(), t);
			}

			einfo.setSystemError(false);
			einfo.setExceptionClassInfo(ye.getClass().getName() + "|"
					+ ye.getDefineCode());
			einfo.setExceptionId(ye.getId());
		} else if (e instanceof BaseException) {
			BaseException ye = (BaseException) e;
			logger.error("catch BaseException, {}", e.getClass()
					.getName());
			logger.error("realClass: {}, exceptionId: {}",
					ye.getRealClassName(), ye.getId());
			logger.error("exception message: {}", e.getMessage());
			logger.error(ye.getCoreStackTraceStr());

			einfo.setExceptionId(ye.getId());
			einfo.setExceptionClassInfo(ye.getClass().getName());
		} else {
			BaseException ye = new BaseException(
					e.getMessage());
			ye.setStackTrace(e.getStackTrace());
//			ye.setRealClassName(e.getClass().getName());
			logger.error("catch UnknowException, {}", e.getClass().getName());
			logger.error("exceptionId: {}", ye.getId());
			logger.error(e.getMessage(), e);

			einfo.setExceptionId(ye.getId());
			einfo.setExceptionClassInfo(ye.getClass().getName());
		}
		einfo.setExceptionMessage(BossWebUtils.cleanXSS(einfo
				.getExceptionMessage()));
		einfo.setExceptionText(BossWebUtils.cleanXSS(einfo
				.getExceptionText()));
		return einfo;
	}
}
