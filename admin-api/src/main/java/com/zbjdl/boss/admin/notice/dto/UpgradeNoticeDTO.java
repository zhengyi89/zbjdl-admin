/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.notice.dto;

import java.util.Date;

import com.zbjdl.boss.admin.dto.basic.BaseDTO;

/**
 * 升级公告
 * 
 * @author：feng
 * @since：2012-8-31 上午10:36:50
 * @version:
 */
public class UpgradeNoticeDTO extends BaseDTO {
	private static final long serialVersionUID = 4403454555817272672L;

	private Long id;

	/**
	 * 功能ID
	 */
	private Long functionId;

	/**
	 * 公告内容
	 */
	private String content;

	/**
	 * 创建时间
	 */
	private Date createDate = new Date();
	
	/**
	 * 升级时间
	 */
	private Date upgradeDate;
	
	/**
	 * OA上线单号
	 */
	private String oaOrderNo;

	/**
	 * 操作员
	 */
	private String operator;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpgradeDate() {
		return upgradeDate;
	}

	public void setUpgradeDate(Date upgradeDate) {
		this.upgradeDate = upgradeDate;
	}

	public String getOaOrderNo() {
		return oaOrderNo;
	}

	public void setOaOrderNo(String oaOrderNo) {
		this.oaOrderNo = oaOrderNo;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}
