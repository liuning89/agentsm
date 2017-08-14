<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<!--[if IE 8]>
<html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]>
<html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html>
<!--<![endif]-->
<%-- <jsp:include page="../common/head.jsp"></jsp:include> --%>
<head>
    <title>评价明细</title>
   <!--  <meta http-equiv="content-type" content="text/html; charset=UTF-8" /><link href="../demo.css" rel="stylesheet" type="text/css" /> -->
	<style type="text/css">
		.datagrid-header-rownumber,.datagrid-cell-rownumber{
   			width:60px;
   		}
	</style>
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
    <script type="text/javascript" src="${ctx}/scripts/boot.js"></script>
    <!-- <script src="../../scripts/boot.js" type="text/javascript"></script> -->

        </head>
<!-- BEGIN BODY -->
<body class="page-header-fixed page-sidebar-fixed page-footer-fixed">
    <h1>评价明细列表</h1>      

    <div style="padding-bottom:5px;">
        
        <span>评价时间：</span>
      	  <input type="text" style="display: none" id="agentId" value="${agentId}"/>
        	<input id="startTime" name="startTime" class="mini-datepicker" /> - 
        	<input id="endTime" name="endTime" class="mini-datepicker" />
        
        <input type="button" value="搜索" onclick="search()" size="10" />
        <input type="button" value="重置" onclick="clean()"/>
        
        
    </div>
    <div class="mini-fit" style="background:red;height:100px;">
	<div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" 
	    url="../agent/agentEvaluateData.action"  idField="id" sizeList="[20,30,50,100]" pageSize="20">
	    <div property="columns">
	        <div type="indexcolumn" ></div>
	        <div field="updateTime" width="120" headerAlign="center" align="center" renderer="onTimeRenderer" allowSort="true">评价时间</div>    
	        <div field="label" width="120" renderer="onEvaluateGood" align="center" headerAlign="center" allowSort="true">好评标签</div>                            
	        <div field="label" width="100" renderer="onEvaluateBad" align="center" headerAlign="center">差评标签</div>
	        <div field="comment" width="100" align="center" headerAlign="center" allowSort="true">补充内容</div>                                
	        <div field="name" width="100" align="center" headerAlign="center" allowSort="true"  dataType="string">客户</div>
	        <div field="guestPhoneNum" width="100" align="center" headerAlign="center"  allowSort="true">客户手机号</div>                
	    </div>
	</div>   
   </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        var aid = $("#agentId").val();
        grid.load({'agentid':aid}); ///初始加载...带上传过来的参数.
       
        //获取url参数
       /*  (function ($) {
            $.getUrlParam = function (name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = window.location.search.substr(1).match(reg);
                if (r != null) return unescape(r[2]); return null;
            }
        })(jQuery);

        //方法二：
        var xx = $.getUrlParam('ddd');
       alert(xx); */
       
       
        function search() {
            var startTime = mini.get("startTime");
            var endTime = mini.get("endTime");
            
            if (startTime.getValue() != '' && endTime.getValue() != '') {
				if (endTime.getValue().getTime() < startTime.getValue().getTime()) {
					mini.alert("结束时间不可小于开始时间");
					return;
				}
			}
            
            grid.load({'agentid':aid, 'startTime': startTime.getFormValue(),'endTime':endTime.getFormValue()});
        }
        function clean(){
        	 var t = mini.get("startTime");
             t.setValue("");
             var t1 = mini.get("endTime");
             t1.setValue("");
         }
        	
        $("#key").bind("keydown", function (e) {
            if (e.keyCode == 13) {
                search();
            }
        });
        
        function onEvaluateGood(e){
        	if(e.value != null && e.value != ""){
        		var evalue = e.value;
        		var evue = evalue.split("-");
        		if(evue[0] == "1"){
        			return evue[1];
        		}
        	}
        	return "";
        }
        function onEvaluateBad(e){
        	if(e.value != null && e.value != ""){
        		var evalue = e.value;
        		var evue = evalue.split("-");
        		if(evue[0] == "2"){
        			return evue[1];
        		}
        	}
        	return "";
        }
        function onTimeRenderer(e) {
            var value = e.value;
            value = new Date(value); 
            if (value) return mini.formatDate(value, 'yyyy-MM-dd HH:mm');
            return "";
        }
    </script>
</body>
</html>