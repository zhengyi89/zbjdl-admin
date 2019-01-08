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

<!--Header-->
<div class="content_wrapper">
<div class="content_main fontText">
  <div class="information">
    <h1 class="fw">基本信息</h1>
    <div class="clearer"></div>
    <table class="list_info">
        <tr>
          <td class="info_tit">所属部门：</td>
          <td class="info_con"><s:property value="%{#request.departmentDTO.departmentName}"/></td>
          <td class="info_tit">用户登录名：</td>
          <td class="info_con"><s:property value="%{#request.userDTO.loginName}"/></td>
        </tr>
        <tr>
          <td class="info_tit">用户姓名：</td>
          <td class="info_con"><s:property value="%{#request.userDTO.userName}"/></td>
          <td class="info_tit">用户状态：</td>
          <td class="info_con">
          <s:if test="#request.userDTO.userstatus.toString() == 'ACTIVE'.toString()">活动</s:if>
          <s:if test="#request.userDTO.userstatus.toString() == 'CHECK_PENDING'.toString()">待审核</s:if>
          <s:if test="#request.userDTO.userstatus.toString() == 'FROZEN'.toString()">已冻结</s:if>
          <s:if test="#request.userDTO.userstatus.toString() == 'FORBIDDEN'.toString()">已废弃</s:if>
          </td>
        </tr>
        <tr>
          <td class="info_tit">邮箱：</td>
          <td class="info_con"><s:property value="%{#request.userDTO.email}"/></td>
          <td class="info_tit">手机：</td>
          <td class="info_con"><s:property value="%{#request.userDTO.mobile}"/></td>
        </tr>
        <tr>  
          <td class="info_tit">部门管理员：</td>
          <td class="info_con">${userType=='1'?'是':'否' }</td>
          <td class="info_tit">创建时间：</td>
          <td class="info_con"><s:property value="%{#request.userDTO.createTime}"/></td>
        </tr>
        <tr>
          <td class="info_tit">角色</td>
          <td class="info_con"><a href="queryOperatorRoles?userId=<s:property value='#request.userDTO.userId'/>">查看角色信息</a></td>
          <td class="info_tit"></td>
          <td class="info_con"></td>
        </tr>
      </table>
    </div>
    <div class="clearer"></div>
    <div class="btn">
      <input type="button" class="btn_cancel fw" value="返回" onclick="javascript:history.back();" />
    </div>
  </div>
  <div class="clearer"></div>
</div>
</body>
</html>
