/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zbjdl.boss.admin.function.entity.PortalMenu;
import com.zbjdl.common.respository.mybatis.GenericRepository;

/**
 * 
 * @author：feng
 * @since：2013-5-30 下午03:41:01
 * @version:
 */
@Repository
public interface PortalMenuDao extends GenericRepository {
	public List<PortalMenu> queryMenusByDepartment(Long departmentId);

	public List<PortalMenu> queryMenusKVByDepartment(Long departmentId);

	public List<PortalMenu> queryMenusByIdList(List<Long> menuIds);

	public List<PortalMenu> queryMenusByParentId(Long parentId);

	public Integer queryMaxSequenceByParentId(Long menuId);

	public void deleteMenus(List<Long> menuIds);

	public void deleteDepartmentMenu(Long departmentId);
}
