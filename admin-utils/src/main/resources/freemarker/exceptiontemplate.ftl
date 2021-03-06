<!DOCTYPE html>
<#assign rsPath = resourcePath!"http://188.131.141.60:8080/static"/>
<html lang="zh-CN">
  <head>
    <title>提示</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0"/>
    <link rel="stylesheet" href="${rsPath}/bootstrap/bootstrap.min.css?version=1.0.1"/>
    <link rel="stylesheet" href="${rsPath}/css/reset.css?version=1.0.1">
  </head>
  <body class="greenBg">
    <div class="tips_head"></div>
    <div class="tips_body">
      <p class="title">温馨提示</p>
  <#if einfo??>
    <p class="detail">系统运行异常,请与管理员联系。
      <h5>异常信息：${einfo.exceptionClassInfo!""}|${einfo.exceptionMessage!""}</h5>
      
   </p>
  <#else>
    <p class="detail">系统运行异常,请与管理员联系。</p>
     <#if message??>
        <h5>${message}</h5>
      </#if>
    
  </#if>
  
    </div>
    <div class="tips_foot">
      <a href="#" onclick="javascript:self.history.back();">返回上一页</a>
    </div>
  </body>
</html>
