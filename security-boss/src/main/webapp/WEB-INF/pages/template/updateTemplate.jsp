<%@ page language="java" contentType="text/html; charset=utf-8"
  pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改模板</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp"%>
<script type="text/javascript">
  $(document).ready(function(){
    var functionId = $("#functionId1").val();
    if(functionId != null && functionId != ""){
      $("#functionId").val(functionId);
      $("#functionId").attr("readonly","readonly");
      $("#content").attr("disabled",true);
      $("#changeBtn").removeAttr("style");
      $("#submitBtn").attr("style","display:none");
    }
    var valList = {
      "functionId" : {
        req : true,
        datatype:"^-?[1-9]\\d*$",
        len: {max:19},
        label : "功能ID"
      },
      "content" : {
        req : true,
        len: {max:1024},
        label : "模板内容"
      }
    };
    ValidateExt.listen("form",valList);

    $("#submitBtn").click(function(){
      if(ValidateExt.val("form")){
        var templateTag = $("#templateTag").val();
        var functionId = $("#functionId1").val();
        if(templateTag == "log"){
          MessageBoxExt.ajax({
            type : "post",
            url : $("#form").attr("action"),
            data : $("#form").serialize(),
            style : "redirect",
            toUrl : GV.ctxPath + "/template/logTemplateMgr.action"
          });
        }else if(templateTag == "workItem"){
          MessageBoxExt.ajax({
            type : "post",
            url : $("#form").attr("action"),
            data : $("#form").serialize(),
            style : "redirect",
            toUrl : GV.ctxPath + "/template/workItemTemplateMgr.action"
          });
        }
      }
    });
  });

  function change(){
    $("#content").removeAttr("disabled");
    $("#submitBtn").removeAttr("style");
    $("#changeBtn").attr("style","display:none");
  }
</script>
</head>
<body>
  <div class="content_wrapper">
    <div class="content_main fontText">
      <div class="result">
        <div class="information">
          <h1 class="fw">修改模板</h1>
          <div class="input_cont">
            <form action="${ctxPath}/template/updateTemplate.action" id="form" method="post">
              <ul>
                <li>
                  <label class="text_tit">功能ID：</label>
                  <input type="hidden" name="templateTag" id="templateTag" value="<s:property value="templateTag"/>"/>
                  <input type="hidden" name="templateId" id="templateId" value="<s:property value="templateId"/>"/>
                  <input type="hidden" name="functionId1" id="functionId1" value="<s:property value="functionId"/>"/>
                    <s:if test="templateTag == 'log'">
                      <input type="text" class="input_text" name="functionId" id="functionId" value="<s:property value="operationLogTemplateDTO.functionId"/>">
                    </s:if>
                    <s:if test="templateTag == 'workItem'">
                      <input type="text" class="input_text" name="functionId" id="functionId" value="<s:property value="workItemTemplateDTO.functionId"/>">
                    </s:if>
                </li>
                <li>
                  <label class="text_tit">模板内容：</label>
                  <s:if test="templateTag == 'log'">
                    <textarea class="textfield" name="content" id="content" ><s:property value="operationLogTemplateDTO.content"/></textarea>
                  </s:if>
                  <s:if test="templateTag == 'workItem'">
                    <textarea class="textfield" name="content" id="content" ><s:property value="workItemTemplateDTO.content"/></textarea>
                  </s:if>
                </li>
                <li>
                  <label class="text_tit">&nbsp;</label>
                  <input type="button" id="changeBtn" class="btn_sure fw" value="修改" onclick="change();" style="display:none">
                  <input type="button" id="submitBtn" class="btn_sure fw" value="确定">
                  <input type="button" onclick="self.history.back(-1);" class="btn_cancel fw" value="取消">
                </li>
              </ul>
            </form>
            <div class="clearer"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
