/**
 * 验证框架
 *	1.req: 必填（选）【true|false;checkbox:{min:,max:},默认:false】                                                                 
 *	2.lable: 标题                                                                                                            
 *	3.trim: 【true|false,默认:defaultTrim可配】                                                                                  
 *	4.datatype: 数据类型【email|mobile|phone|post|date|url|ip|mac|time|datetime|dateortime|int|float|decim|chinese|idcard,正则表达式】
 *	5.equalTo: 重复输入 【｛id:'elementId'｝】                                                                                     
 *	6.len: 长度【{min:,max:}，可以可选一个】                                                                                          
 *	7.number: 数值【{min:,max:}，可以可选一个】                                                                                       
 *	8.msg:错误提示信息，设置msg后lable和默认提示将不起作用                                                                                     
 */
Validate = function(){
  return{
    complexBytes: 3,
   	defaultTrim: true,
    regexs: {
      "email": /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
      "mobile": /^1[0-9]{10}$/,
      "phone": /^0[0-9]{2,3}[-|－][0-9]{7,8}$/,
      "post": /^[0-9]{6}$/,
      "date": /^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$/,
      "url": /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i,
      "ip": /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
	  	"mac": /^([0-9a-fA-F]{2})(([\s:-][0-9a-fA-F]{2}){5})$/,
	  	"time": /^(((0[0-9])|([1-2][0-3]))\:([0-5][0-9])((\s)|(\:([0-5][0-9])))?)?$/,
      "datetime": /^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)(\s(((0[0-9])|([1-2][0-3]))\:([0-5][0-9])((\s)|(\:([0-5][0-9])))?)){1}$/,
      "dateortime": /^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)(\s(((0[0-9])|([1-2][0-3]))\:([0-5][0-9])((\s)|(\:([0-5][0-9])))?))?$/,
	  	"int": /^\d+$/,
      "float": /^\d+\.\d+$/,
      "decimal": /^\d+(\.\d+)?$/,
      "chinese": /^\u4e00-\u9fa5$/,
	  	"idcard": {test: function(idcard){
			  var WI = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
			  var CODE = ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'];
			  if(idcard.length == 15 && /^\d{15}$/.test(idcard)) {
					var si = 0;
					var $18 = "";
					$18 += idcard.substring(0, 6);
					$18 += "19";
					$18 += idcard.substring(6, 15);
					for(var i = 0; i < 17; i++) { 
						si += ($18.charAt(i) - '0') * WI[i]; 
					}
					$18 += CODE[si % 11];
					idcard = $18;
			  }
			  if(idcard.length == 18) {
					var y = idcard.substring(6, 10);
					var m = idcard.substring(10, 12);
					var d = idcard.substring(12, 14);
					var date = y + "-" + m + "-" + d;
					if(!Validate.regexs['date'].test(date)) {
						return false;
					}
					idcard = idcard.toUpperCase();
					if(/^\d{17}[xX]|\d{18}$/.test(idcard)) {
						var chars = idcard.split("");
						var si = 0;
						for(var i = 0; i < 17; i++) {
							si += (chars[i] - '0') * WI[i];
						}
						return chars[17] == CODE[si % 11];
					}
					return false;
			   }
			   return false;
		  }},
	  	"bankcard": {test: function(bankCard){
			  if(bankCard && bankCard.length > 15) {
					var nonCheckCodeCardId = bankCard.substring(0, bankCard.length - 1);
					if (/\d+/.test(nonCheckCodeCardId)) {
						var chs = nonCheckCodeCardId.split("");
						var luhmSum = 0;
						for (var i = chs.length - 1, j = 0; i >= 0; i--, j++) {
							var k = chs[i] - '0';
							if (j % 2 == 0) {
								k *= 2;
								k = parseInt(k / 10 + k % 10);
							}
							luhmSum += k;
						}
						var b = (luhmSum % 10 == 0) ? 48 : ((10 - luhmSum % 10) + 48);
						b = String.fromCharCode(b);
						return bankCard.charAt(bankCard.length - 1) == b;
					}
			   }
			   return false;
	  	}}
	  	/*regexs end*/
    	},
    titles : {
    	"email": "Email",
    	"idcard": "身份证号码",
    	"mobile": "手机",
    	"phone": "座机",
    	"post": "邮编",
    	"date": "日期",
    	"time": "时间",
    	"datetime": "日期时间",
			"dateortime": "日期时间",
    	"url": "URL",
			"ip": "IP地址",
			"mac": "MAC地址",
			"bankcard": "银行卡号"  
		},
    errorMsgs : {
      "email": "格式错误 如:haha@163.com",
      "number": "必须在${min}到${max}之间",
	  	"number_min": "必须大于${min}",
	  	"number_max": "必须小于${max}",
      "url": "格式错误 如:http://www.yeepay.com",
      "mobile": "格式错误 如:13800138000",
      "phone": "格式错误 如:010-60100001",
      "date" : "格式错误 如:2008-08-08",
      "datetime": "格式错误 如:2008-08-08 20:08:08",
	  	"dateortime": "格式错误 如:2008-08-08 20:08:08",
      "time": "格式错误 如:12:08:08",
      "post": "格式错误 如:100035",
      "len": "长度必须在${min}到${max}个字节之间",
	  	"len_min": "长度必须在${min}个字节以上",
	  	"len_max": "长度必须在${max}个字节以下",
      "has" : "已存在",
      "format" : "格式错误",
      "req": "是必填项",
	  	"NOT_NUMBER": "必需为数字",
      "NOT_EQUAL": "必需和equalTo相同",
	  	"checked": "选择必须在${min}到${max}之间",
	  	"checked_min": "选择必须多于${min}",
	  	"checked_max": "选择必须少于${max}",
	  	"decimal": "必须为数字",
	  	"int": "必须为整数",
	  	"float":"必须为小数"
    },
    testFuns: {
      "len": {
          test: function($src){
              var len = $src.attr("len");
              if (len) {
                  len = eval("(" + len + ")");
                  var value = Validate._getVal($src);
                  var realLen = value.length;
                  var validLen = len.max;
                  if (validLen) {
                      for (var i = 0; i < value.length; i++) {
                          if ((validLen -= escape(value.charAt(i)).length > 4 ? Validate.complexBytes : 1) < 0) {
                              return "TOO_LONG";
                          }
                      }
                      if (!isNaN(len.min) && len.max - validLen < len.min) {
                          return "TOO_SHORT";
                      }
                  }
                  else 
                      if (!isNaN(len.min)) {
                          validLen = len.min;
                          for (var i = 0; i < value.length; i++) {
                              if ((validLen -= escape(value.charAt(i)).length > 4 ? Validate.complexBytes : 1) < 0) {
                                  return "SUCCESS";
                              }
                          }
                          if (validLen > 0) {
                              return "TOO_SHORT";
                          }
                      }
              }
              return "SUCCESS";
          }
      },
      "number": {
          test: function($src){
              var rule = $src.attr("number");
              if (rule) {
                  rule = eval("(" + rule + ")");
                  var value = parseFloat($src.val());
 
                  if (isNaN(value)) {
                      return "NOT_NUMBER";
                  }
                  var max = rule.max;
                  var min = rule.min;
                  if (!isNaN(rule.min) && !isNaN(rule.max)) {
                      if (value < min) {
                          return "TOO_SMALL";
                      }
                      if (value > max) {
                          return "TOO_BIG";
                      }
                  }
                  else 
                      if (!isNaN(rule.min) && value < min) {
                          return "TOO_SMALL";
                      }
                      else 
                          if (!isNaN(rule.max) && value > max) {
                              return "TOO_BIG";
                          }
              }
              return "SUCCESS";
          }
      },
      "equalTo": {
           test: function($src) {
             var rule = $src.attr('equalTo');
             if(rule) {
               rule = eval("(" + rule + ")");
               var equalObj = $('#' + rule.id);
               if(equalObj) {
                  if(Validate._getVal(equalObj) == Validate._getVal($src)) {
                     return "SUCCESS";
                  }else {
                     return "NOT_EQUAL";
                  }
               }
             }
             return "SUCCESS";
           } 
      }
    },
    
		/* 输出错误信息 */
    msgPrint: function($src, errorFun, errorMsg) {
      var msg = $src.attr("msg");
      if(errorMsg && msg) {
      	errorMsg = msg;      	
      }
    	if(errorFun) {
    			errorFun($src, errorMsg);
    	}else if(errorMsg){
    		alert(errorMsg);
	      $src.focus();
    	}
    },
    
    value: function($src, options, errorFun){
    		var optionRule = options[$src.attr('name')];
    		if(optionRule) {
  				for(var r in optionRule){
  					var rrr = optionRule[r];
  					if(jQuery.type(rrr) == 'object') {
  						var arr = [];
  						for(var rr in rrr) {
  							arr.push(rr + ":" + rrr[rr]);
  						}
  						rrr = '{' + arr.join(',') + '}';					
  					}
  			  	$src.attr(r, rrr);
    			}
    		}
    		var datatype = $src.attr("datatype");
        var title = $src.attr("label") ? $src.attr("label") : Validate.titles[datatype] ? Validate.titles[datatype] : '';
        var type = null;
        var rule = null;
        var val = Validate._getVal($src);
        var req = eval($src.attr("req"));
        /*判空*/
        if (val.length == 0) {
            if (req) {
                Validate.msgPrint($src, errorFun, title + Validate.errorMsgs['req']);
                return false;
            } else {
            		Validate.msgPrint($src, errorFun);
                return true;
            }
        }
        Validate.msgPrint($src, errorFun);
        if (datatype) {
            if (!Validate.regexs[datatype]) {
                /*自定义正则*/
                var reg = new RegExp(datatype);
                if (!reg.test(val)) {
                    Validate.msgPrint($src, errorFun, title + Validate.errorMsgs['format']);
                    return false;
                }
            }
            else if (!Validate.regexs[datatype].test(val)) {
							var msg =  Validate.errorMsgs[datatype];
							if(!msg) {
								msg = Validate.errorMsgs['format'];
							}
              Validate.msgPrint($src, errorFun, title + msg);
              return false;
            }
        }
        for (var name in Validate.testFuns) {
            if ((rule = $src.attr(name))) {
                var result = null;
                if ((result = Validate.testFuns[name].test($src)) != "SUCCESS") {
                    rule = eval("(" + rule + ")");
                    if (result == 'NOT_NUMBER') {
                      errorMsg = title + Validate.errorMsgs[result];
                    }else if(result == 'NOT_EQUAL') {
                      errorMsg = title + Validate.errorMsgs[result].replace(name, $("#" + rule.id).attr('label'));
                    }else if (!isNaN(rule.min) && !isNaN(rule.max)) {
                      errorMsg = title + Validate.errorMsgs[name].replace("${max}", rule.max).replace("${min}", rule.min);
                    }else if (!isNaN(rule.min)) {
                      errorMsg = title + Validate.errorMsgs[name + '_min'].replace("${min}", rule.min);
                    }else if (!isNaN(rule.max)) {
                      errorMsg = title + Validate.errorMsgs[name + '_max'].replace("${max}", rule.max);
                    }
                    Validate.msgPrint($src, errorFun, errorMsg);
                    return false;
                }
            }
        }
        return true;
    },
   	checked: function($this, rule, errorFun){
       if(rule) {
      			var req = rule['req'];
      			var label = rule['label'] ? rule['label'] : '';
      			if($this.is(':checkbox')) {
      				var checkSum = $('[name=' + $this.attr('name') + ']:checkbox:checked').size();
      				if(!isNaN(req.min) && !isNaN(req.max)) {
      					if(checkSum < req.min || checkSum > req.max){
      						Validate.msgPrint($this, errorFun, label + Validate.errorMsgs['checked'].replace("${max}", req.max).replace("${min}", req.min));
      						return false;
      		            }
      				}else if(!isNaN(req.min)){
      					if(checkSum < req.min){
      						Validate.msgPrint($this, errorFun, label + Validate.errorMsgs['checked_min'].replace("${min}", req.min));
      						return false;
      		            }
      				}else if(!isNaN(req.max)){
      					if(checkSum > req.max){
      						Validate.msgPrint($this, errorFun, label + Validate.errorMsgs['checked_max'].replace("${max}", req.max));
      						return false;
      		            }
      				}else if(req) {
      					if(checkSum == 0) {
      						Validate.msgPrint($this, errorFun, label + Validate.errorMsgs['req']);
      						return false;
      					}
      				}
      			}else if($this.is(':radio')) {
      				var checkSum = $('[name=' + $this.attr('name') + ']:radio:checked').size();
      				if(req) {
      					if(checkSum == 0) {
      						Validate.msgPrint($this, errorFun, label + Validate.errorMsgs['req']);
      						return false;
      					}
      				}
      			}else if($this.is('select')) {
      				var selectVal = $this.find(':selected').val();
      				if(req) {
      					if(!selectVal || selectVal == "false") {
      						Validate.msgPrint($this, errorFun, label + Validate.errorMsgs['req']);
      						return false;
      					}
      				}
      			}
      			Validate.msgPrint($this, errorFun);
     		}
     		return true;
   	},
   	_getVal: function($src){
   		var trim = $src.attr("trim");
   		var val = $src.val();
   		if(trim && trim.length > 0) {
   			if(eval(trim)){
   				val = $.trim(val);		
   			}
   		}else if(Validate.defaultTrim) {
   			val = $.trim(val);	
   		}
   		return val;
   	}
	// end
  };
}();

