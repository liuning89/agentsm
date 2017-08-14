<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>组织架构-门店列表</title>
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
							<a class="mini-button" id="addbtn" iconCls="icon-add" onclick="lf.areaOrg.store.list.addPage()">新增</a>
							<a class="mini-button" id="updatebtn" iconCls="icon-edit" onclick="lf.areaOrg.store.list.updatePage()">修改</a>
							<a class="mini-button" id="deletebtn" iconCls="icon-remove" onclick="lf.areaOrg.store.list.deleteAreaOrg()">删除</a>
						</td>
						<td id="search">
							<span>城市：</span>
							<input id="cityId" name="cityId" class="mini-combobox" textField="text" valueField="id" 
								url="<%=path%>/dicAreaNew/getEnableCityUsedArea.action"/>
							<span>区域：</span>
							<input id="districtId" name="districtId" class="mini-combobox" textField="text" valueField="id" />
							<span>板块：</span>
							<input id="townId" name="townId" class="mini-combobox" textField="text" valueField="id" />
							<span>业务区域：</span>
							<input id="areaName" name="areaName" class="mini-textbox" emptyText="业务区域"/>
							<span>门店：</span>
							<input id="name" name="name" class="mini-textbox" emptyText="门店"/>
							<a class="mini-button" iconCls="icon-search" onclick="lf.areaOrg.store.list.search()">查询</a>
							<a class="mini-button" iconCls="icon-cancel" onclick="lf.areaOrg.store.list.reset()">重置</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!--列表-->
		<div class="mini-fit" style="height:400px">
			<div id="dg" class="mini-datagrid" sizeList="[20,30,50,100]" pageSize="20" style="height:100%;" url="<%=basePath%>areaOrg/store/list.do"
				multiSelect="true" showfooter="false">
				<div property="columns">
					<div type="checkcolumn" width="10"></div>
					<div field="id" align="left" headeralign="left" width="20">ID</div>
					<div field="townNames" align="left" headeralign="left" width="55">管辖板块</div>
					<div field="areaName" align="left" headeralign="left" width="35">业务区域</div>
					<div field="name" align="left" headeralign="left" width="35">门店</div>
					<div field="longitude_latitude" align="left" headeralign="left" width="35">经纬度</div>
					<div field="memo" align="left" headeralign="left" width="60">备注</div>
					<div field="createTime" align="left" headeralign="left" width="40">创建时间</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="scripts/pages/areaOrg/store/list.js?v=${version}"></script>
</html>
