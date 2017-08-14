(function($){
	window.protoJQueryAjax = $.ajax;
	$.ajax=function(opt){
		var sucfn = opt.success;
		var errorStatus = opt.errorStatus;
		opt.success =function(dt,textStatus,jqXHR){
			if(dt){
				//后面根据情况修改
				if(dt.status == 403){
					try{
						mini.alert(dt.message,"系统提示",opt.noPri);
					}catch(e){
						alert(dt.message);
					}
				}else if(lc.checkLogin(dt)){
					lc.fwd(lc.rootPath());
				}else if(lc.checkLimitAccess(dt)){
					try{
						mini.alert("您的ip被限制访问。","系统提示");
					}catch(e){
						alert("您的ip被限制访问。");
					}
				}else if(dt.status && dt.status != 1){
					try{
						if($.isFunction(errorStatus)){
							//调用自定义的 状态不等与1的方法
							errorStatus.call(null,dt,textStatus,jqXHR);
						}else{
							mini.alert(dt.message,"系统提示");
						}
					}catch(e){
						alert(dt.message);
					}
				}
			}
			if($.isFunction(sucfn)){
				//调用success方法
				sucfn.call(null,dt,textStatus,jqXHR);
			}
		};
	
		var errfn = opt.error;
		opt.error = function(XMLHttpRequest, textStatus, errorThrown){
			if (textStatus == "parsererror") {
				if (lc.checkLogin(XMLHttpRequest.responseText)) {
					lc.fwd(lc.rootPath());
				}
			} else if (textStatus == "timeout") {
				//后面根据情况修改
				try{
					mini.alert("请求超时，请重试","系统提示");
				}catch(e){
					alert("请求超时，请重试");
				}
			}
			if($.isFunction(errfn)){
				//调用error方法
				errfn.call(null,XMLHttpRequest,textStatus,errorThrown);
			}
		};
		window.protoJQueryAjax(opt);
	}
})(jQuery);
window.lf = window.lf || {};
var lc = lf.common = {
	body:$("body"),
	/**
	 * 名字空间
	 * 
	 * @param {String}
	 * @author ck
	 *            ns
	 * @return {}
	 */
	namespace : function(ns) {
		var objects = ns.split(".");
		var object = window[objects[0]] = window[objects[0]] || {};
		$.each(objects.splice(1, objects.length), function() {
					object = object[this] = object[this] || {};
				});
		return object;
	},
	/**
	 * namespace的简写
	 * 
	 * @param {String}
	 * @author ck
	 *            ns
	 * @return {}
	 */
	ns : function(ns) {
		return this.namespace(ns);
	},
	/**
	 * 刷新页面
	 * @author ck
	 * @param {Window} win 页面window对象，默认为当前页面
	 */
	refresh : function(win) {
		win = win || window;
		win.location.href = win.location.href;
	},
	/**
	 * 转发
	 * @author ck
	 * @param {String} url 转发地址
	 * @param {Window} win 页面window对象，默认为当前页面
	 */
	forward : function(url, win) {
		win = win || window;
		win.location.href = url;
	},
	/**
	 * 转发
	 * @description forward的简写
	 * @author ck
	 * @param {String} url
	 * @param {Window} win
	 */
	fwd : function(url, win) {
		this.forward(url, win);
	},
	/**
	 * @author ck
	 * @param {String} path 追加在项目根目录后面的路径
	 * @return {String} 返回网站的根目录
	 */
	rootPath : function(path) {
		if(window.rootPath){
			return window.rootPath+path?path:"";
		}else{
			var head = document.getElementsByTagName("head")[0];
			var scripts = head.getElementsByTagName("script");
			for (var i = 0; i < scripts.length; i++) {
				var script = scripts[i];
				var regex=/lf.common.js(\?.*)?/ig;
				var result = regex.exec(script.src);
				if (result) {
					window.rootPath = script.src.substring(0,script.src.indexOf("script"));
					return rootPath+(path?path:"");
				}
			}
		}
	},
	/**
	 * 控制台日志输出
	 * @author ck
	 * @param {Object} text 输出内容
	 */
	log : function(text) {
		if (console && text) {
			console.log(text);
		}
	},
	/**
	 * loading框
	 * @author ck
	 * @param {String} text 加载提示内容
	 * @param {String|Jquery|DocumentObject} id loading框的作用范围
	 */
	mask:function(text,id){
		if(mini){
			mini.mask({
				el : document.body,
				cls : 'mini-mask-loading',
				html : text
			});
		}else{
			if(id){
				this.jqw(id).mask(text);
			}else{
				$("body").mask(text);
			}
		}
		
	},
	/**
	 * 关掉loading框
	 * @author ck
	 * @param {String|Jquery|DocumentObject} id loading框的作用范围
	 */
	unmask:function(id){
		if(mini){
			mini.unmask();
		}else{
			if(id){
				this.jqw(id).unmask();
			}else{
				$("body").unmask();
			}
		}
	},
	/**
	 * 将指定元素转换为jqeury对象
	 * @param {String|Jquery|DocumentObject} id
	 */
	jqw:function(id){
		var $Obj = null;
		if(id.jquery ){
			$Obj = id;
		}else{
			$Obj = $(id);
			if(!$Obj.jquery){
				throw new error("无法转换为jquery对象");
			}
		}
		return $Obj;
	},
	/**
	 * 创建一个类
	 * @author ck
	 * @param {String} name 类名
	 * @param {Object} proty 属性，proty对象中的属性有fields：array、constructor：fn、非固定属性，该属性的值只能为方法。
	 * fields数组中的值就是该类实例的私有属性，会自动给这些属性添加get、set方法；
	 * constructor对应的方法会在该类进行注册的时候执行。
	 * @return {}
	 */
	createModel:function(name,proty){
		var packages=name.split(".");
		var cn=packages.pop();
		var a;
		var infun = function(){
			return function(constructor){
				if(typeof(constructor)=="function"){
					var args = Array.prototype.slice.call(arguments,1);
					args.push(this);
					var _cb = function() {  
						constructor.apply(null,args);
					};
					_cb();
				}else if($.isPlainObject(constructor)){
					for(var i in constructor){
						var n=i.substring(0,1).toUpperCase()+i.substring(1);
						if("set"+n in this){
							this[i] = constructor[i];
						}
					}
				}
				for(var i in proty){
					var val = proty[i];
					if(i == "fields" && $.isArray(val)){
						for(var j=0;j<val.length;j++){
							a[val[j]]=null;
							var n=val[j].substring(0,1).toUpperCase()+val[j].substring(1);
							var _this = a;
							(function(that,v,n){
								
								that.prototype["get"+n] = function(){
									return this[v];
								};
								
								that.prototype["set"+n] = function(arg){
									this[v] = arg;
								};
							})(a,val[j],n);
							
						}
					}else if (i == "constructor" && $.isFunction(val)){
						var _cb = function() {  
							fn.apply(null,arguments);
						};
						_cb();
					}else if($.isFunction(val)){
						a[i] = val;
					}
				}
			};
		}
		
		if(packages.length>0){
			var b=this.ns(packages.join("."));
			b[cn]=infun();
			a = b[cn];
		}else{
			window[cn]=infun();
			a=window[cn];
		}
		return a;
	},
	proxyFn:function(fn,sco){
		return function(){
			fn.apply(sco,arguments);
		}
		
	},
	proxy:function(fn,scope){
		fn.apply(scope,arguments);
	},
	/**
	 * 检测content中是否包含需要登录的信息（解决ajax请求中，未登录需要跳转到登陆页）
	 * @author ck
	 * @param {}
	 *            content 后端返回给前端的返回值（特指登录页面的源码）
	 * @return {}
	 */
	checkLogin : function(content) {
		var checkItem = /<input\s*type\s*=\s*'hidden'\s*value\s*=\s*'__login__'\s*\/>/;
		return checkItem.test(content);
	},
	/**
	 * 检测content中是否包含限制访问
	 * @param {} content
	 * @return {}
	 */
	checkLimitAccess:function(content){
		var checkItem = /<input\s*type\s*=\s*'hidden'\s*value\s*=\s*'__limitAccess__'\s*\/>/
		return checkItem.test(content);
	},
	/**
	 * 基于jqeury的ajax，在无权限、登录超时状态提供默认的处理
	 * @description 默认返回格式为json，请求超时时间为60秒
	 * @author ck
	 * @param {} opt 可完全跟jqeury的opt一致，另外多提供了一个选项，noPri，是一个方法，在没有权限的情况下，弹出框提示后的回调函数
	 * @param {} text loading框里面的提示文字
	 * @param {String|Jquery|DocumentObject} id loading框的作用范围
	 */
	ajax:function(opt,text,id){
		opt = $.extend({
			dataType : 'json',
			timeout : '60000'
		},opt);
		var sucfn = opt.success;
		opt.success =this.proxyFn(function(dt,textStatus,jqXHR){
			this.unmask(id);
			if(dt){
				if(dt.status == 403){
					try{
						mini.alert(dt.message,"系统提示",opt.noPri);
					}catch(e){
						alert(dt.message);
					}
				}
			}
			if($.isFunction(sucfn)){
				//调用success方法
				sucfn.call(null,dt,textStatus,jqXHR);
			}
		},this);
	
		var errfn = opt.error;
		opt.error = this.proxyFn(function(XMLHttpRequest, textStatus, errorThrown){
			this.unmask(id);
			if (textStatus == "parsererror") {
				if (this.checkLogin(XMLHttpRequest.responseText)) {
					this.fwd(this.rootPath());
				}else if(this.checkLimitAccess(XMLHttpRequest.responseText)){
					try{
						mini.alert("您的ip被限制访问。","系统提示");
					}catch(e){
						alert("您的ip被限制访问。");
					}
				}
			} else if (textStatus == "timeout") {
				//后面根据情况修改
				try{
					mini.alert("请求超时，请重试","系统提示");
				}catch(e){
					alert("请求超时，请重试");
				}
			}
			if($.isFunction(errfn)){
				//调用error方法
				errfn.call(null,XMLHttpRequest,textStatus,errorThrown);
			}
		},this);
		window.protoJQueryAjax(opt);
	},
	/**
	 * 将表单序列化成一个object形式
	 * @author ck
	 * @param {FormElment,String,Jqeury} form
	 * @param {Boolean} empty 为true，则表示表单中，值为''的字段不参与序列化
	 */
	serializeForm:function(form,empty){
		$form = this.jqw(form);
		form = $form[0];
		var parts = new Array();
	    var field = null;
	    var formObject = {};
		for (var i = 0, len = form.elements.length; i < len; i++) {
	        field = form.elements[i];
	        switch (field.type) {
	            case "select-one":
	            case "select-multipe":
	                for (var j = 0, optLen = field.options.length; j < optLen; j++) {
	                    var option = field.options[j];
	                    if (option.selected) {
	                        var optValue = "";
	                        if (option.hasAttribute) {
	                            optValue = (option.hasAttribute("value") ? option.value : "");
	                        }
	                        else {
	                            optValue = (option.attributes["value"].specified ? option.value : "");
	                        }
							 if (field.name) {
							 	if(empty == true && optValue == '')
							 		continue;
							 	if(formObject[field.name]){
							 		var vfo = formObject[field.name];
							 		if($.isArray(vfo)){
							 			vfo.push(optValue);
							 		}else{
							 			var arr = [];
							 			arr.push(vfo);
							 			arr.push(optValue);
							 			formObject[field.name]=arr;
							 		}
							 	}else{
							 		formObject[field.name]=optValue;
							 	}
							 }
						}
	                }
	                break;
	            case undefined:
	            case "file":
	            case "submit":
	            case "reset":
	            case "button":
	                break;
	            case "radio":
	            case "checkbox":
	                if (!field.checked) {
	                    break;
	                }
	            default:
	                if(field.name){
	                	if(empty == true &&  field.value=='')
						continue;
						if(formObject[field.name]){
					 		var vfo = formObject[field.name];
					 		if($.isArray(vfo)){
					 			vfo.push(field.value);
					 		}else{
					 			var arr = [];
					 			arr.push(vfo);
					 			arr.push(field.value);
					 			formObject[field.name]=arr;
					 		}
					 	}else{
					 		formObject[field.name]=field.value;
					 	}
					}
	        }
	    }
	    return formObject;
	},
	/**
	 * 获取地址栏中的参数（键值对）集合
	 * @author ck
	 * @param {Window} win 默认为当前window对象
	 * @return {Array}
	 */
	getURLParams:function(win){
    	if(!win)win = window;
    	if(!win.location)win = window;
    	var p=win.location.search.substring(1);
    	var arr=p.split("&");
    	var result={};
    	for(var i=0;i<arr.length;i++){
    		var entry=arr[i].split("=");
    		var key = entry[0];
    		var value = entry[1];
    		if(p.indexOf(key)==p.lastIndexOf(key)){
    			result[key] = value;
    		}else{
    			if(!result[[key]])
    				result[key] = [];
    			result[key].push(value);
    		}
    	}
    	return result;
    },
    /**
     * 根据键获取地址栏中的值
     * @author ck 
     * @param {String} key
     * @param {Window} win 默认为当前window对象
     * @return {String|Array}
     */
    queryParam:function(key,win){
    	return this.getURLParams(win)[key];
    },
    /**
     * 将字符串转成时间
     * @param {} str
     * @param {String} format y：年，M：月，d、D：天，H：小时，m:分钟，s、S：秒
     * @return {Date}
     */
	toDate:function(str,format){
		if(typeof(format) !='string')return null;
		var formatV={
			y:"(\\d{4})",
			M:"(1[0-2]|0?[1-9])",
			d:"(3[0-1]|[1-2]\\d|0?[1-9])",
			D:"(3[0-1]|[1-2]\\d|0?[1-9])",
			H:"(2[0-3]|1[0-9]|0?[0-9])",
			m:"([1-5][0-9]|0?[1-9])",
			s:"([1-5][0-9]|0?[1-9])",
			S:"([1-5][0-9]|0?[1-9])"
		};
		var order ={};
		var i=0;
		var formatEx = format.replace(/[yHMmdDSs]/g,function(v){
			order[v] = ++i;
			return formatV[v];
		});
		var reg = new RegExp(formatEx,"ig");
		var regResult = reg.exec(str); 
		if(regResult){
			return new Date(regResult[order["y"]]||null,regResult[order["M"]]-"1"||null,regResult[(order["d"]||order["D"])]||null,regResult[order["H"]]||null,regResult[order["m"]]||null,regResult[(order["S"]||order["s"])]||null);
		}
		return null;
	},
    dateFormat:function(date,format){
    	format = format||'y-M-d H:m:s';
		if(typeof(date) == "number")
			date = new Date(date);
		var l = date.getMonth()+1+"";
		if(l.length==1)l="0"+l;
		var D = date.getDate()+"";
		if(D.length == 1)D="0"+D;
		var a={y:date.getFullYear(),Y:(date.getFullYear()+"").substring(2),M:date.getMonth()+1,l:l,d:date.getDate(),D:D,H:date.getHours(),h:date.getHours()>12?date.getHours()-12:date.getHours(),m:date.getMinutes(),s:date.getSeconds(),S:date.getSeconds(),w:date.getDay()};
		var week = ["日","一","二","三","四","五","六"];
		var shijianduan="";
		return format.replace(/[yYHhMmdDSslw]/g,function(v){
			if(v == "w"){
				return "星期"+week[a[v]];
			}
			if(v=="h"){
				if(date.getHours()>12){
					shijianduan =" 下午";
				}else{
					shijianduan = " 上午";
				}
			}
			return ((a[v]+"").length>1?"":"0")+a[v];
		})+shijianduan;
	},
	/**
	 * 
	 * @param {String} format
	 * @return {}
	 */
	strFormat:function(format/**[,arg]*/){
		var arr = Array.prototype.slice.call(arguments,1);
		return format.replace(/\{[\d]+\}/g,function(v){
			return arr[v.substring(1,v.length-1)];
		});
	},
	fileUpload:function(opt){
		var params="";
		if(opt.data){
			$.each(opt.data,function(k,v){
				params+='<input type="hidden" name="'+k+'" value="'+v+'"/>';
			});
		}
		var id = (opt.id|"fileUpload");
		var html =  '<iframe name="fileUploaderEmptyHole" style="position:absolute;top:0px;left:0px;visibility:hidden;" id="iframe_'+id+'">'+
					+
					'</iframe>'
		;
		
		var fhtml = '	<form enctype="multipart/form-data"  method="post" action="'+opt.url+'" id="form_'+id+'">' +
							params +
					'	</form>'
		
		$("body").append(html);
		$("body").append(fhtml);
		var iframe =$("#iframe_"+id); 
		var form = $("#form_"+id);
		console.log(form);
		console.log($("#"+opt.id)[0]);
		form[0].appendChild($("#"+id)[0]);
		form[0].target = "fileUploaderEmptyHole";
		iframe.on("load",function(e){
			var doc = this.contentDocument || this.contentWindow.document;
			opt.success.call(opt.scope||this,doc.body.innerHTML);
			$("body").remove(iframe);
			$("body").remove(form);
		});
		form.submit();
		
		/*var iframe;
		try{iframe = document.getElme('<iframe name="fileUploaderEmptyHole">');}catch(e){
			iframe=_.doc.createEl("iframe");
			iframe.setAttribute("name","fileUploaderEmptyHole");
		}
		iframe.style.position = "absolute";
		iframe.style.top = "0px";
		iframe.style.left = "0px";
		iframe.style.visibility = "hidden";
		iframe.setAttribute("firstLoad","1"); 
		iframe.setAttribute("id",_.m.random());
		_.addEvent(iframe,'load',function(o){
			var doc = iframe.contentDocument || iframe.contentWindow.document;
			fn.call(scope||this,doc.body.innerHTML);
			_.doc.body.removeChild(iframe);
		});
		_.doc.body.appendChild(iframe);
		form.target = "fileUploaderEmptyHole";
		var paramsDom;
		if(_.isObj(params)){
			var shtml = "";
			_.each(params,function(i,v,k){
				shtml +='<input type="hidden" name="'+k+'" value="'+v+'"/>';
			});
			paramsDom = _.el.create(shtml);
			form.appendChild(paramsDom);
		}
		form.submit();
		if(paramsDom){
			form.removeChild(paramsDom);
		}*/
	},
	/**
	 * 移除对象的属性
	 * @param {Object} obj
	 * @param {String|Array} properties String类型，可用","分割，表示多个属性
	 */
	removeObjectProperty:function(obj,properties){
		if(properties){
			var proArr = [];
			if(typeof(properties) == "string"){
				proArr = properties.split(",");
			}else if($.isArray(properties)){
				proArr = properties;
			}
			for(var i =0; i<proArr.length;i++){
				delete obj[proArr[i]];
			}
		}
	},
	/**
	 * 移除对象的空值属性,exclude是排除哪些字段不移除
	 * @param {Object} obj
	 * @param {String|Array} excludes String类型，可用","分割，表示多个属性
	 */
	removeObjectEmptyValueProperty:function(obj,excludes){
		var proArr = [];
		if(excludes){
			var proArr = [];
			if(typeof(excludes) == "string"){
				proArr = excludes.split(",");
			}else if($.isArray(excludes)){
				proArr = excludes;
			}
		}
		for(var i in obj){
			if(proArr.length>0 && proArr.indexOf(i)!=-1){
				continue;
			}
			if(obj[i] == ""){
				delete obj[i];
			}
		}
	}
}


$(document).ajaxComplete(function (evt, request, settings) {
	var text = request.responseText;
	//判断返回的数据内容，如果是超时，则跳转到登陆页面
	var checkItem = /<input\s*type\s*=\s*'hidden'\s*value\s*=\s*'__login__'\s*\/>/;
	if (checkItem.test(text)) {
		top.location = bootPATH;
	}

})