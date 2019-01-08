<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新建操作员</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp" %>
<script type="text/javascript">
$(document).ready(
    function () {
      var valList = {
        "primaryDepartmentId" : {
          req : true,
          label : "部门"
        },
        "loginName" : {
          req : true,
          datatype : "^\\w+([-+.]\\w+)*$",
          len : {min:4,max:50},
          label : "登录名称"
        },
        "userName" : {
          req : true,
          label : "姓名"
        },
        "email" : {
          req : true,
          datatype : "email",
          len : {max:50},
          label : "邮箱"
        },
        "mobile" : {
          datatype : "mobile",
          len : {max:50},
          label : "手机"
        },
        "password" : {
          req : true,
          label : "管理员登录密码"
        }
      };
      ValidateExt.listen("addSysAdminFrom", valList);
      $("#addSysAdminBtn").click(function () {
        if (ValidateExt.val("addSysAdminFrom")) {
          MessageBoxExt.ajax({
            type : "post",
            url : GV.ctxPath + "/user/addSysAdmin.action",
            data : $("#addUserForm").serialize(),
            style : "redirect",
            toUrl : GV.ctxPath + "/user/addSysAdmin.action"
          });
        }
      });
    });
</script>
</head>
<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <form id="addSysAdminFrom" method="post">
      <div class="information">
        <h1 class="fw">操作员参数</h1>
        <div class="input_cont">
        <ul>
          <li>
            <label class="text_tit">登录名：</label>
            <s:textfield cssClass="input_text" name="loginName" value="%{#request.loginName}"></s:textfield>
          </li>
          <li>
            <label class="text_tit">姓名：</label>
            <s:textfield cssClass="input_text" name="userName" value="%{#request.userName}"></s:textfield>
          </li>
          <li>
            <label class="text_tit">邮箱：</label>
            <s:textfield cssClass="input_text" name="email" value="%{#request.email}"></s:textfield>
          </li>
          <li>
            <label class="text_tit">手机：</label>
            <s:textfield cssClass="input_text" name="mobile" value="%{#request.mobile}"></s:textfield>
          </li>
          <li>
            <label class="text_tit">管理员登录密码：</label>
            <input class="input_text" type="password" id="password" name="password" value=""/>
          </li>
          <li>
            <label class="text_tit">&nbsp;</label>
            <input type="button" id="addSysAdminBtn" class="btn_sure fw" value="确定" />
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
