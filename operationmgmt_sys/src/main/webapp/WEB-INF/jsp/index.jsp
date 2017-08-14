<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
     <link rel="stylesheet" href="${ctx}/css/index.css" />
	  <link rel="stylesheet" href="${ctx}/css/header.css" />
	 
    <meta http-equiv="expires" content="0" />
	<script src="${ctx }/scripts/boot.js?v=${version}" type="text/javascript"></script>
	<script src="<%=path%>/loginInfo.action?d=<%=new Date().getTime()%>" type="text/javascript"></script>




	<title>悟空找房 - 经纪人运营系统</title>
	<style type="text/css">
	   body{
	       margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	   }    
	   .header
	   {
	       background:url(images/header.gif) repeat-x 0 -1px;
	   }
	</style>
 </head>
 
 <body>
  	<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
	<div class="" region="north" style="height:80px;" showSplit="false" showHeader="false">
		
		<div class="header currentSyS">
				<div class="container">
					<a class="logo">
<img src="<%=path%>/images/yuny.png" style="float:left;"/><span style='color:#d53e0f;font-family:Microsoft Yahei;font-size:24px;float:left;display:block;margin-top:10px;margin-left:13px;'>${environment}</span>						
						
					</a>
					<div class="switch_mod">
						<div class="switch">
							<i class="icon_switch"></i>
							<span>切换</span>
							<ul class="ulSyS">
								<c:forEach items="${systemList}" var="item">
										 <li><a href='${item.url }' >${item.alias }</a></li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<div style="float:right;overflow:hidden;margin-top:28px;">
					<span>欢迎您：<span id="username"></span></span>
			<a id="logoff" class="mini-button mini-button-iconTop" href="<%=path%>/logout.action"  plain="true" >注销</a></div>
				</div>
			</div>
			<!-- <a id="modifyPwd" class="mini-button mini-button-edit"  onclick="modifyPwd();"  plain="true" >修改密码</a> -->
		
	</div>
    <div title="south" region="south" showSplit="false" showHeader="false" height="30" >
        <div style="line-height:28px;text-align:center;cursor:default">Copyright © 好居信息科技有限责任公司版权所有 </div>
    </div>
    <div title="center" region="center" style="border:0;" bodyStyle="overflow:hidden;">
        <!--Splitter-->
        <div class="mini-splitter" style="width:100%;height:100%;" borderStyle="border:0;">
            <div size="180" maxSize="250" minSize="100" showCollapseButton="true" style="border:0;">
                <!--OutlookTree-->
                <div id="leftTree" class="mini-outlooktree" expandOnLoad=true
                    textField="text" idField="id" parentField="pid" >
                </div>
                
            </div>
            <div showCollapseButton="false" style="border:0;">
                <!--Tabs-->
                <div id="mainTabs" class="mini-tabs" activeIndex="2" style="width:100%;height:100%;"      
                     plain="false">
                    <div title="首页" url="" >        
                    </div>
                </div>
            </div>        
        </div>
    </div>
</div>
</body>
	<script type="text/javascript" src="${ctx }/scripts/index.js?v=${version}"></script>
	<script type="text/javascript">
	function modifyPwd(){
		mini.open({
	         url: "${ctx}/update/openUpPwdPage.action",
	         title: "修改密码",
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
	function rejectedData(oldPw,newPw){
    	
    	lc.mask("正在处理中,请稍后...");
    	$.ajax({
    		url : "${ctx}/update/updatePw.action?oldPw="+oldPw+"&newPw="+newPw,
            cache : false,
            dataType : 'text',
            success : function(data) {
        		 if(data == 1){
        			 lc.unmask();
        			 mini.alert("密码修改成功!","成功",function(){});
        		 }else{
        			 lc.unmask();
        			 mini.alert("修改失败,原始密码错误!","错误",function(){ });
        		 }
            }
        });
    	
    }
	</script>
</html>
