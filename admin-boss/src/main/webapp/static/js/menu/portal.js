var diffSrcTab = {};
function pushDiffSrcTab(url){
    var durl=/[http|https]:\/\/([^\/]+)\//i;
    var domain = url.match(durl)[1];
    var project = url.split('/')[3];
    if(project != undefined) {
        domain = domain + '/' + project;
    }
    if(diffSrcTab[domain] == undefined) {
        diffSrcTab[domain] = url;
        return true;
    }
    else {
        return false;
    }
}
function recover(key, url){
    url = $(".nav dd ul li a[url='"+url+"']")[0];
    if(undefined != url) {newTab(url);}
    else {
        $.ajax({
            url: GV.ctxPath + "/tab/delete?title="+key
        });
    }
}

$(document).ready(function () {// 导航下拉效果
  $(".nav dd ul li:first-child").css({
    "border-top" : "#EDEDED solid 1px"
  });
  $(".nav dd ul").hide();
  $(".nav dd").hover(function () {// 滑过
    var self = $(this);
    timeId = setTimeout(function () {
      self.addClass("nav_hover");
      self.children("ul").slideDown(50, "easeOutExpo");
    }, 100);
    return false;
  }, function () {// 离开
    clearTimeout(timeId);
    $(this).removeClass("nav_hover");
    $(this).children("ul").slideUp(10, "easeOutCirc");
    return false;
  });

  if (isIE6()) {
    $(".nav dd ul li a").css("display", "block");
  }
  $(".nav dd ul li").hover(function () {
    $(this).children("a").addClass("hover");
    $(this).children("ul").show();
  }, function () {
    $(this).children("a").removeClass("hover");
    $(this).children("ul").hide();
  });

  rewrite();
  $(".nav dd ul li a").click(function () {
    newTab(this);
  });

  $("#nextBtn").click(function () {
    moveRight(138);
  });
  $("#prevBtn").click(function () {
    moveLeft(138);
  });
  $("#closeAllBtn").click(function () {
    $(".subnav > dd").each(function(){
        $(this).close();
    });
      // 从缓存删除
      $.ajax({
          url: GV.ctxPath+"/tab/clean"
      });
  });
//    var IE_LT_9 = false;
    /*@cc_on
     IE_LT_9 = (@_jscript_version <= 9);
     @*/
    var lastOpenedTab = $.parseJSON(lastOpenedTabConfig);
    if(null != lastOpenedTab && ""!=lastOpenedTab){//!IE_LT_9 && !$.browser.mozilla
        for (var key in lastOpenedTab) {
            if(pushDiffSrcTab(lastOpenedTab[key])){
                lastOpenedTab[key] = undefined;
            }
        }
        for (var key in diffSrcTab) {
            recover("1", diffSrcTab[key]);
        }
        setTimeout(function(){
            for (var key in lastOpenedTab) {
                recover(key, lastOpenedTab[key]);
            }
        }, 3000);
    }
});

function isIE6 () {
  if ($.browser.msie) {
    if ($.browser.version == "6.0") {
      return true;
    }
  }
  return false;
}
function moveLeft (step) {
  $(".subnav").animate({
    scrollLeft : $(".subnav").scrollLeft() - step
  }, "fast");
}
function moveRight (step) {
  $(".subnav").animate({
    scrollLeft : $(".subnav").scrollLeft() + step
  }, "fast");
}
jQuery.fn.active = function () {
  if (hisTab) {
    hisTab.removeClass("subnav_current");
    $("#" + hisTab.attr("frameId")).hide();
  }

  $(this).addClass("subnav_current");
  hisTab = $(this);
  $("#" + $(this).attr("frameId")).show();
}
jQuery.fn.close = function () {
  $(this).hide("fast", function () {
    $(this).remove();
    $("#" + $(this).attr("frameId")).remove();

    // 从缓存删除
      $.ajax({
          url: GV.ctxPath+"/tab/delete?title="+$(this).text()
      });
  });
}

var left = 0;
var hisTab, hisFrame;
var id = 0;
function newTab (menu) {
  if (!$(menu).attr("url")) {
    return;
  }
  var title = $(menu).text();
  var exists = $(".subnav dd a");
  for ( var i = 0; i < exists.length; i++) {
    var exist = $(exists[i]);
    if (exist.text() == title) {
      exist.click();
      exist.children().first().children().click();
      return;
    }
  }

  var obj = $(
      '<dd style="display:none"><a href="#">' + title
          + '<i title="关闭"></i></a><em></em></dd>').appendTo('.subnav');

  obj.show("fast", function () {
    if (obj.position().left > $('.subnav').innerWidth()) {
      moveRight(obj.position().left - $('.subnav').innerWidth()
          + obj.outerWidth());
    }
  });
  if (hisFrame) {
    hisFrame.hide();
  }
  
  var top = getTopHeight(menu);
  var height = "100%";
  var url = $(menu).attr("url");
  var isboss3g = url.indexOf("boss3g") > 0 || $(menu).attr("isboss3g");
  if (!isboss3g && top!=0) {
    height = $(document).height()-96+(top>0?top:-2*top) + "px";
  }
  if (top == 0) {
    top = "auto";
  } else {
    top = top + "px";
  }
  var currentFrame = $(
      '<iframe src="'
          + url
          + '" width="100%" height="'+height+'" frameborder="0" scrolling="auto" style="position: absolute; left: 0; top: '
          + top + ';z-index:-1;" sandbox="allow-forms allow-scripts ms-allow-popups allow-same-origin allow-popups" seamless=""></iframe>').appendTo('#frame');
  currentFrame.attr("id", "frame" + id++);
  obj.attr("frameId", currentFrame.attr("id"));

    // 更新到缓存
    $.ajax({
        url: GV.ctxPath+"/tab/active?title="+title+"&url="+url
    });
  obj.active();

  obj.click(function () {
      // 更新到缓存
      $.ajax({
          url: GV.ctxPath+"/tab/active?title="+title+"&url="+url
      });
    $(this).active();
  });
  obj.children().first().children().click(function () {
    var target = $(this).parent().parent();
    if (target.hasClass("subnav_current")) {
      if (target.next().html()) {
        target.next().active();
      } else if (target.prev().html()) {
        target.prev().active();
      }
    }
    target.close();
    return false;
  });

  $("iframe #mainNav").hide();
}

function rewrite () {
  var employeePath = $("#_employeePath").val() || "";
  if (!employeePath || employeePath.indexOf("boss3g") > 0) {
    return;
  }
  $(".nav dd ul li a").each(function () {
    var url = $(this).attr("url") || "";
    if (!url) {
      return;
    }
    if (url.indexOf(employeePath) >= 0) {
      $(this).attr("isboss3g", "true");
    } 
  });
}

function getTopHeight(menu) {
  var url = $(menu).attr("url");
  var top = 0;
  
  // 优先使用统一配置已配项
  if (topHeightConfig) {
    var topHeightJson = $.parseJSON(topHeightConfig);
    for (var key in topHeightJson) {
      if (url.indexOf(key) > 0) {
        var height = topHeightJson[key];
        if (/^-?[0-9]+$/.test(height)) {
          return parseInt(height);
        } else {
          return height;
        }
      }
    }

  }
  
  // 旧版默认定制处理
  if (url.indexOf("boss3g") > 0 || $(menu).attr("isboss3g")) {
    // 3代后台
    top = -6;
  } else if (url.indexOf("/login") > 0) {
    // 各登录页
    top = 98;
  }
  return top
}
