<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>功能管理</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp" %>
<script language="javascript" src="${ctxPath}/js/function/function.action.js"></script>
<script type="text/javascript">
$(document).ready(function(){
  $("#logNeeded").val($("#hlogNeeded").val());
  $("#checkNeeded").val($("#hcheckNeeded").val());
});
</script>
</head>
<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <!--search star-->
    <form action="functionMgr" id="form" method="get">
      <div class="search">
        <div class="search_tit">
          <h2 class="fw fleft f14">功能查询</h2>
        </div>
        <div class="clearer"></div>
        <div class="search_con">
          <ul class="fix">
            <li>
              <label class="text_tit">功能名称：</label>
              <p>
                <s:textfield name="functionName" value="%{#parameters.functionName}" cssClass="input_text"/>
              </p>
            </li>
            <li>
              <label class="text_tit">功能URL：</label>
              <p>
                <s:textfield name="functionUrl" value="%{#parameters.functionUrl}" cssClass="input_text"/>
              </p>
            </li>
	        <%--<li>--%>
		      <%--<label class="text_tit">标签：</label>--%>
	          <%--<p>--%>
			    <%--<s:textfield name="functionTag" value="%{#parameters.functionTag}" cssClass="input_text"/>--%>
	          <%--</p>--%>
	          <%--</li>--%>
            <li>
              <label class="text_tit">状态：</label>
              <div class="select_border">
                <div class="container">
                  <s:select cssClass="select" name="functionStatus" value="%{#parameters.functionStatus}"
                  list="#{'':'全部','ACTIVE':'可用','FROZEN':'冻结','FORBID':'废弃'}"/>
                </div>
              </div>
            </li>
            <li>
              <label class="text_tit">风险级别：</label>
              <div class="select_border">
                <div class="container">
                  <s:select cssClass="select" name="riskLevel" value="%{#parameters.riskLevel}"
                  list="#{'':'全部','HIGH':'高','MIDDLE':'中','LOW':'低'}"/>
                </div>
              </div>
            </li>
            <li>
              <label class="text_tit">是否记日志：</label>
              <div class="select_border">
                <div class="container">
                  <s:hidden name="hlogNeeded" id="hlogNeeded" value="%{#parameters.logNeeded}"/>
                  <s:select theme="simple" cssClass="select" name="logNeeded" value="%{#parameters.logNeeded}"
                  list="#{'':'不限','1':'是','0':'否'}"/>
                </div>
              </div>
            </li>
            <li>
              <label class="text_tit">审核级别：</label>
              <div class="select_border">
                <div class="container">
                  <s:hidden name="hcheckNeeded" id="hcheckNeeded" value="%{#parameters.checkNeeded}"/>
                  <s:select theme="simple" cssClass="select" name="checkNeeded" value="%{#parameters.checkNeeded}"
                  list="#{'':'不限','0':'不复核','1':'普通复核[可重复申请]','2':'普通复核[不可重复申请]','3':'风控预复核[可重复申请]','4':'风控预复核[不可重复申请]'}"/>
                </div>
              </div>
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
    <!--search End-->
    <div class="clearer"></div>
    <div class="submenu">
      <dl style="float:right;padding-right: 30px; height: 0px;">
        <dd><a href="initAdd" >【添加功能】</a></dd>
      </dl>
    </div>
    <!--List stard--> <!-- #session['SESSION_USERINFO'].userId -->
    <eq:listable queryKey="queryFunction" html="class='list'" formId="form">
      <eq:param name="departmentId" value="departmentId" />
      <eq:col title="功能名称" escape="false">
        <a href="showDetail?functionId=${functionId}" >${messageFormater.escapeHtml(functionName!)}</a>
      </eq:col>
      <eq:col title="风险级别">
        <#if riskLevel == 'HIGH'>
          高
        <#elseif riskLevel == 'MIDDLE'>
          中
        <#elseif riskLevel == 'LOW'>
          低
        </#if>
      </eq:col>
      <eq:col title="状态">
        <#if 'ACTIVE' == functionStatus>
          可用
        <#elseif 'FROZEN' == functionStatus>
          冻结
        <#elseif 'FORBIDDEN' == functionStatus>
          废弃
        </#if>
      </eq:col>
      <eq:col title="描述" value="description"></eq:col>
      <eq:col title="是否记日志">
        <#if logneeded?? && logneeded == '1'>是<#else>否</#if>
      </eq:col>
      <eq:col title="审核级别" value="checkneeded"></eq:col>
      <eq:col title="操作" escape="false" width="300px">
        <#if 'ACTIVE' == functionStatus>
          <a href="javascript:void(0);" onclick="popupPasswordDialog(${functionId},'function','freeze')">冻结</a>
          <a href="initUpdate?functionId=${functionId}" >修改</a>
        <#elseif 'FROZEN' == functionStatus>
          <a href="javascript:void(0);" onclick="popupPasswordDialog(${functionId},'function','activate')">解冻</a>
        </#if>
          <a href="javascript:void(0);" onclick="popupPasswordDialog(${functionId},'function','delete')">删除</a>
          <a href="${ctxPath}/notice/editNotice.action?functionId=${functionId!""}">添加升级公告</a>
        <#if LOGNEEDED?? && LOGNEEDED == '1'>
          <a href="${ctxPath}/template/showUpdateTemplate.action?functionId=${functionId!""}&templateTag=log">查看日志模板</a>
        </#if>
        <#if CHECKNEEDED?? && CHECKNEEDED != '0'>
          <a href="${ctxPath}/template/showUpdateTemplate.action?functionId=${functionId!""}&templateTag=workItem">查看双审核模板</a>
        </#if>
      </eq:col>
    </eq:listable>
    <div class="clearer"></div>
  </div>
  <%@ include file="passwordDialog.jsp" %>   <%-- <a href="initUpdate?functionId=${functionId}" >修改</a> --%>
</div>
</body>
</html>
