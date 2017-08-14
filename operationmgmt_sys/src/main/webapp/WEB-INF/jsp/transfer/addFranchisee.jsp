<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
    Date date=new Date();//取时间
    Date d = date;
    Calendar calendar = Calendar.getInstance();
    
    calendar.setTime(date);
    d = calendar.getTime();
    calendar.add(Calendar.MONTH, -1);
    
    date  = calendar.getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = formatter.format(date);
    String dString = formatter.format(d);
    String startDate = dateString + " 00:00:00";
    String endDate = dString + " 23:59:59";
%>
<html>
<head path="<%=basePath%>">
    <base href="<%=basePath%>" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <script type="text/javascript" src="scripts/boot.js?v=${version}"></script>
<title></title>
    <style type="text/css">
        .show {
            display: block;
        }
        .hdder {
            display: none;
        }

    </style>
</head>
<body>

	<form id="contactform" method="post">

            <div style="text-align: left">
                <h1><strong>合作伙伴信息</strong></h1>
            </div>
            <table style="margin-left:20px;">
                <%-- <tr>
                   <td>
                        城市<span style="color: red">*</span>
                   </td>
                    <td>
                        <input id="cityId" class="mini-combobox" style="width:150px;" textField="name" valueField="id"
                         url="${ctx}/areaorg/getFranchiseeListByLogin.action?level=90" value="" showNullItem="true"  allowInput="true" onvaluechanged="onCityChanged" emptyText="请选择" nullItemText="请选择" />
                    </td>
                    <td style="text-align: right">
            	        区域
                    </td>
                    <td>
                        <input id="areaId" class="mini-combobox" style="width:150px;" textField="name" valueField="code"
                      url="${ctx}/areaorg/getFranchiseeListByLogin?level=1" value="" showNullItem="true"  allowInput="true" emptyText="请选择" nullItemText="请选择"/>
                            <input id="areaId" class="mini-textbox"  style="width: 100px;" />
                            <a class="mini-button" onclick="showWin()" style="width:40px;margin-right:20px;">选择</a>
                            <a class="mini-button" onclick="onClean()" style="width:40px;margin-right:20px;">清空</a>
                    </td>
                </tr> --%>
	          

            
            <tr>
                <td>
            	    简称<span style="color: red">*</span>
                </td>
                <td>
                    <input id="abbreviation" class="mini-textbox" emptyText="请输入简称"  style="width: 150px;" />
                </td>
                <td style="text-align: right">
           		    公司名称
                </td>
                <td>
                    <input id="companyname" class="mini-textbox"  style="width: 150px;" />
                </td>
            </tr>
				<tr>
                    <td>
          		        法人代表
                    </td>
                    <td>
                        <input id="corporate" class="mini-textbox"  style="width: 150px;" />
                    </td>
                    <td style="text-align: right">
          		        电话
                    </td>
                    <td>
                        <input id="corporatephone" class="mini-textbox"  style="width: 150px;" />
                    </td>

                </tr>
            
             <tr>
                 <td>
             	    合伙人1
                 </td>
                 <td>
                    <input id="partner1" class="mini-textbox"  style="width: 150px;" />
                 </td>
                 <td style="text-align: right">
               	    电话
                 </td>
                 <td>
                    <input id="partnerphone1" class="mini-textbox"  style="width: 150px;" />
                 </td>
             </tr>
                    	 
                    	
               	<tr>
                    <td>
               	        合伙人2
                    </td>
                    <td>
                        <input id="partner2" class="mini-textbox"  style="width: 150px;" />
                    </td>
                    <td style="text-align: right">
               	        电话
                    </td>
                    <td>
                        <input id="partnerphone2" class="mini-textbox"  style="width: 150px;" />
                    </td>
                </tr>


                <tr>
                    <td>
                        BD
                    </td>
                    <td>
                        <input id="bd" class="mini-textbox"  style="width: 150px;" />
                    </td>
                    <td style="text-align: right">
                        电话
                    </td>
                    <td>
                        <input id="bdphone" class="mini-textbox"  style="width: 150px;" />
                    </td>
                </tr>

                <tr>
                    <td>
                        BP
                    </td>
                    <td>
                        <input id="bp" class="mini-textbox"  style="width: 150px;" />
                    </td>
                    <td style="text-align: right">
                        电话
                    </td>
                    <td>
                        <input id="bpphone" class="mini-textbox"  style="width: 150px;" />
                    </td>
                </tr>


                <tr>
                    <td>
               		    地址
                    </td>
                    <td colspan="5">
                        <input id="address" class="mini-textbox"  style="width:445px;" />
                    </td>
                </tr>

                <tr>
                    <td>
					    合作时间
                    </td>
                    <td>
                        <input id="queryTimeBegin" class="mini-datepicker" value="<%=startDate %>" format="yyyy-MM-dd" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>
                    </td>
					<td>
					    <input id="queryTimeEnd" class="mini-datepicker" value="<%=endDate %>" format="yyyy-MM-dd" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/>
                    </td>
                    <td>
					    <input id="isseed" class="mini-checkbox"  style="width: 150px;" />是否属于种子用户
                    </td>
                </tr>
                <tr>
                    <td>
                	    保证金<span style="color: red">*</span>
                    </td>
                    <td>
                	    <input id="deposit" class="mini-textbox"  style="width: 150px;" />元
                    </td>
                    <td style="text-align: right">
                	    品牌使用费<span style="color: red">*</span>
                    </td>
                    <td>
                	    <input id="brandcost" class="mini-textbox"  style="width: 150px;" />元
                    </td>
                </tr>
                <tr>
                    <td>
                	    合作费:<span style="color: red">*</span>
                    </td>
                    <td>
                        <input id="cooperationcost" class="mini-textbox"  style="width: 150px;" />%
                    </td>
                </tr>

                    <tr>
                        <td colspan="2">
                            <input id="isaccounts" class="mini-checkbox" onclick="checked"  />

                            开通合作伙伴人事账号
                            </td>
                            <%--<td id="clo" class="hdder" style="text-align: right"  >--%>
                                <%--合作伙伴手机号--%>

                                <%--<span style="color: red">*</span>--%>
                            <%--</td>--%>
                        <td id="clos" colspan="5" class="hdder">
                                <input id="franchiseePhone" class="mini-textbox"   emptyText="请输入手机号" />

                            </td>
                    </tr>

        <tr>
        <div style="margin-left:20px;padding-bottom:5px;padding-top:10px;">
				<table style="text-align: center">
					<tr>
						<td>可选城市
							<div id="dg1" class="mini-datagrid" style="margin-left:20px;width:200px;height:250px" showCheckBox="true"
								multiSelect="true" showPager="false" >
								<div property="columns">
									<div type="checkcolumn" width="10"></div>
									<%--<div header="ID" field="cityId" width="10" ></div>--%>
									<div header="城市" field="text" width="35" ></div>
								</div>
							</div>
						</td>
						<td style="width:120px;text-align:center;">
							<input type="button" value=">" onclick="add()" style="width:40px;" /><br />
							<input type="button" value=">>" onclick="addAll()" style="width:40px;" /><br />
							<input type="button" value="&lt;&lt;" onclick="removeAll()" style="width:40px;" /><br />
							<input type="button" value="&lt;" onclick="removes()" style="width:40px;" /><br /></td>
						<td>已选城市
							<div id="dg2" class="mini-datagrid" style="width:200px;height:250px;" showCheckBox="true"
								multiSelect="true" showPager="false">
								<div property="columns">
									<div type="checkcolumn" width="10"></div>
									<%--<div header="ID" field="cityId" width="10" style="display: block"></div>--%>
									<div header="城市" field="text" width="35"></div>

								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
        </tr><tr style="text-align: center">
            <td colspan="2" >
        	    <a class="mini-button" onclick="onSaveData()" style="width:60px;margin-left:35%;margin-right:20px;">保存</a>
            </td>
            <td colspan="2" >
          	    <a class="mini-button" onclick="onCancel()" style="width:60px;">关闭</a>
            </td>

        </tr>
        </table>
    </form>
    <script type="text/javascript">

        mini.parse();
        var dg1 = mini.get("dg1");
  	 	var dg2 = mini.get("dg2");
  	  	loadDg2();
  		loadDg1();
        //id="+id+"&title="+title+"&pushContent="+istiming+"&pushtime="+pushtime,
      	
        function CloseWindow(action) {
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();            
        }

        function onCancel(e) {
            //CloseWindow("cancel");
        	window.CloseOwnerWindow();
        }
        function ontimeValueChanged(e) {
          //  alert(this.getFormValue());
        }
        
        function loadDg1() {
			$.ajax({
				method : "post",
				url : "${ctx}/areaOrg/list/simple.action?level=90",
				success : function(result) {
					/* var dg1 = mini.get("dg1"); */
					var datas1 = result;
					var datas2 = mini.get("dg2").getData();
					if (datas2.length > 0) {
						for (var i = 0; i < datas2.length; i++) {
							var data2 = datas2[i];
							for (var j = 0; j < datas1.length; j++) {
								var data1 = datas1[j];
								if (data2.id == data1.id) {
									datas1.splice(j, 1);
									break;
								}
							}
						}
					}
					dg1.setData(datas1);
				}
			});
		}


	  function loadDg2() {
		  $.ajax({
			  method : "post",
		//	  url : "${ctx}/areaorg/getListByLoginIn.action?level=70&parentId=${cityId}&",
			  url : "${ctx}/transferMng/getData2.action?areaId=${areaId}",
			  success : function(result) {
				  /* var dg1 = mini.get("dg1"); */
				  var datas1 = result;
//
				  dg2.setData(datas1);
			  }
		  });
	  }
        
        
      //城市区域下啦选择
    	/* function onCityChanged(e) {
                var cityCbo = mini.get("cityId");
                var areaCbo = mini.get("areaId");
                var id = cityCbo.getValue();
                
                areaCbo.select(0);
                var url = "${ctx}/areaorg/getFranchiseeListByLogin?level=70";
                if(id != null && id != "")
                {
                    url += "&parentId=" + id;
                }
                areaCbo.setUrl(url);
                areaCbo.select(0);
                
            } */
          //城市区域下啦选择
        	function onCityChanged(e) {
                    var cityCbo = mini.get("cityId");
                    var areaCbo = mini.get("areaId");
                    var id = cityCbo.getValue();
                    
                   /* areaCbo.select(0); */
                    var url = "${ctx}/areaorg/getFranchiseeOnlyListByLogin.action?level=70";
                    if(id != null && id != "")
                    {
                        url += "&parentId=" + id;
                    }
                   /*  areaCbo.setUrl(url); */
//                    areaCbo.select(0);
                    
                    /* var storeCbo = mini.get("storeId");
                    storeCbo.setUrl("${ctx}/areaorg/getListByLogin.action?level=1");
                    storeCbo.select(0); */
                    /* var newAgentCbo = mini.get("newAgentId");
                    newAgentCbo.setUrl("${ctx}/agent/getAgentByStoreOrg.action?orgCode=000") */
                }

            
            function add() {
       		 /*  var dg1 = mini.get("dg1");
       		  var dg2 = mini.get("dg2"); */
       		  var rows = dg1.getSelecteds();

       		  if (rows.length == 0) {
       			  mini.alert("至少选中1行！");
       			  return ;
       		  }
       		  dg1.removeRows(rows);
       		  dg2.addRows(rows);
       	  }
            function addAll(){

        		var data1 = dg1.getData();
        		if (data1.length == 0) {
        			return ;
        		}
        		dg1.clearRows();
        		var data2 = dg2.getData();
        		data2 = data2.concat(data1);
        		dg2.setData(data2);
        	}

        		function removeAll(){
        			var data2 = dg2.getData();
        			if (data2.length == 0) {
        				return ;
        			}
        			var d2 = new Array();
        			for (var i = 0; i < data2.length; i++) {
        				var data = data2[i];
        					d2.push(data2[i]);
        			}
        			dg2.clearRows();
        			if (d2.length == 0) {
        				return ;
        			}
        			var data1 = dg1.getData();
        			data1 = data1.concat(d2);
        			dg1.setData(data1);
        		}
        		function removes(){
        			var rows = dg2.getSelecteds();
        			if (rows.length == 0) {
        				mini.alert("至少选中1行！");
        				return ;
        			}
        			dg2.removeRows(rows);
        			for (var i = 0; i < rows.length; i++) {
        				var row = rows[i];
        				dg1.addRow(row);
        			}
        		}
            
    /* ********************************************************** */
    var ids = "";
    var names = "";
        function onSaveData(){

            /* if(ids == "")//
            {
                mini.alert("请选择区域");
                return false;
            } */
           

//            if(mini.get("areaId").getValue() == ""){
//                mini.alert("请选择区域");
//                return false;
//            }
            if(mini.get("abbreviation").getValue().trim() == ""){
                mini.alert("请填写公司简称");
                return false;
            }

            if(mini.get("deposit").getValue().trim() == ""){
                mini.alert("请填写保证金");
                return false;
            }
            if(mini.get("brandcost").getValue().trim() == ""){
                mini.alert("请填写品牌使用费");
                return false;
            }
            if(mini.get("cooperationcost").getValue().trim() == ""){
                mini.alert("请填写合作费");
                return false;
            }


            var isaccounts = mini.get("isaccounts").getValue();
            var franchiseePhone="";
            if(isaccounts=="true"){
                franchiseePhone = mini.get("franchiseePhone").getValue();
                if(franchiseePhone.trim() == ""){
                    mini.alert("请填写合作伙伴手机号");
                    return false;
                }
                if(franchiseePhone.trim().length != 11){
                    mini.alert("请填写正确的手机号");
                    return false;
                }
            }else{
                franchiseePhone = "";
            }

            var datas2 = this.dg2.getData();
  		  if (datas2.length == 0) {
  			  mini.alert("至少选择一个城市！");
  			  return ;
  		  }
  		  var cityId = "";
  		  var cityName = ""
  		  var city = "";
  		  for (var i = 0; i < datas2.length; i++) {
  			  var data2 = datas2[i];
	  			cityId += (data2.id + "|");
	  			cityName += (data2.name + "|");
  		  }
  			city = cityId + ";" + cityName;

  		  /* onCance(area); */

            
            lc.mask("正在处理中,请稍后...");
            $.ajax({
                url : "${ctx}/transferMng/saveFranchiseeInfo.action",
                cache : false,
                dataType : 'text',
                data:{cityId:cityId,//mini.get("cityId").getFormValue(),
                    areaId:ids,
                    abbreviation:mini.get("abbreviation").getValue(),companyname:mini.get("companyname").getValue(),
                    corporate:mini.get("corporate").getValue(),corporatephone:mini.get("corporatephone").getValue(),
                    partner1:mini.get("partner1").getValue(),partnerphone1:mini.get("partnerphone1").getValue(),
                    partner2:mini.get("partner2").getValue(),partnerphone2:mini.get("partnerphone2").getValue(),
                    bd:mini.get("bd").getValue(),bdphone:mini.get("bdphone").getValue(),
                    bp:mini.get("bp").getValue(),bpphone:mini.get("bpphone").getValue(),
                    address:mini.get("address").getValue(),cooperationstart:mini.get("queryTimeBegin").getFormValue(),
                    cooperationend:mini.get("queryTimeEnd").getFormValue(),isseed:mini.get("isseed").getValue(),
                    deposit:mini.get("deposit").getValue(),brandcost:mini.get("brandcost").getValue(),
                    cooperationcost:mini.get("cooperationcost").getValue(),franchiseePhone:franchiseePhone
                },
                type: "POST",
                success : function(d) {
                    if(d == 1){
                        lc.unmask();
                        mini.alert("保存成功!","成功",function(){
                        	onCancel();
                        	window.Owner.search();
                        });
                    }else if(d==2){

                        lc.unmask();
                        mini.alert("保存失败,简称已存在,请修改简称后再试!","错误",function(){


                        });
                    }else if(d == 0){
                        lc.unmask();
                        mini.alert("保存失败,请稍后重试","错误",function() {

                        });
                    }
                    else{
                        lc.unmask();
                        mini.alert(decodeURIComponent(d),"错误",function(){

                        });
                    }
                }
            });
        }
        function onClean(){
       	 var t = mini.get("areaId");

            t.setValue("");
            ids = "";
        }
    function showWin(){
        var cityId = mini.get("cityId").getValue();
        if(cityId == ""){
        	mini.alert("请选择城市!");
        	return false;
        }
        mini.open({
            url : "${ctx}/transferMng/openAdd.action?isupdate=2&cityId="+cityId,
            title : "新增",
            width : 800,
            height : 550,
            ondestroy : function(area) {

                if(area != "close"){
                    var areas = area.split(";");
                    ids = areas[0];		
                    names = areas[1];
                    //mini.alert(names);
                    var t = mini.get("areaId");
                   
                    t.setValue(names);
                    //t.setText(names); 

                }
            }
        });
    }
    function onCancel(e) {
        //CloseWindow("cancel");
    	window.CloseOwnerWindow();
    }
        function checked(){
            var isaccounts = mini.get("isaccounts").getValue();
            if(isaccounts=="true"){
                $("#clo").removeClass("hdder");
                $("#clo").addClass("show");

                $("#clos").removeClass("hdder");
                $("#clos").addClass("show");

            }else{

                $("#clo").removeClass("show");
                $("#clo").addClass("hdder");


                $("#clos").removeClass("show");
                $("#clos").addClass("hdder");
            }


        }
    </script>
</body>
</html>