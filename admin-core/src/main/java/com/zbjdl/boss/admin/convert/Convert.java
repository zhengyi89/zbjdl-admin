/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.convert;

import java.util.List;

import com.zbjdl.boss.admin.dto.basic.BaseDTO;
import com.zbjdl.common.persistence.Entity;

/**    
 *    
 * 类名称：Convert <br>    
 * 类描述：DTO与实体之间的转换工具类<br>
 * @author：feng    
 * @since：2011-6-30 下午02:35:11 
 * @version:     
 *     
 */
public interface Convert<T extends BaseDTO, E extends Entity<?>> {
	
	/**
	 * 
	 * 描述：    DTO转Entity
	 * @param dto	数据DTO
	 * @return
	 */
	public E convert(T dto);
	
	/**
	 * 
	 * 描述：    Entity转DTO
	 * @param entity	数据实体
	 * @return
	 */
	public T convert(E entity);
	
	/**
	 * Title：将entity实体List转化为dto List
	 * @param es
	 * @return
	 * added by：xuebo.yang
	 */
	public List<T> convertToDtos(List<E> es);
	
	/**
	 * Title：将dto List
	 * @param ts
	 * @return
	 * added by：xuebo.yang
	 */
	public List<E> convertToEntities(List<T> ts);

}
