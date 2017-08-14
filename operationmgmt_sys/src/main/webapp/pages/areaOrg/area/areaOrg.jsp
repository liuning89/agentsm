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
		<title>修改组织架构-区域页面</title>
	</head>
	<body>
		<form id="areaOrgUpdateForm" method="post">
			<div style="padding-left:11px;padding-bottom:5px;padding-top:10px;" align="center">
				<table style="table-layout:fixed;padding-top: 15px; padding-left: 25px;">
					<%-- <tr>
						<td>城市：</td>
						<td>
							<input name="cityName" id="cityName" class="mini-textbox" enabled="false" value="${areaOrg.cityName }"/>
							<input name="cityId" id="cityId" class="mini-hidden" value="${areaOrg.cityId }"/>
						</td>
					</tr> --%>
					<tr>
						<td>城市架构：</td>
						<td>
							<input name="cityId" id="cityId" class="mini-hidden" value="${areaOrg.cityId }"/>
							<input name="parentName" id="parentName" class="mini-textbox" enabled="false" value="${areaOrg.parentName }"/>
							<input name="parentId" id="parentId" class="mini-hidden" value="${areaOrg.parentId }"/>
						</td>
					</tr>
					<tr>
						<td>合作伙伴：</td>
						<td>
							<input id="franchiseeId" name="franchiseeId" class="mini-combobox" textField="text" valueField="id" required="true" 
								value="${areaOrg.franchiseeId }"/>
							<input id="oldFranchiseeId" name="oldFranchiseeId" class="mini-hidden" value="${areaOrg.franchiseeId }"/>
						</td>
					</tr>
					<tr>
						<td>名称：</td>
						<td>
							<input name="name" id="name" class="mini-textbox" required="true" value="${areaOrg.name }"/>
							<input name="id" id="id" class="mini-hidden" value="${areaOrg.id }"/>
							<input name="code" id="code" class="mini-hidden" value="${areaOrg.code }"/>
							<input name="oldParentId" id="oldParentId" class="mini-hidden" value="${areaOrg.parentId }"/>
							<input name="oldName" id="oldName" class="mini-hidden" value="${areaOrg.name }"/>
							<input name="level" id="level" class="mini-hidden" value="70"/>
						</td>
						<td>
							<%--<input name="typeCheckBox" id="typeCheckBox" class="mini-checkbox" />--%>
							<%--<label id="typeDescription" style="margin-left:-8px;">是否合作伙伴</label>--%>
							<input name="type" id="type" class="mini-hidden" value="${areaOrg.type }" />
						</td>
					</tr>
					<tr>
						<td>备注：</td>
						<td><input name="memo" id="memo" class="mini-textbox" value="${areaOrg.memo }" /></td>
					</tr>
				</table>
			</div>
			<div style="text-align:center;margin-top: 70px;">
				<a class="mini-button" onclick="lf.areaOrg.area.update.onOk()" style="width:60px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="lf.areaOrg.area.update.onCancel()" style="width:60px;">取消</a>
			</div>
		</form>
	</body>
	<script type="text/javascript" src="scripts/pages/areaOrg/area/update.js"></script>
</html>