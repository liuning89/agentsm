<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<!--[if IE 8]>
<html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]>
<html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<jsp:include page="common/head.jsp"></jsp:include>
<link href="${ctx}/css/main.css" rel="stylesheet" type="text/css"/>
<!-- BEGIN BODY -->
<body class="page-header-fixed page-sidebar-fixed page-footer-fixed">
<jsp:include page="common/header.jsp"></jsp:include>
<div class="clearfix">
</div>
<!-- BEGIN CONTAINER -->
<div class="page-container">
	<!-- BEGIN SIDEBAR -->
<div class="page-sidebar-wrapper">
<div class="page-sidebar navbar-collapse collapse">
<!-- BEGIN SIDEBAR MENU -->
<jsp:include page="common/menu.jsp"></jsp:include>
<!-- END SIDEBAR MENU -->
</div>
</div>
	<div class="page-content-wrapper">
		<div class="page-content">
		   <!-- 这里开始写业务页面strat -->
			
			     <div class="container-fluid">
					<div class="row clearfix">
						<div class="col-md-12 column">
						<div class="page-header">
							<h1>
								欢迎来到经纪人后台管理系统
							</h1>
						</div>
						</div>
					</div>
					 <div class="row clearfix">
							<div class="col-md-2 column">
									  <div class="crmpanel">
									     <div class="paneltitle">
									        <h2>经纪人管理</h2>
									     </div>
									      <div class="panelmain">	
									        <div class="panelmainmodel"><a href="${ctx}/agent/agentListIndex.action">经纪人列表</a></div>
									     </div>   
									  </div>
							</div>
							<div class="col-md-2 column">
							  <div class="crmpanel">
									     <div class="paneltitle" style="background-color: #66CCCC  ">
									        <h2>组织架构</h2>
									     </div>
									      <div class="panelmain">	
									        <div class="panelmainmodel"><a href="${ctx}/company/companyListIndex.action">组织管理</a></div>
									     </div>
									      <div class="panelmain">	
									        <div class="panelmainmodel"><a href="${ctx}/store/storeListIndex.action">门店管理</a></div>
									     </div>   
									  </div>
							</div>
							 
							<!-- <div class="col-md-2 column">
							  <div class="crmpanel">
									     <div class="paneltitle" style="background-color: #99CCFF   ">
									        <h2>门店管理</h2>
									     </div>
									        
									  </div>
							</div> -->
							<!--
							<div class="col-md-2 column">
							  <div class="crmpanel">
									     <div class="paneltitle" style="background-color: #CC99FF    ">
									        <h2>XX管理</h2>
									     </div>
									      <div class="panelmain">	
									        <div class="panelmainmodel"><a href="#">XX管理<span class="badge">0</span></a></div>
									     </div>   
									  </div>
							</div>
							<div class="col-md-2 column">
							  <div class="crmpanel">
									     <div class="paneltitle" style="background-color: #00CCCC    " >
									        <h2>XX管理</h2>
									     </div>
									      <div class="panelmain">	
									        <div class="panelmainmodel"><a href="#">XX管理<span class="badge">0</span></a></div>
									     </div>   
									  </div>
							</div>
							 -->
					  </div>
			        </div>
			    
			
			<!-- 这里开始写业务页面end -->
		</div>
		
	</div>
</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<jsp:include page="common/footer.jsp"></jsp:include>
</body>
<!-- END BODY -->
</html>