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
		<title>修改菜单页面</title>
	</head>
	<body>
		<form id="menuUpdateForm" method="post">
			<div style="padding-left:11px;padding-bottom:5px;padding-top:10px;" align="center">
				<table style="table-layout:fixed;padding-top: 15px; padding-left: 25px;">
					<tr>
						<td>是否子节点：</td>
						<td>
							<input name="isLeafText" id="isLeafText" class="mini-textbox" enabled="false" value="${menu.isLeaf == 1 ? '是' : '否' }"/>
							<input name="isLeaf" id="isLeaf" class="mini-hidden" value="${menu.isLeaf }"/>
						</td>
					</tr>
					<tr>
						<td>名称：</td>
						<td>
							<input name="id" id="id" class="mini-hidden" value="${menu.id }"/>
							<input name="name" id="name" class="mini-textbox" required="true" value="${menu.name }"/>
						</td>
					</tr>
					<tr>
						<td>应用名称：</td>
						<td>
							<input name="appName" id="appName" class="mini-textbox" enabled="false" value="${menu.appName }"/>
							<%-- <input name="functionName" id="functionName" class="mini-textbox" enabled="false" value="${menu.functionName }" /> --%>
							<!--<input name="functionId" id="functionId" class="mini-hidden" value="${menu.functionId }" />
							 <a class="mini-button" onclick="lf.menu.add.functionList()">绑定</a>
							<a class="mini-button" onclick="lf.menu.add.clearFunction()">解除</a> -->
						</td>
					</tr>
					<tr>
						<td>父级节点：</td>
						<td>
							<input name="parentName" id="parentName" class="mini-textbox" enabled="false" value="${menu.parentName }" />
								<!--<input name="parentId" id="parentId" class="mini-hidden" required="true" value="${menu.parentId }"/>
						 	<a class="mini-button" onclick="lf.menu.add.parentList()">绑定</a>
							<a class="mini-button" onclick="lf.menu.add.clearParent()">解除</a> -->
						</td>
					</tr>
					<tr>
						<td>URL：</td>
						<td>
							<input name="url" id="url" class="mini-textbox" value="${menu.url }" />
						</td>
					</tr>
					<tr>
						<td>排序：</td>
						<td>
							<input name="sort" id="sort" class="mini-textbox" vtype="int" value="${menu.sort }"/>
						</td>
					</tr>
					<tr>
						<td>备注：</td>
						<td><input name="memo" id="memo" class="mini-textbox" value="${menu.memo }" /></td>
					</tr>
				</table>
			</div>
			<div style="text-align:center;margin-top: 50px;">
				<a class="mini-button" onclick="lf.menu.update.onOk()" style="width:60px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="lf.menu.update.onCancel()" style="width:60px;">取消</a>
			</div>
		</form>
	</body>
	<script type="text/javascript" src="scripts/pages/menu/update.js"></script>
</html>