<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/pages/main/metas.jsp"%>
<title>权限分配</title>
<link rel="stylesheet" href="${ctxPath}/css/common/zTreeStyle.css" type="text/css" />
<link href="${ctxPath}/css/common/jquery.qtip.min.css" rel="stylesheet" />
<style type="text/css">
.list_info2 {
  background: none repeat scroll 0 0 #FFFFFF;
  border: 1px solid #EDEDED;
  text-align: left;
  width: 100%;
}

#follow {
  cursor: pointer;
  height: 400px;
  position: absolute;
  right: 230px;
  top: 120px;
  width: 300px;
}
</style>
</head>
<body>
  <div class="content_wrapper">
    <div class="content_main fontText">
      <form action="distributeAuthority" method="post" id="form">
        <div class="information">
      <div class="setup">
            <h2 class="fw fleft f14">功能树</h2>
            <p>
              <a href="javascript:void(0);" id="treeHandler" class="up">点击收起</a>
            </p>
       </div>
      <div class="serContent" id="tree">
        <div id="treeDemo" class="ztree"></div>
        <div id="follow" class="follow" >
          <div class="content">
            <div class="result">
              <h3 class="fw">快速查看</h3>
              <div class="information">
                <h2 class="fw">选择角色</h2>
                <div class="clearer"></div>
                <div id="quickDiv" class="quickDiv" style="OVERFLOW-Y:auto;">
                <table class="list_info2">
                  <c:forEach var="role" items="${roleDTOList}">
                    <tr>
                      <td class="easyquery_th">
                      &nbsp;&nbsp;
                      <c:if test="${role.roleStatus == 'ACTIVE' }">
                      <input type="checkbox"
                        name="roleBox" value="${role.roleId}" id="${role.roleId}"
                        onclick="toQuickChoise(this);" />
                      </c:if>
                      <c:if test="${role.roleStatus == 'FROZEN' }">
                      <input type="checkbox"
                        name="roleBox" value="${role.roleId}" id="${role.roleId}"
                        onclick="toQuickChoise(this);" disabled="disabled" />
                      </c:if>
                         &nbsp;&nbsp; <a href="javascript:void(0);" roleId="${role.roleId}" name="tips">${role.roleName}</a> <br/>
                          <br/>
                      </td>
                    </tr>
                  </c:forEach>
                </table>
                </div>
              </div>
            </div>

          </div>
        </div>
              </div>
      </div>
      <input type="hidden" name="userId" value="${userId}" id="userId" /> <input
        type="hidden" id="hasRole" value="${hasRole}" /> <input
        type="hidden" id="selectNodes" name="selectNodes" />
      <div class="clearer"></div>

      <br/>
      <br/>

      <div class="content">
        <div class="result">
          <h3 class="fw">人员管理员信息</h3>
          <div class="information">
            <h1 class="fw">人员管理员验证</h1>
            <div class="clearer"></div>
              <div class="input_cont">
                <ul>
                  <li>
                    <label class="text_tit">管理员登录密码：</label>
                    <input type="password" class="input_text" name="password" id="password"/>
                    <div id="passwordMsg"></div>
                  </li>
                 </ul>
              </div>
            <font color="red"><span id="userMessage"></span></font>
          </div>
        </div>
        <div class="clearer"></div>
        <div class="btn">
          <input type="button" id="submitButton" class="btn_sure" value="确定" />
        </div>
      </div>
      </form>
    </div>
  </div>
  <!--Body End-->
  <div class="hideBav">
    <div class="hideBavBut"></div>
  </div>
  <script type="text/javascript" src="${ctxPath}/js/jquery.ztree.all-3.1.js"></script>
  <script type="text/javascript" src="${ctxPath}/js/jquery.qtip.min.js"></script>
  <script type="text/javascript" src="${ctxPath}/js/qTip/qTip.zTree.js"></script>
  <script type="text/javascript">
	  var zNodes = ${jsonFunctionUnit};
	  function showOrHide() {
		  showdiv('follow');
		  showdiv('tree');
	  }

	  function validateInput_Tree() {
		  if (validateInput) {

			  $("#userMessage").empty();
			  $("#passwordMsg").empty();

			  var password = $("#password").val();
			  if (password == "" || password == null) {
				  $("#passwordMsg").append(
						  "<font color='red'>" +
								  "请输入登陆密码！" +
								  "</font>"
				  );
				  return false;
			  }

			  var selectNodes = "";
			  var roles = $("input[name='roleBox']");
			  for(var i = 0;i<roles.length;i++){
				  if(roles[i].checked){
					  selectNodes +=   roles[i].value + ","  ;
				  }
			  }
			  $("#selectNodes").attr("value",selectNodes);
			  return true;
		  } else {
			  return false
		  }
	  }

	  $(document).ready(function () {
		  $("#submitButton").click(function () {
			  if (validateInput_Tree()) {
				  MessageBoxExt.ajax({
					  url: "user/distributeAuthority",
					  data: $("#form").serialize(),
					  style: "redirect",
					  toUrl: GV.ctxPath + "/user/operatorMgr.action"
				  });
			  }
		  });
		  openClose("treeHandler", "tree");
	  });
  </script>
</body>
</html>