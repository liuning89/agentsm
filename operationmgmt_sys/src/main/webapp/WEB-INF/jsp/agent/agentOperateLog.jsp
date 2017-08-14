<%@page import="java.util.Date"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    Date date = new Date();//取时间
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    date = calendar.getTime(); //这个时间就是日期往后推一天的结果
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = formatter.format(date);
    String endDate = dateString;// + " 00:00:00";

    calendar.add(calendar.MONTH,-1);//把日期前推一月.整数往后推,负数往前移动
    date = calendar.getTime();
    String startDate  = formatter.format(date);// + " 23:59:59";
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<!--[if IE 8]>
<html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]>
<html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html>
<!--<![endif]-->
<%-- <jsp:include page="../common/head.jsp"></jsp:include> --%>
<head>
    <title>评价明细</title>
   <!--  <meta http-equiv="content-type" content="text/html; charset=UTF-8" /><link href="../demo.css" rel="stylesheet" type="text/css" /> -->
	<style type="text/css">
		.datagrid-header-rownumber,.datagrid-cell-rownumber{
   			width:60px;
   		}
	</style>
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
    <script type="text/javascript" src="${ctx}/scripts/boot.js"></script>
    <!-- <script src="../../scripts/boot.js" type="text/javascript"></script> -->

        </head>
<!-- BEGIN BODY -->
<body class="page-header-fixed page-sidebar-fixed page-footer-fixed">
<!-- 搜索条件 -->
<div id="housefollowUpDiv" style="width:100%;">
        <!-- 查询条件start -->
       <%--  <div id="search" class="search_form" style="display:block;">
            <div id="searchFrm" style="padding-bottom:5px;">
                <form action="" id="f1">
                    <table spellcheck="false" >
                        <tr>
                            <td style="width: 10px;"></td>
                            <th>城市:</th>
                            <td>
                                <input id="cityId" class="mini-combobox" style="width:135px;" textField="name"  url="${ctx}/areaorg/getListByLogin.action?level=90&cid=1" valueField="id" value="" showNullItem="true" emptyText="全部" nullItemText="全部" allowInput="false" onvaluechanged="onCityChanged" />
                            </td>
                            <td style="width: 10px;"></td>
                            <th>区域:</th>
                            <td>
                               <!--  <input id="areaId" class="mini-combobox" style="width:135px;" textField="name" valueField="id" allowInput="false" onvaluechanged="onAreaChanged" emptyText="请选择" nullItemText="请选择" />
                                --> 
                                
                                <input id="areaId" class="mini-combobox" style="width:135px;" textField="name" valueField="id" url="${ctx}/areaorg/getListByLogin.action?level=1&cid=2" value="" showNullItem="true"  allowInput="true"
                                    onvalidation="onComboValidation" onvaluechanged="onAreaChanged" emptyText="请选择" nullItemText="请选择"/>
                                
                                
                            </td>
                            <td style="width: 10px;"></td>
                            <th>门店:</th>
                            <td>
                                <!-- <input id="storeId" class="mini-combobox" style="width:135px;" textField="name" valueField="id" value="" allowInput="false"  onvaluechanged="onStoreChange"  /> -->
                                
                                <input id="storeId" class="mini-combobox" style="width:135px;" textField="name" valueField="id" url="${ctx}/areaorg/getListByLogin.action?level=1&cid=3" value="" showNullItem="true"  allowInput="true"
                                                onvalidation="onComboValidation" onvaluechanged="onStoreChange"  emptyText="请选择" nullItemText="请选择"/>
                                
                                
                            </td>
                            <td style="width: 10px;"></td>
                            <th>经纪人:</th>
                            <td>
                                <input id="agentId" class="mini-combobox" style="width:150px;" textField="name" valueField="id" value="" showNullItem="true"  allowInput="true" onvalidation="onComboValidation" emptyText="请选择" nullItemText="请选择" />
