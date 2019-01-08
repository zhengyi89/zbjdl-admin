<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<div class="input_cont border_n">
  <ul>
    <s:if test="errMsg != null && errMsg != ''">
    <li>
      <label class="text_tit">&nbsp;</label>
      <div class="tip_error"><s:property value="errMsg"/></div>
    </li>
    </s:if>
    <li>
      <label class="text_tit">提交人：</label>
      <p><s:property value="workItemDTO.submittedUserName"/></p>
    </li>
    <li>
      <label class="text_tit">提交时间：</label>
      <p><s:property value="messageFormater.formatDateWithPattern(workItemDTO.submitTime,'yyyy-MM-dd HH:mm:ss','')"/></p>
    </li>
    <li>
      <label class="text_tit">审核状态：</label>
      <p>
        <s:if test="workItemDTO.workItemStatus.toString() == 'RISK_WAITING'">风控待审核</s:if>
        <s:if test="workItemDTO.workItemStatus.toString() == 'RISK_CHECKING'">风控审核中</s:if>
        <s:if test="workItemDTO.workItemStatus.toString() == 'WAITING'">
          <s:if test="workItemDTO.workItemType != null && workItemDTO.workItemType.toString() == 'RISK'">风控已审核</s:if>
          <s:else>待审核</s:else>
        </s:if>
        <s:if test="workItemDTO.workItemStatus.toString() == 'CHECKING'">审核中</s:if>
        <s:if test="workItemDTO.workItemStatus.toString() == 'SUCESS'">审核通过</s:if>
        <s:if test="workItemDTO.workItemStatus.toString() == 'REFUSE'">审核拒绝</s:if>
        <s:if test="workItemDTO.workItemStatus.toString() == 'FORBIDDEN'">已废弃</s:if>
        <s:if test="workItemDTO.workItemStatus.toString() == 'ERROR'">审核异常</s:if>
      </p>
    </li>
    <li>
      <label class="text_tit">审核内容：</label>
      <p>
        <s:if test="workItemDTO.fullContent != null">
        <s:set name="fullContent" var="fullContent" value="viewLogicHelper.split(workItemDTO.fullContent,',')"/>
        <s:iterator value="#fullContent" id="param">
        <s:property value="#param"/><br/>
        </s:iterator>
        </s:if>
        <s:else>
        ${workItemDTO.content}
        </s:else>
      </p>
    </li>
    <s:if test="errMsg == null && (workItemDTO.workItemStatus.toString() == 'CHECKING' && bizFor == 'audit' || workItemDTO.workItemStatus.toString() == 'RISK_CHECKING' && bizFor == 'risk') && workItemDTO.submittedBy != #session['SESSION_USERINFO'].userId">
      <li>
        <label class="text_tit">拒绝原因：</label>
        <s:token name="workItemToken"/>
        <input type="hidden" name="workItemId" id="workItemId" value="<s:property value='workItemDTO.workItemId'/>" />
        <input class="input_text" type="text" id="refuseReason" name="refuseReason" value="<s:property value='workItemDTO.rejectCause'/>"/>
        <div class="tip_error" id="refuseReason_error" style="display:none;"></div>
      </li>
    </s:if>
    <s:elseif test="workItemDTO.workItemStatus.toString() == 'REFUSE'">
      <li>
        <label class="text_tit">拒绝原因：</label>
        <p><s:property value="workItemDTO.rejectCause"/></p>
      </li>
    </s:elseif>
    <s:elseif test="workItemDTO.workItemStatus.toString() == 'ERROR'">
      <li>
        <label class="text_tit">错误信息：</label>
        <p><s:property value="workItemDTO.resultComment"/></p>
      </li>
    </s:elseif>
    <li>
      <label class="text_tit">&nbsp;</label>
      <s:if test="errMsg == null && bizFor == 'audit' && workItemDTO.workItemStatus.toString() == 'CHECKING' && workItemDTO.lockedBy == #session['SESSION_USERINFO'].userId">
      <input id="audit" type="button" class="btn_sure fw" value="通 过" onclick="doAudit('${workItemId}');"/>
      <input id="refuse" type="button" class="btn_sure fw" value="拒 绝" onclick="doRefuse('${workItemId}');"/>
      </s:if>
      <s:elseif test="errMsg == null && bizFor == 'risk' && workItemDTO.workItemStatus.toString() == 'RISK_CHECKING' && workItemDTO.submittedBy != #session['SESSION_USERINFO'].userId">
      <input id="audit" type="button" class="btn_sure fw" value="通 过" onclick="doPreAudit('${workItemId}');"/>
      <input id="refuse" type="button" class="btn_sure fw" value="拒 绝" onclick="doRefuse('${workItemId}');"/>
      </s:elseif>
      <s:elseif test="errMsg == null && bizFor == 'submit' && workItemDTO.workItemStatus.toString() == 'WAITING' && workItemDTO.submittedBy == #session['SESSION_USERINFO'].userId">
      <input id="cancel" type="button" class="btn_sure fw" value="撤 销" onclick="doCancel('${workItemId}');"/>
      </s:elseif>
      <input type="button" class="btn_cancel fw" value="关 闭" onclick="MessageBoxExt.unpopup('workItemInfo');"/>
    </li>
  </ul>
  <div class="clearer"></div>
</div>
