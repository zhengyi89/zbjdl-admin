function validateInput () {
  var inputElements = $(":input");
  var valid = true;
  var validUrl = true;
  var notNull = true;
  var soLong = true;
  var pattern = /^([a-zA-Z0-9\_\-\.\u4e00-\u9fa5])+$/;

  var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
      + "?(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?" // ftp的user@
      + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
      + "|" // 允许IP和DOMAIN（域名）
      + "([0-9a-zA-Z_!~*'()-]+\.)*" // 域名- www.
      + "([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\." // 二级域名
      + "[a-zA-Z]{2,6})" // first level domain- .com or .museum
      + "(:[0-9]{1,4})?" // 端口- :80
      + "((/?)|" + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";

  $(":input").each(
      function () {
        var inputType = this.type;
        if (inputType == "text" || inputType == "password") {
          var title = this.title;
          var inputValue = this.value;
          var messageId = this.id + "Msg";// "_" + title;
          if (title == "required") {
            // $("#" + messageId).remove();
            if (inputValue == null || inputValue == "") {
              $("#" + messageId).empty();
              $("#" + messageId).append(
                  "<font color='red'>" + "不能为空！" + "</font>")
              notNull = false;
            }
          }
          if (inputValue != null || inputValue != "") {
            var len = $(this).attr("len") || '';
            if (len && len != '') {
              if (strlen(inputValue) > len) {
                $("#" + messageId).empty();
                $("#" + messageId).append(
                    "<font color='red'>" + "输入过长！" + "</font>")
                soLong = false;
              }
            } else if (strlen(inputValue) > 32) {

              $("#" + messageId).empty();
              $("#" + messageId).append(
                  "<font color='red'>" + "输入过长！" + "</font>")

              /*
               * $(this).after("<span class='Error' id='" + messageId + "'><font
               * color='red'>输入过长</font></span>");
               */
              soLong = false;
            }
          }
        }
      });

  if (!notNull) {
    return false;
  }

  if (!soLong) {
    return false;
  }

  $.each(inputElements, function (i, item) {
    var inputType = item.type;
    if (inputType == "text" || inputType == "password") {
      var inputValue = item.value;
      var dataType = $(this).attr("dataType") || '';
      if (inputValue != null && inputValue != "") {
        if (dataType == 'url') {
          var re = new RegExp(strRegex);
          validUrl = re.test(inputValue);
        } else if (dataType == 'none') {

        } else if (!pattern.test(inputValue)) {
          valid = false;
        }
      }
    }
  });

  if (!validUrl) {
    var message = "输入url错误";
    alert(message, "警告");
    return false;
  }
  /*
  if (!valid) {
    var message = "输入字符中只允许含有数字、汉字、大小写字母、下划线_、横线-、点.";
    alert(message, "警告");
    return false;
  }
  */
  return true;
}

function isChinese (str) {
  var lst = /[u00-uFF]/;
  return !lst.test(str);
}

function strlen (str) {
  var strlength = 0;
  for ( var i = 0; i < str.length; i++) {
    if (isChinese(str.charAt(i)) == true) {
      strlength = strlength + 2;
    } else {
      strlength = strlength + 1;
    }
  }
  return strlength;
}

function initPasswordDialog () {
  $passwordDialogView = $("#passwordDialogView").dialog({
    autoOpen : false,
    height : 130,
    modal : true,
    width : 380
  });
}

function openPasswordDialog (action, actionUrl) {
  $passwordDialogView.dialog(
      {
        title : "请输入管理员密码",
        height:150,
        width:400,
        buttons : {
          "确认" : function () {
            $("#passwordMsg").empty();
            var password = $("#password").val();
            if (password == "" || password == null) {
              $("#passwordMsg").append(
                  "<font color='red'>" + "请输入管理员密码！" + "</font>");
            } else {
              MessageBoxExt.ajax({
                url : action + "/" + actionUrl + "?password=" + password,
                data : $("#passwordDialogViewForm").serialize(),
                style : "redirect"
              });
              $("#password").attr("value", "");
              /* $("#passwordDialogViewForm").submit(); */
              $(this).dialog("close");
            }
          },
          "关闭" : function () {
            $("#passwordMsg").empty();
            $("#password").attr("value", "");
            $(this).dialog("close");
          }
        },
        close : function (event, ui) {
          $("#passwordMsg").empty();
        },
        open : function (event, ui) {
          $("#passwordMsg").empty();
        },
        resize : function (event, ui) {
        // resizeSubGridView();
        }
      }).dialog("open");
}
