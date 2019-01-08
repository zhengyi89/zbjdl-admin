/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.notice.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zbjdl.boss.admin.biz.SecurityBiz;
import com.zbjdl.boss.admin.biz.SecurityConfigBiz;
import com.zbjdl.boss.admin.facade.UpgradeNoticeFacade;
import com.zbjdl.boss.admin.function.dto.FunctionDTO;
import com.zbjdl.boss.admin.function.service.FunctionService;
import com.zbjdl.boss.admin.notice.dto.UpgradeNoticeDTO;
import com.zbjdl.boss.admin.notice.service.UpgradeNoticeService;
import com.zbjdl.boss.admin.role.exception.AuthorityException;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;
import com.zbjdl.common.utils.CheckUtils;

/**
 * @author：feng
 * @since：2012-8-31 上午10:43:35
 * @version:
 */
public class UpgradeNoticeFacadeImpl implements UpgradeNoticeFacade {

	/**
	 * 权限业务接口
	 */
	@Autowired
	private SecurityBiz securityBiz;

	@Autowired
	private SecurityConfigBiz securityConfigBiz;

	@Autowired
	private UpgradeNoticeService upgradeNoticeService;

	@Autowired
	private FunctionService functionService;

	@Override
	public void addNotice(UserDTO user, UpgradeNoticeDTO notice) {
		CheckUtils.notNull(user, "用户");
		CheckUtils.notNull(notice, "公告");
		CheckUtils.notNull(notice.getFunctionId(), "功能ID");
		CheckUtils.notNull(notice.getContent(), "公告内容");
		this.checkPermission(user, notice.getFunctionId());
		notice.setOperator(user.getLoginName());
		upgradeNoticeService.addNotice(notice);
	}

	@Override
	public void updateNotice(UserDTO user, UpgradeNoticeDTO notice) {
		CheckUtils.notNull(user, "用户");
		CheckUtils.notNull(notice, "公告");
		CheckUtils.notNull(notice.getId(), "公告ID");
		CheckUtils.notNull(notice.getFunctionId(), "功能ID");
		CheckUtils.notNull(notice.getContent(), "公告内容");
		this.checkPermission(user, notice.getFunctionId());
		notice.setOperator(user.getLoginName());
		upgradeNoticeService.updateNotice(notice);
	}

	@Override
	public void deleteNotice(UserDTO user, Long noticeId) {
		CheckUtils.notNull(user, "用户");
		CheckUtils.notNull(noticeId, "公告ID");
		UpgradeNoticeDTO notice = upgradeNoticeService.queryNotice(noticeId);
		if (notice != null) {
			this.checkPermission(user, notice.getFunctionId());
			upgradeNoticeService.deleteNotice(noticeId);
		}
	}

	@Override
	public UpgradeNoticeDTO showNotice(Long userId, String functionUri) {
		CheckUtils.notNull(userId, "用户ID");
		CheckUtils.notNull(functionUri, "功能URI");
		FunctionDTO functionDTO = functionService
				.queryFunctionByUri(functionUri);
		if (functionDTO == null) {
			return null;
		}
		return upgradeNoticeService.showNotice(userId,
				functionDTO.getFunctionId());
	}

	@Override
	public void closeNotice(Long userId, Long noticeId) {
		CheckUtils.notNull(userId, "用户ID");
		CheckUtils.notNull(noticeId, "公告ID");
		upgradeNoticeService.closeNotice(userId, noticeId);
	}

	@Override
	public UpgradeNoticeDTO queryNotice(Long noticeId) {
		return upgradeNoticeService.queryNotice(noticeId);
	}

	private boolean checkPermission(UserDTO user, Long functionId) {
		boolean hasPermission = false;
		if (user.getIsSuperAdmin() != null && user.getIsSuperAdmin()) {
			// 超级管理员可以给所有功能增加升级公告
			hasPermission = true;
		} else if (user.getIsAdmin() != null && user.getIsAdmin()) {
			// 部门管理员可以给本部门的功能增加升级公告
			List<DepartmentDTO> depts = securityConfigBiz
					.queryDepartmentsByFunctionId(functionId);
			long deptId = user.getPrimaryDepartmentId();
			if (depts != null && !depts.isEmpty()) {
				for (DepartmentDTO dept : depts) {
					if (dept.getDepartmentId() == deptId) {
						hasPermission = true;
						break;
					}
				}
			}
		} else {
			// NOTICE:正常业务不会到达此分支，前端无入口
			// 此接口不区分管理员，所以不能满足本业务所需
			hasPermission = securityBiz.checkPermission(user.getUserId(),
					functionId);
		}
		if (!hasPermission) {
			throw new AuthorityException("没有维护此功能对应公告的权限");
		}
		return hasPermission;
	}
}
