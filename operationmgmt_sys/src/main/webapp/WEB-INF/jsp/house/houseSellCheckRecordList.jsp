<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<!--[if IE 8]>
<html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]>
<html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <style type="text/css">
        .datagrid-header-rownumber,.datagrid-cell-rownumber{
            width:60px;
        }
        body{
            padding-left:10px;
            font-size:13px;
        }
        h1{
            font-size:20px;
            font-family:Verdana;
        }
        h4{
            font-size:16px;
            margin-top:25px;
            margin-bottom:10px;
        }
        .description{
            padding-bottom:30px;
            font-family:Verdana;
        }
        .description h3{
            color:#CC0000;
            font-size:16px;
            margin:0 30px 10px 0px;
            padding:45px 0 8px;
            /*background:url(titleback.png) no-repeat scroll left bottom transparent*/
            border-bottom:solid 1px #888;
        }
    </style>
    <script type="text/javascript" src="${ctx}/scripts/boot.js"></script>
</head>
<!-- BEGIN BODY -->
<body style="height:95%;">
    <div id="housefollowUpDiv" class="mini-fit" style="height:400px">
        <!-- 查询条件start -->
        <div id="search" class="search_form" style="display:none;">
            <div id="searchFrm" style="padding-bottom:5px;">
                <form action="" >
                    <span>房源id：</span><input type="text" id="queryHouseId" value="${houseId}"/>
                    <input type="button" value="查找" onclick="search()"/>
                </form>
            </div>
        </div>
        <!-- 查询条件end -->

        <!-- 结果展示table start -->
        <%--<table id="mainfrom"></table>--%>
        <div id="datagrid1" class="mini-datagrid" style="height:100%;" url="${ctx}/houseResource/sell/houseSellCheckRecordList.do" idField="id" allowResize="true" sizeList="[20,30,50,100]">
            <div property="columns">
                <%--<div type="indexcolumn"></div>--%>
                <div field="id" width="30" headerAlign="center" align="center" allowSort="true">id</div>
                <div field="resultTime" width="50" headerAlign="center" align="center" dateFormat="yyyy-MM-dd" allowSort="true" renderer="onDateFormatRenderer">审核时间</div>
                <div field="realName" width="50" headerAlign="center" align="center" allowSort="true">客服</div>
                <div field="checkState" width="40" align="center" headerAlign="center">审核结果</div>
                <div field="note" headerAlign="center" align="center" width="150" allowSort="true">备注</div>
                <div field="checkFaildType" width="100" headerAlign="center" align="center" allowSort="true">失败原因</div>
            </div>
        </div>
        <%--<div class="description">--%>
            <%--<h3>Description</h3>--%>
        <%--</div>--%>
    </div>
    <div id="showImgWin" class="mini-window" title="Window" style="width:310px;height:310px;"
         showMaxButton="true" showCollapseButton="true" showShadow="true"
         showToolbar="true" showFooter="true" showModal="false" allowResize="true" allowDrag="true"
            >
            <img id="imgAgreementKey" src="#"/>
            <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
        </div>
    </div>
<!-- END CONTAINER -->
<script type="text/javascript">
    //验证是否数字(param  len <= 0 则验证是否数字，len > 0 则还验证长度)
    function isNumber(value,len){
        var strP = /^\d+(\.\d+)?$/; //验证是否数字
        if(!strP.test(value))return false;
        if(len > 0 && value.length != len)return false;
        return true;
    }

    // 对Date的扩展，将 Date 转化为指定格式的String
    // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
    // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
    // 例子：
    // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
    // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
    Date.prototype.format = function(fmt) { //author: meizz
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "H+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    }

    var query = {"pageIndex":0,"pageSize":30,"houseId":"${houseId}"};
    var grid;
    function init(){
        mini.parse();
        grid = mini.get("datagrid1");
        grid.load(query);
    }

    init();

    var checkStateEnum = [{id : 1, text: '审核成功'},{id : 2, text: '审核中'},{id : 3, text: '审核失败'}];
    var checkFaildTypeEnum = [{ id: 0, text: '--' },
        { id: 1, text: '不符合要求（租金低于6000/租期超1月/办公楼/商铺/合租/群租/租售搞错）' },
        { id: 2, text: '中介号码'},
        { id: 3, text: '已租/不租'},
        { id: 4, text: '已售/不售'},
        { id: 5, text: '房源信息错误，小区名错误'},
        { id: 6, text: '房源信息错误，楼号错误'},
        { id: 7, text: '房源信息错误，室号错误'},
        { id: 8, text: '停机/空号/错号'},
        { id: 9, text: '无法联系/无人接听/无法接通/关机/来电提醒/再联系/不配合'}
    ];

    function getValueByJson(json,index){
        for (var i = 0, l = json.length; i < l; i++) {
                if (index == json[i].id) return json[i].text;
            }
            return "";
    }

    var tmpCheckState = 0;
    grid.on("drawcell", function (e) {
        var record = e.record,column = e.column,field = e.field,value = e.value;
        if (field == "checkState") {
            tmpCheckState = e.value;
            e.cellHtml = getValueByJson(checkStateEnum, e.value);
        }else if(field == "checkFaildType"){
            e.cellHtml = (tmpCheckState == "3" ? getValueByJson(checkFaildTypeEnum, e.value) : "");
        }
    });

    function search() {
        var queryHouseId = document.getElementById("queryHouseId").value;
        if(queryHouseId != "" && !isNumber(queryHouseId,0)){
            alert("房源 id 必须为数字！");
            $("queryHouseId").focus();
            return;
        }
        query.houseId = queryHouseId;
        grid.load(query);
    }
    $("#queryHouseId").bind("keydown", function (e) {
        if (e.keyCode == 13) {
            search();
        }
    });
    ///////////////////////////////////////////////////////

    function onDateFormatRenderer(e){
        if(e.value != null && e.value !=""){
            return new Date(e.value).format("yyyy-MM-dd HH:mm");
        }
        return "";
    }
    var Genders = [{ id: 1, text: '男' }, { id: 2, text: '女'}];
    function onGenderRenderer(e) {
        for (var i = 0, l = Genders.length; i < l; i++) {
            var g = Genders[i];
            if (g.id == e.value) return g.text;
        }
        return "";
    }
</script>
</body>
<!-- END BODY -->
</html>
