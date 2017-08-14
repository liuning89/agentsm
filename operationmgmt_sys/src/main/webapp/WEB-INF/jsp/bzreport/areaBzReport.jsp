<%@page import="java.util.Date"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    Date date=new Date();//取时间
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    //calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
    date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String dateStringEnd = formatter.format(date);

    calendar.add(calendar.DATE, -1);
    date=calendar.getTime();
    String dateStringStart = formatter.format(date);

    String startDate = dateStringStart;
    String endDate = dateStringEnd;

    String daterange = startDate + " 至 " + endDate;
%>
<!DOCTYPE html>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<html>
<head path="<%=basePath%>">
    <base href="<%=basePath%>" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
	<script src="${ctx}/scripts/boot.js?v=${version}" type="text/javascript"></script>

    <!--时间段控件 -->
    <script type="text/javascript" src="${ctx}/scripts/daterangepicker/moment.min.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/daterangepicker/daterangepicker.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/daterangepicker/daterangepicker.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/bootstrap/bootstrap.min.css" />

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

        .input-group-addon {
            background-color: #eee;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            font-weight: normal;
            line-height: 1;
            padding: 1px 2px;
            text-align: center;
        }
        .form-control {
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
            color: #333;
            display: block;
            font-size: 14px;
            height: 20px;
            line-height: 1.42857;
            padding: 0 8px;
            transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
            vertical-align: middle;
            width: 100%;
        }
        label {
            font-weight: normal;
        }
        input {
            line-height: normal;
        }
        .mini-list-inner label
        {
            margin-bottom: 0px;
        }
        #attention {
            padding-left: 10px;
            color: red;
        }
    </style>
