$(document).ready(function(){
	var checkboxes = $(":checkbox");
	$.each(checkboxes,function(i,item){
		if(item.id != "selectAll") {
			$(this).bind("click",function(){
				var checkboxList = $(":checkbox");
				var all = true;
				$.each(checkboxList,function(i,item){
					if(item.id != "selectAll") {
						if(!item.checked) {
							all = false;
						}
					} 
				});
				if(all) {
					$("#selectAll").attr("checked",true);
				} else {
					$("#selectAll").attr("checked",false);
				}
			});
		} 
	});
	openClose("userHandler","tree");
});

function selectAllAndNone() {
	var checkboxList = $(":checkbox");
	if($("#selectAll").attr("checked") == "checked") {
		$.each(checkboxList,function(i,item){
			if(item.id != "selectAll") {
				item.checked = true;
			} 
		});
	} else {
		$.each(checkboxList,function(i,item){
			if(item.id != "selectAll") {
				item.checked = false;
			} 
		});
	}
}

function validateInput_User() {
	
	
	$("#loginNameMsg").empty();
	$("#userNameMsg").empty();
	$("#passwordMsg").empty();
	
	if(!validateInput()){
		return false;
	}else{
		return true;
	}
	
}

function validateInput_Tree(){
	if(validateInput){
		
		$("#userMessage").empty();
		$("#passwordMsg").empty();
		
		var password = $("#password").val();
		if(password == "" || password == null) {
			$("#passwordMsg").append(
					"<font color='red'>" + 
					"请输入登陆密码！" + 
					"</font>"
				);
			return false;
		}
		
		var functionIds = "";
		var node = zTree.getCheckedNodes(true);
	    for(var i=0;i<node.length;i++){
	    	functionIds += node[i].id + ","; 
	    }
		if(!functionIds) {
			$("#userMessage").append("必须选中一个带有功能的角色");
			return false;
		}
	}
}