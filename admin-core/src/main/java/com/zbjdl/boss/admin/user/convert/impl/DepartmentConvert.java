/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.user.convert.impl;


import java.util.ArrayList;
import java.util.List;

import com.zbjdl.boss.admin.convert.Convert;
import com.zbjdl.boss.admin.user.dto.DepartmentDTO;
import com.zbjdl.boss.admin.user.entity.DepartmentEntity;

/**    
 *    
 * 类名称：DepartmentConvert <br>    
 * 类描述：  部门信息转换器<br>
 * @author：feng    
 * @since：2011-6-22 下午04:41:53 
 * @version: 1.0    
 *     
 */
public class DepartmentConvert implements Convert<DepartmentDTO, DepartmentEntity> {
	
	/**
	 * 
	 * 描述：    部门DTO转Entity
	 * @param departmentDTO
	 * @return
	 */
	public DepartmentEntity convert(DepartmentDTO departmentDTO){
		if(departmentDTO == null){
			return null;
		}
		DepartmentEntity departmentEntity = new DepartmentEntity();
		org.springframework.beans.BeanUtils.copyProperties(departmentDTO, departmentEntity);
		return departmentEntity;
		
	}
	
	/**
	 * 
	 * 描述：    部门Entity转部门DTO
	 * @param departmentEntity
	 * @return
	 */
	public DepartmentDTO convert(DepartmentEntity departmentEntity){
		if(departmentEntity == null){
			return null;
		}
		DepartmentDTO departmentDTO = new DepartmentDTO();
		org.springframework.beans.BeanUtils.copyProperties(departmentEntity,departmentDTO);
		return departmentDTO;
	}

	@Override
	public List<DepartmentDTO> convertToDtos(List<DepartmentEntity> es) {
		List<DepartmentDTO> dtoList = new ArrayList<DepartmentDTO>();
		if(es !=null && !es.isEmpty()){
			for(DepartmentEntity de : es){
				DepartmentDTO dd = convert(de);
				dtoList.add(dd);
			}
		}
		return dtoList;
	}

	@Override
	public List<DepartmentEntity> convertToEntities(List<DepartmentDTO> ts) {
		// TODO Auto-generated method stub
		return null;
	}

}
