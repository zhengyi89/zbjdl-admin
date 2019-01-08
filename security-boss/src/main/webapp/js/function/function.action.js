$(document).ready(function(){
  var type = $("#type").val();

  var valList = {
      "functionId" : {
        req : true,
        datatype:"^-?[1-9]\\d*$",
        len: {max:19},
        label : "功能ID"
      },
      "keyWord" : {
        len: {max:128},
        label : "关键字"
      },
      "functionName" : {
        req : true,
        label : "功能名称",
        len: {max:64}
      },
      "password" : {
        req : true,
        label : "管理员密码"
      },
      "checkFunctionId" : {
        datatype:"^-?[1-9]\\d*$",
        label : "复核功能ID",
        len: {max:19}
      },
      "preFunctionId" : {
        datatype:"^-?[1-9]\\d*$",
        label : "功能分组ID",
        len: {max:19}
      },
      "functionUrl" : {
        datatype:"^((https|http|ftp|rtsp|mms)?://)"
          + "?(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?" // ftp的user@
          + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
          + "|" // 允许IP和DOMAIN（域名）
          + "([0-9a-zA-Z_!~*'()-]+\.)*" // 域名- www.
          + "([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\." // 二级域名
          + "[a-zA-Z]{2,6})" // first level domain- .com or .museum
          + "(:[0-9]{1,4})?" // 端口- :80
          + "((/?)|" + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$",
        label : "功能URL",
        len: {max:128}
      },
      "description" : {
        label : "功能描述",
        len: {max:128}
      },
      "riskLevel" : {
        label : "风险级别",
        req : true
      }
    };

  if(type == "update"){
    $("#functionId").attr("readonly","readonly");
    var functionType = $.trim($("#functionType").val());
    var checkFunctionId = $.trim($("#checkFunctionId1").val());
    if(functionType == "WORKITEM_COMM_AUDIT" || functionType == "WORKITEM_RISK_AUDIT" || checkFunctionId != ""){
      $("#checkFunctionId").attr("req",true);
      $("#checkNeeded").attr("disabled",true);
    }
  }
  ValidateExt.listen("functionForm",valList);

  $("#updateFunctionButton").click(function () {
    checkFunction('update');
  });
  $("#addFunctionButton").click(function () {
    checkFunction('add');
  });

  $("input[name=roleBox],input[name=deptBox]").change(checkRoleAndDept);
});

function checkRoleAndDept() {
  if ($("input[name=roleBox]:checked").length > 0 && $("input[name=deptBox]:checked").length > 0) {
    ValidateExt.relatedError($("input[name=roleBox]"),"分配给默认角色的功能不能再分配给部门",ValidateExt.inlineError);
    return false;
  } else {
    ValidateExt.relatedError($("input[name=roleBox]"),null,ValidateExt.inlineError);
    ValidateExt.relatedError($("input[name=deptBox]"),null,ValidateExt.inlineError);
  }
  return true;
}

function checkFunction(url) {
  if(checkRoleAndDept() && ValidateExt.val("functionForm")) {
    url = url || '';
    MessageBoxExt.ajax({
      url : "function/"+url,
      data : $("#functionForm").serialize(),
      style : "redirect",
      toUrl : GV.ctxPath + "/function/functionMgr"
    });
  } else {
    return false;
  }
}

function popupPasswordDialog(functionId,action,actionUrl) {
  $(".submitForm").attr("value",functionId);
  initPasswordDialog();
  openPasswordDialog(action,actionUrl);

}
