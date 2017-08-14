<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>功能列表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
	</head>

	<body style="height:100%;" path="<%=basePath%>">
		<div style="width:100%;">
			<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
				<table style="width:99%;">
					<tr>
						<td>
							<a class="mini-button" id="addbtn" iconCls="icon-add" onclick="lf.menu.functionList.save()">保存</a>
						</td>
						<td id="search">
							<span>名称(或者应用名称)：</span>
							<input id="name" name="name" class="mini-textbox" emptyText="名称或者应用名称"/>
							<a class="mini-button" iconCls="icon-search" onclick="lf.menu.functionList.search()">查询</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!--列表-->
		<div class="mini-fit" style="height:400px">
			<div id="dg" class="mini-datagrid" sizeList="[30]" pageSize=30 style="height:100%;" url="<%=basePath%>priFunction/getUrlFunctionList.do"
				multiSelect="true" showfooter="false">
				<div property="columns">
					<div type="checkcolumn" width="10"></div>
					<div field="id" align="left" headeralign="left" width="20">ID</div>
					<div field="name" align="left" headeralign="left" width="50">名称</div>
					<div field="appName" align="left" headeralign="left" width="50">应用名称</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="scripts/pages/menu/functionList.js?v=${version}"></script>
</html>
