var PortalMgr = {
  popEdit : function (title) {
    MessageBoxExt.popup("editMenuDiv", {
      width : 600,
      title : title,
      height : 450,
      buttons : [{
        text : '保存',
        click : function () {
          PortalMgr.saveMenu($(this));
        }
      }, {
        text : '关闭',
        click : function () {
          $(this).dialog("close");
        }
      }]
    });
  },

  importMenu : function () {
    MessageBoxExt.popup("importMenuDiv", {
      width : 700,
      title : "导入菜单",
      height : 500,
      buttons : [{
        text : '保存',
        click : function () {
          if (!$("#menuDetail").val()) {
            MessageBoxExt.alert("请输入导入菜单内容!");
            return;
          }
          if ($("#menuDTO_departmentId").length > 0 && !$("#menuDTO_departmentId").val()) {
            MessageBoxExt.alert("请选择所属部门!");
            return;
          }

          MessageBoxExt.ajax({
            style : "REDIRECT",
            url : GV.ctxPath + "/menu/importPortalMenu.action",
            type : "POST",
            data : $("#importMenuFrom").serialize()
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
  },

  /**
   * 保存菜单
   */
  saveMenu : function (dialog) {
    if (!ValidateExt.val("editMenuForm")) {
      return;
    }
    dialog.dialog("close");
    var url;
    var menuId = $("input[name='menuDTO.menuId']").val();
    if (menuId) {
      url = GV.ctxPath + "/menu/editPortalMenu.action";
    } else {
      url = GV.ctxPath + "/menu/addPortalMenu.action";
    }
    MessageBoxExt.confirm("是否确认保存菜单?", function () {
      MessageBoxExt.ajax({
        style : "REDIRECT",
        url : url,
        type : "POST",
        data : $("#editMenuForm").serialize()
      });
    });
  },

  /**
   * 删除菜单
   */
  deleteMenu : function (menuIds) {
    if (!menuIds) {
      MessageBoxExt.alert("请选择菜单!");
      return;
    }

    MessageBoxExt.confirm("是否确认删除?", function () {
      MessageBoxExt.ajax({
        style : "REDIRECT",
        url : GV.ctxPath + "/menu/delPortalMenu.action?menuIds=" + menuIds,
        type : "GET"
      });
    });
  },

  /**
   * 删除菜单
   */
  batchDeleteMenu : function () {
    if ($("td > :checkbox:checked").length < 1) {
      MessageBoxExt.alert("请选择菜单!");
      return;
    }

    MessageBoxExt.confirm("是否确认删除?", function () {
      MessageBoxExt.ajax({
        style : "REDIRECT",
        url : GV.ctxPath + "/menu/delPortalMenu.action",
        data : $("td > :checkbox:checked").serialize(),
        type : "POST"
      });
    });
  },

  /**
   * 移动菜单
   */
  batchMoveMenu : function () {
    if ($("td > :checkbox:checked").length < 1) {
      MessageBoxExt.alert("请选择菜单!");
      return;
    }
    var tip = "是否确认移动?";
    if ($("#isSuperAdmin").val() == "true") {
      tip = "此操作可能改变所属部门," + tip;
    }
    MessageBoxExt.popup("moveMenuDiv", {
      width : 600,
      title : "移动菜单",
      height : 420,
      buttons : [{
        text : '保存',
        click : function () {
          MessageBoxExt.confirm(tip, function () {
            MessageBoxExt.ajax({
              style : "REDIRECT",
              url : GV.ctxPath + "/menu/movePortalMenu.action",
              data : $("td > :checkbox:checked").serialize()+"&parentId="+$("#parentName").val(),
              type : "POST"
            });
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
};

$(document).ready(function () {
  var valList = {
    "menuDTO.functionUrl" : {
      len: {max:256},
      label : "菜单URL"
    },
    "menuDTO.menuName" : {
      req : true,
      datatype:"^[a-zA-Z0-9\u4e00-\u9fa5._-]+$",
      len: {max:64},
      label : "菜单名称"
    },
    "menuDTO.sequence" : {
      datatype:"^[1-9]\\d*$",
      label : "同级顺序"
    }
  };
  ValidateExt.listen("editMenuForm",valList);

  $("div.result").append($("#hidden_operat").html());
  $("#hidden_operat").remove();

  $("a[name=addSiblingMenu]").click(function(){
	  var tds = $(this).parents("tr").find("td");
    $("#editMenuForm input").val("");
    $("#menuDTO_parentId").val($(this).attr("parentId"));
    $("#menuDTO_levelNum").val($(this).attr("levelNum"));
    PortalMgr.popEdit("添加同级菜单");
	  if($("#edit_departmentId")){
		  $("#edit_departmentId").val($(tds[3]).text()).trigger("liszt:updated");
		  if($("#edit_departmentId").val() != $(tds[3]).text())
			  MessageBoxExt.alert("原部门已不存在，请重新指定！");
	  }
	  $("#edit_parentId").val($(tds[6]).text()).trigger("liszt:updated");
	  if($(tds[6]).text() != -1 && $("#edit_parentId").val() != $(tds[6]).text())
		  MessageBoxExt.alert("原父级菜单已不存在，请重新指定！");
  });
  $("a[name=addSubMenu]").click(function(){
	  var tds = $(this).parents("tr").find("td");
    $("#editMenuForm input").val("");
    $("#menuDTO_parentId").val($(this).attr("menuId"));
    $("#menuDTO_levelNum").val(parseInt($(this).attr("levelNum") || "1") + 1);
    PortalMgr.popEdit("添加子级菜单");
	  if($("#edit_departmentId")){
		  $("#edit_departmentId").val($(tds[3]).text()).trigger("liszt:updated");
		  if($("#edit_departmentId").val() != $(tds[3]).text())
			  MessageBoxExt.alert("原部门已不存在，请重新指定！");
	  }
	  $("#edit_parentId").val($(this).attr("menuId")).trigger("liszt:updated");
  });
  $("a[name=editMenu]").click(function(){
    var tds = $(this).parents("tr").find("td");
    $("#editMenuForm input").val("");
    $("#menuDTO_menuId").val($(this).attr("menuId"));
    $("#menuDTO_menuName").val($(tds[2]).text());
	  $("#menuDTO_functionUrl").val($(tds[4]).text());
	  $("#menuDTO_sequence").val($(tds[5]).text());
    $("#menuDTO_parentId").val($(this).attr("parentId"));
	  $("#menuDTO_levelNum").val($(this).attr("levelNum"));
    PortalMgr.popEdit("修改菜单");
	  if($("#edit_departmentId")){
		  $("#edit_departmentId").val($(tds[3]).text()).trigger("liszt:updated");
		  if($("#edit_departmentId").val() != $(tds[3]).text())
			  MessageBoxExt.alert("原部门已不存在，请重新指定！");
	  }
	  $("#edit_parentId option[value=" + $(this).attr("menuId") + "]").attr('disabled','disabled');
	  $("#edit_parentId").val($(tds[6]).text()).trigger("liszt:updated");
	  if($(tds[6]).text() != -1 && $("#edit_parentId").val() != $(tds[6]).text())
		  MessageBoxExt.alert("原父级菜单已不存在，请重新指定！");
  });

  $("#batchDelete").click(PortalMgr.batchDeleteMenu);
  $("#batchMove").click(PortalMgr.batchMoveMenu);

	// 全选
	$("#selectAll").click(function() {
		$('input[type="checkbox"][name="menuIds"]').attr("checked",this.checked);
	});
});
