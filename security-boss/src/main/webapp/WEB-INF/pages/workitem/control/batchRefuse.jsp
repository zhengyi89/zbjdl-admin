<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<div id="batchSuccessDiv" class="input_cont border_n" style="display:none;">
    <form id="batchSuccessFrom" method="get">
        <ul>
            <li>是否批量预审核通过？</li>
        </ul>
    </form>
    <div class="clearer"></div>
</div>

<div id="batchRefuseDiv" class="input_cont border_n" style="display:none;">
  <form id="batchRefuseFrom" method="get">
    <ul>
	    <s:if test="errMsg != null && errMsg != ''">
		    <li>
			    <label class="text_tit">&nbsp;</label>
			    <div class="tip_error"><s:property value="errMsg"/></div>
		    </li>
	    </s:if>
      <li>
        <label class="text_tit">拒绝理由：</label>
        <p>
          <s:textarea id="refuseReason2" name="refuseReason" cssClass="textfield" cssStyle="width:400px;height:200px;" />
        </p>
      </li>
    </ul>
  </form>
  <div class="clearer"></div>
</div>