<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <title>登录</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0"/>
    <link rel="stylesheet" href="${resourcePath}/bootstrap/bootstrap.min.css"/>
    <link rel="stylesheet" href="${resourcePath}/css/reset.css">
    <link rel="stylesheet" href="${resourcePath}/mCustomScrollbar/jquery.mCustomScrollbar.css"/>
    <style type="text/css">
     .input_content .input_error,.input_content_s .input_error{position:absolute;top:35px;left:10px;color:red;}
    </style>
 <script type="text/javascript" src="${resourcePath}/boss/common/component/jquery1.8.3/lib/jquery.min.js"></script>
<script type="text/javascript">
if (top.location != self.location) {
    top.location.href="${pageContext.request.contextPath}/loginout/logout";
}
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
          $(this).parent("div").find("p[class=input_error]").html("").append("不能为空");
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
  
  return true;
}

function refreshCode(){
  var timestamp = (new Date()).valueOf();
  src = "../captcha?timestamp="+timestamp;
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
  </head>
  <body class="darkBg">
    <div id="content_1" class="form_outer">
      <form class="form_size" action="${pageContext.request.contextPath}/loginout/login" id="godownForma"
			name="godownForma" method="post" onsubmit="return formValidate()">
        <input type="hidden" id="isShowCode" name="isShowCode" value="${isShowCode }" />
        <input type="hidden" id="returnUrl" name="returnUrl" value="${returnUrl }" />
      
        <div class="form_title">用户登录</div>
        <div class="form_content">
          <div class="form_team">
          
          <span id="errorMessage" style="color:red">${errorMessage }</span>
            <div class="input_content">
              <i class="input_label icon iconfont">&#xe624;</i>
              <input  title="required"  id="loginName" name="loginName" type="text" class="input_self" placeholder="" value="${loginName }"/>
              <p class="input_error"></p>
            </div>
            <div class="input_content">
              <i class="input_label icon iconfont">&#xe67a;</i>
              <input  title="required"   id="password" name="password" type="password" class="input_self" placeholder="" value=""/>
              <p class="input_error"></p>
            </div>
        <c:if test="${isShowCode == 'true'}">
            <div class="input_content_s">
              <i class="input_label icon iconfont">&#xe608;</i>
              <input  title="required"  name="code" id="code" type="text" class="input_self" placeholder="" value=""/>
              <a href="javascript:void(0);" onclick="refreshCode();"><img id="securityCode" width="75px" height="25px" src="../captcha"/></a>
              <p class="input_error"></p>
            </div>
        </c:if>
          </div>
          <div class="form_submit">
            <button id="loginButton" class="submit_login" type="submit">提<span class="blank_20"></span>交</button>
          </div>
        </div>
      </form>
    </div>
    <script type="text/javascript" src="${resourcePath}/js/jquery.1.9.1.min.js"></script>
    <script type="text/javascript" src="${resourcePath}/bootstrap/bootstrap.min.js"></script>
  </body>
</html>

