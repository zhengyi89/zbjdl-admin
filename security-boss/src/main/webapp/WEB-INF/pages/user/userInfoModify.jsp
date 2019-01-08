<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息修改</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp"%>
<script type="text/javascript">
$(document).ready(
    function () {
      var valList = {
        "userDto.userName" : {
          req : true,
          label : "姓名"
        },
        "userDto.email" : {
          req : true,
          datatype : "email",
          len : {max:50},
          label : "邮箱"
        },
        "userDto.mobile" : {
          datatype : "mobile",
          len : {max:50},
          label : "手机"
        }
      };
      ValidateExt.listen("modifyInfoForm", valList);
      $("#modifyInfoBtn").click(function () {
        if (ValidateExt.val("modifyInfoForm")) {
          MessageBoxExt.ajax({
            type : "post",
            url : GV.ctxPath + "/user/modifyUserInfo.action",
            data : $("#modifyInfoForm").serialize()
          });
        }
      });
    });
</script>
</head>
<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <form method="post" id="modifyInfoForm">
      <div class="information">
        <h1 class="fw">信息修改</h1>
        <div class="input_cont">
          <ul>
            <li>
              <label class="text_tit">姓名：</label>
              <s:textfield cssClass="input_text" name="userDto.userName" value="%{userDto.userName}"/>
            </li>
            <li>
              <label class="text_tit">邮箱：</label>
              <s:textfield cssClass="input_text" name="userDto.email" value="%{userDto.email}"/>
            </li>
            <li>
              <label class="text_tit">手机：</label>
              <s:textfield cssClass="input_text" name="userDto.mobile" value="%{userDto.mobile}"/>
            </li>
	        <li>
		      <label class="text_tit">定期提示修改密码：</label>
		      <input type="checkbox" name="userDto.pwdShowNotice" value="1" <s:if test="userDto.pwdShowNotice==1">checked</s:if> />
            </li>
            <li>
              <label class="text_tit">&nbsp;</label>
              <input type="button" id="modifyInfoBtn" class="btn_sure fw" value="确定" />
              <input type="button" class="btn_cancel fw" value="取消" onclick="history.go(-1);"/>
            </li>
          </ul>
        </div>
        <div class="clearer"></div>
      </div>
    </form>
  </div>
</div>
</body>
</html>