<script>
  var _query_page_info = new _easyquerypageinfo();
  <#if queryForm.currentPage??>_query_page_info.page = ${queryForm.currentPage};</#if>
  <#if queryForm.orderby??>
    _query_page_info.orderby = '${queryForm.orderby}';
    <#if queryForm.asc??>_query_page_info.asc = '${queryForm.asc?string}';</#if>
  </#if>
  _default_easyquery_pageinfos['${queryForm.queryKey}'] = _query_page_info;
</script>
<div class="result">
  <#if tableBean.paging ><h2 class="fw">查询结果<span>（共${queryForm.totalCount}条）</span></h2></#if>
  <table ${tableBean.html!""}>
    <tr ${rowBean.titlehtml!""}><#assign  lastUnion="noUnion"/>
    <#list columnBeans as columnBean>
      <#if (!columnBean.union ?? || lastUnion != columnBean.union) && columnBean_index !=0 ></th></#if>
      <#if !columnBean.union ?? || lastUnion != columnBean.union ><th ${columnBean.titleHtml}></#if>
      <#if columnBean.union ?? ><#assign  lastUnion=columnBean.union/></#if>
      <#if columnBean.sortable>
        <#if !queryForm.asc?? || !queryForm.orderby?? || !columnBean.orderBy?? || queryForm.orderby != columnBean.orderBy>
          <span class="sx_m">
            <a href='javascript:querysort("${tableBean.formId}","${queryForm.queryKey}","${columnBean.orderBy!""}",true)'>
            ${columnBean.title}
            </a>
          </span>
        <#elseif queryForm.asc>
          <span class="sx_t">
            <a href='javascript:querysort("${tableBean.formId}","${queryForm.queryKey}","${columnBean.orderBy!""}",false)'>
            ${columnBean.title}
            </a>
          </span>
        <#else>
          <span class="sx_b">
            <a href='javascript:querysort("${tableBean.formId}","${queryForm.queryKey}","${columnBean.orderBy!""}",true)'>
            ${columnBean.title}
            </a>
          </span>
        </#if>
      <#else>
        <span>${columnBean.title}</span>
      </#if>
    </#list>
    </th>
    </tr>
    </table>
    <table class="table_info">
    <#if queryForm.queryResult??>
    <#list queryForm.queryResult.data as listable>
       ${stack.push(listable)}
     <tr ${stack.buildHtml(rowBean)!""}>
       <#list columnBeans as columnBean>
       <#if (!columnBean.union ?? || lastUnion != columnBean.union) && columnBean_index !=0 ></td></#if>
       <#if !columnBean.union ?? || lastUnion != columnBean.union ><td ${stack.buildHtml(columnBean)!""}></#if>
       <#if columnBean.union ?? ><#assign  lastUnion=columnBean.union/></#if>
          <span><#if columnBean.value??>${stack.findValue(columnBean.value,columnBean.escape)!"--"}<#elseif columnBean.body??>${stack.parseFtlStr(columnBean.body,columnBean.escape)!"--"}<#else>--</#if></span>
        </#list></td>
      </tr>
      ${stack.pop()}
    </#list>
    </#if>
  </table>
  <div class="clearer"></div>
<#if tableBean.paging?? && !tableBean.paging>
</div>
</#if>