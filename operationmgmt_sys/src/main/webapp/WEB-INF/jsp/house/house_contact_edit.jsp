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
	<form id="contactform" method="post">
        <div style="padding-left:11px;margin-top:50px;  padding-bottom:5px;padding-top:10px;" align="center">
            <table style="table-layout:fixed;padding-top: 20px;">
                   <tr>  
                    <td style="width:70px;">联系人：</td>
                    <td style="width:150px;">    
                        <input name="hostName" id="hostName"  class="mini-textbox" value="${hostName }"  required="true" 
                         />
                    </td>
                    </tr>
                    <tr>
                    <td>请输入新房东电话：</td>
                    <td style="width:160px;">    
                        <input name="hostMobile" vtype="int" id="hostMobile" onvalidation="isMobile"   class="mini-textbox"   required="true"/>
                    </td>
                   </tr>
					<tr>
					<td></td>
					<td ><span id="error_html" class="errorText"></span></td>
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
        var form = new mini.Form("contactform");
        var formdataid;
/*  		function isMobile(v) {
            var re = new RegExp("^[0-9a-zA-Z\_]+$");
            if (re.test(v)) return true;
            return false;
        }
 */        
        function SaveData() {
            form.validate();
            if (form.isValid() == false) return;
            var hostName=mini.get("hostName").getValue();
            var hostMobile=mini.get("hostMobile").getValue();
            $.ajax({
                url: "houseResource/sell/updateContact.do",
                data: {'contactId':'${contactId}','hostName':hostName,'hostMobile':hostMobile},
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
        
		 function isMobile(v) {
			 var v=mini.get("hostMobile").getValue();
			 var telReg =/^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
			 if(!telReg.test(v)){
				$("#error_html").html("<span style='color:red;'>手机号不正确</span>");
			 }else{
					$("#error_html").html("");
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
       
       
        </script>
</body>
</html>