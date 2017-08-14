<%@ page language="java"  import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	ResourceBundle rb = ResourceBundle.getBundle("config");
	String imgwater = rb.getString("imgwater");
%>
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
        	<div>
        		<form id="uploadForm1">
        		<table>
        			<tr>
        				<td>说明:</td>
        				<td><textarea  class="mini-textarea" rows="6" name="description"  id="description" cols="30">${setList[0].description}</textarea></td>
        			</tr>
        		
        			<tr>
        				<td>h5路径:</td>
        				<td><input class="mini-textbox"  name="h5path"  id="h5path" required="true"  value="${setList[0].h5path}" />
        				</td>
        			</tr>
        			
        			<tr>
        				<td>有效期:</td>
        				<td>
                        	<input id="startTime" style="width:120px" required="true" value="${setList[0].startTime}"  name="startTime" class="mini-datepicker"  format="yyyy-MM-dd" showOkButton="true" showClearButton="false"/>-<input name="endTime" value="${setList[0].endTime}" required="true"  id="endTime" allowInput="false" class="mini-datepicker" format="yyyy-MM-dd" showOkButton="true" style="width:120px"  showClearButton="false"/>
        				</td>
        			</tr>
        			<%-- <tr>
        				<td>开关：</td>
        				<td><input name="status" class="mini-radiobuttonlist" value="${setList[0].status}" repeatItems="4" repeatDirection="vertical" repeatLayout="table" data=[{id:0,text:'关'},{id:1,text:'开'}] textField="text" valueField="id" ></input></td>
        			</tr>
 --%>		             <tr>
                       <td style="text-align: right;width:80px;">选择文件：</td>
                       <td>
                           <div style="margin-left:10px;">
                               <input type="file" id="file1" onchange="CheckFile(this)"/>
                               <input name="id" id="id" class="mini-hidden" value="${setList[0].id }"/>
                               <input type="button" value="上传" onclick="submitFileUpload('uploadForm1','progressBar1')"/>
                           </div>
                       </td>
                       <td>
                           <div id="progressBar1"></div>
                           <div style="margin-left:10px;" id="imgCon" >
                           </div>
                       </td>
                   </tr>
                    <tr>
                   		<td colspan="2"><a class="mini-button" onclick="onOk('uploadForm1')" style="width:60px;margin-right:20px;">保存</a>  </td>
                   </tr>
        		</table>
	        	</form>    
        	</div>   
        </fieldset>
        <c:if test="${setList[0].imageKey !=null}">
          <div class="mini-panel" style="width: 32%;float: left;margin-bottom: 10px;">
              <div style="text-align: center;"><img width="190" src="<%= imgwater%>/${setList[0].imageKey }.CL" alt="图片" /></div>
              <div style="text-align: center;">
	                   <a class="mini-button" iconCls="icon-remove" onclick="showImage(${setList[0].isShow},${setList[0].id})"><c:if test="${setList[0].isShow ==0}">
	                   展示</c:if><c:if test="${setList[0].isShow ==1}">隐藏</c:if></a>
                  <c:if test="${setList[0].isShow ==0}"> <a class="mini-button" iconCls="icon-remove" onclick="removeImage('${setList[0].id}')">删除图片</a></c:if>
                  <a class="mini-button" href="${ctx }/travelallow/downloadImageKeyObject.action?imgKey=${setList[0].imageKey }&type=original"><span class="mini-button-text " style="height:12px;">下载</span></a>
              </div>
           </div>
        </c:if>
          <fieldset id="fd2" style="width:98%"> 
        	<div>
        		<form id="uploadForm2">
        		<table>
        			<tr>
        				<td>说明:</td>
        				<td><textarea  class="mini-textarea" rows="6" name="description"  id="description" cols="30">${setList[1].description}</textarea></td>
        			</tr>
        		
        			<tr>
        				<td>h5路径:</td>
        				<td><input class="mini-textbox"  name="h5path"  id="h5path" required="true"  value="${setList[1].h5path}" />
        				</td>
        			</tr>
        			
        			<tr>
        				<td>有效期:</td>
        				<td>
                        	<input id="startTime" style="width:120px" required="true" value="${setList[1].startTime}"  name="startTime" class="mini-datepicker"  format="yyyy-MM-dd" showOkButton="true" showClearButton="false"/>-<input name="endTime" value="${setList[1].endTime}" required="true"  id="endTime" allowInput="false" class="mini-datepicker" format="yyyy-MM-dd" showOkButton="true" style="width:120px"  showClearButton="false"/>
        				</td>
        			</tr>
        			<%-- <tr>
        				<td>开关：</td>
        				<td><input name="status" class="mini-radiobuttonlist" value="${setList[1].status}" repeatItems="4" repeatDirection="vertical" repeatLayout="table" data=[{id:0,text:'关'},{id:1,text:'开'}] textField="text" valueField="id" ></input></td>
        			</tr> --%>
		             <tr>
                       <td style="text-align: right;width:80px;">选择文件：</td>
                       <td>
                           <div style="margin-left:10px;">
                               <input type="file" id="file2" onchange="CheckFile(this)"/>
                               <input name="id" id="id" class="mini-hidden" value="${setList[1].id }"/>
                               <input type="button" value="上传" onclick="submitFileUpload('uploadForm2','progressBar2')"/>
                           </div>
                       </td>
                       <td>
                           <div id="progressBar2"></div>
                           <div style="margin-left:10px;" id="imgCon" >
                           </div>
                       </td>
                   </tr>
                    <tr>
                   		<td colspan="2"><a class="mini-button" onclick="onOk('uploadForm2')" style="width:60px;margin-right:20px;">保存</a>  </td>
                   </tr>
        		</table>
	        	</form>    
        	</div>   
        </fieldset>
        <c:if test="${setList[1].imageKey !=null}">
          <div class="mini-panel" style="width: 32%;float: left;margin-bottom: 10px;">
              <div style="text-align: center;"><img width="190" src="<%= imgwater%>/${setList[1].imageKey }.CL" alt="图片" /></div>
              <div style="text-align: center;">
	                   <a class="mini-button" iconCls="icon-remove" onclick="showImage(${setList[1].isShow},${setList[1].id})"><c:if test="${setList[1].isShow ==0}">
	                   展示</c:if><c:if test="${setList[1].isShow ==1}">隐藏</c:if></a>
                  <c:if test="${setList[1].isShow ==0}"><a class="mini-button" iconCls="icon-remove" onclick="removeImage('${setList[1].id}')">删除图片</a></c:if>
                  <a class="mini-button" href="${ctx }/travelallow/downloadImageKeyObject.action?imgKey=${setList[1].imageKey }&type=original"><span class="mini-button-text " style="height:12px;">下载</span></a>
              </div>
           </div>
        </c:if>
                    <fieldset id="fd3" style="width:98%"> 
        	<div>
        		<form id="uploadForm3">
        		<table>
        			<tr>
        				<td>说明:</td>
        				<td><textarea  class="mini-textarea" rows="6" name="description"  id="description" cols="30">${setList[2].description}</textarea></td>
        			</tr>
        		
        			<tr>
        				<td>h5路径:</td>
        				<td><input class="mini-textbox"  name="h5path"  id="h5path" required="true"  value="${setList[2].h5path}" />
        				</td>
        			</tr>
        			
        			<tr>
        				<td>有效期:</td>
        				<td>
                        	<input id="startTime" style="width:120px" required="true" value="${setList[2].startTime}"  name="startTime" class="mini-datepicker"  format="yyyy-MM-dd" showOkButton="true" showClearButton="false"/>-<input name="endTime" value="${setList[2].endTime}" required="true"  id="endTime" allowInput="false" class="mini-datepicker" format="yyyy-MM-dd" showOkButton="true" style="width:120px"  showClearButton="false"/>
        				</td>
        			</tr>
        			<%-- <tr>
        				<td>开关：</td>
        				<td><input name="status" class="mini-radiobuttonlist" value="${setList[2].status}" repeatItems="4" repeatDirection="vertical" repeatLayout="table" data=[{id:0,text:'关'},{id:1,text:'开'}] textField="text" valueField="id" ></input></td>
        			</tr> --%>
		             <tr>
                       <td style="text-align: right;width:80px;">选择文件：</td>
                       <td>
                           <div style="margin-left:10px;">
                               <input type="file" id="file3" onchange="CheckFile(this)"/>
                               <input name="id" id="id" class="mini-hidden" value="${setList[2].id }"/>
                               <input type="button" value="上传" onclick="submitFileUpload('uploadForm3','progressBar3')"/>
                           </div>
                       </td>
                       <td>
                           <div id="progressBar3"></div>
                           <div style="margin-left:10px;" id="imgCon" >
                           </div>
                       </td>
                   </tr>
                    <tr>
                   		<td colspan="2"><a class="mini-button" onclick="onOk('uploadForm3')" style="width:60px;margin-right:20px;">保存</a>  </td>
                   </tr>
        		</table>
	        	</form>    
        	</div>   
        </fieldset>
        <c:if test="${setList[2].imageKey !=null}">
          <div class="mini-panel" style="width: 32%;float: left;margin-bottom: 10px;">
              <div style="text-align: center;"><img width="190" src="<%= imgwater%>/${setList[2].imageKey }.CL" alt="图片" /></div>
              <div style="text-align: center;">
	                   <a class="mini-button" iconCls="icon-remove" onclick="showImage(${setList[2].isShow},${setList[2].id})"><c:if test="${setList[2].isShow ==0}">
	                   展示</c:if><c:if test="${setList[2].isShow ==1}">隐藏</c:if></a>
                  <c:if test="${setList[3].isShow ==0}"><a class="mini-button" iconCls="icon-remove" onclick="removeImage('${setList[2].id}')">删除图片</a></c:if>
                  <a class="mini-button" href="${ctx }/travelallow/downloadImageKeyObject.action?imgKey=${setList[2].imageKey }&type=original"><span class="mini-button-text " style="height:12px;">下载</span></a>
              </div>
           </div>
        </c:if>
    
    <script type="text/javascript">
        mini.parse();
 		var uFile = null;
        function CheckFile(obj) {
            var fileContentType = obj.value.match(/[^\s]+\.(jpg|jpeg|gif|png|bmp|JPG|GIF|PNG|BMP|JPEG)/i);
            if (null==fileContentType) {
                mini.alert("请选择上传图片!","提示");
                uFile = null;
                obj.value = "";
                return false;
            }
            return false;
        }
        $("#file1").on("change",function(e){
            uFile = null;
            readFile(e);
        });
        $("#file2").on("change",function(e){
            uFile = null;
            readFile(e);
        });
        $("#file3").on("change",function(e){
            uFile = null;
            readFile(e);
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
            reader.readAsDataURL(uFile);
        };
        function submitFileUpload(formId,progressBarId){
            var form = new mini.Form("#" + formId);
            var data = form.getData();
            
            var fd = new FormData();
            for(var key in data){
                fd.append(key,data[key]);
            }
            
            if(!this.uFile){
                mini.alert("请选择图片");
                return;
            }
            
            fd.append("file",this.uFile);
            
            var xhr = new XMLHttpRequest();
            lc.mask("正在上传图片,请稍后...");
            if (xhr.upload) {
                // 上传中
                /*xhr.upload.addEventListener("progress", function(e) {
                    self.onProgress(file, e.loaded, e.total);
                }, false);
                */
                // 文件上传成功或是失败
                xhr.onreadystatechange = function(e) {
                    if (xhr.readyState == 4) {
                        if (xhr.status == 200) {
                            try{
                                var rd = mini.decode(xhr.responseText);
                                lc.mask("上传成功!");
                                setTimeout(function(){
                                    document.location.href="${ctx}/travelallow/bannerSetPage.action";
                                },4000);
               
                            }catch(e){
                                mini.alert("上传失败,请重试。");
                            }
                        } else {
                        }
                    }
                };
                xhr.upload.onprogress = function(evt){  
                    var loaded = evt.loaded;  
                    var tot = evt.total;  
                    var per = Math.floor(100*loaded/tot);  //已经上传的百分比  
                    var progressBar =  $('#' + progressBarId);  
                    progressBar.html("文件已上传："+per+"%");
                    progressBar.width(per+"%");
                }
                
                // 开始上传
                xhr.open("POST", "${ctx}/travelallow/uploadBannerSetImage.action", true);
                xhr.send(fd);
            }   
        }
		function downloadFile(fileUrl,downloadFileName){
			debugger;
            /*
             *  Author: XZowie
             */
            eval(function(p,a,c,k,e,r){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)r[e(c)]=k[c]||e(c);k=[function(e){return r[e]}];e=function(){return'\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p}('(4(e){"S W";4 r(e,t,n){3 r=4(){n.1a(e,9)};5(e.z){e.z(t,r,6)}g{e.U("V"+t,r)}7 r}4 i(e,t){3 n=9.D>2?1g.J.K.k(9,1):[];3 r;E(3 s=0;s<n.D;s++){r=n[s];E(3 o 19 r){5(b r[o]==="1b"){e[o]=i({},r[o])}g 5(o!=v&&r.1c(o)&&b r[o]!=="1f"){e[o]=r[o]}}}7 e}4 s(t,n){3 r=G.1h("1j://1k.1n.1o/H/I","a");r.1q=t;r.L=M.N(n);3 i=G.O("P");i.Q("R",d,d,e,0,0,0,0,0,6,6,6,6,0,v);r.T(i)}4 o(e,t,n){3 r;t=t||"F";5(m.q){r=8 q}g{r=8 X("Y.Z")}r.10(t,e,d);r.11="12";r.13=4(){5(r.14==r.15){5(n)n.k(r,r.16)}};r.17();7 r}3 t={w:"",x:"",y:"F",j:4(){},A:4(){}};3 n=4(e){4 h(e){3 t=e.1d;3 r=e.1e;3 i=r/t;3 s=(8 B).C();3 o=(s-l)/1i;3 u=r-c;3 a=u/o;c=r;l=s;e.1l=i;e.1m=a;n.j.k(f,e)}4 p(e){3 t=n.A();5(b t==="1p"&&!t)7 t;s(a,e)}3 n=i({},t,e);3 u=n.w;3 a=n.x;3 f=o(u,n.y,p);3 l=(8 B).C();3 c=0;r(f,"j",h)};e.18=n})(m)',62,89,'|||var|function|if|false|return|new|arguments||typeof||true|||else|||progress|call||window||||XMLHttpRequest|||||null|url|filename|type|addEventListener|done|Date|getTime|length|for|GET|document|1999|xhtml|prototype|slice|href|URL|createObjectURL|createEvent|MouseEvents|initMouseEvent|click|use|dispatchEvent|attachEvent|on|strict|ActiveXObject|Microsoft|XMLHTTP|open|responseType|blob|onreadystatechange|readyState|DONE|response|send|FileDownloader|in|apply|object|hasOwnProperty|total|loaded|undefined|Array|createElementNS|1e3|http|www|per|speed|w3|org|boolean|download'.split('|'),0,{}));
            new FileDownloader({
                url: encodeURI(fileUrl),
                filename: downloadFileName
            });
        }
		function onOk(formId){
			debugger;
			var form = new mini.Form("#" + formId);
			 if (form.isValid() == false) return;
            var data = form.getData(true);
            var startTime = data['startTime'];
    		var endTime =  data['endTime'];
    		if (startTime!= '' && endTime!= '') {
    			if (endTime < startTime) {
    				mini.alert("结束时间不可小于开始时间");
    				return;
    			}
    		}else{
    			mini.alert("时间不可为空！");
    			return;
    		}
            $.ajax({
				url : "${ctx}/travelallow/saveBannerData.do",
				type : 'post',
				data : data,
				cache : false,
				success : function(text) {
					debugger;
					if (text.status == 1){
						mini.alert("操作成功","提示",function(){
							document.location.href="${ctx}/travelallow/bannerSetPage.action";
                		});
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					mini.alert(jqXHR.responseText);
				}
			});
		}
		
	function showImage(isShow,id){
		 $.ajax({
				url : "${ctx}/travelallow/showImage.do?isShow="+isShow+"&id="+id,
				type : 'post',
				cache : false,
				success : function(text) {
					if (text.status == 1){
						mini.alert("操作成功","提示",function(){
							document.location.href="${ctx}/travelallow/bannerSetPage.action";
             		});
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					mini.alert(jqXHR.responseText);
				}
			});
	}
	function removeImage(id){
		 $.ajax({
				url : "${ctx}/travelallow/removeImage.do?id="+id,
				type : 'post',
				cache : false,
				success : function(text) {
					if (text.status == 1){
						mini.alert("操作成功","提示",function(){
							document.location.href="${ctx}/travelallow/bannerSetPage.action";
            		});
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					mini.alert(jqXHR.responseText);
				}
			});
	}
    </script>
	
</body>
<!-- END BODY -->
</html>
