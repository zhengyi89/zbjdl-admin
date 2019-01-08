<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<div id="migrateDepartmentView" style="display: none;">
	<form id="migrateDepartmentForm" method="post" onsubmit="return false;">
		<input type="hidden" name="departmentId" id="departmentId2" />
		<div id="wrapper" class="input_cont border_n">
			<ul>
				<li>
					<div class="tip_error">不迁移的数据将被清空！</div>
				</li>
			</ul>
			<ul>
				<li style="overflow:visible;">
					<label class="text_tit">拟迁移到的部门</label>
					<s:select list="departList" cssClass="select chosen" headerValue="请选择" listKey="departmentId" listValue="departmentName" id="newDepartmentId" name="newDepartmentId"/>
				</li>
			</ul>
			<ul>
				<li>
					<label class="text_tit">是否迁移用户</label>
					<input type="checkbox" name="migrateUser" class="checkbox" value="true" checked="checked"/>
				</li>
			</ul>
			<ul>
				<li>
					<label class="text_tit">是否迁移角色</label>
					<input type="checkbox" name="migrateRole" class="checkbox" value="true" checked="checked"/>
				</li>
			</ul>
			<ul>
				<li>
					<label class="text_tit">是否迁移功能</label>
					<input type="checkbox" name="migrateFunction" class="checkbox" value="true" checked="checked"/>
				</li>
			</ul>
		</div>
	</form>
</div>