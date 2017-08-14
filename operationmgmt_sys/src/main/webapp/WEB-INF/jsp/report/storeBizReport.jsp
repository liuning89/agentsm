<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    Date date=new Date();//取时间
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
    date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = formatter.format(date);
    String startDate = dateString + " 00:00:00";
    String endDate = dateString + " 23:59:59";
%>
<!DOCTYPE html>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<html>
<head path="<%=basePath%>">
    <base href="<%=basePath%>" />
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
</head>
<body style="height:100%;" path="<%=basePath%>">
	<div style="margin: 10px;">
        <span>开始时间：</span><input id="createTimestart" class="mini-datepicker" value="<%=startDate%>"  format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm:ss"  style="width:240px;" showTime="true" showOkButton="true" showClearButton="false"/>
        <span>结束时间：</span><input id="createTimeend" class="mini-datepicker" value="<%=endDate%>" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm:ss"  style="width:240px;" showTime="true" showOkButton="true" showClearButton="false"/>
        <input type="button" value="查找" onclick="search()"/>
        <input type="button" value="重置" onclick="reset()"/>
    </div>
	<!--列表-->
    <div id="storeReportDiv" class="mini-fit" style="height:400px">
          <div id="dg" class="mini-datagrid"  style="height:100%;" pageSize='20' sizeList="[20,30,50,100]" url="${ctx}/report/storeBizReportList.action">
              <div property="columns">
              	<div type="indexcolumn"></div>
                <div field="storeName"   headerAlign="center">门店</div>                            
                <div field="publishNum"   headerAlign="center" align="center">发布房</div>                             
	            <div field="uploadPicNum" align="center" headerAlign="center">实景房</div>
	            <div field="keyNum"  headerAlign="center" align="center">钥匙房</div>
	            <div field="entrustNum"  headerAlign="center" align="center">独家房</div>                                      
	            <div field="addMasterNum"  headerAlign="center" align="center">新增客户数</div>                                      
	            <div field="contactLandladyCount"  headerAlign="center" align="center">联系房东次数</div>                                      
	            <div field="leadMasterNum" headerAlign="center" align="center">带看客户数</div>     
                    <div field="forwardHouseCount" headerAlign="center" align="center">分享房源次数</div>                                 
              </div>
          </div>
    </div>
</body>
</html>
<script type="text/javascript">
	mini.parse();
	var grid = mini.get("dg");
	
	search();
	function search(){
        var createTimestart = mini.get("createTimestart").getFormValue();
        var createTimeend = mini.get("createTimeend").getFormValue();
		grid.load({createTimestart:createTimestart,createTimeend:createTimeend});
	}
	
	function reset()
	{
		mini.get("createTimestart").setValue("<%=startDate%>");
        mini.get("createTimeend").setValue("<%=endDate%>");
	}
</script>