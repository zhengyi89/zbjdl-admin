<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>操作日志查询</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp" %>
<style type="text/css">
.serContent .serTex{ width:85px; text-align:right; line-height:25px;}
#easyTooltip{
  padding:5px 10px;
  border:1px solid #5e5e5e;
  background:#5e5e5e;
  color:#fff;
  width:250px;
}
#logDetail .info_tit {
  width:60px;
}
</style>
<script type="text/javascript">
function viewLogDetail(id) {
  MessageBoxExt.load("logDetail",
      GV.ctxPath + "/log/logDetail.action?id=" + id,
      {width:900,title:"日志详细信息",buttons:[{
        "text" : "关闭",
        "click" : function () {
          $(this).dialog("close");
        }
      }]});
}
</script>
</head>

<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <div class="search">
      <form action="queryOperationLog" id="form" method="get">
         <div class="search_tit">
           <h2 class="fw fleft f14">日志查询</h2>
         </div>

         <div class="search_con">
         <ul class="fix">
           <s:if test="#session['SESSION_USERINFO'].isSuperAdmin || #session['SESSION_USERINFO'].isAdmin || LoginNames == null ">
           <li>
             <label class="text_tit">操作员：</label>
             <s:textfield cssClass="input_text" name="loginName" value="%{#parameters.loginName}"></s:textfield>
           </li>
           </s:if>
           <li class="between">
             <label class="text_tit">时间区间：</label>
             <s:textfield readonly="true" cssClass="input_text" name="startTime" value="%{#parameters.startTime}"></s:textfield>
             <span>至</span>
             <s:textfield readonly="true" cssClass="input_text" name="endTime" value="%{#parameters.endTime}"></s:textfield>
           </li>
           <li>
             <label class="text_tit">功能ID：</label>
             <s:textfield cssClass="input_text" name="functionId" value="%{#parameters.functionId}"></s:textfield>
           </li>
           <li>
             <label class="text_tit">功能名：</label>
             <s:textfield cssClass="input_text" name="functionName" value="%{#parameters.functionName}"></s:textfield>
           </li>
           <li>
             <label class="text_tit">关键字：</label>
             <s:textfield cssClass="input_text" name="keyword" value="%{#parameters.keyword}"></s:textfield>
           </li>
           <li>
             <label class="text_tit">日志内容：</label>
             <s:textfield cssClass="input_text" name="operateContent" value="%{#parameters.operateContent}"></s:textfield>
           </li>
        </ul>
        <br/>
          <div class="btn">
            <input type="submit" class="btn_sure fw" value="查询"/>
            <input type="button" class="btn_cancel fw" value="清空" onclick="clearAll();"/>
          </div>
          <div class="clearer"></div>
        </div>
      </form>
    </div>
    <!--search End-->
    <div class="clearer"></div>
    <!--List stard-->
    <eq:listable queryKey="queryOperationLog" queryService="logQueryService" html="class='list table table-striped table-bordered'" formId="form">
      <eq:param name="LoginNames" value="LoginNames" />
      <eq:col title="操作员ID" value="operator_Id"></eq:col>
      <eq:col title="操作员" value="operator_Login_Name"></eq:col>
      <eq:col title="功能ID" value="function_Id"></eq:col>
      <eq:col title="功能名" value="function_Name" width="100px"></eq:col>
      <eq:col title="日志内容" value="operate_Content" width="500px"></eq:col>
      <eq:col title="日志时间" value="operate_End_Time"></eq:col>
      <eq:col title="操作结果" value="operate_Result"></eq:col>
      <eq:col title="操作时长(ms)" value="during_Time"></eq:col>
      <eq:col title="关键字" value="keyword"></eq:col>
      <eq:col title="操作" escape="false">
        <a href="javascript:void(0);" onclick="viewLogDetail('${ID!""}')"/>详情</a>
      </eq:col>
    </eq:listable>
    <div class="clearer"></div>
  </div>
</div>

<script type="text/javascript">
(function($) {

  $.fn.easyTooltip = function(options){

    // default configuration properties
    var defaults = {
      xOffset: 10,
      yOffset: 25,
      tooltipId: "easyTooltip",
      clickRemove: false,
      content: "",
      useElement: ""
    };

    var options = $.extend(defaults, options);
    var content;

    this.each(function() {
      var title = $(this).attr("title");
      $(this).hover(function(e){
        content = (options.content != "") ? options.content : title;
        content = (options.useElement != "") ? $("#" + options.useElement).html() : content;
        $(this).attr("title","");
        if (content != "" && content != undefined){
          $("body").append("<div id='"+ options.tooltipId +"'>"+ content +"</div>");
          $("#" + options.tooltipId)
            .css("position","absolute")
            .css("top",(e.pageY - options.yOffset) + "px")
            .css("left",(e.pageX + options.xOffset) + "px")
            .css("display","none")
            .fadeIn("fast")
        }
      },
      function(){
        $("#" + options.tooltipId).remove();
        $(this).attr("title",title);
      });
      $(this).mousemove(function(e){
        $("#" + options.tooltipId)
          .css("top",(e.pageY - options.yOffset) + "px")
          .css("left",(e.pageX + options.xOffset) + "px")
      });
      if(options.clickRemove){
        $(this).mousedown(function(e){
          $("#" + options.tooltipId).remove();
          $(this).attr("title",title);
        });
      }
    });

  };

  DatePickerExt.between("startTime", "endTime");

  $("form input[type='button']").click(function(){
      $("#form input[type='text']").val("")
  });
  $("#form").submit(function(){
    var functionId = $.trim($("#functionId").val());
    if(functionId.length>0 && (!/^(-)?\d+$/.test(functionId))){
      $("#functionId").val("");
      MessageBox.alert("功能ID只能是数字!");
      return false;
    }else{
      $("#functionId").val(functionId);
    }
    return true;
  });
  $("a[name='argsLink']").each(function(){
    $(this).easyTooltip({
      content: $(this).parent().find("div").html()
    });
  });
  $("a[name='functionLink']").each(function(){
    $(this).easyTooltip({
      content: $(this).parent().find("div").html()
    });
  });

})(jQuery);

</script>
</body>
</html>
