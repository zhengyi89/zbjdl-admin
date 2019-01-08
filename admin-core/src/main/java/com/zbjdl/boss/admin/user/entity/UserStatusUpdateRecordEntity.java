/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.entity;

import java.util.Date;

import com.zbjdl.boss.admin.user.enums.UserStatusEnum;
import com.zbjdl.common.utils.EqualUtils;
import com.zbjdl.common.persistence.Entity;

/**    
 *    
 * 类名称：UserStatusUpdateRecord <br>    
 * 类描述： 用户状态变化记录<br>
 * @author：feng    
 * @since：2011-6-20 下午02:58:23 
 * @version: 1.0
 *     
 */
public class UserStatusUpdateRecordEntity implements Entity<Long> {
	
	private static final long serialVersionUID = 7675346776419703444L;

	private Long recordId;//记录ID，主键
	
	private Long userId;//用户ID，外键
	
	private UserStatusEnum preStatus;//变化前状态
	
	private UserStatusEnum currentStatus;//变化后状态
	
	private Long adminUserId;//管理员用户ID，当用户状态是被管理员更新的时候记录
	
	private String updateReason;//状态变化原因
	
	private Date updateDate;//状态变化时间

	/**    
	 * 描述： 记录ID，主键
	 * @param recordId the recordId to set    
	 */
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	/**    
	 * 描述： 用户ID，外键
	 * @param userId the userId to set    
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**    
	 * 描述： 变化前状态
	 * @param preStatus the preStatus to set    
	 */
	public void setPreStatus(UserStatusEnum preStatus) {
		this.preStatus = preStatus;
	}

	/**    
	 * 描述： 变化后状态
	 * @param currentStatus the currentStatus to set    
	 */
	public void setCurrentStatus(UserStatusEnum currentStatus) {
		this.currentStatus = currentStatus;
	}

	/**    
	 * 描述： 管理员用户ID，当用户状态是被管理员更新的时候记录
	 * @param adminUserId the adminUserId to set    
	 */
	public void setAdminUserId(Long adminUserId) {
		this.adminUserId = adminUserId;
	}

	/**    
	 * 描述： 状态变化原因
	 * @param updateReason the updateReason to set    
	 */
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}

	/**    
	 * 描述： 状态变化时间
	 * @param updateDate the updateDate to set    
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**    
	 * recordId    
	 *    
	 * @return  the recordId   
	 */
	
	public Long getRecordId() {
		return recordId;
	}

	/**    
	 * userId    
	 *    
	 * @return  the userId   
	 */
	
	public Long getUserId() {
		return userId;
	}

	/**    
	 * preStatus    
	 *    
	 * @return  the preStatus   
	 */
	
	public UserStatusEnum getPreStatus() {
		return preStatus;
	}

	/**    
	 * currentStatus    
	 *    
	 * @return  the currentStatus   
	 */
	
	public UserStatusEnum getCurrentStatus() {
		return currentStatus;
	}

	/**    
	 * adminUserId    
	 *    
	 * @return  the adminUserId   
	 */
	
	public Long getAdminUserId() {
		return adminUserId;
	}

	/**    
	 * updateReason    
	 *    
	 * @return  the updateReason   
	 */
	
	public String getUpdateReason() {
		return updateReason;
	}

	/**    
	 * updateDate    
	 *    
	 * @return  the updateDate   
	 */
	
	public Date getUpdateDate() {
		return updateDate;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.common.persistence.Entity#getId()    
	 */
	@Override
	public Long getId() {
		return recordId;
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.common.persistence.Entity#setId(java.io.Serializable)    
	 */
	@Override
	public void setId(Long arg0) {
		this.recordId = arg0;
	}

	/* (non-Javadoc)    
	 * @see java.lang.Object#equals(java.lang.Object)    
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserStatusUpdateRecordEntity) {
			UserStatusUpdateRecordEntity entity = (UserStatusUpdateRecordEntity) obj;
			if (!EqualUtils.isEqual(entity.getAdminUserId(), this.getAdminUserId())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getAdminUserId(), this.getAdminUserId())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getCurrentStatus(), this.getCurrentStatus())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getPreStatus(), this.getPreStatus())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getRecordId(), this.getRecordId())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getUpdateDate(), this.getUpdateDate())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getUpdateReason(), this.getUpdateReason())) {
				return false;
			}
			if (!EqualUtils.isEqual(entity.getUserId(), this.getUserId())) {
				return false;
			}
			return true;
		}
		return false;
	}

}
