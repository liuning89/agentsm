<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>经纪人列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <script src="${ctx}/scripts/boot.js?v=${version}" type="text/javascript"></script>
    <style type="text/css">
        html,body
        {
            width:100%;
            height:100%;
            border:0;
            margin:0;
            padding:0;
            overflow:visible;
        }
    </style>
</head>

<body>
    <form id="form1" method="post">
        <input name="id" class="mini-hidden" />
        <fieldset style="border: solid 1px #aaa; padding: 3px;">
            <legend>基本信息</legend>
            <div style="padding-left: 11px; padding-bottom: 5px;">
                <form id="form1" style="padding-left:10px;margin:0px;margin-top:10px;">
                    <table style="table-layout: fixed;">
                        <tr>
                            <td  rowspan="10" >
                               <div>
                                <img id="imgs" width="100px" height="100px" src="${ctx}/imgs/user_default.png"/>
                               </div>
                              <br>
                              <input type="file" id="imgFile" onchange="CheckFile(this)"/>
                            </td>
                        </tr>
                        <tr>
                            <td>工号:</td>
                            <td><input class="mini-textbox"  required="true" name="agentNum"  missingMessage="请输入工号"></input></td>
                        </tr>
                        <tr>
                            <td>真实姓名:</td>
                            <td><input class="mini-textbox" required="true" name="realName"  missingMessage="请填写真实姓名"></input></td>
                        </tr>
                        <tr>
                            <td>身份证号:</td>
                            <td><input class="mini-textbox" onvalidation="idNumberValidation" id="idNumber" name="idNumber"></input></td>
                        </tr>
                        <tr>
                            <td>手机号码：</td>
                            <td><input class="mini-textbox" required="true" onvalidation="mobileValidation" id="mobile" name="mobile"  missingMessage="请填写电话号码"></input></td>
                        </tr>
                        <tr>
                            <td>密码:</td>
                            <td><input class="mini-password"  required="true" id="password" onvalidation="passwordValidation" name="password"  missingMessage="请填写密码"></input></td>
                        </tr>
                        <tr>
                            <td>城市:</td>
                    
                            <td>
                                <input id="cityId" name="cityId" class="mini-combobox" required="true" style="width:135px;" textField="name" valueField="id" 
                                                url="${ctx}/dicAreaNew/getCity.action" value="" showNullItem="true"  allowInput="true"
                                                onvalidation="onComboValidation" onvaluechanged="onCityChanged" />
                            </td>
                        </tr>
                        <tr>
                            <td>所属公司:</td>
                            <td>
                                <input id="companyId" name="companyId" class="mini-combobox" required="true" style="width:135px;" textField="companyName" valueField="id" 
                                                url="${ctx}/company/getCompanyListByCityId.action" value="" showNullItem="true"  allowInput="true"
                                                onvalidation="onComboValidation" onvaluechanged="onComChanged" />
                            </td>
                        </tr>
                        <tr>
                            <td>所属门店:</td>
                            <td>
                                <input id="storeId" name="storeId" class="mini-combobox" required="true" style="width:135px;" textField="storeName" valueField="id" 
                                                url="${ctx}/store/getStoreListByCompanyId.action" value="" showNullItem="true"  allowInput="true"
                                                onvalidation="onComboValidation"  />
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </fieldset>
        <div style="text-align: center; padding: 10px;">
            <a class="mini-button" onclick="onOk" style="width: 60px; margin-right: 20px;">确定
            </a> <a class="mini-button" onclick="onCancel" style="width: 60px;">取消</a>
        </div>
    </form>
     
    
    <script type="text/javascript">
        mini.parse();
        var uFile;
        var form = new mini.Form("form1");
        
        function CheckFile(obj) {
            var fileContentType = obj.value.match(/[^\s]+\.(jpg|jpeg|gif|png|bmp|JPG|GIF|PNG|BMP|JPEG)/i);
            if (null==fileContentType) {
                mini.alert("请选择上传图片!","提示");
                uFile = null;
                obj = null;
            }
        }
        
        $("#imgFile").on("change",function(e){
            uFile = null;
            if(window.navigator.userAgent.indexOf("MSIE")>=1){
                obj.setAttribute("src",e.value);
            }else{
                readFile(e);
            }
        });
        
        function readFile(e){
            if(e.target.files){
                uFile = e.target.files[0];
            }else if(e.dataTransfer.files){
                uFile = e.dataTransfer.files[0];
            }
            var reader = new FileReader();
            /* reader.onload = function(e) {
                $("#imgCon").html('<img style="width:100%;height:auto;" src="' + this.result + '"/>');
            }; */
            reader.onload = function(e){
            	 $("#imgs").attr("src",e.target.result);
            };
            reader.readAsDataURL(uFile);
        };
        
        //////////////////////////////////////////////
        function onComboValidation(e) {
            var items = this.findItems(e.value);
            if (!items || items.length == 0) {
                this.setValue('');
            }
        }
        
        function onCityChanged(e) {
            var cityCbo = mini.get("cityId")
            var comCbo = mini.get("companyId");
            var id = cityCbo.getValue();
            comCbo.select(0);
            var url = "${ctx}/company/getCompanyListByCityId.action";
            if(id != null && id != "")
            {
                url += "?cityId=" + id;
            }
            comCbo.setUrl(url);
            comCbo.select(0);
            
            var storeCbo = mini.get("storeId");
            storeCbo.setUrl("${ctx}/store/getStoreListByCompanyId.action");
        }
        
        function onComChanged(e) {
            var comCbo = mini.get("companyId");
            var storeCbo = mini.get("storeId");
            var id = comCbo.getValue();
            storeCbo.select(0);
            var url = "${ctx}/store/getStoreListByCompanyId.action";
            if(id != null && id != "")
            {
                url += "?companyId=" + id;
            }
            storeCbo.setUrl(url);
            storeCbo.select(0);
        }
        
        //标准方法接口定义
        function SetData(data) {
             
        }
        
        function CloseWindow(action) {
            if (action == "close" && form.isChanged()) {
                if (confirm("数据被修改了，是否先保存？")) {
                    return false;
                }
            }
            if (window.CloseOwnerWindow)
                return window.CloseOwnerWindow(action);
            else
                window.close();
        }
        
        function onOk(e) {
            SaveData();
        }
        function onCancel(e) {
            CloseWindow("cancel");
        }
        
        function SaveData(){
        	form = new mini.Form("form1");
            var data = form.getData();
            
            form.validate();
            if (form.isValid() == false)
            {
                return;
            }
            
            var fd = new FormData();
            for(var key in data){
                fd.append(key,data[key]);
            }
            
            if(this.uFile){
                fd.append("imgFile",this.uFile);
            }
            else
            {
                uFile = new Blob();
                var reader = new FileReader();
                reader.readAsDataURL(uFile);
                fd.append("imgFile",this.uFile);
            }
            
            fd.append("imgFile",this.uFile);
            
            var xhr = new XMLHttpRequest();
            lc.mask("正在提交,请稍后...");
            if (xhr.upload) {
                // 文件上传成功或是失败
                xhr.onreadystatechange = function(e) {
                    if (xhr.readyState == 4) {
                        if (xhr.status == 200) {
                            try{
                                var rd = mini.decode(xhr.responseText);
                                lc.mask("上传成功!");
                                CloseWindow("save");
                            }catch(e){
                            	lc.unmask();
                            	mini.alert("添加失败,请重试。" + xhr.responseText,"错误",function(){CloseWindow("save");});
                            }
                        } else {
                        	lc.unmask();
                        	mini.alert(xhr.responseText,"错误",function(){CloseWindow("save");});
                        }
                    }
                };
                
                // 开始上传
                xhr.open("POST", "${ctx}/agent/addAgent.action", true);
                xhr.send(fd);
            }   
        }
        
        //////////////////validation
        function idNumberValidation(e)
        {
        	if (e.isValid) {
        		if(e.value != '')
        		{
        		    var reg = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;  
                    if(reg.test(e.value) == false)
                	{
                    	e.errorText = "身份证格式不正确";
                        e.isValid = false;
                	}
        		}
            }
        }
        
        function mobileValidation(e)
        {
        	if (e.isValid) {
        		var reg = /^1[3|4|5|7|8|9]\d{9}$/; 
                if(reg.test(e.value) == false)
                {
                    e.errorText = "手机号码格式不正确";
                    e.isValid = false;
                }
            }
        }
        
        function passwordValidation(e)
        {
        	if (e.isValid) {
        		var reg = /^\w{6,12}$/
    			if(reg.test(e.value) == false)
                {
                    e.errorText = "密码需要6-12位的数字字母";
                    e.isValid = false;
                }
            }
        }
         
    </script>
	
</body>
<!-- END BODY -->
</html>
