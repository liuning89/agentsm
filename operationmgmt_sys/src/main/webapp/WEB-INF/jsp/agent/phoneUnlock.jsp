<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>解锁手机号</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <script src="${ctx}/scripts/boot.js?v=${version}" type="text/javascript"></script>
    <style type="text/css">
        html,body
        {
            width:100%;
            height:100%;
            border:0;
            margin:0;
            padding:0;
            overflow:visible;
        }
    </style>
   <script type="text/javascript">
   		function verify(){
   			var mobile = mini.get("mobile").getFormValue();
//			if(mobile.trim() == ""){
//				mini.alert("请填写手机号!");
//				return false;
//			}
//			var reg = /^1[3|4|5|7|8|9]\d{9}$/;
//			if(reg.test(mobile) == false)
//           {
//			mini.alert("请填写正确的手机号!");
//			return false;
//           }
			return true;
   		}
   
		function unPhone(){
			if(verify()){
				 mobile = mini.get("mobile").getFormValue();
			
				mini.confirm("是否确认解锁手机？", "确定？",
                    function (action) {
        		
                        if (action == "ok") {
                        	
                        	lc.mask("正在处理中,请稍后...");
                        	$.ajax({
                        		url : "${ctx}/refuse/updateLfEmp.action?mobile="+mobile,
                                cache : false,
                                dataType : 'json',
                                success : function(text) {
                                	debugger;
                            		 if(text.status == 1){
                            			 lc.unmask();
                            			 mini.alert(text.message);
                            		 }else {
                            			 lc.unmask();
                            			 mini.alert(text.message);
                            		 }
                                }
                            });
                        } else {

							
                        }
                    }
                );
        	}
	
		}
		//查询次手机号一个月内解锁了几次
		function selectPhone(){
			if(verify()){
				var mobile = mini.get("mobile").getFormValue();
				lc.mask("正在处理中,请稍后...");
            	$.ajax({
            		url : "${ctx}/refuse/selectPhoneCount.action?mobile="+mobile,
                    cache : false,
                    dataType : 'text',
                    success : function(data) {
                		 if(data == -1){
                			 lc.unmask();
                			 mini.alert("查询失败,请稍后重试!");
                		 }else{
                			 lc.unmask();
                			 mini.alert("此手机号一个月内解锁次数为:"+data+"次!");
                		 }
                    }
                });
        	}
	
		}
	</script>
</head>

<body>
    <h1><b>解锁手机号</b></h1>
	<hr></hr>
	<span>手机号码:</span><input class="mini-textbox" required="true" id="mobile" name="mobile"  missingMessage="请填写电话号码"></input> <input type="button" value="解锁" onclick="unPhone()"/><input type="button" value="查看" onclick="selectPhone()"/>
	
</body>

<!-- END BODY -->
</html>
