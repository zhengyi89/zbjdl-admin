$(document).ready(function () {
  $("#functionGrid").empty();
  $("#roleNameMsg").empty();
  $("#descriptionMsg").empty();
  $("#passwordMsg").empty();
  $("#systemMsg").empty();
  $("#functionMessage").empty();
  var roleId = $("#roleId").val();
  // showFunctionGrid(roleId);
  $("#addButton").bind("click", edit);
  $("#editButton").bind("click", edit);

  openClose("roleHandler", "tree");
})

function showFunctionGrid (roleId) {
  var timestamp = (new Date()).valueOf();
  var gridContent = "<table border='1'>" + "<thead>" + "<tr>"
      + "<td align='center'>&nbsp;&nbsp;选项&nbsp;&nbsp;</td>"
      + "<td align='center'>&nbsp;&nbsp;功能名称&nbsp;&nbsp;</td>"
      + "<td align='center'>&nbsp;&nbsp;功能描述&nbsp;&nbsp;</td>"
      + "<td align='center'>&nbsp;&nbsp;风险级别&nbsp;&nbsp;</td>"
      + "<td align='center'>&nbsp;&nbsp;所属部门&nbsp;&nbsp;</td>"
      + "<td align='center'>&nbsp;&nbsp;菜单路径&nbsp;&nbsp;</td>" + "</tr>"
      + "</thead>" + "<tbody>";
  $.ajax({
        "url" : "queryAllFunctions?timestamp=" + timestamp,
        "type" : "POST",
        "data" : {
          "roleId" : roleId
        },
        "dataType" : "json",
        "complete" : function (jqXHR, status) {
          if (status == "success") {
            var functions = $.parseJSON(jqXHR.responseText).functionVOList;
            if (functions != null) {
              $
                  .each(
                      functions,
                      function (i, item) {
                        var riskLevel = item.riskLevel;
                        var levelName;
                        if (riskLevel == "HIGH") {
                          levelName = "高";
                        } else if (riskLevel == "MIDDLE") {
                          levelName = "中";
                        } else if (riskLevel == "LOW") {
                          levelName = "低";
                        }
                        var functionStatus = item.functionStatus;
                        var checked = item.selected;
                        var functionName = (item.functionName == null || item.functionName == "")
                            ? "" : item.functionName;
                        var description = (item.description == null || item.description == "")
                            ? "" : item.description;
                        var deptName = (item.departmentName == null || item.departmentName == "")
                            ? "" : item.departmentName;
                        var menuPath = (item.menuPath == null || item.menuPath == "")
                            ? "" : item.menuPath;

                        var checkboxContent;
                        if (checked) {
                          checkboxContent = "<td align='center'><input type='checkbox' onclick='checkStatus(this)' checked='true' value='"
                              + item.functionId
                              + "' name='"
                              + functionStatus
                              + "'></td>"
                        } else {
                          checkboxContent = "<td align='center'><input type='checkbox' onclick='checkStatus(this)' value='"
                              + item.functionId
                              + "' name='"
                              + functionStatus
                              + "'></td>"
                        }

                        gridContent = gridContent + "<tr>" + checkboxContent
                            + "<td>&nbsp;&nbsp;" + functionName
                            + "&nbsp;&nbsp;</td>" + "<td>&nbsp;&nbsp;"
                            + description + "&nbsp;&nbsp;</td>"
                            + "<td align='center'>&nbsp;&nbsp;" + levelName
                            + "&nbsp;&nbsp;</td>" + "<td>&nbsp;&nbsp;"
                            + deptName + "&nbsp;&nbsp;</td>"
                            + "<td>&nbsp;&nbsp;" + menuPath
                            + "&nbsp;&nbsp;</td>" + "</tr>";
                      });
              gridContent = gridContent + "</tbody></table>";
              $("#functionGrid").append(gridContent);
            }
          } else {
            $("#systemMsg").append(
                "<font color='red'>" + "系统异常，请稍候重试！" + "</font>");
          }
        }
      });
}

function edit (button) {
  $(".Error").remove();
  $("#roleNameMsg").empty();
  $("#passwordMsg").empty();
  $("#descriptionMsg").empty();
  $("#functionMessage").empty();
  // 处理选中的功能信息
  var functionIds = "";
  var node = zTree.getCheckedNodes(true);
  for ( var i = 0; i < node.length; i++) {
    functionIds += node[i].id + ",";
  }
  if (!functionIds == "") {
    functionIds = functionIds.substring(0, functionIds.length - 1);
  }
  $("#functionIds").attr("value", functionIds);

  // 对角色名和密码做非空验证
  var roleName = $("#roleName").val();
  var password = $("#password").val();
  var pass = true;
  if (roleName == "" || roleName == null) {
    $("#roleNameMsg").append("<font color='red'>" + "请输入角色名称！" + "</font>");
    pass = false;
  }

  if (password == "" || password == null) {
    $("#passwordMsg").append("<font color='red'>" + "请输入登陆密码！" + "</font>");
    pass = false;
  }

  if (pass) {
    $("#descriptionMsg").empty();
    $("#roleNameMsg").empty();
    $("#passwordMsg").empty();
    if (!validateInput()) {
      return false;
    }
    if (button.currentTarget.value == "添加") {
      validateRoleNameUnique(roleName, "add");
    } else if (button.currentTarget.value == "更新") {
      if ($("#oldRoleName").val() == roleName) {
        /*
         * $("#editRoleForm").attr("action","update");
         * $("#editRoleForm").submit();
         */
        MessageBoxExt.ajax({
          url : "role/update",
          data : $("#editRoleForm").serialize(),
          style : "redirect",
          toUrl : GV.ctxPath + "/role/roleMgr.action"
        });
      } else {
        validateRoleNameUnique(roleName, "update");
      }
    }
  } else {
    return false;
  }
}

function checkStatus (checkBox) {
  if (checkBox.checked) {
    if (checkBox.name == "FROZEN") {
      alert("该功能已经冻结，不能进行分配！", "警告");
      checkBox.checked = false;
    }
  }
}

function validateRoleNameUnique (roleName, type) {
  $("#systemMsg").empty();
  var timestamp = (new Date()).valueOf();
  $.ajax({
    "url" : "validateRoleNameUnique?timestamp=" + timestamp,
    "type" : "POST",
    "data" : {
      "roleName" : roleName
    },
    "dataType" : "json",
    "complete" : function (jqXHR, status) {
      if (status == "success") {
        var roleVO = $.parseJSON(jqXHR.responseText);
        var roleNameUnique = roleVO.roleNameUnique;
        if (roleNameUnique) {
          if (type == "add") {
            /*
             * $("#editRoleForm").attr("action","add");
             * $("#editRoleForm").submit();
             */
            MessageBoxExt.ajax({
              url : "role/add",
              data : $("#editRoleForm").serialize(),
              style : "redirect",
              toUrl : GV.ctxPath + "/role/roleMgr.action"
            });
          } else {
            /*
             * $("#editRoleForm").attr("action","update");
             * $("#editRoleForm").submit();
             */
            MessageBoxExt.ajax({
              url : "role/update",
              data : $("#editRoleForm").serialize(),
              style : "redirect",
              toUrl : GV.ctxPath + "/role/roleMgr.action"
            });
          }
        } else {
          $("#roleNameMsg").append(
              "<font color='red'>" + "角色名称重复，请重新输入！" + "</font>");
        }
      } else {
        $("#systemMsg")
            .append("<font color='red'>" + "系统异常，请稍候重试！" + "</font>");
      }
    }
  });
}
