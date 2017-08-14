<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<html>
<head path="<%=basePath%>">
<base href="<%=basePath%>" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script src="scripts/boot.js" type="text/javascript"></script>
</head>
<body style="height: 100%;" path="<%=basePath%>">
		<audio controls="controls" style="margin-top:10px;margin-left:2px">
			<source id="showSource" src="${src}" type="${type}"/>
			<embed id="showDiv" src="${src}"/>
		</audio>
</body>
</html>