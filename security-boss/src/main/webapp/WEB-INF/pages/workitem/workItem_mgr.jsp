<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>审核管理</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp" %>
</head>
<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <!--search star-->
    <form action="queryWorkItem" id="form" method="get">
      <div class="search">
        <div class="search_tit">
          <h2 class="fw fleft f14">审核查询</h2>
        </div>
        <div class="clearer"></div>
        <div class="search_con">
          <ul class="fix">
            <li>
              <label class="text_tit">申请人：</label>
              <p>
                <s:textfield cssClass="input_text" name="submittedUserName" value="%{#parameters.submittedUserName}"/>
              </p>
            </li>
            <li>
              <label class="text_tit">审核人：</label>
              <p>
                <s:textfield cssClass="input_text" name="approveUserName" value="%{#parameters.approveUserName}"/>
              </p>
            </li>
            <li>
              <label class="text_tit">审核状态：</label>
              <div class="select_border">
                <div class="container">
                  <s:select cssClass="select" id="workItemStatus" name="workItemStatus" value="%{#parameters.workItemStatus}"
                  list="#{'':'全部','RISK_WAITING':'风控待审核','RISK_CHECKING':'风控审核中','WAITING':'待审核','CHECKING':'审核中','SUCESS':'审核通过','REFUSE':'审核拒绝','FORBIDDEN':'废弃','ERROR':'审核异常' }"/>
                </div>
              </div>
            </li>
            <li>
              <label class="text_tit">执行状态：</label>
              <div class="select_border">
                <div class="container">
                  <s:select cssClass="select" id="invocatResult" name="invocatResult" value="%{#parameters.invocatResult}"
                  list="#{'':'全部','SUCCESS':'成功','FAIL':'失败','ACTIONEXCEPTION':'业务异常','SYSEXCEPTION':'系统异常','UNDEFINED':'未知'}"/>
                </div>
              </div>
            </li>
            <li class="between">
              <label class="text_tit">申请时间：</label>
              <p>
                <s:textfield readonly="true" cssClass="input_text" id="submitStartTime" name="submitStartTime" value="%{#parameters.submitStartTime}"/>
                <span>至</span>
                <s:textfield readonly="true" cssClass="input_text" id="submitEndTime" name="submitEndTime" value="%{#parameters.submitEndTime}"/>
              </p>
            </li>
            <li class="between">
              <label class="text_tit">审核时间：</label>
              <p>
                <s:textfield readonly="true" cssClass="input_text" id="approveStartTime" name="approveStartTime" value="%{#parameters.approveStartTime}"/>
                <span>至</span>
                <s:textfield readonly="true" cssClass="input_text" id="approveEndTime" name="approveEndTime" value="%{#parameters.approveEndTime}"/>
              </p>
            </li>
            <li>
              <label class="text_tit">审核内容：</label>
              <p>
                <s:textfield cssClass="input_text" name="fullContent" value="%{#parameters.fullContent}"/>
              </p>
            </li>
            <li>
              <label class="text_tit">审核类型：</label>
              <p>
                <s:textfield cssClass="input_text" name="workItemType" value="%{#parameters.workItemType}"/>
              </p>
            </li>
          </ul>
          <div class="clearer"></div>
            <div class="btn">
              <input type="submit" class="btn_sure fw" value="查 询"/>
              <input type="button"  class="btn_cancel fw" value="清 空" onclick="clearAll();"/>
            </div>
          <div class="clearer"></div>
        </div>
      </div>
    </form>
      <!--search End-->
    <div class="clearer"></div>
    <eq:listable queryKey="queryRecheckRecord" html="class='list'" formId="form">
      <eq:param name="functionIds" value="#request.functionIds" />
      <eq:col title="<input type='checkbox' id='selectAll'/>" escape="false">
        <input type="checkbox" name="workitemId" value="${workitemId}"/>
      </eq:col>
      <eq:col title="申请人" value="submittedUserName"/>
      <eq:col title="申请时间"
        value="messageFormater.formatDateWithPattern(submitTime,'yyyy-MM-dd HH:mm:ss','')"/>
      <eq:col title="审核内容" value="full_content" width="450px"/>
      <eq:col title="审核状态" width="70px;">
        <#if 'RISK_WAITING' == workItemStatus>风控待审核
        <#elseif 'RISK_CHECKING' == workItemStatus>风控审核中
        <#elseif 'WAITING' == workItemStatus>
          <#if workItemType?? && 'RISK' == workItemType>风控已审核<#else>待审核</#if>
        <#elseif 'REFUSE' == workItemStatus>已拒绝
        <#elseif 'CHECKING' == workItemStatus>审核中
        <#elseif 'SUCESS' == workItemStatus>审核通过
        <#elseif 'FORBIDDEN' == workItemStatus>已撤销
        <#elseif 'ERROR' == workItemStatus>审核异常
        </#if>
        </eq:col>
      <eq:col title="审核人" width="80px">
        <#if 'REFUSE' == workItemStatus>
          ${rejectUserName}
        <#elseif approveUserName??>
          ${approveUserName!""}
          <#if 'WAITING' == workItemStatus && 'RISK' == workItemType>
            (风控)
          </#if>
        </#if>
        </eq:col>
      <eq:col title="审核时间">
        <#if 'REFUSE' == workItemStatus>
          ${messageFormater.formatDateWithPattern(rejectTime,'yyyy-MM-dd HH:mm:ss','')}
        <#elseif approveTime??>
          ${messageFormater.formatDateWithPattern(approveTime,'yyyy-MM-dd HH:mm:ss','')}
        </#if>
        </eq:col>
      <eq:col title="拒绝原因" value="REJECTCAUSE" width="100px"/>
      <eq:col title="执行状态">
        <#if 'SUCESS' == workItemStatus || 'ERROR' == workItemStatus>
          <#if 'SUCCESS' == INVOCATRESULT>成功
          <#elseif 'FAIL' == INVOCATRESULT>失败
          <#elseif 'ACTIONEXCEPTION' == INVOCATRESULT>业务异常
          <#elseif 'SYSEXCEPTION' == INVOCATRESULT>系统异常
          <#else>未知
          </#if>
        </#if>
        </eq:col>
      <eq:col title="执行结果" value="RESULTCOMMENT" width="150px"/>
      <eq:col title="操作" escape="false">
        <#if workItemStatus == 'WAITING' || workItemStatus == 'CHECKING'>
          <a href="javascript:void(0);" onclick="lockWorkItem('${workItemId}','audit');">审 核</a>
        <#else>
          <a href="javascript:void(0);" onclick="viewWorkItem('${workItemId}','show');">查 看</a>
        </#if>
        </eq:col>
    </eq:listable>
      <div id="hidden_operat">
          <div class="operat" style="bottom:10px; display:block;height:30px;">
              <ul>
                  <li>
            <span id="button">
              <a href="javascript:void(0)" onclick="batchSuccess();" class="btn_sure_a fw" ><b>批量通过</b><em></em></a>
              <a href="javascript:void(0)" onclick="batchRefuse();" class="btn_sure_a fw" ><b>批量拒绝</b><em></em></a>
            </span>
                  </li>
              </ul>
          </div>
      </div>
  </div>
<%@ include file="/WEB-INF/pages/workitem/control/batchRefuse.jsp"%>
</div>
<div id="workItemInfo" style="display:none;"></div>
<script type="text/javascript" src="${ctxPath}/js/workitem/audit.js"></script>
</body>
</html>
