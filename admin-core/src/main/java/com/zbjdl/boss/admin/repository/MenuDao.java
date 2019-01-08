/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zbjdl.boss.admin.function.entity.MenuEntity;
import com.zbjdl.common.respository.mybatis.GenericRepository;

/**
 * 
 * 类名称：MenuDao <br>
 * 类描述：菜单DAO接口 <br>
 * 
 * @author：feng
 * @since：2011-7-4 下午01:34:03
 * @version:
 * 
 */
@Repository
public interface MenuDao extends GenericRepository {

	Integer hasChildrenMenus(Long menuID);

	Integer queryMaxSequenceByParentId(Long parentId);

	List<MenuEntity> findList(MenuEntity queryEntity);

	List<MenuEntity> queryMenusByFunctionIds(List<Long> functionIds);

	List<MenuEntity> findAll();

	List<MenuEntity> queryMenusByUserId(Long userId);

}
