<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>修改密码</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
  $("#originalPasswordMessage").empty();
  $("#newPasswordMessage").empty();
  $("#confirmPasswordMessage").empty();
  $("#systemMsg").empty();
  if(location.search.indexOf("decoration=false") > 0) {
	MessageBoxExt.popup("dialogView", {
		width : 320,
		title : "系统提示",
		height : 150,
		buttons : [{
			text : '立即修改',
			click : function () {
				$(this).dialog("close");
			}
		}<s:if test="!firstLogin">, {
			text : '不再提醒',
			click : function () {
				$.ajax({
					type : "post",
					url : GV.ctxPath + "/user/modifyUserInfo.action",
					data : {
						"userDto.pwdShowNotice": "2"
					},
					success: function (result, textStatus) {
						<s:if test="toPortal">
						window.location.href = "${ctxPath}/menu/customerPortal?decoration=false"
						</s:if>
						<s:else>
						window.location.href = "${ctxPath}/menu/showMenu"
						</s:else>
					}
				});
			}
		}</s:if>]
	});
  }
});

function checkPass(s) {
	if (s.length < 8) {
		return 0;
	}
	var ls = 0;
	if (s.match(/([a-z])+/)) {
		ls++;
	}
	if (s.match(/([0-9])+/)) {
		ls++;
	}
	if (s.match(/([A-Z])+/)) {
		ls++;
	}
	if (s.match(/[^a-zA-Z0-9]+/)) {
		ls++;
	}
	return ls
}

function modifyPassword() {
	 $("#originalPasswordMessage").empty();
	 $("#newPasswordMessage").empty();
	 $("#confirmPasswordMessage").empty();
	 $("#systemMsg").empty();

  var originalPassword =$("#originalPassword").val();
  var newPassword = $("#newPassword").val();
  var confirmPassword= $("#confirmPassword").val();

  var timestamp = (new Date()).valueOf();
  if(originalPassword == null || originalPassword == "") {
    $("#originalPasswordMessage").append("请输入当前密码");
    return false;
  }

  if(newPassword == null || newPassword == "") {
    $("#newPasswordMessage").append("请输入新密码");
    return false;
  }
  if(newPassword != confirmPassword) {
    $("#confirmPasswordMessage").append("两次输入密码不一致");
    return false;
  }

  if(checkPass(newPassword) < 3){
    $("#confirmPasswordMessage").append("密码复杂度不够");
    return false;
  }
  
  $.ajax({
    "url" : "modifyPwd?timestamp="+timestamp,
    "type" : "POST",
    "data": {"originalPassword":originalPassword,"newPassword":newPassword,"confirmPassword":confirmPassword},
    "dataType" : "json",
    "complete" : function(jqXHR, status) {
      if(status == "success") {
        var result = $.parseJSON(jqXHR.responseText);
        if(result != null) {
          var status = result.message;
          if(status == "newpassword_confirmpassword_notsame") {
            $("#confirmPasswordMessage").append("两次输入密码不一致!");
          }
          else if(status == "password_same") {
            $("#systemMsg").append("新密码与原始密码相同!");
          }
          else if(status == "password_name_same") {
            $("#systemMsg").append("新密码与登录名相同!");
          }
          else if(status == "password_error") {
            $("#systemMsg").append("当前密码输入错误!");
          }
          else if(status == "success") {
	        MessageBoxExt._styleRedirect("修改密码成功, 点击确定跳转到首页",
		        <s:if test="toPortal">
		        "${ctxPath}/menu/customerPortal?decoration=false"
		        </s:if>
		        <s:else>
		        "${ctxPath}/menu/showMenu"
		        </s:else>
            );
          }
          else {
            $("#systemMsg").append(status);
          }
        }
	    return false;
      } else {
        $("#systemMsg").append(
            "系统异常，请稍候重试！"
          );
        return false;
      }
    }
  });
  return false;
}
</script>
</head>
<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <form action="modifyPwd" method="post">
      <div class="information">
        <h1 class="fw">修改密码</h1>
        <div class="input_cont">
          <ul>
            <li>
              <label class="text_tit">当前密码：</label>
              <input type="password" class="input_text" name="originalPassword" id="originalPassword" title="required" value="" onpaste="return false"/>
              <font color="red"><span id="originalPasswordMessage"></span></font>
            </li>
            <li>
              <label class="text_tit">新密码：</label>
              <input type="password" class="input_text" name="newPassword" id="newPassword" title="required" value="" onpaste="return false"/>
              <font color="red"><span id="newPasswordMessage"></span></font>
            </li>
            <li>
              <label class="text_tit">重输新密码：</label>
              <input type="password" class="input_text" name="confirmPassword" id="confirmPassword"  title="required" value="" onpaste="return false"/>
              <font color="red"><span id="confirmPasswordMessage"></span></font>
            </li>
            <li>
              <label class="text_tit">&nbsp;</label>
              <font color="red"><span id="systemMsg"></span></font>
            </li>
            <li>
              <label class="text_tit">&nbsp;</label>
              <input type="button" class="btn_sure fw" value="确定" onclick="modifyPassword();"/>
            </li>
          </ul>
        </div>
        <p>密码要求：1.至少3个月修改一次密码；2.长度大于8位；3.密码必须是字母大写，字母小写，数字，特殊字符中任意三个组合。</p>
      </div>
    </form>
  </div>
</div>
<div id="dialogView">
  <s:if test="firstLogin">
  <span id="failMessage">首次登录请及时修改密码!</span>
  </s:if>
  <s:else>
  <span id="failMessage">距离上次修改密码已经超过90天，请及时修改密码!</span>
  </s:else>
</div>
</body>
</html>