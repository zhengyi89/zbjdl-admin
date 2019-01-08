function popupPasswordDialog(roleId,action,actionUrl) {
	initPasswordDialog();
	openPasswordDialog(action,actionUrl);
	$("#roleId").attr("value",roleId);
}

function batchAudit(roleId) {
	MessageBoxExt.popup("batchAuditDiv", {
		width : 650,
		title : "分配权限",
		height : 480,
		buttons : [{
			text : '保存',
			click : function () {
				MessageBoxExt.confirm("确认批量分配角色吗？", function () {
					MessageBoxExt.ajax({
						style : "REDIRECT",
						url : GV.ctxPath + "/user/distributeAuthority.action",
						data : $("#batchAuditForm").serialize()+"&roleId="+roleId,
						type : "POST"
					});
				});
				$(this).dialog("close");
			}
		}, {
			text : '关闭',
			click : function () {
				$(this).dialog("close");
			}
		}]
	});
}

$.fn.outerHTML = function(s) {
	return s
		? this.before(s).remove()
		: jQuery("<p>").append(this.eq(0).clone()).html();
};
function loadOperator() {
	var departmentId = $("#c_departmentId").val();
	MessageBoxExt.ajax({
		url: GV.ctxPath + "/user/loadOperator.action?departmentId=" + departmentId,
		type: "POST",
		style: "BASIC",
		dataType: "json",
		checkSuccess: function (data) {
			//$("#operators_chzn > div > ul > li.active-result").remove();
			var selected = "";
			$.each($("#operators_chzn > ul > li.search-choice > a"), function (index, element) {
				selected += $("#operators > option").eq($(element).attr("rel"))[0].outerHTML;
			});
			$("#operators").empty();
			var json = eval("(" + data.errMsg + ")");
			$.each(json, function (index, element) {
				$("#operators").append("<option value='" + element.userId + "'>" + element.loginName + "(" + element.userName + ")</option>");
			});
			$("#operators").append(selected.replace(new RegExp("option value","gm"), "option selected='selected' value"));
			$('.chosen').trigger("liszt:updated");
			return true;
		}
	});
}