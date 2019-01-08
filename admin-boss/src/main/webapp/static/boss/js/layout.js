// input选择器
var inputSelector = "input[type='text'],input[type='password'],input[type='checkbox'],input[type='radio'],select,textarea";

$(document).ready(function () {
  /** ********* 去掉超链接和按钮点击时生成的虚线框 **************** */
  $('a,input[type="button"],input[type="submit"]').bind('focus', function () {
    if (this.blur) {
      this.blur();
    }
  });

  /** ********* 菜单动态效果 **************** */
  $('#mainMenu > dd').bind('mouseover', function () {
    $('#mainMenu > dd').each(function () {
      if (!$(this).hasClass("nav_select")) {
        $(this).addClass("nav_default")
      }
    });
    $('#mainMenu > dd').removeClass("nav_hover");
    $('#mainMenu > dd > div.nav_show').hide();
    var menu = $(this);
    menu.addClass("nav_hover");
    menu.removeClass("nav_default");
    var subMenu = menu.find('div.nav_show').eq(0);
    if(menu.offset().left + 490 > $(window).width()) {
      var offsetLeft = $(window).width() - menu.offset().left - 490;
      subMenu.css("left", offsetLeft+"px");
    }
    subMenu.show();
    subMenu.bind('mouseout', function () {
      if (!menu.hasClass("nav_select")) {
        menu.addClass("nav_default");
      }
      menu.removeClass("nav_hover");
      $(this).hide();
      return false;
    });
    return false;
  });

  $('#mainMenu .nav_show_con li > a').each(function () {
    var text = $.trim($(this).text());
    if (text && text.length > 8) {
      text = text.substring(0, 7) + "...";
      $(this).text(text);
    }
  });

  var menuScroll = function() {
    var menusL1 = $("#mainMenu dd");
    if (menusL1.length < 1 || $("#_scrollNextBtn").length < 1) {
      return;
    }
    var offsetTop = menusL1.first().offset().top + menusL1.first().height() - 5;
    var last = menusL1.find(":visible").last();
    if (last.length > 0 && last.offset().top > offsetTop) {
      var left = $(window).width() - ($("#_scrollNextBtn").width() || 12);
      $("#_scrollNextBtn").css("left","");
      $("#_scrollNextBtn").css("left", left + "px");
      $("#_scrollPrevBtn,#_scrollNextBtn").show();
      $("#_scrollPrevBtn,#_scrollNextBtn").unbind("click");
      if ($.browser.msie && parseFloat($.browser.version) < 9) {
        $("#_scrollPrevBtn").html("&lt;");
        $("#_scrollNextBtn").html("&gt;");
      }
      var _lastShow = 0;
      for (var i = menusL1.length - 1 ; i >= 0 ; i--) {
        _lastShow = i - 1;
        var menu = menusL1.eq(i);
        if (menu.offset().top > offsetTop) {
          menu.hide();
        } else {
          break;
        }
      }
      $("#_scrollPrevBtn").attr("lastShow", _lastShow);
      $("#_scrollPrevBtn").click(function() {
          if (menusL1.first().is(":visible")) {
            return;
          }
          var lastShow = parseInt($("#_scrollPrevBtn").attr("lastShow") || "0");
          $("#mainMenu dd:lt(" + lastShow + "):hidden:last").show();
          while ($("#mainMenu dd:visible:last").offset().top > offsetTop && lastShow > 0) {
            $("#mainMenu dd:visible:last").hide();
            lastShow = lastShow - 1;
            $("#_scrollPrevBtn").attr("lastShow", lastShow);
          }
      });
      $("#_scrollNextBtn").click(function() {
          if (menusL1.last().is(":visible")) {
            return;
          }
          var lastShow = parseInt($("#_scrollPrevBtn").attr("lastShow") || "0");
          $("#mainMenu dd:gt(" + lastShow + "):hidden:first").show();
          $("#_scrollPrevBtn").attr("lastShow", lastShow + 1);
          while ($("#mainMenu dd:visible:last").offset().top > offsetTop) {
            $("#mainMenu dd:visible:first").hide();
          }
      });
      var _curMenuId = $("#_scrollPrevBtn").attr("curMenuId") || "";
      while (_curMenuId && $("#firstMenu_" + _curMenuId).is(":hidden")) {
        $("#_scrollNextBtn").click();
      }
    };
  };
  menuScroll();
  //$(window).resize(menuScroll);

  /** ********* 列表样式 **************** */
  $(".list tr:even").addClass('even');
  $(".list tr").hover(function () {
    $(this).addClass('current')
  }, function () {
    $(this).removeClass('current')
  })
  $(".list tr:contains('异常')").addClass('error');

  /** ********* 列表样式 **************** */
  $(".input_cont li").hover(function () {
    $(this).addClass('lihover')
  }, function () {
    $(this).removeClass('lihover')
  });


/** ********* 双排列表样式 2012-12-5 **************** */
  $(".table_info tr td").hover(function () {
    $(this).addClass('current').siblings().addClass("current");
    }, function () {
    $(this).removeClass('current').siblings().removeClass("current");
    
    });
  
   $(".table_info col:nth-child(2n+1)").css("background","#fff");
   $(".table_info col:nth-child(2n)").css("background","#f8f8f8");
   
   $(".table_info tr td .table_info_con tr td").hover(function () {
    $(this).css("background","#f0f6fb").siblings().css("background","#f0f6fb")
    }, function () {
    $(this).css("background","#f0f6fb").siblings().css("background","#f0f6fb")
    
    });



  /** ********* 输入框 **************** */
  $("input.input_text").hover(function () {
    $(this).addClass('hover');
  }, function () {
    $(this).removeClass('hover');
  });

  $("input.input_text").focus(function () {
    $(this).addClass('hover');
  });

  $("input.input_text").blur(function () {
    $(this).removeClass('hover');
  });

  /** ********* 按钮状态样式 **************** */
  $('.btn_sure').hover(function () {
    $(this).addClass('btn_sure_hover');
  }, function () {
    $(this).removeClass('btn_sure_hover');
  });
  $('.btn_cancel').hover(function () {
    $(this).addClass('btn_cancel_hover');
  }, function () {
    $(this).removeClass('btn_cancel_hover');
  });

  /** ********* 提示信息 **************** */
  $('.tip_guide').hide();
  $(".floating").hover(function (e) {
    e.preventDefault();
    $(".tip_guide").toggle();
  });
  $(".tip_guide").hover(function () {
    return false
  });
  // 以下代码可以实现点击空白处层消失
  $(document).mouseup(function (e) {
    if ($(e.target).parent("floating").length == 0) {
      $(".tip_guide").hide();
    }
  });

  /** ********* 左侧导航，如支付产品列表 **************** */
  $(".sub_menu").height($(".right_content").height() + 10);
  $(".role dd").hover(function () {
    $(this).children("span").show().end().addClass("bg");
    $(".two_hid").show();
  }, function () {
    $(this).children("span").hide().end().removeClass("bg");
    $(".two_hid").hide();
  });
  $(".role :checkbox").click(function () {
    if (this.checked) {
      $(".three_hid").show()
    }
  })

/** ********* 账务自动适应高度 **************** */
  $(".accounts_r").height($(".accounts").height() + 1);

  /** ********* 查询区块收起 *****************/
  $(".btn_icon").toggle(function () {
    $("#list li:gt(3)").slideUp(300);
    $(this).parent().removeClass("btn_click");
    $(this).text("展开");
  }, function () {
    $("#list li:gt(3)").slideDown(300);
    $(this).parent().addClass("btn_click");
    $(this).text("收起");
  });

  /** ********* 返回顶部 **************** */
  $('.goToTop').click(function () {
    $('html,body').animate({
      scrollTop : '0px'
    }, 200);
    return false;
  });

  /** ********* IE6PNG透明 **************** */
  if ($.browser.msie) {
    if ($.browser.version == "6.0") {
      correctPNG();
    }
  }
});

