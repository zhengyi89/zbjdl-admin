<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>风控预审核管理</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp" %>
</head>
<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <!--search star-->
    <form action="${ctxPath}/workitem/queryForPreAudit" id="form" method="get">
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
              <label class="text_tit">审核内容：</label>
              <p>
                <s:textfield cssClass="input_text" name="fullContent" value="%{#parameters.fullContent}"/>
              </p>
            </li>
            <li>
              <label class="text_tit">审核状态：</label>
              <div class="select_border">
                <div class="container">
                  <s:select cssClass="select" id="workItemStatus" name="workItemStatus" value="%{#parameters.workItemStatus}"
                  list="#{'':'全部','RISK_WAITING':'风控待审核','RISK_CHECKING':'风控审核中','WAITING':'风控已审核'}"/>
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
            <li>
              <label class="text_tit">审核人：</label>
              <p>
                <s:textfield cssClass="input_text" name="approveUserName" value="%{#parameters.approveUserName}"/>
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
    <eq:listable queryKey="queryForPreAudit" html="class='list'" formId="form">
      <eq:param name="functionIds" value="#request.functionIds" />
      <eq:col title="<input type='checkbox' id='selectAll'/>" escape="false">
        <input type="checkbox" name="workitemId" value="${workitemId}"/>
      </eq:col>
      <eq:col title="申请人" value="submittedUserName"/>
      <eq:col title="申请时间"
        value="messageFormater.formatDateWithPattern(submitTime,'yyyy-MM-dd HH:mm:ss','')"/>
      <eq:col title="审核内容" value="full_content" width="700px"/>
      <eq:col title="审核状态" width="70px;">
        <#if 'RISK_WAITING' == workItemStatus>风控待审核
        <#elseif 'RISK_CHECKING' == workItemStatus>风控审核中
        <#elseif 'WAITING' == workItemStatus>风控已审核
        </#if>
        </eq:col>
      <eq:col title="审核人" value="approveUserName" width="80px"/>
      <eq:col title="审核时间">
          ${messageFormater.formatDateWithPattern(approveTime,'yyyy-MM-dd HH:mm:ss','')}
        </eq:col>
      <eq:col title="操作" escape="false">
        <#if workItemStatus == 'RISK_WAITING' || workItemStatus == 'RISK_CHECKING'>
          <a href="javascript:void(0);" onclick="lockWorkItem('${workItemId}','risk');">审 核</a>
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
              <a href="javascript:void(0)" onclick="batchPreSuccess();" class="btn_sure_a fw" ><b>批量通过</b><em></em></a>
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