/**
 * 支付汇总JS
 */
	//设置日期
		function restFrom(){
		    var d =new Date();
			var curr_time =d.getFullYear()+"-"+(d.getMonth()+1)+"-"+(d.getDate()-1);
			$("#exportTime1").datebox("setValue",curr_time);
			$("#exportTime2").datebox("setValue",curr_time);
			var json = loadArea();
			putOption(json,"cityid");
		}
	//搜索
		function doSearch(){
			var pars="{";
			var t =$("#exportTime1").datetimebox("getValue");
			if(t != null && t !=''){
				pars +="'startTime' : '" + t+"' , ";
			}
			
			var t1 =$("#exportTime2").datetimebox("getValue");
			if(t1 != null && t1 !=''){
				pars +=" 'endTime' : '" + t1+"' , ";
			}
			if(t != '' && t1 != ''){
				if(t>t1){
					$.messager.alert("温馨提示","起始时间不能大于截止时间!","warning");
					return ;
				}
			}
			var realName = $("#realName").val();
			if(realName != null && realName !=''){
				pars +="'realName' : '"+realName+"',";
			}
			var cityid = $("#cityid").val();
			if(cityid != null && cityid !=''){
				pars +="'cityid' : '"+cityid+"',";
			}
			
			var districtid = $("#districtid").val();
			if(districtid != null && districtid !=''){
				pars +="'districtid' : '"+districtid+"',";
			}
			
			if(pars.length >1){
				pars = pars.substr(0,pars.length-1);
			}
			pars += "}";
			pars = eval("("+pars+")");
		    $('#mainfrom').datagrid('reload',pars);
	
		} 
	    //支付詳情表格
	    var pay_form=$("#payfroms").datagrid({
			url : ctxpath+'/pay/getPayInfo.action',
			fitColumns : true, //True 就会自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
			scrollbarSize :0,   //去掉表格的滚动条宽度，美观表格
			striped : true, //True 就把行条纹化
			pagination : false,//分页
			rownumbers : true, //显示行号
			pageSize : 20,
			pageNumber : 1,
			pageList : [ 20, 30, 50, 100 ],//列表分页
			loadMsg : '数据正在努力加载中...',
			selectOnCheck : true, //点击行的时候也触发 同时 触发 点击checkbox
			checkOnSelect : true, //点击 checkbox 同时触发 点击 行
			columns : [ [
			     	  {field : 'createTime',title : '发布时间',align : 'center',resizable : true,hidden : false,sortable : false,
			     		 formatter:function(value,row,index){
								return dateFmt("yyyy-MM-dd hh:mm:ss",row.createTime);
							}}
					,{field : 'employeeId',title : '员工id',align : 'center',resizable : true,hidden : false,sortable : false}
					,{field : 'paySum',title : '付款金额',align : 'center',resizable : true,hidden : false,sortable : false}
					,{field : 'payReason',title : '付款原因 ',align : 'center',resizable : true,hidden : false,sortable : false}
					,{field : 'payState',title : '付款状态',align : 'center',resizable : true,hidden : false,sortable : false,
					formatter:function(value,row,index){
								//return dateFmt("yyyy-MM-dd hh:mm:ss",row.createTime);
								var stats=row.payState;
								if(stats==1){
									return "已支付";
								}else if(stats==2){
									return "未支付";
								}else{
									return "默认";
								}	
				    }}
			] ]

		});
	
		function resizeGrid() {
			$('#mainfrom').datagrid('resize', {
				width : function() {
					return (document.body.clientWidth -220 )* 0.9;
				}
			});
		}
		
		//创建支付汇总表格
		function createMainTable(){
		 $('#mainfrom').datagrid({
				url : ctxpath+'/pay/getPaySumInfo.action',
				fitColumns : true, //True 就会自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
				scrollbarSize :0,   //去掉表格的滚动条宽度，美观表格
				striped : true, //True 就把行条纹化
				pagination : true,//分页
				rownumbers : true, //显示行号
				pageSize : 20,
				pageNumber : 1,
				pageList : [ 20, 30, 50, 100 ],//列表分页
				loadMsg : '数据正在努力加载中...',
				selectOnCheck : true, //点击行的时候也触发 同时 触发 点击checkbox
				checkOnSelect : true, //点击 checkbox 同时触发 点击 行
				queryParams: {startTime:$("#exportTime1").datetimebox("getValue"),endTime:$("#exportTime2").datetimebox("getValue"),cityid:$("#cityid").val(),districtid:$("#districtid").val()
				} ,
				columns : [ [
				          {field : 'userId',title : '用户ID',align : 'center',resizable : true,hidden : true,sortable : false},
				          {field : 'publishDate',title : '发布时间',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1,
				        	       formatter:function(value,row,index){
									  return dateFmt("yyyy-MM-dd",row.publishDate);
								     }}
						, {field : 'realName',title : '姓名',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
						, {field : 'city',title : '城市',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.2}
						, {field : 'payTime',title : '支付时间',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.2,
							formatter:function(value,row,index){
								return dateFmt("yyyy-MM-dd hh:mm:ss",row.payTime);
							}
						}
						, {field : 'payNum',title : '支付金额',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1}
						, {field : 'payState',title : '支付状态',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1,formatter:function(value,row,index){
							//return dateFmt("yyyy-MM-dd hh:mm:ss",row.createTime);
							var stats=row.payState;
							if(stats==1){
								return "已支付";
							}else if(stats==2){
								return "未支付";
							}else{
								return "默认";
							}		
			    }}
						, {field : 'opt',  title : '查看',align : 'center',resizable : true,hidden : false,sortable : false,width:$(this).width() * 0.1,
							formatter : function (value,row,index)
							 {
							var str="<a href='javascript:void(0);' onclick='view_pay_win("+row.userId+","+row.publishDate+")'>查看</a> ";
							return str;
									
						 }
						}
				] ],toolbar : [  {
					text : '导出',
					iconCls : 'icon-export',
					handler : function() {
						
						window.location.href=ctxpath+"/exportxlspay/exportXlsPayListExcel.action";
						
					}
				},  {
					text : '导入',
					iconCls : 'icon-import',
					handler : function() {
						$("#improt_task").dialog("open");
						
					}
				} ],
				onDblClickRow : function(rowIndex, rowData) {
					//alert(rowData.payId);
				}

			});
		
			
			
		}
		


    //打开详情窗口
	function view_pay_win(userid,publishDate){
    	publishDate=dateFmt("yyyy-MM-dd",publishDate);
    	var pars="{";
    	pars +="'userid' : '"+userid+"',";
    	pars +="'publishDate' : '"+publishDate+"'";
    	pars += "}";
    	pars = eval("("+pars+")");
    	$('#payfroms').datagrid('reload',pars);
    	$("#pay_task").dialog("open");
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
	//加载 区域
	function loadAreaDis(cityid){
		var url1= ctxpath+"/dicAreaNew/getDistrictidByCity.action";
		var resultData={};
		$.ajax( {
			url : url1,
			data:{parentId:cityid},
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
	
/*
	添加下拉框的数据
*/
function putOption(json,selectid){
	if(!(json === undefined)){
		var str='';
		if(json == null || json.length == 0 ){
			str ='<option value="0" selected="selected">全部</option>';
		}
		var sel=$("#"+selectid);
		sel.html('');
		for(var i =0 ; i< json.length ; i++){
			var row = json[i];
	    	str +='<option value="'+row.id+'"  >'+row.name+'</option>'; 
	  
		}
		sel.html(str);
		 if(selectid=='cityid'){
			 var cityId=$("#cityid option:first").val();
			 $("#cityid").trigger("change","parentId:"+cityId+"");
		}
	}
}