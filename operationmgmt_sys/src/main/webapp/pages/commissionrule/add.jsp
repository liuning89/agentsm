<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head path="<%=basePath%>">
    <base href="<%=basePath%>"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
    <title>添加功能页面</title>
</head>
<body>
<form id="addForm" method="post">
    <div style="padding-left:11px;padding-bottom:5px;padding-top:10px;" align="center">
        <table style="table-layout:fixed;padding-top: 15px; padding-left: 25px;">
            <tr>
                <td>分佣角色：</td>
                <td>
                    <input name="role" id="role" class="mini-textbox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>分配比例（%）：</td>
                <td>
                    <input name="distpercent" id="distpercent" class="mini-textbox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div name="islocked" id="islocked" class="mini-checkbox"  value="0" trueValue="1" falseValue="0"
                         text="是否锁定："></div>
                </td>
            </tr>
        </table>
    </div>
    <div style="text-align:center;margin-top: 50px;">
        <a class="mini-button" onclick="lf.commissionRule.add.onOk()" style="width:60px;margin-right:20px;">确定</a>
        <a class="mini-button" onclick="lf.commissionRule.add.onCancel()" style="width:60px;">取消</a>
    </div>
</form>
</body>
<script type="text/javascript" src="scripts/pages/commissionRule/add.js"></script>
</html>