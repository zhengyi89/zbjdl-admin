<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/pages/main/metas.jsp"%>
<s:if test="<s:property value='departmentVO.type' /> == 'add'">
  <title>新建部门</title>
</s:if>
<s:else>
  <title>更新部门</title>
</s:else>
<script type="text/javascript" src="${ctxPath}/js/dept/dept.action.js"></script>
<script type="text/javascript" src="${ctxPath}/js/role/role.action.js"></script>
<script type="text/javascript" src="${ctxPath}/js/jquery.ztree.all-3.1.js"></script>
<script type="text/javascript" src="${ctxPath}/js/zTree/zTree.js"></script>
<link rel="stylesheet" href="${ctxPath}/css/common/zTreeStyle.css" type="text/css" />
<script type="text/javascript">
  var zNodes = ${zTreeJson};
</script>
</head>
<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <form id="editDeptForm" action="#" method="post">
      <div class="setup">
        <s:if test="departmentVO.type == 'add'">
          <h1 class="fw fleft f14">新建部门功能</h1>
        </s:if>
        <s:else>
          <h1 class="fw fleft f14">更新部门功能</h1>
        </s:else>
        <p>
          <a href="javascript:void(0);" id="departmentHandler" class="up">点击收起</a>
        </p>
      </div>

      <div id="tree">
        <ul id="treeDemo" class="ztree"></ul>
      </div>
      <input type="hidden" id="functionIds" name="functionIds" /> 
      <input type="hidden" name="departmentId" value="<s:property value='departmentVO.departmentId' />" /> 
      <input type="hidden" id="oldDepartmentName" value="<s:property value='departmentVO.departmentName' />" />
      <input type="hidden" id="oldDepartmentDesc" value="<s:property value='departmentVO.departmentDesc' />" />
      <div class="clearer"></div>
      <div class="information">
        <h1 class="fW">部门信息</h1>
        <div class="input_cont">
          <ul>
            <li>
              <label class="text_tit">部门名称：</label>
              <input class="input_text" type="text" name="departmentName" id="departmentName" title="required" value="<s:property value='departmentVO.departmentName' />" />
              <span id="departmentNameMsg"></span>
            </li>
            <li>
              <label class="text_tit">部门编号：</label>
              <s:if test="departmentVO.type == 'add'">
                <input class="input_text" type="text" name="departmentCode" title="required" id="departmentCode" value="<s:property value='departmentVO.departmentCode' />" />
                <span id="departmentCodeMsg"></span>
              </s:if>
              <s:else>
                <s:property value='departmentVO.departmentCode' />
              </s:else>
            </li>
            <li>
              <label class="text_tit">是否 Portal：</label>
              <input type="checkbox" name="portal" value="true" <s:if test="departmentVO.portal">checked</s:if> />
            </li>
            <li>
              <label class="text_tit">部门描述：</label>
              <input class="input_text" type="text" name="departmentDesc" id="departmentDesc" value="<s:property value='departmentVO.departmentDesc' />" />
              <span id="departmentDescMsg"></span>
            </li>
            <li>
              <label class="text_tit">管理员密码：</label>
              <input class="input_text" type="password" id="password" name="password" title="required" value="" />
              <span id="passwordMsg"></span>
            </li>
            <li>
              <label class="text_tit">&nbsp;</label>
              <span id="systemMsg"></span>
            </li>
          </ul>
          <div class="clearer"></div>
        </div>
        <div class="btn">
          <s:if test="departmentVO.type == 'add'">
            <input type="button" id="addButton" class="btn_sure fw" value="添加" />
          </s:if>
          <s:else>
            <input type="button" id="editButton" class="btn_sure fw" value="更新" />
          </s:else>
          <input type="button" class="btn_cancel fw" value="返回" onclick="window.location.href='departMentMgr'" />
        </div>
        <div class="clearer"></div>
      </div>
    </form>
  </div>
</div>
</body>
</html>