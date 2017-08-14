<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>实景图片审核列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
    <script src="${ctx}/scripts/miniui/miniui.js" type="text/javascript"></script>
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
<body >
    <div style="padding-bottom:5px;">
    	<span>房源编号：</span><input name="houseId" id="houseId"  textField="text" valueField="id" />
    	
    	<span>房源地址：</span><input name="initname" id="initname" textField="text"  />
      
    	
     <span>类型：</span>
        <input name="videoType" id="videoType" class="mini-combobox"  textField="text" valueField="id" data="[{id:1,text:'房屋视频'},{id:2,text:'小区视频'}]" value=2 />
              
        
        <br />
       <span>审核状态：</span> <input id="auditStatus" name=""auditStatus"" class="mini-combobox" textField="text" valueField="id" data="[{id:3,text:'待审核'},{id:5,text:'通过'},{id:6,text:'驳回'}]" value=3/>
          

  <input type="button" value="查找" onclick="search()"/>
        <input type="button" value="重置" onclick="reset()"/>
    </div>
     <!--撑满页面-->
        <div class="mini-fit" style="background:red;height:100px;">
    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" 
        url="${ctx}/houseVideo/getLandlordVideoAuditList.action"  idField="id" allowResize="false"
        sizeList="[20,30,50,100]" pageSize="20">
        <div property="columns">
            <!-- <div type="indexcolumn" style="display: none"></div> -->
            <div field="houseId" width="120"  headerAlign="center" align="center" allowSort="true">房源编号</div>                            
            <div field="initname" width="100"  align="center" headerAlign="center">房源地址</div>
            <div field="videokey" width="100" align="center" headerAlign="center">视频</div>
            <div field="type" width="100" align="center" headerAlign="center">类型</div>
            <div field="agentName" width="120" renderer="onEvaluateBad" align="center" headerAlign="center"  allowSort="false">上传人/部门</div>  
            <div field="createTime" width="100" headerAlign="center" align="center" allowSort="false" renderer="onTimeRenderer" >上传时间</div>
            <div field="videostatus" width="100" headerAlign="center" align="center" allowSort="false">审核状态</div>
            <div field="auditor" name="auditorColumn" width="100" headerAlign="center" align="center" allowSort="false">审核人</div>
            
            <div field="updatetime" name="auditTimeColumn" width="100" headerAlign="center" renderer="onTimeRenderer" align="center" allowSort="false">审核时间</div>
           
           <div field="rejectreason" name="rejectreasonColumn" width="100" headerAlign="center" align="center" allowSort="false">驳回理由</div>
            
            <div field="context"  name="contextColumn" width="100" headerAlign="center"  align="center" allowSort="false">操作</div>
               
        </div>
    </div>   
    </div>
    
    <script type="text/javascript">
        mini.parse();
        mini.get("auditStatus").select(0);
        var grid = mini.get("datagrid1");
        var auditStatus = mini.get("auditStatus").getFormValue();//审核状态
        
        /* var auditStatus = mini.get("auditStatus").getFormValue(); */
         var videoType = mini.get("videoType").getFormValue();//审核状态

        grid.load({videoType: videoType,auditStatus:auditStatus});
     /*    grid.hideColumn("loginNameColumn");
        grid.hideColumn("auditTimeColumn");
        grid.hideColumn("rejectreasonColumn"); */
        grid.on("drawcell", function (e) {
        	  var record = e.record,
	              column = e.column,
	              field = e.field,
	              value = e.value;
        	if (field  == "agentName") {
        			e.cellHtml = "业主";
            }
        	if(field == "videokey"){
        		e.cellHtml = "<a href='javascript:void(0)' onclick='openToShow(\"" + e.value+ "\")'>播放</a>"; 
        	}
        	//0:视频待上传,1:视频上传完成,2:视频处理中,3:视频处理完成(待审核),4:视频处理失败,5:审核成功,6:审核失败',
		 var videoTypes = mini.get("videoType").getFormValue();//审核状态

            if(field == "type"){
                if(videoTypes == 1){
                    e.cellHtml = "房屋视频";

                }else{
                    e.cellHtml = "小区视频";
                }
            }
        	if(field == "videostatus"){
        		if(value == 3){
        			e.cellHtml = "待审核";
        		}else if(value == 5){
        			e.cellHtml = "审核通过";
        		}else if(value == 6){
        			e.cellHtml = "审核不通过";
        		}else{
        			e.cellHtml = "视频网站处理中";
        		}
        	}
        	
        	if(field == "initname"){
        		if(videoTypes == 1){
        			
        			e.cellHtml= record.estateName + record.buildingName + record.room; 
        		}else{
        			e.cellHtml = record.initname;
        		}
        	}
        	
        	if(field == "context"){
        		// e.cellHtml = '<a href="javascript:openDetail(' + e.rowIndex + ',\'' + record.agentId  + '\',\''+record.houseId + '\')">操作</a> ' 
        		if(record.videostatus != 5){
        			var estatename = "";
       				estatename = record.estateName + record.buildingName + record.room; 
        			
        		 	//e.cellHtml = '<a href="javascript:openDetail(' + e.rowIndex + ',\'' + record.agentId  + '\',\''+record.houseId + '\',\'' +record.mobile + '\',\'' +record.token +'\',\''+ sp +'\',\''+ record.auditStatus+'\',\''+ rooms+'\',\''+ mini.formatDate(record.createTime, 'yyyy-MM-dd HH:mm:ss') +'\')">操作</a> '
        		 	
        		 	e.cellHtml = "<a href='javascript:void(0)' onclick='passData(" + record.id + ",\"" + record.mobile+"\",\"" + estatename + "\",\"" + record.houseId  +"\")'>通过</a> | <a href='javascript:void(0)' onclick='openRejectedPage(" + record.id + ",\"" + record.mobile + "\",\""+ record.phoneNum + "\",\"" + estatename+ "\",\"" + record.houseId   +"\")'>驳回</a>";
        		 	
        		}else{
        			
        		}
        	}
        });
        
       
        function search() {
        	
            var houseId = document.getElementById("houseId").value;//编号
            var initname = document.getElementById("initname").value;//地址
            var videoType = mini.get("videoType").getFormValue();//类型
            
            var auditStatus = mini.get("auditStatus").getFormValue();//审核状态
            if(auditStatus != 3){
                grid.hideColumn("contextColumn");
            }else{
                grid.showColumn("contextColumn");
            }
            grid.load({ houseId: houseId, initname: initname, videoType: videoType, auditStatus: auditStatus});
        }
        
    	function openToShow(key,imagekey) {
    		mini.open({
                url: "${ctx}/houseVideo/openToShow.action?key="+key+"&imagekey="+imagekey,
                title: "视频播放",
    			width: 640,
    			height: 360,
                ondestroy: function (action) {
                }
            });
    	}
        
        function reset()
        {

            document.getElementById("houseId").value = "";

            document.getElementById("initname").value = "";

             mini.get("videoType").select(0);
            
            mini.get("auditStatus").select(0);
       	 
        }
        
        $("#key").bind("keydown", function (e) {
            if (e.keyCode == 13) {
                search();
            }
        });
        
        //////////////////////////////////////////////
        function onComboValidation(e) {
            var items = this.findItems(e.value);
            if (!items || items.length == 0) {
               	this.setValue('');
            }
        }

        function onTimeRenderer(e) {
        	
            var value = e.value;
            if (value){
	            value = new Date(value); 
            	return mini.formatDate(value, 'yyyy-MM-dd HH:mm:ss');
            } 
            return "";
        }
        
        
        //驳回理由
        function openRejectedPage(id,mobile,phoneNum,estatename,houseId) {
        	
            var videoType = mini.get("videoType").getFormValue();//类型
   	     mini.open({
   	         url: "${ctx}/houseVideo/openRejectedPage.do?videoType="+videoType+"&id="+id+"&mobile="+mobile+"&estatename="+encodeURI(encodeURI(estatename))+"&phoneNum="+phoneNum+"&houseId="+houseId,
   	         title: "驳回理由",
   	         width: 400, 
   	         height:200,
   	         ondestroy: function (action) {
   	         	//contactgrid.reload();
   	         },
   	         onload: function () {
   	          var iframe = this.getIFrameEl();
   	      }
   	     });
   	}
        //通过
         function passData(id,mobile,estateName,houseId){
             var videoType = mini.get("videoType").getFormValue();//类型
        	lc.mask("正在处理中,请稍后...");
        	$.ajax({
        		url : "${ctx}/houseVideo/updateAudit.action?videoType="+videoType+"&mobile="+mobile+"&id="+id+"&flag=0"+"&estateName="+encodeURI(encodeURI(estateName))+"&ownerId=1"+"&houseId="+houseId,
                cache : false,
                dataType : 'text',
                success : function(data) {
            		 if(data == 1){
            			 lc.unmask();
            			 mini.alert("审核成功!","成功",function(){closed();});
            		 }else if(data == 1000){
                         lc.unmask();
                         mini.alert("审核失败,该房源已经失效,建议驳回!","错误",function(){closed();});
                     }else{
            			 lc.unmask();
            			 mini.alert("审核失败,请稍后重试!","错误",function(){closed();});
            		 }
                }
            });
        }
        //驳回
        function rejectedData(rejectreason,videoType,id,mobile,estatename,phoneNum,houseId){
      //      var videoType = mini.get("videoType").getFormValue();//类型
        	lc.mask("正在处理中,请稍后...");
        	$.ajax({
        		url : "${ctx}/houseVideo/updateAudit.action?videoType="+videoType+"&mobile="+mobile+"&id="+id+"&rejectreason="+encodeURI(encodeURI(rejectreason))+"&estateName="+encodeURI(encodeURI(estatename))+"&ownerId=1"+"&phoneNum="+phoneNum+"&houseId="+houseId,
                cache : false,
                dataType : 'text',
                success : function(data) {
            		 if(data == 1){
            			 lc.unmask();
            			 mini.alert("驳回成功!","成功",function(){closed();});
            		 }else{
            			 lc.unmask();
            			 mini.alert("驳回失败,请稍后重试!","错误",function(){closed();});
            		 }
                }
            });
        }
        
         function closed(){
         	//parent.lf.agentsm.index.refreshHouseImageListTabs();
         	search();
        //	window.CloseOwnerWindow();
         }
    </script> 
    <div class="description">
       <%-- <jsp:include page="../common/footer.jsp"></jsp:include> --%>
    </div>
</body>
</html>