function deleteNotice (noticeId) {
  MessageBoxExt.confirm(null, function () {
    MessageBoxExt.ajax({
      type : "post",
      url : GV.ctxPath + "/notice/deleteNotice.action",
      data : {
        "noticeId" : noticeId
      },
      style : "redirect"
    });
  });
}

function addNotice () {
  MessageBoxExt.ajax({
    type : "post",
    url : GV.ctxPath + "/notice/addNotice.action",
    data : $("#noticeForm").serialize(),
    style : "redirect",
    toUrl : GV.ctxPath + "/notice/noticeMgr.action"
  });
}

function updateNotice () {
  MessageBoxExt.ajax({
    type : "post",
    url : GV.ctxPath + "/notice/updateNotice.action",
    data : $("#noticeForm").serialize(),
    style : "redirect",
    toUrl : GV.ctxPath + "/notice/noticeMgr.action"
  });
}
