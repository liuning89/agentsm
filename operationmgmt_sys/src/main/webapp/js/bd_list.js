/**
 * 经纪人列表JS
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
		pars += "'realName' : '" + realName + "',";
	}

	var mobile = $("#mobile").val();
	if (mobile != null && mobile != '') {
		pars += "'mobile' : '" + mobile + "',";
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
	$('input',$("#add_form") ).each(function(){
	    $(this).validatebox();
	});
	//验证通过，可以提交表单
	$('#add_form').form("submit",{
		onSubmit: function(){
			return $("#add_form").form('validate');
		},
		success:function(data){
			if(data == "success"){
				$('#add-dialog').dialog('close');
				$('#add-dialog').form('clear');
				doSearch();//重新加载数据
			}else{
				$.messager.alert('Info', data, 'info');
			}
		}
	});
}

// 创建支付汇总表格
function createMainTable() {
	$('#mainfrom')
			.datagrid(
					{
						url : ctxpath + '/BD/bdListData.action',
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
						queryParams : {
							realName : $("#realName").val(),
							mobile : $("#mobile").val()
						},
						columns : [ [
								{
									field : 'realName',
									title : '姓名',
									align : 'center',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.1
								},
								{
									field : 'gender',
									title : '性别',
									align : 'gender',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.2,
									formatter:function(value, row, index){
										if(value == 1){
											return "男";
										}else if(value == 2){
											return "女";
										}else{
											return "保密";
										}
									}
								},
								{
									field : 'birthDate',
									title : '出生日期',
									align : 'birthDate',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.2
								},
								{
									field : 'mobile',
									title : '电话/邀请码',
									align : 'mobile',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.1
								},
								{
									field : 'email',
									title : '邮箱',
									align : 'email',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.1
								},
								{
									field : 'spreadCount',
									title : '邀请人数',
									align : 'spreadCount',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.1
								},
								{
									field : 'spreadingCount',
									title : '邀请中人数',
									align : 'spreadingCount',
									resizable : true,
									hidden : false,
									sortable : false,
									width : $(this).width() * 0.1
								}] ],
						toolbar : [
								{
									text : '导出',
									iconCls : 'icon-export',
									handler : function() {
										window.location.href = ctxpath+ "/BD/exportBdUser.action";
									}
								}],
						onDblClickRow : function(rowIndex, rowData) {
							//alert(rowData.payId);
						}
					});

}
