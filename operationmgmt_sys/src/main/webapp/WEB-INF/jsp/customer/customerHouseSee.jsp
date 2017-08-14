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
                        url="${ctx}/customer/getCustomerHouseSee.action?customerId=${customerId}"  idField="id" allowResize="false"
                        sizeList="[20,30,50,100]" pageSize="20">
                        <div property="columns">
                            <div field="id" width="100" align="center" headerAlign="center">带看编号</div>
                            <div field="name" width="100" headerAlign="center" align="center" allowSort="false">带看人</div>
                            <div field="seeTime" width="100" align="center" renderer="onTimeRenderer" headerAlign="center">带看时间</div>
                            <div field="houseId" width="100" headerAlign="center" align="center" allowSort="false">带看房源编号</div>
                            <div field="mark" width="100" headerAlign="center" align="center" allowSort="false">带看说明</div>
                        </div>
                    </div>
                </div>
				<!-- 结果展示table end -->
				<!-- 这里开始写业务页面end -->
			</div>
		</div>
	</div>
	<!-- BEGIN FOOTER -->
    
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        
        grid.on("drawcell", function (e) {
    	    var record = e.record, column = e.column, field = e.field, value = e.value;
            if (column.name == "action") {
                e.cellStyle = "text-align:center";
                var ht = '<a href="javascript:showDetail(\'' + record.agreementKey + '\')">查看</a>&nbsp;';
                e.cellHtml = ht ;
            }
        });

        //房源转移
        function showDetail(key) {
            mini.open({
                url: "${ctx}/customer/gotoShowImage.action?key=" + key,
                title: "查看图片", width: 400, height: 320,
                onload: function () {
                    //var iframe = this.getIFrameEl();
                    //var data = { key: key};
                    //iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    //grid.reload();
                }
            });
        }
        
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

        grid.load();

    </script>
	
</body>
<!-- END BODY -->
</html>
