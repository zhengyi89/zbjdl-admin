<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<s:if test="type == 'add'">
  <title>添加菜单</title>
</s:if>
<s:if test="type == 'update'">
  <title>修改菜单</title>
</s:if>
<%@ include file="/WEB-INF/pages/main/metas.jsp"%>
<script type="text/javascript">
  $(document).ready(function () {
    var valList = {
        "functionId" : {
          req : true,
          datatype:"^-?[1-9]\\d*$",
          len: {max:19},
          label : "功能ID"
        },
        "menuName" : {
          req : true,
          datatype:"^[a-zA-Z0-9\u4e00-\u9fa5._-]+$",
          len: {max:32},
          label : "菜单名称"
        },
        "parentId" : {
          req : true,
          datatype:"^-?[1-9]\\d*$",
          len: {max:19},
          label : "上级菜单ID"
        },
        "password" : {
          req : true,
          label : "管理员密码"
        },
        "levelNum" : {
          datatype:"^[1-9]\\d*$",
          label : "菜单层级"
        },
        "sequence" : {
          datatype:"^[1-9]\\d*$",
          label : "菜单排序"
        }
      };
      ValidateExt.listen("menuForm",valList);

    var menu = $.parseJSON($("#menuAutoCompleteJson").val());
    /*   autoComplete('parentId'); */
    $("#updateMenuButton").click(function () {
      checkMenu('update');
    });
    $("#addMenuButton").click(function () {
      checkMenu('add');
    });
    $("#levelNum").change(function () {
      initParentId(menu);
    });

    initParentId(menu);
    $("#parentId").find("option[value="+$("#hiddenParentId").val()+"]").attr("selected","selected");

    $("#search").click(function () {
      $("#searchFunctionDiv").find("li").find("input[type=text]").val("");
      $("#searchFunctionDiv").find("td").remove();
      $(".result").find("h2").remove();
      MessageBoxExt.popup("searchFunctionDiv", {
        width : 700,
        title : "选择功能",
        height : 400,
        buttons : [{
          text : '选择',
          click : function () {
            $(this).dialog("close");
          }
        }, {
          text : '关闭',
          click : function () {
            $(this).dialog("close");
          }
        }]
      });
    });

  });

  function initParentId (menu) {
    var levelNum = $("#levelNum");
    var parentId = $("#parentId");
    parentId.find("option").remove();
    if (levelNum.val() == 2) {
      var option = document.createElement("option"); // ie6必须先获得dom
      parentId.append(option);
      option.text = "运营系统[-1]"
      option.value = -1;
    } else if (levelNum.val() == 3) {
      for ( var key in menu) {
        var option = document.createElement("option"); // ie6必须先获得dom
        parentId.append(option);
        option.text = menu[key].name + "[" + menu[key].id + "]";
        option.value = menu[key].id;
      }
    }
  }

  function checkMenu (url) {
    var pass = true;
    $("#menuNameMsg").empty();
    $("#functionIdMsg").empty();
    $("#parentIdMsg").empty();
    $("#levelNumMsg").empty();
    $("#iconNameMsg").empty();
    $("#passwordMsg").empty();
    $("#sequenceMsg").empty();


  if (ValidateExt.val("menuForm")) {
      var menuName = $("#menuName").val();
      var menuId = $("#menuId").val();
      menuId = menuId || '';
      $.ajax({
        url : GV.ctxPath + "/menu/validateMenuNameUnique",
        data : {"menuName":menuName,"menuId":menuId},
        cache:false,
        type : 'POST',
        success : function (data) {
          if(data.errMsg){
            $("#menuNameMsg").append("<font color='red'>"+ data.errMsg + "</font>")
          }else{
          url = url || '';
          MessageBoxExt.ajax({
            url : GV.ctxPath + "/menu/" + url,
            data : $("#menuForm").serialize(),
            style : "redirect",
            toUrl : GV.ctxPath + "/menu/menuSetting.action"
          });
          }
        }
      });
    }
  }
</script>
</head>
<body>
<div class="content_wrapper">
  <div class="content_main fontText">
    <form id="menuForm" action="update" method="post">
      <input type="hidden" name="menuId" id="menuId" value="<s:property value='menuVO.menuId'/>"/> 
      <input type="hidden" id="menuAutoCompleteJson" value="<s:property value='menuAutoCompleteJson'/>"/>
      <div class="information">
        <h1 class="fw">基本信息</h1>
        <div class="input_cont">
          <ul>
            <li>
              <label class="text_tit">菜单名称：</label>
              <input class="input_text" type="text" name="menuName" id="menuName" value="<s:property value='menuVO.menuName' />" len="32" />
              <span id="menuNameMsg"></span>
            </li>
            <li>
              <label class="text_tit">功能ID：</label>
              <input class="input_text" type="text" name="functionId" id="functionId" readonly="readonly" value="<s:property value='menuVO.functionId' />"/>
              <span id="functionIdMsg"></span>
              &nbsp;&nbsp;<a href="javaScript:void(0)" id="search">选择</a>
            </li>
            <li>
              <label class="text_tit">菜单排序：</label>
              <input class="input_text" type="text" name="sequence" id="sequence" value="<s:property value='menuVO.sequence' />" />
              <span id="sequenceMsg"></span>
            </li>

            <li>
              <label class="text_tit">菜单层级：</label>
              <div class="select_border">
                <div class="container">
                  <select name="levelNum" id="levelNum" class="select">
                    <option value="2" <s:if test="menuVO.levelNum == 2">selected</s:if>>1级</option>
                    <option value="3" <s:if test="menuVO.levelNum == 3">selected</s:if>>2级</option>
                  </select>
                </div>
              </div>
            </li>

            <li>
              <label class="text_tit">上级菜单：</label>
              <div class="select_border">
                <div class="container">
                  <input type="hidden" id="hiddenParentId" value="<s:property value='menuVO.parentId' />"/>
                  <select name="parentId" id="parentId" class="select">
                  </select>
                </div>
              </div> <span id="parentIdMsg"></span>
            </li>

            <li>
              <label class="text_tit">管理员登录密码：</label>
              <input class="input_text" type="password" name="password"
              id="password" title="required" /> <span id="passwordMsg"></span>
            </li>
            <li>
              <label class="text_tit">&nbsp;</label>
              <s:if test="type == 'update'">
                <input type="button" id="updateMenuButton" class="btn_sure fw" value="更新" />
              </s:if>
              <s:if test="type == 'add'">
                <input type="button" id="addMenuButton" class="btn_sure fw" value="新增" />
              </s:if>
              <input type="button" class="btn_cancel fw" value="返回" onclick="self.history.back(-1);" />
            </li>
          </ul>
        </div>
      </div>
    </form>
  </div>
  <div id="searchFunctionDiv" style="display: none;">
  <%@ include file="./control/searchFunction.jsp"%>
  </div>
</div>
</body>
