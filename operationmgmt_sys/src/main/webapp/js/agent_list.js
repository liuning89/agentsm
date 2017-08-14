/**
 * 经纪人列表JS
 */
// 初始化 数据
$(document).ready(function() {
	var json = loadArea();
	putOption(json,"cityId");
	createMainTable();
});


function restFrom() {
	$("#agentNum").val("");
	$("#realName").val("");
	$("#mobile").val("");
	$("#status").val("");
	$("#cityId").val("");
	$("#companyId").val("");
	$("#storeId").val("");
	$("#isCrownAgent").val("");
}

// 搜索
function doSearch() {
	
	
	var pars = "{";

	var agentNum = $("#agentNum").val();
	if (agentNum != null && agentNum != '') {
		pars += "'agentNum' : '" + agentNum + "',";
	}
	
	var realName = $("#realName").val();
	if (realName != null && realName != '') {
		pars += "'realName' : '" + realName + "',";
	}

	var mobile = $("#mobile").val();
	if (mobile != null && mobile != '') {
		pars += "'mobile' : '" + mobile + "',";
	}

	var status = $("#status").val();
	if (status != null && status != '') {
		pars += "'status' : '" + status + "',";
	}
	
	var cityId = $("#cityId").val();
	if (cityId != null && cityId != '') {
		pars += "'cityId' : '" + cityId + "',";
	}
	
	var companyId = $("#companyId").val();
	if (companyId != null && companyId != '') {
		pars += "'companyId' : '" + companyId + "',";
	}
	
	var storeId = $("#storeId").val();
	if (storeId != null && storeId != '') {
		pars += "'storeId' : '" + storeId + "',";
	}
	
	var isCrownAgent = $("#isCrownAgent").val();
	if(isCrownAgent!=null && isCrownAgent!=''){
		pars += "'isCrownAgent' : '" + isCrownAgent + "',";
	}
	
	if (pars.length > 1) {
		pars = pars.substr(0, pars.length - 1);
	}
	pars += "}";
	pars = eval("(" + pars + ")");
	$('#mainfrom').datagrid('reload', pars);

}

function resizeGrid() {
	$('#mainfrom').datagrid('resize', {
		width : function() {
			return (document.body.clientWidth - 220) * 0.9;
		}
	});
}

//显示添加的对话框
function showAddDialog(){
	
	var json = loadArea();
	putOption(json,"addCityId");
	$("#add-dialog #imgs").attr("src",ctxpath+"/imgs/user_default.png");
	$("#add-dialog").show();//必须先显示，再弹出
    $("#add-dialog").dialog({
        title: "新增",
        width: 650,
        height: 400,
        iconCls: 'icon-add',
        closable: false
    });
    $("#add-dialog #addMobile").val("");
    $("#add-dialog #addPwd").val("");

}
//修改
function edituser(){
	var pars = $("#mainfrom").datagrid("getSelections");
	if(pars.length!=1){
		$.messager.alert('温馨提示', '请选择一个经纪人进行编辑', 'info');
		return;
	}
	var data = pars[0];
	var json = loadArea();
	putOption(json,"updateCityId");
	var cityId= data.cityId;
	var companyId = data.companyId;
	var storeId = data.storeId;
	$("#updateCityId").attr("value", cityId);
	$("#updateCityId").trigger("change","parentId:"+cityId);
	$("#updateCompanyId").attr("value", companyId);
	$("#updateCompanyId").trigger("change","parentId:"+companyId);
	$("#updateStoreId").attr("value", storeId);
	$("#edit-dialog #updateImg").attr("src",data.photoHeadUrl);
	$.ajax({
		url:ctxpath +'/agent/isResourceTransfer.action',
		type:"POST",
		dataType:"json",
		data: "userId="+data.id,
		async:false,//同步
		success : function(data){
			var isResourceTransfer = true;
			for(var i=0;i<data.length;i++){
				if(data[i]==1){
					isResourceTransfer = false;
					break;
				}
			}
			if(isResourceTransfer){
				$("#updateStatus").attr("disabled",false);
			}else{
				$("#updateStatus").attr("disabled",true);
			}
		}
	});
	//设置已有信息
	
	$("#edit-dialog").show();//必须先显示，再弹出
    $("#edit-dialog").dialog({
        title: "修改",
        closable: false,
        width: 620,
        height: 420
    });
    $("#edit-dialog #updateAgentNum").val(data.agentNum);
    $("#edit-dialog #updateRealName").val(data.realName);
	$("#edit-dialog #updateIdNumber").val(data.idNumber);
	$("#edit-dialog #updateMobile").val(data.mobile);
	$("#edit-dialog #updateStatus").val(data.status);
	$("#edit-dialog #updateId").val(data.id);
	$("#edit-dialog input:radio[name='isCrownAgent'][value="+data.isCrownAgent+"]").attr("checked","checked");
}

