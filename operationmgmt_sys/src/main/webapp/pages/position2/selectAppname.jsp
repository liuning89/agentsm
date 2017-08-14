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
						<input id="appName" name="appName" class="mini-combobox" valueField="id" textField="text" />
						<input name="cityId" id="cityId" class="mini-hidden" />
						<input name="department" id="department" class="mini-hidden" />
						<input name="level" id="level" class="mini-hidden" />
						<input name="name" id="name" class="mini-hidden" />
						<input name="type" id="type" class="mini-hidden" />
					</td>
				</tr>
			</table>
			<a class="mini-button" id="savebtn" iconCls="icon-ok" onclick="save()">确定</a>
		</div>
	</body>
	<script type="text/javascript">
		function SetData(apps, type, level, department, cityId, name) {
			mini.get("level").setValue(level);
			mini.get("department").setValue(department);
			mini.get("cityId").setValue(cityId);
			mini.get("name").setValue(name);
			mini.get("type").setValue(type);
			var app="[{id:'0',text:'请选择'},";
			for (var i = 0; i < apps.length; i++) {
				var s = "{id:'"+apps[i].id+"',text:'" + apps[i].text + "'}";
				app+=s;
				if (i != apps.length - 1) {
					app += ",";
				}
			}
			app+="]";
			mini.get("appName").setData(app);
			mini.get("appName").select(0);
			console.log(apps);
		}
		function save() {
			var a = mini.get("appName");
			if (a == "" || a == "0") {
				mini.alert("请选择应用！");
				return ;
			}
			// var appName = encodeURI(encodeURI(a));
			if (mini.get("type").getValue() == 1) {
				mini.open({
					url : "pages/position2/urlList.jsp?cityId="+mini.get("cityId").getValue()
						+"&appName="+a.getValue()+"&department="+mini.get("department").getValue()
						+"&level="+mini.get("level").getValue(),
					title : "查看{" + a.text + "}{" + mini.get("name").getValue() + "}权限列表",
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
					url : "pages/position2/menuList.jsp?cityId="+mini.get("cityId").getValue()
						+"&appName="+a.getValue()+"&department="+mini.get("department").getValue()
						+"&level="+mini.get("level").getValue(),
					title : "查看{"+ a.text + "}{" + mini.get("name").getValue() + "}菜单列表",
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