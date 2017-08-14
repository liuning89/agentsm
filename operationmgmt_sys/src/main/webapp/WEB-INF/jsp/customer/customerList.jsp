<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>客户列表</title>
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
                                            onvalidation="onComboValidation" onvaluechanged="onStoreChange" emptyText="请选择" nullItemText="请选择"/>
                                    </td>
                                    <td>经纪人:</td>
                                    <td>
                                        <input id="agentId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
                                               data="[]" value="" showNullItem="true"  allowInput="true"
                                            onvalidation="onComboValidation"  emptyText="请选择" nullItemText="请选择"/>
                                    </td>

                                <tr>
                                    <td colspan="2" align="center">
                                        <div id="is3A" name="is3A" class="mini-checkbox" checked="true" text="是否A类客户" ></div>
                                    </td>
                                    <td style="width: 75px;">客户级别:</td>
                                    <td>
                                        <input id="cusLevel" class="mini-combobox" style="width:100px;" textField="text" valueField="id"
                                               data="[{id:1,text:'A'},{id:2,text:'B'},{id:3,text:'C'},{id:4,text:'D'}]" value=""
                                               showNullItem="true" allowInput="false" emptyText="请选择" nullItemText="请选择"/>
                                    </td>
                                    <td>客户来源:</td>
                                    <td>
                                        <input id="cusFrom" class="mini-combobox" style="width:100px;" textField="text" valueField="id"
                                               data="[{id:1,text:'悟空找房'},{id:2,text:'网络客户'},{id:3,text:'其它'}]" value=""
                                               showNullItem="true" allowInput="false" emptyText="请选择" nullItemText="请选择"/>
                                    </td>
                                    <td>添加日期:</td>
                                    <td>
                                        <span></span><input id="dateStart" class="mini-datepicker" value="" format="yyyy-MM-dd" timeFormat="H:mm:ss"  style="width:100px;" showOkButton="true" showClearButton="false"/>
                                        <span>至：</span><input id="dateEnd" class="mini-datepicker" value="" format="yyyy-MM-dd" timeFormat="H:mm:ss"  style="width:100px;" showOkButton="true" showClearButton="false"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>客户信息:</td>
                                    <td>
                                        <input name="agentStr" id="agentStr" class="mini-textbox" emptyText="姓名/电话/ID(精确)"/>
                                    </td>
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
                        url="${ctx}/customer/getCustomerList.action"  idField="id" allowResize="false"
                        sizeList="[20,30,50,100]" pageSize="20">
                        <div property="columns">
                            <div type="indexcolumn">序号</div>
                            <div field="name" width="100" headerAlign="center" align="center" allowSort="false">姓名</div>
                            <div field="gender" width="100"  align="center" renderer="onGenderRenderer" headerAlign="center">性别</div>
                            <div field="acus" width="100"  align="center" headerAlign="center">是否A客</div>
                            <div field="acusTime" width="100" align="center" renderer="onTimeRenderer" headerAlign="center">标A时间</div>
                            <div field="acusAgent" width="100" align="center"  headerAlign="center">标A经纪人</div>
                            <div field="cusLevel" width="100" renderer="onCusLevelRenderer" align="center" headerAlign="center">客户级别</div>
                            <div field="source" width="100" renderer="onSourceRenderer"  align="center" headerAlign="center">来源</div>
                            <div field="agentName" width="100" align="center"  headerAlign="center">经纪人</div>
                            <div field="createTime" width="100" align="center" renderer="onTimeRenderer" headerAlign="center">添加时间</div>
                            <%--<div name="action" width="300" align="center" headerAlign="center">操作</div>--%>
                        </div>
                    </div>
                </div>
				<!-- 结果展示table end -->
				<!-- 这里开始写业务页面end -->
			</div>
		</div>
	</div>
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

                var la = '';
                var xq = '<a href="javascript:estateTrans(' + record.id  + ')">小区转移</a>&nbsp;';
                var ic = '';
                if(record.level == 40)
                {
                    ic = '<a href="javascript:setIsExpel(' + record.id  + ')">离职</a>&nbsp;';
                }
                e.cellHtml =  mx + ht + ct + la + xq + ic;
            }
            if (field == "source") {
                 var source = record.source;
                 var isCallBackCus = record.isCallBackCus;

                 if(source == 25)
                 {
                	 value = '悟空找房';
                 }
                 else if(isCallBackCus == 1)
                 {
                	 value = '网络客户';
                 }
                 else{
                     value = '其它';
                 }

                 e.cellHtml = value;
            }
            if (field == "acus") {
                var acus = record.acus;
                if(acus > 0 )
                {
                    e.cellHtml = '是';
                }
                else
                {
                    e.cellHtml = '否';
                }
            }

            if (field == 'acusAgent')
            {
                var acus = record.acus;
                var agentName = record.agentName;
                if(acus > 0)
                {
                    e.cellHtml = agentName;
                }
                else
                {
                    e.cellHtml = '-----';
                }
            }

            if (field == 'name')
            {
                var name = record.name;
                var id = record.id;
                e.cellHtml = '<a href="javascript:showDetail(' + id + ',\'' + name + '\')">' + name + '</a>';
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
            if (value)
            {
                value = new Date(value);
                return mini.formatDate(value, 'yyyy-MM-dd HH:mm');
            }
            return "-----";
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

        function onCusLevelRenderer(e)
        {
            var value = e.value;
            if(value)
            {
                if(value == 1)
                {
                    return "A";
                }
                else if(value == 2)
                {
                    return "B";
                }
                else if(value == 3)
                {
                    return "C";
                }
            }
            return "D";
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
            var areaOrgType = mini.get("areaOrgType");
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
            var newAgentCbo = mini.get("agentId");
            newAgentCbo.data = "[]";

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
            if (id != null && id != "")
            {
                var url = "${ctx}/areaorg/deptListByPartnerId.action";
                url += "?cityId=" + cityId;
                url += "&partnerId=" + id;
                storeCbo.setUrl(url);
            }
            else {
                storeCbo.data = "[]";
            }

            //清空无效数据
            var newAgentCbo = mini.get("agentId");
            newAgentCbo.data = "";

            //默认选择唯一项
            if(storeCbo.data.length == 2)
            {
                storeCbo.select(1);
                onStoreChange();
            }

        }

        function onStoreChange(e) {

            var areaId = mini.get("areaId").getValue();
            var cityId = mini.get("cityId").getValue();
            var storeId = mini.get("storeId").getValue();
            var agentCbo = mini.get("agentId");
            var url = "${ctx}/agent/getAgentByAreaOrg.action";
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

            var cusLevel = mini.get("cusLevel").getFormValue();
            if (cusLevel != null && cusLevel != '') {
                pars += "'cusLevel' : '" + cusLevel + "',";
            }

            var cusFrom = mini.get("cusFrom").getFormValue();
            if (cusFrom != null && cusFrom != '') {
                pars += "'cusFrom' : '" + cusFrom + "',";
            }

            var dateStart = mini.get("dateStart").getFormValue();
            if (dateStart != null && dateStart != '') {
                pars += "'dateStart' : '" + dateStart + "',";
            }

            var dateEnd = mini.get("dateEnd").getFormValue();
            if (dateEnd != null && dateEnd != '') {
                pars += "'dateEnd' : '" + dateEnd + "',";
            }

            var agentStr = mini.get("agentStr").getValue();
            if (agentStr != null && agentStr != '') {
                pars += "'agentStr' : '" + agentStr + "',";
            }

            var agentId = mini.get("agentId").getValue();
            if (agentId != null && agentId != '') {
                pars += "'agentId' : '" + agentId + "',";
            }

            var is3A = mini.get("is3A");
            if(is3A.checked == true)
            {
                pars += "'is3A' : 1,";
            }
            else
            {
                pars += "'is3A' : 0,";
            }
            
            if (pars.length > 1) {
                pars = pars.substr(0, pars.length - 1);
            }
            pars += "}";
            pars = eval("(" + pars + ")");
            
            grid.load(pars)
        }

        function showDetail(id, name)
        {
            var title = name + "-明细";
            parent.lf.agentsm.index.addTabs({
                name:"customerDetail" + id,
                title:title,
                showCloseButton:true,
                url:lc.rootPath("customer/gotoCustomerDetail.action?customerId=" + id)
            });
        }

    </script>
	
</body>
<!-- END BODY -->
</html>
