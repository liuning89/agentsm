<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
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
						<td>

						</td>


					</tr>
				</table>
			</div>
			<div style="padding-left:35px;padding-bottom:5px;padding-top:10px;">
				<table>
					<tr>
						<td>可选区域
							<div id="dg1" class="mini-datagrid" style="width:250px;height:250px" showCheckBox="true"
								multiSelect="true" showPager="false" >
								<div property="columns">
									<div type="checkcolumn" width="10"></div>
									<div header="ID" field="id" width="10"></div>
									<div header="区域" field="name" width="35"></div>
								</div>
							</div>
						</td>
						<td style="width:120px;text-align:center;">
							<input type="button" value=">" onclick="add()" style="width:40px;" /><br />
							<input type="button" value=">>" onclick="addAll()" style="width:40px;" /><br />
							<input type="button" value="&lt;&lt;" onclick="removeAll()" style="width:40px;" /><br />
							<input type="button" value="&lt;" onclick="remove()" style="width:40px;" /><br /></td>
						<td>已选区域
							<div id="dg2" class="mini-datagrid" style="width:450px;height:250px;" showCheckBox="true"
								multiSelect="true" showPager="false">
								<div property="columns">
									<div type="checkcolumn" width="10"></div>
									<div header="ID" field="id" width="10" style="display: block"></div>
									<div header="区域" field="name" width="35"></div>

								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div style="text-align:center;margin-top: 50px;">
				<a class="mini-button" id="isok" onclick="onOk()" style="width:60px;margin-right:20px;">确定</a>
				<a class="mini-button" onclick="onCance('close')" style="width:60px;">取消</a>
			</div>
		</form>
	</body>
	<script type="text/javascript">
	  mini.parse();
	  var dg1 = mini.get("dg1");
	  var dg2 = mini.get("dg2");
		loadDg1();
	  loadDg2();

	  if(${isupdate} == 1){


		  $("#isok").css("display","none");
	  }else{
		  //$("#isok").css("display","block");
	  }


		function loadDg1() {
//			var districtId = this.districtId.getValue();
//			if (districtId == null || districtId == "") {
//				this.dg1.clearRows();
//				return ;
//			}
			$.ajax({
				method : "post",
				url : "${ctx}/areaorg/getFranchiseeOnlyListByLogin.action?level=70&parentId=${cityId}",
				success : function(result) {
					/* var dg1 = mini.get("dg1"); */
					var datas1 = result;
					var datas2 = mini.get("dg2").getData();
					if (datas2.length > 0) {
						for (var i = 0; i < datas2.length; i++) {
							var data2 = datas2[i];
							for (var j = 0; j < datas1.length; j++) {
								var data1 = datas1[j];
								if (data2.id == data1.id) {
									datas1.splice(j, 1);
									break;
								}
							}
						}
					}
					dg1.setData(datas1);
				}
			});
		}


	  function loadDg2() {
		  $.ajax({
			  method : "post",
		//	  url : "${ctx}/areaorg/getListByLoginIn.action?level=70&parentId=${cityId}&",
			  url : "${ctx}/transferMng/getData2.action?areaId=${areaId}",
			  success : function(result) {
				  /* var dg1 = mini.get("dg1"); */
				  var datas1 = result;
//
				  dg2.setData(datas1);
			  }
		  });
	  }

	  function onOk(){
		  var datas2 = this.dg2.getData();
		  if (datas2.length == 0) {
			  mini.alert("至少选择一个版块！");
			  return ;
		  }
		  var areaId = "";
		  var areaName = ""
		  var area = "";
		  for (var i = 0; i < datas2.length; i++) {
			  var data2 = datas2[i];
			  areaId += (data2.id + "|");
			  areaName += (data2.name + "|");
		  }
		  area = areaId + ";" + areaName;
		  onCance(area);
	  }
	  function add() {
		 /*  var dg1 = mini.get("dg1");
		  var dg2 = mini.get("dg2"); */
		  var rows = dg1.getSelecteds();

		  if (rows.length == 0) {
			  mini.alert("至少选中1行！");
			  return ;
		  }
		  dg1.removeRows(rows);
		  dg2.addRows(rows);
	  }

	  function onCance(area){
		
		  if (window.CloseOwnerWindow){
			  return window.CloseOwnerWindow(area);
		  }
		  else{
			  window.close();
		  }
	  }
	  function onClose(e) {
		  //CloseWindow("cancel");
		  window.CloseOwnerWindow();
	  }
	 
	function addAll(){

		var data1 = dg1.getData();
		if (data1.length == 0) {
			return ;
		}
		dg1.clearRows();
		var data2 = dg2.getData();
		data2 = data2.concat(data1);
		dg2.setData(data2);
	}

		function removeAll(){
			var data2 = dg2.getData();
			if (data2.length == 0) {
				return ;
			}
			var d2 = new Array();
			for (var i = 0; i < data2.length; i++) {
				var data = data2[i];
					d2.push(data2[i]);
			}
			dg2.clearRows();
			if (d2.length == 0) {
				return ;
			}
			var data1 = dg1.getData();
			data1 = data1.concat(d2);
			dg1.setData(data1);
		}
		function remove(){
			var rows = dg2.getSelecteds();
			if (rows.length == 0) {
				mini.alert("至少选中1行！");
				return ;
			}
			dg2.removeRows(rows);
			for (var i = 0; i < rows.length; i++) {
				var row = rows[i];
				dg1.addRow(row);
			}
		}
	</script>
</html>