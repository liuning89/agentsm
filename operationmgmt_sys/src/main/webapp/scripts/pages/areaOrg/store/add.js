/**
 * @author Yang'ushan
 * @created 2015年8月5日 上午11:26:24
 */
lc.ns("lf.areaOrg.store.add");
lf.areaOrg.store.add = {
		/**
		 * 初始化
		 */
		init : function() {
			mini.parse();
			this.form = new mini.Form("areaOrgAddForm");
			this.cityAreaOrgId = mini.get("cityAreaOrgId");
			this.loadCityAreaOrg();
			this.cityAreaOrgId.on("valuechanged", lc.proxyFn(function(e){
				this.loadAreaOrg();
				this.loadDistrict();
			},this));
			this.cityId = mini.get("cityId");
			this.districtId = mini.get("districtId");
			this.dg1 = mini.get("dg1");
			this.dg2 = mini.get("dg2");
			this.districtId.on("valuechanged", lc.proxyFn(function(){
				this.loadDg1();
			},this));
		},
		loadDistrict:function() {
			this.districtId.load(lc.rootPath(lc.strFormat("dicAreaNew/getDicAreaNewByParentId.do?parentId={0}", this.cityAreaOrgId.getSelected().cityId)));
			this.districtId.setData([{id:'',text:'请选择'}].concat(this.districtId.getData()));
			this.districtId.select(0);
		},
		loadDg1:function() {
			var districtId = this.districtId.getValue();
			if (districtId == null || districtId == "") {
				this.dg1.clearRows();
				return ;
			}
			$.ajax({
				method : "post", 
				url : "areaOrg/store/getStorePageTownListByDistrictId.do?districtId="+districtId,
				success : function(result) {
					var dg1 = mini.get("dg1");
					var datas1 = result;
					var datas2 = mini.get("dg2").getData();
					if (datas2.length > 0) {
						for (var i = 0; i < datas2.length; i++) {
							var data2 = datas2[i];
							for (var j = 0; j < datas1.length; j++) {
								var data1 = datas1[j];
								if (data2.townid == data1.townid) {
									datas1.splice(j, 1);
									break;
								}
							}
						}
					}
					dg1.setData(datas1);
				}
			});
		},
		/**
		 * 读取城市组织架构列表
		 */
		loadCityAreaOrg : function() {
			this.cityAreaOrgId.load(lc.rootPath(lc.strFormat("areaOrg/list/simple.action?level=90")));
			this.cityAreaOrgId.setData([{id:0,text:'请选择'}].concat(this.cityAreaOrgId.getData()));
			this.cityAreaOrgId.select(0);
		},
		/**
		 * 读取区域组织架构列表
		 */
		loadAreaOrg : function() {
			var parentId = mini.get("parentId");
			parentId.load(lc.rootPath(lc.strFormat("areaOrg/list/simple.action?level=70&parentId={0}", 
				this.cityAreaOrgId.getValue())));
			parentId.setData([{id:0,text:'请选择'}].concat(parentId.getData()));
			parentId.select(0);
		},
		/**
		 * 提交
		 */
		onOk : function() {
			var data = this.form.getData();
			if (data.cityAreaOrgId == "" || data.cityAreaOrgId == 0) {
				mini.alert("请选择城市架构！");
				return ;
			}
			if (data.parentId == "" || data.parentId == 0) {
				mini.alert("请选择区域架构");
				return ;
			}
			if (data.name == "") {
				mini.alert("名称不能为空！");
				return ;
			}
			if (data.longitude == "") {
				mini.alert("经度不能为空！");
				return ;
			}
			var reg = /^\+?(\d*\.\d{6})$/;
			if (!reg.test(data.longitude)) {
				mini.alert("经度必须精确到小数点后6位！");
				return ;
			}
			if (data.latitude == "") {
				mini.alert("纬度不能为空！");
				return ;
			}
			if (!reg.test(data.latitude)) {
				mini.alert("纬度必须精确到小数点后6位！");
				return ;
			}
			data.cityId = this.cityAreaOrgId.getSelected().cityId;
			data.type = mini.get("parentId").getSelected().type;
			var datas2 = this.dg2.getData();
			if (datas2.length == 0) {
				mini.alert("至少选择一个版块！");
				return ;
			}
			var towns = "";
			for (var i = 0; i < datas2.length; i++) {
				var data2 = datas2[i];
				towns += (data2.districtid + "," + data2.townid + ";")
			}
			data.towns = towns;
			$.ajax({
				method : "post", 
				url : "areaOrg/add.do",
				dataType: "json",
				data : data,
				success : function(result) {
					if (result.status == "1") {
						mini.alert("添加成功","提示",function(){
							lf.areaOrg.store.add.CloseWindow("save");
						});
					}
				}
			});
		},
		/**
		 * 取消
		 */
		onCancel : function() {
			lf.areaOrg.store.add.CloseWindow("cancel");
		},
		/**
		 * 关闭窗口
		 */
		CloseWindow : function(action) {
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		},
		/**
		 * 把左边表格移到右边
		 */
		addTown : function() {
			var rows = this.dg1.getSelecteds();
			if (rows.length == 0) {
				mini.alert("至少选中1行！");
				return ;
			}
			this.dg1.removeRows(rows);
			this.dg2.addRows(rows);
		},
		/**
		 * 把左边所有数据全部移到右边
		 */
		addAllTown : function() {
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
		 * 删除
		 */
		removeTown : function() {
			var rows = this.dg2.getSelecteds();
			if (rows.length == 0) {
				mini.alert("至少选中1行！");
				return ;
			}
			this.dg2.removeRows(rows);
			for (var i = 0; i < rows.length; i++) {
				var row = rows[i];
				if (row.districtid == this.districtId.value) {
					this.dg1.addRow(row);
				}
			}
		},
		/**
		 * 把右边全部移到左边
		 */
		removeTownAll : function() {
			var data2 = this.dg2.getData();
			if (data2.length == 0) {
				return ;
			}
			var d2 = new Array();
			for (var i = 0; i < data2.length; i++) {
				var data = data2[i];
				if (data.districtid == this.districtId.value) {
					d2.push(data2[i]);
				}
			}
			this.dg2.clearRows();
			if (d2.length == 0) {
				return ;
			}
			var data1 = this.dg1.getData();
			data1 = data1.concat(d2);
			this.dg1.setData(data1);
		},
		/**
		 * 打开地图
		 */
		openMap : function() {
			var longitude = mini.get("longitude").getValue();
			var latitude = mini.get("latitude").getValue();
			var storeName = mini.get("name").getValue();
			var cityId = this.cityAreaOrgId.getSelected().cityId;
			if (cityId == null || cityId == "" || cityId == 0) {
				mini.alert("请选择城市");
				return ;
			}
			if (storeName == null || storeName == "") {
				mini.alert("请填写门店名称");
				return ;
			}
			var win = mini.open({
				url: lc.rootPath(lc.strFormat("areaOrg/store/openContour.do?longitude={0}&latitude={1}&cityId={2}",longitude, latitude, cityId)),
				title: lc.strFormat("门店经纬度打点({0})", storeName), width: 800, height: 600,
				allowResize: true,       //允许尺寸调节
				allowDrag: true,         //允许拖拽位置
				showCloseButton: true,   //显示关闭按钮
				showMaxButton: true,     //显示最大化按钮
				ondestroy: lc.proxyFn(function (action) {
					if(action != null && action != "" && action != "close"){
						var datas = action.split(",");
						mini.get("longitude").setValue(datas[0]);
						mini.get("latitude").setValue(datas[1]);
					}
				},this)
			});
			win.max();
		}
};

$(function(){
	lf.areaOrg.store.add.init();
});