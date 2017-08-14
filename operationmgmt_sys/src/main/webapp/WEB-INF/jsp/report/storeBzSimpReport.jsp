<%@page import="java.util.Date" %>
<%@page import="java.util.GregorianCalendar" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Calendar" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    Date date = new Date();//取时间
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = formatter.format(date);
    String endDate = dateString;
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    calendar.add(calendar.DATE, -10);//把日期往后增加一天.整数往后推,负数往前移动
    date = calendar.getTime(); //这个时间就是日期往后推一天的结果
    String startDate = formatter.format(date);
    String daterange = startDate + " 至  " + endDate;

%>
<!DOCTYPE html>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<html>
<head path="<%=basePath%>">
    <base href="<%=basePath%>"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <script src="${ctx}/scripts/boot.js?v=${version}" type="text/javascript"></script>

    <!--时间段控件 -->
    <script type="text/javascript" src="${ctx}/scripts/daterangepicker/moment.min.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/daterangepicker/daterangepicker.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/daterangepicker/daterangepicker.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/bootstrap/bootstrap.min.css"/>

    <style type="text/css">
        html, body {
            width: 100%;
            height: 100%;
            border: 0;
            margin: 0;
            padding: 0;
            overflow: visible;
        }

        label {
            font-weight: normal;
        }

        input {
            line-height: normal;
        }

        .mini-list-inner label {
            margin-bottom: 0px;
        }

        #attention {
            padding-left: 10px;
            padding-top: 2px;
            color: red;
        }

        #form {
            padding-top: 5px;
            padding-left: 5px;
        }
    </style>
</head>
<body path="<%=basePath%>">
<div id="form">
    <div style="float: left;" id="queryType" name="queryType" class="mini-radiobuttonlist"
         textField="text" valueField="id" value="1" data="[{id:1,text:'汇总'},{id:0,text:'明细'}]"></div>
    <input id="title" name="title" class="mini-textbox" value="门店接单出价情况" style="display: none;">
    <label style="float: left;">时间:</label>
    <input id="daterange" style="width: 200px" type="text" name="daterange" value="<%=daterange %>"/>
    <span>城市:</span>
    <input id="cityId" name="cityId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
           allowInput="true"
           onvaluechanged="onCityChanged" emptyText="请选择" nullItemText="请选择"/>
    <span>区域:</span>
    <input id="areaId" name="areaId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
           showNullItem="true" allowInput="true"
           onvaluechanged="onAreaChanged" emptyText="请选择" nullItemText="请选择"/>
    <span>门店:</span>
    <input id="storeId" name="storeId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
           showNullItem="true" allowInput="true"
           emptyText="请选择" nullItemText="请选择"/>
    <input type="button" value="查找" onclick="search()"/>
    <input type="button" value="重置" onclick="reset()"/>
    <input type="button" value="导出" onclick="exportExcel()"/>
    <div id="attention">说明：该报表非实时；更新时间为 每天7:55,9:55,12:55,14:55,16:55,18:55,20:55,22:55,23:55</div>
</div>
<div style="clear: both"></div>
<!--列表-->
<div id="agentReportDiv" class="mini-fit" style="height:400px">
    <div id="dg" class="mini-datagrid" style="height:100%;" pageSize='100' sizeList="[20,30,50,100]"
         url="${ctx}/bizsimpreport/getStoreBizSimpReport.action">
        <div property="columns">
            <div header="基本信息" headerAlign="center" align="center">
                <div property="columns">
                    <div type="indexcolumn">序号</div>
                    <div field="statisticsDate" name="statisticsDate" renderer="onTimeRenderer" headerAlign="center"
                         align="center" width="80">日期
                    </div>
                    <div field="areaOrgName" headerAlign="center" allowSort="true" align="center" width="90">区域</div>
                    <div field="areaOrgName" headerAlign="center" allowSort="true" align="center" width="90">门店</div>
                </div>
            </div>
            <div header="接单" headerAlign="center" align="center">
                <div property="columns">
                    <div field="orderAllocation" headerAlign="center" allowSort="true" align="center" width="50">人工派单
                    </div>
                    <div field="orderGrab" headerAlign="center" allowSort="true" align="center" width="50">抢单</div>
                    <div field="orderGrab" headerAlign="center" align="center" width="50" allowSort="true">系统派单（新单）
                    </div>
                    <div field="orderGrab" headerAlign="center" align="center" width="50" allowSort="true">系统派单（老单）
                    </div>
                    <div field="orderTotal" headerAlign="center" allowSort="true" align="center" width="50">总单</div>
                    <div field="orderTotal" headerAlign="center" align="center" width="50" allowSort="true">客户</div>
                </div>
            </div>
            <div header="出价" headerAlign="center">
                <div property="columns">
                    <div field="orderSuccess" headerAlign="center" allowSort="true" align="center" width="70">确认出价</div>
                    <div field="orderFailed" headerAlign="center" allowSort="true" align="center" width="70">撤销出价</div>
                    <div field="priceTotal" headerAlign="center" allowSort="true" align="center" width="50">总数</div>
                </div>
            </div>

        </div>
    </div>
