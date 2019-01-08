<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html ">
<html lang="zh-CN">
  <head>
    <title>提示</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0"/>
    <link rel="stylesheet" href="${resourcePath}/bootstrap/bootstrap.min.css"/>
    <link rel="stylesheet" href="${resourcePath}/css/reset.css">
  </head>
  <body class="greenBg">
    <div class="tips_head"></div>
    <div class="tips_body">
      <p class="title">温馨提示</p>
      <p class="detail">
           <c:choose>
              <c:when test="${not empty ssoLoginInfo}">
              <h3>由于其他人员使用您的账号，您已被迫下线！</h3>
              <h6>对方ip：<span style="color:red">${ssoLoginInfo.loginIp}</span> </h6>
              <h6>对方登录时间：<span style="color:red"><fmt:formatDate value="${ssoLoginInfo.loginTime}"  pattern="yyyy-MM-dd HH:mm:ss"/> </span> </h6>
              </c:when>
              <c:otherwise>
                您已登录超时！
              </c:otherwise>
          </c:choose>
      
      </p>
    </div>
    <div class="tips_foot">
      您现在可以选择：<span><a style="color: red;" href="${ctxPath}/loginout/showLogin?returnUrl=${returnUrl}">重新登录</a></span>
    </div>
  </body>
</html>

