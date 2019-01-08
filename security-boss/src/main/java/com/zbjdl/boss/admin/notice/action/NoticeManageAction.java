/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.notice.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.zbjdl.boss.admin.basic.EmployeeBossBaseAction;
import com.zbjdl.boss.admin.facade.UpgradeNoticeFacade;
import com.zbjdl.boss.admin.frame.utils.Constants;
import com.zbjdl.boss.admin.notice.dto.UpgradeNoticeDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.common.dubbo.proxy.SoaServiceRepository;
import com.zbjdl.common.json.JSONUtils;
import com.zbjdl.common.utils.DateUtils;
import com.zbjdl.common.utils.StringUtils;

/**
 * @author feng
 * @since 2012-8-31 下午05:50:31
 * @version 1.0.0
 */
public class NoticeManageAction extends EmployeeBossBaseAction {
	private static final long serialVersionUID = -8560561786831806641L;

	private UpgradeNoticeFacade upgradeNoticeFacade = SoaServiceRepository
			.getService(UpgradeNoticeFacade.class,Constants.DUBBO_DEFAULT_GROUP,Constants.DUBBO_DEFAULT_VERSION);

	private Long noticeId;

	private Long functionId;

	private String content;

	private Long departmentId;

	private String url;

	private String upgradeDate;

	private String oaOrderNo;

	private String jsonNotice;

	private Long getUserId() {
		UserDTO userDTO = this.getCurrentUser();
		if (userDTO != null) {
			return userDTO.getUserId();
		}
		return null;
	}

	private String getFunctionURI() {
		if (StringUtils.contains(url, "?")) {
			url = StringUtils.substringBefore(url, "?");
		}
		if (StringUtils.endsWith(url, "#")) {
			url = StringUtils.substringBeforeLast(url, "#");
		}
		return url;
	}

	public String execute() {
		departmentId = this.getCurrentDepartment();
		return SUCCESS;
	}

	public String showNotice() {
		try {
			Long userId = getUserId();
			String url = this.getFunctionURI();
			if (userId != null && StringUtils.isNotBlank(url)) {
				UpgradeNoticeDTO notice = upgradeNoticeFacade.showNotice(
						userId, url);
				if (notice != null) {
					noticeId = notice.getId();
					content = notice.getContent();
					if (notice.getUpgradeDate() != null) {
						upgradeDate = DateUtils.getShortDateStr(notice
								.getUpgradeDate());
					}
					Map<String, Object> jsonMap = new HashMap<String, Object>();
					jsonMap.put("noticeId", noticeId);
					jsonMap.put("content", content);
					jsonMap.put("upgradeDate", upgradeDate);
					jsonNotice = JSONUtils.toJsonString(jsonMap);
				}
			}
		} catch (Exception e) {
			logger.error("{}", e);
		}
		if (StringUtils.isBlank(jsonNotice)) {
			jsonNotice = "{}";
		}
		return SUCCESS;
	}

	public String closeNotice() {
		try {
			upgradeNoticeFacade.closeNotice(getUserId(), noticeId);
			// 取下一个公告
			showNotice();
		} catch (Exception e) {
			logger.error("", e);
		}
		return SUCCESS;
	}

	public String editNotice() {
		if (this.noticeId != null) {
			UpgradeNoticeDTO notice = upgradeNoticeFacade.queryNotice(noticeId);
			if (notice != null) {
				this.noticeId = notice.getId();
				this.functionId = notice.getFunctionId();
				this.content = notice.getContent();
				this.oaOrderNo = notice.getOaOrderNo();
				this.upgradeDate = DateUtils.getShortDateStr(notice
						.getUpgradeDate());
			}
		}
		return SUCCESS;
	}

	public String saveNotice() {
		try {
			UpgradeNoticeDTO notice = new UpgradeNoticeDTO();
			notice.setId(noticeId);
			notice.setContent(content);
			notice.setFunctionId(functionId);
			notice.setOaOrderNo(oaOrderNo);
			if (StringUtils.isBlank(upgradeDate)) {
				notice.setUpgradeDate(new Date());
			} else {
				notice.setUpgradeDate(DateUtils.parseDate(upgradeDate,
						"yyyy-MM-dd"));
			}
			if (this.noticeId != null) {
				upgradeNoticeFacade.updateNotice(this.getCurrentUser(), notice);
			} else {
				upgradeNoticeFacade.addNotice(this.getCurrentUser(), notice);
			}
		} catch (Exception e) {
			this.handleException(e);
		}
		return SUCCESS;
	}

	public String deleteNotice() {
		try {
			upgradeNoticeFacade.deleteNotice(this.getCurrentUser(),
					this.noticeId);
		} catch (Exception e) {
			this.handleException(e);
		}
		return SUCCESS;
	}

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUpgradeDate() {
		return upgradeDate;
	}

	public void setUpgradeDate(String upgradeDate) {
		this.upgradeDate = upgradeDate;
	}

	public String getOaOrderNo() {
		return oaOrderNo;
	}

	public void setOaOrderNo(String oaOrderNo) {
		this.oaOrderNo = oaOrderNo;
	}

	public String getJsonNotice() {
		return jsonNotice;
	}
}
