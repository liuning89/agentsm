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
<body>
	<form id="form" method="post">
        <div style="padding-left:11px; padding-bottom:5px;padding-top:10px;" align="center">
            <table style="table-layout:fixed;padding-top: 5px;">
                  <tr>
                      <td style="width:70px;">金额：</td>
	                    <td>    
	                        <input type="text" onkeyup="changValue(this);"  name="price" id="price" />
	                    </td>
                    </tr>
                   <tr>  
	                  	<td style="width:100px;">悟空币面额：</td>
	                    <td>    
	                        <input type="text"  name="wkCoinDenomination" id="wkCoinDenomination"  />
	                    </td>
                    </tr>
                    
            </table>
        </div>
        <div style="text-align:center;margin-top: 50px;">               
            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>       
            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
        </div>        
    </form>
    <script type="text/javascript">
    mini.parse();
    function changValue(v){
    	 v.value=v.value.replace(/\D/g,'');
    	$("#wkCoinDenomination").val($("#price").val());
    }
        function SaveData() {
            var price = $("#price").val();
            var wkCoinDenomination = $("#wkCoinDenomination").val();
            if(price==null ||price==''){
            	mini.alert("金额不能为空！");
            	return;
            }
            if(wkCoinDenomination==null ||wkCoinDenomination==''){
            	mini.alert("悟空币面额不能为空！");
            	return;
            }
            $.ajax({
                url: "amoutset/add.do?price="+price+"&wkCoinDenomination="+wkCoinDenomination,
				type: 'post',
                dataType:'json',
                success: function (text) {
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
        
       
        </script>
</body>
</html>