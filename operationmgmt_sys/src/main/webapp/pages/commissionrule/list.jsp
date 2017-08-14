<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <title>功能列表</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
</head>

<body style="height:100%;" path="<%=basePath%>">

<div style="padding-bottom:5px;"  >
    <fieldset style="border:solid 1px #aaa;padding-bottom:12px;position:relative;">
        <legend>平台佣金分配</legend>
        <div id="form">
            <input name="id" id="id" class="mini-hidden mini-textbox"/>
            <input name="createby" id="createby" class="mini-hidden mini-textbox"/>
            <span>平台佣金分配：</span>
            <input id="commission" style="width:80px;" name="commission" class="mini-textbox" numberFormat="n2" vtype="range:0,100"
                   onvalidation="lf.commissionRule.list.onCellValidation"
                   rangeErrorText="请输入大于0的正数"/>  <span>%</span>
            <input type="button" value="保存" onclick="lf.commissionRule.list.savePercentage()"/>
        </div>
    </fieldset>
</div>

<fieldset style="border:solid 1px #aaa; ">
    <legend>其他角色佣金分配</legend>
<div style="width:100%;">
    <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
        <table style="width:99%;">
            <tr>
                <td>
                    <a class="mini-button" id="addbtn" iconCls="icon-add" onclick="lf.commissionRule.list.add()">添加</a>
                    <a class="mini-button" id="updatebtn" iconCls="icon-remove"
                       onclick="lf.commissionRule.list.delete()">删除</a>
                    <a class="mini-button" id="deletebtn" iconCls="icon-save"
                       onclick="lf.commissionRule.list.save()">保存</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<!--列表-->
<div class="mini-fit"  style="height:645px;border-bottom:0;padding:0px;">
    <div id="dg" class="mini-datagrid" sizeList="[20,30,50,100]" pageSize="20" style="height:100%" idField="id"
         url="<%=basePath%>commissionRule/list.do" allowCellEdit="true" allowCellSelect="true" multiSelect="true"
         editNextOnEnterKey="true" editNextRowCell="true" editNextOnEnterKey="true" allowCellValid="true"
         allowResize="true"
         onCellValidation="lf.commissionRule.list.onCellValidation">
        <div property="columns">
            <div type="checkcolumn" width="2"></div>
            <div field="id" name="id" align="left" headeralign="left" visible="false">
                ID<input property="editor" class="mini-textbox"/>
            </div>
            <div field="role" name="role" align="left" headeralign="left" width="20">
                分佣角色<input property="editor" class="mini-textbox" style="width:100%;"/>
            </div>
            <div field="distpercent" vtype="range:0,100" name="distpercent" align="left" headeralign="left"
                 numberFormat="n2" width="20">
                分配比例（%）
                <input property="editor" style="width:100%;" class="mini-textbox"/>
            </div>
            <div field="islocked" name="islocked" type="checkboxcolumn" value="0" trueValue="1" falseValue="0"
                 align="left"
                 headeralign="left" width="10">是否锁定
            </div>
        </div>
    </div>
</div>
</fieldset>
</body>
<script type="text/javascript" src="scripts/pages/commissionRule/list.js?v=${version}"></script>
</html>