/**
	* 监听form内所有text,password,textarea对象
	* event 必传，监听事件的字符串表示，如：“keyup blur”
	* errorFun 必传，错误处理方法，由用户定义
	* 	两个参数1.obj 出现错误的jquery对象，2. errorMsg 错误信息    
	*
	*/
$.fn.listenerValue = function(event, options, errorFun){
    return this.find("input[type=text],input[type=password],textarea").each(function(){
        var fun = null;
        if (errorFun) {
            $(this).bind(event, function(){
                if (Validate.value($(this), options, errorFun)) {
                    if ((fun = $(this).attr("fun"))) {
                        fun = eval(fun);
                    }
                }
            });
        }
    });
};

/**
	* 监听form内所有:checkbox, :radio, select对象
	* event 必传，监听事件的字符串表示，如：“keyup blur”
	* options 必传，验证规则
	* errorFun 必传，错误处理方法，由用户定义
	* 	两个参数1.obj 出现错误的jquery对象，2. errorMsg 错误信息    
	*
	*/
$.fn.listenerChecked = function(event, options, errorFun){
    return this.find(":checkbox, :radio, select").each(function(){
        var name = $(this).attr("name");
        var rule = options[name];
        var fun = null;
        if (errorFun && rule) {
            $(this).bind(event, function(){
                if (Validate.checked($(this), rule, errorFun)) {
                    if ((fun = $(this).attr("fun"))) {
                        fun = eval(fun);
                    }
                }
            });
        }
    });
};

/**
	* 监听form内所有:checkbox, :radio, select对象
	* event 必传，监听事件的字符串表示，如：“keyup blur”
	* options 必传，验证规则
	* errorFun 必传，错误处理方法，由用户定义
	* 	两个参数1.obj 出现错误的jquery对象，2. errorMsg 错误信息    
	*
	*/
$.fn.validateSubmit = function(options, errorFun){
    var result1 = true;
    $(this).find("input[type=text],input[type=password],textarea").each(function(){
        if (!Validate.value($(this), options, errorFun)) {
            result1 = false;
            return false;
        }
    });
    if (result1) {
        for (var name in options) {
            var rule = options[name];
            if (rule) {
                if (!Validate.checked($(this).find('[name=' + name + ']'), rule, errorFun)) {
                    return false;
                }
            }
        }
    }
    return result1;
};
