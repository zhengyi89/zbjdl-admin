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
 * 类名称：RoleAndFunctionEntity <br>    
 * 类描述：   角色和功能关系实体<br>
 * @author：feng    
 * @since：2011-7-4 下午02:37:34 
 * @version:     
 *     
 */
public class RoleAndFunctionRelationEntity implements Entity<Long>{
	
	private static final long serialVersionUID = 8670249795270627414L;
	
	private Long id;
	
	private Long roleId;//角色id
	
	private Long functionId;//功能id
	
	/**    
	 * roleId    
	 *    
	 * @return  the roleId   
	 */
	
	public Long getRoleId() {
		return roleId;
	}

	/**    
	 * 描述： 角色id
	 * @param roleId the roleId to set    
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**    
	 * fucntionId    
	 *    
	 * @return  the fucntionId   
	 */
	
	public Long getFunctionId() {
		return functionId;
	}

	/**    
	 * 描述： 功能ID
	 * @param fucntionId the fucntionId to set    
	 */
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
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