//显示修改密码对话框
function showUpdatePasswordDialog(){
	var pars = $("#mainfrom").datagrid("getSelections");
	if(pars.length!=1){
		$.messager.alert('温馨提示', '请选择一个经纪人进行编辑', 'info');
		return;
	}
	var data = pars[0];
	$("#update_password-dialog #updatePwdId").val(data.id);
	//设置密码
	$("#update_password-dialog").show();//必须先显示，再弹出
    $("#update_password-dialog").dialog({
        title: "修改密码",
        width: 400,
        height: 350
    });

}
function disabledTransferForm(){
	$('#resourceTransfer-dialog').dialog('close');
	$('#resourceTransfer_form').form('clear');
}
//资源转移
function showResourceTransferDialog(){
	var pars = $("#mainfrom").datagrid("getSelections");
	if(pars.length!=1){
		$.messager.alert('温馨提示', '请选择一个经纪人进行转移', 'info');
		return;
	}
	var data = pars[0];
	$("#resourceTransfer_form input[name='oldAgentId']").val(data.id);
	$("#resourceTransfer-dialog").show();//必须先显示，再弹出
    $("#resourceTransfer-dialog").dialog({
        title: "信息转移",
        closable: false,
        width: 600,
        height: 400,
        modal:true 
    });
	$('#companyName').combobox({
	    url: ctxpath + '/company/getCompany.action',
	    valueField: 'id',
	    textField: 'companyName',
	    	onChange:function(newValue,oldValue){
	        	$('#storeName').combobox({
	        		 url: ctxpath + '/store/getStoreListBySearch.action?companyId='+newValue+'',
	        		    valueField: 'id',
	        		    textField: 'storeName',
	        		    onChange:function(newValue,oldValue){
	        		    	$('#agentName').combobox({
	        		    	    url: ctxpath + '/agent/getAgentListByPars.action?storeId='+newValue+'',
	        		    	    valueField: 'id',
	        		    	    textField: 'realName'
	        		    	});
	        		    }
	        	});	
	        }
	});
	$.ajax({
		url:ctxpath +'/agent/isResourceTransfer.action',
		type:"POST",
		dataType:"json",
		data: "userId="+data.id,
		async:false,//同步
		success : function(data){
//			value="1"   />发布人 
//			value="2"  />钥匙人  
//			value="3" />委托人   
//			value="4"  />实景人  
//			value="5"  />客源   
			if(data[0]==1){
				$("#isPublish").attr("disabled", false);
				$("#isPublish").attr("checked",true);
			}else{
				$("#isPublish").attr("disabled",true);
			}
			if(data[1]==1){
				$("#isKey").attr("disabled",false);
				$("#isKey").attr("checked",true);
			}else{
				$("#isKey").attr("disabled",true);
			}
			if(data[2]==1){
				$("#isComm").attr("disabled", false);
				$("#isComm").attr("checked",true);
			}else{
				$("#isComm").attr("disabled", true);
			}
			if(data[3]==1){
				$("#isPicture").attr("disabled", false);
				$("#isPicture").attr("checked",true);
			}else{
				$("#isPicture").attr("disabled", true);
			}
			if(data[4]==1){
				$("#isBuy").attr("disabled", false);
				$("#isBuy").attr("checked",true);
			}else{
				$("#isBuy").attr("disabled", true);
			}
		}
	});
}
function submitTransferForm(){
	var types =[];
    $("#resourceTransfer-dialog input[name='type']:checked").each(function (index,ele) {
    	types.push(ele.value);
    });
    if(types.length==0){
		$.messager.alert("温馨提示", "请选择转移的资源类型", "info");
		return;
	}
    var newAgentId = $('#agentName').combobox('getValue');
    if(isNull(newAgentId)){
    	$.messager.alert("温馨提示", "请选择转入人", "info");
    	return;
    }
    var oldAgentId = $("#resourceTransfer_form input[name='oldAgentId']").val();
    var id = $
    $.ajax({
	      url:ctxpath +'/agent/resourceTransfer.action',
	      type:"POST",
	      dataType:"text",
	      data: "oldAgentId=" + oldAgentId + "&types="+types+"&newAgentId="+newAgentId,
	     success : function(data){
	    	 if(data = 1){
	 			$('#resourceTransfer-dialog').form('clear');
				$('#resourceTransfer-dialog').dialog('close');
				doSearch();//重新加载数据
				$.messager.alert("温馨提示","资源转移成功", 'info');
	 		}else{
	 			$.messager.alert('温馨提示', "资源转移失败", 'info');
	 		}
	      }
    });
}
// 创建支付汇总表格
function createMainTable() {
	$('#mainfrom')
			.datagrid(
					{
						url : ctxpath + '/agent/agentListData.action',
						fitColumns : true, // True				// 就会自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
						scrollbarSize : 0, // 去掉表格的滚动条宽度，美观表格
						striped : true, // True 就把行条纹化
						singleSelect:false,//是否单选
						pagination : true,// 分页控件
						rownumbers : true, // 显示行号
						pageSize : 20,
						pageNumber : 1,
						pageList : [ 20, 30, 50, 100 ],// 列表分页
						loadMsg : '数据正在努力加载中...',
						selectOnCheck : true, // 点击行的时候也触发 同时 触发 点击checkbox
						checkOnSelect : true, // 点击 checkbox 同时触发 点击 行
						queryParams:{
							cityId:$("#cityId").val()
						},
						columns : [ [
								{
									field : 'agentNum',
									title : '工号',
									align : 'center',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.1
								},
								{
									field : 'realName',
									title : '名字',
									align : 'center',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.1
								},
								{
									field : 'mobile',
									title : '电话',
									align : 'center',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.1
								},
								{
									field : 'cityName',
									title : '城市',
									align : 'center',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.1
								},
								{
									field : 'companyName',
									title : '所属公司简称',
									align : 'center',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.1
								},
								{
									field : 'storeName',
									title : '所属门店',
									align : 'center',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.1
								},
								{
									field : 'createTime',
									title : '创建时间',
									align : 'center',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.1
								},
								{
									field : 'status',
									title : '现状',
									align : 'center',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.1,
									formatter:function(value, row, index){
										if(value == 1){
											return "在职";
										}else if(value == 2){
											return "审核失败";
										}else if(value == 3){
											return "删除";
										}else if(value == 4){
											return "离职";
										}else{
											return "";
										}				
									}
								},
								{
									field : 'isCrownAgent',
									title : '皇冠',
									align : 'center',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.1,
									formatter:function(value, row, index){
										if(value == 1){
											return "是";
										}else if(value == 2){
											return "否";
										}else{
											return "";
										}
									}
								}
								]],
						toolbar : [
								{
									text : '新增',
									iconCls:'icon-add', 
									handler : function() {
										showAddDialog();
									},
								},
								{
									text : '修改',
									iconCls:'icon-add', 
									handler : function() {
										edituser();
									},
								},
								{
									text : '修改密码',
									iconCls:'icon-add', 
									handler : function() {
										showUpdatePasswordDialog();
									},
								},
								{
									text : '资源转移',
									iconCls:'icon-edit', 
									handler : function() {
										showResourceTransferDialog();
									},
								}
								
							
								]
					});
	
}
/*
添加城市下拉
*/
function putOption(json,selectid){
	if(!(json === undefined)){
		var sel=$("#"+selectid);
		sel.html('');
		var str ='';
		for(var i =0 ; i< json.length ; i++){
			var row = json[i];
	    	str +='<option value="'+row.id+'"  >'+row.name+'</option>'; 
	  
		}
		if(selectid=='cityId'){
			str = '<option value="" selected="selected">全部</option>' + str;
		}
		sel.html(str);
		 if(selectid=='cityId'){
			 var cityId=$("#cityId option:first").val();
			 $("#cityId").attr("value", 43);
			 $("#cityId").trigger("change","parentId:"+cityId+"");
			 
		 }else if(selectid=='addCityId'){
			 var cityId=$("#addCityId option:first").val();
			 $("#addCityId").attr("value", 43);
			 $("#addCityId").trigger("change","parentId:43");
		 }
	}
}

