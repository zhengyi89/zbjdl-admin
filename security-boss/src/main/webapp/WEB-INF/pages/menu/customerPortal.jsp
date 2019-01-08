<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/pages/main/metas.jsp"%>
<script type="text/javascript">
var topHeightConfig = '${topHeightJson}';
var lastOpenedTabConfig = '${lastOpenedTab}';
</script>
<!--if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<!--endif]-->
<script type="text/javascript" src="${ctxPath}/js/menu/portal.js?version=20131025"></script>
<link rel="stylesheet" type="text/css" href="${resourcePath}/boss/css/portal.css"/>
<title>${portalName}</title>
</head>
<body>
  <div class="header fix">
    <div class="logo">
      <a href="#"><img src="${resourcePath}/boss/images/logo.png"/></a>
    </div>
    <!-- 登录信息显示 -->
    <div class="login lh22">您好：<span>${curUser.userName}</span><span><a href="${employeePath}/loginout/logout">退出</a></span></div>
    <div class="nav">
      <input type="hidden" id="_employeePath" value="${employeePath}"/>
      <dl>
        <s:property value="menuTree" escapeHtml="false"/>
      </dl>
    </div>
  </div>
  <!-- 网站头部结束 -->
  <div class="clear"></div>
  <!-- 网站位置导航开始 -->
  <div class="main_nav">
    <dl class="subnav" style="margin-right:52px;">
    </dl>
    <a href="javascript:void(0)" title="上一页" class="roll_btn" id="prevBtn"></a>
    <a href="javascript:void(0)" title="下一页" class="roll_btn next_group" style="right:30px;" id="nextBtn"></a>
    <a href="javascript:void(0)" title="关闭所有标签" id="closeAllBtn" style="display:inline-block;position:absolute;left:auto;right:5px;font-size:21px;margin-top:-30px;padding:0;opacity:.5;filter:alpha(opacity=50);">&times;</a>
  </div>
  <div id="frame">
  </div>
</body>
</html>