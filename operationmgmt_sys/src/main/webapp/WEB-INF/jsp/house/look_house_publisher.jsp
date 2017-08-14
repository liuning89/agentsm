<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<html>
<head path="<%=basePath%>">
    <base href="<%=basePath%>" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
<head>
    <script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
<title></title>
</head>

<body style="height:100%;" path="<%=basePath%>">
    <!--列表-->
    <div class="mini-fit" style="height:200px">
          <div id="dg" class="mini-datagrid" style="height:25%;" url="houseResource/sell/getPublisherList.do?houseId=${houseId}"  showPager="false" showfooter="false">
              <div property="columns">
                <div field="publisher" width="20"  align="center" headerAlign="center">发布人</div>
   	            <div field="publishDate"  dateFormat="yyyy-MM-dd HH:mm:ss" width="30" headeralign="center" align="left">操作时间</div>
	            <div field="storeName" width="30" allowSort="true" align="center" headerAlign="center">门店</div>
	            <div field="companyName" width="40" headerAlign="center" align="center" >公司</div>   
              </div>
          </div>
        <div id="dg2" class="mini-datagrid" style="height:25%;" url="houseResource/sell/getKeyList.do?houseId=${houseId}"  showPager="false" showfooter="false">
            <div property="columns">
                <div field="publisher" width="20"  align="center" headerAlign="center">钥匙人</div>
                <div field="publishDate"  dateFormat="yyyy-MM-dd HH:mm:ss" width="30" headeralign="center" align="left">操作时间</div>
                <div field="storeName" width="30" allowSort="true" align="center" headerAlign="center">门店</div>
                <div field="companyName" width="40" headerAlign="center" align="center" >公司</div>
            </div>
        </div>
        <div id="dg3" class="mini-datagrid" style="height:25%;" url="houseResource/sell/getCommonList.do?houseId=${houseId}"  showPager="false" showfooter="false">
            <div property="columns">
                <div field="publisher" width="20"  align="center" headerAlign="center">独家人</div>
                <div field="publishDate"  dateFormat="yyyy-MM-dd HH:mm:ss" width="30" headeralign="center" align="left">操作时间</div>
                <div field="storeName" width="30" allowSort="true" align="center" headerAlign="center">门店</div>
                <div field="companyName" width="40" headerAlign="center" align="center" >公司</div>
            </div>
        </div>
        <div id="dg4" class="mini-datagrid" style="height:25%;" url="houseResource/sell/getPictureList.do?houseId=${houseId}"  showPager="false" showfooter="false">
            <div property="columns">
                <div field="publisher" width="20"  align="center" headerAlign="center">实景人</div>
                <div field="publishDate"  dateFormat="yyyy-MM-dd HH:mm:ss" width="30" headeralign="center" align="left">操作时间</div>
                <div field="storeName" width="30" allowSort="true" align="center" headerAlign="center">门店</div>
                <div field="companyName" width="40" headerAlign="center" align="center" >公司</div>
            </div>
        </div>
    </div>
    
</body>
<script type="text/javascript">
		mini.parse();
		var grid = mini.get("dg");
		grid.load();
        var keygrid = mini.get("dg2");
        keygrid.load();
        var commongrid = mini.get("dg3");
        commongrid.load();
        var picgrid = mini.get("dg4");
        picgrid.load();
</script>
</html>
