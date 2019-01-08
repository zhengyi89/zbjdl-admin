<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色管理</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp" %>

</head>

<body>
<div class="content_wrapper">
      <div class="content_main fontText">
        <!--List stard-->
        <div class="submenu" style="float:right;">
          <dl class="topTab">
            <dd class="txt6 current"><a href="showUserForRole?roleId=<s:property value='roleId'/>">关联用户列表</a></dd>
          </dl>
        </div>
        <div class="clearer"></div>
        <form action="showUserForRole" id="form" method="post">
              <input type="hidden" name="roleId" value="<s:property value='roleId'/>"/>
        </form>
        <eq:listable queryKey="queryUserForRole" html="class='list'" formId="form">
          <eq:param name="roleId" value="#request.roleId" />
          <eq:col title="登录名" value="loginName">
          </eq:col>
          <eq:col title="姓名" value="userName">
          </eq:col>
          <eq:col title="部门" value="departmentName"></eq:col>
          <eq:col title="是否管理员" >
        <#if '1' == isAdmin>
          是
        </#if>

        <#if '0' == isAdmin>
          否
        </#if>
          </eq:col>
          <eq:col title="用户状态" >
        <#if 'ACTIVE' == userStatus>
          活动
        </#if>
        <#if 'FROZEN' == userStatus>
          冻结
        </#if>
        <#if 'FORBID' == userStatus>
          废弃
        </#if>
          </eq:col>
        </eq:listable>
        <div class="clearer"></div>
      </div>
</div>
</body>
</html>
