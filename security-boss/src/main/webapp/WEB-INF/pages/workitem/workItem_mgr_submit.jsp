<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>审核管理</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp" %>
<script type="text/javascript" src="${ctxPath}/js/workitem/audit.js"></script>
</head>
<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <!--search star-->
    <form action="queryWorkItemForSubmit" id="form" method="get">
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
                  <s:select cssClass="select" name="workItemStatus" value="%{#parameters.workItemStatus}"
                  list="#{'':'全部','WAITING':'待审核','CHECKING':'审核中','SUCESS':'审核通过','REFUSE':'审核拒绝','FORBIDDEN':'废弃','ERROR':'审核异常' }"/>
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
      <eq:listable queryKey="queryRecheckRecordForSubmit" html="class='list'" formId="form">
      <eq:param name="submittedBy" value="#session['SESSION_USERINFO'].userId" />
      <eq:col title="申请人" value="submittedUserName"/>
      <eq:col title="申请时间" value="messageFormater.formatDateWithPattern(submitTime,'yyyy-MM-dd HH:mm:ss','')"/>
      <eq:col title="审核人">
        <#if 'REFUSE' == workItemStatus>
          ${rejectUserName}
        <#else>
          ${approveUserName!""}
        </#if>
      </eq:col>
      <eq:col title="审核内容" value="full_content" width="550px"/>
      <eq:col title="审核状态">
          <#if 'WAITING' == workItemStatus>
          待审核
        </#if>
        <#if 'REFUSE' == workItemStatus>
          已拒绝
        </#if>
        <#if 'CHECKING' == workItemStatus>
          审核中
        </#if>
        <#if 'SUCESS' == workItemStatus>
          审核通过
        </#if>
        <#if 'FORBIDDEN' == workItemStatus>
          废弃
        </#if>
        <#if 'ERROR' == workItemStatus>
          审核异常
        </#if>
      </eq:col>
      <eq:col title="审核时间" >
        <#if 'REFUSE' == workItemStatus>
          ${messageFormater.formatDateWithPattern(rejectTime,'yyyy-MM-dd HH:mm:ss','')}
        <#elseif approveTime??>
          ${messageFormater.formatDateWithPattern(approveTime,'yyyy-MM-dd HH:mm:ss','')}
        </#if>
      </eq:col>
      <eq:col title="操作" escape="false">
            <a href="javascript:void(0);" onclick="viewWorkItem('${workItemId}','submit');" >查 看</a>
      </eq:col>
    </eq:listable>
  </div>
</div>
</body>
</html>