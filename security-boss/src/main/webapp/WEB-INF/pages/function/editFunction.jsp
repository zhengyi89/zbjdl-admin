<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<s:if test="type == 'update'">
  <title>修改功能</title>
</s:if>
<s:else>
  <title>添加功能</title>
</s:else>
<%@ include file="/WEB-INF/pages/main/metas.jsp"%>
<script type="text/javascript" src="${ctxPath}/js/function/function.action.js"></script>
<style>
#functionForm .row_x {
  text-align:left;
}
</style>
<script type="text/javascript">
function viewFuncStat() {
  MessageBoxExt.load("funcStat", "${ctxPath}/function/functionStat.action", {title:"功能统计",width:600,height:400})
}
</script>
</head>
<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <div class="clearer"></div>
    <form id="functionForm" action="update" method="post">
      <div class="information">
        <h1 class="fw">基本信息</h1>
        <div class="input_cont">
          <ul>
            <li>
              <label class="text_tit">功能ID：</label>
              <input type="hidden" name="type" id="type" value="<s:property value="type"/>"/>
              <input type="hidden"  id="departmentIds" value="<s:property value="departmentIds"/>"/>
              <input type="hidden"  id="roleIds" value="<s:property value="roleIds"/>"/>
              <input type="hidden"  id="functionType" value="<s:property value="functionVO.functionType"/>"/>
              <input type="hidden"  id="checkFunctionId1" value="<s:property value="functionVO.checkFunctionId"/>"/>
              <input class="input_text" type="text" name="functionId" id="functionId" value="<s:property value='functionVO.functionId' />" />
              <a style="margin-left:10px;" href="javascript:viewFuncStat();">参考现有功能ID范围</a>
            </li>
            <li>
              <label class="text_tit">功能名称：</label>
              <input class="input_text" type="text" name="functionName" id="functionName" value="<s:property value='functionVO.functionName' />" />
            </li>
            <li>
              <label class="text_tit" >功能URL：</label>
              <input class="input_text" type="text" name="functionUrl" id="functionUrl" value="<s:property value='functionVO.functionUrl' />" len="128" dataType="url"/>
            </li>
            <li>
              <label class="text_tit">功能描述：</label>
             <input class="input_text" type="text" name="description" id="description" value="<s:property value='functionVO.description' />"/>
            </li>
            <li >
              <label class="text_tit">功能分组ID：</label>
              <div class="select_border">
                <div class="container">
                  <select name="preFunctionId" id="preFunctionId" class="select">
                    <option value="">请选择</option>
                    <s:if test="preFunctionMapList != null">
                      <s:iterator value="preFunctionMapList" var="preFunctionMap">
                        <option value="<s:property value="key"/>" <s:if test="preFunctionId == key">selected</s:if>><s:property value="value"/>[<s:property value="key"/>]</option>
                      </s:iterator>
                    </s:if>
                  </select>
                </div>
              </div>
              <%-- <input class="input_text" type="text" name="preFunctionId" id="preFunctionId" value="<s:property value='functionVO.preFunctionId' />"/> --%>
            </li>
            <li >
              <label class="text_tit" >复核功能ID：</label>
              <input class="input_text" type="text" name="checkFunctionId" id="checkFunctionId" value="<s:property value='functionVO.checkFunctionId' />"/>
            </li>
            <li><label class="text_tit">是否复核：</label>
              <div class="select_border">
                <div class="container">
                  <select name="checkNeeded" id="checkNeeded" class="select">
                    <option value="">请选择</option>
                    <option value="0" <s:if test="functionVO.checkNeeded == null || functionVO.checkNeeded == 0">selected</s:if> >不复核</option>
                    <option value="1" <s:if test="functionVO.checkNeeded == 1">selected</s:if> >普通复核[可重复申请]</option>
                    <option value="2" <s:if test="functionVO.checkNeeded == 2">selected</s:if> >普通复核[不可重复申请]</option>
                    <option value="3" <s:if test="functionVO.checkNeeded == 3">selected</s:if> >风控预复核[可重复申请]</option>
                    <option value="4" <s:if test="functionVO.checkNeeded == 4">selected</s:if> >风控预复核[不可重复申请]</option>
                  </select>
                </div>
              </div>
            </li>
            <li><label class="text_tit" >是否记录日志：</label>
              <div class="select_border">
                <div class="container">
                  <select name="logNeeded" id="logNeeded" class="select">
                    <option value="">请选择</option>
                    <option value="false" <s:if test="functionVO.logNeeded == null || functionVO.logNeeded == false">selected</s:if> >否</option>
                    <option value="true" <s:if test="functionVO.logNeeded == true">selected</s:if> >是</option>
                  </select>
                </div>
              </div>
            </li>
            <li><label class="text_tit" >是否显示：</label>
              <div class="select_border">
                <div class="container">
                  <select name="display" id="display" class="select">
                    <option value="">请选择</option>
                    <option value="true" <s:if test="functionVO.display == null || functionVO.display == true">selected</s:if> >是</option>
                    <option value="false" <s:if test="functionVO.display == false">selected</s:if> >否</option>
                  </select>
                </div>
              </div>
            </li>
            <li>
              <label class="text_tit">风险级别：</label>
              <div class="select_border">
                <div class="container">
                  <select name="riskLevel" id="riskLevel" class="select">
                    <option value="">请选择</option>
                    <option value="MIDDLE" <s:if test="functionVO.riskLevel == null || functionVO.riskLevel == 'MIDDLE'">selected</s:if> >中</option>
                    <option value="LOW" <s:if test="functionVO.riskLevel == 'LOW'">selected</s:if> >低</option>
                    <option value="HIGH" <s:if test="functionVO.riskLevel == 'HIGH'">selected</s:if> >高</option>
                  </select>
                </div>
              </div>
            </li>
            <li>
              <label class="text_tit" >关键字：</label>
              <input class="input_text" type="text" name="keyWord" id="keyWord"  value="<s:property value='functionVO.keyWord' />"/>
            </li>
	        <li>
	          <label class="text_tit" >标签：</label>
		      <input class="input_text" type="text" name="tag" id="tag"  value="<s:property value='functionVO.tag' />"/> &nbsp;多个标签请用半角逗号(,)分割
	        </li>
            <li><label class="text_tit">管理员登录密码：</label>
             <input class="input_text" type="password" name="password" id="password" />
            </li>
            <s:if test="#session['SESSION_USERINFO'].isSuperAdmin == 1">
            <li>
              <label class="text_tit">角色：</label>
              <p>
                <label class="row_x" for="role1">
                  <input type="checkbox" id="role1" name="roleBox" value="-1001"<s:if test="roleIds != null && roleIds.contains(-1001L)">checked="checked"</s:if>/>系统管理员
                </label>
                <label class="row_x" for="role2">
                  <input type="checkbox" id="role2" name="roleBox" value="-1000"<s:if test="roleIds != null && roleIds.contains(-1000L)">checked="checked"</s:if>/> 部门管理员
                </label>
                <label class="row_x" for="role3">
                  <input type="checkbox" id="role3" name="roleBox" value="-999" <s:if test="roleIds != null && roleIds.contains(-999L)">checked="checked"</s:if>/>操作员
                </label>
                <span class="tip_error ml_l" id="error_roleBox"></span>
              </p>
            </li>
            <li>
              <label class="text_tit">部门：</label>
              <p>
              <s:iterator value="depts" id="depts" var="departmentDTO" status="ind">
                <label class="row_x" for="departmentId<s:property value='departmentId'/>">
                <input type="checkbox" name="deptBox" value="<s:property value='departmentId'/>" id="departmentId<s:property value='departmentId'/>" <s:if test="departmentIds != null && departmentIds.contains(departmentId)">checked="checked"</s:if>/>
                <s:property value='departmentName' />
                </label>
              </s:iterator>
              <span class="tip_error ml_l" id="error_deptBox"></span>
              </p>
            </li>
            </s:if>
            <li><label class="text_tit">&nbsp;</label>
              <s:if test="type == 'update'">
              <input type="button" id="updateFunctionButton" class="btn_sure fw" value="更新"/>
              </s:if>
              <s:if test="type == 'add'">
              <input type="button" id="addFunctionButton" class="btn_sure fw" value="新增"/>
              </s:if>
              <input type="button" class="btn_cancel fw" value="返回" onclick="self.history.back(-1);" />
            </li>
          </ul>
          <div class="clearer"></div>
        </div>
      </div>
    </form>
  </div>
</div>
<div id="funcStat" style="display:none;"></div>
</body>
</html>
