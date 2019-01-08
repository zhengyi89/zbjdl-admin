function doPreAudit (workItemId) {
  var timestamp = (new Date()).valueOf();
  workItemId = workItemId || $("#workItemId").val();
  var refuseReason = $("#refuseReason").val();
  if (refuseReason) {
    $("#refuseReason_error").html("审核通过不应填写拒绝原因！");
    $("#refuseReason_error").show();
    return false;
  } else {
    $("#refuseReason_error").html("");
    $("#refuseReason_error").hide();
  }
  MessageBoxExt.confirm(null, function () {
    MessageBoxExt.ajax({
      type : "post",
      url : GV.ctxPath + "/workitem/preAudit?timestamp=" + timestamp,
      style : "redirect",
      data : {
        "workItemId" : workItemId
      },
      success : function (result) {
        MessageBoxExt.unpopup("workItemInfo");
      },
      afterBizError : afterAuditError
    });
  });
}

function doAudit (workItemId) {
  var timestamp = (new Date()).valueOf();
  workItemId = workItemId || $("#workItemId").val();
  var refuseReason = $("#refuseReason").val();
  if (refuseReason) {
    $("#refuseReason_error").html("审核通过不应填写拒绝原因！");
    $("#refuseReason_error").show();
    return false;
  } else {
    $("#refuseReason_error").html("");
    $("#refuseReason_error").hide();
  }
  MessageBoxExt.confirm(null, function () {
    MessageBoxExt.ajax({
      type : "post",
      url : GV.ctxPath + "/workitem/auditItem?timestamp=" + timestamp,
      style : "redirect",
      data : {
        "workItemId" : workItemId
      },
      success : function (result) {
        MessageBoxExt.unpopup("workItemInfo");
      },
      afterBizError : afterAuditError
    });
  });
}

function doRefuse (workItemId) {
  var timestamp = (new Date()).valueOf();
  workItemId = workItemId || $("#workItemId").val();
  var refuseReason = $("#refuseReason").val();
  if (!refuseReason) {
    $("#refuseReason_error").html("请输入拒绝原因！");
    $("#refuseReason_error").show();
    return false;
  }
  if(refuseReason.length > 150){
    $("#refuseReason_error").html("拒绝原因填写过长！");
    $("#refuseReason_error").show();
    return false;
  }else {
    $("#refuseReason_error").html("");
    $("#refuseReason_error").hide();
  }
  MessageBoxExt.confirm(null, function () {
    MessageBoxExt.ajax({
      type : "post",
      url : GV.ctxPath + "/workitem/refuseItem?timestamp=" + timestamp,
      style : "redirect",
      data : {
        "workItemId" : workItemId,
        "refuseReason" : refuseReason
      },
      success : function (result) {
        MessageBoxExt.unpopup("workItemInfo");
      },
      afterBizError : afterAuditError
    });
  });
}

function doCancel (workItemId) {
  var timestamp = (new Date()).valueOf();
  workItemId = workItemId || $("#workItemId").val();
  MessageBoxExt.confirm(null, function () {
    MessageBoxExt.ajax({
      type : "post",
      url : GV.ctxPath + "/workitem/cancelItem?timestamp=" + timestamp,
      style : "redirect",
      data : {
        "workItemId" : workItemId
      },
      success : function (result) {
        MessageBoxExt.unpopup("workItemInfo");
      }
    });
  });
}

function lockWorkItem (workItemId, bizFor) {
  bizFor = bizFor || "show";
  MessageBoxExt.load("workItemInfo", GV.ctxPath
      + "/workitem/lockWorkItem.action?bizFor=" + bizFor + "&workItemId="
      + workItemId, {
    width : 500,
    title : "双权限审核信息",
    close : function () {
      unLockWorkItem(workItemId, bizFor);
    }
  });
}

function unLockWorkItem (workItemId, bizFor) {
  MessageBoxExt.ajax({
    url : GV.ctxPath + "/workitem/unLockWorkItem.action",
    data : {
      "bizFor" : bizFor,
      "workItemId" : workItemId
    },
    style : "none",
    checkSuccess : function (result) {
      // 关闭操作不提示错误
      return true;
    }
  });
}

function viewWorkItem (workItemId, bizFor) {
  bizFor = bizFor || "show";
  MessageBoxExt.load("workItemInfo", GV.ctxPath
      + "/workitem/showItemDetail.action?bizFor=" + bizFor + "&workItemId="
      + workItemId, {
    width : 500,
    title : "双权限审核信息"
  });
}

/**
 * 审核出错后重新加载页面
 */
