<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
<body>
	<form id="agentform" method="post">
        <div style="padding-left:11px;margin-top:50px;  padding-bottom:5px;padding-top:10px;" align="center">
            <table style="table-layout:fixed;padding-top: 20px;">
                   <tr>  
                    <td style="width:70px;">门店：</td>
                    <td style="width:150px;">    
                        <input name="agentStore" url="houseResource/sell/getStoreListByHouseId.do?houseId=${houseId }&franchiseeId=${franchiseeId}"  allowInput="true"  class="mini-combobox" valueField="id" textField=text required="true" onvaluechanged="onStoreChanged"
                         />
                    </td>
                    </tr>
                    <tr>
                    <td>人员：</td>
                    <td style="width:160px;">    
                        <input name="name" class="mini-combobox" allowInput="true" valueField="id" textField="text" url="houseResource/sell/getAgentListByStoreId.do?storeId=${id }&houseId=${houseId}"  required="true"
                         emptyText="请选择人员"/>
                    </td>
            </table>
        </div>
        <div style="text-align:center;margin-top: 50px;">               
            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>       
            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
        </div>        
    </form>
    <script type="text/javascript">

        mini.parse();
        var form = new mini.Form("agentform");
        var formdataid;
       /*  if('${id}'!=0){
	       mini.getbyName("agentStore").setValue('${id}');
        } */
        
        function SaveData() {
            form.validate();
            if (form.isValid() == false) return;
        	var houseAgentId = mini.getbyName("name").getValue();
            $.ajax({
                url: "houseResource/sell/saveUpdateAgent.do?houseId=${houseId}&houseAgentId="+houseAgentId+"&flag=${flag}&id=${id}",
				type: 'post',
                dataType:'json',
                cache: false,
                success: function (text) {
                	var o = mini.decode(text);
                	var status = o['status'];
                	if(status ==1){
                		mini.alert("更新成功","提示",function(){
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
        
       
       
        function onStoreChanged(e) {
        	var storeId = mini.getbyName("agentStore").getValue();
            var name = mini.getbyName("name");
            name.load("houseResource/sell/getAgentListByStoreId.do?storeId="+storeId+"&houseId=${houseId}");
        }
        </script>
</body>
</html>