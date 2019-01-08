/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.frame.interceptor.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.facade.UserFacade;
import com.zbjdl.boss.admin.facade.WorkItemFacade;
import com.zbjdl.boss.admin.frame.security.SecurityManager;
import com.zbjdl.boss.admin.frame.utils.BossFreemarkerUtils;
import com.zbjdl.boss.admin.frame.utils.BossWebUtils;
import com.zbjdl.boss.admin.frame.utils.Constants;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.boss.admin.workitem.dto.WorkItemDTO;
import com.zbjdl.boss.admin.workitem.enums.WorkItemStatusEnum;
import com.zbjdl.boss.admin.workitem.enums.WorkItemTypeEnum;
import com.zbjdl.common.dubbo.proxy.SoaServiceRepository;
import com.zbjdl.common.encrypt.Digest;
import com.zbjdl.common.json.JSONUtils;
import com.zbjdl.common.utils.CheckUtils;
import com.zbjdl.common.utils.StringUtils;
import com.zbjdl.common.utils.config.ConfigurationUtils;

/**
 * 双权限审核拦截器辅助类
 *
 * @author：feng
 * @since：2014年11月10日 下午3:31:42
 * @version:
 */
public class AuditInterceptorHelper extends BaseInterceptorHelper {
	private static final String SUCCESS_MSG = "提交成功！您使用的功能需要复核，请耐心等待。";

	/**
	 * 提交成功
	 */
	private final String SUBMIT_SUCCESS = "001";

	/**
	 * 提交失败
	 */
	private final String SUBMIT_FAIL = "002";

	/**
	 * 重复提交，该单据待审核状态
	 */
	private final String RECOMMIT_AUDITING = "003";

	/**
	 * 重复提交，该单据已审核通过
	 */
	private final String RECOMMIT_SUCCESS = "004";

	/**
	 * 重复提交，该单据已审核拒绝
	 */
	private final String RECOMMIT_REFUSE = "005";

	/**
	 * 重复提交，该单据已被废弃
	 */
	private final String RECOMMIT_FORBIDDEN = "006";

	/**
	 * 外部域名
	 */
	private static final String DOMAIN_OUTSIDE = "http://boss.yunpal.net";

	private static AuditInterceptorHelper instance;

	private AuditInterceptorHelper() {

	}

	public static AuditInterceptorHelper getInstance() {
		if (instance == null) {
			synchronized (AuditInterceptorHelper.class) {
				if (instance == null) {
					instance = new AuditInterceptorHelper();
				}
			}
		}
		return instance;
	}

	private UserFacade userFacade = SoaServiceRepository
			.getService(UserFacade.class,Constants.DUBBO_DEFAULT_GROUP,Constants.DUBBO_DEFAULT_VERSION);

	private WorkItemFacade workItemFacade = SoaServiceRepository
			.getService(WorkItemFacade.class,Constants.DUBBO_DEFAULT_GROUP,Constants.DUBBO_DEFAULT_VERSION);