// 清空查询Form
function clearAll () {
  $("div.search_con ul.fix").find(inputSelector).each(function () {
    var type = $(this).attr("type");
    if (type == "checkbox" || type == "radio") {
      $(this).attr("checked","");
    } else {
      $(this).val("");
    }
  });
}

// PNG 图片透明
function correctPNG () {
  for ( var i = 0; i < document.images.length; i++) {
    var img = document.images[i];
    var imgName = img.src.toUpperCase();
    if (imgName.substring(imgName.length - 3, imgName.length) == "PNG") {
      var imgID = (img.id) ? "id='" + img.id + "' " : "";
      var imgClass = (img.className) ? "class='" + img.className + "' " : "";
      var imgTitle = (img.title) ? "title='" + img.title + "' " : "title='"
          + img.alt + "' ";
      var imgStyle = "display:inline-block;" + img.style.cssText;
      if (img.align == "left") {
        imgStyle = "float:left;" + imgStyle;
      }
      if (img.align == "right") {
        imgStyle = "float:right;" + imgStyle;
      }
      if (img.parentElement.href) {
        imgStyle = "cursor:hand;" + imgStyle;
      }
      var strNewHTML = "<span " + imgID + imgClass + imgTitle + " style=\""
          + "width:" + img.width + "px; height:" + img.height + "px;"
          + imgStyle + ";"
          + "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader"
          + "(src=\'" + img.src + "\', sizingMethod='scale');\"></span>";
      img.outerHTML = strNewHTML;
    }
  }
}

/**
 * disable、undisable表单
 *
 * @param formId 表单ID
 * @param disable true/false
 */
function disableForm (formId, disable) {
  $("#" + formId).find(inputSelector).each(function () {
    if (disable) {
      $(this).attr("disabled", "disabled");
      $(this).addClass("gray");
    } else {
      $(this).removeAttr("disabled");
      $(this).removeClass("gray");
    }
  });
}

/**
 * 判断是否已经disabled
 */
function isDisabled ($o) {
  return $o.attr("disabled") ? true : false;
}
/**
 * 全局AJAX错误处理
 */
$(document).ajaxError(function (event, request, settings, error) {
  if (request.status == 404) {
    MessageBox.alert("尊敬的用户，您访问的地址不存在！");
  } else if (request.status == 401) {
    MessageBox.alert("登录超时，请尝试 <a href='"+$("#_employeePath_").val()+"/loginout/showLogin?returnUrl="+window.location.href+"'>【重新登录】</a>！");
  } else if (request.status == 403) {
    MessageBox.alert("对不起，您无权使用该功能！");
  } else if (request.status == 500) {
    MessageBox.alert("系统正忙，请您稍候再试！");
  }
});