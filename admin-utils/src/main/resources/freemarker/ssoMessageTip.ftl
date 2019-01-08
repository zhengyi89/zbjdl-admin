<!DOCTYPE html>
<#assign rsPath = resourcePath!"http://188.131.141.60:8080/static"/>
<html lang="zh-CN">
  <head>
    <title>请重新登录</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0"/>
    <link rel="stylesheet" href="${rsPath}/bootstrap/bootstrap.min.css?version=1.0.1"/>
    <link rel="stylesheet" href="${rsPath}/css/reset.css?version=1.0.1">
  </head>
  <body class="greenBg">
    <div class="tips_head"></div>
    <div class="tips_body">
      <p class="title">温馨提示</p>
      <p class="detail">${tips}</p>
    </div>
    <div class="tips_foot">
      <!-- -->
       <#if loginAction??>
        <a href="${loginAction}?returnUrl=${returnUrl!""}">重新登录</a>
      </#if>
      <#if !loginAction??>
        <a href="#" onclick="javascript:self.history.back();">返回上一页</a>
      </#if>
      
    </div>
  </body>
</html>
