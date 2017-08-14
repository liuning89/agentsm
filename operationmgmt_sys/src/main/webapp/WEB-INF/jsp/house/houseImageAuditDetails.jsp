<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>图片审核详情</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
    <script src="${ctx}/scripts/boot.js?v=${version}" type="text/javascript"></script>
    <script src="${ctx}/scripts/miniui/swfupload.js" type="text/javascript"></script>
    
</head>
<body style="height: 100%">
	<input type="text" style="display: none" id="agentId" value="${agentId}"/>
	<input type="text" style="display: none" id="houseId" value="${houseId}"/>
	 <fieldset id="fd0" style="width:98%;display: none">
        <legend><span>首图</span></legend>
               <div class="fieldset-body">
                   <ul style="text-align: left;">
                   	<li style="list-style-type:none;" id="st"></li>
                   </ul>
               </div>
	    </fieldset>
      <fieldset id="fd1" style="width:98%;display: none">
        <legend><span>客厅</span></legend>
             <div class="fieldset-body">
                 <ul style="text-align: left; ">
                 	<li style="list-style-type:none;" id="kt"></li>
                 </ul>
             </div>
    </fieldset>
    
      <fieldset id="fd2" style="width:98%;display: none">
        <legend><span>卧室</span></legend>
           <div class="fieldset-body">
               <ul style="text-align: left;">
               	<li style="list-style-type:none;" id="ws"></li>
               </ul>
           </div>
    </fieldset>
    
      <fieldset id="fd3" style="width:98%;display: none">
        <legend><span>厨房</span></legend>
            <div class="fieldset-body">
                <ul style="text-align: left;">
                	<li style="list-style-type:none;" id="cf"></li>
                </ul>
            </div>
    </fieldset>
    
      <fieldset id="fd4" style="width:98%;display: none">
        <legend><span>卫生间</span></legend>
          <div class="fieldset-body">
              <ul style="text-align: left;">
              	<li style="list-style-type:none;" id="wsj"></li>
              </ul>
          </div>
    </fieldset>
    
      <fieldset id="fd5" style="width:98%;display: none">
        <legend><span> 阳台</span></legend>
          <div class="fieldset-body">
              <ul style="text-align: left;">
              	<li style="list-style-type:none;" id="yt"></li>
              </ul>
          </div>
    </fieldset>
    
      <fieldset id="fd6" style="width:98%;display: none">
        <legend><span>房型图</span></legend>
        <div class="fieldset-body">
             <ul style="text-align: left;">
             	<li style="list-style-type:none;" id="fxt"> </li>
             </ul>
         </div>
      </fieldset>
      <fieldset id="fd7" style="width:98%;display: none">
        <legend><span>外观图</span></legend>
          <div class="fieldset-body">
              <ul style="text-align: left;">
              	<li style="list-style-type:none;" id="wgt"></li>
              </ul>
          </div>
    </fieldset>
    
    房屋卖点:
    <br/>
    <HR SIZE=5 />
    <div id="md">
		${sellPoint}
    </div>
    
    
     <div align="right">
	     <a class="mini-button" onclick="closed" style="width: 60px;margin-right: 20px;">取消</a>
	      <a class="mini-button" onclick="passData" style="width: 60px;margin-right: 20px;">通过</a>
	     <a class="mini-button" onclick="openRejectedPage" style="width: 60px;margin-right: 20px;">驳回</a>
     </div>
      
      <div id="spt">
      	
      </div>
    <script type="text/javascript">
        mini.parse();
        $(document).ready(function(){
        	 $.ajax({
                 url : "${ctx}/houseImage/loadHouseImage.action?houseId=${houseId}&agentId=${agentId}&mobile=${mobile}&guestId=${guestId}&auditStatus=${auditStatus}&rooms=${rooms}&createTime=${createTime}",
                 cache : false,
                 dataType : 'text',
                 success : function(data) {
                	 
                	 var d1 = eval('(' + data + ')');
					for(var i=0;i<d1.length;i++){
						if(d1[i].type == 1){//首图
							$("#fd0").css("display","block");
							$("#st").append("<img align=\"absmiddle\" width=\"20%\" hight=\"20%\" src="+d1[i].imgKey+"></img>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							
						}else if(d1[i].type == 2){
							$("#fd1").css("display","block");
							$("#kt").append("<img align=\"absmiddle\" width=\"20%\" hight=\"20%\" src="+d1[i].imgKey+"></img>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							
						
						}else if(d1[i].type == 3){
							$("#fd2").css("display","block");
							$("#ws").append("<img align=\"absmiddle\" width=\"20%\" hight=\"20%\" src="+d1[i].imgKey+"></img>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							
						}else if(d1[i].type == 4){
							$("#fd3").css("display","block");
							$("#cf").append("<img align=\"absmiddle\" width=\"20%\" hight=\"20%\" src="+d1[i].imgKey+"></img>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							
						}else if(d1[i].type == 5){
							$("#fd4").css("display","block");
							$("#wsj").append("<img align=\"absmiddle\" width=\"20%\" hight=\"20%\" src="+d1[i].imgKey+"></img>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						
						}else if(d1[i].type == 6){
							$("#fd5").css("display","block");
							$("#yt").append("<img align=\"absmiddle\" width=\"20%\" hight=\"20%\"  src="+d1[i].imgKey+"></img>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}else if(d1[i].type == 7){
							$("#fd6").css("display","block");
							$("#fxt").append("<img align=\"absmiddle\" width=\"20%\" hight=\"20%\" src="+d1[i].imgKey+"></img>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}else{
							$("#fd7").css("display","block");
							$("#wgt").append("<img align=\"absmiddle\" width=\"20%\" hight=\"20%\" src="+d1[i].imgKey+"></img>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						}
					}
                 }
             });
        });
        
        function closed(){
       		parent.lf.agentsm.index.refreshHouseImageListTabs(46);
       	
       		parent.lf.agentsm.index.refreshlandlordImageAuditListTabs(51);
        	

        	window.CloseOwnerWindow();
        }
        function passData(){
        	var room = "${rooms}"
        	rooms = encodeURI(encodeURI(room));
        	lc.mask("正在处理中,请稍后...");
        	$.ajax({
                url : "${ctx}/houseImage/updateAudit.action?houseId=${houseId}&agentId=${agentId}&mobile=${mobile}&guestId=${guestId}&flag=0&auditStatus=${auditStatus}&createTime=${createTime}&rooms="+rooms,
                cache : false,
                dataType : 'text',
                success : function(data) {
            		 if(data == 1){
            			lc.unmask();
            			 mini.alert("图片审核通过!","成功",function(){closed();});
            		 }else  if (data == 1000){
                         lc.unmask();
                         mini.alert("审核通过失败,该房源已被设置为无效,建议驳回!","错误",function(){closed();});
            		 }else{
                         lc.unmask();
                         mini.alert("审核通过失败,请稍后重试!","错误",function(){closed();});
                     }
                }
            });
        }
        //驳回
        function rejectedData(rejectreason,mark){
        	
        	/* var room = "${rooms}"
            rooms = encodeURI(encodeURI(room)); */
        	
        	lc.mask("正在处理中,请稍后...");
        	$.ajax({
        		url : "${ctx}/houseImage/updateAudit.action?houseId=${houseId}&agentId=${agentId}&phoneNum=${phoneNum}&mobile=${mobile}&guestId=${guestId}&flag=1&auditStatus=${auditStatus}&createTime=${createTime}&mark="+encodeURI(encodeURI(mark))+"&rejectreason="+encodeURI(encodeURI(rejectreason)),
                //url : "${ctx}/houseImage/sendMessage.action?houseId=${houseId}&agentId=${agentId}",
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
        
        //驳回理由
     function openRejectedPage() {
	     mini.open({
	         url: "${ctx}/houseImage/openRejectedPage.do",
	         title: "驳回理由",
	         width: 400, 
	         height:250,
	         ondestroy: function (action) {
	         	//contactgrid.reload();
	         },
	         onload: function () {
	          var iframe = this.getIFrameEl();
	      }
	     });
	}
        
        
    </script>

    <div class="description">
       <%-- <jsp:include page="../common/footer.jsp"></jsp:include> --%>
    </div>
</body>
</html>