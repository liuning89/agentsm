/**
 * @author Yang'ushan
 * @created 2015年7月24日 下午2:35:24
 */
lc.ns("lf.priFunction.update");
lf.priFunction.update = {
		/**
		 * 初始化
		 */
		init : function() {
			mini.parse();
			this.form = new mini.Form("priFunctionUpdateForm");
		},
		/**
		 * 提交
		 */
		onOk : function() {
			var data = this.form.getData();
			if (data.appName == "" || data.appName == "请选择") {
				mini.alert("请选择应用！");
				return ;
			}
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
				url : "priFunction/update.do",
				dataType: "json",
				data : this.form.getData(),
				success : function(result) {
					if (result.status == "1") {
						mini.alert("修改成功");
						lf.priFunction.update.CloseWindow("save");
					}
				}
			});
		},
		/**
		 * 取消
		 */
		onCancel : function() {
			lf.priFunction.update.CloseWindow("cancel");
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
	lf.priFunction.update.init();
});