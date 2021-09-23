var prefix = ctx + "room/roommanage"

$(function() {
	var columns = [
			{
				checkbox : true
			},
			{
				field : 'roomID',
				title : '房间ID'
			},
			{
				field : 'type',
				title : '图书名称'
			},
			{
				field : 'floor',
				title : '图书作者'
			},
        {
            field : 'status',
            title : '房间ID'
        },
        {
            field : 'user',
            title : '图书名称'
        },
        {
            field : 'describe',
            title : '图书作者'
        },
        {
            visible :false,
            field : 'more',
            title : '预留字段'
        },





			{
				title : '操作',
				align : 'center',
				formatter : function(value, row, index) {
					var actions = [];
					actions
							.push('<a class="btn btn-success btn-xs '
									+ editFlag
									+ '" href="#" title="编辑" mce_href="#" onclick="edit(\''
									+ row.bookId
									+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
					actions
							.push('<a class="btn btn-primary btn-xs '
									+ detailFlag
									+ '" href="#" title="详情" mce_href="#" onclick="detail(\''
									+ row.bookId
									+ '\')"><i class="fa fa-search"></i>详情</a> ');
					actions.push('<a class="btn btn-warning btn-xs '
							+ removeFlag
							+ '" href="#" title="删除" onclick="remove(\''
							+ row.bookId
							+ '\')"><i class="fa fa-remove"></i>删除</a>');
					return actions.join('');
				}
			} ];
	var url = prefix + "/list";
	$.initTable(columns, url,'请输入书名、作者、分类、藏地');
});

/* 详情-详细 */
function detail(id) {
	var url = prefix + '/detail/' + id;
	layer_showAuto("图书详情", url);
}

/* 图书详情-新增 */
function add() {
	var url = prefix + '/add';
	layer_showAuto("新增图书", url);
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
function edit(bookId) {
	var url = prefix + '/edit/' + bookId;
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
	var rows = $.getSelections("bookId");
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
