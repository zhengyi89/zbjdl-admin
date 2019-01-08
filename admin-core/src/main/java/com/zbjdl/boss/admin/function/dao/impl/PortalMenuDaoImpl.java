package com.zbjdl.boss.admin.function.dao.impl;
//package com.zbjdl.boss.admin.function.dao.impl;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.zbjdl.boss.admin.function.entity.PortalMenu;
//import com.zbjdl.boss.admin.repository.PortalMenuDao;
//import com.zbjdl.common.persistence.mybatis.GenericDaoDefault;
//
///**
// * 
// * @author：feng    
// * @since：2013-5-30 下午03:47:38 
// * @version:
// */
//public class PortalMenuDaoImpl extends GenericDaoDefault<PortalMenu> implements
//		PortalMenuDao {
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<PortalMenu> queryMenusByDepartment(Long departmentId) {
//		return (List<PortalMenu>) this.query("queryMenusByDepartment",
//				departmentId);
//	}
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public List<PortalMenu> queryMenusKVByDepartment(Long departmentId) {
//        Map<String,Object> params = new HashMap<String, Object> ();
//        params.put("departmentId", departmentId);
//        return (List<PortalMenu>) this.query("queryMenusKVByDepartment",
//                params);
//    }
//
//    @SuppressWarnings("unchecked")
//	@Override
//	public List<PortalMenu> queryMenusByIdList(List<Long> menuIds) {
//        Map<String,Object> params = new HashMap<String, Object> ();
//        params.put("menuIds", menuIds);
//		return this.query("queryMenusByIdList", params);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<PortalMenu> queryMenusByParentId(Long parentId) {
//		return this.query("queryMenusByParentId", parentId);
//	}
//
//	@Override
//	public Integer queryMaxSequenceByParentId(Long menuId) {
//		return (Integer) this.queryOne("queryMaxSequenceByParentId", menuId);
//	}
//
//	@Override
//	public void deleteMenus(List<Long> menuIds) {
//		Map<String,Object> params = new HashMap<String, Object> ();
//		params.put("menuIds", menuIds);
//		this.delete("deleteMenus", params);
//	}
//
//	@Override
//	public void deleteDepartmentMenu(Long departmentId) {
//		this.delete("deleteDepartmentMenu", departmentId);
//	}
//
//}
