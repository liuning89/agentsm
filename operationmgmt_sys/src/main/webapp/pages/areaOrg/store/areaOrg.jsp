<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<title>修改组织架构-门店页面</title>
	</head>
	<body>
		<form id="areaOrgUpdateForm" method="post">
			<div style="padding-left:11px;padding-bottom:5px;padding-top:10px;" >
				<table style="table-layout:fixed;padding-top: 15px; padding-left: 25px;">
					<tr>
						<td>城市：</td>
						<td>
							<input name="cityAreaOrg" id="cityAreaOrg" class="mini-textbox" enabled="false" value="${areaOrg.cityAreaOrg }"/>
							<input name="cityAreaOrgId" id="cityAreaOrgId" class="mini-hidden" value="${areaOrg.cityAreaOrgId }"/>
						</td>
						<td>业务区域：</td>
						<td><input id="parentId" name="parentId" class="mini-combobox" textField="text" valueField="id" required="true"
								value="${areaOrg.parentId }" /></td>
					</tr>
					<tr>
						<td>门店：</td>
						<td>
							<input name="name" id="name" class="mini-textbox" required="true" value="${areaOrg.name }"/>
							<input name="id" id="id" class="mini-hidden" value="${areaOrg.id }"/>
							<input name="cityId" id="cityId" class="mini-hidden" value="${areaOrg.cityId }"/>
							<input name="code" id="code" class="mini-hidden" value="${areaOrg.code }"/>
							<input name="oldParentId" id="oldParentId" class="mini-hidden" value="${areaOrg.parentId }"/>
							<input name="oldName" id="oldName" class="mini-hidden" value="${areaOrg.name }"/>
							<input name="level" id="level" class="mini-hidden" value="60"/>
							<input id="type" name="type" class="mini-hidden" value="${areaOrg.type }" />
						</td>
						<td>备注：</td>
						<td><input name="memo" id="memo" class="mini-textbox" value="${areaOrg.memo }" /></td>
					</tr>
					<tr>
						<td>经度：</td>
						<td>
							<input name="longitude" id="longitude" class="mini-textbox" required="true" value="${areaOrg.longitude }"/>
						</td>
						<td>纬度：</td>
						<td><input name="latitude" id="latitude" class="mini-textbox" required="true" value="${areaOrg.latitude }"/></td>
						<td>
							<a class="mini-button" onclick="lf.areaOrg.store.update.openMap()" style="width:60px;margin-right:20px;">地图</a>
						</td>
					</tr>
					<tr>
						<td>行政区：</td>
						<td>
							<input id="districtId" name="districtId" class="mini-combobox" textField="text" valueField="id"
								url="<%=path%>/dicAreaNew/getDicAreaNewByParentId.action?parentId=${areaOrg.cityId }" />
						</td>
					</tr>
					<%-- <tr>
						<td>板块：</td>
						<td>
							<input id="townId" name="townId" class="mini-combobox" textField="text" valueField="id" required="true"
								value="${areaOrg.townId }"/>
						</td>
					</tr> --%>
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
								<input type="button" value=">" onclick="lf.areaOrg.store.update.addTown()" style="width:40px;" /><br />
								<input type="button" value=">>" onclick="lf.areaOrg.store.update.addAllTown()" style="width:40px;" /><br />
								<input type="button" value="&lt;&lt;" onclick="lf.areaOrg.store.update.removeTownAll()" style="width:40px;" /><br />
								<input type="button" value="&lt;" onclick="lf.areaOrg.store.update.removeTown()" style="width:40px;" /><br /></td>
							<td>
							<input id="towns" names="towns" class="mini-hidden" value='
								<c:if test="${not empty towns and towns.size() > 0 }">
									[
										<c:forEach var="item" items="${towns }" varStatus="sta">
											{"townid":${item.townid },"district":"${item.district }","town":"${item.town }","districtid":${item.districtid }}
											<c:if test="${not sta.last }">
												,
											</c:if>
										</c:forEach>
									]
								</c:if>' />
							<div id="dg2" class="mini-datagrid" style="width:450px;height:250px;" showCheckBox="true"
								multiSelect="true" showPager="false" >
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
				<a class="mini-button" onclick="lf.areaOrg.store.update.onOk()" style="width:60px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="lf.areaOrg.store.update.onCancel()" style="width:60px;">取消</a>
			</div>
		</form>
	</body>
	<script type="text/javascript" src="scripts/pages/areaOrg/store/update.js"></script>
</html>