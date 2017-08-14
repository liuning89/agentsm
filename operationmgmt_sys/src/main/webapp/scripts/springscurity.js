
function loginFilter (data,base) {
	if (data['access-denied']) {
		if (data.cause=='AUTHENTICATION_FAILURE') {
			if(!window.confirm('登录超时,请重新登录.')){
				return false;
			}
			window.location.href = base + "/index" ;
		} else if (data.cause=='AUTHORIZATION_FAILURE') {
			alert('对不起，你没有访问该资源的权限.');
		}
		return false;
	}
	return true;
}
(function($){
	// 保存原有的jquery ajax;
	var $_ajax = $.ajax;
	
	$.ajax = function(options){
		var originalSuccess,
			mySuccess,
			success_context;
	
		if (options.success) {
			// save reference to original success callback
			originalSuccess = options.success;
			success_context = options.context ? options.context : $;
			// 自定义callback
			mySuccess = function(data) {
				//console.debug(data);
				//console.debug(data['access-denied']);
				//console.debug(data.cause);
				if(!loginFilter(data)){
					return;
				}
				// call original success callback
				originalSuccess.apply(success_context, arguments);
			};
			
			// override success callback with custom implementation
			options.success = mySuccess;
		}
		
		// call original ajax function with modified arguments
		$_ajax.apply($, arguments);
	};
	
})(jQuery);