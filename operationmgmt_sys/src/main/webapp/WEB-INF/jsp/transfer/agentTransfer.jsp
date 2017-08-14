<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>经纪人资源转移</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
    <script src="${ctx}/scripts/miniui/miniui.js" type="text/javascript"></script>
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
<body >
    <div style="padding-bottom:5px;">
        <span style="font-size: 20px;">转出</span>
        <br/>
        <span>城市<span style="color:red">*</span></span>
       <!--  <input name="cityId" id="cityId" class="mini-combobox"  textField="text" valueField="id" data="[{id:0,text:'全国'}]" value="0"/> -->
        
         <input id="cityId" class="mini-combobox" style="width:135px;" textField="name" valueField="id" 
              url="${ctx}/areaorg/getFranchiseeListByLogin.action?level=90" value="" showNullItem="true"  allowInput="true"
              onvalidation="onComboValidation" onvaluechanged="onCityChanged" emptyText="请选择" nullItemText="请选择" />
                         

        <span>合作伙伴区域<span style="color:red">*</span></span>
        <!-- <input name="joinId" id="joinId" class="mini-combobox"  textField="text" valueField="id" data="[{id:0,text:''}]" value="0"/> -->
         <input id="areaId" class="mini-combobox" style="width:135px;" textField="name" valueField="id" 
          url="${ctx}/areaorg/getFranchiseeListByLogin.action?level=1" value="" showNullItem="true"  allowInput="true"
          onvalidation="onComboValidation" onvaluechanged="onAreaChanged" emptyText="请选择" nullItemText="请选择"/>
                         

	<br />
        <span>门店&nbsp&nbsp</span>
		 <input id="storeId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
              url="${ctx}/areaorg/getFranchiseeListByLogin.action?level=1" value="" showNullItem="true"  allowInput="true"
              onvalidation="onComboValidation"  emptyText="请选择" nullItemText="请选择"/>

						
        <input type="button" value="转出" onclick="openTransfer()"/>
        <input type="button" value="查找" onclick="search()"/>
    </div>
<!--列表-->
		<div class="mini-fit" style="background:red;height:50%;">
			<div id="dg" class="mini-datagrid" sizeList="[20,30,50,100]" pageSize="20" style="height:100%;" url="${ctx}/transferMng/getTransferList.action"
				multiSelect="true" showfooter="false">
				<div property="columns">
					<div type="checkcolumn" width="10"></div>
					
					<div field="townname" align="left" headeralign="left" width="55">板块</div>
					<div field="houseCount" align="left" headeralign="left" width="35">房源数</div>
					
					<div field="estateCount" align="left" headeralign="left" width="35">小区数</div>
					<div field="customerCount" align="left" headeralign="left" width="60">客源</div>
					<div field="cusSeeCount" align="left" headeralign="left" width="40">未带看</div>
					
					<div field="cusGuestCount" align="left" headeralign="left" width="35">专属经纪人</div>
					<div field="houseSeeCount" align="left" headeralign="left" width="60">带看</div>
					<div field="requirementCount" align="left" headeralign="left" width="40">客户需求</div>
				</div>
			</div>
		</div>
		
		<!--列表-->
		<%-- <div style="background:red;height:50%;">
			<div id="dg1" class="mini-datagrid" sizeList="[20,30,50,100]" pageSize="20" style="height:100%;" url="${ctx}/transferMng/getCustomerList.action"
				multiSelect="true" showfooter="false">
				<div property="columns">
					<div type="checkcolumn" width="10"></div>
					<div field="storeNmae" align="left" headeralign="left" width="35">门店</div>
					<div field="estateCount" align="left" headeralign="left" width="35">小区数</div>
					<div field="customerCount" align="left" headeralign="left" width="60">客源</div>
					<div field="cusSeeCount" align="left" headeralign="left" width="40">未带看</div>
				</div>
			</div>
		</div> --%>
    <script type="text/javascript">
        mini.parse();
       /*  mini.get("auditStatusId").select(0); */
        var grid = mini.get("dg");
       var grid1 = mini.get("dg1");
        grid.on("drawcell", function (e) {
      	  var record = e.record,
	              column = e.column,
	              field = e.field,
	              value = e.value;
      	
      	if (field  == "HouseCount") {
      		/* mini.alert(value); */
      		
          }
      	
      });
  		function search() {
        	
	 	 	var areaCbo = mini.get("areaId");
	 	 	var id = areaCbo.getValue();
	 	 	
	 	 	var sId = mini.get("storeId");
	 	 	var storeId = sId.getValue();
	 	 	
	 	 	if(id == ""){
	 	 		mini.alert("请选择合作伙伴");
	 	 		return false;
	 	 	}
            grid.load({ parentId: id,storeId:storeId});
            grid1.load({ parentId: id,storeId:storeId});
        }
        
        function openTransfer() {
        	
        	var cityId = mini.get("cityId").getFormValue(); 
        	if(cityId == ""){
        		mini.alert("请选择城市");
        		return false;
        	}
        	var areaId = mini.get("areaId").getFormValue(); 
        	if(areaId == ""){
        		mini.alert("请选择合作伙伴");
        		return false;
        	}
        	var storeId = mini.get("storeId").getFormValue();
        	mini.open({
   				url : "${ctx}/transferMng/openTransfer.action?cityId="+cityId+"&areaId="+areaId+"&storeId="+storeId,
   				title : "转入到...",
   				width : 650,
   				height : 250,
   				ondestroy : function(action) {
   					if(action == "save"){
   						//lf.areaOrg.area.list.search();
   					}
   				}
   			});
        }
        
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
            var url = "${ctx}/areaorg/getFranchiseeListByLogin.action?level=70";
            if(id != null && id != "")
            {
                url += "&parentId=" + id;
            }
            areaCbo.setUrl(url);
            areaCbo.select(0);
            
            var storeCbo = mini.get("storeId");
            storeCbo.setUrl("${ctx}/areaorg/getListByLogin.action?level=1");
            storeCbo.select(0);
            /* var newAgentCbo = mini.get("newAgentId");
            newAgentCbo.setUrl("${ctx}/agent/getAgentByStoreOrg.action?orgCode=000") */
        }
        
        function onAreaChanged(e) {
            var areaCbo = mini.get("areaId");
            var storeCbo = mini.get("storeId");
            var id = areaCbo.getValue();
            storeCbo.select(0);
            var url = "${ctx}/areaorg/getFranchiseeListByLogin.action?level=60";
            if(id != null && id != "")
            {
                url += "&parentId=" + id;
            }
            storeCbo.setUrl(url);
            storeCbo.select(0);
            
            /* var newAgentCbo = mini.get("newAgentId");
            newAgentCbo.setUrl("${ctx}/agent/getAgentByStoreOrg.action?orgCode=000") */
            
        }
        
        function onStoreChange(e) {
            var storeCbo = mini.get("storeId");
            var newAgentCbo = mini.get("newAgentId");
            var id = storeCbo.getValue();
            newAgentCbo.select(0);
            var url = "${ctx}/agent/getAgentByStoreOrg.action";
            if(id != null && id != "")
            {
                url += "?orgCode=" + id;
            }
            newAgentCbo.setUrl(url);
            newAgentCbo.select(0);
        }
     
 
/* ********************************************************** */		
    
    </script> 
    <div class="description">
       <%-- <jsp:include page="../common/footer.jsp"></jsp:include> --%>
    </div>
</body>
</html>