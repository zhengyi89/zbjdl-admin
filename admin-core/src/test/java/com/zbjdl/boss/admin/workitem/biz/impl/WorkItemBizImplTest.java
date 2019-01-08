package com.zbjdl.boss.admin.workitem.biz.impl;

import org.apache.commons.lang.StringUtils;

public class WorkItemBizImplTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String document = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
				+ "<head>\n"
				+ "  <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />\n"
				+ "  <link rel=\"stylesheet\" type=\"text/css\" href=\"${resourcePath!\"http://resource.yunpal.in:8080/static\"}/boss/css/global.css\" />\n"
				+ "  <link rel=\"stylesheet\" type=\"text/css\" href=\"${resourcePath!\"http://resource.yunpal.in:8080/static\"}/boss/css/layout.css\" />\n"
				+ "  <link rel=\"stylesheet\" type=\"text/css\" href=\"${resourcePath!\"http://resource.yunpal.in:8080/static\"}/boss/css/master.css\" />\n"
				+ "  <title>系统运行异常</title>\n"
				+ "  <script type=\"text/javascript\" >\n"
				+ "     window.onload = function(){\n"
				+ "       setTimeout(function(){\n"
				+ "         history.go(-1);\n"
				+ "       },5000);\n"
				+ "     };\n"
				+ "  </script>\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "<div class=\"weberror\">\n"
				+ "  <div class=\"web_tit\">\n"
				+ "    <div class=\"web_l\"></div>\n"
				+ "    <h2>温馨提示</h2>\n"
				+ "    <div class=\"web_r\"></div>\n"
				+ "  </div>\n"
				+ "  <div class=\"web_con\">\n"
				+ "    <div class=\"web_left\"><img src=\"${resourcePath!\"\"}/boss/images/u129_original.png\" width=\"96\" height=\"96\" /></div>\n"
				+ "    <div class=\"web_right\">\n"
				+ "      <#if einfo??>\n"
				+ "      <h3>系统运行异常,请与管理员联系。</h3>\n"
				+ "      <p>异常编号：${einfo.exceptionId!\"\"}</p>\n"
				+ "      <p>异常类信息：${einfo.exceptionClassInfo!\"\"}|${einfo.exceptionMessage!\"\"}</p>\n"
				+ "      <p>异常描述：${einfo.exceptionText!\"\"}</p>\n"
				+ "      <#else>\n"
				+ "      <h3>${message!\"系统异常，请与管理员联系\"}</h3>\n"
				+ "      </#if>\n"
				+ "      <p>系统将在 5 秒钟后返回上一页</p>\n"
				+ "      <p class=\"kongbai\"></p>\n"
				+ "      <p>您现在可以返回：<span><a href=\"#\" onclick=\"javascript:history.back();\">上一页</a></span></p>\n"
				+ "    </div>\n"
				+ "  </div>\n"
				+ "</div>\n"
				+ "</body>\n"
				+ "</html>\n";
		System.out.println(getErrorMessage(document));
	}

	private static String getErrorMessage(String document) {
		// TODO:错误页面改版后得对应修改才能取得错误信息
		String message = StringUtils.substringBetween(document, "<h3>",
				"<p>系统将在");
		if (StringUtils.isBlank(message)) {
			message = "Action调用异常";
		} else {
			message = message.replaceAll("</?[^>]+>", "");
			message = message.replaceAll("[ \n\t]+", " ");
		}
		return message;
	}

}
