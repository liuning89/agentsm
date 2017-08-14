/**
 * Created by Yangushan on 16/1/11.
 */
lc.ns("lf.areaOrg.store.storeMap");
lf.areaOrg.store.storeMap = {
    /**
     * 初始化
     */
    init : function() {
        this.map = new BMap.Map("map");
        this.longitude = $("#longitude").val();
        this.latitude = $("#latitude").val();
        this.cityName = $("#cityName").val();
        this.point; //只能有一个点
        if (this.latitude == null || this.latitude == "" ||
            this. longitude == null || this.longitude == "" ) {
            var myGeo = new BMap.Geocoder();
            myGeo.getPoint(this.cityName, function(point){
                lf.areaOrg.store.storeMap.map.centerAndZoom(point, 11);
            }, "");
        } else {
            var point = new BMap.Point(this.longitude,this.latitude);
            var marker = new BMap.Marker(point);
            this.map.addOverlay(marker);
            marker.enableDragging();
            this.map.centerAndZoom(point,12);
            this.point = marker;
            marker.addEventListener("dragend",function(){
                //var point = this.getPosition();
                lf.areaOrg.store.storeMap.point = this;
            });
        }
        //实例化鼠标绘制工具
        var drawingManager = new BMapLib.DrawingManager(this.map, {
            isOpen : false, //是否开启绘制模式
            enableDrawingTool : true, //是否显示工具栏
            drawingToolOptions : {
                anchor : BMAP_ANCHOR_TOP_RIGHT, //位置
                offset : new BMap.Size(5, 5), //偏离值
                drawingModes : [BMAP_DRAWING_MARKER]
            }
        });
        var overlaycomplete = function(e) {
            console.log(lf.areaOrg.store.storeMap.point);
            if (lf.areaOrg.store.storeMap.point != null) {
                lf.areaOrg.store.storeMap.map.removeOverlay(lf.areaOrg.store.storeMap.point);
            }
            e.overlay.enableDragging();
            e.currentTarget.close();
            lf.areaOrg.store.storeMap.point = e.overlay;
            e.overlay.addEventListener("dragend",function(){
                var point = this;
                lf.areaOrg.store.storeMap.point = point;
            });
        };
        //添加鼠标绘制工具监听事件，用于获取绘制结果
        drawingManager.addEventListener('overlaycomplete', overlaycomplete);
    },
    /**
     * 搜索功能
     */
    search : function() {
        var name = mini.get("name").getValue();
        var myGeo = new BMap.Geocoder();
        myGeo.getPoint(this.cityName + name, function(point){
            lf.areaOrg.store.storeMap.map.centerAndZoom(point, 16);
        }, this.cityName);
    },
    /**
     * 保存点
     */
    save : function() {
        if (this.point == null) {
            mini.alert("还未打点无法保存!");
            return ;
        }
        $.ajax({
            type : "post",
            url : "areaOrg/store/bd2gcj.do",
            data : {
                "longitude": this.point.point.lng,
                "latitude": this.point.point.lat
            },
            success:function(result){
                window.CloseOwnerWindow(result);
            }
        });
    }
};

$(function(){
    lf.areaOrg.store.storeMap.init();
});