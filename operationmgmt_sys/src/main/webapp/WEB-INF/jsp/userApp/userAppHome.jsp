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
        <fieldset id="fd1" style="width:98%">
            <legend><span>（发现页面第一张banner）</span></legend>
                <div class="fieldset-body">
                    <fieldset id="fd1" style="width:98%">
                        <div class="fieldset-body">
                            <div>   
                                <label>链接地址：${topbanner.url}</label>
                            </div>
                            <div>
                                <label>修改链接地址</label>
                                <label><input id="url" name="url" class="mini-textbox"  /></label>
                                <label><a class="mini-button" onclick="onOkUrl" style="width: 60px; margin-right: 20px;">确认</a></label>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset id="fd1" style="width:98%">
                        <div class="fieldset-body">
                            <div style="width: 400px;">
                                <img src="${topbanner.imgPath}" id="imgs" width="400px" />
                            </div>
                            <div>
                                <input style="width:190px" name="imgFile" id="imgFile" type="file" onchange="CheckFile(this)"></input>
                                <a class="mini-button" onclick="onOk" style="width: 60px; margin-right: 20px;">确定</a>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </fieldset>
    </div>
    
    <script type="text/javascript">
        mini.parse();
        var imgcheck = 0;
        var uFile;
        
        function CheckFile(obj) {
            var fileContentType = obj.value.match(/[^\s]+\.(jpg|jpeg|gif|png|bmp|JPG|GIF|PNG|BMP|JPEG)/i);
            if (null==fileContentType) {
                mini.alert("请选择上传图片!","提示");
                uFile = null;
                obj.value = "";
            }
            if(imgcheck == 0)
            {
            	$("#imgs").on("load",function(e){
                    var img = document.getElementsByTagName('img')[0]
                    var w = img.naturalWidth;
                    var h = img.naturalHeight;
                    if(w > 640 || h > 910)
                    {
                        mini.alert("图片超出规格【640*910】");
                        $("#imgFile").val("");
                        uFile = null;
                    }
                });
            	imgcheck = 1;
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
            reader.onload = function(e){
                 $("#imgs").attr("src",e.target.result);
            };
            reader.readAsDataURL(uFile);
        };
        
        function onOk(e) {
            SaveData();
        }
        
        function onOkUrl(e)
        {
        	var url=mini.get("url").getValue();
        	if(!url)
        	{
        		mini.alert("路径补允许为空！");
        		return;
        	}
        	mini.confirm("确认修改路径[修改会影响到APP]？","确定?",
                    function(action)
                    {
                        if (action == "ok") {
                            lc.mask("正在修改链接,请稍后...");
                            $.ajax({
                                url : "${ctx}/userApp/addAndEditUserAppTopUrl.action",
                                type : 'post',
                                data : {
                                    url : url
                                },
                                cache : false,
                                success : function(text) {
                                    lc.mask("修改成功!");
                                    document.location.href="${ctx}/userApp/gotoUserAppTopImgPage.action";
                                }
                            });
                        }
                    }
                );
        }
        
        function SaveData(){
            var fd = new FormData();
            
            if(this.uFile){
            	fd.append("imgFile",this.uFile);
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
                                document.location.href="${ctx}/userApp/gotoUserAppTopImgPage.action";
                            },3000);
                        } else {
                        }
                    }
                };
                
                // 开始上传
                xhr.open("POST", "${ctx}/userApp/addAndEditUserAppTopImg.action", true);
                xhr.send(fd);
            }   
        }
        
    </script>
	
</body>
<!-- END BODY -->
</html>
