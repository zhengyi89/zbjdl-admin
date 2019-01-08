<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>升级公告管理</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp" %>
<script type="text/javascript" src="${ctxPath}/js/notice/notice_mgr.js"></script>
<script type="text/javascript">
$(document).ready(function(){
  DatePickerExt.between("startTime", "endTime");
});
</script>
</head>

<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <!--search star-->
    <form action="${ctxPath}/notice/noticeMgr.action" id="form" method="get">
      <div class="search">
        <div class="search_tit">
          <h2 class="fw fleft f14">公告查询</h2>
        </div>
        <div class="search_con">
          <ul class="fix">
            <li>
              <label class="text_tit">功能ID：</label>
              <s:textfield name="functionId" value="%{#parameters.functionId}" cssClass="input_text"></s:textfield>
            </li>
            <li>
              <label class="text_tit">功能名：</label>
              <s:textfield name="functionName" value="%{#parameters.functionName}" cssClass="input_text"></s:textfield>
            </li>
            <li>
              <label class="text_tit">公告内容：</label>
              <s:textfield name="content" value="%{#parameters.content}" cssClass="input_text"></s:textfield>
            </li>
            <li>
              <label class="text_tit">操作员：</label>
              <s:textfield name="operator" value="%{#parameters.operator}" cssClass="input_text"></s:textfield>
            </li>
            <li>
              <label class="text_tit">OA单号：</label>
              <s:textfield name="oaOrderNo" value="%{#parameters.oaOrderNo}" cssClass="input_text"></s:textfield>
            </li>
            <li class="between">
              <label class="text_tit">升级时间：</label>
              <p>
                <s:textfield readonly="true" cssClass="input_text" id="startTime" name="startTime" value="%{#parameters.startTime}"/>
                <span>至</span>
                <s:textfield readonly="true" cssClass="input_text" id="endTime" name="endTime" value="%{#parameters.endTime}"/>
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
    <div class="submenu">
        <dl style="float:right;padding-right: 30px; height: 0px;">
          <dd><a href="${ctxPath}/notice/editNotice.action" >【添加公告】</a></dd>
        </dl>
      </div>
    <!--search End-->
    <div class="clearer"></div>
    <!--List stard-->
    <eq:listable queryKey="queryUpgradeNotice" queryService="noticeQueryService" html="class='list'"
      formId="noticeForm">
      <eq:param name="departmentId" value="departmentId" />
      <eq:col title="功能ID" value="FUNCTIONID" width="5%"></eq:col>
      <eq:col title="功能名" value="FUNCTIONNAME" width="15%"></eq:col>
      <eq:col title="升级内容" value="CONTENT"></eq:col>
      <eq:col title="OA单号" value="OA_ORDER_NO" width="5%"></eq:col>
      <eq:col title="操作员" value="OPERATOR" width="5%"></eq:col>
      <eq:col title="升级时间" value="messageFormater.formatDateWithPattern(UPGRADE_DATE,'yyyy-MM-dd','')" width="8%"></eq:col>
      <eq:col title="创建时间" value="messageFormater.formatDateWithPattern(CREATE_DATE,'yyyy-MM-dd HH:mm:ss','')" width="12%"></eq:col>
      <eq:col title="操作" width="8%" escape="false">
        <a href="${ctxPath}/notice/editNotice?noticeId=${ID!""}">修改</a>
        <a href="javascript:deleteNotice(${ID!""});">删除</a>
      </eq:col>
    </eq:listable>
  </div>
</div>
</body>
</html>
