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
    calendar.add(calendar.MONTH,-1);//把日期往后增加一天.整数往后推,负数往前移动
    date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
    String dateString = formatter.format(date);
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
        <span>月份：</span><input id="monthP" class="mini-datepicker" value="<%=dateString%>" format="yyyy-MM"  style="width:120px;"/>
        <input type="button" value="查找" onclick="search()"/>
        <input type="button" value="重置" onclick="reset()"/>
    </div>
	<!--列表-->
    <div id="storeReportDiv" class="mini-fit" style="height:400px">
          <div id="dg" class="mini-datagrid"  style="height:100%;" pageSize='20' sizeList="[20,30,50,100]" url="${ctx}/report/storeMonReportList.action">
              <div property="columns">
              	<div type="indexcolumn"></div>
                <div field="storeName"   headerAlign="center">门店</div>                            
                <div field="publishManNum"   headerAlign="center" align="center">发布房源人数</div>                             
	            <div field="uploadPicManNum" align="center" headerAlign="center">上传图片人数</div>
                <div field="shareHouseNum" headerAlign="center" align="center">分享房源套数</div>
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
        var monthP = mini.get("monthP").getFormValue();
        grid.load({month:monthP});
	}
	
	function reset()
	{
        mini.get("monthP").setValue("<%=dateString%>");
	}
</script>