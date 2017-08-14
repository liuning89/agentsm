<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>菜单列表</title>
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
							<a class="mini-button" id="addbtn" iconCls="icon-add" onclick="lf.menu.list.addPage()">新增</a>
							<a class="mini-button" id="updatebtn" iconCls="icon-edit" onclick="lf.menu.list.updatePage()">修改</a>
							<a class="mini-button" id="deletebtn" iconCls="icon-remove" onclick="lf.menu.list.deletePriFunction()">删除</a>
						</td>
						<td id="search">
							<span>名称(或者URL)：</span>
							<input id="name" name="name" class="mini-textbox" emptyText="名称或者URL"/>
							<span>上级名称(或者URL)：</span>
							<input name="parentName" id="parentName" class="mini-textbox" emptyText="上级名称或者URL"/>
							<span>功能名称：</span>
							<input name="functionName" id="functionName" class="mini-textbox" emptyText="功能名称"/>
							<span>是否子节点：</span>
							<input id="isLeaf" name="isLeaf" class="mini-combobox" textField="text" valueField="id" 
								data="[{'id':'','text':'全部'},{'id':'1','text':'是'},{'id':'0','text':'不是'}]" />
							<a class="mini-button" iconCls="icon-search" onclick="lf.menu.list.search()">查询</a>
							<a class="mini-button" iconCls="icon-cancel" onclick="lf.menu.list.reset()">重置</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!--列表-->
		<div class="mini-fit" style="height:400px">
			<div id="dg" class="mini-datagrid" sizeList="[20,30,50,100]" pageSize="20" style="height:100%;" url="<%=basePath%>menu/list.do"
				multiSelect="true" showfooter="false">
				<div property="columns">
					<div type="checkcolumn" width="10"></div>
					<div field="id" align="left" headeralign="left" width="20">ID</div>
					<div field="name" align="left" headeralign="left" width="35">名称</div>
					<div field="parentName" align="left" headeralign="left" width="35">上级名称</div>
					<div field="functionName" align="left" headeralign="left" width="50">功能名称</div>
					<div field="url" align="left" headeralign="left" width="70">URL</div>
					<div field="isLeaf" align="left" headeralign="left" width="20">是否子节点</div>
					<div field="sort" align="sort" headeralign="left" width="20">排序</div>
					<div field="memo" align="left" headeralign="left" width="70">备注</div>
					<div field="createTime" align="left" headeralign="left" width="40">创建时间</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="scripts/pages/menu/list.js?v=${version}"></script>
</html>
