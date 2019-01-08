var zTree;
var zTree_Quick


function formValidate(){
	$("#submitButton").attr("disabled","disabled");
	$("#submitButton").val("请稍候...");
}

function activeButton(){
	$("#submitButton").removeAttr("disabled");
	$("#submitButton").val("确定");
}


function addOrSubHeight(){
	if($(".ztree").outerHeight() > $(".quickDiv").outerHeight()){
		$(".quickDiv").height($(".ztree").outerHeight());
	}
	if($(".ztree").outerHeight() < $(".quickDiv").outerHeight()){
		 $(".quickDiv").height($(".ztree").outerHeight());
	 }
}

function showdiv(divname) {               
    //  alert($("#" + divname).css("display")+divname );
      if ($("#" + divname).css("display") == "none") {       //显示div
          $("#" + divname).slideDown(1000);
      }
      else if ($("#" + divname).css("display") == "block") {       //隐藏div
      $("#" + divname).slideUp(1000);
       }
     else  {
         $("#" + divname).slideDown(1000);
         alert("执行出错");
       }
      }	

var setting = {
		data : {
			key : {
				title : ""
			},
			simpleData : {
				enable : true
			}
		},
		view : {
			showIcon : true
		},
		check : {
			enable : true
		},
		callback: {
			onCheck: function (event,treeId, treeNode) {
				zTree.expandNode(treeNode, treeNode.checked,true, true,true);
				return true;
			},
		    onNodeCreated:  function(){
		    	addOrSubHeight();
		    },
		    onCollapse: function(){
		    	addOrSubHeight();
		    },
		    onExpand: function(){
		    	addOrSubHeight();
		    }


		}
	};

/*function holdWidth(divId){
	$("#"+divId).width($(".ztree").outerWidth());
}*/

var settingWithOutCheck = {
		data : {
			key : {
				title : ""
			},
			simpleData : {
				enable : true
			}
		},
		view : {
			showIcon : true
		},
		check : {
			enable : false
		}
		/*,
	    onExpand: function(){
	    	holdWidth();
	    }*/
	};

$(document).ready(function(){

        var hasRole = $("#hasRole").val();
		
		if(hasRole.indexOf(",") > 0){
		var roleCheckBox = hasRole.split(",");
		
		var checkBoxChoise =  $("input[name='roleBox']");
		
		for(var j=0;j<checkBoxChoise.length;j++){
			checkBoxChoise[j].checked = false ;
			for(var i=0;i<roleCheckBox.length;i++){
				if(roleCheckBox && (checkBoxChoise[j].id == roleCheckBox[i])){
					checkBoxChoise[j].checked = true;
				}
			}
			
		}
		}

		$.fn.zTree.init($("#treeDemo"), setting, zNodes);

		zTree = $.fn.zTree.getZTreeObj("treeDemo");
		
		
		for(var i=0;i<zNodes.length;i++){
			if(!zNodes[i].pId && zNodes[i].checked){
				zTree.expandNode(zTree.getNodeByParam("id", zNodes[i].id, null), true,true, true, true);	
			}
				
		}
		
		var el = $("a[name='tips']").each(function(){
			$(this).qtip({  
				prerender: true,
                style: {width: 400},
				content: 
	        {    
	        	text: '<div class="ztree" id="ul_'+$(this).attr('roleId')+'"  ></div>',//'<iframe src="ajaxQurryRole?employee_decoration=false&roleId='+$(this).attr('roleId')+'" id="ifram_'+$(this).attr('roleId')+'" name="frame_con"  frameborder="0" scrolling="no" ></iframe>',
	            title: "角色拥有权限",
	            ajax: {
	    			url: 'ajaxQurryRole?employee_decoration=false&roleId='+$(this).attr('roleId'), // URL to the local file
	    			type: 'GET', // POST or GET
	    			data: {}, // Data to pass along with your request
	    			success: function(data, status) {
	    				
	    	            if(data.roleJsonList == null || data.roleJsonList.length == 0){
	    	            	$("#ul_"+data.roleId).append("该角色没有功能");
	    	            }
	    	            else{
						var nodes = data.roleJsonList;
	    				$.fn.zTree.init($("#ul_"+data.roleId), settingWithOutCheck, nodes);
	    				zTree_Quick = $.fn.zTree.getZTreeObj("ul_"+data.roleId);
	    				zTree_Quick.expandAll("true");
	    				
	    	            }
	    				
						return false; // Prevent default content update
	    			}
	    		}

	        },

	        position: {
	        		my: 'top left',  // Position my top left...
	        		at: 'bottom left', // at the bottom right of...
	        		target: this // my target
	        	}

	      });
			});
		
		//addOrSubHeight();
});   

	
//function funB(){
//	$(".ztree").parent().attr("width",$(".ztree").width());
//	
//}	
	function toQuickChoise(role){
		
		formValidate();
		
		var userId = $("#userId").attr("value");
		
		
		var roles = $("input[name='roleBox']");
		
		var selectNodes = "";
		
		for(var i = 0;i<roles.length;i++){
			if(roles[i].checked){
				selectNodes +=   roles[i].value + ","  ;	
			}
		}
		
		$("#selectNodes").attr("value",selectNodes);
	
	
		$.ajax({
		    url: 'quickChoiseRoleAjax?selectNodes='+selectNodes+'&userId='+userId,
		    type: 'GET',
		    timeout: 10000,
		    error: function(){
		        alert('Error ');
		    },
		    dataType:"json",
		    beforeSend: function()         				// 避免ajax多次提交造成混乱 
            {
		    	var checkBoxChoise =  $("input[name='roleBox']");
		    	
		    	for(var j=0;j<checkBoxChoise.length;j++){
		    		checkBoxChoise[j].disabled = true;
		    	}
		    	
            },
		    success: function(data){
		                       
		        var newNodes = data.ztreeVOList;
		        
		        
		        $.fn.zTree.init($("#treeDemo"), setting, newNodes);

				zTree = $.fn.zTree.getZTreeObj("treeDemo");

				//zTree.expandAll("true");
				for(var i=0;i<newNodes.length;i++){
					if(!newNodes[i].pId  ){
						zTree.expandNode(zTree.getNodeByParam("id", newNodes[i].id, null), newNodes[i].checked,true, true,true);	
					}
						
				}
				
				
				var checkBoxDisable = data.checkBoxDisable;
			
                var checkBoxChoise =  $("input[name='roleBox']"); // 解锁	
                
                checkBoxChoise.removeAttr("disabled");  
                
                for(var i=0;i<checkBoxDisable.length;i++){  //锁住冻结的角色
                	 for(var j=0;j<checkBoxChoise.length;j++){
                		 if(checkBoxDisable[i] == checkBoxChoise[j].id){
                			 checkBoxChoise[j].disabled = true;
                			 break;
                		 }
                	 }
                }
    	
                activeButton();
		    }
		});

	}