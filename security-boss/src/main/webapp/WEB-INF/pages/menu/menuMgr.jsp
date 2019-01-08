<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单管理</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp"%>
<script type="text/javascript" src="${ctxPath}/js/menu/menu.action.js"></script>
</head>

<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <!--search star-->
    <form action="menuSetting" id="form" method="get">
      <div class="search">
        <div class="search_tit">
          <h2 class="fw fleft f14">菜单查询</h2>
        </div>
        <div class="clearer"></div>
        <div class="search_con">
          <ul class="fix">
            <li>
              <label class="text_tit">菜单名称：</label>
              <s:textfield name="menuName" value="%{#parameters.menuName}" cssClass="input_text"></s:textfield>
            </li>
            <li>
              <label class="text_tit">功能ID：</label>
              <s:textfield name="functionId" value="%{#parameters.functionId}" cssClass="input_text"></s:textfield>
            </li>
          </ul>
          <div class="btn">
            <input type="button" id="submitBtn" class="btn_sure fw" value="查询" />
            <input type="button" class="btn_cancel fw" value="清空" onclick="clearAll();" />
          </div>
          <div class="clearer"></div>
        </div>
      </div>
    </form>

    <div class="clearer"></div>
    <div class="submenu">
      <dl style="float:right;padding-right: 30px; height: 0px;">
        <dd><a href="initAdd">【添加菜单】</a></dd>
      </dl>
    </div>

    <div class="clearer"></div>
    <!--List stard-->
    <eq:listable queryKey="queryAllMenu" html="class='list'"
      formId="form">
      <%-- <eq:param name="userId" value="userId"/> --%>
      <eq:param name="departmentId" value="departmentId"/>
      <eq:col title="菜单名称" value="menuName">
      </eq:col>
      <eq:col title="功能ID" value="functionId">
      </eq:col>
      <eq:col title="同级顺序" value="sequence">
      </eq:col>
      <eq:col title="上一级菜单ID" value="parentId">
      </eq:col>
      <eq:col title="深度" value="levelNum">
      </eq:col>
      <eq:col title="操作" escape="false">
          <a href="initUpdate?menuId=${menuId}" >修改</a>
          <a href="javascript:void(0);" onclick="popupPasswordDialog(${menuId},'menu','delete')">删除</a>
      </eq:col>
    </eq:listable>
  </div>
</div>
<%@ include file="passwordDialog.jsp"%>
</body>
</html>
