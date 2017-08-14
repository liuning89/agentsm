<%@page import="java.util.Date"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
    Date date=new Date();//取时间
    Date d = date;
    Calendar calendar = Calendar.getInstance();
    
    calendar.setTime(date);
    d = calendar.getTime();
    calendar.add(Calendar.MONTH, -1);
    
    date  = calendar.getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = formatter.format(date);
    String dString = formatter.format(d);
    String startDate = dateString + " 00:00:00";
    String endDate = dString + " 23:59:59";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<html>
<head path="<%=basePath%>">
<base href="<%=basePath%>" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script src="scripts/boot.js" type="text/javascript"></script>
</head>
<body style="height: 100%;" path="<%=basePath%>">
<!-- 搜索条件 -->
<div style="width:100%;">
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            	
			<table spellcheck="false">
                        <tr>
                           <td>城市:</td>
                           <td>
                               <input id="cityId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
                                        url="${ctx}/areaorg/getListByLogin.action?level=90&cid=1" value="" showNullItem="true"  allowInput="true"
                                        onvalidation="onComboValidation" onvaluechanged="onCityChanged" emptyText="请选择" nullItemText="请选择" />
                                   
                           </td>
                           <td>区域:</td>
                           <td>  
                                 <input id="areaId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
                                    url="${ctx}/areaorg/getListByLogin.action?level=1&cid=2" value="" showNullItem="true"  allowInput="true"
                                    onvalidation="onComboValidation" onvaluechanged="onAreaChanged" emptyText="请选择" nullItemText="请选择"/>
                         </td>
                         <td>门店:
                           <input id="storeId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
                                                url="${ctx}/areaorg/getListByLogin.action?level=1&cid=3" value="" showNullItem="true"  allowInput="true"
                                                onvalidation="onComboValidation" onvaluechanged="onStoreChange"  emptyText="请选择" nullItemText="请选择"/>
                          
                  </td>
                  <td>经纪人:</td>
                         <td>
                       
                               <input id="newAgentId" class="mini-combobox" style="width:135px;" textField="name" valueField="id" 
                                                    value="" showNullItem="true"  allowInput="true"
                                                    onvalidation="onComboValidation"  emptyText="请选择" nullItemText="请选择"/></td>
                              </td>
                           
                       </tr>
                       <tr >
                           <td>开始时间:</td>
                           <td colspan="1">
							<input id="queryTimeBegin" class="mini-datepicker" value="<%=startDate %>" format="yyyy-MM-dd" timeFormat="H:mm:ss" style="width:135px;" showTime="true" showOkButton="true" showClearButton="false"/>
       
                           </td>
                           <td>结束时间:</td>
                           <td colspan="1">  
                              <input id="queryTimeEnd" class="mini-datepicker" value="<%=endDate %>" ondrawdate="onDrawDate" format="yyyy-MM-dd" timeFormat="H:mm:ss" style="width:135px;" showTime="true" showOkButton="true" showClearButton="false"/>
                      </td>
                       <td colspan="1">
                               <input type="button" class="btn btn-info btn-sm" value="查询" onclick="doSearch();" />
                               <input type="button" class="btn btn-info btn-sm" value="重置" onclick="restFrom();" />
                               <input type="button" class="btn btn-info btn-sm" value="导出" onclick="exportExcel();" />
                           </td>  
                         </tr>
				</table>

         
        </div>
    </div>
	<!--列表-->
	<div id="housefollowUpDiv" class="mini-fit"  style="background:red;height:50%;">
		<div id="dg" class="mini-datagrid" style="height: 100%;" pageSize='20' sizeList="[20,30,50,100]" url="${ctx}/transferMng/getPubReportList.action"  idField="id" allowResize="true"  multiSelect="true" showfooter="false">
		<div property="columns">
			 <div header="基本信息" headerAlign="center">
                    <div property="columns">
                        <div field="reportDate" headerAlign="center" align="center" width="50" allowSort="true">日期</div>
                        <div field="cityName" headerAlign="center" align="center" width="50" allowSort="true">城市</div>
                        <div field="areaName" headerAlign="center" align="center" width="50" allowSort="true">区域</div>
                        <div field="storeName" headerAlign="center" align="center" width="50" allowSort="true">门店</div>
                        <div field="agentName" headerAlign="center" align="center" width="50" allowSort="true">经纪人</div>
                    </div>
                </div>	
                 <div header="公客" headerAlign="center">
                    <div property="columns">
                        <div field="areapub" headerAlign="center" align="center" width="50" allowSort="true">区公客数</div>
                        <div field="storepub" headerAlign="center" align="center" width="50" allowSort="true">店公客数</div>
                    </div>
                </div>					
 				<div header="区公客" headerAlign="center">
                    <div property="columns">
                        <div field="arearl" headerAlign="center" align="center" width="50" allowSort="true">认领数</div>
                        <div field="viewarea" headerAlign="center" align="center" width="50" allowSort="true">查看手机数</div>
                    </div>
                </div>	
                <div header="店公客" headerAlign="center">
                    <div property="columns">
                        <div field="storerl" headerAlign="center" align="center" width="50" allowSort="true">认领数</div>
                        <div field="viewstore" headerAlign="center" align="center" width="50" allowSort="true">查看手机数</div>
                    </div>
                </div>	
                </div>
		</div>
	</div>
