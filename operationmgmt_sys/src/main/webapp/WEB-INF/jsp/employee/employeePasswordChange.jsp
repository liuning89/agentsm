<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>账号列表</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<script src="${ctx}/scripts/miniui/miniui.js" type="text/javascript"></script>
<script src="${ctx}/scripts/boot.js?v=${version}" type="text/javascript"></script>
</head>
<body>
    <form id="form1" method="post">
        <input name="id" class="mini-hidden" />
        <fieldset style="border: solid 1px #aaa; padding: 3px;">
            <legend>基本信息</legend>
            <div style="padding-left: 11px; padding-bottom: 5px;">
            <table style="table-layout: fixed;">
                <tr>
                    <td>真实姓名：</td>
                    <td><input id="loginName" name="loginName" class="mini-textbox"  allowInput="false"/></td>
                </tr>
                <tr>
                    <td>新密码：</td>
                    <td><input id="passWord" name="passWord" class="mini-password" required="true" onvalidation="onPassworValidation" vtype="minLength:6"  emptyText="请输入密码" /></td>
                </tr>
                <tr>
                    <td>确认密码：</td>
                     <td><input id="passWord1" name="passWord1" class="mini-password" required="true" onvalidation="onPassworValidation"  emptyText="请输入密码" /></td>
                </tr>
            </table>
        </div>
        </fieldset>
        <div style="text-align: center; padding: 10px;">
            <a class="mini-button" onclick="onOk" style="width: 60px; margin-right: 20px;">确定
            </a> <a class="mini-button" onclick="onCancel" style="width: 60px;">取消</a>
        </div>
    </form>
    <input type="hidden" id="editTag" value="save"/>
    <script type="text/javascript">
					mini.parse();

					var form = new mini.Form("form1");

					function SaveData() {
						var o = form.getData();
						form.validate();
						if (form.isValid() == false)
							return;
						var json = mini.encode([ o ]);
						$.ajax({
									url : "${ctx}/lfEmployee/changeEmployeePassword.action",
									type : 'post',
									data : {
										data : json
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
					
					function onPassworValidation(e) {
                        if (e.isValid) {
                        	var pwd = mini.get("passWord").getValue();
                        	var pwd1 = mini.get("passWord1").getValue();
                            if (pwd != pwd1) {
                                e.errorText = "两次密码必须相同";
                                e.isValid = false;
                            }
                        }
                    }
					
					//标准方法接口定义
					function SetData(data) {
						if (data.action == "edit") {
							//跨页面传递的数据对象，克隆后才可以安全使用
							data = mini.clone(data);
							$.ajax({
								url : "${ctx}/lfEmployee/getEmployeeById.action?id=" + data.id,
								cache : false,
								success : function(text) {
									var o = mini.decode(text);
									form.setData(o);
									form.setChanged(false);
									
									var pwd = mini.get("passWord").setValue();
									//onDeptChanged();
									//mini.getbyName("position").setValue(o.position);
								}
							});
						}
					}

					function GetData() {
						var o = form.getData();
						return o;
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
					
					function onOk(e) {
						SaveData();
					}
					function onCancel(e) {
						CloseWindow("cancel");
					}
				</script>
</body>
</html>