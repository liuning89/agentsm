/**
 * @author guanshuli
 * @created 2015年7月24日 下午2:35:24
 */
lc.ns("lf.commissionRule.add");
lf.commissionRule.add = {
		/**
		 * 初始化
		 */
		init : function() {
			mini.parse();
			this.form = new mini.Form("addForm");
		},
		/**
		 * 提交
		 */
		onOk : function() {
			var data = this.form.getData();
			if (data.role == "") {
				mini.alert("分佣角色不能为空！");
				return ;
			}
			if (data.distpercent == "") {
				mini.alert("分配比例不能为空！");
				return ;
			}
			$.ajax({
				method : "post", 
				url : "commissionRule/add.do",
				dataType: "json",
				data : this.form.getData(),
				success : function(result) {
					if (result.status == "1") {
						mini.alert("添加成功");
						lf.commissionRule.add.CloseWindow("save");
					}
				}
			});
		},
		/**
		 * 取消
		 */
		onCancel : function() {
			lf.commissionRule.add.CloseWindow("cancel");
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
	lf.commissionRule.add.init();
});