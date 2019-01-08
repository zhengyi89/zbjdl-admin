<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/main/taglibs.jsp"%>
<script type="text/javascript">
  $(document).ready(
      function () {
        
        var valList = {
            "functionId" : {
              req : true,
              datatype:"^-?[1-9]\\d*$",
              len: {max:19},
              label : "功能ID"
            }
          };
          ValidateExt.listen("searchForm",valList);
        
        $("#searchButton").unbind("click");
        $("#searchButton").click(
            function () {
              if(ValidateExt.val("searchForm")) {
                $.ajax({
                  url : GV.ctxPath +'/menu/searchUserFunction.action',
                  type : 'POST',
                  data: {"functionName" : $("#functionSearchName").val()},
                  success : function (result) {
                    var target = $("#searchFunctionDiv");
                    if (target.length < 1) {
                      target = $(document.createElement("div"));
                      target.attr("id", "searchFunctionDiv");
                      target.html(result);
                      $(document).append(target)
                    } else {
                      target.html(result);
                    }
                    MessageBox.popup("searchFunctionDiv", {
                      width : 700,
                      title : "查看用户拥有的功能信息",
                      height : 400,
                      buttons : [{
                        text : '新增',
                        click : function () {
                          $("#functionId").val($("#hiddenSearch").val());
                          $("#functionId").blur();
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
                });  
              }
            });

        $(".searchCheckBox").click(
            function () {
              var checked = $(this);
              $(".searchCheckBox").each(function () {
                $(this).attr("checked", false);
              });
              checked.attr("checked", true);

              $("#hiddenSearch").val(
                  checked.parents("tr").find("td").eq(1).html());
            });

      });
</script>
<div class="content_wrapper">
  <div class="content_main fontText">
    <!--search star-->
    <div class="information">
      <form id="searchForm" method="get" action="">
        <div class="search_tit">
          <h2 class="fw fleft f14">功能搜索</h2>
        </div>
        <div class="clearer"></div>
        <div class="input_cont">
          <ul>
            <li><label class="text_tit">功能名称：</label> <s:textfield
                name="functionName" id="functionSearchName"
                value="%{#parameters.functionName}" cssClass="input_text"></s:textfield>
              <span id="functionSearchNameMsg"></span></li>
            <li><label class="text_tit">&nbsp;</label> <input
              type="button" class="btn_sure fw" id="searchButton"
              value="查询" /></li>
          </ul>
        </div>
        <input id="hiddenSearch" type="hidden" value="" />
      </form>
      <eq:listable queryKey="queryFunction" formId="~"
        queryService="queryService" html="class='list'" paging="false"
        pageSize="500">
        <eq:param name="userId" value="userId" /> 
        <eq:param name="departmentId" value="departmentId" /> 
        <eq:param name="functionName" value="functionName" />
        <eq:col escape="false" title="选择">
            <input type="checkbox" class="searchCheckBox"/>
  </eq:col>

        <eq:col title="功能Id" value="functionId"></eq:col>
        <eq:col title="功能名称" value="functionName"></eq:col>
        <%-- <eq:col title="功能Url" value="functionUrl"></eq:col> --%>
        <eq:col title="状态">
        <#if 'ACTIVE' == functionStatus>
          可用
        </#if>
        <#if 'FROZEN' == functionStatus>
          冻结
        </#if>
        <#if 'FORBIDDEN' == functionStatus>
          废弃
        </#if>
      </eq:col>
        <eq:col title="描述" value="description"></eq:col>

      </eq:listable>
    </div>
  </div>
</div>