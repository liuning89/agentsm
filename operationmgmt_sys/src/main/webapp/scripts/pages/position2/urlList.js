/**
 * @author Yang'ushan
 * @created 2015年7月28日 下午4:17:53
 */
lc.ns("lf.position.urlList");
var search2List = new Array();
lf.position.urlList = {
		init : function () {
			mini.parse();
			mini.get("cityId").setValue(lc.queryParam("cityId"));
			mini.get("department").setValue(lc.queryParam("department"));
			mini.get("level").setValue(lc.queryParam("level"));
			var appName = lc.queryParam("appName");
			if (appName != "" && appName != undefined) {
				mini.get("appName").setValue(decodeURI(decodeURI(appName)));
			}
			this.dg1 = mini.get("dg1");
			this.dg2 = mini.get("dg2");
			this.gridInit();
			this.search2();
			this.dg2.on("load",lc.proxyFn(function(e){
				this.search1();
				this.saveData();
			},this));
			this.dg1.on("preload",lc.proxyFn(function(e){
				this.trim(e);
			},this));
		},
		/**
		 * 初始化
		 */
		gridInit : function() {
			this.dg1.on("drawcell", function(e){
				var field = e.field;
				var value = e.value;
				if (field == "urls") {
					var html = "";
					for (var i = 0; i < value.length; i++) {
						html += value[i].url + "<br />"
					}
					e.cellHtml = html;
				}
			});
			this.dg2.on("drawcell", function(e){
				var field = e.field;
				var value = e.value;
				if (field == "urls") {
					var html = "";
					for (var i = 0; value != null && i < value.length; i++) {
						html += value[i].url + "<br />"
					}
					e.cellHtml = html;
				}
			});
		},
		/**
		 * 排除右边存在的
		 */
		trim : function(e) {
			var data2 = this.dg2.getData();
			var data1 = e.data;
			for (var i = 0; i < data2.length; i++) {
				var d2 = data2[i];
				for (var j = 0; j < data1.length; j++) {
					var d1 = data1[j];
					if (d2.id == d1.id) { // 已经存在了
						data1.splice(j, 1);
						break ;
					}
				}
			}
		},
		/**
		 * 搜索左边
		 */
		search1 : function() {
			var data = {
				"appName":mini.get("appName").getValue(),
				"name":mini.get("name").getValue()
			};
			this.dg1.load(data);
		},
		/**
		 * 加载右边
		 */
		search2 : function() {
			this.dg2.load(new mini.Form("#search").getData());
		},
		/**
		 * 保存数据
		 */
		saveData : function() {
			// 把原始的数据存放在一个列表里面，最后提交的时候进行最后的判断
			for (var i = 0; i < this.dg2.getData().length; i++) {
				var data = this.dg2.getData()[i];
				search2List[i] = data.id;
			}
		},
		/**
		 * 把左边勾选的复制到右边
		 */
		add : function() {
			var rows = this.dg1.getSelecteds();
			if (rows.length == 0) {
				mini.alert("至少选中1行！");
				return ;
			}
			this.dg1.removeRows(rows);
			this.dg2.addRows(rows);
		},
		/**
		 * 把左边所有的移到右边
		 */
		addAll : function() {
			var data1 = this.dg1.getData();
			if (data1.length == 0) {
				return ;
			}
			this.dg1.clearRows();
			var data2 = this.dg2.getData();
			data2 = data2.concat(data1);
			this.dg2.setData(data2);
		},
		/**
		 * 把右边勾选到左边
		 */
		remove : function() {
			var rows = this.dg2.getSelecteds();
			if (rows.length == 0) {
				mini.alert("至少选中1行！");
				return ;
			}
			this.dg2.removeRows(rows);
			this.dg1.addRows(rows);
		},
		/**
		 * 把右边全部移到左边
		 */
		removeAll : function() {
			var data2 = this.dg2.getData();
			if (data2.length == 0) {
				return ;
			}
			this.dg2.clearRows();
			var data1 = this.dg1.getData();
			data1 = data1.concat(data2);
			this.dg1.setData(data1);
		},
		/**
		 * 保存
		 */
		save : function() {
			console.log(search2List);
			var addFunctionIds = "";
			var deleteFunctionIds = "";
			for (var i = 0; i < this.dg2.getData().length; i++) {
				var data = this.dg2.getData()[i];
				var flag = false;
				for (var j = 0; j < search2List.length; j++) {
					if (search2List[j] == data.id) { // 存在了，在search2List里面删除
						search2List.splice(j,1);
						flag = true;
						break ;
					}
				}
				if (flag == false) { // 不存在
					addFunctionIds += data.id + ",";
				}
			}
			for (var i = 0; i < search2List.length; i++) {
				deleteFunctionIds += search2List[i];
				if (i != search2List.length - 1) {
					deleteFunctionIds += ",";
				}
			}
			$.ajax({
				method : "post", 
				url : "position/savePositionPriFunction2.do",
				dataType: "json",
				data : {
					"level" : mini.get("level").getValue(),
					"department" : mini.get("department").getValue(),
					"cityId" : mini.get("cityId").getValue(),
					"addFunctionIds" : addFunctionIds,
					"deleteFunctionIds" : deleteFunctionIds
				},
				success : function(result) {
					if (result.status == "1") {
						mini.alert("保存成功");
						lf.position.urlList.CloseWindow("ok");
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
	lf.position.urlList.init();
});