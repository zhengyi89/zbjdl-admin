/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.authority.entity;

import com.zbjdl.common.persistence.Entity;

/**    
 *    
 * 类名称：UserAndRoleRelationEntity <br>    
 * 类描述：  用户角色关系实体 <br>
 * @author：feng    
 * @since：2011-7-4 下午01:40:46 
 * @version:     
 *     
 */
public class UserAndRoleRelationEntity implements Entity<Long>{

	private static final long serialVersionUID = -1013980436745107982L;
	
	private Long id;
	
	private Long userId;
	
	private Long roleId;
	

	/**    
	 * userId    
	 *    
	 * @return  the userId   
	 */
	
	public Long getUserId() {
		return userId;
	}

	/**    
	 * 描述：用户ID
	 * @param userId the userId to set    
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**    
	 * roleId    
	 *    
	 * @return  the roleId   
	 */
	
	public Long getRoleId() {
		return roleId;
	}

	/**    
	 * 描述： 角色ID
	 * @param roleId the roleId to set    
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.common.persistence.Entity#getId()    
	 */
	@Override
	public Long getId() {
		return id;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.common.persistence.Entity#setId(java.io.Serializable)    
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
