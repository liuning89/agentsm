<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>白名单</title>
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
		function addRefuseGuest(){
			if(!veriData){
				return false;
			}
			var mobile = mini.get("mobile").getFormValue();
			mini.confirm("是否确认加入白名单？", "确定？",
                    function (action) {
        		
                        if (action == "ok") {
                        	
                        	lc.mask("正在处理中,请稍后...");
                        	$.ajax({
                        		url : "${ctx}/refuse/addWhitelist.action?mobile="+mobile,
                                cache : false,
                                dataType : 'text',
                                success : function(data) {
                            		 if(data == 0){
                            			 lc.unmask();
                            			 mini.alert("添加白名单成功!");
                            		 }else if(data == 1){
                            			 lc.unmask();
                            			 mini.alert("添加白名单失败,请稍后重试!");
                            		 }else if(data == 2){
                            			 lc.unmask();
                            			 mini.alert("该号码已绑定,请勿重复添加!");
                            		 }else{
										 lc.unmask();
										 mini.alert("绑定非法号码!");
									 }
                                }
                            });
                        } else {

							
                        }
                    }
                );
        }
		function veriData(){
			var mobile = mini.get("mobile").getFormValue();
			if(mobile.trim() == ""){
				mini.alert("请填写座机号!");
				return false;
			}
			if(!/^[0-9]*$/.test(mobile)){
				mini.alert("请输入数字!");
				return false;
			}

		}
		function delRefuseGuest(){
			if(!veriData){
				return false;
			}
			var mobile = mini.get("mobile").getFormValue();
			mini.confirm("是否确认删除白名单？", "确定？",
                    function (action) {
        		
                        if (action == "ok") {
                        	
                        	lc.mask("正在处理中,请稍后...");
                        	$.ajax({
                        		url : "${ctx}/refuse/delWhitelist.action?mobile="+mobile,
                                cache : false,
                                dataType : 'text',
                                success : function(data) {
                            		 if(data == 1){
                            			 lc.unmask();
                            			 mini.alert("删除白名单成功!");
                            		 }else if(data == 0){
                            			 lc.unmask();
                            			 mini.alert("删除白名单失败,请稍后重试!");
                            		 }else{
                            			 lc.unmask();
                            			 mini.alert("该手机号码已存在黑名单中,请勿重复添加!");
                            		 }
                                }
                            });
                        } else {

							
                        }
                    }
                );
        }
	
	</script>
</head>

<body>
    <h1><b>白名单</b></h1>
	<hr></hr>
	<span>座机号码:</span><input class="mini-textbox" required="true" id="mobile" name="mobile"  missingMessage="请填写电话号码"></input>
	<input type="button" value="添加" onclick="addRefuseGuest()"/>
	<input type="button" value="删除" onclick="delRefuseGuest()"/>
</body>

<!-- END BODY -->
</html>
