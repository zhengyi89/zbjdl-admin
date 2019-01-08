<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<div id="importMenuDiv" class="input_cont border_n" style="display:none;">
  <form id="importMenuFrom" method="get">
    <ul>
      <li>
        <label class="text_tit">菜单内容：</label>
        <p>
          <s:textarea name="menuDetail" cssClass="textfield" cssStyle="width:550px;height:240px;" />
	      <br/><span style="font-size: 12px;"><font color="gray">每个菜单占一行，单行格式：菜单名,菜单URL,父菜单名</font></span>
	      <br/><span style="font-size: 12px;"><font color="gray">同名父级菜单就近匹配，注意先后顺序以免导入后菜单错乱</font></span>
        </p>
      </li>
      <li>
        <label class="text_tit">全新导入：</label>
        <p>
          <s:checkbox name="resetAll" value="false"/><font color="gray">若勾选，则删除本部门已定制的菜单，完全重新导入</font>
        </p>
      </li>
      <s:if test="curUser.getIsSuperAdmin() && departList != null && !departList.isEmpty()">
      <li>
        <label class="text_tit">所属部门：</label>
        <div class="select_border">
          <div class="container">
           <s:select list="departList" cssClass="select" headerKey="" headerValue="全部" listKey="departmentId" listValue="departmentName" id="menuDTO_departmentId" name="departmentId" value="%{#parameters.departmentId}"/>
          </div>
        </div>
      </li>
      </s:if>
      <s:else>
          <s:hidden id="edit_departmentId" name="departmentId"/>
      </s:else>
    </ul>
  </form>
  <div class="clearer"></div>
</div>
