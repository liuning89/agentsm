<%--
  User: Yangushan
  Date: 16/1/11
  Time: 下午6:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>描点工具</title>
        <base href="<%=basePath%>">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
        <style type="text/css">
            body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
        </style>
        <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=GRLKw5G6hZaxpGWSHvjrcmic"></script>
        <script src="http://api.map.baidu.com/getscript?v=2.0&ak=GRLKw5G6hZaxpGWSHvjrcmic&services=&t=20150330161927" type="text/javascript"></script>
        <!-- <link href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" rel="stylesheet"></link> -->
        <link href="styles/drawline.css" rel="stylesheet"></link>
        <script src="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js" type="text/javascript"></script>
        <link href="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.css" rel="stylesheet"></link>
        <script src="http://api.map.baidu.com/library/GeoUtils/1.2/src/GeoUtils_min.js" type="text/javascript"></script>
        <script type="text/javascript" src="scripts/BMap/drawline.js"></script>
        <script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
    </head>
    <body>
        <div id="allmap" style="overflow:hidden;zoom:1;">
            <div id="map" style="height:100%;-webkit-transition: all 0.5s ease-in-out;transition: all 0.5s ease-in-out;"></div>
        </div>
        <div class="mini-toolbar" style="position:absolute;top:0px;left:0px;">
            <input name="name" id="name" class="mini-textbox" />
            <a class="mini-button" id="search" iconCls="icon-search" onclick="lf.areaOrg.store.storeMap.search();">搜索</a>
            <a class="mini-button" id="save" iconCls="icon-save" onclick="lf.areaOrg.store.storeMap.save();">保存</a>
        </div>
        <input type="text" id="longitude" value="${longitude }"/>
        <input type="text" id="latitude" value="${latitude }"/>
        <input type="text" id="cityName" value="${cityName }"/>
    </body>
    <script type="text/javascript" src="scripts/pages/areaOrg/store/StoreMap.js"></script>
</html>
