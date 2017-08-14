<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<html>
<head path="<%=basePath%>">
    <base href="<%=basePath%>" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
<head>
    <script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
<title></title>
</head>

<body style="height:100%;" path="<%=basePath%>">
    <div style="width:100%;">
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:99%;">
	                <tr>
                   <td id="search">
                        <span>操作时间：</span>
                        <input id="startTime" style="width:120px" name="startTime" class="mini-datepicker"  format="yyyy-MM-dd" showOkButton="true" showClearButton="false"/>-<input name="endTime" id="endTime" allowInput="false" class="mini-datepicker" format="yyyy-MM-dd" showOkButton="true" style="width:120px"  showClearButton="false"/>
               			 <span>操作人：</span>
                        <input id="agentName" name="agentName" class="mini-textbox" emptyText="请输操作人名字" style="width:150px;" onenter="onKeyEnter"/>
                         <a class="mini-button" onclick="search()" iconCls="icon-search">查询</a>
                         <a class="mini-button" iconCls="icon-cancel" onclick="refreshData();">重置</a>
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <!--列表-->
    <div class="mini-fit" style="height:400px">
          <div id="dg" class="mini-datagrid" sizeList="[20,30,50,100]" pageSize=20 style="height:100%;" url="houseResource/invalidReasonList.do"  showfooter="false">
              <div property="columns">
                <div field="houseId" name="houseId" width="10" headerAlign="center" align="center" >房源ID</div>
                <div field="estateName" width="20" allowSort="true" align="center" headerAlign="center">小区名称</div>
	            <div field="initName" width="30" allowSort="true" align="center" headerAlign="center">房源地址</div>
	            <div field="reason" width="15" headerAlign="center" align="center" allowSort="true">无效原因</div>   
	            <div field="memo" width="15" allowSort="true" headerAlign="center" align="center">说明</div>                                    
   	            <div field="agentName"  name="agentName" width="10" headeralign="center" align="left">操作人</div>
   	            <div field="createTime"  dateFormat="yyyy-MM-dd HH:mm:ss" width="10" headeralign="center" align="left">操作时间</div>
              </div>
          </div>
    </div>
    
</body>
<script type="text/javascript">
		mini.parse();
		var grid = mini.get("dg");
		grid.load();
		grid.on("drawcell",function(e) {
			var record = e.record, field = e.field, value = e.value, column = e.column;
			if(field == "houseId"){
				e.cellHtml = '<a href="javascript:openDetail(' + record.cityId + ',\'' + value  +'\');">'+value+'</a> '
			}
		});
		function refreshData(){
			 mini.getbyName("startTime").setValue(null);
			 mini.getbyName("endTime").setValue(null);
			mini.getbyName("agentName").setValue(null);
		}
		function search() {
		    var agentName=mini.getbyName("agentName").getValue();
		    var startTime=mini.getbyName("startTime").getValue();
		    var endTime=mini.getbyName("endTime").getValue();
		    
		    var form = new mini.Form("#search");
			var data = form.getData(true);
			lc.removeObjectEmptyValueProperty(data);
		    grid.load(data);
		}
		function openDetail(index,houseId){
			
	        var title = houseId + "-房源详情";
			parent.lf.agentsm.index.addTabs({
				name:"HouseDetail"+houseId,
				title:title,
				showCloseButton:true,
				url:lc.rootPath("houseResource/sell/tabPage.action?houseId="+houseId+"&cityId="+index)
			});
		}
</script>
</html>
