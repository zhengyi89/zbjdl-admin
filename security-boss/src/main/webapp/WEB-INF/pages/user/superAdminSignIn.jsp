<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理员登录</title>
<script type="text/javascript">
$(document).ready(function(){
  activeButton();
  $("#validate_error").remove();
  $("#code_error").remove();
  $(document).keypress(function(event){
    if(13 == event.keyCode) {
      validateInput();
    }
  });
});

$(function(){
  $("#superPassword").keyup(function(){
      $("#superPassword").next().next().remove();
  });
  $("#loginName").keyup(function(){
      $("#loginName").next().next().remove();
  });
  $("#password").keyup(function(){
      $("#password").next().next().remove();
  });
  $("#code").keyup(function(){
      $("#code").next().next().remove();
  });
  $(":input").keyup(function(){
      $("#errorMessage").text("");
  });
  })

function validateInput(){
  var inputElements = $(":input");
  var valid = true;
  var notNull = true;
  var pattern = /^([a-zA-Z0-9\_\-\.\u4e00-\u9fa5])+$/;
  
  $.each(inputElements,function(i,item){
    var inputType = item.type;
    if(inputType == "text" || inputType == "password") {
      var title = item.title;
      var inputValue = item.value;
      var messageId = item.id + "_" + title;
      if(title == "required") {
        $("#" + messageId).remove();
        if(inputValue == null || inputValue == "") {
          $(this).next().after("<span class='Error' id='" + messageId + "'><font color='red'>不能为空</font></span>");
          activeButton();
          notNull = false;
        } 
      }
    }
  });

  if(!notNull) {
    activeButton();
    return false;
  }

  $.each(inputElements,function(i,item){
    var inputType = item.type;
    if(inputType == "text" || inputType == "password") {
      var inputValue = item.value;
      if(!pattern.test(inputValue)){
         valid = false;
        }
    }
  });
  
  if(!valid) {
    var message = "输入字符中只允许含有数字、汉字、大小写字母、下划线_、横线-、点."
    jAlert(message,"警告");
    activeButton();
    return false;
  }

  return true;
}
function refreshCode(){
  var timestamp = (new Date()).valueOf();
  src = "../Kaptcha.jpg?timestamp="+timestamp;
  $("#securityCode").attr("src",src);
}

function formValidate(){
  $("#loginButton").attr("disabled","disabled");
  $("#loginButton").val("请稍候...");
  return validateInput();
}

function activeButton(){
  $("#loginButton").removeAttr("disabled");
  $("#loginButton").val("登录");
}

</script>
<style type="text/css">
body { background:url(../css/common/img/login_bj.gif) repeat-y  center top #e0e2e2;}
.main { width:1000px; height:550px; margin:auto;}
.logo { padding:45px 0 0 45px;}
.login { margin:100px 0 0 100px; width:400px;  color:#666;font-size:14px;}
.login_top,.login_con,.login_bot { }
.login_top { width:340px; background:url(../css/common/img/login_top.png) no-repeat; height:30px; padding:20px 30px 10px 30px;}
.login_top h2 { background:url(../css/common/img/icon.gif) no-repeat 0 3px; padding-left:25px; font-weight:bold; height:30px; line-height:30px; }
.login_con { background:url(../css/common/img/login_con.png);overflow:hidden; height:1%;padding:0px 30px 20px 30px;width:340px;} 
.login_con dl dd { float:left; margin-bottom:8px; height:30px; line-height:30px;}
.login_con dl dt { padding-left:60px; }
.login_bot {background:url(../css/common/img/login_bot.png); height:60px;}
.login_con .text_l { width:60px; text-align:right;}
.login_con .text_r { width:260px;}
.login_con .text_r span { margin-left:10px;}
.login_con .Error { background:url(../css/common/img/icon.gif) no-repeat 0 -30px; padding-left:20px; color:#F00; font-size:12px;}
.w80 { width:80px;}
.w165 { width:165px; height:27px; line-height:27px;}
/*底部*/
.Foot{ height:36px; color:#FFF; line-height:28px; background:url(../css/common/img/templet_footBg.gif) repeat-x 0 0; text-align:center; margin-top:34px;}
.downFoot{ height:33px; color:#666; clear:both; text-align:center;}
div{ behavior: url("../js/iepngfix.htc") };
</style>
</head>

<body>
<div class="main">
  <div class="logo"></div>
  <form action="superAdminSignIn" method="post" onsubmit="return formValidate();">
  <div class="login">
    <div class="login_top">
           <h2><a href="showLogin">用户登录</a>&nbsp;&nbsp;/&nbsp;&nbsp;<a href="showSignIn">管理员登录</a></h2>
       </div>
       <div class="login_con">
       <dl>
           <dd class="text_l">登录名：</dd><dd class="text_r">Admin</dd>
          <dd class="text_l">密码：</dd><dd class="text_r"><input class="input_text w165" type="password" id="superPassword" name="superPassword" title="required" value="" onpaste="return false"/><span></span></dd>
          <dd class="text_l">用户名：</dd><dd class="text_r"><input class="input_text w165" type="text" id="loginName" name="loginName" title="required" value="${loginName}" style="ime-mode:disabled"/><span></span></dd>
          <dd class="text_l">密码：</dd><dd class="text_r"><input class="input_text w165" type="password" id="password" name="password" title="required" value="" onpaste="return false"/><span></span></dd>
          <dd class="text_l">验证码：</dd><dd class="text_r"><input class="input_text w80" type="text" name="code" id="code" title="required" value=""/><span><a href="javascript:void(0);" onclick="refreshCode();"><img id="securityCode" width="75px" height="25px" src="../Kaptcha.jpg"/></a></span></dd>
         <dt><input id="loginButton" type="submit"  class="buttonSty bt2" value="登 录" /></dt>
       </dl>
       <br/>
          <span id="errorMessage" style="color:red"><s:actionmessage/></span>
       </div>
        <div class="login_bot"></div>
    </div>
    </form>
<div class="clearer"></div>
<div class="Foot">© 2015-2016 云宝金服版权所有 </div>
<div class="downFoot"></div>
</div>
<div id="dialogView">
</div>
</body>
</html>
