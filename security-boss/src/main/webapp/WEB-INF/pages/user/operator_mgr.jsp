<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>操作员管理</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp" %>
</head>
<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <form action="operatorMgr" id="form" method="get">
      <div class="search">
        <div class="search_tit">
          <h2 class="fw fleft f14">操作员查询</h2>
        </div>
        <div class="clearer"></div>
        <div class="search_con">
          <ul class="fix">
            <li>
              <label class="text_tit">登录名称：</label>
              <s:textfield cssClass="input_text" name="loginName" value="%{#parameters.loginName}"/>
            </li>
            <li>
              <label class="text_tit">用户状态：</label>
              <div class="select_border">
                <div class="container">
                  <s:select cssClass="select" name="userStatus" value="%{#parameters.userStatus}"
                  list="#{'':'全部','CHECK_PENDING':'待审核','ACTIVE':'活动','FROZEN':'冻结','FORBIDDEN':'删除' }"/>
                </div>
              </div>
            </li>
            <li>
              <label class="text_tit">用户名称：</label>
              <s:textfield cssClass="input_text" name="userName" value="%{#parameters.userName}"/>
            </li>
            <s:if test="departList != null && !departList.isEmpty()">
            <li>
              <label class="text_tit">部门：</label>
              <div class="select_border">
                <div class="container">
	             <s:hidden name="h_departmentId" id="h_departmentId" value="%{primaryDepartmentId}"/>
                 <s:select list="departList" cssClass="select" headerKey="%{primaryDepartmentId}" headerValue="" listKey="departmentId" listValue="departmentName" id="departmentIdSe" name="departmentIdSe" value="%{#parameters.departmentId}"/>
              </div>
              </div>
            </li>
            </s:if>
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
    <div class="submenu">
      <dl style="float:right;padding-right: 30px; height: 0px;">
        <dd><a href="showAddOperator" onclick="">【添加操作员】</a></dd>
      </dl>
    </div>
    <!--search End-->
    <div class="clearer"></div>
    <eq:listable queryKey="queryOperator" html="class='list'" formId="form">
      <%--<eq:param name="primaryDepartmentId" value="#request.primaryDepartmentId" />--%>
	  <eq:param name="h_departmentId" value="primaryDepartmentId" />
      <eq:col title="登录名" escape="false">
            <a href="operatorInfoDetail?userId=${userId}">${messageFormater.escapeHtml(loginName!)}</a>
      </eq:col>
      <eq:col title="姓名" value="userName"/>
      <eq:col title="部门" value="departmentName"/>
      <eq:col title="部门管理员">
           <#if 1 == isDepartmentAdmin>
                             是
           <#else>
                             否
           </#if>
      </eq:col>
      <eq:col title="状态">
        <#if 'CHECK_PENDING' == userStatus>
          待审核
        <#elseif 'ACTIVE' == userStatus>
          活动
        <#elseif 'FROZEN' == userStatus>
          冻结
        <#elseif 'FORBIDDEN' == userStatus>
          废弃
        </#if>
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
        <#if departmentAdminId != userId && isDepartmentAdmin != 1 && primaryDepartmentId == departmentId>
          <#if 'ACTIVE' == userStatus>
            <a href="javascript:void(0);" onclick="popupPasswordDialog(${userId},'user','frozenOperator')">冻结</a>
            <a href="javascript:void(0);" onclick="popupPasswordDialog(${userId},'user','deleteOperator')">删除</a>
          <#elseif 'FROZEN' == userStatus>
            <a href="javascript:void(0);" onclick="popupPasswordDialog(${userId},'user','activeOperator')">解冻</a>
            <a href="javascript:void(0);" onclick="popupPasswordDialog(${userId},'user','deleteOperator')">删除</a>
          </#if>
        </#if>
      </eq:col>
    </eq:listable>
  </div>
</div>
<%@ include file="passwordDialog.jsp" %>
<script language="javascript" src="${ctxPath}/js/user/user.mgr.action.js"></script>
<script type="text/javascript">
	 $(document).ready(function () {
		$('#departmentIdSe').css("width", "500px");
		/* alert($("#h_departmentId").val()); */
			$("#departmentIdSe").val($("#h_departmentId").val());
			if ($("#h_departmentId").val()) {
				$("#departmentIdSe").attr("disabled", true);
			}
			$("#departmentIdSe").change(function () {
				if ($(this).val()) {
					alert($("#departmentIdSe").val());
					$("#h_departmentId").attr("value", $("#departmentIdSe").val());
				} else {
					$("#departmentIdSe").removeAttr("disabled");
				}
			})
	}); 
</script>
</body>
</html>