<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>角色管理</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/css/common/chosen.css" />
<%@ include file="/WEB-INF/pages/main/metas.jsp" %>
</head>
<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <!--search star-->
    <form action="roleMgr" id="form" method="get">
      <div class="search">
        <div class="search_tit">
          <h2 class="fw fleft f14">角色查询</h2>
        </div>
        <div class="search_con">
          <ul class="fix">
            <li>
              <label class="text_tit">角色名称：</label>
              <s:textfield name="roleName" value="%{#parameters.roleName}" cssClass="input_text"/>
            </li>
            <li>
              <label class="text_tit">角色状态：</label>
              <div class="select_border">
                <div class="container">
                  <s:select cssClass="select" name="roleStatus" value="%{#parameters.roleStatus}"
                  list="#{'':'全部','ACTIVE':'开启','FROZEN':'冻结'}"/>
                </div>
              </div>
            </li>
            <s:if test="isSuperAdmin">
            <li>
              <label class="text_tit">部门：</label>
              <div class="select_border">
                <div class="container">
                  <s:hidden name="h_departmentId" id="h_departmentId" value="%{#parameters.departmentId}"/>
                  <s:select list="departList" cssClass="select" listKey="departmentId" listValue="departmentName" id="departmentId" name="departmentId" headerKey="" headerValue="请选择" value="%{#parameters.departmentId}"/>
                </div>
              </div>
            </li>
            <li>
              <label class="text_tit">跨部门：</label>
              <p>
              <s:checkbox name="multiDept" id="multiDept" value="%{#parameters.multiDept}" fieldValue="true" style="margin-top:8px;"/>
              </p>
            </li>
            </s:if>
            <li>
              <label class="text_tit">功能名称：</label>
              <s:textfield name="functionName" value="%{#parameters.functionName}" cssClass="input_text"/>
            </li>
          </ul>
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
          <dd><a href="${ctxPath}/role/init?type=add" >【添加角色】</a></dd>
        </dl>
      </div>
    <!--search End-->
    <div class="clearer"></div>
    <!--List stard-->
    <eq:listable queryKey="queryRole" html="class='list'" formId="form">
      <eq:param name="departmentId" value="departmentId" />
      <eq:param name="isSuperAdmin" value="isSuperAdmin" />
      <eq:col title="角色名称" escape="false">
        <a href="${ctxPath}/role/roleDetail?roleId=${roleId}" >${messageFormater.escapeHtml(roleName)}</a>
      </eq:col>
      <eq:col title="状态" >
        <#if 'ACTIVE' == roleStatus>
          活动
        <#elseif 'FROZEN' == roleStatus>
          冻结
        <#elseif 'FORBID' == roleStatus>
          废弃
        </#if>
      </eq:col>
      <eq:col title="描述" value="description"/>
      <eq:col title="关联用户信息" escape="false">
        <a href="${ctxPath}/role/showUserForRole?roleId=${roleId}">用户列表</a>
      </eq:col>
	  <eq:col title="批量分配权限" escape="false">
		<a href="javascript:void(0);" onclick="batchAudit('${roleId}')">分配权限</a>
	  </eq:col>
      <eq:col title="操作" escape="false">
        <#if roleId == -1001 || roleId == -1000 || roleId == -999>
          <span>不可操作</span>
        <#else>
        <#if 'ACTIVE' == roleStatus>
          <a href="init?roleId=${roleId}&type=edit" >修改</a>
          <a href="javascript:void(0);" onclick="popupPasswordDialog(${roleId},'role','freeze')" >冻结</a>
          <a href="javascript:void(0);" onclick="popupPasswordDialog(${roleId},'role','delete')">删除</a>
        <#elseif 'FROZEN' == roleStatus>
          <a href="javascript:void(0);" onclick="popupPasswordDialog(${roleId},'role','activate')">解冻</a>
          <a href="javascript:void(0);" onclick="popupPasswordDialog(${roleId},'role','delete')">删除</a>
        </#if>
        </#if>
      </eq:col>
    </eq:listable>
  </div>
</div>
<%@ include file="passwordDialog.jsp" %>
<%@ include file="control/batchAudit.jsp" %>
<script type="text/javascript" src="${ctxPath}/js/role/role.mgr.js"></script>
<script type="text/javascript" src="${ctxPath}/js/chosen.jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('.chosen').chosen();
		$('#operators_chzn').css("width", "500px");
		if($("#departmentId").length > 0) {
			$("#departmentId").val($("#h_departmentId").val());
			if ($("#h_departmentId").val()) {
				$("#multiDept").attr("disabled",true);
			}
			$("#departmentId").change(function(){
				if($(this).val()) {
					$("#multiDept").attr("disabled",true);
				} else {
					$("#multiDept").removeAttr("disabled");
				}
			})
		}
	});
</script>
</body>
</html>