$(document).ready(function(){
  $("#departmentNameMsg").empty();
  $("#departmentCodeMsg").empty();
  $("#systemMsg").empty();
  $("#addButton").bind("click",addDept);
  $("#editButton").bind("click",editDept);

  openClose("departmentHandler","tree");
});

function addDept(){
  $(".Error").remove();
  $("#departmentNameMsg").empty();
  $("#departmentCodeMsg").empty();
  $("#systemMsg").empty();
  var deptName = $("#departmentName").val();
  var deptCode = $("#departmentCode").val();

  var departmentDesc = $("#departmentDesc").attr("value");

/*
 * if(departmentDesc.length > 20){ $("#departmentDescMsg").html("<font
 * color='red'>" + "超过限制!" + "</font>"); return false; }
 */

  var pass = true;
  var functionIds = "";
  var node = zTree.getCheckedNodes(true);
    for(var i=0;i<node.length;i++){
      functionIds += node[i].id + ",";
    }
  if(!functionIds == "") {
    functionIds = functionIds.substring(0,functionIds.length - 1);
  } else {
    MessageBoxExt.alert("必须选中一个功能");
    var pass =  false;
  }
  $("#functionIds").attr("value",functionIds);

  if(deptName == "" || deptName == null) {
    $("#departmentNameMsg").append(
        "<font color='red'>" +
        "请输入部门名称！" +
        "</font>"
      );
    pass = false;
  }

  if(deptCode == "" || deptCode == null) {
    $("#departmentCodeMsg").append(
        "<font color='red'>" +
        "请输入部门编号！" +
        "</font>"
      );
    pass = false;
  }

  if(pass) {
    if(!validateInput()) {
      return false;
    }
    validateDeptNameAndCodeUnique(deptName, deptCode);
  } else {
    return false;
  }
}

function editDept() {
  $(".Error").remove();
  $("#departmentNameMsg").empty();
  $("#departmentCodeMsg").empty();
  $("#systemMsg").empty();
  var deptName = $("#departmentName").val();
  var oldDeptName = $("#oldDepartmentName").val();
  var departmentDesc = $("#departmentDesc").val();
  var oldDepartmentDesc = $("#oldDepartmentDesc").val();


  var departmentDesc = $("#departmentDesc").attr("value");


/*
 * if(departmentDesc.length > 20){ $("#departmentDescMsg").html("<font
 * color='red'>" + "超过限制!" + "</font>"); return false; }
 */

  var pass = true;
  var functionIds = "";
  var node = zTree.getCheckedNodes(true);
    for(var i=0;i<node.length;i++){
      functionIds += node[i].id + ",";
    }
  if(!functionIds == "") {
    functionIds = functionIds.substring(0,functionIds.length - 1);
  } else {
    MessageBoxExt.alert("必须选中一个功能");
    var pass = false;
  }
  $("#functionIds").attr("value",functionIds);

  if(deptName == "" || deptName == null) {
    $("#departmentNameMsg").append(
        "<font color='red'>" +
        "请输入部门名称！" +
        "</font>"
      );
    pass = false;
  }

  if(pass) {
    if(!validateInput()) {
      return false;
    }
    if(deptName == oldDeptName) {
      // 不判断重复信息是否提交
      /*
       * $("#editDeptForm").attr("action","update");
       * $("#editDeptForm").submit();
       */
      MessageBoxExt.ajax({
        url : "depart/update",
        data : $("#editDeptForm").serialize(),
        style : "redirect",
        toUrl : GV.ctxPath + "/depart/departMentMgr.action"
      });

    } else {
      validateDeptNameUnique(deptName);
    }
  } else {
    return false;
  }
}
// 删除部门
function popupPasswordDialog(deptId,action,actionUrl) {
  initPasswordDialog();
  openPasswordDialog(action,actionUrl);
  $("#departmentId").attr("value",deptId);
}
// 迁移部门
function migrateDepartment(deptId) {
	$("#newDepartmentId option[value=" + deptId + "]").attr('disabled','disabled');
	$("#newDepartmentId").trigger("liszt:updated");

	$("#departmentId2").attr("value", deptId);
	MessageBoxExt.popup("migrateDepartmentView", {
		width : 500,
		title : "部门迁移",
		height : 450,
		buttons : [{
			text : '保存',
			click : function () {
				if (!$("#newDepartmentId").val()) {
					MessageBoxExt.alert("请选择拟迁移到的部门!");
					return;
				}
				MessageBoxExt.ajax({
					style : "REDIRECT",
					url : GV.ctxPath + "/depart/migrate.action",
					type : "POST",
					data : $("#migrateDepartmentForm").serialize()
				});
				$(this).dialog("close");
			}
		}, {
			text : '关闭',
			click : function () {
				$(this).dialog("close");
			}
		}]
	});
}

function validateDeptNameUnique(deptName) {
  var timestamp = (new Date()).valueOf();
  $.ajax({
    "url" : GV.ctxPath + "/depart/validateDeptNameUnique?timestamp=" + timestamp,
    "type" : "POST",
    "data" : {"departmentName" : deptName},
    "dataType" : "json",
    "complete" : function(jqXHR,status) {
      if(status == "success") {
        var deptVO = $.parseJSON(jqXHR.responseText);
        var deptNameUnique = deptVO.deptNameUnique;
        if(deptNameUnique) {
          $("#editDeptForm").attr("action","update");
          $("#editDeptForm").submit();
        } else {
          $("#departmentNameMsg").append(
              "<font color='red'>" +
              "部门名称重复，请重新输入！" +
              "</font>"
            );
        }
      } else {
        $("#systemMsg").append(
            "<font color='red'>" +
            "系统异常，请稍候重试！" +
            "</font>"
          );
      }
    }
  });
}

function validateDeptNameAndCodeUnique(deptName,deptCode) {
  var timestamp = (new Date()).valueOf();
  $.ajax({
    "url" : GV.ctxPath + "/depart/validateDeptNameAndCodeUnique?timestamp=" + timestamp,
    "type" : "POST",
    "data" : {"departmentName" : deptName,"departmentCode" : deptCode},
    "dataType" : "json",
    "complete" : function(jqXHR,status) {
      if(status == "success") {
        var deptVO = $.parseJSON(jqXHR.responseText);
        var deptNameUnique = deptVO.deptNameUnique;
        var deptCodeUnique = deptVO.deptCodeUnique;
        if(deptNameUnique && deptCodeUnique) {
// $("#editDeptForm").attr("action","add");
// $("#editDeptForm").submit();
          MessageBoxExt.ajax({
            url : "depart/add",
            data : $("#editDeptForm").serialize(),
            style : "redirect",
            toUrl : GV.ctxPath + "/depart/departMentMgr.action"
          });
        } else {
          if(!deptNameUnique) {
            $("#departmentNameMsg").append(
                "<font color='red'>" +
                "部门名称重复，请重新输入！" +
                "</font>"
              );
          }
          if(!deptCodeUnique) {
            $("#departmentCodeMsg").append(
                "<font color='red'>" +
                "部门编码重复，请重新输入！" +
                "</font>"
              );
          }
        }
      } else {
        $("#systemMsg").append(
            "<font color='red'>" +
            "系统异常，请稍候重试！" +
            "</font>"
          );
      }
    }
  });
}