//加载公司下拉框
function putOptionCompany(json,selectid){
	if(!(json === undefined)){
		var sel=$("#"+selectid);
		sel.html('');
		var str = '';
		for(var i =0 ; i< json.length ; i++){
			var row = json[i];
	    	str +='<option value="'+row.id+'"  >'+row.companyName+'</option>'; 
	  
		}
		if(selectid=='companyId'){
			str = '<option value="" selected="selected">全部</option>' + str;
		}
		sel.html(str);
         if(selectid=='companyId'){
        	 var companyId=$("#companyId option:first").val();
    		 $("#companyId").attr("value",companyId);
    		 $("#companyId").trigger("change","companyId:"+companyId+"");  
         }else if(selectid=='addCompanyId' ){
        	 var companyId=$("#addCompanyId option:first").val();
    		 $("#addCompanyId").attr("value",companyId);
    		 $("#addCompanyId").trigger("change","companyId:"+companyId+"");  
         }
		
	}
}

//加载门店下拉框
function putOptionStore(json,selectid){
	if(!(json === undefined)){
		var sel=$("#"+selectid);
		sel.html('');
		var str ='';
		for(var i =0 ; i< json.length ; i++){
			var row = json[i];
	    	str +='<option value="'+row.id+'"  >'+row.storeName+'</option>'; 
		}
		if(selectid=='storeId'){
			str = '<option value="" selected="selected">全部</option>' + str;
		}
		sel.html(str);
		if(selectid=='storeId'){
       	 var storeId=$("#storeId option:first").val();
   		 $("#storeId").attr("value",storeId);
        }else if(selectid=='addStoreId' ){
       	 var addStoreId=$("#addStoreId option:first").val();
   		 $("#addStoreId").attr("value",addStoreId);
        }
	}
}

