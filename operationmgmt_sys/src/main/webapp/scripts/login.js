/**
 * @author carvink
 * @include "lf/lf.common.js"
 */
lc.ns("lf.agentsm.login");
lf.agentsm.login={
	init:function(){
		mini.parse();
		var loginWindow = mini.get("loginWindow");
        loginWindow.show();
	},
	login:function(){
		var form = new mini.Form("#loginWindow");
            form.validate();
            if (form.isValid() == false) return;
            var username=mini.get('username').getValue();
            var password=mini.get('pwd').getValue();
            //mini.loading("登陆中，请稍后！", "登录成功");
            lc.ajax({
                url: lc.rootPath("login.action"),
				type: 'post',
                data: { username: username,password:password},
                cache: false,
                success: function (data) {
               		if(data.status == 1){
               			lc.fwd(lc.rootPath("index.jsp"));
               		}else{
               			mini.alert(data.message);
               		}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    mini.alert(jqXHR.responseText);
                }
            },"正在登录，请稍后...");	
	},
	reset:function(){
		new mini.Form("#loginWindow").clear();
	}
};

$(function(){
	lf.agentsm.login.init();
});
