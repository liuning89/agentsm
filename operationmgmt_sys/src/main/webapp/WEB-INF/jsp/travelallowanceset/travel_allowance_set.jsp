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
    <title>售房列表查询</title>
    <script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
</head>
<body style="height:100%;">
    <div style="width:100%;">
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:99%;">
	            <tr>     
                     <td id="search">
                     		                <span>城市:</span>
				    <input id="cityId" name="cityId" url="areaorg/getCityListByUserId.action" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
				           allowInput="true"
				          />
				                        <a class="mini-button" id="searchBtn" onclick="search()" iconCls="icon-search">查询</a>
				                        <a class="mini-button" id="resetBtn" onclick="reset();" iconCls="icon-cancel">重置</a>
				                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <!--列表-->
    <div class="mini-fit" style="height:400px">
          <div id="dg" class="mini-datagrid" sizeList="[20,30,50,100]" pageSize=20  style="height:100%;" url="travelallow/travelAllowSetList.action" multiSelect="true" showfooter="false">
              <div property="columns">
                  <div type="indexcolumn" width="10"></div>
                  <div field="cityName"  align="left" headeralign="left" width="20">城市</div>
                   <div field="updateTime" dateFormat="yyyy-MM-dd HH:mm:ss" align="left" headeralign="left" width="20">修改时间</div>
                  <div field="publishHouse"  align="left" headeralign="left" width="10">发布房源</div>
                  <div field="imageNotNow"  align="left" headeralign="left" width="10">实景（非现拍）</div>
                   <div field="imageNow"  align="left" headeralign="left" width="20">实景（现拍）</div>
                   <div field="videoNotNow"   align="left" headeralign="left" width="20">视频（非现拍）</div>
                  	<div field="videoNow"  align="left" headeralign="left" width="20">视频（现拍）</div>
                   <div field="favorableComment"  align="left" headeralign="left" width="20">好评</div>
                   <div field="takeHouse"  align="left" headeralign="left" width="20">带盘</div>
                   <div field="invalidHouse"  align="left" headeralign="left" width="20">房源无效成功</div>
                   <div field="status"  align="left" headeralign="left" width="20">状态</div>
                    <div name="action" headeralign="left"  align="left" width="40">操作</div>
              </div>
          </div>
    </div>
</body>
<script type="text/javascript">
	mini.parse();
	var grid = mini.get("dg");
	grid.on("drawcell", function(e) {
		var record = e.record, field = e.field, value = e.value, column = e.column;
		var str='';
		if(record.status==1){
			str="关闭";
		}else{
			str="开启";
		}
		
		if (column.name == "action") {
				var html = lc.strFormat('<a class="mini-button" style="padding-left:10px;padding-right:10px;" onclick="closeStatus({0},{1})">{2}</a>' +
						'<a class="mini-button" style="margin-left:5px;padding-left:10px;padding-right:10px;" onclick="updateDetail({0})">修改</a>'+
						'<a class="mini-button" style="margin-left:5px;padding-left:10px;padding-right:10px;" onclick="operatDetail({0})">操作明细</a>',
						record.id,record.status,str);
			e.cellHtml = html;
		}else if(field=='status'){
			if(value == "1"){
				e.cellHtml = "开启";
			}else if(value=="0"){
				e.cellHtml="关闭";
			}
		}
	});
	grid.load();
	function closeStatus(id,status){
		var str = "确定要设置为";
		if(status == 1)
			str += "关闭吗？";
		else
			str += "开启吗？";
		mini.confirm(str,"提示",function(e){
			if(e == "ok"){
				  $.ajax({
		                url: "travelallow/updateStatus.action?status="+status+"&id="+id,
						type: 'post',
		                dataType:'json',
		                success: function (text) {
		                	mini.alert("操作成功");
		                	grid.reload();
		                },
		                error: function (jqXHR, textStatus, errorThrown) {
		                    alert(jqXHR.responseText);
		                    CloseWindow();
		                }
		            });
			}
		});
	}
	function updateDetail(id){
		mini.open({
            url: "travelallow/updateDetailPage.action?id="+id,
            title: "修改",
            width: 600, 
            height:500,
            ondestroy: function (action) {
            	if(action=='save'){
            		grid.load();
            	}
            
            }
        });
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

	function operatDetail(id){
		mini.open({
            url: "travelallow/lookUpdateRecordPage.action?id="+id,
            title: "修改",
            width: 480, 
            height:300,
            ondestroy: function (action) {
            	if(action=='save'){
            		
            	}
            
            }
        });
	}
</script>
</html>
