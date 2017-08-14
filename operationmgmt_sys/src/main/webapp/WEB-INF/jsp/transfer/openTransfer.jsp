<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>经纪人资源转入</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<script src="${ctx}/scripts/miniui/miniui.js" type="text/javascript"></script>
<script src="${ctx}/scripts/boot.js?v=${version}" type="text/javascript"></script>
<style type="text/css">
html,body {
	width: 100%;
	height: 100%;
	border: 0;
	margin: 0;
	padding: 0;
	overflow: visible;
}
</style>
</head>
<body>
	<div style="padding-bottom: 5px;">
		<span style="font-size: 20px;">转入</span> <br /> <span>城市<span
			style="color: red">*</span></span>
		<!--  <input name="cityId" id="cityId" class="mini-combobox"  textField="text" valueField="id" data="[{id:0,text:'全国'}]" value="0"/> -->

		<input id="cityId" class="mini-combobox" style="width: 135px;"
			textField="name" valueField="id"
			url="${ctx}/areaorg/getFranchiseeListByLogin.action?level=90" value=""
			showNullItem="true" allowInput="true"
			onvalidation="onComboValidation" onvaluechanged="onCityChanged"
			emptyText="请选择" nullItemText="请选择" /> 
			<span>区域/合作伙伴<span style="color: red">*</span></span>
		<!-- <input name="joinId" id="joinId" class="mini-combobox"  textField="text" valueField="id" data="[{id:0,text:''}]" value="0"/> -->
		<input id="areaId" class="mini-combobox" style="width: 135px;"
			textField="name" valueField="id"
			url="${ctx}/areaorg/getListByLoginIn.action?level=1" value=""
			showNullItem="true" allowInput="true"
			onvalidation="onComboValidation" onvaluechanged="onAreaChanged"
			emptyText="请选择" nullItemText="请选择" /> <br /> 
		<span>门店&nbsp&nbsp</span>
		<input id="storeId" class="mini-combobox" style="width: 135px;"
			textField="name" valueField="id"
			url="${ctx}/areaorg/getListByLoginIn.action?level=1" value=""
			showNullItem="true" allowInput="true"
			onvalidation="onComboValidation" emptyText="请选择" nullItemText="请选择" />


		<input type="button" value="转入" onclick="onSaveData()" /> <br />
		<h1>
			<strong>转入资源类型</strong>
		</h1>
		<div id="type" class="mini-checkboxlist" repeatItems="0"
			repeatLayout="table" textField="text" valueField="id" value="1,2,3,4,5,6,7"
			url="${ctx}/txt/transfer.txt"></div>
	</div>

	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("datagrid1");

		//////////////////////////////////////////////
		function onComboValidation(e) {
			var items = this.findItems(e.value);
			if (!items || items.length == 0) {
				this.setValue('');
			}
		}
		//城市区域下啦选择
		function onCityChanged(e) {
			var cityCbo = mini.get("cityId");
			var areaCbo = mini.get("areaId");
			var id = cityCbo.getValue();

			areaCbo.select(0);
			var url = "${ctx}/areaorg/getListByLoginIn.action?level=70";
			if (id != null && id != "") {
				url += "&parentId=" + id;
			}
			areaCbo.setUrl(url);
			areaCbo.select(0);

			var storeCbo = mini.get("storeId");
			storeCbo.setUrl("${ctx}/areaorg/getListByLoginIn.action?level=1");
			storeCbo.select(0);
		}

		function onAreaChanged(e) {
			var areaCbo = mini.get("areaId");
			var storeCbo = mini.get("storeId");
			var id = areaCbo.getValue();
			storeCbo.select(0);
			var url = "${ctx}/areaorg/getListByLoginIn.action?level=60";
			if (id != null && id != "") {
				url += "&parentId=" + id;
			}
			storeCbo.setUrl(url);
			storeCbo.select(0);

		}

		function onStoreChange(e) {
			var storeCbo = mini.get("storeId");
			var newAgentCbo = mini.get("newAgentId");
			var id = storeCbo.getValue();
			newAgentCbo.select(0);
			var url = "${ctx}/agent/getAgentByStoreOrg.action";
			if (id != null && id != "") {
				url += "?orgCode=" + id;
			}
			newAgentCbo.setUrl(url);
			newAgentCbo.select(0);
		}

		/* ***************************资源转移******************************* */
		
		function onSaveData() {
			var cityId = mini.get("cityId").getFormValue();
			if (cityId == "") {
				mini.alert("请选择城市");
				return false;
			}
			var areaId = mini.get("areaId").getFormValue();
			if (areaId == "") {
				mini.alert("请选择合作伙伴");
				return false;
			}
			
			var type = mini.get("type").getFormValue();
			if(type == ""){
				mini.alert("请选择转入类型");
				return false;
			}
			if(mini.get("areaId").getValue() == "${areaIdIn}"){
				mini.alert("自己不能转给自己!");
				return false;
			}
		
			lc.mask("正在处理中,请稍后...");
			$.ajax({
				url : "${ctx}/transferMng/saveResouce.action",
				cache : false,
				dataType : 'text',
				data : {
					
					cityId : mini.get("cityId").getFormValue(),
					areaId : mini.get("areaId").getValue(),
					storeId : mini.get("storeId").getValue(),
					
					cityIdIn : "${cityIdIn}",
					areaIdIn : "${areaIdIn}",
					storeIdIn : "${storeIdIn}",
					
					type:type
				},
				type : "POST",
				success : function(d) {
					if (d == 1) {
						lc.unmask();
						mini.alert("转移成功!", "成功", function() {
							
							window.CloseOwnerWindow();
						});
					} else if(d == 3){
						lc.unmask();
						mini.alert("所选择区域没有经纪人,请重新选择区域/合作伙伴!", "警告", function() {

						});
					}else if(d == 4){
						lc.unmask();
						mini.alert("你转出的合作伙伴与转入的合作伙伴没有对应的板块或没有需要转移的资源", "警告", function() {

						});
					}else if(d == 5){
						lc.unmask();
						mini.alert("您选择转入合作伙伴对应的板块中没有经纪人!", "警告", function() {

						});
					}else if(d == 6){
						lc.unmask();
						mini.alert("您选择转入合作伙伴没有对应的板块!", "警告", function() {

						});
					}
					else{
						lc.unmask();
						mini.alert("转移失败,请稍后重试!", "错误", function() {

						});
					}
				}
			});
		}

	</script>
	<div class="description">
		<%-- <jsp:include page="../common/footer.jsp"></jsp:include> --%>
	</div>
</body>
</html>