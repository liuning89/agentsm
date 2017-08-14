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
                           <td style="width: 10px;"></td>
                           <th>城市:</th>
                           <td>
                               <input id="cityId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
                                        url="${ctx}/areaorg/getListByLogin.action?level=90&cid=1" value="" showNullItem="true"  allowInput="true"
                                        onvalidation="onComboValidation" onvaluechanged="onCityChanged" emptyText="请选择" nullItemText="请选择" />
                                   
                           </td>
                            <td style="width: 10px;"></td>
                           <th>房源ID:</th>
                           <td><input name="houseId" id="houseId" class="mini-textbox" emptyText="房源ID模糊查询" style="width:150px;"/>
                           </td>
                           <td style="width: 10px;"></td>
                           <th>经纪人筛选:</th>
                           <td>  
                                 <input id="areaId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
                                    url="${ctx}/areaorg/getListByLogin.action?level=1&cid=2" value="" showNullItem="true"  allowInput="true"
                                    onvalidation="onComboValidation" onvaluechanged="onAreaChanged" emptyText="请选择" nullItemText="请选择"/>
                         
                           <input id="storeId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
                                                url="${ctx}/areaorg/getListByLogin.action?level=1&cid=3" value="" showNullItem="true"  allowInput="true"
                                                onvalidation="onComboValidation" onvaluechanged="onStoreChange"  emptyText="请选择" nullItemText="请选择"/>
                          
                  
                       
                               <input id="newAgentId" class="mini-combobox" style="width:135px;" textField="name" valueField="id" 
                                                    value="" showNullItem="true"  allowInput="true"
                                                    onvalidation="onComboValidation"  emptyText="请选择" nullItemText="请选择"/></td>
                              </td>
                           
                       </tr>
                       <tr>
                           <td style="width: 10px;"></td>
                           <th>开始时间:</th>
                           <td>
							<input id="queryTimeBegin" class="mini-datepicker" value="<%=startDate %>" format="yyyy-MM-dd" timeFormat="H:mm:ss" style="width:240px;" showTime="true" showOkButton="true" showClearButton="false"/>
       
                           </td>
                           <td style="width: 10px;"></td>
                           <th>结束时间:</th>
                           <td>  
                              <input id="queryTimeEnd" class="mini-datepicker" value="<%=endDate %>" ondrawdate="onDrawDate" format="yyyy-MM-dd" timeFormat="H:mm:ss" style="width:240px;" showTime="true" showOkButton="true" showClearButton="false"/>
                      
                       
                               <input type="button" class="btn btn-info btn-sm" value="查询" onclick="doSearch();" />
                               <input type="button" class="btn btn-info btn-sm" value="重置" onclick="restFrom();" />
                             </td>
                         </tr>
				</table>

         
        </div>
    </div>
	<!--列表-->
	<div id="housefollowUpDiv" class="mini-fit" style="height: 400px">
		<div id="dg" class="mini-datagrid" style="height: 95%;" pageSize='30'
			sizeList="[20,30,50,100]"
			url="houseResource/sell/getLfHouseFollowUpListAll.do"
			multiSelect="true" showfooter="false">
			<div property="columns">
				<div field="createTime" width="20" headerAlign="center" align="left"
					allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">跟进时间</div>
				<div field="houseId" width="10" headerAlign="center" align="left"
					allowSort="true" >房源编号</div>	
					
				<div field="initname" width="10" headerAlign="center" align="left"
					allowSort="true" >房源地址</div>				
				<div field="type" width="15" headerAlign="center" align="center"
					allowSort="true">类型</div>
				<div field="note" width="30" align="left" headerAlign="center">跟进内容</div>
				<div field="storeName" width="15" headerAlign="center"
					align="center">跟进人门店</div>
				<div field="realName" width="15" headerAlign="center" align="center"
					allowSort="跟进人">跟进人</div>

				<div field="shield"  name="shield" width="15" headerAlign="center"  align="center" allowSort="false">操作</div>

			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	mini.parse();
	var grid = mini.get("dg");
   
	grid.on("drawcell",function(e) {
						var typeDict = {
							'1' : '文本',
							'2' : '录音'
						};
						var record = e.record, field = e.field, value = e.value, column = e.column;
						if (field == "type") {
							e.cellHtml = typeDict[value];
						} else if (field == "note") {
							if (record.type == '2' && IsURL(value)) {
								e.cellHtml = '<a href="javascript:download(\''
										+ record.id + '\')">下载</a>' + ' | '
										+ '<a href="javascript:openToShow(\''
										+ record.id + '\');">播放</a>';
							} else {
								e.cellHtml = value;
							}
						}
						if(field == "houseId"){
							e.cellHtml = '<a href="javascript:openDetail(' + record.cityId + ',\'' + value  +'\');">'+value+'</a> '
						}

						if(field == "shield"){
							if(record.shield == 1){
								e.cellHtml = "<a href='javascript:void(0)' onclick='openPush(\"" + record.id+ "\",2)'>屏蔽</a>";
							}else{
								e.cellHtml = "<a href='javascript:void(0)' onclick='openPush(\"" + record.id+ "\",1)'>恢复</a>";
							}
						}
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
		var houseId = mini.get("houseId").getFormValue();
		if(queryTimeEnd < queryTimeBegin){
			mini.alert("结束时间不能小于开始时间!");
			return false;
		}

	    grid.load({houseId:houseId, cityCode:cityCode,areaCode: areaCode, storeCode:storeCode, agentId: agentId,queryTimeBegin:queryTimeBegin,queryTimeEnd:queryTimeEnd});
		
		
		//grid.load({ orgCode:city, cityId: city, areaId: areaId, storeId: storeId, agentId: agentId,queryTimeBegin:queryTimeBegin,queryTimeEnd:queryTimeEnd});
	}
	function restFrom()
    {

		mini.get("houseId").setValue(null);
    	// mini.get("buildingId").select(0);//座栋号
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
    //    var end=new Date(today.getTime()+31 * 24 * 3600 * 1000);  
        
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
///////////////////////////////////////
	    function ajaxShowImgEl(imgAgreementKey) {
	        var url = "${ctx}/agent/getImageKeyObject.action?key=" + imgAgreementKey;
	        $.ajax({
	            url: url,
	            type: "post",
//	            async:true,
	            success: function (text) {
	                eval("var result=" + text);
	                showImgEl(result.data.water_cl);
	            },
	            error: function (jqXHR, textStatus, errorThrown) {
	                alert(jqXHR.responseText);
	            }
	        });
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
	////////////////////////////////////////////
	function openToShow(id) {
		mini.open({
            url: "houseResource/sell/openToShow.do?id="+id,
            title: "音频播放",
			width: 310,
			height: 50,
            ondestroy: function (action) {
            }
        });
	}

	function download(id) {
		var exportframe = $('<iframe style="display:none"/>');
		var url = encodeURI("houseResource/sell/downLfHouseFollowUpVideo.do?id="
				+ id);
		exportframe.attr({
			'src' : url
		});
		$('#housefollowUpDiv').append(exportframe);
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
	function openDetail(index,houseId){
		
		//var row = grid.getRow(index);
        var title = houseId + "-房源详情";
        
    //	parent.lf.agentsm.index.addTabs({
		
		
		//var row = this.grid.getRow(index);
		//var title = "详情";
		parent.lf.agentsm.index.addTabs({
			name:"HouseDetail"+houseId,
			title:title,
			showCloseButton:true,
			url:lc.rootPath("houseResource/sell/tabPage.action?houseId="+houseId+"&cityId="+index)
		});
	}
	function openPush(id,shield){
		lc.mask("正在处理中,请稍后...");
		$.ajax({
			url : "${ctx}/houseResource/openShield.action?id="+id+"&shield="+shield,
			cache : false,
			dataType : 'text',
			success : function(data) {
				if(data == 1){
					lc.unmask();
					mini.alert("操作成功!","成功",function(){doSearch();});
				}else{
					lc.unmask();
					mini.alert("操作失败,请稍后重试!","错误",function(){doSearch();});
				}
			}
		});


	}
</script>
</html>
