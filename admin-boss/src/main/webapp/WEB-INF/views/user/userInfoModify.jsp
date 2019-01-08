<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息修改</title>
<%@ include file="/common/metas.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#modifyInfoBtn").click(function() {
			var userName = $("#userName").val();
			if(userName == '' || userName == null){
				alert("姓名不能为空");
				return false;
			}
			var email = $("#email").val();
			if(email == '' || email == null){
				alert("邮箱不能为空");
				return false;
			}
			MessageBoxExt.ajax({
				type : "post",
				url : GV.ctxPath + "/user/modifyUserInfo",
				data : $("#modifyInfoForm").serialize(),
				success : function(data) {
					if(data != 'success'){
						alert(data);
						return false;
					}
				}
			});
		});
	});
</script>
</head>
<body>
	<div id="content_2" class="content_wrapper">
		<div class="content_main">
			<div class="panel panel-default">
				<div class="panel-heading">修改密码</div>
				<div class="panel-body">
					<form method="post" id="modifyInfoForm" name="modifyInfoForm"
						action="${pageContext.request.contextPath}/user/modifyUserInfo"
						class="cmxform form-horizontal">
						<div class="form-group">
							<label for="inputPassword3" class="col-lg-2 control-label">姓名：</label>
							<div class="col-lg-3">
								<input class="form-control" name="userName" id="userName"
									title="required" value="${userDto.userName }" />
								<font color="red"><span id="userName"></span></font>
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-lg-2 control-label">邮箱：</label>
							<div class="col-lg-3">
								<input class="form-control" name="email" id="email"
									title="required" value="${userDto.email }" />
								<font color="red"><span id="email"></span></font>
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-lg-2 control-label">手机：</label>
							<div class="col-lg-3">
								<input class="form-control" name="mobile" id="mobile"
									title="required" value="${userDto.mobile }" />
								<font color="red"><span id="mobile"></span></font>
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-lg-2 control-label">定期提示修改密码：</label>
							<div class="col-lg-3">
								<input type="checkbox" name="pwdShowNotice" value="1"
									<c:if test="${userDto.pwdShowNotice==1}">checked</c:if> />
							</div>
						</div>
					</form>
					<div class="form-group">
						<div class="col-lg-12 align_center">
							<input type="button" id="modifyInfoBtn" class="btn_sure fw"
								value="确定" /> <input type="button" class="btn_cancel fw"
								value="取消" onclick="history.go(-1);" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>