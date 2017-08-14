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
        	<input name="id" class="mini-hidden" value="${feeSet.id}" />
            <table style="table-layout:fixed;padding-top: 5px;">
                   <tr>  
	                  	<td style="width:100px;">悟空币面额：</td>
	                    <td>    
	                        <input class="mini-textbox" vtype="int;range:0,9999999" name="consumecoin"  id="consumecoin" required="true"  value="${feeSet.consumecoin }" />
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
        var form = new mini.Form("form");
        function SaveData() {
            form.validate();
            if (form.isValid() == false) return;
            var o=form.getData();
            $.ajax({
                url: "amoutset/saveFeeSet.do",
				type: 'post',
                dataType:'json',
                data: o,
                success: function (text) {
                	debugger;
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
        
       
        </script>
</body>
</html>