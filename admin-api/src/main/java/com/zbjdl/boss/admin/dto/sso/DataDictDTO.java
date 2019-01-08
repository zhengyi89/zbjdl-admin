
package com.zbjdl.boss.admin.dto.sso;

/**
 * 
 * 类名称：DataDictDTO <br>
 * 类描述： 数据字典<br>
 * 
 * 
 */
public interface DataDictDTO {

	public static final String SESSION_USERINFO = "SESSION_USERINFO";

	public static final String COOKIE_USERINFO = "COOKIE_USERINFO";

	public static final String RESOURCE_KEY = "frame_resourceid";

	public static final String FORM_KEY = "frame_form";

	public static final String BELONG_SYS = "belong_sys";

	public static final String SECURITY_CODE = "REMOTE_KAPTCHA_SESSION_KEY";

	/**
	 * COOKIE存放路径
	 */
	public static final String COOKIE_PATH = "/";

	/**
	 * COOKIE有效时间
	 */
	public static final int COOKIE_MAX_AGE = 20 * 60;// 有效时间20分钟

	/**
	 * COOKIE中用户信息分隔符
	 */
	public static final String COOKIE_USERINFO_SPLIT = "_";

	/**
	 * 运营后台静态资源路径-配置键
	 */
	public static final String CONF_KEY_STATIC_RESOURCE_PATH = "boss_static_resources_path";

	/**
	 * 运营后台路径-配置键
	 */
	public static final String CONF_KEY_ADMIN_BOSS_PATH = "admin_boss_base_uri";

	/**
	 * 操作员ID
	 */
	public static final String SESSION_USERID = "SESSION_USERID";

	/**
	 * 双权限审核审核人ID
	 */
	public static final String SESSION_AUDIT_USERID = "SESSION_AUDIT_USERID";

	/**
	 * 双权限审核审核人信息
	 */
	public static final String SESSION_AUDIT_USERINFO = "SESSION_AUDIT_USERINFO";
	
	/**
	 * 用户功能权限列表
	 */
	public static final String SESSION_USER_FUNCTION = "SESSION_USER_FUNCTION";
	
	/**
	 * 用户菜单缓存
	 */
	public static final String SESSION_USER_MENU = "menuTree";
}
