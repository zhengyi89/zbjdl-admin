<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>模板管理</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp" %>
<script type="text/javascript">
function deleteTemplate(id) {
  MessageBoxExt.confirm("是否确定删除此模板?", function(){
    MessageBoxExt.ajax({
      type : "post",
      url : GV.ctxPath + "/template/deleteWorkItemTemplate.action",
      data : {templateId : id},
      style : "redirect"
    });
  });
}
$(document).ready(function(){
  var valList = {
    "functionId" : {
      datatype:"^-?[1-9]\\d*$",
      len: {max:19},
      label : "功能ID"
    }
  };
  ValidateExt.listen("form",valList,ValidateExt.tipError);

  $("#submitBtn").click(function(){
    if(ValidateExt.val("form")){
      $("#form").submit();
    }
  });
});
</script>
</head>
<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <div class="result">
      <div id="template_guide" class="tag">
          <a href="${ctxPath}/template/logTemplateMgr" ><em>【日志模板管理】</em></a><span>|</span>
          <a href="${ctxPath}/template/workItemTemplateMgr " class="on"><em>【双权限审核模板管理】</em></a><span>|</span>
      </div>
    </div>
    <div class="search">
      <form action="${ctxPath}/template/workItemTemplateMgr.action" id="form" method="get">
        <div class="search_tit">
          <h2 class="fw fleft f14">查询双权限审核模板</h2>
        </div>
        <div class="clearer"></div>
        <div class="search_con">
          <ul class="fix">
            <li>
              <label class="text_tit">功能名称：</label>
              <s:textfield name="functionName" id="functionName" cssClass="input_text" value="%{#parameters.functionName}" />
            </li>
            <li>
              <label class="text_tit">功能ID：</label>
              <s:textfield name="functionId" id="functionId" cssClass="input_text" value="%{#parameters.functionId}"/>
            </li>
            <li>
              <label class="text_tit">模板内容：</label>
              <s:textfield name="content" id="content" cssClass="input_text" value="%{#parameters.content}"/>
            </li>
          </ul>
          <div class="clearer"></div>
            <div class="btn">
              <input type="button" class="btn_sure fw" id="submitBtn" value="查询"/>
              <input type="button" class="btn_cancel fw" value="清空" onclick="clearAll();"/>
           </div>
          <div class="clearer"></div>
        </div>
      </form>
    </div>
    <div class="clearer"></div>
    <div class="submenu">
      <dl style="float:right;padding-right: 30px; height: 0px;">
        <dd><a href="${ctxPath}/template/showAddTemplate.action?templateTag=workItem">添加双权限审核模板</a></dd>
      </dl>
    </div>
    <eq:listable queryService="queryService" queryKey="queryWorkItemTemplate" formId="form" pageSize="20" html="class='list'">
      <eq:param name="departmentId" value="#session['SESSION_USERINFO'].primaryDepartmentId" />
      <eq:col sortable="false" title="功能名称"  value="functionName"></eq:col>
      <eq:col sortable="false" title="功能ID"  value="functionId"></eq:col>
      <eq:col sortable="false" title="模板内容" value="content" width="750px"></eq:col>
      <eq:col title="操作" escape="false" width="80px">
          <a href="${ctxPath}/template/showUpdateTemplate.action?templateId=${templateId!""}&templateTag=workItem">修改</a>
          <a href="javascript:deleteTemplate('${templateId!""}');">删除</a>
      </eq:col>
    </eq:listable>
  </div>
</div>
</body>
</html>
