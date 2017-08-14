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
								<a href="${ctx}/pay/forwardPaySumIndex.action">支付汇总管理</a>
							</li>
						</ul>
						<hr/>
					</div>
				</div>
			</div>
		   <!-- 这里开始写业务页面strat -->
		  <input type="hidden" id="getImportExcelState" value="${sucess}">
		   
	  <div id="search" class="search_form">
		<div id="searchFrm">
			<form action="">
				<table spellcheck="false">
					<tr height="50px">
						<th>日期选择:</th>
						<td>
						 <input class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" name="startTime"
							id="exportTime1" style="width: 150px" editable="false"></input> -
							<input class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" name="endTime" id="exportTime2"
							style="width: 150px" editable="false"></input>
						</td>
						<td style="width: 10px;"></td>
						<th>经纪人姓名:</th>
						<td><input type='text' name='realName'  id='realName'/></td>	
					  </tr>
					<tr>
						<th>城市:</th>
						<td><select id="cityid" name="cityid" style="width: 135px;" >
							<option value="0" selected="selected">全部</option>
						    </select>
						</td>
						<th></th>
						<td><input type="button" class="btn btn-info btn-sm" value="查询" onclick="doSearch();" />&nbsp;&nbsp;
						    <input type="button" class="btn btn-info btn-sm" value="重置" onclick="restFrom();"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div id="statisticsPay-div"></div>
	<div id='pay_task' title="支付详情" class="easyui-dialog" style="width:500px;height:450px;padding:10px 20px;" closed="true" >
	  <table id="payfroms"> </table>		
    </div>
    
    <div id='improt_task' title="导入" class="easyui-dialog" style="width:300px;height:150px;padding:10px 20px;" closed="true" >
	 <form action="${ctx}/importxlspay/importXlsPayListExcel.action" method="post" enctype="multipart/form-data" name="uploadForm">
	 <input  type="file" name="fileXls"></input>
	 <input class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" name="date" id="date" style="width: 150px" editable="false"></input>
	 <input type="button" class="btn btn-info btn-sm" value="提交" onclick="uploadFileClick();" id="uploadFileButton"></input>
	 </form>
    </div>
	<table id="mainfrom" > </table>
	<script type="text/javascript" src="${ctx}/js/pay_sum.js"></script>
	<script type="text/javascript">

		//初始化 数据
		$(document).ready(function(){
			//设置 日期框 中问前一天的日期
			restFrom();
			createMainTable();
			resizeGrid();
			
			
			//判断导入完成后是否成功
			if($("#getImportExcelState").val()=='sucess'){
				 $.messager.alert("温馨提示","导入成功！！","warning");
				setTimeout(function(){
					window.location="${ctx}/pay/forwardPaySumIndex.action";
				},1000);
			}else if($("#getImportExcelState").val()=='error'){
				 $.messager.alert("温馨提示","导入失败！！","warning");
				setTimeout(function(){
					window.location="${ctx}/pay/forwardPaySumIndex.action";
				},1000);
				
			}
			
		});	

		//级联下拉
		/* $("#cityid").change(function(){
			var cityid=$(this).val();
			var json =loadAreaDis(cityid);
			putOption(json,"districtid");
		}); */
		
		

</script>
<!-- 这里开始写业务页面end -->
		</div>
		
	</div>
</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
<!-- END BODY -->
</html>	
	