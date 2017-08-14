<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head path="<%=basePath%>">
    <base href="<%=basePath%>" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
<title></title>
</head>
<body>
          <div id="dg" class="mini-datagrid"  pageSize=20 style="height:100%;"  showPager="false" url="message/getCompanyAndCity.action" multiSelect="true" showfooter="false">
              <div property="columns">
              	<div type="checkcolumn" width="7" field="id"></div>
                  <div field="pcompanyName"  align="left" headeralign="left" width="20">公司</div>
                  <div field="pcityName"  align="left" headeralign="left" width="20">城市</div>
              </div>
          </div>
      
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("dg");
       	grid.load();
       
        </script>
</body>
</html>