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
<jsp:include page="../common/head.jsp"></jsp:include>
<!-- BEGIN BODY -->
<body class="page-header-fixed page-sidebar-fixed page-footer-fixed">
<jsp:include page="../common/header.jsp"></jsp:include>
<div class="clearfix">
</div>
<!-- BEGIN CONTAINER -->
<div class="page-container">
	<!-- BEGIN SIDEBAR -->
<div class="page-sidebar-wrapper">
<div class="page-sidebar navbar-collapse collapse">
<!-- BEGIN SIDEBAR MENU -->
<jsp:include page="../common/menu.jsp"></jsp:include>
<!-- END SIDEBAR MENU -->
</div>
</div>
	<div class="page-content-wrapper">
		<div class="page-content">
			<div class="container-fluid" style="margin-left:-15px;">
				<div class="row clearfix">
					<div class="col-md-12 column">
						<ul class="breadcrumb" style="background-color:white; margin-bottom:10px;">
							<li>
								<a href="${ctx}/mianForward.action">首页</a>
							</li>
							<li>
								<a href="${ctx}/store/storeListIndex.action">门店管理</a>
							</li>
						</ul>
						<hr/>
					</div>
				</div>
			</div>
		   <!-- 这里开始写业务页面strat -->
		   
		   <!-- 查询条件start -->
				<div id="search" class="search_form">
					<div id="searchFrm">
						<form action="" method="post">
							<table spellcheck="false">
								<tr height="45px">
									<th>门店名称:</th>
									<td>&nbsp;
									  <input type='text' name='storeName'  id='storeName'/>
									</td>
									<td style="width: 10px;"></td>
									<th>所属公司:</th>
									<td>&nbsp;
									  <input class="easyui-combobox" type="text" id="companyName" name="companyName" />
									</td>
									<td style="width: 10px;"></td>
									<td><input type="button" class="btn btn-info btn-sm"
										value="查询" onclick="doSearch();" /></td>
									<td style="width: 10px;"></td>
									
								</tr>
							</table>
						</form>
					</div>
				</div>
				<!-- 查询条件end -->

           <!-- 结果展示table start -->
				<table id="mainfrom">
				</table>
           <!-- 结果展示table end -->
				<!-- 这里开始写业务页面end -->
			<!-- 添加业务start
			<div id="add-dialog" class="easyui-dialog" title="新增" data-options="iconCls:'icon-add'" style="width:400px;height:300px;padding:10px">-->
					<div id="add-dialog" style="padding:20px 60px 20px 60px;display:none;">
					    <form id="add_form" method="post" action="${ctx}/store/addStore.action">
					    	<table cellpadding="5" style="border-collapse:separate; border-spacing:5px;">
					    		<tr>
					    			<td>门店名称:</td>
					    			<td><input class="easyui-textbox" type="text" id="storeName" name="storeName" data-options="required:true" missingMessage="请填写门店名称"></input></td>
					    		</tr>
					    		<tr>
					    			<td>所属公司:</td>
					    			<td><input class="easyui-combobox" type="text" id="companyId" name="companyId" data-options="required:true" missingMessage="请选择所属公司"/></td>
					    		</tr>
					    		<tr>
					    			<td>联系电话：</td>
					    			<td><input class="easyui-numberbox"  type="text" name="mobile" data-options="required:true" missingMessage="请填写联系电话"></input></td>
					    		</tr>
					    		<tr>
					    			<td>城市:</td>
					    			<td><input class="easyui-combobox" type="text" id="cityId" name="cityId" data-options="required:true" missingMessage="请选择城市"/></td>
					    		</tr>
					    		<tr>
					    			<td>区域:</td>
					    			<td><input class="easyui-combobox" type="text" id="districtid" name="districtid" data-options="required:true" missingMessage="请选择区域"/></td>
					    		</tr>
					    		<tr>
					    			<td>板块:</td>
					    			<td><input class="easyui-combobox" type="text" id="townId" name="townid" data-options="required:true" missingMessage="请选择板块"/></td>
					    		</tr>
					    		<tr>
					    			<td>门店地址:</td>
					    			<td><input class="easyui-textbox" type="text" name="address" data-options="required:true" missingMessage="请填写门店地址"></input></td>
					    		</tr>
					    	</table>
					    </form>
				    <div style="text-align:center;padding:5px">
				    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" >确&nbsp;&nbsp认&nbsp;&nbsp;</a>
				    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="disabledAddForm()" >取&nbsp;&nbsp;消&nbsp;&nbsp;</a>
				    </div>
		    	</div>
		    	
		    	<div id="edit-dialog" style="padding:20px 60px 20px 60px;display:none;">
					    <form id="edit_form" method="post" action="${ctx}/store/updateStore.action">
					    	<table cellpadding="5" style="border-collapse:separate; border-spacing:5px;">
					    		<tr>
					    			<td>门店名称:</td>
					    			<td><input class="easyui-textbox"  type="text" id="storeName" name="storeName" data-options="required:true" missingMessage="请填写门店名称"></input></td>
					    		</tr>
					    		<tr>
					    			<td>所属公司:</td>
					    			<td><input class="easyui-combobox" type="text" id="companyId1" name="companyId" data-options="required:true" missingMessage="请选择所属公司"/></td>
					    		</tr>
					    		<tr>
					    			<td>联系电话：</td>
					    			<td><input class="easyui-textbox"  type="text" id ="mobile" name="mobile" data-options="required:true" missingMessage="请填写联系电话"></input></td>
					    		</tr>
					    		<tr>
					    			<td>城市:</td>
					    			<td><input class="easyui-combobox" type="text" id="cityId1" name="cityId" data-options="required:true" missingMessage="请选择区域"/></td>
					    		</tr>
					    		<tr>
					    			<td>区域:</td>
					    			<td><input class="easyui-combobox" type="text" id="districtid1" name="districtid" data-options="required:true" missingMessage="请选择区域"/></td>
					    		</tr>
					    		<tr>
					    			<td>板块:</td>
					    			<td><input class="easyui-combobox" type="text" id="townId1" name="townid" data-options="required:true" missingMessage="请选择板块"/></td>
					    		</tr>
					    		<tr>
					    			<td>门店地址:</td>
					    			<td><input class="easyui-textbox"  type="text" name="address" data-options="required:true" missingMessage="请填写门店地址"></input></td>
					    		</tr>
					    		<tr style="display:none;">
					    			<td>id:</td>
					    			<td><input class="easyui-textbox" type="text" name="id" ></input></td>
					    		</tr>
					    	</table>
					    </form>
				    <div style="text-align:center;padding:5px">
				    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="editForm()" >&nbsp;确&nbsp;认</a>
				    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="disabledEditForm()" >&nbsp;取&nbsp;消</a>
				    </div>
		    	</div>
		   <!--  </div>
			添加业务end -->
		</div>
		
	</div>
</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<jsp:include page="../common/footer.jsp"></jsp:include>
<script type="text/javascript" src="${ctx}/js/store_list.js"></script>
</body>
<!-- END BODY -->
</html>	
	