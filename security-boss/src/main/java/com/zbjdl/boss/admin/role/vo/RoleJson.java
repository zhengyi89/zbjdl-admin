package com.zbjdl.boss.admin.role.vo;

import com.zbjdl.boss.admin.basic.vo.BasicVO;

public class RoleJson extends BasicVO {

	private static final long serialVersionUID = 2305748466017656459L;
	
	private Long id ;
	
	private Long pId;
	
	private String name;
	
	private Boolean checked;
	
	private Boolean chkDisabled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(Boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}
	
	
	

}



