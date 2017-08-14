<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="http://cdn.staticfile.org/twitter-bootstrap/3.0.1/css/bootstrap.min.css">
<style type="text/css">
body {
	font-family: "微软雅黑";
}
.footer {
  position: fixed;
  left: 0;
  right: 0;
  z-index: 10000;
  bottom: 0;
  background-color: #0099CC ; 
  color: white;
}
.footer-inner{
margin-bottom: 5px;
font-size: 13px;
}
</style>
<script language="JavaScript">  
		function loadTopWindow(){
			if (window.parent!=null && window.parent.document.URL!=document.URL){  
				window.parent.top.location= document.URL;
			}
		}
	</script>
</head>
<body onload="loadTopWindow()" style="overflow:hidden;overflow-y:hidden">
<div class="container-fluid">
  <div class="row" style="background-color: #0099CC  ; height: 100px;">
    <div>
        <h1 style=" margin-left: 100px; margin-top:30px; color: white; font-family: '微软雅黑'" >有房有客后台<small style=" color: #9999CC  ">&nbsp;&nbsp;Version 1.0</small></h1>
    </div>
  </div>
  <div class="row" style="margin-top: 100px; height: 400px;" >
	  <div class="col-md-5"></div>
	  <div class="col-md-2">
	        <form id="loginForm" action="${pageContext.request.contextPath}/login.action" method="post">
	          <div class="form-group">
			    <div class="page-header">
			        <h3 style="font-family: '微软雅黑'">用户登录</h3>
			    </div>
			  </div>
	        
			  <div class="form-group">
			    <label for="exampleInputEmail1">姓名</label>
			    <input type="text" class="form-control" name="userName" value="" id="userNameInput" placeholder="请输入姓名">
			  </div>
			  <div class="form-group">
			    <label for="exampleInputPassword1">密码</label>
			    <input type="password" class="form-control"  name="password" value="" placeholder="请输入密码">
			  </div>
			   <div class="form-group">
			    <span style="color: red">${message}</span>
			  </div>
			  <button type="submit" class="btn btn-info">提交</button>
			</form>
	  </div>
	  <div class="col-md-5"></div>
   </div>
   </div>

<div class="footer">
	<div class="footer-inner">
		2014 &copy; 经纪人后台管理系统. All right reserved.
	</div>
	<div class="footer-tools">
			<span class="go-top">
			<i class="icon-angle-up"></i>
			</span>
	</div>
</div>   
   
</body>
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript">
		document.getElementById("userNameInput").focus();
	</script>
</html>