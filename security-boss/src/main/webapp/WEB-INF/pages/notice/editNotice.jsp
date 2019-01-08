<%@ page language="java" contentType="text/html; charset=utf-8"
  pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>编辑公告</title>
<%@ include file="/WEB-INF/pages/main/metas.jsp"%>
<script type="text/javascript">
  $(document).ready(function(){
    DatePickerExt.date("upgradeDate");
    var valList = {
      "functionId" : {
        req : true,
        datatype:"^-?[1-9]\\d*$",
        len: {max:19},
        label : "功能ID"
      },
      "content" : {
        req : true,
        len: {max:1000},
        label : "公告内容"
      },
      "upgradeDate" : {
        req : true,
        label : "升级日期"
      },
      "oaOrderNo" : {
        len: {max:50},
        label : "OA单号"
      }
    };
    ValidateExt.listen("editNoticeForm",valList);

    $("#submitBtn").click(function(){
      if(ValidateExt.val("editNoticeForm")){
        MessageBoxExt.ajax({
          type : "post",
          url : $("#editNoticeForm").attr("action"),
          data : $("#editNoticeForm").serialize(),
          style : "redirect",
          toUrl : GV.ctxPath + "/notice/noticeMgr.action"
        });
      }
    });
  });
</script>
</head>
<body>
  <div class="content_wrapper">
    <div class="content_main fontText">
      <div class="result">
        <div class="information">
          <h1 class="fw">编辑公告</h1>
          <div class="input_cont">
            <form action="${ctxPath}/notice/saveNotice.action" id="editNoticeForm" method="post">
              <ul>
                <li>
                  <label class="text_tit">功能ID：</label>
                  <input type="hidden" name="noticeId" id="noticeId" value="<s:property value="noticeId"/>"/>
                  <input type="text" class="input_text" name="functionId" id="functionId" value="<s:property value="functionId"/>">
                </li>
                <li>
                  <label class="text_tit">升级日期：</label>
                  <input type="text" readonly="readonly" class="input_text" name="upgradeDate" id="upgradeDate" value="<s:property value="upgradeDate"/>">
                </li>
                <li>
                  <label class="text_tit">公告内容：</label>
                  <textarea class="textfield" name="content" id="content"><s:property value="content"/></textarea>
                </li>
                <li>
                  <label class="text_tit">OA单号：</label>
                  <input type="text" class="input_text" name="oaOrderNo" id="oaOrderNo" value="<s:property value="oaOrderNo"/>">
                </li>
                <li>
                  <label class="text_tit">&nbsp;</label>
                  <input type="button" id="submitBtn" class="btn_sure fw" value="保存">
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
