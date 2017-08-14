<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    Date date=new Date();//取时间
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
   // calendar.add(calendar.MONTH,-1);//把日期往后增加一天.整数往后推,负数往前移动
    date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
    String dateString = formatter.format(date);
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

	<form id="contactform" method="post">
        <div style="padding-left:11px; padding-bottom:5px;" align="center">
            <table style="table-layout:fixed;padding-top: 20px;">
                    <tr>
                    	<td>
                    	    标题：<input id="title" value="${push.title}" type="text" readonly="readonly"  style="width: 300px;" />
                    	</td>
                    </tr>
                  
                    <tr>
                    	<td>
                    	    内容：<textarea id="pushContent"   name="pushContent" readOnly="true" class="mini-textarea" style="width: 300px;height: 180">${push.pushcontent}</textarea> <br />
                    	</td>
                    </tr>
                     
                    
                    <tr>
                    	<td>
                    		 <div id="ck1" name="product" class="mini-checkbox" readOnly="true" value="${push.istiming}" checked="false" readOnly="false" text="定时发送" ></div><br/>
                    	</td>
                    </tr>
                    <tr id="settime" style="display: none">
                    	<td>
                    	 
							发送时间：<input id="date2" value="${push.pushtime }" readOnly="true" class="mini-datepicker" style="width:200px;"  format="yyyy-MM-dd H:mm:ss" minDate=<%=dateString %> timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>
                    	</td>
                    </tr>
                    
                     <tr id="limit" >
                    	  <td>发送范围：<input name="pushPlatform"  readOnly="true" value="${push.pushPlatform }"  class="mini-radiobuttonlist"   repeatLayout="table" data=[{id:3,text:'全部'},{id:4,text:'指定范围'}] textField="text" valueField="id" ></input></td>
                    </tr>
            </table>
        </div>
        
        <div style="height:300px;display: none" id="fdg">
          <div id="dg" class="mini-datagrid"  pageSize=20 style="height:100%;"  showPager="false" url="message/getCompanyAndCityByMsgId.action?pushId=${push.id}" multiSelect="true" showfooter="false">
              <div property="columns">
                  <div field="companyName"  align="left" headeralign="left" width="20">公司</div>
                  <div field="cityName"  align="left" headeralign="left" width="20">城市</div>
              </div>
          </div>
    </div>
       
    </form>
    <script type="text/javascript">

        mini.parse();
        var grid = mini.get("dg");
        //id="+id+"&title="+title+"&pushContent="+istiming+"&pushtime="+pushtime,
     $(function(){
      	if("${push.istiming}" == 1){
      		
      		var tc = mini.get("ck1");
            tc.setChecked(true);
            
            var td = mini.get("date2");
            
            td.setValue("${push.pushtime}");
            $("#settime").css("display","block");
      	}
      	if("${push.pushPlatform}"==4){
        	$("#fdg").css("display","block");
        	mini.get("dg").load();

        }
     });
      	
      	
        
        function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        
        function onCancel(e) {
            //CloseWindow("cancel");
        	window.CloseOwnerWindow();
        }
        function ontimeValueChanged(e) {
          //  alert(this.getFormValue());
        }
        
    	
        </script>
</body>
</html>