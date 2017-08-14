/**
 * @author Yang'ushan
 * @created 2015年7月28日 上午11:28:24
 */
lc.ns("lf.menu.update");
lf.menu.update = {
		/**
		 * 初始化
		 */
		init : function() {
			mini.parse();
			this.form = new mini.Form("menuUpdateForm");
			this.isLeaf = mini.get("isLeaf");
			if (this.isLeaf.getValue() != "1") { // 不是子节点
				mini.get("url").disable();
			}
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
			if (data.isLeaf == 1) {
				if (data.url == "") {
					mini.alert("子节点URL不能为空！");
					return ;
				}
			}
			$.ajax({
				method : "post", 
				url : "menu/update.do",
				dataType: "json",
				data : this.form.getData(),
				success : function(result) {
					if (result.status == "1") {
						mini.alert("修改成功");
						lf.menu.update.CloseWindow("save");
					}
				}
			});
		},
		/**
		 * 取消
		 */
		onCancel : function() {
			lf.menu.update.CloseWindow("cancel");
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
	lf.menu.update.init();
});