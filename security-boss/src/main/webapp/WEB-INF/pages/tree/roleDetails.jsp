<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="${ctxPath}/js/jquery.ztree.all-3.1.js"></script>
<link rel="stylesheet" href="${ctxPath}/css/common/zTreeStyle.css" type="text/css"/>
<script type="text/javascript">
  var setting = {
    data : {
      key : {
        title : ""
      },
      simpleData : {
        enable : true
      }
    },
    view : {
      showIcon : true
    }

  };
  var treeObj;
  var zNodes = ${jsonFunction};
  //var bodyHeight;
  
   function funA(){ 
  };
  var treeObj;
  var zNodes = ${jsonFunction};
  
  
   function funA(){ 


      $.fn.zTree.init($("#treeDemo"), setting, zNodes);
      
      treeObj = $.fn.zTree.getZTreeObj("treeDemo");
      
      //bodyHeight = $("body").height();
      
      treeObj.expandAll("true");
      
        /* timer = setInterval("CountDown()",1000); */
        
       }
   function funB(){
         var iframe =  parent.$("#ifram_${roleId}");
         
     
     iframe.attr("height",$(window).height())
   }

     $(document).ready(function() {
  
     funA();

     setInterval("funB()",1000); 
     

  });
</script>
</head>

<body bgcolor="#FFFFA3">
    <ul id="treeDemo" class="ztree"></ul>
    <c:if test="${message == 'true'}" >
    <h2 class="fw"><font color="red" >该角色没有功能</font></h2>
    </c:if>
    <c:if test="${message == 'false'}" >
     <ul id="treeDemo" class="ztree"></ul>
    </c:if>
</body>
</html>