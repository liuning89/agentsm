<%@page import="com.lifang.agentsm.entity.LfEmployee"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String houseId = request.getParameter("houseId");

%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <title>售房列表查询</title>
    <script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
</head>
<body style="height:100%;">
    <div style="width:100%;">
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:99%;">
	            <tr>     
                     <td id="search">
                       <span>加盟公司</span>
                       <input id="companyId" name="companyId" class="mini-combobox" style="width:135px;" textField="text" valueField="id"
		               url="amoutset/getFranchiseeList.action" value="" showNullItem="true"  allowInput="true"
		                emptyText="请选择" onvalidation="onComboValidation" nullItemText="请选择" />                   <span>城市:</span>
				    <input id="cityId" name="cityId" url="areaorg/getCityListByUserId.action" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
				           allowInput="true"
				           onvaluechanged="onCityChanged"/>
				    <span>区域:</span>
				    <input id="areaId" name="areaId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
				           showNullItem="true" allowInput="true"
				           onvaluechanged="onAreaChanged" emptyText="请选择" nullItemText="请选择"/>
				    <span>门店:</span>
				    <input id="storeId" name="storeId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
				           showNullItem="true" allowInput="true"
				           onvaluechanged="onStoreChange" emptyText="请选择" nullItemText="请选择"/>
				    <span>经纪人</span>
				    <input id="agentId" name="agentId" class="mini-combobox" style="width:135px;"
				           textField="name" valueField="id" showNullItem="true"
				           data="[]" allowInput="true"
				           emptyText="请选择" nullItemText="请选择"/>
				     <span>消费日期：</span>
                        <input id="startTime" style="width:120px" name="startTime" class="mini-datepicker"  format="yyyy-MM-dd" showOkButton="true" showClearButton="false"/>-<input name="endTime" id="endTime" allowInput="false" class="mini-datepicker" format="yyyy-MM-dd" showOkButton="true" style="width:120px"  showClearButton="false"/>
       				 <span>消费类型：</span>
                    <input name="businessType" id="businessType" class="mini-combobox"  textField="text" valueField="id" value="-1" data="[{id:-1,text:'全部'},{id:2,text:'抢单（约看）'},{id:3,text:'抢单（微聊）'},{id:4,text:'抢单（悬赏）'}]"/>
       
				                        <a class="mini-button" id="searchBtn" onclick="search()" iconCls="icon-search">查询</a>
				                        <a class="mini-button" id="resetBtn" onclick="reset();" iconCls="icon-cancel">重置</a>
				                        <a class="mini-button" iconCls="icon-redo" onclick="exportData();">导出</a>
				                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <!--列表-->
        <div  align="right">消费悟空币总币额：<span id="total"></span></div>
    <div class="mini-fit" style="height:400px">
          <div id="dg" class="mini-datagrid" sizeList="[20,30,50,100]" pageSize=20  style="height:100%;" url="amoutset/getWkCoinConsumeList.action" multiSelect="true" showfooter="false">
              <div property="columns">
                  <div type="indexcolumn" width="10"></div>
                  <div field="companyName"  align="left" headeralign="left" width="20">公司名称</div>
                  <div field="cityName"  align="left" headeralign="left" width="20">城市</div>
                  <div field="areaOrgName"   align="left" headeralign="left" width="20">区域</div>
                  <div field="storeName"  align="left" headeralign="left" width="10">门店</div>
                  <div field="agentName"  align="left" headeralign="left" width="10">经纪人</div>
                   <div field="price"  align="left" headeralign="left" width="20">消费币额</div>
                  <div field="createTime" dateFormat="yyyy-MM-dd HH:mm:ss" align="left" headeralign="left" width="20">消费时间</div>
                  <div field="businessType"  align="left" headeralign="left" width="20">消费类型</div>
              </div>
          </div>
    </div>
