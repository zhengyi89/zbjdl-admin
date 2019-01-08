<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息显示</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp"%>
<script language="javascript" src="<s:url value='/js/function/function.action.js'/>"></script>
<script language="javascript">
function jumpToUpdatePage() {
  var functionId = $("#functionId").val();
  window.location.href="initUpdate?functionId=" + functionId;
}
</script>
</head>

<body>
<div class="content_wrapper">
<!--Body-->
  <div class="content_main fontText">
    <input type="hidden" id="functionId" value="<s:property value='functionVO.functionId' />" />
    <div class="information">
      <h1 class="fw">基本信息</h1>
      <div class="clearer"></div>
      <table class="list_info">
        <tr>
          <td class="info_tit">功能名称：</td>
          <td class="info_con"><s:property value="functionVO.functionName" /></td>
          <td class="info_tit">所属部门：</td>
          <td class="info_con">
            <s:iterator value="functionVO.deptNames" var="name" status="st">
              <s:if test="#st.last">
                <s:property value="name" />
              </s:if>
              <s:else>
                <s:property value="name" />、
              </s:else>
            </s:iterator>
          </td>
        </tr>
        <tr>
          <td class="info_tit">分配权限展示：</td>
          <td class="info_con">
            ${functionVO.display?'是':'否'}
          </td>
          <td class="info_tit">风险级别：</td>
          <td class="info_con">
            <s:if test="functionVO.riskLevel == 'HIGH'">高</s:if>
            <s:if test="functionVO.riskLevel == 'MIDDLE'">中</s:if>
            <s:if test="functionVO.riskLevel == 'LOW'">低</s:if>
          </td>
        </tr>
        <tr>
          <td class="info_tit">功能状态：</td>
          <td class="info_con">
            <s:if test="functionVO.functionStatus == 'ACTIVE'">
              活动 [<a href="javascript:void(0);" onclick="popupPasswordDialog(${functionVO.functionId},'function','freeze')" >冻结</a>/
                 <a href="javascript:void(0);" onclick="popupPasswordDialog(${functionVO.functionId},'function','delete')">删除</a>]
            </s:if>
            <s:if test="functionVO.functionStatus == 'FROZEN'">
              冻结 [<a href="javascript:void(0);" onclick="popupPasswordDialog(${functionVO.functionId},'function','activate')">解冻</a>/
                 <a href="javascript:void(0);" onclick="popupPasswordDialog(${functionVO.functionId},'function','delete')">删除</a>]
            </s:if>
            <s:if test="functionVO.functionStatus == 'FORBIDDEN'">删除</s:if>
          </td>
          <td class="info_tit">功能描述：</td>
          <td class="info_con"><s:property value="functionVO.description" /></td>
        </tr>
        <tr>
          <td class="info_tit">菜单路径：</td>
          <td class="info_con"><s:property value="functionVO.menuPath" /></td>
          <td class="info_tit">创建时间：</td>
          <td class="info_con"><s:property value="messageFormater.formatDateWithPattern(functionVO.createTime,'yyyy-MM-dd HH:mm:ss','')" /></td>
        </tr>
      </table>
      <div class="clearer"></div>
      <div class="btn" style="text-align:center">
        <s:if test="functionVO.functionStatus != 'FORBIDDEN'">
          <input type="button" class="btn_sure fw" value="更新" onclick="jumpToUpdatePage()" />
        </s:if>
        <input type="button" class="btn_cancel fw" value="返回" onclick="window.location.href='functionMgr'"/>
      </div>
    </div>
    <div class="clearer"></div>
  </div>
</div>
<%@ include file="passwordDialog.jsp" %>
</body>
</html>
