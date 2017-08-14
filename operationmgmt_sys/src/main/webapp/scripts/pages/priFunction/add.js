/**
 * @author Yang'ushan
 * @created 2015年7月24日 下午2:35:24
 */
lc.ns("lf.priFunction.add");
lf.priFunction.add = {
		/**
		 * 初始化
		 */
		init : function() {
			mini.parse();
			this.form = new mini.Form("priFunctionAddForm");
		},
		/**
		 * 提交
		 */
		onOk : function() {
			var data = this.form.getData();
			if (data.appName == "" || data.appName == "0") {
				mini.alert("请选择应用！");
				return ;
			}
			if (data.name == "") {
				mini.alert("名称不能为空！");
				return ;
			}
			$.ajax({
				method : "post", 
				url : "priFunction/add.do",
				dataType: "json",
				data : this.form.getData(),
				success : function(result) {
					if (result.status == "1") {
						mini.alert("添加成功");
						lf.priFunction.add.CloseWindow("save");
					}
				}
			});
		},
		/**
		 * 取消
		 */
		onCancel : function() {
			lf.priFunction.add.CloseWindow("cancel");
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
	lf.priFunction.add.init();
});