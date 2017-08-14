<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head path="<%=basePath%>">
    <base href="<%=basePath%>"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
    <title></title>
</head>
<body style="height: 100%;">
<div class="mini-fit" style="height:400px">
    <div class="mini-tabs" activeIndex="0" style="width: 100%;height:100%">
        <div title="基本信息" url="customer/gotoCustomerBasicInfo.action?customerId=${customerId}" style="width: 100%;height:100%">
        </div>
        <div title="A客标记信息" url="customer/gotoCustomerAMark.action?customerId=${customerId}">
        </div>
        <div title="带看记录" url="customer/gotoCustomerHouseSee.action?customerId=${customerId}">
        </div>
        <div title="客户跟进" url="customer/gotoCustomerFollow.action?customerId=${customerId}">
        </div>
    </div>
</div>
</body>
</html>