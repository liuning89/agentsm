<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<html>
<head path="<%=basePath%>">
<base href="<%=basePath%>" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script src="scripts/boot.js" type="text/javascript"></script>
</head>
<body style="height: 100%;" path="<%=basePath%>">
	<!--列表-->
	<div id="housefollowUpDiv" class="mini-fit" style="height: 400px">
		<h>房源跟进列表</h>
		<div id="dg" class="mini-datagrid" style="height: 95%;" pageSize='30'
			sizeList="[30]"
			url="houseResource/sell/getLfHouseFollowUpList.do?houseId=${houseId}"
			multiSelect="true" showfooter="false">
			<div property="columns">
				<div field="createTime" width="20" headerAlign="center" align="left"
					allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss">跟进时间</div>
				<div field="type" width="15" headerAlign="center" align="center"
					allowSort="true">类型</div>
				<div field="note" width="30" align="left" headerAlign="center">跟进内容</div>
				<div field="storeName" width="15" headerAlign="center"
					align="center">跟进门店</div>
				<div field="realName" width="15" headerAlign="center" align="center">跟进人</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	mini.parse();
	var grid = mini.get("dg");
	grid.on("drawcell",function(e) {
						var typeDict = {
							'1' : '文本',
							'2' : '录音'
						};
						var record = e.record, field = e.field, value = e.value, column = e.column;
						if (field == "type") {
							e.cellHtml = typeDict[value];
						} else if (field == "note") {
							if (record.type == '2' && IsURL(value)) {
								e.cellHtml = '<a href="javascript:download(\''
										+ record.id + '\')">下载</a>' + ' | '
										+ '<a href="javascript:openToShow(\''
										+ record.id + '\');">播放</a>';
							} else {
								e.cellHtml = value;
							}
						}
					});
	grid.load();

	function openToShow(id) {
		mini.open({
            url: "houseResource/sell/openToShow.do?id="+id,
            title: "音频播放",
			width: 310,
			height: 50,
            ondestroy: function (action) {
            }
        });
	}

	function download(id) {
		var exportframe = $('<iframe style="display:none"/>');
		var url = encodeURI("houseResource/sell/downLfHouseFollowUpVideo.do?id="
				+ id);
		exportframe.attr({
			'src' : url
		});
		$('#housefollowUpDiv').append(exportframe);
	}

	function IsURL(str_url) {
		var sr = /((http|ftp|https|file):\/\/([\w\-]+\.)+[\w\-]+(\/[\w\u4e00-\u9fa5\-\.\/?\@\%\!\&=\+\~\:\#\;\,]*)?)/;
		var re = new RegExp(sr);
		//re.test() 
		if (re.test(str_url)) {
			return (true);
		} else {
			return (false);
		}
	}
</script>
</html>
