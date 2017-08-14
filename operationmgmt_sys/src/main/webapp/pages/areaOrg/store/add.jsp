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
		<title>添加组织架构-门店页面</title>
	</head>
	<body>
		<form id="areaOrgAddForm" method="post">
			<div style="padding-left:11px;padding-bottom:5px;padding-top:10px;">
				<table style="table-layout:fixed;padding-top: 15px; padding-left: 25px;">
					<tr>
						<td>城市：</td>
						<td>
							<input id="cityAreaOrgId" name="cityAreaOrgId" class="mini-combobox" textField="text" valueField="id" required="true" />
							<input id="cityId" name="cityId" class="mini-hidden" />
						</td>
						<td>业务区域：</td>
						<td>
							<input id="parentId" name="parentId" class="mini-combobox" textField="text" valueField="id" required="true"/>
							<input id="type" name="type" class="mini-hidden" />
						</td>
					</tr>
					<!-- <tr id="tr1">
						<td>区域：</td>
						<td>
							<input id="districtId" name="districtId" class="mini-combobox" textField="text" valueField="id" required="true"/>
						</td>
						<td>板块：</td>
						<td>
							<input id="townId" name="townId" class="mini-combobox" textField="text" valueField="id" required="true"/>
						</td>
						<td>
							<a class="mini-button" iconCls="icon-add" onclick="lf.areaOrg.store.add.addTown(this)"></a>
							<a class="mini-button" iconCls="icon-no" onclick="lf.areaOrg.store.add.deleteTown()"></a>
						</td>
					</tr> -->
					<tr>
						<td>门店：</td>
						<td>
							<input name="name" id="name" class="mini-textbox" required="true"/>
							<input name="level" id="level" class="mini-hidden" value="60"/>
						</td>
						<td>备注：</td>
						<td><input name="memo" id="memo" class="mini-textbox" /></td>
					</tr>
					<tr>
						<td>经度：</td>
						<td>
							<input name="longitude" id="longitude" class="mini-textbox" required="true"/>
						</td>
						<td>纬度：</td>
						<td><input name="latitude" id="latitude" class="mini-textbox" required="true"/></td>
						<td>
							<a class="mini-button" onclick="lf.areaOrg.store.add.openMap()" style="width:60px;margin-right:20px;">地图</a>
						</td>
					</tr>
					<tr>
						<td>行政区：</td>
						<td>
							<input id="districtId" name="districtId" class="mini-combobox" textField="text" valueField="id" required="true"/>
						</td>
					</tr>
				</table>
			</div>
			<div style="padding-left:35px;padding-bottom:5px;padding-top:10px;">
				<table>
					<tr>
						<td>
							<div id="dg1" class="mini-datagrid" style="width:250px;height:250px" showCheckBox="true"
								multiSelect="true" showPager="false" >
								<div property="columns">
									<div type="checkcolumn" width="10"></div>
									<div header="ID" field="townid" width="10"></div>
									<div header="板块" field="town" width="35"></div>
								</div>
							</div>
						</td>
						<td style="width:120px;text-align:center;">
							<input type="button" value=">" onclick="lf.areaOrg.store.add.addTown()" style="width:40px;" /><br />
							<input type="button" value=">>" onclick="lf.areaOrg.store.add.addAllTown()" style="width:40px;" /><br />
							<input type="button" value="&lt;&lt;" onclick="lf.areaOrg.store.add.removeTownAll()" style="width:40px;" /><br />
							<input type="button" value="&lt;" onclick="lf.areaOrg.store.add.removeTown()" style="width:40px;" /><br /></td>
						<td>
							<div id="dg2" class="mini-datagrid" style="width:450px;height:250px;" showCheckBox="true"
								multiSelect="true" showPager="false">
								<div property="columns">
									<div type="checkcolumn" width="10"></div>
									<div header="ID" field="townid" width="10"></div>
									<div header="行政区" field="district" width="35"></div>
									<div header="板块" field="town" width="35"></div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div style="text-align:center;margin-top: 50px;">
				<a class="mini-button" onclick="lf.areaOrg.store.add.onOk()" style="width:60px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="lf.areaOrg.store.add.onCancel()" style="width:60px;">取消</a>
			</div>
		</form>
	</body>
	<script type="text/javascript" src="scripts/pages/areaOrg/store/add.js"></script>
</html>