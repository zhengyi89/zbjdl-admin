function autoComplete(ele){
  
  $('#'+ele).autocomplete(menuAutoCompleteJson, {
    max: 20,    //列表里的条目数
    minChars: 0,    //自动完成激活之前填入的最小字符
   width: 250,     //提示的宽度，溢出隐藏
   scrollHeight: 300,   //提示的高度，溢出显示滚动条
   matchContains: true,    //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示
  autoFill: false,    //自动填充
     formatItem: function(row, i, max) {
       return i + '/' + max + ':"' + row.name + '"[' + row.parentId + ']';
  },
formatMatch: function(row, i, max) {
  return row.name + row.parentId;
},
formatResult: function(row) {
return row.parentId;
}
}).result(function(event, row, formatted) {
/* alert(row.parentId);*/
});

  
}
