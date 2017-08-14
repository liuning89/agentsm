<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>父节点列表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
	</head>

	<body style="height:100%;" path="<%=basePath%>">
		<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
			<table style="width:99%;">
				<tr>
					<td>
						<a class="mini-button" id="addbtn" iconCls="icon-add" onclick="lf.menu.parentTreeList.save()">保存</a>
					</td>
					<td id="search">
						<span>名称：</span>
						<input id="appName" name="appName" class="mini-hidden"/>
						<input id="name" name="name" class="mini-textbox" emptyText="名称"/>
						<a class="mini-button" iconCls="icon-search" onclick="lf.menu.parentTreeList.search()">查询</a>
					</td>
				</tr>
			</table>
		</div>
		<ul id="tree2" class="mini-tree" url="<%=basePath%>menu/parentTree.do"
			style="width:300px;height:250px;padding:5px;" showTreeIcon="true"
			textField="text" idField="id" parentField="pid" resultAsTree="false"
			allowSelect="false" enableHotTrack="false" expandOnLoad="true"
			showCheckBox="true" checkRecursive="false">
		</ul>
	</body>
	<script type="text/javascript" src="scripts/pages/menu/parentTreeList.js?v=${version}"></script>
</html>
