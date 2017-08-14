<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>经纪人列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <script src="${ctx}/scripts/boot.js?v=${version}" type="text/javascript"></script>
    <style type="text/css">
        html,body
        {
            width:100%;
            height:100%;
            border:0;
            margin:0;
            padding:0;
            overflow:visible;
        }
    </style>
</head>

<body>
    <form id="form1" method="post">
        <input name="id" class="mini-hidden" />
        <fieldset style="border: solid 1px #aaa; padding: 3px;">
            <legend>基本信息</legend>
            <div style="padding-left: 11px; padding-bottom: 5px;">
                <form id="form1" style="padding-left:10px;margin:0px;margin-top:10px;">
                    <table cellpadding="5" style="border-collapse:separate; border-spacing:5px;">
                            <tr>
                                <td>新密码:</td>
                                <td><input class="mini-password"  id="newPassword" name="newPassword" onvalidation="onPassworValidation" required="true" missingMessage="请填写新密码"></input></td>
                            </tr>
                            <tr>
                                <td>确认密码:</td>
                                <td><input class="mini-password" id="confirmPassword" onvalidation="onPassworValidation" name="confirmPassword" required="true" missingMessage="请再次填写新密码"></input></td>
                            </tr>
                    </table>
                </form>
            </div>
        </fieldset>
        <div style="text-align: center; padding: 10px;">
            <a class="mini-button" onclick="onOk" style="width: 60px; margin-right: 20px;">确定
            </a> <a class="mini-button" onclick="onCancel" style="width: 60px;">取消</a>
        </div>
    </form>
    
    <script type="text/javascript">
        mini.parse();
        var form = new mini.Form("form1");
        
        function onPassworValidation(e) {
            if (e.isValid) {
                var pwd = mini.get("newPassword").getValue();
                var pwd1 = mini.get("confirmPassword").getValue();
                if (pwd != pwd1) {
                    e.errorText = "两次密码必须相同";
                    e.isValid = false;
                    return;
                }
                
                var reg = /^\w{6,12}$/
                if(reg.test(e.value) == false)
                {
                    e.errorText = "密码需要6-12位的数字字母";
                    e.isValid = false;
                }
            }
        }
        
        function SaveData() {
            var o = form.getData();
            form.validate();
            if (form.isValid() == false)
                return;
            var json = mini.encode([ o ]);
            console.log(o);
            $.ajax({
                        url : "${ctx}/agent/updatePassword.action",
                        type : 'post',
                        data : {
                            id : o.id,
                            newPassword:o.newPassword,
                            confirmPassword:o.confirmPassword
                        },
                        cache : false,
                        success : function(text) {
                            CloseWindow("save");
                        },
                        error : function(jqXHR, textStatus,
                                errorThrown) {
                            alert(jqXHR.responseText);
                            CloseWindow();
                        }
            });
        }
        
        function onOk(e) {
            SaveData();
        }
        function onCancel(e) {
            CloseWindow("cancel");
        }
        
      //标准方法接口定义
        function SetData(data) {
            if (data.action == "edit") {
                //跨页面传递的数据对象，克隆后才可以安全使用
                data = mini.clone(data);
                $.ajax({
                    url : "${ctx}/agent/getAgentById.action?id=" + data.id,
                    cache : false,
                    success : function(text) {
                        var o = mini.decode(text);
                        form.setData(o);
                        form.setChanged(false);
                    }
                });
            }
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
    </script>
	
</body>
<!-- END BODY -->
</html>
