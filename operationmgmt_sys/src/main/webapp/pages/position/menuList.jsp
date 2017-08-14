<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
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
		<title>查看菜单列表</title>
	</head>
	<body style="height:100%;" path="<%=basePath%>">
		<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
			<table style="width:99%;">
				<tr>
					<td>
						<a class="mini-button" id="addbtn" iconCls="icon-add" onclick="lf.position.menuTreeList.save()">保存</a>
					</td>
					<td id="search">
						<input name="appName" id="appName" class="mini-hidden" />
						<input name="positionId" id="positionId" class="mini-hidden" />
					</td>
				</tr>
			</table>
		</div>
		<ul id="tree" class="mini-tree" style="width:300px;height:250px;padding:5px;" showTreeIcon="true"
			textField="text" idField="id" parentField="pid" resultAsTree="false"
			enableHotTrack="false" expandOnLoad="true"
			showCheckBox="true" checkRecursive="false" autoCheckParent="true">
		</ul>
	</body>
	<script type="text/javascript" src="scripts/pages/position/menuTreeList.js?v=${version}"></script>
</html>