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
		<title>添加组织架构-区域页面</title>
	</head>
	<body>
		<form id="areaOrgAddForm" method="post">
			<div style="padding-left:11px;padding-bottom:5px;padding-top:10px;" align="center">
				<table style="table-layout:fixed;padding-top: 15px; padding-left: 25px;">
					<%-- <tr>
						<td>城市：</td>
						<td><input id="cityId" name="cityId" class="mini-combobox" textField="text" valueField="id" 
								url="<%=path%>/dicAreaNew/getEnableCitySimpleList.action" required="true"/></td>
					</tr> --%>
					<tr>
						<td>城市架构：</td>
						<td>
							<input id="parentId" name="parentId" class="mini-combobox" textField="text" valueField="id" required="true"/>
							<input id="cityId" name="cityId" class="mini-hidden" />
						</td>
					</tr>
					<tr>
						<td>合作伙伴：</td>
						<td>
							<input id="franchiseeId" name="franchiseeId" class="mini-combobox" textField="text" valueField="id" required="true"/>
						</td>
					</tr>
					<tr>
						<td>名称：</td>
						<td>
							<input name="name" id="name" class="mini-textbox" required="true"/>
							<input name="level" id="level" class="mini-hidden" value="70"/>
						</td>
						<td>
							<%--<input name="typeCheckBox" id="typeCheckBox" class="mini-checkbox" />--%>
							<%--<label id="typeDescription" style="margin-left:-8px;margin-top:1px;">是否合作伙伴</label>--%>
							<input name="type" id="type" class="mini-hidden" />
						</td>
					</tr>
					<tr>
						<td>备注：</td>
						<td><input name="memo" id="memo" class="mini-textbox" /></td>
					</tr>
				</table>
			</div>
			<div style="text-align:center;margin-top: 70px;">
				<a class="mini-button" onclick="lf.areaOrg.area.add.onOk()" style="width:60px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="lf.areaOrg.area.add.onCancel()" style="width:60px;">取消</a>
			</div>
		</form>
	</body>
	<script type="text/javascript" src="scripts/pages/areaOrg/area/add.js"></script>
</html>