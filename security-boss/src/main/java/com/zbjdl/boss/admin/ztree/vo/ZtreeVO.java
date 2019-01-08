package com.zbjdl.boss.admin.ztree.vo;

import java.util.Map;

import com.zbjdl.boss.admin.basic.vo.BasicVO;


public class ZtreeVO extends BasicVO {

	private static final long serialVersionUID = 696704521059129654L;
	
    private Long id ;
	
	private Long pId;
	
	private String name;
	
	private Boolean checked;
	
	private Boolean chkDisabled;
	
	private Map<Long,Long[]> dependency;
	
	private Map<Long,Long[]> ref;

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

	public Map<Long, Long[]> getDependency() {
		return dependency;
	}

	public void setDependency(Map<Long, Long[]> dependency) {
		this.dependency = dependency;
	}

	public Map<Long, Long[]> getRef() {
		return ref;
	}

	public void setRef(Map<Long, Long[]> ref) {
		this.ref = ref;
	}

    	
	
	

}
