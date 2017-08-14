<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

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

	   			<form id="form1" method="post">
        <input name="id" class="mini-hidden" />
        <div style="padding-left:11px;padding-bottom:5px;">
            <table style="table-layout:fixed;">
                <tr>
                	<td style="width:70px;">原因：</td>
                   	 <td style="width:150px;">    
                        <input id="reason" name="reason" class="mini-combobox"  valueField="id" textField="text" 
                            url="houseResource/sell/getReasionList.action" 
                           	required="true" nullItemText="请选择..."emptyText="请选择原因"/>
                    </td>
                </tr>
	            <tr>
	                <td >备注：</td>
	                <td>    
	                    <input name="memo" 	required="true"  class="mini-textarea" style="width:500px;height: 160px;" />
	                </td>
	            </tr>
        </table>
        </div>            
        <div style="text-align:center;padding:10px;">               
            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>       
            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
        </div>        
    </form>
</body>
<script type="text/javascript">
	mini.parse();
	 var form = new mini.Form("form1");
	function onOk(){
		var o=form.getData();
		console.info(o)
		mini.confirm("确定要将该房源设置为无效吗？","提示",function(e){
			if(e == "ok"){
				lc.ajax({
					url:lc.rootPath("houseResource/sell/setHouseResourceStatus.action"),
					data:{
						houseResourceStatus:'${status}',
						houseId:'${houseId}',
						reason:mini.get("reason").getText(),
						memo:o['memo']
					},
					success:function(data){
						if(data.status == 1){
							CloseWindow("save");
						}else{
							mini.alert(data.message);
						}
					},
					error:function(){
						mini.alert("操作失败，请重试");
					}
				},"正在修改房源状态，请稍后...");
			}
		});
		
	}
	
	function CloseWindow(action) {            
        if (action == "close" && form.isChanged()) {
            if (confirm("数据被修改了，是否先保存？")) {
                return false;
            }
        }
        if (window.CloseOwnerWindow) 
        	return window.CloseOwnerWindow(action);
        else 
        	window.close();            
    }
    function onCancel(e) {
        CloseWindow("close");
    }
</script>
</html>
