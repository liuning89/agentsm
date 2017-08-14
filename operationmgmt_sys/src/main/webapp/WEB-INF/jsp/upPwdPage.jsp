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

        <div id="loginForm" style="padding:15px;padding-top:10px;">
		        <table >
		            <tr>
		                <td style="width:60px;"><label for="username$text">原密码：</label></td>
		                <td>
		                    <input id="pwd" name="pwd" requiredErrorText="密码不能为空"  class="mini-password" required="true" style="width:150px;"/>
		                </td>    
		            </tr>
		            <tr>
		                <td style="width:70px;"><label for="pwd$text">新密码：</label></td>
		                <td>
		                    <input id="newpwd" name="newpwd" class="mini-password" requiredErrorText="新密码不能为空" required="true" style="width:150px;" />
		                </td>
		            </tr>     
		             <tr>
		                <td style="width:70px;"><label for="pwd$text">确认新密码：</label></td>
		                <td>
		                    <input id="newpwd1" name="newpwd1" class="mini-password" requiredErrorText="新密码不能为空" required="true" style="width:150px;"/>
		                </td>
		            </tr>            
		            <tr>
		                <td style="padding-top:5px;">
		                    <a onclick="upPwd()" class="mini-button" style="width:80px;">确认修改</a>
		                    
		                </td>
		            </tr>
		        </table>
		    </div>      
    <script type="text/javascript">

        mini.parse();
        
        
        
        function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        
        function upPwd() {

        	var n = mini.get("newpwd").getValue();
        	var n1 = mini.get("newpwd1").getValue();
        	
        	if(n != n1){
        		mini.alert("两次新密码不一样!");
        		return false;
        	}
        	
        	 
        	var pwd = mini.get("pwd").getValue();
        	
        	mini.confirm("确定修改密码？", "确定？",
                    function (action) {
                        if (action == "ok") {
                        	onCancel();
                        	window.Owner.rejectedData(pwd,n);
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