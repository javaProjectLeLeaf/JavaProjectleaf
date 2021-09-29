var prefix = ctx + "car/carManage";
//一堆对字段的条件限制
$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
                visible : false,
				field : 'id',
				title : 'id'
			},
			{
				field : 'code',
				title : '车牌'
			},
        {
            field: 'type',
            title: '车型',
            formatter : function(value, row, index) {
                if (value == 0) {
                    return '<span class="label label-success">C型</span>';
                } else if (value == 1) {
                    return '<span class="label label-warning">B型</span>';
                }else if (value == 2) {
                    return '<span class="label label-warning">A型</span>';
                }
            }
        },
			{
				field : 'indate',
				title : '入场时间'
			},
			{
				field : 'outdate',
				title : '出场时间'
			},
			{
				field : 'name',
				title : '车主姓名'
			},
			{
				field : 'phone',
				title : '联系电话'
			},
			{
				field : 'cost',
				title : '停车费'
			},
			{
				field : 'status',
				title : '状态',
                formatter : function(value, row, index) {
                    if (value == 0) {
                        return '<span class="label label-success">在场</span>';
                    } else if (value == 1) {
                        return '<span class="label label-warning">离场</span>';
                    }
                }
			},
			{
				field : 'pay',
				title : '付费状态',
				align : 'center',

				formatter : function(value, row, index) {
					if (value == 0) {
						return '<span class="label label-success">已付</span>';
					} else if (value == 1) {
						return '<span class="label label-warning">未付</span>';
					}
				}
			},
			{
                visible : false,
				field : 'createtime',
				title : '创建时间'
			},
			{
				visible : false,
				field : 'createid',
				title : '创建者'
			},
			{
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions.push(
						'<a class="btn btn-success btn-xs '
									+ editFlag
									+ '" href="#" title="编辑" mce_href="#" onclick="edit(\''
									+ row.id
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					if(row.status==0){
                        actions.push('<a class="btn btn-primary btn-xs '
                            + leaveFlag
                            + '" href="#" id="lc" title="离场" mce_href="#" onclick="leave(\''
                            + row.id
                            + '\')"><i class="fa fa-search"></i>离场</a> ');
					}
					actions.push('<a class="btn btn-primary btn-xs '
									+ detailFlag
									+ '" href="#" title="详情" mce_href="#" onclick="detail(\''
									+ row.id
									+ '\')"><i class="fa fa-search"></i>详情</a> ');
					actions.push('<a class="btn btn-warning btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.id
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable(columns, url,'请输入车牌号');
});

/* 详情-详细 */
function detail(id) {
	var url = prefix + '/detail/' + id;
	layer_showAuto("车辆详情", url);
}


function leave(id) {
    $.modalConfirm("确认离场？", function() {
        _ajax(prefix + "/leave/" + id, "", "post");
    })
}

/* 图书详情-新增 */
function add() {
	var url = prefix + '/add';
	layer_showAuto("车辆入场登记", url);
}

/* 批量上传图书详情-新增 */
function batchAdd() {
	var url = prefix + '/batchAdd';
	// 固定大小更宽
	layer_showAuto2("批量上传图书", url);
}

/* 全部导出excel */
function batchExport() {
	var table1Length = document.getElementById("table1").rows[1].cells.length;
	if (table1Length == 1) {
		// 没有数据
		$.modalMsg("没有图书可供导出", "warning");
		return;
	} else {
		location.href = prefix + "/batchExport";
		layer.msg("正在导出图书,请稍后……", {
			icon : 1,
			shade : [ 0.1, '#fff' ]
		});

	}
}

/* 图书详情-修改 */
function edit(id) {
	var url = prefix + '/edit/' + id;
	layer_showAuto("修改图书", url);
}

// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中图书吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}

// 批量删除
function batchRemove() {
	var rows = $.getSelections("id");
	if (rows.length == 0) {
		$.modalMsg("请选择要删除的数据", "warning");
		return;
	}
	$.modalConfirm("确认要删除选中的" + rows.length + "条数据吗?", function() {
		_ajax(prefix + '/batchRemove', {
			"ids" : rows
		}, "post");
	});
}
