/**
 * @author Yang'ushan
 * @created 2015年7月24日 下午4:38:53
 * @include "../../lf/lf.common.js"
 */
lc.ns("lf.commissionRule.list");
lf.commissionRule.list = {
    init: function () {
        mini.parse();
        this.grid = mini.get("dg");
        var form = new mini.Form("#form");
        $.ajax({
            url: "commissionRule/getPercentage.action",
            type: "post",
            success: function (data) {
                var data = mini.decode(data);
                form.setData(data);
            }
        });

        this.search();
    },
    /**
     * 搜索
     */
    search: function () {
        this.grid.reload();
    },
    /**
     * 设置返回数据
     */
    setData: function (data) {
        mini.parse();
        var form = new mini.Form("#form");
        var o = mini.decode(data.json);
        form.setData(o);
    },
    /**
     * 添加一行
     */
    add: function () {
        var newRow = {};
        this.grid.addRow(newRow, 0);
        this.grid.beginEditCell(newRow, "name");
    },
    /**
     * 删除url
     */
    delete: function () {
        var rows = this.grid.getSelecteds();
        var lockedIds = new Array();
        var unlockedIds = new Array();
        if (rows.length > 0) {
            // 把这些id放入删除的数据里面
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].islocked == 1) {
                    lockedIds.push(rows[i].role);
                } else {
                    unlockedIds.push(rows[i]);
                }
            }
            if (lockedIds.length > 0) {
                mini.alert(lockedIds.join("、") + ",已被锁定不能删除！");
            }
            this.grid.removeRows(unlockedIds, true);
            unlockedIds = new Array();
        } else {
            mini.alert("选择一条数据！");
            return;
        }
    },
    /**
     * 保存数据
     */
    save: function () {
        var changes = this.grid.getChanges(null, false);
        if (!this.grid.isChanged()) {
            mini.alert("数据无更新！");
            return;
        }
        var data = this.grid.getData();
        var sumdistpercent = 0;
        for (var i = 0; i < data.length; i++) {
            if (!isNaN(data[i].distpercent)) {
                sumdistpercent += parseFloat(data[i].distpercent);
            }
        }
        if (data.length < 1 || sumdistpercent.toFixed(2) != 100.00) {
            mini.alert("总分配比例必须等于100！");
            return;
        }
        this.grid.validate();
        if (this.grid.isValid() == true) {
            if (confirm("是否确认保存？")) { // 确认保存
                $.ajax({
                    method: "post",
                    data: {
                        "json": mini.encode(changes)
                    },
                    url: "commissionRule/save.do",
                    dataType: "json",
                    success: function (result) {
                        if (result.status == 1) {
                            mini.alert("保存成功！");
                            mini.get("dg").reload();
                        }
                    }
                });
            }
        }
    },

    onCellValidation: function (e) {
        if (e.field == "distpercent") {
            if (isNaN(e.value)) {
                e.isValid = false;
                e.errorText = "请输入数字!";
            } else if (e.value <= 0) {
                e.isValid = false;
                e.errorText = "请输入大于0的正数!";
            } else {
                e.isValid = true;
            }

        } else if (e.sender.name == "commission") {
            if (e.value == "") {
                e.isValid = true;
            } else if (isNaN(e.value)) {
                e.isValid = false;
                e.errorText = "请输入数字!";
            } else if (e.value <= 0) {
                e.isValid = false;
                e.errorText = "请输入大于0的正数!";
            } else {
                e.isValid = true;
            }
        }

        if (!e.isValid) {
            mini.showTips({
                content: e.errorText,
                state: "warning",
                x: "Top",
                timeout: 1800
            });
        }
    },

    savePercentage: function () {
        var form = new mini.Form("#form");
        form.validate();
        var commission = mini.get("commission").getValue();
        if (commission > 100) {
            mini.alert("请输入小于100的值！");
            return;
        }
        if (form.isValid() == true) {
            var data = form.getData();      //获取表单多个控件的数据
            $.ajax({
                method: "post",
                data: {
                    data: mini.encode(data)
                },
                url: "commissionRule/savePercentage.action",
                success: function (result) {
                    if (result) {
                        var data = mini.decode(result);
                        form.setData(data);
                        mini.alert("保存成功！");
                    } else {
                        mini.alert("操作失败！");
                    }
                },
                error: function (result) {
                    mini.alert("操作失败！");
                }
            });
        } else {
            mini.showTips({
                content: "请输入大于0的正数",
                state: "warning",
                x: "Top",
                timeout: 1800
            });
        }
    },
    /**
     * 关闭窗口
     */
    CloseWindow: function (action) {
        if (window.CloseOwnerWindow)
            return window.CloseOwnerWindow(action);
        else
            window.close();
    }

};
$(function () {
    lf.commissionRule.list.init();
});