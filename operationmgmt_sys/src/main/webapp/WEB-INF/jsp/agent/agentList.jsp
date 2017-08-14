<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
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
<head>
	<style type="text/css">
		.datagrid-header-rownumber,.datagrid-cell-rownumber{
   			width:60px;
   		}
	</style>
</head>
<!-- BEGIN BODY -->
<body class="page-header-fixed page-sidebar-fixed page-footer-fixed">
	<jsp:include page="../common/header.jsp"></jsp:include>
	<div class="clearfix"></div>
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
				<div class="container-fluid" style="margin-left: -15px;">
					<div class="row clearfix">
						<div class="col-md-12 column">
							<ul class="breadcrumb"
								style="background-color: white; margin-bottom: 10px;">
								<li><a href="${ctx}/mianForward.action">首页</a></li>
								<li><a href="${ctx}/agent/agentListIndex.action">经纪人列表</a>
								</li>
							</ul>
							<hr />
						</div>
					</div>
				</div>
				<!-- 这里开始写业务页面strat -->

				<!-- 查询条件start -->
				<div id="search" class="search_form">
					<div id="searchFrm">
						<form action="" >
							<table spellcheck="false">
								<tr height="40px">
									<td style="width: 10px;"></td>
									<th>工号:</th>
									<td>&nbsp; <input type='text' name='agentNum' id='agentNum' />
									</td>
									<td style="width: 10px;"></td>
									<th>姓名:</th>
									<td>&nbsp; <input type='text' name='realName'
										id='realName' />
									</td>

									<td style="width: 10px;"></td>
									<th>电话:</th>
									<td>&nbsp; <input type='text' name='mobile' id='mobile' />
									</td>
									<td style="width: 10px;"></td>
									<th>现状:</th>
									<td>&nbsp; 
									<select style="width: 133px;" id="status" name='status'>
											<option value="" selected="selected">全部</option>
											<option value="1">在职</option>
											<option value="4">离职</option>
									</select>
									</td>
								</tr>
							</table>
							<table>
								<tr>
									<td style="width: 10px;"></td>
									<th>城市:</th>
									<td>&nbsp;
									<select id="cityId" name="cityId" style="width: 135px;" >
										<option value="" selected="selected">全部</option>
						    		</select>
									</td>
									<td style="width: 10px;"></td>
									<th>公司:</th>
									<td>&nbsp; 
									<select id="companyId" name="companyId" style="width: 135px;" >
										<option value="" selected="selected">全部</option>
						    		</select>
								
									<td style="width: 10px;"></td>
									<th>门店:</th>
									<td>&nbsp; 
									<select id="storeId" name="storeId" style="width: 135px;" >
										<option value="" selected="selected">全部</option>
						    		</select>
									</td>
									<td style="width: 10px;"></td>
									<th>皇冠:</th>
									<td>&nbsp; 
									<select id="isCrownAgent" name="isCrownAgent" style="width: 135px;" >
										<option value="" selected="selected">全部</option>
										<option value="1">是</option>
										<option value="2">否</option>
						    		</select>
									</td>

									<td style="width: 50px;"></td>
									<td width="50px"><input type="button"
										class="btn btn-info btn-sm" value="查询" onclick="doSearch();" /></td>
									<td><input type="button" class="btn btn-info btn-sm"
										value="重置" onclick="restFrom();" /></td>
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
			</div>
		</div>
	</div>
	<!-- END CONTAINER -->
	<!--  begin subWindow -->
	<div id="add-dialog" style="padding:20px 60px 20px 60px;display:none;">
					    <form id="add_form" method="post" enctype="multipart/form-data" action="${ctx}/agent/addAgent.action">
					    	<table cellpadding="5" style="border-collapse:separate; border-spacing:5px;">
					    		<tr>
					    			<td  rowspan="10" >
					    			  <div>
                                    <img id="imgs" width="100px" height="100px" />
                                       </div>
                                      <br>
					    			<input style="width:190px" class="easyui-textbox" name="imgFile" id="imgFile" type="file"  
					    			data-options="validType:'fileType'" 
					    				onchange="change(this,'imgs')" >
					    			</input>
					    			</td>

					    		</tr>
					    		<tr>
					    			<td>工号:</td>
					    			<td><input class="easyui-textbox" type="text" name="agentNum" data-options="required:true" missingMessage="请输入工号"></input></td>
					    		</tr>
					    		<tr>
					    			<td>真实姓名:</td>
					    			<td><input class="easyui-textbox" type="text" name="realName" data-options="required:true" missingMessage="请填写真实姓名"></input></td>
					    		</tr>
					    		<tr>
					    			<td>身份证号:</td>
					    			<td><input class="easyui-textbox" type="text" name="idNumber" data-options="validType:'idNumber'"></input></td>
					    		</tr>
					    		<tr>
					    			<td>手机号码：</td>
					    			<td><input class="easyui-textbox" type="text" id="addMobile" name="mobile" data-options="required:true,validType:'mobile'" missingMessage="请填写电话号码"></input></td>
					    		</tr>
					    		<tr>
					    			<td>密码:</td>
					    			<td><input class="easyui-textbox" type="password" id="addPwd" name="password" data-options="required:true,validType:'validPwd'" missingMessage="请填写密码"></input></td>
					    		</tr>
					    		<tr>
					    			<td>城市:</td>

					    			<td>
					    			<select id="addCityId" name="cityId" style="width: 135px;"  >
										<option value="" selected="selected">全部</option>
						    		</select>
					    			</td>
					    		</tr>
					    		<tr>
					    			<td>所属公司:</td>
					    			<td>
					    			<select id="addCompanyId" name="companyId" style="width: 135px;" >
										<option value="" selected="selected">全部</option>
						    		</select>
					    			</td>
					    		</tr>
					    		<tr>
					    			<td>所属门店:</td>
					    			<td>
					    			<select id="addStoreId" name="storeId" style="width: 135px;" >
										<option value="" selected="selected">全部</option>
						    		</select>
						    		</td>
					    		</tr>
					    	</table>
					    </form>
				    <div style="text-align:center;padding:5px">
				    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm(this)" >确定</a>
				    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="cancelAddForm()" >取消</a>
				    	
				    </div>
		    	</div>

	<!--  end subWindow -->
	
	
		<!-- END CONTAINER -->
	<!--  begin subWindow -->
	<div id="edit-dialog" style="padding:20px 60px 20px 60px;display:none;">
					    <form id="edit_form" method="post" enctype="multipart/form-data" action="${ctx}/agent/updateAgent.action">
					    	<table cellpadding="5" style="border-collapse:separate; border-spacing:5px;">
					    	 <tr>
					    	   <td rowspan="10">
					    	   <div>
                                 <img id="updateImg" width="100px" height="100px" />
                               </div>
                               <br>
			                   <input style="width:190px" class="easyui-textbox" name="imgFile" id="imgFile" type="file"  onchange="change(this,'updateImg')"></input>
			                   </td> 
					         </tr>
					         	<tr>
					    			<td>工号:</td>
					    			<td><input class="easyui-textbox" type="text" id="updateAgentNum" name="agentNum" data-options="required:true" missingMessage="请输入工号"></input></td>
					    		</tr>
                               	<tr>
					    			<td>真实姓名:</td>
					    			<td><input class="easyui-textbox" type="text" id="updateRealName" name="realName" data-options="required:true" missingMessage="请填写真实姓名"></input></td>
					    		</tr>
					    		<tr>
					    			<td>身份证号:</td>
					    			<td><input class="easyui-textbox" type="text" id="updateIdNumber" name="idNumber" data-options="validType:'idNumber'"></input></td>
					    		</tr>
					    		<tr>
					    			<td>手机号码：</td>
					    			<td><input class="easyui-textbox" type="text" id="updateMobile" name="mobile" data-options="required:true,validType:'mobile'" missingMessage="请填写电话号码"></input></td>
					    		</tr>
					    		
					    		<tr>
					    			<td>状态:</td>
					    			<td>
					    			<select style="width: 133px;" id="updateStatus" name='status'>
											<option value="1">在职</option>
											<option value="4">离职</option>
									</select>
					    			</td>
					    		</tr> 
				    			<tr>
					    			<td>城市:</td>

					    			<td>
					    			<select id="updateCityId" name="cityId" style="width: 135px;"  >
										<option value="" selected="selected">全部</option>
						    		</select>
					    			</td>
					    		</tr>
					    		<tr>
					    			<td>所属公司:</td>
					    			<td>
					    			<select id="updateCompanyId" name="companyId" style="width: 135px;" >
										<option value="" selected="selected">全部</option>
						    		</select>
					    			</td>
					    		</tr>
					    		<tr>
					    			<td>所属门店:</td>
					    			<td>
					    			<select id="updateStoreId" name="storeId" style="width: 135px;" >
										<option value="" selected="selected">全部</option>
						    		</select>
						    		</td>
					    		</tr>
					    		<tr>
					    			<td>皇冠经纪人</td>
					    			<td>
					    				<input type="radio" name="isCrownAgent" value="1"/>是
					    				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					    				<input type="radio" name="isCrownAgent" value="2"/>否
					    			</td>	
					    		</tr>
					    	</table>
					    	<input type="hidden" id="updateId" name="id">
					    </form>
				    <div style="text-align:center;padding:5px">
				    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitEditForm(this)" >确定</a>
				    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="cancelEditSubmit()" >取消</a>
				    	
				    </div>
		    	</div>

	<!--  end subWindow -->
	
	
			<!-- END CONTAINER -->
	<!--  begin subWindow -->
	<div id="update_password-dialog" style="padding:20px 60px 20px 60px;display:none;">
					    <form id="update_password_form" method="post" action="${ctx}/agent/updatePassword.action">
					    	<table cellpadding="5" style="border-collapse:separate; border-spacing:5px;">
					    		<tr>
					    			<td>新密码:</td>
					    			<td><input class="easyui-textbox" type="password" id="newPassword" name="newPassword" data-options="required:true,validType:'validPwd'" missingMessage="请填写新密码"></input></td>
					    		</tr>
					    		<tr>
					    			<td>确认密码:</td>
					    			<td><input class="easyui-textbox" type="password" name="confirmPassword" data-options="required:true,validType:'newPassword'" missingMessage="请再次填写新密码"></input></td>
					    		</tr>
					    	</table>
					    	<input type="hidden" id="updatePwdId" name="id">
					    </form>
				    <div style="text-align:center;padding:5px">
				    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitPasswordForm(this)" >确定</a>
				    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="caclePasswordForm()" >取消</a>
				    	
				    </div>
		    	</div>
		    	
		<div id="resourceTransfer-dialog" style="padding:10px 20px 10px 20px;display:none;">
					    <form id="resourceTransfer_form" method="post" action="">
					    	<table cellpadding="5" style="border-collapse:separate; border-spacing:5px;">
					    		<tr>
					    			<td>
					    				<input type="checkbox" id="isPublish" name="type" value="1"   />发布人
						                <input type="checkbox" id="isKey" name="type" value="2"  />钥匙人
						                <input type="checkbox" id="isComm" name="type" value="3" />委托人
						                <input type="checkbox" id="isPicture" name="type" value="4"  />实景人
						                <input type="checkbox" id="isBuy" name="type" value="5"  />客源
					    			</td>
					    		</tr>
					    		<tr>
					    			<td>转入人公司：</td>
					    			<td><input class="easyui-combobox"   type="text" id="companyName" name="companyName" /></td>
					    		</tr>
					    		<tr>
					    			<td>转入人门店:</td>
					    			<td><input class="easyui-combobox"   type="text" id="storeName" name="storeName" /></td>
					    		</tr>
					    		<tr>
					    			<td>转入人姓名:</td>
					    			<td><input class="easyui-combobox"   type="text" id="agentName" name="agentName" /></td>
					    		</tr>
					    	</table>
					    	<input type="hidden" name="oldAgentId" id = "oldAgentId"/>
					    </form>
				    <div style="text-align:center;padding:5px">
				    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitTransferForm()" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;确&nbsp;&nbsp;&nbsp;&nbsp;认&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
				    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="disabledTransferForm()" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
				    </div>
		    	</div>    	

	<!--  end subWindow -->
	
	
	
	

	<!-- BEGIN FOOTER -->
	<jsp:include page="../common/footer.jsp"></jsp:include>
	<script type="text/javascript" src="${ctx}/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/js/agent_list.js"></script>
	
</body>
<!-- END BODY -->
</html>
