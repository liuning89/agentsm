<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head path="<%=basePath%>">
    <base href="<%=basePath%>" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
<title></title>
</head>
<body>

	<form id="contactform" method="post">
        <div style="padding-left:11px; padding-bottom:5px;" align="left">
       
			<div id="errors" class="mini-checkboxlist" repeatItems="1" repeatLayout="table" 
		        textField="text" valueField="id" 
		        url="${ctx}/txt/imagebh.txt" >
   			 </div>
                <input labelField="true" label="说明:" style="width:350px;" id="mark" name="mark" class="mini-textarea" />
        </div>
        <div style="text-align:center;">               
            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>       
        </div>        
    </form>
    <script type="text/javascript">

        mini.parse();
        
        
        
        function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        
        function onOk(e) {

        	var e1 = mini.get("errors");
            var mark = mini.get("mark").getValue();
            if(e1.getValue() == ""){
                if(mark == ""){
                    mini.alert("请至少勾选一个驳回理由");
                    return false;
                }

            }

        	
        	mini.confirm("确定驳回图片申请？", "确定？",
                    function (action) {
                        if (action == "ok") {
                        	onCancel();
                        	window.Owner.rejectedData(e1.getValue(),mark);
                        } else {
								
							
                        }
                    }
                );
        }
        
        function onCancel(e) {
            //CloseWindow("cancel");
        	window.CloseOwnerWindow();
        }
        
        </script>
</body>
</html>