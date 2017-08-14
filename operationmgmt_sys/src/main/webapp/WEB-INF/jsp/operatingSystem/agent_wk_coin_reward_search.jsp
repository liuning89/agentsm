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
    <title>经纪人悟空币奖励查询</title>
    <script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
</head>
<body style="height:100%;">
     <div style="width:100%;">
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:99%;">
	            <tr>     
                     <td id="search">
                     <span>经纪人手机号：</span>
                     <input id="agentPhoneNumber" name="agentPhoneNumber" class="mini-textbox" emptyText="精确搜索" allowInput="true">
				     <span>奖励时间：</span>
                     <input id="startTime" style="width:120px" name="startTime" class="mini-datepicker"  format="yyyy-MM-dd" showOkButton="true" showClearButton="false"/>-<input name="endTime" id="endTime" allowInput="false" class="mini-datepicker" format="yyyy-MM-dd" showOkButton="true" style="width:120px"  showClearButton="false"/>
                     <span>奖励原因：</span><input id="rewardReson" name="rewardReson" class="mini-combobox" textField="text" value="0" style="width:150px" valueField="id" data="[{id:0,text:'全部'},{id:6,text:'发布房源录音审核成功'},{id:7,text:'房源照片审核通过'},{id:8,text:'房源视频审核通过'},{id:9,text:'获得用户好评'},{id:10,text:'无效房源成功'}]"/>
       				<span>公司:</span>
                    <input id="companyId" onvalidation="onComboValidation" name="companyId" class="mini-combobox" style="width:135px;" textField="text" valueField="id"
	              	 url="amoutset/getFranchiseeList.action" value="" showNullItem="true"  allowInput="true"
	             	  emptyText="请选择" nullItemText="请选择" />
				    <a class="mini-button" id="searchBtn" onclick="search()" iconCls="icon-search">查询</a>
				    <a class="mini-button" id="resetBtn" onclick="reset();" iconCls="icon-cancel">重置</a>
				    </td>
                </tr>
            </table>           
        </div>
    </div>
    <div class="mini-fit" style="height:400px">
          <div id="dg" class="mini-datagrid" sizeList="[20,30,50,100]" pageSize=20  style="height:100%;" url="operatingSystem/operationManagement/queryAgentWKCoinRewardList.action" multiSelect="true" showfooter="false">
              <div property="columns">
                  <div field="companyName"  align="left" headeralign="left" width="20">公司</div>
                  <div field="cityName"  align="left" headeralign="left" width="20">城市</div>
                  <div field="areaOrgName"   align="left" headeralign="left" width="20">区域</div>
                  <div field="storeName"  align="left" headeralign="left" width="10">门店</div>
                  <div field="agentName"  align="left" headeralign="left" width="10">经纪人</div>
                  <div field="wuKongCoin"  align="left" headeralign="left" width="20">获赠悟空币币额</div>
                  <div field="rewardTime" dateFormat="yyyy-MM-dd HH:mm:ss" align="left" headeralign="left" width="20">奖励时间</div>
                  <div field="rewardReson"  align="left" headeralign="left" width="20">奖励原因</div>
              </div>
          </div>
    </div>
</body>
<script type="text/javascript">
	mini.parse();
	var grid = mini.get("dg");
	grid.load();
	grid.on("load", function (e) {
		var form = new mini.Form("#search");
		var data = form.getData(true);
		lc.removeObjectEmptyValueProperty(data);
	});
	//查询按钮点击
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
	}
	
	function onComboValidation(e) {
        var items = this.findItems(e.value);
        if (!items || items.length == 0) {
           	this.setValue('');
        }
    }
</script>
</html>
