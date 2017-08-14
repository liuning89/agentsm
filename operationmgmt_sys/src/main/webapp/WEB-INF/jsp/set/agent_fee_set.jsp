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
    <!--列表-->
    
    <div class="mini-fit" style="height:400px">
          <div id="dg" class="mini-datagrid" sizeList="[20,30,50,100]" showPager="false" pageSize=20 style="height:100%;" url="amoutset/agentFeeSetList.action" multiSelect="true" showfooter="false">
              <div property="columns">
                  <div type="indexcolumn" width="10"></div>
                  <div field="type"  align="left" headeralign="left" width="30">抢单渠道</div>
                  <div field="consumecoin"  align="left" headeralign="left" width="50">消耗悟空币</div>
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
				var html = lc.strFormat('<a class="mini-button" style="padding-left:10px;padding-right:10px;" onclick="updateValue({0})">修改</a>' +
						'<a class="mini-button" style="margin-left:5px;padding-left:10px;padding-right:10px;" onclick="lookRecord({0})">查看明细</a>',
						record.id,e.rowIndex);
			e.cellHtml = html;
		}else if(field=='type'){
			if(value == "1"){
				e.cellHtml = "微聊";
			}else if(value=="2"){
				e.cellHtml="约看";
			}else{
				e.cellHtml="悬赏";
			}
			
		}
	});
	
	
	function updateValue(id) {
		 mini.open({
             url: "amoutset/updateFeePage.action?id="+id,
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
	function lookRecord(id){
		mini.open({
            url: "amoutset/lookUpdateRecordPage.action?id="+id,
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

</script>
</html>
