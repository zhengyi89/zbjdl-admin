<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<table class="list_info">
  <tr>
    <td class="info_tit">操作员ID：</td>
    <td class="info_con"><s:property value="operationLog.operator_id"/></td>
    <td class="info_tit">操作员：</td>
    <td class="info_con"><s:property value="operationLog.operator_login_name"/></td>
  </tr>
  <tr>
    <td class="info_tit">功能ID：</td>
    <td class="info_con"><s:property value="operationLog.function_id"/></td>
    <td class="info_tit">关键字：</td>
    <td class="info_con"><s:property value="operationLog.keyword"/></td>
  </tr>
  <tr>
    <td class="info_tit">功能名：</td>
    <td class="info_con"><s:property value="operationLog.function_name"/></td>
    <td class="info_tit">功能URL：</td>
    <td class="info_con"><s:property value="operationLog.function_url"/></td>
  </tr>
  <tr>
    <td class="info_tit">日志参数：</td>
    <td class="info_con"><s:property value="operationLog.operate_args"/></td>
    <td class="info_tit">日志内容：</td>
    <td class="info_con">
      <s:if test="operationLog.operate_content != null">
      <s:set name="fullContent" var="fullContent" value="viewLogicHelper.split(operationLog.operate_content,',')"></s:set>
      <s:iterator value="#fullContent" id="param">
      <s:property value="#param"/><br/>
      </s:iterator>
      </s:if>
    </td>
  </tr>
  <tr>
    <td class="info_tit">操作结果：</td>
    <td class="info_con"><s:property value="operationLog.operate_result"/></td>
    <td class="info_tit">操作时长：</td>
    <td class="info_con"><s:property value="operationLog.during_time"/> (ms)</td>
  </tr>
  <tr>
    <td class="info_tit">记录时间：</td>
    <td class="info_con"><s:property value="messageFormater.formatDateWithPattern(operationLog.operate_end_time,'yyyy-MM-dd HH:mm:ss','')"/></td>
    <td class="info_tit"></td>
    <td class="info_con"></td>
  </tr>
</table>
