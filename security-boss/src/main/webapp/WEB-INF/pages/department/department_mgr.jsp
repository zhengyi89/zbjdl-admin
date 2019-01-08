<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>部门管理</title>
<link rel="stylesheet" type="text/css" href="${ctxPath}/css/common/chosen.css" />
<%@ include file="/WEB-INF/pages/main/metas.jsp" %>
<script type="text/javascript" src="${ctxPath}/js/dept/dept.action.js"></script>
</head>
<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <!--search star-->
    <form action="departMentMgr" id="form" method="get">
      <div class="search">
        <div class="search_tit">
          <h2 class="fw fleft f14">部门查询</h2>
        </div>
        <div class="clearer"></div>
        <div class="search_con">
          <ul class="fix">
            <li>
              <label class="text_tit">编号：</label>
              <p>
                <s:textfield cssClass="input_text" name="departmentCode" value="%{#parameters.departmentCode}"/>
              </p>
            </li>
            <li>
              <label class="text_tit">名称：</label>
              <p>
                <s:textfield cssClass="input_text" name="departmentName" value="%{#parameters.departmentName}"/>
              </p>
            </li>
            <li>
              <label class="text_tit">状态：</label>
                <div class="select_border">
                  <div class="container">
                    <s:select cssClass="select" list="#{'':'全部','ACTIVE':'活动','FORBIDDEN':'废弃' }" name="departmentStatus" value="%{#parameters.departmentStatus}"/>
                  </div>
                </div>
            </li>
            <li>
              <label class="text_tit">描述：</label>
              <p>
                <s:textfield cssClass="input_text" name="departmentDesc" value="%{#parameters.departmentDesc}"/>
              </p>
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
    <div class="clearer"></div>
    <div class="submenu">
      <dl style="float:right;padding-right: 30px; height: 0px;">
        <dd><a href="init?type=add" >【添加部门】</a></dd>
      </dl>
    </div>
    <!--search End-->
    <div class="clearer"></div>
    <!--List stard-->
    <eq:listable queryKey="queryDepartment" html="class='list'" formId="form">
      <eq:col title="部门编号" value="departmentCode"/>
      <eq:col title="名称" value="departmentName"/>
      <eq:col title="状态">
        <#if 'ACTIVE' == departmentStatus>
          活动
        </#if>
        <#if 'FORBIDDEN'== departmentStatus>
          废弃
        </#if>
      </eq:col>
      <eq:col title="展示形式">
        <#if isPortal == 1>
          Portal
        <#else>
          --
        </#if>
        </eq:col>
      <eq:col title="描述" value="departmentDesc"/>
      <eq:col title="操作" escape="false">
        <#if 'ACTIVE' == departmentStatus>
          <a href="javascript:void(0);" onclick="popupPasswordDialog(${departmentId},'depart','delete')">删除</a>
          <a href="init?departmentId=${departmentId}&type=edit" >修改</a>
	      <a href="javascript:void(0);" onclick="migrateDepartment(${departmentId})">迁移</a>
        </#if>
      </eq:col>
    </eq:listable>
  </div>
</div>
<%@ include file="control/passwordDialog.jsp" %>
<%@ include file="control/migrateDepartment.jsp" %>
<script type="text/javascript" src="${ctxPath}/js/chosen.jquery.min.js"></script>
<script type="text/javascript">
	$(function () {
		$('.chosen').chosen();
	});
</script>
</body>
</html>