</body>
<script type="text/javascript">
	mini.parse();
	var grid = mini.get("dg");
	grid.load();
	function getTotal(e,para){
		if(e.textStatus=='success'){
			  $.ajax({
	                url: "amoutset/getWkCoinConsumeTotal.action",
					type: 'post',
	                dataType:'json',
	                data:para,
	                success: function (text) {
	                	$("#total").html(text.data);
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    alert(jqXHR.responseText);
	                    CloseWindow();
	                }
	            });
		}
	}
	grid.on("load", function (e) {
		var form = new mini.Form("#search");
		var data = form.getData(true);
		console.info(data);
		lc.removeObjectEmptyValueProperty(data);
		getTotal(e,data);
	});
	grid.on("drawcell", function(e) {
		var record = e.record, field = e.field, value = e.value, column = e.column;
		if (column.name == "action") {
			if(record.yfykStatus == 0){
				var html = lc.strFormat('<a class="mini-button" style="padding-left:10px;padding-right:10px;" onclick="updateStatus({0},{1})">显示</a>' +
						'<a class="mini-button" style="margin-left:5px;padding-left:10px;padding-right:10px;" onclick="updateValue({0})">修改</a>',
						record.id,record.yfykStatus,e.rowIndex);
			}else{
				var html = lc.strFormat(
						'<a class="mini-button" style="padding-left:10px;padding-right:10px;" onclick="updateStatus({0},{1})">隐藏</a>',
						record.id,record.yfykStatus,e.rowIndex);
			}
			
			e.cellHtml = html;
		}else if(field=='businessType'){
			if(value == "2"){
				e.cellHtml = "抢单（约看）";
			}else if(value=="3"){
				e.cellHtml="抢单（微聊）";
			}else if(value=="4"){
				e.cellHtml="抢单（悬赏）";
			}
		}
	});
	
    function onCityChanged(e) {
        var cityCbo = mini.get("cityId");
        var areaCbo = mini.get("areaId");
        var storeCbo = mini.get("storeId");
        var id = cityCbo.getValue();
        var url = "areaorg/getPartnerByCityId.action";
        if (id != null && id != "") {
            url += "?cityId=" + id;
            areaCbo.setUrl(url);
            if (areaCbo.data.length == 2) {
                areaCbo.select(1);
            } else {
                areaCbo.select(0);
            }
        } else {
            areaCbo.setData([]);
            storeCbo.setData([]);
        }
        onAreaChanged();
    }

    function onAreaChanged(e) {
        var areaCbo = mini.get("areaId");
        var cityCbo = mini.get("cityId");
        var storeCbo = mini.get("storeId");
        var idarea = areaCbo.getValue();
        var idcity = cityCbo.getValue();
        var url = "areaorg/deptListByPartnerId.action?";
        if (idarea != null && idarea != "") {
            url += "cityId=" + idcity;
            url += "&partnerId=" + idarea;
            storeCbo.setUrl(url);
            if (storeCbo.data.length == 2) {
                storeCbo.select(1);
            } else {
                storeCbo.select(0);
            }
        } else {
            storeCbo.setData([]);
        }
        onStoreChange();
    }

    function onStoreChange(e) {
        var areaId = mini.get("areaId").getValue();
        var cityId = mini.get("cityId").getValue();
        var storeId = mini.get("storeId").getValue();
        var agentCbo = mini.get("agentId");
        var url = "agent/getAgentByAreaOrg.action";
        if (storeId != null && storeId != "" ) {
            url += "?areaId=" + areaId;
            url += "&cityId=" + cityId;
            url += "&storeId=" + storeId;
            agentCbo.setUrl(url);
            if (agentCbo.data.length == 2) {
                agentCbo.select(1);
            }
        }else {
            agentCbo.setData([]);
        }
    }
	function search(){
		var startTime = mini.get("startTime");
		var endTime = mini.get("endTime");
		if (startTime.getValue() != '' && endTime.getValue() != '') {
			if (endTime.getValue().getTime() < startTime.getValue()
					.getTime()) {
				mini.alert("结束时间不可小于开始时间");
				return;
			}
		}
		var form = new mini.Form("#search");
		var data = form.getData(true);
		lc.removeObjectEmptyValueProperty(data);
		grid.load(data);
	}
	
	function reset(){
		var form = new mini.Form("#search");
		form.reset();
		mini.get("yfykStatus").setValue(-1);
		
	}
	function onComboValidation(e) {
        var items = this.findItems(e.value);
        if (!items || items.length == 0) {
           	this.setValue('');
        }
    }
	function exportData()
    {
		var url = "amoutset/exportConsume.action";
    	var cityId= mini.get("cityId").getValue();
        var areaId= mini.get("areaId").getValue();
        var storeId= mini.get("storeId").getValue();
        var agentId= mini.get("agentId").getValue();
        var companyId= mini.get("companyId").getValue();
        var startTime= mini.get("startTime").getValue();
        var endTime= mini.get("endTime").getValue();
        var businessType = mini.get("businessType").getValue();
        url += "?cityId="
        + cityId + "&areaId=" + areaId + "&storeId="+storeId+"&companyId="+companyId+"&agentId="+agentId+"&businessType="+businessType;
        if(startTime!=null&&startTime!=""){
		 var date = new Date(startTime);
		 url+="&startTime="+ date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	 }
	 if(endTime!=null&&endTime!=""){
		 var date = new Date(endTime);
		 url+="&endTime="+ date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	 } 
    	window.location.href = url;
    }
</script>
</html>
