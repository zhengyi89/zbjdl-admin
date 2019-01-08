;(function(jQuery){
	this.srcJquery = jQuery.ajax;
	prefilter = {};
	jQuery.ajax = function(options){
		var jqXHR  = {}; // 构造一个假的jQueryXMLHttpReques
		var newOptions = {};
		jQuery.extend(newOptions, options);
		inspectPrefilter(newOptions, options, jqXHR);
		srcJquery(newOptions);
	};
	
	jQuery.ajaxPrefilter = function(dataTypeExpression, func) {
		if ( typeof dataTypeExpression !== "string" ) {
			func = dataTypeExpression;
			dataTypeExpression = "*";
		}
		if (jQuery.isFunction( func )) {
			var dataTypes = dataTypeExpression.toLowerCase().split(/\s+/),
				i = 0,
				length = dataTypes.length,
				dataType,
				list,
				placeBefore;
			for ( ; i < length; i++ ) {
				dataType = dataTypes[i];
				placeBefore = /^\+/.test( dataType );
				if ( placeBefore ) {
					dataType = dataType.substr( 1 ) || "*";
				}
				list = prefilter[ dataType ] = prefilter[ dataType ] || [];
				list[ placeBefore ? "unshift" : "push" ]( func );
			}
		}
	};
	
	function inspectPrefilter(options, originalOptions, jqXHR) {
		var dataType = options.dataType;
		for(var p in prefilter){
			if(p == "*" || p == dataType){
				var list = prefilter[p],
					length = list ? list.length : 0;
				for(var i = 0; i < length; i++ ) {
					list[i](options, originalOptions, jqXHR);
				}
			}
	  }
	}
	
	return jQuery;
})(jQuery);

	function _ajaxext_retrive_response_error_data(xhr){
			var errorType = xhr.getResponseHeader('r_error_type');
  		if(errorType){
  			var errorData = new Object();
  			errorData.error_type  = errorType;
  			errorData.exception_msg = xhr.getResponseHeader('r_exception_msg');
  			if(errorData.exception_msg){
  				errorData.exception_msg = _ajaxext_url_decode(errorData.exception_msg);
  			}
  			errorData.exception_id = xhr.getResponseHeader('r_exception_id');
  			errorData.exception_code = xhr.getResponseHeader('r_exception_code');
				xhr.error_data = errorData;
  		}
	}
	
	$.ajaxPrefilter( function( options, originalOptions, jqXHR ) {
			if(options.complete){
  			options.complete = function(jqXHR, textStatus){
  				_ajaxext_retrive_response_error_data(jqXHR);
  				 originalOptions.complete(jqXHR, textStatus);
  			}
  		}
  		if(options.error){
  			options.error = function(jqXHR, textStatus, errorThrown){
  				_ajaxext_retrive_response_error_data(jqXHR)
  				originalOptions.error(jqXHR, textStatus, errorThrown);
  			}
  	  }
  		if(options.success){
  			options.success = function(data,status,xhr){
  				_ajaxext_retrive_response_error_data(xhr)
  				if(xhr.error_data){
  					if(originalOptions.error){
  						originalOptions.error(xhr, status);
  					}
  				} else{
  					originalOptions.success(data,status,xhr);
  				}
  			}
			}
  }
	);
	
	
    var _ajaxext_url_encode = function(inputStr){           
        var resultArr=[];   
        var chars="!\"#$%&'()*+,/:;<=>?[]^`{|}~%";                
        for(var i=0;i<inputStr.length;i++){              
            var tmpChar = inputStr.charAt(i);              
            var c = inputStr.charCodeAt(i);                      
            if(c > 0x7E){                   
                resultArr[i]=encodeURI(tmpChar);             
            }else{                   
                if(tmpChar==" ")                      
                    resultArr[i]="+";                   
                else if(chars.indexOf(tmpChar)!=-1)                      
                    resultArr[i]="%"+c.toString(16);                   
                else                      
                    resultArr[i]=tmpChar;                  
            }              
        }          
        return resultArr.join("");      
    }


    var _ajaxext_url_decode = function(inputStr){     
        var resultArr =[];       
        for(var i=0;i<inputStr.length;i++){     
            var chr = inputStr.charAt(i);  
            if(chr == "+"){                   
                resultArr[resultArr.length]=" ";              
            }else if(chr=="%"){                   
                var asc = inputStr.substring(i+1,i+3);   
                if(parseInt("0x"+asc)>0x7f){                       
                    resultArr[resultArr.length]= decodeURI(inputStr.substring(i,i+9));                   
                    i+=8;                   
                }else{                       
                    resultArr[resultArr.length]=String.fromCharCode(parseInt("0x"+asc));                       
                    i+=2;                   
                }              
            }else{                   
                resultArr[resultArr.length]= chr;              
            }          
        }          
        return resultArr.join("");     
    } 