<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>职位列表</title>
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
						<td id="search">
							<span>名称：</span>
							<input id="name" name="name" class="mini-textbox" />
							<!-- <span>组织：</span>
							<input name="areaOrg" id="areaOrg" class="mini-textbox" /> -->
							<span>部门：</span>
							<input name="department" id="department" class="mini-combobox" textField="text" valueField="id" 
								data="[{'id':'','text':'全部'},{'id':'1','text':'业务'},{'id':'2','text':'运营'},{'id':'3','text':'人事'},
								{'id':'4','text':'财务'},{'id':'5','text':'法务'}]" />
							<span>城市：</span> 
							<input id="cityId" name="cityId" class="mini-combobox" textField="text" valueField="id"
								url="<%=path%>/dicAreaNew/getEnableCitySimpleList.action" /> 
							<span>上级岗位：</span>
							<input name="parentName" id="parentName" class="mini-textbox" />
							<a class="mini-button" iconCls="icon-search" onclick="lf.position.list.search()">查询</a>
							<a class="mini-button" iconCls="icon-cancel" onclick="lf.position.list.reset()">重置</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!--列表-->
		<div class="mini-fit" style="height:400px">
			<div id="dg" class="mini-datagrid" sizeList="[20,30,50,100]" pageSize="20" style="height:100%;" url="<%=basePath%>position/list.do"
				multiSelect="true" showfooter="false">
				<div property="columns">
					<div type="checkcolumn" width="10"></div>
					<div field="id" align="left" headeralign="left" width="20">ID</div>
					<div field="name" align="left" headeralign="left" width="50">名称</div>
					<div field="department" align="left" headeralign="left" width="20">部门</div>
					<div field="areaOrg" align="left" headeralign="left" width="50">组织</div>
					<div field="cityName" align="left" headeralign="left" width="30">所在城市</div>
					<div field="memo" align="left" headeralign="left" width="80">备注</div>
					<div field="createTime" align="left" headeralign="left" width="40">创建时间</div>
					<div name="action" headeralign="left" align="left" width="50">操作</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="scripts/pages/position/list.js?v=${version}"></script>
</html>
