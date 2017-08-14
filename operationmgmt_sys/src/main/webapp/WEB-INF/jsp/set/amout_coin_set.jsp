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
	                 <td>
 	                	<a class="mini-button" id="addbtn" iconCls="icon-add" onclick="add()">新增</a>
	                 </td>
	            </tr>
	            <tr>     
                    <td id="search">
                     <span>有房有客状态：</span>
                        <input name="yfykStatus" id="yfykStatus" class="mini-combobox"  textField="text" valueField="id" value="-1" data="[{id:-1,text:'全部'},{id:1,text:'显示'},{id:0,text:'未显示'}]"/>
                         <span>创建日期时间：</span>
                        <input id="startTime" style="width:120px" name="startTime" class="mini-datepicker"  format="yyyy-MM-dd" showOkButton="true" showClearButton="false"/>-<input name="endTime" id="endTime" allowInput="false" class="mini-datepicker" format="yyyy-MM-dd" showOkButton="true" style="width:120px"  showClearButton="false"/>
                       <span>创建人：</span>
                        <input id="agentName" name="agentName" class="mini-textbox" emptyText="请输操作人名字" style="width:150px;" onenter="onKeyEnter"/>
                        <a class="mini-button" id="searchBtn" onclick="search()" iconCls="icon-search">查询</a>
                        <a class="mini-button" id="resetBtn" onclick="reset();" iconCls="icon-cancel">重置</a>
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <!--列表-->
    
    <div class="mini-fit" style="height:400px">
          <div id="dg" class="mini-datagrid" sizeList="[20,30,50,100]" pageSize=20 showPager="false" style="height:100%;" url="amoutset/list.action" multiSelect="true" showfooter="false">
              <div property="columns">
                  <div type="indexcolumn" width="10"></div>
                  <div field="wkCoinDenomination"  align="left" headeralign="left" width="30">悟空币面额</div>
                  <div field="price"  align="left" headeralign="left" width="50">金额</div>
                  <div field="yfykStatus"   align="left" headeralign="left" width="20">有房有客显示状态</div>
                  <div field="agentName"  align="left" headeralign="left" width="10">创建人</div>
                  <div field="createTime"  dateFormat="yyyy-MM-dd HH:mm:ss" align="left" headeralign="left" width="20">创建时间</div>
                  <div name="action" headeralign="left"  align="left" width="40">操作</div>
              </div>
          </div>
    </div>
</body>
<script type="text/javascript">
	mini.parse();
	var grid = mini.get("dg");
	grid.load();
	grid.on("drawcell", function(e) {
		var record = e.record, field = e.field, value = e.value, column = e.column;
		if (column.name == "action") {
			var html='';
			if(record.yfykStatus == 0){
				 html = lc.strFormat('<a class="mini-button" style="padding-left:10px;padding-right:10px;" onclick="updateStatus({0},{1})">显示</a>' ,
						record.id,record.yfykStatus,e.rowIndex);
			}else{
				 html = lc.strFormat(
						'<a class="mini-button" style="padding-left:10px;padding-right:10px;" onclick="updateStatus({0},{1})">隐藏</a>'
						+
						'<a class="mini-button" style="margin-left:5px;padding-left:10px;padding-right:10px;" onclick="updateValue({0})">修改</a>',
						record.id,record.yfykStatus,e.rowIndex);
			}
			
			e.cellHtml = html;
		}else if(field=='yfykStatus'){
			if(value == "0"){
				e.cellHtml = "未显示";
			}else{
				e.cellHtml="显示";
			}
		}
	});
	
	
	function updateStatus(id, status) {
		var view = status==1?"确定隐藏":"确定显示？";
		var str = status == 1 ? 0 : 1;
		$.ajax({
			url : "amoutset/getCountNum.do?status=1",
			type : 'post',
			cache : false,
			success : function(text) {
				debugger;
				if(str==0 && text.data<=6){
					mini.alert("至少有6条显示记录","提示");
				}else if(str==1 && text.data==9){
					mini.alert("至多显示9条记录","提示");
				}else{
			        mini.confirm(view, "提示",
			                function (action) {
			                    if (action == "ok") {
									$.ajax({
										url : "amoutset/updateStatus.do",
										type : 'post',
										data : {
											id : id,
											yfykStatus : str
										},
										cache : false,
										success : function(text) {
											if (text.message == 'success')
												grid.reload();
										},
										error : function(jqXHR, textStatus, errorThrown) {
											mini.alert(jqXHR.responseText);
										}
									});
			                    } 
			                }
			            );
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				mini.alert(jqXHR.responseText);
			}
		});
	};
	function updateValue(id) {
		 mini.open({
             url: "amoutset/updatePage.action?id="+id,
             title: "修改",
             width: 480, 
             height:300,
             ondestroy: function (action) {
             	if(action=='save'){
             		grid.load();
             	}
             }
         });
	}
	function add(){
		mini.open({
            url: "amoutset/addPage.action",
            title: "修改",
            width: 480, 
            height:300,
            ondestroy: function (action) {
            	if(action=='save'){
            		grid.load();
            	}
            
            }
        });
	}
	function search(){
		var startTime = mini.get("startTime");
		var endTime = mini.get("endTime");
		if (startTime.getValue() != '' && endTime.getValue() != '') {
			if (endTime.getValue().getTime() < startTime.getValue()
					.getTime()) {
				mini.alert("结束时间不可小于开始时间");
				return;
			}
		}
		var form = new mini.Form("#search");
		var data = form.getData(true);
		lc.removeObjectEmptyValueProperty(data);
		grid.load(data);
	}
	
	function reset(){
		var form = new mini.Form("#search");
		form.reset();
		mini.get("yfykStatus").setValue(-1);
		
	}
</script>
</html>
