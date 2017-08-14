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
		<title>添加菜单页面</title>
	</head>
	<body>
		<form id="menuAddForm" method="post">
			<div style="padding-left:11px;padding-bottom:5px;padding-top:10px;" align="center">
				<table style="table-layout:fixed;padding-top: 15px; padding-left: 25px;">
					<tr>
						<td>是否子节点：</td>
						<td><input name="isLeaf" id="isLeaf" class="mini-radiobuttonlist" required="true"
							data="[{'id':'1','text':'是'},{'id':'0','text':'不是'}]" /></td>
					</tr>
					<tr>
						<td>名称：</td>
						<td>
							<input name="name" id="name" class="mini-textbox" required="true"/>
						</td>
					</tr>
					<tr>
						<td>应用名称：</td>
						<td>
							<input name="appName" id="appName" class="mini-combobox" valueField="id" textField="text" required="true"
							url="<%=basePath%>priFunction/app/simpleList.do" value="0"/>
							<!-- <input name="functionName" id="functionName" class="mini-textbox" enabled="false" />
							<input name="appName" id="appName" class="mini-hidden" />
							<input name="functionId" id="functionId" class="mini-hidden" />
							<a class="mini-button" onclick="lf.menu.add.functionList()">绑定</a>
							<a class="mini-button" onclick="lf.menu.add.clearFunction()">解除</a> -->
						</td>
					</tr>
					<tr>
						<td>父级节点：</td>
						<td>
							<input name="parentName" id="parentName" class="mini-textbox" enabled="false" />
							<input name="parentId" id="parentId" class="mini-hidden" required="true" value="0"/>
							<a class="mini-button" onclick="lf.menu.add.parentList()">绑定</a>
							<a class="mini-button" onclick="lf.menu.add.clearParent()">解除</a>
						</td>
					</tr>
					<tr>
						<td>URL：</td>
						<td>
							<input name="url" id="url" class="mini-textbox"/>
						</td>
					</tr>
					<tr>
						<td>排序：</td>
						<td>
							<input name="sort" id="sort" class="mini-textbox" vtype="int" />
						</td>
					</tr>
					<tr>
						<td>备注：</td>
						<td><input name="memo" id="memo" class="mini-textbox" /></td>
					</tr>
				</table>
			</div>
			<div style="text-align:center;margin-top: 50px;">
				<a class="mini-button" onclick="lf.menu.add.onOk()" style="width:60px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="lf.menu.add.onCancel()" style="width:60px;">取消</a>
			</div>
		</form>
	</body>
	<script type="text/javascript" src="scripts/pages/menu/add.js"></script>
</html>