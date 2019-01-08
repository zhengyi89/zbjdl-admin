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
  <h2 class="fw">查询结果<span>（共${queryForm.totalCount}条）</span></h2>
  <table ${tableBean.html!""}>
    <tr ${rowBean.titlehtml!""}>
    <#list columnBeans as columnBean>
      <th ${columnBean.titleHtml}>
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
      </th>
    </#list>
    </tr>
    <#if queryForm.queryResult??>
    <#list queryForm.queryResult.data as listable>
       ${stack.push(listable)}
     <tr ${stack.buildHtml(rowBean)!""}>
       <#list columnBeans as columnBean>
          <td ${stack.buildHtml(columnBean)!""}><#if columnBean.value??>${stack.findValue(columnBean.value,columnBean.escape)!""}<#elseif columnBean.body??>${stack.parseFtlStr(columnBean.body,columnBean.escape)!""}</#if></td>
        </#list>
      </tr>
      ${stack.pop()}
    </#list>
    </#if>
  </table>
  <div class="clearer"></div>
<#if tableBean.paging?? && !tableBean.paging>
</div>
</#if>