	@SuppressWarnings("unchecked")
	@Override
	public boolean before(HttpServletRequest request,
			HttpServletResponse response, Object... extParams) throws Exception {
		UserDTO userDTO = (UserDTO) request.getSession().getAttribute(
				DataDictDTO.SESSION_USERINFO);

		// 判断是否为ajax请求
		boolean isAjax = BossWebUtils.isAjaxRequest(request);

		response.setCharacterEncoding("UTF-8");
		int auditLevel = 0;
		// 判断功能是否需要复合
		FunctionDTO fun = SecurityManager.getThreadFunction(request);
		// 是否需要审核对象0和null不需要
		if (fun != null) {
			auditLevel = fun.getAuditLevel();
		}
		if (auditLevel > 0) {
			WorkItemDTO workItemDTO = new WorkItemDTO();
			// 拼装并保存form表单的地址、参数
			// 在原页面的form表单中需要有原数据、新数据、资源ID
			@SuppressWarnings("rawtypes")
			Map requestMap = request.getParameterMap();
			@SuppressWarnings("rawtypes")
			Map newMap = new HashMap();
			for (Object key : requestMap.keySet()) {
				Object value = requestMap.get(key);
				if (value != null) {
					Object[] array = (Object[]) value;
					if (array.length == 1) {
						newMap.put(key, array[0]);
					} else {
						newMap.put(key, value);
					}
				}
			}

			String insideDomainString = (String) ConfigurationUtils
					.getConfigParam("config_type_text_resources",
							"boss_inside_domain").getValue();
			// 修改为内部IP
			String domainInsideString = StringUtils.replace(
					fun.getFunctionUrl(), getOutsideDomain(),
					insideDomainString);
			domainInsideString = StringUtils.trim(domainInsideString);
			logger.debug("new utl: {}", domainInsideString);

			newMap.put(DataDictDTO.FORM_KEY, domainInsideString);
			newMap.put(DataDictDTO.SESSION_USERID,
					String.valueOf(userDTO.getUserId()));
			Object resourceId = newMap.get(DataDictDTO.RESOURCE_KEY);
			String resourceIdStr;
			if (resourceId != null) {
				resourceIdStr = resourceId.toString();
			} else {
				resourceIdStr = Digest
						.md5Digest(JSONUtils.toJsonString(newMap));
				// throw new NullPointerException("资源ID参数不能为空。");
			}
			workItemDTO.setResourceId(resourceIdStr);
			workItemDTO.setBelongSystem(String.valueOf(fun.getPreFunctionId()));
			workItemDTO.setFunctionId(fun.getFunctionId());

			// 检查是否重复提交
			// auditLevel :
			// [1:普通审核(可重复申请),2:普通审核(不可重复申请),3:风控审核(可重复申请),4:风控审核(不可重复申请)]
			if (auditLevel % 2 == 0 || needCheckMore(request)) {
				// 查询是否存在该资源的审核记录
				List<WorkItemDTO> workItemDTOs = workItemFacade
						.getWorkItemByObject(workItemDTO);

				if (!CheckUtils.isEmpty(workItemDTOs)) {
					// 按id获取，如果不为空，则判断是否存在未处理的审核结果，判断为重复提交
					for (WorkItemDTO wDto : workItemDTOs) {
						WorkItemStatusEnum status = wDto.getWorkItemStatus();
						if (status == WorkItemStatusEnum.WAITING
								|| status == WorkItemStatusEnum.CHECKING
								|| status == WorkItemStatusEnum.RISK_WAITING
								|| status == WorkItemStatusEnum.RISK_CHECKING) {
							String message = getReturnMessage(isAjax, status);
							if (isAjax) {
								output(request, response, message,
										getReturnMessage(false, status));
							} else {
								output(request, response, message);
							}
							return false;
						}
					}
				}
			}
			workItemDTO.setContent(JSONUtils.toJsonString(newMap));
			workItemDTO.setFunctionId(fun.getFunctionId());
			// 需风控预先审核
			if (auditLevel > 2) {
				workItemDTO.setWorkItemStatus(WorkItemStatusEnum.RISK_WAITING);
				workItemDTO.setWorkItemType(WorkItemTypeEnum.RISK);
			} else {
				workItemDTO.setWorkItemStatus(WorkItemStatusEnum.WAITING);
				workItemDTO.setWorkItemType(WorkItemTypeEnum.NORMAL);
			}
			workItemFacade.createWorkItem(workItemDTO, userDTO);
			if (isAjax) {
				ajaxOutput(request, response, this.SUBMIT_SUCCESS, SUCCESS_MSG);
			} else {
				output(request, response, SUCCESS_MSG);
			}
			logger.debug("提交申请完毕");
			return false;
		}
		return true;
	}

	@Override
	public boolean doIntercept(HttpServletRequest request,
			HttpServletResponse response, Object... extParams) throws Exception {
		try {
			// 获取session中的User信息
			UserDTO userInSession = (UserDTO) request.getSession()
					.getAttribute(DataDictDTO.SESSION_USERINFO);
			if (userInSession == null) {
				// 恢复操作员信息
				String userId = request
						.getParameter(DataDictDTO.SESSION_USERID);
				if (StringUtils.isNotBlank(userId)) {
					Long uid = Long.parseLong(userId);
					UserDTO userDTO = userFacade.queryUserById(uid);
					request.getSession().setAttribute(
							DataDictDTO.SESSION_USERINFO, userDTO);
				}
			}

			// 获取审核人信息
			String auditUserId = request
					.getParameter(DataDictDTO.SESSION_AUDIT_USERID);
			if (StringUtils.isNotBlank(auditUserId)) {
				Long uid = Long.parseLong(auditUserId);
				UserDTO userDTO = userFacade.queryUserById(uid);
				request.getSession().setAttribute(
						DataDictDTO.SESSION_AUDIT_USERINFO, userDTO);
			}
			return true;
		} catch (Exception e) {
			logger.error("", e);
		}
		return false;
	}

