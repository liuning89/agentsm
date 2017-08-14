// 格式 日期date(yyyy-MM-dd hh:mm:ss.S), 1.格式化字符串; 2,毫秒数,无该参数表示当前时间
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
function dateFmt(fmt,dateMeilliseconds) {
	var fdate = new Date();
	if(dateMeilliseconds){
		fdate = new Date(dateMeilliseconds);
	}
    var o = {
        "M+": fdate.getMonth() + 1, //月份 
        "d+": fdate.getDate(), //日 
        "h+": fdate.getHours(), //小时 
        "m+": fdate.getMinutes(), //分 
        "s+": fdate.getSeconds(), //秒 
        "q+": Math.floor((fdate.getMonth() + 3) / 3), //季度 
        "S": fdate.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (fdate.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function myformatter(date){
	 var y = date.getFullYear();
	 var m = date.getMonth()+1;
	 var d = date.getDate();
	 return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
function myparser(s){
	 if (!s) return new Date();
	 var ss = (s.split('-'));
	 var y = parseInt(ss[0],10);
	 var m = parseInt(ss[1],10);
	 var d = parseInt(ss[2],10);
	 if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
	 return new Date(y,m-1,d);
	 }else{
	 return new Date();
	 }
}


/*
 js 小数 四舍五入
 x  数值
 len 保留小数点后位数 的 长度, 不填 该参数 默认是 2
*/
function toDecimal(x,len) {
	if(len){
	}else{
		len = 2;
	}
	//debugger;
	var f = parseFloat(x);
	if (isNaN(f)) {
		//不是 数字 类型
		return 0;
	}
	var f = Math.round(x*100)/100;
	var s = f.toString();
	var rs = s.indexOf('.');
	if (rs < 0) {
		rs = s.length;
		s += '.';
	}else{
		//有小数点的数据
		s = f.toFixed(len);
	}
	// 不足 小数 位数的 补充0
	while (s.length <= rs + len) {
		s += '0';
	}
	return s;
}

/** 根据类型加载crm日志列表
	basePath 项目 目录  即 ${rc.contextPath}
	logType 必填
	cityId 可以为空
*/
function load_crmlog(basePath,logType,cityId){
	var msg = '';
	if(cityId == null){
		cityId = '';
	}
	if(logType !=null && logType !=''){
    	var url= basePath+"/crmlog/index?type="+logType+"&cityId="+cityId;
    	window.location.href = url;
    	return ;
//    	$.ajax( {
//    		url : url,
//    		async:false,
//    		method : 'GET',
//    		success : function(data) {
//				msg = data;
//    		},
//    		error : function(data) {
//    			msg = data;
//    		}
//    	});
	}
	return msg;
}
