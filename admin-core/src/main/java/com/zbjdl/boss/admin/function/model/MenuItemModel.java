/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.function.model;

import java.util.Collection;
import java.util.List;

/**    
 *    
 * 类名称：MenuItemModel <br>    
 * 类描述：   菜单项<br>
 * @author：feng    
 * @since：2011-5-4 上午10:30:44 
 * @version:     
 *     
 */
public class MenuItemModel implements Comparable<MenuItemModel> { 
	
	private String url;//url字符串
	
	private String name;//菜单显示名称
	
	private int sequence;//菜单排序号
	
	private List<MenuItemModel> subMenuItems;//子菜单项
	
	/**
	 * 
	 * 描述：    检查是否可用
	 * @param utlCollection
	 */
	public void checkFunction(Collection<String> utlCollection) {
		//TODO
		
		try {
			MenuItemModel im = (MenuItemModel) this.clone();
			for (int i = 0; i < im.getSubMenuItems().size(); i ++) {
				
				MenuItemModel mim = im.getSubMenuItems().get(i);
				
				if (utlCollection.contains(mim.getUrl())) {
					mim.checkFunction(utlCollection);
				} else {
					
				}
			}
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**    
	 * url    
	 *    
	 * @return  the url   
	 */
	
	public String getUrl() {
		return url;
	}

	/**    
	 * 描述： 在这里描述属性的含义
	 * @param url the url to set    
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**    
	 * name    
	 *    
	 * @return  the name   
	 */
	
	public String getName() {
		return name;
	}

	/**    
	 * 描述： 在这里描述属性的含义
	 * @param name the name to set    
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**    
	 * sequence    
	 *    
	 * @return  the sequence   
	 */
	
	public int getSequence() {
		return sequence;
	}

	/**    
	 * 描述： 在这里描述属性的含义
	 * @param sequence the sequence to set    
	 */
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	/**    
	 * subMenuItems    
	 *    
	 * @return  the subMenuItems   
	 */
	
	public List<MenuItemModel> getSubMenuItems() {
		return subMenuItems;
	}

	/**    
	 * 描述： 在这里描述属性的含义
	 * @param subMenuItems the subMenuItems to set    
	 */
	public void setSubMenuItems(List<MenuItemModel> subMenuItems) {
		this.subMenuItems = subMenuItems;
	}

	/* (non-Javadoc)    
	 * @see java.lang.Comparable#compareTo(java.lang.Object)    
	 */
	@Override
	public int compareTo(MenuItemModel o) {
		return this.sequence - o.getSequence();
	}

}
