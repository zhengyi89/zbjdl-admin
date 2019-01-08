/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.dto.sso;

import com.zbjdl.boss.admin.dto.basic.BaseDTO;
import com.zbjdl.boss.admin.user.dto.UserDTO;

/**    
 *    
 * 类名称：UserInfoDTO <br>    
 * 类描述：   <br>
 *     
 */
public class UserInfoDTO extends BaseDTO {
	
	private static final long serialVersionUID = -6878836740663377366L;

	private UserDTO userDTO;//用户DTO信息对象
	
	private Boolean isLogin;//是否登录标识

	   
	/**    
	 * 创建一个新的实例 UserInfoDTO.    
	 *    
	 * @param userDTO
	 * @param isLogin    
	 */
	public UserInfoDTO(UserDTO userDTO, Boolean isLogin) {
		this.userDTO = userDTO;
		this.isLogin = isLogin;
	}

	/**    
	 * userDTO    
	 *    
	 * @return  the userDTO   
	 */
	
	public UserDTO getUserDTO() {
		return userDTO;
	}

	/**    
	 * 描述： 用户DTO信息对象
	 * @param userDTO the userDTO to set    
	 */
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	/**    
	 * isLogin    
	 *    
	 * @return  the isLogin   
	 */
	
	public Boolean getIsLogin() {
		return isLogin;
	}

	/**    
	 * 描述： 是否登录标识
	 * @param isLogin the isLogin to set    
	 */
	public void setIsLogin(Boolean isLogin) {
		this.isLogin = isLogin;
	}

}
