$("a[name='menu_parent']").click(function(){
  var parentObject=$(this);
  if(parentObject.hasClass("active")){
	parentObject.attr("class","");
	parentObject.parent().find("ul.menu_active").hide(500); 
    //return false;
  }else{
    $("a[name='menu_parent']").each(function(){
      $(this).attr("class","");
      $(this).find("span.arr_right").removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-right");
      $(this).parent().find("ul.menu_active").slideUp();
    });
    parentObject.attr("class","active");
    parentObject.parent().find("ul.menu_active").slideDown();
    parentObject.find("span.arr_right").removeClass("glyphicon-chevron-right").addClass("glyphicon-chevron-down");
  }
});
(function($){
  $(window).load(function(){
    $("#content_1").mCustomScrollbar({
      scrollButtons:{
        enable:true
      }
    });
    $("#content_1 .mCSB_dragger_bar").css("background","#1fb5ad");
    $("#content_2 .content_main").css("height",$(window).height()-60);
    $("#content_2 .content_main").css("overflow-y","auto");
    $("#content_2 .content_main").css("overflow-x","hidden");
    $(window).resize(function(){
      $("#content_2 .content_main").css("height",$(window).height()-60);
    });
    function scrollReset(){
        var div_obj=$("#content_1"),
            div_height=div_obj.height(),
            draggerContainer_height=div_obj.find(".mCSB_draggerContainer").height(),
            dragger_height=div_obj.find(".mCSB_dragger").height(),
            menu_obj=$("#content_1").find(".menu"),
            menu_height=menu_obj.height(),
            li_index=menu_obj.find("a[class=active]").parent("li").index(),
            a_index=menu_obj.find("a[class=active]").index(),
            container_top,
            dragger_top;
        if(menu_height>=div_height){
        	if((menu_height-(li_index*54+a_index*54)) <= div_height){
        		//container_top=-(menu_height-div_height);
        		setTimeout('$("#content_1").find(".mCSB_container").css("top",-($("#content_1").find(".menu").height()-$("#content_1").height()))',300);
        		//dragger_top=Math.round(draggerContainer_height-dragger_height);
        		setTimeout('$("#content_1").find(".mCSB_dragger").css("top",Math.round($("#content_1").find(".mCSB_draggerContainer").height()-$("#content_1").find(".mCSB_dragger").height()))',300);
            //alert($("#content_1").find(".mCSB_container").css("top"));
        	}else{
            //container_top=-(li_index*54+a_index*54);
            setTimeout('$("#content_1").find(".mCSB_container").css("top",-($("#content_1").find(".menu").find("a[class=active]").parent("li").index()*54 + $("#content_1").find(".menu").find("a[class=active]").index()*54))',300);
            //dragger_top=Math.round(div_height*container_top/menu_height);
            setTimeout('$("#content_1").find(".mCSB_dragger").css("top",Math.round(($("#content_1").height()*$("#content_1").find(".menu").find("ul[class=menu_active]").parent("li").index()*54 + $("#content_1").find(".menu").find("a[class=selected]").index()*54)/$("#content_1").find(".menu").height()))',300);
          }
        }
    }
    scrollReset();
  });
})(jQuery);