</head>
<body path="<%=basePath%>">

    <div style="padding-bottom: 10px;">
        <div style="float: left;">
            查询类型
        </div>
        <div style="float: left;padding-left: 15px;">
            <div id="queryType" class="mini-radiobuttonlist"
                  textField="text" valueField="id" value="1" data="[{id:1,text:'汇总'},{id:2,text:'明细'}]" onvaluechange></div>
        </div>
        <div style="float: left;margin-left: 30px">
            <div>
                <label>时间:</label><input id="daterange" style="width: 200px" type="text" name="daterange" value="" />
            </div>
        </div>
        <div style="clear: both"></div>
    </div>
    <form id="form1">
    <div style="padding-bottom: 10px;">
        <span>城市:</span>
        <input id="cityId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
            url="${ctx}/areaorg/getCityListByUserId.action" value="" showNullItem="true"  allowInput="true"
            onvalidation="onComboValidation" onvaluechanged="onCityChanged" emptyText="请选择" nullItemText="请选择" />
        <span>区域:</span>
        <input id="areaId" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
            data="[]" value="" showNullItem="true"  allowInput="true"
            onvalidation="onComboValidation" emptyText="请选择" nullItemText="请选择"/>
    </div>
    </form>
    <div style="padding-bottom: 10px;">
        <input style="margin-left: 30px;" type="button" value="查找" onclick="search()"/>
        <input type="button" value="重置" onclick="reset()"/>
        <input type="button" value="导出" onclick="downLoadExcel()"/>
        <span onclick="showFormula()"><a href="javascript:void(0)">行程量计算公式</a></span>
        <span id="attention">说明：该报表非实时；更新时间为 每天7:55,9:55,12:55,14:55,16:55,18:55,20:55,22:55,23:55</span>
    </div>
	<!--列表-->
    <div id="agentReportDiv" class="mini-fit" style="height:400px" >
          <div id="dg" class="mini-datagrid"  style="height:100%;" pageSize='100' sizeList="[20,30,50,100]" url="${ctx}/bzareareport/getAreaBzReport.action" >
              <div property="columns">
              	<div type="indexcolumn">序号</div>
                <div header="基本信息" headerAlign="center" align="center">
                    <div property="columns">
                        <div field="statisticsDate" name="statisticsDate" renderer="onTimeRenderer" headerAlign="center" align="center" width="80">日期</div>
                        <div field="areaOrgName" headerAlign="center" align="center" width="80">区域</div>
                        <div field="agentStroke" headerAlign="center" align="center" width="60" allowSort="true">行程量</div>
                        <div field="agentStrokeAVG" headerAlign="center" align="center" width="50" allowSort="true">平均<br/>行程量</div>
                    </div>
                </div>
                <div header="带看" headerAlign="center">
                    <div property="columns">
                        <div field="houseSeeHCount" headerAlign="center" align="center" allowSort="true" width="50">带盘</div>
                        <div field="houseSeeCCount" headerAlign="center" align="center" allowSort="true" width="50">带客</div>
                    </div>
                </div>
                <div header="新增客户（人）" headerAlign="center">
                    <div property="columns">
                        <div field="newCustomerWK" headerAlign="center" align="center" allowSort="true" width="50">悟空<br/>找房</div>
                        <div field="newCustomerNet" headerAlign="center" align="center" allowSort="true" width="50">网络</div>
                        <div field="newCustomerOther" headerAlign="center" align="center" allowSort="true" width="50">其它</div>
                        <div field="newCustomerTotal" headerAlign="center" align="center" allowSort="true" width="50">新增<br/>总数</div>
                    </div>
                </div>
                <div header="房源数（套）" headerAlign="center">
                    <div property="columns">
                        <div field="housePublish" headerAlign="center" align="center" allowSort="true" width="50">发布</div>
                        <div field="houseKey" headerAlign="center" align="center" allowSort="true" width="50">钥匙</div>
                        <div field="houseEntrust" headerAlign="center" align="center" allowSort="true" width="50">速销</div>
                        <div field="houseImage" headerAlign="center" align="center" allowSort="true" width="50">实景</div>
                        <div field="invalidReview" headerAlign="center" align="center" allowSort="true" width="50">无效审核数量</div>
                    </div>
                </div>
                <div header="跟进情况" headerAlign="center">
                    <div property="columns">
                        <div field="followHouse" headerAlign="center" align="center" allowSort="true" width="50">房源</div>
                        <div field="followCustomer" headerAlign="center" align="center" allowSort="true" width="50">跟进<br/>客户</div>
                    </div>
                </div>
                <div header="自营指数" headerAlign="center">
                    <div property="columns">
                        <div field="shareTotal" headerAlign="center" align="center" allowSort="true" width="50">营销<br/>指数</div>
                        <div field="shareHouseCount" headerAlign="center" align="center" allowSort="true" width="50">分享房<br/>源次数</div>
                        <div field="shareHouse" headerAlign="center" align="center" allowSort="true" width="50">分享房<br/>源套数</div>
                    </div>
                </div>
                <div header="客户评价" headerAlign="center">
                    <div property="columns">
                        <div field="opinionHight" headerAlign="center" align="center" allowSort="true" width="50">好评</div>
                        <div field="opinionLow" headerAlign="center" align="center" allowSort="true" width="50">差评</div>
                    </div>
                </div>
              </div>
          </div>
    </div>
