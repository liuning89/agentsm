/**
 * @author Yang'ushan
 * @created 2015年7月27日 下午4:35:24
 */
lc.ns("lf.menu.add");
lf.menu.add = {
		/**
		 * 初始化
		 */
		init : function() {
			mini.parse();
			this.form = new mini.Form("menuAddForm");
			this.isLeaf = mini.get("isLeaf");
			this.isLeaf.on("valuechanged",lc.proxyFn(function(e){
				lf.menu.add.changeIsLeaf();
			},this));
		},
		/**
		 * 修改是否是子节点
		 */
		changeIsLeaf : function() {
			if (this.isLeaf.getValue() == "1") { // 改为是子节点
				mini.get("url").enable(); // 可用
			} else if (this.isLeaf.getValue() == "0") { // 改为非子节点
				mini.get("url").setValue(null); // 清空url数据
				mini.get("url").disable(); // 不可用
			}
		},
		/**
		 * 查看功能列表
		 */
		functionList : function() {
			var appName = mini.get("appName").getValue();
			if (appName == "" || appName == "0") {
				mini.alert("请选择应用");
				return ;
			}
			mini.open({
				url : "pages/menu/functionList.jsp",
				title : "选择功能",
				width : 600,
				height : 400,
				ondestroy : function(action) {
					if (action != "" && action.split(",").length == 3) { // 选择了数据
						var ds = action.split(",");
						mini.get("functionId").setValue(ds[0]);
						mini.get("functionName").setValue(ds[1]);
						mini.get("appName").setValue(ds[2]);
					}
				}
			});
		},
		/**
		 * 解除功能
		 */
		clearFunction : function() {
			mini.get("functionId").setValue(null);
			mini.get("functionName").setValue(null);
			mini.get("appName").setValue(null);
		},
		/**
		 * 查看父级列表
		 */
		parentList : function() {
			var appName = mini.get("appName").getValue();
			if (appName == "" || appName == "0") {
				mini.alert("请选择应用");
				return ;
			}
			mini.open({
				url : "pages/menu/parentTreeList.jsp?appName="+encodeURI(encodeURI(mini.get("appName").getValue())),
				title : "选择父节点",
				width : 400,
				height : 330,
				ondestroy : function(action) {
					if (action != "" && action.split(",").length == 2) { // 选择了数据
						var ds = action.split(",");
						mini.get("parentId").setValue(ds[0]);
						mini.get("parentName").setValue(ds[1]);
					}
				}
			});
		},
		/**
		 * 解除父级
		 */
		clearParent : function() {
			mini.get("parentId").setValue(0);
			mini.get("parentName").setValue(null);
		},
		/**
		 * 提交
		 */
		onOk : function() {
			var data = this.form.getData();
			if (data.isLeaf == -1) {
				mini.alert("请选择是否子节点！");
				return ;
			}
			if (data.name == "") {
				mini.alert("名称不能为空！");
				return ;
			}
			if (data.parentId == "") {
				mini.alert("请选择上级！");
				return ;
			}
			if (data.appName == "" || data.appName == "0") {
				mini.alert("请选择应用！");
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
				url : "menu/add.do",
				dataType: "json",
				data : this.form.getData(),
				success : function(result) {
					if (result.status == "1") {
						mini.alert("添加成功");
						lf.menu.add.CloseWindow("save");
					}
				}
			});
		},
		/**
		 * 取消
		 */
		onCancel : function() {
			lf.menu.add.CloseWindow("cancel");
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
	lf.menu.add.init();
});