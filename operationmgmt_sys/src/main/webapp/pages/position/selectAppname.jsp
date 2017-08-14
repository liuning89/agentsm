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
		<title>选择应用</title>
	</head>
	<body>
		<div style="padding-top:10px;padding-left:20px;">
			<table style="padding-bottom:10px;">
				<tr>
					<td>
						<span>应用：</span>
						<input id="appName" name="appName" class="mini-combobox" valueField="text" />
						<input name="positionId" id="positionId" class="mini-hidden" />
						<input name="positionName" id="positionName" class="mini-hidden" />
						<input name="type" id="type" class="mini-hidden" />
					</td>
				</tr>
			</table>
			<a class="mini-button" id="savebtn" iconCls="icon-ok" onclick="save()">确定</a>
		</div>
	</body>
	<script type="text/javascript">
		function SetData(positionId, positionName, apps, type) {
			mini.get("positionId").setValue(positionId);
			mini.get("positionName").setValue(positionName);
			mini.get("type").setValue(type);
			var app="[{id:'',text:'请选择'},";
			for (var i = 0; i < apps.length; i++) {
				var s = "{id:'"+apps[i]+"',text:'" + apps[i] + "'}";
				app+=s;
				if (i != apps.length - 1) {
					app += ",";
				}
			}
			app+="]";
			mini.get("appName").setData(app);
			mini.get("appName").select(0);
		}
		function save() {
			var a = mini.get("appName").getValue();
			if (a == "请选择") {
				mini.alert("请选择应用！");
				return ;
			}
			var appName = encodeURI(encodeURI(a));
			if (type == 1) {
				mini.open({
					url : "pages/position/urlList.jsp?positionId="+mini.get("positionId").getValue()
						+"&appName="+appName,
					title : "查看{" + appName + "}{" + mini.get("positionName").getValue() + "}权限列表",
					width : 1200,
					height : 650,
					ondestroy : function(action) {
						if(action == "save"){
							lf.position.list.search();
						}
					}
				});
			} else {
				mini.open({
					url : "pages/position/menuList.jsp?positionId="+mini.get("positionId").getValue()
						+"&appName="+appName,
					title : "查看{"+ appName + "}{" + mini.get("positionName").getValue() + "}菜单列表",
					width : 350,
					height : 450,
					ondestroy : function(action) {
						if(action == "save"){
							lf.position.list.search();
						}
					}
				});
			}
			CloseWindow("save");
		}
		function CloseWindow(action) {
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}
	</script>
</html>