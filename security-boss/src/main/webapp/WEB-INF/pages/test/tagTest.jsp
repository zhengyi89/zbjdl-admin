<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>标签测试页</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp" %>
</head>

<body>
<div class="content_wrapper">
      <div class="content_main fontText">
        <!--search star-->
        <eb:authoryIf url="www.baidu.com">
          测试用例1失败
        </eb:authoryIf>
        <eb:authoryElse>
          测试用例1成功
        </eb:authoryElse>

        <br></br>

        <eb:authoryIf url="/menu/">
          测试用例2成功
        </eb:authoryIf>
        <eb:authoryElse>
          测试用例2失败
        </eb:authoryElse>

        <div class="clearer"></div>
      </div>
</div>
<!--Body End-->

</body>
</html>
