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
		<title>查看权限列表</title>
	</head>
	<body>
		<div style="width:100%;">
			<table style="padding-left:5px;padding-top:20px;">
				<tr>
					<td id="search">
						<span>名称：</span>
						<input id="name" name="name" class="mini-textbox" />
						<input name="appName" id="appName" class="mini-hidden" />
						<input name="positionId" id="positionId" class="mini-hidden" />
						<a class="mini-button" iconCls="icon-search" onclick="lf.position.urlList.search1()">查询</a>
					</td>
				</tr>
				<tr>
					<td>
						<div id="dg1" class="mini-datagrid" style="width:550px;height:450px" showCheckBox="true"
							multiSelect="true" url="<%=basePath%>position/getPriFunctions.do" showPager="false">
							<div property="columns">
								<div type="checkcolumn" width="10"></div>
								<div header="ID" field="id" width="10"></div>
								<div header="功能名称" field="name" width="30"></div>
								<div header="URL列表" field="urls" width="50"></div>
							</div>
						</div>
					</td>
					<td style="width:120px;text-align:center;">
						<input type="button" value=">" onclick="lf.position.urlList.add()" style="width:40px;" /><br />
						<input type="button" value=">>" onclick="lf.position.urlList.addAll()" style="width:40px;" /><br />
						<input type="button" value="&lt;&lt;" onclick="lf.position.urlList.removeAll()" style="width:40px;" /><br />
						<input type="button" value="&lt;" onclick="lf.position.urlList.remove()" style="width:40px;" /><br /></td>
					<td>
						<div id="dg2" class="mini-datagrid" style="width:550px;height:450px;" showCheckBox="true"
							multiSelect="true" url="<%=basePath%>position/getPositionPriFunctions.do" showPager="false">
							<div property="columns">
								<div type="checkcolumn" width="10"></div>
								<div header="ID" field="id" width="10"></div>
								<div header="功能名称" field="name" width="30"></div>
								<div header="URL列表" field="urls" width="50"></div>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="text-align:center;padding-top:20px;">
						<a class="mini-button" id="savebtn" iconCls="icon-save" onclick="lf.position.urlList.save()">保存</a>
					</td>
				</tr>
			</table>
		</div>
		</body>
	<script type="text/javascript" src="scripts/pages/position/urlList.js"></script>
</html>