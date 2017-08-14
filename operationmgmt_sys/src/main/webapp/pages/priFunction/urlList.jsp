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
		<title>查看URL</title>
	</head>
	<body>
		<div style="width:100%;">
			<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
				<table style="width:100%;">
					<tr>
						<td style="width:100%;">
							<a class="mini-button" iconCls="icon-add" onclick="lf.priFunction.urlList.add()" plain="true" tooltip="增加...">增加</a>
							<a class="mini-button" iconCls="icon-remove" onclick="lf.priFunction.urlList.deleteUrl()" plain="true">删除</a>
							<span class="separator"></span>
							<a class="mini-button" iconCls="icon-save" onclick="lf.priFunction.urlList.save()" plain="true">保存</a>
						</td>
						<td style="white-space:nowrap;" id="search">
							<input id="functionId" class="mini-hidden" />
							<input id="name" class="mini-textbox" emptyText="请输入名称或者url" style="width:150px;" onenter="onKeyEnter" />
							<a class="mini-button" onclick="lf.priFunction.urlList.search()">查询</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div id="dg" class="mini-datagrid" url="<%=basePath%>priFunction/url/list.do" 
			idField="id" allowResize="true" allowCellEdit="true" allowCellSelect="true" multiSelect="true" 
			editNextOnEnterKey="true" editNextRowCell="true" showPager="false">
			<div property="columns">
				<div type="checkcolumn" width="10"></div>
				<div name="id" field="id" headerAlign="center" allowSort="true" width="10">
					编号
				</div>
				<div name="name" field="name" headerAlign="center" allowSort="true" width="20">
					URL名称 <input property="editor" class="mini-textbox" style="width:100%;" />
				</div>
				<div field="url" width="30" allowSort="true" name="url" headerAlign="center" >
					URL<input property="editor" class="mini-textbox" style="width:100%;" />
				</div>
				<div field="memo" width="30" headerAlign="center" allowSort="true" name="memo">
					备注 <input property="editor" class="mini-textbox" style="width:100%;" />
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="scripts/pages/priFunction/urlList.js"></script>
</html>