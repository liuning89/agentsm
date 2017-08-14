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
								<a href="${ctx}/company/companyListIndex.action">组织管理</a>
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
									<th>公司名称/电话/地址:</th>
									<td>&nbsp;
									  <input type='text' name='name'  id='realName'/>
									</td>
									<td style="width: 10px;"></td>
									<th>城市:</th>
									<td>&nbsp;
									  <input class="easyui-combobox" type="text" id="cityId" name="cityId" />
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
					<div id="add-dialog" style="padding:10px 20px 10px 20px;display:none;">
					    <form id="add_form" method="post" action="${ctx}/company/addCompany.action">
					    	<table cellpadding="5" style="border-collapse:separate; border-spacing:5px;">
					    		<tr>
					    			<td>公司名称:</td>
					    			<td><input class="easyui-textbox" type="text" name="companyName" data-options="required:true,validType:'companyName'" missingMessage="公司名称"></input></td>
					    		</tr>
					    		<tr>
					    			<td>公司简介:</td>
					    			<td><input class="easyui-textbox" type="text" name="companyProfile" data-options="required:true,validType:'companyProfile'" missingMessage="请填写公司简介"></input></td>
					    		</tr>
					    		<tr>
					    			<td>所在城市：</td>
					    			<td><input class="easyui-combobox" type="text" id="cityId1" name="cityId" data-options="required:true,validType:'cityId'" missingMessage="请选择城市"/></td>
					    		</tr>
					    		<tr>
					    			<td>联系人:</td>
					    			<td><input class="easyui-textbox" type="text" name="userName" data-options="required:true,validType:'userName'" missingMessage="请填写联系人"></input></td>
					    		</tr>
					    		<tr>
					    			<td>联系电话:</td>
					    			<td><input class="easyui-numberbox" type="text" name="mobile" data-options="required:true,validType:'mobile'" missingMessage="请填写联系电话"></input></td>
					    		</tr>
					    		<tr>
					    			<td>地址:</td>
					    			<td><input class="easyui-textbox" type="text" name="address" data-options="required:true,validType:'address'" missingMessage="请填写地址"></input></td>
					    		</tr>
					    	</table>
					    </form>
				    <div style="text-align:center;padding:5px">
				    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;确&nbsp;&nbsp;&nbsp;&nbsp;认&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
				    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="disabledAddForm()" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
				    </div>
		    	</div>
		    	
		    	<div id="edit-dialog" style="padding:10px 20px 10px 20px;display:none;">
					    <form id="edit_form" method="post" action="${ctx}/company/updateCompany.action">
					    	<table cellpadding="5" style="border-collapse:separate; border-spacing:5px;">
					    		<tr>
					    			<td>公司名称:</td>
					    			<td><input class="easyui-textbox"  type="text" name="companyName" data-options="required:true,validType:'companyName'" missingMessage="公司名称"></input></td>
					    		</tr>
					    		<tr>
					    			<td>公司简介:</td>
					    			<td><input class="easyui-textbox"  type="text" name="companyProfile" data-options="required:true,validType:'companyProfile'" missingMessage="请填写公司简介"></input></td>
					    		</tr>
					    		<tr>
					    			<td>所在城市：</td>
					    			<td><input class="easyui-combobox"  type="text" id="cityId2" name="cityId" data-options="required:true,validType:'cityId'" missingMessage="请选择城市"/></td>
					    		</tr>
					    		<tr>
					    			<td>联系人:</td>
					    			<td><input class="easyui-textbox"  type="text" name="userName" data-options="required:true,validType:'userName'" missingMessage="请填写联系人"></input></td>
					    		</tr>
					    		<tr>
					    			<td>联系电话:</td>
					    			<td><input class="easyui-textbox"  type="text" name="mobile" data-options="required:true,validType:'mobile'" missingMessage="请填写联系电话"></input></td>
					    		</tr>
					    		<tr>
					    			<td>地址:</td>
					    			<td><input class="easyui-textbox"  type="text" name="address" data-options="required:true,validType:'address'" missingMessage="请填写地址"></input></td>
					    		</tr>
					    		<tr style="display:none;">
					    			<td>id:</td>
					    			<td><input class="easyui-textbox" type="text" name="id" ></input></td>
					    		</tr>
					    	</table>
					    </form>
				    <div style="text-align:center;padding:5px">
				    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="editForm()" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;确&nbsp;&nbsp;&nbsp;&nbsp;认&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
				    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="disabledEditForm()" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
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
<script type="text/javascript" src="${ctx}/js/company_list.js"></script>
</body>
<!-- END BODY -->
</html>	
	