</body>
<script type="text/javascript">
	mini.parse();
	var grid = mini.get("dg");
   
	grid.on("drawcell",function(e) {
						
					});
	var cityCode = mini.get("cityId").getFormValue();//城市
	
	var areaCode = mini.get("areaId").getFormValue();//区域
	var storeCode = mini.get("storeId").getFormValue();//门店
	var agentId = mini.get("newAgentId").getFormValue();//经纪人
	
	var queryTimeBegin = mini.get("queryTimeBegin").getFormValue();//开始时间
	var queryTimeEnd = mini.get("queryTimeEnd").getFormValue();//结束时间
	
    grid.load({ cityCode:cityCode,areaCode: areaCode, storeCode:storeCode, agentId: agentId,queryTimeBegin:queryTimeBegin,queryTimeEnd:queryTimeEnd});
	
	//grid.load({ cityId: city, areaId: areaId, storeId: storeId, agentId: agentId,queryTimeBegin:queryTimeBegin,queryTimeEnd:queryTimeEnd});
	onCityChanged();
	
	function doSearch(){
		var cityCode = mini.get("cityId").getFormValue();//城市
		var areaCode = mini.get("areaId").getFormValue();//区域
		var storeCode = mini.get("storeId").getFormValue();//门店
		var agentId = mini.get("newAgentId").getFormValue();//经纪人
		var queryTimeBegin = mini.get("queryTimeBegin").getFormValue();//开始时间
		var queryTimeEnd = mini.get("queryTimeEnd").getFormValue();//结束时间
		if(queryTimeEnd < queryTimeBegin){
			mini.alert("结束时间不能小于开始时间!");
			return false;
		}

	    grid.load({cityCode:cityCode,areaCode: areaCode, storeCode:storeCode, agentId: agentId,queryTimeBegin:queryTimeBegin,queryTimeEnd:queryTimeEnd});
		
	}
	function restFrom()
    {

    	 var b = mini.get("cityId");
    	
    	 b.setValue("");
         b.setText("选择城市");
    	 
        var a = mini.get("areaId")
        
        a.setValue("");
        a.setText("选择区域");
        
 		var a1 = mini.get("storeId")
        
        a1.setValue("");
        a1.setText("选择门店");
        
 		var a2 = mini.get("newAgentId")
        
        a2.setValue("");
        a2.setText("选择经纪人");
        
        
        var today = new Date();  
        var start=new Date(today.getTime()-30 * 24 * 3600 * 1000);  
        
        var queryTimeBegin = mini.get("queryTimeBegin");//开始时间
		var queryTimeEnd = mini.get("queryTimeEnd");//结束时间

		queryTimeBegin.setValue(start);
		queryTimeEnd.setValue(new Date());
        
    }

////////////////////////////////
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
            
             var newAgentCbo = mini.get("newAgentId");
            newAgentCbo.setUrl("${ctx}/agent/getAgentByStoreOrg.action?cid=2&orgCode="+id)
            
        }
        
        function onStoreChange(e) {
            var storeCbo = mini.get("storeId");
            var newAgentCbo = mini.get("newAgentId");
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
//////////////
        function onComboValidation(e) {
            var items = this.findItems(e.value);
            if (!items || items.length == 0) {
                this.setValue('');
            }
        }
	    function showImgEl(imgUrl){
	        var win = mini.get("showImgWin");
	        var atEl = document.getElementById("housefollowUpDiv");
	        var imgAgreementKey = document.getElementById("imgAgreementKey");
	        imgAgreementKey.src = imgUrl;
//	        document.getElementById("showImgWin").style.width = (img.width + 20) + "px";
//	        document.getElementById("showImgWin").style.height = img.height + "px";
	        win.showAtEl(atEl, {
	            xAlign: "Center",
	            yAlign: "Middle"
	        });
	    }
	    
	    function onDrawDate(e) {
            var date = e.date;
            var d = new Date();

            if (date.getTime() > d.getTime()) {
                e.allowSelect = false;
            }
        }

	function exportExcel(){
        
        var cityCode = mini.get("cityId").getFormValue();//城市
		var areaCode = mini.get("areaId").getFormValue();//区域
		var storeCode = mini.get("storeId").getFormValue();//门店
		var agentId = mini.get("newAgentId").getFormValue();//经纪人
		var queryTimeBegin = mini.get("queryTimeBegin").getFormValue();//开始时间
		var queryTimeEnd = mini.get("queryTimeEnd").getFormValue();//结束时间
        
        
    	window.location.href = "${ctx}/transferMng/exportExcel.action?cityCode=" + cityCode + "&areaCode=" + areaCode
                + "&storeCode=" + storeCode + "&agentId=" + agentId + "&queryTimeBegin=" + queryTimeBegin + "&queryTimeEnd="
                + queryTimeEnd ;
	}
	
	function IsURL(str_url) {
		var sr = /((http|ftp|https|file):\/\/([\w\-]+\.)+[\w\-]+(\/[\w\u4e00-\u9fa5\-\.\/?\@\%\!\&=\+\~\:\#\;\,]*)?)/;
		var re = new RegExp(sr);
		//re.test() 
		if (re.test(str_url)) {
			return (true);
		} else {
			return (false);
		}
	}
</script>
</html>
