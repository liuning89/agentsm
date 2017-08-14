/**
 * @author Yang'ushan
 * @created 2015年7月29日 下午5:40:53
 * @include "../../lf/lf.common.js"
 */
lc.ns("lf.position.menuTreeList");
var oldMenuIds = new Array();
lf.position.menuTreeList = {
		init : function () {
			mini.parse();
			this.tree = mini.get("tree");
			mini.get("appName").setValue(decodeURI(decodeURI(lc.queryParam("appName"))));
			mini.get("cityId").setValue(lc.queryParam("cityId"));
			mini.get("level").setValue(lc.queryParam("level"));
			mini.get("department").setValue(lc.queryParam("department"));
			/*this.tree.on("beforenodeselect", lc.proxyFn(function(e){
				this.treeSelect(e);
				window.setTimeout(this.validateSelect, 150); // 判断勾选的是否有问题
			},this));*/
			this.tree.on("drawnode", lc.proxyFn(function(e){
				this.drawnode(e);
			},this));
			this.search();
		},
		/**
		 * 选择时，需要判断是否有子节点
		 * @param {} e
		 */
		treeSelect : function(e) {
			var node = e.node;
			if (node.isLeaf != "1") { // 本不是子节点，才做判断
				var childNodes = this.tree.getAllChildNodes(node); // 获取它的所有子节点
				if (childNodes != null && childNodes.length > 0) {
					for (var i = 0; i < childNodes.length; i++) {
						if (childNodes[i].isLeaf == "1") { // 有子节点
							return ;
						}
					}
				}
			} else {
				return ;
			}
			mini.alert("没有可选菜单！");
		},
		/**
		 * 选择节点之后，判断有些不可以 
		 * @param {} e
		 */
		validateSelect : function() {
			var tree = mini.get("tree");
			var childNodes = tree.getCheckedNodes();
			if (childNodes != null && childNodes.length > 0) {
				for (var i = 0; i < childNodes.length; i++) {
					var c = childNodes[i];
					if (c.isLeaf == "1") {
						continue ;
					}
					var childNodes2 = tree.getAllChildNodes(c);
					var isLeaf = false;
					if (childNodes2 != null && childNodes2.length > 0) {
						for (var j = 0; j < childNodes2.length; j++) {
							if (childNodes2[j].isLeaf == "1") { // 有子节点
								isLeaf = true;
								break;
							}
						}
					}
					if (!isLeaf) {
						tree.uncheckNode(c);
					}
				}
			}
		},
		/**
		 * 绘制时，对一些节点禁用
		 * @param {} e
		 */
		drawnode : function(e) {
			var node = e.node;
			if (node.isLeaf != "1") {
				e.showCheckBox = false;
			}
		},
		/**
		 * 搜索
		 */
		search : function() {
			$.ajax({
				method : "post", 
				url : "position/menuTree2.do",
				dataType:'text',
				data : new mini.Form("#search").getData(),
				success : function(result) {
					mini.get("tree").loadList(mini.decode(result), "id", "pid");
					var selects = mini.get("tree").getCheckedNodes();
					// 把选中的放入一个数组，最后做判断
					for (var i = 0; i < selects.length; i++) {
						var s = selects[i];
						if (s.isLeaf == "1") {
							oldMenuIds.push(s.id);
						}
					}
				}
			});
		},
		/**
		 * 保存
		 */
		save : function() {
			console.log(oldMenuIds);
			var nodes = this.tree.getCheckedNodes();
			var aIds="";
			var dIds="";
			for (var i = 0; i < nodes.length; i++) {
				var n = nodes[i];
				var flag = false;
				if (n.isLeaf == "1") { // 是子节点
					for (var j = 0; j < oldMenuIds.length; j++) {
						if (oldMenuIds[j] == n.id) { // 存在
							oldMenuIds.splice(j, 1);
							flag = true;
							break;
						}
					}
					if (flag == false) {
						aIds += n.id + ",";
					}
				}
			}
			for (var i = 0; i < oldMenuIds.length; i++) {
				dIds += oldMenuIds[i];
				if (i != oldMenuIds.length - 1) {
					dIds += ",";
				}
			}
			$.ajax({
				method : "post", 
				url : "position/saveMenu2.do",
				dataType:'json',
				data : {
					"level":mini.get("level").getValue(),
					"department":mini.get("department").getValue(),
					"cityId":mini.get("cityId").getValue(),
					"addMenuIds":aIds,
					"deleteMenuIds":dIds
				},
				success : function(result) {
					if (result.status == "1") {
						mini.alert("保存成功");
						lf.position.menuTreeList.CloseWindow("saveMenu");
					}
				}
			});
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
	lf.position.menuTreeList.init();
});