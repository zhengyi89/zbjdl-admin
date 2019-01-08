/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.frame.interceptor.helper;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zbjdl.boss.admin.dto.operationlog.OperationLogDTO;
import com.zbjdl.boss.admin.facade.OperationLogFacade;
import com.zbjdl.boss.admin.frame.utils.Constants;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.common.dubbo.proxy.SoaServiceRepository;

/**
 * 类描述：操作日志拦截器辅助类
 *
 * @author：feng
 * @since：2014年11月10日 下午5:24:16
 */
public class OperationLogInterceptorHelper extends BaseInterceptorHelper {

	private static ExecutorService executor = Executors.newFixedThreadPool(5);

	private OperationLogFacade operationLogFacade = SoaServiceRepository
			.getService(OperationLogFacade.class,Constants.DUBBO_DEFAULT_GROUP,Constants.DUBBO_DEFAULT_VERSION);

	private static OperationLogInterceptorHelper instance;

	private OperationLogInterceptorHelper() {

	}

	public static OperationLogInterceptorHelper getInstance() {
		if (instance == null) {
			synchronized (OperationLogInterceptorHelper.class) {
				if (instance == null) {
					instance = new OperationLogInterceptorHelper();
				}
			}
		}
		return instance;
	}

	@Override
	public void output(HttpServletRequest request,
			HttpServletResponse response, Object... messages) throws Exception {
		try {
			UserDTO userDTO = (UserDTO) messages[0];
			FunctionDTO function = (FunctionDTO) messages[1];
			String result = (String) messages[2];
			Long startInvokeTime = (Long) messages[3];
			// 异步记录操作日志
			executor.execute(new OperationLogRunner(userDTO, function, request
					.getParameterMap(), result, startInvokeTime));
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	private class OperationLogRunner implements Runnable {
		private UserDTO userDTO;

		private FunctionDTO function;

		@SuppressWarnings("rawtypes")
		private Map paramMap;

		private long startInvokeTime;

		private long endInvokeTime;

		private String result;

		@SuppressWarnings("rawtypes")
		public OperationLogRunner(UserDTO userDTO, FunctionDTO function,
				Map paramMap, String result, long startInvokeTime) {
			this.userDTO = userDTO;
			this.function = function;
			this.paramMap = paramMap;
			this.result = result;
			this.startInvokeTime = startInvokeTime;
			this.endInvokeTime = System.currentTimeMillis();
		}

		@Override
		public void run() {
			OperationLogDTO operationLogDTO = new OperationLogDTO();
			try {
				if (userDTO != null) {
					operationLogDTO.setOperatorId(userDTO.getUserId());
					operationLogDTO
							.setOperatorLoginName(userDTO.getLoginName());
				}
				operationLogDTO.setFunctionName(function.getFunctionName());
				operationLogDTO.setFunctionId(function.getFunctionId());
				operationLogDTO.setFunctionUrl(function.getFunctionUrl());
				operationLogDTO.setKeyword(function.getKeyWord());
				operationLogDTO.setParamMap(paramMap);
				operationLogDTO.setOperateEndTime(new Date(endInvokeTime));
				operationLogDTO.setDuringTime(endInvokeTime - startInvokeTime);
				operationLogDTO.setOperateResult(result);

				// 写日志
				operationLogFacade.writeLog(operationLogDTO);
			} catch (Exception e) {
				logger.error("记录操作日志异常，日志内容：" + operationLogDTO.toString(), e);
			}
		}

	}
}
