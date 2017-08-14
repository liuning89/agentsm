/**
 * @author Yang'ushan
 * @created 2015年8月4日 下午2:06:24
 */
lc.ns("lf.areaOrg.city.add");
lf.areaOrg.city.add = {
		/**
		 * 初始化
		 */
		init : function() {
			mini.parse();
			this.form = new mini.Form("areaOrgAddForm");
		},
		/**
		 * 提交
		 */
		onOk : function() {
			var data = this.form.getData();
			if (data.cityId == "") {
				mini.alert("请选择城市！");
				return ;
			}
			if (data.name == "") {
				mini.alert("名称不能为空！");
				return ;
			}
			$.ajax({
				method : "post", 
				url : "areaOrg/add.do",
				dataType: "json",
				data : this.form.getData(),
				success : function(result) {
					if (result.status == "1") {
						mini.alert("添加成功","提示",function(){
							lf.areaOrg.city.add.CloseWindow("save");
						});
					}
				}
			});
		},
		/**
		 * 取消
		 */
		onCancel : function() {
			lf.areaOrg.city.add.CloseWindow("cancel");
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
	lf.areaOrg.city.add.init();
});