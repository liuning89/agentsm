/**
 * @author Yang'ushan
 * @created 2015年8月4日 下午2:06:24
 */
lc.ns("lf.areaOrg.area.add");
lf.areaOrg.area.add = {
		/**
		 * 初始化
		 */
		init : function() {
			mini.parse();
			this.form = new mini.Form("areaOrgAddForm");
			this.cityId = mini.get("cityId");
			this.parentId = mini.get("parentId");
			this.parentId.on("valuechanged", lc.proxyFn(function(e){
				this.loadFranchisee();
			},this));
			this.loadAreaOrg();
		},
		/**
		 * 读取组织架构列表
		 */
		loadAreaOrg : function() {
			var parentId = mini.get("parentId");
			parentId.load(lc.rootPath(lc.strFormat("areaOrg/list/simple.action?level=90")));
			parentId.setData([{id:0,text:'请选择'}].concat(parentId.getData()));
			parentId.select(0);
		},
		/**
		 * 读取加盟商
		 */
		loadFranchisee : function() {
			var franchiseeId = mini.get("franchiseeId");
			if (this.parentId == 0) { // 设置为空
				franchiseeId.setData([]);
			} else {
				franchiseeId.load(lc.rootPath(lc.strFormat("areaOrg/franchisee/getSimpleList/byCityId.action?cityId=" + this.parentId.getValue())));
				franchiseeId.setData([{id:0,text:'请选择'}].concat(franchiseeId.getData()));
				franchiseeId.select(0);
			}
		},
		/**
		 * 提交
		 */
		onOk : function() {
			var data = this.form.getData();
			if (data.parentId == "" || data.parentId == 0) {
				mini.alert("请选择城市架构");
				return ;
			}
			if (data.franchiseeId == "" || data.franchiseeId == 0) {
				mini.alert("请选择加盟商");
				return ;
			}
			var parentId = mini.get("parentId");
			data.cityId = parentId.getSelected().cityId;
			if (data.name == "") {
				mini.alert("名称不能为空！");
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
				url : "areaOrg/add.do",
				dataType: "json",
				data : data,
				success : function(result) {
					if (result.status == "1") {
						mini.alert("添加成功","提示",function(){
							lf.areaOrg.area.add.CloseWindow("save");
						});
					}
				}
			});
		},
		/**
		 * 取消
		 */
		onCancel : function() {
			lf.areaOrg.area.add.CloseWindow("cancel");
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
	lf.areaOrg.area.add.init();
});