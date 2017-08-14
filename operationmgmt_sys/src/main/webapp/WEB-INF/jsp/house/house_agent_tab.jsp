<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
<body style="height: 100%;">
	<div class="mini-fit" style="height:400px">
		<div class="mini-tabs" activeIndex="0"   style="width: 100%;height:100%">
		    <div title="出售房源详情" url="houseResource/sell/houseDetailPage.do?houseId=${houseId}&cityId=${cityId}" style="width: 100%;height:100%">
		    </div>
		    <div title="房源图片" url="houseImage/gotoHouseImage.action?houseId=${houseId}">
		    </div>
		    <div title="房源跟进" url="houseResource/sell/getLfHouseFollowUpPage.do?houseId=${houseId}" >
		    </div>
		    <div title="最近带看" url="agent/agentRecentlySeeList.action?houseId=${houseId}">
		    </div>
            <div title="审核记录" url="houseResource/sell/showHouseSellCheckRecordList.do?houseId=${houseId}">
            </div>
		</div>
	</div>
</body>
</html>