//加载 城市
function loadArea(){
	var url1= ctxpath+"/dicAreaNew/getCity.action";
	var resultData={};
	$.ajax( {
		url : url1,
		async:false,
		method : 'POST',
		success : function(data) {
			resultData =data;
		},
		error : function(data) {
		}
	});
	return resultData;
}

//加载公司数据
function loadCompany(cityId){
	var url1= ctxpath+"/company/getCompanyListByCityId.action?cityId="+cityId;
	var resultData={};
	$.ajax( {
		url : url1,
		async:false,
		method : 'POST',
		success : function(data) {
			resultData =data;
		},
		error : function(data) {
		}
	});
	return resultData;
}


//加载门店数据
function loadStore(companyId){
	var url1= ctxpath+"/store/getStoreListByCompanyId.action?companyId="+companyId;
	var resultData={};
	$.ajax( {
		url : url1,
		async:false,
		method : 'POST',
		success : function(data) {
			resultData =data;
		},
		error : function(data) {
		}
	});
	return resultData;
}
//选择城市级联公司



$("#cityId").change(function(){
	var cityId=$(this).val();
	var compayDate=loadCompany(cityId);
	putOptionCompany(compayDate,"companyId");
});

//选择公司级联门店
$("#companyId").change(function(){
	var companyId=$(this).val();
	var storeDate=loadStore(companyId);
	putOptionStore(storeDate,"storeId");
});

$("#addCityId").change(function(){
	var cityId=$(this).val();
	var compayDate=loadCompany(cityId);
	putOptionCompany(compayDate,"addCompanyId");
});

//选择公司级联门店
$("#addCompanyId").change(function(){
	var companyId=$(this).val();
	var storeDate=loadStore(companyId);
	putOptionStore(storeDate,"addStoreId");
});

$("#updateCityId").change(function(){
	var cityId=$(this).val();
	var compayDate=loadCompany(cityId);
	putOptionCompany(compayDate,"updateCompanyId");
});

$("#updateCompanyId").change(function(){
	var companyId=$(this).val();
	var storeDate=loadStore(companyId);
	putOptionStore(storeDate,"updateStoreId");
});

