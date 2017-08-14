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
    <title>经纪人悟空币剩余情况查询</title>
    <script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
</head>
<body style="height:100%;">
    <div style="width:100%;">
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:99%;">
	            <tr>     
                    <td id="search">
                    <span>公司</span>
                    <input id="companyId" name="companyId" class="mini-combobox" style="width:135px;" textField="text" valueField="id"
		               url="amoutset/getFranchiseeList.action" value="" showNullItem="true"  allowInput="true"
		                emptyText="请选择" onvalidation="onComboValidation" nullItemText="请选择" />                  
		            <span>城市:</span>
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
				     <span>经纪人手机号：</span>
                     <input id="agentPhoneNumber" name="agentPhoneNumber" class="mini-textbox" emptyText="精确搜索" allowInput="true">
				     <a class="mini-button" id="searchBtn" onclick="search()" iconCls="icon-search">查询</a>
				     <a class="mini-button" id="resetBtn" onclick="reset();" iconCls="icon-cancel">重置</a>
				     <a class="mini-button" iconCls="icon-redo" onclick="exportData();">导出</a>
				     </td>
                </tr>
            </table>           
        </div>
    </div>
    <!--列表-->
        <div  align="right">剩余悟空币总币额：<span id="total">0</span></div>
    <div class="mini-fit" style="height:400px">
          <div id="dg" class="mini-datagrid" sizeList="[20,30,50,100]" pageSize=20  style="height:100%;" url="operatingSystem/operationManagement/queryAgentWKCoinSurplusList.action" multiSelect="true" showfooter="false">
              <div property="columns">
                  <div type="indexcolumn" width="10">序号</div>
                  <div field="companyName"  align="left" headeralign="left" width="20">公司</div>
                  <div field="cityName"  align="left" headeralign="left" width="20">城市</div>
                  <div field="areaOrgName"   align="left" headeralign="left" width="20">区域</div>
                  <div field="storeName"  align="left" headeralign="left" width="10">门店</div>
                  <div field="agentName"  align="left" headeralign="left" width="10">经纪人</div>
                   <div field="wuKongCoin"  align="left" headeralign="left" width="20">剩余悟空币币额</div>
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
	                url: "operatingSystem/operationManagement/getAgentWKCoinSurplusTotal.action",
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
		var form = new mini.Form("#search");
		var data = form.getData(true);
		lc.removeObjectEmptyValueProperty(data);
		grid.load(data);
	}
	
	function reset(){
		var form = new mini.Form("#search");
		form.reset();
	}
	function onComboValidation(e) {
        var items = this.findItems(e.value);
        if (!items || items.length == 0) {
           	this.setValue('');
        }
    }
	function exportData()
    {
		var url = "operatingSystem/operationManagement/agentWkCoinSurplusExport.action";
    	var cityId= mini.get("cityId").getValue();
        var areaId= mini.get("areaId").getValue();
        var storeId= mini.get("storeId").getValue();
        var agentId= mini.get("agentId").getValue();
        var companyId = mini.get("companyId").getValue();
        var agentPhoneNumber = mini.get("agentPhoneNumber").getValue();
        url += "?cityId="+ cityId + "&areaId=" + areaId + "&storeId="+storeId+"&agentId"+agentId+"&companyId="+companyId+"&agentPhoneNumber="+agentPhoneNumber;
    	window.location.href = url;
    }
</script>
</html>
