/**
 * 公司列表JS
 */

// 初始化 数据
$(document).ready(function() {
	createMainTable();
	//resizeGrid();
});


function restFrom() {
	$("#realName").val("");
	$("#mobile").val("");
}

// 搜索
function doSearch() {
	var pars = "{";

	var realName = $("#realName").val();
	if (realName != null && realName != '') {
		pars += "'name' : '" + realName + "',";
	}

	var cityId = $("#cityId").combobox('getValue');
	if (cityId != null && cityId != '') {
		pars += "'cityId' : '" + cityId + "',";
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
	$("#add-dialog").show();//必须先显示，再弹出
    $("#add-dialog").dialog({
        title: "新增",
        width: 400,
        height: 300,
        iconCls: 'icon-add',
        modal:true 
    });
}
/*function showEditDialog(){
	$("#edit-dialog").show();//必须先显示，再弹出
    $("#edit-dialog").dialog({
        title: "编辑",
        width: 400,
        height: 300,
        iconCls: 'icon-add',
    });
}*/
function disabledAddForm(){
	$('#add-dialog').dialog('close');
	$('#add-dialog').form('clear');
}
function disabledEditForm(){
	$('#edit-dialog').dialog('close');
	$('#edit-dialog').form('clear');
}
function showEditDialog(){
    //var row = $('#mainfrom').datagrid('getSelected');
	var pars = $("#mainfrom").datagrid("getSelections");
	if(pars.length == 0){
    	$.messager.alert("温馨提示", "请选择一行进行编辑");
    	return;
    }
    if(pars.length != 1){
    	$.messager.alert("温馨提示", "只能选择一行进行编辑");
    	return;
    }
    var data = pars[0];
	//设置已有信息
	$("#edit_form input[name = companyName]").val(data.companyName);
	$("#edit_form input[name = companyProfile]").val(data.companyProfile);
	//$("#edit_form input[name = cityId]").val(data.city);
	$("#edit_form #cityId2").combobox('setValue',data.cityId); 
	$("#edit_form #cityId2").combobox('setText',data.city);
	$("#edit_form input[name = userName]").val(data.userName);
	$("#edit_form input[name = mobile]").val(data.mobile);
	$("#edit_form input[name = address]").val(data.address);
	$("#edit_form input[name = id]").val(data.id);
	$("#edit-dialog").show();//必须先显示，再弹出
    $("#edit-dialog").dialog({
        title: "修改",
        width: 400,
        height: 350,
        modal:true 
    });
    
}
//自定义验证器
$.extend($.fn.validatebox.defaults.rules, {
	//移动手机号码验证  
    mobile: {//value值为文本框中的值  
        validator: function (value) {  
            var reg = /^1[3|4|5|7|8|9]\d{9}$/;  
            return reg.test(value);  
        },  
        message: '输入手机号码格式不准确'
    },
});

function submitForm(){
	//初始化表单中的验证器
	/*$('input',$("#add_form") ).each(function(){
	    $(this).validatebox();
	});*/ 
	if($("#add_form input[name = companyName]").val()== ''){
		$.messager.alert("温馨提示","请填写公司名称");return;
	}
	if($("#add_form input[name = companyProfile]").val()==''){
		$.messager.alert("温馨提示","请填写公司简介");return;
	}
	if($("#add_form input[name = cityId]").val()==''){
		$.messager.alert("温馨提示","请选择城市");return;
	}
	if($("#add_form input[name = userName]").val()==''){
		$.messager.alert("温馨提示","请填写联系人");return;
	}
	if($("#add_form input[name = mobile]").val()==''){
		$.messager.alert("温馨提示","请填写联系电话");return;
	}
	if($("#add_form input[name = address]").val()==''){
		$.messager.alert("温馨提示","请填写地址");return;
	}
	//验证通过，可以提交表单
	 $('#add_form').form("submit",{
		onSubmit: function(){
			return $("#add_form").form('validate');
		},
		data: $("#add_form").serialize(),
		success:function(data){
			if(data != 0){
				$.messager.alert("温馨提示","添加成功");
				$('#add-dialog').dialog('close');
				$('#add-dialog').form('clear');
				doSearch();//重新加载数据
			}else{
				$.messager.alert('温馨提示', "添加失败，已存在该公司", 'info');
			}
		}
	}); 
}

function editForm(){
	if($("#edit_form input[name = companyName]").val()== ''){
		$.messager.alert("温馨提示","请填写公司名称");return;
	}
	if($("#edit_form input[name = companyProfile]").val()==''){
		$.messager.alert("温馨提示","请填写公司简介");return;
	}
	if($("#edit_form input[name = cityId]").val()==''){
		$.messager.alert("温馨提示","请选择城市");return;
	}
	if($("#edit_form input[name = userName]").val()==''){
		$.messager.alert("温馨提示","请填写联系人");return;
	}
	if($("#edit_form input[name = mobile]").val()==''){
		$.messager.alert("温馨提示","请填写联系电话");return;
	}
	if($("#edit_form input[name = address]").val()==''){
		$.messager.alert("温馨提示","请填写地址");return;
	}
	var reg = /^1[3|4|5|7|8|9]\d{9}$/; 
	if(!reg.test($("#edit_form input[name = mobile]").val())){
		$.messager.alert("温馨提示","联系电话格式有误");return;
	}
	 $('#edit_form').form("submit",{
			onSubmit: function(){
				return $("#edit_form").form('validate');
			},
			data: $("#edit_form").serialize(),
			success:function(data){
				if(data != 0){
					$.messager.alert("温馨提示","修改成功");
					$('#edit-dialog').dialog('close');
					$('#edit-dialog').form('clear');
					doSearch();//重新加载数据
				}else{
					$.messager.alert('温馨提示', "修改失败,已存在该公司", 'info');
				}
			}
		}); 
}
function deleteCompany(){
	var pars = $("#mainfrom").datagrid("getSelections");
	if(pars.length == 0){
    	$.messager.alert("温馨提示", "请选择要删除的公司");
    	return;
    }
	var ids = [];
	for(var i=0; i<pars.length; i++){
		ids.push(pars[i].id);
	}
    //var url = ctxpath + '/company/deleteCompany.action';
   /* $.post(url,{ids:ids},function(data){
    	alert(data);
    	if(data.data != 0){
			$.messager.alert("温馨提示","删除成功");
			doSearch();//重新加载数据
		}else{
			$.messager.alert('温馨提示', "删除失败", 'info');
		}
	}); */
    
    $.messager.confirm("温馨提示", "您确定要删除该公司吗？", function(r){
    	if(r){
    		$.ajax({
    		      url:ctxpath +'/company/deleteCompany.action',
    		      type:"POST",
    		      dataType:"text",
    		      data: "ids=" + ids + "",
    		     success : function(data){
    		    	 if(data > 0){
    		 			$.messager.alert("温馨提示","删除成功");
    		 			doSearch();//重新加载数据
    		 		}else{
    		 			$.messager.alert('温馨提示', "该公司有下属信息，不能删除", 'info');
    		 			doSearch();//重新加载数据
    		 		}
    		      }
    	    });
    	}
    });
}
// 创建支付汇总表格
function createMainTable() {
	$('#mainfrom').datagrid(
					{
						url : ctxpath + '/company/getCompanyList.action',
						fitColumns : true, // True
											// 就会自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
						scrollbarSize : 0, // 去掉表格的滚动条宽度，美观表格
						striped : true, // True 就把行条纹化
						pagination : true,// 分页
						rownumbers : true, // 显示行号
						pageSize : 20,
						pageNumber : 1,
						pageList : [ 20, 30, 50, 100 ],// 列表分页
						loadMsg : '数据正在努力加载中...',
						selectOnCheck : true, // 点击行的时候也触发 同时 触发 点击checkbox
						checkOnSelect : true, // 点击 checkbox 同时触发 点击 行
						columns : [ [
								{ field: 'ck', checkbox: true,},  //复选框 	
								{
									field : 'id',
									align : 'center',
									resizable : true,
									hidden : true,
									sortable : false,
									width : $(this).width() * 0.1
								},
								{
									field : 'companyName',
									title : '公司名称',
									align : 'center',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.1
								},
								{
									field : 'companyProfile',
									title : '公司简称',
									align : 'center',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.1
								},
								{
									field : 'city',
									title : '城市',
									align : 'gender',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.2,
								},
								{
									field : 'cityId',
									title : 'cityId',
									align : 'gender',
									resizable : true,
									hidden : true,
									sortable : false,
									width : $(this).width() * 0.2,
								},
								{
									field : 'userName',
									title : '联系人',
									align : 'birthDate',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.2
								},
								{
									field : 'mobile',
									title : '联系电话',
									align : 'birthDate',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.2
								},
								{
									field : 'address',
									title : '地址',
									align : 'mobile',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.1
								},
								{
									field : 'createTimeStr',
									title : '创建时间',
									align : 'mobile',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.1
								},
								] ],
								toolbar : [
											{
												text : '新增',
												iconCls:'icon-add', 
												handler : function() {
													showAddDialog();
													
												},
											},
											{
												text : '删除',
												iconCls:'icon-remove',
												handler : function() {
													deleteCompany();
												}
											},
											{
												text : '编辑',
												iconCls:'icon-edit', 
												handler : function() {
													showEditDialog();
												},
											}],
						onDblClickRow : function(rowIndex, rowData) {
							//alert(rowData.payId);
						}
					});

}

//获取城市下拉列表
$('#cityId').combobox({
    url: ctxpath +'/dicAreaNew/getCity.action',
    valueField: 'id',
    textField: 'name'
});
$('#cityId1').combobox({
    url: ctxpath +'/dicAreaNew/getCity.action',
    valueField: 'id',
    textField: 'name'
});
$('#cityId2').combobox({
    url: ctxpath +'/dicAreaNew/getCity.action',
    valueField: 'id',
    textField: 'name'
});
