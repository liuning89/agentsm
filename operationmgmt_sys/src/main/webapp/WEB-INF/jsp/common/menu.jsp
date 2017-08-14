<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!-- BEGIN SIDEBAR MENU -->
<!-- #set($role = $session.getAttribute("login_session").role ) -->
<ul class="page-sidebar-menu" data-auto-scroll="true" data-slide-speed="200">
	<li class="sidebar-toggler-wrapper">
		<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
		<div class="sidebar-toggler hidden-phone">
		</div>
		<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
	</li>
    <%-- <li class="start last ">
		<a href="javascript:;">
		<i class="fa fa-folder-open"></i>
		<span class="title">经纪人管理</span>
		</a>
		<ul class="sub-menu">
			<li>
				<a href="${ctx}/agent/agentListIndex.action">
					<i class="fa fa-file-o"></i>经纪人列表</a>
			</li>
		</ul>
	</li> --%>
	<li class="start last ">
		<a href="javascript:;">
		<i class="fa fa-folder-open"></i>
		<span class="title">组织架构</span>
		</a>
		<ul class="sub-menu">
			<li>
				<a href="${ctx}/company/companyListIndex.action">
					<i class="fa fa-file-o"></i>组织管理</a>
			</li>
			<li>
				<a href="${ctx}/store/storeListIndex.action">
					<i class="fa fa-file-o"></i>门店管理</a>
			</li>
		</ul>
	</li>
</ul>
<!-- END SIDEBAR MENU -->
