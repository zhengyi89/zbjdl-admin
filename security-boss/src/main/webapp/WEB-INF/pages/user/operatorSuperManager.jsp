<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>跨部门操作员管理</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp" %>
<script language="javascript" src="${ctxPath}/js/user/user.mgr.action.js"></script>
<script type="text/javascript">
  function changeDepartment (userId) {
    MessageBoxExt.popup("departmentSelect", {
      height : 300,
      width : 400,
      title : "切换部门",
      modal : true,
      buttons : [{
        "text" : "确定",
        "click" : function () {
          var newPrimaryDepartmentId = $.trim($("#newPrimaryDepartmentId").val());
          var managerPassword = $.trim($("#managerPassword").val());
          if(newPrimaryDepartmentId.length<1){
            MessageBoxExt.alert("请选择部门！");
            return ;
          }
          if(managerPassword.length<1){
            MessageBoxExt.alert("请输入管理员密码！");
            return ;
          }
          MessageBoxExt.ajax({
            url:GV.ctxPath + '/user/changeDepartment.action',
            type:'POST',
            data : {
              "newPrimaryDepartmentId" : newPrimaryDepartmentId,
              "userId":userId,
              "password":managerPassword
            },
            style : "redirect"
          });
        }
      },{
        "text" : "取消",
        "click" : function () {
          $(this).dialog("close");
        }
      }],
      draggable : false,
      resizable : false
    })

  }
</script>
</head>

<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <form action="operatorSuperManager" id="form" method="get">
      <div class="search">
        <div class="search_tit">
          <h2 class="fw fleft f14">跨部门操作员管理</h2>
        </div>
        <div class="clearer"></div>
        <div class="search_con">
          <ul class="fix">
            <li>
              <label class="text_tit">登录名称：</label>
              <s:textfield cssClass="input_text" name="loginName" value="%{#parameters.loginName}"></s:textfield>
            </li>
            <li>
              <label class="text_tit">用户状态：</label>
              <div class="select_border">
                <div class="container">
                  <s:select cssClass="select" name="userStatus" value="%{#parameters.userStatus}"
                  list="#{'':'全部','CHECK_PENDING':'待审核','ACTIVE':'活动','FROZEN':'冻结','FORBIDDEN':'删除' }"></s:select>
                </div>
              </div>
            </li>
            <li>
              <label class="text_tit">用户名称：</label>
              <s:textfield cssClass="input_text" name="userName" value="%{#parameters.userName}"></s:textfield>
            </li>
            <li>
             <label class="text_tit">部门：</label>
              <div class="select_border">
                <div class="container">
                    <s:select list="departList" cssClass="select" listKey="departmentCode" listValue="departmentName"
                      name="departmentCode" value="%{#parameters.departmentCode}" headerKey="" headerValue="请选择"></s:select>
                  </div>
              </div>
            </li>
          </ul>
          <div class="clearer"></div>
            <div class="btn">
              <input type="submit" class="btn_sure fw" value="查询"/>
              <input type="button"  class="btn_cancel fw" value="清空" onclick="clearAll();"/>
            </div>
          <div class="clearer"></div>
        </div>
      </div>
    </form>
    <div class="clearer"></div>
    <div id="departmentSelect" style="display: none;">
    <div class="input_cont">
      <ul>
        <li>
          <label class="text_tit">切换部门：</label>
          <div class="select_border">
            <div class="container">
             <s:select list="departList" cssClass="select" listKey="departmentId" listValue="departmentName" name="newPrimaryDepartmentId" value="#request.primaryDepartmentId"></s:select>
            </div>
          </div>
        </li>
        <li>
          <label class="text_tit">管理员密码：</label>
          <p><input type="password" class="input_text" id="managerPassword" name="managerPassword" /></p>
        </li>
      </ul>
      </div>
    </div>
    <!--search End-->
    <div class="clearer"></div>
    <eq:listable queryKey="queryAllOperator" html="class='list'" formId="form">
      <eq:col title="登录名" escape="false">
            <a href="operatorInfoDetail?userId=${userId}">${messageFormater.escapeHtml(loginName!)}</a>
      </eq:col>
      <eq:col title="姓名" value="userName"></eq:col>
      <eq:col title="部门" value="departmentName"></eq:col>
      <eq:col title="状态">
        <#if 'CHECK_PENDING' == userStatus>待审核<#elseif 'ACTIVE' == userStatus>活动<#elseif 'FROZEN' == userStatus>冻结<#elseif 'FORBIDDEN' == userStatus>废弃</#if>
      </eq:col>
      <eq:col title="密码管理" escape="false">
        <#if 'ACTIVE' == userStatus>
          <a href="javascript:void(0);" onclick="resetPassword(${userId},'reSetOperatorPassword')">重置密码</a>
        </#if>
      </eq:col>
      <eq:col title="权限分配" escape="false">
        <#if 'ACTIVE' == userStatus>
          <a href="showAuthority?userId=${userId}" onclick="">权限分配</a>
        </#if>
      </eq:col>
      <eq:col title="操作" escape="false">
          <#if 'ACTIVE' == userStatus>
            <a href="javascript:void(0);" onclick="popupPasswordDialog(${userId},'user','frozenOperator')">冻结</a>
          <#elseif 'FROZEN' == userStatus>
            <a href="javascript:void(0);" onclick="popupPasswordDialog(${userId},'user','activeOperator')">解冻</a>
          </#if>
            <a href="javascript:void(0);" onclick="popupPasswordDialog(${userId},'user','deleteOperator')">删除</a>
		  <a href="javascript:void(0);" onclick="changeDepartment(${userId})">切换部门</a>
      </eq:col>
    </eq:listable>
  </div>
</div>
<%@ include file="passwordDialog.jsp" %>
</body>
</html>
