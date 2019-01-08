<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp" %>
<script language="javascript" src="${ctxPath}/js/user/user.mgr.action.js"></script>
</head>

<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <!--search star-->
    <form action="userMgr" id="form" method="get">
      <div class="search">
        <div class="search_tit">
          <h2 class="fw fleft f14">用户查询</h2>
        </div>
        <div class="search_con">
          <ul class="fix">
           <li>
             <label class="text_tit">部门：</label>
              <div class="select_border">
                <div class="container">
                  <s:select list="departList" cssClass="select" listKey="departmentCode" listValue="departmentName" name="departmentCode" value="%{#parameters.departmentCode}"></s:select>
                </div>
              </div>
            </li>
            <li>
              <label class="text_tit">登录名称：</label>
              <s:textfield cssClass="input_text" name="loginName" value="%{#parameters.loginName}"></s:textfield>
            </li>
            <li>
              <label class="text_tit">用户名称：</label>
              <s:textfield cssClass="input_text" name="userName" value="%{#parameters.userName}"></s:textfield>
            </li>
          </ul>
          <div class="clearer"></div>
          <div class="btn">
            <input type="submit" class="btn_sure fw" value="查询"/>
            <input type="button" class="btn_cancel fw" value="清空" onclick="clearAll();"/>
          </div>
          <div class="clearer"></div>
        </div>
      </div>
    </form>
    <div class="submenu">
      <dl style="float:right;padding-right: 30px; height: 0px;">
        <dd><a href="showaddAdmin">【添加部门管理员】</a></dd>
      </dl>
    </div>
    <!--search End-->
    <div class="clearer"></div>
    <!--List stard-->
    <eq:listable queryKey="queryUser" html="class='list'" formId="form">
    <%-- <eq:param name="roleId" value="-1000" /> --%>
    <eq:param name="userId" value="%{#request.userId}" />
      <eq:col title="部门" value="departmentName"></eq:col>
      <eq:col title="登录名" escape="false">
            <a href="userInfoDetail?userId=${userId}">${messageFormater.escapeHtml(loginName!)}</a>
      </eq:col>
      <eq:col title="姓名" value="userName"></eq:col>
      <eq:col title="状态">
            <#if 'CHECK_PENDING' == userStatus>
          待审核
        </#if>
        <#if 'ACTIVE' == userStatus>
          活动
        </#if>
        <#if 'FROZEN' == userStatus>
          冻结
        </#if>
        <#if 'FORBIDDEN' == userStatus>
          废弃
        </#if>
      </eq:col>
      <eq:col title="密码管理" escape="false">
            <#if 'ACTIVE' == userStatus>
          <a href="javascript:void(0);" onclick="resetPassword(${userId},'reSetUserPassword')">重置密码</a>
        </#if>
      </eq:col>
      <eq:col title="操作" escape="false">
            <#if 'CHECK_PENDING' == userStatus>
          <a href="javascript:void(0);" onclick="">审核通过</a>
          <a href="javascript:void(0);" onclick="">审核拒绝</a>
        </#if>
        <#if 'ACTIVE' == userStatus>
          <a href="javascript:void(0);" onclick="popupPasswordDialog(${userId},'user','frozenUser')">冻结</a>
          <a href="javascript:void(0);" onclick="popupPasswordDialog(${userId},'user','deleteUser')">删除</a>
        </#if>
        <#if 'FROZEN' == userStatus>
          <a href="javascript:void(0);" onclick="popupPasswordDialog(${userId},'user','activeUser')">解冻</a>
          <a href="javascript:void(0);" onclick="popupPasswordDialog(${userId},'user','deleteUser')">删除</a>
        </#if>
      </eq:col>
    </eq:listable>
  </div>
</div>
<%@ include file="passwordDialog.jsp" %>
</body>
</html>
