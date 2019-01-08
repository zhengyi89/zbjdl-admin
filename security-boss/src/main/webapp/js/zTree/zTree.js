var zTree;

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
				
				zTree.expandNode(treeNode, treeNode.checked,true, true);
				
				if(treeNode.pId && !treeNode.checked){
					var cId= zTree.getNodesByParam("pId", treeNode.pId, null)
					if(cId){
						var close = true;
						for(var i=0;i<cId.length;i++){
							if(cId[i].checked){
								close =false;
							}
						}
						if(close){
							zTree.expandNode(zTree.getNodeByParam("id", treeNode.pId, null), false,true, true);
						}
					}
				}


//				var dependency = treeNode.dependency;
//				var ref  = treeNode.ref;
//				var treeObj = $.fn.zTree.getZTreeObj(treeId);
//				var depenArr = dependency[treeNode.id];
//				var refArr = ref[treeNode.id];
//				if(!!refArr && !treeNode.checked){
//					// 取消选中
//					for(var i=0;i<refArr.length;i++){
//						var node = treeObj.getNodeByParam("id", refArr[i], null);
//						if(node.checked){
//							treeObj.checkNode(node, false, true,true);
//						}							
//					}
//				}else if(!!depenArr && treeNode.checked){
//					// 选中
//					for(var i=0;i<depenArr.length;i++){
//						var node = treeObj.getNodeByParam("id", depenArr[i], null);
//						if(!node.checked){
//							treeObj.checkNode(node, true, true,true);
//						}							
//					}
//				}
				
				return true;
			}
		}
	};

$(document).ready(function(){
	
	
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);

	zTree = $.fn.zTree.getZTreeObj("treeDemo");
	
	for(var i=0;i<zNodes.length;i++){
		if(!zNodes[i].pId && zNodes[i].checked){
			zTree.expandNode(zTree.getNodeByParam("id", zNodes[i].id, null), true,true, true);	
		}
			
	}
	
})


/**
 * 点击收起、展开效果
 * 
 * @param handleId 点击按钮ID
 * @param contentId 收起、展开区域ID
 */
function openClose (handleId, contentId) {
  $("#" + handleId).click(function () {
    var text = $.trim($(this).text());
    if (text.indexOf("点击收起") >= 0) {
      $(this).removeClass("open");
      $(this).addClass("up");
      $(this).html("点击展开");
      $("#" + contentId).hide(500);
    } else {
      $(this).removeClass("up");
      $(this).addClass("open");
      $(this).html("点击收起");
      $("#" + contentId).show(500);
    }
  });
}