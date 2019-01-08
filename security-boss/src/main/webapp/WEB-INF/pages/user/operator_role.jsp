<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户详细信息</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp" %>
</head>

<body>

<div class="content_wrapper">
  <div class="content_main fontText">
    <!--List stard-->
      <eq:listable queryResult="queryForm" queryKey="queryOperatorRoles" html="class='list'" formId="form">
        <eq:col title="角色名" value="roleName">
            <a href="operatorInfoDetail?userId=""></a>
          </eq:col>
        <eq:col title="状态">
        <#if 'ACTIVE' == roleStatus>
          活动
        </#if>
        <#if 'FROZEN' == roleStatus>
          冻结
        </#if>
        <#if 'FORBIDDEN' == roleStatus>
          废弃
        </#if>
          </eq:col>
        <eq:col title="描述" value="description">
          </eq:col>
      </eq:listable>
    <div class="clearer"></div>
  </div>
  <div style="text-align:left">
    <a href="operatorInfoDetail?userId=<s:property value='#request.userId'/>">【详细信息查看】</a>
  </div>
</div>
<!--Body End-->
</body>
</html>
