<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>经纪人列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <script src="${ctx}/scripts/boot.js?v=${version}" type="text/javascript"></script>
    <style type="text/css">
        html,body
        {
            width:100%;
            height:100%;
            border:0;
            margin:0;
            padding:0;
            overflow:visible;
        }
    </style>
</head>

<!-- BEGIN BODY -->
<body class="page-header-fixed page-sidebar-fixed page-footer-fixed">
				<!-- 查询条件start -->
				<div id="search" class="search_form">
					<div >
						<form id="searchFrm" action="" >
							<table spellcheck="false">
                                 <tr>
                                    <td>城市:</td>
                                    <td>
                                        <input id="cityId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
                                                url="${ctx}/areaorg/getCityListByUserId.action" value="" showNullItem="true"  allowInput="true"
                                                onvalidation="onComboValidation" onvaluechanged="onCityChanged" emptyText="请选择" nullItemText="请选择" />
                                    </td>
                                    <td>区域:</td>
                                    <td>  
                                        <input id="areaId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
                                               data="[]" value="" showNullItem="true"  allowInput="true"
                                            onvalidation="onComboValidation" onvaluechanged="onAreaChanged" emptyText="请选择" nullItemText="请选择"/>
                                    </td>
                                    <td>门店:</td>
                                    <td>  
                                        <input id="storeId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
                                               data="[]" value="" showNullItem="true"  allowInput="true"
                                               onvalidation="onComboValidation"  emptyText="请选择" nullItemText="请选择"/>
                                    </td>
                                    <td>姓名:</td>
                                    <td>  
                                        <input id="agentName" class="mini-textbox" style="width:135px;">
                                    </td>
                                 </tr>
                                 <tr>
                                    <td width="50px">
                                        <input type="button" class="btn btn-info btn-sm" value="查询" onclick="doSearch();" />
                                    </td>
                                    <td>
                                        <input type="button" class="btn btn-info btn-sm" value="重置" onclick="restFrom();" />
                                    </td>
                                </tr>
							</table>
						</form>
					</div>
				</div>
				<!-- 查询条件end -->
                
				<!-- 结果展示table start -->
				<div class="mini-fit" style="background:red;height:100px;">
                    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" 
                        url="${ctx}/agent/agentListData.action"  idField="id" allowResize="false"
                        sizeList="[20,30,50,100]" pageSize="20">
                        <div property="columns">
                            <div type="indexcolumn">序号</div>
                            <div field="workNumber" width="120" headerAlign="center" allowSort="false">工号</div>                            
                            <div field="realName" width="100"  align="center" headerAlign="center">名字</div>
                            <div field="gender" width="100"  renderer="onGenderRenderer" align="center" headerAlign="center">性别</div>
                            <div field="mobile" width="100" align="center" headerAlign="center">电话</div>
                            <%--<div field="isCrownAgent" width="100" renderer="onCrownRenderer"  align="center" headerAlign="center">皇冠</div>--%>
                            <%--<div field="highPercentage" width="100" align="center" headerAlign="center">好评率</div>--%>
                            <%--<div field="highPercent" width="100" align="center" headerAlign="center">好评数</div>--%>
                            <%--<div name="action" width="300" align="center" headerAlign="center">操作</div>--%>
                        </div>
                    </div>   
                </div>
				<!-- 结果展示table end -->
				<!-- 这里开始写业务页面end -->

	<!-- BEGIN FOOTER -->
    
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        
        grid.on("drawcell", function (e) {
    	   var record = e.record, column = e.column, field = e.field, value = e.value;
            if (column.name == "action") {
                e.cellStyle = "text-align:center";
                var mx = '<a href="javascript:openDetail(' + e.rowIndex + ',\'' + record.id  + '\')">评价明细</a>&nbsp;';
                var ht = '<a href="javascript:houseTrans(' + record.id + ')">房源转移</a>&nbsp;';
                var ct = '<a href="javascript:customerTrans(' + record.id  + ')">客源转移</a>&nbsp;';
                /* var la = '<a href="javascript:setIsCrown(' + record.id  + ')">皇冠设置</a>&nbsp;'; */
                var la = '';
                var xq = '<a href="javascript:estateTrans(' + record.id  + ')">小区转移</a>&nbsp;';
                var ic = '';
                if(record.level == 40)
                {
                    ic = '<a href="javascript:setIsExpel(' + record.id  + ')">离职</a>&nbsp;';
                }
                e.cellHtml =  mx + ht + ct + la + xq + ic;
            }
            if (field == "highPercentage") {
                 var highPercent = record.highPercentage;
                 var highPercentageTotal = record.highPercentageTotal;
                 var value = 0;
                 if(highPercentageTotal > 0)
                 {
                	 value = highPercent / highPercentageTotal * 100;
                 }
                 else
                 {
                	 value = 100;
                 }
                 value = value.toFixed(2);
                 e.cellHtml = value + "%";
            }
            if (field == "highPercent") {
                e.cellHtml = record.highPercentage;
            }
        });
        
        function openDetail(index,agentId)
        {
        	var row = grid.getRow(index);
            var title = row.realName + "-评价明细";
        	parent.lf.bzsm.index.addTabs({
                name:"agentDetail" + agentId,
                title:title,
                showCloseButton:true,
                url:lc.rootPath("agent/evaluateListIndex.action?agentId="+agentId)
            });
        }
        
        //////////////////////////////////////////////////
        function onTimeRenderer(e) {
            var value = e.value;
            value = new Date(value); 
            if (value) return mini.formatDate(value, 'yyyy-MM-dd HH:mm');
            return "";
        }
    
        //////////////////////////////////////////////////////////////////////////
        var Genders = [{ id: 1, text: '男' }, { id: 2, text: '女'}];
        function onGenderRenderer(e) {
            for (var i = 0, l = Genders.length; i < l; i++) {
                var g = Genders[i];
                if (g.id == e.value) return g.text;
            }
            return "保密";
        }
        
        ///////////////////////////////////////////////////////////////////////////
        var Crowns = [{ id: 1, text: '是' }, { id: 2, text: '否'}];
        function onCrownRenderer(e) {
            for (var i = 0, l = Crowns.length; i < l; i++) {
                var g = Crowns[i];
                if (g.id == e.value) return g.text;
            }
            return "";
        }
        
        //////////////////////////////////////////////
        function onComboValidation(e) {
            var items = this.findItems(e.value);
            if (!items || items.length == 0) {
                this.setValue('');
            }
        }

        autoSelected();
        doSearch();

        function autoSelected()
        {
            var cityCbo = mini.get("cityId");
            if(cityCbo.data.length == 2)
            {
                cityCbo.select(1);
                onCityChanged();
                cityCbo.disable();
            }
        }
        
        function restFrom()
        {
        	new mini.Form("#searchFrm").clear();
            autoSelected();
        }

        function onCityChanged(e) {
            var cityCbo = mini.get("cityId");
            var areaCbo = mini.get("areaId");
            var id = cityCbo.getFormValue();

            if(id != null && id != "") {
                var url = "${ctx}/areaorg/getPartnerByCityId.action";
                url += "?cityId=" + id;
                areaCbo.setUrl(url);
            }
            else {
                areaCbo.data = "[]";
            }

            //清空数据
            var storeCbo = mini.get("storeId");
            storeCbo.data = "[]";

            //唯一项自动选择
            if(areaCbo.data.length == 2)
            {
                areaCbo.select(1);
                onAreaChanged();
            }

        }

        function onAreaChanged(e) {
            var cityCbo = mini.get("cityId");
            var areaCbo = mini.get("areaId");
            var storeCbo = mini.get("storeId");
            var id = areaCbo.getValue();
            var cityId = cityCbo.getValue();
            if (id != null && id != "") {
                var url = "${ctx}/areaorg/deptListByPartnerId.action";
                url += "?cityId=" + cityId;
                url += "&partnerId=" + id;
                storeCbo.setUrl(url);
            } else {
                storeCbo.data = "[]";
            }

            //默认选择唯一项
            if(storeCbo.data.length == 2)
            {
                storeCbo.select(1);
            }

        }
        
        function doSearch() {
            var pars = "{";
            var cityId = mini.get("cityId").getValue();
            if (cityId != null && cityId != '') {
                pars += "'cityId' : '" + cityId + "',";
            }
            
            var companyId = mini.get("areaId").getValue();
            if (companyId != null && companyId != '') {
                pars += "'areaId' : '" + companyId + "',";
            }
            
            var storeId = mini.get("storeId").getValue();
            if (storeId != null && storeId != '') {
                pars += "'storeId' : '" + storeId + "',";
            }
            
            var agentName = mini.get("agentName").getValue();
            if (agentName != null && agentName != '') {
                pars += "'agentName' : '" + agentName + "',";
            }
            
            if (pars.length > 1) {
                pars = pars.substr(0, pars.length - 1);
            }
            pars += "}";
            pars = eval("(" + pars + ")");
            
            grid.load(pars)
        }
         
        //房源转移
        function houseTrans(agentId) {
        	var row = grid.getSelected();
            if (row) {
                mini.open({
                    url: "${ctx}/agent/gotoHouseResourceTrans.action?id=" + agentId,
                    title: "房源转移", width: 400, height: 320,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var data = { action: "edit", id: agentId};
                        iframe.contentWindow.SetData(data);
                    },
                    ondestroy: function (action) {
                        grid.reload();
                    }
                });
            }
            else
            {
                mini.alert("请选中一条记录!");
            }
        }
        
        //客源转移
        function customerTrans(agentId) {
            var row = grid.getSelected();
            mini.open({
                url: "${ctx}/agent/gotoCustomerResourceTrans.action?id=" + agentId,
                title: "客源转移", width: 400, height: 320,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "edit", id: agentId};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
        }
        
        
        //小区转移
        function estateTrans(agentId)
        {
        	mini.open({
                url: "${ctx}/agent/gotoEstateResourceTrans.action?id=" + agentId,
                title: "小区转移", width: 800, height: 420,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "edit", id: agentId};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
        }
        
        //皇冠设置
        function setIsCrown(agentId) {
            var row = grid.getSelected();
            mini.open({
                url: "${ctx}/agent/gotoAgentIsCrown.action",
                title: "皇冠设置", width: 400, height: 200,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "edit", id: agentId};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
        }
        
        //开除
        function setIsExpel(agentId) {
            mini.open({
                url: "${ctx}/agent/gotoAgentIsExpel.action",
                title: "离职", width: 400, height: 200,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = { action: "edit", id: agentId};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
        }
    </script>
	
</body>
<!-- END BODY -->
</html>
