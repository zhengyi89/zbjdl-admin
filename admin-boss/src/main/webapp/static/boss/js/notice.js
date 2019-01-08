$(document).ready(function () {
  try {
    var url = getEmployeePath();
    if (!url){
      return;
    }
    url += "notice/showNotice.action";
    url += "?url=" + encodeURIComponent(window.location.href);
    $.getScript(url,function(){
		if (notice) {
          showNotice(notice,true);
		}
    });
  } catch (error){
  }
});

function closeNotice (noticeId) {
  if (!noticeId) {
    $("#_noticeShow_").hide(200);
    return;
  } else {
    $("#_noticeContent_").hide(200);
  }
  var url = getEmployeePath();
  if (!url){
    return;
  }
  url += "notice/closeNotice.action";
  url += "?noticeId=" + noticeId + "&url=" + encodeURIComponent(window.location.href);
  $.getScript(url,function(){
    //show next notice
	if (notice) {
      showNotice(notice, false);
	}
  });
}

function showNotice (result,isFirst) {
  if (result && result.content) {
    $("#_noticeShow_ .btnClose").unbind("click");
    $("#_noticeShow_ .btnClose").click(function(){
      closeNotice(null);
    });
    $("#_noticeShow_ .txtClose").unbind("click");
    $("#_noticeShow_ .txtClose").click(function(){
      closeNotice(result.noticeId);
    });
    var html = result.content.replace(/\n/g, "<br/>");
    html = html.replace(/[ \t]+/g, "&nbsp;");
    if (result.upgradeDate){
      html = "[" + result.upgradeDate + "]&nbsp;&nbsp;" + html;
    }
    $("#_noticeContent_").html(html);
    if (isFirst) {
      $("#_noticeShow_").slideDown(500);
    } else {
      $("#_noticeContent_").show(200);
    }
  } else {
    if (!isFirst) {
      $("#_noticeContent_").html('<font color="red">已读完全部升级公告！</font>');
      $("#_noticeContent_").show(200);
      $("#_noticeShow_").fadeTo(1000,0.25,function(){$(this).slideUp(200)});
    }
  }
}

function getEmployeePath() {
    var employeePath = $("#_employeePath_").val();
    if (!employeePath){
      return null;
    }
    if (!(/^.*\/$/.test(employeePath))) {
      employeePath += "/";
    }
    return employeePath;
}