/**
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.basic;

import com.zbjdl.boss.admin.basic.vo.BasicVO;
import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.frame.service.RemoteService;
import com.zbjdl.boss.admin.frame.service.impl.RemoteServiceImpl;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.common.utils.StringUtils;
import com.zbjdl.common.utils.config.TextResource;
import com.zbjdl.common.utils.web.struts2.BaseAction;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author feng
 * @version 1.0.0
 * @since 2012-6-8 下午02:03:14
 */
public class EmployeeBossBaseAction extends BaseAction {

	private static final long serialVersionUID = 844569696938106732L;

	@Autowired
	protected ViewLogicHelper viewLogicHelper;

	protected RemoteService remoteService = RemoteServiceImpl.getInstance();

	/**
	 * 往页面展示的错误信息
	 */
	protected String errMsg;

	/**
	 * 系统异常信息
	 */
	protected static final String SYS_ERR_MSG = "系统异常，请稍后重试！";

	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * 取得当前用户
	 *
	 * @return
	 */
	protected UserDTO getCurrentUser() {
		return (UserDTO) ServletActionContext.getRequest()
				.getSession().getAttribute(DataDictDTO.SESSION_USERINFO);
	}

	protected Long getCurrentDepartment() {
		UserDTO userDTO = this.getCurrentUser();
		if (userDTO != null) {
			return userDTO.getPrimaryDepartmentId();
		}
		return null;
	}

	protected boolean isDeptAdmin() {
		UserDTO userDTO = this.getCurrentUser();
		if (userDTO != null && userDTO.getIsAdmin() != null) {
			return userDTO.getIsAdmin();
		}
		return false;
	}

	protected boolean isSuperAdmin() {
		UserDTO userDTO = this.getCurrentUser();
		if (userDTO != null && userDTO.getIsSuperAdmin() != null) {
			return userDTO.getIsSuperAdmin();
		}
		return false;
	}

	/**
	 * 判断是否首次打开页面
	 *
	 * @return
	 */
	protected boolean isFirstRequest() {
		String queryStr = this.getMVCFacade().getRequest().getQueryString();
		return StringUtils.isBlank(queryStr) || queryStr.matches("^_menuId=-?\\d+&_firstMenuId=-?\\d+$")
				|| queryStr.matches("^_firstMenuId=-?\\d+&_menuId=-?\\d+$");

	}

	/**
	 * 通用错误处理
	 *
	 * @param e
	 */
	protected void handleException(Exception e) {
		logger.error("", e);
		errMsg = e.getMessage();

		if (StringUtils.isBlank(errMsg)) {
			errMsg = SYS_ERR_MSG;
		}
	}

	/**
	 * 通用错误处理 用于有模型驱动的
	 *
	 * @param e
	 */
	protected void handleException(Exception e, BasicVO base) {
		this.handleException(e);
		if (base != null) {
			base.setErrMsg(errMsg);
		}
	}

	public ViewLogicHelper getViewLogicHelper() {
		return viewLogicHelper;
	}

	/**
	 * 重写toString使更容易查看对象的属性值
	 */
	public String toString() {
		try {
			return ToStringBuilder.reflectionToString(this,
					ToStringStyle.MULTI_LINE_STYLE);
		} catch (Exception e) {
			return super.toString();
		}
	}
}
