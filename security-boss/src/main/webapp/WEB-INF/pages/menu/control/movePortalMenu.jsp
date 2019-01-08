<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<div id="moveMenuDiv" class="input_cont border_n" style="display:none;">
  <form id="moveMenuForm" method="get">
    <ul>
      <li style="overflow:visible;clear:both;width:auto;">
        <label class="text_tit">父级菜单：</label>
        <s:select list="menus" cssClass="select chosen" headerKey="-1" headerValue="顶级菜单" listKey="menuId" listValue="menuName" id="parentName" name="parentName"/>
      </li>
    </ul>
  </form>
  <div class="clearer"></div>
</div>