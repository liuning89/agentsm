<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<html>
	<head>
	    <base href="<%=basePath%>" />
	    <meta http-equiv="pragma" content="no-cache" />
	    <meta http-equiv="cache-control" content="no-cache" />
	    <meta http-equiv="expires" content="0" />
		<script src="scripts/boot.js?v=${version}" type="text/javascript"></script>
	<title></title>
	<script type="text/javascript">	
    	if(window!=window.top){
			window.parent.location.href="<%=basePath%>";
		}
    </script>
	</head>
	<body>
	<h1 style="text-align: center;margin-top: 10%">有房有客运营管理系统</h1>
		<input type='hidden' value='__login__'/>
		<div id="loginWindow" class="mini-window" title="用户登录" style="width:350px;height:165px;" 
		   showModal="true" showCloseButton="false"
		    >
		    <div id="loginForm" style="padding:15px;padding-top:10px;">
		        <table >
		            <tr>
		                <td style="width:60px;"><label for="username$text">帐号：</label></td>
		                <td>
		                    <input id="username" name="username"  class="mini-textbox" required="true" style="width:150px;"/>
		                </td>    
		            </tr>
		            <tr>
		                <td style="width:60px;"><label for="pwd$text">密码：</label></td>
		                <td>
		                    <input id="pwd" name="pwd" class="mini-password" requiredErrorText="密码不能为空" required="true" style="width:150px;" onenter="lf.agentsm.login.login"/>
		                </td>
		            </tr>            
		            <tr>
		                <td></td>
		                <td style="padding-top:5px;">
		                    <a onclick="lf.agentsm.login.login" class="mini-button" style="width:60px;">登录</a>
		                    <a onclick="lf.agentsm.login.reset" class="mini-button" style="width:60px;">重置</a>
		                </td>
		            </tr>
		        </table>
		    </div>
		</div>
	</body>
	<script type="text/javascript" src="scripts/login.js?v=${version}"></script>
</html>