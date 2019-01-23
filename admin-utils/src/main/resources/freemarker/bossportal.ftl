<#if Request.isAjax?? && Request.isAjax=="true">
  <sitemesh:write property="body"/>
<#else>
<#setting number_format="#">
<!DOCTYPE html>
<html>
<#assign resourcePath = Request.resourcePath!""/>
<#if resourcePath == "">
<#assign resourcePath = "http://188.131.141.60:8080/static"/>
</#if>
<#assign bossPath = Request.employeePath!"http://188.131.141.60:8080/admin-boss"/>
<#assign pageType = Session.pageType!""/>
<#assign decoration = Request.decoration!""/>
<#assign jqueryVersion = Request.jqueryVersion!""/>
<#if jqueryVersion == "">
<#assign jqueryVersion = "1.8.3"/>
</#if>
<head>
<title><sitemesh:write property="title"/></title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1.0"/>
  <link rel="stylesheet" href="${resourcePath}/bootstrap/bootstrap.min.css?version=1.0.1"/>
  <link rel="stylesheet" href="${resourcePath}/css/reset.css?version=1.0.1">
  <link rel="stylesheet" href="${resourcePath}/mCustomScrollbar/jquery.mCustomScrollbar.css"/>
  
   <#if Session.SESSION_USERINFO?? && (!Request.purepage?? || Request.purepage != "true")>
    <script type="text/javascript" src="${resourcePath}/js/jquery.1.9.1.min.js"></script>
    
  </#if>
  <sitemesh:write property='head'/>
</head>
<body>
    <input type="hidden" name="_employeePath_" id="_employeePath_" value="${employeePath!""}"/>

<#if Session.SESSION_USERINFO?? && Session.menuTree?? && decoration != "false">
  <div class="content_logo"><img src="${resourcePath}/images/logo.png"/></div>
  <div class="content_nav">
  	<#if Session.SESSION_ACCOUNTINFO??>
  		<#if Session.SESSION_ACCOUNTINFO.accountStatus>
			已结账
		</#if>  
  		<#if !Session.SESSION_ACCOUNTINFO.accountStatus>
			记账中
		</#if>  
		&nbsp; ${Session.SESSION_ACCOUNTINFO.systemName} &nbsp;  ${Session.SESSION_ACCOUNTINFO.accountMonth}
  		
  		<!-- <select id="accountMonth" name="accountMonth" onchange="changeAccountMonth();">
  			<#list Session.SESSION_ACCOUNTINFO.dateSet as date> 
		   		<option value="${date}"
		   			<#if date == Session.SESSION_ACCOUNTINFO.accountMonth>
		   				selected
					</#if>
		   		>${date}</option>
		   		
			</#list>
		</select>
		-->
  	</#if>
  		
<!--    <a class="notice" alt="账户维护"><span class="glyphicon glyphicon-cog"></span><span class="badge count bg-success">4</span></a>
    <a class="notice" alt="修改密码"><span class="glyphicon glyphicon-pencil"></span><span class="badge count bg-info">6</span></a>
    <a class="notice" alt="最新公告"><span class="glyphicon glyphicon-tasks"></span><span class="badge count bg-warning">2</span></a>
    <a class="notice" alt="常见问题"><span class="glyphicon glyphicon-fire"></span><span class="badge count bg-danger">10</span></a>
    -->
    <a class="quit" href="${bossPath}/loginout/logout"><span class="glyphicon glyphicon-off"></span></a>
    <div class="user">
      <!-- 登录信息显示 -->
      <img class="photo" src="${resourcePath}/images/user.jpg">
        <span class="name">${Session.SESSION_USERINFO.userName!""}</span>
    </div>
  </div>
  <!-- 菜单-->
    <div id="content_1" class="content_menu">
      <ul class="menu">
        <li>
          <a name="menu_parent"><span class="glyphicon glyphicon-home"></span><span class="content_blank_15"></span>首页</a>
        </li>

         <#list Session.menuTree.subMenus as system>
          <#if system.menuDTO.menuId == Session.currentSystemId!-1>
            <#list system.subMenus as firstMenu>
              <#if firstMenu.subMenus?size != 0>
              <#assign frameSupport = ""/>
              <li>
                  <a name="menu_parent" id="firstMenu_${firstMenu.menuDTO.menuId?c}" class="<#if firstMenu.menuDTO.menuId == Session.firstMenuId!-1001>active</#if>">
                    <span class="glyphicon glyphicon-equalizer"></span><span class="content_blank_15"></span>${firstMenu.menuDTO.menuName}
                    <span class="glyphicon glyphicon-chevron-right arr_right"></span>
                  </a>

                    <ul class="menu_active <#if firstMenu.menuDTO.menuId == Session.firstMenuId!-1001> <#else>display_none</#if>">
                      <#list firstMenu.subMenus as endMenu>
                         <#assign connector = "?"/>
                         <#if endMenu.functionUrl?? && endMenu.functionUrl?index_of("?") gt 0 ><#assign connector = "&"/></#if>
                         <li id="endMenu_${endMenu.menuDTO.menuId?c}" ><a href='${endMenu.functionUrl!""}${connector}_menuId=${endMenu.menuDTO.menuId?c}&_firstMenuId=${firstMenu.menuDTO.menuId?c}' <#if endMenu.menuDTO.menuId==Session.currentMenuId!-10001>class="selected"</#if>>${endMenu.menuDTO.menuName}</a></li>
 
 
                      </#list>
                    </ul>
              </li>
              </#if>
            </#list>
          </#if>
        </#list>
      </ul>
    </div>  


  <!-- body -->
    <sitemesh:write property="body"/>
<#else>
  <sitemesh:write property="body"/>
</#if>

 <#if Session.SESSION_USERINFO?? && (!Request.purepage?? || Request.purepage != "true")> 
    <script type="text/javascript" src="${resourcePath}/js/resetCommon.js?version=1.0.1"></script>
    <script type="text/javascript" src="${resourcePath}/bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript" src="${resourcePath}/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
    <script type="text/javascript" src="${resourcePath}/jqueryValidation/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${resourcePath}/jqueryValidation/messages_zh.js"></script>
    <script type="text/javascript" src="${resourcePath}/jqueryValidation/additional-methods.js"></script>


 </#if>
</body>
</html>
</#if>
