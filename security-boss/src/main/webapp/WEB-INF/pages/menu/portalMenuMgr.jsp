<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="${ctxPath}/css/common/chosen.css" />
<%@ include file="/WEB-INF/pages/main/metas.jsp"%>
<title>门户菜单管理</title>
</head>
<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <!--search star-->
    <form action="${ctxPath}/menu/portalMenuMgr.action" id="queryPortalMenuForm" method="get">
      <div class="search">
        <div class="search_tit">
          <h2 class="fw fleft f14">菜单查询</h2>
        </div>
        <div class="clearer"></div>
        <div class="search_con">
          <ul class="fix">
            <li>
              <label class="text_tit">菜单名称：</label>
              <s:textfield name="menuName" value="%{#parameters.menuName}" cssClass="input_text"/>
            </li>
            <li>
              <label class="text_tit">功能URL：</label>
              <s:textfield name="functionUrl" value="%{#parameters.functionUrl}" cssClass="input_text"/>
            </li>
            <li>
              <label class="text_tit">菜单层级：</label>
              <div class="select_border">
                <div class="container">
                  <s:select cssClass="select" name="levelNum"
                    list="#{'':'全部','02':'1级','03':'2级','04':'3级','05':'4级','06':'5级' }"
                    value="%{#parameters.levelNum}"/>
                </div>
              </div>
            </li>
            <s:if test="curUser.getIsSuperAdmin() && departList != null && !departList.isEmpty()">
            <li>
              <label class="text_tit">部门：</label>
              <div class="select_border">
                <div class="container">
                 <s:hidden name="isSuperAdmin" value="%{curUser.isSuperAdmin}"/>
	             <s:hidden name="h_departmentId" id="h_departmentId" value="%{#parameters.departmentId}"/>
                 <s:select list="departList" cssClass="select" headerKey="" headerValue="全部" listKey="departmentId" listValue="departmentName" name="departmentId" value="%{#parameters.departmentId}"/>
                </div>
              </div>
            </li>
            </s:if>
          </ul>
          <div class="btn">
            <input type="submit" id="submitBtn" class="btn_sure fw" value="查询" />
            <input type="button" class="btn_cancel fw" value="清空" onclick="clearAll();" />
          </div>
          <div class="clearer"></div>
        </div>
      </div>
    </form>

    <div class="clearer"></div>
    <div class="submenu">
      <dl style="float:right;padding-right:30px;height:0px;">
        <dd><a href="javascript:void(0);" onclick="PortalMgr.importMenu()">【导入菜单】</a></dd>
      </dl>
    </div>

    <div class="clearer"></div>
    <!--List stard-->
    <eq:listable queryKey="queryPortalMenu" html="class='list'" formId="queryPortalMenuForm">
      <eq:param name="departmentId" value="departmentId"/>
      <eq:col title="<input type='checkbox' id='selectAll' name='departmentId'/>" escape="false">
          <input type="checkbox" name="menuIds" value="${menuId}"/>
      </eq:col>
      <eq:col title="菜单ID" value="menuId"/>
      <eq:col title="菜单名称" value="menuName"/>
      <eq:col title="所属部门" value="departmentId"/>
      <eq:col title="功能URL" value="functionUrl" width="600px"/>
      <eq:col title="同级顺序" value="sequence"/>
      <eq:col title="上一级菜单ID" value="parentId"/>
      <eq:col title="深度" value="levelNum - 1"/>
      <eq:col title="操作" escape="false">
          <a href="javascript:void(0);" name="addSiblingMenu" menuId="${menuId}" parentId="${parentId!""}" levelNum="${levelNum!""}">添加同级菜单</a>
          <a href="javascript:void(0);" name="addSubMenu" menuId="${menuId}" parentId="${parentId!""}" levelNum="${levelNum!""}">添加子级菜单</a>
          <a href="javascript:void(0);" name="editMenu" menuId="${menuId}" parentId="${parentId!""}" levelNum="${levelNum!""}">修改</a>
          <a href="javascript:void(0);" onclick="PortalMgr.deleteMenu(${menuId})">删除</a>
      </eq:col>
    </eq:listable>
    <div id="hidden_operat" style="display:none;">
      <div class="operat" style="bottom:10px; display:block;height:30px;">
        <ul>
          <li>
            <span id="button">
              <a href="javascript:void(0)" id="batchDelete" class="btn_sure_a fw" ><b>批量删除</b><em></em></a>
              <a href="javascript:void(0)" id="batchMove" class="btn_sure_a fw" ><b>批量移动</b><em></em></a>
            </span>
          </li>
        </ul>
      </div>
    </div>
  </div>
  <%@ include file="/WEB-INF/pages/menu/control/editPortalMenu.jsp"%>
  <%@ include file="/WEB-INF/pages/menu/control/movePortalMenu.jsp"%>
  <%@ include file="/WEB-INF/pages/menu/control/importPortalMenu.jsp"%>
</div>
<script type="text/javascript" src="${ctxPath}/js/menu/portalMenuMgr.js"></script>
<script type="text/javascript">
	$(document).ready(function () {
		if ($("#departmentId").length > 0) {
			$("#departmentId").val($("#h_departmentId").val());
			if ($("#h_departmentId").val()) {
				$("#multiDept").attr("disabled", true);
			}
			$("#departmentId").change(function () {
				if ($(this).val()) {
					$("#multiDept").attr("disabled", true);
				} else {
					$("#multiDept").removeAttr("disabled");
				}
			})
		}
	});
</script>
<script type="text/javascript" src="${ctxPath}/js/chosen.jquery.min.js"></script>
<script type="text/javascript">
    $(function () {
        $('.chosen').chosen();
    });
</script>
</body>
</html>
