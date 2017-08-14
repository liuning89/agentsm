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
							<a class="mini-button" id="addbtn" iconCls="icon-add" onclick="lf.priFunction.list.addPage()">新增</a>
							<a class="mini-button" id="updatebtn" iconCls="icon-edit" onclick="lf.priFunction.list.updatePage()">修改</a>
							<a class="mini-button" id="deletebtn" iconCls="icon-remove" onclick="lf.priFunction.list.deletePriFunction()">删除</a>
						</td>
						<td id="search">
							<span>名称：</span>
							<input id="name" name="name" class="mini-textbox" textField="text" valueField="id" />
							<span>应用名称：</span>
							<input name="appName" id="appName" class="mini-textbox" textField="text" valueField="id" />
							<a class="mini-button" iconCls="icon-search" onclick="lf.priFunction.list.search()">查询</a>
							<a class="mini-button" iconCls="icon-cancel" onclick="lf.priFunction.list.reset()">重置</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!--列表-->
		<div class="mini-fit" style="height:400px">
			<div id="dg" class="mini-datagrid" sizeList="[20,30,50,100]" pageSize="20" style="height:100%;" url="<%=basePath%>priFunction/list.do"
				multiSelect="true" showfooter="false">
				<div property="columns">
					<div type="checkcolumn" width="10"></div>
					<div field="id" align="left" headeralign="left" width="20">ID</div>
					<div field="name" align="left" headeralign="left" width="50">名称</div>
					<div field="appName" align="left" headeralign="left" width="50">应用</div>
					<div field="memo" align="left" headeralign="left" width="80">备注</div>
					<div field="createTime" align="left" headeralign="left" width="40">创建时间</div>
					<div field="type" align="left" headeralign="left" width="30">类型</div>
					<div name="action" headeralign="left" align="left" width="50">操作</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="scripts/pages/priFunction/list.js?v=${version}"></script>
</html>
