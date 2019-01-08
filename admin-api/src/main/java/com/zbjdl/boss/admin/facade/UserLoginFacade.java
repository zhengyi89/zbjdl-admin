/**    
 * 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 *    
 */
package com.zbjdl.boss.admin.facade;

import java.util.Collection;

import com.zbjdl.boss.admin.dto.sso.SSOLoginInfoDTO;
import com.zbjdl.boss.admin.sso.exception.SSOException;

/**
 * 
 * 类名称：SSOFacade <br>
 * 类描述：单点登录门面接口<br>
 * 
 * @author：feng
 * @since：2011-7-20 上午11:44:29
 * @version: 1.1<br/>
 *           跨域单点登录重构<br/>
 *           重构人：feng <br/>
 *           重构日期：2012-9-26 上午13:44:29<br/>
 */
public interface UserLoginFacade {
	/**
	 * 校验cookie安全信息并返回登陆凭据和userId
	 * 
	 * @param cookieSecurityInfo
	 *            ：cookie中的安全信息
	 * @return：
	 * @throws SSOException
	 */
	public String checkSecurityInfo(String cookieSecurityInfo)
			throws SSOException;

	/**
	 * 创建登录凭据及cookie安全信息
	 * 
	 * @param userId
	 *            ：用户ID
	 * @param loginIp
	 *            ：登录Ip
	 * @return 登录凭据及cookie安全信息
	 */
	public String[] createTokenAndSecurityInfo(String userId, String loginIp);

	/**
	 * 通过token获取登陆信息
	 * 
	 * @param token
	 *            ：登陆凭据
	 * @return
	 * @throws SSOException
	 */
	public SSOLoginInfoDTO getUserIdByToken(String token) throws SSOException;

	/**
	 * 验证登陆状态
	 * 
	 * @param userId
	 *            ：用户ID
	 * @param loginId
	 *            ：登陆ID
	 * @return
	 * @throws SSOException
	 *             ：登陆异常
	 */
	public boolean validate(String userId, String loginId);

	/**
	 * 退出登录
	 * 
	 * @param cookieInfoStr
	 */
	public void logoutCookieInfo(String cookieInfoStr);

	/**
	 * 使用userId及loginId退出登录
	 * 
	 * @param userId
	 *            ：用户ID
	 * @param loginId
	 *            ：登录ID
	 */
	public void logout(String userId, String loginId);

	/**
	 * 解决二代hessian不支持函数重载，单独登录一个接口供二代hessian调用
	 * 
	 * @param userId
	 *            ：用户ID
	 * @param loginId
	 *            ：登录ID
	 */
	public void logoutByUserIdAndLoginId(String userId, String loginId);

	/**
	 * 查询同一个账号下是否有他人登录
	 * 
	 * @param userId
	 *            ：登录信息
	 * @return
	 */
	public Collection<SSOLoginInfoDTO> findLoginInfoByUserId(String cookieInfo);

}
