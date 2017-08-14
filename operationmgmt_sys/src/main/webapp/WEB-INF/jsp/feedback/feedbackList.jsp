<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>意见反馈</title>
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
        <span>城市</span><input id="cityId" class="mini-combobox" style="width:135px;" textField="name" valueField="code" 
                                        url="${ctx}/areaorg/getListByLogin.action?level=90" value="" showNullItem="true"  allowInput="true"
                                        onvalidation="onComboValidation" onvaluechanged="onCityChanged" emptyText="请选择" nullItemText="请选择" />
        <span>区域</span><input id="areaId" class="mini-combobox" style="width:135px;" textField="name" valueField="code" 
                                    url="${ctx}/areaorg/getListByLogin.action?level=1" value="" showNullItem="true"  allowInput="true"
                                    onvalidation="onComboValidation" onvaluechanged="onAreaChanged" emptyText="请选择" nullItemText="请选择"/>
        <span>门店：</span><input id="storeId" class="mini-combobox" style="width:135px;" textField="name" valueField="code" 
                                                url="${ctx}/areaorg/getListByLogin.action?level=1" value="" showNullItem="true"  allowInput="true"
                                                onvalidation="onComboValidation" onvaluechanged="onStoreChange"  emptyText="请选择" nullItemText="请选择"/>
        <span>经纪人：</span><input id="newAgentId" class="mini-combobox"  style="width:135px;" textField="name" valueField="id" 
                                                    url="${ctx}/agent/getAgentByStoreOrg.action?orgCode=000" value="" showNullItem="true"  allowInput="true"
                                                    onvalidation="onComboValidation"  emptyText="请选择" nullItemText="请选择"/>
        <span>电话：</span><input type="text" id="mobile"  />
        <span>开始时间：</span><input id="createTimestart" class="mini-datepicker" value="" format="yyyy-MM-dd H:mm" timeFormat="H:mm" showTime="true" showOkButton="true" showClearButton="false"/>
        <span>结束时间：</span><input id="createTimeend" class="mini-datepicker" value="" format="yyyy-MM-dd H:mm" timeFormat="H:mm" showTime="true" showOkButton="true" showClearButton="false"/>
        <input type="button" value="查找" onclick="search()"/>
        <input type="button" value="重置" onclick="reset()"/>
    </div>
     <!--撑满页面-->
        <div class="mini-fit" style="background:red;height:100px;">
    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" 
        url="${ctx}/lfAppFeedBack/getFeedBackList.action"  idField="id" allowResize="false"
        sizeList="[20,30,50,100]" pageSize="20">
        <div property="columns">
            <!-- <div type="indexcolumn" style="display: none"></div> -->
            <div field="realName" width="120" headerAlign="center" allowSort="true">反馈人</div>                            
            <div field="storeName" width="100"  align="center" headerAlign="center">门店</div>
            <div field="mobile" width="100" align="center" headerAlign="center">电话</div>
            <div field="createTime" width="120" renderer="onTimeRenderer" align="center" headerAlign="center"  allowSort="false">反馈时间</div>  
            <div field="context" width="100" headerAlign="center" allowSort="false">内容</div>   
        </div>
    </div>   
    </div>
    
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
        function search() {
        	var cityId = mini.get("cityId").getValue();
        	var areaId = mini.get("areaId").getValue();
            var storeId = mini.get("storeId").getValue();
            var agentId = mini.get("newAgentId").getValue();
            var mobile = document.getElementById("mobile").value;
            var createTimestart = mini.get("createTimestart").getFormValue();
            var createTimeend = mini.get("createTimeend").getFormValue();
            grid.load({ cityCode: cityId ,areaCode: areaId, storeCode: storeId, agentId: agentId, mobile: mobile, createTimestart: createTimestart, createTimeend: createTimeend});
        }
        
        
        function reset()
        {
        	 mini.get("cityId").select(0);
             mini.get("areaId").select(0);
             mini.get("storeId").select(0);
             mini.get("newAgentId").select(0);
             document.getElementById("mobile").value = "";
             mini.get("createTimestart").setValue();
             mini.get("createTimeend").setValue();
  
        }
        
        $("#key").bind("keydown", function (e) {
            if (e.keyCode == 13) {
                search();
            }
        });
        
        //////////////////////////////////////////////////
        function onTimeRenderer(e) {
            var value = e.value;
            value = new Date(value); 
            if (value) return mini.formatDate(value, 'yyyy-MM-dd HH:mm');
            return "";
        }
        
        //////////////////////////////////////////////
        function onComboValidation(e) {
            var items = this.findItems(e.value);
            if (!items || items.length == 0) {
               	this.setValue('');
            }
        }
        
        function onCityChanged(e) {
            var cityCbo = mini.get("cityId");
            var areaCbo = mini.get("areaId");
            var id = cityCbo.getValue();
            areaCbo.select(0);
            var url = "${ctx}/areaorg/getListByLogin.action?level=70";
            if(id != null && id != "")
            {
                url += "&parentId=" + id;
            }
            areaCbo.setUrl(url);
            areaCbo.select(0);
            
            var storeCbo = mini.get("storeId");
            storeCbo.setUrl("${ctx}/areaorg/getListByLogin.action?level=1");
            storeCbo.select(0);
            var newAgentCbo = mini.get("newAgentId");
            newAgentCbo.setUrl("${ctx}/agent/getAgentByStoreOrg.action?orgCode=000")
        }
        
        function onAreaChanged(e) {
            var areaCbo = mini.get("areaId");
            var storeCbo = mini.get("storeId");
            var id = areaCbo.getValue();
            storeCbo.select(0);
            var url = "${ctx}/areaorg/getListByLogin.action?level=60";
            if(id != null && id != "")
            {
                url += "&parentId=" + id;
            }
            storeCbo.setUrl(url);
            storeCbo.select(0);
            
            var newAgentCbo = mini.get("newAgentId");
            newAgentCbo.setUrl("${ctx}/agent/getAgentByStoreOrg.action?orgCode=000")
            
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

    </script>

    <div class="description">
       <%-- <jsp:include page="../common/footer.jsp"></jsp:include> --%>
    </div>
</body>
</html>