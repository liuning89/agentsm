<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>客户列表</title>
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
            url="${ctx}/customer/getCustomerFollow.action?customerId=${customerId}"  idField="id" allowResize="false"
            sizeList="[20,30,50,100]" pageSize="20">
            <div property="columns">
                <div type="indexcolumn">序号</div>
                <div field="note" width="100" headerAlign="center" align="center" allowSort="false">跟进内容</div>
                <div field="name" width="100" headerAlign="center" align="center" allowSort="false">跟进人</div>
                <div field="createTime" width="100" align="center" renderer="onTimeRenderer" headerAlign="center">添加时间</div>
                <%--<div name="action" width="300" align="center" headerAlign="center">操作</div>--%>
            </div>
        </div>
    </div>
    <!-- 结果展示table end -->
    <!-- 这里开始写业务页面end -->
    
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");

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

    </script>
	
</body>
<!-- END BODY -->
</html>
