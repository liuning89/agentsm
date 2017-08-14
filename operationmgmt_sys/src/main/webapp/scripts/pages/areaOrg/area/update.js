/**
 * @author Yang'ushan
 * @created 2015年8月4日 下午2:46:24
 */
lc.ns("lf.areaOrg.area.update");
lf.areaOrg.area.update = {
		/**
		 * 初始化
		 */
		init : function() {
			mini.parse();
			this.form = new mini.Form("areaOrgUpdateForm");
			/*var type = mini.get("type");
			if (type.value == 1) { // 非加盟商，把加盟商选项设置为不可设置
				/!*mini.get("typeCheckBox").disable();
				$("#typeDescription").attr("style","color:gray;margin-left:-8px;");*!/
			} else { // 加盟商，设置打钩
				mini.get("typeCheckBox").setValue(true);
			}*/
			this.loadFranchisee();
		},
		/**
		 * 读取加盟商
		 */
		loadFranchisee : function() {
			var franchiseeId = mini.get("franchiseeId");
			var parentId = mini.get("parentId");
			franchiseeId.load(lc.rootPath(lc.strFormat("areaOrg/franchisee/getSimpleList/byCityId.action?cityId=" + parentId.getValue())));
			franchiseeId.setValue(mini.get("oldFranchiseeId").getValue());
		},
		/**
		 * 提交
		 */
		onOk : function() {
			var data = this.form.getData();
			if (data.franchiseeId == "" || data.franchiseeId == 0) {
				mini.alert("请选择加盟商！");
				return ;
			}
			if (data.name == "") {
				mini.alert("名称不能为空！");
				return ;
			}
			if (data.parentId == "" || data.parentId == 0) {
				mini.alert("请选择城市架构！");
				return ;
			}
			if (data.id == "") {
				mini.alert("数据有问题！");
				return ;
			}
			/*var typeCheckBox = data.typeCheckBox;
			if (typeCheckBox == "true") {
				data.type = 2;
			} else {
				data.type = 1;
			}*/
			$.ajax({
				method : "post", 
				url : "areaOrg/update.do",
				dataType: "json",
				data : data,
				success : function(result) {
					if (result.status == "1") {
						mini.alert("修改成功","提示",function(){
							lf.areaOrg.area.update.CloseWindow("save");
						});
					}
				}
			});
		},
		/**
		 * 取消
		 */
		onCancel : function() {
			lf.areaOrg.area.update.CloseWindow("cancel");
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
	lf.areaOrg.area.update.init();
});