/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.authority.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zbjdl.boss.admin.authority.entity.UserAndRoleRelationEntity;
import com.zbjdl.boss.admin.authority.service.UserAndRoleRelationService;
import com.zbjdl.boss.admin.repository.UserAndRoleRelationDao;

/**    
 *    
 * 类名称：UserAndRoleRelationServiceImpl <br>    
 * 类描述：   <br>
 * @author：feng    
 * @since：2011-7-6 上午11:17:38 
 * @version:     
 *     
 */
public class UserAndRoleRelationServiceImpl implements UserAndRoleRelationService{
	
	private static final Log logger = LogFactory.getLog(UserAndRoleRelationServiceImpl.class.getName());
	
	private UserAndRoleRelationDao userAndRoleRelationDao;

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.authority.service.UserAndRoleRelationService#createUserAndRoleRelation(java.lang.Long, java.lang.Long)    
	 */
	@Override
	public void createUserAndRoleRelation(Long userId, Long roleId) {
		UserAndRoleRelationEntity userAndRoleRelationEntity = new UserAndRoleRelationEntity();
		userAndRoleRelationEntity.setUserId(userId);
		userAndRoleRelationEntity.setRoleId(roleId);
		userAndRoleRelationDao.save(userAndRoleRelationEntity);
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.authority.service.UserAndRoleRelationService#queryByUserId(java.lang.Long)    
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserAndRoleRelationEntity> queryByUserId(Long userId) {
		return userAndRoleRelationDao.queryByUserId(userId);
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.authority.service.UserAndRoleRelationService#queryByRoleId(java.lang.Long)    
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserAndRoleRelationEntity> queryByRoleId(Long roleId) {
		return userAndRoleRelationDao.queryByRoleId(roleId);
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.authority.service.UserAndRoleRelationService#deleteUserAndRoleRelation(com.zbjdl.boss.admin.authority.entity.UserAndRoleRelationEntity)    
	 */
	@Override
	public void deleteUserAndRoleRelation(Long userId, Long roleId) {
		UserAndRoleRelationEntity userAndRoleRelationEntity = new UserAndRoleRelationEntity();
		userAndRoleRelationEntity.setUserId(userId);
		userAndRoleRelationEntity.setRoleId(roleId);
		userAndRoleRelationDao.deleteUserAndRoleRelation(userAndRoleRelationEntity);
	}
	
	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.authority.service.UserAndRoleRelationService#queryByUserIdAndRoleId(java.lang.Long, java.lang.Long)    
	 */
	@Override
	public UserAndRoleRelationEntity queryByUserIdAndRoleId(Long userId,
			Long roleId) {
		UserAndRoleRelationEntity userAndRoleRelationEntity = new UserAndRoleRelationEntity();
		userAndRoleRelationEntity.setRoleId(roleId);
		userAndRoleRelationEntity.setUserId(userId);
		return (UserAndRoleRelationEntity) userAndRoleRelationDao.queryByRelation(userAndRoleRelationEntity);
	}
	
	

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.authority.service.UserAndRoleRelationService#saveAll(java.util.List)    
	 */
	@Override
	public void saveAll(
			List<UserAndRoleRelationEntity> userAndRoleRelationEntityList) {
//		userAndRoleRelationDao.batchInsert(userAndRoleRelationEntityList);	
		// TODO BATCH
		if(userAndRoleRelationEntityList!=null && !userAndRoleRelationEntityList.isEmpty()){
			for(UserAndRoleRelationEntity userAndRoleRelationEntity:userAndRoleRelationEntityList){
				userAndRoleRelationDao.save(userAndRoleRelationEntity);
			}
		}
	}

	/* (non-Javadoc)    
	 * @see com.zbjdl.boss.admin.authority.service.UserAndRoleRelationService#deleteAll(java.util.List)    
	 */
	@Override
	public void deleteAll(
			List<UserAndRoleRelationEntity> userAndRoleRelationEntityList) {
//		userAndRoleRelationDao.batchDelete(userAndRoleRelationEntityList);	
		// TODO BATCH
		if(userAndRoleRelationEntityList!=null && !userAndRoleRelationEntityList.isEmpty()){
			for(UserAndRoleRelationEntity userAndRoleRelationEntity:userAndRoleRelationEntityList){
				userAndRoleRelationDao.delete(userAndRoleRelationEntity.getId());
			}
		}
	}

	/**    
	 * userAndRoleRelationDao    
	 *    
	 * @return  the userAndRoleRelationDao   
	 */
	
	public UserAndRoleRelationDao getUserAndRoleRelationDao() {
		return userAndRoleRelationDao;
	}

	/**    
	 * 描述： 在这里描述属性的含义
	 * @param userAndRoleRelationDao the userAndRoleRelationDao to set    
	 */
	public void setUserAndRoleRelationDao(
			UserAndRoleRelationDao userAndRoleRelationDao) {
		this.userAndRoleRelationDao = userAndRoleRelationDao;
	}

}
