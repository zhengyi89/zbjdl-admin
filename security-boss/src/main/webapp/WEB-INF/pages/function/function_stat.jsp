<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<div class="information">
  <eq:listable queryKey="functionStat" html="class='list'" formId="form" paging="false" pageSize="1000">
    <eq:param name="departmentId" value="departmentId" />
    <eq:col title="部门ID" value="DEPARTMENTID"></eq:col>
    <eq:col title="部门名称" value="DEPARTMENTNAME"></eq:col>
    <eq:col title="最大功能ID" value="MAX_FUNCTIONID"></eq:col>
    <eq:col title="最小功能ID" value="MIN_FUNCTIONID"></eq:col>
    <eq:col title="功能数" value="FUNCTIONID_COUNT"></eq:col>
  </eq:listable>
  <div class="clearer"></div>
</div>
