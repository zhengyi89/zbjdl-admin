<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<div id="editMenuDiv" class="input_cont border_n" style="display:none;">
  <form id="editMenuForm" method="get">
    <s:hidden name="menuDTO.menuId"/>
    <s:hidden name="menuDTO.levelNum"/>
    <ul>
        <s:if test="curUser.getIsSuperAdmin() && departList != null && !departList.isEmpty()">
            <li style="overflow:visible;clear:both;width:auto;">
                <label class="text_tit">所属部门：</label>
                <div class="select_border">
                    <div class="container">
                        <s:select list="departList" cssClass="select chosen" headerKey="" headerValue="全部" listKey="departmentId" listValue="departmentName" id="edit_departmentId" name="departmentId"/>
                    </div>
                </div>
            </li>
        </s:if>
        <s:else>
            <s:hidden id="edit_departmentId" name="departmentId"/>
        </s:else>
        <li style="overflow:visible;clear:both;width:auto;">
            <label class="text_tit">父级菜单：</label>
            <div class="select_border">
                <div class="container">
                    <s:select list="menus" cssClass="select chosen" headerKey="-1" headerValue="顶级菜单" listKey="menuId" listValue="menuName" id="edit_parentId" name="menuDTO.parentId"/>
                </div>
            </div>
        </li>
      <li>
        <label class="text_tit">菜单名：</label>
        <p><s:textfield name="menuDTO.menuName" cssClass="input_text" cssStyle="width:300px;"/> </p>
      </li>

      <li>
        <label class="text_tit">菜单URL：</label>
        <p><s:textarea name="menuDTO.functionUrl" cssClass="input_cont" cssStyle="width:300px;"/> </p>
      </li>
      <li>
        <label class="text_tit">同级顺序：</label>
        <p><s:textfield name="menuDTO.sequence" cssClass="input_text" cssStyle="width:300px;"/> </p>
      </li>
    </ul>
  </form>
  <div class="clearer"></div>
</div>