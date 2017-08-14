<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>客户标A列表</title>
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

<!-- BEGIN BODY -->
<body class="page-header-fixed page-sidebar-fixed page-footer-fixed">

                
    <!-- 结果展示table start -->
    <div class="mini-fit" style="background:red;height:100px;">
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;"
            url="${ctx}/customer/getCustomerAMark.action?customerId=${customerId}"  idField="id" allowResize="false"
            sizeList="[20,30,50,100]" pageSize="20">
            <div property="columns">
                <div type="indexcolumn">序号</div>
                <div field="name3A" width="100" headerAlign="center" align="center" allowSort="false">3A客户标记人</div>
                <div field="status" width="100"  align="center" renderer="onStatusRenderer" headerAlign="center">标记类型</div>
                <div field="createTime" width="100" align="center" renderer="onTimeRenderer" headerAlign="center">标A时间</div>
                <div field="logUpTime" width="100" align="center" headerAlign="center">取消时间</div>
            </div>
        </div>
    </div>
	<!-- BEGIN FOOTER -->
    
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");

        grid.on("drawcell", function (e) {
            var record = e.record, column = e.column, field = e.field, value = e.value;
            if (field == "logUpTime") {
                var status = record.status;
                var logUpTime = record.logUpTime;

                if(status == 0)
                {
                    value = new Date(logUpTime);
                    e.cellHtml = mini.formatDate(value, 'yyyy-MM-dd HH:mm');
                }
                else
                {
                    e.cellHtml = "-----";
                }
            }
        });
        

        grid.load();

        //////////////////////////////////////////////////
        function onTimeRenderer(e) {
            var value = e.value;
            if (value)
            {
                value = new Date(value);
                return mini.formatDate(value, 'yyyy-MM-dd HH:mm');
            }
            return "-----";
        }

        //////////////////////////////////////////////////////////////////////////
        var Status = [{ id: 0, text: '取消' }, { id: 1, text: '标记'}];
        function onStatusRenderer(e) {
            for (var i = 0, l = Status.length; i < l; i++) {
                var g = Status[i];
                if (g.id == e.value) return g.text;
            }
            return "-----";
        }

    </script>
	
</body>
<!-- END BODY -->
</html>
