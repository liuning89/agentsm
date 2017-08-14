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
        <div style="padding-left:11px; padding-bottom:5px;" align="center">
            <table style="table-layout:fixed;padding-top: 20px;">
                   
                    <tr>
	                    <td >    
	                        <input name="rejectreason" id="rejectreason" onvalidation="rejectreason" class="mini-textarea" style="width: 350px;height: 100px"  required="true"/>
	                    </td>
                    </tr>
					<td ><span id="error_html" class="errorText"></span></td>
				</tr>          
            </table>
        </div>
        <div style="text-align:center;">               
            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>       
        </div>        
    </form>
    <script type="text/javascript">

        mini.parse();
        
        
        
        function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        
        function onOk(e) {
        	var b = mini.get("rejectreason");
        	if(b.getFormValue().trim() == ""){
        		mini.alert("驳回理由不能为空");
        		return fasle;
        	}
        	
        	mini.confirm("确定驳回视频申请？", "确定？",
                    function (action) {
                        if (action == "ok") {
                        	//var b = mini.get("rejectreason");
                        	onCancel();
                          	console.info('${houseId}')
                        	window.Owner.rejectedData(b.getFormValue(),"${videoType}","${id}","${mobile}","${estatename}","${phoneNum}","${houseId}");
                        } else {
								
							
                        }
                    }
                );
        }
        
        function onCancel(e) {
            //CloseWindow("cancel");
        	window.CloseOwnerWindow();
        }
        
        </script>
</body>
</html>