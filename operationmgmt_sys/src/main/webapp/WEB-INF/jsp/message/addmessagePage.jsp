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
                    	    标题：<input id="title" class="mini-textbox" emptyText="请输入标题"  style="width: 300px;" /> 
                    	</td>
                    </tr>
                  
                    <tr>
                    	<td>
                    	    内容：<textarea id="pushContent" name="pushContent" class="mini-textarea" emptyText="请输入短信内容" style="width: 300px;height: 180"></textarea> <br />
                    	</td>
                    </tr>
                     
                    
                    <tr>
                    	<td>
                    		 <div id="ck1" name="product" class="mini-checkbox" checked="false" readOnly="false" text="定时发送" onvaluechanged="onValueChanged"></div><br/>
                    	</td>
                    </tr>
                    <tr id="settime" style="display: none">
                    	<td>
                    	 
							发送时间：<input id="date2" class="mini-datepicker" style="width:200px;"  format="yyyy-MM-dd H:mm:ss" minDate=<%=dateString %> timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>
                    	</td>
                    </tr>
                    
                     <tr id="limit" > <!--  -->
                    	  <td>发送范围：<input name="pushPlatform" class="mini-radiobuttonlist" value="3" onvaluechanged="valueChange"  repeatLayout="table" data=[{id:3,text:'全部'},{id:4,text:'指定范围'}] textField="text" valueField="id" ></input></td>
                    </tr>
            </table>
        </div>
        
        <div style="height:300px;display: none" id="fdg">
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:99%;">
	            <tr>     
                     <td id="search">
                      <span>加盟公司</span>
                       <input id="companyId" name="companyId" class="mini-combobox" style="width:135px;" textField="text" valueField="id"
		               url="amoutset/getFranchiseeList.action" value="" showNullItem="true"  allowInput="true"
		               onvalidation="onComboValidation" emptyText="请选择" nullItemText="请选择" />
		                <span>城市:</span>
				    <input id="cityId" name="cityId" url="areaorg/getCityListByUserId.action" class="mini-combobox" style="width:135px;" textField="name" valueField="id"
				           allowInput="true"
				          />
				    
				                        <a class="mini-button" id="searchBtn" onclick="search()" iconCls="icon-search">查询</a>
				                        <a class="mini-button" id="resetBtn" onclick="reset();" iconCls="icon-cancel">重置</a>
				                    </td>
                </tr>
            </table>           
        </div>
          <div id="dg" class="mini-datagrid"  pageSize=20 style="height:100%;"  showPager="false" url="message/getCompanyAndCity.action" multiSelect="true" showfooter="false">
              <div property="columns">
              	<div type="checkcolumn" width="7" field="id"></div>
                  <div field="companyName"  align="left" headeralign="left" width="20">公司</div>
                  <div field="cityName"  align="left" headeralign="left" width="20">城市</div>
                  
              </div>
          </div>
    </div>
       <div style="text-align:center;margin-top: 60px;">               
           <a class="mini-button" onclick="onsendMessage(1)" style="width:60px;margin-right:20px;">发送</a>       
           <a class="mini-button" onclick="onsendMessage(2)" style="width:60px;margin-right:20px;">保存</a>
           <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
       </div>        
    </form>
    <script type="text/javascript">

        mini.parse();
        var grid = mini.get("dg");
        //id="+id+"&title="+title+"&pushContent="+istiming+"&pushtime="+pushtime,
     
      	var t = mini.get("title");
      	t.setValue("${title}");
      	
      	var p = mini.get("pushContent");
      	p.setValue("${pushContent}");
      	
      	if("${istiming}" == 1){
      		
      		var tc = mini.get("ck1");
            tc.setChecked(true);
            
            var td = mini.get("date2");
            
            td.setValue("${pushtime}");
            $("#settime").css("display","block");
      	}
      	
    	function onComboValidation(e) {
            var items = this.findItems(e.value);
            if (!items || items.length == 0) {
               	this.setValue('');
            }
        }
        function onValueChanged(e) {
            var checked = this.getChecked();
            if(checked){
            	$("#settime").css("display","block");
            }else{
            	$("#settime").css("display","none");
            	
            }
            
        }
        function valueChange(e) {
            var checked = e.value;
            if(checked==4){
            	$("#fdg").css("display","block");
            	mini.get("dg").load();

            }else{
            	$("#fdg").css("display","none");
            	
            }
            
        }
        function CloseWindow(action) {            
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }
        
        function onsendMessage(e) {
		
        var pushPlatform = mini.getbyName("pushPlatform").getValue();
       	 var cid = [];
       	 var cityId=[];
       	 var cids='';
       	 var cityIds='';
        if(pushPlatform==4){
        	var rows = grid.getSelecteds();
            if (rows.length>0) {
                 for (var i = 0, l = rows.length; i < l; i++) {
                     var r = rows[i];
                     cid.push(r.id);
                     cityId.push(r.cityId);
                 }
            } else {
                mini.alert("请选中至少一条记录");
            }
        }
        if(cityId!=''){
        	cids=cid.join(",");
        	cityIds = cityId.join(",");
        }
       	 var a = mini.get("pushContent");
       	var title = mini.get("title");
    	if(a.getValue().trim() == ""){
       		mini.alert("消息内容不能为空！");
       		return false;
       	}
       	if(title.getValue().trim() == ""){
       		mini.alert("标题不能为空！");
       		return false;
       	}
       	
        	 var t = mini.get("date2");
			var ck = mini.get("ck1");
           	onCancel();
           	debugger;
           	window.Owner.sendData(a.getValue(),t.getFormValue(),ck.checked,e,1,title.getValue(),"${id}",cids,cityIds,pushPlatform);
        }
        function onCancel(e) {
            //CloseWindow("cancel");
        	window.CloseOwnerWindow();
        }
        function ontimeValueChanged(e) {
          //  alert(this.getFormValue());
        }
        
    	function search(){
    		var form = new mini.Form("#search");
    		var data = form.getData(true);
    		lc.removeObjectEmptyValueProperty(data);
    		grid.load(data);
    		
    	}
    	function reset(){
    		var form = new mini.Form("#search");
    		form.reset();
    	}
        </script>
</body>
</html>