//自定义验证器
$.extend($.fn.validatebox.defaults.rules, {
	//移动手机号码验证  
    mobile: {//value值为文本框中的值  
        validator: function (value) {  
            var reg = /^1[3|4|5|7|8|9]\d{9}$/;  
            return reg.test(value);  
        },  
        message: '手机号码格式不正确'
    },
    idNumber:{
    	validator: function (value) {
    		if(isNull(value)){
    			return true;
    		}
            var reg = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;  
            var t = reg.test(value); 
            return t;
        },  
        message: '身份证格式不正确'
    },
    newPassword:{
    	validator: function (value) {  
            var newPwd = $('#update_password_form #newPassword').val();
            if(value === newPwd){
            	return true;
            }
            return false;
        },  
        message: '两次密码填写不相同'
    },
    fileType:{
    	validator: function (file) {  
    		var fileType = file.substring(file.lastIndexOf('.')+1,file.length);//文件后缀名 
    		var fileTypeLower = fileType.toLowerCase();
    		if(fileTypeLower == 'jpg' || fileTypeLower=='png'){   
    	        return true;   
    	    }   
            return false;
        },  
        message: '上传图片格式只能为jpg或者png'
    },
    validPwd:{
    	validator: function (value) {  
    		var reg = /^\w{6,12}$/;
    		return reg.test(value);
        },  
        message: '密码需为6至12位的数字字母'
    }
});

//新增提交
function submitForm(btn){
	//初始化表单中的验证器
	$('input',$("#add_form") ).each(function(){
	    $(this).validatebox();
	});
	//验证通过，可以提交表单
	$('#add_form').form("submit",{
		onSubmit: function(){
			var storeId = $('#addStoreId').val();
			if(storeId==null || storeId==""){
				$.messager.alert('温馨提示','请选择门店，若没有门店请先在门店管理菜单新增门店','info');
				return false;
			}
			var va = $("#add_form").form('validate');
			return va;
		},
		success:function(data){
			if(data == 1){
				$('#add-dialog').dialog('close');
				$('#add-dialog').form('clear');
				doSearch();//重新加载数据
				$.messager.alert('温馨提示','新增成功','info');
			}else{
				$.messager.alert('温馨提示', data, 'info');
			}
		}
	});
}

//编辑提交
function submitEditForm(btn){
	$('#agentInfoSubmitBtn').linkbutton('disable');
	
	//初始化表单中的验证器
	$('input',$("#edit_form") ).each(function(){
	    $(this).validatebox();
	});
	//验证通过，可以提交表单
	$('#edit_form').form("submit",{
		onSubmit: function(){
			var isSub = $("#edit_form").form('validate');
			return isSub;
		},
		success:function(data){
			$('#agentInfoSubmitBtn').linkbutton('enable');
			if(data == 1){
				$('#edit-dialog').dialog('close');
				doSearch();//重新加载数据
				$.messager.alert('温馨提示','修改成功','info');
			}else{
				$.messager.alert('温馨提示', data, 'info');
			}
		}
	});
}

//修改密码提交
function submitPasswordForm(btn){
	//初始化表单中的验证器
	$('input',$("#update_password_form") ).each(function(){
	    $(this).validatebox();
	});
	//验证通过，可以提交表单
	$('#update_password_form').form("submit",{
		onSubmit: function(){
			return $("#update_password_form").form('validate');
		},
		success:function(data){
			if(data == 1){
				$('#update_password-dialog').form('clear');
				$('#update_password-dialog').dialog('close');
				doSearch();//重新加载数据
				$.messager.alert('温馨提示','密码修改成功','info');
			}else{
				$.messager.alert('温馨提示', data, 'info');
			}
		}
	});
}

function cancelAddForm(){
	$('#add-dialog').form('clear');
	$('#add-dialog').dialog("close");
}

function cancelEditSubmit(){
	$('#edit-dialog').form("clear");
	$('#edit-dialog').dialog("close");
}

function caclePasswordForm(){
	$('#update_password_form').form("clear");
	$("#update_password-dialog").dialog("close");
}

function change(v,divId){
	getPath2(document.getElementById(divId),v,v.value);
}
	
//附带不用修改浏览器安全配置的javascript代码，兼容ie， firefox全系列
function getPath2(obj,fileQuery,transImg){
	if(window.navigator.userAgent.indexOf("MSIE")>=1){
		obj.setAttribute("src",transImg);
	}else{
		var file =fileQuery.files[0];
		var reader = new FileReader();
		reader.onload = function(e){
			obj.setAttribute("src",e.target.result);
		};
		reader.readAsDataURL(file);
	}
}
