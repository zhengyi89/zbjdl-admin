<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp" %>
<div id="batchAuditDiv" class="input_cont border_n" style="display:none;">
	<form id="batchAuditForm" method="get">
		<ul>
			<li style="overflow:visible;clear:both;width:auto;">
				<label class="text_tit">所属部门：</label>
				<div class="select_border">
					<div class="container">
						<s:select list="departList" cssClass="select chosen" headerKey="" headerValue="请先选择部门" listKey="departmentId" listValue="departmentName" id="c_departmentId" onchange="loadOperator()"/>
					</div>
				</div>
			</li>
			<li style="overflow:visible;clear:both;width:auto;">
				<label class="text_tit">操作员：</label>
				<div class="select_border">
					<div class="container">
						<select name="userIds" id="operators" class="select chosen" multiple></select>
					</div>
				</div>
			</li>
			<li style="clear:both;"><label class="text_tit">提示：</label>
				按着 Ctrl 可以连续选择多个操作员，支持查询功能
			</li>
			<li><label class="text_tit">管理员密码：</label>
				<input type="password" class="input_text" name="password"/>
				<span id="passwordMsg"></span>
			</li>
		</ul>
	</form>
	<div class="clearer"></div>
</div>