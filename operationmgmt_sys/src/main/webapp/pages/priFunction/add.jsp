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
		<title>添加功能页面</title>
	</head>
	<body>
		<form id="priFunctionAddForm" method="post">
			<div style="padding-left:11px;padding-bottom:5px;padding-top:10px;" align="center">
				<table style="table-layout:fixed;padding-top: 15px; padding-left: 25px;">
					<tr>
						<td>应用：</td>
						<td><input name="appName" id="appName" class="mini-combobox" valueField="id" textField="text" required="true"
							url="<%=basePath%>priFunction/app/simpleList.do" value="0"/></td>
					</tr>
					<tr>
						<td>名称：</td>
						<td>
							<input name="name" id="name" class="mini-textbox" required="true"/>
							<input name="type" id="type" class="mini-hidden" value="2"/>
						</td>
					</tr>
					<tr>
						<td>备注：</td>
						<td><input name="memo" id="memo" class="mini-textbox" /></td>
					</tr>
				</table>
			</div>
			<div style="text-align:center;margin-top: 50px;">
				<a class="mini-button" onclick="lf.priFunction.add.onOk()" style="width:60px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="lf.priFunction.add.onCancel()" style="width:60px;">取消</a>
			</div>
		</form>
	</body>
	<script type="text/javascript" src="scripts/pages/priFunction/add.js"></script>
</html>