</body>
</html>
<script type="text/javascript">

    mini.parse();
    var grid = mini.get("dg");
    var queryType = mini.get("queryType");
    grid.hideColumn("statisticsDate");
    queryType.on("valuechanged", function (e) {
        if (queryType.getValue() == 0) {
            grid.showColumn("statisticsDate");
            search();
        }
        else {
            grid.hideColumn("statisticsDate");
            search();
        }
    });
    var dateStart = '<%=startDate %>';
    var dateEnd = '<%=endDate %>';
    resetCity();

    function resetCity() {
        var cityCbo = mini.get("cityId");
        cityCbo.setUrl("${ctx}/areaorg/getCityListByUserId.action");
        cityCbo.select(0);
        if (cityCbo.data.length == 1) {
            cityCbo.disable();
        }
        onCityChanged();
    }


    function search() {
        var form = new mini.Form("#form");
        var json = mini.encode(form.getData());
        grid.load({json: json, dateStart: dateStart, dateEnd: dateEnd});
    }

    function reset() {
        new mini.Form("#form").clear();
        $("#daterange").val("<%=daterange%>");
        mini.get("queryType").setValue(1);
        grid.hideColumn("statisticsDate");
        resetCity();
    }

    $("#daterange").daterangepicker({
        "timePicker24Hour": true,
        "timePickerSeconds": true,
        ranges: {
            '今天': [moment(), moment()],
            '昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            '最近7天': [moment().subtract(6, 'days'), moment()],
            '最近30天': [moment().subtract(29, 'days'), moment()],
            '本月': [moment().startOf('month'), moment().endOf('month')],
            '上个月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        },
        locale: {
            format: 'YYYY-MM-DD',
            separator: ' 至 ',
            daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
                '七月', '八月', '九月', '十月', '十一月', '十二月'],
            applyLabel: '确定',
            cancelLabel: '取消',
            "customRangeLabel": "自定义"
        },
        "startDate": "<%=startDate %>",
        "endDate": "<%=endDate %>"
    }, function (start, end, label) {
        dateStart = start.format('YYYY-MM-DD');
        dateEnd = end.format('YYYY-MM-DD');
    });


    function onCityChanged(e) {
        var cityCbo = mini.get("cityId");
        var areaCbo = mini.get("areaId");
        var storeCbo = mini.get("storeId");
        var id = cityCbo.getValue();
        var url = "${ctx}/areaorg/getPartnerByCityId.action";
        if (id != null && id != "") {
            url += "?cityId=" + id;
            areaCbo.setUrl(url);
            if (areaCbo.data.length == 2) {
                areaCbo.select(1);
            } else {
                areaCbo.setData([]);
                storeCbo.setData([]);
            }
        }
        onAreaChanged();
    }

    function onAreaChanged(e) {
        var areaCbo = mini.get("areaId");
        var cityCbo = mini.get("cityId");
        var storeCbo = mini.get("storeId");
        var idarea = areaCbo.getValue();
        var idcity = cityCbo.getValue();
        var url = "${ctx}/areaorg/deptListByPartnerId.action?";
        if (idarea != null && idarea != "") {
            url += "cityId=" + idcity;
            url += "&partnerId=" + idarea;
            storeCbo.setUrl(url);
            if (storeCbo.data.length == 2) {
                storeCbo.select(1);
            }
        } else {
            storeCbo.setData([]);
        }
    }


    function onTimeRenderer(e) {
        var value = e.value;
        if (value) {
            value = new Date(value);
            return mini.formatDate(value, 'yyyy-MM-dd');
        }
        return "";
    }

    function exportExcel() {
        var columns = grid.getBottomColumns();

        function getColumns(columns) {
            columns = columns.clone();
            for (var i = columns.length - 1; i >= 0; i--) {
                var column = columns[i];
                if (!column.field) {
                    columns.removeAt(i);
                } else {
                    var c = {header: column.header, field: column.field};
                    columns[i] = c;
                }
            }
            return columns;
        }

        var form = new mini.Form("#form");
        var data = mini.encode(form.getData());
        var title = mini.encode(getColumns(columns));
        window.location.href = encodeURI(encodeURI("${ctx}/bizsimpreport/exportStoreBizSimpReport.action?title=" + title + "&data=" + data + "&dateStart=" + dateStart + "&dateEnd=" + dateEnd));
    }
    search();
</script>