<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
  <div class="content_wrapper">
    <!------------ 404错误 ------------>
    <div class="error404">
      <div class="error404_b">
        <div class="error404_con">
            <h2>错误页面！</h2>
            <p>产生该错误的原因： </p>
            <br> <span style="color:#6633ff">您所请求的页面不存在</span>
            <p class="kongbai"></p>
            <p>您现在可以返回：<span><a href="javascript:history.go(-1);">上一页</a></span></p>
          </div>
        </div>
      </div>
  </div>
</body>
</html>
