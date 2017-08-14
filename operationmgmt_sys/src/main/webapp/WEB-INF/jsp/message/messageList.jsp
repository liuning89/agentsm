<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
	<head>
		<title>消息push功能</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="../scripts/boot.js?v=${version}"></script>
		
		
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
	    <script src="${ctx}/scripts/miniui/miniui.js" type="text/javascript"></script>
	</head>

	<body style="height:100%;">
		<div style="width:100%;">
			<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
				<table style="width:99%;">
					<tr>
						<td>
							<a class="mini-button" id="addbtn" iconCls="icon-add" onclick="addPage()">新增</a>
						</td>
						<td id="search">
							<span>状态：</span> <input id="messageStatus" name=""messageStatus"" class="mini-combobox" textField="text" valueField="id" data="[{id:0,text:'全部'},{id:1,text:'待发送'},{id:2,text:'已发送'}]" value=0 />	
							<span>推送内容：</span>
							<input id="pushContent" name="pushContent" class="mini-textbox" emptyText="推送内容"/>
							
							<a class="mini-button" iconCls="icon-search" onclick="search()">查询</a>
							<a class="mini-button" iconCls="icon-cancel" onclick="reset()">重置</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!--列表-->
		<div class="mini-fit" style="height:400px">
			<div id="dg" class="mini-datagrid" sizeList="[20,30,50,100]" pageSize="20" style="height:100%;" url="${ctx}/message/showMessage.action"
				multiSelect="true" showfooter="false">
				<div property="columns">
					<!-- <div type="checkcolumn" width="10"></div> -->
					<div type="indexcolumn" style="display: none">序号</div>
					<div field="pushContent" align="left" headeralign="left" width="70">推送内容</div>
					<div field="updateTime" align="left" headeralign="left" width="35" renderer="onTimeRenderer">编辑时间</div>
					<div field="pushTime" align="left" headeralign="left" width="35" renderer="onTimeRenderer">发送时间</div>
					<div field="pushStatus" align="left" headeralign="left" width="35">状态</div>
					<div field="context" align="left" headeralign="left" width="40">操作</div>
				</div>
			</div>
		</div>
	</body>
	<%-- <script type="text/javascript" src="scripts/pages/areaOrg/area/list.js?v=${version}"></script> --%>
	
	<script type="text/javascript">
	mini.parse();
	 var grid = mini.get("dg");
	grid.load();
	 grid.on("drawcell", function (e) {
   	  var record = e.record,
             column = e.column,
             field = e.field,
             value = e.value;
   	
   	if (field  == "pushStatus") {
   		if(value == 1){
   			e.cellHtml = "待发送";
   		}else if(value == 2){
   			e.cellHtml = "已发送";
   		}else if(value == 3){
   			e.cellHtml = "发送失败";
   		}else{
   			e.cellHtml = "已保存";
   		}
   		
     }
   	if(field == "context"){
   		// e.cellHtml = '<a href="javascript:openDetail(' + e.rowIndex + ',\'' + record.agentId  + '\',\''+record.houseId + '\')">操作</a> '
   		if(record.pushStatus != 2){
		 	e.cellHtml = "<a href='javascript:void(0)' onclick='editData(" + record.id + ",\"" + record.title + "\",\"" + record.pushContent+ "\",\"" + record.isTiming+ "\",\"" + record.pushTime  +"\")'>编辑</a> | <a href='javascript:void(0)' onclick='deleteData(" + record.id + ",\"" + record.mobile  +"\")'>删除</a>";
   			e.cellHtml +="|<a href='javascript:void(0)' onclick='lookDetail(" + record.id +")'>查看</a>";
   		}else{
   			e.cellHtml ="<a href='javascript:void(0)' onclick='lookDetail(" + record.id +")'>查看</a>";
   		}
   	}
   });
	  function search() {
          var pushstatus = mini.get("messageStatus").getFormValue();//类型
          var pushContent = mini.get("pushContent").getValue();
          grid.load({pushstatus:pushstatus,pushContent:pushContent});
      }
      function reset(){

    	  mini.get("pushContent").setValue("");

           mini.get("messageStatus").select(0);
          
      }
	  function addPage () {
			mini.open({
				url : "${ctx}/message/addmessagePage.action",
				title : "新增推送",
				width : 530,
				height : 700,
				ondestroy : function(action) {
					if(action == "save"){
						//lf.areaOrg.area.list.search();
					}
				}
			});
	  }
	  function lookDetail(id){
		  mini.open({
				url : "${ctx}/message/lookDetail.action?id="+id,
				title : "查看",
				width : 530,
				height : 700,
				ondestroy : function(action) {
					if(action == "save"){
						//lf.areaOrg.area.list.search();
					}
				}
			});
	  }
	  
	  function editData (id,title,pushContent,istiming,pushtime) {
			mini.open({
				url : "${ctx}/message/updatemessagePage.action?id="+id+"&title="+encodeURI(encodeURI(title))+"&pushContent="+encodeURI(encodeURI(pushContent))+"&istiming="+istiming+"&pushtime="+pushtime,
				title : "编辑",
				width : 560,
				height : 700,
				ondestroy : function(action) {
					if(action == "save"){
						//lf.areaOrg.area.list.search();
					}
				}
			});
	  }
	  
	//发送消息
      function deleteData(id,mobile){
      	lc.mask("正在处理中,请稍后...");
      	$.ajax({
      		url : "${ctx}/message/sendMessage.action?id="+id+"&isdelete=2",
              cache : false,
              dataType : 'text',
              success : function(data) {
          		 if(data == 1){
          			 lc.unmask();
          			 mini.alert("操作成功!","成功",function(){closed();});
          		 }else{
          			 lc.unmask();
          			 mini.alert("操作失败!","错误",function(){closed();});
          		 }
              }
          });
      }
	  //发送消息
      function sendData(pushContent,pushtime,istiming,saves,isdelete,title,id,companyIds,cityIds,pushPlatform){
		  console.info(cityIds);
      	lc.mask("正在处理中,请稍后...");
      	$.ajax({
      		url : "${ctx}/message/sendMessage.action?pushContent="+encodeURI(encodeURI(pushContent))+"&pushtime="+pushtime+"&istiming="+istiming+"&saves="+saves+"&isdelete="+isdelete+"&title="+encodeURI(encodeURI(title))+"&id="+id+"&companyIds="+companyIds+"&cityIds="+cityIds+"&pushPlatform="+pushPlatform,
              cache : false,
              dataType : 'text',
              success : function(data) {
          		 if(data == 1){
          			 lc.unmask();
          			 mini.alert("操作成功!","成功",function(){closed();});
          		 }else{
          			 lc.unmask();
          			 mini.alert("操作失败!","错误",function(){closed();});
          		 }
              }
          });
      }
	  
      function closed(){
      	//parent.lf.agentsm.index.refreshHouseImageListTabs();
      	search();
      }
    function onTimeRenderer(e) {
    	
        var value = e.value;
        if (value){
            value = new Date(value); 
        	return mini.formatDate(value, 'yyyy-MM-dd HH:mm:ss');
        } 
        return "";
    }
	</script>
</html>