<!--                                 <input id="agentId" class="mini-combobox" style="width:135px;" textField="name" valueField="id" value="" showNullItem="true"  allowInput="true" onvalidation="onComboValidation"  emptyText="请选择" nullItemText="请选择"/></td>
 -->                                
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 10px;"></td>
                            <th>开始时间:</th>
                            <td>
                                <input id="queryTimeBegin" class="mini-datepicker" value="<%=startDate %>" format="yyyy-MM-dd" timeFormat="H:mm:ss" ondrawdate="onDrawDate" showTime="true" showOkButton="true" showClearButton="false"/>
                            </td>
                            <td style="width: 10px;"></td>
                            <th>结束时间:</th>
                            <td>
                                <input id="queryTimeEnd" class="mini-datepicker" value="<%=endDate %>" format="yyyy-MM-dd" timeFormat="H:mm:ss" showTime="true" ondrawdate="onDrawDate" showOkButton="true" showClearButton="false"/>
                            </td>
                            <td style="width: 50px;"></td>
                            <td width="50px">
                                <input type="button" class="btn btn-info btn-sm" value="查询" onclick="search();"/>
                            </td>
                            <td colspan="4">
                                <input type="button" class="btn btn-info btn-sm" value="重置" onclick="fromReset();"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div> --%>
        <!-- 查询条件end -->
    </div>


    <div class="mini-fit" style="background:red;height:100px;">
	<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" 
	    url="../agent/agentLogList.action"  idField="id" sizeList="[20,30,50,100]" pageSize="20">
	    <div property="columns">
	        <div type="indexcolumn" ></div>
	           
	        <div field="name" width="120" renderer="onEvaluateGood" align="center" headerAlign="center" allowSort="true">经纪人</div>     
	                               
	        <div field="houseId" width="120" renderer="onEvaluateGood" align="center" headerAlign="center" allowSort="true">房源ID</div>
	        <div field="before" width="100" renderer="onEvaluateBad" align="center" headerAlign="center">修改前</div>
	        <div field="after" width="100" align="center" headerAlign="center" allowSort="true">修改后</div>        
	        <div field="createTime" width="120" headerAlign="center" align="center" renderer="onTimeRenderer" allowSort="true">时间</div>                         
	        <div field="editType" width="100" align="center" headerAlign="center" allowSort="true"  dataType="string">描述</div>
	    </div>
	</div>   
   </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load(); ///初始加载...带上传过来的参数.
        
		/* function search() {
        	
	 	 	var cityId = mini.get("cityId").getValue();
	 	 	var areaId = mini.get("areaId").getValue();
	 	 	var storeId = mini.get("storeId").getValue();
	 	 	var agentId = mini.get("agentId").getValue();
	 	 	
            grid.load({ cityId: cityId,areaId:areaId,storeId:storeId,agentId:agentId});
        } */
        grid.on("drawcell", function (e) {
      	  var record = e.record,
	              column = e.column,
	              field = e.field,
	              value = e.value;
      	  
      	if (field  == "name") {
      		
    		var realName = record.name;
    		if(realName != null && realName != "" && record.storeName != null && record.storeName != ""){
        		e.cellHtml = realName + "（" + record.storeName+")";
    		}else if(realName != null && realName != ""){
    			e.cellHtml = realName + "";
    		}else if(record.storeName != null && record.storeName != ""){
    			e.cellHtml = "" + record.deptName;
    		}else{
    			e.cellHtml = "";
    		}
    		
        }
      	
      	if (field  == "editType") {
      		//（1.有效 无效 2.价格 3.面积 4.房东认领 5.删除照片 6.添加照片 7.添加跟进） ',
			if(value == 1){
				e.cellHtml = "房源状态";
			}else if(value == 2){
				e.cellHtml = "房屋价格";
			}else if(value == 3){
				e.cellHtml = "房屋面积";
			}else if(value == 4){
				e.cellHtml = "房东认领";
			}else if(value == 5){
				e.cellHtml = "删除照片";
			}else if(value == 6){
				e.cellHtml = "添加照片";
			}else if(value == 7){
				e.cellHtml = "添加跟进";
			}
			else if(value == 8){
				e.cellHtml = "房屋朝向";
			}else if(value == 9){
				e.cellHtml = "房屋装修";
			}else if(value == 10){
				e.cellHtml = "房屋楼层";
			}else if(value == 11){
				e.cellHtml = "房屋卖点";
			}else if(value == 12){
				e.cellHtml = "房屋户型";
			}else if(value == 13){
				e.cellHtml = "各付价";
			}else if(value == 14){
				e.cellHtml = "佣金";
			}
      		
          }
      	
      	if(field == "before"){
      		if(record.editType == 1){
      			if(value == 1){
      				e.cellHtml = "有效";
      			}else{
      				e.cellHtml = "无效";
      			}
				
			}else if(record.editType == 2){
				e.cellHtml = "房源价格："+value;
			}else if(record.editType == 3){
				e.cellHtml = "房屋面积："+ value;
			}else if(record.editType == 4){
				e.cellHtml = "房东认领";
			}else if(record.editType == 5){
				
				e.cellHtml = "删除照片";
			}else if(record.editType == 6){
				e.cellHtml = "添加照片";
			}else if(record.editType == 7){
				e.cellHtml = "添加跟进";
			}else if(record.editType == 8){
				if(value == 1){
					e.cellHtml = "房屋朝向:东";
				}else if(value == 2){
					e.cellHtml = "房屋朝向:南";
				}else if(value == 3){
					e.cellHtml = "房屋朝向:西";
				}else if(value == 4){
					e.cellHtml = "房屋朝向:北";
				}else if(value == 5){
					e.cellHtml = "房屋朝向:西南";
				}else if(value == 6){
					e.cellHtml = "房屋朝向:东南";
				}else if(value == 7){
					e.cellHtml = "房屋朝向:东北";
				}else if(value == 8){
					e.cellHtml = "房屋朝向:西北";
				}else if(value == 9){
					e.cellHtml = "房屋朝向:南北";
				}
				
			}else if(record.editType == 9){
				
				if(value == 1){
					
					e.cellHtml = "房屋装修:毛坯";
					
				}else if(value == 2){
					e.cellHtml = "房屋装修:简装";
					
				}else if(value == 3){
					e.cellHtml = "房屋装修:中装";
					
				}else if(value == 4){
					e.cellHtml = "房屋装修:精装";
					
				}else if(value == 5){
					e.cellHtml = "房屋装修:豪装";
					
				}
				
			}else if(record.editType == 10){
				e.cellHtml = "房屋楼层:" + value;
			}else if(record.editType == 11){
				e.cellHtml = "房屋卖点:" + value;
			}else if(record.editType == 12){
				e.cellHtml = "房屋户型:" + value;
			}else if(record.editType == 13){
				e.cellHtml = "各付价:" + value;
			}else if(record.editType == 14){
				e.cellHtml = "佣金:" + value;
			}
      	}
    	if(field == "after"){
      		if(record.editType == 1){
      			if(value == 1){
      				e.cellHtml = "有效";
      			}else{
      				e.cellHtml = "无效";
      			}
			}else if(record.editType == 2){
				e.cellHtml = "房源价格："+value;
			}else if(record.editType == 3){
				e.cellHtml = "房屋面积："+ value;
			}else if(record.editType == 4){
				e.cellHtml = "房东认领";
			}else if(record.editType == 5){
				e.cellHtml = "删除照片";
			}else if(record.editType == 6){
				e.cellHtml = "添加照片";
			}else if(record.editType == 7){
				e.cellHtml = "添加跟进";
			}else if(record.editType == 8){
				
				if(value == 1){
					e.cellHtml = "房屋朝向:东";
				}else if(value == 2){
					e.cellHtml = "房屋朝向:南";
				}else if(value == 3){
					e.cellHtml = "房屋朝向:西";
				}else if(value == 4){
					e.cellHtml = "房屋朝向:北";
				}else if(value == 5){
					e.cellHtml = "房屋朝向:西南";
				}else if(value == 6){
					e.cellHtml = "房屋朝向:东南";
				}else if(value == 7){
					e.cellHtml = "房屋朝向:东北";
				}else if(value == 8){
					e.cellHtml = "房屋朝向:西北";
				}else if(value == 9){
					e.cellHtml = "房屋朝向:南北";
				}
				
			}else if(record.editType == 9){
				if(value == 1){
					
					e.cellHtml = "房屋装修:毛坯";
					
				}else if(value == 2){
					e.cellHtml = "房屋装修:简装";
					
				}else if(value == 3){
					e.cellHtml = "房屋装修:中装";
					
				}else if(value == 4){
					e.cellHtml = "房屋装修:精装";
					
				}else if(value == 5){
					e.cellHtml = "房屋装修:豪装";
					
				}
			}else if(record.editType == 10){
				e.cellHtml = "房屋楼层:" + value;
			}else if(record.editType == 11){
				e.cellHtml = "房屋卖点:" + value;
			}else if(record.editType == 12){
				e.cellHtml = "房屋户型:" + value;
			}
      	}
      	
      });

        
        function onTimeRenderer(e) {
            var value = e.value;
            value = new Date(value); 
            if (value) return mini.formatDate(value, 'yyyy-MM-dd HH:mm');
            return "";
        }
        /* 城市下啦 */
    /* function onCityChanged() {
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
        var newAgentCbo = mini.get("agentId");
        newAgentCbo.setUrl("${ctx}/agent/getAgentByStoreOrg.action?cid=1&orgCode="+id) 
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
        
         var newAgentCbo = mini.get("agentId");
        newAgentCbo.setUrl("${ctx}/agent/getAgentByStoreOrg.action?cid=2&orgCode="+id)
        
    }
    
    function onStoreChange(e) {
    	 var storeCbo = mini.get("storeId");
         var newAgentCbo = mini.get("agentId");
         var id = storeCbo.getValue();
         newAgentCbo.select(0);
         var url = "${ctx}/agent/getAgentByStoreOrg.action?cid=3";
         if(id != null && id != "")
         {
             url += "&orgCode=" + id;
         }
         newAgentCbo.setUrl(url);
         newAgentCbo.select(0);
    }
        ///////////////////////////
        
        
    function onComChanged(selectedValue) {
        var area = mini.get("areaId");
        var code = area.getValue();
        var url = "${ctx}/areaorg/getList.action";
        if(code != null && code != ""){
            url += "?level=60&parentId=" + code;
        }
//        alert(selectedValue);
        var store = mini.get("storeId");
        store.setUrl(url);
        store.setData([{code:'',name:'全部'}].concat(store.getData()));
        store.select(0);
    }

    function onDeptChanged(selectedValue) {
        var store = mini.get("storeId");
        var code = store.getValue();
        var url = "${ctx}/lfEmployee/selectEmployeeByOrgId.action";
        if(code != null && code != ""){
            url += "?code=" + code;
        }
        var agent = mini.get("agentId");
        agent.setUrl(url);
        agent.setData([{id:'',name:'全部'}].concat(agent.getData()));
        agent.select(0);
    } */

    function onDrawDate(e) {
        var date = e.date;
        var d = new Date();
        if (date.getTime() > d.getTime()) {
            e.allowSelect = false;
        }
    }
        
        
        
    </script>
</body>
</html>