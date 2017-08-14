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
                               <input id="cityId" name="cityId" class="mini-combobox" textField="text" valueField="id" onvalidation="onComboValidation" onvaluechanged="onCityChanged" allowInput="true" showNullItem="true"
                                      url="<%=path%>/dicAreaNew/getEnableCityUsedArea.action"/>
                                   
                           </td>
                           <td>区域:
                                     <input id="districtId" name="districtId" class="mini-combobox" textField="text" valueField="id" onvalidation="onComboValidation" onvaluechanged="onAreaChanged" allowInput="true" showNullItem="true"/>
                         </td>
                         <td>板块:

                             <input id="townId" name="townId" class="mini-combobox" textField="text" valueField="id" onvalidation="onComboValidation" onvaluechanged="onStoreChange" allowInput="true" showNullItem="true" />
                  </td>
                           
                           <td>时间:</td>
                           <td colspan="1">
							<input id="queryTimeBegin" class="mini-datepicker" format="yyyy-MM-dd" timeFormat="H:mm:ss" style="width:135px;" showTime="true" showOkButton="true" showClearButton="false"/>
       
                           </td>

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

<div style="text-align: center;color: red"> 说明:当天只能查T-1日24:00点前数据</div>
	<!--列表-->
<!--撑满页面-->
<div class="mini-fit" style="background:red;height:100px;">
    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" url="${ctx}/house/houseDataTown.action"   idField="id" allowResize="false" sizeList="[20,30,50,100]" pageSize="20">
        <div property="columns">
            <div type="indexcolumn" style="display: none"></div>

            <div field="cityName" width="100"  align="center" headerAlign="center">城市</div>
            <div field="areaName" width="100" align="center" headerAlign="center">行政区域</div>
            <div field="townName" width="100" align="center" headerAlign="center">板块</div>
            <div field="sellcount" width="100" align="center" headerAlign="center">有效房源总数(套)</div>
            <div field="auditcount" width="100" align="center" headerAlign="center">待审批下架房源数(套)</div>
            <div field="picturecount" width="100" align="center" headerAlign="center">实景数(套)</div>
            <div field="videocount" width="100" align="center" headerAlign="center">视频数(套)</div>
            <div field="sellPointcount" width="100" align="center" headerAlign="center">描述数(套)</div>
            <div field="commcount" width="100" align="center" headerAlign="center">速销房(套)</div>
            <div field="keycount" width="100" align="center" headerAlign="center">钥匙房(套)</div>
            <div field="houseStatecount" width="100" align="center" headerAlign="center">无效房源数(套)</div>

        </div>
    </div>
</div>
</body>
<script type="text/javascript">
	mini.parse();
	var grid = mini.get("datagrid1");
	
     /* var queryTimeBegin = mini.get("queryTimeBegin").getFormValue();//开始时间  */
     
     var today = new Date();  
     var start=new Date(today.getTime()-1 * 24 * 3600 * 1000);  
     
     var queryTimeBegin = mini.get("queryTimeBegin");//开始时间

		queryTimeBegin.setValue(start);
     
     
    grid.load({queryTimeBegin:queryTimeBegin.getFormValue()});

	//grid.load({ cityId: city, areaId: areaId, storeId: storeId, agentId: agentId,queryTimeBegin:queryTimeBegin,queryTimeEnd:queryTimeEnd});
//	onCityChanged();
	
	function doSearch(){
        var cityCode = mini.get("cityId").getFormValue();//城市

        var areaCode = mini.get("districtId").getFormValue();//区域
        var storeCode = mini.get("townId").getFormValue();//门店


        var queryTimeBegin = mini.get("queryTimeBegin").getFormValue();//开始时间

        grid.load({ cityId:cityCode,areaId: areaCode, townId:storeCode,queryTimeBegin:queryTimeBegin});
		
	}
	function restFrom()
    {

    	 var b = mini.get("cityId");
    	
        b.select(0);
    	 
        var a = mini.get("districtId")
        
        a.select(0);
        
 		var a1 = mini.get("townId")
        
        a1.select(0);
        
 		
        
        
        var today = new Date();  
        var start=new Date(today.getTime()-1 * 24 * 3600 * 1000);  
        
        var queryTimeBegin = mini.get("queryTimeBegin");//开始时间

		queryTimeBegin.setValue(start);
		
        
    }

////////////////////////////////
	   function onCityChanged(e) {
            var cityCbo = mini.get("cityId");
            var areaCbo = mini.get("districtId");
            var id = cityCbo.getValue();
            
            areaCbo.select(0);
            var url = "${ctx}/dicAreaNew/getDicAreaNewByParentId.do?";
            if(id != null && id != "")
            {
                url += "parentId=" + id;
            }
            areaCbo.setUrl(url);
            areaCbo.select(0);

           var areaCbovalue = areaCbo.getValue();
            var townIdCbo = mini.get("townId");
           townIdCbo.setUrl("${ctx}/dicAreaNew/getDicAreaNewByParentId.do?parentId="+areaCbovalue);
           townIdCbo.select(0);

        }
        //区域选择
        function onAreaChanged(e) {
            var districtId = mini.get("districtId");
            var id = districtId.getValue();
            var url = "${ctx}/dicAreaNew/getDicAreaNewByParentId.do?"
            if(id != null && id != ''){
                url += "parentId=" + id;
            }


            var townId = mini.get("townId");


            townId.setUrl(url);
            townId.select(0);

            <%--var townIdCbo = mini.get("townId");--%>
            <%--townIdCbo.setUrl("${ctx}/dicAreaNew/getDicAreaNewByParentId.do?parentId="+townIdCbo);--%>
            <%--townIdCbo.select(0);--%>
        }
        //板块选择
        function onStoreChange(e) {
            <%--var districtId = this.districtId.getValue();--%>
            <%--this.townId.load(lc.rootPath(lc.strFormat("${ctx}/dicAreaNew/getDicAreaNewByParentId.do?parentId={0}",districtId)));--%>
            <%--this.townId.setData([{id:'',text:'所有'}].concat(this.townId.getData()));--%>
            <%--this.townId.select(0);--%>
        }
//////////////
        function onComboValidation(e) {
//            var items = this.findItems(e.value);
//            if (!items || items.length == 0) {
//                this.setValue('');
//            }
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
        
        /* var cityCode = mini.get("cityId").getFormValue();//城市
		var areaCode = mini.get("areaId").getFormValue();//区域
		var storeCode = mini.get("storeId").getFormValue();//门店
		var agentId = mini.get("newAgentId").getFormValue();//经纪人
		var queryTimeBegin = mini.get("queryTimeBegin").getFormValue();//开始时间
		var queryTimeEnd = mini.get("queryTimeEnd").getFormValue();//结束时间 */
		var cityCode = mini.get("cityId").getFormValue();//城市
        var areaCode = mini.get("districtId").getFormValue();//区域
        var storeCode = mini.get("townId").getFormValue();//门店
        var queryTimeBegin = mini.get("queryTimeBegin").getFormValue();//开始时间

    	window.location.href = "${ctx}/house/exportExcel.action?cityId=" + cityCode + "&areaId=" + areaCode
                + "&townId=" + storeCode  + "&queryTimeBegin=" + queryTimeBegin ;
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
