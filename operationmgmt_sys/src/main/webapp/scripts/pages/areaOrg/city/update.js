/**
 * @author Yang'ushan
 * @created 2015年8月4日 下午2:46:24
 */
lc.ns("lf.areaOrg.city.update");
lf.areaOrg.city.update = {
		/**
		 * 初始化
		 */
		init : function() {
			mini.parse();
			this.form = new mini.Form("areaOrgUpdateForm");
		},
		/**
		 * 提交
		 */
		onOk : function() {
			var data = this.form.getData();
			if (data.name == "") {
				mini.alert("名称不能为空！");
				return ;
			}
			if (data.id == "") {
				mini.alert("数据有问题！");
				return ;
			}
			$.ajax({
				method : "post", 
				url : "areaOrg/update.do",
				dataType: "json",
				data : this.form.getData(),
				success : function(result) {
					if (result.status == "1") {
						mini.alert("修改成功","提示",function(){
							lf.areaOrg.city.update.CloseWindow("save");
						});
					}
				}
			});
		},
		/**
		 * 取消
		 */
		onCancel : function() {
			lf.areaOrg.city.update.CloseWindow("cancel");
		},
		/**
		 * 关闭窗口
		 */
		CloseWindow : function(action) {
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}
};

$(function(){
	lf.areaOrg.city.update.init();
});