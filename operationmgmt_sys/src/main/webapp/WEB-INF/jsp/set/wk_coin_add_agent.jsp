<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
  <%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head path="<%=basePath%>">
    <base href="<%=basePath%>" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
<title></title>
</head>
<body style="height:100%;">
<div style="width:100%;">
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:99%;">
	           <tr>     
                     <td id="search">
                    	<span>公司：</span>
                   <input id="companyName" name="companyName" class="mini-textbox" emptyText="请输公司名称" style="width:150px;" onenter="onKeyEnter"/>
                     <span>电话：</span>
                   <input id="mobile" name="mobile" class="mini-textbox" emptyText="请输经纪人电话" style="width:150px;" onenter="onKeyEnter"/>
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
       
				                        <a class="mini-button" id="searchBtn" onclick="search()" iconCls="icon-search">查询</a>
				                        <a class="mini-button" id="resetBtn" onclick="reset();" iconCls="icon-cancel">重置</a>
				                    </td>
                </tr>
            </table>           
        </div>
    </div>
	<div class="mini-fit" style="height:100px">
          <div id="dg" class="mini-datagrid" sizeList="[20,30,50,100]" pageSize=20  style="height:100%;" url="amoutset/getAgentList.action" multiSelect="true" showfooter="false">
              <div property="columns">
                    <div type="checkcolumn" width="7" field="id"></div>
                   <div field="companyName"  align="left" headeralign="left" width="20">公司名称</div>
                  <div field="cityName"  align="left" headeralign="left" width="20">城市</div>
                  <div field="areaOrgName"   align="left" headeralign="left" width="20">区域</div>
                  <div field="storeName"  align="left" headeralign="left" width="10">门店</div>
                  <div field="agentName"  align="left" headeralign="left" width="10">经纪人</div>
              </div>
          </div>
    </div>
	<form id="form" method="post">
        <div style="padding-left:11px; padding-bottom:5px;padding-top:10px;" align="center">
            <table style="table-layout:fixed;padding-top: 5px;">
                   <tr>  
	                  	<td style="width:100px;">赠送悟空币额：</td>
	                    <td>    
	                        <input class="mini-textbox" vtype="int;range:-9999999,9999999" requiredErrorText="请输入-9999999-9999999之间数字" name="coinNum"  onblur="onChangeValue" id="coinNum" required="true"  />
	                    </td>
                    </tr>
                    <tr>
                     <td style="width:10px;"><span style="color: red">总额：</span></td>
	                    <td>    
	                        <input class="mini-textbox" vtype="int" name="totalNum"  id="totalNum" allowInput="false"  />
	                    </td>
	                </tr>
                   <tr>
						<td>赠送原因：</td>
						<td> <input name="giveReason" class="mini-textarea" style="width:300px;height: 60px;" /></td>
					</tr>
            </table>
        </div>
        <div style="text-align:center;">               
            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>       
            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
        </div>        
    </form>
    <script type="text/javascript">
   	 mini.parse();
   	 var grid = mini.get("dg");
   	 grid.load();
   	 function onChangeValue(e){
   		var totalNum = 0;
   		var rows = grid.getSelecteds();
   		if(rows.length>0){
   			totalNum = parseFloat(mini.getbyName("coinNum").getValue())*rows.length;
   		}
   		mini.getbyName("totalNum").setValue(totalNum);
   	 }
        var form = new mini.Form("form");
        function SaveData() {
        	var rows = grid.getSelecteds();
        	var ids =[];
            if (rows.length>0) {
                 for (var i = 0, l = rows.length; i < l; i++) {
                     var r = rows[i];
	                     ids.push(r.id);
                 }
                 var agentIds = ids.join(',');
                 form.validate();
                 if (form.isValid() == false) return;
                 var o=form.getData();
                
                 o['agentIds']=agentIds;
                 lc.mask("正在处理中,请稍后...");
                 $.ajax({
                     url: "amoutset/addByAgentSave.do",
     				type: 'post',
                     dataType:'json',
                     data: o,
                     success: function (text) {
                    	 lc.unmask();
                     	var o = mini.decode(text);
                     	var status = o['status'];
                     	if(status ==1){
                     		mini.alert("添加成功","提示",function(){
                     			CloseWindow("save");
                     		});
                     	}else{
                     		mini.alert(o['message']);	
                     	}
                     },
                     error: function (jqXHR, textStatus, errorThrown) {
                         alert(jqXHR.responseText);
                         CloseWindow();
                     }
                 });
            } else {
                mini.alert("请选中至少一条记录");
            }
            
          }
        
        function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        
        function onOk(e) {
            SaveData();
        }
        
        function onCancel(e) {
            CloseWindow("cancel");
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
        </script>
</body>
</html>