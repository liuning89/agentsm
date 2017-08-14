<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String houseId = request.getParameter("houseId");

%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <title>售房列表查询</title>
    <script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
</head>
<body style="height:100%;">
    <!--列表-->
    
    <div class="mini-fit" style="height:400px">
          <div id="dg" class="mini-datagrid" sizeList="[20,30,50,100]" pageSize=20 style="height:100%;" url="travelallow/lookUpdateRecordList.action?id=${id}&type=3" multiSelect="true" showfooter="false">
              <div property="columns">
                  <div field="agentName"  align="left" headeralign="left" width="20">修改人</div>
                  <div field="beforeValue"  align="left" headeralign="left" width="20">修改前</div>
                  <div field="afterValue"  align="left" headeralign="left" width="20">修改后</div>
                   <div field="content"  align="left" headeralign="left" width="20">说明</div>
                  <div field="createTime"  dateFormat="yyyy-MM-dd HH:mm:ss" align="left" headeralign="left" width="40">修改时间</div>
              </div>
          </div>
    </div>
</body>
<script type="text/javascript">
	mini.parse();
	var grid = mini.get("dg");
	grid.load();
	grid.on("drawcell", function(e) {
		var record = e.record, field = e.field, value = e.value, column = e.column;
		
		if (field == "content") {
            if(value=="状态" || value =="修改状态"){
                    e.cellHtml = value+"(0:关闭，1:开启)";
            }
    	}
		
	});
</script>
</html>
