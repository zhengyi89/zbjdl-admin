<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色详细信息</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp"%>
</head>

<body>

<div class="content_wrapper">
  <div class="content_main fontText">
    <div class="information">
      <h1 class="fw">基本信息</h1>
      <div class="clearer"></div>
      <table class="list_info">
        <tr>
          <td class="info_tit">角色名称：</td>
          <td class="info_con"><s:property value="%{#request.roleDTO.roleName}" /></td>
          <td class="info_tit">角色状态：</td>
          <td class="info_con">
           <s:if test="#request.roleDTO.roleStatus.toString() == 'ACTIVE'.toString()">活动</s:if>
           <s:if test="#request.roleDTO.roleStatus.toString() == 'FROZEN'.toString()">已冻结</s:if>
           <s:if test="#request.roleDTO.roleStatus.toString() == 'FORBIDDEN'.toString()">已废弃</s:if>
          </td>
        </tr>
         <tr>
          <td class="info_tit">角色描述：</td>
          <td class="info_con"><s:property value="%{#request.roleDTO.description}" /></td>
          <td class="info_tit"></td>
          <td class="info_con">
          </td>
        </tr>
      </table>
    </div>
    <form action="roleDetail" id="form" method="get">
      <input type="hidden" name="roleId" value="<s:property value='roleId'/>" />
    </form>
    <eq:listable queryKey="queryRoleFunction" html="class='list'" formId="form">
      <eq:param name="roleId" value="#request.roleId" />
      <eq:col title="功能名称" value="functionName"></eq:col>
      <eq:col title="功能描述" value="description"></eq:col>
      <eq:col title="风险级别">
        <#if 'LOW' == riskLevel>低
        <#elseif 'MIDDLE' == riskLevel>中
        <#elseif 'HIGH' == riskLevel>高
        </#if>
      </eq:col>
      <eq:col title="状态">
        <#if 'ACTIVE' == functionStatus>可用
        <#elseif 'FROZEN' == functionStatus>冻结
        <#elseif 'FORBIDDEN' == functionStatus>废弃
        </#if>
      </eq:col>
      <eq:col title="菜单路径" value="functionUrl" width="600px"></eq:col>
    </eq:listable>
    <div class="clearer"></div>
  </div>
</div>
</body>
</html>
