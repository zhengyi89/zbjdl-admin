function resetPassword (userId, actionUrl) {
  popupPasswordDialog_Ajax(userId, "user", actionUrl)
}

function ajaxSubmit (userId, actionUrl) {

  var timestamp = (new Date()).valueOf();
  var password = $("#password").attr("value");
  MessageBoxExt.ajax({
    url : "user/" + actionUrl + "?timestamp=" + timestamp + "&password="
        + password,
    data : {
      "userId" : userId
    },
    style : "redirect"
  });
}

function popupPasswordDialog (userId, action, actionUrl) {
  initPasswordDialog();
  openPasswordDialog(action, actionUrl);
  $("#userId").attr("value", userId);
}

function popupPasswordDialog_Ajax (userId, action, actionUrl) {
  initPasswordDialog();
  openPasswordDialog_Ajax(action, actionUrl, userId);
  $("#userId").attr("value", userId);
}

function openPasswordDialog_Ajax (action, actionUrl, userId) {
  $passwordDialogView.dialog(
      {
        title : "请输入管理员密码",
        height : 150,
        width : 400,
        buttons : {
          "确认" : function () {
            $("#passwordMsg").empty();
            var password = $("#password").val();
            if (password == "" || password == null) {
              $("#passwordMsg").append(
                  "<font color='red'>" + "请输入管理员密码！" + "</font>");
            } else {
              var timestamp = (new Date()).valueOf();
              MessageBoxExt.ajax({
                url : action + "/" + actionUrl + "?timestamp=" + timestamp
                    + "&password=" + password,
                data : {
                  "userId" : userId
                },
                style : "redirect"
              });
              $("#password").attr("value", "");
              $(this).dialog("close");
            }
          },
          "关闭" : function () {
            $("#passwordMsg").empty();
            $("#password").attr("value", "");
            $(this).dialog("close");
          }
        },
        close : function (event, ui) {
          $("#passwordMsg").empty();
        },
        open : function (event, ui) {
          $("#passwordMsg").empty();
        }
      }).dialog("open");
}

function batchAudit(){
	if ($("td > :checkbox:checked").length < 1) {
		MessageBoxExt.alert("请选择菜单!");
		return;
	}
	var userIds = $("td > :checkbox:checked").serialize();
	window.location.href = "http://boss.zbjdl.in:8080/security-boss/user/showAuthority?"+userIds;
}

$(document).ready(function(){
	$("div.result").append($("#hidden_operat").html());
	$("#hidden_operat").remove();
	$("#batchAudit").click(batchAudit);

	// 全选
	$("#selectAll").click(function() {
		$('input[type="checkbox"][name="userIds"]').attr("checked",this.checked);
	});
});