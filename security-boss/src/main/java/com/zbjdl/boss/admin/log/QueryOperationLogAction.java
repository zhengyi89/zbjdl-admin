/**
 *
 * Copyright: Copyright (c)2018
 * Company: 八戒财云
 *
 */
package com.zbjdl.boss.admin.log;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.zbjdl.common.utils.easyquery.action.QueryForm;
import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.dto.sso.DataDictDTO;
import com.zbjdl.boss.admin.frame.service.RemoteService;
import com.zbjdl.boss.admin.frame.service.impl.RemoteServiceImpl;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.utils.query.QueryResult;
import com.zbjdl.utils.query.QueryService;

/**
 *
 * 类名称：QueryOperationLogTemplate <br>
 * 类描述： 操作日志模板查询 <br>
 *
 * @author：feng
 * @since：2011-7-30 下午03:55:34
 * @version:
 *
 */
public class QueryOperationLogAction extends EmployeeBossBaseAction {

	private static final long serialVersionUID = 6445410013360580354L;

	private String keyWord;

	private Long id;

	private String LoginNames;

	@Autowired
	private QueryService logQueryService;

	@SuppressWarnings("rawtypes")
	private Map operationLog;

	@Override
	public String execute() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		UserDTO userDTO = (UserDTO) req.getSession().getAttribute(
				DataDictDTO.SESSION_USERINFO);
		StringBuilder sb = new StringBuilder();

		if (userDTO.getIsAdmin()) {
			// 查询部门管理员所在部门的所有操作员名字
			RemoteService bossremoteService = RemoteServiceImpl
					.getInstance();
			List<UserDTO> userList = bossremoteService.getUserFacade()
					.queryDepartmentUsers(userDTO.getPrimaryDepartmentId());

			for (UserDTO user : userList) {
				sb.append("'").append(user.getLoginName()).append("'").append(",");
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			if (sb.length() > 0) {
				LoginNames = sb.toString();
			}
		} else if (!userDTO.getIsSuperAdmin() && !userDTO.getIsAdmin()) {
			sb.append("'").append(userDTO.getLoginName()).append("'");
			LoginNames = sb.toString();
		}

		return SUCCESS;
	}

	/**
	 * 查看日志详情<br/>
	 * 因为日志组件为独立系统，子系统没有直接查询日志的接口，此处通过查询组件查日志详情
	 *
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String queryLogDetail() {
		try {
			QueryForm queryForm = QueryForm.buildForm(
					ServletActionContext.getRequest(), "queryLogDetail");
			QueryResult result = logQueryService.query(
					queryForm.getStratIndex(), 1, "queryLogDetail",
					queryForm.getQueryParams(), "", queryForm.getAsc(), false);
			List resultData = ((List) result.getData());
			if (resultData.size() > 0) {
				operationLog = (Map) resultData.get(0);
			}
		} catch (Exception e) {
			this.handleException(e);
		}
		return SUCCESS;
	}

	/**
	 * keyWord
	 *
	 * @return the keyWord
	 */

	public String getKeyWord() {
		return keyWord;
	}

	/**
	 * 描述： 在这里描述属性的含义
	 *
	 * @param keyWord
	 *            the keyWord to set
	 */
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginNames() {
		return LoginNames;
	}

	public void setLoginNames(String loginNames) {
		LoginNames = loginNames;
	}

	@SuppressWarnings("rawtypes")
	public Map getOperationLog() {
		return operationLog;
	}
}
