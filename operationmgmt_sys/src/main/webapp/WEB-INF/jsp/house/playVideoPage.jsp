<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head path="<%=basePath%>">
<base href="<%=basePath%>" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />

	<link href="http://example.com/path/to/video-js.css" rel="stylesheet">  
	<script src="http://example.com/path/to/video.js"></script>  
	<script>  
	  _V_.options.flash.swf = "http://example.com/path/to/video-js.swf"  
	</script>  
<title></title>
</head>
<body>
	<video id="example_video_1" class="video-js vjs-default-skin" controls
		preload="auto" width="640" height="264"
		poster="${imagekey}"
		data-setup='{"example_option":true}'> 
		<source src="${key}" type='video/mp4' />
		<!-- <source src="http://video-js.zencoder.com/oceans-clip.webm" type='video/webm' />    
 		<source src="http://video-js.zencoder.com/oceans-clip.ogv" type='video/ogg' />     -->
 		
		
	</video>
</body>
</html>