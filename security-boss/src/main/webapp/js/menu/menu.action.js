
function popupPasswordDialog(menuId,action,actionUrl) {
  $("#menuId").val(menuId);
  initPasswordDialog();
  openPasswordDialog(action,actionUrl);

}

$(document).ready(function(){
  var valList = {
    "functionId" : {
      datatype:"^-?[1-9]\\d*$",
      len: {max:19},
      label : "功能ID"
    }
  };
  ValidateExt.listen("form",valList,ValidateExt.tipError);

  $("#submitBtn").click(function(){
    if(ValidateExt.val("form")){
      $("#form").submit();
    }
  });
});
