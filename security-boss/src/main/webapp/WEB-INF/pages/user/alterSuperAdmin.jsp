<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系管员签到变更</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp"%>
</head>

<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <form action="alterSuperAdmin" method="post" onsubmit="return validateInput();">
      <div class="information">
        <h1 class="fw">操作员参数</h1>
        <div class="input_cont">
          <ul>
            <li>
              <label class="text_tit">新签到登录用户名：</label>
              <input type="text" class="input_text" name="loginName" title="required" value=""/>
            </li>
            <li>
              <label class="text_tit">Admin登录密码：</label>
              <input type="password" class="input_text" title="required" name="superPassword" value=""/>
            </li>
            <li>
              <label class="text_tit">&nbsp;</label> 
              <input type="submit" class="btn_sure fw" value="确定" />
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