function afterAuditError () {
  MessageBoxExt.unpopup("workItemInfo");
  $(document).unbind("ajaxError");
  window.location.reload();
}
/**
 * 批量审核
 */
function batchSuccess () {
	var timestamp = (new Date()).valueOf();
	var id = "";
	$("input[type='checkbox'][name='workitemId']:checked").each(function(){
		if ($(this).val()!="on") id += "workItemIds=" + $(this).val() + "&";
	});
	if (id==null || id==""){
		MessageBoxExt.alert("请选择要操作的记录！");
		return;
	}
	MessageBoxExt.confirm("是否批量通过？", function () {
		MessageBoxExt.ajax({
			style : "redirect",
			url : GV.ctxPath + "/workitem/auditItem.action?" + id + "&timestamp=" + timestamp,
			type : "post",
			afterBizError : afterAuditError
		});
	});
}
/**
 * 批量预审核通过
 */
function batchPreSuccess () {
	var timestamp = (new Date()).valueOf();
	var id = "";
	$("input[type='checkbox'][name='workitemId']:checked").each(function(){
		if ($(this).val()!="on") id += "workItemIds=" + $(this).val() + "&";
	});
	if (id==null || id==""){
		MessageBoxExt.alert("请选择要操作的记录！");
		return;
	}
	MessageBoxExt.confirm("是否批量通过？", function () {
		MessageBoxExt.ajax({
			type : "post",
			url : GV.ctxPath + "/workitem/preAudit.action?" + id + "&timestamp=" + timestamp,
			style : "redirect",
			afterBizError : afterAuditError
		});
	});
}
/**
 * 批量拒绝
 */
function batchRefuse () {
	MessageBoxExt.popup("batchRefuseDiv", {
		width : 700,
		title : "批量拒绝理由",
		height : 400,
		buttons : [{
			text : '保存',
			click : function () {
				var timestamp = (new Date()).valueOf();
				var refuseReason = $("#refuseReason2").val();
				var id = "";
				$("input[type='checkbox'][name='workitemId']:checked").each(function(){
					if ($(this).val()!="on") id += "workItemIds=" + $(this).val() + "&";
				});
				if (!refuseReason) {
					MessageBoxExt.alert("请输入拒绝理由!");
					return;
				}
				if (id==null || id==""){
					MessageBoxExt.alert("请选择要操作的记录！");
					return;
				}
				MessageBoxExt.ajax({
					style : "REDIRECT",
					url : GV.ctxPath + "/workitem/refuseItem.action?" + id + "&refuseReason=" + refuseReason + "&timestamp=" + timestamp,
					type : "POST",
					afterBizError : afterAuditError
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
$(document).ready(function () {
  DatePickerExt.between("submitStartTime", "submitEndTime");
  DatePickerExt.between("approveStartTime", "approveEndTime");
  $("#invocatResult").attr("disabled", true);
  $("#workItemStatus").change(function () {
    if ($(this).val() == 'SUCESS' || $(this).val() == 'ERROR') {
      $("#invocatResult").removeAttr("disabled");
    } else {
      $("#invocatResult").attr("disabled", true);
    }
  });

	$("div.result").append($("#hidden_operat").html());
	$("#hidden_operat").remove();

	// 全选
	$("#selectAll").click(function () {
		//$('input[type="checkbox"][name="workitemId"]').attr("checked",this.checked);
		var checkboxs = $('input[type="checkbox"][name="workitemId"]');
		var username = $("div.login").html();
		username = username.substring(username.indexOf(">")+1, username.indexOf("/")-1);
		for (var i in checkboxs) {
			var tds = $(checkboxs[i]).parents("tr").find("td");
			if($.trim($(tds[1]).text()) == username) {
				$(checkboxs[i]).attr("checked", false);
			} else if ($.trim($(tds[4]).text()) == "待审核") {
				$(checkboxs[i]).attr("checked", this.checked);
			} else {
				$(checkboxs[i]).attr("checked", false);
			}
		}
	});
	$('input[type="checkbox"][name="workitemId"]').click(function() {
		var username = $("div.login").html();
		username = username.substring(username.indexOf(">")+1, username.indexOf("/")-1);
		var tds = $(this).parents("tr").find("td");
		if($.trim($(tds[4]).text()) != "待审核") {
			MessageBoxExt.alert("批量操作只支持待审核记录！");
			$(this).attr("checked", false);
		} else if($.trim($(tds[1]).text()) == username) {
			MessageBoxExt.alert("不能审核自己提交的记录！");
			$(this).attr("checked", false);
		}
	})
});