	/**
	 * 查询双权限是否需要检查重复提交
	 *
	 * @param req
	 * @return added by :xuebo.yang
	 */
	private boolean needCheckMore(HttpServletRequest req) {
		String needCheckString = req.getParameter("checkMore");
		return StringUtils.equalsIgnoreCase(needCheckString, "true");
	}

	private String getReturnMessage(boolean isAjax, WorkItemStatusEnum status) {
		if (isAjax) {
			switch (status) {
			case RISK_WAITING:
			case RISK_CHECKING:
			case WAITING:
			case CHECKING:
				return this.RECOMMIT_AUDITING;
			case SUCESS:
				return this.RECOMMIT_SUCCESS;
			case REFUSE:
				return this.RECOMMIT_REFUSE;
			case FORBIDDEN:
				return this.RECOMMIT_FORBIDDEN;
			default:
				return this.SUBMIT_FAIL;
			}
		} else {
			switch (status) {
			case RISK_WAITING:
			case RISK_CHECKING:
			case WAITING:
			case CHECKING:
				return "申请已经提交，正在审核中。";
			case SUCESS:
				return "该申请提交过，已经通过审核。";
			case REFUSE:
				return "该申请提交过，已经被拒绝。";
			case FORBIDDEN:
				return "该申请提交过，已经被废弃";
			default:
				return "提交审核申请失败";
			}
		}
	}

	/**
	 * 描述： 生成业务异常画面
	 */
	@Override
	public void output(HttpServletRequest request,
			HttpServletResponse response, Object... messages) throws Exception {

		// 判断是否为ajax请求
		boolean isAjax = BossWebUtils.isAjaxRequest(request);
		if (isAjax) {
			this.ajaxOutput(request, response, (String) messages[0],
					(String) messages[1]);
			return;
		}

		PrintWriter out = response.getWriter();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("message", messages[0]);
		params.put("resourcePath", request.getAttribute("resourcePath"));
		String result = BossFreemarkerUtils.getContentFromTemplate(
				"freemarker/audittemplate.ftl", params);
		if (StringUtils.isBlank(result)) {
			result = (String) messages[0];
		}
		out.println(result);
	}

	private void ajaxOutput(HttpServletRequest request,
			HttpServletResponse response, String code, String message)
			throws IOException {
		PrintWriter out = response.getWriter();

		if (StringUtils.isBlank(code)) {
			code = "";
		}
		// 兼容性处理：旧版双权限审核必传参数：frame_resourceid，belong_sys
		// 旧版只返回错误代码，由客户端解析
		// 新版返回JSON格式，附错误信息
		if (isOldVersion(request)) {
			response.setContentType("text/html;charset=UTF-8");
			out.print(code);
		} else {
			Map<String, Object> info = new HashMap<String, Object>();
			info.put("defineCode", code);
			info.put("errMsg", message);
			if (StringUtils.equals(SUBMIT_SUCCESS, code)) {
				// 放入成功标识，易于客户端通用化处理
				info.put("success", true);
			}
			response.setContentType("application/json;charset=UTF-8");
			out.print(JSONUtils.toJsonString(info));
		}
	}

	/**
	 * 兼容性处理：旧版双权限审核必传参数：frame_resourceid，belong_sys <br>
	 * 旧版只返回错误代码，由客户端解析<br>
	 * 新版返回JSON格式，附错误信息
	 *
	 * @return
	 */
	private boolean isOldVersion(HttpServletRequest request) {
		// 新版特殊标识，有此标识也可传其他旧版参数
		return !StringUtils.equalsIgnoreCase(
				request.getParameter("frame_version"), "new")
				|| StringUtils.isNotBlank(request
						.getParameter(DataDictDTO.BELONG_SYS))
				|| StringUtils.isNotBlank(request.getParameter("checkMore"))
				|| StringUtils.isNotBlank(request
						.getParameter(DataDictDTO.RESOURCE_KEY));
	}

	private String getOutsideDomain() {
		String outsideDomain = null;
		try {
			outsideDomain = (String) ConfigurationUtils.getConfigParam(
					"config_type_text_resources", "boss_outside_domain")
					.getValue();
		} catch (Exception e) {
		}
		if (StringUtils.isBlank(outsideDomain)) {
			outsideDomain = DOMAIN_OUTSIDE;
		}
		return outsideDomain;
	}
}
