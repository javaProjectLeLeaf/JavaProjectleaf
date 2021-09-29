var prefix = ctx + "park/parkManage"
//一堆对字段的条件限制
$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
                visible : false,
				field : 'id',
				title : '用户id'
			},
			{
				field : 'name',
				title : '姓名'
			},
			{
				field : 'sex',
				title : '性别'
			},
			{

				field : 'age',
				title : '年龄'
			},
			{
				field : 'phone',
				title : '电话'
			},
			{
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions.push('<a class="btn btn-success btn-xs '
									+ editFlag
									+ '" href="#" title="编辑" mce_href="#" onclick="edit(\''
									+ row.id
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					actions.push('<a class="btn btn-primary btn-xs '
									+ detailFlag
									+ '" href="#" title="停车详情" mce_href="#" onclick="detail(\''
									+ row.id
									+ '\')"><i class="fa fa-search"></i>停车详情</a> ');
					actions.push('<a class="btn btn-warning btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.id
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";

	$.initTable(columns, url,'请输入用户名');
});

/* 详情-详细 */
function detail(id) {
	var url = prefix + '/detail/' + id;
	layer_showAuto("用户详情", url);
}

/* 图书详情-新增 */
function add() {
	var url = prefix + '/add';
	layer_showAuto("新增用户", url);
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
	layer_showAuto("修改用户", url);
}

// 单条删除
function remove(id) {
	$.modalConfirm("确定要删除选中用户吗？", function() {
		_ajax(prefix + "/remove/" + id, "", "post");
	})
}

// 批量删除
function batchRemove() {
	alert(4444);
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
