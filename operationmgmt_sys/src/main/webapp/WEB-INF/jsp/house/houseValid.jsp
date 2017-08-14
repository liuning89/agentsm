<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

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

	   			 <div id="dg" class="mini-datagrid"                     
	                    idField="id"  multiSelect="true" showPager="false" style="height:300;widht:300" url="houseResource/sell/houseValidReasonList.action?houseId=${houseId}"
	                    allowCellEdit="true" allowCellSelect="true">
	                    <div property="columns">
	                       	<div field="agentName"  align="left" headeralign="left" width="10">经纪人名称</div>
                  			<div field="content"  align="left" headeralign="left" width="30">原因</div>
                  			<div field="createTime"  dateFormat="yyyy-MM-dd HH:mm:ss" align="left" headeralign="left" width="15">时间</div>
	                    </div>
                </div>
</body>
<script type="text/javascript">
	mini.parse();
	mini.get("dg").load();
</script>
</html>
