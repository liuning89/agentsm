<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>菜单列表</title>
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
	                        <a class="mini-button" iconCls="icon-redo" onclick="exportData()">导出</a>
	                 </td> 
	                </tr>
	            <tr>     
                    <td id="search">
                    	<span>城市：</span>
						<input id="cityId" name="cityId" class="mini-combobox" nullItemText="请选择..." allowInput="true" url="<%=path%>/dicAreaNew/getEnableCitySimpleList.action" emptyText="请选择城市"   textField="text" valueField="id" onvaluechanged="onCityChanged" />
                        <span>所属区域：</span> 
                        <input name="districtId" id="districtId" showNullItem="true" allowInput="true" nullItemText="请选择..." class="mini-combobox"  onvaluechanged="onDicChanged"  value="" emptyText="请选择区域"  textField="text" valueField="id" /> 
                        <span>所属板块：</span>
                        <input name="townId" id="townId" showNullItem="true" allowInput="true" nullItemText="请选择..." class="mini-combobox"  value="" emptyText="请选择板块"  textField="text" valueField="id" />
                    	<span>小区名称：</span>
                        <input id="estateName" name="estateName"class="mini-textbox" emptyText="请输入小区名称或简称" style="width:150px;" onenter="onKeyEnter"/>
						<!-- <span>日期：</span>
						<input id="startTime" style="width:120px" name="startTime" class="mini-datepicker"  format="yyyy-MM-dd" showOkButton="true" showClearButton="false"/>-<input name="endTime" id="endTime" allowInput="false" class="mini-datepicker" format="yyyy-MM-dd" showOkButton="true" style="width:120px"  showClearButton="false"/>
 -->						<a class="mini-button" id="searchBtn" onclick="search()" iconCls="icon-search">查询</a>
                        <a class="mini-button" id="resetBtn" onclick="refresh()" iconCls="icon-cancel">重置</a>
                    </td>
                </tr>
            </table>           
        </div>
    </div>
		<!--列表-->
		<div class="mini-fit" style="height:400px">
			<div id="dg" class="mini-datagrid" sizeList="[20,30,50,100]" pageSize="100" style="height:100%;" url="<%=basePath%>report/getData.do"
				multiSelect="true" showfooter="false">
				<div property="columns">
					<div field="cityName" align="left" headeralign="left" width="35">城市</div>
					<div field="dicName" align="left" headeralign="left" width="35">区域</div>
					<div field="townName" align="left" headeralign="left" width="50">板块</div>
					<div field="estateName" align="left" headeralign="left" width="70">小区</div>
					<div field="totalScore" align="left" headeralign="left" width="40">分数</div>
					<div field="yxkcNum" align="left" headeralign="left" width="40">有效库存数</div>
					<div field="llNum" align="sort" headeralign="left" width="50">浏览数</div>
					<div field="sjNum" align="left" headeralign="left" width="40">实景数</div>
					<div field="sxNum" align="left" headeralign="left" width="40">速销数</div>
					<div field="ysNum" align="left" headeralign="left" width="40">钥匙数</div>
					<div field="msNum" align="left" headeralign="left" width="40">描述数</div>
					<div field="dtNum" align="left" headeralign="left" width="40">店推数</div>
					<div field="openHouse" align="left" headeralign="left" width="40">openHouse</div>
					<div field="dkNum" align="left" headeralign="left" width="40">带看数</div>
					<div field="gjNum" align="left" headeralign="left" width="40">跟进数</div>
					<div field="scNum" align="left" headeralign="left" width="40">收藏数</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" >
		mini.parse(); 
		var grid = mini.get("dg");
		grid.load();
		function search() {
			var form = new mini.Form("#search");
			var data = form.getData(true);
			/* var cityId=mini.getbyName("cityId").getValue();
			 var townId=mini.getbyName("town").getValue();
			 var districtId=mini.getbyName("district").getValue();
			 var estateName=mini.getbyName("estateName").getValue();*/
			lc.removeObjectEmptyValueProperty(data);
			grid.load(data);
		}
		 mini.getbyName("districtId").load("dicAreaNew/getDicAreaNewByParentId.do?parentId=" + 43);
		 function onDicChanged(e) {
	           var suptownCombo = mini.getbyName("districtId");
	           var estateCombo = mini.getbyName("townId");
	           var dicId = suptownCombo.getValue();

	           estateCombo.load("dicAreaNew/getDicAreaNewByParentId.do?parentId=" + dicId);
	           estateCombo.setValue("");
	       }
	       
	       function onCityChanged(e){
	    	   var suptownCombo = mini.getbyName("districtId");
	           var city = mini.getbyName("cityId");
	           var cityValue = city.getValue();
	           
	           suptownCombo.load("dicAreaNew/getDicAreaNewByParentId.do?parentId=" + cityValue);
	           suptownCombo.setValue("");

	       }
	  function refresh(){
		  mini.getbyName("cityId").setValue(null);
		mini.getbyName("townId").setValue(null);
			 mini.getbyName("districtId").setValue(null);
		mini.getbyName("estateName").setValue(null);
	  }
	 function exportData(){
		 var cityId=mini.getbyName("cityId").getValue();
		 var townId=mini.getbyName("townId").getValue();
		 var districtId=mini.getbyName("districtId").getValue();
		 var estateName=mini.getbyName("estateName").getValue();
	/* 	 var startTime=mini.getbyName("startTime").getValue();
		 var endTime=mini.getbyName("endTime").getValue(); */
		 var url="report/export.do?cityId="+cityId;
		 if(townId!=null && townId!=''){
			 url +="&townId="+townId;
		 }
		 if(districtId!=null && districtId!=''){
			 url+="&districtId="+districtId;
		 }
		 if(estateName!=null && estateName!=''){
			 url+="&estateName="+estateName;
		 }
		/*  if(startTime!=null){
			 var date = new Date(startTime);
			 url+="&startTime="+ date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		 }
		 if(endTime!=null){
			 var date = new Date(endTime);
			 url+="&endTime="+ date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		 } */
		 //location.href = "report/export.do?cityId="+cityId;
		 var exportframe = $('<iframe style="display:none"/>');
		  url =encodeURI(url);
		exportframe.attr({'src': url});
		$('#dg').append(exportframe);
	 }
	</script>
</html>
