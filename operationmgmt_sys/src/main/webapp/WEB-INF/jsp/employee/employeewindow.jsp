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
                    <td><input id="loginName" name="loginName" class="mini-textbox" required="true" vtype="minLength:2" emptyText="请填写真实姓名" /></td>
                </tr>
                <tr>
                    <td>性别：</td>
                    <td>
                        <select name="gender" class="mini-radiobuttonlist" required="true">
                                <option value="1">男</option>
                                <option value="2">女</option>
                        </select></td>
                    </tr>
                    <tr>
                        <td>手机号：</td>
                        <td><input name="phone" class="mini-textbox" required="true" onvalidation="onMobileValidation" vtype="int;rangeChar:11,11" emptyText="请填写手机号(11位)" /></td>
                    </tr>

                    <tr>
                        <td>权限：</td>
                        <td><select name="grade" class="mini-radiobuttonlist" required="true">
                                <option value="1">普通</option>
                                <option value="2">管理员</option>
                        </select>
                    </td>
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
                        if(checkEmployeeExists() == false)
                        {
                            $.ajax({
                                        url : "${ctx}/lfEmployee/saveOrEditLfEmployee.action",
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
                        else
                        {
                            mini.alert("账号已经存在!");
                        }
                    }
                    
                    function checkEmployeeExists()
                    {
                        var exists = true;
                        if ($("#editTag").val() == "edit") {
                            exists = false;
                        }
                        else
                        {
                            var loginName = mini.get("loginName").getValue();
                            $.ajax({
                                url : "${ctx}/lfEmployee/checkEmployeeExists.action",
                                type : 'post',
                                async: false,
                                data : {
                                    loginName : loginName
                                },
                                cache : false,
                                success : function(text) {
                                    if(text == 0)
                                    {
                                        exists = false;
                                    }
                                },
                                error : function(jqXHR, textStatus,
                                        errorThrown) {
                                }
                           });
                        }
                        return exists;
                    }
                    
                    
                    
                    function onMobileValidation(e) {
                        if (e.isValid) {
                            if (isMobileNO(e.value) == false) {
                                e.errorText = "必须输入有效的号码";
                                e.isValid = false;
                            }
                        }
                    }

                    //验证手机号码
                    function isMobileNO(mobile)
                    {
                        var myreg = /^1[3|4|5|7|8|9]\d{9}$/; 
                        if(!myreg.test(mobile))
                        {
                            return false;
                        }
                        return true;
                    }

                    ////////////////////
                    //标准方法接口定义
                    function SetData(data) {
                        if (data.action == "edit") {
                            //更新页面编辑标示
                            $("#editTag").val("edit");
                            //跨页面传递的数据对象，克隆后才可以安全使用
                            data = mini.clone(data);
                            $.ajax({
                                url : "${ctx}/lfEmployee/getEmployeeById.action?id=" + data.id,
                                cache : false,
                                success : function(text) {
                                    var o = mini.decode(text);
                                    form.setData(o);
                                    form.setChanged(false);
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
                    //////////////////////////////////
                    function onDeptChanged(e) {
                        var deptCombo = mini.getbyName("dept_id");
                        var positionCombo = mini.getbyName("position");
                        var dept_id = deptCombo.getValue();

                        positionCombo.load("../data/AjaxService.aspx?method=GetPositionsByDepartmenId&id=" + dept_id);
                        positionCombo.setValue("");
                    }
                </script>
</body>
</html>