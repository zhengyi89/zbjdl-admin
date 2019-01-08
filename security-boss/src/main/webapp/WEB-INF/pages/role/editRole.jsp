<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<s:if test="roleVO.type == 'add'">
  <title>新建角色</title>
</s:if>
<s:else>
  <title>更新角色</title>
</s:else>
<%@ include file="/WEB-INF/pages/main/metas.jsp"%>
<script type="text/javascript" src="${ctxPath}/js/role/role.action.js"></script>
<script type="text/javascript" src="${ctxPath}/js/jquery.ztree.all-3.1.js"></script>
<script type="text/javascript" src="${ctxPath}/js/zTree/zTree.js"></script>
<link rel="stylesheet" href="${ctxPath}/css/common/zTreeStyle.css" type="text/css"/>
<script type="text/javascript">
var zNodes = ${zTreeJson}
</script>
</head>
<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <form id="editRoleForm" action="#" method="post">
      <div class="setup">
        <s:if test="roleVO.type == 'add'">
        <h1 class="fw fleft f14">新建角色</h1>
        </s:if>
        <s:else>
        <h1 class="fw fleft f14">更新角色</h1>
        </s:else>
        <p>
          <a href="javascript:void(0);" id="roleHandler" class="up">点击收起</a>
        </p>
      </div>
      <div id="tree">
        <ul id="treeDemo" class="ztree"></ul>
      </div>
      <input type="hidden" id="roleId" name="roleId" value="<s:property value='roleVO.roleId' />" />
      <input type="hidden" id="oldRoleName" value="<s:property value='roleVO.roleName' />" />
      <input type="hidden" id="functionIds" name="functionIds"/>
      <div class="clearer"></div>
      <div class="content">
        <div class="result"> 
          <s:if test="roleVO.type == 'add'">
            <h3 class="fw">角色新增</h3> 
          </s:if>
          <s:else>
           <h3 class="fw">角色修改</h3> 
          </s:else>
          <div class="information">
            <h1 class="fw"> 角色信息 </h1>
            <div class="clearer"></div>
            <div class="input_cont">
              <ul>
                <li>
                  <label class="text_tit">角色名称：</label>
                  <input class="input_text" type="text" name="roleName" id="roleName" title = "required" value="<s:property value='roleVO.roleName' />" />
                  <div id="roleNameMsg"></div>
                </li>
                <li>
                  <label class="text_tit">角色描述：</label>
                  <input class="input_text" type="text" name="description" id="description" value="<s:property value='roleVO.description' />" />
                  <div id="descriptionMsg"></div>
                </li>
                <li>
                  <label class="text_tit">管理员密码：</label>
                  <input class="input_text" type="password"  name="password" id="password" />
                  <div id="passwordMsg"></div>
                </li>
              </ul>
            </div> 
            <font color="red"><span id="functionMessage"></span></font>
          </div>
          <div class="clearer"></div>
          <div class="btn">
            <s:if test="roleVO.type == 'add'">
              <input type="button" id="addButton" class="btn_sure fw" value="添加" />
            </s:if>
            <s:else>
              <input type="button" id="editButton" class="btn_sure fw" value="更新" />
            </s:else>
            <input type="button" class="btn_cancel fw" value="取消" onclick="history.go(-1);"/>
          </div>
        </div>
      </div>
    </form>
    <div class="clearer"></div>
  </div>
</div>
</body>
</html>