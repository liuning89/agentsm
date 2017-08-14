<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>后台页面图片管理</title>
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
    
    <div>
    <!-- 开始循环 -->
        <fieldset id="fd1" style="width:98%"> 
            <span>活动名称：</span><input name="activityName1" id="activityName1" class="mini-textbox" value="${laaList[0].activityName}"  />
            
				 <c:choose>
                      	<c:when test="${laaList[0] == null}">
                      		<a class="mini-button" onclick="onOut(-1)" style="width: 80px; margin-right: 20px;">活动下架</a>
                      	</c:when>
                      	<c:otherwise>
                      		<a class="mini-button" onclick="onOut(${laaList[0].id})" style="width: 80px; margin-right: 20px;">活动下架</a>
                      	</c:otherwise>
                  </c:choose>            




                <div class="fieldset-body">
                    <fieldset id="fd1" style="width:98%">
                        <div class="fieldset-body">
                         <div>
                              <label>活动结束时间：<input id="date1" class="mini-datepicker" style="width:200px;"  format="yyyy-MM-dd H:mm:ss" value="${laaList[0].endTime}" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/></label>
                            </div>
                           <!--  <fieldset id="fd1" style="width:98%"> -->
                        <div class="fieldset-body">
                            <div style="width: 400px;">
                                <img src="${laaList[0].activityImgUrl}" id="imgs1" width="400px" />
                            </div>
                            
                            
                        </div>
                  <!--   </fieldset> -->
                            <div>   
                                <label>链接地址：${laaList[0].activityLinkUrl} </label>
                            </div>
                           
                            <div>
                                <label>修改链接地址</label>
                                <label><input id="url1" name="url1" class="mini-textbox"  /></label>
                                
                                  <c:choose>
	                                <c:when test="${laaList[0] == null}">
                               			 <label><a class="mini-button" onclick="onOkUrl(-1,1)" style="width: 60px; margin-right: 20px;">确认</a></label>
                             	   </c:when>
	                                <c:otherwise>
	                                	<label><a class="mini-button" onclick="onOkUrl(${laaList[0].id},1)" style="width: 60px; margin-right: 20px;">确认</a></label>
	                                </c:otherwise>
                                </c:choose>
                            </div>
                            <div>
                             <c:choose>
                                	<c:when test="${laaList[0] == null}">
                                		<input style="width:190px" name="imgFile1" id="imgFile1" type="file" onchange="CheckFile1(this,-1)"></input>
                                		<a class="mini-button" onclick="onOk1(-1)" style="width: 60px; margin-right: 20px;">上传</a>(建议图片大小:1080*399)
                                	</c:when>
                                	<c:otherwise>
                                		<input style="width:190px" name="imgFile1" id="imgFile1" type="file" onchange="CheckFile1(this,${laaList[0].id})"></input>
                                		<a class="mini-button" onclick="onOk1(${laaList[0].id})" style="width: 60px; margin-right: 20px;">上传</a>(建议图片大小:1080*399)
                                	</c:otherwise>
                            </c:choose>
                            </div>
                            
                        </div>
                    </fieldset>
                    
                </div>
            </fieldset>
            
            <!-- 活动二 -->
            
             <fieldset id="fd2" style="width:98%">
            <span>活动名称：</span><input name="activityName2" id="activityName2" class="mini-textbox" value="${laaList[1].activityName}"  />
            
             <c:choose>
                      	<c:when test="${laaList[1] == null}">
                      		<a class="mini-button" onclick="onOut(-1)" style="width: 80px; margin-right: 20px;">活动下架</a>
                      	</c:when>
                      	<c:otherwise>
                      		<a class="mini-button" onclick="onOut(${laaList[1].id})" style="width: 80px; margin-right: 20px;">活动下架</a>
                      	</c:otherwise>
                  </c:choose>      
            
            
                <div class="fieldset-body">
                    <fieldset id="fd2" style="width:98%">
                        <div class="fieldset-body">
                         <div>
                                <label>活动结束时间：<input id="date2" class="mini-datepicker" style="width:200px;"  format="yyyy-MM-dd H:mm:ss" value="${laaList[1].endTime}" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/></label>
                            </div>
                           <!--  <fieldset id="fd2" style="width:98%"> -->
                        <div class="fieldset-body">
                            <div style="width: 400px;">
                                <img src="${laaList[1].activityImgUrl}" id="imgs2" width="400px" />
                            </div>
                            
                        </div>
                  <!--   </fieldset> -->
                            <div>   
                                <label>链接地址：${laaList[1].activityLinkUrl}</label>
                            </div>
                            
                            <div>
                                <label>修改链接地址</label>
                                <label><input id="url2" name="url2" class="mini-textbox"  /></label>
                                <c:choose>
	                                <c:when test="${laaList[1] == null}">
	                                	<label><a class="mini-button" onclick="onOkUrl(-1,2)" style="width: 60px; margin-right: 20px;">确认</a></label>
	                                </c:when>
	                                <c:otherwise>
	                              	  <label><a class="mini-button" onclick="onOkUrl(${laaList[1].id},2)" style="width: 60px; margin-right: 20px;">确认</a></label>
	                                </c:otherwise>
                                </c:choose>
                            </div>
                            <div>
                             <c:choose>
                                	<c:when test="${laaList[1] == null}">
                                		<input style="width:190px" name="imgFile2" id="imgFile2" type="file" onchange="CheckFile2(this,-1)"></input>
                                		<a class="mini-button" onclick="onOk2(-1)" style="width: 60px; margin-right: 20px;">上传</a>(建议图片大小:1080*399)
                                	</c:when>
                                	<c:otherwise>
                                		<input style="width:190px" name="imgFile2" id="imgFile2" type="file" onchange="CheckFile2(this,${laaList[1].id})"></input>
                                		<a class="mini-button" onclick="onOk2(${laaList[1].id})" style="width: 60px; margin-right: 20px;">上传</a>(建议图片大小:1080*399)
                                	</c:otherwise>
                             </c:choose>
                            </div>
                        </div>
                    </fieldset>
                    
                </div>
            </fieldset>
            
            <!-- 活动三 -->
             <fieldset id="fd3" style="width:98%">
            <span>活动名称：</span><input name="activityName3" id="activityName3" class="mini-textbox" value="${laaList[2].activityName}"  />
            
             <c:choose>
                      	<c:when test="${laaList[2] == null}">
                      		<a class="mini-button" onclick="onOut(-1)" style="width: 80px; margin-right: 20px;">活动下架</a>
                      	</c:when>
                      	<c:otherwise>
                      		<a class="mini-button" onclick="onOut(${laaList[2].id})" style="width: 80px; margin-right: 20px;">活动下架</a>
                      	</c:otherwise>
                  </c:choose>      
            
                <div class="fieldset-body">
                    <fieldset id="fd3" style="width:98%">
                        <div class="fieldset-body">
                      		<div>
                                <label>活动结束时间：<input id="date3" class="mini-datepicker" style="width:200px;"  format="yyyy-MM-dd H:mm:ss" value="${laaList[2].endTime}" timeFormat="H:mm:ss" showTime="true" showOkButton="true" showClearButton="false"/></label>
                            </div>
                           <!--  <fieldset id="fd3" style="width:98%"> -->
                        <div class="fieldset-body">
                            <div style="width: 400px;">
                                <img src="${laaList[2].activityImgUrl}" id="imgs3" width="400px" />
                            </div>
                            
                        </div>
                  <!--   </fieldset> -->
                            <div>   
                                <label>链接地址：${laaList[2].activityLinkUrl}</label>
                            </div>
                             
                            <div>
                                <label>修改链接地址</label>
                                <label><input id="url3" name="url3" class="mini-textbox"  /></label>
                                <c:choose>
                                	<c:when test="${laaList[2] == null}">
                              		  <label><a class="mini-button" onclick="onOkUrl(-1,3)" style="width: 60px; margin-right: 20px;">确认</a></label>
                              		</c:when>
                              		<c:otherwise>
                              			<label><a class="mini-button" onclick="onOkUrl(${laaList[2].id},3)" style="width: 60px; margin-right: 20px;">确认</a></label>
                              		</c:otherwise>
                                </c:choose>
                            </div>
                            <div>
                             <c:choose>
                                	<c:when test="${laaList[2] == null}">
	                               		<input style="width:190px" name="imgFile3" id="imgFile3" type="file" onchange="CheckFile3(this,-1)"></input>
	                               		<a class="mini-button" onclick="onOk3(-1)" style="width: 60px; margin-right: 20px;">上传</a>(建议图片大小:1080*399)
	                                </c:when>
	                                <c:otherwise>
	                                	<input style="width:190px" name="imgFile3" id="imgFile3" type="file" onchange="CheckFile3(this,${laaList[2].id})"></input>
	                                	<a class="mini-button" onclick="onOk3(${laaList[2].id})" style="width: 60px; margin-right: 20px;">上传</a>(建议图片大小:1080*399)
	                                </c:otherwise>
                                </c:choose>
                                
                            </div>
                        </div>
                    </fieldset>
                    
                </div>
            </fieldset>
    </div>
    
    <script type="text/javascript">
        mini.parse();
        var imgcheck = 0;
        var uFile1;
        var uFile2;
        var uFile3;
        function CheckFile1(obj,id) {
        	
        	if(id == -1){
        		mini.alert("活动名称,链接地址,活动结束时间不能为空!");
        		$("#imgFile1").val("");
        		return false;
        	}
            //var fileContentType = obj.value.match(/[^\s]+\.(jpg|jpeg|gif|png|bmp|JPG|GIF|PNG|BMP|JPEG)/i);
            var fileContentType = obj.value.match(/[^\s]+\.(jpg|png)/i);
            if (null==fileContentType) {
                mini.alert("请选择上传图片!","提示");
                uFile1 = null;
                obj.value = "";
                return false;
            }
            if(imgcheck == 0)
            {
            	$("#imgs1").on("load",function(e){
                    var img = document.getElementsByTagName('img')[0]
                    var w = img.naturalWidth;
                    var h = img.naturalHeight;
                   /*  if(w > 640 || h > 910)
                    {
                        mini.alert("图片超出规格【640*910】");
                        $("#imgFile").val("");      
                        uFile = null;
                        return false;
                    } */
                });
            	imgcheck = 1;
            }
        }
        //2
        function CheckFile2(obj,id) {
        	
        	if(id == -1){
        		mini.alert("活动名称,链接地址,活动结束时间不能为空!");
        		$("#imgFile2").val("");
        		return false;
        	}
            var fileContentType = obj.value.match(/[^\s]+\.(jpg|png)/i);
            if (null==fileContentType) {
                mini.alert("请选择上传图片!","提示");
                uFile2 = null;
                obj.value = "";
                return false;
            }
            if(imgcheck == 0)
            {
            	$("#imgs2").on("load",function(e){
                    var img = document.getElementsByTagName('img')[1]
                    var w = img.naturalWidth;
                    var h = img.naturalHeight;
                   /*  if(w > 640 || h > 910)
                    {
                        mini.alert("图片超出规格【640*910】");
                        $("#imgFile").val("");      
                        uFile = null;
                        return false;
                    } */
                });
            	imgcheck = 1;
            }
        }
        //3
        function CheckFile3(obj,id) {
        	
        	if(id == -1){
        		mini.alert("活动名称,链接地址,活动结束时间不能为空!");
        		$("#imgFile3").val("");
        		return false;
        	}
          //  var fileContentType = obj.value.match(/[^\s]+\.(jpg|jpeg|gif|png|bmp|JPG|GIF|PNG|BMP|JPEG)/i);
            var fileContentType = obj.value.match(/[^\s]+\.(jpg|png)/i);
            if (null==fileContentType) {
                mini.alert("请选择上传图片!","提示");
                uFile3 = null;
                obj.value = "";
                return false;
            }
            if(imgcheck == 0)
            {
            	$("#imgs3").on("load",function(e){
                    var img = document.getElementsByTagName('img')[2]
                    var w = img.naturalWidth;
                    var h = img.naturalHeight;
                   /*  if(w > 640 || h > 910)
                    {
                        mini.alert("图片超出规格【640*910】");
                        $("#imgFile").val("");      
                        uFile = null;
                        return false;
                    } */
                });
            	imgcheck = 1;
            }
        }
        $("#imgFile1").on("change",function(e){
            uFile = null;
            if(window.navigator.userAgent.indexOf("MSIE")>=1){
                obj.setAttribute("src",e.value);
            }else{
                readFile1(e);
            }
        });
        $("#imgFile2").on("change",function(e){
            uFile = null;
            if(window.navigator.userAgent.indexOf("MSIE")>=1){
                obj.setAttribute("src",e.value);
            }else{
                readFile2(e);
            }
        });
        $("#imgFile3").on("change",function(e){
            uFile = null;
            if(window.navigator.userAgent.indexOf("MSIE")>=1){
                obj.setAttribute("src",e.value);
            }else{
                readFile3(e);
            }
        });
        
        function readFile1(e){
            if(e.target.files){
                uFile1 = e.target.files[0];
            }else if(e.dataTransfer.files){
                uFile1 = e.dataTransfer.files[0];
            }
            var reader = new FileReader();
            reader.onload = function(e){
                /*  $("#imgs1").attr("src",e.target.result); */
            };
            reader.readAsDataURL(uFile1);
        };
        
        function readFile2(e){
            if(e.target.files){
                uFile2 = e.target.files[0];
            }else if(e.dataTransfer.files){
                uFile2 = e.dataTransfer.files[0];
            }
            var reader = new FileReader();
            reader.onload = function(e){
                 /* $("#imgs2").attr("src",e.target.result); */
            };
            reader.readAsDataURL(uFile2);
        };
        
        function readFile3(e){
            if(e.target.files){
                uFile3 = e.target.files[0];
            }else if(e.dataTransfer.files){
                uFile3 = e.dataTransfer.files[0];
            }
            var reader = new FileReader();
            reader.onload = function(e){
                 /* $("#imgs3").attr("src",e.target.result); */
            };
            reader.readAsDataURL(uFile3);
        };
        
        function onOk1(id) {
            SaveData1(id);
        }
        function onOk2(id) {
            SaveData2(id);
        }
        function onOk3(id) {
            SaveData3(id);
        }
        //活动下架
        function onOut(obj){
        	if(obj == -1){
        		mini.alert("当前无活动!");
        		return false;
        	}
        	
        	mini.confirm("确认下架当前活动吗？","确定?",
                    function(action)
                    {
                        if (action == "ok") {
                            lc.mask("正在修改链接,请稍后...");
                            $.ajax({
                                url : "${ctx}/market/updateOut.action",
                                type : 'post',
                                data : {
                                	id : obj
                                },
                                cache : false,
                                success : function(text) {
                                    lc.mask("修改成功!");
                                    document.location.href="${ctx}/market/showMarketActivity.action";
                                }
                            });
                        }
                    }
                );
        }
        function onOkUrl(id,index)
        {
        	var url=mini.get("url"+index).getValue();
            var date = mini.get("date"+index).getFormValue();
            var activityName = mini.get("activityName"+index).getValue();
        	if(!url)
        	{
        		mini.alert("链接地址不允许为空！");
        		return;
        	}
        	if(!date){
        		mini.alert("活动结束时间不能为空!");
        		return;
        	}
        	if(!activityName){
        		mini.alert("活动名称不能为空!");
        		return;
        	}
        	mini.confirm("确认修改链接地址","确定?",
                    function(action)
                    {
                        if (action == "ok") {
                            lc.mask("正在修改链接,请稍后...");
                            $.ajax({
                                url : "${ctx}/market/addAndEditUserAppTopUrl.action",
                                type : 'post',
                                data : {
                                	id : id,
                                    url : url,
                                    date : date,
                                    activityName : activityName
                                },
                                cache : false,
                                success : function(text) {
                                    lc.mask("修改成功!");
                                    document.location.href="${ctx}/market/showMarketActivity.action";
                                }
                            });
                        }
                    }
                );
        }
        
        function SaveData1(id){
            var fd = new FormData();
            
            if(this.uFile1){
            	fd.append("imgFile",this.uFile1);
            	fd.append("id",id);
            }
            else
            {
            	mini.alert("请选择需要上传的图片");
            	return;
            }
            
            var xhr = new XMLHttpRequest();
            lc.mask("正在上传图片,请稍后...");
            if (xhr.upload) {
                // 文件上传成功或是失败
                xhr.onreadystatechange = function(e) {
                    if (xhr.readyState == 4) {
                        if (xhr.status == 200) {
                            try{
                                var rd = mini.decode(xhr.responseText);
                                lc.mask("上传成功!");
                            }catch(e){
                            	lc.mask("上传失败,请重试。");
                            }
                            setTimeout(function(){
                                document.location.href="${ctx}/market/showMarketActivity.action";
                            },3000);
                        } else {
                        }
                    }
                };
                
                // 开始上传
                xhr.open("POST", "${ctx}/market/addAndEditUserAppTopImg.action", true);
                xhr.send(fd);
            }
        }
        function SaveData2(id){
            var fd = new FormData();
            
            if(this.uFile2){
            	fd.append("imgFile",this.uFile2);
            	fd.append("id",id);
            }
            else
            {
            	mini.alert("请选择需要上传的图片");
            	return;
            }
            
            var xhr = new XMLHttpRequest();
            lc.mask("正在上传图片,请稍后...");
            if (xhr.upload) {
                // 文件上传成功或是失败
                xhr.onreadystatechange = function(e) {
                    if (xhr.readyState == 4) {
                        if (xhr.status == 200) {
                            try{
                                var rd = mini.decode(xhr.responseText);
                                lc.mask("上传成功!");
                            }catch(e){
                            	lc.mask("上传失败,请重试。");
                            }
                            setTimeout(function(){
                                document.location.href="${ctx}/market/showMarketActivity.action";
                            },3000);
                        } else {
                        }
                    }
                };
                
                // 开始上传
                xhr.open("POST", "${ctx}/market/addAndEditUserAppTopImg.action", true);
                xhr.send(fd);
            }
        }
        function SaveData3(id){
            var fd = new FormData();
            
            if(this.uFile3){
            	fd.append("imgFile",this.uFile3);
            	fd.append("id",id);
            }
            else
            {
            	mini.alert("请选择需要上传的图片");
            	return;
            }
            
            var xhr = new XMLHttpRequest();
            lc.mask("正在上传图片,请稍后...");
            if (xhr.upload) {
                // 文件上传成功或是失败
                xhr.onreadystatechange = function(e) {
                    if (xhr.readyState == 4) {
                        if (xhr.status == 200) {
                            try{
                                var rd = mini.decode(xhr.responseText);
                                lc.mask("上传成功!");
                            }catch(e){
                            	lc.mask("上传失败,请重试。");
                            }
                            setTimeout(function(){
                                document.location.href="${ctx}/market/showMarketActivity.action";
                            },3000);
                        } else {
                        }
                    }
                };
                
                // 开始上传
                xhr.open("POST", "${ctx}/market/addAndEditUserAppTopImg.action", true);
                xhr.send(fd);
            }
        }
        
    </script>
	
</body>
<!-- END BODY -->
</html>
