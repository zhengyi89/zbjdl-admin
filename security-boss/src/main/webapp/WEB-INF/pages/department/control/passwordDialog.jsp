<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<script type="text/javascript">
var $passwordDialogView = null;
</script>
<div id="passwordDialogView" style="display: none;">
  <form id="passwordDialogViewForm" method="post" onsubmit="return false;">
    <input type="hidden" name="departmentId" id="departmentId" value="" />
    <div id="wrapper" class="input_cont border_n">
            <ul>
          <li><label class="text_tit">管理员密码：</label> 
           <input type="password" class="input_text" name="password" id="password" />
           <span id="passwordMsg"></span>
          </li>
          </ul>
    </div>
  </form>
</div>