</body>
</html>
<script type="text/javascript">

	mini.parse();
	var grid = mini.get("dg");
	
	
	grid.on("drawcell", function (e) {
	    var record = e.record,
	        column = e.column,
	        field = e.field,
	        value = e.value;

	    //计算新增客户总数
	    if (field == "newCustomerTotal")
    	{
	    	e.cellHtml = record.newCustomerWK + record.newCustomerNet + record.newCustomerOther;
    	}

        if(field == "houseSeeTotal")
        {
            e.cellHtml = record.houseSeeFirst + record.houseSeeSecond;
        }

        if(field == "houseTotal")
        {
            e.cellHtml = record.housePublish + record.houseKey + record.houseEntrust + record.houseImage + record.houseMaintain;
        }

        //计算新增客户总数
        if (field == "agentStrokeAVG")
        {
            var agentStroke = record.agentStroke;
            var dataTotalCount = record.dataTotalCount;
            var value = 0;
            if(dataTotalCount > 0)
            {
                value = agentStroke / dataTotalCount;
            }
            else
            {
                value = 0;
            }
            value = value.toFixed(2);
            e.cellHtml = value;
        }

	});
	
	var dateStart ='<%=startDate %>';
    var dateEnd = '<%=endDate %>';

    autoSelected();
	search();

    function autoSelected()
    {
        var cityCbo = mini.get("cityId");
        if(cityCbo.data.length == 2)
        {
            cityCbo.select(1);
            onCityChanged();
            cityCbo.disable();
        }
    }
	
	function search(){
		var cityId= mini.get("cityId").getFormValue();
		var areaId= mini.get("areaId").getFormValue();
        var queryType = mini.get("queryType").getValue();
        if(queryType == 2)
        {
            grid.showColumn("statisticsDate");
            grid.showColumn("followCustomerN");
            grid.clearSort();
        }
        else
        {
            grid.hideColumn("statisticsDate");
            grid.hideColumn("followCustomerN");
            grid.clearSort();
        }
		grid.load({cityId:cityId,areaId:areaId,dateStart:dateStart,dateEnd:dateEnd,queryType:queryType});
	}
	
	function reset()
	{
		new mini.Form("#form1").clear();
        $("#daterange").val("<%=daterange%>");
        autoSelected();
	}

    function onCityChanged(e) {
        var cityCbo = mini.get("cityId");
        var areaCbo = mini.get("areaId");
        var areaOrgType = mini.get("areaOrgType");
        var id = cityCbo.getFormValue();

        if(id != null && id != "") {
            var url = "${ctx}/areaorg/getPartnerByCityId.action";
            url += "?cityId=" + id;
            areaCbo.setUrl(url);
        }
        else {
            areaCbo.data = "[]";
        }

        //唯一项自动选择
        if(areaCbo.data.length == 2)
        {
            areaCbo.select(1);
        }
    }

    
    //////////////////////////////////////////////////
    function onTimeRenderer(e) {
        var value = e.value;
        if (value) 
        {
            value = new Date(value); 
            return mini.formatDate(value, 'yyyy-MM-dd');
        }
        return "";
    }
    
    function downLoadExcel()
    {
    	var cityId= mini.get("cityId").getFormValue();
        var areaId= mini.get("areaId").getFormValue();
        var queryType = mini.get("queryType").getFormValue();
    	window.location.href = "${ctx}/areaexportExcle/download_excel.action?cityId="
                + cityId + "&areaId=" + areaId  + "&dateStart=" + dateStart + "&dateEnd=" + dateEnd+ "&queryType="+queryType;
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
            separator:' 至 ',
            daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
            monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',
                '七月', '八月', '九月', '十月', '十一月', '十二月' ],
            applyLabel : '确定',
            cancelLabel : '取消',
            "customRangeLabel": "自定义"
        },
        "startDate": "<%=startDate %>",
        "endDate": "<%=endDate %>"
    }, function(start, end, label) {
        dateStart=start.format('YYYY-MM-DD');
        dateEnd=end.format('YYYY-MM-DD');
    });

    function showFormula()
    {
        var alertMsg = "行程量计算公式:<br>";
        alertMsg += "发布 + 1/次<br>";
        alertMsg += "实景 + 1/次<br>";
        alertMsg += "钥匙 + 1/次<br>";
        alertMsg += "速销 + 10/次<br>";
        alertMsg += "悟空 + 1/次<br>";
        alertMsg += "网络 + 1/次<br>";
        alertMsg += "其它 + 1/次<br>";
        alertMsg += "带盘 + 1/次<br>";
        alertMsg += "好评 + 1/次<br>";
        alertMsg += "差评 - 1/次<br>";
        alertMsg += "自营指数 > 20 + 1次<br>";
        alertMsg += "房源跟进 > 20 + 1次<br>";
        alertMsg += "客户跟进 > 20 + 1次<br>";
        alertMsg += "无效审核数量 + 1/次<br>";
        mini.alert(alertMsg);
    }
</script>